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

/**
 * Autor: Miguel Escobar
 * Fecha de creación: 15/06/2025
 * Descripción: Controlador para registrar o editar proyectos en la vista
 * FXMLRegistrarProyecto.
 */
public class FXMLRegistrarProyectoController implements Initializable {

    /* Sección: Componentes de la interfaz
     * Contiene los elementos FXML de la vista.
     */

    /** Etiqueta con el nombre del usuario. */
    @FXML private Label lblNombreUsuario;

    /** Botón para guardar el proyecto. */
    @FXML private Button btnGuardar;

    /** Campo para el cupo del proyecto. */
    @FXML private TextField txtCupo;

    /** Campo para el nombre del proyecto. */
    @FXML private TextField txtNombre;

    /** Botón para cancelar la operación. */
    @FXML private Button btnCancelar;
    
    /** Botón para regresar a la seleccion de OV y Responsable o a la ventana proyecto . */
    @FXML private Button btnRegresar;

    /** Lista desplegable con los estados posibles del proyecto. */
    @FXML private ComboBox<EstadoProyecto> cbEstado;

    /** Área de texto para la descripción del proyecto. */
    @FXML private TextArea txtDescripcion;

    /** Selector de fecha de inicio del proyecto. */
    @FXML private DatePicker dpFechaInicio;

    /** Selector de fecha de fin del proyecto. */
    @FXML private DatePicker dpFechaFin;

    /** Etiqueta que actúa como título dinámico de la ventana. */
    @FXML private Label lblTituloVentana;

    /** Lista desplegable de la organización vinculada al proyecto. */
    @FXML private ComboBox<OrganizacionVinculada> cbOrganizacionVinculada;

    /** Lista desplegable del responsable del proyecto. */
    @FXML private ComboBox<ResponsableProyecto> cbResponsable;

    /* Sección: Variables de instancia
     * Almacena los datos de la sesión y control del proyecto.
     */

    /** Coordinador autenticado en sesión. */
    private Coordinador coordinadorSesion;

    /** Organización vinculada seleccionada para el proyecto. */
    private OrganizacionVinculada organizacionSeleccionada;

    /** Responsable seleccionado para el proyecto. */
    private ResponsableProyecto responsableSeleccionado;

    /** Indicador de modo edición (true si se edita un proyecto existente). */
    private boolean esEdicion = false;

    /** Proyecto que se está editando (nulo si es un registro nuevo). */
    private Proyecto proyectoEditado;


    /* Sección: Inicialización del controlador */

    /**
     * Inicializa el controlador después de que su elemento raíz haya sido procesado.
     * @param url URL de localización.
     * @param rb Recursos para internacionalización.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbEstado.getItems().setAll(EstadoProyecto.values());
        cbEstado.getSelectionModel().select(EstadoProyecto.ACTIVO);

        cbOrganizacionVinculada.setDisable(true);
        cbResponsable.setDisable(true);

        configurarCambioDeOrganizacion();
    }

    /**
     * Inicializa la información del coordinador, organización y responsable.
     * @param coordinadorSesion Coordinador en sesión
     * @param organizacion Organización seleccionada
     * @param responsable Responsable seleccionado
     */
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

    /* Sección: Eventos de botones */

    /**
     * Maneja el clic en el botón Guardar.
     */
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
            String mensaje = esEdicion ? "Proyecto actualizado con éxito" : 
                    "Proyecto registrado con éxito";
            String titulo = esEdicion ? "Actualizacion exitosa" : "Registro exitoso";
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, titulo, mensaje);

            Navegador.cambiarEscenaParametrizada(
                    Utilidad.getEscenarioComponente(btnGuardar),
                    "/sistemapracticasis/vista/FXMLPrincipalCoordinador.fxml",
                    FXMLPrincipalCoordinadorController.class,
                    "inicializarInformacion",
                    coordinadorSesion);
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "No hay conexión con la base de datos",
                    resultado.getMensaje());
        }
    }

    /**
     * Maneja el clic en el botón Cancelar.
     */
    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        String titulo = esEdicion ? "Cancelar actualización" : "Cancelar registro";
        String mensaje = esEdicion ? "¿Desea cancelar la actualización del proyecto?" : 
                "¿Desea cancelar el registro del proyecto?";
        String contenido = "Los datos ingresados se perderán.";

        if (Utilidad.mostrarConfirmacion(titulo, mensaje, contenido)) {
            Navegador.cambiarEscenaParametrizada(
                    Utilidad.getEscenarioComponente(btnCancelar),
                    "/sistemapracticasis/vista/FXMLPrincipalCoordinador.fxml",
                    FXMLPrincipalCoordinadorController.class,
                    "inicializarInformacion",
                    coordinadorSesion);
        }
    }
    
    /**
     * Maneja el clic del botón Regresar. Retorna a la vista principal del coordinador.
     */
    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        if (esEdicion) {
            Navegador.cambiarEscenaParametrizada(
                Utilidad.getEscenarioComponente(btnRegresar),
                "/sistemapracticasis/vista/FXMLProyecto.fxml",
                FXMLProyectoController.class,
                "inicializarInformacion",
                coordinadorSesion
            );
        } else {
            Navegador.cambiarEscenaParametrizada(
                Utilidad.getEscenarioComponente(btnRegresar),
                "/sistemapracticasis/vista/FXMLSeleccionarOVYResponsable.fxml",
                FXMLSeleccionarOVYResponsableController.class,
                "inicializarInformacion",
                coordinadorSesion
            );
        }
    }

    /* Sección: Validaciones y construcción */

    private Proyecto construirProyecto() {
        OrganizacionVinculada orgSeleccionada = 
                cbOrganizacionVinculada.getSelectionModel().getSelectedItem();
        ResponsableProyecto respSeleccionado = 
                cbResponsable.getSelectionModel().getSelectedItem();

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
            if (cupo <= 0 || cupo > 127) {
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

    /**
     * Carga la información de un proyecto para su edición.
     * @param proyecto Proyecto a editar
     * @param coordinadorSesion Coordinador activo
     */
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

        cbOrganizacionVinculada.getItems().setAll(
                OrganizacionVinculadaDAO.obtenerOrganizaciones());
        OrganizacionVinculada ov = OrganizacionVinculadaDAO.obtenerOrganizacionPorId(
                proyecto.getIdOrganizacionVinculada());
        if (ov != null) cbOrganizacionVinculada.getSelectionModel().select(ov);

        if (ov != null) {
            cbResponsable.getItems().setAll(
                ResponsableProyectoDAO.obtenerResponsablesPorIdOrganizacion(
                        ov.getIdOrganizacionVinculada())
            );
            ResponsableProyecto responsable = ResponsableProyectoDAO.obtenerResponsablePorId(
                    proyecto.getIdResponsableProyecto());
            if (responsable != null) cbResponsable.getSelectionModel().select(responsable);
        }
    }

    private void configurarCambioDeOrganizacion() {
        cbOrganizacionVinculada.setOnAction(event -> {
            OrganizacionVinculada organizacionSeleccionada = 
                    cbOrganizacionVinculada.getSelectionModel().getSelectedItem();

            if (organizacionSeleccionada != null) {
                int idOrganizacion = organizacionSeleccionada.getIdOrganizacionVinculada();

                List<ResponsableProyecto> responsables = 
                        ResponsableProyectoDAO.obtenerResponsablesPorIdOrganizacion(idOrganizacion);

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
