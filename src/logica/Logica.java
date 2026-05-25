/**
 * 
 */
package logica;
import modelo.CuentaBancaria;
/**
 * 
 */
public class Logica {


	public Logica() {
		super();
	}

	public Double validarSaldoCuenta(CuentaBancaria cuentaSeleccionada) {
		return cuentaSeleccionada.getSaldo();
	}
	
}
