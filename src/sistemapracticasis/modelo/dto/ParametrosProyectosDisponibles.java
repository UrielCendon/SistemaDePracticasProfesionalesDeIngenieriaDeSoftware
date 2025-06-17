package sistemapracticasis.modelo.dto;

import java.util.List;
import java.util.function.Consumer;

import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.modelo.pojo.Proyecto;


/** 
 * Autor: Uriel Cendón
 * Fecha de creación: 13/06/2025 
 * Descripción: Clase que gestiona los parámetros relacionados con los proyectos 
 * disponibles para un estudiante, incluyendo la lista de proyectos, un callback 
 * para la asignación y la información del estudiante.
 */
public class ParametrosProyectosDisponibles {
    
    /**
     * Lista de proyectos disponibles.
     */
    private final List<Proyecto> LISTA_PROYECTOS;
    
    /**
     * Callback que se invoca para la asignación de un proyecto.
     */
    private final Consumer<String> CALLBACK_ASIGNACION;
    
    /**
     * Información del estudiante a quien se le asignará el proyecto.
     */
    private Estudiante estudiante;
    
    /**
     * Constructor de la clase ParametrosProyectosDisponibles.
     * 
     * @param listaProyectos Lista de proyectos disponibles para asignar.
     * @param callbackAsignacion Función de retorno que se ejecuta al asignar 
     *                           un proyecto.
     * @param estudiante El estudiante al que se le asignará un proyecto.
     */
    public ParametrosProyectosDisponibles(List<Proyecto> listaProyectos, 
            Consumer<String> callbackAsignacion, Estudiante estudiante) {
        this.LISTA_PROYECTOS = listaProyectos;
        this.CALLBACK_ASIGNACION = callbackAsignacion;
        this.estudiante = estudiante;
    }

    /**
     * Obtiene la lista de proyectos disponibles.
     * 
     * @return La lista de proyectos disponibles.
     */
    public List<Proyecto> getListaProyectos() {
        return LISTA_PROYECTOS;
    }

    /**
     * Obtiene el callback que se utiliza para la asignación de un proyecto.
     * 
     * @return El callback de asignación de proyecto.
     */
    public Consumer<String> getCallbackAsignacion() {
        return CALLBACK_ASIGNACION;
    }
    
    /**
     * Obtiene la información del estudiante.
     * 
     * @return El estudiante asociado a la asignación de proyectos.
     */
    public Estudiante getEstudiante() {
        return estudiante;
    }

    /**
     * Establece la información del estudiante.
     * 
     * @param estudiante El estudiante que se desea asignar a un proyecto.
     */
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}
