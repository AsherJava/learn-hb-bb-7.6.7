/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ResultErrorInfo
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityModify
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.intf.IModifyRow
 *  com.jiuqi.nr.entity.engine.intf.IModifyTable
 *  com.jiuqi.nr.entity.engine.result.CheckFailNodeInfo
 *  com.jiuqi.nr.entity.engine.result.EntityCheckResult
 *  com.jiuqi.nr.entity.engine.result.EntityUpdateResult
 *  com.jiuqi.nr.entity.engine.setting.OrderType
 *  com.jiuqi.nr.entity.engine.var.PageCondition
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import com.jiuqi.nr.dafafill.exception.DataFillRuntimeException;
import com.jiuqi.nr.dafafill.model.DFDimensionValue;
import com.jiuqi.nr.dafafill.model.DataFieldWriteAccessResultInfo;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.DataFillDataDeleteRow;
import com.jiuqi.nr.dafafill.model.DataFillDataQueryInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataSaveInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataSaveRow;
import com.jiuqi.nr.dafafill.model.DataFillQueryResult;
import com.jiuqi.nr.dafafill.model.DataFillResult;
import com.jiuqi.nr.dafafill.model.DataFillSaveErrorDataInfo;
import com.jiuqi.nr.dafafill.model.FieldReadWriteAccessResultInfo;
import com.jiuqi.nr.dafafill.model.OrderField;
import com.jiuqi.nr.dafafill.model.PageInfo;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.model.enums.OrderMode;
import com.jiuqi.nr.dafafill.model.enums.TableType;
import com.jiuqi.nr.dafafill.service.IDataFillEnvBaseDataService;
import com.jiuqi.nr.dafafill.util.NrDataFillI18nUtil;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityModify;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.intf.IModifyRow;
import com.jiuqi.nr.entity.engine.intf.IModifyTable;
import com.jiuqi.nr.entity.engine.result.CheckFailNodeInfo;
import com.jiuqi.nr.entity.engine.result.EntityCheckResult;
import com.jiuqi.nr.entity.engine.result.EntityUpdateResult;
import com.jiuqi.nr.entity.engine.setting.OrderType;
import com.jiuqi.nr.entity.engine.var.PageCondition;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
public class DataFillMasterDataEnvServiceImpl
extends IDataFillEnvBaseDataService {
    private static Logger logger = LoggerFactory.getLogger(DataFillMasterDataEnvServiceImpl.class);
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController runtimeCtrl;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;

    @Override
    public TableType getTableType() {
        return TableType.MASTER;
    }

    @Override
    public DataFillQueryResult query(DataFillDataQueryInfo queryInfo) {
        DataFillContext context = queryInfo.getContext();
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
        QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
        List<DFDimensionValue> dimensionValues = queryInfo.getContext().getDimensionValues();
        Optional<DFDimensionValue> findFirst = dimensionValues.stream().filter(e -> e.getName().equals(periodField.getId())).findFirst();
        if (!findFirst.isPresent()) {
            IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(periodField.getId());
            IPeriodRow curPeriod = periodProvider.getCurPeriod();
            String code = curPeriod.getCode();
            DFDimensionValue period = new DFDimensionValue();
            period.setName(periodField.getId());
            period.setValues(code);
            dimensionValues.add(period);
        }
        LinkedHashMap<DimensionValueSet, List<com.jiuqi.np.dataengine.data.AbstractData>> resultMap = new LinkedHashMap<DimensionValueSet, List<com.jiuqi.np.dataengine.data.AbstractData>>();
        DimensionValueSet entityDimensionValueSet = this.dFDimensionParser.parserGetEntityDimensionValueSet(context);
        QueryField dwQueryField = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
        String dwDimensionName = dwQueryField.getSimplifyFullCode();
        ExecutorContext executorContext = new ExecutorContext(this.runtimeCtrl);
        executorContext.setPeriodView(periodField.getId());
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        DimensionValueSet masterKeys = this.dFDimensionParser.entityIdToSqlDimension(context, entityDimensionValueSet);
        iEntityQuery.setMasterKeys(masterKeys);
        iEntityQuery.sorted(true);
        iEntityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(dwQueryField.getId()));
        this.setOrderFieldForEntityQuery(iEntityQuery, context);
        IEntityTable iEntityTable = null;
        try {
            iEntityTable = iEntityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e2) {
            throw new DataFillRuntimeException("\u67e5\u8be2\u5c01\u9762\u4ee3\u7801\u5b9e\u4f53\u8868\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef", e2);
        }
        PageInfo pagerInfo = queryInfo.getPagerInfo();
        List allRows = null;
        if (pagerInfo == null) {
            allRows = iEntityTable.getAllRows();
        } else {
            PageCondition pageCondition = new PageCondition();
            pageCondition.setPageIndex(Integer.valueOf(pagerInfo.getOffset() * pagerInfo.getLimit()));
            pageCondition.setPageSize(Integer.valueOf(pagerInfo.getLimit()));
            allRows = iEntityTable.getPageRows(pageCondition);
        }
        List<QueryField> fields = this.dFDimensionParser.getDisplayColsQueryFields(context);
        for (IEntityRow ifmdmData : allRows) {
            ArrayList<com.jiuqi.np.dataengine.data.AbstractData> values = new ArrayList<com.jiuqi.np.dataengine.data.AbstractData>();
            DimensionValueSet rowDimensionValueSet = new DimensionValueSet();
            rowDimensionValueSet.assign(entityDimensionValueSet);
            rowDimensionValueSet.setValue(dwDimensionName, (Object)ifmdmData.getEntityKeyData());
            for (QueryField field : fields) {
                if (field.getFieldType() != FieldType.ZB) continue;
                AbstractData nrData = ifmdmData.getValue(field.getSimplifyFullCode());
                com.jiuqi.np.dataengine.data.AbstractData npData = com.jiuqi.np.dataengine.data.AbstractData.valueOf((Object)nrData.getAsObject(), (int)nrData.dataType);
                values.add(npData);
            }
            resultMap.put(rowDimensionValueSet, values);
        }
        DataFillQueryResult dataFillQueryResult = new DataFillQueryResult();
        dataFillQueryResult.setDatas(resultMap);
        if (pagerInfo != null) {
            dataFillQueryResult.setPageInfo(pagerInfo);
            dataFillQueryResult.setTotalCount(iEntityTable.getTotalCount());
        }
        return dataFillQueryResult;
    }

    @Override
    @Transactional
    public DataFillResult save(DataFillDataSaveInfo saveInfo) {
        DataFillResult dataFillResult = new DataFillResult();
        ArrayList<DataFillSaveErrorDataInfo> errors = new ArrayList<DataFillSaveErrorDataInfo>();
        dataFillResult.setErrors(errors);
        dataFillResult.setSuccess(true);
        dataFillResult.setMessage(NrDataFillI18nUtil.buildCode("nvwa.base.saveSuccess"));
        DataFillContext context = saveInfo.getContext();
        List<DataFillDataSaveRow> adds = saveInfo.getAdds();
        List<DataFillDataSaveRow> modifys = saveInfo.getModifys();
        List<DataFillDataDeleteRow> deletes = saveInfo.getDeletes();
        if (null != adds && adds.size() > 0) {
            throw new DataFillRuntimeException("\u4e3b\u7ef4\u5ea6\u6682\u4e0d\u652f\u6301\u4ece\u6b64\u5904\u65b0\u589e\uff01");
        }
        if (null != deletes && deletes.size() > 0) {
            throw new DataFillRuntimeException("\u4e3b\u7ef4\u5ea6\u6682\u4e0d\u652f\u6301\u4ece\u6b64\u5904\u5220\u9664\uff01");
        }
        if (null != modifys && modifys.size() > 0) {
            IEntityAttribute iEntityAttribute;
            List<QueryField> displayCols = this.dFDimensionParser.getDisplayColsQueryFields(context);
            List zbQueryFields = displayCols.stream().filter(e -> e.getFieldType() == FieldType.ZB || e.getFieldType() == FieldType.FIELD || e.getFieldType() == FieldType.TABLEDIMENSION || e.getFieldType() == FieldType.EXPRESSION).collect(Collectors.toList());
            HashMap<String, QueryField> zbIdQueryField = new HashMap<String, QueryField>();
            for (QueryField queryField : zbQueryFields) {
                zbIdQueryField.put(queryField.getId(), queryField);
            }
            Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
            QueryField dwQueryField = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
            String dsDimensionName = dwQueryField.getSimplifyFullCode();
            String periodDimensionName = "";
            if (null != fieldTypeQueryFields.get((Object)FieldType.PERIOD) && fieldTypeQueryFields.get((Object)FieldType.PERIOD).size() > 0) {
                periodDimensionName = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0).getSimplifyFullCode();
            }
            IEntityModel entityModel = null;
            try {
                entityModel = this.entityMetaService.getEntityModel(dsDimensionName);
            }
            catch (Exception e2) {
                logger.error("\u4e3b\u7ef4\u5ea6\u67e5\u8be2\u4e0d\u5230\uff01dsDimensionName\uff1a" + dsDimensionName, e2);
                throw new DataFillRuntimeException("\u4e3b\u7ef4\u5ea6\u67e5\u8be2\u4e0d\u5230\uff01dsDimensionName\uff1a" + dsDimensionName);
            }
            List showFields = entityModel.getShowFields();
            Map<String, IEntityAttribute> showFieldsMap = showFields.stream().collect(Collectors.toMap(IModelDefineItem::getID, e -> e));
            DimensionValueSet oneDimension = this.dFDimensionParser.entityIdToSqlDimension(context, this.dFDimensionParser.convert(modifys.get(0).getDimensionValues(), context));
            QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
            EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(dwQueryField.getId());
            IEntityModify iEntityModify = this.entityDataService.newEntityUpdate();
            iEntityModify.setMasterKeys(this.getDataTimeDimension(oneDimension));
            iEntityModify.setEntityView(entityViewDefine);
            ExecutorContext executorContext = new ExecutorContext(this.runtimeCtrl);
            executorContext.setPeriodView(periodField.getId());
            IModifyTable iModifyTable = null;
            try {
                iModifyTable = iEntityModify.executeUpdate((IContext)executorContext);
            }
            catch (Exception e3) {
                throw new DataFillRuntimeException("\u67e5\u8be2\u5c01\u9762\u4ee3\u7801\u5b9e\u4f53\u8868\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef", e3);
            }
            FieldReadWriteAccessResultInfo accessResult = this.getAccess(saveInfo);
            Map<List, List<DataFillDataSaveRow>> collect = modifys.stream().collect(Collectors.groupingBy(e -> e.getDimensionValues()));
            Set<Map.Entry<List, List<DataFillDataSaveRow>>> entrySet = collect.entrySet();
            for (Map.Entry<List, List<DataFillDataSaveRow>> entry : entrySet) {
                List dimensionLists = entry.getKey();
                List<DataFillDataSaveRow> rows = entry.getValue();
                DimensionValueSet sqlRowValueSet = this.dFDimensionParser.entityIdToSqlDimension(context, dimensionLists);
                IModifyRow iModifyRow = iModifyTable.appendModifyRow(this.dFDimensionParser.entityIdToSqlDimension(context, this.dFDimensionParser.convert(dimensionLists, context)));
                for (DataFillDataSaveRow dataFillDataSaveRow : rows) {
                    List<String> zbs = dataFillDataSaveRow.getZbs();
                    List<String> values = dataFillDataSaveRow.getValues();
                    for (int i = 0; i < zbs.size(); ++i) {
                        String zbId = zbs.get(i);
                        QueryField queryField = (QueryField)zbIdQueryField.get(zbId);
                        if (null == queryField || queryField.getFieldType() == FieldType.EXPRESSION) continue;
                        DataFieldWriteAccessResultInfo access = accessResult.getAccess(sqlRowValueSet, queryField.getCode(), context);
                        if (access.haveAccess()) {
                            iEntityAttribute = showFieldsMap.get(zbId);
                            String zbValue = values.get(i);
                            if ("PARENTCODE".equalsIgnoreCase(iEntityAttribute.getCode()) && (!StringUtils.hasLength(zbValue) || "-".equals(zbValue))) {
                                iModifyRow.setValue(iEntityAttribute.getCode(), (Object)"-");
                                continue;
                            }
                            DataFillSaveErrorDataInfo saveErrorDataInfo = this.toSqlValue(zbValue, queryField, context, dataFillDataSaveRow.getDimensionValues(), (ColumnModelDefine)iEntityAttribute, null, null);
                            if (null != saveErrorDataInfo.getDataError()) {
                                errors.add(saveErrorDataInfo);
                                continue;
                            }
                            iModifyRow.setValue(iEntityAttribute.getCode(), null == saveErrorDataInfo.getValue() ? "" : saveErrorDataInfo.getValue());
                            continue;
                        }
                        DataFillSaveErrorDataInfo saveErrorDataInfo = accessResult.getMessage(sqlRowValueSet, queryField.getCode(), queryField, context, dataFillDataSaveRow.getDimensionValues(), access);
                        errors.add(saveErrorDataInfo);
                    }
                }
                iModifyRow.buildRow();
            }
            try {
                if (errors.isEmpty()) {
                    EntityUpdateResult commitChange = iModifyTable.commitChange();
                    EntityCheckResult checkResult = commitChange.getCheckResult();
                    if (null != checkResult.getFailInfos() && !checkResult.getFailInfos().isEmpty()) {
                        Map<String, IEntityAttribute> showFieldsCodeMap = showFields.stream().collect(Collectors.toMap(IModelDefineItem::getCode, e -> e));
                        List<DFDimensionValue> dimensionValues = modifys.get(0).getDimensionValues();
                        String periodDimension = periodDimensionName;
                        Optional<DFDimensionValue> findFirst = dimensionValues.stream().filter(e -> e.getName().equals(periodDimension)).findFirst();
                        DFDimensionValue periodValue = null;
                        if (findFirst.isPresent()) {
                            periodValue = findFirst.get();
                        }
                        for (CheckFailNodeInfo checkFailNodeInfo : checkResult.getFailInfos()) {
                            DataFillSaveErrorDataInfo saveErrorDataInfo = new DataFillSaveErrorDataInfo();
                            ResultErrorInfo dataError = new ResultErrorInfo();
                            ArrayList<DFDimensionValue> newDimensionValues = new ArrayList<DFDimensionValue>();
                            DFDimensionValue newValue = new DFDimensionValue();
                            newValue.setName(dsDimensionName);
                            newValue.setValues(checkFailNodeInfo.getCode());
                            newDimensionValues.add(newValue);
                            if (null != periodValue) {
                                newDimensionValues.add(periodValue);
                            }
                            dataError.setErrorCode(ErrorCode.DATAERROR);
                            dataError.setErrorInfo(checkFailNodeInfo.getMessage());
                            saveErrorDataInfo.setValue((Serializable)checkFailNodeInfo.getValue());
                            saveErrorDataInfo.setDataError(dataError);
                            saveErrorDataInfo.setDimensionValues(newDimensionValues);
                            String attributeCode = null == checkFailNodeInfo.getAttributeCode() ? "" : checkFailNodeInfo.getAttributeCode();
                            iEntityAttribute = showFieldsCodeMap.get(attributeCode);
                            if (null == iEntityAttribute) {
                                iEntityAttribute = showFieldsCodeMap.get(attributeCode.toUpperCase());
                            }
                            saveErrorDataInfo.setZb(null != iEntityAttribute ? iEntityAttribute.getID() : "");
                            errors.add(saveErrorDataInfo);
                        }
                        dataFillResult.setSuccess(false);
                        dataFillResult.setMessage(NrDataFillI18nUtil.buildCode("nvwa.base.saveError") + "\uff01");
                    }
                } else {
                    dataFillResult.setSuccess(false);
                    dataFillResult.setMessage(NrDataFillI18nUtil.buildCode("nvwa.base.saveError") + "\uff01");
                }
            }
            catch (Exception e4) {
                logger.error("\u4e3b\u7ef4\u5ea6\u66f4\u65b0\u5931\u8d25\uff01", e4);
                throw new DataFillRuntimeException("\u4e3b\u7ef4\u5ea6\u66f4\u65b0\u5931\u8d25\uff01");
            }
        }
        return dataFillResult;
    }

    private DimensionValueSet getDataTimeDimension(DimensionValueSet dimensionValueSet) {
        DimensionValueSet queryDimension = new DimensionValueSet();
        if (dimensionValueSet == null) {
            return queryDimension;
        }
        Object periodDimension = dimensionValueSet.getValue("DATATIME");
        if (periodDimension != null) {
            queryDimension.setValue("DATATIME", periodDimension);
        }
        return queryDimension;
    }

    private void setOrderFieldForEntityQuery(IEntityQuery iEntityQuery, DataFillContext context) {
        iEntityQuery.sortedByQuery(false);
        List<OrderField> orderFields = context.getModel().getOrderFields();
        for (OrderField orderField : orderFields) {
            OrderType orderType = orderField.getMode() == OrderMode.ASC ? OrderType.ASC : OrderType.DESC;
            String fullCode = orderField.getFullCode();
            String code = fullCode.contains(".") ? fullCode.substring(fullCode.lastIndexOf(".") + 1) : fullCode;
            iEntityQuery.addOrderAttribute(code, orderType);
        }
    }
}

