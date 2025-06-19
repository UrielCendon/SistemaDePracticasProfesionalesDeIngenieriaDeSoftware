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
import sistemapracticasis.modelo.dao.PeriodoDAO;
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.modelo.pojo.Periodo;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/** 
 * Autor: Uriel Cendon
 * Fecha de creación: 15/06/2025
 * Descripción: Controlador para la vista principal del estudiante,
 * maneja las funcionalidades disponibles para los estudiantes como
 * entrega de documentos, evaluación de organizaciones y consulta de avances.
 */
public class FXMLPrincipalEstudianteController implements Initializable {

    /* Sección: Componentes de la interfaz
     * Contiene los elementos FXML de la vista principal del estudiante.
     */

    /** Botón para cerrar la sesión del estudiante. */
    @FXML private Button btnCerrarSesion;

    /** Etiqueta que muestra el nombre del estudiante en sesión. */
    @FXML private Label lblNombreUsuario;

    /* Sección: Variables de instancia
     * Almacena los datos del estudiante en sesión.
     */

    /** Estudiante actualmente autenticado en la aplicación. */
    private Estudiante estudianteSesion;


    /**
     * Inicializa el controlador después de que su elemento raíz haya sido procesado.
     * @param url Ubicación utilizada para resolver rutas relativas.
     * @param rb Recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Sin implementación por ahora
    }

    /* Sección: Métodos públicos */
    /**
     * Inicializa la información del estudiante en sesión.
     * @param estudianteSesion Objeto Estudiante con los datos de sesión.
     */
    public void inicializarInformacion(Estudiante estudianteSesion) {
        this.estudianteSesion = estudianteSesion;
        cargarInformacionUsuario();
    }

    /**
     * Carga la información del usuario en los componentes de la interfaz.
     */
    private void cargarInformacionUsuario() {
        if (estudianteSesion != null) {
            lblNombreUsuario.setText(estudianteSesion.toString());
        }
    }

    /* Sección: Manejo de documentos */
    /**
     * Maneja el evento para la entrega de documentos iniciales.
     * @param event Evento de acción generado.
     */
    @FXML
    private void clicDocIniciales(ActionEvent event) {
        int idEstudiante = estudianteSesion.getIdEstudiante();

        if (!EstudianteDAO.estaEnPeriodoActual(estudianteSesion.getMatricula())) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                "Periodo incorrecto", 
                "No estás asignado al periodo actual");
            return;
        }

        if (!new ExpedienteDAO().tieneExpedienteEnCurso(estudianteSesion.getMatricula())) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                "Sin expediente activo",
                "Aún no tienes un expediente activo en este periodo, por lo tanto "
                + "no se puede actualizar un documento inicial.");
            return;
        }

        Periodo periodoActual = PeriodoDAO.obtenerPeriodoActualPorEstudiante(idEstudiante);
        if (periodoActual != null) {
            int idPeriodo = periodoActual.getIdPeriodo();
            if (!EntregaDocumentoDAO.existenEntregasInicialesParaPeriodo(idPeriodo)) {
                Utilidad.mostrarAlertaSimple(
                    Alert.AlertType.WARNING,
                    "No hay Documentos Iniciales Programados",
                    "No se han programado Documentos Iniciales para este estudiante "
                    + "en el periodo actual."
                );
                return;
            }
        }

        if (!EntregaDocumentoDAO.existeEntregaInicialVigente(idEstudiante)) {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.WARNING,
                "Fecha inválida",
                "La fecha de entrega ha concluido."
            );
            return;
        }

        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(lblNombreUsuario),
            "/sistemapracticasis/vista/FXMLActualizarExpedienteDocumentoInicial.fxml",
            FXMLActualizarExpedienteDocumentoInicialController.class,
            "inicializarInformacion",
            estudianteSesion
        );
    }

    /**
     * Maneja el evento para la entrega de documentos intermedios 
     * (no implementado).
     * @param event Evento de acción generado.
     */
    @FXML
    private void clicDocIntermedios(ActionEvent event) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
            "Funcionalidad no disponible", 
            "Esta sección del sistema aún no ha sido desarrollada en esta "
            + "versión.");
    }

    /**
     * Maneja el evento para la entrega de documentos finales (no implementado).
     * @param event Evento de acción generado.
     */
    @FXML
    private void clicDocFinales(ActionEvent event) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
            "Funcionalidad no disponible", 
            "Esta sección del sistema aún no ha sido desarrollada en esta "
            + "versión.");
    }

    /**
     * Maneja el evento para la entrega de reportes (no implementado).
     * @param event Evento de acción generado.
     */
    @FXML
    private void clicDocReportes(ActionEvent event) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
            "Funcionalidad no disponible", 
            "Esta sección del sistema aún no ha sido desarrollada en esta "
            + "versión.");
    }

    /* Sección: Evaluación y consultas */
    /**
     * Maneja el evento para generar formato de evaluación (no implementado).
     * @param event Evento de acción generado.
     */
    @FXML
    private void clicGenerarFormatoEvaluacion(ActionEvent event) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
            "Funcionalidad no disponible", 
            "Esta sección del sistema aún no ha sido desarrollada en esta "
            + "versión.");
    }

    /**
     * Maneja el evento para consultar avances (no implementado).
     * @param event Evento de acción generado.
     */
    @FXML
    private void clicConsultarAvance(ActionEvent event) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
            "Funcionalidad no disponible", 
            "Esta sección del sistema aún no ha sido desarrollada en esta "
            + "versión.");
    }

    /**
     * Maneja el evento para evaluar la organización vinculada.
     * @param event Evento de acción generado.
     */
    @FXML
    private void clicEvaluarOV(ActionEvent event) {
        if (!EstudianteDAO.estaEnPeriodoActual(estudianteSesion.
                getMatricula())) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                "Periodo incorrecto", 
                "No estás asignado al periodo actual");
            return;
        }

        if (!new ExpedienteDAO().tieneExpedienteEnCurso(estudianteSesion.
                getMatricula())) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                "Sin expediente activo",
                "Aún no tienes un expediente activo en este periodo, por lo "
                + "tanto no se puede evaluar a una organización vinculada.");
            return;
        }
        
        if (new EstudianteDAO().tieneEvaluacionAOV(estudianteSesion.getMatricula())) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                "No se puede volver a realizar la evaluación", 
                "Ya realizaste la evaluación.");
            return;
        }
        
        int horasAcumuladas = ExpedienteDAO.obtenerHorasAcumuladasPorIdEstudiante
            (estudianteSesion.getIdEstudiante());
        if(horasAcumuladas != 420){
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                "Aún no se puede realizar la evaluación a la organización vinculada.",
                "No se puede realizar la evaluación porque todavía no cuentas con 420 horas "
                + "acumuladas en el momento.\nEl número de horas acumuladas actual es: " 
                + horasAcumuladas + ".");
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

    /* Sección: Manejo de sesión */
    /**
     * Maneja el evento para cerrar la sesión actual.
     * @param event Evento de acción generado.
     */
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