/**
 * 
 */
package validacion;


/**
 * Caja de herramientas para comprobar que los datos que mete el usuario por teclado 
 * tienen sentido y no nos van a romper el programa más adelante
 */
public class Validacion {

	
	public Validacion() {
		super();
	}

	/**
	 * Revisa que el texto tenga una arroba y termine en un dominio conocido
	 * @param correoString El correo que ha escrito el usuario
	 * @return true si tiene buena pinta y pasa el filtro
	 */
	public boolean validarCorreos(String correoString) {
		boolean valido;
		
		if (correoString.contains("@")) {
			
			String[] partes = correoString.split("@");
			String dominio = partes[1].toLowerCase();
			
			
			switch (dominio) {
				case "gmail.com": 
				
					return valido = true;
			
				case "hotmail.com":
				
					return valido = true;
				
				case "outlook.com":
				
					return valido = true;
				
				case "yahoo.es":
				
					return valido = true;
				
				default:
					throw new IllegalArgumentException("Unexpected value: " + dominio);
			}	
		}
		return valido = false;
	}
	
	public boolean validarReintentoCuenta(String respuesta) {
		boolean reintento;
		
		if (respuesta.equalsIgnoreCase("SI")) {
			reintento = true;
			return reintento;
		}else if (respuesta.equalsIgnoreCase("NO")) {
			reintento = false;
			return reintento;
		}
		else {
	    	System.err.println("ERROR Se ha introducido una respuesta invalida");
			reintento = false;
			return reintento;
		}
	}
	
	/**
	 * Evita que el usuario nos cuele un texto que sean solo espacios en blanco
	 * @param credencial La palabra o texto a revisar
	 * @return true si al menos hay una letra o número útil
	 */
	public boolean validarCredencialesString(String credencial) {
		boolean valido;
		
		for(int i = 0; i<credencial.length(); i++) {
			if (credencial.charAt(i) != ' ') {
				return valido = true;
			}
		}
		return valido = false;
	}
	
	public boolean validarDineroAOperar(double cantidad) {
		if (cantidad > 0) {
			return true;			
		}
		return false;
	}
}
