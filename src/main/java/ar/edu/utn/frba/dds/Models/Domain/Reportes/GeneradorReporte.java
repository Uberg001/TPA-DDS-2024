package ar.edu.utn.frba.dds.Models.Domain.Reportes;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Vianda;

public class GeneradorReporte {
    private Reporte reporte;
    private static final Logger logger = LoggerFactory.getLogger(GeneradorReporte.class);

    public GeneradorReporte(Reporte reporte) {
        this.reporte = reporte;
    }

    public Runnable generarReporteBeta(List<Heladera> heladeras, List<PersonaHumana> personasHumanas, List<Vianda> viandas) {
        return () -> {
            logger.info("Ejecutando generarReporteBeta...");
            generarReporte(heladeras, personasHumanas, viandas);
            logger.info("Finalizó generarReporteBeta.");
        };
    }

    public void generarReporte(List<Heladera> heladeras, List<PersonaHumana> personasHumanas, List<Vianda> viandas) {
       try {
            Document document = createDocument();
            addTitle(document, "Reporte Semanal", 24);
            addSection(document, "Fallas por Heladera", reporte.fallasPorHeladera(heladeras));
            addSection(document, "Ingresos y retiros por Heladera", reporte.ingresoYRetiroDeViandasPorHeladera(heladeras));
            addSection(document, "Viandas por Colaborador", reporte.viandasPorColaborador(personasHumanas, viandas));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
       }
   }

    private Document createDocument() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        String fileName = "reporte_" + formattedDateTime + ".pdf";
        File file = new File("src\\main\\resources\\public\\reportes\\" + fileName);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);
        //reporte.setPath("2024-tpa-mi-no-grupo-09/src/main/resources/reportes/"+fileName);
        return new Document(pdf);
    }

    private void addTitle(Document document, String title, Integer fontSize) {
        Text titleText = new Text(title);
        titleText.setBold();
        titleText.setFontSize(fontSize);
        document.add(new Paragraph(titleText));
    }

   private void addSection(Document document, String title, List<String> sectionContent) {
        addTitle(document, title, 16);
        com.itextpdf.layout.element.List list = new com.itextpdf.layout.element.List();
        for (int i = 0; i < sectionContent.size(); i++) {
            ListItem listItem = new ListItem();
            if (i == 0) {
                document.add(new Paragraph(sectionContent.get(i)));
            }
            else {
                listItem.add(new Paragraph(sectionContent.get(i)));
                list.add(listItem);
            }

        }
        document.add(list);
    }
}