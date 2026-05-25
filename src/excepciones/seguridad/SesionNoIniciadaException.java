/**
 * 
 */
package excepciones.seguridad;

import excepciones.BancaAppException;


/**
 * 
 */
public class SesionNoIniciadaException extends BancaAppException{

	public SesionNoIniciadaException(String mensaje) {
		super(mensaje);
	}

	public SesionNoIniciadaException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
	
	
}
