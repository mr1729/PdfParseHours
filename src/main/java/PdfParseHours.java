import service.ParseInvoicesService;
import service.ParsePayStubService;
import service.ParsePdfService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static service.Constants.ENC_PAYSTUB_PASSPHRASE;
import static service.Constants.PAYSTUB_FOLDER;

public class PdfParseHours {

    public static void main(String[] args) {

//        ParseInvoicesService parseInvoicesService = new ParseInvoicesService();

//        parseInvoicesService.getMonthlyInvoicesList();
//
        ParsePdfService parsePdfService = new ParsePdfService();

        ParsePayStubService parsePayStubService = new ParsePayStubService(parsePdfService);
        parsePayStubService.process();
    }
}
