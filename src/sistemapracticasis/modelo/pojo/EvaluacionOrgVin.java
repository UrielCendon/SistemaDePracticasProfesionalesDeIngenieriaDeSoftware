/**
 * Autor: Uriel Cendón  
 * Fecha de creación: 14/06/2025  
 * Descripción: Clase POJO que representa la evaluación realizada por la 
 * organización vinculada sobre el desempeño del estudiante en su estancia o 
 * práctica.
 */
package sistemapracticasis.modelo.pojo;

/**
 * Representa una evaluación emitida por la organización vinculada
 * hacia un estudiante, incluyendo distintos criterios evaluativos y
 * observaciones generales.
 */
public class EvaluacionOrgVin {

    /**
     * Identificador único de la evaluación.
     */
    private int idEvaluacion;

    /**
     * Fecha en que se realizó la evaluación.
     */
    private String fechaRealizado;

    /**
     * Puntaje total obtenido por el estudiante.
     */
    private double puntajeTotal;

    /**
     * Evaluación del ambiente laboral percibido por el estudiante.
     */
    private int ambienteLaboral;

    /**
     * Nivel de supervisión recibido por el estudiante.
     */
    private int supervision;

    /**
     * Nivel de cumplimiento de actividades asignadas.
     */
    private int cumplimientoActividades;

    /**
     * Nivel de carga laboral considerada por el estudiante.
     */
    private int cargaLaboral;

    /**
     * Recomendaciones u observaciones adicionales por parte de la organización.
     */
    private String recomendaciones;

    /**
     * Identificador del expediente del estudiante evaluado.
     */
    private int idExpediente;

    /**
     * Obtiene el ID de la evaluación.
     * @return ID de la evaluación.
     */
    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    /**
     * Establece el ID de la evaluación.
     * @param idEvaluacion ID a asignar.
     */
    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    /**
     * Obtiene la fecha en que se realizó la evaluación.
     * @return Fecha en formato String.
     */
    public String getFechaRealizado() {
        return fechaRealizado;
    }

    /**
     * Establece la fecha de realización de la evaluación.
     * @param fechaRealizado Fecha a asignar.
     */
    public void setFechaRealizado(String fechaRealizado) {
        this.fechaRealizado = fechaRealizado;
    }

    /**
     * Obtiene el puntaje total asignado.
     * @return Puntaje total.
     */
    public double getPuntajeTotal() {
        return puntajeTotal;
    }

    /**
     * Establece el puntaje total.
     * @param puntajeTotal Puntaje a asignar.
     */
    public void setPuntajeTotal(double puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }

    /**
     * Obtiene la calificación del ambiente laboral.
     * @return Puntaje del ambiente laboral.
     */
    public int getAmbienteLaboral() {
        return ambienteLaboral;
    }

    /**
     * Establece la calificación del ambiente laboral.
     * @param ambienteLaboral Puntaje a asignar.
     */
    public void setAmbienteLaboral(int ambienteLaboral) {
        this.ambienteLaboral = ambienteLaboral;
    }

    /**
     * Obtiene la evaluación de la supervisión.
     * @return Puntaje de supervisión.
     */
    public int getSupervision() {
        return supervision;
    }

    /**
     * Establece el puntaje de supervisión.
     * @param supervision Puntaje a asignar.
     */
    public void setSupervision(int supervision) {
        this.supervision = supervision;
    }

    /**
     * Obtiene la calificación del cumplimiento de actividades.
     * @return Puntaje del cumplimiento.
     */
    public int getCumplimientoActividades() {
        return cumplimientoActividades;
    }

    /**
     * Establece la calificación del cumplimiento de actividades.
     * @param cumplimientoActividades Puntaje a asignar.
     */
    public void setCumplimientoActividades(int cumplimientoActividades) {
        this.cumplimientoActividades = cumplimientoActividades;
    }

    /**
     * Obtiene la evaluación de la carga laboral asignada.
     * @return Puntaje de carga laboral.
     */
    public int getCargaLaboral() {
        return cargaLaboral;
    }

    /**
     * Establece el puntaje de carga laboral.
     * @param cargaLaboral Puntaje a asignar.
     */
    public void setCargaLaboral(int cargaLaboral) {
        this.cargaLaboral = cargaLaboral;
    }

    /**
     * Obtiene las recomendaciones de la organización vinculada.
     * @return Texto de recomendaciones.
     */
    public String getRecomendaciones() {
        return recomendaciones;
    }

    /**
     * Establece las recomendaciones de la organización vinculada.
     * @param recomendaciones Texto a asignar.
     */
    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    /**
     * Obtiene el ID del expediente del estudiante evaluado.
     * @return ID del expediente.
     */
    public int getIdExpediente() {
        return idExpediente;
    }

    /**
     * Establece el ID del expediente del estudiante evaluado.
     * @param idExpediente ID a asignar.
     */
    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }
}
