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
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import ne.cnss.immatriculation.ImmatriculationApplication;
import ne.cnss.immatriculation.model.Demande;
import ne.cnss.immatriculation.model.Fichier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class DemandePDF {

    public static File creerPdfRecepisseDemande(Demande demande, List<Fichier> fichierList) throws IOException {
        PdfFont font= PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        PdfFont italique= PdfFontFactory.createFont(FontConstants.TIMES_ITALIC);
        PdfFont bold= PdfFontFactory.createFont(FontConstants.TIMES_BOLD);

        File fichier=null;

        if (demande!=null){
            String nomFichier= "demande N° "+demande.getId()+".pdf";
            PdfWriter pdfWriter= null;
            try {
                pdfWriter= new PdfWriter(nomFichier);
            }catch (FileNotFoundException e){
                System.out.println("Exception fichier : "+e.getMessage());
                return null;
            }

            PdfDocument pdfDocument= new PdfDocument(pdfWriter);
            pdfDocument.setDefaultPageSize(PageSize.A4);
            pdfDocument.addNewPage();

            Document document= new Document(pdfDocument);

            float width = PageSize.A4.getWidth();
            float[] colEnTete = {width / 2, width / 2};
            Table tabEnTet = new Table(colEnTete);
            tabEnTet.addCell(new Cell().setBorder(Border.NO_BORDER));
            tabEnTet.addCell(new Cell().setBorder(Border.NO_BORDER));

            Cell cell = tabEnTet.getCell(0, 0);
            cell.setTextAlignment(TextAlignment.CENTER);
            Paragraph entete= new Paragraph("REPUBLIQUE DU NIGER\n----------").setFontSize(11f).setTextAlignment(TextAlignment.CENTER).setFont(bold);
            cell.add(entete);


            ImageData imageData = ImageDataFactory.create("images\\logo cnss.jpg");
            Image image= new Image(imageData);
            image.setHeight(50f).setWidth(50f).setMarginLeft(110f);
            cell.add(image);

            //rajouter le logo de la CNSS sur le recepissé de la CNSS
//            ImageData imageData = ImageDataFactory.create("logo cnss.jpg");
//            Image image=new Image(imageData);
//            image.setHeight(50f).setWidth(50f);
//            document.add(image);

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

            document.add(tabEnTet);

            document.add(new Paragraph("\nAccusé de réception").setFontSize(20f).setTextAlignment(TextAlignment.CENTER).setFont(bold));
            document.add(
                    new Paragraph()
                            .setFontSize(14f).setMarginLeft(35f)
                            .add(new Text("Demande de :").setUnderline().setFont(font))
                            .add(new Text(" "+demande.getLibelle()+"\n").setFont(bold))
                            .add(new Text("N° :").setUnderline().setFont(font))
                            .add(new Text(" "+demande.getId()+"\n\n").setFont(bold))
            );

            document.add(new Paragraph()
                    .add(new Text(" Informations Concerné(e) ").setFont(bold).setBorder(Border.NO_BORDER)));

            if (demande.getPersonne()!=null) {
                document.add(new Paragraph().setMarginLeft(35f)
                        .add(new Text("Nom (ou raison sociale) : ").setFont(font))
                        .add(new Text(String.valueOf(demande.getPersonne().getNom())).setFont(bold))

                        .add(new Text("\nPrénom : ").setFont(font))
                        .add(new Text(demande.getPersonne().getPrenom()).setFont(bold))

                        .add(new Text("\nAdresse : ").setFont(font))
                        .add(new Text(String.valueOf(demande.getAdresse())).setFont(bold))

                        .add(new Text("\nTel : ").setFont(font))
                        .add(new Text(String.valueOf(demande.getPersonne().getTelephone())).setFont(bold))
                );

                if (demande.getRccm()!=null){
                    document.add(new Paragraph().setMarginLeft(35f)
                            .add(new Text("Numéro RCCM : ").setFont(font))
                            .add(new Text(String.valueOf(demande.getRccm())).setFont(bold))
                    );
                }

                if (demande.getNif()!=null){
                    document.add(new Paragraph().setMarginLeft(35f)
                            .add(new Text("NIF : ").setFont(font))
                            .add(new Text(String.valueOf(demande.getNif())).setFont(bold))
                    );
                }

                if (demande.getRegion()!=null){
                    document.add(new Paragraph().setMarginLeft(35f)
                            .add(new Text("Région : ").setFont(font))
                            .add(new Text(String.valueOf(demande.getRegion())).setFont(bold))
                    );
                }

                if (demande.getVille()!=null){
                    document.add(new Paragraph().setMarginLeft(35f)
                            .add(new Text("Ville : ").setFont(font))
                            .add(new Text(String.valueOf(demande.getVille())).setFont(bold))
                    );
                }
            }

            document.add(new Paragraph("\n\n")
                    .add(new Text(" Constitution du dossier : ").setFont(bold).setBorder(Border.NO_BORDER)));

            Paragraph dossier= new Paragraph().setMarginLeft(35f);

            for (int i=0; i<fichierList.size(); i++){
                dossier.add(new Text(""+String.valueOf(i +1)+".     "+fichierList.get(i).getNomFichier()+"\n"));
            }
            document.add(dossier);

            document.add(
                    new Paragraph("\n\nNiamey, "+ LocalDate.now(ZoneId.of("Africa/Niamey")).toString())
                            .setTextAlignment(TextAlignment.RIGHT)
                            .setFont(font)
            );

            document.add(
                    new Paragraph()
                            .setTextAlignment(TextAlignment.RIGHT)
                            .add(new Text("Le réceptionniste : ").setFont(font))
                            .add(new Text("DJIBRILLA Almoustapha").setFont(bold))
            );

            document.close();

            fichier= new File(nomFichier);
        }
        return fichier;
    }
}
