/**
 * 
 */
package service;

import dao.UsuariosDao;
import modelo.Usuario;

/**
 * 
 */
public class Login {

	private UsuariosDao uDao;
	
	
	
	public Login() {
		super();
		uDao = new UsuariosDao();
		
	}

	public Usuario inicioDeSesion(String usuario, String contrasenia) {
		return uDao.validarUsuario(usuario, contrasenia);
	}
	
	
}
