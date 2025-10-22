/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.io.sb.service.Impl;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.exception.ParamCheckException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseActuator {
    protected final boolean debugEnabled = logger.isDebugEnabled();
    protected IRuntimeDataSchemeService schemeService;
    private static final Logger logger = LoggerFactory.getLogger(BaseActuator.class);
    private static final long ONE_DAY_MILLIS = 86400000L;

    public DataSchemeTmpTable initModel(DataTable table, List<DataField> fields) {
        DataSchemeTmpTable tmpTable = new DataSchemeTmpTable();
        tmpTable.setTable(table);
        for (DataField field : fields) {
            this.addField(tmpTable, field);
            tmpTable.getAllFields().add(field);
        }
        DataField mdCode = tmpTable.getMdCode();
        if (mdCode == null) {
            throw new ParamCheckException("\u5bfc\u5165\u53c2\u6570\u4e0d\u5b8c\u6574\uff0c\u7f3a\u5c11\u5355\u4f4d\u6307\u6807\u5b9a\u4e49");
        }
        List allField = this.schemeService.getDataFieldByTable(table.getKey());
        for (DataField field : allField) {
            if ("BIZKEYORDER".equals(field.getCode()) || "SBID".equals(field.getCode()) || "FLOATORDER".equals(field.getCode()) || tmpTable.getFieldMap().containsKey(field.getKey())) continue;
            this.addField(tmpTable, field);
            tmpTable.getMissingFields().add(field);
        }
        List dwFields = this.schemeService.getDeployInfoByDataFieldKeys(new String[]{mdCode.getKey()});
        if (dwFields.isEmpty()) {
            throw new ParamCheckException("\u672a\u627e\u5230\u53f0\u8d26\u8868\u53d1\u5e03\u4fe1\u606f");
        }
        String tableName = null;
        Map<String, DataFieldDeployInfo> infoMap = tmpTable.getDeployInfoMap();
        for (DataFieldDeployInfo dwField : dwFields) {
            String name = dwField.getTableName();
            if (tableName != null) {
                if (tableName.length() <= name.length()) continue;
                infoMap.put(mdCode.getKey(), dwField);
                tableName = name;
                continue;
            }
            infoMap.put(mdCode.getKey(), dwField);
            tableName = name;
        }
        tmpTable.setTzTableName(tableName);
        String[] fieldKeys = (String[])tmpTable.getDimFields().stream().map(Basic::getKey).toArray(String[]::new);
        tmpTable.setDimDeploys(this.schemeService.getDeployInfoByDataFieldKeys(fieldKeys));
        fieldKeys = (String[])tmpTable.getTableDimFields().stream().map(Basic::getKey).toArray(String[]::new);
        if (fieldKeys.length == 0) {
            throw new ParamCheckException("\u53f0\u8d26\u8868\u5185\u7ef4\u5ea6\u53c2\u6570\u4e0d\u5b8c\u6574");
        }
        tmpTable.setTableDimDeploys(this.schemeService.getDeployInfoByDataFieldKeys(fieldKeys));
        fieldKeys = (String[])tmpTable.getTimePointFields().stream().map(Basic::getKey).toArray(String[]::new);
        tmpTable.setTimePointDeploys(this.schemeService.getDeployInfoByDataFieldKeys(fieldKeys));
        fieldKeys = (String[])tmpTable.getPeriodicFields().stream().map(Basic::getKey).toArray(String[]::new);
        tmpTable.setPeriodicDeploys(this.schemeService.getDeployInfoByDataFieldKeys(fieldKeys));
        Stream.of(tmpTable.getDimDeploys(), tmpTable.getTableDimDeploys(), tmpTable.getTimePointDeploys(), tmpTable.getPeriodicDeploys()).flatMap(Collection::stream).collect(Collectors.toMap(DataFieldDeployInfo::getDataFieldKey, r -> r, (r, v) -> r, () -> infoMap));
        ArrayList<DataFieldDeployInfo> list = new ArrayList<DataFieldDeployInfo>();
        for (DataField field : tmpTable.getVersionTimePointFields()) {
            list.add(tmpTable.getDeployInfoMap().get(field.getKey()));
        }
        tmpTable.setVersionTimePointDeploys(list);
        return tmpTable;
    }

    private void addField(DataSchemeTmpTable tmpTable, DataField field) {
        tmpTable.getFieldMap().put(field.getKey(), field);
        DataFieldKind dataFieldKind = field.getDataFieldKind();
        switch (dataFieldKind) {
            case FIELD: 
            case FIELD_ZB: {
                if (field.isChangeWithPeriod()) {
                    tmpTable.getPeriodicFields().add(field);
                    break;
                }
                tmpTable.getTimePointFields().add(field);
                if (!field.isGenerateVersion()) break;
                tmpTable.getVersionTimePointFields().add(field);
                break;
            }
            case PUBLIC_FIELD_DIM: {
                if ("DATATIME".equals(field.getCode())) {
                    tmpTable.setPeriod(field);
                    break;
                }
                if ("MDCODE".equals(field.getCode())) {
                    tmpTable.setMdCode(field);
                    break;
                }
                tmpTable.getDimFields().add(field);
                break;
            }
            case TABLE_FIELD_DIM: {
                tmpTable.getTableDimFields().add(field);
                break;
            }
            case BUILT_IN_FIELD: {
                if (!"BIZKEYORDER".equals(field.getCode()) && !"SBID".equals(field.getCode())) break;
                tmpTable.setSbId(field);
                break;
            }
        }
    }

    protected boolean compareData(Object[] sysData, Object[] impData, List<DataField> dataFields) {
        for (int i = 0; i < dataFields.size(); ++i) {
            long impDayMillis;
            long sysDayMillis;
            Object sysObj = sysData[i];
            Object impObj = impData[i];
            if (!(sysObj != null && impObj != null ? (dataFields.get(i).getDataFieldType().equals((Object)DataFieldType.DATE) ? (sysDayMillis = ((Date)sysObj).getTime()) / 86400000L != (impDayMillis = ((Date)impObj).getTime()) / 86400000L : (sysObj instanceof Comparable && impObj instanceof Comparable && sysObj.getClass().equals(impObj.getClass()) ? ((Comparable)sysObj).compareTo(impObj) != 0 : !Objects.equals(sysObj, impObj))) : !Objects.equals(sysObj, impObj))) continue;
            return false;
        }
        return true;
    }
}

