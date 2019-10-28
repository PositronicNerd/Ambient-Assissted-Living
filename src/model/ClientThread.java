package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClientThread extends Thread {

	private String correo;

	RecuperarDatos recuperar = new RecuperarDatos();

	public void setUp(String correo) {
		this.correo = correo;
	}

	public void run() {

		ArrayList<Pill> pills = recuperar.getPills(correo);
		System.out.println("Retrieved pills");
		Deque<Pill> deque = new LinkedList<Pill>();
		for (int i = 0; i < pills.size(); i++) {
			deque.add(pills.get(i));
		}
		System.out.println("Total pills: " + deque.size());
		if (deque.size() != 0) {
			while (true) {
				if (!deque.isEmpty())
					if (deque.peek().getFecha().before(new Date())) {
						Pill pill = deque.pop();
						recuperar.eliminarPill(pill);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Recordatorio de Pastilla");
						alert.setHeaderText("Le toca tomarse la pastilla " + pill.getPastilla());
						alert.setContentText(
								"Tiene que tomarsela " + pill.getDosis() + " de la pastilla " + pill.getPastilla());

						alert.showAndWait();
					}

			}
		}

	}
}
