package sistemapracticasis.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import sistemapracticasis.modelo.pojo.Profesor;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/**
 * Autor: Uriel Cendon
 * Fecha de creación: 15/06/2025
 * Descripción: Controlador para la vista principal del profesor,
 * permite validar entregas de estudiantes y consultar expedientes.
 */
public class FXMLPrincipalProfesorController implements Initializable {

    /* Sección: Componentes de la interfaz
     * Contiene los elementos FXML de la vista principal del profesor.
     */

    /** Botón para cerrar la sesión del profesor. */
    @FXML private Button btnCerrarSesion;

    /** Etiqueta que muestra el nombre del profesor en sesión. */
    @FXML private Label lblNombreUsuario;

    /* Sección: Variables de instancia
     * Almacena los datos del profesor en sesión.
     */

    /** Profesor actualmente autenticado en la aplicación. */
    private Profesor profesorSesion;


    /**
     * Inicializa el controlador después de que su elemento raíz haya sido procesado.
     * @param url Ubicación utilizada para resolver rutas relativas.
     * @param rb Recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización si es necesaria
    }

    /* Sección: Métodos públicos */
    /**
     * Inicializa la información del profesor en sesión.
     * @param profesorSesion Objeto Profesor con los datos de sesión.
     */
    public void inicializarInformacion(Profesor profesorSesion) {
        this.profesorSesion = profesorSesion;
        cargarInformacionUsuario();
    }

    /**
     * Carga la información del usuario en los componentes de la interfaz.
     */
    private void cargarInformacionUsuario() {
        if (profesorSesion != null) {
            lblNombreUsuario.setText(profesorSesion.toString());
        }
    }

    /* Sección: Manejo de eventos - Funcionalidades */
    /**
     * Maneja el evento para validar entregas de estudiantes.
     * @param event Evento de acción generado.
     */
    @FXML
    private void clicValidarEntregas(ActionEvent event) {
        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(lblNombreUsuario),
            "/sistemapracticasis/vista/FXMLValidarEntrega.fxml",
            FXMLValidarEntregaController.class,
            "inicializarInformacion",
            profesorSesion
        );
    }

    /**
     * Maneja el evento para consultar expedientes (no implementado).
     * @param event Evento de acción generado.
     */
    @FXML
    private void clicConsultarExpediente(ActionEvent event) {
        Utilidad.mostrarAlertaSimple(
            Alert.AlertType.INFORMATION, 
            "Funcionalidad no disponible", 
            "Esta sección del sistema aún no ha sido desarrollada en esta versión."
        );
    }

    /* Sección: Manejo de sesión */
    /**
     * Maneja el evento para cerrar la sesión actual.
     * @param event Evento de acción generado.
     */
    @FXML
    private void ClicCerrarSesion(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion(
            "Confirmar cierre de sesión", 
            "¿Está seguro(a) de querer cerrar la sesión actual?", 
            "Se perderá el acceso a esta sesión."
        )) {
            Navegador.cerrarSesion(
                Utilidad.getEscenarioComponente(lblNombreUsuario)
            );
        }
    }
}