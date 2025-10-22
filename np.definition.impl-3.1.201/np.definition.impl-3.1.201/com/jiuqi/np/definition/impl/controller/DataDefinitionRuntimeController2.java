/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.RuntimeDefinitionTransfer
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.interval.dao.TableCheckDao
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.np.definition.impl.controller;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.RuntimeDefinitionTransfer;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.interval.dao.TableCheckDao;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Primary
@Service(value="DataDefinitionRuntimeController2")
public class DataDefinitionRuntimeController2
implements IDataDefinitionRuntimeController {
    private static final Logger log = LoggerFactory.getLogger(DataDefinitionRuntimeController2.class);
    @Autowired
    protected IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    protected DataModelService dataModelService;
    @Autowired
    private TableCheckDao tableCheckDao;

    public void setTableCheckDao(TableCheckDao tableCheckDao) {
        this.tableCheckDao = tableCheckDao;
    }

    public void setRuntimeDataSchemeService(IRuntimeDataSchemeService runtimeDataSchemeService) {
        this.runtimeDataSchemeService = runtimeDataSchemeService;
    }

    public void setDataModelService(DataModelService dataModelService) {
        this.dataModelService = dataModelService;
    }

    public TableDefine queryTableDefine(String tableKey) throws JQException {
        return RuntimeDefinitionTransfer.toTableDefine((DataTable)this.runtimeDataSchemeService.getDataTable(tableKey));
    }

    public TableDefine queryTableDefineByCode(String tableDefineCode) throws JQException {
        return RuntimeDefinitionTransfer.toTableDefine((DataTable)this.runtimeDataSchemeService.getDataTableByCode(tableDefineCode));
    }

    public FieldDefine queryFieldDefine(String fieldKey) throws Exception {
        if (!StringUtils.hasText(fieldKey)) {
            return null;
        }
        return RuntimeDefinitionTransfer.toFieldDefine((DataField)this.runtimeDataSchemeService.getDataField(fieldKey));
    }

    public List<FieldDefine> queryFieldDefines(Collection<String> fieldKeys) throws Exception {
        List dataFields = this.runtimeDataSchemeService.getDataFields(new ArrayList<String>(fieldKeys));
        if (null != dataFields && !dataFields.isEmpty()) {
            return dataFields.stream().map(RuntimeDefinitionTransfer::toFieldDefine).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public FieldDefine queryFieldByCodeInTable(String fieldCode, String tableKey) throws Exception {
        return RuntimeDefinitionTransfer.toFieldDefine((DataField)this.runtimeDataSchemeService.getDataFieldByTableKeyAndCode(tableKey, fieldCode));
    }

    public List<FieldDefine> getAllFieldsInTable(String tableKey) throws Exception {
        List dataFields = this.runtimeDataSchemeService.getDataFieldByTable(tableKey);
        if (null != dataFields && !dataFields.isEmpty()) {
            return dataFields.stream().map(RuntimeDefinitionTransfer::toFieldDefine).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public FieldDefine queryFieldDefineByCodeInRange(Collection<String> fieldKeys, String fieldCode) throws Exception {
        Optional<DataField> findAny;
        List dataFields = this.runtimeDataSchemeService.getDataFields(new ArrayList<String>(fieldKeys));
        if (null != dataFields && !dataFields.isEmpty() && (findAny = dataFields.stream().filter(f -> f.getCode().equals(fieldCode)).findAny()).isPresent()) {
            return RuntimeDefinitionTransfer.toFieldDefine((DataField)findAny.get());
        }
        return null;
    }

    public List<TableDefine> queryTableDefinesByFields(Collection<String> fieldKeys) {
        Collection tableKeySet = null;
        List dataFields = this.runtimeDataSchemeService.getDataFields(new ArrayList<String>(fieldKeys));
        if (null == dataFields || dataFields.isEmpty()) {
            return Collections.emptyList();
        }
        tableKeySet = dataFields.stream().map(DataField::getDataTableKey).collect(Collectors.toSet());
        if (null == tableKeySet || tableKeySet.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            return this.queryTableDefinesInRange(tableKeySet);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<TableDefine> queryTableDefinesInRange(Collection<String> tableKeys) throws Exception {
        List dataTables = this.runtimeDataSchemeService.getDataTables(new ArrayList<String>(tableKeys));
        if (null != dataTables && !dataTables.isEmpty()) {
            return dataTables.stream().map(RuntimeDefinitionTransfer::toTableDefine).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public int checkTableDefineIsDestry(String name) {
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(name);
        if (null == tableModel) {
            return 0;
        }
        return 1;
    }

    public boolean checkTableHasRecord(String name) {
        try {
            return this.tableCheckDao.checkTableExistData(name);
        }
        catch (Exception e) {
            log.error("\u68c0\u6d4b\u7269\u7406\u8868[{}]\u662f\u5426\u6709\u6570\u636e\u5f02\u5e38\uff1a", (Object)name, (Object)e);
            return false;
        }
    }

    @Deprecated
    public List<FieldDefine> queryFieldDefinesInRange(Collection<String> fieldKeys) {
        try {
            return this.queryFieldDefines(fieldKeys);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}

