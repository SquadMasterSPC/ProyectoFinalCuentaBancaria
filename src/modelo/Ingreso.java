package modelo;

import java.sql.Date;
import dao.CuentasDao;

public class Ingreso extends Operacion{

	private CuentasDao cDao;

	public Ingreso(String idOperacion, double dineroEnMovimiento, Date fecha) {
		super(idOperacion, dineroEnMovimiento, fecha);
		cDao = new CuentasDao(null);
	}
	
	/*
	 * Ingresa en la cuenta la cantidad ingresada
	 */
	public boolean ingresar(CuentaBancaria cuenta, double cantidad) {
		if(cantidad > 0) {
			if(cDao.actualizarSaldo(cuenta.getNumCuenta(), cantidad)) {
				cuenta.setSaldo(cuenta.getSaldo() + cantidad);
				this.setDineroEnMovimiento(cantidad);
				cDao.registrarMovimiento(cuenta.getNumCuenta(), "INGRESO", cantidad);
				return true; 
			}
		}
		return false;
	}
}