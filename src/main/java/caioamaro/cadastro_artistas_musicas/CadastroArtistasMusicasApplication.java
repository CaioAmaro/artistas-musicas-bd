package caioamaro.cadastro_artistas_musicas;

import caioamaro.cadastro_artistas_musicas.principal.Principal;
import caioamaro.cadastro_artistas_musicas.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CadastroArtistasMusicasApplication implements CommandLineRunner {

	@Autowired
	private ArtistaRepository artistaRepository;

	public static void main(String[] args) {
		SpringApplication.run(CadastroArtistasMusicasApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(artistaRepository);
		principal.execute();
	}
}
