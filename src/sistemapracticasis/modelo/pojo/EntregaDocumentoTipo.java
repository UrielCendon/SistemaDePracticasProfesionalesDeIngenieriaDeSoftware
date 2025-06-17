package sistemapracticasis.modelo.pojo;

/** 
 * Autor: Uriel Cendón
 * Fecha de creación: 13/06/2025
 * Descripción: Enumeración que define los diferentes tipos de entrega de 
 * documentos (inicial, intermedio y final) asociados a un proyecto o actividad.
 */
public enum EntregaDocumentoTipo {
    
    /**
     * Tipo de entrega inicial.
     */
    INICIAL("inicial"),
    
    /**
     * Tipo de entrega intermedia.
     */
    INTERMEDIO("intermedio"),
    
    /**
     * Tipo de entrega final.
     */
    FINAL("final");

    /**
     * El valor que representa el tipo de entrega en la base de datos.
     */
    private final String VALOR_EN_DB;

    /**
     * Constructor que inicializa el valor en base de datos del tipo de entrega.
     * 
     * @param valorEnDB El valor que representa el tipo de entrega en la base 
     * de datos.
     */
    private EntregaDocumentoTipo(String valorEnDB) {
        this.VALOR_EN_DB = valorEnDB;
    }

    /**
     * Obtiene el valor del tipo de entrega en la base de datos.
     * 
     * @return El valor del tipo de entrega en la base de datos.
     */
    public String getValorEnDB() {
        return VALOR_EN_DB;
    }

    /**
     * Convierte un texto en el tipo de entrega correspondiente.
     * 
     * @param texto El texto que representa el tipo de entrega.
     * @return El tipo de entrega correspondiente.
     * @throws IllegalArgumentException Si no se encuentra un tipo de entrega 
     * que coincida con el texto.
     */
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
