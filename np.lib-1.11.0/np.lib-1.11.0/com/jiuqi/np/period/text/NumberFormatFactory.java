/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.period.text;

import com.jiuqi.np.period.text.NumberFormatManagerTest1;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Stack;

public class NumberFormatFactory {
    private static HashMap integerFormats = new HashMap();
    private static HashMap numberFormats = new HashMap();
    private static HashMap patternFormats = new HashMap();
    private static final Integer[] INT_VALUES = new Integer[256];
    private static final Integer[] INT_VALUES_NEG = new Integer[256];

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NumberFormat createIntegerFormat(int integerDigits) {
        NumberFormat format = null;
        Integer key = NumberFormatFactory.getInteger(integerDigits);
        HashMap hashMap = integerFormats;
        synchronized (hashMap) {
            Stack formats = (Stack)integerFormats.get(key);
            if (formats == null) {
                formats = new Stack();
                integerFormats.put(key, formats);
            }
            if (!formats.isEmpty()) {
                format = (NumberFormat)formats.pop();
            }
        }
        if (format == null) {
            format = NumberFormat.getInstance();
            format.setMinimumFractionDigits(0);
            format.setMaximumFractionDigits(0);
            format.setMinimumIntegerDigits(integerDigits);
            format.setGroupingUsed(false);
        }
        return format;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void recycleIntegerFormat(NumberFormat format) {
        Integer key = NumberFormatFactory.getInteger(format.getMinimumIntegerDigits());
        HashMap hashMap = integerFormats;
        synchronized (hashMap) {
            Stack<NumberFormat> formats = (Stack<NumberFormat>)integerFormats.get(key);
            if (formats == null) {
                formats = new Stack<NumberFormat>();
                integerFormats.put(key, formats);
            }
            if (formats != null) {
                formats.push(format);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NumberFormat createNumberFormat(int fractionDigits, boolean groupingUsed) {
        Integer key = NumberFormatFactory.getInteger(groupingUsed ? fractionDigits : -fractionDigits);
        NumberFormat format = null;
        HashMap hashMap = numberFormats;
        synchronized (hashMap) {
            Stack formats = (Stack)numberFormats.get(key);
            if (formats == null) {
                formats = new Stack();
                numberFormats.put(key, formats);
            }
            if (!formats.isEmpty()) {
                format = (NumberFormat)formats.pop();
            }
        }
        if (format == null) {
            format = NumberFormat.getInstance();
            format.setMaximumFractionDigits(fractionDigits);
            format.setMinimumFractionDigits(fractionDigits);
            format.setMinimumIntegerDigits(1);
            format.setGroupingUsed(groupingUsed);
        }
        return format;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NumberFormat createNumberFormat(String pattern) {
        NumberFormat format = null;
        HashMap hashMap = patternFormats;
        synchronized (hashMap) {
            Stack formats = (Stack)patternFormats.get(pattern);
            if (formats == null) {
                formats = new Stack();
                patternFormats.put(pattern, formats);
            }
            if (!formats.isEmpty()) {
                format = (NumberFormat)formats.pop();
            }
        }
        return format != null ? format : new PatternDecimalFormat(pattern);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void recycleNumberFormat(NumberFormat format) {
        if (format instanceof PatternDecimalFormat) {
            HashMap hashMap = patternFormats;
            synchronized (hashMap) {
                String key = ((PatternDecimalFormat)format).pattern;
                Stack<NumberFormat> formats = (Stack<NumberFormat>)patternFormats.get(key);
                if (formats == null) {
                    formats = new Stack<NumberFormat>();
                    patternFormats.put(key, formats);
                }
                if (formats != null) {
                    formats.push(format);
                }
            }
        }
        Integer key = NumberFormatFactory.getInteger(format.isGroupingUsed() ? format.getMinimumFractionDigits() : -format.getMinimumFractionDigits());
        HashMap hashMap = numberFormats;
        synchronized (hashMap) {
            Stack<NumberFormat> formats = (Stack<NumberFormat>)numberFormats.get(key);
            if (formats == null) {
                formats = new Stack<NumberFormat>();
                integerFormats.put(key, formats);
            }
            if (formats != null) {
                formats.push(format);
            }
        }
    }

    public static final Integer getInteger(int index) {
        if (index >= 0 && index < 256) {
            return INT_VALUES[index];
        }
        if (index < 0 && index > -255) {
            return INT_VALUES_NEG[-index];
        }
        return new Integer(index);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 2000; ++i) {
            NumberFormatManagerTest1 testor = new NumberFormatManagerTest1();
            testor.start();
        }
    }

    static {
        for (int i = 0; i < 256; ++i) {
            NumberFormatFactory.INT_VALUES[i] = new Integer(i);
            NumberFormatFactory.INT_VALUES_NEG[i] = new Integer(-i);
        }
    }

    static class PatternDecimalFormat
    extends DecimalFormat {
        private static final long serialVersionUID = -6718804186779424205L;
        public final String pattern;

        public PatternDecimalFormat(String pattern) {
            super(pattern);
            this.pattern = pattern;
        }
    }
}

