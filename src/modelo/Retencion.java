/**
 * 
 */
package modelo;

/**
 * 
 */
public class Retencion {

	private String idRetencion;
	private double monto;
	private boolean estado;
	private String idOperacion;
	private String idCuenta;
	
	public Retencion(String idRetencion, double monto, boolean estado, String idOperacion, String idCuenta) {
		super();
		this.idRetencion = idRetencion;
		this.monto = monto;
		this.estado = estado;
		this.idOperacion = idOperacion;
		this.idCuenta = idCuenta;
	}

	public String getIdRetencion() {
		return idRetencion;
	}

	public void setIdRetencion(String idRetencion) {
		this.idRetencion = idRetencion;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getIdOperacion() {
		return idOperacion;
	}

	public void setIdOperacion(String idOperacion) {
		this.idOperacion = idOperacion;
	}

	public String getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(String idCuenta) {
		this.idCuenta = idCuenta;
	}
}
