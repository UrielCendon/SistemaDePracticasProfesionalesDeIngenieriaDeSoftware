/**
 * Autor: Uriel Cendón  
 * Fecha de creación: 13/06/2025  
 * Descripción: Clase POJO que representa un expediente de prácticas 
 * profesionales.
 * Contiene información como las horas acumuladas, calificaciones individuales,
 * calificación total y estado del expediente.
 */
package sistemapracticasis.modelo.pojo;

/**
 * Representa el expediente de un estudiante durante su participación
 * en prácticas profesionales.
 */
public class Expediente {
    /**
     * Identificador único del expediente.
     */
    private int idExpediente;

    /**
     * Número total de horas acumuladas por el estudiante.
     */
    private int horasAcumuladas;

    /**
     * Calificación del documento entregado.
     */
    private double califDocumento;

    /**
     * Calificación otorgada por la evaluación académica.
     */
    private double califEvaluacion;

    /**
     * Calificación proporcionada por la organización vinculada.
     */
    private double califEvalOrgVin;

    /**
     * Calificación total del expediente.
     */
    private double califTotal;

    /**
     * Estado actual del expediente (activo, concluido, pendiente, etc.).
     */
    private EstadoExpediente estado;

    /**
     * Constructor que inicializa el expediente con ID, horas acumuladas y estado.
     * @param idExpediente Identificador único del expediente.
     * @param horasAcumuladas Número de horas registradas.
     * @param estado Estado actual del expediente.
     */
    public Expediente(int idExpediente, int horasAcumuladas, EstadoExpediente estado) {
        this.idExpediente = idExpediente;
        this.horasAcumuladas = horasAcumuladas;
        this.estado = estado;
    }

    /**
     * Obtiene el identificador del expediente.
     * @return ID del expediente.
     */
    public int getIdExpediente() {
        return idExpediente;
    }

    /**
     * Establece el identificador del expediente.
     * @param idExpediente ID a asignar.
     */
    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }

    /**
     * Obtiene las horas acumuladas del expediente.
     * @return Horas acumuladas.
     */
    public int getHorasAcumuladas() {
        return horasAcumuladas;
    }

    /**
     * Establece las horas acumuladas del expediente.
     * @param horasAcumuladas Total de horas a asignar.
     */
    public void setHorasAcumuladas(int horasAcumuladas) {
        this.horasAcumuladas = horasAcumuladas;
    }

    /**
     * Obtiene la calificación del documento.
     * @return Calificación del documento.
     */
    public double getCalifDocumento() {
        return califDocumento;
    }

    /**
     * Establece la calificación del documento.
     * @param califDocumento Calificación a asignar.
     */
    public void setCalifDocumento(double califDocumento) {
        this.califDocumento = califDocumento;
    }

    /**
     * Obtiene la calificación académica.
     * @return Calificación académica.
     */
    public double getCalifEvaluacion() {
        return califEvaluacion;
    }

    /**
     * Establece la calificación académica.
     * @param califEvaluacion Calificación a asignar.
     */
    public void setCalifEvaluacion(double califEvaluacion) {
        this.califEvaluacion = califEvaluacion;
    }

    /**
     * Obtiene la calificación de la organización vinculada.
     * @return Calificación de la organización.
     */
    public double getCalifEvalOrgVin() {
        return califEvalOrgVin;
    }

    /**
     * Establece la calificación de la organización vinculada.
     * @param califEvalOrgVin Calificación a asignar.
     */
    public void setCalifEvalOrgVin(double califEvalOrgVin) {
        this.califEvalOrgVin = califEvalOrgVin;
    }

    /**
     * Obtiene la calificación total del expediente.
     * @return Calificación total.
     */
    public double getCalifTotal() {
        return califTotal;
    }

    /**
     * Establece la calificación total del expediente.
     * @param califTotal Calificación total a asignar.
     */
    public void setCalifTotal(double califTotal) {
        this.califTotal = califTotal;
    }

    /**
     * Obtiene el estado actual del expediente.
     * @return Estado del expediente.
     */
    public EstadoExpediente getEstado() {
        return estado;
    }

    /**
     * Establece el estado del expediente.
     * @param estado Estado a asignar.
     */
    public void setEstado(EstadoExpediente estado) {
        this.estado = estado;
    }
}
