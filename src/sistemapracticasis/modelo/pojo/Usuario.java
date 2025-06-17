package sistemapracticasis.modelo.pojo;

/**
 * Autor: Miguel Escobar
 * Fecha de creación: 12/06/2025
 * Descripción: Clase POJO que representa un usuario del sistema de prácticas,
 * incluyendo su identificador, nombre de usuario y tipo de usuario
 * (profesor, coordinador, estudiante o evaluador).
 */
public class Usuario {

    /**
     * Identificador único del usuario.
     */
    private int idUsuario;

    /**
     * Nombre de usuario para el acceso al sistema.
     */
    private String username;

    /**
     * Tipo de usuario que puede ser profesor, coordinador, evaluador, etc.
     */
    private String tipo;

    /**
     * Obtiene el identificador del usuario.
     * 
     * @return El identificador único del usuario.
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el identificador del usuario.
     * 
     * @param idUsuario El nuevo identificador único para el usuario.
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el nombre de usuario.
     * 
     * @return El nombre de usuario del usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario.
     * 
     * @param username El nuevo nombre de usuario.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene el tipo de usuario (por ejemplo, profesor, coordinador, 
     * evaluador).
     * 
     * @return El tipo de usuario.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de usuario.
     * 
     * @param tipo El nuevo tipo de usuario.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
