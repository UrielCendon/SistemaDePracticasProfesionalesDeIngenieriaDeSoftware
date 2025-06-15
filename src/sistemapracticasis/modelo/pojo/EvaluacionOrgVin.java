package sistemapracticasis.modelo.pojo;

/**
 *
 * @author uriel
 */
public class EvaluacionOrgVin {
    private int idEvaluacion;
    private String fechaRealizado;
    private double puntajeTotal;
    private int ambienteLaboral;
    private int supervision;
    private int cumplimientoActividades;
    private int cargaLaboral;
    private String recomendaciones;
    private int idExpediente;

    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public String getFechaRealizado() {
        return fechaRealizado;
    }

    public void setFechaRealizado(String fechaRealizado) {
        this.fechaRealizado = fechaRealizado;
    }

    public double getPuntajeTotal() {
        return puntajeTotal;
    }

    public void setPuntajeTotal(double puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }

    public int getAmbienteLaboral() {
        return ambienteLaboral;
    }

    public void setAmbienteLaboral(int ambienteLaboral) {
        this.ambienteLaboral = ambienteLaboral;
    }

    public int getSupervision() {
        return supervision;
    }

    public void setSupervision(int supervision) {
        this.supervision = supervision;
    }

    public int getCumplimientoActividades() {
        return cumplimientoActividades;
    }

    public void setCumplimientoActividades(int cumplimientoActividades) {
        this.cumplimientoActividades = cumplimientoActividades;
    }

    public int getCargaLaboral() {
        return cargaLaboral;
    }

    public void setCargaLaboral(int cargaLaboral) {
        this.cargaLaboral = cargaLaboral;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public int getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }
}
