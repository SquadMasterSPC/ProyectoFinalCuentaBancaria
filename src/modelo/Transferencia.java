package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.ConexionDB;
import dao.CuentasDao;

public class Transferencia extends Operacion {

    private CuentasDao cDao;

    public Transferencia(String idOperacion, double dineroEnMovimiento, Date fecha) {
        super(idOperacion, dineroEnMovimiento, fecha);
        cDao = new CuentasDao(null);
    }

    /*
     * Realizamos la transferencia usando como identificadores los numero de cuenta de
     * la cuenta de origen y la cuenta de destino, despues de haber seleccionado el
     * destinatario se introducira el importe a transferir
     */
    public boolean realizarTransferencia(CuentaBancaria cuentaOrigen, int numCuentaDestino, double cantidad) {
        // 1. Mantenemos tu validación de seguridad inicial
        if (cantidad <= 0 || cuentaOrigen.getSaldo() < cantidad) {
            System.out.println("Error: Saldo insuficiente o cantidad inválida.");
            return false;
        }

        CuentaBancaria cuentaDestino = cDao.obtenerCuentaPorNumero(numCuentaDestino);
        if (cuentaDestino == null) {
            System.out.println("Error: La cuenta destino no existe.");
            return false;
        }

        Connection con = null;
        PreparedStatement psResta = null;
        PreparedStatement psSuma = null;
        PreparedStatement psMovOrigen = null;
        PreparedStatement psMovDestino = null;

        try {
            con = ConexionDB.conectar();
            
            con.setAutoCommit(false);

            String sqlResta = "UPDATE cuentaBancaria SET saldo = saldo - ? WHERE numeroCuenta = ?"; // Usa el nombre exacto de tu tabla (usuarios o CuentaBancaria)
            psResta = con.prepareStatement(sqlResta);
            psResta.setDouble(1, cantidad);
            psResta.setString(2, cuentaOrigen.getNumCuenta());
            int filasResta = psResta.executeUpdate();

            String sqlSuma = "UPDATE cuentaBancaria SET saldo = saldo + ? WHERE numeroCuenta = ?";
            psSuma = con.prepareStatement(sqlSuma);
            psSuma.setDouble(1, cantidad);
            psSuma.setString(2, cuentaDestino.getNumCuenta());
            int filasSuma = psSuma.executeUpdate();

            String sqlMov = "INSERT INTO movimientos (numeroCuenta, tipo, cantidad, fecha) VALUES (?, ?, ?, NOW())"; // Ajusta al nombre de tu tabla de movimientos
            psMovOrigen = con.prepareStatement(sqlMov);
            psMovOrigen.setString(1, cuentaOrigen.getNumCuenta());
            psMovOrigen.setString(2, "TRANSFERENCIA ENVIADA A: " + numCuentaDestino);
            psMovOrigen.setDouble(3, -cantidad);
            psMovOrigen.executeUpdate();

            psMovDestino = con.prepareStatement(sqlMov);
            psMovDestino.setString(1, String.valueOf(numCuentaDestino)); 
            psMovDestino.setString(2, "TRANSFERENCIA RECIBIDA DE: " + cuentaOrigen.getNumCuenta());
            psMovDestino.setDouble(3, cantidad);
            psMovDestino.executeUpdate();

            if (filasResta > 0 && filasSuma > 0) {
                con.commit();
                
                cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - cantidad);
                this.setDineroEnMovimiento(cantidad);
                
                return true;
            } else {
                con.rollback();
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error en la transacción. Aplicando Rollback: " + e.getMessage());
            if (con != null) {
                try {
                    con.rollback(); 
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            ConexionDB.cerrar(con);
        }
    }
}