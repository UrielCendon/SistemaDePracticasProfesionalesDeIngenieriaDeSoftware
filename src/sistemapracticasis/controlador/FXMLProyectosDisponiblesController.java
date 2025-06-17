package sistemapracticasis.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import sistemapracticasis.modelo.dao.EstudianteDAO;
import sistemapracticasis.modelo.dao.ExpedienteDAO;
import sistemapracticasis.modelo.dao.ProyectoDAO;
import sistemapracticasis.modelo.dto.ParametrosProyectosDisponibles;
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.modelo.pojo.Proyecto;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/**
 * Autor: Uriel Cendón
 * Fecha de creación: 13/06/2025
 * Descripción: Controlador de la vista FXMLProyectosDisponibles,
 * que permite al coordinador visualizar y seleccionar un proyecto para
 * asignarle al estudiante seleccionado un proyecto
 */
public class FXMLProyectosDisponiblesController implements Initializable {

    /* Sección: Declaración de variables
    * Contiene todas las variables de instancia y componentes FXML
    * utilizados en el controlador.
    /** Instancia global de un DAO para usarse en varios puntos de la clase */
    private EstudianteDAO estudianteDAO = new EstudianteDAO();
    
    /** Tabla que muestra el listado de proyectos disponibles */
    @FXML private TableView<Proyecto> tblProyectos;

    /** Columna que muestra el nombre del proyecto en la tabla */
    @FXML private TableColumn<Proyecto, String> tbcNombre;

    /** Campo de texto que muestra el proyecto seleccionado */
    @FXML private TextField txtProyectoSeleccionado;

    /** Botón para asignar el proyecto seleccionado */
    @FXML private Button btnAsignarProyecto;

    /** Callback para notificar la asignación de proyecto */
    private Consumer<String> proyectoAsignadoCallback;

    /** Lista de proyectos disponibles */
    private List<Proyecto> listaProyectos;

    /** Estudiante al que se asignará el proyecto */
    private Estudiante estudiante;

    /* Sección: Inicialización del controlador
    * Contiene los métodos relacionados con la configuración inicial
    * del controlador y carga de información básica.
    */
    /**
     * Inicializa el controlador después de que su elemento raíz haya sido 
     * procesado.
     * Configura el estado inicial de los componentes.
     * @param url Ubicación utilizada para resolver rutas relativas para el 
     * objeto raíz.
     * @param rb Recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAsignarProyecto.setDisable(true);
    }    

    /**
     * Establece el callback para notificar la asignación de proyecto.
     * @param callback Función de callback que se ejecutará al asignar un 
     * proyecto.
     */
    public void setProyectoAsignadoCallback(Consumer<String> callback) {
        this.proyectoAsignadoCallback = callback;
    }
    
    /**
     * Inicializa la información del estudiante y proyectos disponibles.
     * @param estudiante Estudiante al que se asignará el proyecto.
     * @param parametros Parámetros que contienen la lista de proyectos 
     * disponibles y callback.
     */
    public void inicializarInformacion(Estudiante estudiante,
            ParametrosProyectosDisponibles parametros) {
        this.estudiante = estudiante;
        this.listaProyectos = parametros.getListaProyectos();
        this.proyectoAsignadoCallback = parametros.getCallbackAsignacion();
        cargarProyectosEnTabla();
    }
    
    /* Sección: Manejo de eventos de UI
    * Contiene los métodos que gestionan las interacciones del usuario
    * con los componentes de la interfaz.
    */
    /**
     * Maneja el evento de clic en el botón para asignar proyecto.
     * Asigna el proyecto seleccionado al estudiante y notifica el resultado.
     * @param event Evento de acción generado por el clic.
     */
    @FXML
    private void clicAsignarProyecto(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion(
            "Confirmación de asignación",
            "Confirmación de asignación",
            "¿Desea guardar la asignación de este proyecto?")) {

            Proyecto proyectoSeleccionado = tblProyectos.getSelectionModel()
                .getSelectedItem();

            if (proyectoSeleccionado != null) {
                int idProyecto = proyectoSeleccionado.getIdProyecto();
                boolean actualizado = estudianteDAO.asignarProyecto
                    (estudiante.getMatricula(), idProyecto);

                if (actualizado) {
                    try {
                        int idExpediente = ExpedienteDAO.
                            insertarExpedienteVacio();

                        if (idExpediente > 0) {
                            boolean actualizadoPeriodo = ExpedienteDAO
                                .insertarEstudianteEnExpediente(estudiante.
                                    getIdEstudiante(), idExpediente);

                            if (!actualizadoPeriodo) {
                                Utilidad.mostrarAlertaSimple(
                                    Alert.AlertType.ERROR,
                                    "Error",
                                    "No se pudo asociar el expediente al "
                                    + "estudiante."
                                );
                                return;
                            }
                        } else {
                            Utilidad.mostrarAlertaSimple(
                                Alert.AlertType.ERROR,
                                "Error",
                                "No se pudo crear el expediente vacío."
                            );
                            return;
                        }
                        boolean cupoActualizado = ProyectoDAO
                                .decrementarCupoProyecto(idProyecto);

                        if (!cupoActualizado) {
                            Utilidad.mostrarAlertaSimple(
                                Alert.AlertType.WARNING,
                                "Aviso",
                                "El proyecto fue asignado, pero no se pudo "
                                + "actualizar el cupo."
                            );
                        }

                        if (proyectoAsignadoCallback != null) {
                            proyectoAsignadoCallback.accept
                                (proyectoSeleccionado.getNombre());
                        }

                        Navegador.cerrarVentana(txtProyectoSeleccionado);
                        Utilidad.mostrarAlertaSimple(
                            Alert.AlertType.INFORMATION,
                            "Proyecto Asignado",
                            "Proyecto asignado con éxito."
                        );

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        Utilidad.mostrarAlertaSimple(
                            Alert.AlertType.ERROR,
                            "ErrorBD",
                            "No hay conexión con la base de datos."
                        );
                    }
                } else {
                    Utilidad.mostrarAlertaSimple(
                        Alert.AlertType.ERROR,
                        "Error",
                        "No se pudo asignar el proyecto al estudiante."
                    );
                }
            }
        }
    }

    /**
     * Maneja el evento de clic en el botón para cancelar la operación.
     * Cierra la ventana previa confirmación del usuario.
     * @param event Evento de acción generado por el clic.
     */
    @FXML
    private void clicCancelar(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion(
            "Cancelar",
            "Cancelar",
            "¿Está seguro de que quiere cancelar?")) {
                Navegador.cerrarVentana(txtProyectoSeleccionado);
        }
    }
    
    /* Sección: Lógica de negocio
    * Contiene los métodos que implementan la funcionalidad principal
    * de la aplicación.
    */
    private void cargarProyectosEnTabla() {
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tblProyectos.getItems().setAll(listaProyectos);

        tblProyectos.getSelectionModel().selectedItemProperty().addListener
                ((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtProyectoSeleccionado.setText(newVal.getNombre());
                btnAsignarProyecto.setDisable(false);
            }
        });
    }
}
