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

import sistemapracticasis.modelo.dao.OrganizacionVinculadaDAO;
import sistemapracticasis.modelo.pojo.Coordinador;
import sistemapracticasis.modelo.pojo.OrganizacionVinculada;
import sistemapracticasis.modelo.pojo.ResultadoOperacion;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/**
 * Autor: Miguel Edurdo
 * Fecha de creación: 15/06/2025
 * Descripción: Controlador para registrar una nueva organización vinculada
 * desde la vista FXMLRegistrarOrganizacionVinculada.
 */
public class FXMLRegistrarOrganizacionVinculadaController implements Initializable {

    /* Sección: Componentes de la interfaz
     * Contiene los elementos FXML de la vista.
     */

    /** Etiqueta con el nombre del usuario. */
    @FXML private Label lblNombreUsuario;

    /** Botón para guardar la organización. */
    @FXML private Button btnGuardar;

    /** Campo para ingresar el correo electrónico. */
    @FXML private TextField txtCorreo;

    /** Campo para ingresar la dirección. */
    @FXML private TextField txtDireccion;

    /** Campo para ingresar el teléfono. */
    @FXML private TextField txtTelefono;

    /** Campo para ingresar el nombre o razón social. */
    @FXML private TextField txtNombre;

    /** Botón para cancelar el registro. */
    @FXML private Button btnCancelar;

    /* Sección: Variables de instancia
     * Almacena los datos del coordinador en sesión.
     */

    /** Coordinador autenticado en sesión. */
    private Coordinador coordinadorSesion;


    /* Sección: Inicialización del controlador */

    /**
     * Inicializa el controlador después de que su elemento raíz haya sido procesado.
     * @param url URL de localización.
     * @param rb Recursos para internacionalización.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // No se necesita configurar validaciones aquí con el nuevo enfoque
    }

    /**
     * Inicializa la sesión con información del coordinador.
     * @param coordinadorSesion Coordinador autenticado.
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

    /* Sección: Manejo de eventos */

    /**
     * Maneja el clic sobre el botón guardar.
     * @param event Evento generado.
     */
    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        if (validarCampos()) {
            OrganizacionVinculada organizacion = construirOrganizacion();
            ResultadoOperacion resultado = 
                    OrganizacionVinculadaDAO.registrarOrganizacion(organizacion);

            if (!resultado.isError()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                        "RegistroExitoso",
                        resultado.getMensaje());

                Navegador.cambiarEscenaParametrizada(
                        Utilidad.getEscenarioComponente(btnGuardar),
                        "/sistemapracticasis/vista/FXMLPrincipalCoordinador.fxml",
                        sistemapracticasis.controlador.FXMLPrincipalCoordinadorController.class,
                        "inicializarInformacion",
                        coordinadorSesion);
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

    /**
     * Maneja el clic sobre el botón cancelar.
     * @param event Evento generado.
     */
    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion(
                "Cancelar registro",
                "¿Desea cancelar el registro de la organización vinculada?",
                "Los datos ingresados se perderán.")) {

            Navegador.cambiarEscenaParametrizada(
                    Utilidad.getEscenarioComponente(btnCancelar),
                    "/sistemapracticasis/vista/FXMLPrincipalCoordinador.fxml",
                    sistemapracticasis.controlador.FXMLPrincipalCoordinadorController.class,
                    "inicializarInformacion",
                    coordinadorSesion);
        }
    }

    /* Sección: Validaciones y construcción de datos */

    /**
     * Valida los campos del formulario.
     * @return true si todos los campos son válidos, false en caso contrario.
     */
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

    /**
     * Construye una instancia de OrganizacionVinculada a partir de los campos.
     * @return La organización construida.
     */
    private OrganizacionVinculada construirOrganizacion() {
        OrganizacionVinculada organizacion = new OrganizacionVinculada();
        organizacion.setRazonSocial(txtNombre.getText().trim());
        organizacion.setTelefono(txtTelefono.getText().trim());
        organizacion.setDireccion(txtDireccion.getText().trim());
        organizacion.setCorreo(txtCorreo.getText().trim());
        return organizacion;
    }

    /* Sección: Estilo visual de campos */

    /**
     * Aplica estilo de error al campo proporcionado.
     * @param campo Campo de texto a modificar.
     */
    private void marcarError(TextField campo) {
        campo.setStyle("-fx-border-color: red; -fx-border-width: 2;");
    }

    /**
     * Limpia los estilos de error en todos los campos.
     */
    private void limpiarEstilos() {
        txtNombre.setStyle("");
        txtTelefono.setStyle("");
        txtDireccion.setStyle("");
        txtCorreo.setStyle("");
    }
}
