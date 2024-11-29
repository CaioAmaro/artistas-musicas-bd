package caioamaro.cadastro_artistas_musicas.repository;

import caioamaro.cadastro_artistas_musicas.model.Artista;
import caioamaro.cadastro_artistas_musicas.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    @Query("""
            SELECT a
            FROM Artista a
            WHERE a.nome = :nomeArtista
            """)
    Optional<Artista> procurarArtistaPorNome(String nomeArtista);

    @Query("""
            SELECT m
            FROM Artista a
            JOIN a.musicas m
            """)
    List<Musica> listarMusicas();

    @Query("""
            SELECT m
            FROM Artista a
            JOIN a.musicas m
            WHERE m.nome ILIKE %:trecho%
            """)
    List<Musica> listarMusicasPorTrecho(String trecho);

    @Query("""
            SELECT m
            FROM Artista a
            JOIN a.musicas m
            WHERE a = :artista
            """)
    List<Musica> listarMusicaPorArtista(Artista artista);

    @Query("""
            SELECT m
            FROM Artista a
            JOIN a.musicas m
            WHERE a = :artista AND m.nome = :musica
            """)
    Optional<Musica> listarMusicaEspecifica(Artista artista, String musica);

}
