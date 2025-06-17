package sistemapracticasis.modelo.pojo;

/** 
 * Autor: Uriel Cendón
 * Fecha de creación: 14/06/2025
 * Descripción: Enum que representa el estado de un expediente dentro del 
 * sistema .Los posibles estados son: EN CURSO, CONCLUIDO y CERRADO. Cada estado
 * tiene un valor asociado que se utiliza para almacenarlo en la base de datos.
 */
public enum EstadoExpediente {
    
    /**
     * El expediente está en curso.
     */
    ENCURSO("en curso"),
    
    /**
     * El expediente ha sido concluido.
     */
    CONCLUIDO("concluido"),
    
    /**
     * El expediente está cerrado.
     */
    CERRADO("cerrado");

    /**
     * Valor que se utiliza para representar el estado en la base de datos.
     */
    private final String VALOR_EN_DB;

    /**
     * Constructor privado para asignar el valor del estado en la base de datos.
     * 
     * @param valorEnDB El valor asociado al estado en la base de datos.
     */
    private EstadoExpediente(String valorEnDB) {
        this.VALOR_EN_DB = valorEnDB;
    }

    /**
     * Obtiene el valor que se utiliza para almacenar el estado en la base de 
     * datos.
     * 
     * @return El valor del estado en la base de datos.
     */
    public String getValorEnDB() {
        return VALOR_EN_DB;
    }

    /**
     * Convierte un texto a su correspondiente estado de expediente.
     * 
     * @param texto El texto que representa el estado del expediente.
     * @return El estado correspondiente al texto proporcionado.
     * @throws IllegalArgumentException Si no se encuentra un estado con el 
     * texto dado.
     */
    public static EstadoExpediente fromValor(String texto) {
        for (EstadoExpediente estado : values()) {
            if (estado.VALOR_EN_DB.equalsIgnoreCase(texto)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Ningún estado con el texto " + 
            texto + " encontrado");
    }
}
