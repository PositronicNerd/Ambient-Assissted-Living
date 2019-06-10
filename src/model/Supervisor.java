package model;

import java.util.ArrayList;

public class Supervisor extends Usuario {
	private ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
	
	public Supervisor(String correo, String contrasena, String roll, ArrayList<Paciente> pacientes) {
		super(correo, contrasena, roll);
		this.pacientes = pacientes;
	}

	public ArrayList<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(ArrayList<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

}
