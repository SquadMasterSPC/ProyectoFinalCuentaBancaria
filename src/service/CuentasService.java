package service;

import dao.CuentasDao;
import modelo.CuentaBancaria;
import modelo.DatosCliente;
import java.util.List;

/**
 * Hace de intermediario entre el controlador y la base de datos para todo lo 
 * que tenga que ver con gestionar las cuentas y consultar historiales
 */
public class CuentasService {
    
    private CuentasDao cDao;
    
    public CuentasService(CuentasDao cDao) {
        this.cDao = cDao;
    }

    public List<CuentaBancaria> obtenerMisCuentas(int idUsuario) {
        return cDao.obtenerCuentasPorUsuario(idUsuario);
    }

    public List<String> obtenerHistorial(String numCuenta) {
        return cDao.obtenerHistorialMovimientos(numCuenta);
    }

    /**
     * Genera un número de cuenta aleatorio y le da un pequeño empujón inicial de 1000€ de regalo
     * @param idUsuario El dueño de la nueva cuenta
     * @param nombreCuenta El alias que le ha puesto 
     * @return El número generado si todo va bien, o -1 si hubo un fallo en la base de datos
     */
    public int crearNuevaCuenta(int idUsuario, String nombreCuenta) {
        int numAleatorio = (int) (Math.random() * 9000000L + 1000000L);

        if (cDao.crearCuenta(idUsuario, nombreCuenta, numAleatorio)) {
            cDao.actualizarSaldo(String.valueOf(numAleatorio), 1000.00);
            return numAleatorio;
        }
        return -1;
    }
    
    // Buscar los datos completos de un cliente
    public DatosCliente obtenerDatosCliente(String nombreCliente) {
        return cDao.mostrarDatosCompletosCliente(nombreCliente);
    }
}