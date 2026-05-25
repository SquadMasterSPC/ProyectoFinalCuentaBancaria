package modelo;

import java.sql.Date;
import dao.CuentasDao;

public class Reintegro extends Operacion {

    private CuentasDao cDao;

    public Reintegro(String idOperacion, double dineroEnMovimiento, Date fecha) {
        super(idOperacion, dineroEnMovimiento, fecha);
        cDao = new CuentasDao(null);
    }

    /*
     * Retira del saldo de la cuenta la cantidad ingresada
     */
    public boolean retirar(CuentaBancaria cuenta, double cantidad, double saldoDisponible) {

        if (cantidad > 0 && saldoDisponible >= cantidad) {
            
            if (cDao.actualizarSaldo(cuenta.getNumCuenta(), -cantidad)) {
                
                cuenta.setSaldo(cuenta.getSaldo() - cantidad);
                
                this.setDineroEnMovimiento(cantidad);
                cDao.registrarMovimiento(cuenta.getNumCuenta(), "REINTEGRO", cantidad);
                return true;
            }
        }
        return false;
    }
}