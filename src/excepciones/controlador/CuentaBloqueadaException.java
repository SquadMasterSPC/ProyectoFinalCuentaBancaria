/**
 * 
 */
package excepciones.controlador;

import excepciones.BancaAppException;

/**
 * 
 */
public class CuentaBloqueadaException extends BancaAppException{

	public CuentaBloqueadaException(String mensaje) {
		super(mensaje);

	}
	
	public CuentaBloqueadaException(String mensaje, Throwable causa) {
		super(mensaje, causa);

	}

}
