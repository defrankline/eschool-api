package com.kachinga.eschool.util;

import java.math.BigDecimal;

public final class MathUtil {

    /**

     Returns the minimum of two BigDecimal numbers.
     @param num1 The first BigDecimal number.
     @param num2 The second BigDecimal number.
     @return The smaller of the two BigDecimal numbers.
     */
    public static BigDecimal getMin(BigDecimal num1, BigDecimal num2) {
        return num1.min(num2);
    }
}
