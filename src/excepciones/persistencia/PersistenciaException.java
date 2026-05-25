/**
 * 
 */
package excepciones.persistencia;

import excepciones.BancaAppException;

/**
 * 
 */
public class PersistenciaException extends BancaAppException{

	public PersistenciaException(String mensaje) {
		super(mensaje);

	}
	
	public PersistenciaException(String mensaje, Throwable causa) {
		super(mensaje, causa);

	}

}
