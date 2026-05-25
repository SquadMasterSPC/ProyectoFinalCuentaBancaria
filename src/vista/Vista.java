package vista;

import java.util.List;
import java.util.Scanner;

import excepciones.vista.DatoInvalidoException;
import validacion.Validacion;
import modelo.CuentaBancaria;
import modelo.DatosCliente;
//import modelo.Usuario;

public class Vista {
    private Scanner sc;
    private Validacion validacion;
    private CuentaBancaria cuentaB;
    
    public Vista() {
        sc = new Scanner(System.in);
        validacion = new Validacion();
        cuentaB = new CuentaBancaria(null, null, 0, 0, null);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public String pedirNombre() {
    	String nombre;
    
    	System.out.println("Introduce tu nombre: ");
        nombre = sc.nextLine();

        if (validacion.validarCredencialesString(nombre)) {
			return nombre;
		}
        else {
        	mensajeErrorCrearCuenta();
        	return null;
		}
    }
    
    public String pedirApellido() {
    	String apellido;
        
    	System.out.println("Introduce tu apellido: ");
        apellido = sc.nextLine();

        if (validacion.validarCredencialesString(apellido)) {
			return apellido;
		}
        else {
        	mensajeErrorCrearCuenta();
        	return null;
		}
    }
    
    public String pedirCorreo() {
    	String correo;
    
        System.out.print("Introduce tu correo electrónico: ");
    	correo = sc.nextLine();
        if(validacion.validarCorreos(correo)) {
        	System.out.println("Correo validado correctamente");
            return correo;
        }
        else {
        	System.out.println("Se ha introducido incorrectamente el correo");
        	mensajeErrorCrearCuenta();
     
        	return null;
		}

    }

    public String pedirContrasenia() {
    	String contrasenia;
    	
        System.out.print("Introduce tu contraseña: ");
        contrasenia = sc.nextLine();

        if (validacion.validarCredencialesString(contrasenia)) {
			return contrasenia;
		}
        else {
        	mensajeErrorCrearCuenta();
        	return null;
		}
        
    }
    
    public int inicioDelSistema() {
    	System.out.println("BIENVENIDO\n");
    	System.out.println("Seleccione la opción que quiera realizar\n");
    	System.out.println("1. Iniciar sesión\n");
    	System.out.println("2. Crear nuevo USUARIO\n");
    	
    	try {
            return Integer.parseInt(sc.nextLine());
        } catch (DatoInvalidoException e) {
        	//throw new NumberFormatException("ERROR Se ha introducido un valor no valido");
        	return -1;
        }
    }
    
    public void mensajeIniciarSesion() {
    	System.out.println("Es un gusto tenerte de vuelta");
    }
    
    public void mensajeCrearCuenta() {
    	System.out.println("Gracias por elegirnos");
    }
    
    public int menuClientes() {
    	System.out.println("MENÚ PRINCIPAL\n");
    	System.out.println("Seleccione la opción que quiera realizar\n");
    	System.out.println("1. Ingreso\n");
    	System.out.println("2. Reintegro\n");
    	System.out.println("3. Transferencia\n");
    	System.out.println("4. Consultar saldo\n");
    	System.out.println("5. Redactar incidencia\n");
    	System.out.println("6. Ver Historial de movmientos\n");
    	System.out.println("7. Cerrar sesión\n");
    	
    	try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    public boolean reintentarCrearCuenta() {
    	String opcion;
    	
    	System.out.println("Quieres intentar de nuevo crear una cuenta\n");
    	System.out.println("Responde SI o NO\n");
    	opcion = sc.nextLine();
    	
    	return validacion.validarReintentoCuenta(opcion);
    }
    
    public void mensajeErrorCrearCuenta() {
    	System.err.println("ERROR no se ha podido crear la cuenta");
    }
    
    public String redactarIncidencia() {
    	String incidencia;
    	
    	System.out.println("Ingrese su incidencia");
    	incidencia = sc.nextLine();
    	
    	return incidencia;
    }
    
    public int mostrarMenuEmpleados() {
    	System.out.println("MENÚ DE EMPLEADOS");
    	System.out.println("1. Leer incidencias pendientes");
    	System.out.println("2. Leer todas las incidencia");
    	System.out.println("3. Atender incidencia");
    	System.out.println("4. Ver historial de movimientos de cuentas");
    	System.out.println("5. Ver datos de usuarios");
    	System.out.println("6. Cerrar Sesión");
    	
    	try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    public Double pedirCantidadDinero() {
        System.out.println("Ingrese la cantidad: ");
        
        /*
         * if (!sc.hasNextDouble()) {
         * 			return null;
		 *	}
         */

        
        double cantidad = sc.nextDouble();
        
        while (!validacion.validarDineroAOperar(cantidad)) {
            System.err.println("ERROR Has ingresado una cantidad de dinero o caracter no permitido");
            sc.next();
        }
        sc.nextLine();
        return cantidad;
      //  double cantidad = sc.nextDouble();
    }
    
    public void mostrarMensajeErrorInicioDeSesion() {
    	System.err.println("ERROR Usuario o contraseña incorrecto");
    }
    
    public void mensajeIncidenciaEliminada() {
    	System.out.println("Incidencia atendida");
    }
    
    public void mensajeCierreSesion() {
    	System.out.println("Cerrando la sesión...");
    }
    
    public void mensajeNingunaIncidencia() {
    	System.out.println("No hay ninguna incidencia por el momento");
    }
    
    public int menuSeleccionCuenta() {
    	System.out.println("Elija la opción que quiera");
    	System.out.println("1. Iniciar sesión");
    	System.out.println("2. Crear una nueva cuenta");
    	System.out.println("3. Salir");
    	String opcion = sc.nextLine();
    	int numero = Integer.parseInt(opcion);
    	
    	return numero;
    }
    
    /**
     * Muestra las cuentas disponibles del usuario y le permite seleccionar una
     */
    public CuentaBancaria elegirCuentaEntreLista(List<CuentaBancaria> cuentas) {
        System.out.println("\n-------------------------------------------");
        System.out.println("       TUS CUENTAS DISPONIBLES");
        System.out.println("-------------------------------------------");
        
        for (int i = 0; i < cuentas.size(); i++) {
            CuentaBancaria cb = cuentas.get(i);
            System.out.println((i + 1) + ". " + cb.getNombre() + " | Nº: " + cb.getNumCuenta() + " | Saldo: " + cb.getSaldo() + "€");
        }
        
        System.out.println("-------------------------------------------");
        
        System.out.print("Seleccione el número de la cuenta para operar: ");
        
        try {
            String entrada = sc.nextLine();
            if (entrada.isEmpty()) { entrada = sc.nextLine(); }
            
            int seleccion = Integer.parseInt(entrada);
            
            if (seleccion >= 1 && seleccion <= cuentas.size()) {
                return cuentas.get(seleccion - 1);
            } else {
                System.err.println("Opción no válida. Esa cuenta no existe en la lista.");
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error: Por favor, introduzca un número válido.");
            return null;
        }
    }
    
    public String pedirNombreCuenta() {
        String nombre;
        boolean valido = false;
        
        // Limpieza de buffer: Si hay un salto de línea pendiente, lo consumimos
        if (sc.hasNextLine()) {
            sc.nextLine(); 
        }
        
        do {
            System.out.println("\nIntroduce un nombre para identificar la nueva cuenta:");
            System.out.print("(Ejemplo: Ahorros, Nómina, Gastos diarios): ");
            nombre = sc.nextLine();

            if (nombre != null && !nombre.trim().isEmpty()) {
                valido = true;
            } else {
                System.err.println("El nombre de la cuenta no puede estar vacío.");
            }
        } while (!valido);

        return nombre;
    }

    public int pedirInt() {
        System.out.println("Ingrese el número correspondiente: ");
        while (!sc.hasNextInt()) {
            System.out.println("Por favor, introduzca un número válido.");
            sc.next();
        }
        int numero = sc.nextInt();
        sc.nextLine();
        return numero;
    }
    
    public String pedirString(String mensaje) {
        System.out.println(mensaje);
        return sc.nextLine();
    }
    
    /*
     * Revisamos la cantidad de movmientos que se han hecho con una cuenta
     * Si no hay se devuelve un mensaje informativo, en caso de haber se muestran
     * todos los movimientos realizados con esa cuenta
     */
    public void mostrarHistorial(List<String> movimientos) {
        if (movimientos.isEmpty()) {
            System.out.println("No hay movimientos registrados en esta cuenta.");
        } else {
            System.out.println("--- HISTORIAL DE MOVIMIENTOS ---");
            for (String m : movimientos) {
                System.out.println(m);
            }
            System.out.println("--------------------------------");
        }
    }
    
    public void mostrarDatosCompletosCliente(DatosCliente datos) {
    	if (datos == null) {
			System.err.println("ERROR no se ha encontrado a ningún usuario con esos datos");
			return;
    	}
    	
    	System.out.println("\n===================");
    	System.out.println("INFORMACIÓN DEL CLIENTE");
    	System.out.println();
    	System.out.println("Id: "+datos.getId());
    	System.out.println("Nombre: "+datos.getNombre());
    	System.out.println("Apellido: "+datos.getApellido());
    	System.out.println("Correo: "+datos.getCorreo());
    	System.out.println("Rol: "+datos.getRol());
    	System.out.println("=====================");
    	System.out.println("CUENTAS ASOCIADAS");
    	
    	
    	
    	if(datos.getCuentas().isEmpty()) {
    		System.err.println("ERROR No hay cuentas registradas con ese usuario");
    	}
    	else {
			for(CuentaBancaria cBancaria : datos.getCuentas()) {
				System.out.println("Nº: "+cBancaria.getNumCuenta()
				+ " | Alias: "+cBancaria.getNombre()
				+ " | Saldo: "+cBancaria.getSaldo()+" €");
				
			}
			
		}
    }
    
    public void mensajeErrorCaracterIngresado() {
    	System.err.println("ERROR has introducido un caracter invalido");
    }
}