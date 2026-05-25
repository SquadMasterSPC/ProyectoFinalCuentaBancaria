/**
 * 
 */
package excepciones.modelo;

import excepciones.BancaAppException;

/**
 * 
 */
public class SaldoInsuficienteException extends BancaAppException{

	public SaldoInsuficienteException(String mensaje) {
		super(mensaje);

	}
	
	public SaldoInsuficienteException(String mensaje, Throwable causa) {
		super(mensaje, causa);

	}

}
