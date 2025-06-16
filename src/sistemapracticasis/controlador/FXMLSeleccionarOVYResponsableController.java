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

public class FXMLSeleccionarOVYResponsableController implements Initializable {

    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnRegresar;
    @FXML
    private ComboBox<OrganizacionVinculada> cbOrganizaciones;
    @FXML
    private ComboBox<ResponsableProyecto> cbResponsables;
    @FXML
    private Label lblNombreUsuario;
    @FXML
    private TextField txtBuscar;

    private Coordinador coordinadorSesion;
    private ObservableList<OrganizacionVinculada> listaOrganizaciones;
    private ObservableList<ResponsableProyecto> listaResponsables;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarOrganizaciones();

        if (listaOrganizaciones.isEmpty()) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Datos insuficientes", 
                "Debe registrar al menos una organización para continuar.");
            btnGuardar.setDisable(true);
        }

        txtBuscar.textProperty().addListener((obs, oldVal, newVal) -> filtrarOrganizaciones(newVal));

        cbOrganizaciones.setOnAction(event -> {
            OrganizacionVinculada seleccionada = cbOrganizaciones.getValue();
            if (seleccionada != null) {
                cargarResponsablesPorOrganizacion(seleccionada.getIdOrganizacionVinculada());
                // Validar aquí si hay responsables para la organización seleccionada
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

    public void inicializarInformacion(Coordinador coordinadorSesion) {
        this.coordinadorSesion = coordinadorSesion;
        lblNombreUsuario.setText(coordinadorSesion.toString());
    }

    private void cargarOrganizaciones() {
        listaOrganizaciones = FXCollections.observableArrayList(
            OrganizacionVinculadaDAO.obtenerOrganizaciones()
        );
        cbOrganizaciones.setItems(listaOrganizaciones);
    }

    private void filtrarOrganizaciones(String filtro) {
        if (filtro == null || filtro.trim().isEmpty()) {
            cbOrganizaciones.setItems(listaOrganizaciones);
            return;
        }

        ObservableList<OrganizacionVinculada> filtradas = listaOrganizaciones.stream()
            .filter(org -> org.getRazonSocial().toLowerCase().contains(filtro.toLowerCase()))
            .collect(Collectors.toCollection(FXCollections::observableArrayList));

        cbOrganizaciones.setItems(filtradas);
    }

    private void cargarResponsablesPorOrganizacion(int idOrganizacion) {
        listaResponsables = FXCollections.observableArrayList(
            ResponsableProyectoDAO.obtenerResponsablesPorIdOrganizacion(idOrganizacion)
        );
        cbResponsables.setItems(listaResponsables);
    }

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
