package sistemapracticasis.modelo.pojo;

/**
 * Autor: Miguel Escobar 
 * Fecha de creación: 15/06/2025
 * Descripción: Clase que representa el resultado de una operación, incluyendo
 * un indicador de error y un mensaje asociado. Es utilizada para manejar
 * los resultados de operaciones dentro del sistema.
 */
public class ResultadoOperacion {
    
    /**
     * Indica si ocurrió un error en la operación.
     */
    private boolean error;
    
    /**
     * Mensaje asociado al resultado de la operación. 
     * Puede contener información sobre el error o el éxito de la operación.
     */
    private String mensaje;

    /**
     * Constructor por defecto.
     * Crea un objeto ResultadoOperacion sin valores iniciales.
     */
    public ResultadoOperacion() {
    }

    /**
     * Constructor con parámetros.
     * Crea un objeto ResultadoOperacion con el valor del error y el mensaje 
     * proporcionados.
     * 
     * @param error Indicador de si hubo un error en la operación.
     * @param mensaje Mensaje relacionado con el resultado de la operación.
     */
    public ResultadoOperacion(boolean error, String mensaje) {
        this.error = error;
        this.mensaje = mensaje;
    }

    /**
     * Obtiene el estado del error de la operación.
     * 
     * @return true si hubo un error, false si no hubo error.
     */
    public boolean isError() {
        return error;
    }

    /**
     * Establece el estado del error de la operación.
     * 
     * @param error true si ocurrió un error, false si no ocurrió.
     */
    public void setError(boolean error) {
        this.error = error;
    }

    /**
     * Obtiene el mensaje relacionado con el resultado de la operación.
     * 
     * @return El mensaje que describe el resultado de la operación.
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece el mensaje relacionado con el resultado de la operación.
     * 
     * @param mensaje El mensaje que se asociará al resultado de la operación.
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
