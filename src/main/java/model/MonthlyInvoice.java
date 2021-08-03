package model;

import java.time.YearMonth;


public class MonthlyInvoice {
    private YearMonth yearMonth;

    private Double hours = 0.0;

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

}
