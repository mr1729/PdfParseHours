package service;

import model.InvoiceDetails;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParsePdfService {

    static double a = 0.0;
    PDDocument document;

    static Set<LocalDate> fromDateSet = new HashSet<>();
    public static List<String> abc = new ArrayList<>();

    public InvoiceDetails getInvoiceData(String text) {

        InvoiceDetails invoiceDetails = new InvoiceDetails();
        Pattern pattern = Pattern.compile("(Period:)\\s(\\d{2}\\/\\d{2}\\/\\d{4})\\s-\\s(\\d{2}\\/\\d{2}\\/\\d{4})\\n");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String fromDate = matcher.group(2);
            if (ParsePdfService.fromDateSet.contains(LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("MM/dd/yyyy")))) {
                return null;
            } else {
                fromDateSet.add(LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            }
            invoiceDetails.setFromDate(LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            String toDate = matcher.group(3);
            invoiceDetails.setToDate(LocalDate.parse(toDate, DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        }
        pattern = Pattern.compile("([A-Z]\\w{2})\\s(\\d{2}\\/\\d{2})\\s(\\d.\\d{2})\\s\\d.\\d{2}");
        matcher = pattern.matcher(text);
        long i = 0;
        while (matcher.find()) {
            invoiceDetails.getInvoice().put(invoiceDetails.getFromDate().plusDays(i), Double.valueOf(matcher.group(3)));
            abc.add(invoiceDetails.getFromDate().plusDays(i).toString());
            i++;
        }
       /*
2020-11-15 -----> 80.0hrs -----> 3760.0    3760    2937.36
2020-11-30 -----> 80.0hrs -----> 3760.0    3760    2937.36
2020-12-15 -----> 88.0hrs -----> 4136.0    4136    3201.60
2020-12-31 -----> 88.0hrs -----> 4136.0    4136    3201.58
2021-01-15 -----> 80.0hrs -----> 3760.0    3760    2937.36
2021-01-31 -----> 72.0hrs -----> 3384.0    3384    2673.12
2021-02-15 -----> 88.0hrs -----> 4136.0    3760    2937.36    - 8 hrs
2021-02-28 -----> 63.0hrs -----> 2961.0    2937    2937.36    + 7 hrs
2021-03-15 -----> 88.0hrs -----> 4136.0    4136    3201
2021-03-31 -----> 96.0hrs -----> 4512.0    4136    3201.60    - 8 hrs
2021-04-15 -----> 88.0hrs -----> 4136.0    4136    3201.58
2021-04-30 -----> 88.0hrs -----> 4136.0    4136    3201.60
2021-05-15 -----> 77.0hrs -----> 3619.0    3619    2838.14
2021-05-31 -----> 80.0hrs -----> 3760.0    3760    2937.36
2021-06-15 -----> 90.0hrs -----> 4230.0    4230    3266.40
2021-06-30 -----> 89.0hrs -----> 4183.0    4136    3201.60    - 1 hrs
2021-07-15 -----> 76.0hrs -----> 3572.0    3384    2673.12    - 4 hrs
2021-07-31 -----> 44.0hrs -----> 2068.0
       */
        return invoiceDetails;
    }

    public String parsePdf(String filePath) {
        File pdfFile = new File(filePath);
        try {
            document = PDDocument.load(pdfFile);
            PDFTextStripper textStripper = new PDFTextStripper();
            document.getPage(0);
            String result = textStripper.getText(document);
            document.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
