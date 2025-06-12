package sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.conexion;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author uriel
 */
public class ConexionBD {
    
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    public static Connection abrirConexion(){
        Connection conexionBD = null;
        String[] credentials = getCredenciales();

        try {
            Class.forName(DRIVER);
            conexionBD = DriverManager.getConnection(credentials[0], 
                    credentials[1], credentials[2]);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return conexionBD;
    }
    
    private static String[] getCredenciales() {
        String[] credenciales = new String[3];

        try (InputStream input = ConexionBD.class.getClassLoader()
                .getResourceAsStream("sistemadepracticasprofesionales"
                        + "deingenieriadesoftware/config.properties")) {

            Properties properties = new Properties();
            properties.load(input);

            credenciales[0] = properties.getProperty("DB_URL");
            credenciales[1] = properties.getProperty("DB_USER");
            credenciales[2] = properties.getProperty("DB_PASSWORD");

        } catch (IOException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, 
                    null, ex);
        }

        return credenciales;
    }
}
