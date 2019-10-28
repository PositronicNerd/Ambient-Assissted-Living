package model;

public class Usuario {
	private String correo, contrasena, roll;
	
	public Usuario(String correo, String contrasena, String roll) {
		this.correo = correo;
		this.contrasena = contrasena;
		this.roll = roll;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getRoll() {
		return roll;
	}

	public void setRoll(String roll) {
		this.roll = roll;
	}
}
