package model;

import java.util.Date;

public class Dato {
	private String correoPaciente;
	private Date fecha;
	private int id, sensorPresion, sensorAcelerometro, sensorCaida;
	
	public Dato(int id, Date fecha, int sensorPresion, int sensorAcelerometro,int sensorCaida, String correoPaciente) {
		this.id = id;
		this.fecha = fecha;
		this.sensorPresion = sensorPresion;
		this.sensorAcelerometro = sensorAcelerometro;
		this.sensorCaida = sensorCaida;
		this.correoPaciente = correoPaciente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCorreoPaciente() {
		return correoPaciente;
	}

	public void setCorreoPaciente(String correoPaciente) {
		this.correoPaciente = correoPaciente;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getSensorPresion() {
		return sensorPresion;
	}

	public void setSensorPresion(int sensorPresion) {
		this.sensorPresion = sensorPresion;
	}

	public int getSensorAcelerometro() {
		return sensorAcelerometro;
	}

	public void setSensorAcelerometro(int sensorAcelerometro) {
		this.sensorAcelerometro = sensorAcelerometro;
	}

	public int getSensorCaida() {
		return sensorCaida;
	}

	public void setSensorCaida(int sensorCaida) {
		this.sensorCaida = sensorCaida;
	}

}
