package sistemadepracticasprofesionalesdeingenieriadesoftware.controlador;

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
import sistemadepracticasprofesionalesdeingenieriadesoftware.SistemaDePracticasProfesionalesDeIngenieriaDeSoftware;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.pojo.Estudiante;
import sistemadepracticasprofesionalesdeingenieriadesoftware.util.Utilidad;

/**
 * FXML Controller class
 */
public class FXMLPrincipalEstudianteController implements Initializable {

    private Estudiante estudianteSesion;

    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Label lblNombreUsuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Sin implementación por ahora
    }

    public void inicializarInformacion(Estudiante estudianteSesion) {
        this.estudianteSesion = estudianteSesion;
        cargarInformacionUsuario();
    }

    private void cargarInformacionUsuario() {
        if (estudianteSesion != null) {
            lblNombreUsuario.setText(
                estudianteSesion.getNombre() + " " +
                estudianteSesion.getApellidoPaterno() + " " +
                estudianteSesion.getApellidoMaterno()
            );
        }
    }

    @FXML
    private void clicDocIniciales(ActionEvent event) {
    }

    @FXML
    private void clicDocIntermedios(ActionEvent event) {
    }

    @FXML
    private void clicDocFinales(ActionEvent event) {
    }

    @FXML
    private void clicDocReportes(ActionEvent event) {
    }

    @FXML
    private void btnClicCerrarSesion(ActionEvent event) {
        boolean confirmado = Utilidad.mostrarConfirmacion(
            "Confirmar cierre de sesión",
            "¿Está seguro(a) de querer cerrar la sesión actual?",
            "Se perderá el acceso a esta sesión."
        );

        if (confirmado) {
            try {
                Stage escenarioBase = Utilidad.getEscenarioComponente(lblNombreUsuario);
                Parent vista = FXMLLoader.load(
                    SistemaDePracticasProfesionalesDeIngenieriaDeSoftware.class.getResource("vista/FXMLInicioSesion.fxml")
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

    @FXML
    private void clicGenerarFormatoEvaluacion(ActionEvent event) {
    }

    @FXML
    private void clicConsultarAvance(ActionEvent event) {
    }

    @FXML
    private void clicEvaluarOV(ActionEvent event) {
    }
}
