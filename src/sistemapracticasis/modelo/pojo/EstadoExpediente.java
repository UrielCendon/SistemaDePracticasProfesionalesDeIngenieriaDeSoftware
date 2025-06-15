package sistemapracticasis.modelo.pojo;

/**
 *
 * @author uriel
 */
public enum EstadoExpediente {
    ENCURSO("en curso"),
    CONCLUIDO("concluido"),
    CERRADO("cerrado");

    private final String valorEnDB;

    private EstadoExpediente (String valorEnDB) {
        this.valorEnDB = valorEnDB;
    }

    public String getValorEnDB() {
        return valorEnDB;
    }

    public static EstadoExpediente fromValor(String texto) {
        for (EstadoExpediente estado : values()) {
            if (estado.valorEnDB.equalsIgnoreCase(texto)) {
                return estado;
            }
        }
        throw new IllegalArgumentException
            ("Ningún estado con el texto " + texto + " encontrado");
    }
}
