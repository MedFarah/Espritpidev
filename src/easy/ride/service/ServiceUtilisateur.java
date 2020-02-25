package easy.ride.service;

import easy.ride.entities.Evenements;
import easy.ride.IService.IServiceUtilisateur;
import easy.ride.Utils.DataBase;
import easy.ride.entities.Utilisateur;
import java.sql.SQLException;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*; 

/**
 *
 * @author House
 */
public class ServiceUtilisateur implements IServiceUtilisateur<Utilisateur> {

    private Connection con;
    private Statement ste;
    public int id=0;
    public Utilisateur u;

    public ServiceUtilisateur() {
        con = DataBase.getInstance().getConnection();

    }

    public boolean testInscription(String s) throws SQLException {
        // TODO Auto-generated method stub
        boolean response;
        String requeteUpdate = "select * FROM utilisateur where login = ? ";
        PreparedStatement preparedStmt = con.prepareStatement(requeteUpdate);
        preparedStmt.setString(1, s);
        List<Utilisateur> arr = new ArrayList<>();
        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id_user");
            String login = rs.getString("login");

        }
        if (arr.size() != 0) {
            System.out.println("FALSE !     Utilisateur deja exsiste \n");
            response = false;
        } else {
            System.out.println("TRUE !    OK! \n" + arr);
            response = true;
        }
        return response;
    }

    @Override
    public void ajouter(Utilisateur t) throws SQLException {
        // TODO Auto-generated method stub

        boolean test;
        test = this.testInscription(t.getLogin());
        if (test == false) {
            System.out.println("Utilisateur déja Inscrit");
        } else {
            ste = con.createStatement();
            String requeteInsert = "INSERT INTO `utilisateur` (`role`,`login`, `password`, `nomComplet`, `mail`,`adresse`,`tel`,`dateNaissance`) VALUES ('Client', '" + t.getLogin() + "', '" + t.getPassword() + "', '" + t.getNomcomplet() + "','" + t.getMail() + "','" + t.getAdresse() + "','" + t.getTel() + "','" + t.getDateNaissance() + "' );";
            ste.executeUpdate(requeteInsert);
            System.out.println("utilisateur   ajouté  \n");
        }

    }

    @Override
    public boolean delete(Utilisateur t) throws SQLException {
        // TODO Auto-generated method stub
        String requeteDelete = "DELETE FROM utilisateur WHERE id_user = ?  ;";
        PreparedStatement preparedStmt = con.prepareStatement(requeteDelete);
        preparedStmt.setInt(1, t.getId_user());
        // execute the preparedstatement
        preparedStmt.execute();
        System.out.println(" Utilisateur supprimé  !  \n");
        return false;
    }

    @Override
    public boolean update(String password, int t) throws SQLException {
        // TODO Auto-generated method stub
        String requeteUpdate = "update utilisateur set password = ? WHERE id_user = ? ";
        PreparedStatement preparedStmt = con.prepareStatement(requeteUpdate);
        preparedStmt.setString(1, password);
        preparedStmt.setInt(2, t);
        preparedStmt.executeUpdate();
        System.out.println(" Informations Utilisateur a été modifiées  !  \n");
        return false;
    }

    @Override
    public Utilisateur connecter(String s, String p) throws SQLException {
        // TODO Auto-generated method stub
        String requeteUpdate = "select * FROM utilisateur where password = ? and login = ?";
        PreparedStatement preparedStmt = con.prepareStatement(requeteUpdate);
        preparedStmt.setString(1, p);
        preparedStmt.setString(2, s);
        List<Utilisateur> arr = new ArrayList<>();
        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id_user");
            String nomcomplet = rs.getString("nomComplet");
            String role = rs.getString("role");
            String login = rs.getString("login");
            String password = rs.getString("password");
            String mail = rs.getString("mail");
            String tel = rs.getString("tel");
            String date = rs.getString("dateNaissance");
            String adresse = rs.getString("adresse");
            Utilisateur u = new Utilisateur(id,login,role, password, nomcomplet, mail, tel, date, adresse);
            arr.add(u);
        }
        
        if (arr.size() == 0) {
            System.out.println("FALSE !     Non authentifié \n");
            return null;
        } else {
            System.out.println("TRUE !       Utilisateur Authentifié \n" + arr+"\n"+arr.stream().map(e->e.getId_user()).findFirst());
            Utilisateur user = arr.stream().findFirst().get();
           
            id=arr.get(0).getId_user();
            this.u = user;
            return user;
        }
    }

    @Override
    public void afficher() throws SQLException {
        System.out.println("Liste des utilisateurs");
        String requeteUpdate = "select * FROM utilisateur";
        PreparedStatement preparedStmt = con.prepareStatement(requeteUpdate);
        List<Utilisateur> arr = new ArrayList<>();
        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id_user");
            String nomcomplet = rs.getString("nomComplet");
            String login = rs.getString("login");
            String role = rs.getString("role");
            String password = rs.getString("password");
            String mail = rs.getString("mail");
            String tel = rs.getString("tel");
            String date = rs.getString("dateNaissance");
            String adresse = rs.getString("adresse");
            String dateCreation = rs.getString("dateCreation");
            Utilisateur u = new Utilisateur(login, role, password, nomcomplet, mail, tel, date, adresse);
            u.setId_user(id);
            u.setDateCreation(dateCreation);
            arr.add(u);
        }
        System.out.println(" \n  " + arr);

    }

    @Override
    public void trier() throws SQLException {
        System.out.println("liste triés des utilisateur par date de création profil");
        String requeteUpdate = "select * FROM utilisateur Order By dateCreation DESC";
        PreparedStatement preparedStmt = con.prepareStatement(requeteUpdate);
        List<Utilisateur> arr = new ArrayList<>();
        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id_user");
            String nomcomplet = rs.getString("nomComplet");
            String login = rs.getString("login");
            String role = rs.getString("role");
            String password = rs.getString("password");
            String mail = rs.getString("mail");
            String tel = rs.getString("tel");
            String date = rs.getString("dateNaissance");
            String adresse = rs.getString("adresse");
            String dateCreation = rs.getString("dateCreation");
            Utilisateur u = new Utilisateur(login, role, password, nomcomplet, mail, tel, date, adresse);
            u.setId_user(id);
            u.setDateCreation(dateCreation);
            arr.add(u);
        }
        System.out.println(" \n  " + arr);

    }

    @Override
    public List rechercher(String t, Utilisateur u1) throws SQLException {
        System.out.println("Rechercher un utilisateur par nom");
        String requeteUpdate = "select * FROM utilisateur WHERE nomcomplet = ? ";
        PreparedStatement preparedStmt = con.prepareStatement(requeteUpdate);
        preparedStmt.setString(1, t);
        List<Utilisateur> arr = new ArrayList<>();
        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id_user");
            String nomcomplet = rs.getString("nomComplet");
            String login = rs.getString("login");
            String role = rs.getString("role");
            String password = rs.getString("password");
            String mail = rs.getString("mail");
            String tel = rs.getString("tel");
            String date = rs.getString("dateNaissance");
            String adresse = rs.getString("adresse");
            String dateCreation = rs.getString("dateCreation");
            Utilisateur u = new Utilisateur(login, role, password, nomcomplet, mail, tel, date, adresse);
            u.setId_user(id);
            u.setDateCreation(dateCreation);
            arr.add(u);
        }
        if (arr.size() != 0) {
            System.out.println(" \n Utilisateur Trouvé  :   \n" + arr);
            return arr;
        } else {
            System.out.println(" \n Utilisateur Non Trouvé  :   \n" + arr);
            return null;
        }
    }

    @Override
    public void trierRole() throws SQLException {
        System.out.println("liste triés des utilisateur par date de création profil");
        String requeteUpdate = "select * FROM utilisateur Group BY role Order By dateCreation ASC";
        PreparedStatement preparedStmt = con.prepareStatement(requeteUpdate);
        List<Utilisateur> arr = new ArrayList<>();
        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id_user");
            String nomcomplet = rs.getString("nomComplet");
            String login = rs.getString("login");
            String role = rs.getString("role");
            String password = rs.getString("password");
            String mail = rs.getString("mail");
            String tel = rs.getString("tel");
            String date = rs.getString("dateNaissance");
            String adresse = rs.getString("adresse");
            String dateCreation = rs.getString("dateCreation");
            Utilisateur u = new Utilisateur(login, role, password, nomcomplet, mail, tel, date, adresse);
            u.setId_user(id);
            u.setDateCreation(dateCreation);
            arr.add(u);
        }
        System.out.println(" \n  " + arr);
    }

    
    public String Recuperer(int ids) throws SQLException{
        String ch="sss";
        String requeteUpdate = "select password FROM utilisateur where id_user = ?";
        PreparedStatement preparedStmt = con.prepareStatement(requeteUpdate);
         preparedStmt.setInt(1, ids);
        List<String> arr = new ArrayList<>();
        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next()) {
         
            String password = rs.getString("password");
        
      
            arr.add(password);
        }
       for(String elem: arr)
       {
       	ch = elem;   
       }
       System.out.println(ch);
        return ch;
    }
    
    
    @Override
    public void Mail(String to,String from) {
      String host = "smtp.gmail.com";//or IP address  
     //Get the session object  
      Properties properties = System.getProperties();  
      properties.setProperty("mail.smtp.host", host);  
      Session session = Session.getDefaultInstance(properties);  
          //compose the message  
      try{  
         MimeMessage message = new MimeMessage(session);  
         message.setFrom(new InternetAddress(from));  
         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
         message.setSubject("Récupération Mot de Passe");  
         message.setText("Bonjour, Votre mot de passe est      ");  
  
         // Send message  
         Transport.send(message);  
         System.out.println("message sent successfully....");  
  
      }catch (MessagingException mex) {mex.printStackTrace();}  
   }
    
    public void MailInscription(String to,String from) {
      String host = "smtp.gmail.com";//or IP address  
     //Get the session object  
      Properties properties = System.getProperties();  
      properties.setProperty("mail.smtp.host", host);  
      Session session = Session.getDefaultInstance(properties);  
          //compose the message  
      try{  
         MimeMessage message = new MimeMessage(session);  
         message.setFrom(new InternetAddress(from));  
         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
         message.setSubject("Félicitation ");  
         message.setText("Votre Incription est Terminée avec Succés      ");  
  
         // Send message  
         Transport.send(message);  
         System.out.println("message sent successfully....");  
  
      }catch (MessagingException mex) {mex.printStackTrace();}  
   }

}
