package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class InvoiceDetails {
    private LocalDate fromDate;
    private LocalDate toDate;
    private final Map<LocalDate, Double> invoice = new HashMap<>();

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Map<LocalDate, Double> getInvoice() {
        return invoice;
    }

    public String toString(){
        return invoice.toString();
    }

}
