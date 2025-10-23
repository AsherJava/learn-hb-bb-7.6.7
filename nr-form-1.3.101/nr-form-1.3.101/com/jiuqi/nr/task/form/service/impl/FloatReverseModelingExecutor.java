/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 */
package com.jiuqi.nr.task.form.service.impl;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeParam;
import com.jiuqi.nr.task.form.controller.dto.ReverseModeRegionDTO;
import com.jiuqi.nr.task.form.field.dto.DataFieldDTO;
import com.jiuqi.nr.task.form.field.dto.DataFieldSettingDTO;
import com.jiuqi.nr.task.form.field.dto.ReverseModeFieldDTO;
import com.jiuqi.nr.task.form.service.impl.AbstractReverseModelingExecutor;
import com.jiuqi.nr.task.form.service.reversemodel.IReverseModelDataProvider;
import com.jiuqi.nr.task.form.table.dto.DataTableDTO;
import com.jiuqi.nr.task.form.util.ReverseModeUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FloatReverseModelingExecutor
extends AbstractReverseModelingExecutor {
    @Override
    public boolean available(DataRegionKind regionKind) {
        return DataRegionKind.DATA_REGION_COLUMN_LIST == regionKind || DataRegionKind.DATA_REGION_ROW_LIST == regionKind || DataRegionKind.DATA_REGION_COLUMN_AND_ROW_LIST == regionKind;
    }

    @Override
    public DataTableDTO createTable(ReverseModeParam param, ReverseModeRegionDTO region) {
        String prefix;
        IReverseModelDataProvider reverseModelDataProvider;
        List<String> stringList = param.getTableCodes();
        String dataSchemePrefix = param.getDataSchemePrefix();
        String formCode = param.getFormCode();
        HashSet<String> tableTitles = new HashSet<String>();
        HashSet<String> tableCodes = new HashSet<String>();
        if (stringList != null) {
            tableCodes.addAll(stringList);
        }
        List allDataTable = (reverseModelDataProvider = param.getReverseModelDataProvider()) != null ? reverseModelDataProvider.getAllDataTable() : ((IDesignDataSchemeService)BeanUtil.getBean(IDesignDataSchemeService.class)).getAllDataTable();
        for (DesignDataTable dataTable : allDataTable) {
            tableTitles.add(dataTable.getTitle());
            tableCodes.add(dataTable.getCode());
        }
        DataTableDTO dataTableDTO = new DataTableDTO();
        dataTableDTO.setKey(UUIDUtils.getKey());
        dataTableDTO.setDataTableType(DataTableType.DETAIL.getValue());
        if (region.getRegionKind().intValue() == DataRegionKind.DATA_REGION_ROW_LIST.getValue()) {
            prefix = this.formatSchemePrefix(dataSchemePrefix) + formCode + "_" + "F";
            dataTableDTO.setCode(this.judgeTableCode(tableCodes, prefix + region.getRegionTop()));
        } else if (region.getRegionKind().intValue() == DataRegionKind.DATA_REGION_COLUMN_LIST.getValue()) {
            prefix = this.formatSchemePrefix(dataSchemePrefix) + formCode + "_" + "F";
            dataTableDTO.setCode(this.judgeTableCode(tableCodes, prefix + region.getRegionLeft()));
        } else if (region.getRegionKind().intValue() == DataRegionKind.DATA_REGION_COLUMN_AND_ROW_LIST.getValue()) {
            prefix = this.formatSchemePrefix(dataSchemePrefix) + formCode + "_" + "F";
            dataTableDTO.setCode(this.judgeTableCode(tableCodes, prefix));
        }
        dataTableDTO.setDataSchemeKey(param.getDataSchemeKey());
        dataTableDTO.setTitle(this.judgeTableTitle(tableTitles, region, param.getFormTitle()));
        dataTableDTO.setState(Constants.DataStatus.NONE.ordinal());
        dataTableDTO.setGatherType(DataTableGatherType.NONE.getValue());
        dataTableDTO.setRepeatCode(true);
        dataTableDTO.setDataGroupKey(ReverseModeUtils.createGroupPath(param.getFormSchemeTitle(), param.getFormGroupTitle(), param.getFormTitle()));
        return dataTableDTO;
    }

    @Override
    public <T extends DataFieldDTO> List<T> createField(ReverseModeParam param, ReverseModeRegionDTO region) {
        List<String> fieldCodes = region.getFieldCodes();
        IReverseModelDataProvider reverseModelDataProvider = param.getReverseModelDataProvider();
        List dbFields = reverseModelDataProvider != null ? reverseModelDataProvider.getFields(param, region) : ((IDesignDataSchemeService)BeanUtil.getBean(IDesignDataSchemeService.class)).getDataFieldByTableKeyAndKind(region.getTableKey(), new DataFieldKind[]{DataFieldKind.FIELD});
        if (dbFields != null && !dbFields.isEmpty()) {
            dbFields.forEach(f -> fieldCodes.add(f.getCode()));
        }
        HashSet<String> strings = new HashSet<String>(fieldCodes);
        int start = 1;
        ArrayList<DataFieldSettingDTO> list = new ArrayList<DataFieldSettingDTO>();
        for (int i = 0; i < region.getFieldNum(); ++i) {
            ReverseModeFieldDTO settingDTO = new ReverseModeFieldDTO();
            settingDTO.setDataSchemeKey(param.getDataSchemeKey());
            settingDTO.setCode(this.judgeFieldCode(strings, this.rmPrefix(param.getDataSchemePrefix(), region.getTableCode()), start++));
            settingDTO.setDataTableKey(region.getTableKey());
            settingDTO.setRegionKey(region.getRegionKey());
            List<Integer> fieldTypes = region.getFieldTypes();
            Integer fieldType = fieldTypes != null ? (fieldTypes.size() == region.getFieldNum().intValue() ? fieldTypes.get(i) : region.getFieldType()) : region.getFieldType();
            if (DataFieldType.valueOf((int)fieldType) == null) {
                fieldType = DataFieldType.BIGDECIMAL.getValue();
            }
            this.initField(list, settingDTO, region, fieldType);
        }
        return list;
    }

    @Override
    protected String judgeTableTitle(Set<String> tableTitles, ReverseModeRegionDTO region, String form) {
        if (region.getRegionKind().intValue() == DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
            return this.judgeTableCode(tableTitles, form + "\u56fa\u5b9a\u533a\u57df");
        }
        if (region.getRegionKind().intValue() == DataRegionKind.DATA_REGION_ROW_LIST.getValue()) {
            return this.judgeTableCode(tableTitles, form + "\u6d6e\u52a8\u533a\u57df" + region.getRegionTop());
        }
        if (region.getRegionKind().intValue() == DataRegionKind.DATA_REGION_COLUMN_LIST.getValue()) {
            return this.judgeTableCode(tableTitles, form + "\u6d6e\u52a8\u533a\u57df" + region.getRegionLeft());
        }
        return "\u9519\u8bef\u7c7b\u578b\u7684\u6570\u636e\u8868";
    }
}

