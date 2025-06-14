package sistemapracticasis.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/**
 * FXML Controller class
 *
 * @author uriel
 */
public class FXMLActualizarExpedienteDocumentoInicialController implements 
        Initializable {

    private Estudiante estudianteSesion;
    
    @FXML
    private Label lblNombreUsuario;
    @FXML
    private TextField txtNombreEstudiante;
    @FXML
    private TextField txtPeriodo;
    @FXML
    private TextField txtInicioPeriodo;
    @FXML
    private TextField txtMatriculaEstudiante;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtFinPeriodo;
    @FXML
    private TextField txtHorasAcumuladas;
    @FXML
    private TextField txtEstado;
    @FXML
    private TextField txtNombreProyecto;
    @FXML
    private TextField txtNombreEE;
    @FXML
    private TextField txtNombreProfesor;
    @FXML
    private TableView<?> tblEntregas;
    @FXML
    private TableColumn<?, ?> tbcNombreDocEntrega;
    @FXML
    private TableColumn<?, ?> tbcFechaInicioEntrega;
    @FXML
    private TableColumn<?, ?> tbcFechaFinEntrega;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void inicializarInformacion(Estudiante estudianteSesion) {
        this.estudianteSesion = estudianteSesion;
        cargarInformacionUsuario();
    }

    private void cargarInformacionUsuario() {
        if (estudianteSesion != null) {
            lblNombreUsuario.setText(
                estudianteSesion.toString()
            );
        }
    }

    @FXML
    private void clicSalir(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion(
            "Salir",
            "Salir",
            "¿Está seguro de que quiere salir?")) {
                Navegador.cambiarEscenaParametrizada(
                    Utilidad.getEscenarioComponente(lblNombreUsuario),
                    "/sistemapracticasis/vista/FXMLPrincipalEstudiante.fxml",
                    FXMLPrincipalEstudianteController.class,
                    "inicializarInformacion",
                    estudianteSesion
                );
        }
    }

    @FXML
    private void clicSubirDocumento(ActionEvent event) {
    }
    
}
