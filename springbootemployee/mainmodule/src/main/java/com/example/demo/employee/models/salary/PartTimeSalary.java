package com.example.demo.employee.models.salary;

import lombok.Data;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;

@Data
public class PartTimeSalary implements Salary {
    private final BigDecimal hourlyWage = new BigDecimal("50.00");
    private final int workingHoursPerDay;

    @Override
    public BigDecimal computeAnnualSalary() {
        return hourlyWage
                .multiply(new BigDecimal(workingHoursPerDay))
                .multiply(new BigDecimal(getNumberOfWeekdaysInCurrentYear()));
    }

    private int getNumberOfWeekdaysInCurrentYear() {
        LocalDate dateIterator = LocalDate.of(Year.now().getValue(), 1, 1);
        LocalDate localDateEnd = LocalDate.of(Year.now().getValue(), 12, 31);

        int numOfWeekdays = 0;
        while(dateIterator.isBefore(localDateEnd)) {
            if(dateIterator.getDayOfWeek() != DayOfWeek.SATURDAY && dateIterator.getDayOfWeek() != DayOfWeek.SUNDAY) {
                numOfWeekdays++;
            }
            dateIterator = dateIterator.plusDays(1);
        }
        return numOfWeekdays;
    }
}
