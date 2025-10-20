/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.DoubleKeyMap
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo
 *  com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv
 *  com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateService
 *  com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemItemDao
 *  com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemTaskDao
 *  com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO
 *  com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO
 *  com.jiuqi.gcreport.conversion.utils.GcConversionUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum
 *  com.jiuqi.gcreport.rate.impl.service.CommonRateService
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo
 *  com.jiuqi.nr.dataentry.service.IBatchCalculateService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.conversion.realtime;

import com.google.common.collect.Maps;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.DoubleKeyMap;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo;
import com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv;
import com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateService;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemItemDao;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemTaskDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO;
import com.jiuqi.gcreport.conversion.utils.GcConversionUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO;
import com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.conversion.realtime.IConversionRealTimeExecutor;
import com.jiuqi.gcreport.inputdata.conversion.realtime.RealTimeConversionContextDTO;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataDao;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataSrcTypeEnum;
import com.jiuqi.gcreport.inputdata.inputdata.service.TemplateEntDaoCacheService;
import com.jiuqi.gcreport.inputdata.util.InputDataConver;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum;
import com.jiuqi.gcreport.rate.impl.service.CommonRateService;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractConversionInputDataRealTime
implements IConversionRealTimeExecutor {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String DELETE_SQL_TEMPLATE = "DELETE FROM %1$s WHERE CONVERTGROUPID IN (SELECT CONVERTGROUPID FROM ( SELECT CONVERTGROUPID FROM %1$s WHERE %2$s ) t ) AND CONVERTGROUPID IS NOT NULL  %3$s ";
    @Autowired
    protected InputDataDao inputDataDao;
    @Autowired
    protected InputDataNameProvider inputDataNameProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ConversionSystemTaskDao taskSchemeDao;
    @Autowired
    private ConversionSystemItemDao itemDao;
    @Autowired
    private CommonRateService commonRateService;
    @Autowired
    private IBatchCalculateService batchCalculateService;
    @Autowired
    private FormulaSchemeConfigService formulaSchemeConfigService;
    @Autowired
    protected TemplateEntDaoCacheService templateEntDaoCacheService;
    @Autowired
    private ConversionRateService conversionRateService;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteHistoryData(String taskId, Map<String, String> dimFieldAndValueMapping, List<String> inputItemIds, boolean isCalculate) {
        HashMap<String, String> dimMappingCopy = new HashMap<String, String>(16);
        dimMappingCopy.putAll(dimFieldAndValueMapping);
        List<String> conversionCurrencyList = this.deleteHistoryDataAndGetConversionCurrency(taskId, dimMappingCopy, inputItemIds);
        conversionCurrencyList = conversionCurrencyList.stream().distinct().collect(Collectors.toList());
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        InputDataEO queryEo = new InputDataEO();
        queryEo.setId(inputItemIds.get(0));
        InputDataEO inputData = (InputDataEO)this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class).selectByEntity((BaseEntity)queryEo);
        if (inputData == null) {
            return;
        }
        this.logger.info("\u7528\u6237\u3010{}\u3011\u5728\u3010{}\u3011\u5bf9\u3010{}\u3011\u8868\u505a\u4e86\u5220\u9664\u64cd\u4f5c\uff0c\u540c\u6b65\u5220\u9664\u6298\u7b97\u6570\u636e\u5e01\u79cd\u3010{}\u3011\u5bf9\u5e94\u6570\u636e", inputData.getCreateUser(), DateUtils.nowTimeStr(), this.getFormCodeAndTitle(inputData.getFormId()), CollectionUtils.toString(conversionCurrencyList));
        if (isCalculate) {
            String currency = dimFieldAndValueMapping.get("MD_CURRENCY");
            String schemeKey = null;
            try {
                schemeKey = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(inputData.getPeriod(), taskId).getSchemeKey();
            }
            catch (Exception e) {
                this.logger.error("\u6839\u636e\u4efb\u52a1\u548c\u65f6\u671f\u4e3a\u627e\u5230\u62a5\u8868\u65b9\u6848", e);
            }
            conversionCurrencyList.remove(currency);
            for (String conversionCurrency : conversionCurrencyList) {
                dimMappingCopy.put("MD_CURRENCY", conversionCurrency);
                this.batchCalculate(inputData.getTaskId(), schemeKey, inputData.getFormId(), dimMappingCopy);
            }
            this.logger.info("\u7528\u6237\u3010{}\u3011\u5728\u3010{}\u3011\u5bf9\u3010{}\u3011\u8868\u505a\u4e86\u5220\u9664\u64cd\u4f5c\uff0c\u5bf9\u6298\u7b97\u6570\u636e\u5e01\u79cd\u3010{}\u3011\u6267\u884c\u8fd0\u7b97", inputData.getCreateUser(), DateUtils.nowTimeStr(), this.getFormCodeAndTitle(inputData.getFormId()), CollectionUtils.toString(conversionCurrencyList));
        }
    }

    protected String getFormCodeAndTitle(String formId) {
        try {
            FormDefine formDefine = this.runTimeAuthViewController.queryEntityForm(formId);
            if (formDefine == null) {
                return formId;
            }
            return formDefine.getFormCode() + "|" + formDefine.getTitle();
        }
        catch (Exception e) {
            this.logger.error("\u5b9e\u65f6\u6298\u7b97\u6839\u636e\u8868\u5355id[" + formId + "]\u83b7\u53d6\u8868\u5355\u4fe1\u606f\u5931\u8d25", e);
            return formId;
        }
    }

    protected void conversionCalculate(DataEntryContext dataEntryContext, List<String> conversionCurrencyList) {
        Map<String, String> dimFieldValueMap = InputDataConver.getDimFieldValueMap(dataEntryContext.getDimensionSet(), dataEntryContext.getTaskKey());
        String currency = dataEntryContext.getDimensionSet().getOrDefault("MD_CURRENCY", new DimensionValue()).getValue();
        conversionCurrencyList = conversionCurrencyList.stream().distinct().collect(Collectors.toList());
        conversionCurrencyList.remove(currency);
        for (String conversionCurrency : conversionCurrencyList) {
            dimFieldValueMap.put("MD_CURRENCY", conversionCurrency);
            this.batchCalculate(dataEntryContext.getTaskKey(), dataEntryContext.getFormSchemeKey(), dataEntryContext.getFormKey(), dimFieldValueMap);
        }
    }

    protected void executeDeleteHistorySql(String taskId, List<String> inputItemIds, List<String> conversionCurrencyList) {
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        String idInSql = SqlBuildUtil.getStrInCondi((String)"ID", inputItemIds);
        String deleteWhereSql = " AND " + SqlBuildUtil.getStrInCondi((String)"MD_CURRENCY", conversionCurrencyList) + "AND" + SqlBuildUtil.getStrNotInCondi((String)"ID", inputItemIds);
        String deleteSql = String.format(DELETE_SQL_TEMPLATE, tableName, idInSql, deleteWhereSql);
        this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class).execute(deleteSql);
    }

    protected RealTimeConversionContextDTO initContext(DataEntryContext dataEntryContext, List<String> conversionCurrencyList) {
        RealTimeConversionContextDTO realTimeConversionContextDTO = new RealTimeConversionContextDTO();
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(dataEntryContext.getTaskKey());
        String period = dataEntryContext.getDimensionSet().getOrDefault("DATATIME", new DimensionValue()).getValue();
        TableModelDefine tableModelDefineByCode = this.dataModelService.getTableModelDefineByName(tableName);
        ConversionSystemTaskEO conversionSystemTask = this.taskSchemeDao.queryByTaskAndScheme(dataEntryContext.getTaskKey(), dataEntryContext.getFormSchemeKey());
        if (conversionSystemTask == null) {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(dataEntryContext.getTaskKey());
            String errorTitle = "\u4fdd\u5b58\u540e\u5b9e\u65f6\u6298\u7b97\u5931\u8d25\uff1a\u4efb\u52a1\uff1a" + taskDefine.getTitle() + "\uff0c\u65f6\u671f\uff1a" + period;
            String errorMsg = " \u83b7\u53d6\u6298\u7b97\u4f53\u7cfb\u5931\u8d25\uff0c\u8df3\u8fc7\u5b9e\u65f6\u6298\u7b97\uff01";
            this.logger.error(errorTitle + errorMsg);
            LogHelper.error((String)"\u5408\u5e76-\u6570\u636e\u5f55\u5165", (String)errorTitle, (String)errorMsg);
            return null;
        }
        TableModelRunInfo tableInfo = this.getDefinitionsCache().getDataModelDefinitionsCache().getTableInfo(tableName);
        List<ConversionSystemItemEO> systemItems = this.getSystemItems(conversionSystemTask, dataEntryContext.getFormKey(), tableInfo);
        DoubleKeyMap<String, String, List<GcConversionIndexRateInfo>> currToConversionCurrToRateMap = this.getConversionIndexRateInfoMap(dataEntryContext, conversionSystemTask, systemItems, conversionCurrencyList);
        if (currToConversionCurrToRateMap.isEmpty()) {
            return realTimeConversionContextDTO;
        }
        Map<String, String> filedDefineKeyToColumnCode = this.getConversionFieldDefines(currToConversionCurrToRateMap, tableInfo);
        realTimeConversionContextDTO.setCurrToConversionCurrToRateMap(currToConversionCurrToRateMap);
        realTimeConversionContextDTO.setFiledDefineKeyToColumnCodeMap(filedDefineKeyToColumnCode);
        realTimeConversionContextDTO.setTableName(tableName);
        realTimeConversionContextDTO.setDataEntryContext(dataEntryContext);
        realTimeConversionContextDTO.setTableDefine(tableModelDefineByCode);
        realTimeConversionContextDTO.setRateSchemeCode(conversionSystemTask.getRateSchemeCode());
        realTimeConversionContextDTO.setPeriodStr(period);
        realTimeConversionContextDTO.setTableModelRunInfo(tableInfo);
        return realTimeConversionContextDTO;
    }

    protected Map<String, List<InputDataEO>> notConversionGetNeedOffsetData(GcOrgCenterService instance, List<InputDataEO> inputItems, String currency) {
        HashMap<String, List<InputDataEO>> resultMap = new HashMap<String, List<InputDataEO>>(2);
        resultMap.put(currency, new ArrayList());
        for (InputDataEO inputData : inputItems) {
            if (!currency.equals(this.getCommonSuperiorBaseCurrency(instance, inputData))) continue;
            ((List)resultMap.get(currency)).add(inputData);
        }
        return resultMap;
    }

    protected InputDataEO conversionInputData(RealTimeConversionContextDTO realTimeConversionContext, InputDataEO inputData, String srcCurrency, String conversionCurrency) {
        InputDataEO conversionInputData = new InputDataEO();
        HashMap conversionFieldMap = Maps.newHashMapWithExpectedSize((int)inputData.getFields().size());
        conversionFieldMap.putAll(inputData.getFields());
        this.updateNotConversionField(realTimeConversionContext, inputData, conversionInputData, conversionCurrency);
        GcConversionOrgAndFormContextEnv env = this.buildConversionOrgAndFormContextEnv(realTimeConversionContext, srcCurrency, conversionCurrency);
        IDataRow dataRow = this.createDataRow(conversionInputData, realTimeConversionContext, conversionCurrency);
        Map<String, String> filedDefineKeyToColumnCodeMap = realTimeConversionContext.getFiledDefineKeyToColumnCodeMap();
        if (filedDefineKeyToColumnCodeMap == null || filedDefineKeyToColumnCodeMap.size() == 0) {
            return conversionInputData;
        }
        Map<String, GcConversionIndexRateInfo> fieldKeyToRateMap = this.getFieldKeyToRateMap(realTimeConversionContext, srcCurrency, conversionCurrency);
        for (String fieldDefineKey : filedDefineKeyToColumnCodeMap.keySet()) {
            GcConversionIndexRateInfo gcConversionIndexRateInfo = fieldKeyToRateMap.get(fieldDefineKey);
            if (gcConversionIndexRateInfo == null) continue;
            if (gcConversionIndexRateInfo.getRateTypeCode().equals(RateTypeEnum.COPY.getCode())) {
                Object beforeValue = inputData.getFieldValue(filedDefineKeyToColumnCodeMap.get(fieldDefineKey).toUpperCase());
                conversionInputData.addFieldValue(filedDefineKeyToColumnCodeMap.get(fieldDefineKey), beforeValue);
                continue;
            }
            BigDecimal zbRateValue = GcConversionUtils.getConversionZbRateValue((IDataRow)dataRow, (GcConversionOrgAndFormContextEnv)env, (GcConversionIndexRateInfo)gcConversionIndexRateInfo);
            BigDecimal beforeValue = ConverterUtils.getAsBigDecimal((Object)inputData.getFieldValue(filedDefineKeyToColumnCodeMap.get(fieldDefineKey).toUpperCase()));
            BigDecimal afterValue = beforeValue == null ? BigDecimal.ZERO : beforeValue.multiply(zbRateValue);
            conversionInputData.addFieldValue(filedDefineKeyToColumnCodeMap.get(fieldDefineKey), afterValue);
        }
        return conversionInputData;
    }

    protected void batchCalculate(String taskId, String schemeId, String formKey, Map<String, String> dimMap) {
        String unitCode = dimMap.get("MDCODE");
        String currency = dimMap.get("MD_CURRENCY");
        String period = dimMap.get("DATATIME");
        String orgType = dimMap.get("MD_GCORGTYPE");
        String adjustCode = dimMap.get("ADJUST");
        FormulaSchemeConfigDTO formulaScheme = this.formulaSchemeConfigService.getSchemeConfigByOrgAndAssistDim(schemeId, unitCode, dimMap);
        if (formulaScheme == null) {
            this.logger.info("\u5185\u90e8\u8868\u5b9e\u65f6\u6298\u7b97\uff0c\u6298\u7b97\u540e\u8fd0\u7b97\u6ca1\u6709\u516c\u5f0f\u65b9\u6848\uff0c\u8df3\u8fc7\u8fd0\u7b97\uff01");
            return;
        }
        if (CollectionUtils.isEmpty((Collection)formulaScheme.getConvertAfterSchemeId())) {
            this.logger.info("\u5185\u90e8\u8868\u5b9e\u65f6\u6298\u7b97\uff0c\u6298\u7b97\u540e\u8fd0\u7b97\u6ca1\u6709\u914d\u7f6e\u6298\u7b97\u540e\u516c\u5f0f\uff0c\u8df3\u8fc7\u8fd0\u7b97\uff01");
            return;
        }
        Map dimensionValueMap = DimensionUtils.buildDimensionMap((String)taskId, (String)currency, (String)period, (String)orgType, (String)unitCode, (String)adjustCode);
        BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
        batchCalculateInfo.setTaskKey(taskId);
        batchCalculateInfo.setDimensionSet(dimensionValueMap);
        HashMap<String, Object> formulas = new HashMap<String, Object>(2);
        formulas.put(formKey, null);
        batchCalculateInfo.setFormulas(formulas);
        batchCalculateInfo.setFormSchemeKey(schemeId);
        for (String formulaSchemeId : formulaScheme.getConvertAfterSchemeId()) {
            batchCalculateInfo.setFormulaSchemeKey(formulaSchemeId);
            this.batchCalculateService.batchCalculateForm(batchCalculateInfo);
        }
    }

    private IDataRow createDataRow(InputDataEO inputData, RealTimeConversionContextDTO realTimeConversionContext, String conversionCurrency) {
        IDataRow iDataRow;
        IDataTable dataTable;
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setUseDnaSql(false);
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(realTimeConversionContext.getDataEntryContext().getFormSchemeKey());
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
        DimensionValueSet masterKey = this.buildDimensionValueSet(realTimeConversionContext, conversionCurrency);
        dataQuery.setMasterKeys(this.buildDimensionValueSet(realTimeConversionContext, conversionCurrency));
        TableModelRunInfo tableModelRunInfo = realTimeConversionContext.getTableModelRunInfo();
        for (FieldDefine fieldDefine : tableModelRunInfo.getColumnFieldMap().values()) {
            dataQuery.addColumn(fieldDefine);
        }
        try {
            dataTable = dataQuery.executeQuery(context);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.queryinputdatas.exceptionmsg"), (Throwable)e);
        }
        masterKey.setValue("RECORDKEY", (Object)inputData.getId());
        masterKey.setValue("VERSIONID", (Object)"00000000-0000-0000-0000-000000000000");
        try {
            iDataRow = dataTable.appendRow(masterKey);
            for (FieldDefine fieldDefine : tableModelRunInfo.getColumnFieldMap().values()) {
                iDataRow.setValue(fieldDefine, inputData.getFieldValue(fieldDefine.getCode()));
            }
        }
        catch (IncorrectQueryException e) {
            throw new RuntimeException(e);
        }
        return iDataRow;
    }

    private DimensionValueSet buildDimensionValueSet(RealTimeConversionContextDTO realTimeConversionContext, String conversionCurrency) {
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)realTimeConversionContext.getDataEntryContext().getDimensionSet());
        dimensionValueSet.setValue("MD_CURRENCY", (Object)conversionCurrency);
        return dimensionValueSet;
    }

    private GcConversionOrgAndFormContextEnv buildConversionOrgAndFormContextEnv(RealTimeConversionContextDTO realTimeConversionContext, String srcCurrency, String conversionCurrency) {
        GcConversionOrgAndFormContextEnv env = new GcConversionOrgAndFormContextEnv();
        env.setDimensionSet(realTimeConversionContext.getDataEntryContext().getDimensionSet());
        env.setTableDefine(realTimeConversionContext.getTableDefine());
        env.setSchemeId(realTimeConversionContext.getDataEntryContext().getFormSchemeKey());
        env.setRateSchemeCode(realTimeConversionContext.getRateSchemeCode());
        env.setBeforeCurrencyCode(srcCurrency);
        env.setAfterCurrencyCode(conversionCurrency);
        env.setPeriodStr(realTimeConversionContext.getPeriodStr());
        return env;
    }

    private void updateNotConversionField(RealTimeConversionContextDTO realTimeConversionContext, InputDataEO srcInputData, InputDataEO conversionInputData, String conversionCurrency) {
        TableModelRunInfo tableInfo = realTimeConversionContext.getTableModelRunInfo();
        Set dimFeildNames = tableInfo.getDimFields().stream().map(ColumnModelDefine::getName).collect(Collectors.toSet());
        dimFeildNames.stream().forEach(dimFeildName -> conversionInputData.addFieldValue((String)dimFeildName, srcInputData.getFieldValue((String)dimFeildName)));
        conversionInputData.addFieldValue("RECORDKEY", conversionInputData.getId());
        conversionInputData.addFieldValue("VERSIONID", "00000000-0000-0000-0000-000000000000");
        conversionInputData.setId(UUIDUtils.newUUIDStr());
        conversionInputData.setBizkeyOrder(conversionInputData.getId());
        conversionInputData.setMdOrg(srcInputData.getMdOrg());
        conversionInputData.setOrgCode(srcInputData.getOrgCode());
        conversionInputData.setOppUnitId(srcInputData.getOppUnitId());
        conversionInputData.setFloatOrder(srcInputData.getFloatOrder());
        conversionInputData.setSubjectCode(srcInputData.getSubjectCode());
        conversionInputData.setSubjectObjCode(srcInputData.getSubjectObjCode());
        conversionInputData.setCurrency(conversionCurrency);
        conversionInputData.setConvertSrcId(srcInputData.getConvertSrcId());
        conversionInputData.setSrcType(InputDataSrcTypeEnum.CONVERSION.getValue());
        conversionInputData.setTaskId(srcInputData.getTaskId());
        conversionInputData.setConvertGroupId(srcInputData.getConvertGroupId());
        conversionInputData.setFormId(srcInputData.getFormId());
        conversionInputData.setReportSystemId(srcInputData.getReportSystemId());
        conversionInputData.setCreateUser(srcInputData.getCreateUser());
        conversionInputData.setOffsetState(srcInputData.getOffsetState());
        conversionInputData.setCreateTime(new Date());
        conversionInputData.setUpdateTime(new Date());
        conversionInputData.setRecordTimestamp(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        conversionInputData.setDc(srcInputData.getDc());
    }

    private Map<String, GcConversionIndexRateInfo> getFieldKeyToRateMap(RealTimeConversionContextDTO realTimeConversionContext, String srcCurrency, String conversionCurrency) {
        DoubleKeyMap<String, String, List<GcConversionIndexRateInfo>> currToRateMap = realTimeConversionContext.getCurrToConversionCurrToRateMap();
        if (currToRateMap == null || currToRateMap.isEmpty()) {
            return Collections.emptyMap();
        }
        List gcConversionIndexRateInfos = (List)currToRateMap.get((Object)srcCurrency, (Object)conversionCurrency);
        if (CollectionUtils.isEmpty((Collection)gcConversionIndexRateInfos)) {
            return Collections.emptyMap();
        }
        return gcConversionIndexRateInfos.stream().collect(Collectors.toMap(GcConversionIndexRateInfo::getIndexId, Function.identity(), (o1, o2) -> o1));
    }

    protected String getCommonSuperiorBaseCurrency(GcOrgCenterService instance, InputDataEO inputData) {
        String unitId = inputData.getUnitId();
        String oppUnitId = inputData.getOppUnitId();
        GcOrgCacheVO commonUnit = instance.getCommonUnit(instance.getOrgByCode(unitId), instance.getOrgByCode(oppUnitId));
        if (null == commonUnit) {
            return "";
        }
        return String.valueOf(commonUnit.getTypeFieldValue("CURRENCYID"));
    }

    protected void addRealTimeOffsetData(String currency, Map<String, List<InputDataEO>> currencyToInputDataListMap, InputDataEO inputData, String commonSuperiorBaseCurrency) {
        if (!commonSuperiorBaseCurrency.equals(currency)) {
            return;
        }
        if (!currencyToInputDataListMap.containsKey(currency)) {
            currencyToInputDataListMap.put(currency, new ArrayList());
        }
        currencyToInputDataListMap.get(currency).add(inputData);
    }

    private Map<String, String> getConversionFieldDefines(DoubleKeyMap<String, String, List<GcConversionIndexRateInfo>> currToConversionCurrToRateMap, TableModelRunInfo tableInfo) {
        HashMap<String, String> filedDefineKeyToColumnCode = new HashMap<String, String>(32);
        tableInfo.getColumnFieldMap().forEach((columnModelDefine, fieldDefine) -> {
            for (List indexRateInfos : currToConversionCurrToRateMap.values()) {
                Optional<GcConversionIndexRateInfo> matchItem;
                GcConversionIndexRateInfo indexRateInfo;
                if (CollectionUtils.isEmpty((Collection)indexRateInfos) || (indexRateInfo = (GcConversionIndexRateInfo)(matchItem = indexRateInfos.stream().filter(item -> fieldDefine.getKey().equals(item.getIndexId())).findFirst()).orElse(null)) == null || RateTypeEnum.NOTCONV.getCode().equals(indexRateInfo.getRateTypeCode())) continue;
                filedDefineKeyToColumnCode.put(fieldDefine.getKey(), columnModelDefine.getName());
            }
        });
        return filedDefineKeyToColumnCode;
    }

    protected abstract List<String> deleteHistoryDataAndGetConversionCurrency(String var1, Map<String, String> var2, List<String> var3);

    protected abstract DoubleKeyMap<String, String, List<GcConversionIndexRateInfo>> getConversionIndexRateInfoMap(DataEntryContext var1, ConversionSystemTaskEO var2, List<ConversionSystemItemEO> var3, List<String> var4);

    protected List<GcConversionIndexRateInfo> getRateInfoListByCurrency(DataEntryContext dataEntryContext, ConversionSystemTaskEO conversionSystemTask, List<ConversionSystemItemEO> systemItems, String beforeCurrency, String afterCurrency) {
        String periodStr = dataEntryContext.getDimensionSet().getOrDefault("DATATIME", new DimensionValue()).getValue();
        Map rateValueMap = this.conversionRateService.getRateInfos(conversionSystemTask.getRateSchemeCode(), dataEntryContext.getFormSchemeKey(), beforeCurrency, afterCurrency, periodStr);
        if (MapUtils.isEmpty((Map)rateValueMap)) {
            return Collections.emptyList();
        }
        ArrayList<GcConversionIndexRateInfo> indexRateInfos = new ArrayList<GcConversionIndexRateInfo>();
        if (!CollectionUtils.isEmpty(systemItems)) {
            systemItems.forEach(systemItem -> {
                GcConversionIndexRateInfo indexRateInfo = new GcConversionIndexRateInfo();
                indexRateInfo.setRateSchemeCode(conversionSystemTask.getRateSchemeCode());
                indexRateInfo.setTaskId(conversionSystemTask.getTaskId());
                indexRateInfo.setSchemeId(conversionSystemTask.getSchemeId());
                indexRateInfo.setFormId(systemItem.getFormId());
                indexRateInfo.setIndexId(systemItem.getIndexId());
                indexRateInfo.setRateTypeCode(systemItem.getRateTypeCode());
                if (rateValueMap != null && rateValueMap.size() > 0) {
                    int lastIndex = indexRateInfo.getRateTypeCode().lastIndexOf(95);
                    String rateTypeCode = lastIndex != -1 ? indexRateInfo.getRateTypeCode().substring(0, lastIndex) : indexRateInfo.getRateTypeCode();
                    indexRateInfo.setRateValue((BigDecimal)rateValueMap.get(rateTypeCode));
                }
                indexRateInfo.setRateFormula(systemItem.getRateFormula());
                indexRateInfo.setSourceCurrecyCode(beforeCurrency);
                indexRateInfo.setTargeCurrencyCode(afterCurrency);
                indexRateInfos.add(indexRateInfo);
            });
        }
        return indexRateInfos;
    }

    private List<ConversionSystemItemEO> getSystemItems(ConversionSystemTaskEO conversionSystemTask, String formKey, TableModelRunInfo tableModelRunInfo) {
        Set tableFieldKeys = tableModelRunInfo.getColumnFieldMap().values().stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        return this.itemDao.batchGetSystemItemsByFormIdAndIndexIds(formKey, tableFieldKeys);
    }

    private DefinitionsCache getDefinitionsCache() {
        DefinitionsCache definitionsCache;
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        try {
            definitionsCache = new DefinitionsCache(context);
        }
        catch (ParseException e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        return definitionsCache;
    }
}

