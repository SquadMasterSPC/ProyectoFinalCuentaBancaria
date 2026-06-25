package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.CuentaBancaria;
import vista.Vista;
import modelo.DatosCliente;
import excepciones.persistencia.PersistenciaException;
import excepciones.seguridad.CuentaNoEncontradaException;


/**
 * Clase que se encarga de gestionar la persistencia de los datos
 * de las cuentas y sus movimientos
 */
public class CuentasDao {

	private CuentaBancaria cuenta;
	private Vista vista;
	private DatosCliente datos;

	public CuentasDao(CuentaBancaria cuenta) {
		super();
		vista = new Vista();
		datos = new DatosCliente(0, null, null, null, null, null);

	}

	/**
	 * Recupera todas las cuentas bancarias asociadas a un cliente específico
	 * @param idUsuario El identificador único del usuario propietario de las cuentas
	 * @return Una lista de objetos CuentaBancaria devolverá una lista vacía si no tiene cuentas
	 */
	public List<CuentaBancaria> obtenerCuentasPorUsuario(int idUsuario) {
		List<CuentaBancaria> lista = new ArrayList<>();
		Connection con = null;
		try {
			con = ConexionDB.conectar();

			String sql = "SELECT * FROM CuentaBancaria WHERE idUsuario = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, idUsuario);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				lista.add(new CuentaBancaria(rs.getString("nombre"), String.valueOf(rs.getLong("numeroCuenta")),
						rs.getDouble("saldo"), 0.0, null));
			}
		} catch (SQLException e) {
			throw new PersistenciaException("Error al obtener las cuentas del usuario", e);
		} finally {
			ConexionDB.cerrar(con);
		}
		return lista;
	}

	/**
	 * Modifica el saldo actual de una cuenta en la base de datos
	 * @param numCuenta El identificador de la cuenta a actualizar
	 * @param cantidad El importe a sumar (puede ser negativo para restarlo en reintegros o transferencias)
	 * @return true si el saldo se actualizó correctamente
	 * @throws excepciones.seguridad.CuentaNoEncontradaException si la cuenta no existe en el sistema
	 */
	public boolean actualizarSaldo(String numCuenta, double cantidad) {
		Connection con = null;
		try {
			con = ConexionDB.conectar();
			String sql = "UPDATE CuentaBancaria SET saldo = saldo + ? WHERE numeroCuenta = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDouble(1, cantidad);
			ps.setLong(2, Long.parseLong(numCuenta));
			
			if (ps.executeUpdate() == 0) {
				throw new CuentaNoEncontradaException("No se pudo actualizar el saldo: la cuenta no existe.");
			}
			return true;
		} catch (SQLException e) {
			throw new PersistenciaException("Error crítico en la base de datos al actualizar el saldo", e);
		} finally {
			ConexionDB.cerrar(con);
		}
	}

	/**
	 * Inicia la creación de una cuenta bancaria en el sistema asociada a un cliente
	 * @param idUsuario El identificador del cliente que será el titular
	 * @param nombre El alias o nombre descriptivo de la cuenta
	 * @param numAleatorio El número de cuenta bancaria generado para este registro
	 * @return true si la inserción en la base de datos fue exitosa
	 */
	public boolean crearCuenta(int idUsuario, String nombre, long numAleatorio) {
		Connection con = null;
		try {
			con = ConexionDB.conectar();
			String sql = "INSERT INTO CuentaBancaria (numeroCuenta, idUsuario, saldo, activa, nombre) VALUES (?, ?, 0.0, true, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, numAleatorio);
			ps.setInt(2, idUsuario);
			ps.setString(3, nombre);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new PersistenciaException("Error al crear la cuenta bancaria", e);
		} finally {
			ConexionDB.cerrar(con);
		}
	}

	/**
	 * Busca y devuelve los datos exactos de una cuenta bancaria a partir de su número identificador
	 * @param numCuenta El número único de la cuenta
	 * @return Un objeto CuentaBancaria con los datos extraídos de la base de datos
	 * @throws excepciones.seguridad.CuentaNoEncontradaException Si no existe ninguna cuenta con ese número
	 */
	public CuentaBancaria obtenerCuentaPorNumero(int numCuenta) {
		Connection con = null;
		CuentaBancaria cuenta = null;
		String sql = "SELECT * FROM CuentaBancaria WHERE numeroCuenta = ?";

		try {
			con = ConexionDB.conectar();
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, numCuenta);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				cuenta = new CuentaBancaria(rs.getString("nombre"), String.valueOf(rs.getInt("numeroCuenta")),
						rs.getDouble("saldo"), 0.0, null);
				return cuenta;
			}
			
			throw new CuentaNoEncontradaException("La cuenta destino (" + numCuenta + ") no existe.");

		} catch (SQLException e) {
			throw new PersistenciaException("Error al consultar la cuenta por número", e);
		} finally {
			ConexionDB.cerrar(con);
		}
	}

	/**
	 * Extrae el registro cronológico de todas las operaciones realizadas en una cuenta específica
	 * @param numCuenta El número de la cuenta a consultar
	 * @return Una lista de cadenas de texto preformateadas con la fecha, tipo y cantidad de cada movimiento
	 */
	public List<String> obtenerHistorialMovimientos(String numCuenta) {
		List<String> historial = new ArrayList<>();
		Connection con = null;
		String sql = "SELECT tipo, cantidad, fecha FROM movimientos WHERE numeroCuenta = ? ORDER BY fecha DESC";
		try {
			con = ConexionDB.conectar();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, Long.parseLong(numCuenta));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String fila = rs.getTimestamp("fecha") + " | " + rs.getString("tipo") + " | " + rs.getDouble("cantidad")
						+ "€";
				historial.add(fila);
			}
		} catch (SQLException | NumberFormatException e) {
			throw new PersistenciaException("Error al recuperar el historial de movimientos", e);
		} finally {
			ConexionDB.cerrar(con);
		}
		return historial;
	}

	/**
	 * Recopila toda la información de un cliente (datos personales y cuentas asociadas)
	 * cruzando las tablas de usuarios y cuentas bancarias. Ideal para la vista de empleados
	 * @param nombreBusqueda el nombre de usuario del cliente a consultar
	 * @return Un objeto DatosCliente empaquetando toda su información
	 * @throws excepciones.seguridad.CuentaNoEncontradaException Si el usuario no existe
	 */
	public DatosCliente mostrarDatosCompletosCliente(String nombreBusqueda) {
		Connection con = null;
		try {
			con = ConexionDB.conectar();

			String sqlUser = "SELECT id, usuario, apellido, correo, rol FROM usuarios WHERE usuario = ?";
			PreparedStatement psUser = con.prepareStatement(sqlUser);
			psUser.setString(1, nombreBusqueda);
			ResultSet rsUser = psUser.executeQuery();

			if (!rsUser.next()) {
				throw new CuentaNoEncontradaException("El cliente buscado no existe.");
			}

			int idUsuario = rsUser.getInt("id");
			String nombre = rsUser.getString("usuario");
			String apellido = rsUser.getString("apellido");
			String correo = rsUser.getString("correo");
			String rol = rsUser.getString("rol");
			List<CuentaBancaria> cuentas = new ArrayList<>();
			String sqlCuentas = "SELECT numeroCuenta, nombre, saldo, activa FROM CuentaBancaria WHERE idUsuario = ?";
			PreparedStatement psCuentas = con.prepareStatement(sqlCuentas);
			psCuentas.setInt(1, idUsuario);
			ResultSet rsCuentas = psCuentas.executeQuery();

			while (rsCuentas.next()) {
				cuentas.add(new CuentaBancaria(rsCuentas.getString("nombre"),
						String.valueOf(rsCuentas.getLong("numeroCuenta")), rsCuentas.getDouble("saldo"), 0.0, null));
			}
			return new DatosCliente(idUsuario, nombre, apellido, correo, rol, cuentas);

		} catch (SQLException e) {
			throw new PersistenciaException("Error al extraer los datos completos del cliente", e);
		} finally {
			ConexionDB.cerrar(con);
		}
	}

	/**
	 * Guarda un nuevo apunte en el historial de movimientos de la cuenta
	 * @param numCuenta El número de la cuenta afectada
	 * @param tipo La descripción de la operación
	 * @param cantidad El valor monetario operado
	 * @return true si el registro se completó correctamente
	 */
	public boolean registrarMovimiento(String numCuenta, String tipo, double cantidad) {
		Connection con = null;
		String sql = "INSERT INTO movimientos (numeroCuenta, tipo, cantidad, fecha) VALUES (?, ?, ?, NOW())";

		try {
			con = ConexionDB.conectar();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, Long.parseLong(numCuenta));
			ps.setString(2, tipo);
			ps.setDouble(3, cantidad);

			return ps.executeUpdate() > 0;
		} catch (SQLException | NumberFormatException e) {
			throw new PersistenciaException("Error al registrar movimiento en el historial", e);
		} finally {
			ConexionDB.cerrar(con);
		}
	}
	
	//ActualizarSaldo atomizado
	public boolean actualizarSaldo(Connection con, String numCuenta, double cantidad) throws SQLException {
		String sql = "UPDATE CuentaBancaria SET saldo = saldo + ? WHERE numeroCuenta = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setDouble(1, cantidad);
			ps.setLong(2, Long.parseLong(numCuenta));
			
			if (ps.executeUpdate() == 0) {
				throw new excepciones.seguridad.CuentaNoEncontradaException("No se pudo actualizar el saldo: la cuenta no existe.");
			}
			return true;
		}
	}

	public boolean registrarMovimiento(Connection con, String numCuenta, String tipo, double cantidad) throws SQLException {
		String sql = "INSERT INTO movimientos (numeroCuenta, tipo, cantidad, fecha) VALUES (?, ?, ?, NOW())";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setLong(1, Long.parseLong(numCuenta));
			ps.setString(2, tipo);
			ps.setDouble(3, cantidad);

			return ps.executeUpdate() > 0;
		}
	}
}