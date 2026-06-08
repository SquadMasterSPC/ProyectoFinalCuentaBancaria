package modelo;

import java.sql.Date;
import excepciones.vista.DatoInvalidoException;
import excepciones.modelo.SaldoInsuficienteException;

public class Transferencia extends Operacion {

    public Transferencia(String idOperacion, double dineroEnMovimiento, Date fecha) {
        super(idOperacion, dineroEnMovimiento, fecha);
    }

    /*
     * Realizamos la transferencia usando como identificadores los numero de cuenta de
     * la cuenta de origen y la cuenta de destino, despues de haber seleccionado el
     * destinatario se introducira el importe a transferir
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