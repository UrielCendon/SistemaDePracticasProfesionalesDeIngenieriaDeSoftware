/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemapracticasis.modelo.pojo;

import javafx.beans.property.*;

/**
 *
 * @author Kaiser
 */
public class EstudianteAsignado {
    private final StringProperty matricula;
    private final StringProperty nombreEstudiante;
    private final StringProperty nombreProyecto;
    private final StringProperty razonSocialOrganizacion;
    private final BooleanProperty seleccionado;

    public EstudianteAsignado(String matricula, String nombreEstudiante,
                              String nombreProyecto, String razonSocialOrganizacion) {
        this.matricula = new SimpleStringProperty(matricula);
        this.nombreEstudiante = new SimpleStringProperty(nombreEstudiante);
        this.nombreProyecto = new SimpleStringProperty(nombreProyecto);
        this.razonSocialOrganizacion = new SimpleStringProperty(razonSocialOrganizacion);
        this.seleccionado = new SimpleBooleanProperty(false);
    }

    public String getMatricula() { return matricula.get(); }
    public StringProperty matriculaProperty() { return matricula; }

    public String getNombreEstudiante() { return nombreEstudiante.get(); }
    public StringProperty nombreEstudianteProperty() { return nombreEstudiante; }

    public String getNombreProyecto() { return nombreProyecto.get(); }
    public StringProperty nombreProyectoProperty() { return nombreProyecto; }

    public String getRazonSocialOrganizacion() { return razonSocialOrganizacion.get(); }
    public StringProperty razonSocialOrganizacionProperty() { return razonSocialOrganizacion; }

    public boolean isSeleccionado() { return seleccionado.get(); }
    public BooleanProperty seleccionadoProperty() { return seleccionado; }
    public void setSeleccionado(boolean seleccionado) { this.seleccionado.set(seleccionado); }
}
