/**
 * Autor: Uriel Cendón
 * Fecha de creación: 13/06/2025
 * Descripción: Clase POJO que representa a una organización vinculada
 * en el sistema de prácticas profesionales. Contiene información como
 * la razón social, teléfono, dirección y correo electrónico.
 */
package sistemapracticasis.modelo.pojo;

/**
 * Representa una organización vinculada que puede participar en el sistema de
 * prácticas profesionales.
 */
public class OrganizacionVinculada {
    /**
     * Identificador único de la organización vinculada.
     */
    private int idOrganizacionVinculada;

    /**
     * Razón social de la organización vinculada.
     */
    private String razonSocial;

    /**
     * Teléfono de contacto de la organización vinculada.
     */
    private String telefono;

    /**
     * Dirección física de la organización vinculada.
     */
    private String direccion;

    /**
     * Correo electrónico de la organización vinculada.
     */
    private String correo;

    /**
     * Obtiene el identificador de la organización vinculada.
     * @return El ID de la organización vinculada.
     */
    public int getIdOrganizacionVinculada() {
        return idOrganizacionVinculada;
    }

    /**
     * Establece el identificador de la organización vinculada.
     * @param idOrganizacionVinculada El ID a asignar.
     */
    public void setIdOrganizacionVinculada(int idOrganizacionVinculada) {
        this.idOrganizacionVinculada = idOrganizacionVinculada;
    }

    /**
     * Obtiene la razón social de la organización vinculada.
     * @return La razón social.
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * Establece la razón social de la organización vinculada.
     * @param razonSocial La razón social a asignar.
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * Obtiene el teléfono de la organización vinculada.
     * @return El número telefónico.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono de la organización vinculada.
     * @param telefono El número telefónico a asignar.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la dirección de la organización vinculada.
     * @return La dirección física.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección de la organización vinculada.
     * @param direccion La dirección física a asignar.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el correo electrónico de la organización vinculada.
     * @return El correo electrónico.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico de la organización vinculada.
     * @param correo El correo electrónico a asignar.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Representación en cadena de la organización vinculada.
     * @return La razón social como representación textual.
     */
    @Override
    public String toString() {
        return razonSocial;
    }
}
