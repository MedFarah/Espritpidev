/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.GUI;
import easy.ride.Utils.DataBase;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.html.WebColors;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.easyride.Entite.Evenements;
import easy.ride.service.Serviceevenements;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.scene.control.Alert;

/**
 *
 * @author AMINE
 */
public class PDFutil {

    Connection cn2;
    Statement ste;
    Serviceevenements service = new Serviceevenements();

    public PDFutil() {
        cn2 = DataBase.getInstance().getConnection();
    }

    Document doc = new Document();

    public void listevenements() throws SQLException, FileNotFoundException, DocumentException, IOException {
        Document document = new Document();
        try {

            PdfWriter.getInstance(document, new FileOutputStream("doc.pdf"));
            document.open();
             Paragraph ph1 = new Paragraph("Bienvenue au service Evenements!");
            //  Paragraph ph2 = new Paragraph(service.readAll().toString());
            PdfPTable table = new PdfPTable(5);
table.setWidthPercentage(100);
        PdfPCell cell;
        
            // `descreptioneve``lieuxeve``dateeve``nombre``nom_evenements`
            //contenu du tableau.
            /*
            table.addCell("nom_evenements");
            table.addCell("date evenements");
            table.addCell("nombre participants");
            table.addCell("lieux evenements");
            table.addCell("descreption evenements");
            */
             cell = new PdfPCell(new Phrase("nom_evenements",FontFactory.getFont("Comic Sans MS",12)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);    
        cell.setBackgroundColor(WebColors.getRGBColor("#f6beb9"));
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("date evenements",FontFactory.getFont("Comic Sans MS",12)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(WebColors.getRGBColor("#f6beb9"));
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("nombre participants",FontFactory.getFont("Comic Sans MS",12)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(WebColors.getRGBColor("#f6beb9"));
        table.addCell(cell);
        
        
        cell = new PdfPCell(new Phrase("lieux evenements",FontFactory.getFont("Comic Sans MS",12)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(WebColors.getRGBColor("#f6beb9"));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("descreption evenements",FontFactory.getFont("Comic Sans MS",12)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(WebColors.getRGBColor("#f6beb9"));
        table.addCell(cell);
            
            
            Serviceevenements se = new Serviceevenements();
            for (Evenements ev : se.readAll()) {
                table.addCell(ev.getNom_evenements());
                table.addCell(Integer.toString(ev.getNombre()));
                table.addCell(ev.getDateeve());
                table.addCell(ev.getLieuxeve());
                table.addCell(ev.getDescreptioneve());

            }
            document.add(ph1);
            document.add(table);
            // document.add(ph2);
            document.addAuthor("Easyride");
                Alert alert=new Alert(Alert.AlertType.WARNING);
         alert.setTitle("creation page pd");
         alert.setHeaderText(null);
         alert.setContentText("creation du fichier pdf avec succées"); 
// Alert.AlertType.WARNING.name.("Creation PDF ", "Votre fichier PDF a ete cree avec success");
        } catch (Exception e) {
            System.out.println(e);
        }
        document.close();
    }
}
