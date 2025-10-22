/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 */
package com.jiuqi.np.definition.impl.controller;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.impl.common.DefinitionTransUtils;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service(value="DataDefinitionDesignTimeController2")
public class DataDefinitionDesignTimeController2
implements IDataDefinitionDesignTimeController {
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;

    private DesignTableDefine toDesignTableDefine(DataTable dataTable) {
        return DefinitionTransUtils.toDesignTableDefine(dataTable);
    }

    public DesignTableDefine queryTableDefine(String tableKey) throws JQException {
        return this.toDesignTableDefine((DataTable)this.designDataSchemeService.getDataTable(tableKey));
    }

    public DesignTableDefine queryTableDefinesByCode(String tableCode) throws JQException {
        return this.toDesignTableDefine((DataTable)this.designDataSchemeService.getDataTableByCode(tableCode));
    }

    public List<DesignTableDefine> queryTableDefines(String[] tableKeys) throws JQException {
        List dataTables = this.designDataSchemeService.getDataTables(Arrays.asList(tableKeys));
        if (null != dataTables && !dataTables.isEmpty()) {
            return dataTables.stream().map(this::toDesignTableDefine).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private DesignFieldDefine toDesignFieldDefine(DataField dataField) {
        return DefinitionTransUtils.toDesignFieldDefine(dataField, null);
    }

    public DesignFieldDefine queryFieldDefine(String fieldKey) throws JQException {
        return this.toDesignFieldDefine((DataField)this.designDataSchemeService.getDataField(fieldKey));
    }

    public DesignFieldDefine queryFieldDefineByCodeInTable(String fieldDefineCode, String tableKey) throws JQException {
        return this.toDesignFieldDefine((DataField)this.designDataSchemeService.getDataFieldByTableKeyAndCode(tableKey, fieldDefineCode));
    }

    public List<DesignFieldDefine> getAllFieldsInTable(String tableKey) throws JQException {
        List dataFields = this.designDataSchemeService.getDataFieldByTable(tableKey);
        if (null != dataFields && !dataFields.isEmpty()) {
            return dataFields.stream().map(this::toDesignFieldDefine).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<DesignFieldDefine> queryFieldDefines(String[] fieldKeys) throws JQException {
        if (null == fieldKeys || 0 == fieldKeys.length) {
            return Collections.emptyList();
        }
        List dataFields = this.designDataSchemeService.getDataFields(Arrays.asList(fieldKeys));
        if (null != dataFields && !dataFields.isEmpty()) {
            return dataFields.stream().map(this::toDesignFieldDefine).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}

