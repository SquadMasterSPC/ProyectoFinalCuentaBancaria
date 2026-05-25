/**
 * 
 */
package excepciones.seguridad;

import excepciones.BancaAppException;

/**
 * 
 */
public class UsuarioNoRegistradoException extends BancaAppException{

	public UsuarioNoRegistradoException(String mensaje) {
		super(mensaje);
	}
	
	public UsuarioNoRegistradoException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
	
}
