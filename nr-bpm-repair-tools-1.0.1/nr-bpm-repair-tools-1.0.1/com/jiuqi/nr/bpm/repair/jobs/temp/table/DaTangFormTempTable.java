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

public class DaTangFormTempTable
implements DBTableEntity {
    public static final String tableName = "DT_TEMP_CQ_TO_GL_FORM_MAP";
    public static final String fm_key_CQ = "fm_key_CQ";
    public static final String fm_code_CQ = "fm_code_CQ";
    public static final String fm_title_CQ = "fm_title_CQ";
    public static final String fm_key_GL = "fm_key_GL";
    public static final String fm_code_GL = "fm_code_GL";
    public static final String fm_title_GL = "fm_title_GL";
    private boolean exist;

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
        ArrayList<LogicField> logicFields = new ArrayList<LogicField>();
        logicFields.add(DBTableEntity.newLogicField(fm_key_CQ, 6, 40));
        logicFields.add(DBTableEntity.newLogicField(fm_code_CQ, 6, 20));
        logicFields.add(DBTableEntity.newLogicField(fm_title_CQ, 6, 200));
        logicFields.add(DBTableEntity.newLogicField(fm_key_GL, 6, 40));
        logicFields.add(DBTableEntity.newLogicField(fm_code_GL, 6, 20));
        logicFields.add(DBTableEntity.newLogicField(fm_title_GL, 6, 200));
        return logicFields;
    }

    @Override
    public List<LogicField> getPrimaryFields() {
        return Collections.emptyList();
    }

    @Override
    public List<LogicField> getAllLogicFields() {
        return Collections.emptyList();
    }
}

