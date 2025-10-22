/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.grid.Font
 *  com.jiuqi.xg.draw2d.Font
 *  com.jiuqi.xg.process.ITemplateElement
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.np.grid.Font;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.facade.print.core.ReportLabelTemplateObject;
import com.jiuqi.nr.definition.internal.impl.print.WordLabelDefineImpl;
import com.jiuqi.xg.process.ITemplateElement;

public class ExportLabelUtils {
    public static WordLabelDefine convertWorldLabel(ReportLabelTemplateObject labelTemplateObject) {
        WordLabelDefineImpl wordLabelDefine = new WordLabelDefineImpl();
        wordLabelDefine.setLocationCode(labelTemplateObject.getLocation());
        Font gridFont = new Font();
        com.jiuqi.xg.draw2d.Font draw2dFont = labelTemplateObject.getFont();
        gridFont.setSize((int)draw2dFont.getSize());
        gridFont.setName(draw2dFont.getName());
        gridFont.setBold(draw2dFont.isBold());
        gridFont.setColor(draw2dFont.getColor());
        gridFont.setItalic(draw2dFont.isItalic());
        gridFont.setStrikeout(gridFont.getStrikeout());
        gridFont.setStylevalue(gridFont.getStylevalue());
        gridFont.setUnderline(gridFont.getUnderline());
        wordLabelDefine.setFont(gridFont);
        wordLabelDefine.setText(labelTemplateObject.getContent());
        wordLabelDefine.setScope(labelTemplateObject.getDrawScope());
        wordLabelDefine.setAutoWrap(labelTemplateObject.isAutoWrap());
        wordLabelDefine.setLetterSpace(labelTemplateObject.getLetterSpace());
        wordLabelDefine.setLineSpace(labelTemplateObject.getLineSpace());
        return wordLabelDefine;
    }

    public static String fillNum(double o1, double o2) {
        String s1 = String.format("%.2f", o1);
        String s2 = String.format("%.2f", o2);
        s1 = s1.replace(".", "");
        s2 = s2.replace(".", "");
        if (s1.length() >= s2.length()) {
            return s1;
        }
        return ExportLabelUtils.padLeft(s1, s2.length());
    }

    private static String padLeft(String origin, int length) {
        StringBuilder originBuilder = new StringBuilder(origin);
        while (originBuilder.length() < length) {
            originBuilder.insert(0, "0");
        }
        origin = originBuilder.toString();
        return origin;
    }

    public static double getDoubleOrder(ITemplateElement<?> t1, ITemplateElement<?> t2) {
        if (t1 != null && t2 != null) {
            return Double.parseDouble(ExportLabelUtils.getCode(((ReportLabelTemplateObject)t1).getLocation()) + ExportLabelUtils.fillNum(t1.getY(), t2.getY()) + ExportLabelUtils.fillNum(t1.getX(), t2.getX()));
        }
        return 0.0;
    }

    public static int getCode(String location) {
        if (location == null || location.length() < 3) {
            return 13;
        }
        switch (location) {
            case "000": {
                return 1;
            }
            case "001": {
                return 2;
            }
            case "002": {
                return 3;
            }
            case "100": {
                return 4;
            }
            case "101": {
                return 5;
            }
            case "102": {
                return 6;
            }
            case "110": {
                return 7;
            }
            case "111": {
                return 8;
            }
            case "112": {
                return 9;
            }
            case "010": {
                return 10;
            }
            case "011": {
                return 11;
            }
            case "012": {
                return 12;
            }
        }
        return 13;
    }
}

