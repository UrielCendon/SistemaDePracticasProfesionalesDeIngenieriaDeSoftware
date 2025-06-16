package sistemapracticasis.modelo.pojo;

/**
 *
 * @author uriel
 */
public class EntregaVisual {
    private int id;
    private String nombreEntrega;
    private String fechaEntregado;
    private String tipo;
    private boolean validado;
    private float calificacion;
    
    public EntregaVisual(int id, String nombreEntrega, String fechaEntregado,
            String tipo) {
        this.id = id;
        this.nombreEntrega = nombreEntrega;
        this.fechaEntregado = fechaEntregado;
        this.tipo = tipo;
    }
    
    public EntregaVisual(int id, String nombreEntrega, String fechaEntregado, 
                        String tipo, boolean validado, float calificacion) {
        this.id = id;
        this.nombreEntrega = nombreEntrega;
        this.fechaEntregado = fechaEntregado;
        this.tipo = tipo;
        this.validado = validado;
        this.calificacion = calificacion;
    }

    public String getNombreEntrega() {
        return nombreEntrega;
    }

    public void setNombreEntrega(String nombreEntrega) {
        this.nombreEntrega = nombreEntrega;
    }

    public String getFechaEntregado() {
        return fechaEntregado;
    }

    public void setFechaEntregado(String fechaEntregado) {
        this.fechaEntregado = fechaEntregado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }
    
    
}
