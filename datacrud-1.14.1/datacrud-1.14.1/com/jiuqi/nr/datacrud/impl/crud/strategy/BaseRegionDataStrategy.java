/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.ICommonQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchZBAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService
 *  com.jiuqi.nvwa.encryption.desensitization.common.DesensitizedEncryptor
 */
package com.jiuqi.nr.datacrud.impl.crud.strategy;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchZBAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.LinkSort;
import com.jiuqi.nr.datacrud.Measure;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.SortMode;
import com.jiuqi.nr.datacrud.impl.DataValue;
import com.jiuqi.nr.datacrud.impl.GroupMetaData;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.RowData;
import com.jiuqi.nr.datacrud.impl.crud.inner.RegionDataSetStrategyFactory;
import com.jiuqi.nr.datacrud.impl.crud.strategy.DefaultValueEvaluate;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.datacrud.spi.IDataValueProcessor;
import com.jiuqi.nr.datacrud.spi.IExecutorContextFactory;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datacrud.spi.filter.EntityDefaultValueFilter;
import com.jiuqi.nr.datacrud.spi.filter.FilterFunction;
import com.jiuqi.nr.datacrud.spi.filter.InValuesFilter;
import com.jiuqi.nr.datacrud.util.DataValueProcessorFactory;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService;
import com.jiuqi.nvwa.encryption.desensitization.common.DesensitizedEncryptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public abstract class BaseRegionDataStrategy<Q extends ICommonQuery>
extends DefaultValueEvaluate {
    protected IQueryInfo queryInfo;
    protected RegionRelation relation;
    protected Q dataQuery;
    protected ExecutorContext context;
    protected final RegionRelationFactory regionRelationFactory;
    protected final DataEngineService dataEngineService;
    protected final IDataAccessServiceProvider dataAccessServiceProvider;
    protected final IRuntimeExpressionService expressionService;
    protected final IExecutorContextFactory executorContextFactory;
    protected final DataValueProcessorFactory dataValueProcessorFactory;
    protected final DesensitizedEncryptor desensitizedEncryptor;
    protected IDataValueProcessor dataValueProcessor;
    protected static Logger logger = LoggerFactory.getLogger(BaseRegionDataStrategy.class);

    public BaseRegionDataStrategy(RegionDataSetStrategyFactory factory) {
        super(factory.getDataEngineService());
        this.regionRelationFactory = factory.getRegionRelationFactory();
        this.dataEngineService = factory.getDataEngineService();
        this.dataAccessServiceProvider = factory.getDataAccessServiceProvider();
        this.expressionService = factory.getExpressionService();
        this.executorContextFactory = factory.getExecutorContextFactory();
        this.dataValueProcessorFactory = factory.getDataValueProcessorFactory();
        this.desensitizedEncryptor = factory.getDesensitizedEncryptor();
    }

    protected void initDataQuery(IQueryInfo queryInfo, RegionRelation relation) {
        this.queryInfo = queryInfo;
        this.relation = relation;
        if (this.relation == null) {
            this.relation = this.regionRelationFactory.getRegionRelation(queryInfo.getRegionKey());
        }
        this.dataQuery = this.getDataQuery(queryInfo, relation);
        DimensionCombination dimensionCombination = this.queryInfo.getDimensionCombination();
        if (dimensionCombination != null) {
            this.dataQuery.setMasterKeys(dimensionCombination.toDimensionValueSet());
            this.context = this.executorContextFactory.getExecutorContext((ParamRelation)relation, queryInfo.getDimensionCombination(), queryInfo.variableItr());
        }
    }

    protected abstract Q getDataQuery(IQueryInfo var1, RegionRelation var2);

    protected void addQueryCol(List<MetaData> metaDatas) {
        for (MetaData metaData : metaDatas) {
            if (metaData.isFieldLink()) {
                int index = this.dataQuery.addColumn((FieldDefine)((DataFieldDTO)metaData.getDataField()));
                metaData.setIndex(index);
                continue;
            }
            if (!metaData.isFormulaLink()) continue;
            this.addExpressionColumn(metaData);
        }
    }

    protected void addExpressionColumn(MetaData metaData) {
        String linkExpression = metaData.getDataLinkDefine().getLinkExpression();
        if (StringUtils.hasLength(linkExpression)) {
            int index = this.dataQuery.addExpressionColumn(linkExpression);
            metaData.setIndex(index);
        }
    }

    protected void addFilter() {
        Iterator<RowFilter> filterItr = this.queryInfo.rowFilterItr();
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

    protected void readAuth(List<MetaData> metaDatas) {
        DimensionCombination dimensionCombination = this.queryInfo.getDimensionCombination();
        DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
        FormSchemeDefine define = this.relation.getFormSchemeDefine();
        DimensionCollection collection = DimensionValueSetUtil.buildDimensionCollection((DimensionValueSet)dimensionValueSet, (String)define.getKey());
        TaskDefine taskDefine = this.relation.getTaskDefine();
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(taskDefine.getKey(), define.getKey());
        IBatchZBAccessResult zbReadAccess = dataAccessService.getZBReadAccess(collection, metaDatas.stream().filter(IMetaData::isFieldLink).map(MetaData::getDataField).filter(Objects::nonNull).map(Basic::getKey).collect(Collectors.toList()));
        try {
            for (MetaData metaData : metaDatas) {
                DataField dataField = metaData.getDataField();
                if (dataField == null) continue;
                IAccessResult access = zbReadAccess.getAccess(dimensionCombination, dataField.getKey());
                metaData.setAccessResult(access);
            }
        }
        catch (Exception e) {
            logger.error("\u8bfb\u5199\u6743\u9650\u5224\u65ad\u9519\u8bef", e);
            throw new CrudException(4001);
        }
    }

    protected List<IRowData> buildData(List<MetaData> metaData) {
        return this.buildData(null, metaData, -1, -1);
    }

    protected List<IRowData> buildData(IReadonlyTable table, List<MetaData> metaData) {
        return this.buildData(table, metaData, -1, -1);
    }

    protected List<IRowData> buildData(IReadonlyTable table, List<MetaData> metaData, int begin, int end) {
        if (begin < 0) {
            begin = 0;
        }
        try {
            int tableCount;
            if (table == null) {
                table = this.dataQuery.executeReader(this.context);
            }
            if ((tableCount = table.getCount()) <= 0) {
                return Collections.emptyList();
            }
            if (end < 0 || end > tableCount) {
                end = tableCount;
            }
            if (end - begin <= 0) {
                return Collections.emptyList();
            }
            return this.buildAllRow(table, metaData, begin, end);
        }
        catch (Exception e) {
            throw new CrudException(4101, "\u67e5\u8be2\u6570\u636e\u5931\u8d25", e);
        }
    }

    protected List<IRowData> buildAllRow(IReadonlyTable table, List<MetaData> metaData, int begin, int end) {
        IDataRow row;
        int i;
        ArrayList<IRowData> rows = new ArrayList<IRowData>(end - begin);
        int filledIndex = 0;
        for (i = 0; i < begin; ++i) {
            boolean detailRow;
            row = table.getItem(i);
            boolean bl = detailRow = row.getGroupTreeDeep() < 0;
            if (detailRow) {
                ++filledIndex;
                continue;
            }
            filledIndex = 0;
        }
        for (i = begin; i < end; ++i) {
            row = table.getItem(i);
            RowData rowData = new RowData();
            ArrayList<IDataValue> values = new ArrayList<IDataValue>(metaData.size() + 1);
            rowData.setDataValues(values);
            DimensionCombinationBuilder builder = new DimensionCombinationBuilder(row.getRowKeys());
            DimensionCombination combination = builder.getCombination();
            rowData.setDimension(combination);
            rowData.setMasterDimension(this.queryInfo.getDimensionCombination());
            boolean detailRow = row.getGroupTreeDeep() < 0;
            rowData.setFilledRow(row.isFilledRow());
            if (detailRow) {
                rowData.setDetailSeqNum(++filledIndex);
            } else {
                filledIndex = 0;
            }
            rowData.setGroupingFlag(row.getGroupTreeDeep());
            rowData.setGroupTreeDeep(row.getGroupTreeDeep());
            for (MetaData meta : metaData) {
                DataValue value;
                AbstractData abstractData = this.metaDataTransfer(meta, row, rowData);
                if (detailRow) {
                    value = new DataValue(meta, abstractData);
                } else {
                    GroupMetaData groupMetaData = new GroupMetaData(meta);
                    value = new DataValue(groupMetaData, abstractData);
                }
                value.setRowData(rowData);
                values.add(value);
            }
            this.metaRowTransfer(row, rowData);
            rows.add(rowData);
        }
        return rows;
    }

    protected void metaRowTransfer(IDataRow row, RowData rowData) {
        Measure measure;
        if (this.dataValueProcessor == null && (measure = this.queryInfo.getMeasure()) != null) {
            this.dataValueProcessor = this.dataValueProcessorFactory.initMeasureDataValueProcessor(this.relation, measure);
        }
        if (this.dataValueProcessor != null) {
            List<IDataValue> dataValues = rowData.getDataValues();
            for (IDataValue dataValue : dataValues) {
                AbstractData abstractData = dataValue.getAbstractData();
                IMetaData metaData = dataValue.getMetaData();
                abstractData = this.dataValueProcessor.processValue(metaData, abstractData);
                dataValue.setAbstractData(abstractData);
            }
        }
    }

    protected AbstractData metaDataTransfer(MetaData metaData, IDataRow row, RowData rowData) {
        AbstractData abstractData;
        block4: {
            String dataMaskCode;
            AbstractData defaultValue;
            int index = metaData.getIndex();
            abstractData = index < 0 ? AbstractData.valueOf(null, (int)metaData.getDataType()) : row.getValue(index);
            DataField dataField = metaData.getDataField();
            if (abstractData.isNull && dataField != null && StringUtils.hasLength(dataField.getDefaultValue()) && (defaultValue = this.evaluateDefaultValue(this.context, this.relation, row.getRowKeys(), metaData)) != null) {
                abstractData = defaultValue;
            }
            if (this.queryInfo.isDesensitized() && dataField != null && dataField.getDataFieldType() == DataFieldType.STRING && StringUtils.hasLength(dataMaskCode = dataField.getDataMaskCode()) && !abstractData.getAsNull()) {
                String valueStr = abstractData.getAsString();
                try {
                    String desensitizeValue = this.desensitizedEncryptor.desensitize(dataMaskCode, valueStr);
                    return AbstractData.valueOf((Object)desensitizeValue, (int)metaData.getDataType());
                }
                catch (Exception e) {
                    logger.warn(" \u8131\u654f\u6267\u884c\u5931\u8d25 | \u7801\u503c:{} | \u539f\u503c:{} | \u9519\u8bef:{}", dataMaskCode, valueStr, e.getMessage());
                    if (!logger.isDebugEnabled()) break block4;
                    logger.error(" \u8131\u654f\u6267\u884c\u5931\u8d25 | \u7801\u503c:{} | \u539f\u503c:{}", dataMaskCode, valueStr, e);
                }
            }
        }
        return abstractData;
    }

    public int queryDataCount(IQueryInfo queryInfo, RegionRelation relation) {
        this.initDataQuery(queryInfo, relation);
        this.addFilter();
        this.addQueryCol(relation.getMetaData());
        try {
            IReadonlyTable table = this.dataQuery.executeReader(this.context);
            return table.getTotalCount();
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u533a\u57df\u4e0b\u6570\u636e\u6761\u6570\u5931\u8d25", e);
            return 0;
        }
    }

    protected void addOrder() {
        Iterator<LinkSort> sortItr = this.queryInfo.linkSortItr();
        boolean appendFloatOrder = true;
        if (sortItr == null) {
            List<LinkSort> regionDefaultOrder = this.relation.getRegionDefaultOrder();
            sortItr = regionDefaultOrder.iterator();
        }
        while (sortItr.hasNext()) {
            LinkSort next = sortItr.next();
            String linkKey = next.getLinkKey();
            SortMode mode = next.getMode();
            if ("FLOATORDER".equals(linkKey)) {
                DataField floatOrderField = this.relation.getFloatOrderField();
                if (floatOrderField == null) continue;
                appendFloatOrder = false;
                this.dataQuery.addOrderByItem((FieldDefine)((DataFieldDTO)floatOrderField), mode == SortMode.DESC);
                continue;
            }
            MetaData byLink = this.relation.getMetaDataByLink(linkKey);
            if (byLink == null || byLink.getDataField() == null) continue;
            this.dataQuery.addOrderByItem((FieldDefine)((DataFieldDTO)byLink.getDataField()), mode == SortMode.DESC);
        }
        if (appendFloatOrder && this.relation.getFloatOrderField() != null) {
            this.dataQuery.addOrderByItem((FieldDefine)((DataFieldDTO)this.relation.getFloatOrderField()), false);
        }
        this.dataQuery.setIgnoreDefaultOrderBy(true);
    }
}

