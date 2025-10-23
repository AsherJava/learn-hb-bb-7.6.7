/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.grid.Font
 *  com.jiuqi.nr.definition.facade.print.WordLabelDefine
 *  com.jiuqi.nr.definition.internal.impl.print.WordLabelDefineImpl
 *  com.jiuqi.xlib.utils.StringUtil
 */
package com.jiuqi.nr.print.web.vo;

import com.jiuqi.np.grid.Font;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.internal.impl.print.WordLabelDefineImpl;
import com.jiuqi.xlib.utils.StringUtil;

public class PrintWordVo {
    private String text;
    private String fontName;
    private String fontSize;
    private boolean bold;
    private boolean italic;
    private boolean underline;
    private boolean strikeout;
    private String fontColor;
    private double letterSpace;
    private double lineSpace;
    private String location;
    private int scope;
    private boolean autoWrap;

    public static WordLabelDefine toWordLabelDefine(PrintWordVo vo) {
        String substring;
        WordLabelDefineImpl define = new WordLabelDefineImpl();
        define.setText(vo.getText());
        Font font = new Font();
        font.setName(vo.getFontName());
        font.setSize(Integer.valueOf(vo.getFontSize()).intValue());
        font.setBold(vo.isBold());
        font.setItalic(vo.isItalic());
        font.setUnderline(vo.isUnderline());
        font.setStrikeout(vo.isStrikeout());
        String fontColor = vo.getFontColor();
        if (StringUtil.isNotEmpty((String)fontColor) && (substring = fontColor.substring(1)).length() > 0) {
            font.setColor(Integer.parseInt(substring, 16));
        }
        define.setFont(font);
        define.setLocationCode(vo.getLocation());
        define.setScope(vo.getScope());
        define.setLetterSpace(vo.getLetterSpace());
        define.setLineSpace(vo.getLineSpace());
        define.setAutoWrap(vo.isAutoWrap());
        return define;
    }

    public static PrintWordVo toPrintWordVo(WordLabelDefine define) {
        PrintWordVo vo = new PrintWordVo();
        vo.setText(define.getText());
        vo.setLocation(define.getLocationCode());
        vo.setScope(define.getScope());
        vo.setLetterSpace(define.getLetterSpace());
        vo.setLineSpace(define.getLineSpace());
        Font font = define.getFont();
        vo.setFontName(font.getName());
        vo.setFontSize(String.valueOf(font.getSize()));
        vo.setBold(font.getBold());
        vo.setItalic(font.getItalic());
        vo.setUnderline(font.getUnderline());
        vo.setStrikeout(font.getStrikeout());
        vo.setAutoWrap(define.isAutoWrap());
        vo.setFontColor("#" + Integer.toHexString(font.getColor()));
        return vo;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFontName() {
        return this.fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public boolean isBold() {
        return this.bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return this.italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isUnderline() {
        return this.underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public boolean isStrikeout() {
        return this.strikeout;
    }

    public void setStrikeout(boolean strikeout) {
        this.strikeout = strikeout;
    }

    public String getFontColor() {
        return this.fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public double getLetterSpace() {
        return this.letterSpace;
    }

    public void setLetterSpace(double letterSpace) {
        this.letterSpace = letterSpace;
    }

    public double getLineSpace() {
        return this.lineSpace;
    }

    public void setLineSpace(double lineSpace) {
        this.lineSpace = lineSpace;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getScope() {
        return this.scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public boolean isAutoWrap() {
        return this.autoWrap;
    }

    public void setAutoWrap(boolean autoWrap) {
        this.autoWrap = autoWrap;
    }
}

