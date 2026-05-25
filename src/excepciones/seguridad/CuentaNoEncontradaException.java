/**
 * 
 */
package excepciones.seguridad;

import excepciones.BancaAppException;

/**
 * 
 */
public class CuentaNoEncontradaException extends BancaAppException{

	public CuentaNoEncontradaException(String mensaje) {
		super(mensaje);
		
	}
	
	public CuentaNoEncontradaException(String mensaje, Throwable causa) {
		super(mensaje, causa);
		
	}

}
