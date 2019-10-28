package PaginaPrincipalFamiliar;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import PaginaPastillero.ControladorPastillero;
import PaginaPerfilUsuario.ControladorPerfilUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Familiar;
import model.Paciente;
import model.RecuperarDatos;
import paginaChatUsuario.ControladorChatUsuario;
import paginaMonitorizacion.ControladorMonitorizacion;
import paginaSensorCaidas.ControladorSensorCaidas;

public class ControladorPrincipalFamiliar {

	@FXML
	private AnchorPane anchorpills;

	@FXML
	private ImageView imagenpastillero;

	@FXML
	private AnchorPane anchorfalling;

	@FXML
	private ImageView imagencaida;

	@FXML
	private AnchorPane anchorsleep;

	@FXML
	private ImageView imagendormir;

	@FXML
	private AnchorPane anchorchat;

	@FXML
	private ImageView imagenchat;

	@FXML
	private AnchorPane anchorprofile;

	@FXML
	private ImageView imagenperfil;

	@FXML
	private AnchorPane anchorlights;

	@FXML
	private ImageView imagenluces;
	
	@FXML
	private ImageView imagensalir;

	private boolean lights;

	String[] languagedata;
	
	RecuperarDatos recuperar = new RecuperarDatos();
	
	Paciente paciente ;
	
	String IP = "10.34.87.131";
	int port = 9000;
	Socket socket = null; 
	DataOutputStream out=null;		
	
	public void getEmail(Familiar familiar, String [] languagedata) {
		try {
			socket = new Socket(IP, port);
			out = new DataOutputStream(socket.getOutputStream());	
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Familiar entrante: "+familiar.getCorreo());
		this.languagedata=languagedata;
		paciente = recuperar.getFamiliarsPatient(familiar.getCorreo());
		if (paciente.getLuzActivado().equals("1")) {
			imagenluces.setImage(new Image("/Multimedia/lighton.png"));
			lights=false;
			imagenluces.setFitHeight(55);
			imagenluces.setFitWidth(55);
		} else {
			imagenluces.setImage(new Image("/Multimedia/lightoff.png"));
			lights=true;
			imagenluces.setFitHeight(55);
			imagenluces.setFitWidth(55);
		}
	}

	public void showPatientInfo() {
		System.out.println("Ejecutando información personal paciente.");
		Stage stage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/PaginaPerfilUsuario/PaginaPerfilUsuario.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			ControladorPerfilUsuario contr = loader.getController();
			contr.getEmail(paciente);
			stage.setTitle(languagedata[5]);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void changeLight() {
		if (lights) {
			System.out.println("Apaga luces");
			imagenluces.setImage(new Image("/Multimedia/lightoff.png"));
			lights=false;
			imagenluces.setFitHeight(55);
			imagenluces.setFitWidth(55);
			recuperar.turnLight(paciente.getCorreo(),"0");
			System.out.println(paciente.getCorreo());
			sendServer("Lights OFF");
		} else {
			System.out.println("Enciende luces");
			imagenluces.setImage(new Image("/Multimedia/lighton.png"));
			lights=true;
			imagenluces.setFitHeight(55);
			imagenluces.setFitWidth(55);
			recuperar.turnLight(paciente.getCorreo(),"1");
			System.out.println(paciente.getCorreo()+" .");
			sendServer("Lights ON");
		}
	}

	public void getSleepGraph() {
		System.out.println("Ejecutando informacion personal paciente.");
		Stage stage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/paginaMonitorizacion/VentanaMonitorizacion.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			ControladorMonitorizacion contr = loader.getController();
			System.out.println("Se pasa el mail "+paciente.getCorreo());
			contr.getEmail(paciente.getCorreo(), languagedata);
			stage.setTitle(languagedata[2]);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void getPills() {
		Stage stage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/PaginaPastillero/Pastillero.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			ControladorPastillero contr = loader.getController();
			contr.getEmail(paciente.getCorreo(), languagedata);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(languagedata[4]);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public void getFallingLog() {
		
		System.out.println("Log de caidas");
		Stage stage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/paginaSensorCaidas/VentanaCaidas.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			ControladorSensorCaidas contr = loader.getController();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(languagedata[6]);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getChat() {
		System.out.println("Chats con el paciente.");
		Stage stage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/paginaChatUsuario/VentanaChatUsuario.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			ControladorChatUsuario contr = loader.getController();
			contr.getEmail(paciente.getCorreo(), paciente.getCorreoFamiliar(), paciente.getCorreoSupervisor(), languagedata[13], languagedata[12]);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(languagedata[2]);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}}

	public void botonSalir() {
		Stage stage = (Stage) imagensalir.getScene().getWindow();
		stage.close();
	}
	
	public void sendServer(String message) {
		try {
			//URL whatismyip = new URL("http://checkip.amazonaws.com");
			//BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			
			out.writeUTF(message);
			out.flush();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
