package ne.cnss.immatriculation.createpdf;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import ne.cnss.immatriculation.model.Employeur;
import ne.cnss.immatriculation.model.Notification;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

public class NotificationPDF {
    public static File creerNotificationAffiliation(Notification notification, Employeur employeur) throws IOException {
        PdfFont font= PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        PdfFont italique= PdfFontFactory.createFont(FontConstants.TIMES_ITALIC);
        PdfFont bold= PdfFontFactory.createFont(FontConstants.TIMES_BOLD);

        File fichier=null;

        if (notification!=null) {
            LocalDate date= LocalDate.now(ZoneId.of("Africa/Niamey"));
            String nomFichier = "Notification " + notification.getId() + "_CNSS_DRC_AFF_"+date.getYear()+".pdf";
            PdfWriter pdfWriter = null;
            try {
                pdfWriter = new PdfWriter(nomFichier);
            } catch (FileNotFoundException e) {
                System.out.println("Exception fichier : " + e.getMessage());
                return null;
            }

            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.setDefaultPageSize(PageSize.A4);
            pdfDocument.addNewPage();

            Document document = new Document(pdfDocument);

            float width = PageSize.A4.getWidth();
            float[] colEnTete = {width / 2, width / 2};
            Table tabEnTet = new Table(colEnTete);
            tabEnTet.addCell(new Cell().setBorder(Border.NO_BORDER));
            tabEnTet.addCell(new Cell().setBorder(Border.NO_BORDER));

            Cell cell = tabEnTet.getCell(0, 0);
            cell.setTextAlignment(TextAlignment.CENTER);
            Paragraph entete= new Paragraph("REPUBLIQUE DU NIGER\n----------").setFontSize(11f).setTextAlignment(TextAlignment.CENTER).setFont(bold);
            cell.add(entete);


            //rajouter le logo de la CNSS sur le recepissé de la CNSS
            ImageData imageData = ImageDataFactory.create("images\\logo cnss.jpg");
            Image image= new Image(imageData);
            image.setHeight(50f).setWidth(50f).setMarginLeft(110f);
            cell.add(image);

            cell.add(new Paragraph("CAISSE NATIONALE DE\nSECURITE SOCIALE").setFontSize(10f).setTextAlignment(TextAlignment.CENTER).setFont(bold));

            cell.add(new LineSeparator(
                    new SolidLine())
                    .setWidthPercent(100));

            cell.add(new Paragraph("Direction du Recouvrement des Cotisations").setFontSize(10f).setTextAlignment(TextAlignment.CENTER).setFont(bold));

            cell.add(new LineSeparator(
                    new SolidLine())
                    .setWidthPercent(100));

            cell.add(new Paragraph("B.P 255 NIAMEY - TEL 20 73 43 47 - FAX 20 73 90 78\n" +
                    "E-mail : cnss@intnet.ne - Site Web : www.cnss.ne").setFontSize(9f).setTextAlignment(TextAlignment.CENTER).setFont(bold));

            cell.add(new LineSeparator(
                    new SolidLine())
                    .setWidthPercent(100));

            Cell cell1 = tabEnTet.getCell(0, 1);
            cell1.add(
                    new Paragraph("Niamey, "+date.getDayOfMonth()+"/"+date.getMonthValue()+"/"+date.getYear())
                            .setTextAlignment(TextAlignment.CENTER)
                            .setFont(bold)
            );

            String nomEmployeur="";
            if (employeur.getSigleSociete()!=null && !employeur.getSigleSociete().replaceAll(" ","").isEmpty())
                nomEmployeur+=employeur.getSigleSociete();
            else
                nomEmployeur+=employeur.getNom();

            cell1.add(
                    new Paragraph("LE DIRECTEUR GENERAL" +
                            "\nA" +
                            "\nMonsieur/Madame le(la) Responsable de" +
                            "\n"+nomEmployeur+
                            "\nN° Employeur : "+employeur.getNumeroEmployeur())
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBorder(new SolidBorder(0.5F))
                            .setMarginTop(20f)
                            .setFont(bold)
            );

            document.add(tabEnTet);

            //Ajouter le numéro e l'objet de la notification
            document.add(
                    new Paragraph().setFont(bold).setMarginTop(50f)
                            .add(new Text("N°:").setUnderline())
                            .add(new Text(" "+notification.getId()+"/CNSS/DRC/AFF/"+date.getYear()))
            );

            document.add(
                    new Paragraph().setFont(bold).setFontSize(12f)
                            .add(new Text("Objet:").setUnderline())
                            .add(new Text(" Notification du numéro d'affiliation"))
            );

            //Ajouter le corps;
            document.add(new Paragraph("Monsieur/Madame le(la) Responsable,").setFont(font));

            document.add(
                    new Paragraph().setFont(font)
                            .add(new Text("J'ai l'honneur de vous informer que vous êtes affilié(e) à la CNSS sous " +
                                    "le numéro : "))
                            .add(new Text(employeur.getNumeroEmployeur()).setBold())
                            .add(new Text(" pour compter du "))
                            .add(new Text(String.valueOf(employeur.getDateEmbauche())+".").setBold())
            );

            document.add(new Paragraph("Conformément à la réglementation du travail en matière de sécurité sociale en " +
                    "vigueur dans notre pays, le paiement des cotisations s'effectue de deux(02) manières : ").setFont(font));

            document.add(
                    new Paragraph().setMarginLeft(15f).setFont(font)
                            .add(new Text("1. mensuellement si vous utilisez "))
                            .add(new Text("20 salariés et plus").setBold().setUnderline())
                            .add(new Text("\n2. trimestriellement si vous utilisez "))
                            .add(new Text("moins de 20 salariés").setBold().setUnderline())
                            .add(new Text(" (Cf. instruction ci-jointe )"))
            );

            document.add(new Paragraph("" +
                    "Aussi préalablement aux dates de versement, la CNSS adressera des imprimés de déclarations de salariés " +
                    "et cotisations que vous voudriez bien retourner dûment remplis accompagnés du versement correspondant.").setFont(font));

            document.add(new Paragraph("Pour une bonne tenue de votre compte cotisant, je vous demande de rappeler " +
                    "votre numéro d'affiliation sur toutes vos correspondances à la CNSS.").setFont(font));

            document.add(new Paragraph("Restant à votre disposition pour tout renseignement complémentaire, je vous prie " +
                    "d'agréer Monsieur/Madame le(la) Responsable, l'expression de mes salutations distinguées.\n").setFont(font));

            Table pied= new Table(colEnTete);
            pied.setMarginTop(45f);
            pied.addCell(new Cell().setBorder(Border.NO_BORDER));
            pied.addCell(new Cell().setBorder(Border.NO_BORDER));

            Cell cell2 = pied.getCell(0, 0);
            cell2.add(
                    new Paragraph().setFont(font)
                            .add(new Text("NB: ").setBold())
                            .add(new Text("Cette notification ne peut en aucun cas servir de pièces pour la constitution " +
                                    "d'un dossier; des attestations sont prévues à cet effet").setFontSize(9f))
            );

            cell2.add(
                    new Paragraph().setFont(font)
                            .add(new Text("PJ: ").setBold())
                            .add(new Text("Instruction relative aux obligations de").setFontSize(9f))
            );

            Cell cell3 = pied.getCell(0, 1);
            cell3.add(new Paragraph("P.O LE DIRECTEUR GENERAL").setTextAlignment(TextAlignment.RIGHT).setFont(bold));

            document.add(pied);
            document.close();

            fichier= new File(nomFichier);
        }
        return fichier;
    }

}
