/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.GUI;

import easy.ride.entities.Site;
import easy.ride.service.ServiceSite;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
//import com.lynden.gmapsfx.javascript.object.MapType;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Dali
 */
public class MapController implements Initializable, MapComponentInitializedListener {

     @FXML
    private GoogleMapView mapView;
    
    private GoogleMap map;
    
    ServiceSite st = new ServiceSite();
    @FXML
    private Label sitedisp;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         mapView.addMapInializedListener(this);
         
         List<Site> arr=new ArrayList<>();
         try {
             arr = st.readAll();
         } catch (SQLException ex) {
             Logger.getLogger(MapController.class.getName()).log(Level.SEVERE, null, ex);
         }
        List<String> listemplacement=new ArrayList<>();
        for(int i=0; i<arr.size(); i++){
            listemplacement.add(arr.get(i).getEmplacement());
        }
        Set<String> setemplacement = new HashSet<>();
        for(int i=0; i<listemplacement.size(); i++){
            setemplacement.add(listemplacement.get(i).substring(0,listemplacement.get(i).lastIndexOf(" ")));
        }
        List<String> list = new ArrayList<String>(setemplacement);
        for(int i=0; i<list.size(); i++){
            sitedisp.setText(sitedisp.getText()+" "+list.get(i));
        }
         
    }    
    
    @Override
    public void mapInitialized() {
        
        List<Site> arr=new ArrayList<>();
         try {
             arr = st.readAll();
         } catch (SQLException ex) {
             Logger.getLogger(MapController.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        
        /*LatLong joshAndersonLocation = new LatLong(36.808367, 10.183899);
        LatLong bobUnderwoodLocation = new LatLong(36.808367, 10.183899);
        LatLong tomChoiceLocation = new LatLong(36.808367, 10.183899);
        LatLong fredWilkieLocation = new LatLong(36.808367, 10.183899);*/
        
        
        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();
        
        mapOptions.center(new LatLong(arr.get(0).getX(), arr.get(0).getY()))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(15);
                   
        map = mapView.createMap(mapOptions);

        //Add markers to the map
        /*MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(joeSmithLocation).label("test");
        
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(joshAndersonLocation);
        
        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions3.position(bobUnderwoodLocation);
        
        MarkerOptions markerOptions4 = new MarkerOptions();
        markerOptions4.position(tomChoiceLocation);
        
        MarkerOptions markerOptions5 = new MarkerOptions();
        markerOptions5.position(fredWilkieLocation);*/
        
        LatLong SiteLocation; 
        MarkerOptions markerOptions1 = new MarkerOptions();
        Marker SiteMarker;
        for(int i=0; i<arr.size(); i++){
            SiteLocation = new LatLong(arr.get(i).getX(), arr.get(i).getY());
            markerOptions1.position(SiteLocation).label(arr.get(i).getEmplacement());
            SiteMarker = new Marker(markerOptions1);
            map.addMarker( SiteMarker );
        }
        
         
        /*Marker joshAndersonMarker = new Marker(markerOptions2);
        Marker bobUnderwoodMarker = new Marker(markerOptions3);
        Marker tomChoiceMarker= new Marker(markerOptions4);
        Marker fredWilkieMarker = new Marker(markerOptions5);*/
        
        
        /*map.addMarker( joshAndersonMarker );
        map.addMarker( bobUnderwoodMarker );
        map.addMarker( tomChoiceMarker );
        map.addMarker( fredWilkieMarker );*/
        
        /*InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content("<h2>Fred Wilkie</h2>"
                                + "Current Location: Safeway<br>"
                                + "ETA: 45 minutes" );

        InfoWindow fredWilkeInfoWindow = new InfoWindow(infoWindowOptions);
        fredWilkeInfoWindow.open(map, fredWilkieMarker);*/
    }   

    @FXML
    private void Retour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Userchoix.fxml"));
        Parent root = loader.load();
        mapView.getScene().setRoot(root);
    }
}
