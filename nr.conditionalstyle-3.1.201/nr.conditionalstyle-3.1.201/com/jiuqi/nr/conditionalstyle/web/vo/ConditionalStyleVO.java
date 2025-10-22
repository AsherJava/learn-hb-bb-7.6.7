/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.conditionalstyle.web.vo;

import java.util.Date;

public class ConditionalStyleVO {
    private String key;
    private String formKey;
    private String linkKey;
    private int posX;
    private int posY;
    private String styleExpression;
    private String fontColor;
    private String foreGroundColor;
    private Boolean bold;
    private Boolean italic;
    private Boolean readOnly;
    private Boolean horizontalBar;
    private Boolean strikeThrough;
    private String order;
    private Date updateTime;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getLinkKey() {
        return this.linkKey;
    }

    public void setLinkKey(String linkKey) {
        this.linkKey = linkKey;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String getStyleExpression() {
        return this.styleExpression;
    }

    public void setStyleExpression(String styleExpression) {
        this.styleExpression = styleExpression;
    }

    public String getFontColor() {
        return this.fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getForeGroundColor() {
        return this.foreGroundColor;
    }

    public void setForeGroundColor(String foreGroundColor) {
        this.foreGroundColor = foreGroundColor;
    }

    public Boolean getBold() {
        return this.bold;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    public Boolean getItalic() {
        return this.italic;
    }

    public void setItalic(Boolean italic) {
        this.italic = italic;
    }

    public Boolean getReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getHorizontalBar() {
        return this.horizontalBar;
    }

    public void setHorizontalBar(Boolean horizontalBar) {
        this.horizontalBar = horizontalBar;
    }

    public Boolean getStrikeThrough() {
        return this.strikeThrough;
    }

    public void setStrikeThrough(Boolean strikeThrough) {
        this.strikeThrough = strikeThrough;
    }
}

