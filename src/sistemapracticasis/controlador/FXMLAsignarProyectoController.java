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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import sistemapracticasis.SistemaPracticasIS;
import sistemapracticasis.modelo.pojo.Coordinador;
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
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        boolean confirmado = Utilidad.mostrarConfirmacion(
            "Cancelar",
            "¿Está seguro de que quiere cancelar?",
            ""
        );

        if (confirmado) {
            try {
                Stage escenarioBase = (Stage) lblNombreUsuario.getScene().getWindow();
                FXMLLoader cargador = new FXMLLoader
                    (SistemaPracticasIS.class.
                            getResource("vista/FXMLPrincipalCoordinador.fxml"));
                Parent vista = cargador.load();
                FXMLPrincipalCoordinadorController controlador = cargador.
                    getController();
                controlador.inicializarInformacion(coordinadorSesion);
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
    private void clicBuscar(ActionEvent event) {
    }
    
}
