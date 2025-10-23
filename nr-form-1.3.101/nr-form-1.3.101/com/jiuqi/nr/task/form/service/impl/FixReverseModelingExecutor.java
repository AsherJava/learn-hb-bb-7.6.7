/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.internal.service.DataFieldDesignService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 */
package com.jiuqi.nr.task.form.service.impl;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.service.DataFieldDesignService;
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

public class FixReverseModelingExecutor
extends AbstractReverseModelingExecutor {
    @Override
    public boolean available(DataRegionKind regionKind) {
        return DataRegionKind.DATA_REGION_SIMPLE == regionKind;
    }

    @Override
    public DataTableDTO createTable(ReverseModeParam param, ReverseModeRegionDTO region) {
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
        String prefix = this.formatSchemePrefix(dataSchemePrefix) + formCode;
        dataTableDTO.setCode(this.judgeTableCode(tableCodes, prefix));
        dataTableDTO.setDataTableType(DataTableType.TABLE.getValue());
        dataTableDTO.setDataSchemeKey(param.getDataSchemeKey());
        dataTableDTO.setTitle(this.judgeTableTitle(tableTitles, region, param.getFormType().equals("FORM_TYPE_FIX") ? param.getFormTitle() : param.getFormTitle() + "\u56fa\u5b9a\u533a\u57df"));
        dataTableDTO.setState(Constants.DataStatus.NONE.ordinal());
        dataTableDTO.setGatherType(DataTableGatherType.CLASSIFY.getValue());
        dataTableDTO.setRepeatCode(true);
        dataTableDTO.setDataGroupKey(ReverseModeUtils.createGroupPath(param.getFormSchemeTitle(), param.getFormGroupTitle(), param.getFormTitle()));
        return dataTableDTO;
    }

    @Override
    public <T extends DataFieldDTO> List<T> createField(ReverseModeParam param, ReverseModeRegionDTO region) {
        List fields;
        IReverseModelDataProvider reverseModelDataProvider = param.getReverseModelDataProvider();
        if (reverseModelDataProvider != null) {
            fields = reverseModelDataProvider.getFields(param, region);
        } else {
            FieldSearchQuery query = new FieldSearchQuery();
            query.setKeyword(param.getFormCode());
            query.setScheme(param.getDataSchemeKey());
            fields = ((DataFieldDesignService)BeanUtil.getBean(DataFieldDesignService.class)).filterField(query);
        }
        HashSet<String> fieldCodes = new HashSet<String>(region.getFieldCodes());
        if (fields != null && !fields.isEmpty()) {
            fields.forEach(f -> fieldCodes.add(f.getCode()));
        }
        int start = 1;
        ArrayList<DataFieldSettingDTO> list = new ArrayList<DataFieldSettingDTO>();
        for (int i = 0; i < region.getFieldNum(); ++i) {
            ReverseModeFieldDTO fieldSettingDTO = new ReverseModeFieldDTO();
            fieldSettingDTO.setDataSchemeKey(param.getDataSchemeKey());
            fieldSettingDTO.setCode(this.judgeFieldCode(fieldCodes, param.getFormCode(), start++));
            fieldSettingDTO.setDataTableKey(region.getTableKey());
            fieldSettingDTO.setRegionKey(region.getRegionKey());
            List<Integer> fieldTypes = region.getFieldTypes();
            Integer fieldType = fieldTypes != null ? (fieldTypes.size() == region.getFieldNum().intValue() ? fieldTypes.get(i) : region.getFieldType()) : region.getFieldType();
            if (DataFieldType.valueOf((int)fieldType) == null) {
                fieldType = DataFieldType.BIGDECIMAL.getValue();
            }
            this.initField(list, fieldSettingDTO, region, fieldType);
        }
        return list;
    }
}

