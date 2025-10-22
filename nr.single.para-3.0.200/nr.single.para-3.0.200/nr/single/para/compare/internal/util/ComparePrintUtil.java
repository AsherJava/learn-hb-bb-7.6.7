/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.grid.Font
 *  com.jiuqi.nr.definition.facade.print.WordLabelDefine
 *  com.jiuqi.nr.single.core.para.parser.print.FontDataClass
 *  com.jiuqi.nr.single.core.para.parser.print.GridPrintTextData
 */
package nr.single.para.compare.internal.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.grid.Font;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.single.core.para.parser.print.FontDataClass;
import com.jiuqi.nr.single.core.para.parser.print.GridPrintTextData;

public class ComparePrintUtil {
    public static void tranToWordLableDefine(GridPrintTextData textData, WordLabelDefine define) {
        int idx;
        define.setElement(0);
        String text = ComparePrintUtil.transformationText(textData.getText());
        String comment = textData.getComments();
        if ("\u91d1\u989d\u5355\u4f4d".equalsIgnoreCase(comment) && StringUtils.isNotEmpty((String)text) && (idx = text.indexOf("\uff1a")) >= 0) {
            String newText;
            text = newText = text.substring(0, idx) + "\uff1a{#RPTMONEYUNIT}";
        }
        short printOption = textData.getPrintOption();
        short option = textData.getPosition();
        switch (option) {
            case 0: {
                define.setHorizontalPos(0);
                define.setVerticalPos(0);
                define.setElement(1);
                break;
            }
            case 1: {
                define.setHorizontalPos(2);
                define.setVerticalPos(0);
                define.setElement(1);
                break;
            }
            case 2: {
                define.setHorizontalPos(0);
                define.setVerticalPos(1);
                define.setElement(1);
                break;
            }
            case 3: {
                define.setHorizontalPos(2);
                define.setVerticalPos(1);
                define.setElement(1);
                break;
            }
            case 4: {
                define.setHorizontalPos(1);
                define.setVerticalPos(0);
                define.setElement(1);
                break;
            }
            case 5: {
                define.setHorizontalPos(1);
                define.setVerticalPos(1);
                define.setElement(1);
                break;
            }
            case 6: {
                define.setHorizontalPos(1);
                define.setVerticalPos(0);
                define.setElement(0);
                break;
            }
            case 7: {
                define.setHorizontalPos(0);
                define.setVerticalPos(0);
                define.setElement(0);
                break;
            }
            case 8: {
                define.setHorizontalPos(2);
                define.setVerticalPos(0);
                define.setElement(0);
                break;
            }
            case 9: {
                define.setHorizontalPos(1);
                define.setVerticalPos(1);
                define.setElement(0);
                break;
            }
            case 10: {
                define.setHorizontalPos(0);
                define.setVerticalPos(1);
                define.setElement(0);
                break;
            }
            case 11: {
                define.setHorizontalPos(2);
                define.setVerticalPos(1);
                define.setElement(0);
                break;
            }
        }
        FontDataClass font = textData.getFontData();
        define.setText(text);
        define.setFont(ComparePrintUtil.convertFont(font));
    }

    private static String transformationText(String textStr) {
        if (StringUtils.isEmpty((String)textStr)) {
            return "";
        }
        textStr = textStr.replace("%d", "{#PageNumber}");
        String ret = new String(textStr);
        int leftIdx = ret.indexOf("<D>");
        int rightIdx = ret.indexOf("</D>");
        String fieldStr = "";
        if (leftIdx >= 0 && leftIdx + 3 < rightIdx) {
            fieldStr = textStr.substring(leftIdx + 3, rightIdx);
            ret = textStr.substring(0, leftIdx) + "{" + fieldStr + "}" + textStr.substring(rightIdx + 4, textStr.length());
        }
        return ret;
    }

    private static String convertTextStr(String textStr) {
        if (StringUtils.isEmpty((String)textStr)) {
            return textStr;
        }
        String ret = new String(textStr);
        int leftIdx = ret.indexOf("<D>");
        int rightIdx = ret.indexOf("</D>");
        String fieldStr = "";
        if (leftIdx >= 0 && leftIdx + 3 < rightIdx) {
            fieldStr = textStr.substring(leftIdx + 3, rightIdx);
            ret = "{" + fieldStr + "}";
        }
        return ret;
    }

    private static Font convertFont(FontDataClass font) {
        Font retFont = new Font();
        retFont.setStylevalue((int)font.getStyles());
        retFont.setSize(new Double(font.getHeight()).intValue());
        retFont.setName(font.getName());
        return retFont;
    }
}

