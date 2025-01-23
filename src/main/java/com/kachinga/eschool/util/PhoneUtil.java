package com.kachinga.eschool.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PhoneUtil {
    /**
     * Checks if a given phone number is invalid based on a specific pattern.
     *
     * @param phoneNumber The phone number to be validated.
     * @return {@code true} if the phone number is invalid, {@code false} otherwise.
     */
    public static boolean isInvalidTzPhoneNumber(String phoneNumber) {
        String tanzaniaPhoneRegex = "(\\+255|255|0)(61|62|63|64|65|66|67|68|69|71|72|73|74|75|76|77|78|79)\\d{7}";
        Pattern pattern = Pattern.compile(tanzaniaPhoneRegex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return !matcher.matches();
    }

    public static boolean isValidTzPhoneNumber(String phoneNumber) {
        String tanzaniaPhoneRegex = "(\\+255|255|0)(61|62|63|64|65|66|67|68|69|71|72|73|74|75|76|77|78|79)\\d{7}";
        Pattern pattern = Pattern.compile(tanzaniaPhoneRegex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
    /**
     * Checks if a given phone number is valid based on a specific pattern.
     *
     * @param phoneNumber The phone number to be validated.
     * @return {@code true} if the phone number is valid, {@code false} otherwise.
     */
    public static boolean validPhoneNumber(String phoneNumber) {
        String pattern = "^(2557|2556|07|06)\\d{8}$"; // Regular expression pattern for valid phone numbers
        Pattern regex = Pattern.compile(pattern); // Compile the pattern into a regex object
        Matcher matcher = regex.matcher(phoneNumber); // Create a Matcher object for the given phone number
        return matcher.matches(); // Return true if the phone number matches the pattern, false otherwise
    }

}
