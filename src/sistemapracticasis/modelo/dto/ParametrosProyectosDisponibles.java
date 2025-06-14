package sistemapracticasis.modelo.dto;

import java.util.List;
import java.util.function.Consumer;
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.modelo.pojo.Proyecto;

/**
 *
 * @author uriel
 */
public class ParametrosProyectosDisponibles {
    private final List<Proyecto> listaProyectos;
    private final Consumer<String> callbackAsignacion;
    private Estudiante estudiante;
    
    public ParametrosProyectosDisponibles(List<Proyecto> listaProyectos, 
            Consumer<String> callbackAsignacion, Estudiante estudiante) {
        this.listaProyectos = listaProyectos;
        this.callbackAsignacion = callbackAsignacion;
        this.estudiante = estudiante;
    }

    public List<Proyecto> getListaProyectos() {
        return listaProyectos;
    }

    public Consumer<String> getCallbackAsignacion() {
        return callbackAsignacion;
    }
    
    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}