/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.grid.CellDataProcessorIntf;
import com.jiuqi.bi.grid.CellDataProperty;
import com.jiuqi.bi.grid.CellDataPropertyIntf;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class BooleanCellProperty
implements CellDataProcessorIntf,
Serializable {
    private static final long serialVersionUID = -484890645359623089L;
    private static final Set<String> TRUE_SET = new HashSet<String>();
    public static final String[] PATTERNS;
    private final CellDataPropertyIntf cellDataProperty;

    public BooleanCellProperty(GridCell cell) {
        this(new CellDataProperty(cell));
    }

    public BooleanCellProperty(CellDataPropertyIntf cellDataProperty) {
        this.cellDataProperty = cellDataProperty;
    }

    @Override
    public String getEditText(String storeText) {
        return storeText;
    }

    @Override
    public String getShowText(String storeText) {
        short index = this.cellDataProperty.getDataProperty();
        if (index < PATTERNS.length) {
            return BooleanCellProperty.format(BooleanCellProperty.text2Bool(storeText), PATTERNS[index]);
        }
        return null;
    }

    @Override
    public String parseEditText(String editText) {
        return editText;
    }

    @Override
    public String parseShowText(String showText) {
        return showText;
    }

    private static String format(Object obj, String showPattern) {
        if (obj == null) {
            return null;
        }
        boolean b = (Boolean)obj;
        if (showPattern == null || showPattern.length() == 0) {
            return b ? "\u662f" : "\u5426";
        }
        int index = showPattern.indexOf(47);
        if (index == -1) {
            return b ? "\u662f" : "\u5426";
        }
        String trueValue = showPattern.substring(0, index);
        String falseValue = showPattern.substring(index + 1);
        return b ? trueValue : falseValue;
    }

    private static Boolean text2Bool(String storeText) {
        return StringUtils.isNotEmpty(storeText) && TRUE_SET.contains(storeText.trim().toUpperCase());
    }

    static {
        for (String p : PATTERNS = new String[]{"True/False", "T/F", "Yes/No", "\u771f/\u5047", "\u662f/\u5426", "\u6b63\u786e/\u9519\u8bef", "\u7537/\u5973", "1/0"}) {
            int index = p.indexOf(47);
            TRUE_SET.add(p.substring(0, index).toUpperCase());
        }
    }
}

