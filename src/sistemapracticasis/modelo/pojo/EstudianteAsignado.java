package sistemapracticasis.modelo.pojo;

import javafx.beans.property.*;

/**
 * Autor: Raziel Filobello  
 * Fecha de creación: 13/06/2025  
 * Descripción: Clase modelo que representa a un estudiante asignado a un 
 * proyecto. Se utiliza principalmente para mostrar información en tablas de 
 * JavaFX, con propiedades observables. Incluye información como la matrícula 
 * del estudiante, su nombre, el nombre del proyecto asignado, la organización 
 * asociada y un estado de selección para manipulación visual.
 */
public class EstudianteAsignado {

    /**
     * Matrícula del estudiante.
     */
    private final StringProperty matricula;

    /**
     * Nombre completo del estudiante.
     */
    private final StringProperty nombreEstudiante;

    /**
     * Nombre del proyecto asignado al estudiante.
     */
    private final StringProperty nombreProyecto;

    /**
     * Razón social de la organización que proporciona el proyecto.
     */
    private final StringProperty razonSocialOrganizacion;

    /**
     * Indicador de si el estudiante ha sido seleccionado (por ejemplo, para una acción en la interfaz).
     */
    private final BooleanProperty seleccionado;

    /**
     * Constructor de la clase EstudianteAsignado.
     * 
     * @param matricula Matrícula del estudiante.
     * @param nombreEstudiante Nombre del estudiante.
     * @param nombreProyecto Nombre del proyecto asignado.
     * @param razonSocialOrganizacion Razón social de la organización vinculada.
     */
    public EstudianteAsignado(String matricula, String nombreEstudiante,
                              String nombreProyecto, String razonSocialOrganizacion) {
        this.matricula = new SimpleStringProperty(matricula);
        this.nombreEstudiante = new SimpleStringProperty(nombreEstudiante);
        this.nombreProyecto = new SimpleStringProperty(nombreProyecto);
        this.razonSocialOrganizacion = new SimpleStringProperty(razonSocialOrganizacion);
        this.seleccionado = new SimpleBooleanProperty(false);
    }

    /**
     * Obtiene la matrícula del estudiante.
     * @return Matrícula como cadena.
     */
    public String getMatricula() {
        return matricula.get();
    }

    /**
     * Obtiene la propiedad observable de la matrícula.
     * @return Objeto StringProperty de la matrícula.
     */
    public StringProperty matriculaProperty() {
        return matricula;
    }

    /**
     * Obtiene el nombre del estudiante.
     * @return Nombre del estudiante.
     */
    public String getNombreEstudiante() {
        return nombreEstudiante.get();
    }

    /**
     * Obtiene la propiedad observable del nombre del estudiante.
     * @return Objeto StringProperty del nombre del estudiante.
     */
    public StringProperty nombreEstudianteProperty() {
        return nombreEstudiante;
    }

    /**
     * Obtiene el nombre del proyecto asignado.
     * @return Nombre del proyecto.
     */
    public String getNombreProyecto() {
        return nombreProyecto.get();
    }

    /**
     * Obtiene la propiedad observable del nombre del proyecto.
     * @return Objeto StringProperty del nombre del proyecto.
     */
    public StringProperty nombreProyectoProperty() {
        return nombreProyecto;
    }

    /**
     * Obtiene la razón social de la organización vinculada al proyecto.
     * @return Razón social de la organización.
     */
    public String getRazonSocialOrganizacion() {
        return razonSocialOrganizacion.get();
    }

    /**
     * Obtiene la propiedad observable de la razón social de la organización.
     * @return Objeto StringProperty de la razón social.
     */
    public StringProperty razonSocialOrganizacionProperty() {
        return razonSocialOrganizacion;
    }

    /**
     * Indica si el estudiante ha sido marcado como seleccionado.
     * @return `true` si está seleccionado, `false` en caso contrario.
     */
    public boolean isSeleccionado() {
        return seleccionado.get();
    }

    /**
     * Obtiene la propiedad observable del estado de selección.
     * @return Objeto BooleanProperty del estado seleccionado.
     */
    public BooleanProperty seleccionadoProperty() {
        return seleccionado;
    }

    /**
     * Establece el estado de selección del estudiante.
     * @param seleccionado Valor booleano que indica si está seleccionado.
     */
    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado.set(seleccionado);
    }
}
