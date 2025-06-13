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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sistemapracticasis.SistemaPracticasIS;
import sistemapracticasis.modelo.pojo.Evaluador;
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
    private void ClicCerrarSesion(ActionEvent event) {
        boolean confirmado = Utilidad.mostrarConfirmacion(
            "Confirmar cierre de sesión",
            "¿Está seguro(a) de querer cerrar la sesión actual?",
            "Se perderá el acceso a esta sesión."
        );

        if (confirmado) {
            try {
                Stage escenarioBase = Utilidad.getEscenarioComponente(lblNombreUsuario);
                Parent vista = FXMLLoader.load(
                    SistemaPracticasIS.class.getResource("vista/FXMLInicioSesion.fxml")
                );
                Scene escenaPrincipal = new Scene(vista);
                escenarioBase.setScene(escenaPrincipal);
                escenarioBase.setTitle("Inicio Sesión");
                escenarioBase.centerOnScreen();
                escenarioBase.show();
            } catch (IOException ex) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudo cerrar sesión.");
                ex.printStackTrace();
            }
        }
    }
}
