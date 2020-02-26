/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author achrefkh
 */
public class ServiceLogs {
    
    final String fileName = "logs.txt"; 
    
    public void writelogs(String req) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName,true);
        PrintWriter printWriter;
        printWriter = new PrintWriter(fileWriter);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        java.util.Date date = new java.util.Date();
        
        printWriter.printf(dateFormat.format(date)+" "+req);
        printWriter.printf("");
        printWriter.close();
        System.out.println("log saved");
    }
    
    public void send(String too){
        try{
            String host ="smtp.gmail.com" ;
            String user = "mohamedali.bouthlija@esprit.tn";
            String pass = "183JMT1738";
            String to = /*too*/"mohamedali.bouthlija@esprit.tn";
            String from = "mohamedali.bouthlija@esprit.tn";
            String messageText = "test";
            String subject = "Your Is Test Email :";
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject); msg.setSentDate(new Date());
            msg.setText(messageText);

           Transport transport=mailSession.getTransport("smtp");
           transport.connect(host, user, pass);
           transport.sendMessage(msg, msg.getAllRecipients());
           transport.close();
           System.out.println("message send successfully");
        }catch(Exception ex)
        {
            System.out.println(ex);
        }
    }
}
