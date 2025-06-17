/**
 * Autor: Uriel Cendón
 * Fecha de creación: 13/06/2025
 * Descripción: Clase POJO que representa un proyecto dentro del sistema de 
 * prácticas, incluyendo su identificador, nombre, descripción, fechas de 
 * inicio y fin, cupo, estado y la relación con la organización vinculada 
 * y el responsable del proyecto.
 */
package sistemapracticasis.modelo.pojo;

public class Proyecto {

    /**
     * Identificador único del proyecto.
     */
    private int idProyecto;

    /**
     * Nombre del proyecto.
     */
    private String nombre;

    /**
     * Descripción detallada del proyecto.
     */
    private String descripcion;

    /**
     * Número de participantes permitidos en el proyecto.
     */
    private int cupo;

    /**
     * Fecha de inicio del proyecto en formato String.
     */
    private String fecha_inicio;

    /**
     * Fecha de finalización del proyecto en formato String.
     */
    private String fecha_fin;

    /**
     * Identificador de la organización vinculada al proyecto.
     */
    private int idOrganizacionVinculada;

    /**
     * Identificador del responsable del proyecto.
     */
    private int idResponsableProyecto;

    /**
     * Nombre de la organización vinculada al proyecto.
     */
    private String nombreOV;

    /**
     * Estado actual del proyecto.
     */
    private EstadoProyecto estado;

    /**
     * Obtiene el identificador del proyecto.
     * @return El id del proyecto.
     */
    public int getIdProyecto() {
        return idProyecto;
    }

    /**
     * Establece el identificador del proyecto.
     * @param idProyecto El id del proyecto.
     */
    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    /**
     * Obtiene el nombre del proyecto.
     * @return El nombre del proyecto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del proyecto.
     * @param nombre El nombre del proyecto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del proyecto.
     * @return La descripción del proyecto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del proyecto.
     * @param descripcion La descripción del proyecto.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el estado del proyecto.
     * @return El estado del proyecto.
     */
    public EstadoProyecto getEstado() {
        return estado;
    }

    /**
     * Establece el estado del proyecto.
     * @param estado El estado del proyecto.
     */
    public void setEstado(EstadoProyecto estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el cupo de participantes en el proyecto.
     * @return El cupo de participantes del proyecto.
     */
    public int getCupo() {
        return cupo;
    }

    /**
     * Establece el cupo de participantes en el proyecto.
     * @param cupo El cupo de participantes.
     */
    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    /**
     * Obtiene la fecha de inicio del proyecto.
     * @return La fecha de inicio del proyecto.
     */
    public String getFecha_inicio() {
        return fecha_inicio;
    }

    /**
     * Establece la fecha de inicio del proyecto.
     * @param fecha_inicio La fecha de inicio del proyecto.
     */
    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    /**
     * Obtiene la fecha de finalización del proyecto.
     * @return La fecha de finalización del proyecto.
     */
    public String getFecha_fin() {
        return fecha_fin;
    }

    /**
     * Establece la fecha de finalización del proyecto.
     * @param fecha_fin La fecha de finalización del proyecto.
     */
    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    /**
     * Obtiene el identificador de la organización vinculada al proyecto.
     * @return El id de la organización vinculada.
     */
    public int getIdOrganizacionVinculada() {
        return idOrganizacionVinculada;
    }

    /**
     * Establece el identificador de la organización vinculada al proyecto.
     * @param idOrganizacionVinculada El id de la organización vinculada.
     */
    public void setIdOrganizacionVinculada(int idOrganizacionVinculada) {
        this.idOrganizacionVinculada = idOrganizacionVinculada;
    }

    /**
     * Obtiene el identificador del responsable del proyecto.
     * @return El id del responsable del proyecto.
     */
    public int getIdResponsableProyecto() {
        return idResponsableProyecto;
    }

    /**
     * Establece el identificador del responsable del proyecto.
     * @param idResponsableProyecto El id del responsable.
     */
    public void setIdResponsableProyecto(int idResponsableProyecto) {
        this.idResponsableProyecto = idResponsableProyecto;
    }
}
