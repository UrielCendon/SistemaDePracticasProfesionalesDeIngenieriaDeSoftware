package sistemapracticasis.modelo.pojo;

/** 
 * Autor: Raziel Filobello  
 * Fecha de creación: 14/06/2025  
 * Descripción: Clase que representa la evaluación realizada por el profesor 
 * a un estudiante con base en distintos criterios como uso de técnicas, 
 * requisitos, seguridad, contenido y ortografía. Se registra el puntaje total 
 * y se asocia con un expediente y una observación específica.  
 */
public class EvaluacionEstudiante {

    /**
     * Puntaje asignado por el uso de técnicas adecuadas en el desarrollo del 
     * proyecto.
     */
    private int usoTecnicas;

    /**
     * Puntaje asignado por el cumplimiento de los requisitos del proyecto.
     */
    private int requisitos;

    /**
     * Puntaje asignado por el manejo de aspectos de seguridad en el sistema.
     */
    private int seguridad;

    /**
     * Puntaje asignado por la calidad del contenido presentado en la entrega.
     */
    private int contenido;

    /**
     * Puntaje asignado por el uso correcto de ortografía en la documentación.
     */
    private int ortografia;

    /**
     * Puntaje total acumulado de la evaluación del estudiante.
     */
    private double puntajeTotal;

    /**
     * Identificador del expediente asociado al estudiante evaluado.
     */
    private int idExpediente;

    /**
     * Identificador de la observación asociada a esta evaluación.
     */
    private String nota;

    /**
     * Obtiene el puntaje de uso de técnicas.
     * @return Puntaje de uso de técnicas.
     */
    public int getUsoTecnicas() {
        return usoTecnicas;
    }

    /**
     * Establece el puntaje de uso de técnicas.
     * @param usoTecnicas Puntaje a establecer.
     */
    public void setUsoTecnicas(int usoTecnicas) {
        this.usoTecnicas = usoTecnicas;
    }

    /**
     * Obtiene el puntaje de requisitos.
     * @return Puntaje de requisitos.
     */
    public int getRequisitos() {
        return requisitos;
    }

    /**
     * Establece el puntaje de requisitos.
     * @param requisitos Puntaje a establecer.
     */
    public void setRequisitos(int requisitos) {
        this.requisitos = requisitos;
    }

    /**
     * Obtiene el puntaje de seguridad.
     * @return Puntaje de seguridad.
     */
    public int getSeguridad() {
        return seguridad;
    }

    /**
     * Establece el puntaje de seguridad.
     * @param seguridad Puntaje a establecer.
     */
    public void setSeguridad(int seguridad) {
        this.seguridad = seguridad;
    }

    /**
     * Obtiene el puntaje de contenido.
     * @return Puntaje de contenido.
     */
    public int getContenido() {
        return contenido;
    }

    /**
     * Establece el puntaje de contenido.
     * @param contenido Puntaje a establecer.
     */
    public void setContenido(int contenido) {
        this.contenido = contenido;
    }

    /**
     * Obtiene el puntaje de ortografía.
     * @return Puntaje de ortografía.
     */
    public int getOrtografia() {
        return ortografia;
    }

    /**
     * Establece el puntaje de ortografía.
     * @param ortografia Puntaje a establecer.
     */
    public void setOrtografia(int ortografia) {
        this.ortografia = ortografia;
    }

    /**
     * Obtiene el puntaje total de la evaluación.
     * @return Puntaje total.
     */
    public double getPuntajeTotal() {
        return puntajeTotal;
    }

    /**
     * Establece el puntaje total de la evaluación.
     * @param puntajeTotal Puntaje total a establecer.
     */
    public void setPuntajeTotal(double puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }

    /**
     * Obtiene el identificador del expediente evaluado.
     * @return ID del expediente.
     */
    public int getIdExpediente() {
        return idExpediente;
    }

    /**
     * Establece el identificador del expediente evaluado.
     * @param idExpediente ID del expediente a establecer.
     */
    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }

    /**
     * Obtiene el identificador de la observación asociada.
     * @return ID de la observación.
     */
    public String getNota() {
        return nota;
    }

    /**
     * Establece el identificador de la observación asociada.
     * @param idObservacion ID de la observación a establecer.
     */
    public void setNota(String nota) {
        this.nota = nota;
    }
}