/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 */
package com.jiuqi.dc.bill.util;

import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class DcStorageUtil {
    public static List<DataModelColumn> mergeDataModel(DataModelDO origalDataModel, DataModelDO dataModelDO) {
        if (origalDataModel == null || origalDataModel.getColumns() == null) {
            return dataModelDO == null ? null : dataModelDO.getColumns();
        }
        if (dataModelDO == null || dataModelDO.getColumns() == null) {
            return origalDataModel == null ? null : origalDataModel.getColumns();
        }
        LinkedHashMap<String, DataModelColumn> columnMap = new LinkedHashMap<String, DataModelColumn>();
        String columnName = null;
        for (DataModelColumn dataModelColumn : origalDataModel.getColumns()) {
            columnName = dataModelColumn.getColumnName().toUpperCase().trim();
            dataModelColumn.setColumnName(columnName);
            columnMap.put(columnName, dataModelColumn);
        }
        for (DataModelColumn column : dataModelDO.getColumns()) {
            columnName = column.getColumnName().toUpperCase().trim();
            column.setColumnName(columnName);
            if (!columnMap.containsKey(columnName)) {
                columnMap.put(columnName, column);
                continue;
            }
            if (Objects.equals(DataModelType.ColumnAttr.FIXED, column.getColumnAttr())) {
                ((DataModelColumn)columnMap.get(columnName)).columnTitle(column.getColumnTitle()).lengths(column.getLengths());
                continue;
            }
            ((DataModelColumn)columnMap.get(columnName)).columnTitle(column.getColumnTitle()).lengths(column.getLengths()).mapping(column.getMapping());
        }
        return new ArrayList<DataModelColumn>(columnMap.values());
    }

    public static List<DataModelColumn> getBaseDataTemplateFields() {
        ArrayList<DataModelColumn> columns = new ArrayList<DataModelColumn>();
        columns.add(new DataModelColumn().columnName("ID").columnTitle("\u552f\u4e00\u6807\u8bc6").columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}).pkey(Boolean.valueOf(true)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("VER").columnTitle("\u884c\u7248\u672c").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 6}).nullable(Boolean.valueOf(false)).defaultVal("0").columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("CODE").columnTitle("\u4ee3\u7801").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("NAME").columnTitle("\u540d\u79f0").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{100}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("SHORTNAME").columnTitle("\u7b80\u79f0").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{100}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("UNITCODE").columnTitle("\u7ec4\u7ec7\u673a\u6784").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).mapping("MD_ORG.CODE").mappingType(Integer.valueOf(4)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("VALIDTIME").columnTitle("\u751f\u6548\u65f6\u95f4").columnType(DataModelType.ColumnType.TIMESTAMP).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("INVALIDTIME").columnTitle("\u5931\u6548\u65f6\u95f4").columnType(DataModelType.ColumnType.TIMESTAMP).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("PARENTCODE").columnTitle("\u7236\u7ea7").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("STOPFLAG").columnTitle("\u505c\u7528\u72b6\u6001").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{1}).nullable(Boolean.valueOf(false)).defaultVal("0").mappingType(Integer.valueOf(0)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("RECOVERYFLAG").columnTitle("\u4f5c\u5e9f\u72b6\u6001").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{1}).nullable(Boolean.valueOf(false)).defaultVal("0").mappingType(Integer.valueOf(0)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("ORDINAL").columnTitle("\u6392\u5e8f").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 6}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("CREATEUSER").columnTitle("\u521b\u5efa\u4eba").columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}).mapping("AUTH_USER.ID").mappingType(Integer.valueOf(3)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("CREATETIME").columnTitle("\u521b\u5efa\u65f6\u95f4").columnType(DataModelType.ColumnType.TIMESTAMP).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("PARENTS").columnTitle("\u5168\u8def\u5f84").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{610}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        return columns;
    }

    public static List<DataModelColumn> getBillMasterTableTemplateFields() {
        ArrayList<DataModelColumn> columns = new ArrayList<DataModelColumn>();
        columns.add(new DataModelColumn().columnName("ID").columnTitle("\u552f\u4e00\u6807\u8bc6").columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}).pkey(Boolean.valueOf(true)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("VER").columnTitle("\u884c\u7248\u672c").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 0}).nullable(Boolean.valueOf(false)).defaultVal("0").columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("DEFINECODE").columnTitle("\u5355\u636e\u5b9a\u4e49").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("BILLCODE").columnTitle("\u5355\u636e\u7f16\u53f7").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("BILLDATE").columnTitle("\u5355\u636e\u65e5\u671f").columnType(DataModelType.ColumnType.DATE).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("BILLSTATE").columnTitle("\u5355\u636e\u72b6\u6001").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{5}).mapping("EM_BILLSTATE.VAL").mappingType(Integer.valueOf(2)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("CREATEUSER").columnTitle("\u521b\u5efa\u4eba").columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}).mapping("AUTH_USER.ID").mappingType(Integer.valueOf(3)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("CREATETIME").columnTitle("\u521b\u5efa\u65f6\u95f4").columnType(DataModelType.ColumnType.TIMESTAMP).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("QRCODE").columnTitle(" \u4e8c\u7ef4\u7801").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{100}).columnAttr(DataModelType.ColumnAttr.FIXED));
        columns.add(new DataModelColumn().columnName("IMAGESTATE").columnTitle("\u5f71\u50cf\u72b6\u6001").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{2}).mapping("EM_IMAGESTATE.VAL").mappingType(Integer.valueOf(2)).columnAttr(DataModelType.ColumnAttr.FIXED));
        columns.add(new DataModelColumn().columnName("ATTACHNUM").columnTitle("\u9644\u4ef6\u6570\u91cf").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{5}).columnAttr(DataModelType.ColumnAttr.FIXED));
        return columns;
    }

    public static List<DataModelColumn> getBillSubTableTemplateFields() {
        ArrayList<DataModelColumn> columns = new ArrayList<DataModelColumn>();
        columns.add(new DataModelColumn().columnName("ID").columnTitle("\u552f\u4e00\u6807\u8bc6").columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}).pkey(Boolean.valueOf(true)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("VER").columnTitle("\u884c\u7248\u672c").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 0}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("MASTERID").columnTitle("\u4e3b\u8868ID").columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("UNITCODE").columnTitle("UNITCODE").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("SUBJECTNAME").columnTitle("SUBJECTNAME").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("CFITEMNAME").columnTitle("CFITEMNAME").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("BILLCODE").columnTitle("\u5355\u636e\u7f16\u53f7").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("ORDINAL").columnTitle("\u6392\u5e8f").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 6}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        return columns;
    }

    public static List<DataModelColumn> getOtherTemplateFields() {
        ArrayList<DataModelColumn> columns = new ArrayList<DataModelColumn>();
        columns.add(new DataModelColumn().columnName("ID").columnTitle("\u552f\u4e00\u6807\u8bc6").columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}).pkey(Boolean.valueOf(true)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        return columns;
    }
}

