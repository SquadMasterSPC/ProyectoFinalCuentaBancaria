/**
 * 
 */
package modelo;

import java.sql.Date;

/**
 * 
 */
public class BackupDeRegistros {
	
	private String idBackUp;
	private Date fechaCreacion;
	
	public BackupDeRegistros(String idBackUp, Date fechaCreacion) {
		super();
		this.idBackUp = idBackUp;
		this.fechaCreacion = fechaCreacion;
	}

	public String getIdBackUp() {
		return idBackUp;
	}

	public void setIdBackUp(String idBackUp) {
		this.idBackUp = idBackUp;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	
}
