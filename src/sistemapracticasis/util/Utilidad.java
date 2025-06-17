package sistemapracticasis.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Autor: Uriel Cendón
 * Fecha de creación: 13/06/2025
 * Descripción: Clase de utilidades generales para el sistema,
 * que incluye métodos para mostrar alertas, confirmar acciones,
 * obtener el escenario de un componente y cargar la fecha actual
 * en controles como TextField o Label.
 */
public class Utilidad {
    
    /*
     * Sección: Alertas
     * Esta sección contiene métodos para mostrar alertas de tipo información,
     * advertencia o error al usuario, de manera sencilla y reutilizable, así
     * como alertas que gestionan confirmaciones por parte del usuario mediante
     * dialogos con botones de aceptar y cancelar.
     */
    /**
     * Muestra una alerta simple con el tipo, título y mensaje especificados.
     *
     * @param tipo El tipo de alerta (INFORMATION, ERROR, WARNING, etc.).
     * @param titulo El título de la alerta.
     * @param mensaje El mensaje que se mostrará en el contenido de la alerta.
     */
    public static void mostrarAlertaSimple(Alert.AlertType tipo, String titulo, 
            String mensaje){
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    /**
     * Muestra una alerta de confirmación con los textos indicados
     * y devuelve el resultado de la acción del usuario.
     *
     * @param titulo El título de la alerta de confirmación.
     * @param encabezado El encabezado de la alerta.
     * @param contenido El contenido (mensaje principal) de la alerta.
     * @return true si el usuario presiona OK, false en cualquier otro caso.
     */
    public static boolean mostrarConfirmacion(String titulo, String encabezado, 
            String contenido) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);

        Optional<ButtonType> respuesta = alerta.showAndWait();
        return respuesta.isPresent() && respuesta.get() == ButtonType.OK;
    }
    
    /*
     * Sección: Utilidades de interfaz
     * Esta sección incluye funciones para interactuar con componentes de JavaFX,
     * como obtener la ventana actual o insertar automáticamente la fecha.
     */

    /**
     * Obtiene el escenario (Stage) asociado al componente especificado.
     *
     * @param componente El componente desde el cual se desea obtener el 
     *        escenario.
     * @return El Stage (ventana) en el que se encuentra el componente.
     */
    public static Stage getEscenarioComponente(Control componente){
        return ((Stage) componente.getScene().getWindow());
    }
    
    /**
     * Carga la fecha actual en el componente especificado, si es un TextField 
     * o Label.
     * El formato de la fecha será "dd/MM/yyyy".
     *
     * @param componente El componente (TextField o Label) donde se desea 
     * mostrar la fecha actual.
     */
    public static void cargarFechaActual(Control componente){
        if (componente instanceof TextField) {
            TextField textField = (TextField) componente;
            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern
                ("dd/MM/yyyy");
            textField.setText(fechaActual.format(formatter));
        } else if (componente instanceof Label) {
            Label label = (Label) componente;
            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern
                ("dd/MM/yyyy");
            label.setText(fechaActual.format(formatter));
        }
    
    }
}
