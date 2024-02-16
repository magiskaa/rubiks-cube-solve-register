package fxrubkinkuutio;

import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import fi.jyu.mit.fxgui.*;
import javafx.application.Application;

/**
 * 
 * @author Valtteri
 * @version 18.1.2024
 *
 */
public class RubikinkuutioGUIController extends Application {
    @FXML private TextField Ratkaisu;
    
    @FXML private TextField Aika;
    
    @FXML private TextField Päivämäärä;
    
    @FXML private TextField Kellonaika;
    
    @FXML private void handleLopeta() {
        Platform.exit();
    }
    
    @FXML private void handleTallenna() {
        Dialogs.showMessageDialog("Ei vielä toimi");
    }
    
    @FXML private void handleLisaa() {
        ModalController.showModal(RubikinkuutioGUIController.class.getResource("RubikinkuutioLisaaView.fxml"), null,null,null);
    }

    @FXML private void handlePoista() {
        Dialogs.showMessageDialog("Ei vielä toimi");
    }
    
    @FXML private void handleApua() {
        Dialogs.showMessageDialog("Ei vielä toimi");
    }
    
    @FXML private void handleMuokkaa() {
        Dialogs.showMessageDialog("Ei vielä toimi");
    }
    
    @FXML private void handleTulosta() {
        Dialogs.showMessageDialog("Ei vielä toimi");
    }
    
    @Override
    public void start(Stage primaryStage) {
        try {
            AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("RubikinkuutioLisaaView.fxml"));
            Scene scene = new Scene(root,350,500);
            scene.getStylesheets().add(getClass().getResource("rubikinkuutio.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}