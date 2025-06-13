package enums;

public enum CategoriaJuego {
    DEPORTES("Deportes"),
    ACCION("Accion"),
    AVENTURA("Aventura"),
    RPG("RPG"),
    ESTRATEGIA("Estrategia"),
    SIMULACION("Simulación"),
    INDIE("Indie");

    private final String displayName;

    CategoriaJuego(String displayName) {
        this.displayName = displayName;
    }
    @Override
    public String toString() {          // Se imprime el nombre legible
        return displayName;
    }
}

