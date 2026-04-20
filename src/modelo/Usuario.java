/**
 * 
 */
package modelo;

/**
 * 
 */
public class Usuario {

	private String numeroDeCuenta;
	private String nombre;
	private String apellido;
	private String correoElectronico;
	private String contrasenia;
	
	public Usuario(String numeroDeCuenta, String nombre, String apellido, String correoElectronico,
			String contrasenia) {
		super();
		this.numeroDeCuenta = numeroDeCuenta;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correoElectronico = correoElectronico;
		this.contrasenia = contrasenia;
	}
	
	public boolean iniciarSesion() {
		return false;
	}
	
	public void cerrarSesion() {
		
	}

	public String getNumeroDeCuenta() {
		return numeroDeCuenta;
	}

	public void setNumeroDeCuenta(String numeroDeCuenta) {
		this.numeroDeCuenta = numeroDeCuenta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	
	
}
