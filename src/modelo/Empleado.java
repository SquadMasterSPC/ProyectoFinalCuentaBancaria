/**
 * 
 */
package modelo;

/**
 * 
 */
public class Empleado extends Usuario{

	public Empleado(String numeroDeCuenta, String nombre, String apellido, String correoElectronico,
			String contrasenia, double saldo, String rol, int id) {
		super(numeroDeCuenta, nombre, apellido, correoElectronico, contrasenia, saldo, rol, id);
	}

	public void gestionarClientes() {
		
	}
	
	public boolean verificarIdentidad() {
		return false;
	}
	
}
