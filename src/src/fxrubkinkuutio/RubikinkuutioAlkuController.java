package fxrubkinkuutio;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;

/**
 * @author Valtteri
 * @version 16.2.2024
 *
 */
public class RubikinkuutioAlkuController implements ModalControllerInterface<String> {
    
    @FXML private void handleAloita() {
        ModalController.closeStage(null);
    }
    
    @FXML private void handleLopeta() {
        ModalController.closeStage(null);
        Platform.exit();
    }

    @Override
    public String getResult() {
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(String arg0) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @param modalityStage stage
     */
    public static void naytaAlku(Stage modalityStage) {
        ModalController.showModal(RubikinkuutioAlkuController.class.getResource("RubikinkuutioAlkuView.fxml"), "Rekisteri", modalityStage, null);
    }
    
}
