/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.temptable.BaseTempTableDefine
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.integritycheck.utils;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.integritycheck.utils.IntegrityCheckTempTableDefine;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TmpTableUtils {
    @Autowired
    private ITempTableManager tempTableManager;
    @Autowired
    private JdbcTemplate jdbcTpl;

    public ITempTable createTempTableAndInsertData(List<DimensionCombination> dimensionCombinations) throws SQLException {
        IntegrityCheckTempTableDefine myTable = new IntegrityCheckTempTableDefine();
        ArrayList<String> colName = new ArrayList<String>();
        ArrayList<LogicField> logicFields = new ArrayList<LogicField>();
        ArrayList<String> pk = new ArrayList<String>();
        Iterator<DimensionCombination> iterator = dimensionCombinations.iterator();
        if (iterator.hasNext()) {
            DimensionCombination dimensionCombination = iterator.next();
            DimensionValueSet dim = dimensionCombination.toDimensionValueSet();
            for (int i = 0; i < dim.size(); ++i) {
                String dimName = dim.getName(i);
                colName.add(dimName);
                LogicField logicField = new LogicField();
                logicField.setFieldName(dimName);
                logicField.setDataType(6);
                logicField.setSize(40);
                logicField.setNullable(false);
                logicFields.add(logicField);
                pk.add(dimName);
            }
        }
        myTable.setLogicFields(logicFields);
        myTable.setPks(pk);
        ITempTable tempTable = this.tempTableManager.getTempTableByMeta((BaseTempTableDefine)myTable);
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            Object[] batchArray = new Object[colName.size()];
            for (int i = 0; i < colName.size(); ++i) {
                batchArray[i] = "ADJUST".equals(colName.get(i)) ? Integer.valueOf(0) : dimensionCombination.getValue((String)colName.get(i));
            }
            batchValues.add(batchArray);
        }
        tempTable.insertRecords(batchValues);
        return tempTable;
    }

    public ITempTable createTempTable(List<DimensionCombination> dimensionCombinations) throws SQLException {
        IntegrityCheckTempTableDefine myTable = new IntegrityCheckTempTableDefine();
        ArrayList<String> colName = new ArrayList<String>();
        ArrayList<LogicField> logicFields = new ArrayList<LogicField>();
        ArrayList<String> pk = new ArrayList<String>();
        Iterator<DimensionCombination> iterator = dimensionCombinations.iterator();
        if (iterator.hasNext()) {
            DimensionCombination dimensionCombination = iterator.next();
            DimensionValueSet dim = dimensionCombination.toDimensionValueSet();
            for (int i = 0; i < dim.size(); ++i) {
                String dimName = dim.getName(i);
                colName.add(dimName);
                LogicField logicField = new LogicField();
                logicField.setFieldName(dimName);
                logicField.setDataType(6);
                logicField.setSize(40);
                logicField.setNullable(false);
                logicFields.add(logicField);
                pk.add(dimName);
            }
        }
        myTable.setLogicFields(logicFields);
        myTable.setPks(pk);
        return this.tempTableManager.getTempTableByMeta((BaseTempTableDefine)myTable);
    }

    public void deleteTempTableData(ITempTable tempTable) {
        String sql = "DELETE FROM " + tempTable.getTableName();
        this.jdbcTpl.execute(sql);
    }

    public void insertTempTableData(ITempTable tempTable, List<DimensionCombination> dimensionCombinations) throws SQLException {
        if (null == tempTable || null == dimensionCombinations || dimensionCombinations.isEmpty()) {
            return;
        }
        ArrayList<String> colName = new ArrayList<String>();
        DimensionValueSet dim = dimensionCombinations.get(0).toDimensionValueSet();
        for (int i = 0; i < dim.size(); ++i) {
            String dimName = dim.getName(i);
            colName.add(dimName);
        }
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            Object[] batchArray = new Object[colName.size()];
            for (int i = 0; i < colName.size(); ++i) {
                batchArray[i] = "ADJUST".equals(colName.get(i)) ? Integer.valueOf(0) : dimensionCombination.getValue((String)colName.get(i));
            }
            batchValues.add(batchArray);
        }
        tempTable.insertRecords(batchValues);
    }

    public boolean dropTempTable(ITempTable tempTable) throws Exception {
        boolean isDrop = false;
        tempTable.close();
        isDrop = true;
        return isDrop;
    }
}

