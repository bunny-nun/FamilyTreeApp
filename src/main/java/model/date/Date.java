package model.date;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Date implements Comparable<Date> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final LocalDate date;

    /**
     * Class Date
     *
     * @param date the string value of the date in format "dd.MM.yyyy"
     */
    public Date(String date) {
        this.date = LocalDate.parse(date, formatter);
    }

    /**
     * Class Date with value of current date
     */
    public Date() {
        this.date = LocalDate.now();
    }


    /**
     * Returns the value of this date in LocalDate format
     *
     * @return the value of this date in LocalDate format
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the period in years between two dates given in string format
     *
     * @param startDate string value of start date in format "dd.MM.yyyy"
     * @param endDate   string value of end date in format "dd.MM.yyyy"
     * @return int value of years between start date and end date
     */
    public int countPeriod(String startDate, String endDate) {
        Date start = new Date(startDate);
        Date end = new Date();
        if (endDate != null) {
            end = new Date(endDate);
        }
        return start.getPeriod(end);
    }

    /**
     * Returns the amount of years between this date and given date
     *
     * @param endDate end date in form of the class Date
     * @return the int value of years between this date and given object of class Date
     */
    private int getPeriod(Date endDate) {
        return (Period.between(this.date, endDate.date)).getYears();
    }

    /**
     * Returns the string value of this date
     *
     * @return the string value of the object of class Date in format "dd.MM.yyyy"
     */
    @Override
    public String toString() {
        return this.date.format(this.formatter);
    }

    @Override
    public int compareTo(Date anotherDate) {
        return this.date.compareTo(anotherDate.getDate());
    }
}

