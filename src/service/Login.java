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
	//private Usuario u;
	
	
	public Login() {
		super();
		this.uDao = new UsuariosDao();
		//u = new Usuario(null, null, null, null, null, 0, null, 0);
	}

	public Usuario inicioDeSesion(String username, String password) {
		
		Usuario u = uDao.buscarPorUsername(username);
		
		uDao.verificarPassword(password, u.getContrasenia());
		
		return uDao.validarUsuario(username, password);
	}
	
	
}
