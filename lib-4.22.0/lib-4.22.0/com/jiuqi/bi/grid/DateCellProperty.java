/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.grid.CellDataProcessorIntf;
import com.jiuqi.bi.grid.CellDataProperty;
import com.jiuqi.bi.grid.CellDataPropertyIntf;
import com.jiuqi.bi.grid.DateCellPropertyIntf;
import com.jiuqi.bi.grid.GridCell;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateCellProperty
implements DateCellPropertyIntf,
CellDataProcessorIntf,
Serializable {
    private static final long serialVersionUID = -7965914848918154544L;
    public static final int YYYY_MM_DD = 0;
    public static final int YYYY_MM_DD_HH_mm = 1;
    public static final int YYYY_MM_DD_HH_mm_24H = 2;
    public static final int YY_MM_DD = 3;
    public static final int MM_DD_YY = 4;
    public static final int YYYY_MM = 5;
    public static final int MM_DD = 6;
    public static final int YYYY_MM_DD_CHINESE = 7;
    public static final int YYYY_MM_CHINESE = 8;
    public static final int MM_DD_CHINESE = 9;
    public static final int DD_MM_YY_ENGLISH = 10;
    public static final int MM_DD_YY_ENGLISH_FULL = 11;
    public static final int MM_YY_ENGLISH = 12;
    public static final int DD_MM_ENGLISH = 13;
    public static final String[] PATTERNS = new String[]{"yyyy-MM-dd", "yyyy-MM-dd hh:mm a", "yyyy-MM-dd HH:mm", "yy-MM-dd", "MM-dd-yy", "yyyy-MM", "MM-dd", "yyyy\u5e74MM\u6708dd\u65e5", "yyyy\u5e74MM\u6708", "MM\u6708dd\u65e5", "dd-MMM-yy", "dd-MMMMMM-yy", "MMM-yy", "dd-MMM"};
    private static final List<Integer> TIME_PROPS = Arrays.asList(1, 2);
    private CellDataPropertyIntf cellDataProperty;
    private static final String[] TIME_PATTERNS = new String[]{"yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss"};
    private static final String[] DATE_PATTERNS = new String[]{"yyyyMMdd", "yyyy-MM-dd", "yyyy\u5e74MM\u6708dd\u65e5"};

    public DateCellProperty(GridCell cell) {
        this(new CellDataProperty(cell));
    }

    public DateCellProperty(CellDataPropertyIntf dataProperty) {
        this.cellDataProperty = dataProperty;
    }

    @Override
    public short getDateShowType() {
        return this.cellDataProperty.getDataProperty();
    }

    @Override
    public void setDateShowType(short type) {
        this.cellDataProperty.setDataProPerty(type);
    }

    @Override
    public String getEditText(String storeText) {
        return storeText;
    }

    @Override
    public String getShowText(String storeText) {
        if (storeText == null || storeText.length() == 0) {
            return storeText;
        }
        Date date = DateCellProperty.tryParseDate(storeText);
        if (date == null) {
            return storeText;
        }
        short value = this.cellDataProperty.getDataProperty();
        if (value >= 0 && value < PATTERNS.length) {
            SimpleDateFormat dateFormat = value == 10 || value == 13 || value == 11 || value == 12 ? new SimpleDateFormat(PATTERNS[value], Locale.ENGLISH) : new SimpleDateFormat(PATTERNS[value]);
            return dateFormat.format(date);
        }
        return storeText;
    }

    @Override
    public String parseEditText(String editText) {
        if (editText == null || editText.length() == 0) {
            return editText;
        }
        Date date = DateCellProperty.tryParseDate(editText);
        if (date == null) {
            return editText;
        }
        SimpleDateFormat cellDataParser = new SimpleDateFormat("yyyy-MM-dd");
        cellDataParser.setLenient(true);
        return cellDataParser.format(date);
    }

    @Override
    public String parseShowText(String showText) {
        return this.parseEditText(showText);
    }

    public boolean isTimePattern() {
        short pattern = this.cellDataProperty.getDataProperty();
        return TIME_PROPS.contains(pattern);
    }

    public static Date tryParseDate(String text) {
        SimpleDateFormat fmt;
        if (text == null || text.length() == 0) {
            return null;
        }
        boolean separated = text.contains("-");
        if (text.indexOf(58) >= 0 || text.length() >= 14) {
            for (String pattern : TIME_PATTERNS) {
                if (separated && !pattern.contains("-")) continue;
                fmt = new SimpleDateFormat(pattern);
                try {
                    return fmt.parse(text);
                }
                catch (ParseException parseException) {
                    // empty catch block
                }
            }
        }
        for (String pattern : DATE_PATTERNS) {
            if (separated && !pattern.contains("-")) continue;
            fmt = new SimpleDateFormat(pattern);
            try {
                return fmt.parse(text);
            }
            catch (ParseException parseException) {
                // empty catch block
            }
        }
        return null;
    }
}

