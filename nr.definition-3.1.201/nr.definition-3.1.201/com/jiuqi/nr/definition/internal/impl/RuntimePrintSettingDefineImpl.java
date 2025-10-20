/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.common.PageSize;
import com.jiuqi.nr.definition.facade.PrintSettingDefine;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@DBAnno.DBTable(dbTable="NR_PARAM_PRINTSETTING")
public class RuntimePrintSettingDefineImpl
implements PrintSettingDefine {
    @DBAnno.DBField(dbField="PS_PRINT_SCHEME_KEY", isPk=true)
    private String printSchemeKey;
    @DBAnno.DBField(dbField="PS_FORM_KEY", isPk=true)
    private String formKey;
    @DBAnno.DBField(dbField="PS_PAGE_SIZE", tranWith="transPageSize", dbType=Integer.class, appType=PageSize.class)
    private PageSize pageSize;
    @DBAnno.DBField(dbField="PS_LANDSCAPE", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private Boolean landscape;
    @DBAnno.DBField(dbField="PS_TOP_MARGIN")
    private Double topMargin;
    @DBAnno.DBField(dbField="PS_BOTTOM_MARGIN")
    private Double bottomMargin;
    @DBAnno.DBField(dbField="PS_LEFT_MARGIN")
    private Double leftMargin;
    @DBAnno.DBField(dbField="PS_RIGHT_MARGIN")
    private Double rightMargin;
    @DBAnno.DBField(dbField="PS_SCALE", tranWith="transShort", dbType=Integer.class, appType=Short.class)
    private Short scale;
    @DBAnno.DBField(dbField="PS_FIT_TO_WIDTH", tranWith="transShort", dbType=Integer.class, appType=Short.class)
    private Short fitToWidth;
    @DBAnno.DBField(dbField="PS_FIT_TO_HEIGHT", tranWith="transShort", dbType=Integer.class, appType=Short.class)
    private Short fitToHeight;
    @DBAnno.DBField(dbField="PS_COLUMN_BREAKS", tranWith="transIntArray", dbType=String.class, appType=int[].class)
    private int[] columnBreaks;
    @DBAnno.DBField(dbField="PS_ROW_BREAKS", tranWith="transIntArray", dbType=String.class, appType=int[].class)
    private int[] rowBreaks;
    @DBAnno.DBField(dbField="PS_HORIZONTALLY_CENTER", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private Boolean horizontallyCenter;
    @DBAnno.DBField(dbField="PS_VERTICALLY_CENTER", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private Boolean verticallyCenter;
    @DBAnno.DBField(dbField="PS_LEFT_TO_RIGHT", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private Boolean leftToRight;
    @DBAnno.DBField(dbField="PS_UPDATE_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="PS_LEVEL")
    private String ownerLevelAndId;

    @Override
    public String getPrintSchemeKey() {
        return this.printSchemeKey;
    }

    public void setPrintSchemeKey(String printSchemeKey) {
        this.printSchemeKey = printSchemeKey;
    }

    @Override
    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    @Override
    public PageSize getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(PageSize pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public Boolean getLandscape() {
        return this.landscape;
    }

    public void setLandscape(Boolean landscape) {
        this.landscape = landscape;
    }

    @Override
    public Double getTopMargin() {
        return this.topMargin;
    }

    public void setTopMargin(Double topMargin) {
        this.topMargin = topMargin;
    }

    @Override
    public Double getBottomMargin() {
        return this.bottomMargin;
    }

    public void setBottomMargin(Double bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    @Override
    public Double getLeftMargin() {
        return this.leftMargin;
    }

    public void setLeftMargin(Double leftMargin) {
        this.leftMargin = leftMargin;
    }

    @Override
    public Double getRightMargin() {
        return this.rightMargin;
    }

    public void setRightMargin(Double rightMargin) {
        this.rightMargin = rightMargin;
    }

    @Override
    public Short getScale() {
        return this.scale;
    }

    public void setScale(Short scale) {
        this.scale = scale;
    }

    @Override
    public Short getFitToWidth() {
        return this.fitToWidth;
    }

    public void setFitToWidth(Short fitToWidth) {
        this.fitToWidth = fitToWidth;
    }

    @Override
    public Short getFitToHeight() {
        return this.fitToHeight;
    }

    public void setFitToHeight(Short fitToHeight) {
        this.fitToHeight = fitToHeight;
    }

    @Override
    public int[] getColumnBreaks() {
        return this.columnBreaks;
    }

    public void setColumnBreaks(int[] columnBreaks) {
        this.columnBreaks = columnBreaks;
    }

    @Override
    public int[] getRowBreaks() {
        return this.rowBreaks;
    }

    public void setRowBreaks(int[] rowBreaks) {
        this.rowBreaks = rowBreaks;
    }

    @Override
    public Boolean getHorizontallyCenter() {
        return this.horizontallyCenter;
    }

    public void setHorizontallyCenter(Boolean horizontallyCenter) {
        this.horizontallyCenter = horizontallyCenter;
    }

    @Override
    public Boolean getVerticallyCenter() {
        return this.verticallyCenter;
    }

    public void setVerticallyCenter(Boolean verticallyCenter) {
        this.verticallyCenter = verticallyCenter;
    }

    @Override
    public Boolean getLeftToRight() {
        return this.leftToRight;
    }

    public void setLeftToRight(Boolean leftToRight) {
        this.leftToRight = leftToRight;
    }

    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        RuntimePrintSettingDefineImpl that = (RuntimePrintSettingDefineImpl)o;
        return Objects.equals(this.printSchemeKey, that.printSchemeKey) && Objects.equals(this.formKey, that.formKey);
    }

    public int hashCode() {
        return Objects.hash(this.printSchemeKey, this.formKey);
    }
}

