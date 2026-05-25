/**
 * 
 */
package validacion;

import java.util.Iterator;
import java.util.Scanner;

/**
 * 
 */
public class Validacion {

	
	public Validacion() {
		super();
	}

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
	
	/*
	 * Validamos que el String introducido no sea solo caracteres en blanco
	 * permite al usuario dejar caracteres en blanco pero si todo el String
	 * esta en blanco le devuelve un false
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
