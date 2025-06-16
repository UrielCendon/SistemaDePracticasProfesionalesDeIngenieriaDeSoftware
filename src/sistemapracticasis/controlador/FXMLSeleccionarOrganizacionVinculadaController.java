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

public class FXMLSeleccionarOrganizacionVinculadaController implements Initializable {

    @FXML
    private TextField txtBuscar;
    @FXML
    private Label lblNombreUsuario;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnGuardar;
    @FXML
    private ComboBox<OrganizacionVinculada> cbOrganizacionVinculada;

    private Coordinador coordinadorSesion;
    private ObservableList<OrganizacionVinculada> listaOrganizaciones;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarOrganizaciones();
        if (listaOrganizaciones.isEmpty()) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Datos insuficientes", 
                "Debe registrar al menos una organización vinculada para continuar.");
            btnGuardar.setDisable(true);
        }

        txtBuscar.textProperty().addListener((obs, oldVal, newVal) -> filtrarOrganizaciones(newVal));
    }

    public void inicializarInformacion(Coordinador coordinadorSesion) {
        this.coordinadorSesion = coordinadorSesion;
        cargarInformacionUsuario();
    }

    private void cargarInformacionUsuario() {
        if (coordinadorSesion != null) {
            lblNombreUsuario.setText(coordinadorSesion.toString());
        }
    }

    public Label getLblNombreUsuario() {
        return lblNombreUsuario;
    }

    private void cargarOrganizaciones() {
        listaOrganizaciones = FXCollections.observableArrayList(OrganizacionVinculadaDAO.obtenerOrganizaciones());
        cbOrganizacionVinculada.setItems(listaOrganizaciones);
    }

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
