package paginaMonitorizacion;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfoenix.controls.JFXToggleButton;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.ManejadorDeFichero;
import model.Puntos;
import model.RecuperarDatos;

public class ControladorMonitorizacion {

	@FXML
	private TextField datechoosertext;

	@FXML
	private TextField datechoosertext2;

	@FXML
	private CategoryAxis pressiontime;

	@FXML
	private NumberAxis pressionboolean;

	@FXML
	private CategoryAxis accelerometertime;

	@FXML
	private NumberAxis accelerometervalue;

	@FXML
	private LineChart<?, ?> pressionlinechart;

	@FXML
	private LineChart<?, ?> accelerometerlinechart;

	@FXML
	private JFXToggleButton onoff;

	@FXML
	private Button buttonSearch;

	private boolean ison;

	@FXML
	private Button exitbutton;

	ArrayList<String> date = new ArrayList<String>();
	ArrayList<Integer> acceleration = new ArrayList<Integer>();
	ArrayList<Integer> sleep = new ArrayList<Integer>();
	ArrayList<Puntos> acelerometro = new ArrayList<Puntos>();
	ArrayList<Puntos> presion = new ArrayList<Puntos>();
	RecuperarDatos recuperar = new RecuperarDatos();
	XYChart.Series puntosacel = new XYChart.Series<>();
	XYChart.Series puntospres = new XYChart.Series<>();
	String IP = "10.34.87.131";
	int port = 9000;
	Socket socket = null;
	DataOutputStream out = null;
	String mail;
	public void getEmail(String mail, String[] languagedata) {
		this.mail=mail;
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
		System.out.println("Mail:" + mail);
		ison = recuperar.getGraphState(mail);
		onoff.setSelected(ison);
		if (!ison) {
			buttonSearch.setStyle("-fx-background-color:  #616161; -fx-background-radius: 10;");
			datechoosertext.setStyle("-fx-background-color: #616161; \r\n" + "	-fx-background-radius: 10; \r\n");
			datechoosertext.setEditable(false);
			datechoosertext2.setStyle("-fx-background-color: #616161; \r\n" + "	-fx-background-radius: 10; \r\n");
			datechoosertext2.setEditable(false);
		}
		onoff.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if (onoff.isSelected()) {
					buttonSearch.setStyle(
							"-fx-background-color:  #448AFF; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, #1c1515 ,5, 0.05, 0, 0 );");
					datechoosertext.setStyle("-fx-background-color: #448AFF; \r\n" + "	-fx-background-radius: 10; \r\n"
							+ "	-fx-text-inner-color: #FFFFFF;\r\n"
							+ "	-fx-effect: innerShadow( gaussian , #1c1515b2 ,  10, 0.3, 0, 0 );");
					datechoosertext.setEditable(true);
					datechoosertext2.setStyle("-fx-background-color: #448AFF; \r\n" + "	-fx-background-radius: 10; \r\n"
							+ "	-fx-text-inner-color: #FFFFFF;\r\n"
							+ "	-fx-effect: innerShadow( gaussian , #1c1515b2 ,  10, 0.3, 0, 0 );");
					datechoosertext2.setEditable(true);
					ison = true;
					recuperar.turnGraph(mail, "1");
					sendServer("Graph on..");
				} else {
					buttonSearch.setStyle("-fx-background-color:  #616161; -fx-background-radius: 10;");
					datechoosertext
							.setStyle("-fx-background-color: #616161; \r\n" + "	-fx-background-radius: 10; \r\n");
					datechoosertext.setEditable(false);
					datechoosertext2
							.setStyle("-fx-background-color: #616161; \r\n" + "	-fx-background-radius: 10; \r\n");
					datechoosertext2.setEditable(false);
					recuperar.turnGraph(mail, "0");
					sendServer("Graph off");
					ison = false;
				}

			}

		});

		pressionlinechart.setCreateSymbols(false);
		accelerometerlinechart.setCreateSymbols(false);
		pressionboolean.setLabel(languagedata[25]);
		pressiontime.setLabel(languagedata[27]);
		accelerometervalue.setLabel(languagedata[26]);
		accelerometertime.setLabel(languagedata[27]);

		if (ison) {
			acelerometro = recuperar.getAccelerationSleepData(mail);
			presion = recuperar.getPresureSleepData(mail);
			for (int i = 0; i < acelerometro.size() && i < presion.size(); i++) {
				puntosacel.getData()
						.add(new XYChart.Data(acelerometro.get(i).getHora(), acelerometro.get(i).getValor()));
				puntospres.getData().add(new XYChart.Data(presion.get(i).getHora(), presion.get(i).getValor()));
			}
			accelerometerlinechart.getData().addAll(puntosacel);
			pressionlinechart.getData().addAll(puntospres);
		}

		ImageView send = new ImageView("/Multimedia/log-out.png");
		send.setFitHeight(40);
		send.setFitWidth(40);
		exitbutton.setGraphic(send);
	}

	public String getUsername(String mail) {
		String[] name = mail.split("@");
		System.out.println("Seleccionado el usuario " + name[0]);
		return name[0];
	}

	public void getDataInterval() {
		System.out.println("Get new data.");
		accelerometerlinechart.getData().removeAll(acelerometro);
        pressionlinechart.getData().removeAll(presion);
		acelerometro = new ArrayList<Puntos>();
		presion = new ArrayList<Puntos>();
		String date1 = datechoosertext.getText()+" 00:00:00";
		String date2 = datechoosertext2.getText()+" 00:00:00";
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date parse1=null;
        Date parse2=null;
		try {
			parse1 = df.parse(date1);
			parse2 =df.parse(date2);
		} catch (ParseException e) {
		System.out.println("Error parsing dates in getting new data");
			e.printStackTrace();
		}
        
        Timestamp t1=new Timestamp(parse1.getTime()); 
        Timestamp t2=new Timestamp(parse2.getTime()); 
        
        presion = recuperar.getPresureSleepDataInterval(mail,t1,t2);
        acelerometro = recuperar.getAccelerationSleepDataInterval(mail, t1, t2);
        puntosacel = new XYChart.Series<>();
    	puntospres = new XYChart.Series<>();
        for (int i = 0; i < acelerometro.size() && i < presion.size(); i++) {
			puntosacel.getData()
					.add(new XYChart.Data(acelerometro.get(i).getHora(), acelerometro.get(i).getValor()));
			puntospres.getData().add(new XYChart.Data(presion.get(i).getHora(), presion.get(i).getValor()));
		}
        
		accelerometerlinechart.getData().addAll(puntosacel);
		pressionlinechart.getData().addAll(puntospres);
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

	public void sendServer(String message) {
		try {
			// URL whatismyip = new URL("http://checkip.amazonaws.com");
			// BufferedReader in = new BufferedReader(new
			// InputStreamReader(whatismyip.openStream()));

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