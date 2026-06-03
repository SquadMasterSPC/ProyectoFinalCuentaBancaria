package modelo;

import java.sql.Date;
import excepciones.vista.DatoInvalidoException;

public class Ingreso extends Operacion{

	public Ingreso(String idOperacion, double dineroEnMovimiento, Date fecha) {
		super(idOperacion, dineroEnMovimiento, fecha);
	}
	
	/*
	 * Ingresa en la cuenta la cantidad ingresada
	 */
	public boolean ingresar(CuentaBancaria cuenta, double cantidad) {
		if(cantidad <= 0) {
			throw new DatoInvalidoException("La cantidad a ingresar debe ser mayor que cero.");
		}
		
		cuenta.setSaldo(cuenta.getSaldo() + cantidad);
		this.setDineroEnMovimiento(cantidad);
		return true; 
	}
}