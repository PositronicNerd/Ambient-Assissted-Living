package paginaSensorCaidas;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXToggleButton;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.RecuperarDatos;

public class ControladorSensorCaidas {

	@FXML
	private GridPane root;

	@FXML
	private TextArea textmessage;

	@FXML
	private ImageView exitbutton;

	@FXML
	private JFXToggleButton onoff;

	private boolean ison = false;
	
	String email;

	RecuperarDatos recuperar = new RecuperarDatos();
	String IP = "10.34.84.187";
	int port = 9000;
	Socket socket = null; 
	DataOutputStream out=null;			
	ArrayList<String> falls = new ArrayList<String>();
	public void getEmail(String email) {
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
		this.email = email;
		ison = recuperar.getFallState(email);
		onoff.setSelected(ison);
		if (ison) {
			System.out.println("Encendido");
			fillFalls();
			} else {
			textmessage
					.setStyle("-fx-control-inner-background: #616161; \r\n" + "		-fx-background-radius: 10; \r\n");
			System.out.println("Apagado");
		}
		onoff.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if (onoff.isSelected()) {
					textmessage.setStyle("	-fx-control-inner-background: #448AFF; \r\n"
							+ "	-fx-background-radius: 10; \r\n" + "	-fx-text-fill: white;\r\n"
							+ "	-fx-effect: innerShadow( gaussian , #1c1515b2 ,  10, 0.3, 0, 0 );");
					System.out.println("Encendido.");
					recuperar.turnFall(email, "1");
					sendServer("Fall on...");
					ison = true;
					fillFalls();
				} else {
					System.out.println("Apagado...");
					textmessage.setStyle(
							"-fx-control-inner-background: #616161; \r\n" + "		-fx-background-radius: 10; \r\n");
					recuperar.turnFall(email, "0");
					sendServer("Fall off..");
					ison = false;
					textmessage.setText("");
				}

			}

		});
	}

	public void botonSalir() {
		Stage stage = (Stage) exitbutton.getScene().getWindow();
		try {
			out.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stage.close();
	}

	public void fillFalls() {
		falls = recuperar.getFalls(email);
		for (int i=0;i<falls.size();i++) {
			textmessage.setText(textmessage.getText()+"Sensors detected a fall on "+falls.get(i)+"\n");
		}
		
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
