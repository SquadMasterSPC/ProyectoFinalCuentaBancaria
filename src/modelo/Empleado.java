/**
 * 
 */
package modelo;

/**
 * Representa a un trabajador del banco con permisos especiales para revisar 
 * los historiales de los clientes y atender sus incidencias
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
