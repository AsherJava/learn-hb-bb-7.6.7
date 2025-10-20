/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.types.TimeGranularityTypes
 *  com.jiuqi.bi.util.chinese.ChineseDigit
 */
package com.jiuqi.bi.text;

import com.jiuqi.bi.text.DateFormatSymbolsEx;
import com.jiuqi.bi.text.DontCareFieldPosition;
import com.jiuqi.bi.types.TimeGranularityTypes;
import com.jiuqi.bi.util.FiscalCalendar;
import com.jiuqi.bi.util.chinese.ChineseDigit;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeHelper;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormatEx
extends SimpleDateFormat {
    private static final long serialVersionUID = 1L;
    private String pattern;
    private transient char[] compiledPattern;
    private Date defaultCenturyStart;
    private transient int defaultCenturyStartYear;
    private transient char zeroDigit;
    private DateFormatSymbolsEx formatData;
    private transient Calendar lastDay;
    private int timeGranularity = -1;
    private static final int TAG_QUOTE_ASCII_CHAR = 100;
    private static final int TAG_QUOTE_CHARS = 101;
    private static final int EX_FIELD_HALFYEAR = 0;
    private static final int EX_FIELD_QUATOR = 1;
    private static final int EX_FIELD_XUN = 2;
    private static final int millisPerMinute = 60000;
    private static final String GMT = "GMT";
    private static final int[] PATTERN_INDEX_TO_CALENDAR_FIELD = new int[]{0, 1, 2, 5, 11, 11, 12, 13, 14, 7, 6, 8, 3, 4, 9, 10, 10, 15, 15, 17, 1000, 5, 2, 2, 2, 5, 1, 2, 5, 1};
    private static final int[] PATTERN_INDEX_TO_DATE_FORMAT_FIELD = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 17, 1, 9, 3, 2, 2, 2, 3, 1, 2, 3, 1};
    private static final DateFormat.Field[] PATTERN_INDEX_TO_DATE_FORMAT_FIELD_ID = new DateFormat.Field[]{DateFormat.Field.ERA, DateFormat.Field.YEAR, DateFormat.Field.MONTH, DateFormat.Field.DAY_OF_MONTH, DateFormat.Field.HOUR_OF_DAY1, DateFormat.Field.HOUR_OF_DAY0, DateFormat.Field.MINUTE, DateFormat.Field.SECOND, DateFormat.Field.MILLISECOND, DateFormat.Field.DAY_OF_WEEK, DateFormat.Field.DAY_OF_YEAR, DateFormat.Field.DAY_OF_WEEK_IN_MONTH, DateFormat.Field.WEEK_OF_YEAR, DateFormat.Field.WEEK_OF_MONTH, DateFormat.Field.AM_PM, DateFormat.Field.HOUR1, DateFormat.Field.HOUR0, DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE, DateFormat.Field.YEAR, DateFormat.Field.DAY_OF_WEEK, DateFormat.Field.DAY_OF_MONTH, DateFormat.Field.MONTH, DateFormat.Field.MONTH, DateFormat.Field.MONTH, DateFormat.Field.DAY_OF_MONTH, DateFormat.Field.YEAR, DateFormat.Field.MONTH, DateFormat.Field.DAY_OF_MONTH, DateFormat.Field.YEAR};

    public DateFormatEx(String pattern) {
        this(pattern, Locale.getDefault());
    }

    public DateFormatEx(String pattern, int timeGranularity) {
        this(pattern, Locale.getDefault());
        this.timeGranularity = timeGranularity;
    }

    public DateFormatEx(String pattern, Locale local) {
        super("", local);
        this.pattern = pattern;
        this.formatData = new DateFormatSymbolsEx(local);
        this.setDateFormatSymbols(this.formatData);
        this.initialize(local);
    }

    public int getTimeGranularity() {
        return this.timeGranularity;
    }

    public void setTimeGranularity(int timeGranularity) {
        this.timeGranularity = timeGranularity;
    }

    public String format(Calendar date) {
        return this.formatCalendar(date, new StringBuffer(), new FieldPosition(0)).toString();
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos) {
        pos.setBeginIndex(0);
        pos.setEndIndex(0);
        return this.format(date, toAppendTo, new FormatDelegate(pos));
    }

    public StringBuffer formatCalendar(Calendar date, StringBuffer toAppendTo, FieldPosition pos) {
        pos.setBeginIndex(0);
        pos.setEndIndex(0);
        if (date instanceof FiscalCalendar) {
            this.setCalendar((Calendar)date.clone());
        } else {
            this.getCalendar().setTimeInMillis(date.getTimeInMillis());
        }
        return this.internalFormat(toAppendTo, new FormatDelegate(pos));
    }

    private StringBuffer format(Date date, StringBuffer toAppendTo, FieldDelegate delegate) {
        this.calendar.setTime(date);
        return this.internalFormat(toAppendTo, delegate);
    }

    private StringBuffer internalFormat(StringBuffer toAppendTo, FieldDelegate delegate) {
        this.lastDay = null;
        int i = 0;
        block4: while (i < this.compiledPattern.length) {
            int count;
            int tag = this.compiledPattern[i] >>> 8;
            if ((count = this.compiledPattern[i++] & 0xFF) == 255) {
                count = this.compiledPattern[i++] << 16;
                count |= this.compiledPattern[i++];
            }
            switch (tag) {
                case 100: {
                    toAppendTo.append((char)count);
                    continue block4;
                }
                case 101: {
                    toAppendTo.append(this.compiledPattern, i, count);
                    i += count;
                    continue block4;
                }
            }
            this.subFormat(tag, count, delegate, toAppendTo);
        }
        return toAppendTo;
    }

    private void subFormat(int patternCharIndex, int count, FieldDelegate delegate, StringBuffer buffer) {
        int maxIntCount = Integer.MAX_VALUE;
        String current = null;
        int beginOffset = buffer.length();
        int field = PATTERN_INDEX_TO_CALENDAR_FIELD[patternCharIndex];
        int value = patternCharIndex >= 26 && patternCharIndex <= 28 ? this.getLastDay().get(field) : (patternCharIndex == 12 ? this.getWeekOfYear() : (patternCharIndex == 13 ? this.getWeekOfMonth() : this.calendar.get(field)));
        switch (patternCharIndex) {
            case 0: {
                String[] eras = this.formatData.getEras();
                if (value < eras.length) {
                    current = eras[value];
                }
                if (current != null) break;
                current = "";
                break;
            }
            case 1: 
            case 19: {
                if (count != 2) {
                    this.zeroPaddingNumber(value, count, maxIntCount, buffer);
                    break;
                }
                this.zeroPaddingNumber(value, 2, 2, buffer);
                break;
            }
            case 29: {
                this.formatZHYear(value, count > 2, buffer);
                break;
            }
            case 2: 
            case 22: 
            case 27: {
                if (value == -1) {
                    if (count >= 4) {
                        current = this.formatData.getMonth0s()[1];
                        break;
                    }
                    if (count == 3) {
                        current = this.formatData.getMonth0s()[0];
                        break;
                    }
                    this.zeroPaddingNumber(value + 1, count, maxIntCount, buffer);
                    break;
                }
                if (count >= 4) {
                    current = this.formatData.getMonths()[value];
                    break;
                }
                if (count == 3) {
                    current = this.formatData.getShortMonths()[value];
                    break;
                }
                this.zeroPaddingNumber(value + 1, count, maxIntCount, buffer);
                break;
            }
            case 4: {
                if (value == 0) {
                    this.zeroPaddingNumber(this.calendar.getMaximum(11) + 1, count, maxIntCount, buffer);
                    break;
                }
                this.zeroPaddingNumber(value, count, maxIntCount, buffer);
                break;
            }
            case 9: {
                if (count >= 4) {
                    current = this.formatData.getWeekdays()[value];
                    break;
                }
                current = this.formatData.getShortWeekdays()[value];
                break;
            }
            case 14: {
                current = this.formatData.getAmPmStrings()[value];
                break;
            }
            case 15: {
                if (value == 0) {
                    this.zeroPaddingNumber(this.calendar.getLeastMaximum(10) + 1, count, maxIntCount, buffer);
                    break;
                }
                this.zeroPaddingNumber(value, count, maxIntCount, buffer);
                break;
            }
            case 17: {
                int index;
                int zoneIndex = this.formatData.getZoneIndex(this.calendar.getTimeZone().getID());
                if (zoneIndex == -1) {
                    value = this.calendar.get(15) + this.calendar.get(16);
                    buffer.append(DateFormatEx.toCustomID(value));
                    break;
                }
                int n = index = this.calendar.get(16) == 0 ? 1 : 3;
                if (count < 4) {
                    ++index;
                }
                buffer.append(this.formatData.getZoneStrings()[zoneIndex][index]);
                break;
            }
            case 18: {
                value = (this.calendar.get(15) + this.calendar.get(16)) / 60000;
                int width = 4;
                if (value >= 0) {
                    buffer.append('+');
                } else {
                    ++width;
                }
                int num = value / 60 * 100 + value % 60;
                DateFormatEx.sprintf0d(buffer, num, width);
                break;
            }
            case 23: {
                int halfYearIdx = value / 6;
                buffer.append(this.formatData.getHalfYears()[halfYearIdx]);
                break;
            }
            case 24: {
                int quarterIdx = value / 3;
                if (count >= 3) {
                    buffer.append(this.formatData.getShortQuaters()[quarterIdx]);
                    break;
                }
                if (count == 2) {
                    this.zeroPaddingNumber(quarterIdx + 1, 2, 2, buffer);
                    break;
                }
                buffer.append(quarterIdx + 1);
                break;
            }
            case 21: 
            case 25: {
                int dateForX = Math.min(value, 30);
                int xunIdx = (dateForX - 1) / 10;
                buffer.append(this.formatData.getXuns()[xunIdx]);
                break;
            }
            default: {
                this.zeroPaddingNumber(value, count, maxIntCount, buffer);
            }
        }
        if (current != null) {
            buffer.append(current);
        }
        int fieldID = PATTERN_INDEX_TO_DATE_FORMAT_FIELD[patternCharIndex];
        DateFormat.Field f = PATTERN_INDEX_TO_DATE_FORMAT_FIELD_ID[patternCharIndex];
        delegate.formatted(fieldID, f, beginOffset, buffer.length());
    }

    private void formatZHYear(int year, boolean fullLen, StringBuffer buffer) {
        if (fullLen) {
            buffer.append(ChineseDigit.toChineseChar((int)((year %= 10000) / 1000), (boolean)false));
            buffer.append(ChineseDigit.toChineseChar((int)((year %= 1000) / 100), (boolean)false));
        }
        buffer.append(ChineseDigit.toChineseChar((int)((year %= 100) / 10), (boolean)false));
        buffer.append(ChineseDigit.toChineseChar((int)(year %= 10), (boolean)false));
    }

    private final void zeroPaddingNumber(int value, int minDigits, int maxDigits, StringBuffer buffer) {
        try {
            if (this.zeroDigit == '\u0000') {
                this.zeroDigit = ((DecimalFormat)this.numberFormat).getDecimalFormatSymbols().getZeroDigit();
            }
            if (value >= 0) {
                if (value < 100 && minDigits >= 1 && minDigits <= 2) {
                    if (value < 10) {
                        if (minDigits == 2) {
                            buffer.append(this.zeroDigit);
                        }
                        buffer.append((char)(this.zeroDigit + value));
                    } else {
                        buffer.append((char)(this.zeroDigit + value / 10));
                        buffer.append((char)(this.zeroDigit + value % 10));
                    }
                    return;
                }
                if (value >= 1000 && value < 10000) {
                    if (minDigits == 4) {
                        buffer.append((char)(this.zeroDigit + value / 1000));
                        buffer.append((char)(this.zeroDigit + (value %= 1000) / 100));
                        buffer.append((char)(this.zeroDigit + (value %= 100) / 10));
                        buffer.append((char)(this.zeroDigit + value % 10));
                        return;
                    }
                    if (minDigits == 2 && maxDigits == 2) {
                        this.zeroPaddingNumber(value % 100, 2, 2, buffer);
                        return;
                    }
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.numberFormat.setMinimumIntegerDigits(minDigits);
        this.numberFormat.setMaximumIntegerDigits(maxDigits);
        this.numberFormat.format(value, buffer, DontCareFieldPosition.INSTANCE);
    }

    @Override
    public Date parse(String text, ParsePosition pos) {
        Calendar parsedDate = this.internalParse(text, pos);
        return parsedDate == null ? null : parsedDate.getTime();
    }

    private Calendar internalParse(String text, ParsePosition pos) {
        int start;
        int oldStart = start = pos.getIndex();
        int textLength = text.length();
        boolean[] ambiguous = new boolean[]{false, false, false};
        this.calendar.clear();
        this.lastDay = null;
        int i = 0;
        block6: while (i < this.compiledPattern.length) {
            int nextTag;
            int count;
            int tag = this.compiledPattern[i] >>> 8;
            if ((count = this.compiledPattern[i++] & 0xFF) == 255) {
                count = this.compiledPattern[i++] << 16;
                count |= this.compiledPattern[i++];
            }
            switch (tag) {
                case 100: {
                    if (start >= textLength || text.charAt(start) != (char)count) {
                        pos.setIndex(oldStart);
                        pos.setErrorIndex(start);
                        return null;
                    }
                    ++start;
                    continue block6;
                }
                case 101: {
                    while (count-- > 0) {
                        if (start >= textLength || text.charAt(start) != this.compiledPattern[i++]) {
                            pos.setIndex(oldStart);
                            pos.setErrorIndex(start);
                            return null;
                        }
                        ++start;
                    }
                    continue block6;
                }
            }
            boolean obeyCount = false;
            if (i < this.compiledPattern.length && (nextTag = this.compiledPattern[i] >>> 8) != 100 && nextTag != 101) {
                obeyCount = true;
            }
            if ((start = this.subParse(text, start, tag, count, obeyCount, ambiguous, pos)) >= 0) continue;
            pos.setIndex(oldStart);
            return null;
        }
        pos.setIndex(start);
        try {
            if (ambiguous[0] && this.calendar.before(this.defaultCenturyStart)) {
                Calendar parsedCalendar = (Calendar)this.calendar.clone();
                parsedCalendar.set(1, this.defaultCenturyStartYear + 100);
                return parsedCalendar;
            }
            return this.calendar;
        }
        catch (IllegalArgumentException e) {
            pos.setIndex(oldStart);
            pos.setErrorIndex(start);
            return null;
        }
    }

    public Calendar parseCalendar(String source) throws ParseException {
        ParsePosition pos = new ParsePosition(0);
        Calendar result = this.parseCalendar(source, pos);
        if (pos.getIndex() == 0) {
            throw new ParseException("Unparseable date: \"" + source + "\"", pos.getErrorIndex());
        }
        return result;
    }

    public Calendar parseCalendar(String source, ParsePosition pos) {
        Calendar date = this.internalParse(source, pos);
        return date == this.calendar ? (Calendar)date.clone() : date;
    }

    @Override
    public void applyPattern(String pattern) {
        this.compiledPattern = this.compile(pattern);
        this.pattern = pattern;
    }

    @Override
    public Object clone() {
        DateFormatEx other = (DateFormatEx)super.clone();
        other.formatData = (DateFormatSymbolsEx)this.formatData.clone();
        return other;
    }

    private int subParse(String text, int start, int patternCharIndex, int count, boolean obeyCount, boolean[] ambiguous, ParsePosition origPos) {
        Number number = null;
        int value = 0;
        ParsePosition pos = new ParsePosition(0);
        pos.setIndex(start);
        if (patternCharIndex == 19) {
            patternCharIndex = 1;
        }
        int field = PATTERN_INDEX_TO_CALENDAR_FIELD[patternCharIndex];
        while (true) {
            if (pos.getIndex() >= text.length()) {
                origPos.setErrorIndex(start);
                return -1;
            }
            char c = text.charAt(pos.getIndex());
            if (c != ' ' && c != '\t') break;
            pos.setIndex(pos.getIndex() + 1);
        }
        if (patternCharIndex == 4 || patternCharIndex == 15 || (patternCharIndex == 2 || patternCharIndex == 27) && count <= 2 || patternCharIndex == 24 && count <= 2 || patternCharIndex == 1 || patternCharIndex == 19 || patternCharIndex == 26 || patternCharIndex == 13 || patternCharIndex == 12) {
            if (obeyCount) {
                if (start + count > text.length()) {
                    origPos.setErrorIndex(start);
                    return -1;
                }
                number = this.numberFormat.parse(text.substring(0, start + count), pos);
            } else {
                number = this.numberFormat.parse(text, pos);
            }
            if (number == null) {
                origPos.setErrorIndex(pos.getIndex());
                return -1;
            }
            value = number.intValue();
        } else if (patternCharIndex == 29) {
            throw new UnsupportedOperationException("\u672a\u652f\u6301\u4e2d\u6587\u5e74\u4efd\u7684\u89e3\u6790");
        }
        switch (patternCharIndex) {
            case 0: {
                int index = this.matchString(text, start, 0, this.formatData.getEras());
                if (index > 0) {
                    return index;
                }
                origPos.setErrorIndex(pos.getIndex());
                return -1;
            }
            case 1: 
            case 19: {
                if (count <= 2 && pos.getIndex() - start == 2 && Character.isDigit(text.charAt(start)) && Character.isDigit(text.charAt(start + 1))) {
                    int ambiguousTwoDigitYear = this.defaultCenturyStartYear % 100;
                    ambiguous[0] = value == ambiguousTwoDigitYear;
                    value += this.defaultCenturyStartYear / 100 * 100 + (value < ambiguousTwoDigitYear ? 100 : 0);
                }
                this.calendar.set(1, value);
                return pos.getIndex();
            }
            case 26: {
                return pos.getIndex();
            }
            case 2: {
                if (count <= 2) {
                    this.calendar.set(2, value - 1);
                    ambiguous[1] = true;
                    return pos.getIndex();
                }
                int newStart = 0;
                newStart = this.matchString(text, start, 2, this.formatData.getMonths());
                if (newStart > 0) {
                    ambiguous[1] = true;
                    return newStart;
                }
                int index = this.matchString(text, start, 2, this.formatData.getShortMonths());
                if (index > 0) {
                    ambiguous[1] = true;
                    return index;
                }
                origPos.setErrorIndex(pos.getIndex());
                return -1;
            }
            case 27: {
                if (count <= 2) {
                    return pos.getIndex();
                }
                int nextPos = this.ignoreString(text, start, this.formatData.getMonths());
                if (nextPos > 0) {
                    return nextPos;
                }
                origPos.setErrorIndex(pos.getIndex());
                return -1;
            }
            case 4: {
                if (value == this.calendar.getMaximum(11) + 1) {
                    value = 0;
                }
                this.calendar.set(11, value);
                return pos.getIndex();
            }
            case 9: {
                int newStart = 0;
                newStart = this.matchString(text, start, 7, this.formatData.getWeekdays());
                if (newStart > 0) {
                    return newStart;
                }
                int index = this.matchString(text, start, 7, this.formatData.getShortWeekdays());
                if (index > 0) {
                    return index;
                }
                origPos.setErrorIndex(pos.getIndex());
                return -1;
            }
            case 14: {
                int index = this.matchString(text, start, 9, this.formatData.getAmPmStrings());
                if (index > 0) {
                    return index;
                }
                origPos.setErrorIndex(pos.getIndex());
                return -1;
            }
            case 15: {
                if (value == this.calendar.getLeastMaximum(10) + 1) {
                    value = 0;
                }
                this.calendar.set(10, value);
                return pos.getIndex();
            }
            case 17: 
            case 18: {
                int offset;
                int sign = 0;
                if (text.length() - start >= GMT.length() && text.regionMatches(true, start, GMT, 0, GMT.length())) {
                    int num;
                    char c;
                    this.calendar.set(16, 0);
                    pos.setIndex(start + GMT.length());
                    try {
                        if (text.charAt(pos.getIndex()) == '+') {
                            sign = 1;
                        } else if (text.charAt(pos.getIndex()) == '-') {
                            sign = -1;
                        }
                    }
                    catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                        // empty catch block
                    }
                    if (sign == 0) {
                        this.calendar.set(15, 0);
                        return pos.getIndex();
                    }
                    try {
                        pos.setIndex(pos.getIndex() + 1);
                        c = text.charAt(pos.getIndex());
                        if (c < '0' || c > '9') {
                            origPos.setErrorIndex(pos.getIndex());
                            return -1;
                        }
                        num = c - 48;
                        pos.setIndex(pos.getIndex() + 1);
                        if (text.charAt(pos.getIndex()) != ':') {
                            c = text.charAt(pos.getIndex());
                            if (c < '0' || c > '9') {
                                origPos.setErrorIndex(pos.getIndex());
                                return -1;
                            }
                            num *= 10;
                            num += c - 48;
                            pos.setIndex(pos.getIndex() + 1);
                        }
                        if (num > 23) {
                            origPos.setErrorIndex(pos.getIndex() - 1);
                            return -1;
                        }
                        if (text.charAt(pos.getIndex()) != ':') {
                            origPos.setErrorIndex(pos.getIndex());
                            return -1;
                        }
                    }
                    catch (StringIndexOutOfBoundsException e) {
                        origPos.setErrorIndex(pos.getIndex());
                        return -1;
                    }
                    offset = num * 60;
                    try {
                        pos.setIndex(pos.getIndex() + 1);
                        c = text.charAt(pos.getIndex());
                        if (c < '0' || c > '9') {
                            origPos.setErrorIndex(pos.getIndex());
                            return -1;
                        }
                        num = c - 48;
                        pos.setIndex(pos.getIndex() + 1);
                        c = text.charAt(pos.getIndex());
                        if (c < '0' || c > '9') {
                            origPos.setErrorIndex(pos.getIndex());
                            return -1;
                        }
                        num *= 10;
                        if ((num += c - 48) > 59) {
                            origPos.setErrorIndex(pos.getIndex());
                            return -1;
                        }
                    }
                    catch (StringIndexOutOfBoundsException e) {
                        origPos.setErrorIndex(pos.getIndex());
                        return -1;
                    }
                    offset += num;
                } else {
                    int i = this.subParseZoneString(text, pos.getIndex());
                    if (i != 0) {
                        return i;
                    }
                    try {
                        if (text.charAt(pos.getIndex()) == '+') {
                            sign = 1;
                        } else if (text.charAt(pos.getIndex()) == '-') {
                            sign = -1;
                        }
                        if (sign == 0) {
                            origPos.setErrorIndex(pos.getIndex());
                            return -1;
                        }
                        int hours = 0;
                        pos.setIndex(pos.getIndex() + 1);
                        char c = text.charAt(pos.getIndex());
                        if (c < '0' || c > '9') {
                            origPos.setErrorIndex(pos.getIndex());
                            return -1;
                        }
                        hours = c - 48;
                        pos.setIndex(pos.getIndex() + 1);
                        c = text.charAt(pos.getIndex());
                        if (c < '0' || c > '9') {
                            origPos.setErrorIndex(pos.getIndex());
                            return -1;
                        }
                        hours *= 10;
                        if ((hours += c - 48) > 23) {
                            origPos.setErrorIndex(pos.getIndex());
                            return -1;
                        }
                        int minutes = 0;
                        pos.setIndex(pos.getIndex() + 1);
                        c = text.charAt(pos.getIndex());
                        if (c < '0' || c > '9') {
                            origPos.setErrorIndex(pos.getIndex());
                            return -1;
                        }
                        minutes = c - 48;
                        pos.setIndex(pos.getIndex() + 1);
                        c = text.charAt(pos.getIndex());
                        if (c < '0' || c > '9') {
                            origPos.setErrorIndex(pos.getIndex());
                            return -1;
                        }
                        minutes *= 10;
                        if ((minutes += c - 48) > 59) {
                            origPos.setErrorIndex(pos.getIndex());
                            return -1;
                        }
                        offset = hours * 60 + minutes;
                    }
                    catch (StringIndexOutOfBoundsException e) {
                        origPos.setErrorIndex(pos.getIndex());
                        return -1;
                    }
                }
                if (sign != 0) {
                    this.calendar.set(15, offset *= 60000 * sign);
                    this.calendar.set(16, 0);
                    pos.setIndex(pos.getIndex() + 1);
                    return pos.getIndex();
                }
                origPos.setErrorIndex(pos.getIndex());
                return -1;
            }
            case 12: {
                this.setWeekOfYear(value);
                return pos.getIndex();
            }
            case 13: {
                this.setWeekOfMonth(value);
                return pos.getIndex();
            }
            case 23: {
                int newStartForB = 0;
                newStartForB = this.matchString(text, start, 0, this.formatData.getHalfYears(), true, ambiguous);
                if (newStartForB > 0) {
                    return newStartForB;
                }
                origPos.setErrorIndex(pos.getIndex());
                return -1;
            }
            case 24: {
                if (count >= 3) {
                    int newStartForQ = 0;
                    newStartForQ = this.matchString(text, start, 1, this.formatData.getShortQuaters(), true, ambiguous);
                    if (newStartForQ > 0) {
                        return newStartForQ;
                    }
                    origPos.setErrorIndex(pos.getIndex());
                    return -1;
                }
                this.calendar.set(2, 3 * (value - 1));
                return pos.getIndex();
            }
            case 21: 
            case 25: {
                int newStartForX = 0;
                newStartForX = this.matchString(text, start, 2, this.formatData.getXuns(), true, ambiguous);
                if (newStartForX > 0) {
                    return newStartForX;
                }
                origPos.setErrorIndex(pos.getIndex());
                return -1;
            }
        }
        if (obeyCount) {
            if (start + count > text.length()) {
                origPos.setErrorIndex(pos.getIndex());
                return -1;
            }
            number = this.numberFormat.parse(text.substring(0, start + count), pos);
        } else {
            number = this.numberFormat.parse(text, pos);
        }
        if (number != null) {
            if (patternCharIndex != 28) {
                this.calendar.set(field, number.intValue());
                if (field == 5) {
                    ambiguous[2] = true;
                }
            }
            return pos.getIndex();
        }
        origPos.setErrorIndex(pos.getIndex());
        return -1;
    }

    private int matchString(String text, int start, int field, String[] data) {
        int i = 0;
        int count = data.length;
        if (field == 7) {
            i = 1;
        }
        int bestMatchLength = 0;
        int bestMatch = -1;
        while (i < count) {
            int length = data[i].length();
            if (length > bestMatchLength && text.regionMatches(true, start, data[i], 0, length)) {
                bestMatch = i;
                bestMatchLength = length;
            }
            ++i;
        }
        if (bestMatch >= 0) {
            this.calendar.set(field, bestMatch);
            return start + bestMatchLength;
        }
        return -start;
    }

    private int ignoreString(String text, int start, String[] data) {
        int bestMatchLength = 0;
        int bestMatch = -1;
        for (int i = 0; i < data.length; ++i) {
            int length;
            int n = length = data[i] == null ? 0 : data[i].length();
            if (length <= bestMatchLength || !text.regionMatches(true, start, data[i], 0, length)) continue;
            bestMatch = i;
            bestMatchLength = length;
        }
        return bestMatch >= 0 ? start + bestMatchLength : -start;
    }

    private int matchString(String text, int start, int field, String[] data, boolean localPattern, boolean[] ambiguous) {
        if (!localPattern) {
            return this.matchString(text, start, field, data);
        }
        int count = data.length;
        int bestMatchLength = 0;
        int bestMatch = -1;
        for (int i = 0; i < count; ++i) {
            int length = data[i].length();
            if (length <= bestMatchLength || !text.regionMatches(true, start, data[i], 0, length)) continue;
            bestMatch = i;
            bestMatchLength = length;
        }
        if (bestMatch >= 0) {
            int tmpField = field;
            int tmpBestMatch = bestMatch;
            if (tmpField == 0) {
                tmpField = 2;
                if (!ambiguous[1]) {
                    this.calendar.set(tmpField, 6 * tmpBestMatch);
                }
            } else if (tmpField == 1) {
                tmpField = 2;
                if (!ambiguous[1]) {
                    this.calendar.set(tmpField, 3 * tmpBestMatch);
                }
            } else if (tmpField == 2) {
                tmpField = 5;
                if (!ambiguous[2]) {
                    this.calendar.set(tmpField, 10 * tmpBestMatch + 1);
                }
            }
            return start + bestMatchLength;
        }
        return -start;
    }

    private int subParseZoneString(String text, int start) {
        boolean useSameName = false;
        TimeZone currentTimeZone = this.getTimeZone();
        int zoneIndex = this.formatData.getZoneIndex(currentTimeZone.getID());
        Object tz = null;
        int j = 0;
        int i = 0;
        if (zoneIndex != -1 && (j = this.matchZoneString(text, start, zoneIndex)) > 0) {
            if (j <= 2) {
                useSameName = this.matchDSTString(text, start, zoneIndex, j);
            }
            tz = TimeZone.getTimeZone(this.formatData.getZoneStrings()[zoneIndex][0]);
            i = zoneIndex;
        }
        if (tz == null && (zoneIndex = this.formatData.getZoneIndex(TimeZone.getDefault().getID())) != -1 && (j = this.matchZoneString(text, start, zoneIndex)) > 0) {
            if (j <= 2) {
                useSameName = this.matchDSTString(text, start, zoneIndex, j);
            }
            tz = TimeZone.getTimeZone(this.formatData.getZoneStrings()[zoneIndex][0]);
            i = zoneIndex;
        }
        if (tz == null) {
            for (i = 0; i < this.formatData.getZoneStrings().length; ++i) {
                j = this.matchZoneString(text, start, i);
                if (j <= 0) continue;
                if (j <= 2) {
                    useSameName = this.matchDSTString(text, start, i, j);
                }
                tz = TimeZone.getTimeZone(this.formatData.getZoneStrings()[i][0]);
                break;
            }
        }
        if (tz != null) {
            if (!tz.equals(currentTimeZone)) {
                this.setTimeZone((TimeZone)tz);
            }
            if (!useSameName) {
                this.calendar.set(15, ((TimeZone)tz).getRawOffset());
                this.calendar.set(16, j >= 3 ? ((TimeZone)tz).getDSTSavings() : 0);
            }
            return start + this.formatData.getZoneStrings()[i][j].length();
        }
        return 0;
    }

    private int matchZoneString(String text, int start, int zoneIndex) {
        for (int j = 1; j <= 4; ++j) {
            String zoneName = this.formatData.getZoneStrings()[zoneIndex][j];
            if (!text.regionMatches(true, start, zoneName, 0, zoneName.length())) continue;
            return j;
        }
        return -1;
    }

    private boolean matchDSTString(String text, int start, int zoneIndex, int standardIndex) {
        int index = standardIndex + 2;
        String zoneName = this.formatData.getZoneStrings()[zoneIndex][index];
        return text.regionMatches(true, start, zoneName, 0, zoneName.length());
    }

    private void initialize(Locale loc) {
        this.defaultCenturyStart = this.get2DigitYearStart();
        this.defaultCenturyStartYear = this.calendar.get(1);
        this.compiledPattern = this.compile(this.pattern);
    }

    private char[] compile(String pattern) {
        int length = pattern.length();
        boolean inQuote = false;
        StringBuilder compiledPattern = new StringBuilder(length * 2);
        StringBuilder tmpBuffer = new StringBuilder(0);
        int count = 0;
        int lastTag = -1;
        for (int i = 0; i < length; ++i) {
            char c = pattern.charAt(i);
            if (c == '\'') {
                if (i + 1 < length && (c = pattern.charAt(i + 1)) == '\'') {
                    ++i;
                    if (count != 0) {
                        DateFormatEx.encode(lastTag, count, compiledPattern);
                        lastTag = -1;
                        count = 0;
                    }
                    if (inQuote) {
                        tmpBuffer.append(c);
                        continue;
                    }
                    compiledPattern.append((char)(0x6400 | c));
                    continue;
                }
                if (!inQuote) {
                    if (count != 0) {
                        DateFormatEx.encode(lastTag, count, compiledPattern);
                        lastTag = -1;
                        count = 0;
                    }
                    tmpBuffer.setLength(0);
                    inQuote = true;
                    continue;
                }
                int len = tmpBuffer.length();
                if (len == 1) {
                    char ch = tmpBuffer.charAt(0);
                    if (ch < '\u0080') {
                        compiledPattern.append((char)(0x6400 | ch));
                    } else {
                        compiledPattern.append('\u6501');
                        compiledPattern.append(ch);
                    }
                } else {
                    DateFormatEx.encode(101, len, compiledPattern);
                    compiledPattern.append((CharSequence)tmpBuffer);
                }
                inQuote = false;
                continue;
            }
            if (inQuote) {
                tmpBuffer.append(c);
                continue;
            }
            if (!(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')) {
                char d;
                int j;
                if (count != 0) {
                    DateFormatEx.encode(lastTag, count, compiledPattern);
                    lastTag = -1;
                    count = 0;
                }
                if (c < '\u0080') {
                    compiledPattern.append((char)(0x6400 | c));
                    continue;
                }
                for (j = i + 1; !(j >= length || (d = pattern.charAt(j)) == '\'' || d >= 'a' && d <= 'z' || d >= 'A' && d <= 'Z'); ++j) {
                }
                compiledPattern.append((char)(0x6500 | j - i));
                while (i < j) {
                    compiledPattern.append(pattern.charAt(i));
                    ++i;
                }
                --i;
                continue;
            }
            int tag = "GyMdkHmsSEDFwWahKzZYuXLBQTrNen".indexOf(c);
            if (tag == -1) {
                throw new IllegalArgumentException("Illegal pattern character '" + c + "'");
            }
            if (lastTag == -1 || lastTag == tag) {
                lastTag = tag;
                ++count;
                continue;
            }
            DateFormatEx.encode(lastTag, count, compiledPattern);
            lastTag = tag;
            count = 1;
        }
        if (inQuote) {
            throw new IllegalArgumentException("Unterminated quote");
        }
        if (count != 0) {
            DateFormatEx.encode(lastTag, count, compiledPattern);
        }
        int len = compiledPattern.length();
        char[] r = new char[len];
        compiledPattern.getChars(0, len, r, 0);
        return r;
    }

    private static final void encode(int tag, int length, StringBuilder buffer) {
        if (length < 255) {
            buffer.append((char)(tag << 8 | length));
        } else {
            buffer.append((char)(tag << 8 | 0xFF));
            buffer.append((char)(length >>> 16));
            buffer.append((char)(length & 0xFFFF));
        }
    }

    private static String toCustomID(int gmtOffset) {
        char sign;
        int offset = gmtOffset / 60000;
        if (offset >= 0) {
            sign = '+';
        } else {
            sign = '-';
            offset = -offset;
        }
        int hh = offset / 60;
        int mm = offset % 60;
        char[] buf = new char[]{'G', 'M', 'T', sign, '0', '0', ':', '0', '0'};
        if (hh >= 10) {
            buf[4] = (char)(buf[4] + hh / 10);
        }
        buf[5] = (char)(buf[5] + hh % 10);
        if (mm != 0) {
            buf[7] = (char)(buf[7] + mm / 10);
            buf[8] = (char)(buf[8] + mm % 10);
        }
        return new String(buf);
    }

    public static final StringBuffer sprintf0d(StringBuffer sb, int value, int width) {
        int i;
        long d = value;
        if (d < 0L) {
            sb.append('-');
            d = -d;
            --width;
        }
        int n = 10;
        for (i = 2; i < width; ++i) {
            n *= 10;
        }
        for (i = 1; i < width && d < (long)n; ++i) {
            sb.append('0');
            n /= 10;
        }
        sb.append(d);
        return sb;
    }

    private int getWeekOfYear() {
        Calendar firstDay = (Calendar)this.calendar.clone();
        firstDay.setFirstDayOfWeek(this.calendar.get(7));
        firstDay.set(6, 1);
        TimeHelper.alignWeekForward(firstDay);
        int distance = this.calendar.get(6) - firstDay.get(6);
        return distance / 7 + 1;
    }

    private int getWeekOfMonth() {
        Calendar firstDay = (Calendar)this.calendar.clone();
        firstDay.setFirstDayOfWeek(this.calendar.get(7));
        firstDay.set(5, 1);
        TimeHelper.alignWeekForward(firstDay);
        int distance = this.calendar.get(6) - firstDay.get(6);
        return distance / 7 + 1;
    }

    private void setWeekOfYear(int week) {
        this.calendar.set(6, 1);
        TimeHelper.alignWeekForward(this.calendar);
        if (week > 1) {
            this.calendar.add(6, (week - 1) * 7);
        }
    }

    private void setWeekOfMonth(int week) {
        this.calendar.set(6, 1);
        TimeHelper.alignWeekForward(this.calendar);
        if (week > 1) {
            this.calendar.add(5, (week - 1) * 7);
        }
    }

    private Calendar getLastDay() {
        if (this.lastDay != null) {
            return this.lastDay;
        }
        if (this.timeGranularity < 0) {
            this.timeGranularity = this.sumiseTimeGranularity();
        }
        try {
            this.lastDay = TimeHelper.getLastDay(this.calendar, this.timeGranularity);
        }
        catch (TimeCalcException e) {
            throw new IllegalArgumentException("\u683c\u5f0f\u5316\u65e5\u671f\u9519\u8bef\uff0c\u65e0\u6cd5\u8ba1\u7b97\u5468\u671f\u7684\u6700\u540e\u4e00\u5929", e);
        }
        return this.lastDay;
    }

    private int sumiseTimeGranularity() {
        int[] lastRange = new int[]{-1, -1};
        List<Integer> granularites = this.scanGranularities(lastRange);
        if (granularites.isEmpty() || lastRange[0] < 0 || lastRange[1] <= 0) {
            throw new IllegalArgumentException("\u683c\u5f0f\u65e5\u671f\u9519\u8bef\uff0c\u65e0\u6cd5\u5206\u6790\u5468\u671f\u6a21\u5f0f\u7684\u7c92\u5ea6");
        }
        int[] retainGranus = this.retainGranularities(granularites, lastRange[0], lastRange[1]);
        if (retainGranus.length == 0) {
            throw new IllegalArgumentException("\u683c\u5f0f\u65e5\u671f\u9519\u8bef\uff0c\u65e0\u6cd5\u5206\u6790\u5468\u671f\u6a21\u5f0f\u7684\u7c92\u5ea6");
        }
        return this.getGranularity(retainGranus);
    }

    private List<Integer> scanGranularities(int[] lastRange) {
        ArrayList<Integer> granularites = new ArrayList<Integer>();
        int i = 0;
        while (i < this.compiledPattern.length) {
            int count;
            int tag = this.compiledPattern[i] >>> 8;
            if ((count = this.compiledPattern[i++] & 0xFF) == 255) {
                count = this.compiledPattern[i++] << 16;
                count |= this.compiledPattern[i++];
            }
            if (tag == 101) {
                i += count;
            }
            switch (tag) {
                case 1: 
                case 19: {
                    granularites.add(0);
                    break;
                }
                case 23: {
                    granularites.add(1);
                    break;
                }
                case 24: {
                    granularites.add(2);
                    break;
                }
                case 2: {
                    granularites.add(3);
                    break;
                }
                case 25: {
                    granularites.add(4);
                    break;
                }
                case 12: 
                case 13: {
                    granularites.add(6);
                    break;
                }
                case 3: 
                case 9: 
                case 10: 
                case 11: {
                    granularites.add(5);
                    break;
                }
                case 26: {
                    if (lastRange[0] < 0) {
                        lastRange[0] = granularites.size();
                    }
                    granularites.add(0);
                    lastRange[1] = granularites.size();
                    break;
                }
                case 27: {
                    if (lastRange[0] < 0) {
                        lastRange[0] = granularites.size();
                    }
                    granularites.add(3);
                    lastRange[1] = granularites.size();
                    break;
                }
                case 28: {
                    if (lastRange[0] < 0) {
                        lastRange[0] = granularites.size();
                    }
                    granularites.add(5);
                    lastRange[1] = granularites.size();
                }
            }
        }
        return granularites;
    }

    private int[] retainGranularities(List<Integer> granularites, int start, int end) {
        int i;
        int len = end - start;
        int pos = -1;
        for (i = 0; i <= start - len; ++i) {
            if (!DateFormatEx.matchRange(granularites, i, start, len)) continue;
            pos = i;
            break;
        }
        if (pos < 0) {
            for (i = end; i <= granularites.size() - len; ++i) {
                if (!DateFormatEx.matchRange(granularites, i, start, len)) continue;
                pos = i;
                break;
            }
        }
        if (pos == -1) {
            return new int[0];
        }
        int[] granus = new int[granularites.size() - len * 2];
        int index = 0;
        for (int i2 = 0; i2 < granularites.size(); ++i2) {
            if (i2 >= pos && i2 < pos + len || i2 >= start && i2 < end) continue;
            granus[index++] = granularites.get(i2);
        }
        return granus;
    }

    private int getGranularity(int[] granularities) {
        int minDays = Integer.MAX_VALUE;
        int minGranu = -1;
        for (int granu : granularities) {
            int days = TimeGranularityTypes.daysOf((int)granu);
            if (days >= minDays) continue;
            minGranu = granu;
            minDays = days;
        }
        return minGranu;
    }

    private static boolean matchRange(List<Integer> list, int p1, int p2, int len) {
        for (int i = 0; i < len; ++i) {
            if (list.get(p1 + i).equals(list.get(p2 + i))) continue;
            return false;
        }
        return true;
    }

    private final class FormatDelegate
    extends FieldDelegate {
        private boolean encounteredField;

        private FormatDelegate(FieldPosition pos) {
            super(pos);
        }

        @Override
        void formatted(Format.Field attr, int start, int end) {
            if (this.pos == null) {
                return;
            }
            boolean matchesField = false;
            Format.Field attribute = this.pos.getFieldAttribute();
            if (attribute != null) {
                matchesField = attribute.equals(attr);
            }
            if (!this.encounteredField && matchesField) {
                this.pos.setBeginIndex(start);
                this.pos.setEndIndex(end);
                this.encounteredField = start != end;
            }
        }

        @Override
        void formatted(int fieldID, Format.Field attr, int start, int end) {
            if (this.pos == null) {
                return;
            }
            boolean matchesField = false;
            Format.Field attribute = this.pos.getFieldAttribute();
            if (attribute != null) {
                matchesField = attribute.equals(attribute);
            } else {
                boolean bl = matchesField = fieldID == this.pos.getField();
            }
            if (!this.encounteredField && matchesField) {
                this.pos.setBeginIndex(start);
                this.pos.setEndIndex(end);
                this.encounteredField = start != end;
            }
        }
    }

    private abstract class FieldDelegate {
        FieldPosition pos;

        public FieldDelegate(FieldPosition pos) {
            this.pos = pos;
        }

        abstract void formatted(Format.Field var1, int var2, int var3);

        abstract void formatted(int var1, Format.Field var2, int var3, int var4);
    }
}

