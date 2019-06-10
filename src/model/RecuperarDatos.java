package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class RecuperarDatos {
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://esp.uem.es:3306/pr_svji";
	String usuario = "jromero";
	String clave = "qazwsx";

	PreparedStatement statement = null;
	Connection conexion = null;
	ResultSet resultado = null;

	public Usuario recuperarUsuario(String correo, String contrasena) {
		System.out.println("Recuperando usuarios");
		String correoRec = null, contrasenaRec = null, rollRec = null;
		Usuario user = null;
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);

			statement = conexion.prepareStatement("SELECT * FROM Usuarios where Correo = ? AND Contrasena = ?");
			statement.setString(1, correo);
			statement.setString(2, contrasena);
			resultado = statement.executeQuery();

			while (resultado.next()) {
				correoRec = resultado.getString("Correo");
				contrasenaRec = resultado.getString("Contrasena");
				rollRec = resultado.getString("Roll");
			}

			user = new Usuario(correoRec, contrasenaRec, rollRec);

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

		return user;
	}

	public Paciente recuperarPaciente(String correo) {
		System.out.println("Recuperando paciente");
		String contrasena = null, roll = null, nombre = null, direccion = null, telefono = null, luzActivado = null,
				monitoreoActivado = null, pastilleroActivado = null, caidaActivado = null, correoSupervisor = null,
				correoFamiliar = null;
		Date fechaNacimiento = null;
		Paciente paciente = null;
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);

			statement = conexion.prepareStatement("SELECT * FROM Usuarios WHERE Correo = ?");
			statement.setString(1, correo);
			resultado = statement.executeQuery();

			while (resultado.next()) {
				correo = resultado.getString("Correo");
				contrasena = resultado.getString("Contrasena");
				roll = resultado.getString("Roll");
			}

			statement = conexion.prepareStatement("SELECT * FROM Pacientes WHERE Correo_Usuario = ?");
			statement.setString(1, correo);
			resultado = statement.executeQuery();

			while (resultado.next()) {
				nombre = resultado.getString("Nombre");
				fechaNacimiento = resultado.getDate("Fecha_Nacimiento");
				direccion = resultado.getString("Direccion");
				telefono = resultado.getString("Telefono");
				luzActivado = resultado.getString("Luz_Activado");
				monitoreoActivado = resultado.getString("Monitoreo_Activado");
				pastilleroActivado = resultado.getString("Pastillero_Activado");
				caidaActivado = resultado.getString("Caida_Activado");
				correoSupervisor = resultado.getString("Correo_Supervisor");
				correoFamiliar = resultado.getString("Correo_Familiar");
			}

			paciente = new Paciente(correo, contrasena, roll, nombre, fechaNacimiento, direccion, telefono, luzActivado,
					monitoreoActivado, pastilleroActivado, caidaActivado, correoSupervisor, correoFamiliar);

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

		return paciente;
	}

	public Supervisor recuperarSupervisor(String correo) {
		System.out.println("Recuperando supervisor");
		String contrasena = null, roll = null;
		ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
		Supervisor supervisor = null;
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);

			statement = conexion.prepareStatement("SELECT * FROM Usuarios WHERE Correo = ?");
			statement.setString(1, correo);
			resultado = statement.executeQuery();

			while (resultado.next()) {
				correo = resultado.getString("Correo");
				contrasena = resultado.getString("Contrasena");
				roll = resultado.getString("Roll");
			}

			statement = conexion.prepareStatement(
					"SELECT * FROM Pacientes INNER JOIN Usuarios ON Pacientes.Correo_Usuario = Usuarios.Correo WHERE Pacientes.Correo_Supervisor = ?");
			statement.setString(1, correo);
			resultado = statement.executeQuery();

			while (resultado.next()) {
				String correoPaciente = resultado.getString("Correo_Usuario");
				String contrasenaPaciente = resultado.getString("Contrasena");
				String rollPaciente = resultado.getString("Roll");
				String nombre = resultado.getString("Nombre");
				Date fechaNacimiento = resultado.getDate("Fecha_Nacimiento");
				String direccion = resultado.getString("Direccion");
				String telefono = resultado.getString("Telefono");
				String luzActivado = resultado.getString("Luz_Activado");
				String monitoreoActivado = resultado.getString("Monitoreo_Activado");
				String pastilleroActivado = resultado.getString("Pastillero_Activado");
				String caidaActivado = resultado.getString("Caida_Activado");
				String correoSupervisor = resultado.getString("Correo_Supervisor");
				String correoFamiliar = resultado.getString("Correo_Familiar");
				pacientes.add(new Paciente(correoPaciente, contrasenaPaciente, rollPaciente, nombre, fechaNacimiento,
						direccion, telefono, luzActivado, monitoreoActivado, pastilleroActivado, caidaActivado,
						correoSupervisor, correoFamiliar));
			}

			supervisor = new Supervisor(correo, contrasena, roll, pacientes);

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

		return supervisor;
	}

	public Familiar recuperarFamiliar(String correo) {
		System.out.println("Recuperando familiar");
		String contrasena = null, roll = null;
		Paciente paciente = null;
		Familiar familiar = null;
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);

			statement = conexion.prepareStatement("SELECT * FROM Usuarios WHERE Correo = ?");
			statement.setString(1, correo);
			resultado = statement.executeQuery();

			while (resultado.next()) {
				correo = resultado.getString("Correo");
				contrasena = resultado.getString("Contrasena");
				roll = resultado.getString("Roll");
			}

			statement = conexion.prepareStatement(
					"SELECT * FROM Pacientes INNER JOIN Usuarios ON Pacientes.Correo_Usuario = Usuarios.Correo WHERE Pacientes.Correo_Familiar = ?");
			statement.setString(1, correo);
			resultado = statement.executeQuery();

			while (resultado.next()) {
				String correoPaciente = resultado.getString("Correo_Usuario");
				String contrasenaPaciente = resultado.getString("Contrasena");
				String rollPaciente = resultado.getString("Roll");
				String nombre = resultado.getString("Nombre");
				Date fechaNacimiento = resultado.getDate("Fecha_Nacimiento");
				String direccion = resultado.getString("Direccion");
				String telefono = resultado.getString("Telefono");
				String luzActivado = resultado.getString("Luz_Activado");
				String monitoreoActivado = resultado.getString("Monitoreo_Activado");
				String pastilleroActivado = resultado.getString("Pastillero_Activado");
				String caidaActivado = resultado.getString("Caida_Activado");
				String correoSupervisor = resultado.getString("Correo_Supervisor");
				String correoFamiliar = resultado.getString("Correo_Familiar");
				paciente = new Paciente(correoPaciente, contrasenaPaciente, rollPaciente, nombre, fechaNacimiento,
						direccion, telefono, luzActivado, monitoreoActivado, pastilleroActivado, caidaActivado,
						correoSupervisor, correoFamiliar);
			}

			familiar = new Familiar(correo, contrasena, roll, paciente);

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

		return familiar;
	}

	public ArrayList<Pill> getPills(String correo) {
		ArrayList<Pill> pills = new ArrayList<Pill>();
		int id;
		Date fecha;
		String correoPaciente, dia, pastilla, dosis;
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);
			System.out.println("Buscando pastillas para " + correo);
			statement = conexion.prepareStatement("SELECT * FROM Pastillero WHERE Correo_Paciente = ? ORDER BY fecha DESC");
			statement.setString(1, correo);
			resultado = statement.executeQuery();

			while (resultado.next()) {
				id = resultado.getInt("ID");
				System.out.println(id);
				correoPaciente = resultado.getString("Correo_Paciente");
				System.out.println(correoPaciente);
				fecha = resultado.getTimestamp("Fecha");
				System.out.println(fecha);
				dia = resultado.getString("Dia");
				System.out.println(dia);
				pastilla = resultado.getString("Pastilla");
				System.out.println(pastilla);
				dosis = resultado.getString("Dosis");
				System.out.println(dosis);
				pills.add(new Pill(id, correoPaciente, fecha, dia, pastilla, dosis));
			}

			return pills;

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
		return pills;
	}
	
	public void eliminarPill(Pill pill) {
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);

			statement = conexion.prepareStatement("DELETE FROM Pastillero WHERE ID = ?");
			statement.setInt(1, pill.getId());
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

	// Get Patients that are currently not assigned.
	public ArrayList<Paciente> getUnsignedPatients() {
		ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
		Date fechanacimiento;
		String correopaciente, correofamiliar, correosupervisor, direccion, nombre, telefono, password, roll,
				luzactivado, monitoreoactivado, pastilleroactivado, caidaactivado;

		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);

			statement = conexion.prepareStatement(
					"SELECT * FROM Pacientes INNER JOIN Usuarios ON Pacientes.Correo_Usuario=Usuarios.Correo");
			resultado = statement.executeQuery();

			while (resultado.next()) {

				correopaciente = resultado.getString("Correo_Usuario");
				password = resultado.getString("Contrasena");
				roll = resultado.getString("Roll");
				nombre = resultado.getString("Nombre");
				fechanacimiento = resultado.getDate("Fecha_Nacimiento");
				direccion = resultado.getString("Direccion");
				telefono = resultado.getString("Telefono");
				luzactivado = resultado.getString("Luz_Activado");
				monitoreoactivado = resultado.getString("Monitoreo_Activado");
				pastilleroactivado = resultado.getString("Pastillero_Activado");
				caidaactivado = resultado.getString("Caida_Activado");
				correosupervisor = resultado.getString("Correo_Supervisor");
				correofamiliar = resultado.getString("Correo_Familiar");
				if (resultado.getString("Correo_Supervisor") == null) {
					pacientes.add(new Paciente(correopaciente, password, roll, nombre, fechanacimiento, direccion,
							telefono, luzactivado, monitoreoactivado, pastilleroactivado, caidaactivado,
							resultado.getString("Correo_Supervisor"), correofamiliar));
				}

			}

			return pacientes;

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
		return pacientes;
	}

	public ArrayList<Paciente> getAssignedPatients(String mail) {
		ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
		Date fechanacimiento;
		String correopaciente, correofamiliar, correosupervisor, direccion, nombre, telefono, password, roll,
				luzactivado, monitoreoactivado, pastilleroactivado, caidaactivado;

		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);

			statement = conexion.prepareStatement(
					"SELECT * FROM Pacientes INNER JOIN Usuarios ON Pacientes.Correo_Usuario=Usuarios.Correo WHERE Pacientes.Correo_Supervisor = ?");
			statement.setString(1, mail);
			resultado = statement.executeQuery();

			while (resultado.next()) {

				correopaciente = resultado.getString("Correo_Usuario");
				password = resultado.getString("Contrasena");
				roll = resultado.getString("Roll");
				nombre = resultado.getString("Nombre");
				fechanacimiento = resultado.getDate("Fecha_Nacimiento");
				direccion = resultado.getString("Direccion");
				telefono = resultado.getString("Telefono");
				luzactivado = resultado.getString("Luz_Activado");
				monitoreoactivado = resultado.getString("Monitoreo_Activado");
				pastilleroactivado = resultado.getString("Pastillero_Activado");
				caidaactivado = resultado.getString("Caida_Activado");
				correosupervisor = resultado.getString("Correo_Supervisor");
				correofamiliar = resultado.getString("Correo_Familiar");
				pacientes.add(new Paciente(correopaciente, password, roll, nombre, fechanacimiento, direccion, telefono,
						luzactivado, monitoreoactivado, pastilleroactivado, caidaactivado, correosupervisor,
						correofamiliar));
			}

			return pacientes;

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
		return pacientes;
	}

	// ELiminate assigned patient from the list.
	public void removeAssignedPatient(String mail) {
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);
			statement = conexion
					.prepareStatement("UPDATE Pacientes SET Correo_Supervisor = null WHERE Correo_Usuario = ?");
			statement.setString(1, mail);
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

	public void turnLight(String mail, String condition) {
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);
			statement = conexion.prepareStatement("UPDATE Pacientes SET Luz_Activado = ? WHERE Correo_Usuario = ?");
			statement.setString(1, condition);
			statement.setString(2, mail);
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

	public boolean getGraphState(String mail) {
		boolean ison = true;
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);

			statement = conexion.prepareStatement("SELECT Monitoreo_Activado FROM Pacientes WHERE Correo_Usuario = ?");
			statement.setString(1, mail);
			resultado = statement.executeQuery();

			while (resultado.next()) {
				if (resultado.getString("Monitoreo_Activado").equals("0")) {
					ison = false;
				}
			}

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
		return ison;
	}

	public void turnGraph(String mail, String condition) {
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);
			statement = conexion
					.prepareStatement("UPDATE Pacientes SET Monitoreo_Activado = ? WHERE Correo_Usuario = ?");
			statement.setString(1, condition);
			statement.setString(2, mail);
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

	public boolean getPillsState(String mail) {
		boolean ison = true;
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);

			statement = conexion.prepareStatement("SELECT Pastillero_Activado FROM Pacientes WHERE Correo_Usuario = ?");
			statement.setString(1, mail);
			resultado = statement.executeQuery();

			while(resultado.next()) {
				if (resultado.getString("Pastillero_Activado").equals("0")) {
					ison = false;
				}
			}

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
		return ison;
	}

	public void turnPills(String mail, String condition) {
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);
			statement = conexion
					.prepareStatement("UPDATE Pacientes SET Pastillero_Activado = ? WHERE Correo_Usuario = ?");
			statement.setString(1, condition);
			statement.setString(2, mail);
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

	public boolean getFallState(String mail) {
		boolean ison = true;
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);

			statement = conexion.prepareStatement("SELECT Caida_Activado FROM Pacientes WHERE Correo_Usuario = ?");
			statement.setString(1, mail);
			resultado = statement.executeQuery();

			while(resultado.next()) {
				if (resultado.getString("Caida_Activado").equals("0")) {
					ison = false;
				}
			}
			
			

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
		return ison;
	}

	public void turnFall(String mail, String condition) {
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);
			statement = conexion.prepareStatement("UPDATE Pacientes SET Caida_Activado = ? WHERE Correo_Usuario = ?");
			statement.setString(1, condition);
			statement.setString(2, mail);
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

	public ArrayList<String> getFalls(String mail){
		ArrayList<String> falls = new ArrayList<String>();
		try {

			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);

			statement = conexion.prepareStatement(
					"SELECT Datos.Fecha, Caida.Sensor_Caida FROM Datos INNER JOIN Caida ON Datos.ID=Caida.ID");
			resultado = statement.executeQuery();

			while (resultado.next()) {
				String result = resultado.getTimestamp("Fecha").toString();
				falls.add(result);
			}

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

		return falls;
	}
	
	// Get patient's personal info, like location, age, name ...
	// @param mail : Mail of the patient.
	public Paciente getPatientData(String mail) {
		Paciente paciente = null;
		try {
			String password, roll, nombre, direccion, telefono, correoSupervisor, correoFamiliar, luzActivado,
					monitoreoActivado, pastilleroActivado, caidaActivado;
			Date fechaNacimiento;

			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);

			statement = conexion
					.prepareStatement("SELECT * FROM Pacientes,Usuarios WHERE Pacientes.Correo_Usuario = ?");
			statement.setString(1, mail);
			resultado = statement.executeQuery();

			while (resultado.next()) {
				nombre = resultado.getString("Nombre");
				password = resultado.getString("Contrasena");
				roll = resultado.getString("Roll");
				fechaNacimiento = resultado.getDate("Fecha_Nacimiento");
				direccion = resultado.getString("Direccion");
				telefono = resultado.getString("Telefono");
				luzActivado = resultado.getString("Luz_Activado");
				monitoreoActivado = resultado.getString("Monitoreo_Activado");
				pastilleroActivado = resultado.getString("Pastillero_Activado");
				caidaActivado = resultado.getString("Caida_Activado");
				correoSupervisor = resultado.getString("Correo_Supervisor");
				correoFamiliar = resultado.getString("Correo_Familiar");
				paciente = new Paciente(mail, password, roll, nombre, fechaNacimiento, direccion, telefono, luzActivado,
						monitoreoActivado, pastilleroActivado, caidaActivado, correoSupervisor, correoFamiliar);
			}

			return paciente;

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

		return paciente;
	}

	public void modifyPatientData(String name, String location, String phone, String mail) {
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);
			System.out.println("Correo a modificar: " + mail);
			statement = conexion.prepareStatement(
					"Update Pacientes SET Nombre = ?,  Direccion = ?, Telefono = ?  WHERE Correo_Usuario = ?  ;");
			statement.setString(1, name);
			statement.setString(2, location);
			statement.setString(3, phone);
			statement.setString(4, mail);
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

	// Añadir paciente a la lista de los supervisados.
	// mail del paciente, mailsupervisor del supervisor.
	public Paciente addUnsignedPatient(String mail, String mailsupervisor) {
		Paciente paciente = null;

		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);

			statement = conexion
					.prepareStatement("UPDATE Pacientes SET Correo_Supervisor = ? WHERE Correo_Usuario = ?");
			statement.setString(1, mailsupervisor);
			statement.setString(2, mail);
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
		return paciente;
	}

	public ArrayList<Puntos> getAccelerationSleepData(String mail) {
		ArrayList<Puntos> data = new ArrayList<Puntos>();
		try {

			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);

			statement = conexion.prepareStatement(
					"SELECT Datos.Fecha, Acelerometro.Sensor_Acelerometro FROM Datos INNER JOIN Acelerometro ON Datos.ID=Acelerometro.ID");
			resultado = statement.executeQuery();

			while (resultado.next()) {
				String[] result = resultado.getTimestamp("Fecha").toString().split(" ");
				String hora = result[1].replace(".0", "");
				data.add(new Puntos(hora, resultado.getInt("Sensor_Acelerometro")));
			}

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

		return data;
	}

	public ArrayList<Puntos> getPresureSleepData(String mail) {
		System.out.println("Correo del paciente: " + mail);
		ArrayList<Puntos> data = new ArrayList<Puntos>();
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://esp.uem.es:3306/pr_svji";
		String usuario = "jromero";
		String clave = "qazwsx";
		PreparedStatement statement = null;
		Connection conexion = null;
		ResultSet resultado = null;

		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);

			statement = conexion.prepareStatement(
					"SELECT Datos.Fecha, Presion.Sensor_Presion FROM Datos INNER JOIN Presion ON Datos.ID=Presion.ID where Datos.Correo_Paciente=?");
			statement.setString(1, "paco@gmail.com");
			resultado = statement.executeQuery();
		
			while (resultado.next()) {
				System.out.println("Encontramos datos");
				String[] result = resultado.getTimestamp("Fecha").toString().split(" ");
				String hora = result[1].replace(".0", "");
				int dato = resultado.getInt("Sensor_Presion");
				data.add(new Puntos(hora, dato));
			}

			System.out.println(data.size());

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
		System.out.println("Size:" + data.size());
		return data;
	}

	public ArrayList<Puntos> getPresureSleepDataInterval(String mail, Timestamp t1, Timestamp t2) {
		System.out.println("Correo del paciente: " + mail);
		ArrayList<Puntos> data = new ArrayList<Puntos>();

		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);

			statement = conexion.prepareStatement(
					"SELECT Datos.Fecha, Presion.Sensor_Presion FROM Datos INNER JOIN Presion ON Datos.ID=Presion.ID where Datos.Correo_Paciente=? AND Datos.Fecha  BETWEEN ? AND ?");
			statement.setString(1, "paco@gmail.com");
			statement.setTimestamp(2, t1);
			statement.setTimestamp(3, t2);
			resultado = statement.executeQuery();
		
			while (resultado.next()) {
				System.out.println("Encontramos datos");
				String[] result = resultado.getTimestamp("Fecha").toString().split(" ");
				String hora = result[1].replace(".0", "");
				int dato = resultado.getInt("Sensor_Presion");
				data.add(new Puntos(hora, dato));
			}

			System.out.println(data.size());

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
		System.out.println("Size:" + data.size());
		return data;
	}
	
	public ArrayList<Puntos> getAccelerationSleepDataInterval(String mail, Timestamp t1, Timestamp t2) {
		System.out.println("Correo del paciente: " + mail);
		ArrayList<Puntos> data = new ArrayList<Puntos>();

		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);

			statement = conexion.prepareStatement(
					"SELECT Datos.Fecha, Acelerometro.Sensor_Acelerometro FROM Datos INNER JOIN Acelerometro ON Datos.ID=Acelerometro.ID where Datos.Correo_Paciente=? AND Datos.Fecha  BETWEEN ? AND ?");
			statement.setString(1, "paco@gmail.com");
			statement.setTimestamp(2, t1);
			statement.setTimestamp(3, t2);
			resultado = statement.executeQuery();
		
			while (resultado.next()) {
				System.out.println("Encontramos datos");
				String[] result = resultado.getTimestamp("Fecha").toString().split(" ");
				String hora = result[1].replace(".0", "");
				int dato = resultado.getInt("Sensor_Acelerometro");
				data.add(new Puntos(hora, dato));
			}

			System.out.println(data.size());

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
		System.out.println("Size:" + data.size());
		return data;
	}
	
	public void createPill(Pill pill) {

		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);
			System.out.println(pill.getCorreoPaciente() + "/" + pill.getFecha() + "/" + pill.getDia() + "/"
					+ pill.getPastilla() + "/" + pill.getDosis());
			statement = conexion.prepareStatement("Insert into pastillero values (null,?,?,?,?,?)");
			statement.setString(1, pill.getCorreoPaciente());
			System.out.println("Feecha del pastilla: "+pill.getFecha().toString());
			Timestamp ts=new Timestamp(pill.getFecha().getTime());  
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            System.out.println("Antiguo formato: "+ts);
            formatter.format(ts);
            System.out.println("Nuevo formato: "+ts);
			statement.setTimestamp(2, ts);
			statement.setString(3, pill.getDia());
			statement.setString(4, pill.getPastilla());
			statement.setString(5, pill.getDosis());
			statement.execute();

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

	// Devuelve los datos del paciente de ese familiar.
	public Paciente getFamiliarsPatient(String mail) {
		Paciente paciente = null;
		try {
			String mailpatient, password, roll, nombre, direccion, telefono, correoSupervisor, correoFamiliar,
					luzActivado, monitoreoActivado, pastilleroActivado, caidaActivado;
			Date fechaNacimiento;

			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);
			System.out.println("Correo del familiar: " + mail);
			statement = conexion
					.prepareStatement("SELECT * FROM Pacientes,Usuarios WHERE Pacientes.Correo_Familiar = ?");
			statement.setString(1, mail);
			resultado = statement.executeQuery();

			while (resultado.next()) {
				mailpatient = resultado.getString("Correo_Usuario");
				nombre = resultado.getString("Nombre");
				password = resultado.getString("Contrasena");
				roll = resultado.getString("Roll");
				fechaNacimiento = resultado.getDate("Fecha_Nacimiento");
				direccion = resultado.getString("Direccion");
				telefono = resultado.getString("Telefono");
				luzActivado = resultado.getString("Luz_Activado");
				monitoreoActivado = resultado.getString("Monitoreo_Activado");
				pastilleroActivado = resultado.getString("Pastillero_Activado");
				caidaActivado = resultado.getString("Caida_Activado");
				correoSupervisor = resultado.getString("Correo_Supervisor");
				correoFamiliar = resultado.getString("Correo_Familiar");
				paciente = new Paciente(mailpatient, password, roll, nombre, fechaNacimiento, direccion, telefono,
						luzActivado, monitoreoActivado, pastilleroActivado, caidaActivado, correoSupervisor,
						correoFamiliar);
			}

			return paciente;

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

		return paciente;
	}

	public void createKey(String mail, String key) {
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);
			statement = conexion.prepareStatement("Insert into recuperarcontrasena values (?,?)");
			statement.setString(1, mail);
			statement.setString(2, key);
			statement.execute();

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

	public void modifyPassword(String mail, String pass) {
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);
			statement = conexion.prepareStatement("UPDATE Usuarios SET Contrasena = ? WHERE Correo = ?");
			statement.setString(1, pass);
			statement.setString(2, mail);
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

	public void deleteKey(String mail) {
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);
			statement = conexion.prepareStatement("DELETE FROM RecuperarContrasena WHERE Correo=?;");
			statement.setString(1, mail);
			statement.execute();

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
	public ArrayList<Mensaje> getChats(String correoEmisor, String correoReceptor) {
		System.out.println("Recuperando mensajes");
		int id = -1;
		String texto = null;
		Date fecha = null;
		ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);

			statement = conexion
					.prepareStatement("SELECT * FROM Mensajes WHERE (Correo_Emisor = ? AND Correo_Receptor = ?) OR (Correo_Emisor = ? AND Correo_Receptor = ?) ORDER BY ID ASC");
			statement.setString(1, correoEmisor);
			statement.setString(2, correoReceptor);
			statement.setString(3, correoReceptor);
			statement.setString(4, correoEmisor);
			resultado = statement.executeQuery();

			while (resultado.next()) {
				id = resultado.getInt("ID");
				fecha = resultado.getTimestamp("Fecha");
				texto = resultado.getString("Texto");
				correoEmisor = resultado.getString("Correo_Emisor");
				correoReceptor = resultado.getString("Correo_Receptor");

				Mensaje mensaje = new Mensaje(id, texto, fecha, correoEmisor, correoReceptor);

				mensajes.add(mensaje);
			}

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

		return mensajes;
	}
	
	public void createChat(String texto, String fecha, String mail1, String mail2) {
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, clave);
			statement = conexion.prepareStatement("Insert into Mensajes values (null,?,?,?,?)");
			statement.setString(1, fecha);
			statement.setString(2, texto);
			statement.setString(3, mail1);
			statement.setString(4, mail2);
			statement.execute();

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
