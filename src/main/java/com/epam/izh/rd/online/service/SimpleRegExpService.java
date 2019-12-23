package com.epam.izh.rd.online.service;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {
    private static String srcPath = "src/main/resources/sensitive_data.txt";

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        final String CARD_NUMBER_VALIDATOR = "((?<dg1>\\d{4})\\s*(?<dg2>\\d{4})\\s*(?<dg3>\\d{4})\\s*(?<dg4>\\d{4})\\s*)+";
        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(srcFile))) {
            do {
                stringBuilder.append(reader.readLine());
            } while (reader.readLine() != null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Matcher matcher = Pattern.compile(CARD_NUMBER_VALIDATOR).matcher(stringBuilder);
        while (matcher.find()) {
            stringBuilder.replace(matcher.start("dg2"), matcher.end("dg2"),"****");
            stringBuilder.replace(matcher.start("dg3"), matcher.end("dg3"),"****");
        }
        return stringBuilder.toString();
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        final String PAYMENT_VALIDATOR = ".*(\\$\\{payment_amount\\}).*";
        final String BALANCE_VALIDATOR = ".*(\\$\\{balance\\}).*";
        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(srcFile))) {
            do {
                stringBuilder.append(reader.readLine());
            } while (reader.readLine() != null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] placeHolders = {PAYMENT_VALIDATOR, BALANCE_VALIDATOR};
        double[] moneys = {paymentAmount, balance};
        for (int i = 0; i < placeHolders.length; i++) {
            Matcher matcher = Pattern.compile(placeHolders[i]).matcher(stringBuilder);
            if (matcher.find()) {
                stringBuilder.replace(matcher.start(1), matcher.end(1), String.format("%.0f", moneys[i]));
            }
        }
        return stringBuilder.toString();
    }
}
