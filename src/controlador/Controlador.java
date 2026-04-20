/**
 * 
 */
package controlador;
import java.security.KeyStore.PrivateKeyEntry;

import modelo.Administrador;
import modelo.BackupDeRegistros;
import modelo.Cliente;
import modelo.CuentaBancaria;
import modelo.Empleado;
import modelo.Estadisticas;
import modelo.HistorialDeSesiones;
import modelo.Incidencia;
import modelo.Ingreso;
import modelo.Operacion;
import modelo.Reintegro;
import modelo.Retencion;
import modelo.Tarjeta;
import modelo.Transferencia;
import modelo.Usuario;
import modelo.VerificarIdentidad;
/**
 * 
 */
public class Controlador {

	private Administrador administrador;
	private BackupDeRegistros backUp;
	private Cliente cliente;
	private CuentaBancaria cBancaria;
	private Empleado empleado;
	private Estadisticas estadisticas;
	private HistorialDeSesiones hDeSesiones;
	private Incidencia incidencia;
	private Ingreso ingreso;
	private Operacion operacion;
	private Reintegro reintegro;
	private Retencion retencion;
	private Tarjeta tarjeta;
	private Transferencia transferencia;
	private Usuario usuario;
	private VerificarIdentidad vIdentidad;
	
	public Controlador() {
		super();
		administrador = new Administrador(null, null, null, null, null);
		backUp = new BackupDeRegistros(null, null);
		cliente = new Cliente(null, null, null, null, null, 0);
		cBancaria = new CuentaBancaria(null, null, 0, 0, null);
		empleado = new Empleado(null, null, null, null, null);
		estadisticas = new Estadisticas(null, null);
		hDeSesiones = new HistorialDeSesiones();
		incidencia = new Incidencia(null, null, null, false, null, null);
		ingreso = new Ingreso(null, 0, null);
		operacion = new Operacion(null, 0, null);
		reintegro = new Reintegro(null, 0, null);
		retencion = new Retencion(null, 0, false, null, null);
		tarjeta = new Tarjeta(null, 0, 0);
		transferencia = new Transferencia(null, 0, null);
		usuario = new Usuario(null, null, null, null, null);
		vIdentidad = new VerificarIdentidad(null, null, false, null, null);
	}
	
	
	
}
