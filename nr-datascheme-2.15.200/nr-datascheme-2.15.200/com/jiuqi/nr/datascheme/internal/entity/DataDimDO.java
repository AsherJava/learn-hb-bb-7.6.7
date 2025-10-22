/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.api.type.PeriodPattern
 */
package com.jiuqi.nr.datascheme.internal.entity;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.api.type.PeriodPattern;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@DBAnno.DBTable(dbTable="NR_DATASCHEME_DIM")
public class DataDimDO
implements DataDimension {
    private static final long serialVersionUID = -2448456885479673899L;
    @DBAnno.DBField(dbField="DD_DS_KEY", isPk=true)
    protected String dataSchemeKey;
    @DBAnno.DBField(dbField="DD_TYPE", tranWith="transDimensionType", appType=DimensionType.class, dbType=Integer.class)
    protected DimensionType dimensionType;
    @DBAnno.DBField(dbField="DD_DIM_KEY", isPk=true)
    protected String dimKey;
    @DBAnno.DBField(dbField="DD_PERIOD_TYPE", tranWith="transPeriodType", appType=PeriodType.class, dbType=Integer.class)
    protected PeriodType periodType;
    @DBAnno.DBField(dbField="DD_PERIOD_PATTERN", tranWith="transPeriodPattern", appType=PeriodPattern.class, dbType=Integer.class)
    protected PeriodPattern periodPattern;
    @DBAnno.DBField(dbField="DD_VERSION")
    protected String version;
    @DBAnno.DBField(dbField="DD_LEVEL")
    protected String level;
    @DBAnno.DBField(dbField="DD_ORDER", isOrder=true)
    protected String order;
    @DBAnno.DBField(dbField="DD_UPDATE_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, autoDate=true)
    protected Instant updateTime;
    @DBAnno.DBField(dbField="DD_REPORT_DIM", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected Boolean reportDim;
    @DBAnno.DBField(dbField="DD_DIM_ATTRIBUTE")
    protected String dimAttribute;

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

    public PeriodType getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public PeriodPattern getPeriodPattern() {
        return this.periodPattern;
    }

    public void setPeriodPattern(PeriodPattern periodPattern) {
        this.periodPattern = periodPattern;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getReportDim() {
        return this.reportDim != null && this.reportDim != false;
    }

    public String getDimAttribute() {
        return this.dimAttribute;
    }

    public void setReportDim(Boolean reportDim) {
        this.reportDim = reportDim;
        if (Objects.isNull(this.reportDim)) {
            this.reportDim = Boolean.FALSE;
        }
    }

    public void setDimAttribute(String dimAttribute) {
        this.dimAttribute = dimAttribute;
    }

    public int compareTo(Ordered o) {
        if (o == null || o.getOrder() == null) {
            return 1;
        }
        if (this.order == null) {
            return -1;
        }
        return this.order.compareTo(o.getOrder());
    }

    public DataDimDO clone() {
        try {
            return (DataDimDO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u6df1\u5ea6\u590d\u5236\u6570\u636e\u51fa\u9519");
        }
    }

    public String toString() {
        return "DataDimDO{dataSchemeKey='" + this.dataSchemeKey + '\'' + ", dimensionType=" + this.dimensionType + ", dimKey='" + this.dimKey + '\'' + ", periodType=" + this.periodType + ", periodPattern=" + this.periodPattern + ", version='" + this.version + '\'' + ", level='" + this.level + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        DataDimDO dataDimDO = (DataDimDO)o;
        if (!Objects.equals(this.dataSchemeKey, dataDimDO.dataSchemeKey)) {
            return false;
        }
        if (!Objects.equals(this.dimKey, dataDimDO.dimKey)) {
            return false;
        }
        return this.dimensionType == dataDimDO.dimensionType;
    }

    public int hashCode() {
        int result = this.dataSchemeKey != null ? this.dataSchemeKey.hashCode() : 0;
        result = 31 * result + (this.dimKey != null ? this.dimKey.hashCode() : 0);
        result = 31 * result + (this.dimensionType != null ? this.dimensionType.hashCode() : 0);
        return result;
    }

    public static DataDimDO valueOf(DataDimension dim) {
        if (dim == null) {
            return null;
        }
        DataDimDO t = new DataDimDO();
        DataDimDO.copyProperties(dim, t);
        return t;
    }

    public static void copyProperties(DataDimension dim, DataDimDO t) {
        t.setDataSchemeKey(dim.getDataSchemeKey());
        t.setUpdateTime(dim.getUpdateTime());
        t.setLevel(dim.getLevel());
        t.setVersion(dim.getVersion());
        t.setOrder(dim.getOrder());
        t.setDimensionType(dim.getDimensionType());
        t.setDimKey(dim.getDimKey());
        t.setPeriodType(dim.getPeriodType());
        t.setPeriodPattern(dim.getPeriodPattern());
        t.setReportDim(dim.getReportDim());
        t.setDimAttribute(dim.getDimAttribute());
    }
}

