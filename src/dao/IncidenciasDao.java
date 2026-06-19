package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import excepciones.persistencia.PersistenciaException;

/**
 * Gestiona la persistencia de los tickets de soporte
 * redactados por los clientes para ser atendidos por los empleados
 */
public class IncidenciasDao {

	/**
	 * Crea un nuevo registro de incidencia con estado 'ABIERTA' y fecha actual
	 * @param idUsuario El identificador del cliente que reporta el problema
	 * @param nombreUsuario El alias del cliente
	 * @param descripcion El cuerpo del mensaje o problema reportado
	 * @return true si la incidencia se guardó correctamente
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
	        throw new PersistenciaException("Error al guardar incidencia: " + e.getMessage(), e);
	    } finally {
	        ConexionDB.cerrar(con);
	    }
	}
	
	/**
	 * Recupera el registro histórico absoluto de todas las incidencias creadas en la base de datos,
	 * independientemente de su estado
	 * @return Una lista de cadenas con el resumen de cada incidencia
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
	        throw new PersistenciaException("Error al leer las incidencias: " + e.getMessage(), e);
	    } finally {
	        ConexionDB.cerrar(con);
	    }
	    return lista;
	}

	/**
	 * Actualiza el estado de una incidencia a 'RESUELTA' registrando la fecha de cierre 
	 * y el empleado que la ha gestionado
	 * @param idIncidencia El número de ticket de la incidencia a cerrar
	 * @param idEmpleado El identificador del empleado que realiza la acción
	 * @return true si se actualizó con éxito en la base de datos
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
	        throw new PersistenciaException("Error al atender la incidencia: " + e.getMessage(), e);
	    } finally {
	        ConexionDB.cerrar(con);
	    }
	}
	
	/**
	 * Extrae exclusivamente las incidencias que mantienen el estado 'ABIERTA'
	 * ordenadas habitualmente por antigüedad para ser atendidas
	 * @return Una lista de cadenas con los datos de las incidencias pendientes
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
	        throw new PersistenciaException("Error al listar incidencias pendientes: " + e.getMessage(), e);
	    } finally {
	        ConexionDB.cerrar(con);
	    }
	    return pendientes;
	}
}