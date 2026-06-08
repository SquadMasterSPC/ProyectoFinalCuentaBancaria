package controlador;

import modelo.*;
import dao.UsuariosDao;
import dao.ConexionDB;
import dao.CuentasDao;
import service.Login;
import vista.Vista;
import java.util.Scanner;
import modelo.DatosCliente;
import dao.IncidenciasDao;
import logica.Logica;

import java.security.PublicKey;
import java.util.Iterator;
import java.util.List;


public class Controlador {

	private CuentaBancaria cBancaria;
	private Ingreso ingreso;
	private Transferencia transferencia;
	private Usuario usuario;
	private UsuariosDao uDao;
	private IncidenciasDao iDao;
	private CuentasDao cDao; 
	private Login login;
	private String nombreUsuario;
	private String contraseniaUsuario;
	private Vista vista;
	private Reintegro reintegro;
	private Logica logica;
	
	private final int intentosRestantes = 3;

	public Controlador() {
		super();
		cBancaria = new CuentaBancaria(null, null, 0, 0, null);
		ingreso = new Ingreso(null, 0, null);
		transferencia = new Transferencia(null, 0, null);
		usuario = new Usuario(null, null, null, null, null, 0.0, null, 0);
		uDao = new UsuariosDao();
		iDao = new IncidenciasDao();
		cDao = new CuentasDao(cBancaria);
		login = new Login();
		vista = new Vista();
		reintegro = new Reintegro(contraseniaUsuario, 0, null);
		logica = new Logica();
	}

	public void ejecutar() {
		int opcion;
		do {
			opcion = vista.inicioDelSistema();
			switch (opcion) {
			case 1:
				procesarIniciarSesion();
				break;
			case 2:
				procesarRegistrarNuevoUsuario();
				break;
			}
		} while (opcion != 0);
	}

	private void procesarIniciarSesion() {
	    try {
	        boolean sesionIniciada = false;

	        for (int i = 0; i < intentosRestantes; i++) {
	            String nombre = vista.pedirNombre();
	            String contrasenia = vista.pedirContrasenia();

	            try {
	                usuario = login.inicioDeSesion(nombre, contrasenia);
	                
	                vista.mostrarMensaje("Bienvenido " + usuario.getNombre());
	                sesionIniciada = true;
	                procesarRedirigirSegunRol();
	                break; 
	                
	            } catch (excepciones.seguridad.CredencialesInvalidasException e) {
	                int restantes = intentosRestantes - (i + 1);
	                if (restantes > 0) {
	                    vista.mostrarMensaje(e.getMessage() + " (Intentos restantes: " + restantes + ")");
	                }
	            }
	        }

	        if (!sesionIniciada) {
	            throw new excepciones.seguridad.SesionNoIniciadaException("ERROR: Has superado el límite de intentos permitidos. Acceso denegado.");
	        }

	    } catch (excepciones.seguridad.SesionNoIniciadaException e) {
	        vista.mostrarMensaje(e.getMessage());
	    }
	}

	private void procesarRedirigirSegunRol() {
		String rol = usuario.getRol();
		if ("ADMIN".equalsIgnoreCase(rol)) {

		} else if ("EMPLEADO".equalsIgnoreCase(rol)) {
			int opcionEmpleado;
			do {
				opcionEmpleado = vista.mostrarMenuEmpleados();
				switch (opcionEmpleado) {
				case 1:
					vista.mostrarMensaje("\n--- LISTA DE INCIDENCIAS PENDIENTES ---");
					List<String> listaPendientes = iDao.obtenerIncidenciasPendientes();
					if(listaPendientes.isEmpty()) {
						vista.mensajeNingunaIncidencia();
					} else {
						for(String s : listaPendientes) {
							vista.mostrarMensaje(s);
						}
					}

					break;
				case 2:
					vista.mostrarMensaje("\n--- LISTA DE INCIDENCIAS ---");
					List<String> lista = iDao.leerIncidencias();
					if (lista.isEmpty()) {
						vista.mensajeNingunaIncidencia();
					} else {
						for (String s : lista) {
							vista.mostrarMensaje(s);
						}
					}
					break;
				case 3: // Atender incidencia
				    vista.mostrarMensaje("--- INCIDENCIAS PENDIENTES DE ATENDER ---");
				    List<String> pendientes = iDao.obtenerIncidenciasPendientes();
				    
				    if (pendientes.isEmpty()) {
				        vista.mostrarMensaje("No hay incidencias pendientes. ¡Buen trabajo!");
				    } else {
				        // Mostramos las incidencias pendientes
				        for (String inc : pendientes) {
				            System.out.println(inc);
				        }
				        
				        // Pedimos el ID de la incidencia que quiere resolver
				        int idIncidenciaAAtender = vista.pedirInt(); 
				        
				        // CORRECCIÓN: Usamos 'usuario', que es la variable real del login
				        int idEmpleadoActivo = usuario.getId(); 
				        
				        System.out.println("DEBUG: Enviando al DAO -> idIncidencia: " + idIncidenciaAAtender + " | idEmpleado: " + idEmpleadoActivo);
				        
				        // Llamamos a tu método pasándole los datos en el orden correcto
				        if (iDao.atenderIncidencias(idIncidenciaAAtender, idEmpleadoActivo)) {
				            vista.mostrarMensaje("Se ha resuelto la incidencia con éxito.");
				        } else {
				            vista.mostrarMensaje("No se pudo actualizar la incidencia. Verifica el ID.");
				        }
				    }
				    break;
				case 4: // Ver movimientos de cualquier cuenta
				    String cuentaABuscar = vista.pedirString("Introduzca el nº de cuenta a revisar: ");
				    List<String> historialEmp = cDao.obtenerHistorialMovimientos(cuentaABuscar);
				    vista.mostrarHistorial(historialEmp);
				    break;

				case 5: // Ver datos de cliente y sus cuentas
				    String clienteABuscar = vista.pedirString("Introduzca el nombre del cliente: ");
				    DatosCliente datos = cDao.mostrarDatosCompletosCliente(clienteABuscar);
				    //cDao.mostrarDatosCompletosCliente(clienteABuscar);
				    vista.mostrarDatosCompletosCliente(datos);
				    break;
					
				case 6:
					vista.mensajeCierreSesion();
					break;
				}
			} while (opcionEmpleado != 6);

		} else {

			int opcionGestion;
			do {
				opcionGestion = vista.menuSeleccionCuenta();
				switch (opcionGestion) {
				case 1:
					List<CuentaBancaria> misCuentas = cDao.obtenerCuentasPorUsuario(usuario.getId());
					if (misCuentas.isEmpty()) {
						vista.mostrarMensaje("No tienes cuentas activas.");
					} else {
						CuentaBancaria seleccionada = vista.elegirCuentaEntreLista(misCuentas);
						if (seleccionada != null) {
							ejecutarMenuOperaciones(seleccionada);
						}
					}
					break;
				case 2:
					procesarAltaNuevaCuenta();
					break;
				}
			} while (opcionGestion != 3);
		}
	}


	private void ejecutarMenuOperaciones(CuentaBancaria cuentaSeleccionada) {
		int opcion;
		do {
			opcion = vista.menuClientes();
			switch (opcion) {
			case 1: //Hacer ingreso
				try {
					vista.mostrarMensaje("Escriba el dinero a ingresar en la cuenta: " + cuentaSeleccionada.getNumCuenta()+"\n");
					Double cantidad = vista.pedirCantidadDinero();
					
					ingreso.ingresar(cuentaSeleccionada, cantidad);
					
					cDao.actualizarSaldo(cuentaSeleccionada.getNumCuenta(), cantidad);
					cDao.registrarMovimiento(cuentaSeleccionada.getNumCuenta(), "INGRESO", cantidad);
					
					vista.mostrarMensaje("Ingreso exitoso. Saldo: " + cuentaSeleccionada.getSaldo());
				} catch (excepciones.vista.DatoInvalidoException e) {
					vista.mostrarMensaje(e.getMessage());
				} catch (excepciones.persistencia.PersistenciaException e) {
					vista.mostrarMensaje("Error al realizar el ingreso. Cantidad no válida o error en BD.");
				}
				break;
			case 2: //Hacer reintegro
				try {
					vista.mostrarMensaje("Escriba el dinero a retirar de la cuenta: " + cuentaSeleccionada.getNumCuenta()+"\n");
					Double cantRetirar = vista.pedirCantidadDinero();
					Double saldoCuenta = logica.validarSaldoCuenta(cuentaSeleccionada);
					
					reintegro.retirar(cuentaSeleccionada, cantRetirar, saldoCuenta);
					
					cDao.actualizarSaldo(cuentaSeleccionada.getNumCuenta(), -cantRetirar);
					cDao.registrarMovimiento(cuentaSeleccionada.getNumCuenta(), "REINTEGRO", cantRetirar);
					
					vista.mostrarMensaje("Retirada completada. Nuevo saldo: " + cuentaSeleccionada.getSaldo() + "€");
				} catch (excepciones.vista.DatoInvalidoException | excepciones.modelo.SaldoInsuficienteException e) {
					vista.mostrarMensaje(e.getMessage());
				} catch (excepciones.persistencia.PersistenciaException e) {
					vista.mostrarMensaje("Error: Saldo insuficiente o cantidad no válida.");
				}
				break;
			case 3: //Hacer transferencia
				try {
					vista.mostrarMensaje("--- Nueva Transferencia ---");
					vista.mostrarMensaje("Cuenta origen: " + cuentaSeleccionada.getNumCuenta());
					
					int numeroCuentaDestino = vista.pedirInt();
					Double cantTransferir = vista.pedirCantidadDinero();
					
					CuentaBancaria cuentaDestino = cDao.obtenerCuentaPorNumero(numeroCuentaDestino);
					
					transferencia.realizarTransferencia(cuentaSeleccionada, cuentaDestino, cantTransferir);
					
					cDao.actualizarSaldo(cuentaSeleccionada.getNumCuenta(), -cantTransferir);
					cDao.actualizarSaldo(cuentaDestino.getNumCuenta(), cantTransferir);
					cDao.registrarMovimiento(cuentaSeleccionada.getNumCuenta(), "TRANSFERENCIA ENVIADA", -cantTransferir);
					cDao.registrarMovimiento(cuentaDestino.getNumCuenta(), "TRANSFERENCIA RECIBIDA", cantTransferir);
					
					vista.mostrarMensaje("Transferencia enviada con éxito.");
					vista.mostrarMensaje("Nuevo saldo: " + cuentaSeleccionada.getSaldo() + "€");
				} catch (excepciones.vista.DatoInvalidoException | excepciones.modelo.SaldoInsuficienteException | excepciones.seguridad.CuentaNoEncontradaException e) {
					vista.mostrarMensaje(e.getMessage());
				} catch (excepciones.persistencia.PersistenciaException e) {
					vista.mostrarMensaje("Error: Fondos insuficientes o la cuenta destino no existe.");
				}
				break;
			case 4: //Obtener saldo
				vista.mostrarMensaje("Cuenta: " + cuentaSeleccionada.getNombre());
				vista.mostrarMensaje("Su saldo es de: " + cuentaSeleccionada.getSaldo() + "€");
				break;
			case 5: //Redactar incidencias
				String desc = vista.pedirString("Describa su incidencia:");
				if (iDao.guardarIncidencia(usuario.getId(), usuario.getNombre(), desc)) {
					vista.mostrarMensaje("Incidencia registrada con éxito.");
				} else {
					vista.mostrarMensaje("Error al registrar incidencia.");
				}
				break;
			case 6: // Historial de movimientos
				List<String> movs = cDao.obtenerHistorialMovimientos(cuentaSeleccionada.getNumCuenta());
				vista.mostrarHistorial(movs);
				break;
				
			case 7: //salir
				vista.mostrarMensaje("Volviendo a selección de cuenta...");
				break;
			}
		} while (opcion != 7);
	}

	private void procesarAltaNuevaCuenta() {
		String nombreC = vista.pedirNombreCuenta();

		int numAleatorio = (int) (Math.random() * 9000000L + 1000000L);

		if (cDao.crearCuenta(usuario.getId(), nombreC, numAleatorio)) {

			cDao.actualizarSaldo(String.valueOf(numAleatorio), 1000.00);

			vista.mostrarMensaje("¡Cuenta creada con éxito!");
			vista.mostrarMensaje("Nombre: " + nombreC);
			vista.mostrarMensaje("Número: " + numAleatorio);

		} else {
			vista.mostrarMensaje("Error al crear la cuenta.");
		}
	}

	private void procesarRegistrarNuevoUsuario() {
		boolean valido = true;
		usuario = new Usuario(nombreUsuario, contraseniaUsuario, null, null, null, 0, null, 0);

		while (valido) {
			vista.mostrarMensaje("\n--- FORMULARIO DE REGISTRO ---");
			String nom = vista.pedirNombre();
			if (nom != null) {
				String ape = vista.pedirApellido();
				if (ape != null) {
					String correo = vista.pedirCorreo();
					if (correo != null) {
						String contrasenia = vista.pedirContrasenia();
						if (contrasenia != null) {
							usuario.setNombre(nom);
							usuario.setApellido(ape);
							usuario.setCorreoElectronico(correo);
							usuario.setContrasenia(contrasenia);

							boolean exito = uDao.registrarUsuarioCompleto(usuario);
							if (exito) {
								vista.mostrarMensaje("Usuario registrado. ID: " + usuario.getId());
							} else {
								vista.mostrarMensaje("No se pudo completar el registro.");
							}
							break;
						} else {
							valido = false;
						}
					} else {
						valido = false;
					}
				} else {
					valido = false;
				}
			} else {
				valido = false;
			}

			if (!valido) {
				if (vista.reintentarCrearCuenta()) {
					valido = true;
				} else {
					vista.mostrarMensaje("Registro cancelado.");
					break;
				}
			}
		}
	}
}