/**
 * 
 */
package modelo;

/**
 * 
 */
public class VerificarIdentidad {

	private String idVerIdentidad;
	private String tipoDocumento;
	private boolean verificado;
	private String idEmpleado;
	private String idCliente;
	
	public VerificarIdentidad(String idVerIdentidad, String tipoDocumento, boolean verificado, String idEmpleado,
			String idCliente) {
		super();
		this.idVerIdentidad = idVerIdentidad;
		this.tipoDocumento = tipoDocumento;
		this.verificado = verificado;
		this.idEmpleado = idEmpleado;
		this.idCliente = idCliente;
	}

	public String getIdVerIdentidad() {
		return idVerIdentidad;
	}

	public void setIdVerIdentidad(String idVerIdentidad) {
		this.idVerIdentidad = idVerIdentidad;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public boolean isVerificado() {
		return verificado;
	}

	public void setVerificado(boolean verificado) {
		this.verificado = verificado;
	}

	public String getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(String idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
}
