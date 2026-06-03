package dao;

import java.security.interfaces.RSAKey;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.CuentaBancaria;
import vista.Vista;
import modelo.DatosCliente;

public class CuentasDao {

	private CuentaBancaria cuenta;
	private Vista vista;
	private DatosCliente datos;

	public CuentasDao(CuentaBancaria cuenta) {
		super();
		vista = new Vista();
		datos = new DatosCliente(0, null, null, null, null, null);
		// cuenta = new CuentaBancaria(null, null, 0, 0, null);

	}

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
			System.out.println("Error al obtener cuentas: " + e.getMessage());
		} finally {
			ConexionDB.cerrar(con);
		}
		return lista;
	}

	/*
	 * Actualizamos el valor del saldo de la cuenta seleccionada según
	 * la operación elegida
	 */
	public boolean actualizarSaldo(String numCuenta, double cantidad) {
		Connection con = null;
		try {
			con = ConexionDB.conectar();
			String sql = "UPDATE CuentaBancaria SET saldo = saldo + ? WHERE numeroCuenta = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDouble(1, cantidad);
			ps.setLong(2, Long.parseLong(numCuenta));
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			return false;
		} finally {
			ConexionDB.cerrar(con);
		}
	}

	/*
	 * Creamos una cuenta que le pertenecera a un usuario y se le asignara
	 * un nombre y un numero de cuenta
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
			System.out.println("Error SQL al crear cuenta: " + e.getMessage());
			return false;
		} finally {
			ConexionDB.cerrar(con);
		}
	}

	/*
	 * Obtenemos una cuenta bancaria de la base de datos utilizando
	 * su número de cuenta
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
			}
		} catch (SQLException e) {
			System.out.println("Error al obtener cuenta: " + e.getMessage());
		} finally {
			ConexionDB.cerrar(con);
		}

		return cuenta;
	}

	/*
	 * Obtenemos todos los movimientos relacionados con la cuenta del cliente
	 * seleccionada
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
			System.out.println("Error al consultar movimientos: " + e.getMessage());
		} finally {
			ConexionDB.cerrar(con);
		}
		return historial;
	}

	/*
	 * Permitimos que los empleados puedan sacar todos los datos del cliente
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
				return null;
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

		}

		catch (SQLException e) {
			System.out.println("Error al obtener datos del cliente: " + e.getMessage());
			return null;
		} finally {
			ConexionDB.cerrar(con);
		}

	}

	/*
	 * Registramos en el historial de movimientos el movimiento realizado por
	 * cualquier cuenta
	 */
	public boolean registrarMovimiento(String numCuenta, String tipo, double cantidad) throws SQLException {
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
			throw new SQLException("Ha reventao en el metodo registrar movimiento");
			//System.out.println("Error al registrar movimiento: " + e.getMessage());
			//return false;
		} finally {
			ConexionDB.cerrar(con);
		}
	}
}