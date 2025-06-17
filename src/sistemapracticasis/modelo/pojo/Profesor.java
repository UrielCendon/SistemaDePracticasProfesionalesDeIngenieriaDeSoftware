/**
 * Autor: Miguel Escobar
 * Fecha de creación: 12/06/2025
 * Descripción: Clase POJO que representa a un profesor dentro del sistema de 
 * prácticas, incluyendo su identificador, número de personal, nombre, correo, 
 * apellidos, experiencia educativa y el identificador de usuario relacionado.
 */
package sistemapracticasis.modelo.pojo;

public class Profesor {

    /**
     * Identificador único del profesor.
     */
    private int idProfesor;

    /**
     * Número de personal del profesor.
     */
    private int numPersonal;

    /**
     * Nombre del profesor.
     */
    private String nombre;

    /**
     * Correo electrónico del profesor.
     */
    private String correo;

    /**
     * Apellido paterno del profesor.
     */
    private String apellidoPaterno;

    /**
     * Apellido materno del profesor.
     */
    private String apellidoMaterno;

    /**
     * Identificador de la experiencia educativa asociada al profesor.
     */
    private int idExperienciaEducativa;

    /**
     * Identificador de usuario del profesor en el sistema.
     */
    private int idUsuario;

    /**
     * Obtiene el identificador del profesor.
     * @return El id del profesor.
     */
    public int getIdProfesor() {
        return idProfesor;
    }

    /**
     * Establece el identificador del profesor.
     * @param idProfesor El id del profesor.
     */
    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    /**
     * Obtiene el número de personal del profesor.
     * @return El número de personal del profesor.
     */
    public int getNumPersonal() {
        return numPersonal;
    }

    /**
     * Establece el número de personal del profesor.
     * @param numPersonal El número de personal.
     */
    public void setNumPersonal(int numPersonal) {
        this.numPersonal = numPersonal;
    }

    /**
     * Obtiene el nombre del profesor.
     * @return El nombre del profesor.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del profesor.
     * @param nombre El nombre del profesor.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el correo electrónico del profesor.
     * @return El correo del profesor.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del profesor.
     * @param correo El correo del profesor.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el apellido paterno del profesor.
     * @return El apellido paterno del profesor.
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Establece el apellido paterno del profesor.
     * @param apellidoPaterno El apellido paterno del profesor.
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * Obtiene el apellido materno del profesor.
     * @return El apellido materno del profesor.
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Establece el apellido materno del profesor.
     * @param apellidoMaterno El apellido materno del profesor.
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * Obtiene el identificador de la experiencia educativa del profesor.
     * @return El id de la experiencia educativa.
     */
    public int getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    /**
     * Establece el identificador de la experiencia educativa del profesor.
     * @param idExperienciaEducativa El id de la experiencia educativa.
     */
    public void setIdExperienciaEducativa(int idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    /**
     * Obtiene el identificador de usuario asociado al profesor.
     * @return El id de usuario del profesor.
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el identificador de usuario del profesor.
     * @param idUsuario El id de usuario del profesor.
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Representación en cadena del profesor.
     * @return El nombre completo del profesor (nombre + apellido paterno 
     * + apellido materno).
     */
    @Override
    public String toString() {
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }
}
