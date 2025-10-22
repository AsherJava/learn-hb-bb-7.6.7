/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo
 *  com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv
 *  com.jiuqi.gcreport.conversion.common.GcConversionResult
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.va.domain.common.MD5Util
 */
package com.jiuqi.gcreport.conversion.executor.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo;
import com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv;
import com.jiuqi.gcreport.conversion.common.GcConversionResult;
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
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.va.domain.common.MD5Util;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ConversionFloatFormExecutorImpl
extends AbstractConversionFormExecutor {
    @Override
    public GcConversionResult conversion(GcConversionOrgAndFormContextEnv env, ConversionSystemTaskEO taskSchemeEO, List<ConversionSystemItemEO> systemItems, List<GcConversionIndexRateInfo> indexRateInfos, TableModelRunInfo tableInfo, Set<String> converisonFieldNames) {
        boolean allowDuplicateKey = env.getDataRegionDefine().getAllowDuplicateKey();
        AtomicBoolean isExistsSegmentRateType = new AtomicBoolean(false);
        indexRateInfos.stream().forEach(indexRateInfo -> {
            boolean isSegmentRateType;
            String rateTypeCode = indexRateInfo.getRateTypeCode();
            if (StringUtils.isEmpty((String)rateTypeCode) || isExistsSegmentRateType.get()) {
                return;
            }
            boolean bl = isSegmentRateType = rateTypeCode.endsWith("_02") || rateTypeCode.endsWith("_03");
            if (isSegmentRateType) {
                isExistsSegmentRateType.set(true);
            }
        });
        if (allowDuplicateKey && isExistsSegmentRateType.get()) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.executor.float.isExistsSegmentRateType.error"));
        }
        GcConversionResult conversionResult = !allowDuplicateKey && isExistsSegmentRateType.get() ? this.conversionByAllowSegment(env, taskSchemeEO, systemItems, indexRateInfos, tableInfo, converisonFieldNames) : this.conversionByNotAllowSegment(env, taskSchemeEO, systemItems, indexRateInfos, tableInfo, converisonFieldNames);
        return conversionResult;
    }

    private GcConversionResult conversionByAllowSegment(GcConversionOrgAndFormContextEnv env, ConversionSystemTaskEO taskSchemeEO, List<ConversionSystemItemEO> systemItems, List<GcConversionIndexRateInfo> indexRateInfos, TableModelRunInfo tableInfo, Set<String> converisonFieldNames) {
        AtomicInteger insertCount = new AtomicInteger(0);
        int deleteCount = 0;
        int updateCount = 0;
        try {
            int afterTotalCount;
            Set<String> dimFeildNames = this.getDimFieldNames(tableInfo);
            Collection<FieldDefine> converisonFieldDefines = this.getConverisonFieldDefines(tableInfo, converisonFieldNames);
            String priorPeriodStr = GcConversionUtils.getPriorPeriod(env.getPeriodStr());
            boolean isSameYear = GcConversionUtils.isSameYear(priorPeriodStr, env.getPeriodStr());
            IDataTable priorBeforeQueryDataTable = GcConversionUtils.getPriorQueryDataTable(env, converisonFieldDefines, true);
            IDataTable priorAfterQueryDataTable = GcConversionUtils.getPriorQueryDataTable(env, converisonFieldDefines, false);
            IDataTable beforeQueryDataTable = GcConversionUtils.getQueryDataTable(env, tableInfo, converisonFieldDefines, true);
            int beforeTotalCount = beforeQueryDataTable.getTotalCount();
            Map<String, List<IDataRow>> priorBeforeQueryDataTableMap = this.groupQueryDataTableByDimFields(dimFeildNames, tableInfo, priorBeforeQueryDataTable);
            Map<String, List<IDataRow>> priorAfterQueryDataTableMap = this.groupQueryDataTableByDimFields(dimFeildNames, tableInfo, priorAfterQueryDataTable);
            IDataTable afterDataTable = GcConversionUtils.getQueryDataTable(env, tableInfo, converisonFieldDefines, false);
            deleteCount = afterTotalCount = afterDataTable.getTotalCount();
            afterDataTable.deleteAll();
            Map<String, BigDecimal> rateInfos = this.getConversionRateInfos(env, taskSchemeEO, systemItems);
            for (int i = 0; i < beforeTotalCount; ++i) {
                IDataRow afterItem;
                IDataRow beforeItem = beforeQueryDataTable.getItem(i);
                String id = UUIDUtils.newUUIDStr();
                DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)DimensionValueSetUtil.getDimensionSet((DimensionValueSet)afterDataTable.getMasterKeys()));
                dimFeildNames.stream().forEach(dimFeildName -> {
                    String dimensionName = tableInfo.getDimensionName(dimFeildName);
                    FieldDefine dimFieldDefine = this.getFieldDefineByFieldName(tableInfo, (String)dimFeildName);
                    if (dimensionValueSet.getValue(dimensionName) == null) {
                        Object dimensionValue = beforeItem.getValue(dimFieldDefine).getAsObject();
                        dimensionValueSet.setValue(dimensionName, dimensionValue);
                    }
                });
                dimensionValueSet.setValue("RECORDKEY", (Object)id);
                dimensionValueSet.setValue("VERSIONID", (Object)"00000000-0000-0000-0000-000000000000");
                try {
                    afterItem = afterDataTable.appendRow(dimensionValueSet);
                }
                catch (IncorrectQueryException e) {
                    throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
                }
                insertCount.incrementAndGet();
                block12: for (String converisonFieldName : converisonFieldNames) {
                    String fieldName;
                    FieldDefine fieldDefine = this.getFieldDefineByFieldName(tableInfo, converisonFieldName);
                    if (dimFeildNames.contains(converisonFieldName)) {
                        Object dimFieldValue = this.getAfterDimensionFieldValue(tableInfo, beforeItem, afterItem, fieldDefine);
                        afterItem.setValue(fieldDefine, dimFieldValue);
                        continue;
                    }
                    switch (fieldName = converisonFieldName.toUpperCase()) {
                        case "ID": 
                        case "BIZKEYORDER": {
                            afterItem.setValue(fieldDefine, (Object)id);
                            continue block12;
                        }
                    }
                    AbstractData value = beforeItem.getValue(fieldDefine);
                    if (value.isNull) {
                        afterItem.setValue(fieldDefine, null);
                        continue;
                    }
                    if (fieldName.equals("FLOATORDER")) {
                        Object beforeValue = value.getAsObject();
                        afterItem.setValue(fieldDefine, beforeValue);
                        continue;
                    }
                    GcConversionIndexRateInfo indexRateInfo = GcConversionUtils.getRateByFormKeyAndFieldKey(indexRateInfos, env.getFormDefine().getKey(), fieldDefine.getKey());
                    if (indexRateInfo == null || indexRateInfo.getRateTypeCode().equals(RateTypeEnum.NOTCONV.getCode())) continue;
                    if (indexRateInfo.getRateTypeCode().equals(RateTypeEnum.COPY.getCode())) {
                        Object beforeValue = value.getAsObject();
                        afterItem.setValue(fieldDefine, beforeValue);
                        continue;
                    }
                    BigDecimal afterValue = this.getFieldAfterValue(env, tableInfo, dimFeildNames, isSameYear, rateInfos, fieldDefine, indexRateInfo, beforeItem, priorBeforeQueryDataTableMap, priorAfterQueryDataTableMap);
                    if (afterValue == null) continue;
                    afterItem.setValue(fieldDefine, (Object)afterValue);
                }
            }
            afterDataTable.commitChanges(true);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        return new GcConversionResult(deleteCount, insertCount.get(), updateCount);
    }

    public GcConversionResult conversionByNotAllowSegment(GcConversionOrgAndFormContextEnv env, ConversionSystemTaskEO taskSchemeEO, List<ConversionSystemItemEO> systemItems, List<GcConversionIndexRateInfo> indexRateInfos, TableModelRunInfo tableInfo, Set<String> converisonFieldNames) {
        int insertCount = 0;
        int deleteCount = 0;
        int updateCount = 0;
        try {
            int afterTotalCount;
            Set<String> dimFeildNames = this.getDimFieldNames(tableInfo);
            Collection<FieldDefine> converisonFieldDefines = this.getConverisonFieldDefines(tableInfo, converisonFieldNames);
            IDataTable beforeDataTable = GcConversionUtils.getQueryDataTable(env, tableInfo, converisonFieldDefines, true);
            int beforeTotalCount = beforeDataTable.getTotalCount();
            IDataTable afterDataTable = GcConversionUtils.getQueryDataTable(env, tableInfo, converisonFieldDefines, false);
            deleteCount = afterTotalCount = afterDataTable.getTotalCount();
            afterDataTable.deleteAll();
            for (int i = 0; i < beforeTotalCount; ++i) {
                IDataRow beforeItem = beforeDataTable.getItem(i);
                String id = UUIDUtils.newUUIDStr();
                DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)DimensionValueSetUtil.getDimensionSet((DimensionValueSet)afterDataTable.getMasterKeys()));
                dimFeildNames.stream().forEach(dimFeildName -> {
                    String dimensionName = tableInfo.getDimensionName(dimFeildName);
                    FieldDefine dimFieldDefine = this.getFieldDefineByFieldName(tableInfo, (String)dimFeildName);
                    if (dimensionValueSet.getValue(dimensionName) == null) {
                        Object dimensionValue = beforeItem.getValue(dimFieldDefine).getAsObject();
                        dimensionValueSet.setValue(dimensionName, dimensionValue);
                    }
                });
                dimensionValueSet.setValue("RECORDKEY", (Object)id);
                dimensionValueSet.setValue("VERSIONID", (Object)"00000000-0000-0000-0000-000000000000");
                IDataRow afterItem = afterDataTable.appendRow(dimensionValueSet);
                ++insertCount;
                block10: for (String converisonFieldName : converisonFieldNames) {
                    Object beforeValue;
                    String fieldName;
                    FieldDefine fieldDefine = this.getFieldDefineByFieldName(tableInfo, converisonFieldName);
                    if (dimFeildNames.contains(converisonFieldName)) {
                        Object dimFieldValue = this.getAfterDimensionFieldValue(tableInfo, beforeItem, afterItem, fieldDefine);
                        afterItem.setValue(fieldDefine, dimFieldValue);
                        continue;
                    }
                    switch (fieldName = converisonFieldName.toUpperCase()) {
                        case "ID": 
                        case "BIZKEYORDER": {
                            afterItem.setValue(fieldDefine, (Object)id);
                            continue block10;
                        }
                    }
                    AbstractData value = beforeItem.getValue(fieldDefine);
                    if (value.isNull) {
                        afterItem.setValue(fieldDefine, null);
                        continue;
                    }
                    if (fieldName.equals("FLOATORDER")) {
                        Object beforeValue2 = value.getAsObject();
                        afterItem.setValue(fieldDefine, beforeValue2);
                        continue;
                    }
                    GcConversionIndexRateInfo indexRateInfo = GcConversionUtils.getRateByFormKeyAndFieldKey(indexRateInfos, env.getFormDefine().getKey(), fieldDefine.getKey());
                    if (indexRateInfo == null || indexRateInfo.getRateTypeCode().equals(RateTypeEnum.NOTCONV.getCode())) continue;
                    if (indexRateInfo.getRateTypeCode().equals(RateTypeEnum.COPY.getCode())) {
                        beforeValue = value.getAsObject();
                        afterItem.setValue(fieldDefine, beforeValue);
                        continue;
                    }
                    beforeValue = value.getAsCurrency();
                    BigDecimal zbRateValue = GcConversionUtils.getConversionZbRateValue(beforeItem, env, indexRateInfo);
                    if (zbRateValue == null) continue;
                    afterItem.setValue(fieldDefine, (Object)(beforeValue == null ? BigDecimal.ZERO : ((BigDecimal)beforeValue).multiply(zbRateValue)));
                }
            }
            afterDataTable.commitChanges(true);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        return new GcConversionResult(deleteCount, insertCount, updateCount);
    }

    private BigDecimal getFieldAfterValue(GcConversionOrgAndFormContextEnv env, TableModelRunInfo tableRunInfo, Set<String> dimFieldNames, boolean isSameYear, Map<String, BigDecimal> rateInfos, FieldDefine conversionFeildDefine, GcConversionIndexRateInfo indexRateInfo, IDataRow beforeItem, Map<String, List<IDataRow>> priorBeforeQueryDataTableMap, Map<String, List<IDataRow>> priorAfterQueryDataTableMap) throws DataTypeException {
        BigDecimal afterValue;
        String rateTypeCode;
        RateTypeEnum rateTypeEnum;
        String groupMd5Value = this.getMD5ByItemDimFields(dimFieldNames, tableRunInfo, beforeItem);
        List<IDataRow> priorBeforeQueryDataRows = priorBeforeQueryDataTableMap.get(groupMd5Value);
        List<IDataRow> priorAfterQueryDataRows = priorAfterQueryDataTableMap.get(groupMd5Value);
        BigDecimal beforeFieldValue = this.getFeildValueAsBigDecimal(beforeItem, conversionFeildDefine);
        if (BigDecimal.ZERO.equals(beforeFieldValue)) {
            return BigDecimal.ZERO;
        }
        BigDecimal priorBeforeFeildValue = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(priorBeforeQueryDataRows)) {
            IDataRow dataRow = priorBeforeQueryDataRows.get(0);
            priorBeforeFeildValue = this.getFeildValueAsBigDecimal(dataRow, conversionFeildDefine);
        }
        BigDecimal priorAfterFeildValue = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(priorAfterQueryDataRows)) {
            IDataRow dataRow = priorAfterQueryDataRows.get(0);
            priorAfterFeildValue = this.getFeildValueAsBigDecimal(dataRow, conversionFeildDefine);
        }
        if ((rateTypeEnum = RateTypeEnum.getEnumByCode((String)(rateTypeCode = indexRateInfo.getRateTypeCode()))) == null) {
            BigDecimal afterValue2 = this.defaultConversionZbRateValue(env, indexRateInfo, beforeItem, beforeFieldValue);
            return afterValue2;
        }
        switch (rateTypeEnum) {
            case SEGMENT_QC_LJ: {
                BigDecimal qcLjRateValue = rateInfos.get(RateTypeEnum.QC.getCode());
                if (qcLjRateValue == null || qcLjRateValue.compareTo(BigDecimal.ZERO) == 0) {
                    qcLjRateValue = BigDecimal.ONE;
                }
                afterValue = beforeFieldValue.subtract(priorBeforeFeildValue).multiply(qcLjRateValue).add(priorAfterFeildValue);
                break;
            }
            case SEGMENT_QC_BN: {
                BigDecimal qcBnRateValue = rateInfos.get(RateTypeEnum.QC.getCode());
                if (qcBnRateValue == null || qcBnRateValue.compareTo(BigDecimal.ZERO) == 0) {
                    qcBnRateValue = BigDecimal.ONE;
                }
                if (!isSameYear) {
                    afterValue = beforeFieldValue.multiply(qcBnRateValue);
                    break;
                }
                afterValue = beforeFieldValue.subtract(priorBeforeFeildValue).multiply(qcBnRateValue).add(priorAfterFeildValue);
                break;
            }
            case SEGMENT_PJ_LJ: {
                BigDecimal pjLjRateValue = rateInfos.get(RateTypeEnum.PJ.getCode());
                if (pjLjRateValue == null || pjLjRateValue.compareTo(BigDecimal.ZERO) == 0) {
                    pjLjRateValue = BigDecimal.ONE;
                }
                afterValue = beforeFieldValue.subtract(priorBeforeFeildValue).multiply(pjLjRateValue).add(priorAfterFeildValue);
                break;
            }
            case SEGMENT_PJ_BN: {
                BigDecimal pjBnRateValue = rateInfos.get(RateTypeEnum.PJ.getCode());
                if (pjBnRateValue == null || pjBnRateValue.compareTo(BigDecimal.ZERO) == 0) {
                    pjBnRateValue = BigDecimal.ONE;
                }
                if (!isSameYear) {
                    afterValue = beforeFieldValue.multiply(pjBnRateValue);
                    break;
                }
                afterValue = beforeFieldValue.subtract(priorBeforeFeildValue).multiply(pjBnRateValue).add(priorAfterFeildValue);
                break;
            }
            case SEGMENT_FORMULA_LJ: {
                BigDecimal gsLjRateValue = GcConversionUtils.getConversionZbRateValue(beforeItem, env, indexRateInfo);
                if (gsLjRateValue == null) {
                    afterValue = null;
                    break;
                }
                afterValue = beforeFieldValue.subtract(priorBeforeFeildValue).multiply(gsLjRateValue).add(priorAfterFeildValue);
                break;
            }
            case SEGMENT_FORMULA_BN: {
                BigDecimal gsBnRateValue = GcConversionUtils.getConversionZbRateValue(beforeItem, env, indexRateInfo);
                if (gsBnRateValue == null) {
                    afterValue = null;
                    break;
                }
                if (!isSameYear) {
                    afterValue = beforeFieldValue.multiply(gsBnRateValue);
                    break;
                }
                afterValue = beforeFieldValue.subtract(priorBeforeFeildValue).multiply(gsBnRateValue).add(priorAfterFeildValue);
                break;
            }
            default: {
                afterValue = this.defaultConversionZbRateValue(env, indexRateInfo, beforeItem, beforeFieldValue);
            }
        }
        return afterValue;
    }

    private BigDecimal defaultConversionZbRateValue(GcConversionOrgAndFormContextEnv env, GcConversionIndexRateInfo indexRateInfo, IDataRow beforeItem, BigDecimal beforeFieldValue) {
        BigDecimal zbRateValue = GcConversionUtils.getConversionZbRateValue(beforeItem, env, indexRateInfo);
        BigDecimal afterValue = zbRateValue == null ? null : beforeFieldValue.multiply(zbRateValue);
        return afterValue;
    }

    private Map<String, List<IDataRow>> groupQueryDataTableByDimFields(Set<String> dimFieldNames, TableModelRunInfo tableRunInfo, IDataTable queryDataTable) {
        HashMap<String, List<IDataRow>> queryDataTableMap = new HashMap<String, List<IDataRow>>();
        int priorBeforeQueryDataTotalCount = queryDataTable.getTotalCount();
        for (int i = 0; i < priorBeforeQueryDataTotalCount; ++i) {
            IDataRow dataRowItem = queryDataTable.getItem(i);
            String md5ByItemDimFields = this.getMD5ByItemDimFields(dimFieldNames, tableRunInfo, dataRowItem);
            if (!queryDataTableMap.containsKey(md5ByItemDimFields)) {
                queryDataTableMap.put(md5ByItemDimFields, new ArrayList());
            }
            ((List)queryDataTableMap.get(md5ByItemDimFields)).add(dataRowItem);
        }
        return queryDataTableMap;
    }

    private String getMD5ByItemDimFields(Set<String> dimFieldNames, TableModelRunInfo tableRunInfo, IDataRow dataRow) {
        StringBuilder dimFeildGroupValue = new StringBuilder();
        dimFieldNames.stream().forEach(dimFieldName -> {
            FieldDefine dimFieldDefine = this.getFieldDefineByFieldName(tableRunInfo, (String)dimFieldName);
            String dimensionName = tableRunInfo.getDimensionName(dimFieldName);
            if ("MD_CURRENCY".equalsIgnoreCase(dimensionName)) {
                return;
            }
            if ("DATATIME".equalsIgnoreCase(dimensionName)) {
                return;
            }
            dimFeildGroupValue.append(dataRow.getAsString(dimFieldDefine)).append("||");
        });
        String md5Value = MD5Util.encrypt((String)dimFeildGroupValue.toString());
        return md5Value;
    }

    private BigDecimal getFeildValueAsBigDecimal(IDataRow dataRow, FieldDefine matchFeildDefine) throws DataTypeException {
        BigDecimal fieldValue = BigDecimal.ZERO;
        AbstractData value = dataRow.getValue(matchFeildDefine);
        if (!value.isNull) {
            fieldValue = value.getAsCurrency();
        }
        return fieldValue;
    }

    @Override
    protected boolean isMatch(GcConversionOrgAndFormContextEnv formContextEnv) {
        boolean isFloatRegion = !DataRegionKind.DATA_REGION_SIMPLE.equals((Object)formContextEnv.getDataRegionDefine().getRegionKind());
        return isFloatRegion;
    }

    @Override
    protected int matchOrder() {
        return Integer.MAX_VALUE;
    }
}

