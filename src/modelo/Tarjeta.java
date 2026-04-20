/**
 * 
 */
package modelo;

/**
 * 
 */
public class Tarjeta {

	private String numeroDeTarjeta;
	private int CCV;
	private int PIN;
	
	public Tarjeta(String numeroDeTarjeta, int cCV, int pIN) {
		super();
		this.numeroDeTarjeta = numeroDeTarjeta;
		CCV = cCV;
		PIN = pIN;
	}

	public String getNumeroDeTarjeta() {
		return numeroDeTarjeta;
	}

	public void setNumeroDeTarjeta(String numeroDeTarjeta) {
		this.numeroDeTarjeta = numeroDeTarjeta;
	}

	public int getCCV() {
		return CCV;
	}

	public void setCCV(int cCV) {
		CCV = cCV;
	}

	public int getPIN() {
		return PIN;
	}

	public void setPIN(int pIN) {
		PIN = pIN;
	}
	
	
	
}
