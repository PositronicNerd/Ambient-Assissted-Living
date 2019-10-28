package model;

import java.util.Date;

public class Mensaje {
String texto, correo_emisor, correo_receptor;
Date fecha;
int id;

public Mensaje(int id, String texto, Date fecha, String correo_emisor, String correo_receptor) {
	this.id=id;
	this.texto=texto;
	this.fecha=fecha;
	this.correo_emisor = correo_emisor; 
	this.correo_receptor = correo_receptor;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getTexto() {
	return texto;
}

public void setTexto(String texto) {
	this.texto = texto;
}

public Date getFecha() {
	return fecha;
}

public void setFecha(Date fecha) {
	this.fecha = fecha;
}

public String getCorreo_emisor() {
	return correo_emisor;
}

public void setCorreo_emisor(String correo_emisor) {
	this.correo_emisor = correo_emisor;
}

public String getCorreo_receptor() {
	return correo_receptor;
}

public void setCorreo_receptor(String correo_receptor) {
	this.correo_receptor = correo_receptor;
}

}
