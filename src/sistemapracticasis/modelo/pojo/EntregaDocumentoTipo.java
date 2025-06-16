package sistemapracticasis.modelo.pojo;

/**
 *
 * @author uriel
 */
public enum EntregaDocumentoTipo {
    INICIAL("inicial"),
    INTERMEDIO("intermedio"),
    FINAL("final");

    private final String VALOR_EN_DB;

    private EntregaDocumentoTipo (String valorEnDB) {
        this.VALOR_EN_DB = valorEnDB;
    }

    public String getValorEnDB() {
        return VALOR_EN_DB;
    }

    public static EntregaDocumentoTipo fromValor(String texto) {
        for (EntregaDocumentoTipo tipo : values()) {
            if (tipo.VALOR_EN_DB.equalsIgnoreCase(texto)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException
            ("Ningún estado con el texto " + texto + " encontrado");
    }
}
