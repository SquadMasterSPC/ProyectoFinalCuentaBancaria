/**
 * 
 */
package modelo;

import java.sql.Date;

/**
 * 
 */
public class Operacion {

	private String idOperacion;
	private double dineroEnMovimiento;
	private Date fecha;
	
	public Operacion(String idOperacion, double dineroEnMovimiento, Date fecha) {
		super();
		this.idOperacion = idOperacion;
		this.dineroEnMovimiento = dineroEnMovimiento;
		this.fecha = fecha;
	}

	public String getIdOperacion() {
		return idOperacion;
	}

	public void setIdOperacion(String idOperacion) {
		this.idOperacion = idOperacion;
	}

	public double getDineroEnMovimiento() {
		return dineroEnMovimiento;
	}

	public void setDineroEnMovimiento(double dineroEnMovimiento) {
		this.dineroEnMovimiento = dineroEnMovimiento;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
