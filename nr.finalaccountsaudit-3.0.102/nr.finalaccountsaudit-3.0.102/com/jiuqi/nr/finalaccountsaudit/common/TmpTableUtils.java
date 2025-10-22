/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.temp.ITempTableMeta
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.definition.paramcheck.DataBaseLimitModeProvider
 */
package com.jiuqi.nr.finalaccountsaudit.common;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.temp.ITempTableMeta;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.definition.paramcheck.DataBaseLimitModeProvider;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.internal.controller.MainDimData;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TmpTableUtils {
    @Autowired
    DataBaseLimitModeProvider dataBaseLimitModeProvider;
    @Autowired
    private ITempTableManager tempTableManager;
    private String fname = "CODE";
    private String tempTableType = "NR_M_CHECK";

    public String getFName() {
        return this.fname;
    }

    public String getTempTableType() {
        return this.tempTableType;
    }

    public ITempTable createTempTable() throws Exception {
        ITempTableMeta tempTableMeta = new ITempTableMeta(){

            public String getType() {
                return TmpTableUtils.this.tempTableType;
            }

            public List<LogicField> getLogicFields() {
                ArrayList<LogicField> logicFields = new ArrayList<LogicField>();
                LogicField logicField = new LogicField();
                logicField.setFieldName(TmpTableUtils.this.fname);
                logicField.setDataType(6);
                logicField.setSize(40);
                logicFields.add(logicField);
                return logicFields;
            }

            public List<String> getPrimaryKeyFields() {
                ArrayList<String> primaryKeyFields = new ArrayList<String>();
                primaryKeyFields.add(TmpTableUtils.this.fname);
                return primaryKeyFields;
            }
        };
        return this.tempTableManager.getTempTableByMeta(tempTableMeta);
    }

    public ITempTable createTempTable(final int extFieldCount) throws Exception {
        if (extFieldCount == 0) {
            return this.createTempTable();
        }
        ITempTableMeta tempTableMeta = new ITempTableMeta(){

            public String getType() {
                return TmpTableUtils.this.tempTableType;
            }

            public List<LogicField> getLogicFields() {
                ArrayList<LogicField> logicFields = new ArrayList<LogicField>();
                LogicField logicField = new LogicField();
                logicField.setFieldName(TmpTableUtils.this.fname);
                logicField.setDataType(6);
                logicField.setSize(40);
                logicFields.add(logicField);
                for (int i = 0; i < extFieldCount; ++i) {
                    logicField = new LogicField();
                    logicField.setFieldName("FD" + i);
                    logicField.setDataType(6);
                    logicField.setSize(6);
                    logicFields.add(logicField);
                }
                return logicFields;
            }

            public List<String> getPrimaryKeyFields() {
                ArrayList<String> primaryKeyFields = new ArrayList<String>();
                primaryKeyFields.add(TmpTableUtils.this.fname);
                return primaryKeyFields;
            }
        };
        return this.tempTableManager.getTempTableByMeta(tempTableMeta);
    }

    public int prepareTempTableData(ITempTable tempTable, List<String> fValues) throws Exception {
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (String fValue : fValues) {
            Object[] batchArray = new Object[]{fValue};
            batchValues.add(batchArray);
        }
        tempTable.insertRecords(batchValues);
        return fValues.size();
    }

    public int prepareTempTableData(ITempTable tempTable, MainDimData mainDimData) throws Exception {
        if (!mainDimData.hasExtData()) {
            List<String> mainDimValue = mainDimData.getMainDimValue();
            ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
            for (String fValue : mainDimValue) {
                Object[] batchArray = new Object[]{fValue};
                batchValues.add(batchArray);
            }
            tempTable.insertRecords(batchValues);
            return mainDimValue.size();
        }
        List<String> mainDimValue = mainDimData.getMainDimValue();
        List<List<String>> extFdValues = mainDimData.getExtFdValues();
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (int i = 0; i < mainDimValue.size(); ++i) {
            Object[] batchArray = new Object[1 + extFdValues.size()];
            batchArray[0] = mainDimValue.get(i);
            for (int idx = 0; idx < extFdValues.size(); ++idx) {
                batchArray[1 + idx] = mainDimData.getExtValue(idx, i);
            }
            batchValues.add(batchArray);
        }
        tempTable.insertRecords(batchValues);
        return mainDimValue.size();
    }

    public Boolean dropTempTable(ITempTable tempTable) throws Exception {
        Boolean isDrop = false;
        tempTable.close();
        isDrop = true;
        return isDrop;
    }
}

