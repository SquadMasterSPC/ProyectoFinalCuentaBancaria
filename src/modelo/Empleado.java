/**
 * 
 */
package modelo;

/**
 * 
 */
public class Empleado extends Usuario{

	public Empleado(String numeroDeCuenta, String nombre, String apellido, String correoElectronico,
			String contrasenia) {
		super(numeroDeCuenta, nombre, apellido, correoElectronico, contrasenia);
	}

	public void gestionarClientes() {
		
	}
	
	public boolean verificarIdentidad() {
		return false;
	}
	
}
