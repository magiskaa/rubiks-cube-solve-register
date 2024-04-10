package fxrubkinkuutio;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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
		    final FXMLLoader ldr = new FXMLLoader(getClass().getResource("RubikinkuutioGUIView.fxml"));
		    final Pane root = (Pane)ldr.load();
		    final RubikinkuutioGUIController rekisteriCtrl = (RubikinkuutioGUIController)ldr.getController();
		    
		    final Scene scene = new Scene(root);
		    scene.getStylesheets().add(getClass().getResource("rubikinkuutio.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Rekisteri");
			
			Rekisteri rekisteri = new Rekisteri();
			rekisteriCtrl.setRekisteri(rekisteri);
			primaryStage.show();
			
			rekisteriCtrl.lueTiedosto("data");
			
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
