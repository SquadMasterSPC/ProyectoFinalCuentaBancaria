/**
 * 
 */
package modelo;

/**
 * 
 */
public class CuentaBancaria {

	private String nombre;
	private String numCuenta;
	private double saldo;
	private double saldoRetenido;
	private String idBloqueadoPor;
	
	public CuentaBancaria(String nombre, String numCuenta, double saldo, double saldoRetenido, String idBloqueadoPor) {
		super();
		this.nombre = nombre;
		this.numCuenta = numCuenta;
		this.saldo = saldo;
		this.saldoRetenido = saldoRetenido;
		this.idBloqueadoPor = idBloqueadoPor;
	}
	
	public boolean ingresar() {
		return false;
	}
	
	public boolean reintegrar() {
		return false;
	}
	
	public boolean transferir() {
		return false;
	}
	
	public double getSaldoDisponible() {
		return 0.0;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumCuenta() {
		return numCuenta;
	}

	public void setNumCuenta(String numCuenta) {
		this.numCuenta = numCuenta;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public double getSaldoRetenido() {
		return saldoRetenido;
	}

	public void setSaldoRetenido(double saldoRetenido) {
		this.saldoRetenido = saldoRetenido;
	}

	public String getIdBloqueadoPor() {
		return idBloqueadoPor;
	}

	public void setIdBloqueadoPor(String idBloqueadoPor) {
		this.idBloqueadoPor = idBloqueadoPor;
	}
	
	
}
