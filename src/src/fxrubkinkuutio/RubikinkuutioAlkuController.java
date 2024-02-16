package fxrubkinkuutio;

import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
import fi.jyu.mit.fxgui.*;
import javafx.application.Application;

/**
 * @author Valtteri
 * @version 16.2.2024
 *
 */
public class RubikinkuutioAlkuController extends Application {
    
    @FXML private void handleAloita() {
        Stage stage = new Stage();
        start(stage);
    }
    
    @FXML private void handleLopeta() {
        Platform.exit();
    }
    
    
    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("RubikinkuutioGUIView.fxml"));
            Scene scene = new Scene(root,800,480);
            scene.getStylesheets().add(getClass().getResource("rubikinkuutio.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}
