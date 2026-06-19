package service;

import dao.CuentasDao;
import modelo.CuentaBancaria;
import modelo.Ingreso;
import modelo.Reintegro;
import modelo.Transferencia;

/**
 * El motor financiero de la app centraliza las sumas y restas de los ingresos, reintegros 
 * y transferencias y se encarga de guardar el historial para que no se pierda nada
 */
public class OperacionesService {
    
    private CuentasDao cDao;
    
    public OperacionesService(CuentasDao cDao) {
        this.cDao = cDao;
    }

    public void procesarIngreso(CuentaBancaria cuenta, double cantidad) {
        Ingreso ingreso = new Ingreso(null, cantidad, null);
        ingreso.ingresar(cuenta, cantidad); 
        cDao.actualizarSaldo(cuenta.getNumCuenta(), cantidad);
        cDao.registrarMovimiento(cuenta.getNumCuenta(), "INGRESO", cantidad);
    }

    public void procesarReintegro(CuentaBancaria cuenta, double cantidad, double saldoDisponible) {
        Reintegro reintegro = new Reintegro(null, cantidad, null);
        reintegro.retirar(cuenta, cantidad, saldoDisponible);
        cDao.actualizarSaldo(cuenta.getNumCuenta(), -cantidad);
        cDao.registrarMovimiento(cuenta.getNumCuenta(), "REINTEGRO", cantidad);
    }

    /**
     * Saca dinero de una cuenta para meterlo en otra, registrando el movimiento en los dos historiales
     * @param origen La cuenta que da el dinero
     * @param numDestino El identificador de la cuenta que lo recibe
     * @param cantidad Lo que se va a enviar
     */
    public void procesarTransferencia(CuentaBancaria origen, int numDestino, double cantidad) {
        CuentaBancaria destino = cDao.obtenerCuentaPorNumero(numDestino);
        Transferencia transferencia = new Transferencia(null, cantidad, null);
        transferencia.realizarTransferencia(origen, destino, cantidad);
        cDao.actualizarSaldo(origen.getNumCuenta(), -cantidad);
        cDao.actualizarSaldo(destino.getNumCuenta(), cantidad);
        cDao.registrarMovimiento(origen.getNumCuenta(), "TRANSFERENCIA ENVIADA A " + destino.getNumCuenta(), -cantidad);
        cDao.registrarMovimiento(destino.getNumCuenta(), "TRANSFERENCIA RECIBIDA DE " + origen.getNumCuenta(), cantidad);
    }
}