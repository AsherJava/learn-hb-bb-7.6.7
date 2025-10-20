/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.period.text;

import com.jiuqi.np.period.text.NumberFormatFactory;
import java.text.NumberFormat;

class NumberFormatManagerTest
extends Thread {
    private static double[] testNumbers = new double[]{1.23456789012345E10, 2.34567890123451E10, 3.45678901234512E10, 4.56789012345123E10, 5.67890123451234E10, 6.78901234512345E10, 7.89012345123456E10, 8.90123451234567E10, 9.01234512345678E10, 1.2345123456789E9, 1.2345123456789E10, 2.34512345678901E10, 3.45123456789012E10, 4.51234567890123E10, 5.12345678901234E10, 1.234567890456E10, 2.345678904561E10, 3.456789045612E10, 4.567890456123E10, 5.678904561234E10, 6.789045612345E10, 7.890456123456E10, 8.904561234567E10, 9.045612345678E10, 4.56123456789E9, 4.56123456789E10, 5.612345678904E10, 6.123456789045E10};

    NumberFormatManagerTest() {
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
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < testNumbers.length; ++j) {
                try {
                    NumberFormat format = NumberFormatFactory.createNumberFormat(4, true);
                    String value = format.format(testNumbers[j]);
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

