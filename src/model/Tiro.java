package model;

public class Tiro {

	private ObjetoGrafico objeto;

	private int linhaProgressoMatriz;

	private int contadorTiros;

	public Tiro() {

	}

	public Tiro(ObjetoGrafico objeto, int linhaProgressoMatriz, int contadorTiros) {
		this.objeto = objeto;
		this.linhaProgressoMatriz = linhaProgressoMatriz;
		this.contadorTiros = contadorTiros;
	}

	public ObjetoGrafico getObjeto() {
		return objeto;
	}

	public void setObjeto(ObjetoGrafico objeto) {
		this.objeto = objeto;
	}

	public int getLinhaProgressoMatriz() {
		return linhaProgressoMatriz;
	}

	public void setLinhaProgressoMatriz(int linhaProgressoMatriz) {
		this.linhaProgressoMatriz = linhaProgressoMatriz;
	}

	public int getContadorTiros() {
		return contadorTiros;
	}

	public void setContadorTiros(int contadorTiros) {
		this.contadorTiros = contadorTiros;
	}
}
