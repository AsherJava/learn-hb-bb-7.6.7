/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ResultErrorInfo
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.dafafill.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import com.jiuqi.nr.dafafill.model.DFDimensionValue;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.DataFillDataDeleteRow;
import com.jiuqi.nr.dafafill.model.DataFillDataQueryInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataSaveInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataSaveRow;
import com.jiuqi.nr.dafafill.model.DataFillEntityData;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataQueryInfo;
import com.jiuqi.nr.dafafill.model.DataFillQueryResult;
import com.jiuqi.nr.dafafill.model.DataFillResult;
import com.jiuqi.nr.dafafill.model.DataFillSaveErrorDataInfo;
import com.jiuqi.nr.dafafill.model.FieldReadWriteAccessResultInfo;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.model.enums.TableType;
import com.jiuqi.nr.dafafill.service.IDFDimensionQueryFieldParser;
import com.jiuqi.nr.dafafill.service.IDataFillEntityDataService;
import com.jiuqi.nr.dafafill.service.impl.BatchFieldWriteAccessInfoHelper;
import com.jiuqi.nr.dafafill.service.impl.DataFillFloatDataEnvServiceImpl;
import com.jiuqi.nr.dafafill.util.BooleanUtil;
import com.jiuqi.nr.dafafill.util.DateUtils;
import com.jiuqi.nr.dafafill.util.NrDataFillI18nUtil;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public abstract class IDataFillEnvBaseDataService {
    private static final Logger logger = LoggerFactory.getLogger(IDataFillEnvBaseDataService.class);
    @Autowired
    protected IDFDimensionQueryFieldParser dFDimensionParser;
    @Autowired
    protected IDataFillEntityDataService dataFillEntityDataService;
    @Autowired
    protected IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    protected IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    protected IRunTimeViewController runTimeViewController;
    @Autowired
    protected IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    protected ReadWriteAccessProvider readWriteAccessProvider;
    @Autowired
    private BatchFieldWriteAccessInfoHelper batchFieldWriteAccessInfoHelper;
    @Autowired
    protected IEntityMetaService entityMetaService;

    public abstract TableType getTableType();

    public abstract DataFillQueryResult query(DataFillDataQueryInfo var1);

    public abstract DataFillResult save(DataFillDataSaveInfo var1);

    protected List<DataTable> getDataTables(List<TableDefine> tableDefines) {
        ArrayList<DataTable> tables = new ArrayList<DataTable>();
        for (TableDefine tableDefine : tableDefines) {
            tables.add(this.dataSchemeService.getDataTable(tableDefine.getKey()));
        }
        return tables;
    }

    protected List<TableDefine> getTableDefines(List<QueryField> zbQueryFields) throws Exception {
        ArrayList<TableDefine> defines = new ArrayList<TableDefine>();
        Map dataSchemeCodeToZbIdsMap = zbQueryFields.stream().collect(Collectors.groupingBy(QueryField::getDataSchemeCode, Collectors.mapping(QueryField::getId, Collectors.toList())));
        dataSchemeCodeToZbIdsMap.forEach((key, fieldKeys) -> defines.addAll(this.dataDefinitionRuntimeController.queryTableDefinesByFields((Collection)fieldKeys)));
        return defines;
    }

    protected List<FieldDefine> getOrderFields(List<TableDefine> defines) throws Exception {
        ArrayList<FieldDefine> queryFields;
        block0: {
            queryFields = new ArrayList<FieldDefine>();
            Iterator<TableDefine> iterator = defines.iterator();
            if (!iterator.hasNext()) break block0;
            TableDefine table = iterator.next();
            FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable("FLOATORDER", table.getKey());
            queryFields.add(fieldDefine);
        }
        return queryFields;
    }

    protected FieldReadWriteAccessResultInfo getAccess(DataFillDataSaveInfo saveInfo, List<DataFillDataSaveRow> rows) {
        return this.getAccess(saveInfo, rows, null);
    }

    protected FieldReadWriteAccessResultInfo getAccess(DataFillDataSaveInfo saveInfo, List<DataFillDataSaveRow> rows, Map<String, String> authMap) {
        DataFillContext context = saveInfo.getContext();
        HashSet<String> queryZbSet = new HashSet<String>();
        ArrayList<DimensionValueSet> mapList = new ArrayList<DimensionValueSet>();
        for (DataFillDataSaveRow dataFillDataSaveRow : rows) {
            List<DFDimensionValue> dimensionValues = dataFillDataSaveRow.getDimensionValues();
            List<String> zbs = dataFillDataSaveRow.getZbs();
            if (null != zbs && !zbs.isEmpty()) {
                queryZbSet.addAll(zbs);
            }
            DimensionValueSet entityDimension = this.dFDimensionParser.convert(dimensionValues, context);
            mapList.add(entityDimension);
        }
        List<QueryField> allQueryFields = this.dFDimensionParser.getAllQueryFields(context);
        DimensionValueSet dimensionValueSet = this.dFDimensionParser.dimensionValueIntegration(context, mapList);
        Map<String, QueryField> idMap = allQueryFields.stream().collect(Collectors.toMap(QueryField::getId, e -> e, (e, n) -> e));
        if (queryZbSet.contains("ID")) {
            queryZbSet.remove("ID");
        }
        if (queryZbSet.contains("FLOATORDER")) {
            queryZbSet.remove("FLOATORDER");
        }
        List queryZb = queryZbSet.stream().filter(e -> {
            QueryField zbQueryField = (QueryField)idMap.get(e);
            return null != zbQueryField && (zbQueryField.getFieldType() == FieldType.ZB || zbQueryField.getFieldType() == FieldType.TABLEDIMENSION || zbQueryField.getFieldType() == FieldType.FIELD);
        }).collect(Collectors.toList());
        TableType tableType = saveInfo.getContext().getModel().getTableType();
        ArrayList<String> queryZbIds = new ArrayList<String>();
        for (String id : queryZb) {
            QueryField temp = idMap.get(id);
            if (tableType == TableType.MASTER) {
                queryZbIds.add(temp.getCode());
                if (null == authMap) continue;
                authMap.put(temp.getId(), temp.getCode());
                continue;
            }
            queryZbIds.add(temp.getId());
            if (null == authMap) continue;
            authMap.put(temp.getId(), temp.getId());
        }
        try {
            return this.batchFieldWriteAccessInfoHelper.buildAccessResult(context, dimensionValueSet, queryZbIds);
        }
        catch (Exception e2) {
            logger.error("\u67e5\u8be2\u6743\u9650\u62a5\u9519", e2);
            return new FieldReadWriteAccessResultInfo();
        }
    }

    protected FieldReadWriteAccessResultInfo getAccess(DataFillDataSaveInfo saveInfo) {
        List zbids = null;
        List<DataFillDataSaveRow> adds = saveInfo.getAdds();
        List<DataFillDataSaveRow> modifys = saveInfo.getModifys();
        List<DataFillDataDeleteRow> deletes = saveInfo.getDeletes();
        ArrayList<DataFillDataSaveRow> list = new ArrayList<DataFillDataSaveRow>();
        if (null != adds && adds.size() > 0) {
            list.addAll(adds);
        }
        if (null != modifys && modifys.size() > 0) {
            list.addAll(modifys);
        }
        if (null != deletes && deletes.size() > 0) {
            for (DataFillDataDeleteRow dataDeleteRow : deletes) {
                DataFillDataSaveRow temp = new DataFillDataSaveRow();
                temp.setDimensionValues(dataDeleteRow.getDimensionValues());
                if (zbids == null) {
                    List<QueryField> displayCols = this.dFDimensionParser.getDisplayColsQueryFields(saveInfo.getContext());
                    List zbList = displayCols.stream().filter(e -> e.getFieldType() == FieldType.ZB || e.getFieldType() == FieldType.FIELD || e.getFieldType() == FieldType.TABLEDIMENSION).collect(Collectors.toList());
                    zbids = zbList.stream().map(QueryField::getId).collect(Collectors.toList());
                }
                temp.setZbs(zbids);
                list.add(temp);
            }
        }
        return this.getAccess(saveInfo, list);
    }

    protected DataFillSaveErrorDataInfo toSqlValue(String zbValue, QueryField queryField, DataFillContext originalContext, List<DFDimensionValue> dimensionValues, ColumnModelDefine iEntityAttribute, FieldDefine fieldDefine, DataFillFloatDataEnvServiceImpl.FloatTypeDTO floatTypeDTO) {
        ResultErrorInfo dataError;
        String zbTitle;
        AttributeCheckInfo attributeCheckInfo = new AttributeCheckInfo(iEntityAttribute, fieldDefine, queryField);
        DataFillSaveErrorDataInfo dataFillSaveErrorDataInfo = new DataFillSaveErrorDataInfo();
        DataFieldType dataType = queryField.getDataType();
        String string = zbTitle = StringUtils.hasLength(queryField.getAlias()) ? queryField.getAlias() : queryField.getTitle();
        if (null == zbValue) {
            zbValue = "";
        }
        zbValue = zbValue.trim();
        Object value = null;
        switch (dataType) {
            case DATE_TIME: 
            case DATE: {
                try {
                    if (!StringUtils.hasLength(zbValue) || "null".equals(zbValue)) break;
                    Date date = DateUtils.stringToDate(zbValue);
                    if (DateUtils.checkDate(date)) {
                        value = new Time(date.getTime());
                        break;
                    }
                    dataError = new ResultErrorInfo();
                    dataFillSaveErrorDataInfo.setDataError(dataError);
                    dataError.setErrorCode(ErrorCode.DATAERROR);
                    dataError.setErrorInfo(NrDataFillI18nUtil.buildCode("nr.dataquery.Field") + ":" + zbTitle + " ;" + NrDataFillI18nUtil.buildCode("nr.dataFill.dataFormatIs") + "yyyy-MM-dd" + "\u3002" + NrDataFillI18nUtil.buildCode("nr.dataFill.dateOutOfRange") + "\u3002" + NrDataFillI18nUtil.buildCode("nr.dataFill.errorData") + ":" + zbValue.toString());
                }
                catch (Exception e) {
                    try {
                        Double doubleValue = new Double(zbValue);
                        Date javaDate = DateUtil.getJavaDate(doubleValue);
                        if (DateUtils.checkDate(javaDate)) {
                            value = new Time(javaDate.getTime());
                        }
                    }
                    catch (NumberFormatException doubleValue) {
                        // empty catch block
                    }
                    if (null != value) break;
                    dataError = new ResultErrorInfo();
                    dataFillSaveErrorDataInfo.setDataError(dataError);
                    dataError.setErrorCode(ErrorCode.DATAERROR);
                    dataError.setErrorInfo(NrDataFillI18nUtil.buildCode("nr.dataquery.Field") + ":" + zbTitle + " ;" + NrDataFillI18nUtil.buildCode("nr.dataFill.dataFormatIs") + "yyyy-MM-dd" + "\u3002" + NrDataFillI18nUtil.buildCode("nr.dataFill.dateOutOfRange") + "\u3002" + NrDataFillI18nUtil.buildCode("nr.dataFill.errorData") + ":" + zbValue.toString());
                }
                break;
            }
            case INTEGER: {
                try {
                    if (!StringUtils.hasLength(zbValue) || "null".equals(zbValue)) break;
                    value = Integer.parseInt(zbValue.toString());
                }
                catch (NumberFormatException e) {
                    dataError = new ResultErrorInfo();
                    dataFillSaveErrorDataInfo.setDataError(dataError);
                    try {
                        Long.parseLong(zbValue.toString());
                        dataError.setErrorCode(ErrorCode.DATAERROR);
                        dataError.setErrorInfo(NrDataFillI18nUtil.buildCode("nr.dataquery.Field") + ":" + zbTitle + " ;" + NrDataFillI18nUtil.buildCode("nr.dataFill.onlyIntAndMaxIs") + "\uff1a" + Integer.MAX_VALUE + "\uff0c" + NrDataFillI18nUtil.buildCode("nr.dataFill.minValIs") + "\uff1a" + Integer.MIN_VALUE + "\u3002" + NrDataFillI18nUtil.buildCode("nr.dataFill.errorData") + ":" + zbValue.toString());
                    }
                    catch (NumberFormatException e2) {
                        dataError.setErrorCode(ErrorCode.DATAERROR);
                        dataError.setErrorInfo(NrDataFillI18nUtil.buildCode("nr.dataquery.Field") + ":" + zbTitle + " ;" + NrDataFillI18nUtil.buildCode("nr.dataFill.onlyInt") + "\u3002" + NrDataFillI18nUtil.buildCode("nr.dataFill.errorData") + ":" + zbValue.toString());
                    }
                }
                break;
            }
            case BOOLEAN: {
                if (StringUtils.hasLength(zbValue) && !"null".equals(zbValue)) {
                    Boolean returnBoolean = BooleanUtil.parseBoolean(zbValue.toString());
                    if (null == returnBoolean) {
                        dataError = new ResultErrorInfo();
                        dataFillSaveErrorDataInfo.setDataError(dataError);
                        dataError.setErrorCode(ErrorCode.DATAERROR);
                        dataError.setErrorInfo(NrDataFillI18nUtil.buildCode("nr.dataquery.Field") + ":" + zbTitle + " ;" + NrDataFillI18nUtil.buildCode("nr.dataFill.onlyBool") + "\u3002" + NrDataFillI18nUtil.buildCode("nr.dataFill.errorData") + ":" + zbValue.toString());
                    }
                    value = returnBoolean;
                    break;
                }
                value = false;
                break;
            }
            case BIGDECIMAL: {
                try {
                    int scale;
                    Integer decimal;
                    String longString;
                    if (!StringUtils.hasLength(zbValue) || "null".equals(zbValue)) break;
                    BigDecimal bigDecimal = null;
                    String tempValue = zbValue.toString();
                    if (tempValue.contains(",")) {
                        tempValue = tempValue.replace(",", "");
                    }
                    bigDecimal = new BigDecimal(tempValue);
                    bigDecimal = bigDecimal.stripTrailingZeros();
                    boolean haveError = false;
                    Integer size = attributeCheckInfo.getSize();
                    if (null != size && (longString = bigDecimal.longValue() + "").length() > size) {
                        haveError = true;
                        ResultErrorInfo dataError2 = new ResultErrorInfo();
                        dataFillSaveErrorDataInfo.setDataError(dataError2);
                        dataError2.setErrorCode(ErrorCode.DATAERROR);
                        dataError2.setErrorInfo(NrDataFillI18nUtil.buildCode("nr.dataquery.Field") + ":" + zbTitle + " ;" + NrDataFillI18nUtil.buildCode("nr.dataFill.exceedAccuracyIs") + "\uff1a" + size + "\u3002" + NrDataFillI18nUtil.buildCode("nr.dataFill.errorData") + ":" + zbValue.toString());
                    }
                    if (null != (decimal = attributeCheckInfo.getDecimal()) && !haveError && (scale = bigDecimal.scale()) > decimal) {
                        ResultErrorInfo dataError3 = new ResultErrorInfo();
                        dataFillSaveErrorDataInfo.setDataError(dataError3);
                        dataError3.setErrorCode(ErrorCode.DATAERROR);
                        dataError3.setErrorInfo(NrDataFillI18nUtil.buildCode("nr.dataquery.Field") + ":" + zbTitle + " ;" + NrDataFillI18nUtil.buildCode("nr.dataFill.exceedBitMinIs") + "\uff1a" + decimal + "\u3002" + NrDataFillI18nUtil.buildCode("nr.dataFill.errorData") + ":" + zbValue.toString());
                    }
                    value = bigDecimal;
                }
                catch (Exception e) {
                    dataError = new ResultErrorInfo();
                    dataFillSaveErrorDataInfo.setDataError(dataError);
                    dataError.setErrorCode(ErrorCode.DATAERROR);
                    dataError.setErrorInfo(NrDataFillI18nUtil.buildCode("nr.dataquery.Field") + ":" + zbTitle + " ;" + NrDataFillI18nUtil.buildCode("nr.dataFill.shouldBeDecimal") + "\u3002" + NrDataFillI18nUtil.buildCode("nr.dataFill.errorData") + ":" + zbValue.toString());
                }
                break;
            }
            case STRING: {
                if (!StringUtils.hasLength(zbValue) || "null".equals(zbValue)) break;
                boolean isMultiVal = false;
                String[] zbValues = null;
                if (zbValue.contains(";")) {
                    isMultiVal = true;
                    zbValues = zbValue.split(";");
                }
                if (StringUtils.hasLength(queryField.getExpression())) {
                    DataFillContext tempContext = new DataFillContext();
                    tempContext.setModel(originalContext.getModel());
                    tempContext.setDimensionValues(dimensionValues);
                    if (isMultiVal && attributeCheckInfo.isAllowMultiSelect().booleanValue()) {
                        StringBuilder tempValue = new StringBuilder();
                        for (String eachZBValue : zbValues) {
                            String tempId = "";
                            DataFillEntityDataQueryInfo queryInfo = new DataFillEntityDataQueryInfo();
                            queryInfo.setContext(tempContext);
                            queryInfo.setCode(eachZBValue);
                            queryInfo.setFullCode(queryField.getFullCode());
                            DataFillEntityData queryByIdOrCode = this.dataFillEntityDataService.queryByPrimaryOrSearch(queryInfo);
                            if (null != queryByIdOrCode) {
                                boolean allowNotLeafNodeRefer = queryField.isAllowNotLeafNodeRefer();
                                if (!allowNotLeafNodeRefer) {
                                    boolean leaf = queryByIdOrCode.isLeaf();
                                    if (!leaf) {
                                        ResultErrorInfo dataError4 = new ResultErrorInfo();
                                        dataFillSaveErrorDataInfo.setDataError(dataError4);
                                        dataError4.setErrorCode(ErrorCode.DATAERROR);
                                        dataError4.setErrorInfo(NrDataFillI18nUtil.buildCode("nr.dataquery.Field") + ":" + zbTitle + " ;" + NrDataFillI18nUtil.buildCode("nr.multcheck.emunCheck.no_leaf_node_selected") + "! " + NrDataFillI18nUtil.buildCode("nr.dataFill.errorData") + ":" + zbValue);
                                        continue;
                                    }
                                    tempId = queryByIdOrCode.getId() + ";";
                                    tempValue.append(tempId);
                                    continue;
                                }
                                tempId = queryByIdOrCode.getId() + ";";
                                tempValue.append(tempId);
                                continue;
                            }
                            if (eachZBValue.contains("|")) {
                                String[] split;
                                for (String codeOrTitle : split = eachZBValue.split("\\|")) {
                                    queryInfo.setCode(codeOrTitle);
                                    queryByIdOrCode = this.dataFillEntityDataService.queryByIdOrCode(queryInfo);
                                    if (null == queryByIdOrCode) continue;
                                    boolean allowNotLeafNodeRefer = queryField.isAllowNotLeafNodeRefer();
                                    if (!allowNotLeafNodeRefer) {
                                        boolean leaf = queryByIdOrCode.isLeaf();
                                        if (!leaf) {
                                            tempId = "";
                                            ResultErrorInfo dataError5 = new ResultErrorInfo();
                                            dataFillSaveErrorDataInfo.setDataError(dataError5);
                                            dataError5.setErrorCode(ErrorCode.DATAERROR);
                                            dataError5.setErrorInfo(NrDataFillI18nUtil.buildCode("nr.dataquery.Field") + ":" + zbTitle + " ;" + NrDataFillI18nUtil.buildCode("nr.multcheck.emunCheck.no_leaf_node_selected") + "! " + NrDataFillI18nUtil.buildCode("nr.dataFill.errorData") + ":" + zbValue);
                                            break;
                                        }
                                        tempId = queryByIdOrCode.getId() + ";";
                                        break;
                                    }
                                    tempId = queryByIdOrCode.getId() + ";";
                                    break;
                                }
                            }
                            if (tempId.length() == 0) {
                                ResultErrorInfo dataError6 = new ResultErrorInfo();
                                dataFillSaveErrorDataInfo.setDataError(dataError6);
                                dataError6.setErrorCode(ErrorCode.DATAERROR);
                                dataError6.setErrorInfo(NrDataFillI18nUtil.buildCode("nr.dataquery.Field") + ":" + zbTitle + " ;" + NrDataFillI18nUtil.buildCode("nr.dataFill.inputNotExsit") + "! " + NrDataFillI18nUtil.buildCode("nr.dataFill.errorData") + ":" + zbValue);
                                continue;
                            }
                            tempValue.append(tempId);
                        }
                        if (tempValue.length() == 0) break;
                        value = tempValue.toString().substring(0, tempValue.length() - 1);
                        break;
                    }
                    DataFillEntityDataQueryInfo queryInfo = new DataFillEntityDataQueryInfo();
                    queryInfo.setContext(tempContext);
                    queryInfo.setCode(zbValue);
                    queryInfo.setFullCode(queryField.getFullCode());
                    DataFillEntityData queryByIdOrCode = this.dataFillEntityDataService.queryByPrimaryOrSearch(queryInfo);
                    if (null != queryByIdOrCode) {
                        boolean leaf;
                        value = queryByIdOrCode.getId();
                        boolean allowNotLeafNodeRefer = queryField.isAllowNotLeafNodeRefer();
                        if (allowNotLeafNodeRefer || (leaf = queryByIdOrCode.isLeaf())) break;
                        value = null;
                        ResultErrorInfo dataError7 = new ResultErrorInfo();
                        dataFillSaveErrorDataInfo.setDataError(dataError7);
                        dataError7.setErrorCode(ErrorCode.DATAERROR);
                        dataError7.setErrorInfo(NrDataFillI18nUtil.buildCode("nr.dataquery.Field") + ":" + zbTitle + " ;" + NrDataFillI18nUtil.buildCode("nr.multcheck.emunCheck.no_leaf_node_selected") + "! " + NrDataFillI18nUtil.buildCode("nr.dataFill.errorData") + ":" + zbValue);
                        break;
                    }
                    if (zbValue.contains("|")) {
                        String[] split;
                        for (String codeOrTitle : split = zbValue.split("\\|")) {
                            queryInfo.setCode(codeOrTitle);
                            queryByIdOrCode = this.dataFillEntityDataService.queryByIdOrCode(queryInfo);
                            if (null == queryByIdOrCode) continue;
                            value = queryByIdOrCode.getId();
                            break;
                        }
                    }
                    if (null != value) break;
                    ResultErrorInfo dataError8 = new ResultErrorInfo();
                    dataFillSaveErrorDataInfo.setDataError(dataError8);
                    dataError8.setErrorCode(ErrorCode.DATAERROR);
                    dataError8.setErrorInfo(NrDataFillI18nUtil.buildCode("nr.dataquery.Field") + ":" + zbTitle + " ;" + NrDataFillI18nUtil.buildCode("nr.dataFill.inputNotExsit") + "! " + NrDataFillI18nUtil.buildCode("nr.dataFill.errorData") + ":" + zbValue);
                    break;
                }
                Integer size = attributeCheckInfo.getSize();
                if (null != size && zbValue.length() > size) {
                    ResultErrorInfo dataError9 = new ResultErrorInfo();
                    dataFillSaveErrorDataInfo.setDataError(dataError9);
                    dataError9.setErrorCode(ErrorCode.DATAERROR);
                    dataError9.setErrorInfo(NrDataFillI18nUtil.buildCode("nr.dataquery.Field") + ":" + zbTitle + " ;" + NrDataFillI18nUtil.buildCode("nr.dataFill.exceedMaxLen") + "\uff1a" + size + "\u3002" + NrDataFillI18nUtil.buildCode("nr.dataFill.errorData") + ":" + zbValue);
                }
                value = zbValue;
                break;
            }
            default: {
                if (!StringUtils.hasLength(zbValue) || "null".equals(zbValue)) break;
                value = zbValue;
            }
        }
        dataFillSaveErrorDataInfo.setValue((Serializable)((Object)zbValue));
        dataFillSaveErrorDataInfo.setZb(queryField.getId());
        dataFillSaveErrorDataInfo.setDimensionValues(dimensionValues);
        if (null == dataFillSaveErrorDataInfo.getDataError()) {
            boolean nullAble;
            if (!(null == attributeCheckInfo || (nullAble = attributeCheckInfo.isNullAble()) || null != value || floatTypeDTO != null && floatTypeDTO.getFieldKeys().contains(queryField.getId()))) {
                dataError = new ResultErrorInfo();
                dataFillSaveErrorDataInfo.setDataError(dataError);
                dataError.setErrorCode(ErrorCode.DATAERROR);
                dataError.setErrorInfo(NrDataFillI18nUtil.buildCode("nr.dataquery.Field") + ":" + zbTitle + " ;" + NrDataFillI18nUtil.buildCode("nr.dataFill.require") + "\uff01");
                return dataFillSaveErrorDataInfo;
            }
            dataFillSaveErrorDataInfo.setValue((Serializable)value);
        }
        return dataFillSaveErrorDataInfo;
    }

    public class AttributeCheckInfo {
        private ColumnModelDefine iEntityAttribute;
        private FieldDefine fieldDefine;
        private QueryField queryField;

        public AttributeCheckInfo(ColumnModelDefine iEntityAttribute, FieldDefine fieldDefine, QueryField queryField) {
            this.iEntityAttribute = iEntityAttribute;
            this.fieldDefine = fieldDefine;
            this.queryField = queryField;
        }

        public boolean isNullAble() {
            if (null != this.queryField && this.queryField.isNotEmpty()) {
                return false;
            }
            if (null != this.fieldDefine) {
                return this.fieldDefine.getNullable();
            }
            if (null != this.iEntityAttribute) {
                return this.iEntityAttribute.isNullAble();
            }
            return true;
        }

        public Integer getSize() {
            if (null != this.fieldDefine) {
                return this.fieldDefine.getSize();
            }
            if (null != this.iEntityAttribute) {
                return this.iEntityAttribute.getPrecision();
            }
            return null;
        }

        public Integer getDecimal() {
            if (null != this.fieldDefine) {
                return this.fieldDefine.getFractionDigits();
            }
            if (null != this.iEntityAttribute) {
                return this.iEntityAttribute.getDecimal();
            }
            return null;
        }

        private Boolean isAllowMultiSelect() {
            if (this.iEntityAttribute == null) {
                return this.fieldDefine.getAllowMultipleSelect();
            }
            return this.iEntityAttribute.isMultival();
        }
    }
}

