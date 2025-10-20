/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.sql.IConnectionProvider
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.tree.ObjectVistor
 *  com.jiuqi.bi.util.tree.TreeBuilder
 *  com.jiuqi.bi.util.tree.TreeBuilderFactory
 *  com.jiuqi.bi.util.tree.TreeNode
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.parameter;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetNotFoundException;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.sql.SQLConnectionProviderManager;
import com.jiuqi.bi.parameter.DataSetManagerUtils;
import com.jiuqi.bi.parameter.DataSourceException;
import com.jiuqi.bi.parameter.ParameterDataRowIterator;
import com.jiuqi.bi.parameter.ParameterObjectVistor;
import com.jiuqi.bi.parameter.extend.ds.field.DSFieldDataSourceModel;
import com.jiuqi.bi.parameter.extend.ds.hier.DSHierarchyDataSourceModel;
import com.jiuqi.bi.parameter.manager.DataSourceAttrBean;
import com.jiuqi.bi.parameter.manager.DataSourceMetaInfo;
import com.jiuqi.bi.parameter.manager.IDataSourceDataProvider;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import com.jiuqi.bi.parameter.model.ParameterHierarchyType;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.SmartSelector;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceValueModel;
import com.jiuqi.bi.sql.IConnectionProvider;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.tree.ObjectVistor;
import com.jiuqi.bi.util.tree.TreeBuilder;
import com.jiuqi.bi.util.tree.TreeBuilderFactory;
import com.jiuqi.bi.util.tree.TreeNode;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataSourceUtils {
    public static MemoryDataSet<ParameterColumnInfo> dataSourceValueModels2ParamterDataSet(List<DataSourceValueModel> values) throws DataSourceException {
        try {
            MemoryDataSet dataSet = new MemoryDataSet();
            dataSet.getMetadata().addColumn(new Column("code", 6));
            dataSet.getMetadata().addColumn(new Column("name", 6));
            if (values != null && values.size() != 0) {
                for (DataSourceValueModel value : values) {
                    String code = value.getCode();
                    String name = value.getName();
                    DataRow dataRow = dataSet.add();
                    dataRow.setString(0, code);
                    dataRow.setString(1, name);
                    dataRow.commit();
                }
            }
            return dataSet;
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    public static MemoryDataSet<ParameterColumnInfo> dataSourceValueModels2ParamterDataSet(List<DataSourceValueModel> values, DataSourceMetaInfo metaInfo) throws DataSourceException {
        try {
            MemoryDataSet dataSet = new MemoryDataSet();
            DataSourceAttrBean attrBean = metaInfo.getAttrBeans().get(0);
            dataSet.getMetadata().addColumn(new Column(attrBean.getKeyColName(), 6));
            dataSet.getMetadata().addColumn(new Column(attrBean.getNameColName(), 6));
            if (values != null && values.size() != 0) {
                for (DataSourceValueModel value : values) {
                    String code = value.getCode();
                    String name = value.getName();
                    DataRow dataRow = dataSet.add();
                    dataRow.setString(0, code);
                    dataRow.setString(1, name);
                    dataRow.commit();
                }
            }
            return dataSet;
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    public static JSONObject memoryDataSet2Json(MemoryDataSet<ParameterColumnInfo> dataSet) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray columnArray = new JSONArray();
        Metadata metaData = dataSet.getMetadata();
        List columns = metaData.getColumns();
        for (Column column : columns) {
            JSONObject columnJsObject = new JSONObject();
            columnJsObject.put("name", (Object)column.getName());
            columnJsObject.put("dataType", column.getDataType());
            columnArray.put((Object)columnJsObject);
        }
        jsonObject.put("metaData", (Object)columnArray);
        JSONArray valueArray = new JSONArray();
        for (DataRow row : dataSet) {
            JSONObject valueObject = new JSONObject();
            for (int i = 0; i < columns.size(); ++i) {
                String value = DataSourceUtils.formatDate(row.getValue(i));
                valueObject.put("" + i, (Object)value);
            }
            valueArray.put((Object)valueObject);
        }
        jsonObject.put("value", (Object)valueArray);
        return jsonObject;
    }

    public static MemoryDataSet<ParameterColumnInfo> json2MemoryDataSet(JSONObject value) throws JSONException {
        Metadata resultMetaDataSet = new Metadata(null);
        MemoryDataSet resultDataSet = new MemoryDataSet(ParameterColumnInfo.class, resultMetaDataSet);
        JSONArray columnArray = value.getJSONArray("metaData");
        for (int i = 0; i < columnArray.length(); ++i) {
            JSONObject columnJsObj = columnArray.getJSONObject(i);
            String name = columnJsObj.getString("name");
            int dataType = columnJsObj.getInt("dataType");
            Column column = new Column(name, dataType, (Object)new ParameterColumnInfo());
            resultMetaDataSet.addColumn(column);
        }
        JSONArray valueJsArray = value.getJSONArray("value");
        for (int i = 0; i < valueJsArray.length(); ++i) {
            JSONObject rowJsObj = valueJsArray.getJSONObject(i);
            DataRow resultRow = resultDataSet.add();
            for (int j = 0; j < resultMetaDataSet.size(); ++j) {
                if (rowJsObj.isNull(j + "")) continue;
                resultRow.setValue(j, rowJsObj.get(j + ""));
            }
        }
        return resultDataSet;
    }

    public static List<DataSourceValueModel> jsonArray2DataSourceValueModels(Object defalutValueJS) throws JSONException {
        ArrayList<DataSourceValueModel> defaultValueModels = new ArrayList<DataSourceValueModel>();
        JSONArray defalutValueJSArray = (JSONArray)defalutValueJS;
        for (int i = 0; i < defalutValueJSArray.length(); ++i) {
            Object value = defalutValueJSArray.get(i);
            if (!(value instanceof JSONObject)) continue;
            JSONObject valueModelJs = defalutValueJSArray.getJSONObject(i);
            DataSourceValueModel valueModel = new DataSourceValueModel();
            valueModel.fromJson(valueModelJs);
            defaultValueModels.add(valueModel);
        }
        return defaultValueModels;
    }

    public static JSONArray dataSourceValues2JsonArray(List<DataSourceValueModel> valueModels) throws JSONException {
        JSONArray valueModelJsArray = new JSONArray();
        for (DataSourceValueModel dataSourceValueModel : valueModels) {
            JSONObject dataSourceValueModelJSObj = new JSONObject();
            dataSourceValueModel.toJson(dataSourceValueModelJSObj);
            valueModelJsArray.put((Object)dataSourceValueModelJSObj);
        }
        return valueModelJsArray;
    }

    public static Connection getConnection(String connName) throws DataSourceException {
        try {
            IConnectionProvider provider = SQLConnectionProviderManager.getInstance().getConnectionProvider();
            if (provider == null) {
                throw new DataSourceException("\u672a\u6ce8\u518c\u6570\u636e\u8fde\u63a5\u63d0\u4f9b\u5668\uff0c\u65e0\u6cd5\u83b7\u53d6\u6570\u636e\u5e93\u8fde\u63a5");
            }
            return provider.open(connName);
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    public static MemoryDataSet<ParameterColumnInfo> getFirstValue(MemoryDataSet<ParameterColumnInfo> dataSet) throws DataSourceException {
        try {
            MemoryDataSet resultDataSet = new MemoryDataSet();
            Metadata biMetaDataSet = dataSet.getMetadata();
            Metadata resultMetaDataSet = resultDataSet.getMetadata();
            List columns = biMetaDataSet.getColumns();
            for (Column column : columns) {
                resultMetaDataSet.addColumn(new Column(column.getName(), column.getDataType(), (Object)new ParameterColumnInfo()));
            }
            Iterator it = dataSet.iterator();
            if (it.hasNext()) {
                DataRow dataRow = (DataRow)it.next();
                DataRow row = resultDataSet.add();
                for (int i = 0; i < columns.size(); ++i) {
                    row.setValue(i, dataRow.getValue(i));
                }
                row.commit();
            }
            return resultDataSet;
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    public static MemoryDataSet<ParameterColumnInfo> getParent_SonFirstValue(MemoryDataSet<ParameterColumnInfo> dataSet, ParameterModel parameterModel, TreeNode rootNode, MemoryDataSet<ParameterColumnInfo> resultDataSet) throws DataSourceException {
        TreeNode[] treeNodes = rootNode.getChildren();
        int colCount = resultDataSet.getMetadata().size();
        int treeLeafIndex = resultDataSet.getMetadata().indexOf("treeLeaf");
        if (treeNodes != null && treeNodes.length != 0) {
            TreeNode treeNode = treeNodes[0];
            if (parameterModel.isOnlyLeafSelectable()) {
                while (treeNode.getChildren() != null && treeNode.getChildren().length != 0) {
                    treeNode = treeNode.getChildren()[0];
                }
            }
            DataRow row = (DataRow)treeNode.getItem();
            DataRow dataRow = resultDataSet.add();
            DataSourceUtils.parameterDataRow2DataRow(row, dataRow, colCount);
            if (treeNode.getChildren() != null && treeNode.getChildren().length != 0) {
                dataRow.setInt(treeLeafIndex, 0);
            } else {
                dataRow.setInt(treeLeafIndex, 1);
            }
            return resultDataSet;
        }
        return null;
    }

    public static Map<String, String> parseParentValue(String parentValue, ParameterHierarchyType parameterHierarchyType) {
        HashMap<String, String> values = new HashMap<String, String>();
        if (StringUtils.isNotEmpty((String)parentValue)) {
            String[] parentValues;
            for (String value : parentValues = parentValue.split("\\|\\|")) {
                String[] everyParentValue = value.split("@");
                String key = everyParentValue[0].toUpperCase();
                String val = null;
                if (everyParentValue.length > 1) {
                    val = value.substring(key.length() + 1);
                }
                if (parameterHierarchyType.equals((Object)ParameterHierarchyType.NORMAL)) {
                    if (values.containsKey(everyParentValue[0])) continue;
                    values.put(key, val);
                    continue;
                }
                values.put(key, val);
            }
        }
        return values;
    }

    public static MemoryDataSet<ParameterColumnInfo> getMemoryDataSet(DataSourceMetaInfo metaInfo) {
        MemoryDataSet dataSet = new MemoryDataSet();
        Metadata metaData = dataSet.getMetadata();
        List<DataSourceAttrBean> attrBeans = metaInfo.getAttrBeans();
        for (DataSourceAttrBean dataSourceAttrBean : attrBeans) {
            if (!metaData.contains(dataSourceAttrBean.getKeyColName())) {
                metaData.addColumn(new Column(dataSourceAttrBean.getKeyColName(), 6));
            }
            if (metaData.contains(dataSourceAttrBean.getNameColName())) continue;
            metaData.addColumn(new Column(dataSourceAttrBean.getNameColName(), 6));
        }
        return dataSet;
    }

    public static boolean isContainsSearchValues(String value, List<String> searchValues) {
        if (StringUtils.isEmpty((String)value) || searchValues == null || searchValues.size() == 0) {
            return false;
        }
        value = value.toUpperCase();
        for (String searchValue : searchValues) {
            if (value.contains(searchValue = searchValue.toUpperCase())) continue;
            return false;
        }
        return true;
    }

    public static MemoryDataSet<ParameterColumnInfo> removeDataRowsNotInTargetDataSet(MemoryDataSet<ParameterColumnInfo> srcDataSet, MemoryDataSet<ParameterColumnInfo> tarDataSet, DataSourceMetaInfo dataSourceMetaInfo) throws DataSourceException {
        try {
            MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.getMemoryDataSet((Metadata<ParameterColumnInfo>)srcDataSet.getMetadata());
            HashMap<String, Integer> srcMetaDataMap = new HashMap<String, Integer>();
            HashMap<String, Integer> tarMetaDataMap = new HashMap<String, Integer>();
            block2: for (DataRow srcDataRow : srcDataSet) {
                for (DataRow tarDataRow : tarDataSet) {
                    if (!DataSourceUtils.isDataRowEqual(srcDataRow, tarDataRow, (Metadata<ParameterColumnInfo>)srcDataSet.getMetadata(), (Metadata<ParameterColumnInfo>)tarDataSet.getMetadata(), dataSourceMetaInfo, srcMetaDataMap, tarMetaDataMap)) continue;
                    DataSourceUtils.addDataRow2DataSet(srcDataRow, resultDataSet);
                    continue block2;
                }
            }
            return resultDataSet;
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    public static MemoryDataSet<ParameterColumnInfo> getMemoryDataSet(Metadata<ParameterColumnInfo> metadata) {
        MemoryDataSet resultDataSet = new MemoryDataSet();
        for (Column column : metadata) {
            resultDataSet.getMetadata().addColumn(column);
        }
        return resultDataSet;
    }

    private static boolean isDataRowEqual(DataRow row1, DataRow row2, Metadata<ParameterColumnInfo> metadata1, Metadata<ParameterColumnInfo> metadata2, DataSourceMetaInfo dataSourceMetaInfo, Map<String, Integer> metaDataMap1, Map<String, Integer> metaDataMap2) {
        List<DataSourceAttrBean> attrBeans = dataSourceMetaInfo.getAttrBeans();
        for (DataSourceAttrBean dataSourceAttrBean : attrBeans) {
            Object keyValue2;
            int keyIndex2;
            int keyIndex1;
            String keyColName = dataSourceAttrBean.getKeyColName();
            Integer keyIndexTep1 = metaDataMap1.get(keyColName);
            if (keyIndexTep1 != null) {
                keyIndex1 = keyIndexTep1;
            } else {
                keyIndex1 = metadata1.indexOf(keyColName);
                metaDataMap1.put(keyColName, keyIndex1);
            }
            Integer keyIndexTep2 = metaDataMap2.get(keyColName);
            if (keyIndexTep2 != null) {
                keyIndex2 = keyIndexTep2;
            } else {
                keyIndex2 = metadata2.indexOf(keyColName);
                metaDataMap2.put(keyColName, keyIndex2);
            }
            if (keyIndex1 == -1 || keyIndex2 == -1) continue;
            Object keyValue1 = row1.getValue(keyIndex1);
            int rs = DataType.compareObject((Object)keyValue1, (Object)(keyValue2 = row2.getValue(keyIndex2)));
            return rs == 0;
        }
        return true;
    }

    public static void addDataRow2DataSet(DataRow row, MemoryDataSet<ParameterColumnInfo> dataSet) throws DataSetException {
        DataRow resultRow = dataSet.add();
        for (int i = 0; i < dataSet.getMetadata().size(); ++i) {
            resultRow.setValue(i, row.getValue(i));
        }
        resultRow.commit();
    }

    public static MemoryDataSet<ParameterColumnInfo> sortByAppointValues(List<DataSourceValueModel> dataSourceValues, MemoryDataSet<ParameterColumnInfo> dataSet, DataSourceMetaInfo dataSourceMetaInfo) throws DataSourceException {
        try {
            if (dataSourceValues.size() == 0 || dataSet.size() == 0) {
                return dataSet;
            }
            MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.getMemoryDataSet((Metadata<ParameterColumnInfo>)dataSet.getMetadata());
            DataSourceAttrBean attrBean = dataSourceMetaInfo.getAttrBeans().get(0);
            String keyColName = attrBean.getKeyColName();
            for (DataSourceValueModel dataSourceModel : DataSourceUtils.adapt2DataSourceValues(dataSourceValues, keyColName)) {
                for (DataRow dataRow : dataSet) {
                    String code = dataSourceModel.getCode();
                    if (!code.equals(dataRow.getString(dataSet.getMetadata().indexOf(keyColName)))) continue;
                    DataSourceUtils.addDataRow2DataSet(dataRow, resultDataSet);
                }
            }
            return resultDataSet;
        }
        catch (Exception e) {
            throw new DataSourceException("\u6309\u7167\u6307\u5b9a\u7684\u6210\u5458\u6392\u5e8f\u7ed3\u679c\u96c6\u65f6\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    public static MemoryDataSet<ParameterColumnInfo> sortByAppointValues(MemoryDataSet<ParameterColumnInfo> dataSourceValues, MemoryDataSet<ParameterColumnInfo> dataSet, DataSourceMetaInfo dataSourceMetaInfo) throws DataSourceException {
        try {
            if (dataSourceValues.size() == 0 || dataSet.size() == 0) {
                return dataSet;
            }
            HashMap<String, Integer> srcMetaDataMap = new HashMap<String, Integer>();
            HashMap<String, Integer> tarMetaDataMap = new HashMap<String, Integer>();
            MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.getMemoryDataSet((Metadata<ParameterColumnInfo>)dataSet.getMetadata());
            block2: for (DataRow dataSourceValueRow : dataSourceValues) {
                for (DataRow dataRow : dataSet) {
                    if (!DataSourceUtils.isDataRowEqual(dataSourceValueRow, dataRow, (Metadata<ParameterColumnInfo>)dataSourceValues.getMetadata(), (Metadata<ParameterColumnInfo>)dataSet.getMetadata(), dataSourceMetaInfo, srcMetaDataMap, tarMetaDataMap)) continue;
                    DataSourceUtils.addDataRow2DataSet(dataRow, resultDataSet);
                    continue block2;
                }
            }
            return resultDataSet;
        }
        catch (Exception e) {
            throw new DataSourceException("\u6309\u7167\u6307\u5b9a\u7684\u6210\u5458\u6392\u5e8f\u7ed3\u679c\u96c6\u65f6\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    public static MemoryDataSet<ParameterColumnInfo> sortByAppointValues(SmartSelector smartSelector, MemoryDataSet<ParameterColumnInfo> dataSet, DataSourceMetaInfo dataSourceMetaInfo) throws DataSourceException {
        if (smartSelector.getType() != SmartSelector.SelectType.FIXED) {
            return dataSet;
        }
        List<SmartSelector.SelectedValue> selectedValues = smartSelector.getSelectedValues();
        try {
            if (selectedValues.size() == 0 || dataSet.size() == 0) {
                return dataSet;
            }
            MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.getMemoryDataSet((Metadata<ParameterColumnInfo>)dataSet.getMetadata());
            DataSourceAttrBean attrBean = dataSourceMetaInfo.getAttrBeans().get(0);
            String keyColName = attrBean.getKeyColName();
            for (SmartSelector.SelectedValue selectedValue : selectedValues) {
                for (DataRow dataRow : dataSet) {
                    String code = selectedValue.value.toString();
                    if (!code.equals(dataRow.getString(dataSet.getMetadata().indexOf(keyColName)))) continue;
                    DataSourceUtils.addDataRow2DataSet(dataRow, resultDataSet);
                }
            }
            return resultDataSet;
        }
        catch (Exception e) {
            throw new DataSourceException("\u6309\u7167\u6307\u5b9a\u7684\u6210\u5458\u6392\u5e8f\u7ed3\u679c\u96c6\u65f6\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    public static MemoryDataSet<ParameterColumnInfo> buildParameter_SonDataSet(MemoryDataSet<ParameterColumnInfo> allValues, String parentFieldName, DataSourceAttrBean attrBean) throws DataSourceException {
        try {
            Metadata metaData = allValues.getMetadata();
            MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.getMemoryDataSet((Metadata<ParameterColumnInfo>)metaData);
            if (resultDataSet.getMetadata().indexOf("treeLeaf") == -1) {
                resultDataSet.getMetadata().addColumn(new Column("treeLeaf", 3));
            }
            TreeBuilder treeBuilder = TreeBuilderFactory.createParentTreeBuilder((ObjectVistor)new ParameterObjectVistor(metaData.indexOf(attrBean.getKeyColName()), metaData.indexOf(parentFieldName)));
            treeBuilder.setSortMode(1);
            TreeNode rootNode = treeBuilder.build((Iterator)new ParameterDataRowIterator(allValues));
            return DataSourceUtils.buildTreeNodes2MemoryDataSet(resultDataSet, rootNode.getChildren());
        }
        catch (Exception e) {
            throw new DataSourceException("\u6784\u9020\u7236\u5b50\u5c42\u7ea7\u6570\u636e\u96c6\u5931\u8d25," + e.getMessage(), e);
        }
    }

    public static MemoryDataSet<ParameterColumnInfo> biDataSet2ParameterMemoryDataSet(BIDataSet dataSet) throws DataSourceException {
        try {
            MemoryDataSet resultDataSet = new MemoryDataSet();
            Metadata<BIDataSetFieldInfo> biMetaDataSet = dataSet.getMetadata();
            Metadata resultMetaDataSet = resultDataSet.getMetadata();
            List columns = biMetaDataSet.getColumns();
            for (Column column : columns) {
                resultMetaDataSet.addColumn(new Column(column.getName(), column.getDataType(), (Object)new ParameterColumnInfo()));
            }
            for (BIDataRow biDataRow : dataSet) {
                DataRow row = resultDataSet.add();
                for (int i = 0; i < columns.size(); ++i) {
                    row.setValue(i, biDataRow.getValue(i));
                }
                row.commit();
            }
            return resultDataSet;
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    public static List<DataSourceValueModel> dataSet2DataSourceValueModelsForDsField(MemoryDataSet<ParameterColumnInfo> dataSet, DSFieldDataSourceModel dataSourceModel) throws DataSourceException {
        try {
            ArrayList<DataSourceValueModel> results = new ArrayList<DataSourceValueModel>();
            Metadata metaData = dataSet.getMetadata();
            String fieldName = dataSourceModel.getDsFieldName();
            String dsName = dataSourceModel.getDsName();
            IDataSetManager dataSetManager = DataSetManagerUtils.createDataSetManager();
            DSField dsField = dataSetManager.findField(dsName, dataSourceModel.getDsType(), fieldName);
            String[] keyAndName = DataSourceUtils.getDsFieldKeyAndNameCol(dsField);
            String keyName = keyAndName[0];
            String nameName = keyAndName[1];
            Iterator it = dataSet.iterator();
            int keyIndex = metaData.indexOf(keyName);
            int nameIndex = metaData.indexOf(nameName);
            while (it.hasNext()) {
                DataRow row = (DataRow)it.next();
                DataSourceValueModel value = new DataSourceValueModel();
                value.setCode(row.getString(keyIndex));
                value.setName(row.getString(nameIndex));
                results.add(value);
            }
            return results;
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    public static String[] getDsFieldKeyAndNameCol(DSField dsField) {
        String keyCol = null;
        String nameCol = null;
        FieldType fieldType = dsField.getFieldType();
        if (fieldType.equals((Object)FieldType.DESCRIPTION) || fieldType.equals((Object)FieldType.MEASURE)) {
            keyCol = dsField.getName();
            nameCol = dsField.getName();
        } else {
            keyCol = dsField.getKeyField();
            nameCol = dsField.getNameField();
        }
        return new String[]{keyCol, nameCol};
    }

    public static Object format(String value, com.jiuqi.bi.dataset.DataType dataType) {
        if (dataType.equals((Object)com.jiuqi.bi.dataset.DataType.BOOLEAN)) {
            if (!StringUtils.isEmpty((String)value)) {
                if (value.equalsIgnoreCase("true")) {
                    return true;
                }
                if (value.equalsIgnoreCase("false")) {
                    return false;
                }
            }
            return Boolean.getBoolean(value);
        }
        if (dataType.equals((Object)com.jiuqi.bi.dataset.DataType.DATETIME)) {
            int year = Integer.valueOf(value.substring(0, 4));
            int month = Integer.valueOf(value.substring(4, 6)) - 1;
            int day = Integer.valueOf(value.substring(6, 8));
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, 0, 0, 0);
            calendar.set(10, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            return calendar;
        }
        if (dataType.equals((Object)com.jiuqi.bi.dataset.DataType.DOUBLE)) {
            return Double.valueOf(value);
        }
        if (dataType.equals((Object)com.jiuqi.bi.dataset.DataType.INTEGER)) {
            return Integer.valueOf(value);
        }
        if (dataType.equals((Object)com.jiuqi.bi.dataset.DataType.STRING)) {
            return value;
        }
        return value;
    }

    public static String formatExpressionValue(String value, com.jiuqi.bi.dataset.DataType dataType) {
        if (dataType.equals((Object)com.jiuqi.bi.dataset.DataType.INTEGER)) {
            Double doubleValue = Double.valueOf(value);
            int intValue = doubleValue.intValue();
            return String.valueOf(intValue);
        }
        return value;
    }

    public static List<Integer> parseStructureCodes(String structureCode) {
        ArrayList<Integer> structureCodes = new ArrayList<Integer>();
        if (StringUtils.isNotEmpty((String)structureCode)) {
            String[] codes;
            for (String code : codes = structureCode.split(",")) {
                structureCodes.add(Integer.valueOf(code));
            }
        }
        return structureCodes;
    }

    public static MemoryDataSet<ParameterColumnInfo> getFirstLevelData(TreeNode rootNode, DataSourceMetaInfo metaInfo) {
        MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.getMemoryDataSet(metaInfo);
        TreeNode[] children = rootNode.getChildren();
        int colCount = resultDataSet.getMetadata().size();
        if (children != null && children.length != 0) {
            for (TreeNode treeNode : children) {
                DataRow row = (DataRow)treeNode.getItem();
                DataSourceUtils.parameterDataRow2DataRow(row, resultDataSet.add(), colCount);
            }
        }
        return resultDataSet;
    }

    public static MemoryDataSet<ParameterColumnInfo> getAllSubLevelData(TreeNode rootNode, String parentValue, int level, DataSourceMetaInfo metaInfo) {
        MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.getMemoryDataSet(metaInfo);
        if (resultDataSet.getMetadata().indexOf("treeLeaf") == -1) {
            resultDataSet.getMetadata().addColumn(new Column("treeLeaf", 3));
        }
        ArrayList<TreeNode> parentNodes = new ArrayList<TreeNode>();
        DataSourceUtils.recursiveFindParentTreeNode(rootNode, parentValue, level, parentNodes);
        ArrayList<TreeNode> children = new ArrayList<TreeNode>();
        DataSourceUtils.recursiveFindAllChildrenTreeNode((TreeNode)parentNodes.get(0), children);
        DataSourceUtils.buildTreeNodes2MemoryDataSet(resultDataSet, children.toArray(new TreeNode[children.size()]));
        return resultDataSet;
    }

    private static void recursiveFindAllChildrenTreeNode(TreeNode treeNode, List<TreeNode> children) {
        for (TreeNode child : treeNode.getChildren()) {
            children.add(child);
            DataSourceUtils.recursiveFindAllChildrenTreeNode(child, children);
        }
    }

    public static MemoryDataSet<ParameterColumnInfo> getDirectSubLevelData(TreeNode rootNode, String parentValue, int level, DataSourceMetaInfo metaInfo) {
        ArrayList<TreeNode> parentNodes = new ArrayList<TreeNode>();
        DataSourceUtils.recursiveFindParentTreeNode(rootNode, parentValue, level, parentNodes);
        MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.getMemoryDataSet(metaInfo);
        if (parentNodes.isEmpty()) {
            System.out.println("parentValue->" + parentValue + "\t level->" + level);
            return resultDataSet;
        }
        if (resultDataSet.getMetadata().indexOf("treeLeaf") == -1) {
            resultDataSet.getMetadata().addColumn(new Column("treeLeaf", 3));
        }
        return DataSourceUtils.buildTreeNodes2MemoryDataSet(resultDataSet, ((TreeNode)parentNodes.get(0)).getChildren());
    }

    private static void recursiveFindParentTreeNode(TreeNode node, String parentValue, int level, List<TreeNode> parentNodes) {
        if (node.level() == level) {
            DataRow row = (DataRow)node.getItem();
            if (row.getString(0).equals(parentValue)) {
                parentNodes.add(node);
                return;
            }
        } else {
            for (TreeNode treeNode : node.getChildren()) {
                DataSourceUtils.recursiveFindParentTreeNode(treeNode, parentValue, level, parentNodes);
            }
        }
    }

    private static MemoryDataSet<ParameterColumnInfo> buildTreeNodes2MemoryDataSet(MemoryDataSet<ParameterColumnInfo> resultDataSet, TreeNode[] treeNodes) {
        int colCount = resultDataSet.getMetadata().size();
        if (treeNodes != null && treeNodes.length != 0) {
            int treeLeafIndex = resultDataSet.getMetadata().indexOf("treeLeaf");
            for (TreeNode treeNode : treeNodes) {
                DataRow row = (DataRow)treeNode.getItem();
                DataRow dataRow = resultDataSet.add();
                DataSourceUtils.parameterDataRow2DataRow(row, dataRow, colCount);
                if (treeNode.getChildren() != null && treeNode.getChildren().length != 0) {
                    dataRow.setInt(treeLeafIndex, 0);
                    continue;
                }
                dataRow.setInt(treeLeafIndex, 1);
            }
        }
        return resultDataSet;
    }

    private static void parameterDataRow2DataRow(DataRow parameterRow, DataRow dataRow, int colCount) {
        for (int i = 0; i < colCount; ++i) {
            Object value;
            if (parameterRow.getBuffer().length < colCount) {
                if (i == colCount - 1) {
                    dataRow.setValue(i, parameterRow.getValue(i - 1));
                    continue;
                }
                value = parameterRow.getValue(i);
                dataRow.setValue(i, value);
                continue;
            }
            value = parameterRow.getValue(i);
            dataRow.setValue(i, value);
        }
    }

    public static MemoryDataSet<ParameterColumnInfo> buildStructureCodeDataSet(MemoryDataSet<ParameterColumnInfo> allValues, String structureCode, boolean shortCode) throws DataSourceException {
        try {
            MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.getMemoryDataSet((Metadata<ParameterColumnInfo>)allValues.getMetadata());
            if (resultDataSet.getMetadata().indexOf("treeLeaf") == -1) {
                resultDataSet.getMetadata().addColumn(new Column("treeLeaf", 3));
            }
            TreeBuilder treeBuilder = TreeBuilderFactory.createStructTreeBuilder((ObjectVistor)new ParameterObjectVistor(), (String)structureCode, (boolean)shortCode);
            treeBuilder.setSortMode(1);
            TreeNode rootNode = treeBuilder.build((Iterator)new ParameterDataRowIterator(allValues));
            return DataSourceUtils.buildTreeNodes2MemoryDataSet(resultDataSet, rootNode.getChildren());
        }
        catch (Exception e) {
            throw new DataSourceException("\u6784\u9020\u7ed3\u6784\u7f16\u7801\u6570\u636e\u96c6\u5931\u8d25," + e.getMessage(), e);
        }
    }

    public static MemoryDataSet<ParameterColumnInfo> removeDuplicateValues(MemoryDataSet<ParameterColumnInfo> allValues) throws DataSetException {
        MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.getMemoryDataSet((Metadata<ParameterColumnInfo>)allValues.getMetadata());
        for (DataRow dataRow : allValues) {
            if (DataSourceUtils.inDataSet(dataRow, resultDataSet)) continue;
            DataSourceUtils.addDataRow2DataSet(dataRow, resultDataSet);
        }
        return resultDataSet;
    }

    private static boolean inDataSet(DataRow row, MemoryDataSet<ParameterColumnInfo> dataSet) {
        for (DataRow dataRow : dataSet) {
            if (!DataSourceUtils.isDataRowEqual(dataRow, row, dataSet.getMetadata().size())) continue;
            return true;
        }
        return false;
    }

    private static boolean isDataRowEqual(DataRow dataRow, DataRow row, int colCount) {
        for (int i = 0; i < colCount; ++i) {
            Object dataRowValue = dataRow.getValue(i);
            Object rowValue = row.getValue(i);
            if (dataRowValue == null || rowValue == null || dataRowValue.equals(rowValue)) continue;
            return false;
        }
        return true;
    }

    public static MemoryDataSet<ParameterColumnInfo> formatDataSet(MemoryDataSet<ParameterColumnInfo> dataSet, DataSourceMetaInfo metaInfo, IDataSourceDataProvider dataProvider) throws DataSourceException {
        List<DataSourceAttrBean> attrBeans = metaInfo.getAttrBeans();
        if (attrBeans.size() != 0) {
            for (DataSourceAttrBean dataSourceAttrBean : attrBeans) {
                DataSourceUtils.addTitleColumn2DataSet(dataSet, dataSourceAttrBean, dataSet, dataProvider);
            }
        }
        return dataSet;
    }

    private static void addTitleColumn2DataSet(MemoryDataSet<ParameterColumnInfo> resultDataSet, DataSourceAttrBean attrBean, MemoryDataSet<ParameterColumnInfo> dataSet, IDataSourceDataProvider dataProvider) throws DataSourceException {
        String nameCol = attrBean.getNameColName();
        int nameIndex = dataSet.getMetadata().indexOf(nameCol);
        if (nameIndex == -1) {
            return;
        }
        String titleCol = nameCol + "_TITLE";
        resultDataSet.getMetadata().addColumn(new Column(titleCol, 6));
        int titleIndex = dataSet.getMetadata().indexOf(titleCol);
        for (DataRow dataRow : dataSet) {
            String name = dataRow.getString(nameIndex);
            dataRow.setString(titleIndex, dataProvider.formatValue(name, attrBean));
        }
    }

    public static MemoryDataSet<ParameterColumnInfo> getDataSourceValuesDataSet(MemoryDataSet<ParameterColumnInfo> dataSourceValues, DataSourceMetaInfo metaInfo) {
        MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.getMemoryDataSet(metaInfo);
        List<DataSourceAttrBean> attrBeans = metaInfo.getAttrBeans();
        for (DataRow row : dataSourceValues) {
            DataRow resultRow = resultDataSet.add();
            for (DataSourceAttrBean attrBean : attrBeans) {
                int keyCol = dataSourceValues.getMetadata().indexOf(attrBean.getKeyColName() + "_CODE");
                if (keyCol == -1) {
                    keyCol = dataSourceValues.getMetadata().indexOf(attrBean.getKeyColName());
                }
                resultRow.setValue(resultDataSet.getMetadata().indexOf(attrBean.getKeyColName()), row.getValue(keyCol));
            }
        }
        return resultDataSet;
    }

    public static Object formatDSParentValue(String parentValue, String parentFieldName, DataSourceModel dataSourceModel) throws BIDataSetNotFoundException, BIDataSetException {
        IDataSetManager dataSetMgr = DataSetManagerUtils.createDataSetManager();
        String dsName = "";
        String dsType = "";
        if (dataSourceModel instanceof DSHierarchyDataSourceModel) {
            dsName = ((DSHierarchyDataSourceModel)dataSourceModel).getDsName();
            dsType = ((DSHierarchyDataSourceModel)dataSourceModel).getDsType();
        } else if (dataSourceModel instanceof DSFieldDataSourceModel) {
            dsName = ((DSFieldDataSourceModel)dataSourceModel).getDsName();
            dsType = ((DSFieldDataSourceModel)dataSourceModel).getDsType();
        }
        DSField dsField = dataSetMgr.findField(dsName, dsType, parentFieldName);
        return DataSourceUtils.format(parentValue, com.jiuqi.bi.dataset.DataType.valueOf(dsField.getValType()));
    }

    public static boolean isInteger(String value) {
        if (StringUtils.isEmpty((String)value)) {
            return false;
        }
        try {
            Integer.valueOf(value);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static String formatDate(Object valObj) {
        if (valObj == null) {
            return null;
        }
        if (valObj instanceof Calendar) {
            return new SimpleDateFormat("yyyyMMdd").format(((Calendar)valObj).getTime());
        }
        return valObj.toString();
    }

    public static List<DataSourceValueModel> adapt2DataSourceValues(List<DataSourceValueModel> values) {
        return DataSourceUtils.adapt2DataSourceValues(values, null);
    }

    public static List<DataSourceValueModel> adapt2DataSourceValues(List<DataSourceValueModel> values, String attrName) {
        ArrayList<DataSourceValueModel> valueModels = new ArrayList<DataSourceValueModel>();
        if (values.isEmpty()) {
            return valueModels;
        }
        if (values.get(0) instanceof DataSourceValueModel) {
            return values;
        }
        if (values.get(0) instanceof String) {
            for (DataSourceValueModel obj : values) {
                String value = (String)((Object)obj);
                DataSourceValueModel valueModel = new DataSourceValueModel();
                valueModel.setAttrName(null);
                valueModel.setCode(value);
                valueModel.setName(value);
                valueModels.add(valueModel);
            }
        }
        return valueModels;
    }
}

