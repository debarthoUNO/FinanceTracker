package com.financetracker;

import android.text.InputFilter;
import android.text.Spanned;

public class DecimalDigitsInputFilter implements InputFilter {

    private final int decimalDigits;

    public DecimalDigitsInputFilter(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {

        String result = dest.toString() + source.toString();

        if (result.contains(".")) {
            int index = result.indexOf(".");
            int lengthAfterDecimal = result.length() - index - 1;

            if (lengthAfterDecimal > decimalDigits) {
                return "";
            }
        }

        return null;
    }
}