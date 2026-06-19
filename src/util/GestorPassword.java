/**
 * 
 */
package util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Gestiona el cifrado y verificación de contraseñas con BCrypt.
 *
 * Esta clase es el único punto del proyecto que conoce BCrypt. Si en el futuro
 * se cambia el algoritmo de cifrado, solo hay que modificar esta clase. El
 * resto del proyecto no se toca.
 *
 * ¿Cómo funciona BCrypt internamente?
 *
 * 1. Al HASHEAR genera un salt aleatorio y lo mezcla con la contraseña. El
 * resultado incluye el salt dentro del propio hash:
 *
 * "1234" → $2a$10$Kd3X7zQmP9rYvN... (salt + hash juntos)
 *
 * 2. Al VERIFICAR extrae el salt del hash guardado, recalcula y compara. Por
 * eso nunca se comparan hashes directamente.
 *
 * El parámetro STRENGTH (cost factor) controla cuántas iteraciones hace el
 * algoritmo internamente: 2^STRENGTH rondas. A mayor valor, más lento para
 * atacantes, imperceptible para usuarios. El valor 10 es el estándar
 * recomendado para aplicaciones en producción.
 * 
 * 
 * @author Pedro
 *
 */
public class GestorPassword {
	// Coste del algoritmo: 2^10 = 1024 iteraciones internas.
	// Rango recomendado: 10-12. No bajar de 10 en producción.
	private static final int STRENGTH = 10;

	/**
	 * Genera el hash BCrypt de una contraseña en texto plano.
	 *
	 * Se usa en el momento del registro o del cambio de contraseña. NUNCA se
	 * almacena la contraseña original, solo este hash.
	 *
	 * @param passwordPlano la contraseña tal como la escribe el usuario
	 * @return el hash BCrypt listo para guardar en la base de datos
	 */
	public static String hashear(String passwordPlano) {
		return BCrypt.hashpw(passwordPlano, BCrypt.gensalt(STRENGTH));
	}

	/**
	 * Comprueba si una contraseña en texto plano coincide con su hash.
	 *
	 * Se usa en el login. BCrypt extrae el salt del hash guardado, recalcula
	 * internamente y devuelve true si coinciden.
	 *
	 * @param passwordPlano la contraseña introducida por el usuario
	 * @param hashGuardado  el hash recuperado de la base de datos
	 * @return true si la contraseña es correcta, false si no lo es
	 */
	public static boolean verificar(String passwordPlano, String hashGuardado) {
		return BCrypt.checkpw(passwordPlano, hashGuardado);
	}
}
