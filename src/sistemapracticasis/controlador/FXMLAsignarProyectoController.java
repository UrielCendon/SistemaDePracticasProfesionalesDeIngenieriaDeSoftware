package sistemapracticasis.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sistemapracticasis.modelo.dao.EstudianteDAO;
import sistemapracticasis.modelo.dao.ProyectoDAO;
import sistemapracticasis.modelo.dto.ParametrosProyectosDisponibles;
import sistemapracticasis.modelo.pojo.Coordinador;
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.modelo.pojo.Proyecto;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/**
 * FXML Controller class
 *
 * @author uriel
 */
public class FXMLAsignarProyectoController implements Initializable {

    private Coordinador coordinadorSesion;
    private EstudianteDAO estudianteDAO = new EstudianteDAO();
    
    @FXML
    private Button btnAsignarProyecto;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtBuscar;
    @FXML
    private TextField txtMatricula;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtProyecto;
    @FXML
    private Label lblNombreUsuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAsignarProyecto.setDisable(true);
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

    @FXML
    private void clicAsignarProyecto(ActionEvent event) {
        try {
            String textoBusqueda = txtBuscar.getText().trim();
            List<Proyecto> proyectos = ProyectoDAO.
                    obtenerProyectosDisponibles();
            Estudiante estudiante = obtenerEstudiante(textoBusqueda);

            ParametrosProyectosDisponibles parametros = new 
                ParametrosProyectosDisponibles(
                    proyectos,
                    (nombreProyecto) -> actualizarProyectoAsignado
                        (nombreProyecto),
                    estudiante
            );

            Navegador.abrirVentanaModalParametrizada(
                Utilidad.getEscenarioComponente(lblNombreUsuario),
                "/sistemapracticasis/vista/FXMLProyectosDisponibles.fxml",
                FXMLProyectosDisponiblesController.class,
                "inicializarInformacion",
                estudiante,
                parametros
            );

        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                "Error de base de datos", 
                "No se pudieron cargar los proyectos.");
            ex.printStackTrace();
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
    private void clicBuscar(ActionEvent event) {
        String textoBusqueda = txtBuscar.getText().trim();

        if (!validarCampos(textoBusqueda)) return;

        Estudiante estudianteEncontrado = obtenerEstudiante(textoBusqueda);

        if (estudianteEncontrado != null) {
            llenarCamposEstudiante(estudianteEncontrado);
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                "No encontrado", "No se encontró ningún estudiante con la "
                    + "matrícula ingresada.");
            limpiarCampos();
        }
    }

    private boolean validarCampos(String matricula) {
        if (matricula.isEmpty()) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                "Campo vacío", "Ingrese una matrícula para buscar al "
                + "estudiante.");
            return false;
        }

        if (!matricula.matches("S\\d{8}")) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                "Matrícula inválida", "La matrícula debe iniciar con 'S' "
                    + "seguido de 8 dígitos (Ej. S12345678).");
            return false;
        }

        return true;
    }

    private void llenarCamposEstudiante(Estudiante estudiante) {
        txtMatricula.setText(estudiante.getMatricula());
        txtNombre.setText(estudiante.toString());
        txtCorreo.setText(estudiante.getCorreo());
        txtTelefono.setText(estudiante.getTelefono());

        if (estudiante.getIdProyecto() == 0) {
            txtProyecto.setText("Aún no cuenta con un proyecto asignado");
            btnAsignarProyecto.setDisable(false);
        } else {
            txtProyecto.setText(estudiante.getNombreProyecto());
        }
    }

    private void limpiarCampos() {
        txtMatricula.clear();
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtProyecto.clear();
    }
    
    public void actualizarProyectoAsignado(String nombreProyecto) {
        txtProyecto.setText(nombreProyecto);
        btnAsignarProyecto.setDisable(true);
    }
    
    private Estudiante obtenerEstudiante(String matricula) {
        Estudiante estudiante = new Estudiante();
        boolean encontrado = estudianteDAO.buscarPorMatricula
            (matricula, estudiante);
        return (encontrado) ? estudiante : null;
    }

}