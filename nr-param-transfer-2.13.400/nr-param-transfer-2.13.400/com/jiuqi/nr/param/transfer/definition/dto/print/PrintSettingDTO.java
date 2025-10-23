/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.definition.common.PageSize
 *  com.jiuqi.nr.definition.facade.DesignPrintSettingDefine
 *  com.jiuqi.nr.definition.facade.PrintSettingDefine
 */
package com.jiuqi.nr.param.transfer.definition.dto.print;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.definition.common.PageSize;
import com.jiuqi.nr.definition.facade.DesignPrintSettingDefine;
import com.jiuqi.nr.definition.facade.PrintSettingDefine;
import com.jiuqi.nr.param.transfer.definition.dto.BaseDTO;
import java.io.IOException;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PrintSettingDTO
extends BaseDTO {
    protected String printSchemeKey;
    protected String formKey;
    protected PageSize pageSize;
    protected Boolean landscape;
    protected Double topMargin;
    protected Double bottomMargin;
    protected Double leftMargin;
    protected Double rightMargin;
    protected Short scale;
    protected Short fitToWidth;
    protected Short fitToHeight;
    protected int[] columnBreaks;
    protected int[] rowBreaks;
    protected Boolean horizontallyCenter;
    protected Boolean verticallyCenter;
    protected Boolean leftToRight;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    protected Date updateTime;
    private String ownerLevelAndId;

    public String getPrintSchemeKey() {
        return this.printSchemeKey;
    }

    public void setPrintSchemeKey(String printSchemeKey) {
        this.printSchemeKey = printSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public PageSize getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(PageSize pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getLandscape() {
        return this.landscape;
    }

    public void setLandscape(Boolean landscape) {
        this.landscape = landscape;
    }

    public Double getTopMargin() {
        return this.topMargin;
    }

    public void setTopMargin(Double topMargin) {
        this.topMargin = topMargin;
    }

    public Double getBottomMargin() {
        return this.bottomMargin;
    }

    public void setBottomMargin(Double bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    public Double getLeftMargin() {
        return this.leftMargin;
    }

    public void setLeftMargin(Double leftMargin) {
        this.leftMargin = leftMargin;
    }

    public Double getRightMargin() {
        return this.rightMargin;
    }

    public void setRightMargin(Double rightMargin) {
        this.rightMargin = rightMargin;
    }

    public Short getScale() {
        return this.scale;
    }

    public void setScale(Short scale) {
        this.scale = scale;
    }

    public Short getFitToWidth() {
        return this.fitToWidth;
    }

    public void setFitToWidth(Short fitToWidth) {
        this.fitToWidth = fitToWidth;
    }

    public Short getFitToHeight() {
        return this.fitToHeight;
    }

    public void setFitToHeight(Short fitToHeight) {
        this.fitToHeight = fitToHeight;
    }

    public int[] getColumnBreaks() {
        return this.columnBreaks;
    }

    public void setColumnBreaks(int[] columnBreaks) {
        this.columnBreaks = columnBreaks;
    }

    public int[] getRowBreaks() {
        return this.rowBreaks;
    }

    public void setRowBreaks(int[] rowBreaks) {
        this.rowBreaks = rowBreaks;
    }

    public Boolean getHorizontallyCenter() {
        return this.horizontallyCenter;
    }

    public void setHorizontallyCenter(Boolean horizontallyCenter) {
        this.horizontallyCenter = horizontallyCenter;
    }

    public Boolean getVerticallyCenter() {
        return this.verticallyCenter;
    }

    public void setVerticallyCenter(Boolean verticallyCenter) {
        this.verticallyCenter = verticallyCenter;
    }

    public Boolean getLeftToRight() {
        return this.leftToRight;
    }

    public void setLeftToRight(Boolean leftToRight) {
        this.leftToRight = leftToRight;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public void value2Define(DesignPrintSettingDefine define) {
        if (define == null) {
            return;
        }
        define.setPrintSchemeKey(this.getPrintSchemeKey());
        define.setFormKey(this.getFormKey());
        define.setUpdateTime(this.getUpdateTime());
        define.setPageSize(this.getPageSize());
        define.setLandscape(this.getLandscape());
        define.setTopMargin(this.getTopMargin());
        define.setBottomMargin(this.getBottomMargin());
        define.setLeftMargin(this.getLeftMargin());
        define.setRightMargin(this.getRightMargin());
        define.setScale(this.getScale());
        define.setFitToHeight(this.getFitToHeight());
        define.setFitToWidth(this.getFitToWidth());
        define.setColumnBreaks(this.getColumnBreaks());
        define.setRowBreaks(this.getRowBreaks());
        define.setHorizontallyCenter(this.getHorizontallyCenter());
        define.setVerticallyCenter(this.getVerticallyCenter());
        define.setLeftToRight(this.getLeftToRight());
        define.setOwnerLevelAndId(this.getOwnerLevelAndId());
    }

    public static PrintSettingDTO valueOf(PrintSettingDefine define) {
        if (define == null) {
            return null;
        }
        PrintSettingDTO dto = new PrintSettingDTO();
        dto.setPrintSchemeKey(define.getPrintSchemeKey());
        dto.setFormKey(define.getFormKey());
        dto.setUpdateTime(define.getUpdateTime());
        dto.setPageSize(define.getPageSize());
        dto.setLandscape(define.getLandscape());
        dto.setTopMargin(define.getTopMargin());
        dto.setBottomMargin(define.getBottomMargin());
        dto.setLeftMargin(define.getLeftMargin());
        dto.setRightMargin(define.getRightMargin());
        dto.setScale(define.getScale());
        dto.setFitToHeight(define.getFitToHeight());
        dto.setFitToWidth(define.getFitToWidth());
        dto.setColumnBreaks(define.getColumnBreaks());
        dto.setRowBreaks(define.getRowBreaks());
        dto.setHorizontallyCenter(define.getHorizontallyCenter());
        dto.setVerticallyCenter(define.getVerticallyCenter());
        dto.setLeftToRight(define.getLeftToRight());
        dto.setOwnerLevelAndId(define.getOwnerLevelAndId());
        return dto;
    }

    public static PrintSettingDTO valueOf(byte[] bytes, ObjectMapper objectMapper) throws IOException {
        return (PrintSettingDTO)objectMapper.readValue(bytes, PrintSettingDTO.class);
    }
}

