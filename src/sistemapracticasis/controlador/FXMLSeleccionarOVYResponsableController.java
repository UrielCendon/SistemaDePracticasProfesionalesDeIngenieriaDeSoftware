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
import sistemapracticasis.modelo.dao.ResponsableProyectoDAO;
import sistemapracticasis.modelo.pojo.Coordinador;
import sistemapracticasis.modelo.pojo.OrganizacionVinculada;
import sistemapracticasis.modelo.pojo.ResponsableProyecto;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/**
 * Autor: Miguel Eduardo
 * Fecha de creación: 16/06/2025
 * Descripción: Controlador de la vista FXMLSeleccionarOVYResponsable,
 * que permite seleccionar una organización vinculada y su responsable
 * para asociarlos a un nuevo proyecto.
 */
public class FXMLSeleccionarOVYResponsableController implements Initializable {

    /* Sección: Componentes de la interfaz
     * Contiene los elementos FXML de la vista.
     */

    /** Botón para guardar la selección. */
    @FXML private Button btnGuardar;

    /** Botón para regresar a la pantalla anterior. */
    @FXML private Button btnRegresar;

    /** Lista desplegable con las organizaciones disponibles. */
    @FXML private ComboBox<OrganizacionVinculada> cbOrganizaciones;

    /** Lista desplegable con los responsables disponibles. */
    @FXML private ComboBox<ResponsableProyecto> cbResponsables;

    /** Etiqueta que muestra el nombre del usuario en sesión. */
    @FXML private Label lblNombreUsuario;

    /** Campo de texto para buscar organizaciones por nombre o palabra clave. */
    @FXML private TextField txtBuscar;

    /* Sección: Variables de instancia
     * Almacena los datos de la sesión y listas de entidades.
     */

    /** Coordinador autenticado en sesión. */
    private Coordinador coordinadorSesion;

    /** Lista observable de organizaciones disponibles. */
    private ObservableList<OrganizacionVinculada> listaOrganizaciones;

    /** Lista observable de responsables asociados a la organización seleccionada. */
    private ObservableList<ResponsableProyecto> listaResponsables;


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
                "Debe registrar al menos una organización para continuar.");
            btnGuardar.setDisable(true);
        }

        txtBuscar.textProperty().addListener((obs, oldVal, newVal) -> 
                filtrarOrganizaciones(newVal));

        cbOrganizaciones.setOnAction(event -> {
            OrganizacionVinculada seleccionada = cbOrganizaciones.getValue();
            if (seleccionada != null) {
                cargarResponsablesPorOrganizacion(seleccionada.getIdOrganizacionVinculada());
                if (listaResponsables.isEmpty()) {
                    Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Datos insuficientes", 
                        "No hay responsables registrados para la organización seleccionada.");
                    btnGuardar.setDisable(true);
                } else {
                    btnGuardar.setDisable(false);
                }
            }
        });
    }

    /**
     * Inicializa la sesión del coordinador.
     * @param coordinadorSesion Coordinador autenticado.
     */
    public void inicializarInformacion(Coordinador coordinadorSesion) {
        this.coordinadorSesion = coordinadorSesion;
        lblNombreUsuario.setText(coordinadorSesion.toString());
    }

    /* Sección: Carga de datos */

    /**
     * Carga todas las organizaciones disponibles.
     */
    private void cargarOrganizaciones() {
        listaOrganizaciones = FXCollections.observableArrayList(
            OrganizacionVinculadaDAO.obtenerOrganizaciones()
        );
        cbOrganizaciones.setItems(listaOrganizaciones);
    }

    /**
     * Filtra la lista de organizaciones con base en un texto de búsqueda.
     * @param filtro Texto ingresado para buscar
     */
    private void filtrarOrganizaciones(String filtro) {
        ObservableList<OrganizacionVinculada> fuente = listaOrganizaciones;

        if (filtro != null && !filtro.isEmpty()) {
            fuente = listaOrganizaciones.stream()
                .filter(org -> org.getRazonSocial()
                                  .toLowerCase()
                                  .contains(filtro.toLowerCase()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }

        cbOrganizaciones.getSelectionModel().clearSelection();

        cbOrganizaciones.setItems(fuente);

        listaResponsables.clear();   
        btnGuardar.setDisable(true); 
    }


    /**
     * Carga los responsables vinculados a una organización.
     * @param idOrganizacion ID de la organización seleccionada.
     */
    private void cargarResponsablesPorOrganizacion(int idOrganizacion) {
        listaResponsables = FXCollections.observableArrayList(
            ResponsableProyectoDAO.obtenerResponsablesPorIdOrganizacion(idOrganizacion)
        );
        cbResponsables.setItems(listaResponsables);
    }

    /* Sección: Manejo de eventos */

    /**
     * Maneja el clic en el botón Guardar, valida selección y redirige.
     */
    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        OrganizacionVinculada org = cbOrganizaciones.getValue();
        ResponsableProyecto responsable = cbResponsables.getValue();

        if (org == null || responsable == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                "Selección incompleta",
                "Por favor seleccione una organización y un responsable.");
            return;
        }

        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(btnGuardar),
            "/sistemapracticasis/vista/FXMLRegistrarProyecto.fxml",
            FXMLRegistrarProyectoController.class,
            "inicializarInformacion",
            coordinadorSesion,
            org,
            responsable
        );
    }

    /**
     * Maneja el clic en el botón Regresar y redirige a la pantalla principal.
     */
    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(btnRegresar),
            "/sistemapracticasis/vista/FXMLPrincipalCoordinador.fxml",
            FXMLPrincipalCoordinadorController.class,
            "inicializarInformacion",
            coordinadorSesion
        );
    }
}
