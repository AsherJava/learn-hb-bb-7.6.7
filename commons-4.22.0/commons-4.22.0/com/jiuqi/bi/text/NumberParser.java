/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.text;

import java.text.ParseException;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

public class NumberParser {
    private static final Map<Character, Integer> NUMCHARS = new HashMap<Character, Integer>();
    private static final Map<Character, Integer> NUMUNITS;
    private boolean groupingUsed = false;
    private int fractionDigits = 0;
    private int ratio = 1;
    private int multiplier = 1;
    private Currency currency = null;

    public static Number toNumber(String text) throws ParseException {
        NumberParser parser = new NumberParser();
        return parser.parseNumber(text);
    }

    public static Double toCurrency(String text) throws ParseException {
        NumberParser parser = new NumberParser();
        return parser.parseCurrency(text);
    }

    public Number parseNumber(String text) throws ParseException {
        Integer chVal;
        char ch;
        int p;
        this.groupingUsed = false;
        this.fractionDigits = 0;
        this.ratio = 1;
        if (text == null || text.length() == 0) {
            return null;
        }
        int sign = 1;
        long integerVal = 0L;
        long fractionVal = 0L;
        String numStr = text;
        if (text.startsWith("-") || text.startsWith("\u8d1f")) {
            sign = -1;
            numStr = text.substring(1);
        } else if (text.startsWith("+")) {
            numStr = text.substring(1);
        }
        if (numStr.startsWith("\u767e\u5206\u4e4b")) {
            this.ratio = 100;
            numStr = numStr.substring(3);
        } else if (numStr.endsWith("%") || numStr.endsWith("\uff05")) {
            this.ratio = 100;
            numStr = numStr.substring(0, numStr.length() - 1);
        } else if (numStr.startsWith("\u5343\u5206\u4e4b")) {
            this.ratio = 1000;
            numStr = numStr.substring(3);
        } else if (numStr.endsWith("\u2030")) {
            this.ratio = 1000;
            numStr = numStr.substring(0, numStr.length() - 1);
        }
        Stack<NumberLevel> levels = new Stack<NumberLevel>();
        for (p = 0; p < numStr.length(); ++p) {
            ch = numStr.charAt(p);
            if (ch == '.' || ch == '\u70b9' || ch == '\u5143' || ch == '\u5706') break;
            if (ch == ',' || ch == '\uff0c') {
                this.groupingUsed = true;
                continue;
            }
            chVal = NUMCHARS.get(Character.valueOf(ch));
            if (chVal != null) {
                if (NumberParser.pushValue(levels, chVal)) continue;
                throw new ParseException("\u89e3\u6790\u6570\u503c\u9519\u8bef\uff1a" + text, p - 1);
            }
            Integer unitVal = NUMUNITS.get(Character.valueOf(ch));
            if (unitVal != null) {
                if (NumberParser.pushUnit(levels, unitVal)) continue;
                throw new ParseException("\u89e3\u6790\u6570\u503c\u9519\u8bef\uff1a" + text, p - 1);
            }
            throw new ParseException("\u89e3\u6790\u6570\u503c[" + text + "]\u65f6\u9047\u5230\u65e0\u6cd5\u8bc6\u522b\u7684\u5b57\u7b26\uff1a" + ch, p - 1);
        }
        if ((integerVal = NumberParser.sumValue(levels)) < 0L) {
            throw new ParseException("\u89e3\u6790\u6570\u503c\u9519\u8bef\uff1a" + text, p - 1);
        }
        while (p < numStr.length()) {
            ch = numStr.charAt(p);
            ++p;
            if (ch == '\u89d2') continue;
            if (ch == '\u5206') {
                if (this.fractionDigits > 1) continue;
                ++this.fractionDigits;
                continue;
            }
            ++this.fractionDigits;
            chVal = NUMCHARS.get(Character.valueOf(ch));
            if (chVal == null) {
                throw new ParseException("\u89e3\u6790\u6570\u503c[" + text + "]\u65f6\u9047\u5230\u65e0\u6cd5\u8bc6\u522b\u7684\u5b57\u7b26\uff1a" + ch, p - 1);
            }
            fractionVal = fractionVal * 10L + (long)chVal.intValue();
        }
        return NumberParser.makeValue(sign, integerVal, fractionVal, this.fractionDigits, this.ratio);
    }

    public Double parseCurrency(String text) throws ParseException {
        this.multiplier = 1;
        this.currency = null;
        if (text == null || text.length() == 0) {
            return null;
        }
        if (text.endsWith("%") || text.endsWith("\uff05")) {
            throw new ParseException("\u89e3\u6790\u7684\u6570\u503c\u4e0d\u662f\u8d27\u5e01\u7c7b\u578b\uff1a" + text, text.length() - 1);
        }
        text = this.parseSymbol(text);
        Number val = this.parseNumber(text = this.parseMultiplier(text));
        return val == null ? null : Double.valueOf(val.doubleValue() * (double)this.multiplier);
    }

    public boolean isGroupingUsed() {
        return this.groupingUsed;
    }

    public int getFractionDigits() {
        return this.fractionDigits;
    }

    public int getRatio() {
        return this.ratio;
    }

    public int getMultiplier() {
        return this.multiplier;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    private static boolean pushValue(Stack<NumberLevel> levels, int value) {
        if (levels.isEmpty()) {
            levels.add(new NumberLevel(1L, value));
        } else {
            NumberLevel current = levels.peek();
            if (current.maxUnit == 1L) {
                current.value = current.value * 10L + (long)value;
                if (current.value < 0L) {
                    return false;
                }
            } else {
                levels.add(new NumberLevel(1L, value));
            }
        }
        return true;
    }

    private static boolean pushUnit(Stack<NumberLevel> levels, int unitVal) {
        if (levels.isEmpty()) {
            if (unitVal == 10) {
                levels.add(new NumberLevel(10L, 10L));
                return true;
            }
            return false;
        }
        long sum = 0L;
        while (!levels.isEmpty()) {
            NumberLevel last = levels.peek();
            if (last.maxUnit > (long)unitVal) break;
            levels.pop();
            if ((sum += last.value) >= 0L) continue;
            return false;
        }
        levels.add(new NumberLevel(unitVal, sum * (long)unitVal));
        return true;
    }

    private static long sumValue(Stack<NumberLevel> levels) {
        long sumVal = 0L;
        for (NumberLevel level : levels) {
            if ((sumVal += level.value) >= 0L) continue;
            return sumVal;
        }
        return sumVal;
    }

    private static Number makeValue(int sign, long intVal, long decimalVal, int decimalLen, int ratio) {
        if (decimalVal == 0L && ratio == 1) {
            return (long)sign * intVal;
        }
        double value = intVal;
        if (decimalVal > 0L) {
            value += (double)decimalVal / Math.pow(10.0, decimalLen);
        }
        return (double)sign * value / (double)ratio;
    }

    private String parseSymbol(String text) {
        Map.Entry<String, Currency> entry = CurrencyCache.matchStart(text);
        if (entry != null) {
            this.currency = entry.getValue();
            return text.substring(entry.getKey().length());
        }
        entry = CurrencyCache.matchEnd(text);
        if (entry != null) {
            this.currency = entry.getValue();
            return text.substring(0, text.length() - entry.getKey().length());
        }
        return text;
    }

    private String parseMultiplier(String text) {
        if (text.endsWith("\u6574")) {
            text = text.substring(0, text.length() - 1);
        }
        if (text.endsWith("\u4e07\u5143") || text.endsWith("\u4e07\u5706")) {
            if (NumberParser.unitMoreThan(text.substring(0, text.length() - 2), 10000)) {
                this.multiplier = 1;
                text = text.substring(0, text.length() - 1);
            } else {
                this.multiplier = 10000;
                text = text.substring(0, text.length() - 2);
            }
        } else if (text.endsWith("\u4ebf\u5143") || text.endsWith("\u4ebf\u5706")) {
            this.multiplier = 100000000;
            text = text.substring(0, text.length() - 2);
        } else if (text.endsWith("\u5143") || text.endsWith("\u5706")) {
            this.multiplier = 1;
            text = text.substring(0, text.length() - 1);
        } else if (text.endsWith("\u4e07")) {
            this.multiplier = 10000;
            text = text.substring(0, text.length() - 1);
        }
        return text;
    }

    private static boolean unitMoreThan(String text, int unit) {
        for (int i = 0; i < text.length(); ++i) {
            Integer curUnit = NUMUNITS.get(Character.valueOf(text.charAt(i)));
            if (curUnit == null || curUnit <= unit) continue;
            return true;
        }
        return false;
    }

    static {
        NUMCHARS.put(Character.valueOf('0'), 0);
        NUMCHARS.put(Character.valueOf('1'), 1);
        NUMCHARS.put(Character.valueOf('2'), 2);
        NUMCHARS.put(Character.valueOf('3'), 3);
        NUMCHARS.put(Character.valueOf('4'), 4);
        NUMCHARS.put(Character.valueOf('5'), 5);
        NUMCHARS.put(Character.valueOf('6'), 6);
        NUMCHARS.put(Character.valueOf('7'), 7);
        NUMCHARS.put(Character.valueOf('8'), 8);
        NUMCHARS.put(Character.valueOf('9'), 9);
        NUMCHARS.put(Character.valueOf('\u3007'), 0);
        NUMCHARS.put(Character.valueOf('\u4e00'), 1);
        NUMCHARS.put(Character.valueOf('\u4e8c'), 2);
        NUMCHARS.put(Character.valueOf('\u4e09'), 3);
        NUMCHARS.put(Character.valueOf('\u56db'), 4);
        NUMCHARS.put(Character.valueOf('\u4e94'), 5);
        NUMCHARS.put(Character.valueOf('\u516d'), 6);
        NUMCHARS.put(Character.valueOf('\u4e03'), 7);
        NUMCHARS.put(Character.valueOf('\u516b'), 8);
        NUMCHARS.put(Character.valueOf('\u4e5d'), 9);
        NUMCHARS.put(Character.valueOf('\u96f6'), 0);
        NUMCHARS.put(Character.valueOf('\u58f9'), 1);
        NUMCHARS.put(Character.valueOf('\u8d30'), 2);
        NUMCHARS.put(Character.valueOf('\u53c1'), 3);
        NUMCHARS.put(Character.valueOf('\u8086'), 4);
        NUMCHARS.put(Character.valueOf('\u4f0d'), 5);
        NUMCHARS.put(Character.valueOf('\u9646'), 6);
        NUMCHARS.put(Character.valueOf('\u67d2'), 7);
        NUMCHARS.put(Character.valueOf('\u634c'), 8);
        NUMCHARS.put(Character.valueOf('\u7396'), 9);
        NUMCHARS.put(Character.valueOf('\u4e24'), 2);
        NUMUNITS = new HashMap<Character, Integer>();
        NUMUNITS.put(Character.valueOf('\u5341'), 10);
        NUMUNITS.put(Character.valueOf('\u62fe'), 10);
        NUMUNITS.put(Character.valueOf('\u767e'), 100);
        NUMUNITS.put(Character.valueOf('\u4f70'), 100);
        NUMUNITS.put(Character.valueOf('\u5343'), 1000);
        NUMUNITS.put(Character.valueOf('\u4edf'), 1000);
        NUMUNITS.put(Character.valueOf('\u4e07'), 10000);
        NUMUNITS.put(Character.valueOf('\u4ebf'), 100000000);
    }

    private static final class CurrencyCache {
        static final Map<String, Currency> cache = new TreeMap<String, Currency>();

        private CurrencyCache() {
        }

        static Map.Entry<String, Currency> matchStart(String source) {
            for (Map.Entry<String, Currency> entry : cache.entrySet()) {
                if (!source.startsWith(entry.getKey())) continue;
                return entry;
            }
            return null;
        }

        static Map.Entry<String, Currency> matchEnd(String source) {
            for (Map.Entry<String, Currency> entry : cache.entrySet()) {
                if (!source.endsWith(entry.getKey())) continue;
                return entry;
            }
            return null;
        }

        static {
            for (Locale locale : Locale.getAvailableLocales()) {
                Currency currency;
                if (locale.getCountry() == null || locale.getCountry().length() != 2) continue;
                try {
                    currency = Currency.getInstance(locale);
                }
                catch (IllegalArgumentException e) {
                    continue;
                }
                String symbol = currency.getSymbol(locale);
                if (cache.containsKey(symbol)) continue;
                cache.put(symbol, currency);
            }
        }
    }

    private static final class NumberLevel {
        public long maxUnit;
        public long value;

        public NumberLevel(long unit, long value) {
            this.maxUnit = unit;
            this.value = value;
        }

        public String toString() {
            return this.value + "@" + this.maxUnit;
        }
    }
}

