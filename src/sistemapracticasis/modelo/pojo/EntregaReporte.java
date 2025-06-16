package sistemapracticasis.modelo.pojo;

/**
 *
 * @author uriel
 */
public class EntregaReporte {
    private int idEntregaReporte;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private int validado;
    private double calificacion;
    private int idExpediente;
    private int idObservacion;
    private String observacion; // campo nuevo para la descripción

    public EntregaReporte(int idEntregaReporte, String nombre, String fechaInicio, String fechaFin, int validado, double calificacion, int idExpediente, int idObservacion) {
        this.idEntregaReporte = idEntregaReporte;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.validado = validado;
        this.calificacion = calificacion;
        this.idExpediente = idExpediente;
        this.idObservacion = idObservacion;
    }

    public int getIdEntregaReporte() {
        return idEntregaReporte;
    }

    public void setIdEntregaReporte(int idEntregaReporte) {
        this.idEntregaReporte = idEntregaReporte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getValidado() {
        return validado;
    }

    public void setValidado(int validado) {
        this.validado = validado;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public int getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }

    public int getIdObservacion() {
        return idObservacion;
    }

    public void setIdObservacion(int idObservacion) {
        this.idObservacion = idObservacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}