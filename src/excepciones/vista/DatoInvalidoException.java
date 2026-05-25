/**
 * 
 */
package excepciones.vista;

import excepciones.BancaAppException;

/**
 * 
 */
public class DatoInvalidoException extends BancaAppException{

	public DatoInvalidoException(String mensaje) {
		super(mensaje);

	}
	
	public DatoInvalidoException(String mensaje, Throwable causa) {
		super(mensaje, causa);

	}

}
