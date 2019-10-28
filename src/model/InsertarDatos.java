package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class InsertarDatos {
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://esp.uem.es:3306/pr_svji";
	String usuario = "jromero";
	String clave = "qazwsx";

	PreparedStatement statement = null;
	Connection conexion = null;
	ResultSet resultado = null;

	public void insertarMensaje(Date fecha, String texto, String correoEmisor, String correoReceptor) {
		System.out.println("Insertando mensaje, correo emisor:"+correoEmisor+", correo receptor:"+correoReceptor);

		Timestamp fechaTS = new Timestamp(fecha.getTime());
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);

			statement = conexion.prepareStatement(
					"INSERT INTO Mensajes (Fecha, Texto, Correo_Emisor, Correo_Receptor) VALUES (?, ?, ?, ?)");
			statement.setTimestamp(1, fechaTS);
			statement.setString(2, texto);
			statement.setString(3, correoEmisor);
			statement.setString(4, correoReceptor);
			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultado != null) {
					resultado.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			try {
				if (conexion != null) {
					conexion.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
} 
