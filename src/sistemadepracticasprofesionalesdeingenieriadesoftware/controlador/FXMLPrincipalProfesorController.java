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
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.pojo.Profesor;
import sistemadepracticasprofesionalesdeingenieriadesoftware.util.Utilidad;

public class FXMLPrincipalProfesorController implements Initializable {

    private Profesor profesorSesion;

    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Label lbNombreUsuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización si es necesaria
    }

    public void inicializarInformacion(Profesor profesorSesion) {
        this.profesorSesion = profesorSesion;
        cargarInformacionUsuario();
    }

    private void cargarInformacionUsuario() {
        if (profesorSesion != null) {
            lbNombreUsuario.setText(
                profesorSesion.getNombre() + " " +
                profesorSesion.getApellidoPaterno() + " " +
                profesorSesion.getApellidoMaterno()
            );
        }
    }

    @FXML
    private void clicValidarEntregas(ActionEvent event) {
    }

    @FXML
    private void clicConsultarExpediente(ActionEvent event) {
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
                Stage escenarioBase = Utilidad.getEscenarioComponente(lbNombreUsuario);
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
}

