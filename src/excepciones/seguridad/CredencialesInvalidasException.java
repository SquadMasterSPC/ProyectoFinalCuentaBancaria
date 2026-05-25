/**
 * 
 */
package excepciones.seguridad;

import excepciones.BancaAppException;

/**
 * 
 */
public class CredencialesInvalidasException extends BancaAppException{

	public CredencialesInvalidasException(String mensaje) {
		super(mensaje);

	}

	public CredencialesInvalidasException(String mensaje, Throwable causa) {
		super(mensaje, causa);

	}
}
