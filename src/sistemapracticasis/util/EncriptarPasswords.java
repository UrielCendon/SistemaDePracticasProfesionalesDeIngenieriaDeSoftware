package sistemapracticasis.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

import sistemapracticasis.modelo.conexion.ConexionBD;


/**
 * Autor: Miguel Escobar
 * Fecha de creación: 15/06/2025
 * Descripción: Clase que permite encriptar las contraseñas de los usuarios
 * en la base de datos utilizando el algoritmo BCrypt. El proceso verifica si
 * la contraseña ya está encriptada y, si no lo está, la encripta y la actualiza
 * en la base de datos.
 */
public class EncriptarPasswords {
    /**
     * Método principal que ejecuta el proceso de encriptación de contraseñas.
     * Establece la conexión con la base de datos, selecciona los usuarios,
     * verifica si la contraseña ya está encriptada y, en caso contrario, la 
     * encripta usando el algoritmo BCrypt.
     * 
     * @param args Argumentos de línea de comandos, no utilizados en este caso.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            try {
                String querySelect = "SELECT id_usuario, usuario, password "
                    + "FROM usuario";
                PreparedStatement select = conexion.prepareStatement
                    (querySelect);
                ResultSet rs = select.executeQuery();

                while (rs.next()) {
                    int idUsuario = rs.getInt("id_usuario");
                    String nombreUsuario = rs.getString("usuario");
                    String password = rs.getString("password");

                    System.out.println(idUsuario + " - " + nombreUsuario + " - "
                        + "" + password);
                }

                rs.close();
                select.close();

                System.out.print("\nIngresa el nombre de usuario que quieres "
                    + "encriptar: ");
                String usuarioAEncriptar = scanner.nextLine();

                String queryUser = "SELECT id_usuario, password FROM usuario "
                    + "WHERE usuario = ?";
                PreparedStatement buscar = conexion.prepareStatement(queryUser);
                buscar.setString(1, usuarioAEncriptar);
                ResultSet resultado = buscar.executeQuery();

                if (resultado.next()) {
                    int idUsuario = resultado.getInt("id_usuario");
                    String passwordActual = resultado.getString("password");

                    if (passwordActual.startsWith("$2a$") || passwordActual.
                            startsWith("$2b$")) {
                        System.out.println("La contraseña ya está encriptada "
                            + "para ese usuario.");
                    } else {
                        String hash = BCrypt.hashpw(passwordActual, BCrypt.
                            gensalt());

                        String updateQuery = "UPDATE usuario SET password = ? "
                            + "WHERE id_usuario = ?";
                        PreparedStatement update = conexion.prepareStatement
                            (updateQuery);
                        update.setString(1, hash);
                        update.setInt(2, idUsuario);
                        update.executeUpdate();
                        update.close();

                        System.out.println("Contraseña encriptada exitosamente "
                            + "para: " + usuarioAEncriptar);
                    }
                } else {
                    System.out.println("Usuario no encontrado.");
                }

                resultado.close();
                buscar.close();
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error al conectar a la base de datos.");
        }

        scanner.close();
    }
}
