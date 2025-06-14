package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.EstadoProyecto;
import sistemapracticasis.modelo.pojo.Proyecto;

/**
 *
 * @author uriel
 */
public class ProyectoDAO {
    public static ArrayList<Proyecto> obtenerProyectosDisponibles() 
            throws SQLException {
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            String consulta = "SELECT p.id_proyecto, p.nombre, p.descripcion, "
                    + "p.estado, p.cupo, p.fecha_inicio, p.fecha_fin, "
                    + "p.id_organizacion_vinculada FROM proyecto p "
                    + "LEFT JOIN estudiante e ON p.id_proyecto = "
                    + "e.id_proyecto WHERE e.id_proyecto IS NULL;";

            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Proyecto proyecto = new Proyecto();
                proyecto.setIdProyecto(resultado.getInt("id_proyecto"));
                proyecto.setNombre(resultado.getString("nombre"));
                proyecto.setDescripcion(resultado.getString("descripcion"));
                proyecto.setEstado(EstadoProyecto.fromValor
                    (resultado.getString("estado")));
                proyecto.setCupo(resultado.getInt("cupo"));
                proyecto.setFecha_inicio(resultado.getDate("fecha_inicio").
                    toString());
                proyecto.setFecha_fin(resultado.getDate("fecha_fin").
                    toString());
                proyecto.setIdOrganizacionVinculada(resultado.getInt
                    ("id_organizacion_vinculada"));

                proyectos.add(proyecto);
            }

            resultado.close();
            sentencia.close();
            conexion.close();
        }

        return proyectos;
    }
}
