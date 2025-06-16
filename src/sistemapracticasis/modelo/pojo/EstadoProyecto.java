package sistemapracticasis.modelo.pojo;

/**
 *
 * @author uriel
 */
public enum EstadoProyecto {
    ACTIVO("activo"),
    CONCLUIDO("concluido"),
    CANCELADO("cancelado");

    private final String VALOR_EN_DB;

    private EstadoProyecto(String valorEnDB) {
        this.VALOR_EN_DB = valorEnDB;
    }

    public String getValorEnDB() {
        return VALOR_EN_DB;
    }

    public static EstadoProyecto fromValor(String texto) {
        for (EstadoProyecto estado : values()) {
            if (estado.VALOR_EN_DB.equalsIgnoreCase(texto)) {
                return estado;
            }
        }
        throw new IllegalArgumentException
            ("Ningún estado con el texto " + texto + " encontrado");
    }
}
