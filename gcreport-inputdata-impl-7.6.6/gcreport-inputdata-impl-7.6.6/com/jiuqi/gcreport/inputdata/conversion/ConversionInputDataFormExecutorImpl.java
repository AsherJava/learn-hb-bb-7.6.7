/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo
 *  com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv
 *  com.jiuqi.gcreport.conversion.common.GcConversionResult
 *  com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO
 *  com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO
 *  com.jiuqi.gcreport.conversion.executor.AbstractConversionFormExecutor
 *  com.jiuqi.gcreport.conversion.utils.GcConversionUtils
 *  com.jiuqi.gcreport.conversion.utils.RateTypeUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.va.domain.common.MD5Util
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.conversion;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo;
import com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv;
import com.jiuqi.gcreport.conversion.common.GcConversionResult;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO;
import com.jiuqi.gcreport.conversion.executor.AbstractConversionFormExecutor;
import com.jiuqi.gcreport.conversion.utils.GcConversionUtils;
import com.jiuqi.gcreport.conversion.utils.RateTypeUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.conversion.realtime.ConversionRealTimeGather;
import com.jiuqi.gcreport.inputdata.conversion.realtime.IConversionRealTimeExecutor;
import com.jiuqi.gcreport.inputdata.formsetting.service.OffsetDimSettingService;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataDao;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputWriteNecLimitCondition;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataSrcTypeEnum;
import com.jiuqi.gcreport.inputdata.inputdata.service.InputDataLockService;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.va.domain.common.MD5Util;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class ConversionInputDataFormExecutorImpl
extends AbstractConversionFormExecutor {
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private ConversionRealTimeGather realTimeConversionGather;
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private OffsetDimSettingService offsetDimSettingService;
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private InputDataDao inputDataDao;
    private static final String SQL_UPDATE_CONVERTGROUPID_BEFORECONV_INPUTDATAS = "update  %1$s  set convertgroupid = ? where BIZKEYORDER = ? ";
    private static final String SQL_UPDATE_UNIONCHILDORG_AFTERCONV_INPUTDATAS = "update  %1$s  set offsetstate = ? where convertsrcid = ? ";
    private static String SQL_GET_UNIONCHILDORG_BEFORECONV_INPUTDATAS = " select e.ID, e.OFFSETSTATE \n from %6$s e \n join %4$s unit on e.MDCODE = unit.code \n join %4$s oppunit on e.oppunitid = oppunit.code \n where substr(to_char(unit.gcparents), %1$s, %5$s) <> substr(to_char(oppunit.gcparents), %1$s, %5$s) \n and substr(to_char(unit.parents), 1, %2$s)  = '%3$s'\n and substr(to_char(oppunit.parents), 1, %2$s)  = '%3$s' \n and oppunit.validtime <= ? and oppunit.invalidtime > ?  \n and unit.validtime <= ? and unit.invalidtime > ? \n and e.DATATIME = ? \n and e.MD_CURRENCY = ?  \n and e.MD_GCORGTYPE = ? \n and e.taskid = ?  \n and e.formid = ? ";

    public GcConversionResult conversion(GcConversionOrgAndFormContextEnv env, ConversionSystemTaskEO taskSchemeEO, List<ConversionSystemItemEO> systemItems, List<GcConversionIndexRateInfo> indexRateInfos, TableModelRunInfo tableInfo, Set<String> converisonFieldNames) {
        GcOrgCacheVO org;
        ConsolidatedTaskVO consolidatedTaskVO = this.taskService.getTaskByTaskKeyAndPeriodStr(env.getTaskId(), env.getPeriodStr());
        ConsolidatedOptionVO optionData = this.optionService.getOptionData(consolidatedTaskVO.getSystemId());
        if (optionData.getRealTimeConversion().booleanValue()) {
            if (!env.isConversionInputData()) {
                return new GcConversionResult(0, 0, 0);
            }
            if (!"UNIT_PATH".equals(optionData.getRealTimeConversionExecutorName())) {
                IConversionRealTimeExecutor byExecutorName = this.realTimeConversionGather.getByExecutorName(optionData.getRealTimeConversionExecutorName());
                byExecutorName.conversionButtonCheck(env);
            }
        }
        if (GcOrgKindEnum.UNIONORG.equals((Object)(org = env.getOrg()).getOrgKind()) || GcOrgKindEnum.DIFFERENCE.equals((Object)org.getOrgKind())) {
            return this.unionOrgConversion(env);
        }
        return this.notUnionOrgConversion(env, consolidatedTaskVO, taskSchemeEO, systemItems, indexRateInfos, tableInfo, converisonFieldNames, optionData.getRealTimeConversion());
    }

    private GcConversionResult unionOrgConversion(GcConversionOrgAndFormContextEnv env) {
        Map<String, Object> beforeConversionOffSetStateInfos = this.getUnionChildOrgBeforeConvInputDatas(env);
        int updateCount = this.updateUnionChildOrgAfterConvInputdatas(beforeConversionOffSetStateInfos, env.getTaskId());
        return new GcConversionResult(0, 0, updateCount);
    }

    private GcConversionResult notUnionOrgConversion(GcConversionOrgAndFormContextEnv env, ConsolidatedTaskVO consolidatedTaskVO, ConversionSystemTaskEO taskSchemeEO, List<ConversionSystemItemEO> systemItems, List<GcConversionIndexRateInfo> indexRateInfos, TableModelRunInfo tableInfo, Set<String> converisonFieldNames, boolean realTimeConversion) {
        boolean allowDuplicateKey = env.getDataRegionDefine().getAllowDuplicateKey();
        AtomicBoolean isExistsSegmentRateType = new AtomicBoolean(false);
        indexRateInfos.stream().forEach(indexRateInfo -> {
            String rateTypeCode = indexRateInfo.getRateTypeCode();
            if (StringUtils.isEmpty((String)rateTypeCode) || isExistsSegmentRateType.get()) {
                return;
            }
            boolean isSegmentRateType = RateTypeUtils.isSegmentRateType((String)rateTypeCode);
            if (isSegmentRateType) {
                isExistsSegmentRateType.set(true);
            }
        });
        GcConversionResult conversionResult = isExistsSegmentRateType.get() ? this.conversionByAllowSegment(env, consolidatedTaskVO, taskSchemeEO, systemItems, indexRateInfos, tableInfo, converisonFieldNames, realTimeConversion) : this.conversionByNotAllowSegment(env, tableInfo, converisonFieldNames, indexRateInfos, realTimeConversion);
        return conversionResult;
    }

    private GcConversionResult conversionByNotAllowSegment(GcConversionOrgAndFormContextEnv env, TableModelRunInfo tableInfo, Set<String> converisonFieldNames, List<GcConversionIndexRateInfo> indexRateInfos, boolean realTimeConversion) {
        int insertCount = 0;
        int deleteCount = 0;
        int updateCount = 0;
        try {
            Set dimFeildNames = this.getDimFieldNames(tableInfo);
            Collection converisonFieldDefines = this.getConverisonFieldDefines(tableInfo, converisonFieldNames);
            IDataTable beforeDataTable = GcConversionUtils.getQueryDataTable((GcConversionOrgAndFormContextEnv)env, (TableModelRunInfo)tableInfo, (Collection)converisonFieldDefines, (boolean)true);
            int beforeTotalCount = beforeDataTable.getTotalCount();
            HashMap<String, String> beforeIdToConvertGroupIdMap = new HashMap<String, String>(beforeTotalCount);
            IDataTable afterDataTable = GcConversionUtils.getQueryDataTable((GcConversionOrgAndFormContextEnv)env, (TableModelRunInfo)tableInfo, (Collection)converisonFieldDefines, (boolean)false);
            int afterTotalCount = afterDataTable.getTotalCount();
            ArrayList<String> copyFieldNames = new ArrayList<String>();
            copyFieldNames.add("MDCODE");
            copyFieldNames.add("SUBJECTCODE");
            copyFieldNames.add("SUBJECTOBJ");
            copyFieldNames.add("OPPUNITID");
            copyFieldNames.add("RECORDKEY");
            copyFieldNames.add("VERSIONID");
            copyFieldNames.add("FLOATORDER");
            FieldDefine orgCodeFieldDefine = this.getFieldDefineByFieldName(tableInfo, "ORGCODE");
            FieldDefine oppUnitCodeFieldDefine = this.getFieldDefineByFieldName(tableInfo, "OPPUNITID");
            String orgType = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)env.getTaskId());
            GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(env.getSchemeId(), env.getPeriodStr()));
            FieldDefine idFieldDefine = null;
            for (String converisonFieldName : converisonFieldNames) {
                String fieldName;
                FieldDefine fieldDefine = this.getFieldDefineByFieldName(tableInfo, converisonFieldName);
                switch (fieldName = converisonFieldName.toUpperCase()) {
                    case "ID": {
                        idFieldDefine = fieldDefine;
                        break;
                    }
                }
            }
            deleteCount = afterTotalCount;
            afterDataTable.deleteAll();
            HashSet<String> convertGroupIds = new HashSet<String>();
            HashMap<String, String> conversionOffsetGroupIdByOffsetState = new HashMap<String, String>();
            for (int i = 0; i < beforeTotalCount; ++i) {
                IDataRow beforeItem = beforeDataTable.getItem(i);
                String id = UUIDUtils.newUUIDStr();
                DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)DimensionValueSetUtil.getDimensionSet((DimensionValueSet)afterDataTable.getMasterKeys()));
                dimFeildNames.stream().forEach(dimFieldName -> {
                    FieldDefine dimFieldDefine = this.getFieldDefineByFieldName(tableInfo, (String)dimFieldName);
                    String dimensionName = tableInfo.getDimensionName(dimFieldName);
                    if (dimensionValueSet.getValue(dimensionName) == null) {
                        Object dimensionValue = beforeItem.getValue(dimFieldDefine).getAsObject();
                        dimensionValueSet.setValue(dimensionName, dimensionValue);
                    }
                });
                dimensionValueSet.setValue("RECORDKEY", (Object)id);
                dimensionValueSet.setValue("VERSIONID", (Object)"00000000-0000-0000-0000-000000000000");
                IDataRow afterItem = afterDataTable.appendRow(dimensionValueSet);
                ++insertCount;
                for (String converisonFieldName : converisonFieldNames) {
                    FieldDefine fieldDefine = this.getFieldDefineByFieldName(tableInfo, converisonFieldName);
                    if (dimFeildNames.contains(converisonFieldName)) {
                        Object dimFieldValue = this.getAfterDimensionFieldValue(tableInfo, beforeItem, afterItem, fieldDefine);
                        afterItem.setValue(fieldDefine, dimFieldValue);
                        continue;
                    }
                    AbstractData value = beforeItem.getValue(fieldDefine);
                    if (value.isNull) {
                        afterItem.setValue(fieldDefine, null);
                        continue;
                    }
                    GcConversionIndexRateInfo indexRateInfo = GcConversionUtils.getRateByFormKeyAndFieldKey(indexRateInfos, (String)env.getFormDefine().getKey(), (String)fieldDefine.getKey());
                    if (indexRateInfo != null) {
                        if (indexRateInfo.getRateTypeCode().equals(RateTypeEnum.NOTCONV.getCode())) continue;
                        if (indexRateInfo.getRateTypeCode().equals(RateTypeEnum.COPY.getCode())) {
                            Object beforeValue = value.getAsObject();
                            afterItem.setValue(fieldDefine, beforeValue);
                            continue;
                        }
                        BigDecimal zbRateValue = GcConversionUtils.getConversionZbRateValue((IDataRow)beforeItem, (GcConversionOrgAndFormContextEnv)env, (GcConversionIndexRateInfo)indexRateInfo);
                        BigDecimal beforeValue = value.getAsCurrency();
                        if (zbRateValue == null) continue;
                        afterItem.setValue(fieldDefine, (Object)(beforeValue == null ? BigDecimal.ZERO : beforeValue.multiply(zbRateValue)));
                        continue;
                    }
                    if (!copyFieldNames.contains(converisonFieldName)) continue;
                    afterItem.setValue(fieldDefine, value.getAsObject());
                }
                String afterCurrencyCode = env.getAfterCurrencyCode();
                boolean isOffsetCurrency = this.isOffsetCurrencyCode(instance, afterCurrencyCode, afterItem, orgCodeFieldDefine, oppUnitCodeFieldDefine);
                boolean offsetInfoFlag = env.isAfterConversionRealTimeOffset() && (!realTimeConversion || realTimeConversion && isOffsetCurrency);
                String offsetGroupId = null;
                String offsetState = null;
                for (String converisonFieldName : converisonFieldNames) {
                    String fieldName;
                    FieldDefine fieldDefine = this.getFieldDefineByFieldName(tableInfo, converisonFieldName);
                    switch (fieldName = converisonFieldName.toUpperCase()) {
                        case "ID": 
                        case "BIZKEYORDER": {
                            afterItem.setValue(fieldDefine, (Object)id);
                            break;
                        }
                        case "ORGID": {
                            afterItem.setValue(fieldDefine, (Object)env.getOrgId());
                            break;
                        }
                        case "MD_CURRENCY": {
                            afterItem.setValue(fieldDefine, (Object)afterCurrencyCode);
                            break;
                        }
                        case "SRCID": {
                            afterItem.setValue(fieldDefine, (Object)beforeItem.getValue(idFieldDefine).getAsString());
                            break;
                        }
                        case "CONVERTSRCID": {
                            afterItem.setValue(fieldDefine, (Object)beforeItem.getValue(idFieldDefine).getAsString());
                            break;
                        }
                        case "CONVERTGROUPID": {
                            String convertGroupId = beforeItem.getValue(fieldDefine).getAsString();
                            if (StringUtils.isEmpty((String)convertGroupId)) {
                                convertGroupId = UUIDUtils.newUUIDStr();
                                beforeItem.setValue(fieldDefine, (Object)convertGroupId);
                                beforeIdToConvertGroupIdMap.put(beforeItem.getValue(idFieldDefine).getAsString(), convertGroupId);
                            }
                            convertGroupIds.add(convertGroupId);
                            afterItem.setValue(fieldDefine, (Object)convertGroupId);
                            break;
                        }
                        case "SRCTYPE": {
                            afterItem.setValue(fieldDefine, (Object)InputDataSrcTypeEnum.CONVERSION.getValue());
                            break;
                        }
                        case "OFFSETTIME": {
                            afterItem.setValue(fieldDefine, null);
                            break;
                        }
                        case "OFFSETPERSON": {
                            afterItem.setValue(fieldDefine, null);
                            break;
                        }
                        case "OFFSETSTATE": {
                            if (offsetInfoFlag) {
                                afterItem.setValue(fieldDefine, (Object)"0");
                                break;
                            }
                            offsetState = beforeItem.getValue(fieldDefine).getAsString();
                            afterItem.setValue(fieldDefine, (Object)offsetState);
                            break;
                        }
                        case "OFFSETGROUPID": {
                            if (offsetInfoFlag) {
                                afterItem.setValue(fieldDefine, null);
                                break;
                            }
                            offsetGroupId = beforeItem.getValue(fieldDefine).getAsString();
                            afterItem.setValue(fieldDefine, (Object)offsetGroupId);
                            break;
                        }
                        case "DIFFAMT": {
                            afterItem.setValue(fieldDefine, (Object)BigDecimal.ZERO);
                            break;
                        }
                        case "OFFSETAMT": {
                            afterItem.setValue(fieldDefine, (Object)BigDecimal.ZERO);
                            break;
                        }
                    }
                }
                if (offsetInfoFlag) continue;
                conversionOffsetGroupIdByOffsetState.put(offsetGroupId, offsetState);
            }
            this.updateBeforeConvertGroupIdById(env, beforeIdToConvertGroupIdMap);
            String tableName = this.inputDataNameProvider.getTableNameByTaskId(env.getTaskId());
            List<InputDataEO> oppUnitInputDatas = this.getConversionOppUnitData(tableName, env.getAfterCurrencyCode(), conversionOffsetGroupIdByOffsetState, convertGroupIds);
            afterDataTable.commitChanges(true);
            if (!CollectionUtils.isEmpty(oppUnitInputDatas)) {
                this.inputDataDao.updateOffsetInfoManualConversion(oppUnitInputDatas, tableName);
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        return new GcConversionResult(deleteCount, insertCount, updateCount);
    }

    private GcConversionResult conversionByAllowSegment(GcConversionOrgAndFormContextEnv env, ConsolidatedTaskVO consolidatedTaskVO, ConversionSystemTaskEO taskSchemeEO, List<ConversionSystemItemEO> systemItems, List<GcConversionIndexRateInfo> indexRateInfos, TableModelRunInfo tableInfo, Set<String> converisonFieldNames, boolean realTimeConversion) {
        AtomicInteger insertCount = new AtomicInteger(0);
        int deleteCount = 0;
        int updateCount = 0;
        try {
            int afterTotalCount;
            FieldDefine orgCodeFieldDefine = this.getFieldDefineByFieldName(tableInfo, "ORGCODE");
            FieldDefine oppUnitCodeFieldDefine = this.getFieldDefineByFieldName(tableInfo, "OPPUNITID");
            String orgType = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)env.getTaskId());
            GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(env.getSchemeId(), env.getPeriodStr()));
            Set dimFeildNames = this.getDimFieldNames(tableInfo);
            Set<String> uniqueDimFieldNames = this.getDimFieldNames(env, consolidatedTaskVO, tableInfo);
            Collection converisonFieldDefines = this.getConverisonFieldDefines(tableInfo, converisonFieldNames);
            String priorPeriodStr = GcConversionUtils.getPriorPeriod((String)env.getPeriodStr());
            boolean isSameYear = GcConversionUtils.isSameYear((String)priorPeriodStr, (String)env.getPeriodStr());
            IDataTable priorBeforeQueryDataTable = GcConversionUtils.getPriorQueryDataTable((GcConversionOrgAndFormContextEnv)env, (Collection)converisonFieldDefines, (boolean)true);
            IDataTable priorAfterQueryDataTable = GcConversionUtils.getPriorQueryDataTable((GcConversionOrgAndFormContextEnv)env, (Collection)converisonFieldDefines, (boolean)false);
            IDataTable beforeQueryDataTable = GcConversionUtils.getQueryDataTable((GcConversionOrgAndFormContextEnv)env, (TableModelRunInfo)tableInfo, (Collection)converisonFieldDefines, (boolean)true);
            int beforeTotalCount = beforeQueryDataTable.getTotalCount();
            Map<String, List<IDataRow>> priorBeforeQueryDataTableMap = this.groupQueryDataTableByDimFields(uniqueDimFieldNames, tableInfo, priorBeforeQueryDataTable);
            Map<String, List<IDataRow>> priorAfterQueryDataTableMap = this.groupQueryDataTableByDimFields(uniqueDimFieldNames, tableInfo, priorAfterQueryDataTable);
            IDataTable afterDataTable = GcConversionUtils.getQueryDataTable((GcConversionOrgAndFormContextEnv)env, (TableModelRunInfo)tableInfo, (Collection)converisonFieldDefines, (boolean)false);
            deleteCount = afterTotalCount = afterDataTable.getTotalCount();
            afterDataTable.deleteAll();
            Map rateInfos = this.getConversionRateInfos(env, taskSchemeEO, systemItems);
            HashSet<String> convertGroupIds = new HashSet<String>();
            HashMap<String, String> conversionOffsetGroupIdByOffsetState = new HashMap<String, String>();
            HashMap<String, String> beforeIdToConvertGroupIdMap = new HashMap<String, String>(beforeTotalCount);
            ArrayList<String> copyFieldNames = new ArrayList<String>();
            copyFieldNames.add("MDCODE");
            copyFieldNames.add("SUBJECTCODE");
            copyFieldNames.add("SUBJECTOBJ");
            copyFieldNames.add("OPPUNITID");
            copyFieldNames.add("RECORDKEY");
            copyFieldNames.add("VERSIONID");
            copyFieldNames.add("FLOATORDER");
            FieldDefine idFieldDefine = null;
            for (String converisonFieldName : converisonFieldNames) {
                String fieldName;
                FieldDefine fieldDefine = this.getFieldDefineByFieldName(tableInfo, converisonFieldName);
                switch (fieldName = converisonFieldName.toUpperCase()) {
                    case "ID": {
                        idFieldDefine = fieldDefine;
                        break;
                    }
                }
            }
            for (int i = 0; i < beforeTotalCount; ++i) {
                IDataRow afterItem;
                IDataRow beforeItem = beforeQueryDataTable.getItem(i);
                String id = UUIDUtils.newUUIDStr();
                DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)DimensionValueSetUtil.getDimensionSet((DimensionValueSet)afterDataTable.getMasterKeys()));
                dimFeildNames.stream().forEach(dimFeildName -> {
                    String dimensionName = tableInfo.getDimensionName(dimFeildName);
                    if (StringUtils.isEmpty((String)dimensionName)) {
                        return;
                    }
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
                String afterCurrencyCode = env.getAfterCurrencyCode();
                boolean isOffsetCurrency = this.isOffsetCurrencyCode(instance, afterCurrencyCode, afterItem, orgCodeFieldDefine, oppUnitCodeFieldDefine);
                boolean offsetInfoFlag = env.isAfterConversionRealTimeOffset() && (!realTimeConversion || realTimeConversion && isOffsetCurrency);
                insertCount.incrementAndGet();
                String offsetGroupId = null;
                String offsetState = null;
                block43: for (String converisonFieldName : converisonFieldNames) {
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
                            continue block43;
                        }
                        case "ORGID": {
                            afterItem.setValue(fieldDefine, (Object)env.getOrgId());
                            continue block43;
                        }
                        case "MD_CURRENCY": {
                            afterItem.setValue(fieldDefine, (Object)env.getAfterCurrencyCode());
                            continue block43;
                        }
                        case "SRCID": {
                            afterItem.setValue(fieldDefine, (Object)beforeItem.getValue(idFieldDefine).getAsString());
                            continue block43;
                        }
                        case "CONVERTSRCID": {
                            afterItem.setValue(fieldDefine, (Object)beforeItem.getValue(idFieldDefine).getAsString());
                            continue block43;
                        }
                        case "CONVERTGROUPID": {
                            String convertGroupId = beforeItem.getValue(fieldDefine).getAsString();
                            if (StringUtils.isEmpty((String)convertGroupId)) {
                                convertGroupId = UUIDUtils.newUUIDStr();
                                beforeItem.setValue(fieldDefine, (Object)convertGroupId);
                                beforeIdToConvertGroupIdMap.put(beforeItem.getValue(idFieldDefine).getAsString(), convertGroupId);
                            }
                            afterItem.setValue(fieldDefine, (Object)convertGroupId);
                            continue block43;
                        }
                        case "SRCTYPE": {
                            afterItem.setValue(fieldDefine, (Object)InputDataSrcTypeEnum.CONVERSION.getValue());
                            continue block43;
                        }
                        case "OFFSETTIME": {
                            afterItem.setValue(fieldDefine, null);
                            continue block43;
                        }
                        case "OFFSETPERSON": {
                            afterItem.setValue(fieldDefine, null);
                            continue block43;
                        }
                        case "OFFSETSTATE": {
                            if (offsetInfoFlag) {
                                afterItem.setValue(fieldDefine, (Object)"0");
                                continue block43;
                            }
                            offsetState = beforeItem.getValue(fieldDefine).getAsString();
                            afterItem.setValue(fieldDefine, (Object)offsetState);
                            continue block43;
                        }
                        case "OFFSETGROUPID": {
                            if (offsetInfoFlag) {
                                afterItem.setValue(fieldDefine, null);
                                continue block43;
                            }
                            offsetGroupId = beforeItem.getValue(fieldDefine).getAsString();
                            afterItem.setValue(fieldDefine, (Object)offsetGroupId);
                            continue block43;
                        }
                        case "DIFFAMT": {
                            afterItem.setValue(fieldDefine, (Object)BigDecimal.ZERO);
                            continue block43;
                        }
                        case "OFFSETAMT": {
                            afterItem.setValue(fieldDefine, (Object)BigDecimal.ZERO);
                            continue block43;
                        }
                    }
                    AbstractData value = beforeItem.getValue(fieldDefine);
                    if (value.isNull) {
                        afterItem.setValue(fieldDefine, null);
                        continue;
                    }
                    GcConversionIndexRateInfo indexRateInfo = GcConversionUtils.getRateByFormKeyAndFieldKey(indexRateInfos, (String)env.getFormDefine().getKey(), (String)fieldDefine.getKey());
                    if (indexRateInfo == null) {
                        if (!copyFieldNames.contains(converisonFieldName)) continue;
                        afterItem.setValue(fieldDefine, value.getAsObject());
                        continue;
                    }
                    if (indexRateInfo.getRateTypeCode().equals(RateTypeEnum.NOTCONV.getCode())) continue;
                    if (indexRateInfo.getRateTypeCode().equals(RateTypeEnum.COPY.getCode())) {
                        Object beforeValue = value.getAsObject();
                        afterItem.setValue(fieldDefine, beforeValue);
                        continue;
                    }
                    BigDecimal afterValue = this.getFieldAfterValue(env, tableInfo, uniqueDimFieldNames, isSameYear, rateInfos, fieldDefine, indexRateInfo, beforeItem, priorBeforeQueryDataTableMap, priorAfterQueryDataTableMap);
                    if (afterValue == null) continue;
                    afterItem.setValue(fieldDefine, (Object)afterValue);
                }
                if (offsetInfoFlag) continue;
                conversionOffsetGroupIdByOffsetState.put(offsetGroupId, offsetState);
            }
            this.updateBeforeConvertGroupIdById(env, beforeIdToConvertGroupIdMap);
            String tableName = this.inputDataNameProvider.getTableNameByTaskId(env.getTaskId());
            List<InputDataEO> oppUnitInputDatas = this.getConversionOppUnitData(tableName, env.getAfterCurrencyCode(), conversionOffsetGroupIdByOffsetState, convertGroupIds);
            afterDataTable.commitChanges(true);
            if (!CollectionUtils.isEmpty(oppUnitInputDatas)) {
                this.inputDataDao.updateOffsetInfoManualConversion(oppUnitInputDatas, tableName);
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        return new GcConversionResult(deleteCount, insertCount.get(), updateCount);
    }

    public Set<String> getDimFieldNames(GcConversionOrgAndFormContextEnv env, ConsolidatedTaskVO consolidatedTaskVO, TableModelRunInfo tableInfo) {
        HashSet<String> dimFieldNames = new HashSet<String>();
        Set dataentryDimFieldNames = super.getDimFieldNames(tableInfo);
        dimFieldNames.addAll(dataentryDimFieldNames);
        Set<String> offsetDimFields = this.offsetDimSettingService.getDimsByFormId(env.getFormDefine().getKey(), consolidatedTaskVO.getSystemId());
        offsetDimFields.stream().forEach(offsetDimField -> {
            ColumnModelDefine columnModelDefine = tableInfo.getFieldByName(offsetDimField);
            if (columnModelDefine == null) {
                return;
            }
            ColumnModelType columnType = columnModelDefine.getColumnType();
            if (ColumnModelType.STRING.equals((Object)columnType) || ColumnModelType.UUID.equals((Object)columnType)) {
                dimFieldNames.add((String)offsetDimField);
            }
        });
        return dimFieldNames;
    }

    private BigDecimal getFieldAfterValue(GcConversionOrgAndFormContextEnv env, TableModelRunInfo tableRunInfo, Set<String> uniqueDimFieldNames, boolean isSameYear, Map<String, BigDecimal> rateInfos, FieldDefine conversionFeildDefine, GcConversionIndexRateInfo indexRateInfo, IDataRow beforeItem, Map<String, List<IDataRow>> priorBeforeQueryDataTableMap, Map<String, List<IDataRow>> priorAfterQueryDataTableMap) throws DataTypeException {
        BigDecimal afterValue;
        String rateTypeCode;
        RateTypeEnum rateTypeEnum;
        String groupMd5Value = this.getMD5ByItemDimFields(uniqueDimFieldNames, tableRunInfo, beforeItem);
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
                BigDecimal gsLjRateValue = GcConversionUtils.getConversionZbRateValue((IDataRow)beforeItem, (GcConversionOrgAndFormContextEnv)env, (GcConversionIndexRateInfo)indexRateInfo);
                if (gsLjRateValue == null) {
                    afterValue = null;
                    break;
                }
                afterValue = beforeFieldValue.subtract(priorBeforeFeildValue).multiply(gsLjRateValue).add(priorAfterFeildValue);
                break;
            }
            case SEGMENT_FORMULA_BN: {
                BigDecimal gsBnRateValue = GcConversionUtils.getConversionZbRateValue((IDataRow)beforeItem, (GcConversionOrgAndFormContextEnv)env, (GcConversionIndexRateInfo)indexRateInfo);
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
        BigDecimal zbRateValue = GcConversionUtils.getConversionZbRateValue((IDataRow)beforeItem, (GcConversionOrgAndFormContextEnv)env, (GcConversionIndexRateInfo)indexRateInfo);
        BigDecimal afterValue = zbRateValue == null ? null : beforeFieldValue.multiply(zbRateValue);
        return afterValue;
    }

    private Map<String, List<IDataRow>> groupQueryDataTableByDimFields(Set<String> uniqueDimFieldNames, TableModelRunInfo tableRunInfo, IDataTable queryDataTable) {
        HashMap<String, List<IDataRow>> queryDataTableMap = new HashMap<String, List<IDataRow>>();
        int priorBeforeQueryDataTotalCount = queryDataTable.getTotalCount();
        for (int i = 0; i < priorBeforeQueryDataTotalCount; ++i) {
            IDataRow dataRowItem = queryDataTable.getItem(i);
            String md5ByItemDimFields = this.getMD5ByItemDimFields(uniqueDimFieldNames, tableRunInfo, dataRowItem);
            if (!queryDataTableMap.containsKey(md5ByItemDimFields)) {
                queryDataTableMap.put(md5ByItemDimFields, new ArrayList());
            }
            ((List)queryDataTableMap.get(md5ByItemDimFields)).add(dataRowItem);
        }
        return queryDataTableMap;
    }

    private String getMD5ByItemDimFields(Set<String> uniqueDimFieldNames, TableModelRunInfo tableRunInfo, IDataRow dataRow) {
        StringBuilder dimFeildGroupValue = new StringBuilder();
        uniqueDimFieldNames.stream().forEach(uniqueDimFieldName -> {
            FieldDefine dimFieldDefine = this.getFieldDefineByFieldName(tableRunInfo, (String)uniqueDimFieldName);
            if (dimFieldDefine == null) {
                return;
            }
            if ("BIZKEYORDER".equalsIgnoreCase((String)uniqueDimFieldName)) {
                return;
            }
            String dimensionName = tableRunInfo.getDimensionName(uniqueDimFieldName);
            if (StringUtils.isEmpty((String)dimensionName)) {
                return;
            }
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void updateBeforeConvertGroupIdById(GcConversionOrgAndFormContextEnv env, Map<String, String> beforeIdToConvertGroupIdMap) {
        if (beforeIdToConvertGroupIdMap.isEmpty()) {
            return;
        }
        InputDataLockService inputDataLockService = (InputDataLockService)SpringContextUtils.getBean(InputDataLockService.class);
        InputWriteNecLimitCondition limitCondition = InputWriteNecLimitCondition.newLeafOrgLimit(env.getTaskId(), env.getPeriodStr(), env.getBeforeCurrencyCode(), env.getOrgId());
        String lockId = inputDataLockService.tryLock(beforeIdToConvertGroupIdMap.keySet(), limitCondition, "\u6298\u7b97\u6309\u94ae\uff0c\u66f4\u65b0\u6298\u7b97\u524d\u6570\u636e\u7684\u6298\u7b97\u5206\u7ec4ID");
        if (StringUtils.isEmpty((String)lockId)) {
            String userName = inputDataLockService.queryUserNameByInputItemId(beforeIdToConvertGroupIdMap.keySet());
            Object[] args = new String[]{userName};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.inputdataservice.lockidemptyoffsetfailmsg", (Object[])args));
        }
        try {
            ArrayList batchArgs = new ArrayList();
            JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
            InputDataNameProvider inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
            String tableName = inputDataNameProvider.getTableNameByTaskId(env.getTaskId());
            String sqlFormat = String.format(SQL_UPDATE_CONVERTGROUPID_BEFORECONV_INPUTDATAS, tableName);
            beforeIdToConvertGroupIdMap.forEach((id, convertGroupId) -> batchArgs.add(new Object[]{convertGroupId, id}));
            jdbcTemplate.batchUpdate(sqlFormat, batchArgs);
        }
        finally {
            inputDataLockService.unlock(lockId);
        }
    }

    public int updateUnionChildOrgAfterConvInputdatas(Map<String, Object> beforeConversionOffSetStateInfos, String taskId) {
        if (beforeConversionOffSetStateInfos == null || beforeConversionOffSetStateInfos.size() == 0) {
            return 0;
        }
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
        ArrayList batchArgs = new ArrayList();
        beforeConversionOffSetStateInfos.forEach((id, offSetState) -> batchArgs.add(new Object[]{offSetState, id}));
        InputDataNameProvider inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
        String tableName = inputDataNameProvider.getTableNameByTaskId(taskId);
        String sqlFormat = String.format(SQL_UPDATE_UNIONCHILDORG_AFTERCONV_INPUTDATAS, tableName);
        int[] updateCounts = jdbcTemplate.batchUpdate(sqlFormat, batchArgs);
        return updateCounts == null ? 0 : updateCounts.length;
    }

    private Map<String, Object> getUnionChildOrgBeforeConvInputDatas(GcConversionOrgAndFormContextEnv env) {
        GcOrgCacheVO hbOrg = env.getOrg();
        YearPeriodObject yp = new YearPeriodObject(null, env.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)env.getOrgVersionType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        Date orgvalidate = yp.formatYP().getEndDate();
        int orgCodeLen = tool.getOrgCodeLength();
        String hbUnitParents = hbOrg.getParentStr();
        int gcUnitParentsStartIndex = hbOrg.getGcParentStr().length() + 2;
        int gcUnitParentsEndIndex = hbUnitParents.length();
        InputDataNameProvider inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
        String tableName = inputDataNameProvider.getTableNameByTaskId(env.getTaskId());
        String formatSQL = String.format(SQL_GET_UNIONCHILDORG_BEFORECONV_INPUTDATAS, gcUnitParentsStartIndex, gcUnitParentsEndIndex, hbUnitParents, env.getOrgTypeId(), orgCodeLen, tableName);
        if (DimensionUtils.isExistAdjust((String)env.getTaskId())) {
            StringBuilder appendAdjustCond = new StringBuilder().append(" and e.ADJUST='").append(env.getSelectAdjustCode()).append("' ");
            formatSQL = formatSQL + appendAdjustCond;
        }
        List recordSet = EntNativeSqlDefaultDao.getInstance().selectMap(formatSQL, new Object[]{orgvalidate, orgvalidate, orgvalidate, orgvalidate, env.getPeriodStr(), env.getBeforeCurrencyCode(), env.getOrgTypeId(), env.getTaskId(), env.getFormDefine().getKey()});
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        recordSet.forEach(v -> {
            String id = ConverterUtils.getAsString(v.get("ID"));
            String offSetState = ConverterUtils.getAsString(v.get("OFFSETSTATE"));
            resultMap.put(id, offSetState);
        });
        return resultMap;
    }

    protected boolean isMatch(GcConversionOrgAndFormContextEnv formContextEnv) {
        String tableName = formContextEnv.getTableDefine().getCode();
        return tableName.contains("GC_INPUTDATA");
    }

    protected int matchOrder() {
        return Integer.MIN_VALUE;
    }

    private boolean isOffsetCurrencyCode(GcOrgCenterService instance, String afterCurrencyCode, IDataRow beforeItem, FieldDefine orgCodeFieldDefine, FieldDefine oppUnitFieldDefine) {
        String orgCode = beforeItem.getValue(orgCodeFieldDefine).getAsString();
        String oppUnitCode = beforeItem.getValue(oppUnitFieldDefine).getAsString();
        GcOrgCacheVO commonUnit = instance.getCommonUnit(instance.getOrgByCode(orgCode), instance.getOrgByCode(oppUnitCode));
        String offsetCurrencyCode = Objects.isNull(commonUnit) ? "" : String.valueOf(commonUnit.getTypeFieldValue("CURRENCYID"));
        return afterCurrencyCode.equals(offsetCurrencyCode);
    }

    private List<InputDataEO> getConversionOppUnitData(String tableName, String afterCurrencyCode, Map<String, String> conversionOffsetGroupIds, Collection<String> convertGroupIds) {
        List<InputDataEO> inputDataItems = this.inputDataDao.queryManualConversionInputDataByOffsetGroupIds(conversionOffsetGroupIds.keySet(), afterCurrencyCode, tableName);
        if (CollectionUtils.isEmpty(inputDataItems)) {
            return null;
        }
        ArrayList<InputDataEO> conversionOppUnitData = new ArrayList<InputDataEO>();
        for (InputDataEO inputData : inputDataItems) {
            String convertGroupId = inputData.getConvertGroupId();
            String currencyCode = inputData.getCurrency();
            if (convertGroupIds.contains(convertGroupId) || !afterCurrencyCode.equals(currencyCode)) continue;
            String offsetGroupId = inputData.getOffsetGroupId();
            inputData.setOffsetState(conversionOffsetGroupIds.get(offsetGroupId));
            inputData.setOffsetGroupId(offsetGroupId);
            inputData.setSrcType(InputDataSrcTypeEnum.CONVERSION.getValue());
            conversionOppUnitData.add(inputData);
        }
        return conversionOppUnitData;
    }
}

