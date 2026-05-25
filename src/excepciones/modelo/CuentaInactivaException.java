/**
 * 
 */
package excepciones.modelo;

import excepciones.BancaAppException;

/**
 * 
 */
public class CuentaInactivaException extends BancaAppException{

	public CuentaInactivaException(String mensaje) {
		super(mensaje);

	}
	
	public CuentaInactivaException(String mensaje, Throwable causa) {
		super(mensaje, causa);

	}

}
