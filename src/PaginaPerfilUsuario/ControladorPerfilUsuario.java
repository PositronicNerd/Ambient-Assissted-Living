package PaginaPerfilUsuario;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.ManejadorDeFichero;
import model.Paciente;
import model.RecuperarDatos;

public class ControladorPerfilUsuario {

	@FXML
	private ImageView profilepic;

	@FXML
	private Button botonguardar;

	@FXML
	private TextField textmail;

	@FXML
	private TextField textname;

	@FXML
	private TextField textage;

	@FXML
	private TextField textlocation;

	@FXML
	private TextField textphone;

	String mail;
	
	RecuperarDatos recuperar = new RecuperarDatos();

	Paciente aux;
	
	public void getEmail(Paciente paciente) {
		aux = paciente;
		textmail.setText(paciente.getCorreo());
		textmail.setEditable(false);
		textname.setText(paciente.getNombre());
		textage.setText(paciente.getFechaNacimiento().toString());
		textlocation.setText(paciente.getDireccion());
		textphone.setText(paciente.getTelefono());
		ImageView send = new ImageView("/Multimedia/save.png");
		send.setFitHeight(40);
		send.setFitWidth(40);
		botonguardar.setGraphic(send);
		System.out.println("Correo mostrado: "+textmail.getText());
	}
	
	public void modifyPatient() {
		recuperar.modifyPatientData(textname.getText(), textlocation.getText(), textphone.getText(), aux.getCorreo());
		Stage stage = (Stage) botonguardar.getScene().getWindow();
		stage.close();
	}

	
}
