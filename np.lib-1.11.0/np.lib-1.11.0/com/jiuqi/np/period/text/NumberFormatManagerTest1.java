/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.period.text;

import com.jiuqi.np.period.text.NumberFormatFactory;
import java.text.NumberFormat;

class NumberFormatManagerTest1
extends Thread {
    private static String[] testNumbers = new String[]{"12345678901.2345", "23456789012.3451", "34567890123.4512", "45678901234.5123", "56789012345.1234", "67890123451.2345", "78901234512.3456", "89012345123.4567", "90123451234.5678", "01234512345.6789", "12345123456.7890", "23451234567.8901", "34512345678.9012", "45123456789.0123", "51234567890.1234", "12345678904.56", "23456789045.61", "34567890456.12", "45678904561.23", "56789045612.34", "67890456123.45", "78904561234.56", "89045612345.67", "90456123456.78", "04561234567.89", "45612345678.90", "56123456789.04", "61234567890.45"};

    NumberFormatManagerTest1() {
    }

    public static int countDigits(String number) {
        int result = 0;
        for (int i = 0; i < number.length(); ++i) {
            char ch = number.charAt(i);
            if (ch < '0' || ch > '9') continue;
            result += ch - 48;
        }
        return result;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < testNumbers.length; ++j) {
                try {
                    NumberFormat format = NumberFormatFactory.createNumberFormat(4, true);
                    Number value = format.parse(testNumbers[j]);
                    NumberFormatFactory.recycleNumberFormat(format);
                    Thread.sleep(17L + Math.round(40.0 * Math.random()));
                    continue;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
    }
}

