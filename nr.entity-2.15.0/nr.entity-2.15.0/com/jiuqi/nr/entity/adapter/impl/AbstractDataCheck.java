/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.feign.client.DataModelClient
 */
package com.jiuqi.nr.entity.adapter.impl;

import com.jiuqi.nr.entity.engine.result.CheckFailNodeInfo;
import com.jiuqi.nr.entity.engine.result.EntityDataRow;
import com.jiuqi.nr.entity.param.IEntityUpdateParam;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.client.DataModelClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDataCheck {
    @Autowired
    private DataModelClient dataModelClient;

    public List<CheckFailNodeInfo> checkData(IEntityUpdateParam updateParam, boolean insert) {
        List<EntityDataRow> modifyRows = updateParam.getModifyRows();
        String tableName = updateParam.getEntityId();
        Date version = updateParam.getVersionDate();
        ArrayList<CheckFailNodeInfo> checkFailNodeInfos = new ArrayList<CheckFailNodeInfo>();
        ArrayList<String> modifyCodes = new ArrayList<String>();
        Map<String, DataModelColumn> orgColumns = this.getOrgColumns(tableName);
        DataModelColumn codeColumn = this.getCodeColumn(orgColumns);
        for (EntityDataRow modifyRow : modifyRows) {
            int countFail = checkFailNodeInfos.size();
            Set<String> keySet = modifyRow.getRowData().keySet();
            List<CheckFailNodeInfo> systemFieldCheck = this.systemFieldCheck(insert, orgColumns, modifyRow, keySet);
            checkFailNodeInfos.addAll(systemFieldCheck);
            for (String key : keySet) {
                DataModelColumn orgColumn = orgColumns.get(key.toUpperCase());
                if (orgColumn == null) continue;
                List<CheckFailNodeInfo> columnCheckInfo = this.tryCheckColumnData(modifyRow, key, orgColumn, insert);
                checkFailNodeInfos.addAll(columnCheckInfo);
            }
            List<CheckFailNodeInfo> filterFail = this.filterData(updateParam, modifyRow);
            checkFailNodeInfos.addAll(filterFail);
            if (checkFailNodeInfos.size() == countFail) {
                Object codeValue = modifyRow.getValue(codeColumn.getColumnName().toLowerCase(Locale.ROOT));
                if (codeValue == null) continue;
                modifyCodes.add(codeValue.toString());
                continue;
            }
            modifyRow.setCheckFailed(true);
        }
        if (this.enableRepeatCheck(tableName, insert)) {
            Map<String, List<String>> existCode = this.queryExistData(modifyCodes, tableName, version);
            for (EntityDataRow modifyRow : modifyRows) {
                Object value = modifyRow.getValue(this.getCodeColumn(orgColumns).getColumnName().toLowerCase(Locale.ROOT));
                if (value == null || !existCode.containsKey(value) || modifyRow.isIgnoreCodeCheck()) continue;
                List<String> codeList = existCode.get(value);
                String code = (String)value;
                if (codeList != null) {
                    if (modifyRow.getEntityKeyData() != null && codeList.contains(modifyRow.getEntityKeyData())) continue;
                    code = codeList.get(0);
                }
                CheckFailNodeInfo failNodeInfo = new CheckFailNodeInfo(code, codeColumn.getColumnName(), value, codeColumn.getColumnTitle().concat("\u91cd\u590d"));
                failNodeInfo.setCheckOption(0);
                checkFailNodeInfos.add(failNodeInfo);
                modifyRow.setCheckFailed(true);
            }
        }
        return checkFailNodeInfos;
    }

    protected List<CheckFailNodeInfo> filterData(IEntityUpdateParam updateParam, EntityDataRow row) {
        List<CheckFailNodeInfo> checkFailNodeInfos = this.checkData(updateParam, row);
        if (checkFailNodeInfos == null) {
            return Collections.emptyList();
        }
        return checkFailNodeInfos;
    }

    protected abstract DataModelColumn getCodeColumn(Map<String, DataModelColumn> var1);

    protected abstract DataModelColumn getKeyColumn(Map<String, DataModelColumn> var1);

    protected abstract DataModelColumn getNameColumn(Map<String, DataModelColumn> var1);

    protected abstract DataModelColumn getParentColumn(Map<String, DataModelColumn> var1);

    protected abstract Map<String, List<String>> queryExistData(List<String> var1, String var2, Date var3);

    protected abstract boolean enableRepeatCheck(String var1, boolean var2);

    protected abstract List<CheckFailNodeInfo> checkData(IEntityUpdateParam var1, EntityDataRow var2);

    protected List<CheckFailNodeInfo> extCheck(EntityDataRow modifyRow) {
        return null;
    }

    private List<CheckFailNodeInfo> systemFieldCheck(boolean insert, Map<String, DataModelColumn> orgColumns, EntityDataRow modifyRow, Set<String> keySet) {
        ArrayList<CheckFailNodeInfo> checkFailNodeInfos = new ArrayList<CheckFailNodeInfo>();
        DataModelColumn codeColumn = this.getCodeColumn(orgColumns);
        DataModelColumn keyColumn = this.getKeyColumn(orgColumns);
        DataModelColumn nameColumn = this.getNameColumn(orgColumns);
        CheckFailNodeInfo codeCheck = this.entityCodeCheck(insert, modifyRow, keySet, codeColumn);
        CheckFailNodeInfo nameCheck = this.entityCodeCheck(insert, modifyRow, keySet, nameColumn);
        CheckFailNodeInfo keyCheck = this.keyOrParentCodeCheck(insert, modifyRow, keyColumn);
        if (codeCheck != null) {
            checkFailNodeInfos.add(codeCheck);
        }
        if (nameCheck != null) {
            checkFailNodeInfos.add(nameCheck);
        }
        if (keyCheck != null) {
            checkFailNodeInfos.add(keyCheck);
        }
        return checkFailNodeInfos;
    }

    private CheckFailNodeInfo entityCodeCheck(boolean insert, EntityDataRow modifyRow, Set<String> keySet, DataModelColumn column) {
        String name;
        Object nameValue;
        if (column != null && this.isNullObject(nameValue = modifyRow.getValue(name = column.getColumnName().toLowerCase(Locale.ROOT))) && this.checkState(insert, keySet.contains(name))) {
            CheckFailNodeInfo checkFailNodeInfo = new CheckFailNodeInfo(null, name, nameValue, column.getColumnTitle().concat("\u4e0d\u80fd\u4e3a\u7a7a"));
            checkFailNodeInfo.setCheckOption(0);
            return checkFailNodeInfo;
        }
        return null;
    }

    private CheckFailNodeInfo keyOrParentCodeCheck(boolean insert, EntityDataRow modifyRow, DataModelColumn column) {
        if (column != null && insert) {
            String name = column.getColumnName().toLowerCase(Locale.ROOT);
            Object nameValue = modifyRow.getValue(name);
            if (this.isNullObject(nameValue)) {
                return null;
            }
            return this.upperCaseCheck(nameValue, name, column);
        }
        return null;
    }

    private CheckFailNodeInfo upperCaseCheck(Object nameValue, String name, DataModelColumn column) {
        if (!nameValue.equals(nameValue.toString().toUpperCase())) {
            CheckFailNodeInfo checkFailNodeInfo = new CheckFailNodeInfo(null, name, nameValue, column.getColumnTitle().concat("\u4e0d\u80fd\u5305\u542b\u5c0f\u5199\u5b57\u6bcd"));
            checkFailNodeInfo.setCheckOption(0);
            return checkFailNodeInfo;
        }
        return null;
    }

    protected Map<String, DataModelColumn> getOrgColumns(String table) {
        DataModelDTO param = new DataModelDTO();
        param.setName(table);
        DataModelDO orgModelDO = this.dataModelClient.get(param);
        List orgColumns = orgModelDO.getColumns();
        return orgColumns.stream().collect(Collectors.toMap(DataModelColumn::getColumnName, e -> e));
    }

    private boolean checkState(boolean insert, boolean contains) {
        if (insert) {
            return true;
        }
        return contains;
    }

    private List<CheckFailNodeInfo> tryCheckColumnData(EntityDataRow modifyRow, String attribute, DataModelColumn orgColumn, boolean insert) {
        ArrayList<CheckFailNodeInfo> failNodeInfos = new ArrayList<CheckFailNodeInfo>();
        DataModelType.ColumnAttr columnAttr = orgColumn.getColumnAttr();
        if (columnAttr != DataModelType.ColumnAttr.SYSTEM && columnAttr != DataModelType.ColumnAttr.FIXED) {
            CheckFailNodeInfo failNodeInfo = this.nullCheck(orgColumn, modifyRow.getValue(attribute));
            if (failNodeInfo != null) {
                failNodeInfo.setCheckOption(insert ? 0 : 1);
                failNodeInfo.setCode(modifyRow.getEntityKeyData());
                failNodeInfos.add(failNodeInfo);
            }
            if ((failNodeInfo = this.sizeCheck(orgColumn, modifyRow.getValue(attribute))) != null) {
                failNodeInfo.setCheckOption(insert ? 0 : 1);
                failNodeInfo.setCode(modifyRow.getEntityKeyData());
                failNodeInfos.add(failNodeInfo);
            }
        }
        return failNodeInfos;
    }

    private CheckFailNodeInfo nullCheck(DataModelColumn column, Object data) {
        if (column.isNullable().booleanValue()) {
            return null;
        }
        if (this.isNullObject(data)) {
            CheckFailNodeInfo checkFailNodeInfo = new CheckFailNodeInfo();
            checkFailNodeInfo.setValue(data);
            checkFailNodeInfo.setAttributeCode(column.getColumnName());
            checkFailNodeInfo.setMessage(column.getColumnTitle() + "\u4e0d\u80fd\u4e3a\u7a7a\u3002");
            return checkFailNodeInfo;
        }
        return null;
    }

    private CheckFailNodeInfo sizeCheck(DataModelColumn column, Object data) {
        int size = 0;
        String prefixMessage = "\u957f\u5ea6";
        if (data instanceof Boolean) {
            return null;
        }
        if (DataModelType.ColumnType.DATE.equals((Object)column.getColumnType()) || DataModelType.ColumnType.CLOB.equals((Object)column.getColumnType()) || DataModelType.ColumnType.TIMESTAMP.equals((Object)column.getColumnType())) {
            return null;
        }
        if (DataModelType.ColumnType.INTEGER.equals((Object)column.getColumnType()) || DataModelType.ColumnType.NVARCHAR.equals((Object)column.getColumnType())) {
            size = String.valueOf(data).length();
        } else if (DataModelType.ColumnType.NUMERIC.equals((Object)column.getColumnType())) {
            size = String.valueOf(data).split("\\.")[0].length();
            prefixMessage = "\u6574\u6570\u4f4d\u957f\u5ea6";
        }
        Integer[] lengths = column.getLengths();
        if (size > lengths[0]) {
            CheckFailNodeInfo checkFailNodeInfo = new CheckFailNodeInfo();
            checkFailNodeInfo.setValue(data);
            checkFailNodeInfo.setAttributeCode(column.getColumnName());
            checkFailNodeInfo.setMessage(column.getColumnTitle() + prefixMessage + "\u4e0d\u80fd\u8d85\u8fc7" + lengths[0] + "\u4f4d");
            return checkFailNodeInfo;
        }
        return null;
    }

    protected boolean isNullObject(Object value) {
        return value == null || "".equals(value);
    }
}

