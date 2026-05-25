/**
 * 
 */
package excepciones;

/**
* Excepción base de la aplicación bancaria.
* Todas las excepciones propias del sistema heredan de esta clase.
* Al heredar de RuntimeException no es necesario declararla con throws.
*
* @author Mario Lizón
*/
public class BancaAppException extends RuntimeException{

	public BancaAppException(String mensaje) {
		super(mensaje);
	}

	public BancaAppException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
