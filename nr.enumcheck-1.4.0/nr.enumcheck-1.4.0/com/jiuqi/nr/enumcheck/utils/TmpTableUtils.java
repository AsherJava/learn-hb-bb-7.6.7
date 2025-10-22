/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.temptable.BaseTempTableDefine
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.enumcheck.utils;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.enumcheck.utils.EnumCheckTempTableDefine;
import com.jiuqi.nr.enumcheck.utils.TempTableRes;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TmpTableUtils {
    @Autowired
    private ITempTableManager tempTableManager;

    public TempTableRes createTempTableAndInsertData(DimensionCollection dims, Map<String, List<String>> extFieldData, String entityDimName, int extFieldCount) throws SQLException {
        EnumCheckTempTableDefine myTable = new EnumCheckTempTableDefine();
        ArrayList<String> colName = new ArrayList<String>();
        ArrayList<LogicField> logicFields = new ArrayList<LogicField>();
        ArrayList<String> pk = new ArrayList<String>();
        Iterator iterator = dims.iterator();
        if (iterator.hasNext()) {
            DimensionValueSet dim = (DimensionValueSet)iterator.next();
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
        for (int i = 0; i < extFieldCount; ++i) {
            LogicField logicField = new LogicField();
            logicField.setFieldName("FD" + i);
            logicField.setDataType(6);
            logicField.setSize(6);
            logicFields.add(logicField);
        }
        myTable.setLogicFields(logicFields);
        myTable.setPks(pk);
        ITempTable tempTable = this.tempTableManager.getTempTableByMeta((BaseTempTableDefine)myTable);
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        List dimensionCombinations = dims.getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            Object[] batchArray = new Object[colName.size() + extFieldCount];
            for (int i = 0; i < colName.size(); ++i) {
                batchArray[i] = "ADJUST".equals(colName.get(i)) ? Integer.valueOf(0) : dimensionCombination.getValue((String)colName.get(i));
            }
            if (!extFieldData.isEmpty()) {
                String dwValue = (String)dimensionCombination.getValue(entityDimName);
                List<String> extDatas = extFieldData.get(dwValue);
                for (int i = 0; i < extDatas.size(); ++i) {
                    batchArray[colName.size() + i] = extDatas.get(i);
                }
            }
            batchValues.add(batchArray);
        }
        tempTable.insertRecords(batchValues);
        return new TempTableRes(colName, tempTable);
    }

    public Boolean dropTempTable(ITempTable tempTable) throws Exception {
        Boolean isDrop = false;
        tempTable.close();
        isDrop = true;
        return isDrop;
    }
}

