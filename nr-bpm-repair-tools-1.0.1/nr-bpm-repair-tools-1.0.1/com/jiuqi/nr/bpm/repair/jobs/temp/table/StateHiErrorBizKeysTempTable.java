/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 */
package com.jiuqi.nr.bpm.repair.jobs.temp.table;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.bpm.repair.db.utils.DBTableEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StateHiErrorBizKeysTempTable
implements DBTableEntity {
    private static final String tableName = "NR_BPM_REPAIR_HI_BIZKEYS";
    private boolean exist;
    private final List<LogicField> logicFields = new ArrayList<LogicField>();

    public StateHiErrorBizKeysTempTable() {
        this.logicFields.add(DBTableEntity.newLogicField("BIZKEYORDER", 6, 100));
    }

    @Override
    public boolean isExist() {
        return this.exist;
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
        return Collections.emptyList();
    }

    @Override
    public List<LogicField> getAllLogicFields() {
        return this.logicFields;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}

