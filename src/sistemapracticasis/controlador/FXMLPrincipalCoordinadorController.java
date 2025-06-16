package sistemapracticasis.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sistemapracticasis.modelo.pojo.Coordinador;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

public class FXMLPrincipalCoordinadorController implements Initializable {

    private Coordinador coordinadorSesion;

    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Label lblNombreUsuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Sin implementación por ahora
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
    private void clicRegistrarProyecto(ActionEvent event) {
    }

    @FXML
    private void clicRegistrarResponsable(ActionEvent event) {
    }

    @FXML
    private void clicRegistrarOV(ActionEvent event) {
    }

    @FXML
    private void clicActualizarProyecto(ActionEvent event) {
    }

    @FXML
    private void clicActualizarResponsable(ActionEvent event) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
            "Funcionalidad no disponible", "Esta sección del sistema aún no "
            + "ha sido desarrollada en esta versión.");
    }

    @FXML
    private void clicAsignarProyecto(ActionEvent event) {
        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(lblNombreUsuario),
            "/sistemapracticasis/vista/FXMLAsignarProyecto.fxml",
            FXMLAsignarProyectoController.class,
            "inicializarInformacion",
            coordinadorSesion
        );
    }

    @FXML
    private void clicGenerarDocuAsignacion(ActionEvent event) {
        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(lblNombreUsuario),
            "/sistemapracticasis/vista/FXMLGenerarOficios.fxml",
            sistemapracticasis.controlador.FXMLGenerarOficiosController.class,
            "inicializarInformacion",
            coordinadorSesion
        );
    }

    @FXML
    private void clicProgramarEntregas(ActionEvent event) {
        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(lblNombreUsuario),
            "/sistemapracticasis/vista/FXMLProgramarEntrega.fxml",
            FXMLProgramarEntregaController.class,
            "inicializarInformacion",
            coordinadorSesion // → se lo vamos a pasar para que muestre nombre o más datos si quieres
        );
    }

    @FXML
    private void ClicCerrarSesion(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion("Confirmar cierre de sesión", 
            "¿Está seguro(a) de querer cerrar la sesión actual?", 
            "Se perderá el acceso a esta sesión.")) {
                Navegador.cerrarSesion(
                    Utilidad.getEscenarioComponente(lblNombreUsuario)
                );
        }
    }
}
