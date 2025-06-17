package sistemapracticasis.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import sistemapracticasis.modelo.dao.OrganizacionVinculadaDAO;
import sistemapracticasis.modelo.pojo.Coordinador;
import sistemapracticasis.modelo.pojo.OrganizacionVinculada;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/**
 * Autor: Miguel Escobar
 * Fecha de creación: 15/06/2025
 * Descripción: Controlador de la vista FXMLSeleccionarOrganizacionVinculada,
 * permite al coordinador seleccionar una organización para asignarle un responsable.
 */
public class FXMLSeleccionarOrganizacionVinculadaController implements Initializable {

    /* Sección: Componentes de la interfaz
     * Contiene los elementos FXML de la vista.
     */

    /** Campo de texto para buscar organizaciones por nombre o palabra clave. */
    @FXML private TextField txtBuscar;

    /** Etiqueta que muestra el nombre del coordinador en sesión. */
    @FXML private Label lblNombreUsuario;

    /** Botón para regresar al menú principal. */
    @FXML private Button btnRegresar;

    /** Botón para continuar con la organización seleccionada. */
    @FXML private Button btnGuardar;

    /** Lista desplegable con las organizaciones vinculadas. */
    @FXML private ComboBox<OrganizacionVinculada> cbOrganizacionVinculada;

    /* Sección: Variables de instancia
     * Almacena los datos del coordinador y la lista de organizaciones.
     */

    /** Coordinador autenticado en sesión. */
    private Coordinador coordinadorSesion;

    /** Lista observable de organizaciones disponibles. */
    private ObservableList<OrganizacionVinculada> listaOrganizaciones;


    /* Sección: Inicialización del controlador */

    /**
     * Inicializa el controlador después de que su elemento raíz haya sido procesado.
     * @param url URL de localización.
     * @param rb Recursos para internacionalización.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarOrganizaciones();
        if (listaOrganizaciones.isEmpty()) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Datos insuficientes", 
                "Debe registrar al menos una organización vinculada para continuar.");
            btnGuardar.setDisable(true);
        }

        txtBuscar.textProperty().addListener((obs, oldVal, newVal) -> 
                filtrarOrganizaciones(newVal));
    }

    /**
     * Inicializa la información de sesión.
     * @param coordinadorSesion Coordinador en sesión
     */
    public void inicializarInformacion(Coordinador coordinadorSesion) {
        this.coordinadorSesion = coordinadorSesion;
        cargarInformacionUsuario();
    }

    /**
     * Carga el nombre del coordinador en la interfaz.
     */
    private void cargarInformacionUsuario() {
        if (coordinadorSesion != null) {
            lblNombreUsuario.setText(coordinadorSesion.toString());
        }
    }

    /**
     * Permite acceder externamente a la etiqueta del nombre del usuario.
     * @return Etiqueta del nombre del usuario
     */
    public Label getLblNombreUsuario() {
        return lblNombreUsuario;
    }

    /* Sección: Carga y filtro de datos */

    /**
     * Carga todas las organizaciones disponibles en el ComboBox.
     */
    private void cargarOrganizaciones() {
        listaOrganizaciones = FXCollections.observableArrayList
        (OrganizacionVinculadaDAO.obtenerOrganizaciones());
        cbOrganizacionVinculada.setItems(listaOrganizaciones);
    }

    /**
     * Filtra las organizaciones según el texto ingresado.
     * @param filtro Texto usado para filtrar
     */
    private void filtrarOrganizaciones(String filtro) {
        if (filtro == null || filtro.trim().isEmpty()) {
            cbOrganizacionVinculada.setItems(listaOrganizaciones);
            return;
        }

        ObservableList<OrganizacionVinculada> filtradas = listaOrganizaciones.stream()
            .filter(org -> org.getRazonSocial().toLowerCase().contains(filtro.toLowerCase()))
            .collect(Collectors.toCollection(FXCollections::observableArrayList));

        cbOrganizacionVinculada.setItems(filtradas);
    }

    /* Sección: Manejo de eventos */

    /**
     * Maneja el clic del botón Guardar. Redirige a registrar responsable si hay selección válida.
     */
    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        OrganizacionVinculada seleccionada = cbOrganizacionVinculada.getValue();

        if (seleccionada != null) {
            Navegador.cambiarEscenaParametrizada(
                Utilidad.getEscenarioComponente(btnGuardar),
                "/sistemapracticasis/vista/FXMLRegistrarResponsableDeProyecto.fxml",
                sistemapracticasis.controlador.FXMLRegistrarResponsableDeProyectoController.class,
                "inicializarInformacion",
                coordinadorSesion,
                seleccionada.getIdOrganizacionVinculada()
            );
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                "Datos inválidos", "Debe seleccionar una organización vinculada.");
        }
    }

    /**
     * Maneja el clic del botón Regresar. Retorna a la vista principal del coordinador.
     */
    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(lblNombreUsuario),
            "/sistemapracticasis/vista/FXMLPrincipalCoordinador.fxml",
            FXMLPrincipalCoordinadorController.class,
            "inicializarInformacion",
            coordinadorSesion
        );
    }
}
