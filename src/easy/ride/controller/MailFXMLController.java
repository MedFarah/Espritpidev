/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.controller;

import easy.ride.entities.Reclamation;
import easy.ride.service.Mailing;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class MailFXMLController implements Initializable {

    @FXML
    private TextArea messagetxt;
    @FXML
    private TextField mailtxt;
    @FXML
    private TextField objettxt;
    @FXML
    private Button envoyerbtn;
    Reclamation reclamation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Envoyer(ActionEvent event) {
        Mailing.send(mailtxt.getText().toString(),objettxt.getText() ,messagetxt.getText(), "", "");
        Notifications n = Notifications.create()
                                            .title("Success")
                                            .text("Operation effectue")
                                            .graphic(null)
                                            .position(Pos.CENTER)
                                            .hideAfter(Duration.seconds(5));
                                    n.showConfirm();
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
        mailtxt.setText(reclamation.getEmail());
        mailtxt.setDisable(true);
    }
    
    
}
