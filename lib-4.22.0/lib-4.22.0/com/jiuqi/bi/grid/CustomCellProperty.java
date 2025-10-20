/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.grid.CellDataProcessorIntf;
import com.jiuqi.bi.grid.CustomCellPropertyIntf;
import com.jiuqi.bi.grid.DateCellProperty;
import com.jiuqi.bi.text.DecimalFormat;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomCellProperty
implements CustomCellPropertyIntf,
CellDataProcessorIntf,
Serializable {
    private static final long serialVersionUID = 1L;
    private String showPattern;
    private int type = 0;

    public CustomCellProperty(String showPattern) {
        this.showPattern = showPattern;
        if (StringUtils.isEmpty(showPattern)) {
            return;
        }
        if (CustomCellProperty.isDateFormat(showPattern)) {
            this.type = 5;
        } else if (CustomCellProperty.isNumberFormat(showPattern)) {
            this.type = 2;
        }
    }

    @Override
    public String getEditText(String storeText) {
        return storeText;
    }

    @Override
    public String getShowText(String storeText) {
        if (StringUtils.isEmpty(this.showPattern)) {
            return storeText;
        }
        if (StringUtils.isEmpty(storeText)) {
            return "";
        }
        try {
            if (this.type == 2) {
                String tempText = storeText;
                tempText = StringUtils.eliminate(tempText, ',');
                DecimalFormat df = new DecimalFormat(this.showPattern);
                BigDecimal value = new BigDecimal(tempText);
                return df.format(value);
            }
            if (this.type == 5) {
                SimpleDateFormat sdf = new SimpleDateFormat(this.showPattern);
                Date date = DateCellProperty.tryParseDate(storeText);
                return sdf.format(date);
            }
            return storeText;
        }
        catch (Exception e) {
            return storeText;
        }
    }

    @Override
    public String parseEditText(String editText) {
        return editText;
    }

    @Override
    public String parseShowText(String showText) {
        return showText;
    }

    @Override
    public String getShowPattern() {
        return this.showPattern;
    }

    @Override
    public void setShowPattern(String showPattern) {
        this.showPattern = showPattern;
    }

    public static boolean isDateFormat(String format) {
        if (StringUtils.isEmpty(format)) {
            return false;
        }
        String pattern = format.toLowerCase();
        return pattern.contains("yy") || pattern.contains("m") || pattern.contains("d");
    }

    public static boolean isNumberFormat(String format) {
        if (StringUtils.isEmpty(format)) {
            return false;
        }
        return format.contains("#") || format.contains("0");
    }
}

