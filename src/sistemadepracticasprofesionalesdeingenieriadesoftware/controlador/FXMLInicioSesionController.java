/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import sistemadepracticasprofesionalesdeingenieriadesoftware.
        SistemaDePracticasProfesionalesDeIngenieriaDeSoftware;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.dao.
        InicioSesionDAO;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.pojo.
        Usuario;
import sistemadepracticasprofesionalesdeingenieriadesoftware.util.Utilidad;

/**
 * FXML Controller class
 *
 * @author uriel
 */
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarListeners();
    }    

    @FXML
    private void btnClicVerificarSesion(ActionEvent event) {
        String usuario = tfUsuario.getText();
        String contrasena = pfPassword.getText();
        if(validarCampos(usuario, contrasena))
            validarCredenciales(usuario, contrasena);
    }
    
    private boolean validarCampos(String usuario, String contrasena){
        //Limpiar campos
        lbErrorUsuario.setText("");
        lbErrorContraseña.setText("");
        boolean camposValidos = true;
        if(usuario.isEmpty()){
            lbErrorUsuario.setText("Usuario obligatorio");
            camposValidos = false;
        }
        if(contrasena.isEmpty()){
            lbErrorContraseña.setText("Contraseña obligatoria");
            camposValidos = false;
        }
        return camposValidos;
    }
    
    private void validarCredenciales(String usuario, String contrasena){
        try{
            Usuario usuarioSesion = InicioSesionDAO.verificarCredenciales
            (usuario, contrasena);
            if (usuarioSesion != null){
                irPantallaPrincipalCoordinador(usuarioSesion);
            }else{                
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                        "Credenciales incorrectas", "Usuario y/o contraseña "
                        + "incorrectos, por favor verifica tu información.");
            }
        } catch (SQLException ex){
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                    "Problemas de conexión", ex.getMessage());
        }
    }
    private void irPantallaPrincipalCoordinador(Usuario usuario){
        try {
            Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
            FXMLLoader cargador = new FXMLLoader
                (SistemaDePracticasProfesionalesDeIngenieriaDeSoftware.
                class.getResource("vista/FXMLPrincipalCoordinador.fxml"));
            Parent vista = cargador.load();
            FXMLPrincipalCoordinadorController controlador = cargador.getController();
            controlador.inicializarInformacion(usuario);
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Sistema de gestión de prácticas "
                    + "profesionales");
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

        tfPasswordVisible.textProperty().addListener((obs, oldText, 
                newText) -> {
            pfPassword.setText(newText);
        });
    }
    
    private String obtenerContrasenaActual() {
        return pfPassword.isVisible() ? pfPassword.getText() : 
                tfPasswordVisible.getText();
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
