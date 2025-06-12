/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemadepracticasprofesionalesdeingenieriadesoftware.controlador;

import java.io.IOException;
import java.net.URL;
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
import javafx.stage.Stage;
import sistemadepracticasprofesionalesdeingenieriadesoftware.SistemaDePracticasProfesionalesDeIngenieriaDeSoftware;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.pojo.Usuario;
import sistemadepracticasprofesionalesdeingenieriadesoftware.util.Utilidad;

/**
 * FXML Controller class
 *
 * @author uriel
 */
public class FXMLPrincipalProfesorController implements Initializable {

    private Usuario usuarioSesion;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Label lbNombreUsuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarInformacion(Usuario usuarioSesion){
        this.usuarioSesion = usuarioSesion;
        cargarInformacionUsuario();
    }
    
    private void cargarInformacionUsuario(){
        if(usuarioSesion != null){
            lbNombreUsuario.setText(usuarioSesion.toString());
        }
    }

    @FXML
    private void clicValidarEntregas(ActionEvent event) {
    }

    @FXML
    private void clicConsultarExpediente(ActionEvent event) {
    }

    @FXML
    private void btnClicCerrarSesion(ActionEvent event) {
        boolean confirmado = Utilidad.mostrarConfirmacion(
            "Confirmar cierre de sesión",
            "¿Está seguro(a) de querer cerrar la sesión actual?",
            "Se perderá el acceso a esta sesión.");

        if (confirmado) {
            try {
                Stage escenarioBase = Utilidad.getEscenarioComponente
                    (lbNombreUsuario);
                Parent vista = FXMLLoader.load
                    (SistemaDePracticasProfesionalesDeIngenieriaDeSoftware.
                        class.getResource("vista/FXMLInicioSesion.fxml"));

                Scene escenaPrincipal = new Scene(vista);
                escenarioBase.setScene(escenaPrincipal);
                escenarioBase.setTitle("Inicio Sesión");
                escenarioBase.centerOnScreen();
                escenarioBase.show();
            } catch (IOException ex) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error",
                        "No se pudo cerrar sesión.");
                ex.printStackTrace();
            }
        }
    }
    
}
