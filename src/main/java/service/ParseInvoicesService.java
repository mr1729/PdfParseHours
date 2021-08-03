package service;

import model.InvoiceDetails;

import java.io.File;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

import static service.Constants.INV_FOLDER;

public class ParseInvoicesService {

    File invoicesFolder = new File(INV_FOLDER);
    ParsePdfService parsePdfService = new ParsePdfService();

    public Map<YearMonth, Double> getMonthlyInvoicesList() {
        Map<YearMonth, Double> monthlyInvoiceList = new HashMap<>();
        Map<LocalDate, Double> semiMonthlyInvoice = new HashMap<>();

        List<String> filesList = getFilesInFolder();
        for (String file : filesList) {
            InvoiceDetails invoiceDetails = parsePdfService.getInvoiceData(parsePdfService.parsePdf(INV_FOLDER + file));
           if(invoiceDetails!=null) {
               for (LocalDate date : invoiceDetails.getInvoice().keySet()) {
                   YearMonth yearMonth = YearMonth.of(date.getYear(), date.getMonth());
                   monthlyInvoiceList.put(yearMonth, monthlyInvoiceList.getOrDefault(yearMonth, 0.0) + invoiceDetails.getInvoice().get(date));
                   LocalDate midOfMonth = yearMonth.atDay(15);
                   if(date.isAfter(midOfMonth)){
                       semiMonthlyInvoice.put(yearMonth.atEndOfMonth(),semiMonthlyInvoice.getOrDefault(yearMonth.atEndOfMonth(), 0.0) + invoiceDetails.getInvoice().get(date));
                   }
                   else{
                       semiMonthlyInvoice.put(midOfMonth,semiMonthlyInvoice.getOrDefault(midOfMonth, 0.0) + invoiceDetails.getInvoice().get(date));
                   }
               }
           }
        }
        System.err.println(monthlyInvoiceList+"\n");
        System.err.println("semiMonthlyInvoice ---\n"+semiMonthlyInvoice);

        List<LocalDate> set =  semiMonthlyInvoice.keySet().stream().collect(Collectors.toList());
        Collections.sort(set);
        for(LocalDate date: set){
            System.out.println(date+" -----> "+semiMonthlyInvoice.get(date)+"hrs -----> "+semiMonthlyInvoice.get(date)*47);
        }

        return monthlyInvoiceList;
    }

    private List<String> getFilesInFolder() {
        return List.of(invoicesFolder.list()).stream().filter(element -> element.contains(".pdf")).collect(Collectors.toList());
    }
}
