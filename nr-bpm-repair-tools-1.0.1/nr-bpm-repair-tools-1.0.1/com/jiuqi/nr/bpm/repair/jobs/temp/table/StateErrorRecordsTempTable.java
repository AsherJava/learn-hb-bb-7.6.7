/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.repair.jobs.temp.table;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.bpm.repair.db.utils.DBTableEntity;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.ArrayList;
import java.util.List;

public class StateErrorRecordsTempTable
implements DBTableEntity {
    private boolean exist;
    private final List<LogicField> logicFields = new ArrayList<LogicField>();
    private final List<LogicField> primaryFields = new ArrayList<LogicField>();
    private static final String tableName = "NR_BPM_REPAIR_INS_ROWS";

    public StateErrorRecordsTempTable(WorkFlowType workFlowType) {
        this.primaryFields.add(DBTableEntity.newLogicField("MDCODE", 6, 40));
        if (WorkFlowType.FORM == workFlowType || WorkFlowType.GROUP == workFlowType) {
            this.primaryFields.add(DBTableEntity.newLogicField("FORMID", 6, 40));
        }
        this.logicFields.add(DBTableEntity.newLogicField("CURNODE", 6, 50));
        this.logicFields.add(DBTableEntity.newLogicField("PREVEVENT", 6, 50));
    }

    @Override
    public boolean isExist() {
        return this.exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public List<LogicField> getLogicFields() {
        return this.logicFields;
    }

    @Override
    public List<LogicField> getPrimaryFields() {
        return this.primaryFields;
    }

    @Override
    public List<LogicField> getAllLogicFields() {
        ArrayList<LogicField> allFields = new ArrayList<LogicField>();
        allFields.addAll(this.primaryFields);
        allFields.addAll(this.logicFields);
        return allFields;
    }

    public String getMdCode() {
        return "MDCODE";
    }
}

