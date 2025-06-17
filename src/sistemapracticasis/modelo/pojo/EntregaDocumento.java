package sistemapracticasis.modelo.pojo;

/** 
 * Autor: Uriel Cendón
 * Fecha de creación: 14/06/2025
 * Descripción: Clase que representa una entrega de documento asociada a un 
 * expediente de un estudiante, con detalles sobre las fechas, tipo, validación
 * y calificación de la entrega.
 */
public class EntregaDocumento {
    
    /**
     * Identificador único de la entrega de documento.
     */
    private int idEntregaDocumento;
    
    /**
     * Nombre de la entrega de documento.
     */
    private String nombre;
    
    /**
     * Fecha de inicio de la entrega de documento.
     */
    private String fechaInicio;
    
    /**
     * Fecha de fin de la entrega de documento.
     */
    private String fechaFin;
    
    /**
     * Tipo de entrega (inicial, intermedio, final).
     */
    private EntregaDocumentoTipo tipoEntrega;
    
    /**
     * Estado de validación de la entrega (1 = validado, 0 = no validado).
     */
    private int validado;
    
    /**
     * Calificación asociada a la entrega de documento.
     */
    private double calificacion;
    
    /**
     * Identificador del expediente relacionado con esta entrega de documento.
     */
    private int idExpediente;
    
    /**
     * Identificador de la observación asociada a la entrega de documento.
     */
    private int idObservacion;
    
    /**
     * Descripción de la entrega de documento.
     */
    private String descripcion;

    /**
     * Constructor para crear una nueva entrega de documento con los detalles 
     * básicos.
     * 
     * @param idEntregaDocumento El identificador único de la entrega de 
     *                           documento.
     * @param nombre El nombre de la entrega de documento.
     * @param fechaInicio La fecha de inicio de la entrega de documento.
     * @param fechaFin La fecha de fin de la entrega de documento.
     */
    public EntregaDocumento(int idEntregaDocumento, String nombre,
            String fechaInicio, String fechaFin) {
        this.idEntregaDocumento = idEntregaDocumento;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = nombre;
    }

    /**
     * Obtiene el identificador de la entrega de documento.
     * 
     * @return El identificador de la entrega de documento.
     */
    public int getIdEntregaDocumento() {
        return idEntregaDocumento;
    }

    /**
     * Establece el identificador de la entrega de documento.
     * 
     * @param idEntregaDocumento El identificador de la entrega de documento.
     */
    public void setIdEntregaDocumento(int idEntregaDocumento) {
        this.idEntregaDocumento = idEntregaDocumento;
    }

    /**
     * Obtiene el nombre de la entrega de documento.
     * 
     * @return El nombre de la entrega de documento.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la entrega de documento.
     * 
     * @param nombre El nombre de la entrega de documento.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la fecha de inicio de la entrega de documento.
     * 
     * @return La fecha de inicio de la entrega de documento.
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la fecha de inicio de la entrega de documento.
     * 
     * @param fechaInicio La fecha de inicio de la entrega de documento.
     */
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la fecha de fin de la entrega de documento.
     * 
     * @return La fecha de fin de la entrega de documento.
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece la fecha de fin de la entrega de documento.
     * 
     * @param fechaFin La fecha de fin de la entrega de documento.
     */
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Obtiene el tipo de entrega (inicial, intermedio, final).
     * 
     * @return El tipo de entrega de documento.
     */
    public EntregaDocumentoTipo getTipoEntrega() {
        return tipoEntrega;
    }

    /**
     * Establece el tipo de entrega (inicial, intermedio, final).
     * 
     * @param tipoEntrega El tipo de entrega de documento.
     */
    public void setTipoEntrega(EntregaDocumentoTipo tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    /**
     * Obtiene el estado de validación de la entrega.
     * 
     * @return El estado de validación de la entrega (1 = validado, 
     * 0 = no validado).
     */
    public int getValidado() {
        return validado;
    }

    /**
     * Establece el estado de validación de la entrega.
     * 
     * @param validado El estado de validación de la entrega (1 = validado, 
     * 0 = no validado).
     */
    public void setValidado(int validado) {
        this.validado = validado;
    }

    /**
     * Obtiene la calificación de la entrega de documento.
     * 
     * @return La calificación de la entrega de documento.
     */
    public double getCalificacion() {
        return calificacion;
    }

    /**
     * Establece la calificación de la entrega de documento.
     * 
     * @param calificacion La calificación de la entrega de documento.
     */
    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    /**
     * Obtiene el identificador del expediente asociado a la entrega de 
     * documento.
     * 
     * @return El identificador del expediente asociado.
     */
    public int getIdExpediente() {
        return idExpediente;
    }

    /**
     * Establece el identificador del expediente asociado a la entrega de 
     * documento.
     * 
     * @param idExpediente El identificador del expediente asociado.
     */
    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }

    /**
     * Obtiene el identificador de la observación asociada a la entrega de 
     * documento.
     * 
     * @return El identificador de la observación asociada.
     */
    public int getIdObservacion() {
        return idObservacion;
    }

    /**
     * Establece el identificador de la observación asociada a la entrega de 
     * documento.
     * 
     * @param idObservacion El identificador de la observación asociada.
     */
    public void setIdObservacion(int idObservacion) {
        this.idObservacion = idObservacion;
    }

    /**
     * Obtiene la descripción de la entrega de documento.
     * 
     * @return La descripción de la entrega de documento.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la entrega de documento.
     * 
     * @param descripcion La descripción de la entrega de documento.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}