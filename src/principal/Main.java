package principal;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Parent paginaPrincipalUsuario = FXMLLoader.load(getClass().getResource("/paginaLogin/PaginaLogin.fxml"));
			Scene scene2 = new Scene(paginaPrincipalUsuario);
			primaryStage.setTitle("Welcome page");
			primaryStage.setScene(scene2);
			primaryStage.show();
		} catch(Exception e) {e.printStackTrace();}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}