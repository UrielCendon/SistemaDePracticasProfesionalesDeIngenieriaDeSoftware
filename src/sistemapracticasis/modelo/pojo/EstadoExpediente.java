package sistemapracticasis.modelo.pojo;

/**
 *
 * @author uriel
 */
public enum EstadoExpediente {
    ENCURSO("en curso"),
    CONCLUIDO("concluido"),
    CERRADO("cerrado");

    private final String VALOR_EN_DB;

    private EstadoExpediente (String valorEnDB) {
        this.VALOR_EN_DB = valorEnDB;
    }

    public String getValorEnDB() {
        return VALOR_EN_DB;
    }

    public static EstadoExpediente fromValor(String texto) {
        for (EstadoExpediente estado : values()) {
            if (estado.VALOR_EN_DB.equalsIgnoreCase(texto)) {
                return estado;
            }
        }
        throw new IllegalArgumentException
            ("Ningún estado con el texto " + texto + " encontrado");
    }
}
