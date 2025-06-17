package sistemapracticasis.modelo.pojo;

/** 
 * Autor: Uriel Cendón
 * Fecha de creación: 14/06/2025
 * Descripción: Enum que representa el estado de un proyecto dentro del sistema.
 * Los posibles estados son: ACTIVO, CONCLUIDO y CANCELADO. Cada estado 
 * tiene un valor asociado que se utiliza para almacenarlo en la base de datos.
 */
public enum EstadoProyecto {
    
    /**
     * El proyecto está en estado activo.
     */
    ACTIVO("activo"),
    
    /**
     * El proyecto está concluido.
     */
    CONCLUIDO("concluido"),
    
    /**
     * El proyecto está cancelado.
     */
    CANCELADO("cancelado");

    /**
     * Valor que se utiliza para representar el estado en la base de datos.
     */
    private final String VALOR_EN_DB;

    /**
     * Constructor privado para asignar el valor del estado en la base de datos.
     * 
     * @param valorEnDB El valor asociado al estado en la base de datos.
     */
    private EstadoProyecto(String valorEnDB) {
        this.VALOR_EN_DB = valorEnDB;
    }

    /**
     * Obtiene el valor que se utiliza para almacenar el estado en la base de datos.
     * 
     * @return El valor del estado en la base de datos.
     */
    public String getValorEnDB() {
        return VALOR_EN_DB;
    }

    /**
     * Convierte un texto a su correspondiente estado de proyecto.
     * 
     * @param texto El texto que representa el estado del proyecto.
     * @return El estado correspondiente al texto proporcionado.
     * @throws IllegalArgumentException Si no se encuentra un estado con el 
     * texto dado.
     */
    public static EstadoProyecto fromValor(String texto) {
        for (EstadoProyecto estado : values()) {
            if (estado.VALOR_EN_DB.equalsIgnoreCase(texto)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Ningún estado con el texto " + 
            texto + " encontrado");
    }
}
