package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor

public class PayStubDetails {
    private Double salary;
    private Integer hours;
    private Double netPay;
    private LocalDate fromPeriod;
    private LocalDate toPeriod;
    private LocalDate payDate;
}
