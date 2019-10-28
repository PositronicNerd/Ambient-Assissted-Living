package PaginaPastillero;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import com.jfoenix.controls.JFXToggleButton;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Pill;
import model.RecuperarDatos;

public class ControladorPastillero {

	private ObservableList<Pill> firstdaylist = FXCollections.observableArrayList(),
			seconddaylist = FXCollections.observableArrayList(), thirddaylist = FXCollections.observableArrayList(),
			forthdaylist = FXCollections.observableArrayList(), fifthdaylist = FXCollections.observableArrayList(),
			sixthdaylist = FXCollections.observableArrayList(), seventhdaylist = FXCollections.observableArrayList();

	@FXML
	private ListView<Pill> listview1day = new ListView<Pill>();

	@FXML
	private ListView<Pill> listview2day = new ListView<Pill>();

	@FXML
	private ListView<Pill> listview3day = new ListView<Pill>();

	@FXML
	private ListView<Pill> listview4day = new ListView<Pill>();

	@FXML
	private ListView<Pill> listview5day = new ListView<Pill>();

	@FXML
	private ListView<Pill> listview6day = new ListView<Pill>();

	@FXML
	private ListView<Pill> listview7day = new ListView<Pill>();

	@FXML
	private Label label1day;

	@FXML
	private Label label2day;

	@FXML
	private Label label3day;

	@FXML
	private Label label4day;

	@FXML
	private Label label5day;

	@FXML
	private Label label6day;

	@FXML
	private Label label7day;

	@FXML
	private JFXToggleButton onoff;

	@FXML
	private Button buttonAdd;

	@FXML
	private Button buttonEliminate;

	@FXML
	private Button buttonClose;

	private String email;

	private boolean ison;

	RecuperarDatos recuperar = new RecuperarDatos();

	String[] languagedata;

	public void getEmail(String email, String[] languagedata) {
		System.out.println("Llegamos al controlador pastillero.");
		this.email = email;
		this.languagedata = languagedata;
		label1day.setText(languagedata[14]);
		label2day.setText(languagedata[15]);
		label3day.setText(languagedata[16]);
		label4day.setText(languagedata[17]);
		label5day.setText(languagedata[18]);
		label6day.setText(languagedata[19]);
		label7day.setText(languagedata[20]);
		addPills();
		onoff.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {

				if (onoff.isSelected()) {

					buttonAdd.setStyle(
							"-fx-background-color:  #448AFF; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, #1c1515 ,5, 0.05, 0, 0 );");
					buttonEliminate.setStyle(
							"-fx-background-color:  #448AFF; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, #1c1515 ,5, 0.05, 0, 0 );");
					buttonClose.setStyle(
							"-fx-background-color:  #448AFF; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, #1c1515 ,5, 0.05, 0, 0 );");
					ison = true;
					recuperar.turnPills(email, "1");

				} else {
					buttonAdd.setStyle("-fx-background-color:  #616161; -fx-background-radius: 10;");
					buttonEliminate.setStyle("-fx-background-color:  #616161; -fx-background-radius: 10;");
					recuperar.turnPills(email, "0");
					ison = false;
				}

			}

		});

	}

	public void addPills() {
		System.out.println("Pastillas:");
		ison = recuperar.getPillsState(email);
		onoff.setSelected(ison);
		if (!ison) {
			buttonAdd.setStyle("-fx-background-color:  #616161; -fx-background-radius: 10;");
			buttonEliminate.setStyle("-fx-background-color:  #616161; -fx-background-radius: 10;");
			buttonClose.setStyle("-fx-background-color:  #616161; -fx-background-radius: 10;");
		}
		ArrayList<Pill> pills = recuperar.getPills(email);
		for (int i = 0; i < pills.size(); i++) {
			if (pills.get(i).getDia().equals("1")) {
				firstdaylist.add(pills.get(i));
			} else if (pills.get(i).getDia().equals("2")) {
				seconddaylist.add(pills.get(i));
			} else if (pills.get(i).getDia().equals("3")) {
				thirddaylist.add(pills.get(i));
			} else if (pills.get(i).getDia().equals("4")) {
				forthdaylist.add(pills.get(i));
			} else if (pills.get(i).getDia().equals("5")) {
				fifthdaylist.add(pills.get(i));
			} else if (pills.get(i).getDia().equals("6")) {
				sixthdaylist.add(pills.get(i));
			} else {
				seventhdaylist.add(pills.get(i));
			}
		}

		listview1day.setItems(firstdaylist);
		listview2day.setItems(seconddaylist);
		listview3day.setItems(thirddaylist);
		listview4day.setItems(forthdaylist);
		listview5day.setItems(fifthdaylist);
		listview6day.setItems(sixthdaylist);
		listview7day.setItems(seventhdaylist);

		listview1day.setCellFactory(studentListView -> new StudentListViewCell());
		listview2day.setCellFactory(studentListView -> new StudentListViewCell());
		listview3day.setCellFactory(studentListView -> new StudentListViewCell());
		listview4day.setCellFactory(studentListView -> new StudentListViewCell());
		listview5day.setCellFactory(studentListView -> new StudentListViewCell());
		listview6day.setCellFactory(studentListView -> new StudentListViewCell());
		listview7day.setCellFactory(studentListView -> new StudentListViewCell());

	}

	public void addButton() {
		if (ison) {
			Stage popupstage = new Stage();
			popupstage.initModality(Modality.APPLICATION_MODAL);
			popupstage.setTitle("Nueva Pastilla");
			VBox sp = new VBox();
			sp.setId("root");
			HBox namebox = new HBox(), dosebox = new HBox(), timebox = new HBox(), daybox = new HBox();
			Label namelabel = new Label(), doselabel = new Label(), timelabel = new Label(), daylabel = new Label();
			TextField namefield = new TextField(), dosefield = new TextField();
			ChoiceBox hourchoice = new ChoiceBox(), minutechoice = new ChoiceBox(), daychoice = new ChoiceBox();
			Button savedata = new Button();
			ObservableList<String> hours = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06",
					"07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
					"23");
			ObservableList<String> minutes = FXCollections.observableArrayList("00", "05", "10", "15", "20", "25", "30",
					"35", "40", "45", "50", "55");
			ObservableList<String> days = FXCollections.observableArrayList(languagedata[14], languagedata[15],
					languagedata[16], languagedata[17], languagedata[18], languagedata[19], languagedata[20]);

			savedata.setText(languagedata[21]);
			hourchoice.setItems(hours);
			minutechoice.setItems(minutes);
			daychoice.setItems(days);

			namelabel.setText(languagedata[22]);
			namebox.getChildren().add(namelabel);
			namebox.getChildren().add(namefield);

			doselabel.setText(languagedata[23]);
			dosebox.getChildren().add(doselabel);
			dosebox.getChildren().add(dosefield);

			timebox.getChildren().add(hourchoice);
			timelabel.setText(":");
			timebox.getChildren().add(minutechoice);

			daylabel.setText(languagedata[24]);
			daybox.getChildren().add(daylabel);
			daybox.getChildren().add(daychoice);

			sp.getChildren().add(namebox);
			sp.getChildren().add(dosebox);
			sp.getChildren().add(timebox);
			sp.getChildren().add(daybox);
			sp.getChildren().add(savedata);

			Scene scene = new Scene(sp, 200, 200);
			scene.getStylesheets().add(getClass().getResource("/principal/style.css").toExternalForm());
			popupstage.setScene(scene);
			popupstage.show();

			savedata.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					Pill aux;
					String day;
					java.util.Date date = new java.util.Date();
					java.sql.Date sDate = convertUtilToSql(date);
					System.out.println("Fecha creada: "+sDate.toString());
					String finaldate = sDate.toString() + " " + hourchoice.getValue().toString() + ":"
							+ minutechoice.getValue().toString() + ":00";
					System.out.println("String de la pastilla: "+finaldate);
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date1 = null;
					try {
						date1 = df.parse(finaldate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Fecha de la pastilla: " + date1.toString());
					if (daychoice.getValue().toString().equals(languagedata[14])) {
						day = "1";
					} else if (daychoice.getValue().toString().equals(languagedata[15])) {
						day = "2";
					} else if (daychoice.getValue().toString().equals(languagedata[16])) {
						day = "3";
					} else if (daychoice.getValue().toString().equals(languagedata[17])) {
						day = "4";
					} else if (daychoice.getValue().toString().equals(languagedata[18])) {
						day = "5";
					} else if (daychoice.getValue().toString().equals(languagedata[19])) {
						day = "6";
					} else {
						day = "7";
					}

					aux = new Pill(0, email, date1, day, namefield.getText(), dosefield.getText());
					recuperar.createPill(aux);
					switch (aux.getDia()) {
					case "1":
						firstdaylist.add(aux);
						listview1day.setItems(firstdaylist);
						listview1day.setCellFactory(studentListView -> new StudentListViewCell());
						break;
					case "2":
						seconddaylist.add(aux);
						listview2day.setItems(seconddaylist);
						listview2day.setCellFactory(studentListView -> new StudentListViewCell());
						break;
					case "3":
						thirddaylist.add(aux);
						listview3day.setItems(thirddaylist);
						listview3day.setCellFactory(studentListView -> new StudentListViewCell());
						break;
					case "4":
						forthdaylist.add(aux);
						listview4day.setItems(forthdaylist);
						listview4day.setCellFactory(studentListView -> new StudentListViewCell());
						break;
					case "5":
						fifthdaylist.add(aux);
						listview5day.setItems(fifthdaylist);
						listview5day.setCellFactory(studentListView -> new StudentListViewCell());
						break;
					case "6":
						sixthdaylist.add(aux);
						listview6day.setItems(sixthdaylist);
						listview6day.setCellFactory(studentListView -> new StudentListViewCell());
						break;
					case "7":
						seventhdaylist.add(aux);
						listview7day.setItems(seventhdaylist);
						listview7day.setCellFactory(studentListView -> new StudentListViewCell());
						break;
					}
				}

			});
		}

	}

	private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
		java.sql.Date sDate = new java.sql.Date(uDate.getTime());
		return sDate;
	}

	public void botonSalir() {
		Stage stage = (Stage) buttonClose.getScene().getWindow();
		stage.close();
	}

}