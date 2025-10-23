/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.attachment.message.FixedFieldAndGroupInfo
 *  com.jiuqi.nr.attachment.message.FloatFieldAndGroupInfo
 *  com.jiuqi.nr.common.temptable.BaseTempTableDefine
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.common.temptable.IndexField
 *  com.jiuqi.nr.common.temptable.IndexMeta
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.Basic
 */
package com.jiuqi.nr.datacopy.util;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.attachment.message.FixedFieldAndGroupInfo;
import com.jiuqi.nr.attachment.message.FloatFieldAndGroupInfo;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.common.temptable.IndexField;
import com.jiuqi.nr.common.temptable.IndexMeta;
import com.jiuqi.nr.datacopy.param.CopyDataTableDefine;
import com.jiuqi.nr.datacopy.param.CopyDataTempTable;
import com.jiuqi.nr.datacopy.param.TempTableReturnInfo;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.Basic;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class TempTableUtils {
    @Autowired
    private ITempTableManager tempTableManager;

    public TempTableReturnInfo createTempTable(CopyDataTableDefine source, List<CopyDataTableDefine> targets) throws SQLException {
        ArrayList<String> sourceColName = new ArrayList<String>();
        ArrayList<String> targetColName = new ArrayList<String>();
        CopyDataTableDefine target = CollectionUtils.isEmpty(targets) ? source : targets.get(0);
        ITempTable tempTable = this.getTempTableByDv(source, target, sourceColName, targetColName);
        return new TempTableReturnInfo(tempTable, sourceColName, targetColName);
    }

    public void insertData(TempTableReturnInfo tempTableInfo, Map<DimensionValueSet, DimensionValueSet> source2TargetMap) throws SQLException {
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        List<String> sourceColName = tempTableInfo.getSourceColName();
        List<String> targetColName = tempTableInfo.getTargetColName();
        ITempTable tempTable = tempTableInfo.getTempTable();
        for (Map.Entry<DimensionValueSet, DimensionValueSet> entry : source2TargetMap.entrySet()) {
            DimensionValueSet sourceValueSet = entry.getKey();
            DimensionValueSet targetValueSet = entry.getValue();
            Object[] values = new Object[sourceColName.size() + targetColName.size()];
            int i = 0;
            for (String sourceCol : sourceColName) {
                values[i] = sourceCol.equals("MDCODE") ? sourceValueSet.getValue("MD_ORG") : sourceValueSet.getValue(sourceCol);
                ++i;
            }
            for (String targetCol : targetColName) {
                values[i] = targetCol.equals("MDCODE") ? targetValueSet.getValue("MD_ORG") : targetValueSet.getValue(targetCol);
                ++i;
            }
            batchValues.add(values);
        }
        if (tempTable != null) {
            tempTable.insertRecords(batchValues);
        }
    }

    public void clearData(TempTableReturnInfo tempTableInfo) throws SQLException {
        ITempTable tempTable = tempTableInfo.getTempTable();
        if (tempTable != null) {
            tempTable.deleteAll();
        }
    }

    public ITempTable getKeyValueTempTable() throws SQLException {
        return this.tempTableManager.getKeyValueTempTable();
    }

    private ITempTable getTempTableByDv(CopyDataTableDefine source, CopyDataTableDefine target, List<String> sourceColName, List<String> targetColName) throws SQLException {
        ArrayList<LogicField> logicFields = new ArrayList<LogicField>();
        ArrayList<String> pk = new ArrayList<String>();
        List<DataField> sourceFields = this.getDimDataFields(source);
        List<DataField> targetFields = this.getDimDataFields(target);
        for (DataField dataField : sourceFields) {
            String code = dataField.getCode();
            pk.add(code);
            sourceColName.add(code);
            this.addColumn(code, logicFields);
        }
        ArrayList<IndexMeta> indexes = new ArrayList<IndexMeta>();
        for (DataField dataField : targetFields) {
            String fieldName = dataField.getCode();
            targetColName.add(fieldName);
            this.addColumn("TARGET_" + fieldName, logicFields);
        }
        IndexMeta indexMeta = this.createIndexMeta(targetColName);
        indexes.add(indexMeta);
        CopyDataTempTable tempTableDefine = new CopyDataTempTable();
        tempTableDefine.setLogicFields(logicFields);
        tempTableDefine.setPrimaryKeyFields(pk);
        tempTableDefine.setIndexes(indexes);
        return this.createTempTable(tempTableDefine);
    }

    public ITempTable createTempTable(CopyDataTempTable tempTableDefine) throws SQLException {
        return this.tempTableManager.getTempTableByMeta((BaseTempTableDefine)tempTableDefine);
    }

    private List<DataField> getDimDataFields(CopyDataTableDefine tableDefine) {
        ArrayList<DataField> sourceFields = new ArrayList<DataField>();
        sourceFields.add(tableDefine.getUnitField());
        sourceFields.add(tableDefine.getPeriodField());
        if (!CollectionUtils.isEmpty(tableDefine.getPublicDimFields())) {
            sourceFields.addAll(tableDefine.getPublicDimFields());
        }
        return sourceFields;
    }

    private IndexMeta createIndexMeta(List<String> targetColName) {
        IndexMeta indexMeta = new IndexMeta();
        ArrayList<IndexField> indexFields = new ArrayList<IndexField>();
        for (String targetCol : targetColName) {
            IndexField indexField = new IndexField();
            indexField.setFieldName("TARGET_" + targetCol);
            indexField.setSortType(1);
            indexFields.add(indexField);
        }
        indexMeta.setIndexFields(indexFields);
        return indexMeta;
    }

    private void addColumn(String dimName, List<LogicField> logicFields) {
        LogicField logicField = new LogicField();
        logicField.setFieldName(dimName);
        logicField.setDataType(6);
        logicField.setSize(40);
        logicField.setNullable(false);
        logicFields.add(logicField);
    }

    public List<Object[]> buildFloatTempValues(List<FloatFieldAndGroupInfo> floatFieldAndGroupInfos, ITempTable tempTable, List<DataField> sourceAttachmentFields) {
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        List columns = tempTable.getMeta().getLogicFields();
        Map<String, DataField> collect = sourceAttachmentFields.stream().collect(Collectors.toMap(Basic::getCode, a -> a));
        for (FloatFieldAndGroupInfo info : floatFieldAndGroupInfos) {
            Object[] rowValue = new Object[columns.size()];
            LinkedHashMap fieldGroupMap = info.getFieldGroupMap();
            for (int i = 0; i < columns.size(); ++i) {
                LogicField logicField = (LogicField)columns.get(i);
                rowValue[i] = logicField.getFieldName().equals("BIZKEYORDER") ? info.getBizKey() : fieldGroupMap.get(collect.get(logicField.getFieldName()).getKey());
            }
            batchValues.add(rowValue);
        }
        return batchValues;
    }

    public List<Object[]> buildFixedTempValues(List<FixedFieldAndGroupInfo> fixedFieldAndGroupInfos, ITempTable tempTable, List<DataField> sourceAttachmentFields) {
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        List columns = tempTable.getMeta().getLogicFields();
        List primaryKeyFields = tempTable.getMeta().getPrimaryKeyFields();
        Map<String, DataField> collect = sourceAttachmentFields.stream().collect(Collectors.toMap(Basic::getCode, a -> a));
        for (FixedFieldAndGroupInfo info : fixedFieldAndGroupInfos) {
            Object[] rowValue = new Object[columns.size()];
            LinkedHashMap fieldGroupMap = info.getFieldGroupMap();
            Object[] dimValue = info.getDims();
            int index = 0;
            for (int i = 0; i < primaryKeyFields.size(); ++i) {
                rowValue[index] = dimValue[index];
                ++index;
            }
            for (DataField dataField : sourceAttachmentFields) {
                rowValue[index] = fieldGroupMap.get(collect.get(dataField.getCode()).getKey());
                ++index;
            }
            batchValues.add(rowValue);
        }
        return batchValues;
    }
}

