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
    private final List<Proyecto> LISTA_PROYECTOS;
    private final Consumer<String> CALLBACK_ASIGNACION;
    private Estudiante estudiante;
    
    public ParametrosProyectosDisponibles(List<Proyecto> listaProyectos, 
            Consumer<String> callbackAsignacion, Estudiante estudiante) {
        this.LISTA_PROYECTOS = listaProyectos;
        this.CALLBACK_ASIGNACION = callbackAsignacion;
        this.estudiante = estudiante;
    }

    public List<Proyecto> getListaProyectos() {
        return LISTA_PROYECTOS;
    }

    public Consumer<String> getCallbackAsignacion() {
        return CALLBACK_ASIGNACION;
    }
    
    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}