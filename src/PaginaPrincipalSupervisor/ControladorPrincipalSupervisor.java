package PaginaPrincipalSupervisor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import PaginaPastillero.ControladorPastillero;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Paciente;
import model.RecuperarDatos;
import model.Sort;
import model.Supervisor;
import paginaChatUsuario.ControladorChatUsuario;
import paginaMonitorizacion.ControladorMonitorizacion;
import paginaSensorCaidas.ControladorSensorCaidas;

public class ControladorPrincipalSupervisor {

	@FXML
	private TextField labelmail;

	@FXML
	private TextField labelcontactphone;

	@FXML
	private TextField labellocation;

	@FXML
	private TextField labelage;

	@FXML
	private TextField labelprofile;

	@FXML
	private ListView<Label> listusers = new ListView<Label>();

	@FXML
	ListView<Label> popuplist = new ListView<Label>();

	@FXML
	private ImageView buttonadd;

	@FXML
	private ImageView buttondelete;

	@FXML
	private ImageView imagesleep;

	@FXML
	private ImageView imagelight;

	@FXML
	private ImageView imagefall;

	@FXML
	private ImageView imagepills;

	@FXML
	private ImageView imagechat;

	@FXML
	private ImageView imageleave;

	private String patientmail, relativemail;

	private Supervisor supervisor;

	private String[] languagedata;

	private boolean lights = true;

	RecuperarDatos recuperar = new RecuperarDatos();

	String IP = "10.34.87.131";
	int port = 9000;
	Socket socket = null;
	DataOutputStream out = null;

	// Modelo basico donde va a cargar labels de ejemplo.

	public void setEmail(Supervisor supervisor, String[] languagedata) {

		try {
			socket = new Socket(IP, port);
			out = new DataOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.languagedata = languagedata;
		this.supervisor = supervisor;
		listusers.setStyle("-fx-control-inner-background: #448AFF;");

		// Hay que ordenar la lista en orden ascendente or descendente.
		Sort sort = new Sort();
		ArrayList<Paciente> users = recuperar.getAssignedPatients(supervisor.getCorreo());
		mergeSort(users);
		for (int i = 0; i < users.size(); i++) {
			Label label = new Label();
			label.setText(users.get(i).getCorreo());
			label.setTextFill(Color.web("#FFFFFF"));
			listusers.getItems().add(label);
		}
	}

	public void getMailFromCell() {
		try {
			patientmail = listusers.getSelectionModel().getSelectedItem().getText();
			Paciente patientinfo = recuperar.getPatientData(patientmail);
			labelmail.setText(patientinfo.getCorreo());
			labelprofile.setText(patientinfo.getNombre());
			labelage.setText(patientinfo.getFechaNacimiento().toString());
			labellocation.setText(patientinfo.getDireccion());
			labelcontactphone.setText(patientinfo.getTelefono());
			relativemail = patientinfo.getCorreoFamiliar();
			if (patientinfo.getLuzActivado().equals("1")) {
				imagelight.setImage(new Image("/Multimedia/lighton.png"));
				lights = true;
				imagelight.setFitHeight(55);
				imagelight.setFitWidth(55);
			} else {
				imagelight.setImage(new Image("/Multimedia/lightoff.png"));
				lights = false;
				imagelight.setFitHeight(55);
				imagelight.setFitWidth(55);
			}
			System.out.println("Usuario seleccionado " + patientmail);
		} catch (NullPointerException e) {
		}
	}

	public void addButton() {
		popuplist = new ListView<Label>();
		popuplist.setStyle("-fx-control-inner-background: #448AFF;");
		Stage popupstage = new Stage();
		popupstage.initModality(Modality.APPLICATION_MODAL);
		StackPane sp = new StackPane();
		ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
		pacientes = recuperar.getUnsignedPatients();
		Label label;

		for (int i = 0; i < pacientes.size(); i++) {
			label = new Label();
			label.setTextFill(Color.web("#FFFFFF"));
			label.setText(pacientes.get(i).getCorreo());
			popuplist.getItems().add(label);
		}

		// popupstage.setTitle("Usuarios A elegir.");
		sp.getChildren().add(popuplist);
		Scene scene = new Scene(sp, 300, 200);
		popupstage.setScene(scene);
		popupstage.show();

		popuplist.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				recuperar.addUnsignedPatient(supervisor.getCorreo(), popuplist.getSelectionModel().getSelectedItem().getText());
				listusers.getItems().add(popuplist.getSelectionModel().getSelectedItem());
			}
		});
	}

	public void deleteButton() {
		try {
			System.out.println("Usuario seleccionado para eliminar.");
			recuperar.removeAssignedPatient(listusers.getSelectionModel().getSelectedItem().getText());
			listusers.getItems().remove(listusers.getSelectionModel().getSelectedIndex());
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Select user please");
		}
	}

	public void showSleep() {
		System.out.println("Ejecutando información personal paciente.");
		Stage stage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/paginaMonitorizacion/VentanaMonitorizacion.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			ControladorMonitorizacion contr = loader.getController();
			contr.getEmail(patientmail, languagedata);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(languagedata[2]);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void changeLight() {
		if (lights) {
			imagelight.setImage(new Image("/Multimedia/lightoff.png"));
			lights = false;
			imagelight.setFitHeight(55);
			imagelight.setFitWidth(55);
			recuperar.turnLight(patientmail, "0");
			sendServer("Lights OFF");
		} else {
			imagelight.setImage(new Image("/Multimedia/lighton.png"));
			lights = true;
			imagelight.setFitHeight(55);
			imagelight.setFitWidth(55);
			recuperar.turnLight(patientmail, "1");
			sendServer("Lights ON");
		}
	}

	public void fallingLog() {
		System.out.println("Log de caidas");
		Stage stage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/paginaSensorCaidas/VentanaCaidas.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			ControladorSensorCaidas contr = loader.getController();
			contr.getEmail(patientmail);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(languagedata[6]);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showPills() {
		System.out.println("Pastillero para: " + patientmail);
		Stage stage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/PaginaPastillero/Pastillero.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			ControladorPastillero contr = loader.getController();
			contr.getEmail(patientmail, languagedata);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(languagedata[4]);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showChats() {
		System.out.println("Chats con el paciente.");
		Stage stage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/paginaChatUsuario/VentanaChatUsuario.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			ControladorChatUsuario contr = loader.getController();
			contr.getEmail(patientmail, supervisor.getCorreo(), relativemail, languagedata[13], languagedata[11]);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle(languagedata[3]);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendServer(String message) {
		try {
			// URL whatismyip = new URL("http://checkip.amazonaws.com");
			// BufferedReader in = new BufferedReader(new
			// InputStreamReader(whatismyip.openStream()));
			System.out.println(message);
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

	public ArrayList<Paciente> mergeSort(ArrayList<Paciente> whole) {
		ArrayList<Paciente> left = new ArrayList<Paciente>();
		ArrayList<Paciente> right = new ArrayList<Paciente>();
		int center;

		if (whole.size() == 1) {
			return whole;
		} else {
			center = whole.size() / 2;
			// copy the left half of whole into the left.
			for (int i = 0; i < center; i++) {
				left.add(whole.get(i));
			}

			// copy the right half of whole into the new arraylist.
			for (int i = center; i < whole.size(); i++) {
				right.add(whole.get(i));
			}

			// Sort the left and right halves of the arraylist.
			left = mergeSort(left);
			right = mergeSort(right);

			// Merge the results back together.
			merge(left, right, whole);
		}
		return whole;
	}

	private void merge(ArrayList<Paciente> left, ArrayList<Paciente> right, ArrayList<Paciente> whole) {
		int leftIndex = 0;
		int rightIndex = 0;
		int wholeIndex = 0;

		// As long as neither the left nor the right ArrayList has
		// been used up, keep taking the smaller of left.get(leftIndex)
		// or right.get(rightIndex) and adding it at both.get(bothIndex).
		while (leftIndex < left.size() && rightIndex < right.size()) {
			if ((left.get(leftIndex).getCorreo().compareTo(right.get(rightIndex).getCorreo())) < 0) {
				whole.set(wholeIndex, left.get(leftIndex));
				leftIndex++;
			} else {
				whole.set(wholeIndex, right.get(rightIndex));
				rightIndex++;
			}
			wholeIndex++;
		}

		ArrayList<Paciente> rest;
		int restIndex;
		if (leftIndex >= left.size()) {
			// The left ArrayList has been use up...
			rest = right;
			restIndex = rightIndex;
		} else {
			// The right ArrayList has been used up...
			rest = left;
			restIndex = leftIndex;
		}

		// Copy the rest of whichever ArrayList (left or right) was not used up.
		for (int i = restIndex; i < rest.size(); i++) {
			whole.set(wholeIndex, rest.get(i));
			wholeIndex++;
		}
	}

	public void botonSalir() {
		Stage stage = (Stage) imageleave.getScene().getWindow();
		stage.close();
	}

}