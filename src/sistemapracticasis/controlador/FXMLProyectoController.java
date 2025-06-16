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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author migue
 */
public class FXMLProyectoController implements Initializable {

    @FXML
    private TableView<?> tblEntregas;
    @FXML
    private TableColumn<?, ?> tbcNombre;
    @FXML
    private TableColumn<?, ?> tbcEstado;
    @FXML
    private TableColumn<?, ?> tbcCupo;
    @FXML
    private TableColumn<?, ?> tbcFechaInicio;
    @FXML
    private TableColumn<?, ?> tbcFechaFin;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtBuscar;
    @FXML
    private Label lblNombreUsuario;
    @FXML
    private Button btnRegresar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBtnBuscar(ActionEvent event) {
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
    }

    @FXML
    private void clicBtnActualizar(ActionEvent event) {
    }

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
    }
    
}
