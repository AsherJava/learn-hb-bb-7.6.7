/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.task.form.util;

import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.task.form.table.dto.DataTableDTO;
import java.time.Instant;

public class TableBeanUtils {
    public static DataTableDTO toDto(DesignDataTable design) {
        if (design == null) {
            return null;
        }
        DataTableDTO dataTableDTO = new DataTableDTO();
        dataTableDTO.setDataSchemeKey(design.getDataSchemeKey());
        dataTableDTO.setDataGroupKey(design.getDataGroupKey());
        dataTableDTO.setDataTableType(design.getDataTableType().getValue());
        dataTableDTO.setKey(design.getKey());
        dataTableDTO.setTitle(design.getTitle());
        dataTableDTO.setOrder(design.getOrder());
        dataTableDTO.setLevel(design.getLevel());
        dataTableDTO.setCode(design.getCode());
        dataTableDTO.setRepeatCode(design.getRepeatCode());
        Instant updateTime = design.getUpdateTime();
        if (updateTime != null) {
            dataTableDTO.setUpdateTime(updateTime.toString());
        }
        dataTableDTO.setBizKeys(design.getBizKeys());
        dataTableDTO.setGatherType(design.getDataTableGatherType().getValue());
        return dataTableDTO;
    }

    public static void copyProperty(DataTableDTO source, DesignDataTable target) {
        if (source == null || target == null) {
            return;
        }
        target.setDataSchemeKey(source.getDataSchemeKey());
        target.setDataGroupKey(source.getDataGroupKey());
        target.setBizKeys(source.getBizKeys());
        if (source.getDataTableType() != null) {
            target.setDataTableType(DataTableType.valueOf((int)source.getDataTableType()));
        }
        if (source.getGatherType() != null) {
            target.setDataTableGatherType(DataTableGatherType.valueOf((int)source.getGatherType()));
        }
        target.setLevel(source.getLevel());
        target.setRepeatCode(source.getRepeatCode());
        target.setRepeatCode(source.getRepeatCode());
        target.setKey(source.getKey());
        target.setCode(source.getCode());
        target.setTitle(source.getTitle());
    }
}

