package paginaChatUsuario;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.InsertarDatos;
import model.Mensaje;
import model.RecuperarDatos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;

public class ControladorChatUsuario {

	@FXML
	private Tab tab1;

	@FXML
	private TextArea patienttextarea;

	@FXML
	private AnchorPane patientsendbutton;

	@FXML
	private TextField patienttextfield;

	@FXML
	private Tab tab2;

	@FXML
	private TextArea supervisortextarea;

	@FXML
	private Button supervisorsendbutton;

	@FXML
	private TextField supervisortextfield;

	String mailemisor1;
	String mailreceptor1, mailreceptor2;
	Date uDate;
	DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");


	RecuperarDatos recuperar = new RecuperarDatos();
	InsertarDatos insertar = new InsertarDatos();
	// Siendo el mail1 el cliente, y mail2 y mail3 las otras dos personas con las
	// que se va a comunicar.
	// tab1text será siempre el de paciente, mientras que tab2text será
	// familiar/supervisor.
	public void getEmail(String mail1, String mail2, String mail3, String tab1text, String tab2text) {
		mailreceptor1 = mail1;
		mailemisor1 = mail2;
		mailreceptor2 = mail3;
		ArrayList<Mensaje> chat1, chat2;
		tab1.setText(tab1text);
		tab2.setText(tab2text);
		try {
			chat1 = recuperar.getChats(mail1, mail2);
			
			System.out.println("Chat size:" + chat1.size());
			for (int i = 0; i < chat1.size(); i++) {
				patienttextarea.setText(patienttextarea.getText() + "[" + chat1.get(i).getFecha() + "]"
						+ chat1.get(i).getCorreo_emisor() + ": " + chat1.get(i).getTexto() + "\n");
				System.out.println("[" + chat1.get(i).getFecha() + "]"
						+ chat1.get(i).getCorreo_emisor() + ": " + chat1.get(i).getTexto() + "\n");
			}
		} catch (NullPointerException e) {
			System.out.println("No hay mensajes para mostrar en paciente");
		}
		try {
			chat2 = recuperar.getChats(mail2, mail3);
			for (int i = 0; i < chat2.size(); i++) {
				supervisortextarea.setText(supervisortextarea.getText() + "[" + chat2.get(i).getFecha() + "]"
						+ chat2.get(i).getCorreo_emisor() + ": " + chat2.get(i).getTexto() + "\n");
				System.out.println("[" + chat2.get(i).getFecha() + "]"
						+ chat2.get(i).getCorreo_emisor() + ": " + chat2.get(i).getTexto() + "\n");
			}
		} catch (NullPointerException e) {
			System.out.println("No hay mensajes para mostrar en el otro chat");
		}

		supervisortextarea.setWrapText(true);
		patienttextarea.setWrapText(true);

		// Ordenar chat1 y chat2 por ID, se entiende que acabarían siendo en orden
		// cronológico.

	}

	public void sendMessagePatient() {
		uDate = new java.util.Date();
		System.out.println("Time in java.util.Date is : " + uDate);
		patienttextarea.setText(patienttextarea.getText() + "[" + df.format(uDate) + "]" + mailemisor1 + ": "
				+ patienttextfield.getText() + "\n");
		// recuperar.createChat(patienttextfield.getText(), df.format(uDate),
		// mailemisor1, mailreceptor1);
		insertar.insertarMensaje(uDate, patienttextfield.getText(), mailemisor1, mailreceptor1);
		patienttextfield.setText("");}

	public void sendMessageSupervisor() {
		uDate = new java.util.Date();
		System.out.println("Time in java.util.Date is : " + uDate);
		supervisortextarea.setText(supervisortextarea.getText() + "[" + df.format(uDate) + "]" + mailemisor1 + ": "
				+ supervisortextfield.getText() + "\n");
		// recuperar.createChat(supervisortextfield.getText(), df.format(uDate),
		// mailemisor1, mailreceptor2);
		insertar.insertarMensaje(uDate, supervisortextfield.getText(), mailemisor1, mailreceptor2);
		supervisortextfield.setText("");
	}

}
