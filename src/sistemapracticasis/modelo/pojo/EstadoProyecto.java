package sistemapracticasis.modelo.pojo;

/**
 *
 * @author uriel
 */
public enum EstadoProyecto {
    ACTIVO("activo"),
    CONCLUIDO("concluido"),
    CANCELADO("cancelado");

    private final String valorEnDB;

    private EstadoProyecto(String valorEnDB) {
        this.valorEnDB = valorEnDB;
    }

    public String getValorEnDB() {
        return valorEnDB;
    }

    public static EstadoProyecto fromValor(String texto) {
        for (EstadoProyecto estado : values()) {
            if (estado.valorEnDB.equalsIgnoreCase(texto)) {
                return estado;
            }
        }
        throw new IllegalArgumentException
            ("Ningún estado con el texto " + texto + " encontrado");
    }
}
