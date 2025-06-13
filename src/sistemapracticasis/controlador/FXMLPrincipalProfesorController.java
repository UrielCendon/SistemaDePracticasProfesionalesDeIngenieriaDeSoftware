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
import sistemapracticasis.modelo.pojo.Profesor;
import sistemapracticasis.util.Utilidad;

public class FXMLPrincipalProfesorController implements Initializable {

    private Profesor profesorSesion;

    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Label lblNombreUsuario;

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
            lblNombreUsuario.setText(
                profesorSesion.toString()
            );
        }
    }

    @FXML
    private void clicValidarEntregas(ActionEvent event) {
        try {
            Stage escenarioBase = (Stage) lblNombreUsuario.getScene().getWindow();
            FXMLLoader cargador = new FXMLLoader
                (SistemaPracticasIS.class.
                        getResource("vista/FXMLValidarEntrega.fxml"));
            Parent vista = cargador.load();
            FXMLValidarEntregaController controlador = cargador.
                getController();
            controlador.inicializarInformacion(profesorSesion);
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

    @FXML
    private void clicConsultarExpediente(ActionEvent event) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
            "Funcionalidad no disponible", "Esta sección del sistema aún no "
            + "ha sido desarrollada en esta versión.");
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

