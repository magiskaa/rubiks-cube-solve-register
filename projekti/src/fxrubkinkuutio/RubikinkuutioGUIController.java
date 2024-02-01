package fxrubkinkuutio;

import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import fi.jyu.mit.fxgui.*;

/**
 * 
 * @author Valtteri
 * @version 18.1.2024
 *
 */
public class RubikinkuutioGUIController {
    @FXML private TextField Ratkaisu;
    
    @FXML private TextField Aika;
    
    @FXML private TextField Päivämäärä;
    
    @FXML private TextField Kellonaika;
    
    @FXML private void handleLopeta() {
        Platform.exit();
    }
    

    private static void launch(String[] args) {
        // TODO Auto-generated method stub
        
    }
}