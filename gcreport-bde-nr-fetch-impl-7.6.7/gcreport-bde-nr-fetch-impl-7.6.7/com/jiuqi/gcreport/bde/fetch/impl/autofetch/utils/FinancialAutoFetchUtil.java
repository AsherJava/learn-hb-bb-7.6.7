/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.dto.fetch.init.FetchFormDTO
 *  com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO
 *  com.jiuqi.bde.common.dto.fetch.init.FetchRegionDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.bpm.common.UploadStateNew
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionState
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 *  com.jiuqi.nr.data.engine.condition.IFormConditionService
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.gcreport.bde.fetch.impl.autofetch.utils;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.dto.fetch.init.FetchFormDTO;
import com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO;
import com.jiuqi.bde.common.dto.fetch.init.FetchRegionDTO;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionState;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.data.engine.condition.IFormConditionService;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FinancialAutoFetchUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FinancialAutoFetchUtil.class);
    public static Set<String> DIM_COMPUTATIONMODEL_LIST = new HashSet<String>();
    public static Set<String> AGING_COMPUTATIONMODEL_LIST;
    public static Set<String> CF_COMPUTATIONMODEL_LIST;
    private static final String FN_TMPL_END_SCOPE = "%1$sZZ";
    public static final String FETCH_REGION_CONDI = "FETCH_REGION_CONDI";
    public static final String TASKID = "TASKID";
    public static final String FINCUBES_AGING = "FINCUBES_AGING";
    public static final String FINCUBES_CF = "FINCUBES_CF";
    public static final String FINCUBES_DIM = "FINCUBES_DIM";
    static final String UPLOADSTATETOOL_SUBMITED = "gc.org.nr.uploadstatetool.submited.error";
    static final String UPLOADSTATETOOL_UPLOADED = "gc.org.nr.uploadstatetool.uploaded.error";
    static final String UPLOADSTATETOOL_CONFIRMED = "gc.org.nr.uploadstatetool.confirmed.error";

    public static boolean needToFilterBusinessModel(String financialTableName, String computationModel) {
        if ("GC_FINCUBES_DIM".equals(financialTableName)) {
            return DIM_COMPUTATIONMODEL_LIST.contains(computationModel);
        }
        if ("GC_FINCUBES_AGING".equals(computationModel)) {
            return AGING_COMPUTATIONMODEL_LIST.contains(computationModel);
        }
        if ("GC_FINCUBES_CF".equals(computationModel)) {
            return CF_COMPUTATIONMODEL_LIST.contains(computationModel);
        }
        return false;
    }

    public static boolean matchSingle(String setting, String code) {
        return code.startsWith(setting);
    }

    public static boolean matchMultiple(String setting, String code) {
        String[] settingArr = setting.split(",");
        if (settingArr.length == 1) {
            return FinancialAutoFetchUtil.matchSingle(setting, code);
        }
        for (String settingStr : settingArr) {
            if (!FinancialAutoFetchUtil.matchSingle(settingStr, code)) continue;
            return true;
        }
        return false;
    }

    public static boolean matchRange(String setting, String code) {
        String[] settingArr = setting.split(":");
        if (settingArr.length != 2) {
            return false;
        }
        String begin = settingArr[0];
        String end = String.format(FN_TMPL_END_SCOPE, settingArr[1]);
        return code.compareTo(begin) >= 0 && code.compareTo(end) <= 0;
    }

    public static boolean isSamePeriod(PeriodType periodType, FinancialCubesPeriodTypeEnum financialCubesPeriodTypeEnum) {
        if (PeriodType.MONTH.equals((Object)periodType) && FinancialCubesPeriodTypeEnum.Y.equals((Object)financialCubesPeriodTypeEnum)) {
            return true;
        }
        if (PeriodType.SEASON.equals((Object)periodType) && FinancialCubesPeriodTypeEnum.J.equals((Object)financialCubesPeriodTypeEnum)) {
            return true;
        }
        if (PeriodType.HALFYEAR.equals((Object)periodType) && FinancialCubesPeriodTypeEnum.H.equals((Object)financialCubesPeriodTypeEnum)) {
            return true;
        }
        return PeriodType.YEAR.equals((Object)periodType) && FinancialCubesPeriodTypeEnum.N.equals((Object)financialCubesPeriodTypeEnum);
    }

    public static boolean isSamePeriod(PeriodType periodType, String financialPeriodType) {
        if (PeriodType.MONTH.equals((Object)periodType) && FinancialCubesPeriodTypeEnum.Y.getCode().equals(financialPeriodType)) {
            return true;
        }
        if (PeriodType.SEASON.equals((Object)periodType) && FinancialCubesPeriodTypeEnum.J.getCode().equals(financialPeriodType)) {
            return true;
        }
        if (PeriodType.HALFYEAR.equals((Object)periodType) && FinancialCubesPeriodTypeEnum.H.getCode().equals(financialPeriodType)) {
            return true;
        }
        return PeriodType.YEAR.equals((Object)periodType) && FinancialCubesPeriodTypeEnum.N.getCode().equals(financialPeriodType);
    }

    public static UploadState queryUploadState(DimensionValueSet dimensionValueSet, String schemeId, String orgId, String formId) {
        ArrayList<String> formIds = new ArrayList<String>();
        formIds.add(formId);
        List<UploadState> uploadState = FinancialAutoFetchUtil.queryUploadState(dimensionValueSet, schemeId, orgId, formIds);
        if (CollectionUtils.isEmpty(uploadState)) {
            return UploadState.ORIGINAL;
        }
        return uploadState.get(0);
    }

    public static ReadWriteAccessDesc writeable(UploadState status) {
        Boolean writeable = true;
        String unwriteableDesc = "";
        if (status == UploadState.SUBMITED) {
            writeable = false;
            unwriteableDesc = GcI18nUtil.getMessage((String)UPLOADSTATETOOL_SUBMITED);
        }
        if (status == UploadState.UPLOADED) {
            writeable = false;
            unwriteableDesc = GcI18nUtil.getMessage((String)UPLOADSTATETOOL_UPLOADED);
        }
        if (status == UploadState.CONFIRMED) {
            writeable = false;
            unwriteableDesc = GcI18nUtil.getMessage((String)UPLOADSTATETOOL_CONFIRMED);
        }
        return new ReadWriteAccessDesc(writeable, unwriteableDesc);
    }

    public static List<UploadState> queryUploadState(DimensionValueSet dimensionValueSet, String schemeId, String orgId, List<String> formIds) {
        FormSchemeDefine formScheme;
        IRunTimeViewController authViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        try {
            formScheme = authViewController.getFormScheme(schemeId);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u5931\u8d25");
        }
        IBatchQueryUploadStateService batchQueryUploadStateService = (IBatchQueryUploadStateService)SpringContextUtils.getBean(IBatchQueryUploadStateService.class);
        List uploadStateNewList = batchQueryUploadStateService.queryUploadStateNew(formScheme, dimensionValueSet, null);
        if (CollectionUtils.isEmpty((Collection)uploadStateNewList)) {
            return formIds.stream().map(item -> UploadState.ORIGINAL).collect(Collectors.toList());
        }
        Map<String, UploadState> formId2UploadStateMap = uploadStateNewList.stream().collect(Collectors.toMap(UploadStateNew::getFormId, uploadStateNew -> {
            ActionStateBean actionStateBean = uploadStateNew.getActionStateBean();
            if (null == actionStateBean) {
                return UploadState.ORIGINAL;
            }
            return UploadState.valueOf((String)actionStateBean.getCode());
        }));
        IWorkflow iWorkflow = (IWorkflow)SpringContextUtils.getBean(IWorkflow.class);
        WorkFlowType workFlowType = iWorkflow.queryStartType(schemeId);
        if (WorkFlowType.GROUP.equals((Object)workFlowType)) {
            IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            uploadStateNewList.forEach(item -> {
                try {
                    List formDefines = iRunTimeViewController.getAllFormsInGroup(item.getFormId());
                    if (!CollectionUtils.isEmpty((Collection)formDefines)) {
                        formDefines.forEach(formDefine -> formId2UploadStateMap.put(formDefine.getKey(), (UploadState)formId2UploadStateMap.get(item.getFormId())));
                    }
                }
                catch (Exception e) {
                    LOGGER.warn(e.getMessage(), e);
                }
            });
        }
        return formIds == null ? Collections.emptyList() : formIds.stream().map(formId -> formId2UploadStateMap.getOrDefault(formId, UploadState.ORIGINAL)).collect(Collectors.toList());
    }

    public static UploadState queryUnitUploadState(String formSchemeKey, DimensionValueSet dimensionValueSet) {
        DataEntryParam dataEntryParam = new DataEntryParam();
        dataEntryParam.setDim(dimensionValueSet);
        dataEntryParam.setFormSchemeKey(formSchemeKey);
        IDataentryFlowService dataFlowService = (IDataentryFlowService)BeanUtil.getBean(IDataentryFlowService.class);
        IFormConditionService formConditionService = (IFormConditionService)BeanUtil.getBean(IFormConditionService.class);
        IConditionCache conditionCache = formConditionService.getConditionForms(dimensionValueSet, formSchemeKey);
        dataEntryParam.setFormKeys(conditionCache.getSeeForms(dimensionValueSet));
        dataEntryParam.setGroupKeys(conditionCache.getSeeFormGroups(dimensionValueSet));
        ActionState uploadState = dataFlowService.queryState(dataEntryParam);
        if (null == uploadState || null == uploadState.getUnitState() || StringUtils.isEmpty((String)uploadState.getUnitState().getCode())) {
            return UploadState.ORIGINAL;
        }
        return UploadState.valueOf((String)uploadState.getUnitState().getCode());
    }

    public static UploadState queryUnitUploadState(String formSchemeKey, String unitCode, String dataTime, String currencyCode, String orgType, String adjustCode, String taskKey) {
        DimensionValueSet dimensionValueSet = DimensionUtils.generateDimSet((Object)unitCode, (Object)dataTime, (Object)currencyCode, (Object)orgType, (String)adjustCode, (String)taskKey);
        return FinancialAutoFetchUtil.queryUnitUploadState(formSchemeKey, dimensionValueSet);
    }

    public static ReadWriteAccessDesc queryUnitWriteable(String formSchemeKey, String unitCode, String dataTime, String currencyCode, String orgType, String adjustCode, String taskKey) {
        DimensionValueSet dimensionValueSet = DimensionUtils.generateDimSet((Object)unitCode, (Object)dataTime, (Object)currencyCode, (Object)orgType, (String)adjustCode, (String)taskKey);
        return FinancialAutoFetchUtil.writeable(FinancialAutoFetchUtil.queryUnitUploadState(formSchemeKey, dimensionValueSet));
    }

    public static String fetchFormInfoStr(FetchInitTaskDTO fetchInitTask) {
        String str = String.format("\u3010\u81ea\u52a8\u53d6\u6570\u4e0a\u4e0b\u6587\u3011\n\u3010\u62a5\u8868\u4efb\u52a1\u540d\u79f0\u3011%s\n\u3010\u62a5\u8868\u65b9\u6848ID\u3011%s\u3010\u62a5\u8868\u65b9\u6848\u540d\u79f0\u3011%s\n\u3010\u53d6\u6570\u65b9\u6848ID\u3011%s\u3010\u53d6\u6570\u65b9\u6848\u540d\u79f0\u3011%s\n", fetchInitTask.getTaskTitle(), fetchInitTask.getFormSchemeId(), fetchInitTask.getFormSchemeTitle(), fetchInitTask.getFetchSchemeId(), fetchInitTask.getFetchSchemeTitle());
        str = str + "\u3010\u81ea\u52a8\u53d6\u6570\u62a5\u8868\u548c\u533a\u57df\u4fe1\u606f\u3011\u5171\u81ea\u52a8\u53d6\u6570\u62a5\u8868 %s \u5f20\n";
        List fetchForms = fetchInitTask.getFetchForms();
        if (CollectionUtils.isEmpty((Collection)fetchForms)) {
            return String.format(str, 0);
        }
        StringBuilder info = new StringBuilder(String.format(str, fetchInitTask.getFetchForms().size()));
        for (FetchFormDTO fetchForm : fetchForms) {
            if (fetchForm == null) continue;
            long regionCount = CollectionUtils.isEmpty((Collection)fetchForm.getFetchRegions()) ? 0L : (long)fetchForm.getFetchRegions().stream().map(FetchRegionDTO::getId).collect(Collectors.toSet()).size();
            String formInfo = String.format("\u3010\u62a5\u8868\u4ee3\u7801\u3011%s\u3010\u62a5\u8868\u540d\u79f0\u3011%s\u3010\u533a\u57df\u6570\u91cf\u3011%s\n", fetchForm.getFormCode(), fetchForm.getFormTitle(), regionCount);
            info.append(formInfo);
        }
        return info.toString();
    }

    static {
        DIM_COMPUTATIONMODEL_LIST.add(ComputationModelEnum.ASSBALANCE.getCode());
        DIM_COMPUTATIONMODEL_LIST.add(ComputationModelEnum.BALANCE.getCode());
        DIM_COMPUTATIONMODEL_LIST.add(ComputationModelEnum.CFLBALANCE.getCode());
        DIM_COMPUTATIONMODEL_LIST.add(ComputationModelEnum.ASSCFLBALANCE.getCode());
        DIM_COMPUTATIONMODEL_LIST.add(ComputationModelEnum.DJYEBALANCE.getCode());
        CF_COMPUTATIONMODEL_LIST = new HashSet<String>();
        CF_COMPUTATIONMODEL_LIST.add(ComputationModelEnum.XJLLBALANCE.getCode());
        AGING_COMPUTATIONMODEL_LIST = new HashSet<String>();
        AGING_COMPUTATIONMODEL_LIST.add(ComputationModelEnum.FINANCIALASSAGINGBALANCE.getCode());
    }
}

