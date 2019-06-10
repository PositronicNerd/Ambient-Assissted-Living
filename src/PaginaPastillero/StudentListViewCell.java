package PaginaPastillero;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Pill;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by Johannes on 23.05.16.
 *
 */

public class StudentListViewCell extends ListCell<Pill> {

	@FXML
	private Label label1;

	@FXML
	private Label label2;

	@FXML
	private Label label3;

	@FXML
	private BorderPane pillpane;

	private FXMLLoader mLLoader;

	@Override
	protected void updateItem(Pill pill, boolean empty) {
		super.updateItem(pill, empty);
		if (empty || pill == null) {

			setText(null);
			setGraphic(null);

		} else {
			if (mLLoader == null) {
				mLLoader = new FXMLLoader(getClass().getResource("/PaginaPastillero/ListCell.fxml"));
				mLLoader.setController(this);

				try {
					mLLoader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			String[] time = pill.getFecha().toString().split(" "); // Only shows the hour and minute.
			label1.setText(pill.getPastilla());
			label2.setText(pill.getDosis());
			label3.setText(time[1].replace(".0", ""));

			setGraphic(pillpane);
		}

	}

}
