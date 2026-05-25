/**
 * 
 */
package modelo;

import java.sql.Date;

/**
 * 
 */
public class HistorialDeSesiones {
	
	private String idSesion;
	private Date fechaInicio;
	private Date fechaFin;
	private String ip;
	private String tipoAuth;
	private Usuario usuario;
	
	public HistorialDeSesiones() {
		super();
		this.usuario = new Usuario("TEMP000", "Temporal", "", "", "", 0.0, "CLIENTE", 0);
		
	}

	public HistorialDeSesiones(String idSesion, Date fechaInicio, Date fechaFin, String ip, String tipoAuth,
			Usuario usuario) {
		super();
		this.idSesion = idSesion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.ip = ip;
		this.tipoAuth = tipoAuth;
		this.usuario = usuario;
	}
	
	
	
}
