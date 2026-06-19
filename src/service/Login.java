package service;

import dao.UsuariosDao;
import modelo.Usuario;

/**
 * Se encarga en exclusiva de la puerta de entrada a la app.
 * Comprueba que el usuario exista y que la contraseña tecleada coincida con la de la base de datos
 */
public class Login {

	private UsuariosDao uDao;
	
	public Login() {
		super();
		this.uDao = new UsuariosDao();
	}

	/**
	 * Intenta loguear al usuario buscando su nombre y verificando su contraseña encriptada
	 * @param username El nombre que ha introducido el usuario
	 * @param password La contraseña normal, tal cual la ha escrito
	 * @return El usuario con todos sus datos si ha acertado
	 * @throws excepciones.seguridad.CredencialesInvalidasException Si se equivoca de nombre o de clave
	 */
	public Usuario inicioDeSesion(String username, String password) throws excepciones.seguridad.CredencialesInvalidasException {
		
		Usuario u = uDao.buscarPorUsername(username);
		
		if (u != null && util.GestorPassword.verificar(password, u.getContrasenia())) {
			
			return u;
			
		} else {
			throw new excepciones.seguridad.CredencialesInvalidasException("Usuario o contraseña incorrectos");
		}
	}
}