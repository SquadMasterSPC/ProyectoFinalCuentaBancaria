package modelo;

import java.sql.Date;
import excepciones.vista.DatoInvalidoException;
import excepciones.modelo.SaldoInsuficienteException;

public class Transferencia extends Operacion {

    public Transferencia(String idOperacion, double dineroEnMovimiento, Date fecha) {
        super(idOperacion, dineroEnMovimiento, fecha);
    }

    /**
     * Pasa dinero de una cuenta a otra, comprobando que la cuenta de origen tenga fondos suficientes
     * @param cuentaOrigen La cuenta que manda el dinero
     * @param cuentaDestino La cuenta que lo recibe
     * @param cantidad El dinero exacto a transferir
     * @return true si la transferencia llega a buen puerto
     * @throws excepciones.modelo.SaldoInsuficienteException Si la cuenta de origen está pelada
     */
    public boolean realizarTransferencia(CuentaBancaria cuentaOrigen, CuentaBancaria cuentaDestino, double cantidad) {
        if (cantidad <= 0) {
            throw new DatoInvalidoException("La cantidad a transferir debe ser mayor que cero.");
        }

        if (cuentaOrigen.getSaldo() < cantidad) {
            throw new SaldoInsuficienteException("No dispone de saldo suficiente para realizar la transferencia.");
        }

        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - cantidad);
        cuentaDestino.setSaldo(cuentaDestino.getSaldo() + cantidad);
        this.setDineroEnMovimiento(cantidad);
        
        return true;
    }
}