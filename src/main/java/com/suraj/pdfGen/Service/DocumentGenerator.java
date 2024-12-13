package com.suraj.pdfGen.Service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class DocumentGenerator {

    @Autowired
    private org.springframework.core.io.ResourceLoader resourceLoader;

    public String htmlToPdf(String processedHtml) {

        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
             FileOutputStream fOut = new FileOutputStream("/home/decimal/Desktop/Documents/doc.pdf")) {

            // Create a FontProvider
            FontProvider fontProvider = new FontProvider();

            // Load the font using Spring's ResourceLoader
            Resource fontResource = resourceLoader.getResource("classpath:/fonts/custom-font.ttf");

            // Debugging step: print out the font path to ensure it's correct
            URI fontURI = fontResource.getURI();
            System.out.println("Font Path: " + fontURI);

            // Check if the font exists
            if (!fontResource.exists()) {
                throw new RuntimeException("Custom font not found! Path: " + fontURI);
            }

            // Optionally, verify the font file is accessible on disk (for local testing)
            if (Files.exists(Paths.get(fontURI))) {
                System.out.println("Font file exists at: " + fontURI.getPath());
            } else {
                System.out.println("Font file does not exist at: " + fontURI.getPath());
            }

            // Register the font with the FontProvider
            fontProvider.addFont(fontURI.getPath());

            // Add standard fonts
            fontProvider.addStandardPdfFonts();

            // Set up the converter properties to use the font provider
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(fontProvider);

            // Convert the HTML to PDF and write to byteArrayOutputStream
            HtmlConverter.convertToPdf(processedHtml, pdfWriter, converterProperties);

            // Write the output stream content to the final PDF file
            byteArrayOutputStream.writeTo(fOut);

            return "/home/decimal/Desktop/doc.pdf"; // Return the path to the generated PDF

        } catch (IOException e) {
            e.printStackTrace(); // Log the error
            return "Error generating PDF: " + e.getMessage();
        }
    }
}
