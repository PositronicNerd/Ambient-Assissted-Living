package paginaLogin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Familiar;
import model.ManejadorDeFichero;
import model.Paciente;
import model.RecuperarDatos;
import model.Supervisor;
import model.Usuario;
import paginaPrincipalPaciente.ControladorPrincipalUsuario;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import PaginaPerfilUsuario.ControladorPerfilUsuario;
import PaginaPrincipalFamiliar.ControladorPrincipalFamiliar;
import PaginaPrincipalSupervisor.ControladorPrincipalSupervisor;
import javafx.event.ActionEvent;
import paginaRecuperacionPassword.ControladorRecuperacionPassword;
import paginaSensorCaidas.ControladorSensorCaidas;

public class ControladorLogin {

	@FXML
	private ImageView imagenLogin;

	@FXML
	private TextField campoCorreo;

	@FXML
	private PasswordField campoContrasena;

	@FXML
	private Button botonEntrar;

	@FXML
	private Button botonOlvidado;

	@FXML
	private Label labelError;

	@FXML
	private AnchorPane finlandpane;

	@FXML
	private AnchorPane usapane;

	@FXML
	private AnchorPane spainpane;

	@FXML
	private AnchorPane germanypane;

	@FXML
	private AnchorPane japanpane;

	private String language = "english";

	private String[] languagedata;

	Stage stage;

	RecuperarDatos recuperar = new RecuperarDatos();
	ManejadorDeFichero lectura = new ManejadorDeFichero();

	@FXML
	public void initialize() {
		languagedata = lectura.getLanguageData(language);
		usapane.setStyle(
				"-fx-background-color: #448AFF; -fx-effect: innerShadow( gaussian , #1c1515b2 ,  10, 0.3, 0, 0 );");
	}

	//Action where we check if that user with that password exists and in affirmative case, proceed to the login.
	public void botonEntrar(ActionEvent e) {
		System.out.println("Boton entrar");

		Usuario usuario = recuperar.recuperarUsuario(campoCorreo.getText(), campoContrasena.getText());

		if (usuario.getCorreo() != null && usuario.getContrasena() != null && usuario.getRoll() != null) {
			System.out.println("Entrando");
			validateLogin(usuario);
		} else {
			System.out.println("Error al entrar");
			labelError.setText("Correo o contrasena incorrectos.");
		}
	}

	public void BotonOlvidado(ActionEvent e) {
		System.out.println("Boton contrase�a olvidada");
		Stage stage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/paginaRecuperacionPassword/VentanaRecuperacionPass.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			ControladorRecuperacionPassword contr = loader.getController();
			contr.initialize(languagedata);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(languagedata[1]);
			stage.setScene(scene);
			stage.show();
		} catch (Exception E) {
			E.printStackTrace();
		}
	}

	public void validateLogin(Usuario usuario) {

		switch (usuario.getRoll()) {
		case "Supervisor":
			System.out.println("Supervisor");
			Supervisor supervisor = recuperar.recuperarSupervisor(campoCorreo.getText());
			displayInfoSupervisor(supervisor, languagedata);
			break;
		case "Paciente":
			System.out.println("Paciente");
			Paciente paciente = recuperar.recuperarPaciente(campoCorreo.getText());
			displayInfoPatient(paciente, languagedata);
			break;
		case "Familiar":
			System.out.println("Familiar");
			Familiar familiar = recuperar.recuperarFamiliar(campoCorreo.getText());
			System.out.println("Familiar encontrado: "+familiar.getCorreo());
			displayInfoFamiliar(familiar, languagedata);
			break;
		}

	}

	// Muestra informacion sobre el usuario seleccionado.
	public void displayInfoPatient(Paciente paciente, String[] languagedata) {
		System.out.println();
		Stage stage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/paginaPrincipalPaciente/PaginaPrincipalPaciente.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			ControladorPrincipalUsuario contr = loader.getController();
			contr.getEmail(paciente, languagedata[11], languagedata[12]);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(languagedata[9]);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void displayInfoSupervisor(Supervisor supervisor, String[] languagedata) {
		Stage stage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/PaginaPrincipalSupervisor/PaginaPrincipalSupervisor.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			ControladorPrincipalSupervisor contr = loader.getController();
			contr.setEmail(supervisor, languagedata);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(languagedata[7]);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void displayInfoFamiliar(Familiar familiar, String[] languagedata) {
		Stage stage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/PaginaPrincipalFamiliar/PantallaPrincipalFamiliar.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			ControladorPrincipalFamiliar contr = loader.getController();
			contr.getEmail(familiar, languagedata);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(languagedata[8]);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void chooseFinnish() {
		language = "finnish";
		languagedata = lectura.getLanguageData(language);
		stage = (Stage) finlandpane.getScene().getWindow();
		stage.setTitle(languagedata[0]);
		usapane.setStyle("-fx-background-color:  #006db3;");
		finlandpane.setStyle(
				"-fx-background-color: #448AFF; -fx-effect: innerShadow( gaussian , #1c1515b2 ,  10, 0.3, 0, 0 );");
		spainpane.setStyle("-fx-background-color:  #006db3;");
		germanypane.setStyle("-fx-background-color:  #006db3;");
		japanpane.setStyle("-fx-background-color:  #006db3;");
	}

	public void chooseEnglish() {
		language = "english";
		languagedata = lectura.getLanguageData(language);
		stage = (Stage) finlandpane.getScene().getWindow();
		stage.setTitle(languagedata[0]);
		usapane.setStyle(
				"-fx-background-color: #448AFF; -fx-effect: innerShadow( gaussian , #1c1515b2 ,  10, 0.3, 0, 0 );");
		finlandpane.setStyle("-fx-background-color:  #006db3;");
		spainpane.setStyle("-fx-background-color:  #006db3;");
		germanypane.setStyle("-fx-background-color:  #006db3;");
		japanpane.setStyle("-fx-background-color:  #006db3;");
	}

	public void chooseSpanish() {
		language = "spanish";
		languagedata = lectura.getLanguageData(language);
		stage = (Stage) finlandpane.getScene().getWindow();
		stage.setTitle(languagedata[0]);
		usapane.setStyle("-fx-background-color:  #006db3;");
		finlandpane.setStyle("-fx-background-color:  #006db3;");
		spainpane.setStyle(
				"-fx-background-color: #448AFF; -fx-effect: innerShadow( gaussian , #1c1515b2 ,  10, 0.3, 0, 0 );");
		germanypane.setStyle("-fx-background-color:  #006db3;");
		japanpane.setStyle("-fx-background-color:  #006db3;");
	}

	public void chooseGerman() {
		language = "german";
		languagedata = lectura.getLanguageData(language);
		stage = (Stage) finlandpane.getScene().getWindow();
		stage.setTitle(languagedata[0]);
		usapane.setStyle("-fx-background-color:  #006db3;");
		finlandpane.setStyle("-fx-background-color:  #006db3;");
		spainpane.setStyle("-fx-background-color:  #006db3;");
		germanypane.setStyle(
				"-fx-background-color: #448AFF; -fx-effect: innerShadow( gaussian , #1c1515b2 ,  10, 0.3, 0, 0 );");
		japanpane.setStyle("-fx-background-color:  #006db3;");
	}

	public void chooseJapanese() {
		language = "japanese";
		languagedata = lectura.getLanguageData(language);
		stage = (Stage) finlandpane.getScene().getWindow();
		stage.setTitle(languagedata[0]);
		usapane.setStyle("-fx-background-color:  #006db3;");
		finlandpane.setStyle("-fx-background-color:  #006db3;");
		spainpane.setStyle("-fx-background-color:  #006db3;");
		germanypane.setStyle("-fx-background-color:  #006db3;");
		japanpane.setStyle(
				"-fx-background-color: #448AFF; -fx-effect: innerShadow( gaussian , #1c1515b2 ,  10, 0.3, 0, 0 );");
	}

}
