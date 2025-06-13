package sistemapracticasis.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sistemapracticasis.SistemaPracticasIS;
import sistemapracticasis.modelo.pojo.Estudiante;
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
        boolean confirmado = Utilidad.mostrarConfirmacion(
            "Salir",
            "¿Está seguro de que quiere salir?",
            ""
        );

        if (confirmado) {
            try {
                Stage escenarioBase = (Stage) lblNombreUsuario.getScene().getWindow();
                FXMLLoader cargador = new FXMLLoader
                    (SistemaPracticasIS.class.
                            getResource("vista/FXMLPrincipalEstudiante.fxml"));
                Parent vista = cargador.load();
                FXMLPrincipalEstudianteController controlador = cargador.
                    getController();
                controlador.inicializarInformacion(estudianteSesion);
                Scene escenaPrincipal = new Scene(vista);
                escenarioBase.setScene(escenaPrincipal);
                escenarioBase.setTitle("Sistema de gestión de prácticas "
                    + "profesionales");
                escenarioBase.centerOnScreen();
                escenarioBase.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void clicSubirDocumento(ActionEvent event) {
    }
    
}
