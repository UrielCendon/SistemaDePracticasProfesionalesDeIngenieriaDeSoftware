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

/**
 * Autor: Miguel Escobar
 * Fecha de creación: 15/06/2025
 * Descripción: Controlador de la vista FXMLRegistrarResponsableDeProyecto,
 * permite registrar nuevos responsables vinculados a una organización.
 */
public class FXMLRegistrarResponsableDeProyectoController implements Initializable {

    /* Sección: Componentes de la interfaz
     * Contiene los elementos FXML de la vista.
     */

    /** Botón para cancelar la operación y cerrar la ventana. */
    @FXML private Button btnCancelar;

    /** Campo para ingresar el nombre del responsable. */
    @FXML private TextField txtNombre;

    /** Campo para ingresar el apellido paterno. */
    @FXML private TextField txtAPaterno;

    /** Campo para ingresar el departamento del responsable. */
    @FXML private TextField txtDepartamento;

    /** Campo para ingresar el correo electrónico. */
    @FXML private TextField txtCorreo;

    /** Botón para guardar la información del responsable. */
    @FXML private Button btnGuardar;

    /** Etiqueta que muestra el nombre del usuario en sesión. */
    @FXML private Label lblNombreUsuario;

    /** Campo para ingresar el puesto del responsable. */
    @FXML private TextField txtPuesto;

    /** Campo para ingresar el apellido materno. */
    @FXML private TextField txtAMaterno;

    /** Campo para ingresar el teléfono de contacto. */
    @FXML private TextField txtTelefono;
    
    /** Botón para regresar a la seleccion de responsable. */
    @FXML private Button btnRegresar;

    /* Sección: Variables de instancia
     * Almacena los datos de la sesión y la organización vinculada.
     */

    /** Coordinador autenticado en sesión. */
    private Coordinador coordinadorSesion;

    /** Identificador de la organización vinculada. */
    private int idOrganizacionVinculada;



    /* Sección: Inicialización del controlador */

    /**
     * Inicializa el controlador después de que su elemento raíz haya sido procesado.
     * @param url URL de localización.
     * @param rb Recursos para internacionalización.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Inicializa los datos del controlador.
     * @param coordinadorSesion Coordinador en sesión
     * @param idOrganizacionVinculada ID de la organización a la que se vincula el responsable
     */
    public void inicializarInformacion(Coordinador coordinadorSesion, Integer idOrganizacionVinculada) {
        this.coordinadorSesion = coordinadorSesion;
        this.idOrganizacionVinculada = idOrganizacionVinculada;
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

    /* Sección: Eventos */

    /**
     * Maneja el clic del botón Cancelar.
     * @param event Evento generado al hacer clic.
     */
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
                    coordinadorSesion);
        }
    }

    /**
     * Maneja el clic del botón Guardar.
     * @param event Evento generado al hacer clic.
     */
    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        if (validarCampos()) {
            ResponsableProyecto responsable = construirResponsable();
            ResultadoOperacion resultado = ResponsableProyectoDAO.registrarResponsable(responsable);

            if (!resultado.isError()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                        "Registro exitoso",
                        resultado.getMensaje());
                Navegador.cambiarEscenaParametrizada(
                        Utilidad.getEscenarioComponente(btnGuardar),
                        "/sistemapracticasis/vista/FXMLPrincipalCoordinador.fxml",
                        sistemapracticasis.controlador.FXMLPrincipalCoordinadorController.class,
                        "inicializarInformacion",
                        coordinadorSesion);
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                        "Error de conexión",
                        resultado.getMensaje());
            }
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    "Datos inválidos",
                    "Por favor verifique la información ingresada.");
        }
    }
    
    /**
     * Maneja el clic del botón Regresar. Retorna a la vista principal del coordinador.
     */
    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(lblNombreUsuario),
            "/sistemapracticasis/vista/FXMLSeleccionarOrganizacionVinculada.fxml",
            FXMLSeleccionarOrganizacionVinculadaController.class,
            "inicializarInformacion",
            coordinadorSesion
        );
    }

    /* Sección: Validación y construcción */

    /**
     * Construye un objeto ResponsableProyecto con los datos del formulario.
     * @return ResponsableProyecto construido.
     */
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

    /**
     * Valida los campos del formulario.
     * @return true si los campos son válidos, false en caso contrario.
     */
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

    /* Sección: Estilo visual */

    /**
     * Marca un campo de texto con estilo de error.
     * @param campo Campo de texto a marcar.
     */
    private void marcarError(TextField campo) {
        campo.setStyle("-fx-border-color: red; -fx-border-width: 2;");
    }

    /**
     * Limpia el estilo de error de todos los campos.
     */
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
