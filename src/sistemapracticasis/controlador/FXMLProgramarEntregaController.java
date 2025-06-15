/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
 * FXML Controller class
 *
 * @author Kaiser
 */
public class FXMLProgramarEntregaController implements Initializable {

    @FXML
    private Button btnCancelar;
    @FXML
    private Label lblNombreUsuario;
    @FXML
    private Button btnGenerarEntregaIniciales;
    @FXML
    private Button btnGenerarEntregaIntermedios;
    @FXML
    private Button btnGenerarEntregaFinales;
    @FXML
    private Button btnGenerarEntregaReportes;
    @FXML
    private Label lblExperienciaEducativa;
    @FXML
    private Label lblNRC;
    @FXML
    private Label lblPeriodoEscolar;
    
    private Coordinador coordinadorSesion;
    
    private Periodo periodoActual;

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
        cargarDatosPeriodo();
    }
    
    private void cargarInformacionUsuario() {
        if (coordinadorSesion != null) {
            lblNombreUsuario.setText(coordinadorSesion.toString());
        }
    }
    
    private void cargarDatosPeriodo() {
    periodoActual = PeriodoDAO.obtenerPeriodoActual();
    if (periodoActual != null) {
        lblExperienciaEducativa.setText(periodoActual.getNombreEE());
        lblNRC.setText(periodoActual.getNrc());
        lblPeriodoEscolar.setText(periodoActual.getNombrePeriodo());
    } else {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
            "Periodo no encontrado", 
            "No hay un periodo escolar vigente actualmente.");
    }
}

    @FXML
    private void clicCancelar(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion("Cancelar", "¿Está seguro?", "Se perderán los cambios.")) {
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
        if (periodoActual != null) {
            Navegador.abrirVentanaModalParametrizada(
                Utilidad.getEscenarioComponente(btnGenerarEntregaIniciales),
                "/sistemapracticasis/vista/FXMLGenerarEntregaIniciales.fxml",
                FXMLGenerarEntregaInicialesController.class,
                "inicializarInformacion",
                periodoActual.getFechaInicio(),
                periodoActual.getFechaFin(),
                periodoActual.getIdExpediente()
            );
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                "Error", 
                "No hay periodo escolar vigente cargado.");
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
        Utilidad.mostrarAlertaSimple(
            Alert.AlertType.INFORMATION,
            "Funcionalidad no disponible",
            "Esta sección del sistema aún no ha sido desarrollada en esta versión."
        );
    }
    
}
