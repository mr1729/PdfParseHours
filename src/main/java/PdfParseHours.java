import service.ParseInvoicesService;

public class PdfParseHours {

    public static void main(String[] args){

        ParseInvoicesService parseInvoicesService = new ParseInvoicesService();

        parseInvoicesService.getMonthlyInvoicesList();

    }
}
