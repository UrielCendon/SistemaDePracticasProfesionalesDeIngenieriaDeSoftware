/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemapracticasis.controlador;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import sistemapracticasis.modelo.dao.GenerarOficiosDAO;
import sistemapracticasis.modelo.pojo.Coordinador;
import sistemapracticasis.modelo.pojo.EstudianteAsignado;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.PDFGenerador;
import sistemapracticasis.util.Utilidad;

/**
 * FXML Controller class
 *
 * @author Kaiser
 */
public class FXMLGenerarOficiosController implements Initializable {
    
    private Coordinador coordinadorSesion;

    @FXML
    private Button btnCancelar;
    @FXML
    private Label lblNombreUsuario;
    @FXML
    private Button btnGenerarDocumentos;
    @FXML
    private TableView<EstudianteAsignado> tvOficiosAsignacion;
    @FXML
    private TableColumn<EstudianteAsignado, String> colMatricula;
    @FXML
    private TableColumn<EstudianteAsignado, String> colEstudiante;
    @FXML
    private TableColumn<EstudianteAsignado, String> colProyectoAsignado;
    @FXML
    private TableColumn<EstudianteAsignado, String> colOrganizacionVinculada;
    @FXML
    private TableColumn<EstudianteAsignado, Boolean> colSeleccionEstudiante;
    @FXML
    private Button btnSeleccionarTodos;
    
    private ObservableList<EstudianteAsignado> listaEstudiantes = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvOficiosAsignacion.setEditable(true);
        colSeleccionEstudiante.setEditable(true);
        configurarColumnas();
        cargarEstudiantes();
    }
    
        public void inicializarInformacion(Coordinador coordinadorSesion) {
        this.coordinadorSesion = coordinadorSesion;
        cargarInformacionUsuario();
    }

    private void cargarInformacionUsuario() {
        if (coordinadorSesion != null) {
            lblNombreUsuario.setText(
                coordinadorSesion.toString()
            );
        }
    }
    
    private void configurarColumnas() {
        colMatricula.setCellValueFactory(cellData -> cellData.getValue().matriculaProperty());
        colEstudiante.setCellValueFactory(cellData -> cellData.getValue().nombreEstudianteProperty());
        colProyectoAsignado.setCellValueFactory(cellData -> cellData.getValue().nombreProyectoProperty());
        colOrganizacionVinculada.setCellValueFactory(cellData -> cellData.getValue().razonSocialOrganizacionProperty());
        colSeleccionEstudiante.setCellValueFactory(cellData -> cellData.getValue().seleccionadoProperty());
        colSeleccionEstudiante.setCellFactory(CheckBoxTableCell.forTableColumn(colSeleccionEstudiante));
    }
    
    private void cargarEstudiantes() {
        try {
            List<EstudianteAsignado> estudiantes = GenerarOficiosDAO.obtenerEstudiantesAsignadosPeriodoActual();
            listaEstudiantes.setAll(estudiantes);
            tvOficiosAsignacion.setItems(listaEstudiantes);
        } catch (Exception e) {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Sin conexión",
                "No hay conexión con la base de datos."
            );
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion(
            "Cancelar",
            "Cancelar",
            "¿Está seguro de que quiere cancelar?")) {
                Navegador.cambiarEscenaParametrizada(
                    Utilidad.getEscenarioComponente(lblNombreUsuario),
                    "/sistemapracticasis/vista/FXMLPrincipalCoordinador.fxml",
                    FXMLPrincipalCoordinadorController.class,
                    "inicializarInformacion",
                    coordinadorSesion
                );
        }
    }

    @FXML
    private void clicSeleccionarTodos(ActionEvent event) {
        for (EstudianteAsignado est : listaEstudiantes) {
            est.setSeleccionado(true);
        }
        tvOficiosAsignacion.refresh();
    }

    @FXML
    private void clicGenerarDocumentos(ActionEvent event) {
        List<EstudianteAsignado> seleccionados = listaEstudiantes.stream()
            .filter(EstudianteAsignado::isSeleccionado)
            .collect(Collectors.toList());

        if (seleccionados.isEmpty()) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                "Estudiantes no seleccionados",
                "Se debe seleccionar al menos un estudiante antes de generar los oficios.");
            return;
        }

        File carpeta = new File("oficios_generados");
        if (!carpeta.exists()) carpeta.mkdirs();

        boolean todosGenerados = true;

        for (EstudianteAsignado est : seleccionados) {
            boolean ok = PDFGenerador.generarOficio(est, carpeta.getAbsolutePath());
            if (!ok) todosGenerados = false;
        }

        if (todosGenerados) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                "Éxito", "Oficios generados correctamente.");
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                "Error", "No fue posible generar los OFICIOS DE ASIGNACIÓN.");
        }
    }
    
}