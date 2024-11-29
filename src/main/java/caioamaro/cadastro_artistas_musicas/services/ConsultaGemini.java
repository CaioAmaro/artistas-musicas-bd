package caioamaro.cadastro_artistas_musicas.services;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class ConsultaGemini {
    private static final String API_KEY = System.getenv("API_KEY_GEMINI");

    public static String obterBiografia(String texto) {

        ChatLanguageModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey(API_KEY)
                .modelName("gemini-1.5-flash")
                .build();

        return gemini.generate("Quem é : " + texto).trim();
    }

    public static String obterInfoMusica(String Artista, String musica) {

        ChatLanguageModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey(API_KEY)
                .modelName("gemini-1.5-flash")
                .build();

        return gemini.generate("Me conte sobre a música  "+musica+" de(o) "+ Artista + "em poucas palavras").trim();
    }

}
