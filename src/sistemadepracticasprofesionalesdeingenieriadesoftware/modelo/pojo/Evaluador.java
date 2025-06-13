package sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.pojo;

public class Evaluador {
    private int idEvaluador;
    private int numPersonal;
    private String nombre;
    private String correo;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private int idEvaluacionEstudiante;
    private int idUsuario;

    public int getIdEvaluador() {
        return idEvaluador;
    }

    public void setIdEvaluador(int idEvaluador) {
        this.idEvaluador = idEvaluador;
    }

    public int getNumPersonal() {
        return numPersonal;
    }

    public void setNumPersonal(int numPersonal) {
        this.numPersonal = numPersonal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public int getIdEvaluacionEstudiante() {
        return idEvaluacionEstudiante;
    }

    public void setIdEvaluacionEstudiante(int idEvaluacionEstudiante) {
        this.idEvaluacionEstudiante = idEvaluacionEstudiante;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }
    
    
}
