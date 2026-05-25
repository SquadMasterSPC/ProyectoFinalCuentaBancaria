/**
 * 
 */
package modelo;

import java.util.List;

/**
 * 
 */
public class DatosCliente {

	private int id;
	private String nombre;
	private String apellido;
	private String correo;
	private String rol;
	private List<CuentaBancaria> cuentas;
	
	public DatosCliente(int id, String nombre, String apellido, String correo, String rol,
			List<CuentaBancaria> cuentas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.rol = rol;
		this.cuentas = cuentas;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public String getRol() {
		return rol;
	}

	public List<CuentaBancaria> getCuentas() {
		return cuentas;
	}

	
}
