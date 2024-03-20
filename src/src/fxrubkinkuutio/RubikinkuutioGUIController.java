package fxrubkinkuutio;

import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import fi.jyu.mit.fxgui.*;

import javafx.application.Application;
import javafx.application.Platform;

/**
 * 
 * @author Valtteri
 * @version 18.1.2024
 *
 */
public class RubikinkuutioGUIController implements Initializable {
    
    @FXML private TextField Ratkaisu;
    @FXML private TextField Aika;
    @FXML private TextField Päivämäärä;
    @FXML private TextField Kellonaika;
    @FXML private ListChooser<Ratkaisu> chooserRatkaisut;
    @FXML private ScrollPane panelRatkaisu;
    
    /**
     * @param url ni
     * @param bundle ni
     */
    @Override
    public void initialize(URL url, ResourceBundle bundle) {    
        alusta();      
    }

    
    @FXML private void handleLopeta() {
        Platform.exit();
    }
    
    @FXML private void handleTallenna() {
        Dialogs.showMessageDialog("Ei vielä toimi");
    }
    
    @FXML private void handleLisaa() {
        //ModalController.showModal(RubikinkuutioGUIController.class.getResource("RubikinkuutioLisaaView.fxml"), null,null,null);
        lisaaRatkaisu();
    }
    
    @FXML private void handleLisaaSekoitus() {
        //ModalController.showModal(RubikinkuutioGUIController.class.getResource("RubikinkuutioLisaaSekoitusView.fxml"), null,null,null);
        lisaaSekoitus();
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
    
    
    //@Override
    //public void start(Stage primaryStage) {
    //    try {
    //        AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("RubikinkuutioLisaaView.fxml"));
    //        Scene scene = new Scene(root,350,500);
    //        scene.getStylesheets().add(getClass().getResource("rubikinkuutio.css").toExternalForm());
    //        primaryStage.setScene(scene);
    //        primaryStage.show();
    //    } catch(Exception e) {
    //        e.printStackTrace();
    //    }
    //}
    
    
    //========= EI KÄYTTÖLIITTYMÄÄN LIITTYVÄÄ KOODIA =========
    
    private Rekisteri rekisteri;
    private Ratkaisu ratkaisuKohdalla;
    private TextArea areaRatkaisu = new TextArea();
    
    
    /**
     * 
     */
    protected void alusta() {
        panelRatkaisu.setContent(areaRatkaisu);
        areaRatkaisu.setFont(new Font("Courier New", 12));
        panelRatkaisu.setFitToHeight(true);
        
        chooserRatkaisut.clear();
        chooserRatkaisut.addSelectionListener(e -> naytaRatkaisu());
    }
    
    
    /**
     * 
     */
    protected void naytaRatkaisu() {
        ratkaisuKohdalla = chooserRatkaisut.getSelectedObject();
        
        if (ratkaisuKohdalla == null) return;
        
        areaRatkaisu.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaRatkaisu)) {
            tulosta(os,ratkaisuKohdalla);
        }
        
    }
    
    
    /**
     * @param id id
     */
    protected void hae(int id) {
        chooserRatkaisut.clear();
        
        int index = 0;
        for (int i = 0; i < rekisteri.getRatkaisuja(); i++) {
            Ratkaisu ratkaisu = rekisteri.annaRatkaisu(i);
            if (ratkaisu.getId() == id) index = i;
            chooserRatkaisut.add(ratkaisu.getAika(), ratkaisu);
        }
        chooserRatkaisut.setSelectedIndex(index);
    }
    
    /**
     * 
     */
    protected void lisaaRatkaisu() {
        Ratkaisu uusi = new Ratkaisu();
        uusi.rekisteroi();
        uusi.testiArvot();
        try {
            rekisteri.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden lisäämisessä " + e.getMessage());
            return;
        }
        hae(uusi.getId());
    }
    
    /**
     * 
     */
    public void lisaaSekoitus() {
        if (ratkaisuKohdalla == null) return;
        Sekoitus sek = new Sekoitus();
        sek.rekisteroi();
        sek.testiArvot(ratkaisuKohdalla.getId());
        rekisteri.lisaa(sek);
        hae(ratkaisuKohdalla.getId());
    }
    
    /**
     * @param os mikä virta
     * @param ratkaisu mikä ratkaisu
     */
    public void tulosta(PrintStream os, final Ratkaisu ratkaisu) {
        os.println("-------------------------------------------");
        ratkaisu.tulosta(os);
        os.println("-------------------------------------------");
        List<Sekoitus> sekoitukset = rekisteri.annaSekoitukset(ratkaisu);
        for (Sekoitus sek : sekoitukset)
            sek.tulosta(os);
    }
    
    
    /**
     * @param rekisteri ni
     */
    public void setRekisteri(Rekisteri rekisteri) {
        this.rekisteri = rekisteri;
        naytaRatkaisu();
    } 
    
    
    
}