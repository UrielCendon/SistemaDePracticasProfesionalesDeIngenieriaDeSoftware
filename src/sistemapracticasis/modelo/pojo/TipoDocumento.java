package sistemapracticasis.modelo.pojo; 

/**
 * Autor: Uriel Cendón
 * Fecha de creación: 13/06/2025
 * Descripción: Enumeración que representa los tipos de documentos utilizados
 * en el sistema de prácticas profesionales. Cada tipo de documento tiene un
 * valor asociado que se almacena en la base de datos.
 */
public enum TipoDocumento {
    
    /**
     * Tipo de documento para carta de aceptación.
     */
    CARTAACEPTACION("carta de aceptacion"),
    
    /**
     * Tipo de documento para constancia de seguro.
     */
    CONSTANCIASEGURO("constancia seguro"),
    
    /**
     * Tipo de documento para cronograma de actividades.
     */
    CRONOGRAMAACTIVIDADES("cronograma actividades"),
    
    /**
     * Tipo de documento para horario UV.
     */
    HORARIOUV("horario uv"),
    
    /**
     * Tipo de documento para oficio de asignación.
     */
    OFICIOASIGNACION("oficio asignacion");

    /**
     * Valor que se almacena en la base de datos para este tipo de documento.
     */
    private final String VALOR_EN_DB;

    /**
     * Constructor de la enumeración TipoDocumento.
     * 
     * @param valorEnDB El valor del tipo de documento en la base de datos.
     */
    private TipoDocumento(String valorEnDB) {
        this.VALOR_EN_DB = valorEnDB;
    }

    /**
     * Obtiene el valor asociado al tipo de documento en la base de datos.
     * 
     * @return El valor de tipo de documento en la base de datos.
     */
    public String getValorEnDB() {
        return VALOR_EN_DB;
    }

    /**
     * Obtiene el tipo de documento correspondiente al valor proporcionado.
     * 
     * @param texto El valor del tipo de documento en formato de texto.
     * @return El tipo de documento correspondiente al valor proporcionado.
     * @throws IllegalArgumentException Si el texto no corresponde a un tipo de
     *         documento válido.
     */
    public static TipoDocumento fromValor(String texto) {
        for (TipoDocumento tipo : values()) {
            if (tipo.VALOR_EN_DB.equalsIgnoreCase(texto)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException(
            "Ningún estado con el texto " + texto + " encontrado"
        );
    }
}
