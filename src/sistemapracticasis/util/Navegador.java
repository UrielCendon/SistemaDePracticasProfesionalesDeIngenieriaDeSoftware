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
 * Autor: Uriel Cendón
 * Fecha de creación: 13/06/2025
 * Descripción: Esta clase contiene métodos estáticos utilizados para manejar la 
 * navegación entre diferentes vistas en una aplicación JavaFX. Incluye 
 * funcionalidades
 * como cambiar de escena, abrir ventanas modales y cerrar sesiones.
 */

public class Navegador { 

    /*
     * Sección: Cambio de escena parametrizada
     * Esta sección contiene el método para cambiar la escena de la 
     * aplicación, permitiendo pasar parámetros al controlador asociado.
     */
    
    /**
     * Cambia la escena actual del escenario principal a una nueva escena basada
     * en un archivo FXML. Además, invoca un método de inicialización en el 
     * controlador de la nueva escena si se proporciona.
     *
     * @param escenarioBase El escenario principal de la aplicación donde se 
     *                      cambiará la escena.
     * @param rutaFXML La ruta del archivo FXML que representa la nueva vista.
     * @param claseControlador La clase del controlador de la nueva vista.
     * @param metodoInicializacion El nombre del método de inicialización en el 
     *                             controlador que se debe invocar, si se 
     *                             proporciona.
     * @param parametros Parámetros opcionales que se pasan al método de 
     *                  inicialización.
     */
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

    /*
     * Sección: Cierre de sesión
     * Esta sección permite cambiar la escena a la pantalla de inicio de sesión.
     */
    
    /**
     * Cambia la escena actual del escenario principal a la pantalla de inicio 
     * de sesión.
     *
     * @param escenarioBase El escenario principal de la aplicación donde se 
     *                      cambiará la escena.
     */
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

    /*
     * Sección: Apertura de ventana modal
     * Esta sección contiene métodos que permiten abrir ventanas modales 
     * utilizando un archivo FXML y su respectivo controlador.
     */
    
    /**
     * Abre una ventana modal en la aplicación, utilizando un archivo FXML 
     * específico para definir la vista y un controlador asociado.
     *
     * @param ventanaPadre La ventana principal desde la cual se abrirá la 
     *                     ventana modal.
     * @param rutaFXML La ruta del archivo FXML que representa la nueva vista.
     * @param claseControlador La clase del controlador de la nueva vista.
     */
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

    /*
     * Sección: Apertura de ventana modal parametrizada
     * Esta sección contiene métodos que permiten abrir ventanas modales 
     * parametrizadas, lo que permite pasar parámetros a la vista a través 
     * del controlador.
     */
    
    /**
     * Abre una ventana modal parametrizada en la aplicación, permitiendo pasar
     * parámetros al controlador de la vista.
     *
     * @param ventanaPadre La ventana principal desde la cual se abrirá la 
     *                     ventana modal.
     * @param rutaFXML La ruta del archivo FXML que representa la nueva vista.
     * @param claseControlador La clase del controlador de la nueva vista.
     * @param metodoInicializacion El nombre del método de inicialización en el 
     *                             controlador que se debe invocar, si se 
     *                             proporciona.
     * @param parametros Parámetros opcionales que se pasan al método de 
     *                  inicialización.
     */
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
                throw new IOException("Recurso FXML no encontrado: "
                    + rutaFXML);
            }

            FXMLLoader cargador = new FXMLLoader(url);
            Parent vista = cargador.load();
            C controlador = cargador.getController();

            if (metodoInicializacion != null && parametros != null) {
                Method metodo = encontrarMetodoCompatible(controlador.getClass(), 
                    metodoInicializacion, parametros);

                if (metodo == null) {
                    throw new NoSuchMethodException(
                        "No se encontró un método compatible llamado '" + metodoInicializacion +
                        "' en la clase '" + controlador.getClass().getName() +
                        "' para los parámetros dados."
                    );
                }
                
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
    
    /**
     * Busca un método compatible en una clase.
     * Es "compatible" si el nombre coincide y cada argumento pasado puede ser asignado
     * al tipo de parámetro del método (usando isAssignableFrom).
     * Esto soluciona el problema con interfaces y lambdas.
     *
     * @param clase La clase en la que se buscará el método.
     * @param nombreMetodo El nombre del método a buscar.
     * @param args Los argumentos que se pasarán al método.
     * @return El objeto Method si se encuentra, o null si no.
     */
    private static Method encontrarMetodoCompatible(Class<?> clase, String nombreMetodo, 
            Object[] args) {
        for (Method metodo : clase.getMethods()) {
            if (metodo.getName().equals(nombreMetodo)) {
                Class<?>[] tiposParametrosMetodo = metodo.getParameterTypes();
                if (tiposParametrosMetodo.length == args.length) {
                    boolean compatible = true;
                    for (int i = 0; i < tiposParametrosMetodo.length; i++) {
                        Class<?> tipoParametro = tiposParametrosMetodo[i];
                        if (tipoParametro.isPrimitive()) {
                            if (tipoParametro.equals(int.class) && !(args[i] instanceof Integer))
                                compatible = false;
                            else if (tipoParametro.equals(double.class) && !(args[i] 
                                instanceof Double)) compatible = false;
                        } else if (args[i] != null && !tipoParametro.isAssignableFrom
                            (args[i].getClass())) {
                                compatible = false;
                        }
                        if (!compatible) break;
                    }
                    if (compatible) {
                        return metodo;
                    }
                }
            }
        }
        return null;
    }

    /*
     * Sección: Cierre de ventana
     * Esta sección contiene el método que cierra una ventana de la aplicación.
     */
    
    /**
     * Cierra la ventana que contiene el componente de la interfaz de usuario.
     *
     * @param componente El componente de la interfaz que pertenece a la ventana
     *                  que se cerrará.
     */
    public static void cerrarVentana(Control componente){
        Stage ventana = (Stage) componente.getScene().getWindow();
        ventana.close();
    }

    /*
     * Sección: Obtener tipos de parámetros
     * Esta sección contiene el método privado para obtener los tipos de los 
     * parámetros pasados a un método.
     */
    
    /**
     * Obtiene los tipos de parámetros de un método basado en los objetos 
     * pasados
     * como parámetros.
     *
     * @param parametros Los parámetros cuyos tipos se deben determinar.
     * @return Un arreglo con las clases de los parámetros.
     */
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