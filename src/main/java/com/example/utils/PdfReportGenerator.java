package com.example.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.nio.file.Paths;

public class PdfReportGenerator {
    public static void generateReport(List<String> testResults) {
        Document document = new Document();
        FileOutputStream fos = null;
        try {
            String outputDir = createTargetDirectory();
            String filePath = Paths.get(outputDir, "TestReport.pdf").toString();
            System.out.println("Intentando generar el informe PDF en: " + filePath);
            
            File file = new File(filePath);
            fos = new FileOutputStream(file);
            PdfWriter.getInstance(document, fos);
            document.open();
            
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

            Paragraph title = new Paragraph("Test Execution Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            for (String result : testResults) {
                Paragraph paragraph = new Paragraph(result, contentFont);
                document.add(paragraph);
                document.add(Chunk.NEWLINE);
            }

            System.out.println("Informe PDF generado exitosamente en: " + filePath);
        } catch (DocumentException | IOException e) {
            System.err.println("Error al generar el informe PDF: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Fallo al generar el informe PDF", e);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el FileOutputStream: " + e.getMessage());
                }
            }
        }
    }

    private static String createTargetDirectory() throws IOException {
        String projectDir = System.getProperty("user.dir");
        String targetDir = Paths.get(projectDir, "target").toString();
        File targetFolder = new File(targetDir);
        
        if (!targetFolder.exists()) {
            System.out.println("El directorio 'target' no existe. Intentando crearlo...");
            if (targetFolder.mkdirs()) {
                System.out.println("Directorio 'target' creado exitosamente.");
            } else {
                throw new IOException("No se pudo crear el directorio 'target'.");
            }
        } else {
            System.out.println("El directorio 'target' ya existe.");
        }
        
        System.out.println("Usando directorio: " + targetDir);
        return targetDir;
    }
}