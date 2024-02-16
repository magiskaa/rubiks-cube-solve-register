package fxrubkinkuutio;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * 
 * @author Valtteri
 * @version 25.1.2024
 *
 */
public class RubikinkuutioMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("RubikinkuutioAlkuView.fxml"));
			Scene scene = new Scene(root,450,300);
			scene.getStylesheets().add(getClass().getResource("rubikinkuutio.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param args Ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
