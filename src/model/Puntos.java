package model;

public class Puntos {

	String hora;
	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	int valor;
	
	public Puntos(String hora, int valor) {
		this.hora=hora;
		this.valor=valor;
	}
	
	
	
}
