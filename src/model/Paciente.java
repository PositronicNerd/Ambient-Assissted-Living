package model;

import java.util.Date;

public class Paciente extends Usuario {
	private String nombre, direccion, telefono, correoSupervisor, correoFamiliar, luzActivado, monitoreoActivado, pastilleroActivado, caidaActivado;
	

	private Date fechaNacimiento;
	
	public Paciente(String correo, String contrasena, String roll, String nombre, Date fechaNacimiento,String direccion, String telefono, String luzActivado, String monitoreoActivado, String pastilleroActivado, String caidaActivado, String correoSupervisor, String correoFamiliar) {
		super(correo, contrasena, roll);
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.direccion = direccion;
		this.telefono = telefono;
		this.luzActivado = luzActivado;
		this.monitoreoActivado = monitoreoActivado;
		this.pastilleroActivado = pastilleroActivado;
		this.caidaActivado = caidaActivado;
		this.correoSupervisor = correoSupervisor;
		this.correoFamiliar = correoFamiliar;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreoSupervisor() {
		return correoSupervisor;
	}

	public void setCorreoSupervisor(String correoSupervisor) {
		this.correoSupervisor = correoSupervisor;
	}

	public String getCorreoFamiliar() {
		return correoFamiliar;
	}

	public void setCorreoFamiliar(String correoFamiliar) {
		this.correoFamiliar = correoFamiliar;
	}

	public String getLuzActivado() {
		return luzActivado;
	}

	public void setLuzActivado(String luzActivado) {
		this.luzActivado = luzActivado;
	}

	public String getMonitoreoActivado() {
		return monitoreoActivado;
	}

	public void setMonitoreoActivado(String monitoreoActivado) {
		this.monitoreoActivado = monitoreoActivado;
	}

	public String getPastilleroActivado() {
		return pastilleroActivado;
	}

	public void setPastilleroActivado(String pastilleroActivado) {
		this.pastilleroActivado = pastilleroActivado;
	}

	public String getCaidaActivado() {
		return caidaActivado;
	}

	public void setCaidaActivado(String caidaActivado) {
		this.caidaActivado = caidaActivado;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		//Actualizar valor SQL y en el cliente de RaspberryPi
		this.fechaNacimiento = fechaNacimiento;
	}

}
