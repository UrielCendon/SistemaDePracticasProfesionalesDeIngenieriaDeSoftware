package sistemapracticasis.controlador;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sistemapracticasis.modelo.dao.OrganizacionVinculadaDAO;
import sistemapracticasis.modelo.dao.ProyectoDAO;
import sistemapracticasis.modelo.dao.ResponsableProyectoDAO;
import sistemapracticasis.modelo.pojo.Coordinador;
import sistemapracticasis.modelo.pojo.EstadoProyecto;
import sistemapracticasis.modelo.pojo.OrganizacionVinculada;
import sistemapracticasis.modelo.pojo.Proyecto;
import sistemapracticasis.modelo.pojo.ResponsableProyecto;
import sistemapracticasis.modelo.pojo.ResultadoOperacion;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

public class FXMLRegistrarProyectoController implements Initializable {

    @FXML
    private Label lblNombreUsuario;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField txtCupo;
    @FXML
    private TextField txtNombre;
    @FXML
    private Button btnCancelar;
    @FXML
    private ComboBox<EstadoProyecto> cbEstado;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private Label lblTituloVentana;
    @FXML
    private ComboBox<OrganizacionVinculada> cbOrganizacionVinculada;
    @FXML
    private ComboBox<ResponsableProyecto> cbResponsable;

    private Coordinador coordinadorSesion;
    private OrganizacionVinculada organizacionSeleccionada;
    private ResponsableProyecto responsableSeleccionado;
    
    private boolean esEdicion = false;
    private Proyecto proyectoEditado;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbEstado.getItems().setAll(EstadoProyecto.values());
        cbEstado.getSelectionModel().select(EstadoProyecto.ACTIVO);

        cbOrganizacionVinculada.setDisable(true);
        cbResponsable.setDisable(true);

        configurarCambioDeOrganizacion();

    }


    public void inicializarInformacion(Coordinador coordinadorSesion,
                                       OrganizacionVinculada organizacion,
                                       ResponsableProyecto responsable) {
        this.coordinadorSesion = coordinadorSesion;
        this.organizacionSeleccionada = organizacion;
        this.responsableSeleccionado = responsable;

        cargarInformacionUsuario();
        cargarInformacionRelacionada();
    }

    private void cargarInformacionUsuario() {
        if (coordinadorSesion != null) {
            lblNombreUsuario.setText(coordinadorSesion.toString());
        }
    }

    private void cargarInformacionRelacionada() {
        if (organizacionSeleccionada != null) {
            cbOrganizacionVinculada.getItems().add(organizacionSeleccionada);
            cbOrganizacionVinculada.getSelectionModel().selectFirst();
        }

        if (responsableSeleccionado != null) {
            cbResponsable.getItems().add(responsableSeleccionado);
            cbResponsable.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        if (!validarCampos()) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    "Datos inválidos", "Por favor verifique la información ingresada.");
            return;
        }

        Proyecto proyecto = construirProyecto();

        ResultadoOperacion resultado;
        if (esEdicion) {
            proyecto.setIdProyecto(proyectoEditado.getIdProyecto());
            resultado = ProyectoDAO.actualizarProyecto(proyecto);
        } else {
            resultado = ProyectoDAO.registrarProyecto(proyecto);
        }

        if (!resultado.isError()) {
            String mensaje = esEdicion ? "Proyecto actualizado con éxito" : "Proyecto registrado con éxito";
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                    "Operación exitosa", mensaje);

            Navegador.cambiarEscenaParametrizada(
                    Utilidad.getEscenarioComponente(btnGuardar),
                    "/sistemapracticasis/vista/FXMLPrincipalCoordinador.fxml",
                    FXMLPrincipalCoordinadorController.class,
                    "inicializarInformacion",
                    coordinadorSesion
            );
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "No hay conexión con la base de datos",
                    resultado.getMensaje());
        }
    }


    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion(
                "Cancelar registro",
                "¿Desea cancelar el registro del proyecto?",
                "Los datos ingresados se perderán.")) {

            Navegador.cambiarEscenaParametrizada(
                    Utilidad.getEscenarioComponente(btnCancelar),
                    "/sistemapracticasis/vista/FXMLPrincipalCoordinador.fxml",
                    FXMLPrincipalCoordinadorController.class,
                    "inicializarInformacion",
                    coordinadorSesion
            );
        }
    }
    
    private Proyecto construirProyecto() {
        OrganizacionVinculada orgSeleccionada = cbOrganizacionVinculada.getSelectionModel().getSelectedItem();
        ResponsableProyecto respSeleccionado = cbResponsable.getSelectionModel().getSelectedItem();

        if (orgSeleccionada == null || respSeleccionado == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                "Selección incompleta",
                "Debe seleccionar una organización y un responsable.");
            return null;
        }

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(txtNombre.getText().trim());
        proyecto.setDescripcion(txtDescripcion.getText().trim());
        proyecto.setEstado(cbEstado.getValue());
        proyecto.setCupo(Integer.parseInt(txtCupo.getText().trim()));
        proyecto.setFecha_inicio(dpFechaInicio.getValue().toString());
        proyecto.setFecha_fin(dpFechaFin.getValue().toString());
        proyecto.setIdOrganizacionVinculada(orgSeleccionada.getIdOrganizacionVinculada());
        proyecto.setIdResponsableProyecto(respSeleccionado.getIdEncargado());

        // Si estás editando un proyecto existente, asegúrate de tener este campo
        if (proyectoEditado != null) {
            proyecto.setIdProyecto(proyectoEditado.getIdProyecto());
        }

        return proyecto;
    }


    private boolean validarCampos() {
        boolean valido = true;
        limpiarEstilos();

        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String cupoTexto = txtCupo.getText().trim();
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue();
        LocalDate hoy = LocalDate.now();

        if (nombre.isEmpty() || nombre.length() < 5) {
            marcarError(txtNombre); valido = false;
        }

        if (descripcion.isEmpty() || descripcion.length() < 10) {
            marcarError(txtDescripcion); valido = false;
        }

        if (!cupoTexto.matches("\\d+")) {
            marcarError(txtCupo); valido = false;
        } else {
            int cupo = Integer.parseInt(cupoTexto);
            if (cupo <= 0 || cupo > 127) { // límite para TINYINT sin signo
                marcarError(txtCupo); valido = false;
            }
        }

        if (fechaInicio == null || fechaInicio.isBefore(hoy)) {
            marcarError(dpFechaInicio); valido = false;
        }

        if (fechaFin == null || fechaFin.isBefore(fechaInicio)) {
            marcarError(dpFechaFin); valido = false;
        }

        return valido;
    }

    private void marcarError(Control campo) {
        campo.setStyle("-fx-border-color: red; -fx-border-width: 2;");
    }

    private void limpiarEstilos() {
        txtNombre.setStyle("");
        txtDescripcion.setStyle("");
        txtCupo.setStyle("");
        dpFechaInicio.setStyle("");
        dpFechaFin.setStyle("");
    }
    
    
    public void cargarDatosProyectoParaEditar(Proyecto proyecto, Coordinador coordinadorSesion) {
        this.coordinadorSesion = coordinadorSesion;
        this.proyectoEditado = proyecto;
        this.esEdicion = true;

        cargarInformacionUsuario();

        lblTituloVentana.setText("Actualizar proyecto");

        cbOrganizacionVinculada.setDisable(false);
        cbResponsable.setDisable(false);

        txtNombre.setText(proyecto.getNombre());
        txtDescripcion.setText(proyecto.getDescripcion());
        txtCupo.setText(String.valueOf(proyecto.getCupo()));
        dpFechaInicio.setValue(LocalDate.parse(proyecto.getFecha_inicio()));
        dpFechaFin.setValue(LocalDate.parse(proyecto.getFecha_fin()));
        cbEstado.getSelectionModel().select(proyecto.getEstado());

        cbOrganizacionVinculada.getItems().setAll(OrganizacionVinculadaDAO.obtenerOrganizaciones());
        OrganizacionVinculada ov = OrganizacionVinculadaDAO.obtenerOrganizacionPorId(proyecto.getIdOrganizacionVinculada());
        if (ov != null) cbOrganizacionVinculada.getSelectionModel().select(ov);

        if (ov != null) {
            cbResponsable.getItems().setAll(
                ResponsableProyectoDAO.obtenerResponsablesPorIdOrganizacion(ov.getIdOrganizacionVinculada())
            );
            ResponsableProyecto responsable = ResponsableProyectoDAO.obtenerResponsablePorId(proyecto.getIdResponsableProyecto());
            if (responsable != null) cbResponsable.getSelectionModel().select(responsable);
        }
    }

    private void configurarCambioDeOrganizacion() {
        cbOrganizacionVinculada.setOnAction(event -> {
            OrganizacionVinculada organizacionSeleccionada = cbOrganizacionVinculada.getSelectionModel().getSelectedItem();

            if (organizacionSeleccionada != null) {
                int idOrganizacion = organizacionSeleccionada.getIdOrganizacionVinculada();

                List<ResponsableProyecto> responsables = ResponsableProyectoDAO.obtenerResponsablesPorIdOrganizacion(idOrganizacion);

                cbResponsable.getItems().clear();
                cbResponsable.getItems().addAll(responsables);
                cbResponsable.setDisable(responsables.isEmpty());

                if (!responsables.isEmpty()) {
                    cbResponsable.getSelectionModel().selectFirst();
                }
            }
        });
    }



}

