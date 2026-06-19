package modelo;

import java.sql.Date;
import excepciones.vista.DatoInvalidoException;
import excepciones.modelo.SaldoInsuficienteException;

public class Reintegro extends Operacion {

    public Reintegro(String idOperacion, double dineroEnMovimiento, Date fecha) {
        super(idOperacion, dineroEnMovimiento, fecha);
    }

    /**
     * Saca dinero de la cuenta, comprobando antes que no te quedes en números rojos
     * @param cuenta La cuenta de donde sacamos el dinero
     * @param cantidad Lo que queremos sacar
     * @param saldoDisponible El saldo que tiene la cuenta en este momento
     * @return true si se ha podido retirar el dinero con éxito
     * @throws excepciones.modelo.SaldoInsuficienteException Si intentas sacar más dinero del que tienes
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