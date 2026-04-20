/**
 * 
 */
package modelo;

import java.sql.Date;

/**
 * 
 */
public class Incidencia {

	private String idIncidencia;
	private String descripcion;
	private Date fecha;
	private boolean esResuelto;
	private String idCliente;
	private String idEmpleado;
	
	public Incidencia(String idIncidencia, String descripcion, Date fecha, boolean esResuelto, String idCliente,
			String idEmpleado) {
		super();
		this.idIncidencia = idIncidencia;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.esResuelto = esResuelto;
		this.idCliente = idCliente;
		this.idEmpleado = idEmpleado;
	}

	public String getIdIncidencia() {
		return idIncidencia;
	}

	public void setIdIncidencia(String idIncidencia) {
		this.idIncidencia = idIncidencia;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isEsResuelto() {
		return esResuelto;
	}

	public void setEsResuelto(boolean esResuelto) {
		this.esResuelto = esResuelto;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(String idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
}
