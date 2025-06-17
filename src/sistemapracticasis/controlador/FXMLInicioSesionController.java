package sistemapracticasis.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import sistemapracticasis.modelo.dao.CoordinadorDAO;
import sistemapracticasis.modelo.dao.EstudianteDAO;
import sistemapracticasis.modelo.dao.EvaluadorDAO;
import sistemapracticasis.modelo.dao.InicioSesionDAO;
import sistemapracticasis.modelo.dao.ProfesorDAO;
import sistemapracticasis.modelo.pojo.Coordinador;
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.modelo.pojo.Evaluador;
import sistemapracticasis.modelo.pojo.Profesor;
import sistemapracticasis.modelo.pojo.Usuario;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/** 
 * Autor: [Nombre del autor]
 * Fecha de creación: [Fecha de creación]
 * Descripción: Controlador para la vista de inicio de sesión,
 * maneja la autenticación de usuarios y redirección a las pantallas
 * principales según el tipo de usuario (estudiante, profesor, evaluador, coordinador).
 */
public class FXMLInicioSesionController implements Initializable {

    /* Sección: Componentes de la interfaz
     * Contiene los elementos FXML de la vista de login.
     */
    @FXML private PasswordField pfPassword;
    @FXML private Button btnMostrarContraseña;
    @FXML private TextField txtUsuario;
    @FXML private Label lblErrorUsuario;
    @FXML private Label lblErrorContraseña;
    @FXML private TextField txtPasswordVisible;

    /**
     * Initializes the controller class.
     * @param url Ubicación utilizada para resolver rutas relativas.
     * @param rb Recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarListeners();
    }

    /* Sección: Manejo de credenciales */
    /**
     * Maneja el evento de verificación de credenciales al hacer clic en el botón.
     * @param event Evento de acción generado.
     */
    @FXML
    private void btnClicVerificarSesion(ActionEvent event) {
        String usuario = txtUsuario.getText();
        String contrasena = obtenerContrasenaActual();
        
        if (validarCampos(usuario, contrasena)) {
            validarCredenciales(usuario, contrasena);
        }
    }

    /**
     * Valida que los campos de usuario y contraseña no estén vacíos.
     * @param usuario Nombre de usuario ingresado.
     * @param contrasena Contraseña ingresada.
     * @return true si los campos son válidos, false en caso contrario.
     */
    private boolean validarCampos(String usuario, String contrasena) {
        lblErrorUsuario.setText("");
        lblErrorContraseña.setText("");
        boolean camposValidos = true;
        
        if (usuario.isEmpty()) {
            lblErrorUsuario.setText("Usuario obligatorio");
            camposValidos = false;
        }
        if (contrasena.isEmpty()) {
            lblErrorContraseña.setText("Contraseña obligatoria");
            camposValidos = false;
        }
        
        return camposValidos;
    }

    /**
     * Valida las credenciales del usuario contra la base de datos.
     * @param usuario Nombre de usuario a validar.
     * @param contrasena Contraseña a validar.
     */
    private void validarCredenciales(String usuario, String contrasena) {
        try {
            Usuario usuarioSesion = InicioSesionDAO.verificarCredenciales(usuario, contrasena);

            if (usuarioSesion != null) {
                redirigirSegunTipoUsuario(usuarioSesion);
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    "Credenciales incorrectas",
                    "Usuario y/o contraseña incorrectos, por favor verifica tu información.");
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                "Problemas de conexión",
                ex.getMessage());
        }
    }

    /* Sección: Redirección por tipo de usuario */
    /**
     * Redirige al usuario a su pantalla principal según su tipo.
     * @param usuarioSesion Objeto Usuario con los datos de sesión.
     */
    private void redirigirSegunTipoUsuario(Usuario usuarioSesion) throws SQLException {
        switch (usuarioSesion.getTipo().toLowerCase()) {
            case "estudiante":
                Estudiante estudiante = EstudianteDAO.obtenerEstudiantePorIdUsuario(usuarioSesion.getIdUsuario());
                if (estudiante != null) {
                    irPantallaPrincipalEstudiante(estudiante);
                } else {
                    mostrarErrorDatosNoEncontrados("estudiante");
                }
                break;

            case "profesor":
                Profesor profesor = ProfesorDAO.obtenerProfesorPorIdUsuario(usuarioSesion.getIdUsuario());
                if (profesor != null) {
                    irPantallaPrincipalProfesor(profesor);
                } else {
                    mostrarErrorDatosNoEncontrados("profesor");
                }
                break;

            case "evaluador":
                Evaluador evaluador = EvaluadorDAO.obtenerEvaluadorPorIdUsuario(usuarioSesion.getIdUsuario());
                if (evaluador != null) {
                    irPantallaPrincipalEvaluador(evaluador);
                } else {
                    mostrarErrorDatosNoEncontrados("evaluador");
                }
                break;

            case "coordinador":
                Coordinador coordinador = CoordinadorDAO.obtenerCoordinadorPorIdUsuario(usuarioSesion.getIdUsuario());
                if (coordinador != null) {
                    irPantallaPrincipalCoordinador(coordinador);
                } else {
                    mostrarErrorDatosNoEncontrados("coordinador");
                }
                break;

            default:
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Tipo de usuario no reconocido",
                    "No se pudo identificar el tipo de usuario.");
                break;
        }
    }

    /**
     * Muestra un mensaje de error cuando no se encuentran los datos del usuario.
     * @param tipoUsuario Tipo de usuario (estudiante, profesor, etc.)
     */
    private void mostrarErrorDatosNoEncontrados(String tipoUsuario) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
            "Datos no encontrados",
            "No se pudo obtener la información del " + tipoUsuario + ".");
    }

    /* Sección: Navegación a pantallas principales */
    private void irPantallaPrincipalCoordinador(Coordinador coordinador) {
        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(txtUsuario),
            "/sistemapracticasis/vista/FXMLPrincipalCoordinador.fxml",
            FXMLPrincipalCoordinadorController.class,
            "inicializarInformacion",
            coordinador
        );
    }

    private void irPantallaPrincipalEstudiante(Estudiante estudiante) {
        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(txtUsuario),
            "/sistemapracticasis/vista/FXMLPrincipalEstudiante.fxml",
            FXMLPrincipalEstudianteController.class,
            "inicializarInformacion",
            estudiante
        );
    }

    private void irPantallaPrincipalProfesor(Profesor profesor) {
        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(txtUsuario),
            "/sistemapracticasis/vista/FXMLPrincipalProfesor.fxml",
            FXMLPrincipalProfesorController.class,
            "inicializarInformacion",
            profesor
        );
    }

    private void irPantallaPrincipalEvaluador(Evaluador evaluador) {
        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(txtUsuario),
            "/sistemapracticasis/vista/FXMLPrincipalEvaluador.fxml",
            FXMLPrincipalEvaluadorController.class,
            "inicializarInformacion",
            evaluador
        );
    }

    /* Sección: Manejo de visibilidad de contraseña */
    /**
     * Maneja el evento para mostrar/ocultar la contraseña.
     * @param event Evento de acción generado.
     */
    @FXML
    private void btnClicMostrarContraseña(ActionEvent event) {
        if (txtPasswordVisible.isVisible()) {
            ocultarContrasenaVisible();
        } else {
            mostrarContrasenaVisible();
        }
    }

    private void mostrarContrasenaVisible() {
        txtPasswordVisible.setText(pfPassword.getText());
        txtPasswordVisible.setVisible(true);
        txtPasswordVisible.setManaged(true);
        pfPassword.setVisible(false);
        pfPassword.setManaged(false);
    }

    private void ocultarContrasenaVisible() {
        pfPassword.setText(txtPasswordVisible.getText());
        pfPassword.setVisible(true);
        pfPassword.setManaged(true);
        txtPasswordVisible.setVisible(false);
        txtPasswordVisible.setManaged(false);
    }

    /**
     * Configura los listeners para sincronizar los campos de contraseña.
     */
    private void configurarListeners() {
        pfPassword.textProperty().addListener((obs, oldText, newText) -> {
            txtPasswordVisible.setText(newText);
        });

        txtPasswordVisible.textProperty().addListener((obs, oldText, newText) -> {
            pfPassword.setText(newText);
        });
    }

    /**
     * Obtiene la contraseña actual del campo visible.
     * @return La contraseña ingresada por el usuario.
     */
    private String obtenerContrasenaActual() {
        return pfPassword.isVisible() ? pfPassword.getText() : txtPasswordVisible.getText();
    }
}