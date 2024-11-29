package caioamaro.cadastro_artistas_musicas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "musicas")
public class Musica {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    private Artista artista;


    public Musica(){}

    public Musica(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }
}
