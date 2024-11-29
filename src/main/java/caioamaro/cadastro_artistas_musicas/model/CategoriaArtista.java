package caioamaro.cadastro_artistas_musicas.model;

public enum CategoriaArtista {
    SOLO("Solo"),
    DUPLA("Dupla"),
    BANDA("Banda");

    private String categoriaString;

    CategoriaArtista(String categoria){
        this.categoriaString = categoria;
    }

    public static CategoriaArtista fromString(String text){
        for (CategoriaArtista categoria: CategoriaArtista.values()){
            if(categoria.categoriaString.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma opção válida foi encontrada com nome de ["+text+"]");
    }

}
