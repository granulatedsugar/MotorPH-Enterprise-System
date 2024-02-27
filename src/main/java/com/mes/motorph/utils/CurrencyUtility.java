package com.mes.motorph.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtility {
    public static NumberFormat getPhilippinesCurrencyFormatter() {
        // Create a NumberFormat instance for the Philippines locale
        return NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
    }
}
