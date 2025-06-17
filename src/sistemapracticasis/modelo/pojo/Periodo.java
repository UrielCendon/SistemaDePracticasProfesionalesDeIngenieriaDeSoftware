/**
 * Autor: Uriel Cendón
 * Fecha de creación: 13/06/2025
 * Descripción: Clase POJO que representa un periodo académico asociado a un 
 * estudiante, 
 * con información como nombre del periodo, fechas de inicio y fin, 
 * identificadores de expediente y estudiante, así como detalles de la 
 * experiencia educativa.
 */
package sistemapracticasis.modelo.pojo;

public class Periodo {

    /**
     * Nombre del periodo académico.
     */
    private String nombrePeriodo;

    /**
     * Fecha de inicio del periodo académico.
     */
    private String fechaInicio;

    /**
     * Fecha de fin del periodo académico.
     */
    private String fechaFin;

    /**
     * Identificador del expediente asociado al periodo.
     */
    private int idExpediente;

    /**
     * Identificador del estudiante asociado al periodo.
     */
    private int idEstudiante;

    /**
     * Nombre de la experiencia educativa asociada al periodo.
     */
    private String nombreEE;

    /**
     * NRC (Número de Registro de Clases) asociado al periodo.
     */
    private String nrc;

    /**
     * Constructor de la clase Periodo.
     * @param nombrePeriodo El nombre del periodo académico.
     * @param fechaInicio La fecha de inicio del periodo académico.
     * @param fechaFin La fecha de fin del periodo académico.
     * @param idExpediente El identificador del expediente asociado.
     * @param idEstudiante El identificador del estudiante asociado.
     */
    public Periodo(String nombrePeriodo, String fechaInicio, String fechaFin, 
                   int idExpediente, int idEstudiante) {
        this.nombrePeriodo = nombrePeriodo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idExpediente = idExpediente;
        this.idEstudiante = idEstudiante;
    }

    /**
     * Obtiene el nombre del periodo académico.
     * @return El nombre del periodo académico.
     */
    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    /**
     * Establece el nombre del periodo académico.
     * @param nombrePeriodo El nombre del periodo académico.
     */
    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }

    /**
     * Obtiene la fecha de inicio del periodo académico.
     * @return La fecha de inicio del periodo académico.
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la fecha de inicio del periodo académico.
     * @param fechaInicio La fecha de inicio del periodo académico.
     */
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la fecha de fin del periodo académico.
     * @return La fecha de fin del periodo académico.
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece la fecha de fin del periodo académico.
     * @param fechaFin La fecha de fin del periodo académico.
     */
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Obtiene el identificador del expediente asociado al periodo.
     * @return El id del expediente asociado al periodo.
     */
    public int getIdExpediente() {
        return idExpediente;
    }

    /**
     * Establece el identificador del expediente asociado al periodo.
     * @param idExpediente El id del expediente.
     */
    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }

    /**
     * Obtiene el identificador del estudiante asociado al periodo.
     * @return El id del estudiante asociado al periodo.
     */
    public int getIdEstudiante() {
        return idEstudiante;
    }

    /**
     * Establece el identificador del estudiante asociado al periodo.
     * @param idEstudiante El id del estudiante.
     */
    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    /**
     * Obtiene el nombre de la experiencia educativa asociada al periodo.
     * @return El nombre de la experiencia educativa.
     */
    public String getNombreEE() {
        return nombreEE;
    }

    /**
     * Establece el nombre de la experiencia educativa asociada al periodo.
     * @param nombreEE El nombre de la experiencia educativa.
     */
    public void setNombreEE(String nombreEE) {
        this.nombreEE = nombreEE;
    }

    /**
     * Obtiene el NRC (Número de Registro de Clases) asociado al periodo.
     * @return El NRC del periodo.
     */
    public String getNrc() {
        return nrc;
    }

    /**
     * Establece el NRC (Número de Registro de Clases) asociado al periodo.
     * @param nrc El NRC del periodo.
     */
    public void setNrc(String nrc) {
        this.nrc = nrc;
    }
}
