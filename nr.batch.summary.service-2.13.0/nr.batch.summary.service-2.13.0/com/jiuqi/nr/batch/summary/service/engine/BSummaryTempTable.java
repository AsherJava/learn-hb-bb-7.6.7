/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.common.temptable.ITempTable
 */
package com.jiuqi.nr.batch.summary.service.engine;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableEntity;
import com.jiuqi.nr.common.temptable.ITempTable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BSummaryTempTable
implements ITableEntity {
    public static final String DM_KJ = "DM_KJ";
    private String tableName;
    private ITempTable tempTableDefine;
    private List<LogicField> logicFields = new ArrayList<LogicField>();
    private List<LogicField> primaryFields = new ArrayList<LogicField>();
    private final Map<String, LogicField> code2Field = new HashMap<String, LogicField>();

    public BSummaryTempTable() {
        LogicField md_kj = new LogicField();
        md_kj.setFieldName(DM_KJ);
        md_kj.setDataType(6);
        md_kj.setSize(50);
        md_kj.setNullable(false);
        this.primaryFields.add(md_kj);
        this.code2Field.put(md_kj.getFieldName(), md_kj);
    }

    @Override
    public String getTableName() {
        return this.tempTableDefine.getTableName();
    }

    @Override
    public ITempTable getTempTableDefine() {
        return this.tempTableDefine;
    }

    public void setTempTableDefine(ITempTable tempTableDefine) {
        this.tempTableDefine = tempTableDefine;
    }

    @Override
    public LogicField findColumn(String columnCode) {
        return this.code2Field.get(columnCode);
    }

    @Override
    public boolean hasColumn(String columnCode) {
        return this.code2Field.containsKey(columnCode);
    }

    @Override
    public List<LogicField> getPrimaryColumns() {
        return this.primaryFields;
    }

    public void setPrimaryColumns(List<LogicField> primaryFields) {
        this.primaryFields.addAll(primaryFields);
        primaryFields.forEach(e -> this.code2Field.put(e.getFieldName(), (LogicField)e));
    }

    @Override
    public List<LogicField> getLogicColumns() {
        return this.logicFields;
    }

    public void setLogicColumns(List<LogicField> logicFields) {
        this.logicFields.addAll(logicFields);
        logicFields.forEach(e -> this.code2Field.put(e.getFieldName(), (LogicField)e));
    }

    @Override
    public List<LogicField> getAllColumns() {
        ArrayList<LogicField> columns = new ArrayList<LogicField>();
        columns.addAll(this.primaryFields);
        columns.addAll(this.logicFields);
        return columns;
    }
}

