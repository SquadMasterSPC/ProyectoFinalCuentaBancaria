/**
 * 
 */
package modelo;

/**
 * 
 */
public class Cliente extends Usuario{
	
	private double saldo;

	public Cliente(String numeroDeCuenta, String nombre, String apellido, String correoElectronico, String contrasenia,
			double saldo) {
		super(numeroDeCuenta, nombre, apellido, correoElectronico, contrasenia);
		this.saldo = saldo;
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
	
	public void verHistorialMovimientos() {
		
	}
	
	public double consultarSaldo() {
		return 0.0;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	
}
