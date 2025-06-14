package sistemapracticasis.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import sistemapracticasis.modelo.pojo.Coordinador;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/**
 * FXML Controller class
 *
 * @author uriel
 */
public class FXMLAsignarProyectoController implements Initializable {

    private Coordinador coordinadorSesion;
    
    @FXML
    private Button btnAsignarProyecto;
    @FXML
    private Label lblMatricula;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblCorreo;
    @FXML
    private Label lblTelefono;
    @FXML
    private Label lblProyecto;
    @FXML
    private Button btnCancelar;
    @FXML
    private Line linMatricula;
    @FXML
    private Line linNombre;
    @FXML
    private Line linCorreo;
    @FXML
    private Line linTelefono;
    @FXML
    private Line linProyecto;
    @FXML
    private TextField txtBuscar;
    @FXML
    private TextField txtMatricula;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtProyecto;
    @FXML
    private Label lblNombreUsuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void inicializarInformacion(Coordinador coordinadorSesion) {
        this.coordinadorSesion = coordinadorSesion;
        cargarInformacionUsuario();
    }

    private void cargarInformacionUsuario() {
        if (coordinadorSesion != null) {
            lblNombreUsuario.setText(
                coordinadorSesion.toString()
            );
        }
    }

    @FXML
    private void clicAsignarProyecto(ActionEvent event) {
        Navegador.abrirVentanaModal(
            Utilidad.getEscenarioComponente(lblNombreUsuario),
            "/sistemapracticasis/vista/FXMLProyectosDisponibles.fxml",
            FXMLProyectosDisponiblesController.class
        );
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion(
            "Cancelar",
            "Cancelar",
            "¿Está seguro de que quiere cancelar?")) {
                Navegador.cambiarEscena(
                    Utilidad.getEscenarioComponente(lblNombreUsuario),
                    "/sistemapracticasis/vista/FXMLPrincipalCoordinador.fxml",
                    FXMLPrincipalCoordinadorController.class,
                    "inicializarInformacion",
                    coordinadorSesion
                );
        }
    }

    @FXML
    private void clicBuscar(ActionEvent event) {
    }
    
}
