package model;

import java.util.List;

public class YearlyInvoice {

    private Long year;
    private final List<MonthlyInvoice> monthlyInvoices = List.of(new MonthlyInvoice[12]);
    private Double hours = 0.0;

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public List<MonthlyInvoice> getMonthlyInvoices() {
        return monthlyInvoices;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

}
