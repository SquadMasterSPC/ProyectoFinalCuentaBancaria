package modelo;

import java.sql.Date;
import excepciones.vista.DatoInvalidoException;
import excepciones.modelo.SaldoInsuficienteException;

public class Reintegro extends Operacion {

    public Reintegro(String idOperacion, double dineroEnMovimiento, Date fecha) {
        super(idOperacion, dineroEnMovimiento, fecha);
    }

    /*
     * Retira del saldo de la cuenta la cantidad ingresada
     */
    public boolean retirar(CuentaBancaria cuenta, double cantidad, double saldoDisponible) {

        if (cantidad <= 0) {
            throw new DatoInvalidoException("La cantidad a retirar debe ser mayor que cero.");
        }
        
        if (saldoDisponible < cantidad) {
            throw new SaldoInsuficienteException("No tienes saldo suficiente para retirar esa cantidad.");
        }
        
        cuenta.setSaldo(cuenta.getSaldo() - cantidad);
        this.setDineroEnMovimiento(cantidad);
        return true;
    }
}