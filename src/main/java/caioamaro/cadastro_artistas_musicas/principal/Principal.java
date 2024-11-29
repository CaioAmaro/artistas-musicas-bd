package caioamaro.cadastro_artistas_musicas.principal;

import caioamaro.cadastro_artistas_musicas.model.Artista;
import caioamaro.cadastro_artistas_musicas.model.CategoriaArtista;
import caioamaro.cadastro_artistas_musicas.model.Musica;
import caioamaro.cadastro_artistas_musicas.repository.ArtistaRepository;
import caioamaro.cadastro_artistas_musicas.services.ConsultaGemini;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ArtistaRepository artistaRepository;

    private Optional<Artista> pesquisaArtista;

    public Principal(ArtistaRepository artistaRepository){
        this.artistaRepository = artistaRepository;
    }

    public void execute(){
        var opcaoMenu = -1;

        while(opcaoMenu != 0){
            exibirMenu();
            System.out.print("Escolha: ");
            opcaoMenu = leitura.nextInt();
            leitura.nextLine();

            switch (opcaoMenu){
                case 1:
                    cadastrarArtista();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    listarTodosArtistas();
                    break;
                case 5:
                    listarMusicasPorTrechos();
                    break;
                case 6:
                    obterBiografia();
                    break;
                case 7:
                    obterInfoMusica();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opcão Incorreta");
            }
        }

    }

    public void exibirMenu(){
        var menu = """
                
                Menu Opções
                
                1 - Cadastrar Artista/Dupla/Banda
                2 - Cadastrar Músicas
                3 - Listar Músicas
                4 - Listar Artista/Dupla/Banda Cadastradas
                5 - Procurar Músicas com trecho
                6 - Saber mais Sobre um Artista/Dupla/Banda
                7 - Saber mais Sobre determinada Música de um cantor
                
                0 - Sair da aplicação
                
                """;

        System.out.println(menu);
    }

    public void cadastrarArtista(){
        var verificao = -1; //VARIAVEL PARA CONTROLAR A INTEGRIDADE DA CategoriaArtista.

        System.out.println();
        System.out.println("Você selecionou a opção de cadastrado - Artista/Dupla/Banda");
        System.out.println();
        System.out.println("Qual categoria você irá registrar? (SOLO, DUPLA, BANDA)");

        CategoriaArtista categoria = null;
        while(verificao == -1) {
            System.out.print("Categoria: ");
            try {
                var categoriaArtista = leitura.nextLine();
                categoria = CategoriaArtista.fromString(categoriaArtista);
                verificao = 1; // DEU CERTO!!
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println();
        System.out.print("Nome: ");
        var nomeArtista = leitura.nextLine();

        Artista artista = new Artista(nomeArtista.toUpperCase(),categoria);

        System.out.println();
        artistaRepository.save(artista);
        System.out.println("Cadastrado com sucesso!");
        System.out.println(artista);
    }

    public void cadastrarMusicas(){
        listarTodosArtistas();
        procurarArtistaPorNome("Essa música seria de qual artista? ");

        if(pesquisaArtista.isPresent()){
            var artista = pesquisaArtista.get();
            System.out.println("Digite o nome da Música");
            var nomeMusica = leitura.nextLine();
            var musicaCriada = new Musica(nomeMusica.toUpperCase());
            artista.setMusicas(musicaCriada);
            System.out.println("Música Cadastrada com sucesso!");
            artistaRepository.save(artista);
        }else{
            System.out.println("Artista não encontrado");
        }
//



    }

    public void listarTodosArtistas(){
        List<Artista> artistas = artistaRepository.findAll();

        System.out.println();
        System.out.println("Artistas / Duplas / Bandas cadastradas");
        artistas.forEach(a -> System.out.println(a.getCategoriaArtista() + " - " + a.getNome()));
        System.out.println();
    }

    public void listarMusicas(){
        var musicas = artistaRepository.listarMusicas();

        musicas.forEach(e -> System.out.println(e.getArtista().getNome() + " - " + e.getNome()));
    }

    public  void listarMusicasPorTrechos(){

        System.out.print("Digite o trecho da música para encontrar: ");
        var trecho = leitura.nextLine();

        List<Musica> musicas = artistaRepository.listarMusicasPorTrecho(trecho);

        System.out.println("As músicas correspondentes são: ");
        musicas.forEach(m -> System.out.println(m.getArtista().getNome() + " - " + m.getNome()));

    }

    public  void obterBiografia (){
        listarTodosArtistas();
        procurarArtistaPorNome("Você gostária de saber mais sobre quem?");

        if(this.pesquisaArtista.isPresent()){
            var nomeArtista = pesquisaArtista.get().getNome();
            var biografia = ConsultaGemini.obterBiografia(nomeArtista);
            System.out.println(biografia);
        }else{
            System.out.println("Artista Não encontrado em nosso banco");
        }

    }

    public void obterInfoMusica(){
        listarTodosArtistas();
        procurarArtistaPorNome("Você gostária de saber mais sobre quem?");

        if(pesquisaArtista.isPresent()){
            var artista = pesquisaArtista.get();
            List<Musica> musicas = artistaRepository.listarMusicaPorArtista(artista);
            System.out.println("Músicas Encontradas: ");
            musicas.forEach(m -> System.out.println("Musica: " + m.getNome()));
            System.out.println();
            System.out.println("Qual Música você gostaria de saber mais sobre? ");
            var nomeMusica = leitura.nextLine();

            Optional<Musica> musica = artistaRepository.listarMusicaEspecifica(artista, nomeMusica.toUpperCase());

            if (musica.isPresent()){
                var infoMusica = ConsultaGemini.obterInfoMusica(artista.getNome(), nomeMusica);
                System.out.println(infoMusica);
            }else {
                System.out.println("Musica não encontrada! ");
            }

        }else{
            System.out.println("Artista não encontrado!");
        }

    }

    public void procurarArtistaPorNome(String mensagem){
        System.out.println(mensagem);
        var artistaProcurado = leitura.nextLine();

        this.pesquisaArtista = artistaRepository.procurarArtistaPorNome(artistaProcurado.toUpperCase());
    }
}
