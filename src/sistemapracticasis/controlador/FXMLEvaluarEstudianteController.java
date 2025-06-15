/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemapracticasis.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Kaiser
 */
public class FXMLEvaluarEstudianteController implements Initializable {

    @FXML
    private Button btnCancelar;
    @FXML
    private Label lblNombreUsuario;
    @FXML
    private Button btnAceptar;
    @FXML
    private TableView<?> tvEvaluarEstudiante;
    @FXML
    private TableColumn<?, ?> colMatricula;
    @FXML
    private TableColumn<?, ?> colEstudiante;
    @FXML
    private TableColumn<?, ?> colProyectoAsignado;
    @FXML
    private TableColumn<?, ?> colSeleccionEstudiante;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicCancelar(ActionEvent event) {
    }

    @FXML
    private void clicAceptar(ActionEvent event) {
    }
    
}
