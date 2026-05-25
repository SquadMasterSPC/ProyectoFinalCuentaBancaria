/*
 * 
 */
package dao;
/*
 * 
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IncidenciasDao {

	/*
	 *Se guarda en la base de datos la incidencia realizada junto con el nombre del
	 *usuario que la realizo para que los empleados en el futuro la atiendan
	 */
	public boolean guardarIncidencia(int idUsuario, String nombreUsuario, String descripcion) {
	    Connection con = null;
	    String sql = "INSERT INTO incidencia (idUsuario, nombre_usuario, descripcion, estado, fechaCreacion, fechaResolucion, idEmpleado) VALUES (?, ?, ?, 'ABIERTA', NOW(), NULL, NULL)";
	    
	    try {
	        con = ConexionDB.conectar();
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, idUsuario);
	        ps.setString(2, nombreUsuario);
	        ps.setString(3, descripcion);
	        
	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        System.out.println("Error al guardar incidencia: " + e.getMessage());
	        return false;
	    } finally {
	        ConexionDB.cerrar(con);
	    }
	}
	/*
	 * Muestra todas las incidencias pendietes de solucionar usando el sistema FIFO
	 * la incidencia más antigua es la primera en mostrarse
	 */
	public List<String> leerIncidencias() {
	    List<String> lista = new ArrayList<>();
	    Connection con = null;
	    try {
	        con = ConexionDB.conectar();
	        String sql = "SELECT idIncidencia, nombre_usuario, descripcion FROM Incidencia ORDER BY idIncidencia ASC";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            lista.add(rs.getInt("idIncidencia") + " - " + rs.getString("nombre_usuario") + ": " + rs.getString("descripcion"));
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al leer: " + e.getMessage());
	    } finally {
	        ConexionDB.cerrar(con);
	    }
	    return lista;
	}

	/*
	 * Atendemos la incidencia mas antigua usando el sistema FIFO
	 */
	public boolean atenderIncidencias(int idIncidencia, int idEmpleado) {
	    Connection con = null;
	    String sql = "UPDATE incidencia SET estado = 'RESUELTA', fechaResolucion = NOW(), idEmpleado = ? WHERE idIncidencia = ?";
	    
	    try {
	        con = ConexionDB.conectar();
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, idEmpleado);
	        ps.setInt(2, idIncidencia);
	        
	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        System.out.println("Error al atender la incidencia: " + e.getMessage());
	        return false;
	    } finally {
	        ConexionDB.cerrar(con);
	    }
	}
	
	/*
	 * Obtenemos las incidencias pendientes de atender por los empleados las cuales son
	 * las incidencias marcadas como ABIERTAS
	 */
	public List<String> obtenerIncidenciasPendientes() {
	    List<String> pendientes = new ArrayList<>();
	    Connection con = null;
	    String sql = "SELECT idIncidencia, nombre_usuario, descripcion, fechaCreacion FROM incidencia WHERE estado = 'ABIERTA'";
	    
	    try {
	        con = ConexionDB.conectar();
	        PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            String inc = "ID: " + rs.getInt("idIncidencia") + 
	                         " | Usuario: " + rs.getString("nombre_usuario") + 
	                         " | Detalle: " + rs.getString("descripcion") + 
	                         " | Fecha: " + rs.getTimestamp("fechaCreacion");
	            pendientes.add(inc);
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al listar incidencias pendientes: " + e.getMessage());
	    } finally {
	        ConexionDB.cerrar(con);
	    }
	    return pendientes;
	}
}