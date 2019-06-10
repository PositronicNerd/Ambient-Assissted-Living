package paginaRecuperacionPassword;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.RecuperarDatos;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.security.SecureRandom;
import java.math.BigInteger;

public class ControladorRecuperacionPassword {

	@FXML
	private AnchorPane root;

	@FXML
	private Label labelinstructions;

	@FXML
	private TextField textclave;

	@FXML
	private TextField textpassword;

	@FXML
	private Button botonAceptar;

	@FXML
	private TextField textcorreo;

	@FXML
	private Button sendcorreo;

	RecuperarDatos recuperar = new RecuperarDatos();

	String key;

	String mail;

	Stage stage;

	private String pass;

	public void initialize(String[] text) {
		labelinstructions.setText(text[10]);
		labelinstructions.setWrapText(true);
		labelinstructions.setTextAlignment(TextAlignment.JUSTIFY);
	}

	public void createKey() {
		mail = textcorreo.getText();
		SecureRandom random = new SecureRandom();
		String pss = new BigInteger(10, random).toString(8);
		System.out.println("Código a generar: " + pss);
		String destinatario = textcorreo.getText();
		recuperar.createKey(mail, pss);
		enviarConGMail(destinatario, pss);
		pass = pss;
		System.out.println("Nueva key:"+pass);
		System.out.println(pss);

	}

	private static void enviarConGMail(String destinatario, String pss) {

		// Correo y contrasenia del correo del proyecto
		String remitente = "proyecto.svji"; // proyecto.svji@gmail.com
		String password = "Pss_JAVA";

		// Establecer la sesion de JavaMail

		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // El servidor SMTP de Google
		props.put("mail.smtp.user", remitente);
		props.put("mail.smtp.clave", password);
		props.put("mail.smtp.auth", "true"); // Usar autenticación mediante usuario y clave
		props.put("mail.smtp.starttls.enable", "true"); // Para conectar de manera segura al servidor SMTP
		props.put("mail.smtp.port", "587"); // El puerto SMTP seguro de Google

		Session session = Session.getDefaultInstance(props);

		// Construimos el mensaje

		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(remitente));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			message.setSubject("Recuperacion password"); // Asunto
			message.setText("Estimado cliente," + '\n' + "Su nueva contraseña es: " + pss); // Cuerpo

			// Envio del mensaje

			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", remitente, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}

	public void changePassword() {
		System.out.println("Pass a comparar:"+pass);
		if (pass.equals(textclave.getText())) {
			recuperar.modifyPassword(mail, textpassword.getText());
			recuperar.deleteKey(mail);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText("Wrong key");
			alert.setContentText("The key you wrote is not the one you received");
			alert.showAndWait();
		}

	}

}
