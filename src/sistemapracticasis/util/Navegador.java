package sistemapracticasis.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author uriel
 */
public class Navegador {
    public static <T, C> void cambiarEscenaParametrizada(
            Stage escenarioBase,
            String rutaFXML,
            Class<C> claseControlador,
            String metodoInicializacion,
            Object... parametros
    ) {
        try {
            URL url = Navegador.class.getResource(rutaFXML);
            
            if (url == null) {
                System.err.println("No se pudo encontrar el FXML en la ruta: " 
                    + rutaFXML);
                throw new IOException("Recurso FXML no encontrado: " + 
                    rutaFXML);
            }

            FXMLLoader cargador = new FXMLLoader(url);
            Parent vista = cargador.load();
            C controlador = cargador.getController();

            if (metodoInicializacion != null && 
                parametros != null) {
                    Method metodo = claseControlador.getMethod(
                        metodoInicializacion,
                        getParameterTypes(parametros));
                    metodo.invoke(controlador, parametros);
            }

            Scene escena = new Scene(vista);
            escenarioBase.setScene(escena);
            escenarioBase.setTitle("Sistema de gestión de prácticas "
                + "profesionales");
            escenarioBase.centerOnScreen();
            escenarioBase.show();
            
        } catch (IOException | ReflectiveOperationException ex) {
            System.err.println("Error al cambiar de escena: " + 
                ex.getMessage());
            ex.printStackTrace();
        }
    }
      
    public static void cerrarSesion(Stage escenarioBase) {
        final String rutaFXML = "/sistemapracticasis/vista/FXMLInicioSesion."
            + "fxml";

        try {
            URL url = Navegador.class.getResource(rutaFXML);

            if (url == null) {
                System.err.println("No se pudo encontrar el FXML en la ruta: " 
                    + rutaFXML);
                throw new IOException("Recurso FXML no encontrado: " + 
                    rutaFXML);
            }

            FXMLLoader cargador = new FXMLLoader(url);
            Parent vista = cargador.load();
            
            Scene escena = new Scene(vista);
            escenarioBase.setScene(escena);
            escenarioBase.setTitle("Sistema de gestión de prácticas "
                + "profesionales");
            escenarioBase.centerOnScreen();
            escenarioBase.show();

        } catch (IOException ex) {
            System.err.println("Error al cambiar de escena: " + 
                ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public static <C> void abrirVentanaModal(
        Window ventanaPadre,
        String rutaFXML,
        Class<C> claseControlador
    ) {
        try {
            URL url = Navegador.class.getResource(rutaFXML);

            if (url == null) {
                System.err.println("No se pudo encontrar el FXML en la ruta: " 
                    + rutaFXML);
                throw new IOException("Recurso FXML no encontrado: " 
                    + rutaFXML);
            }

            FXMLLoader cargador = new FXMLLoader(url);
            Parent vista = cargador.load();
            C controlador = cargador.getController();

            Stage ventanaModal = new Stage();
            ventanaModal.initOwner(ventanaPadre);
            ventanaModal.initModality(Modality.APPLICATION_MODAL);
            ventanaModal.setTitle("Sistema de gestión de prácticas "
                + "profesionales");
            ventanaModal.setScene(new Scene(vista));
            ventanaModal.centerOnScreen();
            ventanaModal.showAndWait();

        } catch (IOException ex) {
            System.err.println("Error al abrir la ventana: " + 
                ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public static <C> void abrirVentanaModalParametrizada(
        Window ventanaPadre,
        String rutaFXML,
        Class<C> claseControlador,
        String metodoInicializacion,
        Object... parametros
    ) {
        try {
            URL url = Navegador.class.getResource(rutaFXML);

            if (url == null) {
                System.err.println("No se pudo encontrar el FXML en la ruta: "
                    + rutaFXML);
                throw new IOException("Recurso FXML no encontrado: "
                    + rutaFXML);
            }

            FXMLLoader cargador = new FXMLLoader(url);
            Parent vista = cargador.load();
            C controlador = cargador.getController();

            if (metodoInicializacion != null && parametros != null) {
                Method metodo = claseControlador.getMethod(
                    metodoInicializacion,
                    getParameterTypes(parametros));
                metodo.invoke(controlador, parametros);
            }

            Stage ventanaModal = new Stage();
            ventanaModal.initOwner(ventanaPadre);
            ventanaModal.initModality(Modality.APPLICATION_MODAL);
            ventanaModal.setTitle("Sistema de gestión de prácticas"
                    + " profesionales");
            ventanaModal.setScene(new Scene(vista));
            ventanaModal.centerOnScreen();
            ventanaModal.showAndWait();

        } catch (IOException | ReflectiveOperationException ex) {
            System.err.println("Error al abrir la ventana modal: "
                    + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public static void cerrarVentana(Control componente){
        Stage ventana = (Stage) componente.getScene().getWindow();
        ventana.close();
    }
    
    private static Class<?>[] getParameterTypes(Object... parametros) {
        Class<?>[] parameterTypes = new Class<?>[parametros.length];
        for (int i = 0; i < parametros.length; i++) {
            parameterTypes[i] = (parametros[i] != null)
                ? parametros[i].getClass()
                : Object.class;
        }
        return parameterTypes;
    }
}
