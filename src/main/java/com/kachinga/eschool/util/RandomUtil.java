package com.kachinga.eschool.util;

import org.apache.commons.lang3.RandomStringUtils;

public final class RandomUtil {
    private static final String NUMERIC_STRING = "0123456789";

    private RandomUtil() {
    }

    /**
     * Generates a random numeric string of the specified length.
     *
     * @param count the length of the random numeric string to generate
     * @return a random numeric string of the specified length
     */
    public static String randomNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * NUMERIC_STRING.length());
            builder.append(NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    /**
     * Generates a random transaction number in the format XXXX-XXXX-XXXX-XXXX.
     *
     * @return a random transaction number
     */
    public static String transactionNumber() {
        String section1 = RandomStringUtils.randomNumeric(4);
        String section2 = RandomStringUtils.randomNumeric(4);
        String section3 = RandomStringUtils.randomNumeric(4);
        String section4 = RandomStringUtils.randomNumeric(4);
        return (section1.concat("-").concat(section2).concat("-").concat(section3).concat("-")
                .concat(section4)).toUpperCase();
    }

}
