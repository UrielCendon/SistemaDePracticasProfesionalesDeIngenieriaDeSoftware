package sistemapracticasis.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import sistemapracticasis.modelo.dao.PeriodoDAO;
import sistemapracticasis.modelo.pojo.Coordinador;
import sistemapracticasis.modelo.pojo.Periodo;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/**
 * Autor: Raziel Filobello
 * Fecha de creación: 15/06/2025
 * Descripción: Controlador para la programación de entregas académicas,
 * permite generar diferentes tipos de entregas (iniciales, reportes) 
 * asociadas al periodo escolar actual.
 */
public class FXMLProgramarEntregaController implements Initializable {

    /* Sección: Componentes de la interfaz */
    @FXML private Button btnCancelar;
    @FXML private Label lblNombreUsuario;
    @FXML private Button btnGenerarEntregaIniciales;
    @FXML private Button btnGenerarEntregaIntermedios;
    @FXML private Button btnGenerarEntregaFinales;
    @FXML private Button btnGenerarEntregaReportes;
    @FXML private Label lblExperienciaEducativa;
    @FXML private Label lblNRC;
    @FXML private Label lblPeriodoEscolar;

    /* Sección: Variables de instancia */
    private Coordinador coordinadorSesion;
    private Periodo periodoActual;

    /**
     * Initializes the controller class.
     * @param url Ubicación utilizada para resolver rutas relativas.
     * @param rb Recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización de componentes
    }

    /* Sección: Métodos públicos */
    /**
     * Inicializa la información del coordinador y carga datos del periodo.
     * @param coordinadorSesion Objeto Coordinador con datos de sesión.
     */
    public void inicializarInformacion(Coordinador coordinadorSesion) {
        this.coordinadorSesion = coordinadorSesion;
        cargarInformacionUsuario();
        cargarDatosPeriodo();
    }

    /* Sección: Carga de datos */
    private void cargarInformacionUsuario() {
        if (coordinadorSesion != null) {
            lblNombreUsuario.setText(coordinadorSesion.toString());
        }
    }

    /**
     * Carga y muestra los datos del periodo escolar actual.
     */
    private void cargarDatosPeriodo() {
        periodoActual = PeriodoDAO.obtenerPeriodoActual();
        if (periodoActual != null) {
            lblExperienciaEducativa.setText(periodoActual.getNombreEE());
            lblNRC.setText(periodoActual.getNrc());
            lblPeriodoEscolar.setText(periodoActual.getNombrePeriodo());
        } else {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.WARNING, 
                "Periodo no encontrado", 
                "No hay un periodo escolar vigente actualmente."
            );
        }
    }

    /* Sección: Manejo de eventos */
    @FXML
    private void clicCancelar(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion(
            "Cancelar", 
            "¿Está seguro?", 
            "Se perderán los cambios."
        )) {
            Navegador.cambiarEscenaParametrizada(
                Utilidad.getEscenarioComponente(btnCancelar),
                "/sistemapracticasis/vista/FXMLPrincipalCoordinador.fxml",
                FXMLPrincipalCoordinadorController.class,
                "inicializarInformacion",
                coordinadorSesion
            );
        }
    }

    @FXML
    private void clicGenerarEntregaIniciales(ActionEvent event) {
        if (validarPeriodoVigente()) {
            Navegador.abrirVentanaModalParametrizada(
                Utilidad.getEscenarioComponente(btnGenerarEntregaIniciales),
                "/sistemapracticasis/vista/FXMLGenerarEntregaIniciales.fxml",
                FXMLGenerarEntregaInicialesController.class,
                "inicializarInformacion",
                periodoActual.getFechaInicio(),
                periodoActual.getFechaFin(),
                periodoActual.getIdExpediente()
            );
        }
    }

    @FXML
    private void clicGenerarEntregaIntermedios(ActionEvent event) {
        Utilidad.mostrarAlertaSimple(
            Alert.AlertType.INFORMATION,
            "Funcionalidad no disponible",
            "Esta sección del sistema no será desarrollada en este sistema."
        );
    }

    @FXML
    private void clicGenerarEntregaFinales(ActionEvent event) {
        Utilidad.mostrarAlertaSimple(
            Alert.AlertType.INFORMATION,
            "Funcionalidad no disponible",
            "Esta sección del sistema no será desarrollada en este sistema."
        );
    }

    @FXML
    private void clicGenerarEntregaReportes(ActionEvent event) {
        if (validarPeriodoVigente()) {
            Navegador.abrirVentanaModalParametrizada(
                Utilidad.getEscenarioComponente(btnGenerarEntregaReportes),
                "/sistemapracticasis/vista/FXMLGenerarEntregaReportes.fxml",
                FXMLGenerarEntregaReportesController.class,
                "inicializarInformacion",
                periodoActual.getFechaInicio(),
                periodoActual.getFechaFin(),
                periodoActual.getIdExpediente()
            );
        }
    }

    /* Sección: Métodos de validación */
    /**
     * Valida que exista un periodo escolar vigente.
     * @return true si hay periodo vigente, false en caso contrario.
     */
    private boolean validarPeriodoVigente() {
        if (periodoActual == null) {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.WARNING, 
                "Error", 
                "No hay periodo escolar vigente cargado."
            );
            return false;
        }
        return true;
    }
}