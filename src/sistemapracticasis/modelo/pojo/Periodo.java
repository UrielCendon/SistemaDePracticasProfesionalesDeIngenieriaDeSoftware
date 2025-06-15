package sistemapracticasis.modelo.pojo;

/**
 *
 * @author uriel
 */
public class Periodo {
    private String nombrePeriodo;
    private String fechaInicio;
    private String fechaFin;
    private int idExpediente;
    private int idEstudiante;

    public Periodo(String nombrePeriodo, String fechaInicio, String fechaFin, 
            int idExpediente, int idEstudiante) {
        this.nombrePeriodo = nombrePeriodo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idExpediente = idExpediente;
        this.idEstudiante = idEstudiante;
    }
    
    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
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

    public int getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
    
    
}
