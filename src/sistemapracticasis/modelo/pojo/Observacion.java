/**
 * Autor: Uriel Cendón
 * Fecha de creación: 13/06/2025
 * Descripción: Clase POJO que representa una observación realizada por 
 * un profesor en una entrega de un estudiante dentro del sistema de prácticas 
 * profesionales.
 * Contiene una descripción y la fecha en que fue realizada.
 */
package sistemapracticasis.modelo.pojo;

/**
 * Representa una observación asociada a una entrega de estudiante.
 */
public class Observacion {
    /**
     * Identificador único de la observación.
     */
    private int idObservacion;

    /**
     * Descripción textual de la observación.
     */
    private String descripcion;

    /**
     * Fecha en la que se registró la observación (formato: AAAA-MM-DD).
     */
    private String fechaObservacion;

    /**
     * Constructor para inicializar una observación con todos sus atributos.
     * @param idObservacion Identificador de la observación.
     * @param descripcion Descripción de la observación.
     * @param fechaObservacion Fecha en que se registró la observación.
     */
    public Observacion(int idObservacion, String descripcion, String 
            fechaObservacion) {
        this.idObservacion = idObservacion;
        this.descripcion = descripcion;
        this.fechaObservacion = fechaObservacion;
    }

    /**
     * Obtiene el identificador de la observación.
     * @return El ID de la observación.
     */
    public int getIdObservacion() {
        return idObservacion;
    }

    /**
     * Establece el identificador de la observación.
     * @param idObservacion El ID a asignar.
     */
    public void setIdObservacion(int idObservacion) {
        this.idObservacion = idObservacion;
    }

    /**
     * Obtiene la descripción de la observación.
     * @return La descripción textual.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la observación.
     * @param descripcion La descripción a asignar.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la fecha de la observación.
     * @return La fecha en formato de texto.
     */
    public String getFechaObservacion() {
        return fechaObservacion;
    }

    /**
     * Establece la fecha de la observación.
     * @param fechaObservacion La fecha a asignar.
     */
    public void setFechaObservacion(String fechaObservacion) {
        this.fechaObservacion = fechaObservacion;
    }
}
