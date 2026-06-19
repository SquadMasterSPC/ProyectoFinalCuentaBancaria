package dao;

import java.sql.*;

import modelo.Usuario;
import excepciones.persistencia.PersistenciaException;
import excepciones.seguridad.CredencialesInvalidasException;

public class UsuariosDao {

	/**
	 * Encargada de las operaciones a nivel de base de datos de los perfiles de usuario
	 * Proporciona métodos para la búsqueda, validación y registro de usuarios en el sistema
	 */
	public UsuariosDao() {
		super();
	}
	
	/**
	 * Localiza un usuario en la base de datos basándose únicamente en su nombre de usuario
	 * Es fundamental para el proceso de autenticación con contraseñas encriptadas
	 * @param username El nombre del usuario introducido en el login
	 * @return Un objeto Usuario con todos sus datos cargados
	 * @throws excepciones.seguridad.CredencialesInvalidasException Si el usuario no existe en la base de datos
	 */
	public Usuario buscarPorUsername(String username) {
	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;	
		
	    try {
	        con = ConexionDB.conectar();
	        String sql = "SELECT * FROM usuarios WHERE usuario = ?";
	        ps = con.prepareStatement(sql);
	        ps.setString(1, username);
	        rs = ps.executeQuery();

	        if (rs.next()) {
	            Usuario user = new Usuario();
	            
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
	        throw new CredencialesInvalidasException("El usuario introducido no existe");
	    } catch (SQLException e) {
	    	throw new PersistenciaException("Error al consultar usuario en base de datos");
	    } finally {
	        ConexionDB.cerrar(con);
	    }
	}
	
	
	public void verificarPassword(String passwordIntroducida, String passwordBaseDatos) {

        if (!passwordIntroducida.equals(passwordBaseDatos)) {
            throw new CredencialesInvalidasException("El usuario o la contraseña introducidos son incorrectos");
        }
    }
	
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
	    	throw new PersistenciaException("No se ha validado correctamente al usuario");
	    } finally {
	        ConexionDB.cerrar(con);
	    }
	    return null;
	}

	/**
	 * Inserta un nuevo usuario en la base de datos 
	 * Tras la inserción recupera el ID autogenerado para construir y asignarle su identificador 
	 * de cuenta documental 
	 * @param user El objeto Usuario con los datos extraídos del formulario de registro
	 * @return true si el alta y la actualización de su número de cuenta fueron exitosas
	 * @throws excepciones.persistencia.PersistenciaException Si ocurre un fallo en la transacción SQL
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
            throw new PersistenciaException("Error en registro: " + e.getMessage(), e);
        } finally {
            ConexionDB.cerrar(con);
        }
    }
}