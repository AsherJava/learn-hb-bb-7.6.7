/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 */
package com.jiuqi.nr.datascheme.internal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.internal.dto.DataDimDTO;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataDimDesignDTO
extends DataDimDTO
implements DesignDataDimension {
    private static final long serialVersionUID = 4356053136152285016L;

    @Override
    public DataDimDesignDTO clone() {
        return (DataDimDesignDTO)super.clone();
    }

    public DataDimDesignDTO() {
    }

    public DataDimDesignDTO(String dataSchemeKey, String dimKey, DimensionType dimensionType, PeriodType periodType) {
        this.dataSchemeKey = dataSchemeKey;
        this.dimKey = dimKey;
        this.dimensionType = dimensionType;
        this.periodType = periodType;
    }

    @Override
    public String toString() {
        return "DataDimDesignDTO{dataSchemeKey='" + this.dataSchemeKey + '\'' + ", dimensionType=" + this.dimensionType + ", dimKey='" + this.dimKey + '\'' + ", periodType=" + this.periodType + ", periodPattern=" + this.periodPattern + ", version='" + this.version + '\'' + ", level='" + this.level + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public static DataDimDesignDTO valueOf(DataDimension dataDimension) {
        if (dataDimension == null) {
            return null;
        }
        DataDimDesignDTO dto = new DataDimDesignDTO();
        DataDimDesignDTO.copyProperties(dataDimension, dto);
        return dto;
    }
}

