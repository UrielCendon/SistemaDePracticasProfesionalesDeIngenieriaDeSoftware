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

/** 
 * Autor: [Nombre del autor]
 * Fecha de creación: [Fecha de creación]
 * Descripción: Controlador principal para la vista del coordinador,
 * que permite acceder a todas las funcionalidades del sistema
 * como gestión de proyectos, responsables y organizaciones vinculadas.
 */
public class FXMLPrincipalCoordinadorController implements Initializable {

    /* Sección: Componentes de la interfaz
     * Contiene los elementos FXML de la vista principal.
     */
    @FXML private Button btnCerrarSesion;
    @FXML private Label lblNombreUsuario;

    /* Sección: Variables de instancia
     * Almacena los datos del coordinador en sesión.
     */
    private Coordinador coordinadorSesion;

    /**
     * Initializes the controller class.
     * @param url Ubicación utilizada para resolver rutas relativas.
     * @param rb Recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Sin implementación por ahora
    }

    /* Sección: Métodos públicos */
    /**
     * Inicializa la información del coordinador en sesión.
     * @param coordinadorSesion Objeto Coordinador con los datos de sesión.
     */
    public void inicializarInformacion(Coordinador coordinadorSesion) {
        this.coordinadorSesion = coordinadorSesion;
        cargarInformacionUsuario();
    }

    /**
     * Carga la información del usuario en los componentes de la interfaz.
     */
    private void cargarInformacionUsuario() {
        if (coordinadorSesion != null) {
            lblNombreUsuario.setText(coordinadorSesion.toString());
        }
    }

    /* Sección: Manejo de eventos - Navegación */
    /**
     * Maneja el evento para registrar un nuevo proyecto.
     * @param event Evento de acción generado.
     */
    @FXML
    private void clicRegistrarProyecto(ActionEvent event) {
        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(lblNombreUsuario),
            "/sistemapracticasis/vista/FXMLSeleccionarOVYResponsable.fxml",
            sistemapracticasis.controlador.FXMLSeleccionarOVYResponsableController.class,
            "inicializarInformacion",
            coordinadorSesion
        );
    }

    /**
     * Maneja el evento para registrar un nuevo responsable.
     * @param event Evento de acción generado.
     */
    @FXML
    private void clicRegistrarResponsable(ActionEvent event) {
        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(lblNombreUsuario),
            "/sistemapracticasis/vista/FXMLSeleccionarOrganizacionVinculada.fxml",
            sistemapracticasis.controlador.FXMLSeleccionarOrganizacionVinculadaController.class,
            "inicializarInformacion",
            coordinadorSesion
        );
    }

    /**
     * Maneja el evento para registrar una nueva organización vinculada.
     * @param event Evento de acción generado.
     */
    @FXML
    private void clicRegistrarOV(ActionEvent event) {
        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(lblNombreUsuario),
            "/sistemapracticasis/vista/FXMLRegistrarOrganizacionVinculada.fxml",
            sistemapracticasis.controlador.FXMLRegistrarOrganizacionVinculadaController.class,
            "inicializarInformacion",
            coordinadorSesion
        );
    }

    /**
     * Maneja el evento para actualizar un proyecto existente.
     * @param event Evento de acción generado.
     */
    @FXML
    private void clicActualizarProyecto(ActionEvent event) {
        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(lblNombreUsuario),
            "/sistemapracticasis/vista/FXMLProyecto.fxml",
            sistemapracticasis.controlador.FXMLProyectoController.class,
            "inicializarInformacion",
            coordinadorSesion
        );
    }

    /**
     * Maneja el evento para actualizar un responsable (no implementado).
     * @param event Evento de acción generado.
     */
    @FXML
    private void clicActualizarResponsable(ActionEvent event) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
            "Funcionalidad no disponible", "Esta sección del sistema aún no "
            + "ha sido desarrollada en esta versión.");
    }

    /**
     * Maneja el evento para asignar proyectos a estudiantes.
     * @param event Evento de acción generado.
     */
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

    /**
     * Maneja el evento para generar documentos de asignación.
     * @param event Evento de acción generado.
     */
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

    /**
     * Maneja el evento para programar entregas.
     * @param event Evento de acción generado.
     */
    @FXML
    private void clicProgramarEntregas(ActionEvent event) {
        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(lblNombreUsuario),
            "/sistemapracticasis/vista/FXMLProgramarEntrega.fxml",
            FXMLProgramarEntregaController.class,
            "inicializarInformacion",
            coordinadorSesion
        );
    }

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