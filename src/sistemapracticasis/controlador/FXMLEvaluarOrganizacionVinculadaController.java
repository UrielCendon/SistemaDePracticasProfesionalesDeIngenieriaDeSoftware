package sistemapracticasis.controlador;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import sistemapracticasis.modelo.dao.EvaluacionOrgVinDAO;
import sistemapracticasis.modelo.dao.ExpedienteDAO;
import sistemapracticasis.modelo.dao.OrganizacionVinculadaDAO;
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.modelo.pojo.EvaluacionOrgVin;
import sistemapracticasis.modelo.pojo.OrganizacionVinculada;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/**
 * Autor: Uriel Cendón
 * Fecha de creación: 14/06/2025
 * Descripción: Controlador de la vista FXMLEvaluarOrganizacionVinculada,
 * que permite al estudiante evaluar a la organización vinculada que tiene
 * relacionada según su proyecto, y que esta evaluación se guarde correctamente.
 */
public class FXMLEvaluarOrganizacionVinculadaController implements 
        Initializable {
    
    /* Sección: Declaración de variables y componentes
     * Contiene todas las variables de instancia y componentes FXML del 
     * controlador
     */
    /** Estudiante actualmente en sesión */
    private Estudiante estudianteSesion;

    /** Campo de texto para la razón social de la organización */
    @FXML private TextField txtRazonSocial;    
    /** Campo de texto para evaluar el ambiente laboral (1-10) */
    @FXML private TextField txtAmbienteLaboral;
    /** Campo de texto para evaluar la supervisión del encargado (1-10) */
    @FXML private TextField txtSupervisionEncargado;
    /** Campo de texto para evaluar el cumplimiento de actividades (1-10) */
    @FXML private TextField txtCumplimientoActividades;
    /** Campo de texto para evaluar la carga laboral (1-10) */
    @FXML private TextField txtCargaLaboral;
    /** Campo de texto que muestra la fecha de evaluación */
    @FXML private TextField txtFecha;
    /** Campo de texto que muestra el puntaje total calculado */
    @FXML private TextField txtPuntajeTotal;
    /** Área de texto para ingresar recomendaciones */
    @FXML private TextArea txaRecomendaciones;
    /** Etiqueta que muestra el nombre del usuario */
    @FXML private Label lblNombreUsuario;
    /** Botón para subir la evaluación */
    @FXML private Button btnSubir;

    /* Sección: Métodos de inicialización
     * Contiene los métodos relacionados con la configuración inicial del 
     * controlador
     */
    /**
     * Inicializa el controlador después de que su raíz ha sido procesada.
     * Configura los listeners para validación de campos.
     * 
     * @param url Ubicación relativa del objeto raíz
     * @param rb Recursos localizados para este objeto
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        agregarListenersValidacion();
    }  
    
    /**
     * Inicializa la información del estudiante y carga datos iniciales.
     * 
     * @param estudianteSesion Objeto Estudiante con los datos de la sesión 
     * activa
     */
    public void inicializarInformacion(Estudiante estudianteSesion) {
        this.estudianteSesion = estudianteSesion;
        cargarInformacionUsuario();
        cargarInformacionOrganizacionVinculada();
        Utilidad.cargarFechaActual(txtFecha);
        txtFecha.setText("Fecha: " + txtFecha.getText());
        txtFecha.setDisable(true);
        txtPuntajeTotal.setDisable(true);
        btnSubir.setDisable(true);
    }

    /* Sección: Manejo de eventos
     * Contiene los métodos que responden a las interacciones del usuario
     */
    /**
     * Maneja el evento de clic en el botón Cancelar.
     * Regresa a la vista principal del estudiante previa confirmación.
     * 
     * @param event Evento de acción generado por el clic
     */
    @FXML
    private void clicCancelar(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion(
            "Cancelar",
            "Cancelar",
            "¿Está seguro de que quiere cancelar?")) {
                Navegador.cambiarEscenaParametrizada(
                    Utilidad.getEscenarioComponente(lblNombreUsuario),
                    "/sistemapracticasis/vista/FXMLPrincipalEstudiante.fxml",
                    FXMLPrincipalEstudianteController.class,
                    "inicializarInformacion",
                    estudianteSesion
                );
        }
    }

    /**
     * Maneja el evento de clic en el botón Subir.
     * Valida los campos, calcula el puntaje y guarda la evaluación.
     * 
     * @param event Evento de acción generado por el clic
     */
    @FXML
    private void clicSubir(ActionEvent event) {
        if (validarCampos()) {
            calcularPuntajeTotal();
            if (Utilidad.mostrarConfirmacion(
                "Guardar Evaluación", 
                "Guardar Evaluación", 
                "¿Está seguro de querer guardar la evaluación?"
            )){
                    Integer idExpediente = ExpedienteDAO
                        .obtenerIdExpedientePorEstudiante(estudianteSesion.
                            getIdEstudiante());

                    if (idExpediente != null) {
                        EvaluacionOrgVin evaluacion = 
                            crearEvaluacion(idExpediente);
                        guardarEvaluacion(evaluacion);
                    } else {
                        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                            "Error",
                            "No se encontró expediente del estudiante.");
                    }
            }

            txtFecha.setDisable(false);
            txtPuntajeTotal.setDisable(false);
            
            btnSubir.setDisable(true);
            txtAmbienteLaboral.setDisable(true);
            txtSupervisionEncargado.setDisable(true);
            txtCumplimientoActividades.setDisable(true);
            txtCargaLaboral.setDisable(true);
            txaRecomendaciones.setDisable(true);
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                "Datos Inválidos", 
                "Existen campos inválidos, por favor corrija la evaluación");
        }
    }
    
    /* Sección: Validación de datos
     * Contiene los métodos para validar los campos de entrada
     */
    private boolean validarCampos() {
        boolean camposLlenados = true;

        camposLlenados &= validarCampo(txtAmbienteLaboral);
        camposLlenados &= validarCampo(txtSupervisionEncargado);
        camposLlenados &= validarCampo(txtCumplimientoActividades);
        camposLlenados &= validarCampo(txtCargaLaboral);
        camposLlenados &= validarCampo(txaRecomendaciones);

        btnSubir.setDisable(!camposLlenados);
        return camposLlenados;
    }
    
    private boolean validarCampo(TextField campo) {
        try {
            String texto = campo.getText().trim();
            int valor = Integer.parseInt(texto);

            if (valor < 1 || valor > 10) {
                campo.setStyle("-fx-border-color: red;");
                return false;
            } else {
                campo.setStyle("");
                return true;
            }
        } catch (NumberFormatException e) {
            campo.setStyle("-fx-border-color: red;");
            return false;
        }
    }
    
    private boolean validarCampo(TextArea campo) {
        String texto = campo.getText().trim();
        if (texto.length() > 255) {
            campo.setStyle("-fx-border-color: red;");
            return false;
        } else {
            campo.setStyle("");
            return true;
        }
    }
    
    /* Sección: Cálculos y procesamiento
     * Contiene los métodos para realizar cálculos y procesar datos
     */
    private void calcularPuntajeTotal() {
        double puntaje = 0;
        puntaje += Integer.parseInt(txtAmbienteLaboral.getText().trim());
        puntaje += Integer.parseInt(txtSupervisionEncargado.getText().trim());
        puntaje += Integer.parseInt(txtCumplimientoActividades.getText().
            trim());
        puntaje += Integer.parseInt(txtCargaLaboral.getText().trim());
        puntaje = puntaje/4;

        txtPuntajeTotal.setText("Puntaje: " + String.valueOf(puntaje));
    }
    
    private void verificarCamposLlenos() {
        boolean todosLlenos =
            !txtAmbienteLaboral.getText().trim().isEmpty() &&
            !txtSupervisionEncargado.getText().trim().isEmpty() &&
            !txtCumplimientoActividades.getText().trim().isEmpty() &&
            !txtCargaLaboral.getText().trim().isEmpty() &&
            !txaRecomendaciones.getText().trim().isEmpty();

        btnSubir.setDisable(!todosLlenos);
    }
    
    /* Sección: Configuración de Interfaz gráfica
     * Contiene métodos para configurar componentes de la interfaz
     */
    private void agregarListenersValidacion() {
        configurarCampoNumerico(txtAmbienteLaboral);
        configurarCampoNumerico(txtSupervisionEncargado);
        configurarCampoNumerico(txtCumplimientoActividades);
        configurarCampoNumerico(txtCargaLaboral);
        
        txtAmbienteLaboral.textProperty().addListener
            ((obs, oldVal, newVal) -> verificarCamposLlenos());
        txtSupervisionEncargado.textProperty().addListener
            ((obs, oldVal, newVal) -> verificarCamposLlenos());
        txtCumplimientoActividades.textProperty().addListener
            ((obs, oldVal, newVal) -> verificarCamposLlenos());
        txtCargaLaboral.textProperty().addListener
            ((obs, oldVal, newVal) -> verificarCamposLlenos());
        txaRecomendaciones.textProperty().addListener
            ((obs, oldVal, newVal) -> verificarCamposLlenos());
    }
    
    private void configurarCampoNumerico(TextField campo) {
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            if (change.getText().matches("\\d*")) {
                return change;
            }
            return null;
        };

        campo.setTextFormatter(new TextFormatter<>(filtro));
    }
    
    /* Sección: Operaciones con datos
     * Contiene métodos para crear y guardar entidades de negocio
     */
    private EvaluacionOrgVin crearEvaluacion(Integer idExpediente) {
        EvaluacionOrgVin evaluacion = new EvaluacionOrgVin();
        evaluacion.setFechaRealizado(LocalDate.now().toString());
        evaluacion.setPuntajeTotal(Double.parseDouble
            (txtPuntajeTotal.getText().replace("Puntaje: ", "").trim()));
        evaluacion.setAmbienteLaboral(Integer.parseInt
            (txtAmbienteLaboral.getText()));
        evaluacion.setSupervision(Integer.parseInt
            (txtSupervisionEncargado.getText()));
        evaluacion.setCumplimientoActividades
            (Integer.parseInt(txtCumplimientoActividades.getText()));
        evaluacion.setCargaLaboral(Integer.parseInt(txtCargaLaboral.getText()));
        evaluacion.setRecomendaciones(txaRecomendaciones.getText());
        evaluacion.setIdExpediente(idExpediente);
        return evaluacion;
    }
    
    private void guardarEvaluacion(EvaluacionOrgVin evaluacion) {
        boolean exito = EvaluacionOrgVinDAO.
            guardarEvaluacionOrganizacionVinculada(evaluacion);

        if (exito) {
            double calif = Double.parseDouble(txtPuntajeTotal.getText().replace
                ("Puntaje: ", "").trim());
            boolean actualizado = ExpedienteDAO.
                actualizarCalifPorEvaluacionOrgVin
                    (calif, evaluacion.getIdExpediente());
            if (actualizado) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                    "Éxito", "Evaluación guardada con éxito.");
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                    "Evaluación guardada", 
                    "No se ha pudo actualizar el expediente correctamente.");
            }
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", 
                "No se pudo guardar la evaluación.");
        }
    }
    
    /* Sección: Carga de datos
     * Contiene métodos para cargar información en la interfaz
     */
    private void cargarInformacionUsuario() {
        if (estudianteSesion != null) {
            lblNombreUsuario.setText(
                estudianteSesion.toString()
            );
        }
    }
    
    private void cargarInformacionOrganizacionVinculada() {
        if (estudianteSesion != null) {
            OrganizacionVinculada organizacion = OrganizacionVinculadaDAO.
                obtenerOrganizacionVinculadaPorEstudiante
                    (estudianteSesion.getIdEstudiante());
            if (organizacion != null) {
                txtRazonSocial.setText(organizacion.getRazonSocial());
            }
        }
    }
}