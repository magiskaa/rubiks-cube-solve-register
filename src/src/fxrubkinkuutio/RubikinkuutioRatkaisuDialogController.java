package fxrubkinkuutio;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 * @author Valtteri
 * @version 19.4.2024
 *
 */
public class RubikinkuutioRatkaisuDialogController implements ModalControllerInterface<Ratkaisu>,Initializable {

    @FXML private Label labelVirhe;
    @FXML private GridPane gridRatkaisu;
    
    @FXML private void handleOK() {
        if (ratkaisuKohdalla != null && ratkaisuKohdalla.getAika().trim().equals("")) {
            naytaVirhe("Aika ei saa olla tyhjä");
            return;
        }
        Ratkaisut.muutettu = true;
        ModalController.closeStage(labelVirhe);
    }
    
    @FXML private void handleCancel() {
        ratkaisuKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    
    private Ratkaisu ratkaisuKohdalla;
    private static Ratkaisu apuRatkaisu = new Ratkaisu();
    private TextField[] edits;
    private int kentta = 0;
    
    
    /**
     * @param gridRatkaisu ni
     * @return ni
     */
    public static TextField[] luoKentat(GridPane gridRatkaisu) {
        gridRatkaisu.getChildren().clear();
        TextField[] edits = new TextField[apuRatkaisu.getKenttia()];
        
        for (int i = 0, k = apuRatkaisu.ekaKentta(); k < apuRatkaisu.getKenttia(); k++, i++) {
            Label label = new Label(apuRatkaisu.getKysymys(k));
            gridRatkaisu.add(label, 0, i);
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridRatkaisu.add(edit, 1, i);
        }
        return edits;
    }
    
    
    /**
     * Tyhjentää tekstikentät
     * @param edits ni
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits) {
            if(edit != null) edit.setText("");
        }
    }
    
    
    /**
     * Palautetaan komponentin id:stä saatava luku
     * @param obj tutkittava komponentti
     * @param oletus mikä arvo jos id ei ole kunnollinen
     * @return komponentin id lukuna 
     */
    public static int getFieldId(Object obj, int oletus) {
        if (!(obj instanceof Node)) return oletus;
        Node node = (Node)obj;
        return Mjonot.erotaInt(node.getId().substring(1),oletus);
    }
    
    /**
     * 
     */
    protected void alusta() {
        edits = luoKentat(gridRatkaisu);
        for (TextField edit : edits) {
            if (edit != null) {
                edit.setOnKeyReleased(e -> kasitteleMuutosRatkaisuun((TextField)(e.getSource())));
            }
        }
    }
    
    
    private void naytaVirhe(String virhe) {
        if (virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
    
    /**
     * @param edit tekstikenttä
     */
    private void kasitteleMuutosRatkaisuun(TextField edit) {
        if (ratkaisuKohdalla == null) return;
        int k = getFieldId(edit, apuRatkaisu.ekaKentta());
        String s = edit.getText();
        String virhe = null;
        virhe = ratkaisuKohdalla.aseta(k, s);
        if (virhe == null) {
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit, virhe);
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        }
    }
    
    private void setKentta(int kentta) {
        this.kentta = kentta;
    }
    
    
    @Override
    public void setDefault(Ratkaisu oletus) {
        ratkaisuKohdalla = oletus;
        naytaRatkaisu(edits, ratkaisuKohdalla);
    }
    

    @Override
    public Ratkaisu getResult() {
        return ratkaisuKohdalla;
    }

    @Override
    public void handleShown() {
        kentta = Math.max(apuRatkaisu.ekaKentta(), Math.min(kentta, apuRatkaisu.getKenttia()-1));
        edits[kentta].requestFocus();        
    }

    /**
     * @param edits lista
     * @param ratkaisu ratkaisu
     */
    public static void naytaRatkaisu(TextField[] edits, Ratkaisu ratkaisu) {
        if (ratkaisu == null) return;    
        for (int k = ratkaisu.ekaKentta(); k < ratkaisu.getKenttia(); k++) {
            edits[k].setText(ratkaisu.anna(k));
        }
    }
    
    /**
     * @param modalityStage ni
     * @param oletus ni
     * @param kentta kentta
     * @return ni
     */
    public static Ratkaisu kysyRatkaisu(Stage modalityStage, Ratkaisu oletus, int kentta) {
        return ModalController.<Ratkaisu, RubikinkuutioRatkaisuDialogController>showModal(
                RubikinkuutioRatkaisuDialogController.class.getResource("RubikinkuutioMuokkaaView.fxml"), "Rekisteri",
                modalityStage, oletus, ctrl -> ctrl.setKentta(kentta)); 
    }

}
