/**
 * Autor: Uriel Cendón
 * Fecha de creación: 14/06/2025
 * Descripción: Clase POJO que representa un responsable de proyecto dentro
 * del sistema de prácticas, incluyendo información como su nombre, contacto,
 * puesto y el departamento al que pertenece, así como la organización 
 * vinculada.
 */
package sistemapracticasis.modelo.pojo;

public class ResponsableProyecto {

    /**
     * Identificador único del encargado del proyecto.
     */
    private int idEncargado;

    /**
     * Nombre del encargado del proyecto.
     */
    private String nombre;

    /**
     * Teléfono de contacto del encargado del proyecto.
     */
    private String telefono;

    /**
     * Correo electrónico del encargado del proyecto.
     */
    private String correo;

    /**
     * Puesto que ocupa el encargado del proyecto en la organización.
     */
    private String puesto;

    /**
     * Departamento al que pertenece el encargado del proyecto.
     */
    private String departamento;

    /**
     * Apellido paterno del encargado del proyecto.
     */
    private String apellidoPaterno;

    /**
     * Apellido materno del encargado del proyecto.
     */
    private String apellidoMaterno;

    /**
     * Identificador de la organización vinculada con el encargado del proyecto.
     */
    private int idOrganizacionVinculada;

    // Getters y Setters

    /**
     * Obtiene el identificador del encargado del proyecto.
     * @return El id del encargado.
     */
    public int getIdEncargado() {
        return idEncargado;
    }

    /**
     * Establece el identificador del encargado del proyecto.
     * @param idEncargado El id del encargado.
     */
    public void setIdEncargado(int idEncargado) {
        this.idEncargado = idEncargado;
    }

    /**
     * Obtiene el nombre del encargado del proyecto.
     * @return El nombre del encargado.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del encargado del proyecto.
     * @param nombre El nombre del encargado.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el teléfono del encargado del proyecto.
     * @return El teléfono del encargado.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del encargado del proyecto.
     * @param telefono El teléfono del encargado.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el correo del encargado del proyecto.
     * @return El correo del encargado.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo del encargado del proyecto.
     * @param correo El correo del encargado.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el puesto del encargado del proyecto.
     * @return El puesto del encargado.
     */
    public String getPuesto() {
        return puesto;
    }

    /**
     * Establece el puesto del encargado del proyecto.
     * @param puesto El puesto del encargado.
     */
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    /**
     * Obtiene el departamento al que pertenece el encargado del proyecto.
     * @return El departamento del encargado.
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * Establece el departamento del encargado del proyecto.
     * @param departamento El departamento del encargado.
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * Obtiene el apellido paterno del encargado del proyecto.
     * @return El apellido paterno del encargado.
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Establece el apellido paterno del encargado del proyecto.
     * @param apellidoPaterno El apellido paterno del encargado.
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * Obtiene el apellido materno del encargado del proyecto.
     * @return El apellido materno del encargado.
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Establece el apellido materno del encargado del proyecto.
     * @param apellidoMaterno El apellido materno del encargado.
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * Obtiene el identificador de la organización vinculada al encargado del 
     * proyecto.
     * @return El id de la organización vinculada.
     */
    public int getIdOrganizacionVinculada() {
        return idOrganizacionVinculada;
    }

    /**
     * Establece el identificador de la organización vinculada al encargado del 
     * proyecto.
     * @param idOrganizacionVinculada El id de la organización vinculada.
     */
    public void setIdOrganizacionVinculada(int idOrganizacionVinculada) {
        this.idOrganizacionVinculada = idOrganizacionVinculada;
    }

    /**
     * Devuelve una representación en cadena del responsable del proyecto,
     * que incluye su nombre completo.
     * @return El nombre completo del responsable.
     */
    @Override
    public String toString() {
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }

}
