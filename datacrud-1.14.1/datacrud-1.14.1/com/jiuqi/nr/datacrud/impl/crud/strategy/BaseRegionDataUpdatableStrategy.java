/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.FieldValidateResult
 *  com.jiuqi.np.dataengine.common.RowExpressionValidResult
 *  com.jiuqi.np.dataengine.common.RowValidateResult
 *  com.jiuqi.np.dataengine.common.ValidateResult
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.BoolData
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.exception.DataValidateException
 *  com.jiuqi.np.dataengine.exception.DuplicateRowKeysException
 *  com.jiuqi.np.dataengine.exception.ExpressionValidateException
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.exception.ValueValidateException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchZBAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.dataservice.core.datastatus.monitor.DataStatusMonitorFactory
 *  com.jiuqi.nr.dataservice.core.datastatus.monitor.IDataStatusMonitor
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.datacrud.impl.crud.strategy;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.FieldValidateResult;
import com.jiuqi.np.dataengine.common.RowExpressionValidResult;
import com.jiuqi.np.dataengine.common.RowValidateResult;
import com.jiuqi.np.dataengine.common.ValidateResult;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.BoolData;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.DataValidateException;
import com.jiuqi.np.dataengine.exception.DuplicateRowKeysException;
import com.jiuqi.np.dataengine.exception.ExpressionValidateException;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.exception.ValueValidateException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchZBAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRegionInfo;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.SaveData;
import com.jiuqi.nr.datacrud.SaveResItem;
import com.jiuqi.nr.datacrud.SaveRowData;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.crud.strategy.DefaultValueEvaluate;
import com.jiuqi.nr.datacrud.impl.loggger.DataServiceLogWrapper;
import com.jiuqi.nr.datacrud.impl.loggger.DataServiceLogger;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.datacrud.spi.IExecutorContextFactory;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datacrud.spi.filter.EntityDefaultValueFilter;
import com.jiuqi.nr.datacrud.spi.filter.FilterFunction;
import com.jiuqi.nr.datacrud.spi.filter.InValuesFilter;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.dataservice.core.datastatus.monitor.DataStatusMonitorFactory;
import com.jiuqi.nr.dataservice.core.datastatus.monitor.IDataStatusMonitor;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public abstract class BaseRegionDataUpdatableStrategy
extends DefaultValueEvaluate {
    protected IRegionInfo regionInfo;
    protected RegionRelation relation;
    protected IDataQuery dataQuery;
    protected ExecutorContext context;
    protected final RegionRelationFactory regionRelationFactory;
    protected final IExecutorContextFactory executorContextFactory;
    protected final DataEngineService dataEngineService;
    protected final IDataAccessServiceProvider dataAccessServiceProvider;
    protected final IRuntimeExpressionService expressionService;
    protected DataServiceLogWrapper dataServiceLogWrapper;
    protected IEntityMetaService entityMetaService;
    protected DataServiceLogger crudLogger;
    protected static Logger logger = LoggerFactory.getLogger(BaseRegionDataUpdatableStrategy.class);

    public BaseRegionDataUpdatableStrategy(RegionRelationFactory regionRelationFactory, IExecutorContextFactory executorContextFactory, DataEngineService dataEngineService, IDataAccessServiceProvider dataAccessServiceProvider, IRuntimeExpressionService expressionService, DataServiceLogWrapper dataServiceLogWrapper, IEntityMetaService entityMetaService) {
        super(dataEngineService);
        this.regionRelationFactory = regionRelationFactory;
        this.executorContextFactory = executorContextFactory;
        this.dataEngineService = dataEngineService;
        this.dataAccessServiceProvider = dataAccessServiceProvider;
        this.expressionService = expressionService;
        this.dataServiceLogWrapper = dataServiceLogWrapper;
        this.entityMetaService = entityMetaService;
    }

    protected void initDataQuery(IRegionInfo clearInfo, RegionRelation relation) {
        this.regionInfo = clearInfo;
        this.relation = relation;
        if (this.relation == null) {
            this.relation = this.regionRelationFactory.getRegionRelation(clearInfo.getRegionKey());
        }
        this.dataQuery = this.dataEngineService.getDataQuery(relation);
        DimensionCombination dimensionCombination = this.regionInfo.getDimensionCombination();
        if (dimensionCombination != null) {
            this.dataQuery.setMasterKeys(dimensionCombination.toDimensionValueSet());
            this.context = this.executorContextFactory.getExecutorContext((ParamRelation)relation, this.regionInfo.getDimensionCombination());
            IDataStatusMonitor dataStatusMonitor = DataStatusMonitorFactory.getMonitor((DimensionCombination)dimensionCombination, (String)this.relation.getFormSchemeDefine().getKey(), Collections.singletonList(this.relation.getFormDefine().getKey()), (IMonitor)this.dataQuery.getMonitor());
            this.dataQuery.setMonitor((IMonitor)dataStatusMonitor);
        }
    }

    protected void addQueryCol(List<MetaData> metaDatas) {
        for (MetaData metaData : metaDatas) {
            if (metaData.isFieldLink()) {
                int index = this.dataQuery.addColumn((FieldDefine)((DataFieldDTO)metaData.getDataField()));
                metaData.setIndex(index);
                continue;
            }
            metaData.setIndex(-1);
        }
    }

    protected void addFilter(Iterator<RowFilter> filterItr) {
        if (filterItr != null) {
            ArrayList<String> filters = new ArrayList<String>();
            while (filterItr.hasNext()) {
                RowFilter filter = filterItr.next();
                if (filter instanceof InValuesFilter) {
                    InValuesFilter inFilter = (InValuesFilter)filter;
                    String link = inFilter.getLink();
                    MetaData meta = this.relation.getMetaDataByLink(link);
                    if (meta == null || inFilter.getValues() == null) continue;
                    ArrayList<String> values = new ArrayList<String>(inFilter.getValues());
                    this.dataQuery.setColumnFilterValueList(meta.getIndex(), values);
                    continue;
                }
                String formula = filter.toFormula();
                if (filter.supportFormula()) {
                    filters.add("(" + formula + ")");
                    continue;
                }
                filters.add("(FILTER_ROW('" + filter.name() + "','" + formula + "'))");
                FilterFunction.getInstance().registerRowFilter(filter);
            }
            if (!filters.isEmpty()) {
                this.dataQuery.setRowFilter(String.join((CharSequence)" AND ", filters));
            }
        } else {
            String whereFilter;
            DataRegionDefine regionDefine = this.relation.getRegionDefine();
            String filterCondition = regionDefine.getFilterCondition();
            EntityDefaultValueFilter entityDefaultValueFilter = new EntityDefaultValueFilter(this.relation);
            String formula = entityDefaultValueFilter.toFormula();
            ArrayList<String> where = new ArrayList<String>();
            if (StringUtils.hasLength(filterCondition)) {
                where.add(filterCondition);
            }
            if (StringUtils.hasLength(formula)) {
                where.add(formula);
            }
            if (StringUtils.hasLength(whereFilter = String.join((CharSequence)" AND ", where))) {
                this.dataQuery.setRowFilter(whereFilter);
            }
        }
    }

    protected void writeAuth(List<MetaData> metaDatas) {
        DimensionCombination dimensionCombination = this.regionInfo.getDimensionCombination();
        DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
        FormSchemeDefine define = this.relation.getFormSchemeDefine();
        DimensionCollection collection = DimensionValueSetUtil.buildDimensionCollection((DimensionValueSet)dimensionValueSet, (String)define.getKey());
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(this.relation.getTaskDefine().getKey(), this.relation.getFormSchemeDefine().getKey());
        IBatchZBAccessResult zbWriteAccess = dataAccessService.getZBWriteAccess(collection, metaDatas.stream().filter(IMetaData::isFieldLink).map(MetaData::getDataField).filter(Objects::nonNull).map(Basic::getKey).collect(Collectors.toList()));
        try {
            for (MetaData metaData : metaDatas) {
                DataField dataField = metaData.getDataField();
                if (dataField == null) continue;
                IAccessResult access = zbWriteAccess.getAccess(dimensionCombination, dataField.getKey());
                metaData.setWriteAccessResult(access);
            }
        }
        catch (Exception e) {
            logger.error("\u8bfb\u5199\u6743\u9650\u5224\u65ad\u9519\u8bef", e);
            throw new CrudException(4001);
        }
    }

    protected List<SaveResItem> exceptionErrors(Exception e) {
        return this.exceptionErrors(null, e);
    }

    protected List<SaveResItem> exceptionErrors(SaveData saveData, Exception e) {
        SaveResItem resItem;
        DataValidateException dataValidateException;
        ArrayList<SaveResItem> errorList = new ArrayList<SaveResItem>();
        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        if (e instanceof ValueValidateException) {
            ValueValidateException valueValidateException = (ValueValidateException)e;
            List validateResults = valueValidateException.getRowValidateResults();
            for (RowValidateResult rowValidateResult : validateResults) {
                DimensionValueSet rowKeys = rowValidateResult.getRowKeys();
                DimensionCombination rowCombination = null;
                List<SaveRowData> rows = null;
                if (rowKeys != null) {
                    DimensionCombinationBuilder builder = new DimensionCombinationBuilder(rowKeys);
                    rowCombination = builder.getCombination();
                    if (saveData != null) {
                        rows = saveData.getRowsByDimensionValueSet(rowKeys);
                    }
                }
                List fieldValidateResults = rowValidateResult.getFieldValidateResults();
                for (FieldValidateResult fieldValidateResult : fieldValidateResults) {
                    String resultMsg = this.getResultMsg(fieldValidateResult);
                    if (!StringUtils.hasLength(resultMsg)) continue;
                    SaveResItem resItem2 = new SaveResItem();
                    resItem2.setDimension(rowCombination);
                    resItem2.setMessage(resultMsg);
                    if (rows != null) {
                        for (SaveRowData row : rows) {
                            resItem2.setRowIndex(row.getRowIndex());
                        }
                    }
                    errorList.add(resItem2);
                }
            }
        } else if (e instanceof ExpressionValidateException) {
            ExpressionValidateException expressionValidateException = (ExpressionValidateException)e;
            List rowExpressionValidResults = expressionValidateException.getRowExpressionValidResults();
            for (RowExpressionValidResult rowExpressionValidResult : rowExpressionValidResults) {
                List errorExpressions = rowExpressionValidResult.getErrorExpressions();
                DimensionValueSet rowKeys = rowExpressionValidResult.getRowKeys();
                List<SaveRowData> rows = null;
                DimensionCombination combination = null;
                if (rowKeys != null) {
                    DimensionCombinationBuilder builder = new DimensionCombinationBuilder(rowKeys);
                    combination = builder.getCombination();
                    if (saveData != null) {
                        rows = saveData.getRowsByDimensionValueSet(rowKeys);
                    }
                }
                for (IParsedExpression errorExpression : errorExpressions) {
                    SaveResItem resItem3 = new SaveResItem();
                    Formula formula = errorExpression.getSource();
                    String message = e.getMessage() + (StringUtils.hasLength(formula.getMeanning()) ? formula.getMeanning() : formula.getFormula());
                    if (!StringUtils.hasLength(message)) {
                        message = "\u6570\u636e\u6821\u9a8c\u5931\u8d25";
                    }
                    if (rows != null) {
                        for (SaveRowData row : rows) {
                            resItem3.setRowIndex(row.getRowIndex());
                        }
                    }
                    resItem3.setDimension(combination);
                    resItem3.setLinkKey(formula.getId());
                    resItem3.setMessage(message);
                    errorList.add(resItem3);
                }
            }
        } else if (e instanceof DataValidateException) {
            dataValidateException = (DataValidateException)e;
            SaveResItem resItem4 = new SaveResItem();
            String message = dataValidateException.getMessage();
            if (!StringUtils.hasLength(message)) {
                message = "\u6570\u636e\u6821\u9a8c\u5931\u8d25";
            }
            resItem4.setMessage(message);
            errorList.add(resItem4);
        } else if (e instanceof DuplicateRowKeysException) {
            dataValidateException = (DuplicateRowKeysException)e;
            List duplicateKeys = dataValidateException.getDuplicateKeys();
            if (duplicateKeys != null) {
                List dimFields = this.relation.getDimFields().stream().filter(k -> k.getDataFieldKind().equals((Object)DataFieldKind.TABLE_FIELD_DIM)).collect(Collectors.toList());
                for (DimensionValueSet duplicateKey : duplicateKeys) {
                    List<SaveRowData> rows;
                    ArrayList<String> titles = new ArrayList<String>();
                    ArrayList<String> values = new ArrayList<String>();
                    for (Object dataField : dimFields) {
                        Object dimValue;
                        String code = dataField.getCode();
                        String entityKey = dataField.getRefDataEntityKey();
                        if (StringUtils.hasText(entityKey)) {
                            code = this.entityMetaService.queryEntity(entityKey).getCode();
                        }
                        if (!Objects.nonNull(dimValue = duplicateKey.getValue(code))) continue;
                        titles.add(dataField.getTitle());
                        values.add(String.valueOf(dimValue));
                    }
                    StringBuilder message = new StringBuilder();
                    if (!titles.isEmpty()) {
                        Object dataField;
                        for (String title : titles) {
                            message.append("\u3010").append(title).append("\u3011");
                        }
                        if (titles.size() > 1) {
                            message.append("\u7684\u7ec4\u5408");
                        }
                        message.append("\u5b58\u5728\u91cd\u590d\u6570\u636e");
                        dataField = values.iterator();
                        while (dataField.hasNext()) {
                            String value = (String)dataField.next();
                            message.append("\u3010").append(value).append("\u3011");
                        }
                    }
                    if (message.length() > 0) {
                        message.append("\u3002");
                    }
                    SaveResItem resItem5 = new SaveResItem();
                    resItem5.setMessage(message.toString());
                    DimensionCombinationBuilder builder = new DimensionCombinationBuilder(duplicateKey);
                    resItem5.setDimension(builder.getCombination());
                    if (saveData != null && (rows = saveData.getRowsByDimensionValueSet(duplicateKey)) != null) {
                        for (SaveRowData row : rows) {
                            resItem5.setRowIndex(row.getRowIndex());
                        }
                    }
                    errorList.add(resItem5);
                }
            }
        } else if (e instanceof IncorrectQueryException) {
            resItem = new SaveResItem();
            resItem.setMessage(e.getMessage());
            errorList.add(resItem);
        }
        if (errorList.isEmpty()) {
            resItem = new SaveResItem();
            resItem.setMessage("\u6570\u636e\u4fdd\u5b58\u51fa\u9519");
            errorList.add(resItem);
        }
        return errorList;
    }

    protected String getResultMsg(FieldValidateResult fieldValidateResult) {
        List validateResults = fieldValidateResult.getValidateResult();
        if (CollectionUtils.isEmpty(validateResults)) {
            return null;
        }
        StringBuilder resultMsg = new StringBuilder();
        for (ValidateResult validateResult : validateResults) {
            if (validateResult.isResultValue()) continue;
            resultMsg.append(validateResult.getResultMsg());
        }
        return resultMsg.toString();
    }

    protected boolean isReadRegionOnly() {
        AbstractData abstractData;
        boolean readOnly = false;
        DataRegionDefine regionDefine = this.relation.getRegionDefine();
        String readOnlyCondition = regionDefine.getReadOnlyCondition();
        if (StringUtils.hasLength(readOnlyCondition) && (abstractData = this.dataEngineService.expressionEvaluate(readOnlyCondition, this.context, this.dataQuery.getMasterKeys(), this.relation)) instanceof BoolData) {
            try {
                readOnly = abstractData.getAsBool();
            }
            catch (DataTypeException e) {
                logger.error("\u516c\u5f0f{}\u89e3\u6790\u53d1\u751f\u9519\u8bef", (Object)readOnlyCondition, (Object)e);
            }
        }
        return readOnly;
    }
}

