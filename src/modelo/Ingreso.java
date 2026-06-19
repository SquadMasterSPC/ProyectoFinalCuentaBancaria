package modelo;

import java.sql.Date;
import excepciones.vista.DatoInvalidoException;

public class Ingreso extends Operacion{

	
	public Ingreso(String idOperacion, double dineroEnMovimiento, Date fecha) {
		super(idOperacion, dineroEnMovimiento, fecha);
	}
	
	/**
	 * Suma el dinero indicado al saldo de la cuenta
	 * @param cuenta La cuenta donde se va a meter el dinero
	 * @param cantidad El dinero a sumar
	 * @return true si el ingreso se realiza sin problemas
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