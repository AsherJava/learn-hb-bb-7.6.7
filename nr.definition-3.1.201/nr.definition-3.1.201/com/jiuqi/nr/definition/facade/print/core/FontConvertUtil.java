/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.Font
 *  com.jiuqi.np.grid.Font
 *  com.jiuqi.xg.draw2d.Font
 *  com.jiuqi.xg.draw2d.FontMetrics
 *  com.jiuqi.xg.draw2d.XG
 *  com.jiuqi.xg.process.IGraphicalText
 */
package com.jiuqi.nr.definition.facade.print.core;

import com.jiuqi.np.grid.Font;
import com.jiuqi.xg.draw2d.FontMetrics;
import com.jiuqi.xg.draw2d.XG;
import com.jiuqi.xg.process.IGraphicalText;

public class FontConvertUtil {
    public static String LINE_BREAK = "\r\n";

    public static com.jiuqi.xg.draw2d.Font getDraw2dFont(com.jiuqi.grid.Font gridFont) {
        com.jiuqi.xg.draw2d.Font font = new com.jiuqi.xg.draw2d.Font();
        font.setBold(gridFont.getBold());
        font.setColor(gridFont.getColor());
        font.setItalic(gridFont.getItalic());
        font.setName(gridFont.getName());
        font.setSize((double)gridFont.getSize());
        font.setStrikeout(gridFont.getStrikeout());
        font.setStyleValue(gridFont.getStylevalue());
        font.setUnderline(gridFont.getUnderline());
        return font;
    }

    public static com.jiuqi.xg.draw2d.Font getDraw2ndFont(Font gridFont) {
        com.jiuqi.xg.draw2d.Font font = new com.jiuqi.xg.draw2d.Font();
        font.setBold(gridFont.getBold());
        font.setColor(gridFont.getColor());
        font.setItalic(gridFont.getItalic());
        font.setName(gridFont.getName());
        font.setSize((double)gridFont.getSize());
        font.setStrikeout(gridFont.getStrikeout());
        font.setStyleValue(gridFont.getStylevalue());
        font.setUnderline(gridFont.getUnderline());
        return font;
    }

    public static com.jiuqi.grid.Font getGridFont(com.jiuqi.xg.draw2d.Font draw2dFont) {
        com.jiuqi.grid.Font font = new com.jiuqi.grid.Font();
        font.setBold(draw2dFont.isBold());
        font.setColor(draw2dFont.getColor());
        font.setItalic(draw2dFont.isItalic());
        font.setName(draw2dFont.getName());
        font.setSize((int)draw2dFont.getSize());
        font.setStrikeout(draw2dFont.hasStrikeout());
        font.setStylevalue(draw2dFont.getStyleValue());
        font.setUnderline(draw2dFont.hasUnderline());
        return font;
    }

    public static double[] getActualSize(com.jiuqi.xg.draw2d.Font font, String content) {
        if (content != null && content.length() == 0) {
            return null;
        }
        double[] actualSize = new double[]{0.0, 0.0};
        com.jiuqi.xg.draw2d.Font cloneFont = font.clone();
        cloneFont.setSize(XG.DEFAULT_LENGTH_UNIT.fromPoint(font.getSize()));
        FontMetrics fm = FontMetrics.getMetrics((com.jiuqi.xg.draw2d.Font)cloneFont);
        String[] lines = content.split(LINE_BREAK);
        actualSize[0] = FontConvertUtil.getMaxWidth(fm, lines) + IGraphicalText.DEFAULT_TEXT_INSETS.getWidth();
        actualSize[1] = fm.getFontHeight() * (double)lines.length;
        return actualSize;
    }

    public static double getActualWidth(com.jiuqi.xg.draw2d.Font font, String content) {
        return FontConvertUtil.getActualSize(font, content)[0];
    }

    private static double getMaxWidth(FontMetrics fm, String[] lines) {
        double maxWidth = fm.getStringWidth(lines[0]);
        for (int i = 1; i < lines.length; ++i) {
            double tempWidth = fm.getStringWidth(lines[i]);
            if (!(tempWidth - maxWidth > 0.1)) continue;
            maxWidth = tempWidth;
        }
        return maxWidth;
    }
}

