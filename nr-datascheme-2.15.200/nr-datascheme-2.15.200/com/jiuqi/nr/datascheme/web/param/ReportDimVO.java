/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 */
package com.jiuqi.nr.datascheme.web.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataDimDO;
import com.jiuqi.nr.datascheme.web.param.ShowFieldVO;
import java.util.ArrayList;
import java.util.List;

public class ReportDimVO {
    private String dataSchemeKey;
    @JsonIgnore
    private DimensionType dimensionType;
    private String dimKey;
    private Boolean reportDim;
    private String dimAttribute;
    private String defaultDimAttribute;
    private List<ShowFieldVO> showFields;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public DimensionType getDimensionType() {
        return this.dimensionType;
    }

    public void setDimensionType(DimensionType dimensionType) {
        this.dimensionType = dimensionType;
    }

    public String getDimKey() {
        return this.dimKey;
    }

    public void setDimKey(String dimKey) {
        this.dimKey = dimKey;
    }

    public Boolean getReportDim() {
        return this.reportDim;
    }

    public void setReportDim(Boolean reportDim) {
        this.reportDim = reportDim;
    }

    public String getDimAttribute() {
        return this.dimAttribute;
    }

    public void setDimAttribute(String dimAttribute) {
        this.dimAttribute = dimAttribute;
    }

    public String getDefaultDimAttribute() {
        return this.defaultDimAttribute;
    }

    public void setDefaultDimAttribute(String defaultDimAttribute) {
        this.defaultDimAttribute = defaultDimAttribute;
    }

    public List<ShowFieldVO> getShowFields() {
        if (this.showFields == null) {
            this.showFields = new ArrayList<ShowFieldVO>();
        }
        return this.showFields;
    }

    public void setShowFields(List<ShowFieldVO> showFields) {
        this.showFields = showFields;
    }

    public static ReportDimVO convertToVO(DesignDataDimension dim) {
        if (dim == null) {
            return null;
        }
        ReportDimVO t = new ReportDimVO();
        t.setDataSchemeKey(dim.getDataSchemeKey());
        t.setDimensionType(DimensionType.DIMENSION);
        t.setDimKey(dim.getDimKey());
        t.setReportDim(dim.getReportDim());
        t.setDimAttribute(dim.getDimAttribute());
        return t;
    }

    public static DesignDataDimDO convertToDO(ReportDimVO dim) {
        if (dim == null) {
            return null;
        }
        DesignDataDimDO t = new DesignDataDimDO();
        t.setDataSchemeKey(dim.getDataSchemeKey());
        t.setDimensionType(DimensionType.DIMENSION);
        t.setDimKey(dim.getDimKey());
        t.setReportDim(dim.getReportDim());
        t.setDimAttribute(dim.getDimAttribute());
        return t;
    }
}

