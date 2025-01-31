package com.ing.creditModule.Util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LoanProcessUtil {

    public LoanProcessUtil() {

    }

    /**
     * @param amount
     * @param numberOfInstallment
     * @return
     */
    public static BigDecimal calculateLoanAmount(BigDecimal amount, Integer numberOfInstallment) {
        return amount.divide(BigDecimal.valueOf(numberOfInstallment), 2, RoundingMode.HALF_UP);
    }


    /**
     * @param numberOfInstallments
     * @return
     */
    public static List<Date> generateDueDates(int numberOfInstallments) {
        List<Date> dueDates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        for (int i = 0; i < numberOfInstallments; i++) {
            dueDates.add(calendar.getTime());
            calendar.add(Calendar.MONTH, 1);
        }

        return dueDates;
    }

    public static Boolean isDateBeforeFutureDateByMonths(Date date , int addMonth) {

        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MONTH, addMonth);
        Date newDate = cal.getTime();
        return date.before(newDate);

    }

    public static Long  dayDifference(Date date ) {

        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        Date newDate = cal.getTime();

        return (newDate.getTime() - date.getTime()) / (1000 * 60 * 60 * 24);

    }
}
