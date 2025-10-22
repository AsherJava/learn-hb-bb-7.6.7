/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataType
 *  com.jiuqi.bi.quickreport.writeback.IWritebackFieldProvider
 *  com.jiuqi.bi.quickreport.writeback.SearchBean
 *  com.jiuqi.bi.quickreport.writeback.TableField
 *  com.jiuqi.bi.quickreport.writeback.WritebackException
 *  com.jiuqi.bi.quickreport.writeback.WritebackFolder
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.bi.quickreport;

import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.quickreport.NrWritebackParam;
import com.jiuqi.bi.quickreport.writeback.IWritebackFieldProvider;
import com.jiuqi.bi.quickreport.writeback.SearchBean;
import com.jiuqi.bi.quickreport.writeback.TableField;
import com.jiuqi.bi.quickreport.writeback.WritebackException;
import com.jiuqi.bi.quickreport.writeback.WritebackFolder;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import java.util.ArrayList;
import java.util.List;

public class NrWritebackFieldProvider
implements IWritebackFieldProvider {
    private NrWritebackParam param;
    private String tableName;

    public NrWritebackFieldProvider(NrWritebackParam param, String tableName) {
        this.param = param;
        this.tableName = tableName;
    }

    public List<TableField> getAllFields(String tableKey) throws WritebackException {
        List dataFields;
        ArrayList<TableField> tableFields = new ArrayList<TableField>();
        DataTable dataTable = this.param.dataSchemeService.getDataTableByCode(this.tableName);
        if (dataTable == null) {
            dataTable = this.param.dataSchemeService.getDataTable(this.tableName);
        }
        if ((dataFields = this.param.dataSchemeService.getDataFieldByTable(dataTable.getKey())) == null) {
            return tableFields;
        }
        for (DataField dataField : dataFields) {
            if (dataField.getCode().equals("BIZKEYORDER") || dataField.getCode().equals("FLOATORDER")) continue;
            TableField tableField = this.dataFieldToTableField(dataField);
            tableFields.add(tableField);
        }
        return tableFields;
    }

    public List<WritebackFolder> getFieldFolders(String parent) throws WritebackException {
        return null;
    }

    public List<TableField> getFields(String parent) throws WritebackException {
        return this.getAllFields(parent);
    }

    private TableField dataFieldToTableField(DataField dataField) {
        Integer precision;
        TableField tableField = new TableField();
        tableField.setName(dataField.getCode());
        DataFieldType dataFieldType = dataField.getDataFieldType();
        DataType dataType = DataType.valueOf((int)dataFieldType.getValue());
        if (dataFieldType == DataFieldType.BIGDECIMAL) {
            dataType = DataType.DOUBLE;
        }
        tableField.setDataType(dataType);
        tableField.setTitle(dataField.getTitle());
        Integer decimal = dataField.getDecimal();
        if (decimal != null) {
            tableField.setDecimal(decimal.intValue());
        }
        if ((precision = dataField.getPrecision()) != null) {
            tableField.setPrecision(precision.intValue());
        }
        return tableField;
    }

    public List<TableField> searchFields(String arg0, SearchBean searchBean) throws WritebackException {
        String keyword = searchBean.getFilter();
        ArrayList<TableField> fields = new ArrayList<TableField>();
        DataTable dataTable = this.param.dataSchemeService.getDataTableByCode(this.tableName);
        if (dataTable == null) {
            dataTable = this.param.dataSchemeService.getDataTable(this.tableName);
        }
        List dataFields = this.param.dataSchemeService.getDataFieldByTable(dataTable.getKey());
        for (DataField dataField : dataFields) {
            if (dataField.getCode().contains(keyword.toUpperCase()) || dataField.getTitle().contains(keyword)) {
                fields.add(this.dataFieldToTableField(dataField));
            }
            if (searchBean.getMaxSize() <= 1 || searchBean.getMaxSize() > fields.size()) continue;
            break;
        }
        return fields;
    }
}

