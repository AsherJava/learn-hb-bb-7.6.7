/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator
 *  com.jiuqi.np.dataengine.intf.IFieldValueProcessor
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.datacrud.impl.calc;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator;
import com.jiuqi.np.dataengine.intf.IFieldValueProcessor;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.Measure;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.api.IDataValueBalanceActuator;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.format.strategy.BalanceFieldValueConvert;
import com.jiuqi.nr.datacrud.impl.format.strategy.SysNumberTypeStrategy;
import com.jiuqi.nr.datacrud.impl.measure.MeasureData;
import com.jiuqi.nr.datacrud.impl.param.TaskDefineProxy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class RowBalanceServiceImpl
extends SysNumberTypeStrategy
implements IDataValueBalanceActuator {
    protected static final Logger LOGGER = LoggerFactory.getLogger(RowBalanceServiceImpl.class);
    private final Map<String, RegionRelation> relationKeyMap = new HashMap<String, RegionRelation>();

    @Override
    public void balanceValue(IRowData rowData) {
        if (rowData == null) {
            return;
        }
        this.resetValue(rowData);
        for (IDataValue linkDataValue : rowData.getLinkDataValues()) {
            this.balanceValue(linkDataValue.getRowData(), linkDataValue.getMetaData(), linkDataValue.getAbstractData());
        }
    }

    public void resetValue(IRowData rowData) {
        List<IDataValue> linkDataValues = rowData.getLinkDataValues();
        for (IDataValue linkDataValue : linkDataValues) {
            DataFieldType dataFieldType;
            IMetaData colMate = linkDataValue.getMetaData();
            DataLinkDefine dataLinkDefine = colMate.getDataLinkDefine();
            if (dataLinkDefine == null || DataFieldType.INTEGER != (dataFieldType = colMate.getDataFieldType()) && DataFieldType.BIGDECIMAL != dataFieldType) continue;
            AbstractData decimal = this.resetValue(linkDataValue);
            linkDataValue.setAbstractData(decimal);
        }
    }

    private AbstractData resetValue(IDataValue dataValue) {
        IMetaData metaData = dataValue.getMetaData();
        AbstractData abstractData = dataValue.getAbstractData();
        if (abstractData == null || abstractData.isNull) {
            return abstractData;
        }
        DataFieldType dataFieldType = metaData.getDataFieldType();
        if (DataFieldType.INTEGER != dataFieldType && DataFieldType.BIGDECIMAL != dataFieldType) {
            return abstractData;
        }
        if (abstractData.dataType == 6) {
            return abstractData;
        }
        if (DataFieldType.INTEGER == dataFieldType) {
            return abstractData;
        }
        BigDecimal asCurrency = abstractData.getAsCurrency();
        if (asCurrency == null) {
            return abstractData;
        }
        DataField field = metaData.getDataField();
        if (field == null && metaData.getFmAttribute() != null) {
            field = TaskDefineProxy.createDataFieldProxy(metaData.getFmAttribute());
        }
        if (field == null) {
            return abstractData;
        }
        Integer displayDigits = this.getNumDecimalPlaces(metaData.getDataLinkDefine(), metaData.getDataField(), asCurrency);
        if (displayDigits == null) {
            displayDigits = 0;
        }
        return AbstractData.valueOf((Object)asCurrency.setScale((int)displayDigits, RoundingMode.HALF_UP), (int)metaData.getDataType());
    }

    @Override
    protected Integer getNumDecimalPlaces(DataLinkDefine link, DataField field, BigDecimal value) {
        if (this.getGlobalNumDecimalPlaces() != null) {
            return this.getGlobalNumDecimalPlaces();
        }
        String measureUnit = link.getMeasureUnit();
        if (measureUnit == null && field != null) {
            measureUnit = field.getMeasureUnit();
        }
        if (StringUtils.hasLength(measureUnit) && measureUnit.endsWith("NotDimession")) {
            return super.getNumDecimalPlaces(link, field, value);
        }
        if (this.getNumDecimalPlaces() != null) {
            return this.getNumDecimalPlaces();
        }
        if (field != null) {
            return field.getDecimal();
        }
        if (value != null) {
            return value.scale();
        }
        return 2;
    }

    @Override
    public AbstractData balanceValue(IRowData currRowData, IMetaData metaData, AbstractData abstractData) {
        if (!this.isValidInput(currRowData, metaData)) {
            return abstractData;
        }
        String balanceExpression = metaData.getBalanceExpression();
        if (!StringUtils.hasLength(balanceExpression)) {
            return abstractData;
        }
        MemoryDataSet<FieldDefine> dataSet = this.createDataSetFromRowData(currRowData);
        BigDecimal currData = this.populateDataSetAndGetCurrentData(dataSet, currRowData, metaData);
        if (currData != null) {
            return this.evaluateBalanceExpression(dataSet, currData, metaData, currRowData.getMasterDimension());
        }
        return abstractData;
    }

    private boolean isValidInput(IRowData currRowData, IMetaData metaData) {
        return metaData != null && currRowData != null && currRowData.getMasterDimension() != null;
    }

    private MemoryDataSet<FieldDefine> createDataSetFromRowData(IRowData currRowData) {
        MemoryDataSet dataSet = new MemoryDataSet();
        for (IDataValue linkDataValue : currRowData.getLinkDataValues()) {
            IMetaData colMate = linkDataValue.getMetaData();
            if (colMate.getDataLinkDefine() == null || !this.isNumericField(colMate)) continue;
            String fieldName = this.getFieldName(colMate);
            dataSet.getMetadata().addColumn(new Column(fieldName, 10));
        }
        return dataSet;
    }

    private boolean isNumericField(IMetaData colMate) {
        DataFieldType dataFieldType = colMate.getDataFieldType();
        return dataFieldType == DataFieldType.INTEGER || dataFieldType == DataFieldType.BIGDECIMAL;
    }

    private String getFieldName(IMetaData colMate) {
        return colMate.getDeployInfos().stream().findFirst().map(DataFieldDeployInfo::getFieldName).orElse(colMate.getCode());
    }

    private BigDecimal populateDataSetAndGetCurrentData(MemoryDataSet<FieldDefine> dataSet, IRowData currRowData, IMetaData metaData) {
        DataRow row = dataSet.add();
        int index = 0;
        BigDecimal currData = null;
        for (IDataValue linkDataValue : currRowData.getLinkDataValues()) {
            IMetaData colMate = linkDataValue.getMetaData();
            if (colMate.getDataLinkDefine() == null || !this.isNumericField(colMate)) continue;
            BigDecimal decimal = this.getCalcDecimal(colMate, linkDataValue.getAbstractData());
            row.setBigDecimal(index++, decimal);
            if (!colMate.equals(metaData)) continue;
            currData = decimal;
        }
        return currData;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private AbstractData evaluateBalanceExpression(MemoryDataSet<FieldDefine> dataSet, BigDecimal currData, IMetaData metaData, DimensionCombination currMasterKey) {
        try (IDataSetExprEvaluator evaluator = this.dataAccessProvider.newDataSetExprEvaluator(dataSet);){
            DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(metaData.getLinkKey());
            String regionKey = dataLinkDefine.getRegionKey();
            RegionRelation regionRelation = this.relationKeyMap.computeIfAbsent(regionKey, k -> this.regionRelationFactory.getRegionRelation((String)k));
            ExecutorContext executorContext = this.executorContextFactory.getExecutorContext((ParamRelation)regionRelation, currMasterKey);
            IFmlExecEnvironment env = executorContext.getEnv();
            if (env instanceof ReportFmlExecEnvironment) {
                this.setupFieldValueProcessor((ReportFmlExecEnvironment)env, dataSet, regionRelation, metaData);
            }
            evaluator.prepare(executorContext, currMasterKey.toDimensionValueSet(), metaData.getBalanceExpression());
            DataRow row = dataSet.get(0);
            AbstractData evaluate = evaluator.evaluate(row);
            if (evaluate == null) return null;
            currData = currData.subtract(evaluate.getAsCurrency());
            AbstractData abstractData = AbstractData.valueOf((Object)currData, (int)10);
            return abstractData;
        }
        catch (Exception e) {
            LOGGER.warn(" \u5e73\u8861\u516c\u5f0f {} \u6267\u884c\u5931\u8d25 {}", metaData.getBalanceExpression(), dataSet, e);
        }
        return null;
    }

    private void setupFieldValueProcessor(ReportFmlExecEnvironment env, MemoryDataSet<FieldDefine> dataSet, RegionRelation regionRelation, IMetaData metaData) {
        Set<String> noConvertCols = dataSet.getMetadata().getColumns().stream().map(Column::getName).collect(Collectors.toSet());
        BalanceFieldValueConvert balanceFieldValueProcessor = new BalanceFieldValueConvert(this.selectMeasure, regionRelation.getMeasureView(), regionRelation.getMeasureData());
        balanceFieldValueProcessor.setMeasureService(this.measureService);
        balanceFieldValueProcessor.setRuntimeDataSchemeService(regionRelation.getRuntimeDataSchemeService());
        balanceFieldValueProcessor.setNoConvertCols(noConvertCols);
        balanceFieldValueProcessor.setNumDecimalPlaces(super.getNumDecimalPlaces(metaData.getDataLinkDefine(), metaData.getDataField(), null));
        env.setFieldValueProcessor((IFieldValueProcessor)balanceFieldValueProcessor);
    }

    @Override
    public void balanceValue(IDataValue dataValue) {
        IRowData rowData = dataValue.getRowData();
        if (rowData == null) {
            return;
        }
        this.resetValue(rowData);
        AbstractData abstractData = this.balanceValue(rowData, dataValue.getMetaData(), dataValue.getAbstractData());
        dataValue.setAbstractData(abstractData);
    }

    @Override
    public void setMeasure(Measure selectMeasure) {
        super.setSelectMeasure(selectMeasure);
    }

    @Override
    public void setMeasure(MeasureData measure) {
        this.selectMeasure = measure;
    }

    public BigDecimal getCalcDecimal(IMetaData metaData, AbstractData abstractData) {
        if (this.isInvalidInput(abstractData, metaData)) {
            return BigDecimal.ZERO;
        }
        if (this.isIntegerField(metaData)) {
            return this.handleIntegerField(abstractData);
        }
        return this.handleBigDecimalField(metaData, abstractData);
    }

    private boolean isInvalidInput(AbstractData abstractData, IMetaData metaData) {
        return abstractData == null || abstractData.isNull || !this.isNumericField(metaData) || abstractData.dataType == 6;
    }

    private boolean isIntegerField(IMetaData metaData) {
        return metaData.getDataFieldType() == DataFieldType.INTEGER;
    }

    private BigDecimal handleIntegerField(AbstractData abstractData) {
        int asInt = abstractData.getAsInt();
        return asInt == 0 ? BigDecimal.ZERO : BigDecimal.valueOf(asInt);
    }

    private BigDecimal handleBigDecimalField(IMetaData metaData, AbstractData abstractData) {
        BigDecimal asCurrency = abstractData.getAsCurrency();
        if (asCurrency == null || BigDecimal.ZERO.compareTo(asCurrency) == 0) {
            return BigDecimal.ZERO;
        }
        DataField field = this.resolveDataField(metaData);
        if (field == null) {
            return BigDecimal.ZERO;
        }
        Integer displayDigits = this.getDisplayDigits(metaData, asCurrency);
        return asCurrency.setScale((int)displayDigits, RoundingMode.HALF_UP);
    }

    private DataField resolveDataField(IMetaData metaData) {
        DataField field = metaData.getDataField();
        if (field == null && metaData.getFmAttribute() != null) {
            field = TaskDefineProxy.createDataFieldProxy(metaData.getFmAttribute());
        }
        return field;
    }

    private Integer getDisplayDigits(IMetaData metaData, BigDecimal asCurrency) {
        Integer displayDigits = super.getNumDecimalPlaces(metaData.getDataLinkDefine(), metaData.getDataField(), asCurrency);
        return displayDigits != null ? displayDigits : 0;
    }
}

