/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle
 */
package com.jiuqi.nr.param.transfer.definition.dto.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ConditionalStyleDTO {
    private String key;
    private String formKey;
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
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private String linkKey;

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

    public String getLinkKey() {
        return this.linkKey;
    }

    public void setLinkKey(String linkKey) {
        this.linkKey = linkKey;
    }

    public void setHorizontalBar(Boolean horizontalBar) {
        this.horizontalBar = horizontalBar;
    }

    public Boolean getHorizontalBar() {
        return this.horizontalBar;
    }

    public void setStrikeThrough(Boolean strikeThrough) {
        this.strikeThrough = strikeThrough;
    }

    public Boolean getStrikeThrough() {
        return this.strikeThrough;
    }

    public static ConditionalStyleDTO valueOf(DesignConditionalStyle csDefine) {
        ConditionalStyleDTO csDTO = new ConditionalStyleDTO();
        csDTO.setKey(csDefine.getKey());
        csDTO.setFormKey(csDefine.getFormKey());
        csDTO.setPosX(csDefine.getPosX());
        csDTO.setPosY(csDefine.getPosY());
        csDTO.setStyleExpression(csDefine.getStyleExpression());
        csDTO.setFontColor(csDefine.getFontColor());
        csDTO.setForeGroundColor(csDefine.getForeGroundColor());
        csDTO.setItalic(csDefine.getItalic());
        csDTO.setBold(csDefine.getBold());
        csDTO.setReadOnly(csDefine.getReadOnly());
        csDTO.setOrder(csDefine.getOrder());
        csDTO.setUpdateTime(csDefine.getUpdateTime());
        csDTO.setLinkKey(csDefine.getLinkKey());
        csDTO.setHorizontalBar(csDefine.getHorizontalBar());
        csDTO.setStrikeThrough(csDefine.getStrikeThrough());
        return csDTO;
    }

    public void value2Define(DesignConditionalStyle csparam) {
        csparam.setKey(this.getKey());
        csparam.setFormKey(this.getFormKey());
        csparam.setPosX(this.getPosX());
        csparam.setPosY(this.getPosY());
        csparam.setStyleExpression(this.getStyleExpression());
        csparam.setFontColor(this.getFontColor());
        csparam.setForeGroundColor(this.getForeGroundColor());
        csparam.setItalic(this.getItalic());
        csparam.setBold(this.getBold());
        csparam.setReadOnly(this.getReadOnly());
        csparam.setOrder(this.getOrder());
        csparam.setUpdateTime(this.getUpdateTime());
        csparam.setLinkKey(this.getLinkKey());
        csparam.setHorizontalBar(this.getHorizontalBar());
        csparam.setStrikeThrough(this.getStrikeThrough());
    }
}

