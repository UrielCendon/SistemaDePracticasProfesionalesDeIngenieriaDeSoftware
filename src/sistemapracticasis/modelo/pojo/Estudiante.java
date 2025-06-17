package sistemapracticasis.modelo.pojo;

/**
 * Autor: Miguel Escobar
 * Fecha de creación: 12/06/2025  
 * Descripción: Clase modelo que representa a un estudiante en el sistema,
 * incluyendo información personal y académica como matrícula, nombre, 
 * correo, teléfono, y los IDs de proyecto, periodo y experiencia educativa.
 * Esta clase también incluye un método `toString` para mostrar el nombre 
 * completo del estudiante.
 */
public class Estudiante {

    /**
     * ID único del estudiante.
     */
    private int idEstudiante;

    /**
     * Matrícula del estudiante.
     */
    private String matricula;

    /**
     * Nombre del estudiante.
     */
    private String nombre;

    /**
     * Correo electrónico del estudiante.
     */
    private String correo;

    /**
     * Número de teléfono del estudiante.
     */
    private String telefono;

    /**
     * Apellido paterno del estudiante.
     */
    private String apellidoPaterno;

    /**
     * Apellido materno del estudiante.
     */
    private String apellidoMaterno;

    /**
     * Foto del estudiante en formato de cadena.
     */
    private String foto;

    /**
     * ID del proyecto asignado al estudiante.
     */
    private int idProyecto;

    /**
     * ID del periodo en el que el estudiante está inscrito.
     */
    private int idPeriodo;

    /**
     * ID de la experiencia educativa en la que el estudiante está participando.
     */
    private int idExperienciaEducativa;

    /**
     * ID de usuario asociado al estudiante.
     */
    private int idUsuario;

    /**
     * Nombre del proyecto asignado al estudiante.
     */
    private String nombreProyecto;

    /**
     * Obtiene el ID del estudiante.
     * 
     * @return ID del estudiante.
     */
    public int getIdEstudiante() {
        return idEstudiante;
    }

    /**
     * Establece el ID del estudiante.
     * 
     * @param idEstudiante ID del estudiante.
     */
    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    /**
     * Obtiene la matrícula del estudiante.
     * 
     * @return Matrícula del estudiante.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Establece la matrícula del estudiante.
     * 
     * @param matricula Matrícula del estudiante.
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * Obtiene el nombre del estudiante.
     * 
     * @return Nombre del estudiante.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del estudiante.
     * 
     * @param nombre Nombre del estudiante.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el correo electrónico del estudiante.
     * 
     * @return Correo electrónico del estudiante.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del estudiante.
     * 
     * @param correo Correo electrónico del estudiante.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el número de teléfono del estudiante.
     * 
     * @return Número de teléfono del estudiante.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del estudiante.
     * 
     * @param telefono Número de teléfono del estudiante.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el apellido paterno del estudiante.
     * 
     * @return Apellido paterno del estudiante.
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Establece el apellido paterno del estudiante.
     * 
     * @param apellidoPaterno Apellido paterno del estudiante.
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * Obtiene el apellido materno del estudiante.
     * 
     * @return Apellido materno del estudiante.
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Establece el apellido materno del estudiante.
     * 
     * @param apellidoMaterno Apellido materno del estudiante.
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * Obtiene la foto del estudiante.
     * 
     * @return Foto del estudiante.
     */
    public String getFoto() {
        return foto;
    }

    /**
     * Establece la foto del estudiante.
     * 
     * @param foto Foto del estudiante.
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     * Obtiene el ID del proyecto asignado al estudiante.
     * 
     * @return ID del proyecto.
     */
    public int getIdProyecto() {
        return idProyecto;
    }

    /**
     * Establece el ID del proyecto asignado al estudiante.
     * 
     * @param idProyecto ID del proyecto.
     */
    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    /**
     * Obtiene el ID del periodo en el que el estudiante está inscrito.
     * 
     * @return ID del periodo.
     */
    public int getIdPeriodo() {
        return idPeriodo;
    }

    /**
     * Establece el ID del periodo en el que el estudiante está inscrito.
     * 
     * @param idPeriodo ID del periodo.
     */
    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    /**
     * Obtiene el ID de la experiencia educativa en la que el estudiante está 
     * participando.
     * 
     * @return ID de la experiencia educativa.
     */
    public int getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    /**
     * Establece el ID de la experiencia educativa en la que el estudiante está 
     * participando.
     * 
     * @param idExperienciaEducativa ID de la experiencia educativa.
     */
    public void setIdExperienciaEducativa(int idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    /**
     * Obtiene el ID de usuario asociado al estudiante.
     * 
     * @return ID de usuario.
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el ID de usuario asociado al estudiante.
     * 
     * @param idUsuario ID de usuario.
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el nombre del proyecto asignado al estudiante.
     * 
     * @return Nombre del proyecto.
     */
    public String getNombreProyecto() {
        return nombreProyecto;
    }

    /**
     * Establece el nombre del proyecto asignado al estudiante.
     * 
     * @param nombreProyecto Nombre del proyecto.
     */
    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    /**
     * Método sobrecargado `toString` para mostrar el nombre completo del 
     * estudiante.
     * 
     * @return Nombre completo del estudiante.
     */
    @Override
    public String toString() {
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }
}
