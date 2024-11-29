package caioamaro.cadastro_artistas_musicas.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ValueGenerationType;

import java.util.List;

@Entity
@Table(name = "artistas")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    @Enumerated(EnumType.STRING)
    private CategoriaArtista categoriaArtista;

    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Musica> musicas;

    public Artista(){}

    public Artista(String nome, CategoriaArtista categoriaArtista){
        this.nome = nome;
        this.categoriaArtista = categoriaArtista;
    }

    public String getNome() {
        return nome;
    }

    public CategoriaArtista getCategoriaArtista() {
        return categoriaArtista;
    }

    public void setMusicas(Musica musica) {
        musica.setArtista(this);
        this.musicas.add(musica);
    }

    @Override
    public String toString() {
        if(this.musicas == null){
            return "Nome: " + nome + ", Tipo: " + categoriaArtista;
        }

        return "Nome: " + nome + ", Tipo: " + categoriaArtista + ", Musicas = " + musicas;
    }
}
