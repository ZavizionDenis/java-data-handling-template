package com.epam.izh.rd.online.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class SimpleBigNumbersService implements BigNumbersService {

    /**
     * Метод делит первое число на второе с заданной точностью
     * Например 1/3 с точностью 2 = 0.33
     * @param range точность
     * @return результат
     */
    @Override
    public BigDecimal getPrecisionNumber(int a, int b, int range) {
        return BigDecimal.valueOf(a).divide(BigDecimal.valueOf(b), range, RoundingMode.HALF_UP);
    }

    /**
     * Метод находит простое число по номеру
     *
     * @param range номер числа, считая с числа 2
     * @return простое число
     */
    @Override
    public BigInteger getPrimaryNumber(int range) {
        int count = 0;
        BigInteger primaryNumber = BigInteger.valueOf(0L);
        BigInteger checkedNumber = BigInteger.valueOf(2L);
        while (count <= range) {
            boolean isPrimary = true;
            BigInteger delimetr = BigInteger.valueOf(2L);
            while (delimetr.compareTo(checkedNumber) < 0) {
                if (checkedNumber.remainder(delimetr).equals(BigInteger.valueOf(0L))) {
                    isPrimary = false;
                    break;
                }
                delimetr = delimetr.add(BigInteger.valueOf(1L));
            }
            if (isPrimary) {
                primaryNumber = checkedNumber;
                count++;
            }
            checkedNumber = checkedNumber.add(BigInteger.valueOf(1L));
        }
        return primaryNumber;
    }
}
