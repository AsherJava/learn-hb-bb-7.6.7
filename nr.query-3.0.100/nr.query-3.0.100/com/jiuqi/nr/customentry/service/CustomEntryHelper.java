/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  io.netty.util.internal.StringUtil
 *  org.json.JSONObject
 */
package com.jiuqi.nr.customentry.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.customentry.define.CustomEntryCellData;
import com.jiuqi.nr.customentry.define.CustomEntryContext;
import com.jiuqi.nr.customentry.define.CustomEntryData;
import com.jiuqi.nr.customentry.define.CustomEntryType;
import com.jiuqi.nr.customentry.define.IndexObj;
import com.jiuqi.nr.customentry.service.CellData;
import com.jiuqi.nr.customentry.service.CustomEntrySaveContext;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.query.block.ColumnWidth;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.block.QuerySelectField;
import com.jiuqi.nr.query.common.DateUtils;
import com.jiuqi.nr.query.service.QueryGridDefination;
import com.jiuqi.nr.query.service.QueryGridFactory;
import com.jiuqi.nr.query.service.impl.QueryHelper;
import io.netty.util.internal.StringUtil;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CustomEntryHelper {
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    private String fieldTag;
    private static final Logger logger = LoggerFactory.getLogger(CustomEntryHelper.class);
    private final int floatOrderIncrease = 1;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String saveData(CustomEntryContext queryContext) {
        JSONObject result = new JSONObject();
        QueryGridFactory queryGridCache = new QueryGridFactory();
        String gridKey = QueryGridFactory.buildKey(queryContext.getBlock().getId(), false);
        try {
            HashMap<String, DataRegionDefine> regionDefineMap = new HashMap<String, DataRegionDefine>();
            QueryGridDefination defina = queryGridCache.get(gridKey);
            if (defina == null) {
                defina = new QueryGridDefination(queryContext.getBlock(), false);
            }
            CustomEntrySaveContext saveContext = this.prepare(queryContext, defina);
            Map<String, Map<String, List<Map<String, Map<String, CellData>>>>> saveValues = saveContext.saveValues;
            Boolean hasBizKey = saveContext.hasBizKey;
            HashMap<Integer, IndexObj> indexRows = saveContext.indexRows;
            Integer rowIndex = 0;
            for (String entrytype : saveValues.keySet()) {
                Map<String, List<Map<String, Map<String, CellData>>>> entryTypeValues = saveValues.get(entrytype);
                if (entryTypeValues == null) continue;
                Map<String, Map<String, FieldDefine>> entyFields = saveContext.regionFields.get(entrytype);
                if (entyFields == null) {
                    LogHelper.info((String)"CustomEntry", (String)"\u81ea\u5b9a\u4e49\u5f55\u5165\u4fdd\u5b58\u5931\u8d25", (String)(entrytype + "\u64cd\u4f5c\u6a21\u5f0f\u4e0b\u65e0\u4efb\u4f55\u6307\u6807"));
                    result.put("status", false);
                    String string = result.toString();
                    return string;
                }
                for (String region : entryTypeValues.keySet()) {
                    List<Map<String, Map<String, CellData>>> regionValueRows = entryTypeValues.get(region);
                    for (Map<String, Map<String, CellData>> regionValues : regionValueRows) {
                        JSONObject rowStatus = new JSONObject();
                        if (regionValues == null) continue;
                        Optional<CellData> dataOptional = null;
                        for (Map.Entry<String, Map<String, CellData>> stringMapEntry : regionValues.entrySet()) {
                            dataOptional = stringMapEntry.getValue().values().stream().filter(it -> it.value != null).findFirst();
                        }
                        if (dataOptional != null && !dataOptional.isPresent()) {
                            rowStatus.put("status", false);
                            continue;
                        }
                        IDataQuery dataQuery = this.getDataQuery(region);
                        DataRegionDefine regionDefine = this.runtimeView.queryDataRegionDefine(region);
                        regionDefineMap.put(region, regionDefine);
                        Map<String, FieldDefine> fields = entyFields.get(region);
                        if (fields == null) {
                            LogHelper.info((String)"CustomEntry", (String)"\u81ea\u5b9a\u4e49\u5f55\u5165\u4fdd\u5b58\u5931\u8d25", (String)("\u533a\u57df\u4e2d\u4e0b\u65e0\u4efb\u4f55\u6307\u6807\uff1a" + region));
                            result.put("status", false);
                            String string = result.toString();
                            return string;
                        }
                        HashMap<String, Integer> fieldIndex = new HashMap<String, Integer>();
                        for (FieldDefine field : fields.values()) {
                            int index = dataQuery.addColumn(field);
                            fieldIndex.put(field.getKey(), index);
                        }
                        boolean allowDuplicateKey = false;
                        boolean isMoreRegion = false;
                        try {
                            if (regionDefineMap.containsKey(region)) {
                                String fieldKey;
                                DataRegionDefine dataRegionDefine = (DataRegionDefine)regionDefineMap.get(region);
                                allowDuplicateKey = dataRegionDefine.getAllowDuplicateKey();
                                boolean bl = isMoreRegion = ((DataRegionDefine)regionDefineMap.get(region)).getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE;
                                if (((DataRegionDefine)regionDefineMap.get(region)).getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE && !defina.floatOrderField.isEmpty() && (fieldKey = defina.floatOrderMap.get(region)) != null) {
                                    Optional<FieldDefine> optional = defina.floatOrderField.stream().filter(i -> i.getKey().equals(fieldKey)).findFirst();
                                }
                            }
                        }
                        catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
                        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, defina.block.getBlockInfo().getFormSchemeKey());
                        context.setEnv((IFmlExecEnvironment)environment);
                        dataQuery.setMasterKeys(new DimensionValueSet());
                        boolean hasModified = false;
                        for (String rowkeys : regionValues.keySet()) {
                            Map<String, CellData> values = regionValues.get(rowkeys);
                            String finalRowkeys = rowkeys;
                            Optional<IndexObj> indexObj = indexRows.values().stream().filter(i -> finalRowkeys.equals(i.getRowKeys())).findFirst();
                            if (indexObj.isPresent()) {
                                IndexObj obj = indexObj.get();
                                rowIndex = obj.getRowIndex();
                                rowStatus.put("rowIndex", (Object)obj.getRowIndex());
                            }
                            rowkeys = rowkeys.replace(":", "=").replace("\"", "").replace("{", "").replace("}", "");
                            DimensionValueSet dimension = new DimensionValueSet();
                            dimension.parseString(rowkeys);
                            Object row = null;
                            ArrayList queryRows = new ArrayList();
                            IDataUpdator updatorModify = null;
                            switch (CustomEntryType.valueOf(entrytype)) {
                                case MODIFY: {
                                    int index;
                                    CellData celldata;
                                    Optional<FieldDefine> first;
                                    String fieldKey;
                                    boolean needNewInsert;
                                    String per = null;
                                    for (String fcode : fields.keySet()) {
                                        if (!values.containsKey(fcode)) continue;
                                        per = values.get((Object)fcode).period;
                                        break;
                                    }
                                    Boolean hasNoneRow = false;
                                    if (defina.hasMoreRegion) {
                                        QueryHelper queryHelper = new QueryHelper();
                                        hasNoneRow = queryHelper.rowDuQuery(region, dimension);
                                    }
                                    boolean bl = needNewInsert = hasNoneRow != null && hasNoneRow != false;
                                    if (dimension.hasValue("FLOATORDER")) {
                                        Optional<FieldDefine> first2;
                                        String fieldKey2;
                                        CellData celldata2 = new CellData();
                                        celldata2.value = dimension.getValue("FLOATORDER").toString();
                                        celldata2.period = per;
                                        if (!defina.floatOrderField.isEmpty() && (fieldKey2 = defina.floatOrderMap.get(region)) != null && (first2 = defina.floatOrderField.stream().filter(i -> i.getKey().equals(fieldKey2)).findFirst()).isPresent()) {
                                            FieldDefine fieldDefine = first2.get();
                                            int index2 = dataQuery.addColumn(fieldDefine);
                                            fieldIndex.put(fieldDefine.getKey(), index2);
                                            values.put(fieldDefine.getKey(), celldata2);
                                            fields.put(fieldDefine.getKey(), fieldDefine);
                                        }
                                    } else if (needNewInsert && !defina.floatOrderField.isEmpty() && (fieldKey = defina.floatOrderMap.get(region)) != null && (first = defina.floatOrderField.stream().filter(i -> i.getKey().equals(fieldKey)).findFirst()).isPresent()) {
                                        FieldDefine fieldDefine = first.get();
                                        celldata = new CellData();
                                        String order = null;
                                        if (dimension.hasValue("FLOATORDER")) {
                                            order = dimension.getValue("FLOATORDER").toString();
                                        } else {
                                            order = this.generateFloatOrder(region, dimension, fieldDefine);
                                            dimension.setValue("FLOATORDER", (Object)order);
                                        }
                                        index = dataQuery.addColumn(fieldDefine);
                                        fieldIndex.put(fieldDefine.getKey(), index);
                                        celldata.value = order;
                                        celldata.period = per;
                                        values.put(fieldDefine.getKey(), celldata);
                                        fields.put(fieldDefine.getKey(), fieldDefine);
                                    }
                                    updatorModify = dataQuery.openForUpdate(context);
                                    if (!hasBizKey.booleanValue() && !allowDuplicateKey && regionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE || needNewInsert) {
                                        hasModified = this.addRow(dataQuery, dimension, fields, values, fieldIndex, updatorModify, hasBizKey, rowStatus, context);
                                        break;
                                    }
                                    hasModified = this.modifyRow(defina, dimension, fields, values, fieldIndex, updatorModify, rowStatus);
                                    break;
                                }
                                case ADD: {
                                    int index;
                                    CellData celldata;
                                    String period = null;
                                    for (String fcode : fields.keySet()) {
                                        if (values.get(fcode) == null) continue;
                                        period = values.get((Object)fcode).period;
                                        dimension.setValue("DATATIME", (Object)values.get((Object)fcode).period);
                                        break;
                                    }
                                    if (regionDefineMap.containsKey(region) && ((DataRegionDefine)regionDefineMap.get(region)).getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE && !defina.floatOrderField.isEmpty()) {
                                        for (FieldDefine fieldDefine : defina.floatOrderField) {
                                            celldata = new CellData();
                                            String order = null;
                                            order = dimension.hasValue("FLOATORDER") ? dimension.getValue("FLOATORDER").toString() : this.generateFloatOrder(region, dimension, fieldDefine);
                                            celldata.value = order;
                                            celldata.period = period;
                                            values.put(fieldDefine.getKey(), celldata);
                                            fields.put(fieldDefine.getKey(), fieldDefine);
                                            index = dataQuery.addColumn(fieldDefine);
                                            fieldIndex.put(fieldDefine.getKey(), index);
                                        }
                                    }
                                    updatorModify = dataQuery.openForUpdate(context);
                                    hasModified = this.addRow(dataQuery, dimension, fields, values, fieldIndex, updatorModify, hasBizKey, rowStatus, context);
                                    break;
                                }
                                case DELETE: {
                                    updatorModify = dataQuery.openForUpdate(context);
                                    updatorModify.addDeletedRow(dimension);
                                    hasModified = true;
                                    break;
                                }
                                default: {
                                    updatorModify = dataQuery.openForUpdate(context);
                                }
                            }
                            try {
                                if (!hasModified) continue;
                                boolean commited = updatorModify.commitChanges(true);
                                rowStatus.put("status", commited);
                            }
                            catch (Exception e) {
                                rowStatus.put("status", false);
                                logger.error(e.getMessage(), e);
                            }
                        }
                        result.put(rowIndex.toString(), (Object)rowStatus);
                    }
                }
            }
            String string = result.toString();
            return string;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            result.put("message", (Object)ex.getMessage());
            LogHelper.error((String)"CustomEntry", (String)"\u6570\u636e\u4fdd\u5b58\u5931\u8d25", (String)("\u5931\u8d25\u539f\u56e0\uff1a" + ex.getMessage()));
        }
        finally {
            queryGridCache.remove(gridKey);
        }
        return result.toString();
    }

    private String generateFloatOrder(String key, DimensionValueSet dimension, FieldDefine fieldDefine) {
        QueryHelper queryHelper = new QueryHelper();
        Double order = queryHelper.queryFloatOrder(key, fieldDefine, dimension);
        if (order == null) {
            try {
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                return String.valueOf(random.nextDouble() * 1000.0);
            }
            catch (NoSuchAlgorithmException e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        }
        order = order + 1.0;
        return order.toString();
    }

    private boolean addRow(IDataQuery dataQuery, DimensionValueSet dimension, Map<String, FieldDefine> fields, Map<String, CellData> values, Map<String, Integer> fieldIndex, IDataUpdator updatorModify, Boolean hasBizkey, JSONObject rowStatus, ExecutorContext context) {
        try {
            IDataRow row = null;
            try {
                IDataUpdator updatorAdd = dataQuery.openForUpdate(context);
                row = updatorAdd.addInsertedRow(dimension);
                this.setRow(row, fields, values, fieldIndex, null);
                boolean addcommited = updatorAdd.commitChanges(true);
                if (addcommited) {
                    rowStatus.put("isAdd", (Object)"success");
                    rowStatus.put("status", true);
                } else {
                    rowStatus.put("isAdd", (Object)"failed");
                }
                return false;
            }
            catch (SQLException ex) {
                BatchUpdateException be = (BatchUpdateException)ex.getCause();
                SQLException nex = be.getNextException();
                rowStatus.put("message", (Object)(nex != null ? nex.getMessage() : "null"));
                if (!hasBizkey.booleanValue()) {
                    try {
                        row = updatorModify.addModifiedRow(dimension);
                        this.setRow(row, fields, values, fieldIndex, null);
                        rowStatus.put("isModify", (Object)"unknow");
                        return true;
                    }
                    catch (Exception ex1) {
                        rowStatus.put("isModify", (Object)"failed");
                        rowStatus.put("message", (Object)ex.getMessage());
                        LogHelper.error((String)"CustomEntry", (String)"\u6570\u636e\u4fdd\u5b58\u5931\u8d25", (String)("\u5931\u8d25\u539f\u56e0\uff1a" + ex1.getMessage()));
                        return false;
                    }
                }
                rowStatus.put("status", false);
                rowStatus.put("isAdd", (Object)"failed");
            }
            catch (Exception ex) {
                rowStatus.put("message", (Object)ex.getMessage());
                rowStatus.put("status", false);
                logger.error(ex.getMessage(), ex);
                LogHelper.error((String)"CustomEntry", (String)"\u6570\u636e\u4fdd\u5b58\u5931\u8d25", (String)("\u5931\u8d25\u539f\u56e0\uff1a" + ex.getMessage()));
                return false;
            }
        }
        catch (Exception ex) {
            rowStatus.put("message", (Object)ex.getMessage());
            logger.error(ex.getMessage(), ex);
        }
        return false;
    }

    private boolean modifyRow(QueryGridDefination defina, DimensionValueSet dimension, Map<String, FieldDefine> fields, Map<String, CellData> values, Map<String, Integer> fieldIndex, IDataUpdator updatorModify, JSONObject rowStatus) {
        try {
            IDataRow row = null;
            try {
                IDataRow queryRow = defina.dataTable.findRow(dimension);
                row = updatorModify.addModifiedRow(dimension);
                this.setRow(row, fields, values, fieldIndex, queryRow);
                rowStatus.put("isModify", (Object)"unknow");
                return true;
            }
            catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                rowStatus.put("isModify", (Object)"failed");
                rowStatus.put("message", (Object)ex.getMessage());
                LogHelper.error((String)"CustomEntry", (String)"\u6570\u636e\u4fdd\u5b58\u5931\u8d25", (String)("\u5931\u8d25\u539f\u56e0\uff1a" + ex.getMessage()));
                return false;
            }
        }
        catch (Exception ex) {
            rowStatus.put("message", (Object)ex.getMessage());
            logger.error(ex.getMessage(), ex);
            return false;
        }
    }

    private void setRow(IDataRow row, Map<String, FieldDefine> fields, Map<String, CellData> values, Map<String, Integer> fieldIndex, IDataRow queryRow) {
        try {
            boolean i = false;
            for (String fcode : fields.keySet()) {
                FieldDefine field = fields.get(fcode);
                com.jiuqi.np.dataengine.data.AbstractData value = null;
                if (queryRow != null) {
                    value = queryRow.getValue(field);
                }
                try {
                    if (values.containsKey(fcode)) {
                        Object temp = values.get((Object)fcode).value;
                        if (field.getType() == FieldType.FIELD_TYPE_TIME) {
                            temp = DateUtils.timeToDate(values.get((Object)fcode).value);
                        }
                        if (field.getType() == FieldType.FIELD_TYPE_LOGIC) {
                            temp = !"".equals(values.get((Object)fcode).value) ? Integer.valueOf(Integer.parseInt(values.get((Object)fcode).value)) : null;
                        }
                        if (StringUtil.isNullOrEmpty((String)temp.toString()) && (QueryHelper.isNumField(field) || field.getType() == FieldType.FIELD_TYPE_DATE)) {
                            temp = null;
                        }
                        value = com.jiuqi.np.dataengine.data.AbstractData.valueOf((Object)temp, (int)DataTypesConvert.fieldTypeToDataType((FieldType)field.getType()));
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                row.setValue(fieldIndex.get(fcode).intValue(), value == null ? field.getDefaultValue() : value.getAsObject());
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            LogHelper.error((String)"CustomEntry", (String)"\u6570\u636e\u884c\u521d\u59cb\u5316\u5931\u8d25", (String)("\u5931\u8d25\u539f\u56e0\uff1a" + ex.getMessage()));
        }
    }

    private CustomEntrySaveContext prepare(CustomEntryContext queryContext, QueryGridDefination defina) {
        QueryBlockDefine block = queryContext.getBlock();
        List<ColumnWidth> gridcols = block.getBlockInfo().getColumnWidth();
        List<CustomEntryData> datas = queryContext.getDatas();
        List<QuerySelectField> fields = defina.showedFields;
        Boolean hasBizKey = false;
        HashMap<String, Map<String, List<Map<String, Map<String, CellData>>>>> saveValues = new HashMap<String, Map<String, List<Map<String, Map<String, CellData>>>>>();
        HashMap<String, Map<String, Map<String, FieldDefine>>> entyFields = new HashMap<String, Map<String, Map<String, FieldDefine>>>();
        HashMap<Integer, Integer> index_listIndex_map = new HashMap<Integer, Integer>();
        HashMap<Integer, IndexObj> indexRows = new HashMap<Integer, IndexObj>();
        try {
            for (CustomEntryData data : datas) {
                String savetype = data.getType().toString();
                HashMap saveValuesWithType = (HashMap)saveValues.get(savetype);
                HashMap regionFields = (HashMap)entyFields.get(savetype);
                if (regionFields == null) {
                    regionFields = new HashMap();
                }
                if (saveValuesWithType == null) {
                    saveValuesWithType = new HashMap();
                }
                List<CustomEntryCellData> cells = data.getCells();
                hasBizKey = Boolean.parseBoolean(cells.get(0).getHasBizKey());
                index_listIndex_map = new HashMap();
                Map<Integer, List<CustomEntryCellData>> rowIndexMap = cells.stream().collect(Collectors.groupingBy(CustomEntryCellData::getRowIndex));
                for (Map.Entry<Integer, List<CustomEntryCellData>> entry : rowIndexMap.entrySet()) {
                    Integer rowIndex = entry.getKey();
                    List<CustomEntryCellData> rowCells = entry.getValue();
                    ArrayList regionValuesRows = null;
                    HashMap regionValues = null;
                    for (CustomEntryCellData cell : rowCells) {
                        String regionKey = null;
                        QuerySelectField field = null;
                        regionValues = null;
                        Map<String, String> clientData = cell.getClientDataObj();
                        int index = 0;
                        String fieldIndex = clientData.get("fieldIndex");
                        if (StringUtil.isNullOrEmpty((String)fieldIndex)) {
                            this.fieldTag = null;
                            int colIndex = cell.getColIndex();
                            gridcols.forEach(gc -> {
                                if (gc != null && gc.getColumn() == colIndex) {
                                    this.fieldTag = gc.getColTag();
                                }
                            });
                        } else {
                            index = Integer.parseInt(fieldIndex);
                        }
                        field = fields.get(index);
                        regionKey = field.getRegionKey();
                        HashMap<String, FieldDefine> fieldDefines = (HashMap<String, FieldDefine>)regionFields.get(regionKey);
                        if (fieldDefines == null) {
                            fieldDefines = new HashMap<String, FieldDefine>();
                        }
                        if (!fieldDefines.containsKey(field.getCode())) {
                            FieldDefine fieldDefine = defina.fieldDefines.get(field.getCode());
                            fieldDefines.put(field.getCode(), fieldDefine);
                        }
                        regionFields.put(regionKey, fieldDefines);
                        String dims = clientData.get("rowkeys");
                        regionValuesRows = (ArrayList)saveValuesWithType.get(regionKey);
                        if (regionValuesRows == null) {
                            regionValuesRows = new ArrayList();
                        }
                        Integer listIndex = (Integer)index_listIndex_map.get(rowIndex);
                        if (index_listIndex_map.containsKey(rowIndex) && listIndex != null && regionValuesRows.size() - 1 >= listIndex) {
                            regionValues = (HashMap)regionValuesRows.get(listIndex);
                        } else {
                            index_listIndex_map.put(rowIndex, regionValuesRows.size());
                            indexRows.put(rowIndex, new IndexObj(rowIndex, dims, regionValuesRows.size()));
                            if (CollectionUtils.isEmpty(regionValues) && !regionValuesRows.contains(regionValues = new HashMap())) {
                                regionValuesRows.add(regionValues);
                            }
                        }
                        HashMap<String, CellData> fieldValue = (HashMap<String, CellData>)regionValues.get(dims);
                        if (fieldValue == null) {
                            fieldValue = new HashMap<String, CellData>();
                        }
                        if (fieldValue.containsKey(index)) {
                            LogHelper.warn((String)"CustomEntry", (String)"\u81ea\u5b9a\u4e49\u5f55\u5165\u51fa\u73b0\u6570\u636e\u8986\u76d6\u5f02\u5e38", (String)("\u81ea\u5b9a\u4e49\u5f55\u5165\u6570\u636e\u8986\u76d6" + dims + "_\u539f\u503c\uff1a" + fieldValue.get(fieldIndex) + "\u73b0\u503c\uff1a" + cell.getEditText()));
                        }
                        CellData celldata = new CellData();
                        celldata.value = cell.getEditText();
                        celldata.period = cell.getPeriod();
                        fieldValue.put(field.getCode(), celldata);
                        regionValues.put(dims, fieldValue);
                        saveValuesWithType.put(regionKey, regionValuesRows);
                    }
                    entyFields.put(savetype, regionFields);
                    saveValues.put(savetype, saveValuesWithType);
                }
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            LogHelper.error((String)"CustomEntry", (String)"\u4fdd\u5b58\u6570\u636e\u4e0a\u4e0b\u6587\u51c6\u5907\u5931\u8d25", (String)("\u5931\u8d25\u539f\u56e0\uff1a" + ex.getMessage()));
        }
        CustomEntrySaveContext saveContext = new CustomEntrySaveContext();
        saveContext.saveValues = saveValues;
        saveContext.regionFields = entyFields;
        saveContext.hasBizKey = hasBizKey;
        saveContext.indexRows = indexRows;
        return saveContext;
    }

    public IDataQuery getDataQuery(String regionKey) {
        try {
            QueryEnvironment queryEnvironment = new QueryEnvironment();
            queryEnvironment.setRegionKey(regionKey);
            DataRegionDefine regionDefine = this.runtimeView.queryDataRegionDefine(regionKey);
            FormDefine formDefine = this.runtimeView.queryFormById(regionDefine.getFormKey());
            queryEnvironment.setFormKey(formDefine.getKey());
            queryEnvironment.setFormCode(formDefine.getFormCode());
            IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
            if (StringUtils.isNotEmpty((String)regionDefine.getInputOrderFieldKey())) {
                FieldDefine inputOrderField = null;
                try {
                    inputOrderField = this.dataDefinitionRuntimeController.queryFieldDefine(regionDefine.getInputOrderFieldKey());
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                if (inputOrderField != null) {
                    DataFieldDeployInfo deployInfoByColumnKey = this.iRuntimeDataSchemeService.getDeployInfoByColumnKey(inputOrderField.getKey());
                    dataQuery.setDefaultGroupName(deployInfoByColumnKey.getTableName());
                }
            }
            return dataQuery;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            LogHelper.error((String)"CustomEntry", (String)"\u521b\u5efa\u6570\u636e\u5b58\u50a8\u5bf9\u8c61\u5931\u8d25", (String)("\u5931\u8d25\u539f\u56e0\uff1a" + ex.getMessage()));
            return null;
        }
    }

    public String getCaption(IEntityRow row, DataLinkDefine linkDefine) {
        String rowCaption = row.getTitle();
        try {
            if (linkDefine.getFilterExpression() != null) {
                if (StringUtils.isEmpty((String)linkDefine.getCaptionFieldsString())) {
                    String[] fields = linkDefine.getCaptionFieldsString().split(";");
                    for (int i = 0; i < fields.length; ++i) {
                        String fieldKey = fields[i];
                        if (fieldKey == null) continue;
                        AbstractData value = row.getValue(fieldKey);
                        if (rowCaption.length() > 0) {
                            rowCaption = rowCaption + "|";
                        }
                        rowCaption = rowCaption + value.getAsString();
                    }
                } else {
                    rowCaption = row.getTitle();
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return rowCaption;
    }

    public String generateFloatOrder(String regionKey, String fieldKey, String masterKey) throws Exception {
        FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
        DimensionValueSet dims = new DimensionValueSet();
        dims.parseString(masterKey);
        String floatOrder = this.generateFloatOrder(regionKey, dims, fieldDefine);
        return floatOrder.toString();
    }

    public IEntityRow getEntityRow(String entityDataKey, IEntityTable entityTable) {
        if (entityTable == null) {
            return null;
        }
        IEntityRow entityRow = entityTable.findByEntityKey(entityDataKey);
        if (entityRow != null) {
            return entityRow;
        }
        entityRow = entityTable.findByCode(entityDataKey);
        if (entityRow != null) {
            return entityRow;
        }
        return null;
    }
}

