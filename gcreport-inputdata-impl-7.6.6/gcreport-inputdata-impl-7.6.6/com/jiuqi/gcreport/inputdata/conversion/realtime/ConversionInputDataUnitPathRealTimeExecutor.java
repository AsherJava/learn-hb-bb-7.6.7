/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.DoubleKeyMap
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo
 *  com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv
 *  com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO
 *  com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.conversion.realtime;

import com.google.common.collect.Maps;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.DoubleKeyMap;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo;
import com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.inputdata.conversion.realtime.AbstractConversionInputDataRealTime;
import com.jiuqi.gcreport.inputdata.conversion.realtime.RealTimeConversionContextDTO;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.ReportOffsetStateEnum;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ConversionInputDataUnitPathRealTimeExecutor
extends AbstractConversionInputDataRealTime {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String getExecutorName() {
        return "UNIT_PATH";
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Map<String, List<InputDataEO>> realTimeConversion(DataEntryContext dataEntryContext, List<InputDataEO> updateInputItems, List<InputDataEO> inputItems) {
        ArrayList<String> afterCurrencyList;
        ArrayList<String> beforeCurrencyList;
        String currency = dataEntryContext.getDimensionSet().getOrDefault("MD_CURRENCY", new DimensionValue()).getValue();
        String period = dataEntryContext.getDimensionSet().getOrDefault("DATATIME", new DimensionValue()).getValue();
        String unitCode = dataEntryContext.getDimensionSet().getOrDefault("MD_ORG", new DimensionValue()).getValue();
        String orgType = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)dataEntryContext.getTaskKey());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, period));
        List<String> currencyPathList = this.getConversionCurrencyList(unitCode, period, orgType);
        if (CollectionUtils.isEmpty(currencyPathList)) {
            return this.notConversionGetNeedOffsetData(instance, inputItems, currency);
        }
        GcOrgCacheVO orgToJsonVO = instance.getOrgByCode(unitCode);
        String baseCurrencyId = String.valueOf(orgToJsonVO.getTypeFieldValue("CURRENCYID"));
        if (!this.isCurrencyInList(currency, baseCurrencyId, currencyPathList, beforeCurrencyList = new ArrayList<String>(), afterCurrencyList = new ArrayList<String>())) {
            return this.notConversionGetNeedOffsetData(instance, inputItems, currency);
        }
        RealTimeConversionContextDTO realTimeConversionContext = this.initContext(dataEntryContext, afterCurrencyList);
        if (realTimeConversionContext == null) {
            return this.notConversionGetNeedOffsetData(instance, inputItems, currency);
        }
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(dataEntryContext.getTaskKey());
        DoubleKeyMap<String, String, String> convertGroupToCurrencyToOffsetGroup = new DoubleKeyMap<String, String, String>();
        if (!CollectionUtils.isEmpty(updateInputItems)) {
            convertGroupToCurrencyToOffsetGroup = this.getOffsetGroupIdDoubleKeyMap(tableName, beforeCurrencyList, updateInputItems);
        }
        ArrayList<InputDataEO> conversionSaveDataList = new ArrayList<InputDataEO>();
        ArrayList<InputDataEO> needUpdateInputDataList = new ArrayList<InputDataEO>();
        HashMap<String, List<InputDataEO>> currencyToInputDataListMap = new HashMap<String, List<InputDataEO>>(16);
        ArrayList<String> needUpdateOffsetCurrencyList = new ArrayList<String>(afterCurrencyList);
        needUpdateOffsetCurrencyList.add(currency);
        for (InputDataEO inputData : inputItems) {
            String commonSuperiorBaseCurrency = this.getCommonSuperiorBaseCurrency(instance, inputData);
            this.addRealTimeOffsetData(currency, currencyToInputDataListMap, inputData, commonSuperiorBaseCurrency);
            String offsetGroupId = null;
            if (!StringUtils.isEmpty((String)inputData.getConvertGroupId())) {
                offsetGroupId = this.handlerUpdateDataAndGetOffsetGroupId(convertGroupToCurrencyToOffsetGroup, inputData, commonSuperiorBaseCurrency);
            } else {
                inputData.setConvertGroupId(UUIDUtils.newUUIDStr());
                needUpdateInputDataList.add(inputData);
            }
            if (!StringUtils.isEmpty((String)offsetGroupId)) {
                this.updateOffsetState(inputData, offsetGroupId);
                needUpdateInputDataList.add(inputData);
                needUpdateInputDataList.addAll(this.listOppInputData(tableName, offsetGroupId, inputData.getConvertGroupId(), needUpdateOffsetCurrencyList));
            }
            InputDataEO srcInputData = inputData;
            String srcCurrency = currency;
            HashMap currencyToInputDataMap = Maps.newHashMapWithExpectedSize((int)(afterCurrencyList.size() + 1));
            currencyToInputDataMap.put(srcCurrency, srcInputData);
            for (String conversionCurrency : afterCurrencyList) {
                InputDataEO conversionInputData;
                if (currencyToInputDataMap.containsKey(conversionCurrency)) {
                    srcInputData = (InputDataEO)((Object)currencyToInputDataMap.get(conversionCurrency));
                    srcCurrency = conversionCurrency;
                    continue;
                }
                srcInputData = conversionInputData = this.conversionInputData(realTimeConversionContext, srcInputData, srcCurrency, conversionCurrency);
                srcCurrency = conversionCurrency;
                if (!StringUtils.isEmpty((String)offsetGroupId)) {
                    this.updateOffsetState(conversionInputData, offsetGroupId);
                }
                conversionSaveDataList.add(conversionInputData);
                currencyToInputDataMap.put(conversionCurrency, conversionInputData);
                this.addRealTimeOffsetData(conversionCurrency, currencyToInputDataListMap, conversionInputData, commonSuperiorBaseCurrency);
            }
        }
        this.saveData(tableName, conversionSaveDataList, needUpdateInputDataList);
        this.conversionCalculate(dataEntryContext, afterCurrencyList);
        InputDataEO inputData = inputItems.get(0);
        this.logger.info("\u7528\u6237\u3010{}\u3011\u5728\u3010{}\u3011\u5bf9\u3010{}\u3011\u8868\u505a\u4e86\u65b0\u589e\u6216\u66f4\u65b0\u64cd\u4f5c\uff0c\u6309\u6298\u7b97\u8def\u5f84\u3010{}\u3011\u6298\u7b97\u751f\u6210\u5bf9\u5e94\u6570\u636e", inputData.getCreateUser(), DateUtils.nowTimeStr(), this.getFormCodeAndTitle(inputData.getFormId()), CollectionUtils.toString(afterCurrencyList));
        return currencyToInputDataListMap;
    }

    private List<InputDataEO> listOppInputData(String tableName, String offsetGroupId, String convertGroupId, List<String> needUpdateOffsetCurrencyList) {
        String selectFields = SqlUtils.getNewColumnsSqlByTableDefine((String)tableName, (String)"e");
        String sql = "SELECT %1$s FROM %2$s E \nWHERE CONVERTGROUPID IN (SELECT CONVERTGROUPID FROM %2$s WHERE OFFSETGROUPID = ? and CONVERTGROUPID <> ? ) AND " + SqlBuildUtil.getStrInCondi((String)"MD_CURRENCY", needUpdateOffsetCurrencyList);
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        List inputDataEOS = dao.selectEntity(String.format(sql, selectFields, tableName), new Object[]{offsetGroupId, convertGroupId});
        if (CollectionUtils.isEmpty((Collection)inputDataEOS)) {
            return Collections.emptyList();
        }
        for (InputDataEO inputDataEO : inputDataEOS) {
            this.updateOffsetState(inputDataEO, offsetGroupId);
        }
        return inputDataEOS;
    }

    private void saveData(String tableName, List<InputDataEO> conversionSaveDataList, List<InputDataEO> needUpdateInputDataList) {
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        if (!CollectionUtils.isEmpty(needUpdateInputDataList)) {
            dao.updateBatch(needUpdateInputDataList);
        }
        if (conversionSaveDataList.size() > 0) {
            dao.addBatch(conversionSaveDataList);
        }
    }

    private void updateOffsetState(InputDataEO inputData, String offsetGroupId) {
        inputData.setOffsetGroupId(offsetGroupId);
        inputData.setOffsetState(ReportOffsetStateEnum.OFFSET.getValue());
        inputData.setDiffAmt(0.0);
        inputData.setOffsetAmt(inputData.getAmt());
        inputData.setRecordTimestamp(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
    }

    private String handlerUpdateDataAndGetOffsetGroupId(DoubleKeyMap<String, String, String> convertGroupToCurrencyToOffsetGroup, InputDataEO inputData, String commonSuperiorBaseCurrency) {
        if (!convertGroupToCurrencyToOffsetGroup.containsKey((Object)inputData.getConvertGroupId())) {
            return null;
        }
        return (String)convertGroupToCurrencyToOffsetGroup.get((Object)inputData.getConvertGroupId(), (Object)commonSuperiorBaseCurrency);
    }

    private Map<String, Integer> covertCurrencyPathListToMap(List<String> currencyPathList) {
        HashMap currencyToIndexMap = Maps.newHashMapWithExpectedSize((int)currencyPathList.size());
        for (int i = 0; i < currencyPathList.size(); ++i) {
            currencyToIndexMap.put(currencyPathList.get(i), i);
        }
        return currencyToIndexMap;
    }

    private DoubleKeyMap<String, String, String> getOffsetGroupIdDoubleKeyMap(String tableName, List<String> beforeCurrencyList, List<InputDataEO> updateInputItems) {
        List updateIdList = updateInputItems.stream().map(InputDataEO::getId).collect(Collectors.toList());
        StringBuilder selectSqlSb = new StringBuilder();
        selectSqlSb.append(" select CONVERTGROUPID,currencycode,offsetgroupid from %1$s  \n");
        selectSqlSb.append("  where offsetState='1' ");
        selectSqlSb.append("    and convertgroupid in ( select CONVERTGROUPID from %1$s where %2$s ) ");
        selectSqlSb.append("    and convertgroupid is not null ");
        selectSqlSb.append("    %3$s  ");
        String idInSql = SqlBuildUtil.getStrInCondi((String)"ID", updateIdList);
        String selectWhereSql = "";
        if (!CollectionUtils.isEmpty(beforeCurrencyList)) {
            selectWhereSql = " AND " + SqlBuildUtil.getStrInCondi((String)"MD_CURRENCY", beforeCurrencyList);
        }
        selectWhereSql = selectWhereSql + "AND" + SqlBuildUtil.getStrNotInCondi((String)"ID", updateIdList);
        String selectSql = String.format(selectSqlSb.toString(), tableName, idInSql, selectWhereSql);
        DoubleKeyMap result = (DoubleKeyMap)this.jdbcTemplate.query(selectSql, rs -> {
            DoubleKeyMap doubleKeyMap = new DoubleKeyMap();
            while (rs.next()) {
                doubleKeyMap.put((Object)rs.getString(1), (Object)rs.getString(2), (Object)rs.getString(3));
            }
            return doubleKeyMap;
        });
        return result == null ? new DoubleKeyMap() : result;
    }

    @Override
    public List<String> deleteHistoryDataAndGetConversionCurrency(String taskId, Map<String, String> dimFieldAndValueMapping, List<String> inputItemIds) {
        ArrayList<String> afterCurrencyList;
        ArrayList<String> beforeCurrencyList;
        String orgType;
        String unitCode = dimFieldAndValueMapping.get("MDCODE");
        String currency = dimFieldAndValueMapping.get("MD_CURRENCY");
        String period = dimFieldAndValueMapping.get("DATATIME");
        List<String> currencyPathList = this.getConversionCurrencyList(unitCode, period, orgType = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)taskId));
        if (CollectionUtils.isEmpty(currencyPathList)) {
            return currencyPathList;
        }
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, period));
        GcOrgCacheVO orgToJsonVO = instance.getOrgByCode(unitCode);
        String baseCurrencyId = String.valueOf(orgToJsonVO.getTypeFieldValue("CURRENCYID"));
        if (!this.isCurrencyInList(currency, baseCurrencyId, currencyPathList, beforeCurrencyList = new ArrayList<String>(), afterCurrencyList = new ArrayList<String>())) {
            return currencyPathList;
        }
        this.deleteUpdateOffsetStatus(taskId, inputItemIds, currency, instance, currencyPathList, beforeCurrencyList, afterCurrencyList);
        if (afterCurrencyList.size() > 0) {
            this.executeDeleteHistorySql(taskId, inputItemIds, afterCurrencyList);
        }
        return afterCurrencyList;
    }

    private void deleteUpdateOffsetStatus(String taskId, List<String> inputItemIds, String currency, GcOrgCenterService instance, List<String> currencyPathList, List<String> beforeCurrencyList, List<String> afterCurrencyList) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        List<InputDataEO> deleteInputDataList = this.inputDataDao.queryByIds(inputItemIds, tableName);
        if (CollectionUtils.isEmpty(deleteInputDataList)) {
            return;
        }
        DoubleKeyMap<String, String, String> convertGroupToCurrencyToOffsetGroup = this.getOffsetGroupIdDoubleKeyMap(tableName, beforeCurrencyList, deleteInputDataList);
        ArrayList<String> needUpdateOffsetCurrencyList = new ArrayList<String>(afterCurrencyList);
        needUpdateOffsetCurrencyList.add(currency);
        ArrayList<InputDataEO> needUpdateInputDataList = new ArrayList<InputDataEO>();
        Map<String, Integer> currencyToIndexMap = this.covertCurrencyPathListToMap(currencyPathList);
        Integer currencyIndex = currencyToIndexMap.get(currency);
        ArrayList<String> covertGroupIdList = new ArrayList<String>();
        for (InputDataEO inputData : deleteInputDataList) {
            int commonSuperiorIndex;
            String commonSuperiorBaseCurrency = this.getCommonSuperiorBaseCurrency(instance, inputData);
            int n = commonSuperiorIndex = currencyToIndexMap.get(commonSuperiorBaseCurrency) == null ? 0 : currencyToIndexMap.get(commonSuperiorBaseCurrency);
            if (commonSuperiorIndex >= currencyIndex) {
                covertGroupIdList.add(inputData.getConvertGroupId());
                continue;
            }
            String offsetGroupId = this.handlerUpdateDataAndGetOffsetGroupId(convertGroupToCurrencyToOffsetGroup, inputData, commonSuperiorBaseCurrency);
            needUpdateInputDataList.addAll(this.listOppInputData(tableName, offsetGroupId, inputData.getConvertGroupId(), needUpdateOffsetCurrencyList));
        }
        if (covertGroupIdList.size() > 0) {
            this.executeUpdateBeforeCurrencyOffsetState(tableName, covertGroupIdList, beforeCurrencyList);
        }
        if (!CollectionUtils.isEmpty(needUpdateInputDataList)) {
            EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
            dao.updateBatch(needUpdateInputDataList);
        }
    }

    @Override
    public boolean conversionButtonCheck(GcConversionOrgAndFormContextEnv env) {
        List<String> conversionCurrencyList = this.getConversionCurrencyList(env.getOrgId(), env.getPeriodStr(), env.getOrgVersionType());
        if (CollectionUtils.isEmpty(conversionCurrencyList)) {
            throw new BusinessRuntimeException("\u6298\u7b97\u8def\u5f84\u4e3a\u7a7a\uff0c\u4e0d\u5141\u8bb8\u6298\u7b97");
        }
        String baseCurrencyId = this.getBaseCurrencyId(env);
        if (!StringUtils.isEmpty((String)baseCurrencyId)) {
            conversionCurrencyList.add(0, baseCurrencyId);
        }
        Map<String, Integer> currencyToIndexMap = this.covertCurrencyPathListToMap(conversionCurrencyList);
        String beforeCurrencyCode = env.getBeforeCurrencyCode();
        String afterCurrencyCode = env.getAfterCurrencyCode();
        if (!currencyToIndexMap.containsKey(beforeCurrencyCode)) {
            throw new BusinessRuntimeException("\u6298\u7b97\u524d\u5e01\u79cd\u4e0d\u5728\u6298\u7b97\u8def\u5f84\u4e2d\uff0c\u4e0d\u5141\u8bb8\u6298\u7b97");
        }
        if (!currencyToIndexMap.containsKey(afterCurrencyCode)) {
            throw new BusinessRuntimeException("\u6298\u7b97\u540e\u5e01\u79cd\u4e0d\u5728\u6298\u7b97\u8def\u5f84\u4e2d\uff0c\u4e0d\u5141\u8bb8\u6298\u7b97");
        }
        if (currencyToIndexMap.get(afterCurrencyCode) - currencyToIndexMap.get(beforeCurrencyCode) != 1) {
            throw new BusinessRuntimeException("\u6298\u7b97\u8def\u5f84\u9519\u8bef\uff0c\u4e0d\u5141\u8bb8\u6298\u7b97");
        }
        return true;
    }

    @Override
    public List<String> getCancelOffsetCurrencyList(String taskId, Map<String, String> dimFieldAndValueMapping) {
        ArrayList<String> afterCurrencyList;
        ArrayList<String> beforeCurrencyList;
        String orgType;
        String unitCode = dimFieldAndValueMapping.get("MDCODE");
        String currency = dimFieldAndValueMapping.get("MD_CURRENCY");
        String period = dimFieldAndValueMapping.get("DATATIME");
        List<String> currencyPathList = this.getConversionCurrencyList(unitCode, period, orgType = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)taskId));
        if (CollectionUtils.isEmpty(currencyPathList)) {
            return Collections.emptyList();
        }
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, period));
        GcOrgCacheVO orgToJsonVO = instance.getOrgByCode(unitCode);
        String baseCurrencyId = String.valueOf(orgToJsonVO.getTypeFieldValue("CURRENCYID"));
        if (!this.isCurrencyInList(currency, baseCurrencyId, currencyPathList, beforeCurrencyList = new ArrayList<String>(), afterCurrencyList = new ArrayList<String>())) {
            return Collections.emptyList();
        }
        afterCurrencyList.add(0, currency);
        return afterCurrencyList;
    }

    private String getBaseCurrencyId(GcConversionOrgAndFormContextEnv env) {
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)env.getOrgVersionType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, env.getPeriodStr()));
        GcOrgCacheVO orgToJsonVO = instance.getOrgByCode(env.getOrgId());
        String baseCurrencyId = String.valueOf(orgToJsonVO.getTypeFieldValue("CURRENCYID"));
        return baseCurrencyId;
    }

    private void executeUpdateBeforeCurrencyOffsetState(String tableName, List<String> covertGroupIdList, List<String> beforeCurrencyList) {
        StringBuilder updateSqlSb = new StringBuilder();
        updateSqlSb.append(" update %1$s i \n");
        updateSqlSb.append("    set offsetgroupId=null,offsetState='0', \n");
        updateSqlSb.append("        diffamt=0,offsetamt=0,recordTimestamp=? \n");
        updateSqlSb.append("  where %2$s   ");
        String updateWhereSql = SqlBuildUtil.getStrInCondi((String)"CONVERTGROUPID", covertGroupIdList);
        if (!CollectionUtils.isEmpty(beforeCurrencyList)) {
            updateWhereSql = updateWhereSql + " AND " + SqlBuildUtil.getStrInCondi((String)"MD_CURRENCY", beforeCurrencyList);
        }
        String updateSql = String.format(updateSqlSb.toString(), tableName, updateWhereSql);
        this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class).execute(updateSql, new Object[]{LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli()});
    }

    private boolean isCurrencyInList(String currency, String baseCurrencyId, List<String> currencyPathList, List<String> beforCurrencyList, List<String> afterCurrencyList) {
        boolean currencyInList = false;
        boolean endWithBaseCurrency = this.isEndWithBaseCurrency(currency, baseCurrencyId, currencyPathList);
        for (String currencyCode : currencyPathList) {
            if (currencyInList && endWithBaseCurrency && currencyCode.equals(baseCurrencyId)) {
                return currencyInList;
            }
            if (currencyInList) {
                afterCurrencyList.add(currencyCode);
                continue;
            }
            if (currency.equals(currencyCode)) {
                currencyInList = true;
                continue;
            }
            beforCurrencyList.add(currencyCode);
        }
        return currencyInList;
    }

    private boolean isEndWithBaseCurrency(String currency, String baseCurrencyId, List<String> currencyPathList) {
        if (currency.equals(baseCurrencyId)) {
            return false;
        }
        return currencyPathList.get(0).equals(baseCurrencyId);
    }

    private List<String> getConversionCurrencyList(String unitCode, String period, String orgType) {
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, period));
        GcOrgCacheVO orgToJsonVO = instance.getOrgByCode(unitCode);
        if (orgToJsonVO == null) {
            this.logger.info("\u5355\u4f4d\u3010{}\u3011\u4fee\u6539\u5185\u90e8\u8868\u6570\u636e\u65f6\u56e0\u4e3a\u6ca1\u80fd\u5728\u5355\u4f4d\u7c7b\u578b\u3010{}\u3011\u65f6\u671f\u3010{}\u3011\u4e2d\u67e5\u627e\u5230\u5f53\u524d\u5355\u4f4d\uff0c\u4e0d\u5b9e\u65f6\u6298\u7b97\uff01", unitCode, orgType, period);
            return Collections.emptyList();
        }
        ArrayList<String> currencyPathList = new ArrayList<String>();
        Object[] parents = orgToJsonVO.getParents();
        if (CollectionUtils.isEmpty((Object[])parents)) {
            return currencyPathList;
        }
        String baseCurrencyId = String.valueOf(orgToJsonVO.getTypeFieldValue("CURRENCYID"));
        for (int i = parents.length - 2; i >= 0; --i) {
            String parentBaseCurrencyId;
            Object parentCode = parents[i];
            GcOrgCacheVO orgByCode = instance.getOrgByCode((String)parentCode);
            if (orgByCode == null || StringUtils.isEmpty((String)(parentBaseCurrencyId = String.valueOf(orgByCode.getTypeFieldValue("CURRENCYID"))))) continue;
            currencyPathList.add(parentBaseCurrencyId);
        }
        if (currencyPathList.contains(baseCurrencyId)) {
            currencyPathList.add(0, baseCurrencyId);
        }
        return currencyPathList;
    }

    @Override
    protected DoubleKeyMap<String, String, List<GcConversionIndexRateInfo>> getConversionIndexRateInfoMap(DataEntryContext dataEntryContext, ConversionSystemTaskEO conversionSystemTask, List<ConversionSystemItemEO> systemItems, List<String> conversionCurrencyList) {
        String currency = dataEntryContext.getDimensionSet().getOrDefault("MD_CURRENCY", new DimensionValue()).getValue();
        DoubleKeyMap currToConversionCurrToRateMap = new DoubleKeyMap();
        for (String conversion : conversionCurrencyList) {
            currToConversionCurrToRateMap.put((Object)currency, (Object)conversion, this.getRateInfoListByCurrency(dataEntryContext, conversionSystemTask, systemItems, currency, conversion));
            currency = conversion;
        }
        return currToConversionCurrToRateMap;
    }
}

