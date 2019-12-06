package com.epam.izh.rd.online.service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SimpleDateService implements DateService {

    /**
     * Метод парсит дату в строку
     *
     * @param localDate дата
     * @return строка с форматом день-месяц-год(01-01-1970)
     */
    @Override
    public String parseDate(LocalDate localDate) {
        if (localDate == null) {
            return "";
        }
        String date = null;
        try {
            date = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyy"));
        } catch (DateTimeException ex) {
            ex.printStackTrace();
        }
        return date;
    }

    /**
     * Метод парсит строку в дату
     *
     * @param string строка в формате год-месяц-день часы:минуты (1970-01-01 00:00)
     * @return дата и время
     */
    @Override
    public LocalDateTime parseString(String string) {
        LocalDateTime dateTime = null;
        try {
            dateTime = LocalDateTime.parse(string, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException ex) {
            ex.printStackTrace();
        }
        return dateTime;
    }

    /**
     * Метод конвертирует дату в строку с заданным форматом
     *
     * @param localDate исходная дата
     * @param formatter формат даты
     * @return полученная строка
     */
    @Override
    public String convertToCustomFormat(LocalDate localDate, DateTimeFormatter formatter) {
        if (localDate == null || formatter == null) {
            return "";
        }
        String date = null;
        try {
            date = localDate.format(formatter);
        } catch (DateTimeException ex) {
            ex.printStackTrace();
        }
        return date;
    }

    /**
     * Метод получает следующий високосный год
     *
     * @return високосный год
     */
    @Override
    public long getNextLeapYear() {
        LocalDate date = LocalDate.now();
        long count = 1;
        while (!date.isLeapYear()) {
            date = date.plusYears(count);
            count++;
        }
        return date.getYear();
    }

    /**
     * Метод считает число секунд в заданном году
     *
     * @return число секунд
     */
    @Override
    public long getSecondsInYear(int year) {
        final int SECONDS_OF_DAY = 24 * 60 * 60;
        LocalDate date = LocalDate.of(year, Month.JANUARY, 1);
        return date.lengthOfYear() * SECONDS_OF_DAY;
    }


}
