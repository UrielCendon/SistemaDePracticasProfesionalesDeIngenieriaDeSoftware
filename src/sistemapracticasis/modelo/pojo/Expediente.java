package sistemapracticasis.modelo.pojo;

/**
 *
 * @author uriel
 */
public class Expediente {
    private int idExpediente;
    private int horasAcumuladas;
    private double califDocumento;
    private double califEvaluacion;
    private double califEvalOrgVin;
    private double califTotal;
    private EstadoExpediente estado;

    public Expediente(int idExpediente, int horasAcumuladas, EstadoExpediente 
            estado) {
        this.idExpediente = idExpediente;
        this.horasAcumuladas = horasAcumuladas;
        this.estado = estado;
    }
    
    public int getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }

    public int getHorasAcumuladas() {
        return horasAcumuladas;
    }

    public void setHorasAcumuladas(int horasAcumuladas) {
        this.horasAcumuladas = horasAcumuladas;
    }

    public double getCalifDocumento() {
        return califDocumento;
    }

    public void setCalifDocumento(double califDocumento) {
        this.califDocumento = califDocumento;
    }

    public double getCalifEvaluacion() {
        return califEvaluacion;
    }

    public void setCalifEvaluacion(double califEvaluacion) {
        this.califEvaluacion = califEvaluacion;
    }

    public double getCalifEvalOrgVin() {
        return califEvalOrgVin;
    }

    public void setCalifEvalOrgVin(double califEvalOrgVin) {
        this.califEvalOrgVin = califEvalOrgVin;
    }

    public double getCalifTotal() {
        return califTotal;
    }

    public void setCalifTotal(double califTotal) {
        this.califTotal = califTotal;
    }

    public EstadoExpediente getEstado() {
        return estado;
    }

    public void setEstado(EstadoExpediente estado) {
        this.estado = estado;
    }
}
