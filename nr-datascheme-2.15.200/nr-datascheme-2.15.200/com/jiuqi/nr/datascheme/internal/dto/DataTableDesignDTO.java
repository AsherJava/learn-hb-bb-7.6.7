/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 */
package com.jiuqi.nr.datascheme.internal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.internal.dto.DataTableDTO;
import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataTableDesignDTO
extends DataTableDTO
implements DesignDataTable {
    private static final long serialVersionUID = 8510850485794139381L;

    @Override
    public DataTableDesignDTO clone() {
        return (DataTableDesignDTO)super.clone();
    }

    @Override
    public String toString() {
        return "DataTableDesignDTO{key='" + this.key + '\'' + ", dataSchemeKey='" + this.dataSchemeKey + '\'' + ", dataGroupKey='" + this.dataGroupKey + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", dataTableType=" + this.dataTableType + ", desc='" + this.desc + '\'' + ", bizKeys=" + Arrays.toString(this.bizKeys) + ", dataTableGatherType=" + this.dataTableGatherType + ", version='" + this.version + '\'' + ", level='" + this.level + '\'' + ", order='" + this.order + '\'' + ", repeatCode=" + this.repeatCode + ", updateTime=" + this.updateTime + '}';
    }

    public static DataTableDesignDTO valueOf(DataTable dataTable) {
        if (dataTable == null) {
            return null;
        }
        DataTableDesignDTO dto = new DataTableDesignDTO();
        DataTableDesignDTO.copyProperties(dataTable, dto);
        return dto;
    }
}

