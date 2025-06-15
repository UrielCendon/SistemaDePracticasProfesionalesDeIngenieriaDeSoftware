package sistemapracticasis.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sistemapracticasis.modelo.dao.EntregaDocumentoDAO;
import sistemapracticasis.modelo.dao.EstudianteDAO;
import sistemapracticasis.modelo.dao.ExpedienteDAO;
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

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
                estudianteSesion.toString()
            );
        }
    }

    @FXML
    private void clicDocIniciales(ActionEvent event) {
        int idEstudiante = estudianteSesion.getIdEstudiante();

        if(!EntregaDocumentoDAO.existeEntregaInicialVigente(idEstudiante)){
            Utilidad.mostrarAlertaSimple(
            Alert.AlertType.WARNING,
            "Fecha inválida",
            "La fecha de entrega ha concluido."
            );
            return;
        }
        
        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(lblNombreUsuario),
            "/sistemapracticasis/vista/FXMLActualizarExpedienteDocumento"
                + "Inicial.fxml",
            FXMLActualizarExpedienteDocumentoInicialController.class,
            "inicializarInformacion",
            estudianteSesion
        );
    }

    @FXML
    private void clicDocIntermedios(ActionEvent event) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
            "Funcionalidad no disponible", "Esta sección del sistema aún no "
            + "ha sido desarrollada en esta versión.");
    }

    @FXML
    private void clicDocFinales(ActionEvent event) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
            "Funcionalidad no disponible", "Esta sección del sistema aún no "
            + "ha sido desarrollada en esta versión.");
    }

    @FXML
    private void clicDocReportes(ActionEvent event) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
            "Funcionalidad no disponible", "Esta sección del sistema aún no "
            + "ha sido desarrollada en esta versión.");
    }

    @FXML
    private void clicGenerarFormatoEvaluacion(ActionEvent event) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
            "Funcionalidad no disponible", "Esta sección del sistema aún no "
            + "ha sido desarrollada en esta versión.");
    }

    @FXML
    private void clicConsultarAvance(ActionEvent event) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
            "Funcionalidad no disponible", "Esta sección del sistema aún no "
            + "ha sido desarrollada en esta versión.");
    }

    @FXML
    private void clicEvaluarOV(ActionEvent event) {
        if (!EstudianteDAO.estaEnPeriodoActual
            (estudianteSesion.getMatricula())) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                    "Periodo incorrecto", 
                    "No estás asignado al periodo actual");
                return;
        }

        if (!new ExpedienteDAO().tieneExpedienteEnCurso
            (estudianteSesion.getMatricula())) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    "Sin expediente activo",
                    "Aún no tienes un expediente activo en este periodo, por "
                    + "lo tanto no se puede evaluar a una organización "
                    + "vinculada.");
                return;
        }
        
        if (new EstudianteDAO().tieneEvaluacionAOV
            (estudianteSesion.getMatricula())) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                    "No se puede volver a realizar la evaluación", 
                    "Ya realizaste la evaluación.");
                return;
        }

        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(lblNombreUsuario),
            "/sistemapracticasis/vista/FXMLEvaluarOrganizacionVinculada.fxml",
            FXMLEvaluarOrganizacionVinculadaController.class,
            "inicializarInformacion",
            estudianteSesion
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
