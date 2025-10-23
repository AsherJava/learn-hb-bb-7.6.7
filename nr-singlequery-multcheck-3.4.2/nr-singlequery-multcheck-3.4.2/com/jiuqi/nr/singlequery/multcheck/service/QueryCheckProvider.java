/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.dataset.report.model.DefaultValueMode
 *  com.jiuqi.bi.dataset.report.model.ReportDsModelDefine
 *  com.jiuqi.bi.dataset.report.model.ReportDsParameter
 *  com.jiuqi.bi.dataset.report.model.ReportExpField
 *  com.jiuqi.bi.dataset.report.model.ReportFieldType
 *  com.jiuqi.bi.dataset.report.query.ReportQueryExecutor
 *  com.jiuqi.bi.dataset.report.remote.controller.vo.PreviewResultVo
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMainDimFilter
 *  com.jiuqi.np.definition.common.FormulaCheckType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datacheck.common.SerializeUtil
 *  com.jiuqi.nr.datacheck.dataanalyze.CheckCondition
 *  com.jiuqi.nr.datacheck.dataanalyze.OrgSelectType
 *  com.jiuqi.nr.datacheck.dataanalyze.vo.OrgStateVO
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.designer.service.StepSaveService
 *  com.jiuqi.nr.designer.web.treebean.TaskLinkObject
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.multcheck2.bean.MCErrorDescription
 *  com.jiuqi.nr.multcheck2.bean.MultcheckItem
 *  com.jiuqi.nr.multcheck2.bean.MultcheckResOrg
 *  com.jiuqi.nr.multcheck2.bean.MultcheckScheme
 *  com.jiuqi.nr.multcheck2.common.CheckRestultState
 *  com.jiuqi.nr.multcheck2.dynamic.IMCErrorInfoService
 *  com.jiuqi.nr.multcheck2.dynamic.IMCItemOrgService
 *  com.jiuqi.nr.multcheck2.provider.CheckItemParam
 *  com.jiuqi.nr.multcheck2.provider.CheckItemResult
 *  com.jiuqi.nr.multcheck2.provider.FailedOrgDimInfo
 *  com.jiuqi.nr.multcheck2.provider.FailedOrgInfo
 *  com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider
 *  com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO
 *  com.jiuqi.nr.multcheck2.provider.PluginInfo
 *  com.jiuqi.nr.multcheck2.service.IMCEnvService
 *  com.jiuqi.nr.multcheck2.service.IMCResultService
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 *  com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 *  com.jiuqi.nvwa.dataanalyze.service.ResourceTreeContext
 *  com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 *  com.jiuqi.util.StringUtils
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.singlequery.multcheck.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.report.model.DefaultValueMode;
import com.jiuqi.bi.dataset.report.model.ReportDsModelDefine;
import com.jiuqi.bi.dataset.report.model.ReportDsParameter;
import com.jiuqi.bi.dataset.report.model.ReportExpField;
import com.jiuqi.bi.dataset.report.model.ReportFieldType;
import com.jiuqi.bi.dataset.report.query.ReportQueryExecutor;
import com.jiuqi.bi.dataset.report.remote.controller.vo.PreviewResultVo;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMainDimFilter;
import com.jiuqi.np.definition.common.FormulaCheckType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datacheck.common.SerializeUtil;
import com.jiuqi.nr.datacheck.dataanalyze.CheckCondition;
import com.jiuqi.nr.datacheck.dataanalyze.OrgSelectType;
import com.jiuqi.nr.datacheck.dataanalyze.vo.OrgStateVO;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.designer.service.StepSaveService;
import com.jiuqi.nr.designer.web.treebean.TaskLinkObject;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.multcheck2.bean.MCErrorDescription;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckResOrg;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.common.CheckRestultState;
import com.jiuqi.nr.multcheck2.dynamic.IMCErrorInfoService;
import com.jiuqi.nr.multcheck2.dynamic.IMCItemOrgService;
import com.jiuqi.nr.multcheck2.provider.CheckItemParam;
import com.jiuqi.nr.multcheck2.provider.CheckItemResult;
import com.jiuqi.nr.multcheck2.provider.FailedOrgDimInfo;
import com.jiuqi.nr.multcheck2.provider.FailedOrgInfo;
import com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider;
import com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO;
import com.jiuqi.nr.multcheck2.provider.PluginInfo;
import com.jiuqi.nr.multcheck2.service.IMCEnvService;
import com.jiuqi.nr.multcheck2.service.IMCResultService;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nr.singlequery.multcheck.checkdes.ISingleQueryCheckDesValidator;
import com.jiuqi.nr.singlequery.multcheck.checkdes.ISingleQueryDesValidatorProvider;
import com.jiuqi.nr.singlequery.multcheck.checkdes.SingleQueryContext;
import com.jiuqi.nr.singlequery.multcheck.service.IQueryCheckService;
import com.jiuqi.nr.singlequery.multcheck.vo.QueryCheckConfig;
import com.jiuqi.nr.singlequery.multcheck.vo.QueryCheckModel;
import com.jiuqi.nr.singlequery.multcheck.vo.SingleQueryErrorInfo;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import com.jiuqi.nvwa.dataanalyze.service.ResourceTreeContext;
import com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.util.StringUtils;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class QueryCheckProvider
implements IMultcheckItemProvider {
    private static final Logger logger = LoggerFactory.getLogger(QueryCheckProvider.class);
    static final String SUCCESS = "TRUE";
    static final String FAILED = "FALSE";
    public static final String ICON_SUCCESS = "#icon-J_HXGLB_C_ZFJS_GreenDot";
    public static final String ICON_SUCCESS_ERROR = "#icon-J_HXGLB_C_ZFJS_YellowDot";
    public static final String ICON_FAILED = "#icon-J_HXGLB_C_ZFJS_RedDot";
    static final String RES_UNIT_SUCCESS = "\u5ba1\u6838\u901a\u8fc7";
    static final String RES_UNIT_SUCCESS1 = "\u5ba1\u6838\u672a\u901a\u8fc7\u3010\u5b58\u5728\u8bf4\u660e\u3011";
    static final String RES_UNIT_FAILED1 = "\u5ba1\u6838\u672a\u901a\u8fc7\u3010\u6ca1\u6709\u8bf4\u660e\u3011";
    static final String RES_UNIT_FAILED = "\u5ba1\u6838\u672a\u901a\u8fc7";
    static final String MODEL_COLOR_SUCCESS = "#5fb55f";
    static final String MODEL_COLOR_SUCCESS1 = "#ff9902";
    static final String MODEL_COLOR_FAILED = "red";
    private static final String DESCRIBE = "\u4e2a\u6a21\u677f\u6ca1\u6709\u89e3\u91ca\u8bf4\u660e";
    private static final String DESCRIBE_NEEDPASS = "\u4e2a\u6a21\u7248\u5ba1\u6838\u4e0d\u901a\u8fc7";
    static final String TYPE = "querycheck";
    private final String ENCRYFMLPREFIX = "ENCRY:";
    @Autowired
    IMCErrorInfoService errorInfoService;
    @Autowired
    IMCItemOrgService orgService;
    @Autowired
    IMCResultService resultService;
    @Autowired
    IQueryCheckService checkService;
    @Autowired
    IMCEnvService envService;
    @Autowired
    private ResourceTreeNodeService resourceTreeNodeService;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired(required=false)
    private List<ISingleQueryDesValidatorProvider> checkDesValidatorProviders;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private StepSaveService stepSaveService;
    @Autowired
    private ReportQueryExecutor executor;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getType() {
        return TYPE;
    }

    public String getTitle() {
        return "\u6a2a\u5411\u8fc7\u5f55\u8868\u67e5\u8be2";
    }

    public double getOrder() {
        return 100.0;
    }

    public PluginInfo getPropertyPluginInfo() {
        return new PluginInfo("@nr", "nr-singlequery-multcheck", "Config");
    }

    public String getItemDescribe(String formSchemeKey, MultcheckItem item) {
        if (!org.springframework.util.StringUtils.hasText(item.getConfig())) {
            return "\u9009\u62e9\u6a21\u677f | 0\u4e2a";
        }
        try {
            QueryCheckConfig config = this.getConfig(item.getConfig());
            boolean count = false;
            List<QueryCheckModel> models = config.getModels();
            return "\u9009\u62e9\u6a21\u677f | <span class=\"mtc-item-number-cls\">" + models.size() + "</span>\u4e2a";
        }
        catch (Exception e) {
            logger.error("\u7efc\u5408\u5ba1\u6838\uff1a\u6a2a\u5411\u8fc7\u5f55\u8868\u6a21\u578b\u5f02\u5e38" + item.getKey(), e);
            return null;
        }
    }

    private QueryCheckConfig getConfig(String configStr) {
        try {
            return (QueryCheckConfig)SerializeUtil.deserializeFromJson((String)configStr, QueryCheckConfig.class);
        }
        catch (Exception e) {
            try {
                JSONObject json = new JSONObject(configStr);
                QueryCheckConfig config = new QueryCheckConfig();
                Boolean needErrorDesc = json.getBoolean("needErrorDesc");
                ArrayList<QueryCheckModel> modelList = new ArrayList<QueryCheckModel>();
                config.setCheckRequires(needErrorDesc != false ? CheckCondition.NEEDERROR : CheckCondition.NEEDPASS);
                config.setModels(modelList);
                JSONArray models = json.getJSONArray("models");
                for (int i = 0; i < models.length(); ++i) {
                    JSONObject model = models.getJSONObject(i);
                    modelList.add((QueryCheckModel)SerializeUtil.deserializeFromJson((String)model.toString(), QueryCheckModel.class));
                }
                return config;
            }
            catch (Exception ee) {
                logger.error("\u7efc\u5408\u5ba1\u6838\uff1a\uff1a\u6a2a\u5411\u8fc7\u5f55\u8868\u6a21\u578b\u5f02\u5e38:" + configStr, e);
                return null;
            }
        }
    }

    private ResourceTreeContext buildContext() {
        ResourceTreeContext context = new ResourceTreeContext();
        context.setUserId(NpContextHolder.getContext().getIdentityId());
        context.setPrivilegeId("dataanalysis_read");
        return context;
    }

    private List<QueryCheckModel> getAuthModel(List<QueryCheckModel> models) throws DataAnalyzeResourceException {
        ArrayList<QueryCheckModel> res = new ArrayList<QueryCheckModel>();
        for (QueryCheckModel model : models) {
            ResourceTreeNode node = this.resourceTreeNodeService.get(this.buildContext(), model.getKey());
            if (node == null) continue;
            res.add(model);
        }
        return res;
    }

    public MultCheckItemDTO copyCheckItem(MultcheckScheme sourceScheme, MultcheckItem sourceItem, String targetFormSchemeKey) {
        MultCheckItemDTO dto = new MultCheckItemDTO();
        BeanUtils.copyProperties(sourceItem, dto);
        return dto;
    }

    public boolean canChangeConfig() {
        return false;
    }

    public String getRunItemDescribe(String formSchemeKey, MultcheckItem item) {
        return this.getItemDescribe(formSchemeKey, item);
    }

    public PluginInfo getRunPropertyPluginInfo() {
        return new PluginInfo("@nr", "nr-singlequery-multcheck", "Selector");
    }

    public CheckItemResult runCheck(CheckItemParam param) {
        try {
            QueryCheckConfig config = this.getConfig(param.getCheckItem().getConfig());
            if (config.getCheckRequires() == null) {
                config.setCheckRequires(CheckCondition.NEEDPASS);
            }
            List<QueryCheckModel> models = config.getModels();
            ArrayList<Formula> formulasCurrent = new ArrayList<Formula>();
            ArrayList<Formula> formulasDirect = new ArrayList<Formula>();
            ArrayList<Formula> formulasAll = new ArrayList<Formula>();
            ArrayList<String> noFmlModels = new ArrayList<String>();
            HashMap<Formula, OrgSelectType> floatFormula = new HashMap<Formula, OrgSelectType>();
            HashMap<String, Map<String, Boolean>> formFieldIsFloatMap = new HashMap<String, Map<String, Boolean>>();
            HashMap<String, QueryCheckModel> fmlModelMap = new HashMap<String, QueryCheckModel>();
            for (QueryCheckModel model : models) {
                if (org.springframework.util.StringUtils.hasText(model.getFml()) && !model.getFml().startsWith("//")) {
                    Formula formula = new Formula();
                    formula.setId(model.getKey());
                    formula.setFormula(model.getFml().length() >= 6 && "ENCRY:".equals(model.getFml().substring(0, 6)) ? this.decryptFml(model.getFml()) : model.getFml());
                    if (!org.springframework.util.StringUtils.hasText(formula.getFormula())) {
                        noFmlModels.add(model.getKey());
                        continue;
                    }
                    if (this.judgmentFloat(model.getFml(), param.getContext().getFormSchemeKey(), formFieldIsFloatMap)) {
                        floatFormula.put(formula, model.getOrgSelectType());
                        fmlModelMap.put(model.getKey(), model);
                        continue;
                    }
                    formula.setChecktype(Integer.valueOf(FormulaCheckType.FORMULA_CHECK_ERROR.getValue()));
                    if (config.getUnitRange() == OrgSelectType.UCURRENT.value()) {
                        formulasCurrent.add(formula);
                    } else if (config.getUnitRange() == OrgSelectType.UCURRENTDIRECTSUB.value()) {
                        formulasDirect.add(formula);
                    } else {
                        formulasAll.add(formula);
                    }
                } else {
                    noFmlModels.add(model.getKey());
                }
                fmlModelMap.put(model.getKey(), model);
            }
            if (param.getAsyncTaskMonitor().isCancel()) {
                return null;
            }
            logger.info("\u5ba1\u6838\u9879\u3010\u6a2a\u5411\u8fc7\u5f55\u8868\u3011\u6267\u884c\u5ba1\u6838\u516c\u5f0f\uff1a" + models.size());
            List orgList = param.getContext().getOrgList();
            HashMap<String, List<String>> checkFmlRes = new HashMap<String, List<String>>();
            IEntityTable entityTableAll = this.checkFml(formulasCurrent, formulasDirect, formulasAll, floatFormula, param, checkFmlRes);
            HashMap<String, Set<String>> errorModelOrgRes = new HashMap<String, Set<String>>();
            HashMap<String, Set<String>> orgCurrent = new HashMap<String, Set<String>>();
            if (!CollectionUtils.isEmpty(checkFmlRes)) {
                HashMap<String, List<String>> OrgAllMap = new HashMap<String, List<String>>();
                HashMap<String, List<String>> OrgDirectMap = new HashMap<String, List<String>>();
                for (Map.Entry el : checkFmlRes.entrySet()) {
                    List<String> children;
                    QueryCheckModel model = (QueryCheckModel)fmlModelMap.get(el.getKey());
                    String modelKey = (String)el.getKey();
                    HashSet<String> errorOrgs = new HashSet<String>((Collection)el.getValue());
                    if (config.getUnitRange() == OrgSelectType.UCURRENT.value()) {
                        errorModelOrgRes.put(modelKey, errorOrgs);
                        continue;
                    }
                    if (config.getUnitRange() == OrgSelectType.UCURRENTDIRECTSUB.value()) {
                        if (entityTableAll == null) {
                            System.out.println("entityTableAll == null");
                            entityTableAll = this.getEntityTable(param, null);
                        }
                        for (String org : orgList) {
                            children = this.getChildren(OrgDirectMap, entityTableAll, org);
                            this.checkHasError(errorModelOrgRes, modelKey, errorOrgs, org, children, orgCurrent);
                        }
                        continue;
                    }
                    if (entityTableAll == null) {
                        System.out.println("entityTableAll == null");
                        entityTableAll = this.getEntityTable(param, null);
                    }
                    for (String org : orgList) {
                        children = this.getAllChildren(OrgAllMap, entityTableAll, org);
                        this.checkHasError(errorModelOrgRes, modelKey, errorOrgs, org, children, orgCurrent);
                    }
                }
            }
            HashSet allOrgSet = null;
            HashSet<String> successWithExplainOrgs = new HashSet<String>();
            String task = param.getContext().getTaskKey();
            String period = param.getContext().getPeriod();
            ArrayList<MultcheckResOrg> resOrgs = new ArrayList<MultcheckResOrg>();
            HashMap<String, FailedOrgInfo> failedOrgs = new HashMap<String, FailedOrgInfo>();
            boolean hasDataAndNoDesc = false;
            boolean hasDataAndHasWrongDesc = false;
            boolean hasDataAndHasRightDesc = false;
            CheckRestultState restultState = CheckRestultState.SUCCESS;
            boolean isNeedError = config.getCheckRequires() == CheckCondition.NEEDERROR;
            List dimensionCombinations = param.getContext().getDims().getDimensionCombinations();
            List dimsList = this.resultService.getDynamicFieldsByTask(task);
            for (QueryCheckModel model : models) {
                Set checkErrorOrgs = (Set)errorModelOrgRes.get(model.getKey());
                Map<Object, Object> orgErrorMap = new HashMap();
                if (isNeedError) {
                    List errors = this.errorInfoService.getByResource(task, period, this.getType(), model.getKey());
                    orgErrorMap = this.buildOrgErrorMap(orgList, errors);
                    if (noFmlModels.contains(model.getKey())) {
                        if (allOrgSet == null) {
                            allOrgSet = new HashSet(orgList);
                        }
                        checkErrorOrgs = allOrgSet;
                    }
                }
                for (DimensionCombination dimensionCombination : dimensionCombinations) {
                    String org = (String)dimensionCombination.getDWDimensionValue().getValue();
                    MultcheckResOrg orgRes = this.initResOrg(param, resOrgs, model, org);
                    OrgStateVO orgStateVO = new OrgStateVO();
                    HashMap<String, String> currentDims = new HashMap<String, String>();
                    String currentDimStr = this.buildCurrentDim(dimsList, dimensionCombination, currentDims, orgRes);
                    if (!CollectionUtils.isEmpty(checkErrorOrgs) && checkErrorOrgs.contains(org)) {
                        List mcErrorDescriptions = (List)orgErrorMap.get(org);
                        if (CollectionUtils.isEmpty(mcErrorDescriptions)) {
                            orgStateVO.setErrorRes(false);
                            orgStateVO.setOrgRes(false);
                            if (CollectionUtils.isEmpty(dimsList)) {
                                this.buildFailedOrgInfo(failedOrgs, org, isNeedError);
                            } else {
                                this.buildFailedOrgDimInfo(failedOrgs, dimsList, org, currentDims, currentDimStr);
                            }
                            hasDataAndNoDesc = true;
                        } else if (CollectionUtils.isEmpty(dimsList)) {
                            if (param.isBeforeReport()) {
                                this.checkErrorDes(param, orgStateVO, mcErrorDescriptions, failedOrgs, successWithExplainOrgs);
                            } else {
                                orgStateVO.setErrorRes(true);
                                orgStateVO.setOrgRes(true);
                                this.checkErrorDes(param, orgStateVO, mcErrorDescriptions, failedOrgs, successWithExplainOrgs);
                                if (orgStateVO.isErrorRes()) {
                                    hasDataAndHasRightDesc = true;
                                } else {
                                    hasDataAndHasWrongDesc = true;
                                }
                            }
                        } else {
                            boolean sameDim = this.hasSameDim(dimsList, currentDimStr, mcErrorDescriptions);
                            if (!sameDim) {
                                orgStateVO.setErrorRes(false);
                                orgStateVO.setOrgRes(false);
                                this.buildFailedOrgDimInfo(failedOrgs, dimsList, org, currentDims, currentDimStr);
                            } else {
                                if (param.isBeforeReport()) {
                                    this.checkErrorDes(param, orgStateVO, mcErrorDescriptions, failedOrgs, successWithExplainOrgs);
                                } else {
                                    orgStateVO.setErrorRes(true);
                                    orgStateVO.setOrgRes(true);
                                }
                                this.checkErrorDes(param, orgStateVO, mcErrorDescriptions, failedOrgs, successWithExplainOrgs);
                                if (orgStateVO.isErrorRes()) {
                                    hasDataAndHasRightDesc = true;
                                } else {
                                    hasDataAndHasWrongDesc = true;
                                }
                            }
                        }
                    } else {
                        orgStateVO.setFmlRes(true);
                        orgStateVO.setOrgRes(true);
                    }
                    orgRes.setResult(orgStateVO.getRes());
                }
            }
            this.orgService.batchSave(resOrgs, dimsList, task);
            List<Object> successOrgs = new ArrayList();
            if (failedOrgs.size() == 0) {
                successOrgs.addAll(orgList);
            } else if (orgList.size() > failedOrgs.size()) {
                successOrgs = orgList.stream().filter(o -> !failedOrgs.containsKey(o)).collect(Collectors.toList());
            }
            if (hasDataAndNoDesc) {
                restultState = CheckRestultState.FAIL;
            } else if (hasDataAndHasWrongDesc) {
                restultState = CheckRestultState.WARN;
            } else if (hasDataAndHasRightDesc) {
                restultState = CheckRestultState.SUCCESS_ERROR;
            }
            CheckItemResult result = new CheckItemResult();
            ArrayList successWithExplainOrgList = new ArrayList(successWithExplainOrgs);
            successOrgs.removeAll(successWithExplainOrgList);
            result.setRunId(param.getRunId());
            result.setResult(restultState);
            result.setSuccessOrgs(successOrgs);
            result.setFailedOrgs(failedOrgs);
            result.setSuccessWithExplainOrgs(new ArrayList(successWithExplainOrgs));
            result.setRunConfig(param.getCheckItem().getConfig());
            this.saveSumUnitRecord(param.getRunId(), orgCurrent);
            return result;
        }
        catch (Exception e) {
            logger.error("\u7efc\u5408\u5ba1\u6838\uff1a\uff1a\u6a2a\u5411\u8fc7\u5f55\u8868\u6a21\u578b\u5f02\u5e38" + param.getCheckItem().getKey(), e);
            return null;
        }
    }

    private void saveSumUnitRecord(String key, Map<String, Set<String>> orgCurrent) {
        String sql = "INSERT INTO SYS_SINGLE_QUERY_MULTCHECK (SQM_KEY, SQM_RESULT, SQM_UPDATETIME) VALUES (?, ?, ?)";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.deleteResults();
            String jsonResult = objectMapper.writeValueAsString(orgCurrent);
            this.jdbcTemplate.update(sql, new Object[]{key, jsonResult, new Timestamp(System.currentTimeMillis())});
        }
        catch (Exception e) {
            logger.error("Failed to insert data into SYS_SINGLE_QUERY_MULTCHECK", e);
        }
    }

    private void deleteResults() {
        Calendar c = Calendar.getInstance();
        c.add(13, -1296000);
        Date dateBefore = c.getTime();
        String sql = "DELETE FROM SYS_SINGLE_QUERY_MULTCHECK WHERE SQM_UPDATETIME <= ?";
        this.jdbcTemplate.update(sql, pss -> pss.setTimestamp(1, new Timestamp(dateBefore.getTime())));
    }

    private void checkErrorDes(CheckItemParam param, OrgStateVO orgStateVO, List<MCErrorDescription> mcErrorDescriptions, Map<String, FailedOrgInfo> failedOrgs, Set<String> successWithExplainOrgs) {
        ArrayList<SingleQueryErrorInfo> singleQueryErrors = new ArrayList<SingleQueryErrorInfo>();
        for (MCErrorDescription source : mcErrorDescriptions) {
            SingleQueryErrorInfo target = new SingleQueryErrorInfo();
            BeanUtils.copyProperties(source, (Object)target);
            singleQueryErrors.add(target);
        }
        if (this.errorDesCheck(singleQueryErrors, param.getCheckItem().getKey())) {
            orgStateVO.setErrorRes(true);
            orgStateVO.setOrgRes(true);
            if (!successWithExplainOrgs.contains(((SingleQueryErrorInfo)((Object)singleQueryErrors.get(0))).getOrg())) {
                successWithExplainOrgs.add(((SingleQueryErrorInfo)((Object)singleQueryErrors.get(0))).getOrg());
            }
        } else {
            this.buildFailedOrgInfoBeforeReport(failedOrgs, mcErrorDescriptions.get(0).getOrg());
            orgStateVO.setErrorRes(false);
            orgStateVO.setOrgRes(false);
        }
    }

    private void buildFailedOrgInfoBeforeReport(Map<String, FailedOrgInfo> failedOrgs, String org) {
        FailedOrgInfo failedOrgInfo = failedOrgs.get(org);
        if (failedOrgInfo == null) {
            failedOrgInfo = new FailedOrgInfo();
            failedOrgs.put(org, failedOrgInfo);
            failedOrgInfo.setDesc("\u51fa\u9519\u8bf4\u660e\u4e0d\u7b26\u5408\u89c4\u5219\uff0c\u8bf7\u68c0\u67e5");
        } else {
            failedOrgInfo.setDesc("\u51fa\u9519\u8bf4\u660e\u4e0d\u7b26\u5408\u89c4\u5219\uff0c\u8bf7\u68c0\u67e5");
        }
    }

    public boolean errorDesCheck(List<SingleQueryErrorInfo> errorInfos, String itemKey) {
        if (this.checkDesValidatorProviders != null && this.checkDesValidatorProviders.size() > 0) {
            List<ISingleQueryCheckDesValidator> checkDesValidators = this.getCheckDesValidators(itemKey);
            for (SingleQueryErrorInfo error : errorInfos) {
                if (StringUtils.isEmpty((String)error.getDescription())) continue;
                for (ISingleQueryCheckDesValidator validator : checkDesValidators) {
                    if (validator.validate(error)) continue;
                    return false;
                }
            }
        }
        return true;
    }

    @NotNull
    private List<ISingleQueryCheckDesValidator> getCheckDesValidators(String itemKey) {
        ArrayList<ISingleQueryCheckDesValidator> checkDesValidators = new ArrayList<ISingleQueryCheckDesValidator>();
        for (ISingleQueryDesValidatorProvider checkDesValidatorProvider : this.checkDesValidatorProviders) {
            ISingleQueryCheckDesValidator validator = checkDesValidatorProvider.getValidator(new SingleQueryContext(itemKey));
            if (validator == null) continue;
            checkDesValidators.add(validator);
        }
        return checkDesValidators;
    }

    private List<String> getAllChildren(Map<String, List<String>> OrgAllMap, IEntityTable entityTableAll, String org) {
        List<String> children = OrgAllMap.get(org);
        if (children == null) {
            List childrenRows = entityTableAll.getAllChildRows(org);
            children = CollectionUtils.isEmpty(childrenRows) ? new ArrayList<String>() : childrenRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
            OrgAllMap.put(org, children);
        }
        return children;
    }

    private List<String> getChildren(Map<String, List<String>> OrgDirectMap, IEntityTable entityTableAll, String org) {
        List<String> children = OrgDirectMap.get(org);
        if (children == null) {
            List childrenRows = entityTableAll.getChildRows(org);
            children = CollectionUtils.isEmpty(childrenRows) ? new ArrayList<String>() : childrenRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
            OrgDirectMap.put(org, children);
        }
        return children;
    }

    private void checkHasError(Map<String, Set<String>> errorModelOrgMap, String modelKey, Set<String> errorOrgs, String org, List<String> children, Map<String, Set<String>> orgCurrent) {
        if (errorOrgs.contains(org)) {
            Set<String> errorOrgList = errorModelOrgMap.get(modelKey);
            if (errorOrgList == null) {
                errorOrgList = new HashSet<String>();
                errorModelOrgMap.put(modelKey, errorOrgList);
            }
            errorOrgList.add(org);
        } else if (!CollectionUtils.isEmpty(children)) {
            Set<String> errorCurrentOrgList = orgCurrent.get(modelKey);
            if (errorCurrentOrgList == null) {
                errorCurrentOrgList = new HashSet<String>();
                orgCurrent.put(modelKey, errorCurrentOrgList);
            }
            errorCurrentOrgList.add(org);
            for (String child : children) {
                if (!errorOrgs.contains(child)) continue;
                Set<String> errorOrgList = errorModelOrgMap.get(modelKey);
                if (errorOrgList == null) {
                    errorOrgList = new HashSet<String>();
                    errorModelOrgMap.put(modelKey, errorOrgList);
                }
                errorOrgList.add(org);
                break;
            }
        }
    }

    private IEntityTable checkFml(List<Formula> formulasCurrent, List<Formula> formulasDirect, List<Formula> formulasAll, Map<Formula, OrgSelectType> floatFormula, CheckItemParam param, Map<String, List<String>> checkFmlRes) throws Exception {
        Iterator res;
        List allChildRows;
        IEntityTable entityTableAll = null;
        long t = System.currentTimeMillis();
        List orgList = param.getContext().getOrgList();
        if (!CollectionUtils.isEmpty(formulasCurrent)) {
            Map<String, List<String>> res2 = this.doCheckFml(formulasCurrent, orgList, param);
            checkFmlRes.putAll(res2);
            logger.info("\u5ba1\u6838\u9879\u3010\u6a2a\u5411\u8fc7\u5f55\u8868\u3011\u5f53\u524d\u5355\u4f4d\u516c\u5f0f=" + formulasCurrent.size() + "\u6267\u884c\u65f6\u95f4=" + (System.currentTimeMillis() - t) + "\u6267\u884c\u5355\u4f4d=" + orgList.size());
            t = System.currentTimeMillis();
        }
        if (!CollectionUtils.isEmpty(formulasDirect)) {
            if (entityTableAll == null) {
                entityTableAll = this.getEntityTable(param, null);
            }
            HashSet<String> checkOrgs = new HashSet<String>();
            for (String string : orgList) {
                checkOrgs.add(string);
                List childRows = entityTableAll.getChildRows(string);
                if (CollectionUtils.isEmpty(childRows)) continue;
                checkOrgs.addAll(childRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toSet()));
            }
            Map<String, List<String>> res3 = this.doCheckFml(formulasDirect, new ArrayList<String>(checkOrgs), param);
            checkFmlRes.putAll(res3);
            logger.info("\u5ba1\u6838\u9879\u3010\u6a2a\u5411\u8fc7\u5f55\u8868\u3011\u76f4\u63a5\u4e0b\u7ea7\u5355\u4f4d\u516c\u5f0f=" + formulasDirect.size() + "\u6267\u884c\u65f6\u95f4=" + (System.currentTimeMillis() - t) + "\u6267\u884c\u5355\u4f4d=" + checkOrgs.size());
            t = System.currentTimeMillis();
        }
        if (!CollectionUtils.isEmpty(formulasAll)) {
            IEntityTable entityTable = this.getEntityTable(param, orgList);
            List rootRows = entityTable.getRootRows();
            if (entityTableAll == null) {
                entityTableAll = this.getEntityTable(param, null);
            }
            HashSet<String> hashSet = new HashSet<String>();
            for (IEntityRow row : rootRows) {
                hashSet.add(row.getEntityKeyData());
                allChildRows = entityTableAll.getAllChildRows(row.getEntityKeyData());
                if (CollectionUtils.isEmpty(allChildRows)) continue;
                hashSet.addAll(allChildRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toSet()));
            }
            res = this.doCheckFml(formulasAll, new ArrayList<String>(hashSet), param);
            checkFmlRes.putAll((Map<String, List<String>>)((Object)res));
            logger.info("\u5ba1\u6838\u9879\u3010\u6a2a\u5411\u8fc7\u5f55\u8868\u3011\u6240\u6709\u4e0b\u7ea7\u5355\u4f4d\u516c\u5f0f=" + formulasAll.size() + "\u6267\u884c\u65f6\u95f4=" + (System.currentTimeMillis() - t) + "\u6267\u884c\u5355\u4f4d=" + hashSet.size());
            t = System.currentTimeMillis();
        }
        if (!CollectionUtils.isEmpty(floatFormula.keySet())) {
            if (entityTableAll == null) {
                entityTableAll = this.getEntityTable(param, null);
            }
            ArrayList<String> orgCheckList = null;
            for (Map.Entry entry : floatFormula.entrySet()) {
                if (orgCheckList == null) {
                    orgCheckList = new ArrayList<String>();
                    if (entry.getValue() == OrgSelectType.UCURRENT) {
                        orgCheckList.addAll(param.getContext().getOrgList());
                    } else if (entry.getValue() == OrgSelectType.UCURRENTDIRECTSUB) {
                        for (String org : orgList) {
                            orgCheckList.add(org);
                            List childRows = entityTableAll.getChildRows(org);
                            if (CollectionUtils.isEmpty(childRows)) continue;
                            orgCheckList.addAll(childRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toSet()));
                        }
                    } else {
                        orgCheckList.addAll(orgList);
                        for (String code : orgList) {
                            allChildRows = entityTableAll.getAllChildRows(code);
                            if (CollectionUtils.isEmpty(allChildRows)) continue;
                            orgCheckList.addAll(allChildRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toSet()));
                        }
                    }
                }
                res = this.doCheckFmlEx((Formula)entry.getKey(), orgCheckList, param);
                checkFmlRes.putAll((Map<String, List<String>>)((Object)res));
                logger.info("\u5ba1\u6838\u9879\u3010\u6a2a\u5411\u8fc7\u5f55\u8868\u3011\u6240\u6709\u4e0b\u7ea7\u5355\u4f4d\u516c\u5f0f=" + ((Formula)entry.getKey()).getFormula() + "\u6267\u884c\u65f6\u95f4=" + (System.currentTimeMillis() - t) + "\u6267\u884c\u5355\u4f4d=" + orgCheckList.size());
            }
        }
        return entityTableAll;
    }

    private IEntityTable getEntityTable(CheckItemParam param, List<String> orgList) throws Exception {
        String taskKey = param.getContext().getTaskKey();
        String period = param.getContext().getPeriod();
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
        EntityViewDefine entityView = this.runtimeView.getViewByTaskDefineKey(taskKey);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        if (!CollectionUtils.isEmpty(orgList)) {
            String dimensionName = this.entityMetaService.queryEntity(taskDefine.getDw()).getDimensionName();
            dimensionValueSet.setValue(dimensionName, orgList);
        }
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.sorted(true);
        query.setEntityView(entityView);
        query.setMasterKeys(dimensionValueSet);
        query.setAuthorityOperations(AuthorityType.Read);
        query.sortedByQuery(false);
        SchemePeriodLinkDefine scheme = this.runtimeView.querySchemePeriodLinkByPeriodAndTask(period, taskKey);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, scheme.getSchemeKey());
        context.setEnv((IFmlExecEnvironment)environment);
        context.setVarDimensionValueSet(dimensionValueSet);
        context.setPeriodView(taskDefine.getDateTime());
        return query.executeFullBuild((IContext)context);
    }

    private Map<String, List<String>> doCheckFml(List<Formula> formulas, List<String> orgList, CheckItemParam param) throws Exception {
        long t = System.currentTimeMillis();
        IMainDimFilter mainDimFilter = this.dataAccessProvider.newMainDimFilter();
        EntityViewDefine dwEntityView = this.runtimeView.getViewByFormSchemeKey(param.getContext().getFormSchemeKey());
        String dimensionName = this.entityMetaService.getDimensionName(dwEntityView.getEntityId());
        System.out.println("1doCheckFml=" + (System.currentTimeMillis() - t));
        t = System.currentTimeMillis();
        com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = new com.jiuqi.np.dataengine.executors.ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, param.getContext().getFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        System.out.println("2doCheckFml=" + (System.currentTimeMillis() - t));
        t = System.currentTimeMillis();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)param.getContext().getDims());
        System.out.println("3doCheckFml=" + (System.currentTimeMillis() - t));
        t = System.currentTimeMillis();
        DimensionValueSet dimensionTemp = new DimensionValueSet(dimensionValueSet);
        dimensionTemp.setValue(dimensionName, orgList);
        System.out.println("4doCheckFml=" + (System.currentTimeMillis() - t));
        return mainDimFilter.filterByFormulas(executorContext, dimensionTemp, formulas);
    }

    private Map<String, List<String>> doCheckFmlEx(Formula formula, List<String> codes, CheckItemParam param) throws Exception {
        HashMap<String, List<String>> result = new HashMap<String, List<String>>();
        ArrayList<String> expList = new ArrayList<String>();
        Map<String, String> taskInfo = this.getTaskInfo(param.getContext().getTaskKey(), param.getContext().getFormSchemeKey(), expList);
        ReportDsModelDefine reportDsModelDefine = this.createReportDsModelDefine(expList, codes, formula.getFormula(), taskInfo, param);
        PreviewResultVo preview = this.executor.preview(reportDsModelDefine, -1, 0);
        ArrayList<String> resultCodes = new ArrayList<String>();
        for (Object[] obs : preview.getResult()) {
            String code = obs[0].toString();
            if (resultCodes.contains(code) || obs.length <= 1 || !"true".equals(obs[1].toString())) continue;
            resultCodes.add(code);
        }
        result.put(formula.getId(), resultCodes);
        return result;
    }

    Map<String, String> getTaskInfo(String taskKey, String formSchemeKey, List<String> expList) throws Exception {
        HashMap<String, String> parms = new HashMap<String, String>();
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
        expList.add(String.format("%s[CODE]", taskDefine.getDw().split("@")[0]));
        List formSchemeDefines = this.runtimeView.queryFormSchemeByTask(taskKey);
        DataScheme dataScheme = this.iRuntimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String prefix = dataScheme.getPrefix();
        ArrayList taskLinkObjBySchemeKey = this.stepSaveService.getTaskLinkObjBySchemeKey(((FormSchemeDefine)formSchemeDefines.get(0)).getKey());
        if (!taskLinkObjBySchemeKey.isEmpty()) {
            FormSchemeDefine relateFormScheme = this.runtimeView.getFormScheme(((TaskLinkObject)taskLinkObjBySchemeKey.get(0)).getRelatedFormSchemeKey());
            if (null != relateFormScheme) {
                TaskDefine relateTaskDefine = this.runtimeView.queryTaskDefine(relateFormScheme.getTaskKey());
                DataScheme relateDataScheme = this.iRuntimeDataSchemeService.getDataScheme(relateTaskDefine.getDataScheme());
                String relatePrefix = relateDataScheme.getPrefix();
                parms.put("relatePrefix", relatePrefix);
            } else {
                parms.put("relatePrefix", "RELATE_PERFIX");
            }
        } else {
            parms.put("relatePrefix", "RELATE_PERFIX");
        }
        parms.put("perfix", prefix);
        parms.put("reportScheme", ((FormSchemeDefine)formSchemeDefines.get(0)).getKey());
        parms.put("fromPeriod", taskDefine.getFromPeriod());
        parms.put("org", taskDefine.getDw().split("@")[0]);
        parms.put("dw", taskDefine.getDw());
        parms.put("data", taskDefine.getDateTime());
        parms.put("reportTask", taskDefine.getKey());
        parms.put("schemeKey", dataScheme.getKey());
        if (org.springframework.util.StringUtils.hasText(formSchemeKey)) {
            boolean isFind = false;
            List schemePeriodLinkDefineList = this.runtimeView.querySchemePeriodLinkByTask(taskKey);
            for (SchemePeriodLinkDefine define : schemePeriodLinkDefineList) {
                if (!formSchemeKey.equals(define.getSchemeKey())) continue;
                parms.put("period", define.getPeriodKey());
                isFind = true;
                break;
            }
            if (!isFind) {
                for (SchemePeriodLinkDefine define : formSchemeDefines) {
                    if (!formSchemeKey.equals(define.getKey())) continue;
                    parms.put("period", define.getFromPeriod().replace("N0001", "0101"));
                    isFind = true;
                    break;
                }
            }
            if (!isFind) {
                parms.put("period", taskDefine.getFromPeriod().replace("N0001", "0101"));
            }
        } else {
            parms.put("period", taskDefine.getFromPeriod().replace("N0001", "0101"));
        }
        return parms;
    }

    ReportExpField judgmentType(String code, Map<String, String> taskInfo, ReportExpField reportExpField) {
        reportExpField.setExp(code);
        if (code.contains("MD_ORG")) {
            reportExpField.setDataType(6);
            if (code.contains("CODE") || code.contains("NAME") || code.contains("PARENTCODE") || code.contains("ORGCODE") || code.contains("ORDINAL")) {
                reportExpField.setFieldType(FieldType.GENERAL_DIM);
                reportExpField.setReportFieldType(ReportFieldType.UNIT);
                return reportExpField;
            }
            reportExpField.setFieldType(FieldType.DESCRIPTION);
            return reportExpField;
        }
        return reportExpField;
    }

    private ReportExpField createReportExpField(String exp, Map<String, String> taskInfo, Integer index) {
        ReportExpField reportExpField = new ReportExpField();
        reportExpField.setCode(String.format("t_%d", index));
        reportExpField.setTitle(reportExpField.getCode());
        this.judgmentType(exp, taskInfo, reportExpField);
        return reportExpField;
    }

    private ReportDsModelDefine createReportDsModelDefine(List<String> expList, List<String> codes, String filter, Map<String, String> taskInfo, CheckItemParam param) {
        ReportDsModelDefine reportDsModelDefine = new ReportDsModelDefine();
        Integer index = 0;
        for (String exp : expList) {
            Integer n = index;
            Integer n2 = index = Integer.valueOf(index + 1);
            reportDsModelDefine.getFields().add(this.createReportExpField(exp, taskInfo, n));
        }
        Integer n = index;
        Integer n3 = index = Integer.valueOf(index + 1);
        reportDsModelDefine.getFields().add(this.createReportExpField(filter, taskInfo, n));
        if (taskInfo.containsKey("hasFloat") && taskInfo.containsKey("hasZb") && Boolean.valueOf(taskInfo.get("hasFloat")).booleanValue() && Boolean.valueOf(taskInfo.get("hasZb")).booleanValue()) {
            logger.info("\u67e5\u8be2\u6a21\u677f\u4e2d\u6709\u5f53\u524d\u5e74\u5ea6\u7684\u6d6e\u52a8\u8868\u6307\u6807\u65f6\uff0c\u4e0d\u80fd\u540c\u65f6\u67e5\u8be2\u4e0a\u5e74\u5ea6\u6307\u6807");
        }
        reportDsModelDefine.setReportTaskKey(taskInfo.get("reportTask"));
        this.setReportDsParameters(taskInfo, reportDsModelDefine.getParameters(), param, codes);
        return reportDsModelDefine;
    }

    private void setReportDsParameters(Map<String, String> taskInfo, List<ReportDsParameter> parameters, CheckItemParam param, List<String> codes) {
        String catagoryName = taskInfo.get("org");
        String taskTypeFlag = taskInfo.get("data");
        String period = taskInfo.get("period");
        ReportDsParameter periodParameter = new ReportDsParameter();
        periodParameter.setName(String.format("NR_PERIOD_%S", taskTypeFlag));
        periodParameter.setTitle("\u65f6\u671f");
        periodParameter.setDataType(6);
        periodParameter.setSelectMode(ParameterSelectMode.SINGLE);
        periodParameter.setDefaultValueMode(DefaultValueMode.APPOINT);
        periodParameter.setMessageAlias(PeriodTableColumn.TIMEKEY.getCode());
        periodParameter.setEntityId(taskTypeFlag);
        periodParameter.setDefaultValues(new String[]{period});
        IEntityDefine entity = this.entityMetaService.queryEntity(catagoryName);
        ReportDsParameter orgParameter = new ReportDsParameter();
        orgParameter.setName(catagoryName);
        orgParameter.setTitle(entity.getTitle());
        orgParameter.setDataType(6);
        orgParameter.setEntityId(catagoryName);
        orgParameter.setSelectMode(ParameterSelectMode.MUTIPLE);
        String[] orgLists = codes.toArray(new String[0]);
        if (orgLists.length > 0) {
            orgParameter.setDefaultValues(orgLists);
            orgParameter.setDefaultValueMode(DefaultValueMode.APPOINT);
        } else {
            orgParameter.setDefaultValueMode(DefaultValueMode.NONE);
        }
        periodParameter.setDefaultValues(new String[]{param.getContext().getPeriod()});
        orgParameter.setMessageAlias(catagoryName + ".ORGCODE");
        parameters.add(orgParameter);
        parameters.add(periodParameter);
    }

    public boolean judgmentFloat(String exp, String formSchemeKey, Map<String, Map<String, Boolean>> formFieldIsFloatMap) {
        if (!org.springframework.util.StringUtils.hasText(exp)) {
            return false;
        }
        exp = exp.toUpperCase();
        try {
            Integer index = exp.indexOf("[");
            if (index < 0) {
                return false;
            }
            while (index >= 0) {
                Map<String, Boolean> fieldIsFloat;
                FormDefine formDefine;
                Integer indexAt = index - 1;
                String formCode = "";
                while (indexAt >= 0) {
                    Integer n = indexAt;
                    Integer n2 = indexAt = Integer.valueOf(indexAt - 1);
                    char achar = exp.charAt(n);
                    if (!(achar >= 'A' && achar <= 'Z' || achar >= '0' && achar <= '9') && achar != '_') break;
                    formCode = achar + formCode;
                }
                if (!formFieldIsFloatMap.containsKey(formCode) && (formDefine = this.runtimeView.queryFormByCodeInScheme(formSchemeKey, formCode)) != null) {
                    List regionDefines = this.runtimeView.getAllRegionsInForm(formDefine.getKey());
                    HashMap<String, Boolean> fieldIsFloatMapInRegion = new HashMap<String, Boolean>();
                    for (DataRegionDefine regionDefine : regionDefines) {
                        Boolean floatRegion = regionDefine.getRegionKind() == DataRegionKind.DATA_REGION_ROW_LIST || regionDefine.getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST;
                        List linkDefines = this.runtimeView.getAllLinksInRegion(regionDefine.getKey());
                        for (DataLinkDefine linkDefine : linkDefines) {
                            fieldIsFloatMapInRegion.put(String.format("%d,%d", linkDefine.getColNum(), linkDefine.getRowNum()), floatRegion);
                        }
                        List fieldKeys = linkDefines.stream().map(DataLinkDefine::getLinkExpression).collect(Collectors.toList());
                        List dataFields = this.iRuntimeDataSchemeService.getDataFields(fieldKeys);
                        for (DataField dataField : dataFields) {
                            fieldIsFloatMapInRegion.put(dataField.getCode(), floatRegion);
                        }
                    }
                    formFieldIsFloatMap.put(formCode, fieldIsFloatMapInRegion);
                }
                if ((fieldIsFloat = formFieldIsFloatMap.get(formCode)) == null) {
                    return false;
                }
                indexAt = (exp = exp.substring(index + 1, exp.length())).indexOf("]");
                if (indexAt > 0) {
                    String field = exp.substring(0, indexAt).replace(" ", "");
                    if (fieldIsFloat.containsKey(field) && fieldIsFloat.get(field).booleanValue()) {
                        return true;
                    }
                    exp = exp.substring(indexAt + 1, exp.length());
                }
                index = exp.indexOf("[");
            }
            return false;
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    private void buildFailedOrgInfo(Map<String, FailedOrgInfo> failedOrgs, String org, boolean isNeedError) {
        FailedOrgInfo failedOrgInfo = failedOrgs.get(org);
        if (failedOrgInfo == null) {
            failedOrgInfo = new FailedOrgInfo();
            failedOrgs.put(org, failedOrgInfo);
            failedOrgInfo.setDesc(1 + (isNeedError ? DESCRIBE : DESCRIBE_NEEDPASS));
        } else {
            String msg = failedOrgInfo.getDesc();
            int count = 0;
            try {
                count = Integer.valueOf(msg.replace(isNeedError ? DESCRIBE : DESCRIBE_NEEDPASS, ""));
                ++count;
            }
            catch (Exception e) {
                logger.error(msg, e);
            }
            failedOrgInfo.setDesc(count + (isNeedError ? DESCRIBE : DESCRIBE_NEEDPASS));
        }
    }

    @NotNull
    private MultcheckResOrg initResOrg(CheckItemParam param, List<MultcheckResOrg> resOrgs, QueryCheckModel model, String org) {
        MultcheckResOrg orgRes = new MultcheckResOrg();
        resOrgs.add(orgRes);
        orgRes.setKey(UUID.randomUUID().toString());
        orgRes.setRecordKey(param.getRunId());
        orgRes.setItemKey(param.getCheckItem().getKey());
        orgRes.setItemType(this.getType());
        orgRes.setOrg(org);
        orgRes.setResource(model.getKey());
        return orgRes;
    }

    private boolean hasSameDim(List<String> dimsList, String currentDimStr, List<MCErrorDescription> mcErrorDescriptions) {
        boolean sameDim = false;
        for (MCErrorDescription error : mcErrorDescriptions) {
            Map errorDims = error.getDims();
            StringBuilder errorDimStr = new StringBuilder();
            for (String dim : dimsList) {
                errorDimStr.append(dim).append("=").append((String)errorDims.get(dim));
            }
            if (!errorDimStr.toString().equals(currentDimStr)) continue;
            sameDim = true;
            break;
        }
        return sameDim;
    }

    private void buildFailedOrgDimInfo(Map<String, FailedOrgInfo> failedOrgs, List<String> dimsList, String org, Map<String, String> currentDims, String currentDimStr) {
        ArrayList<FailedOrgDimInfo> dimInfos = null;
        FailedOrgInfo failedOrgInfo = failedOrgs.get(org);
        if (failedOrgInfo == null) {
            failedOrgInfo = new FailedOrgInfo();
            failedOrgs.put(org, failedOrgInfo);
            dimInfos = new ArrayList<FailedOrgDimInfo>();
            failedOrgInfo.setDimInfo(dimInfos);
            FailedOrgDimInfo dimInfo = new FailedOrgDimInfo();
            dimInfos.add(dimInfo);
            dimInfo.setDims(currentDims);
            dimInfo.setDesc("1\u4e2a\u6a21\u677f\u6ca1\u6709\u89e3\u91ca\u8bf4\u660e");
        } else {
            dimInfos = failedOrgInfo.getDimInfo();
            FailedOrgDimInfo sameDimInfo = null;
            for (FailedOrgDimInfo dimInfo : dimInfos) {
                StringBuilder errorDimStr = new StringBuilder();
                for (String dim : dimsList) {
                    errorDimStr.append(dim).append("=").append((String)dimInfo.getDims().get(dim));
                }
                if (!currentDimStr.equals(errorDimStr.toString())) continue;
                sameDimInfo = dimInfo;
                break;
            }
            if (sameDimInfo == null) {
                FailedOrgDimInfo dimInfo = new FailedOrgDimInfo();
                dimInfo.setDims(currentDims);
                dimInfo.setDesc("1\u4e2a\u6a21\u677f\u6ca1\u6709\u89e3\u91ca\u8bf4\u660e");
                dimInfos.add(dimInfo);
            } else {
                String msg = sameDimInfo.getDesc();
                int count = Integer.valueOf(msg.replace(DESCRIBE, ""));
                sameDimInfo.setDesc(++count + DESCRIBE);
            }
        }
    }

    @Nullable
    private String buildCurrentDim(List<String> dimsList, DimensionCombination dimensionCombination, Map<String, String> currentDims, MultcheckResOrg orgRes) {
        String currentDimStr = null;
        if (!CollectionUtils.isEmpty(dimsList)) {
            StringBuilder combinationDimStr = new StringBuilder();
            for (String dim : dimsList) {
                String dimValue = (String)dimensionCombination.getValue(dim);
                combinationDimStr.append(dim).append("=").append(dimValue);
                currentDims.put(dim, dimValue);
            }
            currentDimStr = combinationDimStr.toString();
            orgRes.setDims(currentDims);
        }
        return currentDimStr;
    }

    private Map<String, List<MCErrorDescription>> buildOrgErrorMap(List<String> orgList, List<MCErrorDescription> errors) {
        HashMap<String, List<MCErrorDescription>> orgErrorMap = new HashMap<String, List<MCErrorDescription>>();
        if (!CollectionUtils.isEmpty(errors)) {
            for (MCErrorDescription e : errors) {
                if (!orgList.contains(e.getOrg())) continue;
                ArrayList<MCErrorDescription> mcErrorDescriptions = (ArrayList<MCErrorDescription>)orgErrorMap.get(e.getOrg());
                if (mcErrorDescriptions == null) {
                    mcErrorDescriptions = new ArrayList<MCErrorDescription>();
                    orgErrorMap.put(e.getOrg(), mcErrorDescriptions);
                }
                mcErrorDescriptions.add(e);
            }
        }
        return orgErrorMap;
    }

    public PluginInfo getResultPlugin() {
        return new PluginInfo("@nr", "nr-singlequery-multcheck", "Result");
    }

    private String decryptFml(String fml) {
        try {
            if (fml.length() == 6 && "ENCRY:".equals(fml.substring(0, 6))) {
                return "";
            }
            String decryptStr = new String(Base64.getDecoder().decode(fml.substring("ENCRY:".length(), fml.length())));
            decryptStr = decryptStr.replace("+", "__PLUS__");
            decryptStr = URLDecoder.decode(decryptStr, "UTF-8");
            decryptStr = decryptStr.replace("__PLUS__", "+");
            return decryptStr;
        }
        catch (Exception e) {
            logger.error(String.format("%s\u89e3\u5bc6\u5931\u8d25", fml));
            return null;
        }
    }
}

