package sistemapracticasis.modelo.pojo;

/**
 *
 * @author uriel
 */
public class EntregaDocumento {
    private int idEntregaDocumento;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private EntregaDocumentoTipo tipoEntrega;
    private int validado;
    private double calificacion;
    private int idExpediente;
    private int idObservacion;

    public EntregaDocumento(int idEntregaDocumento, String nombre,
            String fechaInicio, String fechaFin) {
        this.idEntregaDocumento = idEntregaDocumento;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getIdEntregaDocumento() {
        return idEntregaDocumento;
    }

    public void setIdEntregaDocumento(int idEntregaDocumento) {
        this.idEntregaDocumento = idEntregaDocumento;
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

    public EntregaDocumentoTipo getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(EntregaDocumentoTipo tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
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
    
    
}
