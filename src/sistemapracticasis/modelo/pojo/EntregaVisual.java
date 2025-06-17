package sistemapracticasis.modelo.pojo;

/** 
 * Autor: Uriel Cendón
 * Fecha de creación: 14/06/2025
 * Descripción: Clase que representa una entrega visual de un estudiante dentro
 * del sistema. Contiene los detalles de la entrega, como el nombre, la fecha, 
 * el tipo, si está validada y la calificación asignada.
 */
public class EntregaVisual {
    
    /**
     * El ID de la entrega visual.
     */
    private int id;
    
    /**
     * El nombre de la entrega visual.
     */
    private String nombreEntrega;
    
    /**
     * La fecha en que la entrega fue realizada.
     */
    private String fechaEntregado;
    
    /**
     * El tipo de entrega visual (puede ser un archivo, un enlace, etc.).
     */
    private String tipo;
    
    /**
     * Indica si la entrega ha sido validada o no.
     */
    private boolean validado;
    
    /**
     * La calificación asignada a la entrega visual.
     */
    private float calificacion;

    /**
     * Constructor que inicializa una entrega visual sin los campos de 
     * validación y calificación.
     * 
     * @param id El ID de la entrega visual.
     * @param nombreEntrega El nombre de la entrega visual.
     * @param fechaEntregado La fecha en que se entregó la entrega visual.
     * @param tipo El tipo de la entrega visual.
     */
    public EntregaVisual(int id, String nombreEntrega, String fechaEntregado, 
            String tipo) {
        this.id = id;
        this.nombreEntrega = nombreEntrega;
        this.fechaEntregado = fechaEntregado;
        this.tipo = tipo;
    }

    /**
     * Constructor que inicializa una entrega visual con todos los detalles.
     * 
     * @param id El ID de la entrega visual.
     * @param nombreEntrega El nombre de la entrega visual.
     * @param fechaEntregado La fecha en que se entregó la entrega visual.
     * @param tipo El tipo de la entrega visual.
     * @param validado Indica si la entrega ha sido validada o no.
     * @param calificacion La calificación asignada a la entrega visual.
     */
    public EntregaVisual(int id, String nombreEntrega, String fechaEntregado, 
                        String tipo, boolean validado, float calificacion) {
        this.id = id;
        this.nombreEntrega = nombreEntrega;
        this.fechaEntregado = fechaEntregado;
        this.tipo = tipo;
        this.validado = validado;
        this.calificacion = calificacion;
    }

    /**
     * Obtiene el nombre de la entrega visual.
     * 
     * @return El nombre de la entrega visual.
     */
    public String getNombreEntrega() {
        return nombreEntrega;
    }

    /**
     * Establece el nombre de la entrega visual.
     * 
     * @param nombreEntrega El nombre de la entrega visual.
     */
    public void setNombreEntrega(String nombreEntrega) {
        this.nombreEntrega = nombreEntrega;
    }

    /**
     * Obtiene la fecha en que se entregó la entrega visual.
     * 
     * @return La fecha de entrega.
     */
    public String getFechaEntregado() {
        return fechaEntregado;
    }

    /**
     * Establece la fecha en que se entregó la entrega visual.
     * 
     * @param fechaEntregado La fecha de entrega.
     */
    public void setFechaEntregado(String fechaEntregado) {
        this.fechaEntregado = fechaEntregado;
    }

    /**
     * Obtiene el ID de la entrega visual.
     * 
     * @return El ID de la entrega visual.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID de la entrega visual.
     * 
     * @param id El ID de la entrega visual.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el tipo de la entrega visual.
     * 
     * @return El tipo de la entrega visual.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de la entrega visual.
     * 
     * @param tipo El tipo de la entrega visual.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene si la entrega visual ha sido validada.
     * 
     * @return true si la entrega ha sido validada, false si no.
     */
    public boolean isValidado() {
        return validado;
    }

    /**
     * Establece si la entrega visual ha sido validada.
     * 
     * @param validado true si la entrega ha sido validada, false si no.
     */
    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    /**
     * Obtiene la calificación asignada a la entrega visual.
     * 
     * @return La calificación de la entrega visual.
     */
    public float getCalificacion() {
        return calificacion;
    }

    /**
     * Establece la calificación asignada a la entrega visual.
     * 
     * @param calificacion La calificación de la entrega visual.
     */
    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }
}
