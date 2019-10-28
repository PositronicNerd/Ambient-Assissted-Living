package model;

public class Familiar extends Usuario {
	Paciente paciente;

	public Familiar(String correo, String contrasena, String roll, Paciente paciente) {
		super(correo, contrasena, roll);
		this.paciente = paciente;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

}
