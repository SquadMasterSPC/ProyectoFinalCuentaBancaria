package dao;

import java.sql.*;
import modelo.Usuario;

public class UsuariosDao {

	public Usuario validarUsuario(String usuario, String contrasenia) {
	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    try {
	        con = ConexionDB.conectar();
	        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasenia = ?";
	        ps = con.prepareStatement(sql);
	        ps.setString(1, usuario);
	        ps.setString(2, contrasenia);
	        rs = ps.executeQuery();

	        if (rs.next()) {
	            Usuario user = new Usuario("", "", "", "", "", 0.0, "", 0);
	            
	            user.setId(rs.getInt("id")); 
	            user.setNombre(rs.getString("usuario"));
	            user.setApellido(rs.getString("apellido"));
	            user.setCorreoElectronico(rs.getString("correo"));
	            user.setContrasenia(rs.getString("contrasenia"));
	            user.setNumeroDeCuenta(rs.getString("numeroDeCuenta"));
	            user.setRol(rs.getString("rol"));
	            
	            user.setSaldo(0.0); 
	            
	            return user;
	        }
	    } catch (SQLException e) {
	        System.out.println("ERROR SQL al validar: " + e.getMessage());
	    } finally {
	        ConexionDB.cerrar(con);
	    }
	    return null;
	}

/*
 * Creamos un nuevo usuario pidiendo nombre, apellido, correo y contraseña
 * aparte se le asignara un rol segun si es cliente, empleado o admin 
 */
    public boolean registrarUsuarioCompleto(Usuario user) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ConexionDB.conectar();
            String sql = "INSERT INTO usuarios (usuario, apellido, correo, contrasenia, rol) VALUES (?, ?, ?, ?, ?)";
            
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getNombre());
            ps.setString(2, user.getApellido());
            ps.setString(3, user.getCorreoElectronico());
            ps.setString(4, user.getContrasenia());
            ps.setString(5, "CLIENTE");
            
            ps.executeUpdate();
            
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int idGenerado = rs.getInt(1);
                user.setId(idGenerado); 
                
                String cuentaDoc = "ES" + String.format("%06d", idGenerado);
                
                String sqlUpdate = "UPDATE usuarios SET numeroDeCuenta = ? WHERE id = ?";
                PreparedStatement psUp = con.prepareStatement(sqlUpdate);
                psUp.setString(1, cuentaDoc);
                psUp.setInt(2, idGenerado);
                psUp.executeUpdate();
                
                user.setNumeroDeCuenta(cuentaDoc);
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error en registro: " + e.getMessage());
            return false;
        } finally {
            ConexionDB.cerrar(con);
        }
    }
}