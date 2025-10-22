/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo
 *  com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv
 *  com.jiuqi.gcreport.conversion.common.GcConversionResult
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.gcreport.conversion.executor.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo;
import com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv;
import com.jiuqi.gcreport.conversion.common.GcConversionResult;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemItemDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO;
import com.jiuqi.gcreport.conversion.executor.AbstractConversionFormExecutor;
import com.jiuqi.gcreport.conversion.utils.GcConversionUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ConversionSimpleFormExecutorImpl
extends AbstractConversionFormExecutor {
    @Override
    public GcConversionResult conversion(GcConversionOrgAndFormContextEnv env, ConversionSystemTaskEO taskSchemeEO, List<ConversionSystemItemEO> systemItems, List<GcConversionIndexRateInfo> indexRateInfos, TableModelRunInfo tableInfo, Set<String> converisonFieldNames) {
        int insertCount = 0;
        int deleteCount = 0;
        int updateCount = 0;
        try {
            IDataRow afterItem;
            Set<String> dimFeildNames = this.getDimFieldNames(tableInfo);
            Collection<FieldDefine> converisonFieldDefines = this.getConverisonFieldDefines(tableInfo, converisonFieldNames);
            String priorPeriodStr = GcConversionUtils.getPriorPeriod(env.getPeriodStr());
            boolean isSameYear = GcConversionUtils.isSameYear(priorPeriodStr, env.getPeriodStr());
            IDataTable priorBeforeQueryDataTable = GcConversionUtils.getPriorQueryDataTable(env, converisonFieldDefines, true);
            IDataTable priorAfterQueryDataTable = GcConversionUtils.getPriorQueryDataTable(env, converisonFieldDefines, false);
            IDataTable beforeDataTable = GcConversionUtils.getQueryDataTable(env, tableInfo, converisonFieldDefines, true);
            int beforeTotalCount = beforeDataTable.getTotalCount();
            IDataTable afterDataTable = GcConversionUtils.getQueryDataTable(env, tableInfo, converisonFieldDefines, false);
            int afterTotalCount = afterDataTable.getTotalCount();
            if (beforeTotalCount > 1 || afterTotalCount > 1) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.executor.simple.repeatdata.error"));
            }
            if (beforeTotalCount == 0) {
                if (afterTotalCount > 0) {
                    afterDataTable.deleteAll();
                    deleteCount = afterTotalCount;
                    afterDataTable.commitChanges(true);
                }
                return new GcConversionResult(insertCount, deleteCount, updateCount);
            }
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)DimensionValueSetUtil.getDimensionSet((DimensionValueSet)afterDataTable.getMasterKeys()));
            IDataRow beforeItem = beforeDataTable.getItem(0);
            if (afterTotalCount == 0) {
                afterItem = afterDataTable.appendRow(dimensionValueSet);
                ++insertCount;
            } else {
                afterItem = afterDataTable.getItem(0);
                ++updateCount;
            }
            Map<String, BigDecimal> rateInfos = this.getConversionRateInfos(env, taskSchemeEO, systemItems);
            for (String converisonFieldName : converisonFieldNames) {
                FieldDefine fieldDefine = this.getFieldDefineByFieldName(tableInfo, converisonFieldName);
                if (dimFeildNames.contains(converisonFieldName)) {
                    Object dimFieldValue = this.getAfterDimensionFieldValue(tableInfo, beforeItem, afterItem, fieldDefine);
                    afterItem.setValue(fieldDefine, dimFieldValue);
                    continue;
                }
                Object beforeFieldObjectValue = this.getFeildValueAsObject(beforeDataTable, fieldDefine);
                if (beforeFieldObjectValue == null) {
                    afterItem.setValue(fieldDefine, null);
                    continue;
                }
                GcConversionIndexRateInfo indexRateInfo = GcConversionUtils.getRateByFieldKey(indexRateInfos, fieldDefine.getKey());
                if (indexRateInfo == null || indexRateInfo.getRateTypeCode().equals(RateTypeEnum.NOTCONV.getCode())) continue;
                if (indexRateInfo.getRateTypeCode().equals(RateTypeEnum.COPY.getCode())) {
                    afterItem.setValue(fieldDefine, beforeFieldObjectValue);
                    continue;
                }
                BigDecimal afterValue = this.getFieldAfterValue(env, beforeDataTable, isSameYear, priorBeforeQueryDataTable, priorAfterQueryDataTable, rateInfos, fieldDefine, indexRateInfo);
                if (afterValue == null) continue;
                afterItem.setValue(fieldDefine, (Object)afterValue);
            }
            afterDataTable.commitChanges(true);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        return new GcConversionResult(insertCount, deleteCount, updateCount);
    }

    @Override
    protected List<ConversionSystemItemEO> getSystemItems(ConversionSystemTaskEO taskSchemeEO, GcConversionOrgAndFormContextEnv env, TableModelRunInfo tableInfo) {
        Set<String> tableFieldKeys = tableInfo.getColumnFieldMap().values().stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        ConversionSystemItemDao itemDao = (ConversionSystemItemDao)SpringContextUtils.getBean(ConversionSystemItemDao.class);
        List<ConversionSystemItemEO> systemItems = itemDao.getSystemItemsByIndexIds(tableFieldKeys);
        return systemItems;
    }

    public BigDecimal getFieldAfterValue(GcConversionOrgAndFormContextEnv env, IDataTable beforeDataTable, boolean isSameYear, IDataTable priorBeforeQueryDataTable, IDataTable priorAfterQueryDataTable, Map<String, BigDecimal> rateInfos, FieldDefine matchFeildDefine, GcConversionIndexRateInfo indexRateInfo) throws DataTypeException {
        BigDecimal beforeFieldValue = this.getFeildValueAsBigDecimal(beforeDataTable, matchFeildDefine);
        BigDecimal priorBeforeFeildValue = this.getFeildValueAsBigDecimal(priorBeforeQueryDataTable, matchFeildDefine);
        BigDecimal priorAfterFeildValue = this.getFeildValueAsBigDecimal(priorAfterQueryDataTable, matchFeildDefine);
        int converScheme = 1;
        if (indexRateInfo.getRateTypeCode().endsWith("_02")) {
            converScheme = 2;
        } else if (indexRateInfo.getRateTypeCode().endsWith("_03")) {
            converScheme = 3;
        }
        IDataRow dataRow = beforeDataTable.getTotalCount() == 0 ? null : beforeDataTable.getItem(0);
        BigDecimal zbRateValue = GcConversionUtils.getConversionZbRateValue(dataRow, env, indexRateInfo);
        if (zbRateValue == null || zbRateValue.compareTo(BigDecimal.ZERO) == 0) {
            zbRateValue = BigDecimal.ZERO;
        }
        BigDecimal afterValue = converScheme != 1 ? (!isSameYear && converScheme == 3 ? beforeFieldValue.multiply(zbRateValue) : beforeFieldValue.subtract(priorBeforeFeildValue).multiply(zbRateValue).add(priorAfterFeildValue)) : beforeFieldValue.multiply(zbRateValue);
        return afterValue;
    }

    public Object getFeildValueAsObject(IDataTable queryDataTable, FieldDefine matchFeildDefine) throws DataTypeException {
        int totalCount = queryDataTable.getTotalCount();
        Object fieldValue = null;
        if (totalCount == 1) {
            IDataRow item = queryDataTable.getItem(0);
            AbstractData value = item.getValue(matchFeildDefine);
            if (!value.isNull) {
                fieldValue = value.getAsObject();
            }
        }
        return fieldValue;
    }

    public BigDecimal getFeildValueAsBigDecimal(IDataTable queryDataTable, FieldDefine matchFeildDefine) throws DataTypeException {
        int totalCount = queryDataTable.getTotalCount();
        BigDecimal fieldValue = BigDecimal.ZERO;
        if (totalCount == 1) {
            IDataRow item = queryDataTable.getItem(0);
            AbstractData value = item.getValue(matchFeildDefine);
            if (!value.isNull) {
                fieldValue = value.getAsCurrency();
            }
        }
        return fieldValue;
    }

    @Override
    protected boolean isMatch(GcConversionOrgAndFormContextEnv formContextEnv) {
        return DataRegionKind.DATA_REGION_SIMPLE.equals((Object)formContextEnv.getDataRegionDefine().getRegionKind());
    }

    @Override
    protected int matchOrder() {
        return Integer.MAX_VALUE;
    }
}

