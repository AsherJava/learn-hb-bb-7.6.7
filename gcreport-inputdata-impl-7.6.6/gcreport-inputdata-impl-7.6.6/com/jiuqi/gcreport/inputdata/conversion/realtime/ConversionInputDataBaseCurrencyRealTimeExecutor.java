/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.DoubleKeyMap
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.rate.client.vo.CommonRateInfoVO
 *  com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo
 *  com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv
 *  com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemTaskDao
 *  com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO
 *  com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.rate.impl.service.CommonRateService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.dataentry.service.IFuncExecuteService
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.service.RunTimeTaskDefineService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.conversion.realtime;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.DoubleKeyMap;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.rate.client.vo.CommonRateInfoVO;
import com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo;
import com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemTaskDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.inputdata.conversion.realtime.AbstractConversionInputDataRealTime;
import com.jiuqi.gcreport.inputdata.conversion.realtime.RealTimeConversionContextDTO;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.rate.impl.service.CommonRateService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.service.RunTimeTaskDefineService;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ConversionInputDataBaseCurrencyRealTimeExecutor
extends AbstractConversionInputDataRealTime {
    @Autowired
    private ConversionSystemTaskDao taskSchemeDao;
    @Autowired
    private IFuncExecuteService iFuncExecuteService;
    @Autowired
    private RunTimeTaskDefineService taskDefineService;
    @Autowired
    private CommonRateService commonRateService;

    @Override
    public String getExecutorName() {
        return "BASE_CURRENCY";
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Map<String, List<InputDataEO>> realTimeConversion(DataEntryContext dataEntryContext, List<InputDataEO> updateInputItems, List<InputDataEO> inputItems) {
        String unitCode = dataEntryContext.getDimensionSet().getOrDefault("MD_ORG", new DimensionValue()).getValue();
        String currency = dataEntryContext.getDimensionSet().getOrDefault("MD_CURRENCY", new DimensionValue()).getValue();
        String period = dataEntryContext.getDimensionSet().getOrDefault("DATATIME", new DimensionValue()).getValue();
        String orgType = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)dataEntryContext.getTaskKey());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, period));
        List<String> conversionCurrencyList = this.getConversionCurrencyList(unitCode, currency, period, orgType, dataEntryContext.getTaskKey());
        if (CollectionUtils.isEmpty(conversionCurrencyList)) {
            return this.notConversionGetNeedOffsetData(instance, inputItems, currency);
        }
        RealTimeConversionContextDTO realTimeConversionContext = this.initContext(dataEntryContext, conversionCurrencyList);
        if (realTimeConversionContext == null) {
            return this.notConversionGetNeedOffsetData(instance, inputItems, currency);
        }
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(dataEntryContext.getTaskKey());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        ArrayList<InputDataEO> needUpdateInputDataList = new ArrayList<InputDataEO>();
        ArrayList<InputDataEO> conversionSaveDataList = new ArrayList<InputDataEO>();
        HashMap<String, List<InputDataEO>> currencyToInputDataListMap = new HashMap<String, List<InputDataEO>>(16);
        for (InputDataEO inputData : inputItems) {
            String commonSuperiorBaseCurrency = this.getCommonSuperiorBaseCurrency(instance, inputData);
            if (StringUtils.isEmpty((String)inputData.getConvertGroupId())) {
                inputData.setConvertGroupId(UUIDUtils.newUUIDStr());
                needUpdateInputDataList.add(inputData);
            }
            this.addRealTimeOffsetData(currency, currencyToInputDataListMap, inputData, commonSuperiorBaseCurrency);
            for (String conversionCurrency : conversionCurrencyList) {
                InputDataEO conversionInputData = this.conversionInputData(realTimeConversionContext, inputData, currency, conversionCurrency);
                conversionSaveDataList.add(conversionInputData);
                this.addRealTimeOffsetData(conversionCurrency, currencyToInputDataListMap, conversionInputData, commonSuperiorBaseCurrency);
            }
        }
        if (!CollectionUtils.isEmpty(needUpdateInputDataList)) {
            dao.updateBatch(needUpdateInputDataList);
        }
        if (!CollectionUtils.isEmpty(conversionSaveDataList)) {
            dao.addBatch(conversionSaveDataList);
        }
        this.conversionCalculate(dataEntryContext, conversionCurrencyList);
        InputDataEO inputData = inputItems.get(0);
        this.logger.info("\u7528\u6237\u3010{}\u3011\u5728\u3010{}\u3011\u5bf9\u3010{}\u3011\u8868\u505a\u4e86\u65b0\u589e\u6216\u66f4\u65b0\u64cd\u4f5c\uff0c\u540c\u6b65\u6298\u7b97\u51fa\u4ece\u672c\u4f4d\u5e01\u3010{}\u3011\u5230\u5e01\u79cd\u3010{}\u3011\u5bf9\u5e94\u7684\u6570\u636e", inputData.getCreateUser(), DateUtils.nowTimeStr(), this.getFormCodeAndTitle(inputData.getFormId()), currency, CollectionUtils.toString(conversionCurrencyList));
        return currencyToInputDataListMap;
    }

    @Override
    public List<String> deleteHistoryDataAndGetConversionCurrency(String taskId, Map<String, String> dimFieldAndValueMapping, List<String> inputItemIds) {
        String orgType;
        String period;
        String currency;
        String unitCode = dimFieldAndValueMapping.get("MDCODE");
        List<String> conversionCurrencyList = this.getConversionCurrencyList(unitCode, currency = dimFieldAndValueMapping.get("MD_CURRENCY"), period = dimFieldAndValueMapping.get("DATATIME"), orgType = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)taskId), taskId);
        if (CollectionUtils.isEmpty(conversionCurrencyList)) {
            this.updateOffsetStatus(taskId, inputItemIds, currency, period, orgType);
            return conversionCurrencyList;
        }
        this.executeDeleteHistorySql(taskId, inputItemIds, conversionCurrencyList);
        return conversionCurrencyList;
    }

    private void updateOffsetStatus(String taskId, List<String> inputItemIds, String currency, String period, String orgType) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        List<InputDataEO> deleteInputDataList = this.inputDataDao.queryByIds(inputItemIds, tableName);
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, period));
        ArrayList<String> covertGroupIdList = new ArrayList<String>();
        for (InputDataEO inputData : deleteInputDataList) {
            String commonSuperiorBaseCurrency = this.getCommonSuperiorBaseCurrency(instance, inputData);
            if (!currency.equals(commonSuperiorBaseCurrency)) continue;
            covertGroupIdList.add(inputData.getConvertGroupId());
        }
        if (covertGroupIdList.size() > 0) {
            this.executeUpdateOffsetState(tableName, covertGroupIdList);
        }
    }

    private void executeUpdateOffsetState(String tableName, List<String> covertGroupIdList) {
        StringBuilder updateSqlSb = new StringBuilder();
        updateSqlSb.append(" update %1$s i \n");
        updateSqlSb.append("    set offsetgroupId=null,offsetState='0', \n");
        updateSqlSb.append("        diffamt=0,offsetamt=0,recordTimestamp=? \n");
        updateSqlSb.append("  where ").append(SqlBuildUtil.getStrInCondi((String)"CONVERTGROUPID", covertGroupIdList));
        String updateSql = String.format(updateSqlSb.toString(), tableName);
        this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class).execute(updateSql, new Object[]{LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli()});
    }

    @Override
    public boolean conversionButtonCheck(GcConversionOrgAndFormContextEnv env) {
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)env.getOrgVersionType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, env.getPeriodStr()));
        GcOrgCacheVO orgToJsonVO = instance.getOrgByCode(env.getOrgId());
        String baseCurrencyId = String.valueOf(orgToJsonVO.getTypeFieldValue("CURRENCYID"));
        if (StringUtils.isEmpty((String)baseCurrencyId)) {
            throw new BusinessRuntimeException("\u5355\u4f4d\u672c\u4f4d\u5e01\u4e3a\u7a7a\uff0c\u4e0d\u5141\u8bb8\u6298\u7b97");
        }
        String beforeCurrencyCode = env.getBeforeCurrencyCode();
        if (!baseCurrencyId.equals(beforeCurrencyCode)) {
            throw new BusinessRuntimeException("\u6298\u7b97\u524d\u5e01\u79cd\u4e0d\u662f\u672c\u4f4d\u5e01\uff0c\u4e0d\u5141\u8bb8\u6298\u7b97");
        }
        return true;
    }

    @Override
    public List<String> getCancelOffsetCurrencyList(String taskId, Map<String, String> dimFieldAndValueMapping) {
        String unitCode = dimFieldAndValueMapping.get("MDCODE");
        String currency = dimFieldAndValueMapping.get("MD_CURRENCY");
        String period = dimFieldAndValueMapping.get("DATATIME");
        String orgType = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)taskId);
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, period));
        GcOrgCacheVO orgToJsonVO = instance.getOrgByCode(unitCode);
        if (orgToJsonVO == null) {
            return Collections.emptyList();
        }
        String baseCurrencyId = String.valueOf(orgToJsonVO.getTypeFieldValue("CURRENCYID"));
        if (StringUtils.isEmpty((String)baseCurrencyId) || baseCurrencyId.trim().equals(currency)) {
            return Collections.emptyList();
        }
        return Arrays.asList(currency);
    }

    private List<String> getConversionCurrencyList(String unitCode, String currency, String period, String orgType, String taskKey) {
        String[] reportCurrencyCodeArray;
        String taskTitle;
        try {
            TaskDefine taskDefine = this.taskDefineService.queryTaskDefine(taskKey);
            Assert.isNotNull((Object)taskDefine);
            taskTitle = taskDefine.getTitle();
        }
        catch (Exception e) {
            this.logger.info("\u83b7\u53d6\u62a5\u8868\u4efb\u52a1\u4fe1\u606f\u5931\u8d25\uff0c\u4efb\u52a1id\uff1a{}", (Object)taskKey);
            return Collections.emptyList();
        }
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, period));
        GcOrgCacheVO orgToJsonVO = instance.getOrgByCode(unitCode);
        if (orgToJsonVO == null) {
            this.logger.info("\u4efb\u52a1\u3010{}\u3011,\u5355\u4f4d\u3010{}\u3011\u4fee\u6539\u5185\u90e8\u8868\u6570\u636e\u65f6\u56e0\u4e3a\u6ca1\u80fd\u5728\u5355\u4f4d\u7c7b\u578b\u3010{}\u3011\u65f6\u671f\u3010{}\u3011\u4e2d\u67e5\u627e\u5230\u5f53\u524d\u5355\u4f4d,\u4e0d\u5b9e\u65f6\u6298\u7b97\uff01", taskTitle, unitCode, orgType, period);
            return Collections.emptyList();
        }
        String baseCurrencyId = String.valueOf(orgToJsonVO.getTypeFieldValue("CURRENCYID"));
        if (StringUtils.isEmpty((String)baseCurrencyId) || !baseCurrencyId.trim().equals(currency)) {
            this.logger.info("\u4efb\u52a1\u3010{}\u3011,\u5355\u4f4d{}\u4fee\u6539\u5185\u90e8\u8868\u6570\u636e\u65f6\u56e0\u4e3a\u6ca1\u6709\u8bbe\u7f6e\u672c\u4f4d\u5e01\uff0c\u4e0d\u5b9e\u65f6\u6298\u7b97\uff01", (Object)taskTitle, (Object)unitCode);
            return Collections.emptyList();
        }
        String reportCurrencyIdListStr = String.valueOf(orgToJsonVO.getTypeFieldValue("CURRENCYIDS"));
        if (StringUtils.isEmpty((String)reportCurrencyIdListStr)) {
            this.logger.info("\u4efb\u52a1\u3010{}\u3011,\u5355\u4f4d{}\u4fee\u6539\u5185\u90e8\u8868\u6570\u636e\u65f6\u56e0\u4e3a\u6ca1\u6709\u8bbe\u7f6e\u62a5\u8868\u5e01\u79cd\uff0c\u4e0d\u5b9e\u65f6\u6298\u7b97\uff01", (Object)taskTitle, (Object)unitCode);
            return Collections.emptyList();
        }
        ArrayList<String> reportCurrencyCodeList = new ArrayList<String>();
        for (String currencyCode : reportCurrencyCodeArray = reportCurrencyIdListStr.split(";")) {
            if (currency.equals(currencyCode)) continue;
            FormSchemeDefine formSchemeDefine = this.iFuncExecuteService.queryFormScheme(taskKey, period);
            ConversionSystemTaskEO conversionSystemTask = this.taskSchemeDao.queryByTaskAndScheme(taskKey, formSchemeDefine.getKey());
            CommonRateInfoVO rateInfoVO = this.commonRateService.queryRateInfo(conversionSystemTask.getRateSchemeCode(), period, currency, currencyCode);
            if (rateInfoVO == null) {
                this.logger.info("\u4efb\u52a1\u3010{}\u3011,\u5355\u4f4d\uff1a{}\u8df3\u8fc7\u5b9e\u65f6\u6298\u7b97,\u539f\u56e0\uff1a\u6298\u7b97\u4f53\u7cfb\u672a\u914d\u7f6e\u6298\u7b97\u6c47\u7387\uff0c\u65f6\u671f{}\uff0c\u5e01\u79cd[{}-{}]", taskTitle, unitCode, period, currency, currencyCode);
                continue;
            }
            reportCurrencyCodeList.add(currencyCode);
        }
        return reportCurrencyCodeList;
    }

    @Override
    protected DoubleKeyMap<String, String, List<GcConversionIndexRateInfo>> getConversionIndexRateInfoMap(DataEntryContext dataEntryContext, ConversionSystemTaskEO conversionSystemTask, List<ConversionSystemItemEO> systemItems, List<String> conversionCurrencyList) {
        String currency = dataEntryContext.getDimensionSet().getOrDefault("MD_CURRENCY", new DimensionValue()).getValue();
        DoubleKeyMap currToConversionCurrToRateMap = new DoubleKeyMap();
        for (String conversion : conversionCurrencyList) {
            currToConversionCurrToRateMap.put((Object)currency, (Object)conversion, this.getRateInfoListByCurrency(dataEntryContext, conversionSystemTask, systemItems, currency, conversion));
        }
        return currToConversionCurrToRateMap;
    }
}

