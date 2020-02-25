/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ASUS
 */
public class EasyRide extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //GUI/IndexReclamationAdminFXML.fxml    GUI/AcceuilClientFXML.fxml GUI/AcceuilAdminFXML.fxml
        Parent root = FXMLLoader.load(getClass().getResource("GUI/Login.fxml"));
        Scene scene = new Scene(root);
        
      // AquaFx.style();
       // FlatterFX.style();
        //stage.setTitle("Reclamation");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
