package com.sais.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;


@Component
public class DateUtil {

    private static final DateTimeFormatter TURKISH_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

   
    public String formatTurkish(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(TURKISH_DATE_FORMATTER);
    }

   
    public LocalDate parseTurkish(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateStr, TURKISH_DATE_FORMATTER);
    }

    
    public LocalDate today() {
        return LocalDate.now();
    }

    
    public long daysBetween(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(start, end);
    }

    
    public long monthsBetween(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            return 0;
        }
        return ChronoUnit.MONTHS.between(start, end);
    }

    
    public long yearsBetween(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            return 0;
        }
        return ChronoUnit.YEARS.between(start, end);
    }

   
    public boolean isAfterToday(LocalDate date) {
        if (date == null) {
            return false;
        }
        return date.isAfter(LocalDate.now());
    }

    
    public boolean isBeforeToday(LocalDate date) {
        if (date == null) {
            return false;
        }
        return date.isBefore(LocalDate.now());
    }

   
    public boolean isBetween(LocalDate date, LocalDate start, LocalDate end) {
        if (date == null || start == null || end == null) {
            return false;
        }
        return !date.isBefore(start) && !date.isAfter(end);
    }
}


