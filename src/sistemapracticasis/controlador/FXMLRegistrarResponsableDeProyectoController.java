package sistemapracticasis.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sistemapracticasis.modelo.dao.ResponsableProyectoDAO;
import sistemapracticasis.modelo.pojo.Coordinador;
import sistemapracticasis.modelo.pojo.ResponsableProyecto;
import sistemapracticasis.modelo.pojo.ResultadoOperacion;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

public class FXMLRegistrarResponsableDeProyectoController implements Initializable {

    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtAPaterno;
    @FXML
    private TextField txtDepartamento;
    @FXML
    private TextField txtCorreo;
    @FXML
    private Button btnGuardar;
    @FXML
    private Label lblNombreUsuario;
    @FXML
    private TextField txtPuesto;
    @FXML
    private TextField txtAMaterno;
    @FXML
    private TextField txtTelefono;

    private Coordinador coordinadorSesion;
    private int idOrganizacionVinculada;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // no usado por ahora
    }

    public void inicializarInformacion(Coordinador coordinadorSesion, Integer idOrganizacionVinculada) {
        this.coordinadorSesion = coordinadorSesion;
        this.idOrganizacionVinculada = idOrganizacionVinculada;
        cargarInformacionUsuario();
    }

    private void cargarInformacionUsuario() {
        if (coordinadorSesion != null) {
            lblNombreUsuario.setText(coordinadorSesion.toString());
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion(
                "Cancelar registro",
                "¿Desea cancelar el registro del responsable del proyecto?",
                "Los datos ingresados se perderán.")) {

            Navegador.cambiarEscenaParametrizada(
                    Utilidad.getEscenarioComponente(btnCancelar),
                    "/sistemapracticasis/vista/FXMLPrincipalCoordinador.fxml",
                    sistemapracticasis.controlador.FXMLPrincipalCoordinadorController.class,
                    "inicializarInformacion",
                    coordinadorSesion
            );
        }
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        if (validarCampos()) {
            ResponsableProyecto responsable = construirResponsable();

            ResultadoOperacion resultado = ResponsableProyectoDAO.registrarResponsable(responsable);

            if (!resultado.isError()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                        "RegistroExitoso",
                        resultado.getMensaje());
                Navegador.cambiarEscenaParametrizada(
                        Utilidad.getEscenarioComponente(btnGuardar),
                        "/sistemapracticasis/vista/FXMLPrincipalCoordinador.fxml",
                        sistemapracticasis.controlador.FXMLPrincipalCoordinadorController.class,
                        "inicializarInformacion",
                        coordinadorSesion
                );
             
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                        "No hay conexión con la base de datos",
                        resultado.getMensaje());
            }
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    "DatosInválidos",
                    "Por favor verifique la información ingresada.");
        }
    }

    private ResponsableProyecto construirResponsable() {
        ResponsableProyecto responsable = new ResponsableProyecto();
        responsable.setNombre(txtNombre.getText().trim());
        responsable.setApellidoPaterno(txtAPaterno.getText().trim());
        responsable.setApellidoMaterno(txtAMaterno.getText().trim());
        responsable.setTelefono(txtTelefono.getText().trim());
        responsable.setCorreo(txtCorreo.getText().trim());
        responsable.setDepartamento(txtDepartamento.getText().trim());
        responsable.setPuesto(txtPuesto.getText().trim());
        responsable.setIdOrganizacionVinculada(idOrganizacionVinculada);
        return responsable;
    }

    private boolean validarCampos() {
        boolean valido = true;
        limpiarEstilos();

        if (!txtNombre.getText().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")) {
            marcarError(txtNombre); valido = false;
        }
        if (!txtAPaterno.getText().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")) {
            marcarError(txtAPaterno); valido = false;
        }
        if (!txtAMaterno.getText().isEmpty() &&
            !txtAMaterno.getText().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")) {
            marcarError(txtAMaterno); valido = false;
        }
        if (!txtDepartamento.getText().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")) {
            marcarError(txtDepartamento); valido = false;
        }
        if (!txtPuesto.getText().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")) {
            marcarError(txtPuesto); valido = false;
        }
        if (!txtTelefono.getText().trim().matches("\\d{10}")) {
            marcarError(txtTelefono); valido = false;
        }
        if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$", txtCorreo.getText().trim())) {
            marcarError(txtCorreo); valido = false;
        }

        return valido;
    }

    private void marcarError(TextField campo) {
        campo.setStyle("-fx-border-color: red; -fx-border-width: 2;");
    }

    private void limpiarEstilos() {
        txtNombre.setStyle("");
        txtAPaterno.setStyle("");
        txtAMaterno.setStyle("");
        txtTelefono.setStyle("");
        txtCorreo.setStyle("");
        txtDepartamento.setStyle("");
        txtPuesto.setStyle("");
    }

}


