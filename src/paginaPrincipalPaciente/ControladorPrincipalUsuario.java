package paginaPrincipalPaciente;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.ClientThread;
import model.InsertarDatos;
import model.Mensaje;
import model.Paciente;
import model.Puntos;
import model.RecuperarDatos;

public class ControladorPrincipalUsuario {

	@FXML
	private ImageView imagendormir;

	@FXML
	private ImageView imagenluz;

	@FXML
	private Tab TabFamiliar;

	@FXML
	private TextField TextMensajeFamiliar;

	@FXML
	private Button BotonMensajeFamiliar;

	@FXML
	private TextArea TextAreaFamiliar;

	@FXML
	private Tab TabSupervisor;

	@FXML
	private TextArea TextAreaSupervisor;

	@FXML
	private TextField TextMensajeSupervisor;

	@FXML
	private Button BotonMensajeSupervisor;

	@FXML
	Paciente paciente;

	Date uDate;
	DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

	RecuperarDatos recuperar = new RecuperarDatos();
	InsertarDatos insertar = new InsertarDatos();
	ClientThread client = new ClientThread();
	public void getEmail(Paciente paciente, String tabfamiliartext, String tabsupervisortext) {
		System.out.println("Paciente: " + paciente.getCorreo());
		this.paciente = recuperar.getPatientData(paciente.getCorreo());
		TabFamiliar.setText(tabfamiliartext);
		TabSupervisor.setText(tabsupervisortext);
		TextAreaFamiliar
				.setStyle("-fx-control-inner-background: #448AFF; -fx-background-radius: 10; -fx-text-fill: white;");
		TextAreaSupervisor
				.setStyle("-fx-control-inner-background: #448AFF; -fx-background-radius: 10; -fx-text-fill: white;");
		ImageView tickfamiliar = new ImageView("/Multimedia/send.png");
		ImageView ticksupervisor = new ImageView("/Multimedia/send.png");
		tickfamiliar.setFitHeight(70);
		tickfamiliar.setFitWidth(70);
		ticksupervisor.setFitHeight(70);
		ticksupervisor.setFitWidth(70);
		BotonMensajeFamiliar.setGraphic(tickfamiliar);
		BotonMensajeSupervisor.setGraphic(ticksupervisor);
		if (paciente.getLuzActivado().contentEquals("1")) {
			imagenluz.setImage(new Image("/Multimedia/lighton.png"));
			imagenluz.setFitHeight(175);
			imagenluz.setFitWidth(175);
		} else {
			imagenluz.setImage(new Image("/Multimedia/lightoff.png"));
			imagenluz.setFitHeight(175);
			imagenluz.setFitWidth(175);
		}
		calculateSleep();

		try {
			ArrayList<Mensaje> chat1, chat2;
			chat1 = recuperar.getChats(paciente.getCorreo(), paciente.getCorreoFamiliar());
			chat2 = recuperar.getChats(paciente.getCorreo(), paciente.getCorreoSupervisor());
			TextAreaFamiliar.setWrapText(true);
			TextAreaSupervisor.setWrapText(true);

			// Ordenar chat1 y chat2 por ID, se entiende que acabarían siendo en orden
			// cronológico.

			for (int i = 0; i < chat1.size(); i++) {
				TextAreaFamiliar.setText(TextAreaFamiliar.getText() + "[" + chat1.get(i).getFecha() + "]"
						+ chat1.get(i).getCorreo_emisor() + ": " + chat1.get(i).getTexto() + "\n");
			}

			for (int i = 0; i < chat2.size(); i++) {
				TextAreaSupervisor.setText(TextAreaSupervisor.getText() + "[" + chat2.get(i).getFecha() + "]"
						+ chat2.get(i).getCorreo_emisor() + ": " + chat2.get(i).getTexto() + "\n");
			}
		} catch (NullPointerException e) {
			System.out.println("No hay mensajes cargados.");
		}
		client.setUp(paciente.getCorreo());
		client.run();
	}

	public void calculateSleep() {
		System.out.println("Calculamos sleep");
		int sleep = 0;
		ArrayList<Puntos> data = new ArrayList<Puntos>();
		data = recuperar.getPresureSleepData(paciente.getCorreo());

		if (data != null) {
			for (int i = 0; i < data.size(); i++) {
				sleep += data.get(i).getValor();
			}

			if (sleep >= (data.size() / 3)) {
				imagendormir.setImage(new Image("/Multimedia/sleepgood.png"));
				imagendormir.setFitHeight(175);
				imagendormir.setFitWidth(175);
			} else if (sleep < (data.size() / 3) && sleep > (data.size() / 4)) {
				imagendormir.setImage(new Image("/Multimedia/sleepnormal.png"));
				imagendormir.setFitHeight(175);
				imagendormir.setFitWidth(175);
			} else {
				imagendormir.setImage(new Image("/Multimedia/sleepbad.png"));
				imagendormir.setFitHeight(175);
				imagendormir.setFitWidth(175);
			}

		}

	}

	public String getUsername(String mail) {
		String[] name = mail.split("@");
		System.out.println("Seleccionado el usuario " + name[0]);
		return name[0];
	}

	public void sendMessageRelative() {
		uDate = new java.util.Date();
		System.out.println("Time in java.util.Date is : " + uDate);
		TextAreaFamiliar.setText(TextAreaFamiliar.getText() + "[" + df.format(uDate) + "]: "
				+ TextMensajeFamiliar.getText() + "\n");
		insertar.insertarMensaje(uDate, TextMensajeFamiliar.getText(), paciente.getCorreo(),
				paciente.getCorreoFamiliar());
		TextMensajeFamiliar.setText("");
	}

	public void sendMessageSupervisor() {
		uDate = new java.util.Date();
		System.out.println("Time in java.util.Date is : " + uDate);
		TextAreaSupervisor.setText(TextAreaSupervisor.getText() + "[" + df.format(uDate) + "]: " + TextMensajeSupervisor.getText() + "\n");
// recuperar.createChat(TextMensajeSupervisor.getText(), df.format(uDate),
// paciente.getCorreo(),paciente.getCorreoSupervisor());
		insertar.insertarMensaje(uDate, TextMensajeSupervisor.getText(), paciente.getCorreo(),
				paciente.getCorreoSupervisor());
		TextMensajeSupervisor.setText("");
	}

}
