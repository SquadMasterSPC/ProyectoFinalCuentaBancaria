package service;

import java.sql.Connection;
import java.sql.SQLException;

import dao.CuentasDao;
import dao.ConexionDB;
import modelo.CuentaBancaria;
import modelo.Ingreso;
import modelo.Reintegro;
import modelo.Transferencia;
import excepciones.persistencia.PersistenciaException;

/**
 * El motor financiero de la app. Centraliza las sumas y restas de los ingresos, reintegros 
 * y transferencias, y se encarga de que todo se guarde de golpe o no se guarde nada (atómico)
 * para que no se pierda dinero por el camino
 */
public class OperacionesService {
    
    private CuentasDao cDao;

    /**
     * Construye el servicio asociándole el DAO de cuentas necesario para tocar la base de datos
     * @param cDao El gestor de persistencia de las cuentas bancarias
     */
    public OperacionesService(CuentasDao cDao) {
        this.cDao = cDao;
    }

    /**
     * Gestiona un ingreso de dinero en una cuenta de forma segura
     * Abre una conexión transaccional para actualizar el saldo y apuntar el movimiento a la vez
     * @param cuenta La cuenta donde se va a ingresar el dinero
     * @param cantidad El importe exacto a meter
     */
    public void procesarIngreso(CuentaBancaria cuenta, double cantidad) {
        Connection con = null;
        try {
            con = ConexionDB.conectar();
            con.setAutoCommit(false); 

            Ingreso ingreso = new Ingreso(null, cantidad, null);
            ingreso.ingresar(cuenta, cantidad); 
            
            cDao.actualizarSaldo(con, cuenta.getNumCuenta(), cantidad);
            cDao.registrarMovimiento(con, cuenta.getNumCuenta(), "INGRESO", cantidad);
            
            con.commit();
            
        } catch (Exception e) {
            hacerRollback(con); // Si algo falla, deshacemos TODO
            throw new PersistenciaException("Error crítico en la operación dinero devuelto", e);
        } finally {
            ConexionDB.cerrar(con);
        }
    }

    /**
     * Gestiona la retirada de efectivo comprobando que todo sea correcto
     * Si el usuario tiene fondos, resta el dinero y lo registra en el historial en la misma transacción
     * @param cuenta La cuenta de donde se saca el dinero
     * @param cantidad El importe a retirar
     * @param saldoDisponible El dinero que tiene la cuenta antes de la operación
     */
    public void procesarReintegro(CuentaBancaria cuenta, double cantidad, double saldoDisponible) {
        Connection con = null;
        try {
            con = ConexionDB.conectar();
            con.setAutoCommit(false);

            Reintegro reintegro = new Reintegro(null, cantidad, null);
            reintegro.retirar(cuenta, cantidad, saldoDisponible);

            cDao.actualizarSaldo(con, cuenta.getNumCuenta(), -cantidad);
            cDao.registrarMovimiento(con, cuenta.getNumCuenta(), "REINTEGRO", cantidad);

            con.commit();
        } catch (Exception e) {
            hacerRollback(con);
            throw new PersistenciaException("Error crítico en la operación dinero devuelto", e);
        } finally {
            ConexionDB.cerrar(con);
        }
    }

    /**
     * Pasa dinero entre dos cuentas de forma totalmente atómica
     * Si la cuenta de destino falla o no existe el dinero vuelve al origen de forma automática
     * gracias al rollback, evitando que el dinero se quede en el limbo
     * @param origen la cuenta que envía los fondos
     * @param numDestino el número de la cuenta receptora
     * @param cantidad el dinero a transferir
     */
    public void procesarTransferencia(CuentaBancaria origen, int numDestino, double cantidad) {
        Connection con = null;
        try {
            con = ConexionDB.conectar();
            con.setAutoCommit(false);

            CuentaBancaria destino = cDao.obtenerCuentaPorNumero(numDestino);
            Transferencia transferencia = new Transferencia(null, cantidad, null);
            transferencia.realizarTransferencia(origen, destino, cantidad);

            cDao.actualizarSaldo(con, origen.getNumCuenta(), -cantidad);
            cDao.actualizarSaldo(con, destino.getNumCuenta(), cantidad);
            cDao.registrarMovimiento(con, origen.getNumCuenta(), "TRANSFERENCIA ENVIADA A " + destino.getNumCuenta(), -cantidad);
            cDao.registrarMovimiento(con, destino.getNumCuenta(), "TRANSFERENCIA RECIBIDA DE " + origen.getNumCuenta(), cantidad);

            con.commit();
            
        } catch (Exception e) {
            hacerRollback(con); 
            throw new PersistenciaException("Error crítico en la operación dinero devuelto", e);
        } finally {
            ConexionDB.cerrar(con);
        }
    }
    
    /**
     * Método auxiliar de emergencia para deshacer todos los cambios si algo falla a mitad de camino
     * @param con La conexión activa que queremos cancelar
     */
    private void hacerRollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
                System.err.println("Rollback ejecutado operación cancelada para evitar pérdida de dinero");
            } catch (SQLException e) {
                System.err.println("Fallo crítico al intentar ejecutar el Rollback: " + e.getMessage());
            }
        }
    }
}