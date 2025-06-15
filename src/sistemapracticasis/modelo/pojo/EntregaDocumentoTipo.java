package sistemapracticasis.modelo.pojo;

/**
 *
 * @author uriel
 */
public enum EntregaDocumentoTipo {
    INICIAL("inicial"),
    INTERMEDIO("intermedio"),
    FINAL("final");

    private final String valorEnDB;

    private EntregaDocumentoTipo (String valorEnDB) {
        this.valorEnDB = valorEnDB;
    }

    public String getValorEnDB() {
        return valorEnDB;
    }

    public static EntregaDocumentoTipo fromValor(String texto) {
        for (EntregaDocumentoTipo tipo : values()) {
            if (tipo.valorEnDB.equalsIgnoreCase(texto)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException
            ("Ningún estado con el texto " + texto + " encontrado");
    }
}
