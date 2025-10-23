/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nr.definition.common.PageSize
 *  com.jiuqi.nr.definition.facade.DesignPrintSettingDefine
 *  com.jiuqi.nvwa.cellbook.json.CellBookDeserialize
 *  com.jiuqi.nvwa.cellbook.json.CellBookSerialize
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 */
package com.jiuqi.nr.print.web.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.definition.common.PageSize;
import com.jiuqi.nr.definition.facade.DesignPrintSettingDefine;
import com.jiuqi.nvwa.cellbook.json.CellBookDeserialize;
import com.jiuqi.nvwa.cellbook.json.CellBookSerialize;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import java.util.Date;
import java.util.Map;

public class ExcelPrintSettingVO {
    private String printSchemeKey;
    private String formKey;
    private Date updateTime;
    private String ownerLevelAndId;
    private PageSize pageSize = PageSize.A4_PAPER;
    private boolean landscape = false;
    private double topMargin = 2.54;
    private double bottomMargin = 2.54;
    private double leftMargin = 1.91;
    private double rightMargin = 1.91;
    private boolean horizontallyCenter = false;
    private boolean verticallyCenter = false;
    private boolean leftToRight = false;
    private Short scale;
    private Short fitToWidth;
    private Short fitToHeight;
    private int[] columnBreaks = null;
    private int[] rowBreaks = null;
    private Map<Integer, String> pageSizes;
    @JsonSerialize(using=CellBookSerialize.class)
    @JsonDeserialize(using=CellBookDeserialize.class)
    private CellBook cellBook;

    public ExcelPrintSettingVO() {
    }

    public ExcelPrintSettingVO(DesignPrintSettingDefine define) {
        this.setPrintSchemeKey(define.getPrintSchemeKey());
        this.setFormKey(define.getFormKey());
        this.setUpdateTime(define.getUpdateTime());
        this.setOwnerLevelAndId(define.getOwnerLevelAndId());
        this.setScale(define.getScale());
        this.setFitToWidth(define.getFitToWidth());
        this.setFitToHeight(define.getFitToHeight());
        this.setColumnBreaks(define.getColumnBreaks());
        this.setRowBreaks(define.getRowBreaks());
        if (null != define.getPageSize()) {
            this.setPageSize(define.getPageSize());
        }
        if (null != define.getLandscape()) {
            this.setLandscape(define.getLandscape());
        }
        if (null != define.getTopMargin()) {
            this.setTopMargin(define.getTopMargin());
        }
        if (null != define.getBottomMargin()) {
            this.setBottomMargin(define.getBottomMargin());
        }
        if (null != define.getLeftMargin()) {
            this.setLeftMargin(define.getLeftMargin());
        }
        if (null != define.getRightMargin()) {
            this.setRightMargin(define.getRightMargin());
        }
        if (null != define.getHorizontallyCenter()) {
            this.setHorizontallyCenter(define.getHorizontallyCenter());
        }
        if (null != define.getVerticallyCenter()) {
            this.setVerticallyCenter(define.getVerticallyCenter());
        }
        if (null != define.getLeftToRight()) {
            this.setLeftToRight(define.getLeftToRight());
        }
    }

    public void value2Define(DesignPrintSettingDefine define) {
        define.setPrintSchemeKey(this.getPrintSchemeKey());
        define.setFormKey(this.getFormKey());
        define.setUpdateTime(this.getUpdateTime());
        define.setOwnerLevelAndId(this.getOwnerLevelAndId());
        define.setPageSize(this.getPageSize());
        define.setLandscape(Boolean.valueOf(this.isLandscape()));
        define.setTopMargin(Double.valueOf(this.getTopMargin()));
        define.setBottomMargin(Double.valueOf(this.getBottomMargin()));
        define.setLeftMargin(Double.valueOf(this.getLeftMargin()));
        define.setRightMargin(Double.valueOf(this.getRightMargin()));
        define.setFitToWidth(this.getFitToWidth());
        define.setFitToHeight(this.getFitToHeight());
        define.setColumnBreaks(this.getColumnBreaks());
        define.setRowBreaks(this.getRowBreaks());
        define.setHorizontallyCenter(Boolean.valueOf(this.isHorizontallyCenter()));
        define.setVerticallyCenter(Boolean.valueOf(this.isVerticallyCenter()));
        define.setLeftToRight(Boolean.valueOf(this.isLeftToRight()));
    }

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

    public PageSize getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(PageSize pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isLandscape() {
        return this.landscape;
    }

    public void setLandscape(boolean landscape) {
        this.landscape = landscape;
    }

    public double getTopMargin() {
        return this.topMargin;
    }

    public void setTopMargin(double topMargin) {
        this.topMargin = topMargin;
    }

    public double getBottomMargin() {
        return this.bottomMargin;
    }

    public void setBottomMargin(double bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    public double getLeftMargin() {
        return this.leftMargin;
    }

    public void setLeftMargin(double leftMargin) {
        this.leftMargin = leftMargin;
    }

    public double getRightMargin() {
        return this.rightMargin;
    }

    public void setRightMargin(double rightMargin) {
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

    public boolean isHorizontallyCenter() {
        return this.horizontallyCenter;
    }

    public void setHorizontallyCenter(boolean horizontallyCenter) {
        this.horizontallyCenter = horizontallyCenter;
    }

    public boolean isVerticallyCenter() {
        return this.verticallyCenter;
    }

    public void setVerticallyCenter(boolean verticallyCenter) {
        this.verticallyCenter = verticallyCenter;
    }

    public boolean isLeftToRight() {
        return this.leftToRight;
    }

    public void setLeftToRight(boolean leftToRight) {
        this.leftToRight = leftToRight;
    }

    public Map<Integer, String> getPageSizes() {
        return this.pageSizes;
    }

    public void setPageSizes(Map<Integer, String> pageSizes) {
        this.pageSizes = pageSizes;
    }

    public CellBook getCellBook() {
        return this.cellBook;
    }

    public void setCellBook(CellBook cellBook) {
        this.cellBook = cellBook;
    }
}

