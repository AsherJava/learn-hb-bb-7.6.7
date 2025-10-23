/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.task.form.dto.AbstractState;
import java.util.Date;

public class ConditionStyleDTO
extends AbstractState {
    private String key;
    private String formKey;
    private int posX;
    private int posY;
    private String styleExpression;
    private String fontColor;
    private String foreGroundColor;
    private Boolean bold = false;
    private Boolean italic = false;
    private Boolean readOnly = false;
    private String order;
    private Date updateTime;
    private String linkKey;
    private Boolean horizontalBar = false;
    private Boolean strikeThrough = false;

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
        this.bold = bold != null && bold != false;
    }

    public Boolean getItalic() {
        return this.italic;
    }

    public void setItalic(Boolean italic) {
        this.italic = italic != null && italic != false;
    }

    public Boolean getReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly != null && readOnly != false;
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

    public String getLinkKey() {
        return this.linkKey;
    }

    public void setLinkKey(String linkKey) {
        this.linkKey = linkKey;
    }

    public Boolean getHorizontalBar() {
        return this.horizontalBar;
    }

    public void setHorizontalBar(Boolean horizontalBar) {
        this.horizontalBar = horizontalBar != null && horizontalBar != false;
    }

    public Boolean getStrikeThrough() {
        return this.strikeThrough;
    }

    public void setStrikeThrough(Boolean strikeThrough) {
        this.strikeThrough = strikeThrough != null && strikeThrough != false;
    }

    public boolean equals(Object obj) {
        if (obj instanceof ConditionStyleDTO) {
            ConditionStyleDTO cs = new ConditionStyleDTO();
            return this.fontColor.equals(cs.getFontColor()) && this.foreGroundColor.equals(cs.getForeGroundColor()) && this.italic.equals(cs.getItalic()) && this.bold.equals(cs.getBold()) && this.readOnly.equals(cs.getReadOnly());
        }
        return super.equals(obj);
    }

    public int hashCode() {
        if (this.fontColor == null) {
            this.fontColor = "";
        }
        if (this.foreGroundColor == null) {
            this.foreGroundColor = "";
        }
        return this.fontColor.hashCode() + this.foreGroundColor.hashCode() + this.italic.hashCode() * 3 + this.readOnly.hashCode() * 5 + this.bold.hashCode() * 7;
    }
}

