package sistemadepracticasprofesionalesdeingenieriadesoftware.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sistemadepracticasprofesionalesdeingenieriadesoftware.SistemaDePracticasProfesionalesDeIngenieriaDeSoftware;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.dao.CoordinadorDAO;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.dao.EstudianteDAO;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.dao.EvaluadorDAO;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.dao.InicioSesionDAO;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.dao.ProfesorDAO;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.pojo.Coordinador;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.pojo.Estudiante;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.pojo.Evaluador;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.pojo.Profesor;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.pojo.Usuario;
import sistemadepracticasprofesionalesdeingenieriadesoftware.util.Utilidad;

public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private Label lbErrorUsuario;
    @FXML
    private Label lbErrorContraseña;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private TextField tfPasswordVisible;
    @FXML
    private Button btnMostrarContraseña;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarListeners();
    }

    @FXML
    private void btnClicVerificarSesion(ActionEvent event) {
        String usuario = tfUsuario.getText();
        String contrasena = pfPassword.getText();
        if (validarCampos(usuario, contrasena))
            validarCredenciales(usuario, contrasena);
    }

    private boolean validarCampos(String usuario, String contrasena) {
        lbErrorUsuario.setText("");
        lbErrorContraseña.setText("");
        boolean camposValidos = true;
        if (usuario.isEmpty()) {
            lbErrorUsuario.setText("Usuario obligatorio");
            camposValidos = false;
        }
        if (contrasena.isEmpty()) {
            lbErrorContraseña.setText("Contraseña obligatoria");
            camposValidos = false;
        }
        return camposValidos;
    }

    private void validarCredenciales(String usuario, String contrasena) {
        try {
            Usuario usuarioSesion = InicioSesionDAO.verificarCredenciales(usuario, contrasena);

            if (usuarioSesion != null) {
                switch (usuarioSesion.getTipo().toLowerCase()) {
                    case "estudiante":
                        Estudiante estudiante = EstudianteDAO.obtenerEstudiantePorIdUsuario(usuarioSesion.getIdUsuario());
                        if (estudiante != null) {
                            irPantallaPrincipalEstudiante(estudiante);
                        } else {
                            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                                    "Datos no encontrados",
                                    "No se pudo obtener la información del estudiante.");
                        }
                        break;

                    case "profesor":
                        Profesor profesor = ProfesorDAO.obtenerProfesorPorIdUsuario(usuarioSesion.getIdUsuario());
                        if (profesor != null) {
                            irPantallaPrincipalProfesor(profesor);
                        } else {
                            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                                    "Datos no encontrados",
                                    "No se pudo obtener la información del profesor.");
                        }
                        break;

                    case "evaluador":
                        Evaluador evaluador = EvaluadorDAO.obtenerEvaluadorPorIdUsuario(usuarioSesion.getIdUsuario());
                        if (evaluador != null) {
                            irPantallaPrincipalEvaluador(evaluador);
                        } else {
                            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                                    "Datos no encontrados",
                                    "No se pudo obtener la información del evaluador.");
                        }
                        break;

                    case "coordinador":
                        Coordinador coordinador = CoordinadorDAO.obtenerCoordinadorPorIdUsuario(usuarioSesion.getIdUsuario());
                        if (coordinador != null) {
                            irPantallaPrincipalCoordinador(coordinador);
                        } else {
                            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                                    "Datos no encontrados",
                                    "No se pudo obtener la información del coordinador.");
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
                        "Usuario y/o contraseña incorrectos, por favor verifica tu información.");
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Problemas de conexión",
                    ex.getMessage());
        }
    }

    private void irPantallaPrincipalCoordinador(Coordinador coordinador) {
        try {
            Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
            FXMLLoader cargador = new FXMLLoader(SistemaDePracticasProfesionalesDeIngenieriaDeSoftware.class.getResource("vista/FXMLPrincipalCoordinador.fxml"));
            Parent vista = cargador.load();
            FXMLPrincipalCoordinadorController controlador = cargador.getController();
            controlador.inicializarInformacion(coordinador);
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Sistema de gestión de prácticas profesionales");
            escenarioBase.centerOnScreen();
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void irPantallaPrincipalEstudiante(Estudiante estudiante) {
        try {
            Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
            FXMLLoader cargador = new FXMLLoader(SistemaDePracticasProfesionalesDeIngenieriaDeSoftware.class.getResource("vista/FXMLPrincipalEstudiante.fxml"));
            Parent vista = cargador.load();
            FXMLPrincipalEstudianteController controlador = cargador.getController();
            controlador.inicializarInformacion(estudiante);
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Sistema de gestión de prácticas profesionales");
            escenarioBase.centerOnScreen();
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void irPantallaPrincipalProfesor(Profesor profesor) {
        try {
            Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
            FXMLLoader cargador = new FXMLLoader(SistemaDePracticasProfesionalesDeIngenieriaDeSoftware.class.getResource("vista/FXMLPrincipalProfesor.fxml"));
            Parent vista = cargador.load();
            FXMLPrincipalProfesorController controlador = cargador.getController();
            controlador.inicializarInformacion(profesor);
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Sistema de gestión de prácticas profesionales");
            escenarioBase.centerOnScreen();
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void irPantallaPrincipalEvaluador(Evaluador evaluador) {
        try {
            Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
            FXMLLoader cargador = new FXMLLoader(SistemaDePracticasProfesionalesDeIngenieriaDeSoftware.class.getResource("vista/FXMLPrincipalEvaluador.fxml"));
            Parent vista = cargador.load();
            FXMLPrincipalEvaluadorController controlador = cargador.getController();
            controlador.inicializarInformacion(evaluador);
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Sistema de gestión de prácticas profesionales");
            escenarioBase.centerOnScreen();
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private void mostrarContrasenaVisible() {
        tfPasswordVisible.setText(pfPassword.getText());
        tfPasswordVisible.setVisible(true);
        tfPasswordVisible.setManaged(true);

        pfPassword.setVisible(false);
        pfPassword.setManaged(false);
    }

    private void ocultarContrasenaVisible() {
        pfPassword.setText(tfPasswordVisible.getText());
        pfPassword.setVisible(true);
        pfPassword.setManaged(true);

        tfPasswordVisible.setVisible(false);
        tfPasswordVisible.setManaged(false);
    }

    private void configurarListeners() {
        pfPassword.textProperty().addListener((obs, oldText, newText) -> {
            tfPasswordVisible.setText(newText);
        });

        tfPasswordVisible.textProperty().addListener((obs, oldText, newText) -> {
            pfPassword.setText(newText);
        });
    }

    private String obtenerContrasenaActual() {
        return pfPassword.isVisible() ? pfPassword.getText() : tfPasswordVisible.getText();
    }

    @FXML
    private void btnClicMostrarContraseña(ActionEvent event) {
        if (tfPasswordVisible.isVisible()) {
            ocultarContrasenaVisible();
        } else {
            mostrarContrasenaVisible();
        }
    }
}

