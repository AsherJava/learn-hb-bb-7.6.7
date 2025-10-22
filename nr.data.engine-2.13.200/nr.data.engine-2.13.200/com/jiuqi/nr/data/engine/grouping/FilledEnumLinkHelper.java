/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.BitMaskAndNullValue
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.DataTypes
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.intf.impl.DataRowImpl
 *  com.jiuqi.np.dataengine.intf.impl.GroupingTableImpl
 *  com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl
 *  com.jiuqi.np.dataengine.intf.impl.RowItem
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.data.engine.grouping;

import com.jiuqi.np.dataengine.common.BitMaskAndNullValue;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.GroupingTableImpl;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.intf.impl.RowItem;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.engine.grouping.AdvancedGatherHelper;
import com.jiuqi.nr.data.engine.grouping.GroupTreeRow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilledEnumLinkHelper {
    private static final Logger logger = LoggerFactory.getLogger(FilledEnumLinkHelper.class);

    private static DataRowImpl newDataRow(ReadonlyTableImpl tableImpl, int colCount) {
        ArrayList<Object> rowDatas = new ArrayList<Object>();
        for (int colIndex = 0; colIndex < colCount; ++colIndex) {
            rowDatas.add(null);
        }
        DimensionSet rowDimensions = tableImpl.getRowDimensions();
        DimensionValueSet rowKeys = rowDimensions == null ? new DimensionValueSet() : new DimensionValueSet(rowDimensions);
        DataRowImpl dataRowImpl = new DataRowImpl(tableImpl, rowKeys, rowDatas);
        return dataRowImpl;
    }

    public static void filledGroupingDataByEnumLinks(GroupingTableImpl tableImpl, boolean wantDetail, List<Integer> groupColumns, List<FieldDefine> enumFields, List<List<String>> enumObjects) {
        if (enumFields == null || enumObjects == null) {
            return;
        }
        if (enumFields.size() <= 0 || enumObjects.size() <= 0 || groupColumns.size() <= 0) {
            return;
        }
        try {
            List dataRowImpls = tableImpl.getAllDataRows();
            ArrayList<DataRowImpl> resultRows = new ArrayList<DataRowImpl>();
            IFieldsInfo fieldsInfo = tableImpl.getFieldsInfo();
            IFieldsInfo fieldsInfoImpl = tableImpl.getFieldsInfo();
            int colCount = fieldsInfoImpl.getFieldCount();
            ArrayList<Integer> newGroupColumns = new ArrayList<Integer>(groupColumns);
            HashMap grpByColsEffectiveInGroupingId = tableImpl.getGrpByColsEffectiveInGroupingId();
            FilledEnumLinkHelper.initGroupParam(enumFields, fieldsInfoImpl, newGroupColumns);
            LinkedHashMap<Integer, Integer> groupColMap = FilledEnumLinkHelper.getGroupingFields(newGroupColumns, fieldsInfo, enumFields);
            FilledEnumLinkHelper.appendDataRowImpl(resultRows, dataRowImpls, newGroupColumns, wantDetail, tableImpl, enumFields, enumObjects, groupColMap, colCount, grpByColsEffectiveInGroupingId);
            tableImpl.setAllDataRows(resultRows);
            tableImpl.setTotalCount(resultRows.size());
        }
        catch (Exception e) {
            logger.error("\u679a\u4e3e\u586b\u5145\u6570\u636e\u884c\u751f\u6210\u51fa\u9519", e);
            return;
        }
    }

    private static void appendDataRowImpl(List<DataRowImpl> resultRows, List<DataRowImpl> dataRowImpls, List<Integer> newGroupColumns, boolean wantDetail, GroupingTableImpl tableImpl, List<FieldDefine> enumFields, List<List<String>> enumObjects, LinkedHashMap<Integer, Integer> groupColMap, int colCount, HashMap<Integer, BitMaskAndNullValue> grpByColsEffectiveInGroupingId) {
        Map<Integer, LinkedHashMap<String, LinkedList<RowItem>>> rowMap = FilledEnumLinkHelper.getGroupingRowMap(dataRowImpls, newGroupColumns);
        int cubeCount = newGroupColumns.size();
        int groupingFlag = wantDetail ? -1 : 0;
        HashSet<Integer> rowIndexes = new HashSet<Integer>();
        for (int index = cubeCount; index >= groupingFlag; --index) {
            int currentFlag = index < 0 ? -1 : (int)Math.pow(2.0, index) - 1;
            LinkedList<Object> filledRows = new LinkedList();
            if (grpByColsEffectiveInGroupingId.size() < enumFields.size() && currentFlag < 0) {
                HashMap<Integer, BitMaskAndNullValue> newGrpByColsEffectiveInGroupingId = FilledEnumLinkHelper.resetColEffectiveGroupValue(newGroupColumns, tableImpl.getFieldsInfo(), grpByColsEffectiveInGroupingId);
                filledRows = FilledEnumLinkHelper.getFilledRows(currentFlag, tableImpl, colCount, enumFields, enumObjects, groupColMap, newGrpByColsEffectiveInGroupingId);
            } else {
                filledRows = FilledEnumLinkHelper.getFilledRows(currentFlag, tableImpl, colCount, enumFields, enumObjects, groupColMap, grpByColsEffectiveInGroupingId);
            }
            HashMap groupingRows = rowMap.get(currentFlag);
            if (groupingRows == null || groupingRows.size() <= 0) {
                resultRows.addAll(filledRows);
                continue;
            }
            for (DataRowImpl dataRowImpl : filledRows) {
                String rowKeys = FilledEnumLinkHelper.getRowKeys(dataRowImpl, newGroupColumns);
                LinkedList listRows = (LinkedList)groupingRows.get(rowKeys);
                if (listRows == null || listRows.size() <= 0) {
                    resultRows.add(dataRowImpl);
                    continue;
                }
                for (RowItem rowItem : listRows) {
                    DataRowImpl currentRow = rowItem.getDataRowImpl();
                    resultRows.add(currentRow);
                    rowIndexes.add(rowItem.getRowIndex());
                }
            }
        }
        for (int rowIndex = 0; rowIndex < dataRowImpls.size(); ++rowIndex) {
            if (rowIndexes.contains(rowIndex)) continue;
            DataRowImpl currentRow = dataRowImpls.get(rowIndex);
            resultRows.add(currentRow);
        }
        List<GroupTreeRow> groupTreeRows = AdvancedGatherHelper.getGroupTreeRows(resultRows, tableImpl.getGrpByColsEffectiveInGroupingId());
        resultRows.clear();
        AdvancedGatherHelper.getDataRows(groupTreeRows, resultRows, cubeCount);
    }

    private static HashMap<Integer, BitMaskAndNullValue> resetColEffectiveGroupValue(List<Integer> groupColumns, IFieldsInfo fieldsInfoImpl, HashMap<Integer, BitMaskAndNullValue> grpByColsEffectiveInGroupingId) {
        HashMap<Integer, BitMaskAndNullValue> grpByColsEffectiveInGrouping = new HashMap<Integer, BitMaskAndNullValue>();
        for (int columnIndex : groupColumns) {
            int groupIndex = groupColumns.indexOf(columnIndex);
            int bitMask = 1 << groupColumns.size() - groupIndex - 1;
            FieldDefine field = fieldsInfoImpl.getFieldDefine(columnIndex);
            AbstractData nullValue = DataTypes.getNullValue((int)field.getType().getValue());
            grpByColsEffectiveInGrouping.put(columnIndex, new BitMaskAndNullValue(bitMask, nullValue));
        }
        return grpByColsEffectiveInGrouping;
    }

    private static void initGroupParam(List<FieldDefine> enumFields, IFieldsInfo fieldsInfoImpl, List<Integer> newGroupColumns) {
        HashMap<String, FieldDefine> enumFieldMap = new HashMap<String, FieldDefine>();
        for (FieldDefine enumField : enumFields) {
            enumFieldMap.put(enumField.getKey(), enumField);
        }
        ArrayList<Integer> newGroupColum = new ArrayList<Integer>();
        int count = fieldsInfoImpl.getFieldCount();
        for (int index = 0; index < count; ++index) {
            String key = fieldsInfoImpl.getFieldDefine(index).getKey();
            if (!enumFieldMap.containsKey(key)) continue;
            newGroupColum.add(index);
        }
        newGroupColumns.clear();
        newGroupColumns.addAll(newGroupColum);
    }

    private static LinkedList<DataRowImpl> getFilledRows(int currentFlag, GroupingTableImpl tableImpl, int colCount, List<FieldDefine> enumFields, List<List<String>> enumObjects, LinkedHashMap<Integer, Integer> groupColMap, HashMap<Integer, BitMaskAndNullValue> grpByColsEffectiveInGroupingId) {
        HashSet<String> existRows = new HashSet<String>();
        LinkedList<DataRowImpl> filledRows = new LinkedList<DataRowImpl>();
        for (List<String> lineObject : enumObjects) {
            String rowKeys = FilledEnumLinkHelper.getFilledRowKeys(lineObject, groupColMap, grpByColsEffectiveInGroupingId, currentFlag);
            if (existRows.contains(rowKeys)) continue;
            existRows.add(rowKeys);
            DataRowImpl dataRowImpl = FilledEnumLinkHelper.newDataRow((ReadonlyTableImpl)tableImpl, colCount);
            dataRowImpl.setGroupingFlag(currentFlag);
            dataRowImpl.setFilledRow(true);
            for (Map.Entry<Integer, Integer> colMap : groupColMap.entrySet()) {
                BitMaskAndNullValue nullValue = grpByColsEffectiveInGroupingId.get(colMap.getKey());
                if (currentFlag >= 0 && (nullValue == null || (currentFlag & nullValue.getBitMask()) != 0)) continue;
                dataRowImpl.directSet(colMap.getKey().intValue(), (Object)lineObject.get(colMap.getValue()));
            }
            filledRows.add(dataRowImpl);
        }
        return filledRows;
    }

    private static String getFilledRowKeys(List<String> lineObject, LinkedHashMap<Integer, Integer> groupColMap, HashMap<Integer, BitMaskAndNullValue> grpByColsEffectiveInGroupingId, int currentFlag) {
        StringBuilder rowKeys = new StringBuilder();
        for (Map.Entry<Integer, Integer> colMap : groupColMap.entrySet()) {
            BitMaskAndNullValue nullValue = grpByColsEffectiveInGroupingId.get(colMap.getKey());
            if (nullValue != null && (nullValue == null || currentFlag < 0 || (currentFlag & nullValue.getBitMask()) <= 0)) {
                rowKeys.append(lineObject.get(colMap.getValue()));
            }
            rowKeys.append("#^$");
        }
        return rowKeys.toString();
    }

    private static Map<Integer, LinkedHashMap<String, LinkedList<RowItem>>> getGroupingRowMap(List<DataRowImpl> dataRowImpls, List<Integer> groupColumns) {
        HashMap<Integer, LinkedHashMap<String, LinkedList<RowItem>>> rowMap = new HashMap<Integer, LinkedHashMap<String, LinkedList<RowItem>>>();
        for (int rowIndex = 0; rowIndex < dataRowImpls.size(); ++rowIndex) {
            String rowKeys;
            LinkedList<RowItem> rowList;
            DataRowImpl dataRowImpl = dataRowImpls.get(rowIndex);
            RowItem rowItem = new RowItem(dataRowImpl, rowIndex);
            LinkedHashMap<String, LinkedList<RowItem>> groupingRows = (LinkedHashMap<String, LinkedList<RowItem>>)rowMap.get(dataRowImpl.getGroupingFlag());
            if (groupingRows == null) {
                groupingRows = new LinkedHashMap<String, LinkedList<RowItem>>();
                rowMap.put(dataRowImpl.getGroupingFlag(), groupingRows);
            }
            if ((rowList = (LinkedList<RowItem>)groupingRows.get(rowKeys = FilledEnumLinkHelper.getRowKeys(dataRowImpl, groupColumns))) == null) {
                rowList = new LinkedList<RowItem>();
                groupingRows.put(rowKeys, rowList);
            }
            rowList.add(rowItem);
        }
        return rowMap;
    }

    private static String getRowKeys(DataRowImpl dataRowImpl, List<Integer> groupColumns) {
        StringBuilder rowKeys = new StringBuilder();
        for (Integer grpColumn : groupColumns) {
            Object colValue = dataRowImpl.internalGetValue(grpColumn.intValue());
            if (colValue == null) {
                colValue = "";
            }
            rowKeys.append(colValue);
            rowKeys.append("#^$");
        }
        return rowKeys.toString();
    }

    private static LinkedHashMap<Integer, Integer> getGroupingFields(List<Integer> newGroupColumns, IFieldsInfo fieldsInfo, List<FieldDefine> enumFields) {
        LinkedHashMap<Integer, Integer> groupColMap = new LinkedHashMap<Integer, Integer>();
        HashMap<String, Integer> enumIndexMap = FilledEnumLinkHelper.getEnumIndexMap(enumFields);
        for (Integer groupColumn : newGroupColumns) {
            FieldDefine groupField = fieldsInfo.getFieldDefine(groupColumn.intValue());
            if (groupField == null || !enumIndexMap.containsKey(groupField.getKey())) continue;
            groupColMap.put(groupColumn, enumIndexMap.get(groupField.getKey()));
        }
        return groupColMap;
    }

    private static HashMap<String, Integer> getEnumIndexMap(List<FieldDefine> enumFields) {
        HashMap<String, Integer> enumIndexMap = new HashMap<String, Integer>();
        for (int index = 0; index < enumFields.size(); ++index) {
            enumIndexMap.put(enumFields.get(index).getKey(), index);
        }
        return enumIndexMap;
    }

    private static String getBizKeyStr(List<String> enumObject, List<FieldDefine> enumFields) {
        if (enumObject == null || enumObject.size() != enumFields.size()) {
            return "";
        }
        StringBuilder bizKeyStrBuf = new StringBuilder();
        for (int index = 0; index < enumObject.size(); ++index) {
            String enumItem;
            if (index > 0) {
                bizKeyStrBuf.append("#^$");
            }
            if ((enumItem = enumObject.get(index)) == null) continue;
            bizKeyStrBuf.append(enumObject.get(index));
        }
        return bizKeyStrBuf.toString();
    }

    private static Map<String, List<RowItem>> getRowMap(List<DataRowImpl> dataRowImpls, List<FieldDefine> enumFields) throws DataTypeException {
        HashMap<String, List<RowItem>> rowMap = new HashMap<String, List<RowItem>>();
        for (int rowIndex = 0; rowIndex < dataRowImpls.size(); ++rowIndex) {
            DataRowImpl dataRowImpl = dataRowImpls.get(rowIndex);
            if (dataRowImpl.getGroupingFlag() >= 0) continue;
            String bizKey = FilledEnumLinkHelper.getEntityBizKeyStr(dataRowImpl, enumFields);
            ArrayList<RowItem> rowList = (ArrayList<RowItem>)rowMap.get(bizKey);
            if (rowList == null) {
                rowList = new ArrayList<RowItem>();
                rowMap.put(bizKey, rowList);
            }
            RowItem rowItem = new RowItem(dataRowImpl, rowIndex);
            rowList.add(rowItem);
        }
        return rowMap;
    }

    private static String getEntityBizKeyStr(DataRowImpl dataRowImpl, List<FieldDefine> enumFields) throws DataTypeException {
        StringBuilder bizKeyStrBuf = new StringBuilder();
        boolean addDot = false;
        for (FieldDefine enumField : enumFields) {
            if (addDot) {
                bizKeyStrBuf.append("#^$");
            }
            addDot = true;
            bizKeyStrBuf.append(dataRowImpl.getAsString(enumField));
        }
        return bizKeyStrBuf.toString();
    }
}

