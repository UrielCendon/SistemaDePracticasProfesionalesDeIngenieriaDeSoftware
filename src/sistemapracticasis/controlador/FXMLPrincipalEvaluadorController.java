package sistemapracticasis.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sistemapracticasis.modelo.pojo.Evaluador;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/**
 * FXML Controller class
 */
public class FXMLPrincipalEvaluadorController implements Initializable {

    private Evaluador evaluadorSesion;

    @FXML
    private Label lblNombreUsuario;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Button btnAceptar;
    @FXML
    private TableView<?> tvEvaluarEstudiante;
    @FXML
    private TableColumn<?, ?> colMatricula;
    @FXML
    private TableColumn<?, ?> colEstudiante;
    @FXML
    private TableColumn<?, ?> colProyectoAsignado;
    @FXML
    private TableColumn<?, ?> colSeleccionEstudiante;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Sin inicialización específica
    }

    public void inicializarInformacion(Evaluador evaluadorSesion) {
        this.evaluadorSesion = evaluadorSesion;
        cargarInformacionUsuario();
    }

    private void cargarInformacionUsuario() {
        if (evaluadorSesion != null) {
            lblNombreUsuario.setText(
                evaluadorSesion.toString()
            );
        }
    }

    @FXML
    private void clicCerrarSesion(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion("Confirmar cierre de sesión", 
            "¿Está seguro(a) de querer cerrar la sesión actual?", 
            "Se perderá el acceso a esta sesión.")) {
                Navegador.cerrarSesion(
                    Utilidad.getEscenarioComponente(lblNombreUsuario)
                );
        }
    }

    @FXML
    private void clicAceptar(ActionEvent event) {
    }
}
