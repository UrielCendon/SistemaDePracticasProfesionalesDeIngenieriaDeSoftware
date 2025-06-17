/**
 * Autor: Miguel Escobar
 * Fecha de creación: 12/06/2025  
 * Descripción: Clase POJO que representa a un evaluador dentro del sistema
 * de prácticas profesionales. Incluye información personal, su relación con
 * una evaluación estudiantil y su usuario correspondiente en el sistema.
 */
package sistemapracticasis.modelo.pojo;

/**
 * Representa a un evaluador que califica a los estudiantes
 * en el sistema de prácticas profesionales.
 */
public class Evaluador {

    /**
     * Identificador único del evaluador.
     */
    private int idEvaluador;

    /**
     * Número de personal del evaluador.
     */
    private int numPersonal;

    /**
     * Nombre del evaluador.
     */
    private String nombre;

    /**
     * Correo electrónico del evaluador.
     */
    private String correo;

    /**
     * Apellido paterno del evaluador.
     */
    private String apellidoPaterno;

    /**
     * Apellido materno del evaluador.
     */
    private String apellidoMaterno;

    /**
     * Identificador de la evaluación asignada al estudiante.
     */
    private int idEvaluacionEstudiante;

    /**
     * Identificador del usuario vinculado al evaluador.
     */
    private int idUsuario;

    /**
     * Obtiene el ID del evaluador.
     * @return ID del evaluador.
     */
    public int getIdEvaluador() {
        return idEvaluador;
    }

    /**
     * Establece el ID del evaluador.
     * @param idEvaluador ID a asignar.
     */
    public void setIdEvaluador(int idEvaluador) {
        this.idEvaluador = idEvaluador;
    }

    /**
     * Obtiene el número de personal del evaluador.
     * @return Número de personal.
     */
    public int getNumPersonal() {
        return numPersonal;
    }

    /**
     * Establece el número de personal del evaluador.
     * @param numPersonal Número a asignar.
     */
    public void setNumPersonal(int numPersonal) {
        this.numPersonal = numPersonal;
    }

    /**
     * Obtiene el nombre del evaluador.
     * @return Nombre del evaluador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del evaluador.
     * @param nombre Nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el correo del evaluador.
     * @return Correo electrónico.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo del evaluador.
     * @param correo Correo a asignar.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el apellido paterno del evaluador.
     * @return Apellido paterno.
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Establece el apellido paterno del evaluador.
     * @param apellidoPaterno Apellido a asignar.
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * Obtiene el apellido materno del evaluador.
     * @return Apellido materno.
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Establece el apellido materno del evaluador.
     * @param apellidoMaterno Apellido a asignar.
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * Obtiene el ID de la evaluación asignada al estudiante.
     * @return ID de evaluación del estudiante.
     */
    public int getIdEvaluacionEstudiante() {
        return idEvaluacionEstudiante;
    }

    /**
     * Establece el ID de la evaluación asignada al estudiante.
     * @param idEvaluacionEstudiante ID a asignar.
     */
    public void setIdEvaluacionEstudiante(int idEvaluacionEstudiante) {
        this.idEvaluacionEstudiante = idEvaluacionEstudiante;
    }

    /**
     * Obtiene el ID del usuario relacionado al evaluador.
     * @return ID del usuario.
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el ID del usuario relacionado al evaluador.
     * @param idUsuario ID a asignar.
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Retorna una representación en texto del evaluador.
     * @return Nombre completo del evaluador.
     */
    @Override
    public String toString() {
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }
}
