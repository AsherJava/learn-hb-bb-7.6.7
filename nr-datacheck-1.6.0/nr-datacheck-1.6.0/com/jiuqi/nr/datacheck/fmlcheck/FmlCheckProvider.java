/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckParam
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam
 *  com.jiuqi.nr.data.logic.facade.param.input.DefaultQueryFilter
 *  com.jiuqi.nr.data.logic.facade.param.input.GroupType
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.data.logic.facade.param.input.QueryCol
 *  com.jiuqi.nr.data.logic.facade.param.input.QueryCondition
 *  com.jiuqi.nr.data.logic.facade.param.input.QueryConditionBuilder
 *  com.jiuqi.nr.data.logic.facade.param.input.QueryFilterOperator
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroup
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroupData
 *  com.jiuqi.nr.data.logic.facade.service.ICheckResultService
 *  com.jiuqi.nr.data.logic.facade.service.ICheckService
 *  com.jiuqi.nr.data.logic.internal.util.CheckResultUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.multcheck2.bean.MultcheckItem
 *  com.jiuqi.nr.multcheck2.bean.MultcheckScheme
 *  com.jiuqi.nr.multcheck2.common.CheckRestultState
 *  com.jiuqi.nr.multcheck2.provider.CheckItemParam
 *  com.jiuqi.nr.multcheck2.provider.CheckItemResult
 *  com.jiuqi.nr.multcheck2.provider.FailedOrgInfo
 *  com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider
 *  com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO
 *  com.jiuqi.nr.multcheck2.provider.MultcheckContext
 *  com.jiuqi.nr.multcheck2.provider.PluginInfo
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 */
package com.jiuqi.nr.datacheck.fmlcheck;

import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.DefaultQueryFilter;
import com.jiuqi.nr.data.logic.facade.param.input.GroupType;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCol;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCondition;
import com.jiuqi.nr.data.logic.facade.param.input.QueryConditionBuilder;
import com.jiuqi.nr.data.logic.facade.param.input.QueryFilterOperator;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroup;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroupData;
import com.jiuqi.nr.data.logic.facade.service.ICheckResultService;
import com.jiuqi.nr.data.logic.facade.service.ICheckService;
import com.jiuqi.nr.data.logic.internal.util.CheckResultUtil;
import com.jiuqi.nr.datacheck.common.SerializeUtil;
import com.jiuqi.nr.datacheck.fmlcheck.CheckRequire;
import com.jiuqi.nr.datacheck.fmlcheck.FmlCheckConfig;
import com.jiuqi.nr.datacheck.fmlcheck.monitor.CheckMonitor;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.common.CheckRestultState;
import com.jiuqi.nr.multcheck2.provider.CheckItemParam;
import com.jiuqi.nr.multcheck2.provider.CheckItemResult;
import com.jiuqi.nr.multcheck2.provider.FailedOrgInfo;
import com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider;
import com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO;
import com.jiuqi.nr.multcheck2.provider.MultcheckContext;
import com.jiuqi.nr.multcheck2.provider.PluginInfo;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class FmlCheckProvider
implements IMultcheckItemProvider {
    static final String TYPE = "FML_CHECK";
    static final String TITLE = "\u516c\u5f0f\u5ba1\u6838";
    private static final String PROD_LINE = "@nr";
    private static final String APP_NAME = "nr-datacheck-fmlcheck-plugin";
    private static final String DES_EXPOSE = "Config";
    private static final String RUN_EXPOSE = "RunConfig";
    private static final String RESULT_EXPOSE = "Result";
    private static final String ENTRY_VIEW = "fmlCheckView";
    private static final Logger logger = LoggerFactory.getLogger(FmlCheckProvider.class);
    @Autowired
    private ICheckService checkService;
    @Autowired
    private ICheckResultService checkResultService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;
    @Autowired
    private NrdbHelper nrdbHelper;
    @Autowired
    private IEntityMetaService entityMetaService;

    public String getType() {
        return TYPE;
    }

    public String getTitle() {
        return TITLE;
    }

    public double getOrder() {
        return 0.0;
    }

    public PluginInfo getPropertyPluginInfo() {
        return new PluginInfo(PROD_LINE, APP_NAME, DES_EXPOSE);
    }

    public String getItemDescribe(String formSchemeKey, MultcheckItem item) {
        if (StringUtils.hasText(item.getConfig())) {
            try {
                FmlCheckConfig fmlCheckConfig = SerializeUtil.deserializeFromJson(item.getConfig(), FmlCheckConfig.class);
                FormulaSchemeDefine formulaScheme = this.formulaRunTimeController.queryFormulaSchemeDefine(fmlCheckConfig.getFormulaSchemeKey());
                String fmlSchemeDes = formulaScheme == null ? "" : formulaScheme.getTitle() + " | ";
                Map<String, List<String>> formulas = fmlCheckConfig.getFormulas();
                String formDes = CollectionUtils.isEmpty(formulas) ? "\u5df2\u9009 <span class=\"mtc-item-number-cls\">0</span>\u5f20\u62a5\u8868" : (formulas.size() == 1 && formulas.containsKey("ALL-FORM") ? "\u5df2\u9009\u6240\u6709\u62a5\u8868" : "\u5df2\u9009 <span class=\"mtc-item-number-cls\">" + formulas.size() + "</span>\u5f20\u62a5\u8868");
                return fmlSchemeDes + formDes;
            }
            catch (Exception e) {
                logger.error("\u7efc\u5408\u5ba1\u6838\uff1a\u516c\u5f0f\u5ba1\u6838\u914d\u7f6e\u5f02\u5e38" + item.getKey() + e.getMessage(), e);
            }
        }
        return "\u672a\u914d\u7f6e";
    }

    public MultCheckItemDTO copyCheckItem(MultcheckScheme sourceScheme, MultcheckItem sourceItem, String targetFormSchemeKey) {
        MultCheckItemDTO dto = new MultCheckItemDTO();
        BeanUtils.copyProperties(sourceItem, dto);
        return dto;
    }

    public String getRunItemDescribe(String formSchemeKey, MultcheckItem item) {
        return this.getItemDescribe(formSchemeKey, item);
    }

    public PluginInfo getRunPropertyPluginInfo() {
        return new PluginInfo(PROD_LINE, APP_NAME, RUN_EXPOSE);
    }

    public CheckItemResult runCheck(CheckItemParam param) {
        this.setOrgEntity(param);
        CheckItemResult checkItemResult = new CheckItemResult();
        MultcheckItem checkItem = param.getCheckItem();
        MultcheckContext context = param.getContext();
        String executeId = CheckResultUtil.toFakeUUID((String)(param.getRunId() + checkItem.getKey())).toString();
        try {
            FmlCheckConfig fmlCheckConfig = SerializeUtil.deserializeFromJson(checkItem.getConfig(), FmlCheckConfig.class);
            this.setResultParam(param, checkItemResult, executeId, fmlCheckConfig);
            Map<String, List<String>> formulas = fmlCheckConfig.getFormulas();
            if (CollectionUtils.isEmpty(formulas)) {
                checkItemResult.setResult(CheckRestultState.SUCCESS);
                checkItemResult.setSuccessOrgs(context.getOrgList());
                return checkItemResult;
            }
            if (param.getAsyncTaskMonitor().isCancel()) {
                return checkItemResult;
            }
            this.runCheck(param, executeId, fmlCheckConfig);
            if (param.getAsyncTaskMonitor().isCancel()) {
                return checkItemResult;
            }
            if (this.nrdbHelper.isEnableNrdb()) {
                this.setCheckErrResult(checkItemResult, context, executeId, fmlCheckConfig);
            } else {
                this.setImpactReportResult(checkItemResult, context, executeId, fmlCheckConfig);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            checkItemResult.setResult(CheckRestultState.WARN);
        }
        return checkItemResult;
    }

    private void setOrgEntity(CheckItemParam param) {
        DsContext dsContext = DsContextHolder.getDsContext();
        if (dsContext instanceof DsContextImpl) {
            DsContextImpl context = (DsContextImpl)dsContext;
            context.setEntityId(param.getContext().getOrg());
        }
    }

    private void setCheckErrResult(CheckItemResult checkItemResult, MultcheckContext context, String executeId, FmlCheckConfig fmlCheckConfig) {
        CheckResultGroup checkResultGroup = this.queryCheckResult(context, executeId, fmlCheckConfig);
        FmlCheckProvider.processItemResult(checkItemResult, context, checkResultGroup);
    }

    private void setImpactReportResult(CheckItemResult checkItemResult, MultcheckContext context, String executeId, FmlCheckConfig fmlCheckConfig) {
        int passSize;
        Map requireAuidtMap = fmlCheckConfig.getCheckRequires().entrySet().stream().collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())));
        List<Integer> writeCkdAuditList = requireAuidtMap.get(CheckRequire.WRITE_DES.getCode());
        List<Integer> mustPassAuditList = requireAuidtMap.get(CheckRequire.MUST_CHECK_PASS.getCode());
        int writeSize = writeCkdAuditList == null ? 0 : writeCkdAuditList.size();
        int n = passSize = mustPassAuditList == null ? 0 : mustPassAuditList.size();
        if (writeSize + passSize == 0) {
            checkItemResult.setResult(CheckRestultState.SUCCESS);
            checkItemResult.setSuccessOrgs(context.getOrgList());
        } else {
            QueryCondition queryCondition = FmlCheckProvider.buildQueryCondition(fmlCheckConfig, writeCkdAuditList, mustPassAuditList);
            CheckResultGroup checkResultGroup = this.queryCheckResult(context, executeId, fmlCheckConfig, queryCondition);
            FmlCheckProvider.processItemResult(checkItemResult, context, checkResultGroup);
            this.updateItemStateIfCKD(checkItemResult, context, executeId, fmlCheckConfig, writeCkdAuditList);
        }
    }

    private void updateItemStateIfCKD(CheckItemResult checkItemResult, MultcheckContext context, String executeId, FmlCheckConfig fmlCheckConfig, @Nullable List<Integer> writeCkdAuditList) {
        int writeSize;
        int n = writeSize = writeCkdAuditList == null ? 0 : writeCkdAuditList.size();
        if (writeSize == 0) {
            return;
        }
        List successOrgs = checkItemResult.getSuccessOrgs();
        CheckResultQueryParam checkResultQueryParam = new CheckResultQueryParam();
        QueryConditionBuilder queryConditionBuilder = new QueryConditionBuilder(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.IN, writeCkdAuditList);
        QueryCondition queryCondition = queryConditionBuilder.build();
        checkResultQueryParam.setQueryCondition(queryCondition);
        checkResultQueryParam.setBatchId(executeId);
        checkResultQueryParam.setFormulaSchemeKeys(Collections.singletonList(fmlCheckConfig.getFormulaSchemeKey()));
        DimensionCollectionBuilder dimensionCollectionBuilder = new DimensionCollectionBuilder();
        String periodDimensionName = this.periodEngineService.getPeriodAdapter().getPeriodDimensionName();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(context.getFormSchemeKey());
        dimensionCollectionBuilder.setEntityValue(periodDimensionName, formScheme.getDateTime(), new Object[]{context.getPeriod()});
        String contextMainDimId = this.getContextMainDimId(formScheme);
        String dwDimensionName = this.entityMetaService.getDimensionName(contextMainDimId);
        dimensionCollectionBuilder.setDWValue(dwDimensionName, contextMainDimId, new Object[]{successOrgs});
        DimensionCollection collection = dimensionCollectionBuilder.getCollection();
        checkResultQueryParam.setDimensionCollection(collection);
        checkResultQueryParam.setQueryByDim(true);
        checkResultQueryParam.setGroupType(GroupType.unit);
        CheckResultGroup checkResultGroup = this.checkResultService.queryBatchCheckResultGroup(checkResultQueryParam);
        if (checkResultGroup != null && !CollectionUtils.isEmpty(checkResultGroup.getGroupData())) {
            if (CheckRestultState.SUCCESS == checkItemResult.getResult()) {
                checkItemResult.setResult(CheckRestultState.SUCCESS_ERROR);
            }
            List successWithExplainOrgs = checkResultGroup.getGroupData().stream().map(CheckResultGroupData::getKey).collect(Collectors.toList());
            ArrayList successOrgsCopy = new ArrayList(successOrgs);
            successOrgsCopy.removeAll(successWithExplainOrgs);
            checkItemResult.setSuccessOrgs(successOrgsCopy);
            checkItemResult.setSuccessWithExplainOrgs(successWithExplainOrgs);
        }
    }

    private void setResultParam(CheckItemParam param, CheckItemResult checkItemResult, String executeId, FmlCheckConfig fmlCheckConfig) throws Exception {
        fmlCheckConfig.setExecuteId(executeId);
        fmlCheckConfig.setBeforeReport(param.isBeforeReport());
        checkItemResult.setRunId(executeId);
        String runConfig = SerializeUtil.serializeToJson(fmlCheckConfig);
        checkItemResult.setRunConfig(runConfig);
    }

    private static void processItemResult(CheckItemResult checkItemResult, MultcheckContext context, CheckResultGroup unitCheckTypeGroup) {
        if (unitCheckTypeGroup != null && !CollectionUtils.isEmpty(unitCheckTypeGroup.getGroupData())) {
            checkItemResult.setResult(CheckRestultState.FAIL);
            LinkedHashMap<String, FailedOrgInfo> failedOrgInfoMap = new LinkedHashMap<String, FailedOrgInfo>();
            for (CheckResultGroupData failOrg : unitCheckTypeGroup.getGroupData()) {
                FailedOrgInfo failedOrgInfo = FmlCheckProvider.getFailedOrgInfo(failOrg);
                failedOrgInfoMap.put(failOrg.getKey(), failedOrgInfo);
            }
            checkItemResult.setFailedOrgs(failedOrgInfoMap);
            ArrayList orgListCopy = new ArrayList(context.getOrgList());
            orgListCopy.removeAll(failedOrgInfoMap.keySet());
            checkItemResult.setSuccessOrgs(orgListCopy);
        } else {
            checkItemResult.setResult(CheckRestultState.SUCCESS);
            checkItemResult.setSuccessOrgs(context.getOrgList());
        }
    }

    @NonNull
    private static FailedOrgInfo getFailedOrgInfo(CheckResultGroupData failOrg) {
        FailedOrgInfo failedOrgInfo = new FailedOrgInfo();
        if (!CollectionUtils.isEmpty(failOrg.getChildren())) {
            StringBuilder failOrgDesc = new StringBuilder("\u5b58\u5728");
            failOrg.getChildren().forEach(o -> failOrgDesc.append(o.getTitle()).append("\u5ba1\u6838\u9519\u8bef").append(o.getCount()).append("\u6761").append("\uff0c"));
            failOrgDesc.setLength(failOrgDesc.length() - 1);
            failedOrgInfo.setDesc(failOrgDesc.toString());
        }
        return failedOrgInfo;
    }

    private CheckResultGroup queryCheckResult(MultcheckContext context, String executeId, FmlCheckConfig fmlCheckConfig) {
        return this.queryCheckResult(context, executeId, fmlCheckConfig, null);
    }

    private CheckResultGroup queryCheckResult(MultcheckContext context, String executeId, FmlCheckConfig fmlCheckConfig, QueryCondition queryCondition) {
        CheckResultQueryParam checkResultQueryParam = new CheckResultQueryParam();
        checkResultQueryParam.setQueryCondition(queryCondition);
        checkResultQueryParam.setBatchId(executeId);
        checkResultQueryParam.setFormulaSchemeKeys(Collections.singletonList(fmlCheckConfig.getFormulaSchemeKey()));
        DimensionCollectionBuilder dimensionCollectionBuilder = new DimensionCollectionBuilder();
        String periodDimensionName = this.periodEngineService.getPeriodAdapter().getPeriodDimensionName();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(context.getFormSchemeKey());
        dimensionCollectionBuilder.setEntityValue(periodDimensionName, formScheme.getDateTime(), new Object[]{context.getPeriod()});
        DimensionCollection collection = dimensionCollectionBuilder.getCollection();
        checkResultQueryParam.setDimensionCollection(collection);
        checkResultQueryParam.setGroupType(GroupType.UNIT_CHECKTYPE);
        return this.checkResultService.queryBatchCheckResultGroup(checkResultQueryParam);
    }

    private static QueryCondition buildQueryCondition(FmlCheckConfig fmlCheckConfig, List<Integer> writeCkdAuditList, List<Integer> mustPassAuditList) {
        DefaultQueryFilter queryCondition;
        int writeSize = writeCkdAuditList == null ? 0 : writeCkdAuditList.size();
        int passSize = mustPassAuditList == null ? 0 : mustPassAuditList.size();
        QueryConditionBuilder queryConditionBuilder = null;
        if (writeSize == 0) {
            if (passSize > 1) {
                queryConditionBuilder = new QueryConditionBuilder(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.IN, mustPassAuditList);
            } else if (passSize == 1) {
                queryConditionBuilder = new QueryConditionBuilder(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.EQUALS, (Object)mustPassAuditList.get(0));
            }
            queryCondition = queryConditionBuilder == null ? DefaultQueryFilter.NO_FILTER : queryConditionBuilder.build();
        } else {
            boolean desCheckPass = fmlCheckConfig.isDesCheckPass();
            for (Integer writeCkdAuditCode : writeCkdAuditList) {
                QueryConditionBuilder subQuery = new QueryConditionBuilder(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.EQUALS, (Object)writeCkdAuditCode);
                if (desCheckPass) {
                    subQuery.andSubQuery((QueryCondition)DefaultQueryFilter.DES_IS_NULL_OR_CHECK_FAIL);
                } else {
                    subQuery.andSubQuery((QueryCondition)DefaultQueryFilter.DES_IS_NULL);
                }
                if (queryConditionBuilder == null) {
                    queryConditionBuilder = new QueryConditionBuilder(subQuery.build());
                    continue;
                }
                queryConditionBuilder.orSubQuery(subQuery.build());
            }
            assert (queryConditionBuilder != null);
            if (passSize > 1) {
                queryConditionBuilder.or(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.IN, mustPassAuditList);
            } else if (passSize == 1) {
                queryConditionBuilder.or(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.EQUALS, (Object)mustPassAuditList.get(0));
            }
            queryCondition = queryConditionBuilder.build();
        }
        return queryCondition;
    }

    private void runCheck(CheckItemParam param, String executeId, FmlCheckConfig fmlCheckConfig) {
        MultcheckContext context = param.getContext();
        CheckParam checkParam = new CheckParam();
        checkParam.setActionId(executeId);
        DimensionCollection dims = context.getDims();
        checkParam.setDimensionCollection(dims);
        checkParam.setFormulaSchemeKey(fmlCheckConfig.getFormulaSchemeKey());
        Map<String, List<String>> formulas = fmlCheckConfig.getFormulas();
        if (formulas.containsKey("ALL-FORM")) {
            checkParam.setMode(Mode.FORM);
            checkParam.setRangeKeys(new ArrayList());
        } else {
            Iterator<Map.Entry<String, List<String>>> iterator = formulas.entrySet().iterator();
            if (iterator.hasNext()) {
                Map.Entry<String, List<String>> entry = iterator.next();
                if (entry.getValue().isEmpty()) {
                    checkParam.setMode(Mode.FORM);
                    checkParam.setRangeKeys(new ArrayList<String>(formulas.keySet()));
                } else {
                    checkParam.setMode(Mode.FORMULA);
                    ArrayList<String> formulaKeys = new ArrayList<String>();
                    for (List<String> value : formulas.values()) {
                        formulaKeys.addAll(value);
                    }
                    checkParam.setRangeKeys(formulaKeys);
                }
            }
        }
        checkParam.setCheckDes(true);
        CheckMonitor checkMonitor = new CheckMonitor(param.getAsyncTaskMonitor());
        this.checkService.batchCheck(checkParam, (IFmlMonitor)checkMonitor);
    }

    public PluginInfo getResultPlugin() {
        return new PluginInfo(PROD_LINE, APP_NAME, RESULT_EXPOSE);
    }

    public MultCheckItemDTO getDefaultCheckItem(String formSchemeKey) {
        MultCheckItemDTO multCheckItemDTO = new MultCheckItemDTO();
        multCheckItemDTO.setType(TYPE);
        multCheckItemDTO.setTitle(TITLE);
        FmlCheckConfig fmlCheckConfig = new FmlCheckConfig();
        FormulaSchemeDefine defaultFormulaSchemeInFormScheme = this.formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(formSchemeKey);
        if (defaultFormulaSchemeInFormScheme == null) {
            return null;
        }
        fmlCheckConfig.setFormulaSchemeKey(defaultFormulaSchemeInFormScheme.getKey());
        HashMap<String, List<String>> formulas = new HashMap<String, List<String>>();
        formulas.put("ALL-FORM", Collections.emptyList());
        fmlCheckConfig.setFormulas(formulas);
        HashMap<Integer, Integer> checkRequires = new HashMap<Integer, Integer>();
        List auditTypes = null;
        try {
            auditTypes = this.auditTypeDefineService.queryAllAuditType();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (!CollectionUtils.isEmpty(auditTypes)) {
            for (AuditType auditType : auditTypes) {
                checkRequires.put(auditType.getCode(), CheckRequire.MUST_CHECK_PASS.getCode());
            }
        }
        fmlCheckConfig.setCheckRequires(checkRequires);
        String confJson = null;
        try {
            confJson = SerializeUtil.serializeToJson(fmlCheckConfig);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        multCheckItemDTO.setConfig(confJson);
        return multCheckItemDTO;
    }

    public String getEntryView() {
        return ENTRY_VIEW;
    }

    public boolean entryAlwaysDisplayView(Map<String, Object> params) {
        return true;
    }

    private String getContextMainDimId(FormSchemeDefine formScheme) {
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        return StringUtils.hasText(entityId) ? entityId : formScheme.getDw();
    }
}

