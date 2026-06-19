/**
 * 
 */
package logica;
import modelo.CuentaBancaria;
/**
 * Clase auxiliar para hacer comprobaciones sueltas que no encajan directamente 
 * en el acceso a datos ni en los servicios principales.
 */
public class Logica {


	public Logica() {
		super();
	}

	/**
	 * Comprueba y devuelve el dinero que tiene disponible una cuenta
	 * @param cuentaSeleccionada La cuenta que queremos consultar
	 * @return El saldo actual de la cuenta
	 */
	public Double validarSaldoCuenta(CuentaBancaria cuentaSeleccionada) {
		return cuentaSeleccionada.getSaldo();
	}
	
}
