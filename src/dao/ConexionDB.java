/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 ** ConexionDBMejorada — versión avanzada de ConexionDB.
 *
 * Mejoras respecto a ConexionDB:
 * 
 * 1. URL construida desde constantes → más fácil de cambiar un parámetro.
 * 
 * 2. Properties → forma estándar de pasar configuración al driver JDBC.
 * 
 * 3. Logger → sustituye a System.err; los * mensajes quedan registrados.
 * 
 * 4. Constructor privado → impide crear objetos de esta clase (todos los
 * métodos son static, no necesitamos instancias).
 * 
 * 5. * useSSL=false + UTF-8 + UTC → evita warnings comunes del driver MySQL.
 *
 * IMPORTANTE PARA EL ALUMNO: Esta clase vive en el mismo paquete 'conexion' que
 * ConexionDB. Para cambiar de versión en ProductoDAO solo hay que cambiar UNA
 * línea:
 *
 * // Versión básica: con = ConexionDB.getConnection();
 *
 * // Versión mejorada: con = ConexionDBMejorada.conectar();
 * 
 * @author Pedro
 *
 */
public class ConexionDB {
	// ── Logger ───────────────────────────────────────────────────────────
	// Sustituye a System.err.println(). Permite filtrar mensajes por nivel:
	// INFO → información normal | WARNING → aviso | SEVERE → error grave
	/**
	 * Estamos creando un logger asociado a la clase ConexionDBMejorada, que se usa
	 * para:
	 * 
	 * Registrar mensajes de depuración (info, warning, severe, etc.) Ayudar a
	 * diagnosticar errores Llevar un seguimiento de lo que hace el programa
	 * 
	 */
	private static final Logger LOGGER = Logger.getLogger(ConexionDB.class.getName());

	// ── Parámetros de conexión ───────────────────────────────────────────
	// Separados en constantes → si cambia el servidor, solo tocas aquí
	private static final String NOMBRE_BD = "bancodb";
	private static final String UBICACION = "localhost";
	private static final String PUERTO = "3306";
	private static final String USUARIO = "root";
	private static final String CLAVE = "root"; // XAMPP: vacío

	// ── URL construida desde las constantes anteriores ───────────────────
	// useUnicode + characterEncoding → caracteres especiales (ñ, acentos)
	// serverTimezone=UTC → evita error de zona horaria en MySQL 8
	/**
	 * Incluye cosas importantes:
	 * 
	 * useUnicode=true → soporta acentos characterEncoding=utf-8 → codificación
	 * correcta serverTimezone=UTC → evita errores típicos de MySQL 8
	 * 
	 */
	private static final String URL = "jdbc:mysql://" + UBICACION + ":" + PUERTO + "/" + NOMBRE_BD
			+ "?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";

	// ── Constructor privado ──────────────────────────────────────────────
	// Nadie puede hacer: ConexionDBMejorada obj = new ConexionDBMejorada();
	// Todos los métodos son static → se usan directamente sobre la clase.

	/**
	 * Porque esta clase:
	 * 
	 * No tiene estado Solo tiene métodos static
	 * 
	 * Es una clase utilitaria pura
	 * 
	 */

	public ConexionDB() {
		super();
	}

	/**
	 * Hace esto:
	 * 
	 * Crea un objeto Properties con: usuario contraseña configuración extra Llama
	 * a: DriverManager.getConnection(URL, props);
	 * 
	 * Si todo va bien: ✔ Devuelve la conexión ✔ Registra un mensaje con LOGGER.info
	 * Si falla: ❌ No lanza excepción ✔ Registra el error (LOGGER.severe) ✔ Devuelve
	 * null
	 *
	 * @return Connection abierta, o null si hubo error
	 */
	public static Connection conectar() {

		Connection conexion = null;

		try {
			// Properties: mapa clave-valor con la configuración del driver
			Properties props = new Properties();
			props.put("user", USUARIO);
			props.put("password", CLAVE);
			props.put("useSSL", "false"); // Desactiva SSL en desarrollo local

			conexion = DriverManager.getConnection(URL, props);

			// Logger.info() → mensaje informativo (conexión correcta)
			LOGGER.info("Conexión establecida con la base de datos '" + NOMBRE_BD + "'");

		} catch (SQLException e) {
			// Logger.severe() → error grave. Incluye la excepción completa.
			LOGGER.log(Level.SEVERE, "Error al conectar con la base de datos", e);
		}

		return conexion; // null si falló → el DAO debe comprobarlo
	}

	/**
	 * Cierra la conexión de forma segura:
	 * 
	 * Si con es null → no hace nada Si existe: Intenta cerrarla Registra: ✔ éxito
	 * (info) ⚠ error (warning)
	 *
	 * @param con conexión a cerrar (puede ser null)
	 */
	public static void cerrar(Connection con) {
		if (con != null) {
			try {
				con.close();
				LOGGER.info("Conexión cerrada correctamente.");
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "Error al cerrar la conexión", e);
			}
		}
	}

	/**
	 * La clase toma esta decisión:
	 * 
	 * NO lanza excepciones, devuelve null
	 * 
	 * Eso implica que quien la use debe hacer:
	 * 
	 * Connection con = ConexionDBMejorada.conectar();
	 * 
	 * if (con != null) { // usar conexión } else { // manejar error }
	 * 
	 * 
	 */
	
	// Este es el método que UsuariosDao busca y a veces no encuentra
	public static Connection getConnection() {
	    return conectar(); 
	}


}
