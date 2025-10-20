/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.DateUtil
 */
package com.jiuqi.np.grid;

import com.jiuqi.bi.util.DateUtil;
import com.jiuqi.np.grid.CellDataProcessorIntf;
import com.jiuqi.np.grid.CellDataProperty;
import com.jiuqi.np.grid.CellDataPropertyIntf;
import com.jiuqi.np.grid.DateCellPropertyIntf;
import com.jiuqi.np.grid.GridCell;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private CellDataPropertyIntf cellDataProperty;
    private static final SimpleDateFormat cellDataParser = new SimpleDateFormat("yyyy-MM-dd");

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
        Date date = DateUtil.tryParserDateString((String)storeText);
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
        Date date = DateUtil.tryParserDateString((String)editText);
        if (date == null) {
            return editText;
        }
        return cellDataParser.format(date);
    }

    @Override
    public String parseShowText(String showText) {
        return this.parseEditText(showText);
    }

    static {
        cellDataParser.setLenient(true);
    }
}

