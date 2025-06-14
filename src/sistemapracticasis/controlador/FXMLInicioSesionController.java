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

public class FXMLInicioSesionController implements Initializable {

    @FXML
    private PasswordField pfPassword;
    @FXML
    private Button btnMostrarContraseña;
    @FXML
    private TextField txtUsuario;
    @FXML
    private Label lblErrorUsuario;
    @FXML
    private Label lblErrorContraseña;
    @FXML
    private TextField txtPasswordVisible;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarListeners();
    }

    @FXML
    private void btnClicVerificarSesion(ActionEvent event) {
        String usuario = txtUsuario.getText();
        String contrasena = pfPassword.getText();
        if (validarCampos(usuario, contrasena))
            validarCredenciales(usuario, contrasena);
    }

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

    private void validarCredenciales(String usuario, String contrasena) {
        try {
            Usuario usuarioSesion = InicioSesionDAO.verificarCredenciales
                (usuario, contrasena);

            if (usuarioSesion != null) {
                switch (usuarioSesion.getTipo().toLowerCase()) {
                    case "estudiante":
                        Estudiante estudiante = EstudianteDAO.
                                obtenerEstudiantePorIdUsuario
                                    (usuarioSesion.getIdUsuario());
                        if (estudiante != null) {
                            irPantallaPrincipalEstudiante(estudiante);
                        } else {
                            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                                    "Datos no encontrados",
                                    "No se pudo obtener la información del "
                                        + "estudiante.");
                        }
                        break;

                    case "profesor":
                        Profesor profesor = ProfesorDAO.
                                obtenerProfesorPorIdUsuario
                                    (usuarioSesion.getIdUsuario());
                        if (profesor != null) {
                            irPantallaPrincipalProfesor(profesor);
                        } else {
                            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                                    "Datos no encontrados",
                                    "No se pudo obtener la información del "
                                        + "profesor.");
                        }
                        break;

                    case "evaluador":
                        Evaluador evaluador = EvaluadorDAO.
                                obtenerEvaluadorPorIdUsuario
                                    (usuarioSesion.getIdUsuario());
                        if (evaluador != null) {
                            irPantallaPrincipalEvaluador(evaluador);
                        } else {
                            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                                    "Datos no encontrados",
                                    "No se pudo obtener la información del "
                                        + "evaluador.");
                        }
                        break;

                    case "coordinador":
                        Coordinador coordinador = CoordinadorDAO.
                                obtenerCoordinadorPorIdUsuario
                                    (usuarioSesion.getIdUsuario());
                        if (coordinador != null) {
                            irPantallaPrincipalCoordinador(coordinador);
                        } else {
                            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                                    "Datos no encontrados",
                                    "No se pudo obtener la información del "
                                        + "coordinador.");
                        }
                        break;

                    default:
                        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                                "Tipo de usuario no reconocido",
                                "No se pudo identificar el tipo de usuario.");
                        break;
                }
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                        "Credenciales incorrectas",
                        "Usuario y/o contraseña incorrectos, por favor "
                            + "verifica tu información.");
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Problemas de conexión",
                    ex.getMessage());
        }
    }

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

    private void configurarListeners() {
        pfPassword.textProperty().addListener((obs, oldText, newText) -> {
            txtPasswordVisible.setText(newText);
        });

        txtPasswordVisible.textProperty().addListener((obs, oldText, 
            newText) -> {
                pfPassword.setText(newText);
        });
    }

    private String obtenerContrasenaActual() {
        return pfPassword.isVisible() ? pfPassword.getText() : 
            txtPasswordVisible.getText();
    }

    @FXML
    private void btnClicMostrarContraseña(ActionEvent event) {
        if (txtPasswordVisible.isVisible()) {
            ocultarContrasenaVisible();
        } else {
            mostrarContrasenaVisible();
        }
    }
}

