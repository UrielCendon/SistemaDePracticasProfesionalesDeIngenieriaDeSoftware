package sistemapracticasis.controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.function.Consumer;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import sistemapracticasis.modelo.dao.EstudianteDAO;
import sistemapracticasis.modelo.dto.ParametrosProyectosDisponibles;
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.modelo.pojo.Proyecto;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/**
 * FXML Controller class
 *
 * @author uriel
 */
public class FXMLProyectosDisponiblesController implements Initializable {

    private EstudianteDAO estudianteDAO = new EstudianteDAO();
    
    @FXML
    private TableView<Proyecto> tblProyectos;
    @FXML
    private TableColumn<Proyecto, String> tbcNombre;
    @FXML
    private TextField txtProyectoSeleccionado;
    
    private Consumer<String> proyectoAsignadoCallback;
    private List<Proyecto> listaProyectos;
    private Estudiante estudiante;
    
    @FXML
    private Button btnAsignarProyecto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAsignarProyecto.setDisable(true);
    }    

    public void setProyectoAsignadoCallback(Consumer<String> callback) {
        this.proyectoAsignadoCallback = callback;
    }
    
    public void inicializarInformacion(Estudiante estudiante,
            ParametrosProyectosDisponibles parametros) {
        this.estudiante = estudiante;
        this.listaProyectos = parametros.getListaProyectos();
        this.proyectoAsignadoCallback = parametros.getCallbackAsignacion();
        cargarProyectosEnTabla();
    }
    
    @FXML
    private void clicAsignarProyecto(ActionEvent event) {
        if(Utilidad.mostrarConfirmacion(
            "Confirmación de asignación",
            "Confirmación de asignación",
            "¿Desea guardar la asignación de este proyecto?")){
                Proyecto proyectoSeleccionado = tblProyectos.
                    getSelectionModel().getSelectedItem();
                if (proyectoSeleccionado != null) {
                    int idProyecto = proyectoSeleccionado.getIdProyecto();
                    boolean actualizado = estudianteDAO.asignarProyecto(
                        estudiante.getMatricula(), idProyecto);

                    if (actualizado) {
                        if (proyectoAsignadoCallback != null) {
                            proyectoAsignadoCallback.accept
                                (proyectoSeleccionado.getNombre());
                        }
                        Navegador.cerrarVentana(txtProyectoSeleccionado);
                        Utilidad.mostrarAlertaSimple(
                            Alert.AlertType.INFORMATION, 
                            "Proyecto Asignado", 
                            "Proyecto asignado con éxito"
                        );
                    } else {
                        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                            "Error", "No se pudo asignar el proyecto."
                        );
                    }
                }
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion(
            "Cancelar",
            "Cancelar",
            "¿Está seguro de que quiere cancelar?")) {
                Navegador.cerrarVentana(txtProyectoSeleccionado);
        }
    }
    
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
