package sistemapracticasis.modelo.pojo;

/**
 * Autor: Miguel Guevara
 * Fecha de creación: 12/06/2025
 * Descripción: Clase que representa al Coordinador dentro del sistema, 
 * incluyendo su información personal como nombre, correo, y número de personal.
 * Además, permite acceder a los datos de usuario relacionados al coordinador.
 */
public class Coordinador {
    
    /**
     * Identificador único del coordinador.
     */
    private int idCoordinador;
    
    /**
     * Número de personal del coordinador.
     */
    private int numPersonal;
    
    /**
     * Nombre del coordinador.
     */
    private String nombre;
    
    /**
     * Correo electrónico del coordinador.
     */
    private String correo;
    
    /**
     * Apellido paterno del coordinador.
     */
    private String apellidoPaterno;
    
    /**
     * Apellido materno del coordinador.
     */
    private String apellidoMaterno;
    
    /**
     * Identificador del usuario asociado al coordinador.
     */
    private int idUsuario;

    /**
     * Obtiene el identificador único del coordinador.
     * 
     * @return El identificador del coordinador.
     */
    public int getIdCoordinador() {
        return idCoordinador;
    }

    /**
     * Establece el identificador único del coordinador.
     * 
     * @param idCoordinador El identificador del coordinador.
     */
    public void setIdCoordinador(int idCoordinador) {
        this.idCoordinador = idCoordinador;
    }

    /**
     * Obtiene el número de personal del coordinador.
     * 
     * @return El número de personal del coordinador.
     */
    public int getNumPersonal() {
        return numPersonal;
    }

    /**
     * Establece el número de personal del coordinador.
     * 
     * @param numPersonal El número de personal del coordinador.
     */
    public void setNumPersonal(int numPersonal) {
        this.numPersonal = numPersonal;
    }

    /**
     * Obtiene el nombre del coordinador.
     * 
     * @return El nombre del coordinador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del coordinador.
     * 
     * @param nombre El nombre del coordinador.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el correo electrónico del coordinador.
     * 
     * @return El correo electrónico del coordinador.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del coordinador.
     * 
     * @param correo El correo electrónico del coordinador.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el apellido paterno del coordinador.
     * 
     * @return El apellido paterno del coordinador.
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Establece el apellido paterno del coordinador.
     * 
     * @param apellidoPaterno El apellido paterno del coordinador.
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * Obtiene el apellido materno del coordinador.
     * 
     * @return El apellido materno del coordinador.
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Establece el apellido materno del coordinador.
     * 
     * @param apellidoMaterno El apellido materno del coordinador.
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * Obtiene el identificador del usuario asociado al coordinador.
     * 
     * @return El identificador del usuario.
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el identificador del usuario asociado al coordinador.
     * 
     * @param idUsuario El identificador del usuario asociado al coordinador.
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Devuelve una representación en formato cadena del coordinador, 
     * compuesta por su nombre completo.
     * 
     * @return El nombre completo del coordinador.
     */
    @Override
    public String toString() {
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }
}
