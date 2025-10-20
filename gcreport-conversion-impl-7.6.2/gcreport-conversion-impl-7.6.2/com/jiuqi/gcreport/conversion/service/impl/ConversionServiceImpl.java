/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.rate.client.vo.CommonRateSchemeVO
 *  com.jiuqi.common.web.util.BusinessLogUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.conversion.api.dto.ConversionInitEnvDTO
 *  com.jiuqi.gcreport.conversion.common.GcConversionContextEnv
 *  com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv
 *  com.jiuqi.gcreport.conversion.common.GcConversionResult
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.intern.JtableContextConvertUtils
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.rate.impl.service.CommonRateSchemeService
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodLanguage
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.dataentry.bean.FormLockParam
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.dataentry.readwrite.bean.FormLockBatchReadWriteResult
 *  com.jiuqi.nr.dataentry.service.IBatchCalculateService
 *  com.jiuqi.nr.dataentry.service.IFormLockService
 *  com.jiuqi.nr.dataentry.util.DataEntryUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.conversion.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.rate.client.vo.CommonRateSchemeVO;
import com.jiuqi.common.web.util.BusinessLogUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.conversion.api.dto.ConversionInitEnvDTO;
import com.jiuqi.gcreport.conversion.common.GcConversionContextEnv;
import com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv;
import com.jiuqi.gcreport.conversion.common.GcConversionResult;
import com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateService;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionLogInfoEo;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO;
import com.jiuqi.gcreport.conversion.conversionsystem.service.ConversionLogInfoService;
import com.jiuqi.gcreport.conversion.executor.ConversionFormExecutorDispatcher;
import com.jiuqi.gcreport.conversion.service.ConversionService;
import com.jiuqi.gcreport.conversion.utils.GcConversionUtils;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO;
import com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.intern.JtableContextConvertUtils;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.rate.impl.service.CommonRateSchemeService;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodLanguage;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.dataentry.bean.FormLockParam;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.bean.FormLockBatchReadWriteResult;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.dataentry.service.IFormLockService;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class ConversionServiceImpl
implements ConversionService {
    private Logger logger = LoggerFactory.getLogger(ConversionServiceImpl.class);
    private static final String CONEVERSION_CURRENCY_CODE_BWB = "CONVERSIONBWB";
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ConversionLogInfoService conversionLogInfoService;
    @Autowired
    private IBatchCalculateService batchOperationService;
    @Autowired
    private ConversionFormExecutorDispatcher conversionFormExecutorDispatcher;
    @Autowired
    private FormulaSchemeConfigService formulaSchemeConfigService;
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;
    @Autowired
    private ConversionRateService conversionRateService;
    @Autowired
    private CommonRateSchemeService rateSchemeService;

    @Override
    public ConversionInitEnvDTO getConversionInitEnv() {
        ConversionInitEnvDTO initEnvDTO = new ConversionInitEnvDTO();
        try {
            Object inputDataServiceImpl = SpringContextUtils.getBean((String)"inputDataServiceImpl");
            if (inputDataServiceImpl == null) {
                initEnvDTO.setShowAfterConversionRealTimeOffset(false);
            }
        }
        catch (NoSuchBeanDefinitionException e) {
            initEnvDTO.setShowAfterConversionRealTimeOffset(false);
        }
        return initEnvDTO;
    }

    public ConversionServiceImpl getConversionService() {
        ConversionServiceImpl conversionService = (ConversionServiceImpl)SpringContextUtils.getBean(ConversionServiceImpl.class);
        return conversionService;
    }

    @Override
    public void conversion(GcConversionContextEnv conversionContextEnv) {
        try {
            BusinessLogUtils.operate((String)"\u5916\u5e01\u6298\u7b97", (String)"\u6267\u884c\u5916\u5e01\u6298\u7b97", (String)JsonUtils.writeValueAsString((Object)conversionContextEnv));
            this.createAsyncTask(conversionContextEnv);
            this.fillAndCheckEnvArgs(conversionContextEnv);
            GcOrgTypeUtils.setContextEntityId((String)conversionContextEnv.getOrgVersionType());
            this.modifyAsyncTaskState(conversionContextEnv, TaskState.PROCESSING, 0.1, GcI18nUtil.getMessage((String)"gc.coversion.service.taskstate.processing.info"));
            this.executeConversion(conversionContextEnv);
        }
        catch (Exception e) {
            conversionContextEnv.getConversionMessages().add(e.getMessage());
            throw e;
        }
        finally {
            NpContextHolder.clearContext();
            this.modifyAsyncTaskState(conversionContextEnv, TaskState.PROCESSING, 1.0, "");
        }
    }

    private void fillAndCheckEnvArgs(GcConversionContextEnv conversionContextEnv) {
        if (CollectionUtils.isEmpty((Collection)conversionContextEnv.getOrgIds())) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.service.orgid.notnull.error"));
        }
        if (CollectionUtils.isEmpty((Collection)conversionContextEnv.getFormIds())) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.service.formid.notnull.error"));
        }
        Objects.requireNonNull(conversionContextEnv.getPeriodStr(), GcI18nUtil.getMessage((String)"gc.coversion.service.periodStr.notnull.error"));
        Objects.requireNonNull(conversionContextEnv.getTaskId(), GcI18nUtil.getMessage((String)"gc.coversion.service.taskId.notnull.error"));
        Objects.requireNonNull(conversionContextEnv.getSchemeId(), GcI18nUtil.getMessage((String)"gc.coversion.service.schemeid.notnull.error"));
        String option = this.nvwaSystemOptionService.get("ALLOW_UPLOADSTATE_CONVERSION", "ALLOW_UPLOADSTATE_CONVERSION");
        conversionContextEnv.setAllowUploadStateConversion(Boolean.valueOf("1".equals(option)));
    }

    private void executeConversion(GcConversionContextEnv conversionContextEnv) {
        if (CollectionUtils.isEmpty((Collection)conversionContextEnv.getOrgIds())) {
            return;
        }
        ConversionSystemTaskEO taskSchemeEO = GcConversionUtils.getConversionSystemTaskEO(conversionContextEnv.getTaskId(), conversionContextEnv.getSchemeId());
        List<GcConversionOrgAndFormContextEnv> orgAndFormContextEnvs = this.getOrgAndFormAndTableContextEnvs(conversionContextEnv, taskSchemeEO);
        double stepProgress = this.getStepProgress(orgAndFormContextEnvs);
        conversionContextEnv.setStepProgress(stepProgress);
        conversionContextEnv.getOrgIds().stream().filter(Objects::nonNull).forEach(orgId -> {
            List<GcConversionOrgAndFormContextEnv> orgContextEnvs = orgAndFormContextEnvs.stream().filter(env -> orgId.equals(env.getOrgId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(orgContextEnvs)) {
                GcOrgCacheVO org = ((GcConversionOrgAndFormContextEnv)orgContextEnvs.get(0)).getOrg();
                String orgBeforeCurrencyCode = ((GcConversionOrgAndFormContextEnv)orgContextEnvs.get(0)).getBeforeCurrencyCode();
                String orgAfterCurrencyCode = ((GcConversionOrgAndFormContextEnv)orgContextEnvs.get(0)).getAfterCurrencyCode();
                this.getConversionService().conversionByOrg(conversionContextEnv, org, orgBeforeCurrencyCode, orgAfterCurrencyCode, orgContextEnvs);
            }
        });
    }

    private double getStepProgress(List<GcConversionOrgAndFormContextEnv> orgAndFormContextEnvs) {
        double stepProgress = orgAndFormContextEnvs.size() > 0 ? new BigDecimal(0.95f / (float)orgAndFormContextEnvs.size()).setScale(5, 1).doubleValue() : 0.95;
        return stepProgress;
    }

    private String getFormulaSchemeKeys(GcConversionContextEnv env, GcOrgCacheVO org, String orgAfterCurrencyCode) {
        Map dimensionSetMap = DimensionUtils.generateDimMap(null, null, (String)orgAfterCurrencyCode, (String)env.getOrgTypeId(), (String)"0", (String)env.getTaskId());
        List convertAfterSchemeIds = null;
        FormulaSchemeConfigDTO formulaSchemeConfigDTO = null;
        boolean pd = true;
        try {
            formulaSchemeConfigDTO = this.formulaSchemeConfigService.getConvertSchemeConfig(env.getSchemeId(), org.getId(), dimensionSetMap);
            if (formulaSchemeConfigDTO == null) {
                pd = false;
            }
        }
        catch (Exception e) {
            pd = false;
        }
        if (!pd) {
            this.logger.info("\u672a\u67e5\u5230\u5bf9\u5e94\u7684\u53d6\u6570\u4e0e\u516c\u5f0f\u65b9\u6848\u914d\u7f6e\uff0c\u6309\u7167\u9ed8\u8ba4\u8fd0\u7b97\u65b9\u5f0f\u6267\u884c");
            return null;
        }
        convertAfterSchemeIds = formulaSchemeConfigDTO.getConvertAfterSchemeId();
        if (null == convertAfterSchemeIds || CollectionUtils.isEmpty((Collection)convertAfterSchemeIds)) {
            this.logger.info("\u5bf9\u5e94\u7684\u53d6\u6570\u4e0e\u516c\u5f0f\u65b9\u6848\u914d\u7f6e\u4e2d\u672a\u914d\u7f6e\u6298\u7b97\u540e\u8fd0\u7b97\u65b9\u6848\uff0c\u6309\u7167\u9ed8\u8ba4\u8fd0\u7b97\u65b9\u5f0f\u6267\u884c");
            return null;
        }
        String formulaSchemeKeys = null;
        formulaSchemeKeys = convertAfterSchemeIds.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(";"));
        this.logger.info("\u5f53\u524d\u5916\u5e01\u6298\u7b97\u6240\u6267\u884c\u7684\u6298\u7b97\u62a5\u8868\u516c\u5f0f\u65b9\u6848ID\u4e3a[{}]\uff0c\u6765\u6e90\u4e8e\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u529f\u80fd\u70b9ID\u4e3a[{}]\u7684\u914d\u7f6e\u9879\u3002", (Object)formulaSchemeConfigDTO.getFetchSchemeId(), (Object)formulaSchemeKeys);
        return formulaSchemeKeys;
    }

    private List<GcConversionOrgAndFormContextEnv> getOrgAndFormAndTableContextEnvs(GcConversionContextEnv env, ConversionSystemTaskEO taskSchemeEO) {
        Map<String, FormDefine> formDefineMap = GcConversionUtils.getFormDefinesByEnv(env.getSchemeId(), env.getFormIds(), null);
        if (formDefineMap == null || formDefineMap.size() == 0) {
            return null;
        }
        YearPeriodObject yp = new YearPeriodObject(null, env.getPeriodStr());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)env.getOrgVersionType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        HashMap orgFormLockedMap = new HashMap();
        ArrayList<GcConversionOrgAndFormContextEnv> orgAndFormContextEnvs = new ArrayList<GcConversionOrgAndFormContextEnv>();
        env.getOrgIds().stream().filter(Objects::nonNull).forEach(orgId -> {
            String errMsg;
            GcOrgCacheVO orgToJsonVO = instance.getOrgByCode(orgId);
            if (orgToJsonVO == null) {
                String msg = GcI18nUtil.getMessage((String)"gc.coversion.service.orgid.notfound.error", (Object[])new Object[]{orgId});
                env.getConversionMessages().add(msg);
                this.logger.info(msg);
                return;
            }
            String orgTitle = orgToJsonVO.getTitle();
            String currencyId = String.valueOf(orgToJsonVO.getTypeFieldValue("CURRENCYID"));
            if (StringUtils.isEmpty(currencyId)) {
                String msg = GcI18nUtil.getMessage((String)"gc.coversion.service.bwbcurrecy.null.error", (Object[])new Object[]{orgTitle});
                env.getConversionMessages().add(msg);
                this.logger.info(msg);
                return;
            }
            String currencyIds = String.valueOf(orgToJsonVO.getTypeFieldValue("CURRENCYIDS"));
            String orgAfterCurrecyCode = ConverterUtils.getAsString((Object)env.getAfterCurrencyCode(), (String)"");
            String orgBeforeCurrecyCode = CONEVERSION_CURRENCY_CODE_BWB.equals(env.getBeforeCurrencyCode()) ? currencyId : env.getBeforeCurrencyCode();
            orgBeforeCurrecyCode = ConverterUtils.getAsString((Object)orgBeforeCurrecyCode, (String)"");
            GcBaseData beforeCurrency = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CURRENCY", orgBeforeCurrecyCode);
            if (beforeCurrency == null) {
                String msg = GcI18nUtil.getMessage((String)"gc.coversion.service.beforeCurrency.null.error", (Object[])new Object[]{orgTitle, orgBeforeCurrecyCode});
                env.getConversionMessages().add(msg);
                this.logger.info(msg);
                return;
            }
            GcBaseData afterCurrency = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CURRENCY", orgAfterCurrecyCode);
            if (afterCurrency == null) {
                String msg = GcI18nUtil.getMessage((String)"gc.coversion.service.afterCurrency.null.error", (Object[])new Object[]{orgTitle, orgAfterCurrecyCode});
                env.getConversionMessages().add(msg);
                this.logger.info(msg);
                return;
            }
            if (!env.getAllowConversionBWB().booleanValue() && orgAfterCurrecyCode.equals(currencyId)) {
                String msg = GcI18nUtil.getMessage((String)"gc.coversion.service.before2bwbCurrency.error", (Object[])new Object[]{orgTitle, beforeCurrency.getTitle(), afterCurrency.getTitle()});
                env.getConversionMessages().add(msg);
                this.logger.info(msg);
                return;
            }
            if (currencyIds.indexOf(orgBeforeCurrecyCode) == -1) {
                String msg = GcI18nUtil.getMessage((String)"gc.coversion.service.notfound.beforeCurrency.error", (Object[])new Object[]{orgTitle, beforeCurrency.getTitle()});
                env.getConversionMessages().add(msg);
                this.logger.info(msg);
                return;
            }
            if (currencyIds.indexOf(orgAfterCurrecyCode) == -1) {
                String msg = GcI18nUtil.getMessage((String)"gc.coversion.service.notfound.afterCurrency.error", (Object[])new Object[]{orgTitle, afterCurrency.getTitle()});
                env.getConversionMessages().add(msg);
                this.logger.info(msg);
                return;
            }
            if (orgBeforeCurrecyCode.equals(orgAfterCurrecyCode)) {
                String msg = GcI18nUtil.getMessage((String)"gc.coversion.service.beforeEqualsAfterCurrency.error", (Object[])new Object[]{orgTitle, beforeCurrency.getTitle()});
                env.getConversionMessages().add(msg);
                this.logger.info(msg);
                return;
            }
            if (!env.getAllowUploadStateConversion().booleanValue() && !StringUtils.isEmpty(errMsg = this.checkUpload(env, orgToJsonVO, orgAfterCurrecyCode))) {
                String msg = GcI18nUtil.getMessage((String)"gc.coversion.service.exception.error", (Object[])new Object[]{orgTitle, errMsg});
                env.getConversionMessages().add(msg);
                this.logger.info(msg);
                return;
            }
            Map<String, BigDecimal> rateValueMap = this.conversionRateService.getRateInfos(taskSchemeEO == null ? null : taskSchemeEO.getRateSchemeCode(), env.getSchemeId(), orgBeforeCurrecyCode, orgAfterCurrecyCode, env.getPeriodStr());
            if (rateValueMap == null || rateValueMap.size() == 0) {
                CommonRateSchemeVO rateSchemeVO = this.rateSchemeService.queryRateScheme(taskSchemeEO.getRateSchemeCode());
                String msg = GcI18nUtil.getMessage((String)"gc.coversion.service.rateSettingNotFound.error", (Object[])new Object[]{orgTitle, rateSchemeVO.getName(), env.getPeriodStr(), beforeCurrency.getTitle(), afterCurrency.getTitle()});
                throw new BusinessRuntimeException(msg);
            }
            Map dimensionSetMap = DimensionUtils.buildDimensionMap((String)env.getTaskId(), (String)orgBeforeCurrecyCode, (String)env.getPeriodStr(), (String)env.getOrgTypeId(), (String)orgId, (String)env.getSelectAdjustCode());
            DimensionParamsVO dimensionParamsVO = new DimensionParamsVO();
            dimensionParamsVO.setCurrency(orgAfterCurrecyCode);
            dimensionParamsVO.setCurrencyId(orgAfterCurrecyCode);
            dimensionParamsVO.setOrgId(orgId);
            dimensionParamsVO.setOrgType(env.getOrgVersionType());
            dimensionParamsVO.setOrgTypeId(env.getOrgTypeId());
            dimensionParamsVO.setPeriodStr(env.getPeriodStr());
            dimensionParamsVO.setSchemeId(env.getSchemeId());
            dimensionParamsVO.setTaskId(env.getTaskId());
            dimensionParamsVO.setSelectAdjustCode(env.getSelectAdjustCode());
            String finalOrgBeforeCurrecyCode = orgBeforeCurrecyCode;
            String finalOrgAfterCurrecyCode = orgAfterCurrecyCode;
            HashMap<String, ReadWriteAccessDesc> formUploadMap = new HashMap<String, ReadWriteAccessDesc>();
            List formIds = env.getFormIds();
            if (!env.getAllowUploadStateConversion().booleanValue()) {
                List formUploadStates = FormUploadStateTool.getInstance().queryUploadState(dimensionParamsVO, dimensionParamsVO.getOrgId(), formIds);
                for (int i = 0; i < formUploadStates.size(); ++i) {
                    ReadWriteAccessDesc readWriteAccessDesc = this.writeable((UploadState)formUploadStates.get(i));
                    String formId2 = (String)formIds.get(i);
                    formUploadMap.put(formId2, readWriteAccessDesc);
                }
            }
            JtableContext jtableContext = JtableContextConvertUtils.convert2JtableContext((DimensionParamsVO)dimensionParamsVO, (String)orgId);
            FormLockParam lockParam = new FormLockParam();
            lockParam.setContext(jtableContext);
            IFormLockService formLockService = (IFormLockService)SpringContextUtils.getBean(IFormLockService.class);
            List formLockBatchReadWriteResult = formLockService.batchDimension(jtableContext);
            Set formLockedList = formLockBatchReadWriteResult.stream().filter(item -> item.isLock()).map(FormLockBatchReadWriteResult::getFormKey).collect(Collectors.toSet());
            env.getFormIds().stream().filter(Objects::nonNull).forEach(formId -> {
                ReadWriteAccessDesc readWriteAccessDescUploadState;
                FormDefine formDefine = (FormDefine)formDefineMap.get(formId);
                if (formDefine == null) {
                    return;
                }
                if (formLockedList.contains(formId)) {
                    String msg = GcI18nUtil.getMessage((String)"gc.coversion.service.formlock.error", (Object[])new Object[]{orgTitle, formDefine.getTitle(), "\u62a5\u8868\u5df2\u9501\u5b9a\uff0c\u4e0d\u5141\u8bb8\u6267\u884c\u64cd\u4f5c"});
                    env.getConversionMessages().add(msg);
                    this.logger.info(msg);
                    if (orgFormLockedMap.get(orgId) == null) {
                        orgFormLockedMap.put(orgId, new ArrayList());
                    }
                    ((List)orgFormLockedMap.get(orgId)).add(formId);
                    return;
                }
                if (!env.getAllowUploadStateConversion().booleanValue() && Boolean.FALSE.equals((readWriteAccessDescUploadState = (ReadWriteAccessDesc)formUploadMap.get(formId)).getAble())) {
                    String msg = GcI18nUtil.getMessage((String)"gc.coversion.service.uploadstate.error", (Object[])new Object[]{orgTitle, formDefine.getTitle(), readWriteAccessDescUploadState.getDesc()});
                    env.getConversionMessages().add(msg);
                    this.logger.info(msg);
                    return;
                }
                Map<DataRegionDefine, Set<TableModelDefine>> formTableDefines = GcConversionUtils.getFormTableDefines(formDefine.getKey());
                if (formTableDefines == null) {
                    return;
                }
                formTableDefines.forEach((dataRegionDefine, tableDefines) -> tableDefines.stream().filter(Objects::nonNull).forEach(tableDefine -> {
                    GcConversionOrgAndFormContextEnv singleOrgEnv = new GcConversionOrgAndFormContextEnv();
                    singleOrgEnv.setAfterCurrencyCode(finalOrgAfterCurrecyCode);
                    singleOrgEnv.setBeforeCurrencyCode(finalOrgBeforeCurrecyCode);
                    singleOrgEnv.setDimensionSet(dimensionSetMap);
                    singleOrgEnv.setOrgId(orgId);
                    singleOrgEnv.setOrgTitle(orgTitle);
                    singleOrgEnv.setOrgTypeId(env.getOrgTypeId());
                    singleOrgEnv.setOrgVersionType(env.getOrgVersionType());
                    singleOrgEnv.setPeriodStr(env.getPeriodStr());
                    singleOrgEnv.setSchemeId(env.getSchemeId());
                    singleOrgEnv.setTaskId(env.getTaskId());
                    singleOrgEnv.setFormDefine(formDefine);
                    singleOrgEnv.setTableDefine(tableDefine);
                    singleOrgEnv.setOrg(orgToJsonVO);
                    singleOrgEnv.setDataRegionDefine(dataRegionDefine);
                    singleOrgEnv.setSelectAdjustCode(env.getSelectAdjustCode());
                    singleOrgEnv.setAfterConversionRealTimeOffset(Boolean.TRUE.equals(env.getAfterConversionRealTimeOffset()));
                    singleOrgEnv.setConversionInputData(Boolean.TRUE.equals(env.getConversionInputData()));
                    orgAndFormContextEnvs.add(singleOrgEnv);
                }));
            });
        });
        if (!CollectionUtils.isEmpty(orgAndFormContextEnvs)) {
            Iterator iterator = orgAndFormContextEnvs.iterator();
            while (iterator.hasNext()) {
                String formId;
                GcConversionOrgAndFormContextEnv contextEnv = (GcConversionOrgAndFormContextEnv)iterator.next();
                String orgId2 = contextEnv.getOrgId();
                List lockFormKeys = (List)orgFormLockedMap.get(orgId2);
                if (CollectionUtils.isEmpty((Collection)lockFormKeys) || !lockFormKeys.contains(formId = contextEnv.getFormDefine().getKey())) continue;
                iterator.remove();
            }
        }
        return orgAndFormContextEnvs;
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void conversionByOrg(GcConversionContextEnv env, GcOrgCacheVO org, String orgBeforeCurrencyCode, String orgAfterCurrencyCode, List<GcConversionOrgAndFormContextEnv> contextEnvs) {
        block16: {
            if (CollectionUtils.isEmpty(contextEnvs)) {
                return;
            }
            String errorMsg = GcConversionUtils.checkAllowConversionByOrg(env.getTaskId(), env.getSchemeId(), env.getOrgVersionType(), org.getId(), org.getTitle(), env.getOrgTypeId(), env.getAfterCurrencyCode(), env.getPeriodStr(), env.getSelectAdjustCode());
            if (!StringUtils.isEmpty(errorMsg)) {
                env.getConversionMessages().add(errorMsg);
                return;
            }
            ArrayList orgMatchFormKeys = new ArrayList();
            contextEnvs.stream().forEachOrdered(contextEnv -> {
                String key = contextEnv.getFormDefine().getKey();
                if (orgMatchFormKeys.contains(key)) {
                    return;
                }
                orgMatchFormKeys.add(key);
            });
            StringBuilder loginInfo = new StringBuilder();
            PeriodWrapper periodWrapper = new PeriodWrapper(env.getPeriodStr());
            if (DataEntryUtil.isChinese()) {
                periodWrapper.setLanguage(PeriodLanguage.Chinese);
            } else {
                periodWrapper.setLanguage(PeriodLanguage.English);
            }
            String periodTitle = new DefaultPeriodAdapter().getPeriodTitle(periodWrapper);
            loginInfo.append(GcI18nUtil.getMessage((String)"gc.coversion.service.result.info", (Object[])new Object[]{periodTitle, env.getPeriodStr(), env.getFormIds().size()}));
            boolean successFlag = true;
            try {
                AtomicBoolean isContainInputDataConversion = new AtomicBoolean(false);
                orgMatchFormKeys.stream().forEachOrdered(formId -> {
                    List<GcConversionOrgAndFormContextEnv> orgAndFormContextEnvs = contextEnvs.stream().filter(Objects::nonNull).filter(contextEnv -> {
                        if (!isContainInputDataConversion.get() && contextEnv.getTableDefine().getName().toUpperCase().contains("GC_INPUTDATA")) {
                            isContainInputDataConversion.set(true);
                        }
                        boolean equals = formId.equals(contextEnv.getFormDefine().getKey());
                        return equals;
                    }).collect(Collectors.toList());
                    if (CollectionUtils.isEmpty(orgAndFormContextEnvs)) {
                        return;
                    }
                    loginInfo.append(((GcConversionOrgAndFormContextEnv)orgAndFormContextEnvs.get(0)).getFormDefine().getTitle()).append("\u3001");
                    this.convesionByOrgAndForm(env, orgAndFormContextEnvs);
                });
                if (!env.getAfterConversionoperation().booleanValue()) break block16;
                try {
                    NpContext context = NpContextHolder.getContext();
                    CompletableFuture<Boolean> completableFuture = this.getConversionService().batchCalculateForm(context, env, org, orgAfterCurrencyCode);
                    Boolean result = completableFuture.get();
                    if (Boolean.TRUE.equals(result)) {
                        this.logger.info("\u6298\u7b97\u6279\u6b21\u53f7ID:" + env.getSn() + "]\uff0c\u5355\u4f4d: [" + org.getTitle() + "]\uff0c\u6298\u7b97\u5168\u7b97\u6267\u884c\u5b8c\u6bd5\u3002");
                        break block16;
                    }
                    this.logger.info("\u6298\u7b97\u6279\u6b21\u53f7ID:" + env.getSn() + "]\uff0c\u5355\u4f4d: [" + org.getTitle() + "]\uff0c\u6298\u7b97\u5168\u7b97\u6267\u884c\u5931\u8d25\u3002");
                }
                catch (Exception e) {
                    this.logger.error("\u6298\u7b97\u6279\u6b21\u53f7ID:" + env.getSn() + "]\uff0c\u6298\u7b97\u5168\u7b97\u8fc7\u7a0b\u4e2d\u51fa\u73b0\u5f02\u5e38\u3002", e);
                }
            }
            catch (Exception e) {
                successFlag = false;
                throw e;
            }
            finally {
                try {
                    this.addLogInfo(env, org, orgBeforeCurrencyCode, orgAfterCurrencyCode, loginInfo.substring(0, loginInfo.length() - 1), successFlag);
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
        }
    }

    private String checkUpload(GcConversionContextEnv env, GcOrgCacheVO org, String orgAfterCurrecyCode) {
        DimensionParamsVO params = new DimensionParamsVO();
        params.setTaskId(env.getTaskId());
        params.setSchemeId(env.getSchemeId());
        params.setCurrency(orgAfterCurrecyCode);
        params.setCurrencyId(orgAfterCurrecyCode);
        params.setOrgId(org.getId());
        params.setOrgType(env.getOrgVersionType());
        params.setOrgTypeId(env.getOrgTypeId());
        params.setPeriodStr(env.getPeriodStr());
        params.setSelectAdjustCode(env.getSelectAdjustCode());
        ReadWriteAccessDesc writeAbleInfo = new UploadStateTool().writeable(params);
        if (!Boolean.TRUE.equals(writeAbleInfo.getAble())) {
            return org.getTitle() + writeAbleInfo.getDesc();
        }
        return "";
    }

    private void convesionByOrgAndForm(GcConversionContextEnv env, List<GcConversionOrgAndFormContextEnv> orgAndFormContextEnvs) {
        orgAndFormContextEnvs.forEach(orgAndFormEnv -> {
            this.getConversionService().convesionByOrgAndFormAndTable(env, (GcConversionOrgAndFormContextEnv)orgAndFormEnv);
            double currentProgress = env.getCurrentProgress() + env.getStepProgress();
            env.setCurrentProgress(currentProgress);
            this.modifyAsyncTaskState(env, TaskState.PROCESSING, currentProgress, GcI18nUtil.getMessage((String)"gc.coversion.service.taskstate.executing.info", (Object[])new Object[]{orgAndFormEnv.getFormDefine().getTitle()}));
        });
    }

    public GcConversionResult convesionByOrgAndFormAndTable(GcConversionContextEnv env, GcConversionOrgAndFormContextEnv formContextEnv) {
        return this.conversionFormExecutorDispatcher.conversion(formContextEnv);
    }

    private void addLogInfo(GcConversionContextEnv env, GcOrgCacheVO org, String orgBeforeCurrencyCode, String orgAfterCurrencyCode, String loginInfo, boolean successFlag) {
        ConversionLogInfoEo infoEo = new ConversionLogInfoEo();
        infoEo.setId(UUIDUtils.newUUIDStr());
        infoEo.setLoginfo(loginInfo);
        infoEo.setPeriodStr(env.getPeriodStr());
        String adjustCode = StringUtils.isEmpty(env.getSelectAdjustCode()) ? "0" : env.getSelectAdjustCode();
        infoEo.setAdjustCode(adjustCode);
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        infoEo.setLogtimeText(format);
        GcBaseData beforeCurrency = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CURRENCY", orgBeforeCurrencyCode);
        GcBaseData afterCurrency = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CURRENCY", orgAfterCurrencyCode);
        infoEo.setSrcCurrency(beforeCurrency.getCode());
        infoEo.setDstCurrency(afterCurrency.getCode());
        infoEo.setTaskId(env.getTaskId());
        try {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(env.getTaskId());
            infoEo.setTaskName(taskDefine.getTitle());
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        infoEo.setSchemeId(env.getSchemeId());
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(env.getSchemeId());
            infoEo.setSchemeName(formScheme.getTitle());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        infoEo.setUnitId(org.getId());
        infoEo.setUnitName(org.getTitle());
        infoEo.setSuccessFlag(successFlag ? 1 : 0);
        ContextUser currentUser = NpContextHolder.getContext().getUser();
        if (currentUser != null) {
            infoEo.setUserTitle(StringUtils.isEmpty(currentUser.getFullname()) ? currentUser.getName() : currentUser.getFullname());
        }
        this.logger.warn(JsonUtils.writeValueAsString((Object)((Object)infoEo)));
        this.conversionLogInfoService.saveLogInfo(infoEo);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Async
    public CompletableFuture<Boolean> batchCalculateForm(NpContext context, GcConversionContextEnv env, GcOrgCacheVO org, String orgAfterCurrencyCode) {
        NpContextHolder.setContext((NpContext)context);
        try {
            String formulaSchemeKeys = this.getFormulaSchemeKeys(env, org, orgAfterCurrencyCode);
            BatchCalculateInfo batchCalculateInfo = this.buidBatchCalculateInfo(env, org, orgAfterCurrencyCode, formulaSchemeKeys);
            this.batchOperationService.batchCalculateForm(batchCalculateInfo);
            CompletableFuture<Boolean> completableFuture = CompletableFuture.completedFuture(true);
            return completableFuture;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            CompletableFuture<Boolean> completableFuture = CompletableFuture.completedFuture(false);
            return completableFuture;
        }
        finally {
            NpContextHolder.clearContext();
        }
    }

    private BatchCalculateInfo buidBatchCalculateInfo(GcConversionContextEnv env, GcOrgCacheVO org, String orgAfterCurrencyCode, String formulaSchemeKeys) {
        Map dimensionSetMap = DimensionUtils.buildDimensionMap((String)env.getTaskId(), (String)orgAfterCurrencyCode, (String)env.getPeriodStr(), (String)env.getOrgTypeId(), (String)org.getId(), (String)env.getSelectAdjustCode());
        BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
        batchCalculateInfo.setDimensionSet(dimensionSetMap);
        HashMap formulas = new HashMap();
        batchCalculateInfo.setFormulas(formulas);
        batchCalculateInfo.setTaskKey(env.getTaskId());
        batchCalculateInfo.setFormSchemeKey(env.getSchemeId());
        batchCalculateInfo.setFormulaSchemeKey(formulaSchemeKeys);
        batchCalculateInfo.setIgnoreWorkFlow(env.getAllowUploadStateConversion().booleanValue());
        return batchCalculateInfo;
    }

    private void createAsyncTask(GcConversionContextEnv conversionContextEnv) {
        AsyncTaskMonitor asyncTaskMonitor = conversionContextEnv.getAsyncTaskMonitor();
        if (asyncTaskMonitor == null) {
            return;
        }
        asyncTaskMonitor.progressAndMessage(0.0, GcI18nUtil.getMessage((String)"gc.coversion.service.taskstate.start.info"));
    }

    private void modifyAsyncTaskState(GcConversionContextEnv conversionContextEnv, TaskState taskState, double progress, String result) {
        AsyncTaskMonitor asyncTaskMonitor = conversionContextEnv.getAsyncTaskMonitor();
        if (asyncTaskMonitor == null) {
            return;
        }
        asyncTaskMonitor.progressAndMessage(progress, result);
    }

    private ReadWriteAccessDesc writeable(UploadState status) {
        Boolean writeable = true;
        String unwriteableDesc = "";
        if (status == UploadState.SUBMITED) {
            writeable = false;
            unwriteableDesc = GcI18nUtil.getMessage((String)"gc.org.nr.uploadstatetool.submited.error");
        }
        if (status == UploadState.UPLOADED) {
            writeable = false;
            unwriteableDesc = GcI18nUtil.getMessage((String)"gc.org.nr.uploadstatetool.uploaded.error");
        }
        if (status == UploadState.CONFIRMED) {
            writeable = false;
            unwriteableDesc = GcI18nUtil.getMessage((String)"gc.org.nr.uploadstatetool.confirmed.error");
        }
        return new ReadWriteAccessDesc(writeable, unwriteableDesc);
    }
}

