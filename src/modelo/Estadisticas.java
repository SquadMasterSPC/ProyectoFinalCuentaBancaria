/**
 * 
 */
package modelo;

import java.sql.Date;

/**
 * 
 */
public class Estadisticas {
	
	private String idEstadisticas;
	private Date fechaCreacion;
	
	public Estadisticas(String idEstadisticas, Date fechaCreacion) {
		super();
		this.idEstadisticas = idEstadisticas;
		this.fechaCreacion = fechaCreacion;
	}

	public String getIdEstadisticas() {
		return idEstadisticas;
	}

	public void setIdEstadisticas(String idEstadisticas) {
		this.idEstadisticas = idEstadisticas;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	
}
