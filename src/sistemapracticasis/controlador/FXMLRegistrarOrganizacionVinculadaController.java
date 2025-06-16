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
import javafx.stage.Stage;
import sistemapracticasis.modelo.dao.OrganizacionVinculadaDAO;
import sistemapracticasis.modelo.pojo.Coordinador;
import sistemapracticasis.modelo.pojo.OrganizacionVinculada;
import sistemapracticasis.modelo.pojo.ResultadoOperacion;
import sistemapracticasis.util.Utilidad;

public class FXMLRegistrarOrganizacionVinculadaController implements Initializable {

    @FXML
    private Label lblNombreUsuario;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtNombre;
    @FXML
    private Button btnCancelar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // No se necesita configurar validaciones aquí con el nuevo enfoque
    }

    
    public void inicializarInformacion(Coordinador coordinador) {
        if (coordinador != null) {
            lblNombreUsuario.setText(coordinador.toString());
        }
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        if (validarCampos()) {
            OrganizacionVinculada organizacion = new OrganizacionVinculada();
            organizacion.setRazonSocial(txtNombre.getText().trim());
            organizacion.setTelefono(txtTelefono.getText().trim());
            organizacion.setDireccion(txtDireccion.getText().trim());
            organizacion.setCorreo(txtCorreo.getText().trim());

            ResultadoOperacion resultado = OrganizacionVinculadaDAO.registrarOrganizacion(organizacion);

            if (!resultado.isError()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                        "RegistroExitoso",
                        "Organización registrada con éxito");
                limpiarCampos();
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                        "Error",
                        resultado.getMensaje());
            }
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    "DatosInválidos",
                    "Por favor verifique la información ingresada.");
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        boolean confirmar = Utilidad.mostrarConfirmacion(
                "Cancelar",
                "¿Desea cancelar el registro de la organización vinculada?",
                "Los datos ingresados se perderán");

        if (confirmar) {
            Stage escenario = Utilidad.getEscenarioComponente(btnCancelar);
            escenario.close();
        }
    }

    private boolean validarCampos() {
        boolean valido = true;
        limpiarEstilos();

        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (nombre.isEmpty() || nombre.length() < 5) {
            marcarError(txtNombre);
            valido = false;
        } else if (OrganizacionVinculadaDAO.existeOrganizacionConNombre(nombre)) {
            marcarError(txtNombre);
            valido = false;
        }

        if (!telefono.matches("\\d{10}")) {
            marcarError(txtTelefono);
            valido = false;
        }

        if (direccion.isEmpty() || direccion.length() < 10) {
            marcarError(txtDireccion);
            valido = false;
        }

        if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$", correo)) {
            marcarError(txtCorreo);
            valido = false;
        }

        return valido;
    }

    private void marcarError(TextField campo) {
        campo.setStyle("-fx-border-color: red; -fx-border-width: 2;");
    }

    private void limpiarEstilos() {
        txtNombre.setStyle("");
        txtTelefono.setStyle("");
        txtDireccion.setStyle("");
        txtCorreo.setStyle("");
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        txtCorreo.clear();
        limpiarEstilos();
    }

    public Label getLblNombreUsuario() {
        return lblNombreUsuario;
    }
}
