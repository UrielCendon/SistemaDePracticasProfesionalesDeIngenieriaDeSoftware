package sistemapracticasis.modelo.pojo;

/** 
 * Autor: Uriel Cendón
 * Fecha de creación: 14/06/2025
 * Descripción: Clase que representa una entrega de reporte en el sistema, 
 * incluyendo detalles sobre el reporte, como fechas de inicio y fin, validación, 
 * calificación, y observaciones asociadas.
 */
public class EntregaReporte {
    
    /**
     * El ID de la entrega del reporte.
     */
    private int idEntregaReporte;
    
    /**
     * El nombre del reporte.
     */
    private String nombre;
    
    /**
     * La fecha de inicio del reporte.
     */
    private String fechaInicio;
    
    /**
     * La fecha de fin del reporte.
     */
    private String fechaFin;
    
    /**
     * Indica si el reporte ha sido validado (1 para validado, 0 para 
     * no validado).
     */
    private int validado;
    
    /**
     * La calificación asignada al reporte.
     */
    private double calificacion;
    
    /**
     * El ID del expediente asociado al reporte.
     */
    private int idExpediente;
    
    /**
     * El ID de la observación asociada al reporte.
     */
    private int idObservacion;
    
    /**
     * La observación realizada sobre el reporte.
     */
    private String observacion;

    /**
     * Constructor que inicializa una entrega de reporte con los detalles dados.
     * 
     * @param idEntregaReporte El ID de la entrega del reporte.
     * @param nombre El nombre del reporte.
     * @param fechaInicio La fecha de inicio del reporte.
     * @param fechaFin La fecha de fin del reporte.
     * @param validado Indica si el reporte ha sido validado (1 para validado,
     *                 0 para no validado).
     * @param calificacion La calificación asignada al reporte.
     * @param idExpediente El ID del expediente asociado al reporte.
     */
    public EntregaReporte(int idEntregaReporte, String nombre, String 
            fechaInicio, String fechaFin, int validado, double calificacion, 
            int idExpediente) {
        this.idEntregaReporte = idEntregaReporte;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.validado = validado;
        this.calificacion = calificacion;
        this.idExpediente = idExpediente;
    }

    /**
     * Obtiene el ID de la entrega del reporte.
     * 
     * @return El ID de la entrega del reporte.
     */
    public int getIdEntregaReporte() {
        return idEntregaReporte;
    }

    /**
     * Establece el ID de la entrega del reporte.
     * 
     * @param idEntregaReporte El ID de la entrega del reporte.
     */
    public void setIdEntregaReporte(int idEntregaReporte) {
        this.idEntregaReporte = idEntregaReporte;
    }

    /**
     * Obtiene el nombre del reporte.
     * 
     * @return El nombre del reporte.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del reporte.
     * 
     * @param nombre El nombre del reporte.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la fecha de inicio del reporte.
     * 
     * @return La fecha de inicio del reporte.
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la fecha de inicio del reporte.
     * 
     * @param fechaInicio La fecha de inicio del reporte.
     */
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la fecha de fin del reporte.
     * 
     * @return La fecha de fin del reporte.
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece la fecha de fin del reporte.
     * 
     * @param fechaFin La fecha de fin del reporte.
     */
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Obtiene si el reporte ha sido validado.
     * 
     * @return 1 si ha sido validado, 0 si no.
     */
    public int getValidado() {
        return validado;
    }

    /**
     * Establece el estado de validación del reporte.
     * 
     * @param validado 1 si el reporte ha sido validado, 0 si no.
     */
    public void setValidado(int validado) {
        this.validado = validado;
    }

    /**
     * Obtiene la calificación del reporte.
     * 
     * @return La calificación del reporte.
     */
    public double getCalificacion() {
        return calificacion;
    }

    /**
     * Establece la calificación del reporte.
     * 
     * @param calificacion La calificación del reporte.
     */
    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    /**
     * Obtiene el ID del expediente asociado al reporte.
     * 
     * @return El ID del expediente asociado al reporte.
     */
    public int getIdExpediente() {
        return idExpediente;
    }

    /**
     * Establece el ID del expediente asociado al reporte.
     * 
     * @param idExpediente El ID del expediente asociado al reporte.
     */
    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }
}
