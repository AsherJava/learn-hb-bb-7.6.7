/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.api.type.PeriodPattern
 *  javax.validation.constraints.Size
 */
package com.jiuqi.nr.datascheme.internal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.api.type.PeriodPattern;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonDeserializer;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonSerializer;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataDimDTO
implements DataDimension {
    private static final long serialVersionUID = -2448456885479673899L;
    @Size(max=40, message="{text.size}")
    protected @Size(max=40, message="{text.size}") String dataSchemeKey;
    protected DimensionType dimensionType;
    @Size(max=40, message="{text.size}")
    protected @Size(max=40, message="{text.size}") String dimKey;
    protected PeriodType periodType;
    protected PeriodPattern periodPattern;
    @Size(max=20, message="{text.size}")
    protected @Size(max=20, message="{text.size}") String version;
    @Size(max=5, message="{text.size}")
    protected @Size(max=5, message="{text.size}") String level;
    @Size(max=10, message="{text.size}")
    protected @Size(max=10, message="{text.size}") String order;
    @JsonDeserialize(using=InstantJsonDeserializer.class)
    @JsonSerialize(using=InstantJsonSerializer.class)
    protected Instant updateTime;
    protected Boolean reportDim;
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

    public Boolean getReportDim() {
        return this.reportDim;
    }

    public String getDimAttribute() {
        return this.dimAttribute;
    }

    public void setDimAttribute(String dimAttribute) {
        this.dimAttribute = dimAttribute;
    }

    public void setReportDim(Boolean reportDim) {
        this.reportDim = reportDim;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
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

    public DataDimDTO clone() {
        try {
            return (DataDimDTO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u6df1\u5ea6\u590d\u5236\u6570\u636e\u51fa\u9519", e);
        }
    }

    public String toString() {
        return "DataDimDTO{dataSchemeKey='" + this.dataSchemeKey + '\'' + ", dimensionType=" + this.dimensionType + ", dimKey='" + this.dimKey + '\'' + ", periodType=" + this.periodType + ", periodPattern=" + this.periodPattern + ", version='" + this.version + '\'' + ", level='" + this.level + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        DataDimDTO that = (DataDimDTO)o;
        if (!Objects.equals(this.dataSchemeKey, that.dataSchemeKey)) {
            return false;
        }
        if (this.dimensionType != that.dimensionType) {
            return false;
        }
        return Objects.equals(this.dimKey, that.dimKey);
    }

    public int hashCode() {
        int result = this.dataSchemeKey != null ? this.dataSchemeKey.hashCode() : 0;
        result = 31 * result + (this.dimensionType != null ? this.dimensionType.hashCode() : 0);
        result = 31 * result + (this.dimKey != null ? this.dimKey.hashCode() : 0);
        return result;
    }

    public static DataDimDTO valueOf(DataDimension dataDimension) {
        if (dataDimension == null) {
            return null;
        }
        DataDimDTO dto = new DataDimDTO();
        DataDimDTO.copyProperties(dataDimension, dto);
        return dto;
    }

    public static void copyProperties(DataDimension dim, DataDimDTO t) {
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

