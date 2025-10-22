/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.block;

import com.jiuqi.nr.query.common.QueryTitleAlign;

public class QueryTitleDefine {
    private String id;
    private String title;
    private String content;
    private QueryTitleAlign align = QueryTitleAlign.PAGE_TOPLEFT;
    private String blockId;
    private String fontName;
    private int fontSize = 9;
    private String fontColor;
    private byte[] packedProperties = new byte[1];
    private boolean fontUnderLine = false;
    private boolean fontStrikeOut = false;
    private boolean fontBold = false;
    private boolean fontItalic = false;
    private String packedPropertiesStr = null;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public QueryTitleAlign getAlign() {
        return this.align;
    }

    public void setAlign(QueryTitleAlign align) {
        this.align = align;
    }

    public String getBlockId() {
        return this.blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getFontName() {
        return this.fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontColor() {
        return this.fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public byte[] getPackedProperties() {
        return this.packedProperties;
    }

    public void setPackedProperties(byte[] packedProperties) {
        this.packedProperties = packedProperties;
    }

    public boolean isFontUnderLine() {
        return (this.packedProperties[0] & 1) != 0;
    }

    public void setFontUnderLine(boolean fontUnderLine) {
        this.packedProperties[0] = fontUnderLine ? (byte)(this.packedProperties[0] | 1) : (byte)(this.packedProperties[0] & 0xFE);
    }

    public boolean isFontStrikeOut() {
        return (this.packedProperties[0] & 2) != 0;
    }

    public void setFontStrikeOut(boolean fontStrikeOut) {
        this.packedProperties[0] = fontStrikeOut ? (byte)(this.packedProperties[0] | 2) : (byte)(this.packedProperties[0] & 0xFD);
    }

    public boolean isFontBold() {
        return (this.packedProperties[0] & 4) != 0;
    }

    public void setFontBold(boolean fontBold) {
        this.packedProperties[0] = fontBold ? (byte)(this.packedProperties[0] | 4) : (byte)(this.packedProperties[0] & 0xFB);
    }

    public boolean isFontItalic() {
        return (this.packedProperties[0] & 8) != 0;
    }

    public void setFontItalic(boolean fontItalic) {
        this.packedProperties[0] = fontItalic ? (byte)(this.packedProperties[0] | 8) : (byte)(this.packedProperties[0] & 0xF7);
    }

    public String getPackedPropertiesStr() {
        return this.packedPropertiesStr;
    }

    public void setPackedPropertiesStr(String packedPropertiesStr) {
        this.packedPropertiesStr = packedPropertiesStr;
    }
}

