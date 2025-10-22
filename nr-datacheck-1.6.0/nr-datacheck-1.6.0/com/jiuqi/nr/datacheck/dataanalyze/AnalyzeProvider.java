/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bsp.contentcheckrules.common.ContentCheckResult
 *  com.jiuqi.bsp.contentcheckrules.service.ContentCheckByGroupService
 *  com.jiuqi.bsp.contentcheckrules.service.impl.ContentCheckServiceFactory
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
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
 *  com.jiuqi.nr.dataservice.core.dimension.ArrayDimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DynamicDimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.multcheck2.bean.MCErrorDescription
 *  com.jiuqi.nr.multcheck2.bean.MultcheckItem
 *  com.jiuqi.nr.multcheck2.bean.MultcheckResOrg
 *  com.jiuqi.nr.multcheck2.bean.MultcheckScheme
 *  com.jiuqi.nr.multcheck2.common.CheckRestultState
 *  com.jiuqi.nr.multcheck2.dynamic.IMCContextService
 *  com.jiuqi.nr.multcheck2.dynamic.IMCErrorInfoService
 *  com.jiuqi.nr.multcheck2.dynamic.IMCItemOrgService
 *  com.jiuqi.nr.multcheck2.provider.CheckItemParam
 *  com.jiuqi.nr.multcheck2.provider.CheckItemResult
 *  com.jiuqi.nr.multcheck2.provider.FailedOrgDimInfo
 *  com.jiuqi.nr.multcheck2.provider.FailedOrgInfo
 *  com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider
 *  com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO
 *  com.jiuqi.nr.multcheck2.provider.PluginInfo
 *  com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 *  com.jiuqi.nvwa.dataanalyze.service.ResourceTreeContext
 *  com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.datacheck.dataanalyze;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bsp.contentcheckrules.common.ContentCheckResult;
import com.jiuqi.bsp.contentcheckrules.service.ContentCheckByGroupService;
import com.jiuqi.bsp.contentcheckrules.service.impl.ContentCheckServiceFactory;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
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
import com.jiuqi.nr.datacheck.common.SerializeUtil;
import com.jiuqi.nr.datacheck.dataanalyze.AnalyzeConfig;
import com.jiuqi.nr.datacheck.dataanalyze.AnalyzeErrorCheckService;
import com.jiuqi.nr.datacheck.dataanalyze.AnalyzeModel;
import com.jiuqi.nr.datacheck.dataanalyze.AnalyzeRuleGroupParam;
import com.jiuqi.nr.datacheck.dataanalyze.CheckCondition;
import com.jiuqi.nr.datacheck.dataanalyze.OrgSelectType;
import com.jiuqi.nr.datacheck.dataanalyze.vo.OrgDimSetDTO;
import com.jiuqi.nr.datacheck.dataanalyze.vo.OrgStateVO;
import com.jiuqi.nr.dataservice.core.dimension.ArrayDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DynamicDimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.multcheck2.bean.MCErrorDescription;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckResOrg;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.common.CheckRestultState;
import com.jiuqi.nr.multcheck2.dynamic.IMCContextService;
import com.jiuqi.nr.multcheck2.dynamic.IMCErrorInfoService;
import com.jiuqi.nr.multcheck2.dynamic.IMCItemOrgService;
import com.jiuqi.nr.multcheck2.provider.CheckItemParam;
import com.jiuqi.nr.multcheck2.provider.CheckItemResult;
import com.jiuqi.nr.multcheck2.provider.FailedOrgDimInfo;
import com.jiuqi.nr.multcheck2.provider.FailedOrgInfo;
import com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider;
import com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO;
import com.jiuqi.nr.multcheck2.provider.PluginInfo;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import com.jiuqi.nvwa.dataanalyze.service.ResourceTreeContext;
import com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class AnalyzeProvider
implements IMultcheckItemProvider {
    private static final Logger logger = LoggerFactory.getLogger(AnalyzeProvider.class);
    static final String SUCCESS = "TRUE";
    static final String FAILED = "FALSE";
    public static final String CONFIRM_DESC = "CONFIRM-TRUE";
    public static final String ICON_SUCCESS = "#icon-J_HXGLB_C_ZFJS_GreenDot";
    public static final String ICON_SUCCESS_ERROR = "#icon-J_HXGLB_C_ZFJS_YellowDot";
    public static final String ICON_FAILED = "#icon-J_HXGLB_C_ZFJS_RedDot";
    private static final String DESCRIBE = "\u4e2a\u6a21\u677f\u5ba1\u6838\u4e0d\u901a\u8fc7";
    static final String TYPE = "dataanalyze";
    @Autowired
    IMCErrorInfoService errorInfoService;
    @Autowired
    IMCItemOrgService orgService;
    @Autowired
    IMCContextService contextService;
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
    private RunTimeAuthViewController runTimeAuthView;
    @Autowired
    protected IEntityDataService entityDataService;
    @Autowired
    private AnalyzeErrorCheckService checkService;
    @Autowired
    private ContentCheckServiceFactory checkFactory;

    public String getType() {
        return TYPE;
    }

    public String getTitle() {
        return "\u6570\u636e\u5206\u6790";
    }

    public double getOrder() {
        return 10.0;
    }

    public PluginInfo getPropertyPluginInfo() {
        return new PluginInfo("@nr", "nr-datacheck-dataanalyze-plugin", "Config");
    }

    public String getItemDescribe(String formSchemeKey, MultcheckItem item) {
        if (!StringUtils.hasText(item.getConfig())) {
            return "\u9009\u62e9\u6a21\u677f | 0\u4e2a";
        }
        try {
            AnalyzeConfig config = this.getConfig(item.getConfig());
            List<AnalyzeModel> models = this.getAuthModel(config.getModels());
            return "\u9009\u62e9\u6a21\u677f | <span class=\"mtc-item-number-cls\">" + models.size() + "</span>\u4e2a";
        }
        catch (Exception e) {
            logger.error("\u7efc\u5408\u5ba1\u6838\uff1a\uff1a\u6570\u636e\u5206\u6790\u6a21\u578b\u5f02\u5e38" + item.getKey(), e);
            return null;
        }
    }

    private AnalyzeConfig getConfig(String configStr) {
        try {
            return SerializeUtil.deserializeFromJson(configStr, AnalyzeConfig.class);
        }
        catch (Exception e) {
            try {
                JSONObject json = new JSONObject(configStr);
                AnalyzeConfig config = new AnalyzeConfig();
                if (json.has("needErrorDesc")) {
                    Boolean needErrorDesc = json.getBoolean("needErrorDesc");
                    config.setCheckRequires(needErrorDesc != false ? CheckCondition.NEEDERROR : CheckCondition.NEEDPASS);
                }
                if (json.has("checkRequires")) {
                    config.setCheckRequires(CheckCondition.valueOf(json.getString("checkRequires")));
                }
                if (!json.has("orgSelectType")) {
                    config.setOrgSelectType(OrgSelectType.UCURRENTALLSUB);
                } else {
                    config.setOrgSelectType(OrgSelectType.valueOf(json.getString("orgSelectType")));
                }
                ArrayList<AnalyzeModel> modelList = new ArrayList<AnalyzeModel>();
                config.setModels(modelList);
                JSONArray models = json.getJSONArray("models");
                for (int i = 0; i < models.length(); ++i) {
                    JSONObject model = models.getJSONObject(i);
                    AnalyzeModel modelObj = new AnalyzeModel();
                    modelObj.setKey(model.getString("key"));
                    modelObj.setTitle(model.getString("title"));
                    modelObj.setType(model.getString("type"));
                    if (model.has("code") && model.get("code") != null && !"null".equalsIgnoreCase(model.get("code").toString())) {
                        modelObj.setCode(model.getString("code"));
                    }
                    if (model.has("fml") && model.get("fml") != null && !"null".equalsIgnoreCase(model.get("fml").toString())) {
                        modelObj.setFml(model.getString("fml"));
                    }
                    modelList.add(modelObj);
                }
                return config;
            }
            catch (Exception ee) {
                logger.error("\u7efc\u5408\u5ba1\u6838\uff1a\uff1a\u6570\u636e\u5206\u6790\u6a21\u578b\u5f02\u5e38:" + configStr, e);
                return null;
            }
        }
    }

    private List<AnalyzeModel> getAuthModel(List<AnalyzeModel> models) throws DataAnalyzeResourceException {
        ArrayList<AnalyzeModel> res = new ArrayList<AnalyzeModel>();
        for (AnalyzeModel model : models) {
            ResourceTreeNode node = this.resourceTreeNodeService.get(this.buildContext(), model.getKey());
            if (node == null) continue;
            res.add(model);
        }
        return res;
    }

    private ResourceTreeContext buildContext() {
        ResourceTreeContext context = new ResourceTreeContext();
        context.setUserId(NpContextHolder.getContext().getIdentityId());
        context.setPrivilegeId("dataanalysis_read");
        return context;
    }

    public MultCheckItemDTO copyCheckItem(MultcheckScheme sourceScheme, MultcheckItem sourceItem, String targetFormSchemeKey) {
        MultCheckItemDTO dto = new MultCheckItemDTO();
        BeanUtils.copyProperties(sourceItem, dto);
        return dto;
    }

    public boolean canChangeConfig() {
        return true;
    }

    public String getRunItemDescribe(String formSchemeKey, MultcheckItem item) {
        return this.getItemDescribe(formSchemeKey, item);
    }

    public PluginInfo getRunPropertyPluginInfo() {
        return null;
    }

    public CheckItemResult runCheck(CheckItemParam param) {
        try {
            AnalyzeConfig config = this.getConfig(param.getCheckItem().getConfig());
            AsyncTaskMonitor asyncTaskMonitor = param.getAsyncTaskMonitor();
            if (asyncTaskMonitor.isCancel()) {
                logger.info("\u5ba1\u6838\u9879\uff1a\uff1a\u6570\u636e\u5206\u6790:\u5df2\u4e2d\u65ad");
                return null;
            }
            if (config.getCheckRequires() == null) {
                config.setCheckRequires(CheckCondition.NEEDPASS);
            }
            List<AnalyzeModel> models = this.getAuthModel(config.getModels());
            List orgList = param.getContext().getOrgList();
            ArrayList<String> noFmlModels = new ArrayList<String>();
            ArrayList<Formula> fmlModels = new ArrayList<Formula>();
            ArrayList<String> allModels = new ArrayList<String>();
            for (AnalyzeModel model : models) {
                allModels.add(model.getKey());
                if (StringUtils.hasText(model.getFml()) && !model.getFml().startsWith("//")) {
                    Formula formula = new Formula();
                    formula.setId(model.getKey());
                    formula.setFormula(model.getFml());
                    formula.setChecktype(Integer.valueOf(FormulaCheckType.FORMULA_CHECK_ERROR.getValue()));
                    fmlModels.add(formula);
                    continue;
                }
                noFmlModels.add(model.getKey());
            }
            logger.info("\u5ba1\u6838\u9879\u3010\u6570\u636e\u5206\u6790\u3011\u6267\u884c\u5ba1\u6838\u516c\u5f0f\uff1a" + models.size());
            String task = param.getContext().getTaskKey();
            String period = param.getContext().getPeriod();
            IEntityTable entityTableAll = null;
            Map<Object, Object> checkFmlOrgRes = new HashMap();
            if (!CollectionUtils.isEmpty(fmlModels)) {
                ArrayList<String> fmlOrgList = new ArrayList<String>();
                switch (config.getOrgSelectType()) {
                    case UCURRENT: {
                        fmlOrgList.addAll(orgList);
                        break;
                    }
                    case UCURRENTDIRECTSUB: {
                        entityTableAll = this.contextService.getTreeEntityTable(task, period, param.getContext().getOrg(), param.getContext().getFormSchemeKey());
                        HashSet<String> checkOrgs1 = new HashSet<String>();
                        for (String org : orgList) {
                            checkOrgs1.add(org);
                            List childRows = entityTableAll.getChildRows(org);
                            if (CollectionUtils.isEmpty(childRows)) continue;
                            checkOrgs1.addAll(childRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toSet()));
                        }
                        fmlOrgList.addAll(checkOrgs1);
                        break;
                    }
                    default: {
                        IEntityTable entityTable = this.contextService.getTreeEntityTable(task, period, param.getContext().getOrg(), param.getContext().getFormSchemeKey());
                        List rootRows = entityTable.getRootRows();
                        entityTableAll = this.contextService.getTreeEntityTable(task, period, param.getContext().getOrg(), param.getContext().getFormSchemeKey(), orgList);
                        HashSet<String> checkOrgs2 = new HashSet<String>();
                        for (IEntityRow row : rootRows) {
                            checkOrgs2.add(row.getEntityKeyData());
                            List allChildRows = entityTableAll.getAllChildRows(row.getEntityKeyData());
                            if (CollectionUtils.isEmpty(allChildRows)) continue;
                            checkOrgs2.addAll(allChildRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toSet()));
                        }
                        fmlOrgList.addAll(checkOrgs2);
                    }
                }
                if (asyncTaskMonitor.isCancel()) {
                    logger.info("\u5ba1\u6838\u9879\uff1a\uff1a\u6570\u636e\u5206\u6790:\u5df2\u4e2d\u65ad");
                    return null;
                }
                long t = System.currentTimeMillis();
                checkFmlOrgRes = this.doCheckFml(fmlModels, fmlOrgList, param);
                logger.info("doCheckFml::=" + (System.currentTimeMillis() - t) + "::checkFmlOrgRes=" + checkFmlOrgRes.size());
            }
            if (asyncTaskMonitor.isCancel()) {
                logger.info("\u5ba1\u6838\u9879\uff1a\uff1a\u6570\u636e\u5206\u6790:\u5df2\u4e2d\u65ad");
                return null;
            }
            if (!CollectionUtils.isEmpty(checkFmlOrgRes)) {
                HashMap<String, List<String>> OrgAllMap = new HashMap<String, List<String>>();
                HashMap<String, List<String>> OrgDirectMap = new HashMap<String, List<String>>();
                for (String modelKey : checkFmlOrgRes.keySet()) {
                    List<String> children;
                    HashSet<String> checkOrgList;
                    Set<String> errorOrgList;
                    List orgDimSetDTOList = (List)checkFmlOrgRes.get(modelKey);
                    if (CollectionUtils.isEmpty(orgDimSetDTOList) || config.getOrgSelectType() == OrgSelectType.UCURRENT) continue;
                    if (config.getOrgSelectType() == OrgSelectType.UCURRENTDIRECTSUB) {
                        if (entityTableAll == null) {
                            logger.info("entityTableAll == null");
                            entityTableAll = this.contextService.getTreeEntityTable(task, period, param.getContext().getOrg(), param.getContext().getFormSchemeKey(), orgList);
                        }
                        for (OrgDimSetDTO orgDimSetDTO : orgDimSetDTOList) {
                            errorOrgList = orgDimSetDTO.getErrorOrgList();
                            checkOrgList = new HashSet<String>();
                            block11: for (String org : orgList) {
                                if (errorOrgList.contains(org)) {
                                    checkOrgList.add(org);
                                    continue;
                                }
                                children = this.getChildren(OrgDirectMap, entityTableAll, org);
                                if (CollectionUtils.isEmpty(children)) continue;
                                for (String child : children) {
                                    if (!errorOrgList.contains(child)) continue;
                                    checkOrgList.add(org);
                                    continue block11;
                                }
                            }
                            orgDimSetDTO.setErrorOrgList(checkOrgList);
                        }
                        continue;
                    }
                    if (entityTableAll == null) {
                        logger.info("entityTableAll == null");
                        entityTableAll = this.contextService.getTreeEntityTable(task, period, param.getContext().getOrg(), param.getContext().getFormSchemeKey(), orgList);
                    }
                    for (OrgDimSetDTO orgDimSetDTO : orgDimSetDTOList) {
                        errorOrgList = orgDimSetDTO.getErrorOrgList();
                        checkOrgList = new HashSet();
                        block14: for (String org : orgList) {
                            if (errorOrgList.contains(org)) {
                                checkOrgList.add(org);
                                continue;
                            }
                            children = this.getAllChildren(OrgAllMap, entityTableAll, org);
                            if (CollectionUtils.isEmpty(children)) continue;
                            for (String child : children) {
                                if (!errorOrgList.contains(child)) continue;
                                checkOrgList.add(org);
                                continue block14;
                            }
                        }
                        orgDimSetDTO.setErrorOrgList(checkOrgList);
                    }
                }
            }
            if (asyncTaskMonitor.isCancel()) {
                logger.info("\u5ba1\u6838\u9879\uff1a\uff1a\u6570\u636e\u5206\u6790:\u5df2\u4e2d\u65ad");
                return null;
            }
            ArrayList<MultcheckResOrg> resOrgs = new ArrayList<MultcheckResOrg>();
            HashMap<String, FailedOrgInfo> failedOrgs = new HashMap<String, FailedOrgInfo>();
            List dimensionCombinations = param.getContext().getDims().getDimensionCombinations();
            List dimsList = this.contextService.getDynamicFieldsByTask(task);
            HashSet<Object> successWidthExplainOrgs = new HashSet<Object>();
            CheckRestultState restultState = CheckRestultState.SUCCESS;
            if (config.getCheckRequires() == CheckCondition.NEEDERROR) {
                Map<String, ArrayList<MCErrorDescription>> orgErrorMap;
                AnalyzeRuleGroupParam ruleParam = new AnalyzeRuleGroupParam();
                ruleParam.setTaskKey(param.getContext().getTaskKey());
                ruleParam.setCheckSchemeKey(param.getContext().getCheckSchemeKey());
                ruleParam.setFormSchemeKey(param.getContext().getFormSchemeKey());
                ruleParam.setOrg(param.getContext().getOrg());
                String ruleGroup = this.checkService.getRuleGroup(ruleParam);
                ContentCheckByGroupService doRuleCheckService = this.checkFactory.getCheckService();
                if (asyncTaskMonitor.isCancel()) {
                    logger.info("\u5ba1\u6838\u9879\uff1a\uff1a\u6570\u636e\u5206\u6790:\u5df2\u4e2d\u65ad");
                    return null;
                }
                boolean failedOrg = false;
                boolean failedAndErrorOrg = false;
                HashMap modelOrgErrorMap = new HashMap();
                long t = System.currentTimeMillis();
                List errors = this.errorInfoService.getByResourcesAndOrgs(task, period, this.getType(), allModels, orgList);
                logger.info("getByResourcesAndOrgs::=" + (System.currentTimeMillis() - t) + "::allModels=" + allModels.size() + "::orgList=" + orgList.size() + "::errors=" + errors.size());
                if (!CollectionUtils.isEmpty(errors)) {
                    for (MCErrorDescription e : errors) {
                        ArrayList<MCErrorDescription> errorList;
                        String source = e.getResource();
                        orgErrorMap = (HashMap)modelOrgErrorMap.get(source);
                        if (orgErrorMap == null) {
                            orgErrorMap = new HashMap();
                            modelOrgErrorMap.put(source, orgErrorMap);
                        }
                        if ((errorList = (ArrayList<MCErrorDescription>)orgErrorMap.get(e.getOrg())) == null) {
                            errorList = new ArrayList<MCErrorDescription>();
                            orgErrorMap.put(e.getOrg(), errorList);
                        }
                        errorList.add(e);
                    }
                }
                for (AnalyzeModel model : models) {
                    if (asyncTaskMonitor.isCancel()) {
                        logger.info("\u5ba1\u6838\u9879\uff1a\uff1a\u6570\u636e\u5206\u6790:\u5df2\u4e2d\u65ad");
                        return null;
                    }
                    HashSet noFmlOrgs = new HashSet();
                    if (noFmlModels.contains(model.getKey())) {
                        noFmlOrgs = new HashSet(orgList);
                    }
                    orgErrorMap = (Map)modelOrgErrorMap.get(model.getKey());
                    List orgDimSetDTOList = (List)checkFmlOrgRes.get(model.getKey());
                    Map<String, Set<String>> dimOrgListMap = this.buildDimOrgList(orgDimSetDTOList, dimsList);
                    for (DimensionCombination dimensionCombination : dimensionCombinations) {
                        String org = (String)dimensionCombination.getDWDimensionValue().getValue();
                        MultcheckResOrg orgRes = this.initResOrg(param, resOrgs, model, org);
                        HashMap<String, String> currentDims = new HashMap<String, String>();
                        String currentDimStr = this.buildCurrentDim(dimsList, dimensionCombination, currentDims, orgRes);
                        boolean orgCheckRes = true;
                        if (!CollectionUtils.isEmpty(noFmlOrgs) && noFmlOrgs.contains(org)) {
                            orgCheckRes = false;
                        } else {
                            Set<String> errorOrgList = dimOrgListMap.get(StringUtils.hasLength(currentDimStr) ? currentDimStr : "ORG");
                            if (!CollectionUtils.isEmpty(errorOrgList) && errorOrgList.contains(org)) {
                                orgCheckRes = false;
                            }
                        }
                        OrgStateVO orgStateVO = new OrgStateVO();
                        if (!orgCheckRes) {
                            boolean errorPass;
                            String content;
                            List mcErrorDescriptions;
                            orgStateVO.setFmlRes(false);
                            List list = mcErrorDescriptions = CollectionUtils.isEmpty(orgErrorMap) ? null : (List)orgErrorMap.get(org);
                            if (CollectionUtils.isEmpty(mcErrorDescriptions)) {
                                orgStateVO.setErrorRes(false);
                                orgStateVO.setLegalRes(false);
                                orgStateVO.setOrgRes(false);
                                failedOrg = true;
                                if (CollectionUtils.isEmpty(dimsList)) {
                                    this.buildFailedOrgInfo(failedOrgs, org);
                                } else {
                                    this.buildFailedOrgDimInfo(failedOrgs, dimsList, org, currentDims, currentDimStr);
                                }
                            } else if (CollectionUtils.isEmpty(dimsList)) {
                                orgStateVO.setErrorRes(true);
                                content = ((MCErrorDescription)mcErrorDescriptions.get(0)).getDescription();
                                errorPass = this.checkContent(content, ruleGroup, doRuleCheckService);
                                orgStateVO.setLegalRes(errorPass);
                                orgStateVO.setOrgRes(errorPass);
                                if (errorPass) {
                                    failedAndErrorOrg = true;
                                    successWidthExplainOrgs.add(org);
                                } else {
                                    failedOrg = true;
                                    this.buildFailedOrgInfo(failedOrgs, org);
                                }
                            } else {
                                content = this.getSameDimContent(dimsList, currentDimStr, mcErrorDescriptions);
                                if (!StringUtils.hasText(content)) {
                                    orgStateVO.setErrorRes(false);
                                    orgStateVO.setLegalRes(false);
                                    orgStateVO.setOrgRes(false);
                                    failedOrg = true;
                                    this.buildFailedOrgDimInfo(failedOrgs, dimsList, org, currentDims, currentDimStr);
                                } else {
                                    orgStateVO.setErrorRes(true);
                                    errorPass = this.checkContent(content, ruleGroup, doRuleCheckService);
                                    orgStateVO.setLegalRes(errorPass);
                                    orgStateVO.setOrgRes(errorPass);
                                    if (errorPass) {
                                        failedAndErrorOrg = true;
                                        successWidthExplainOrgs.add(org);
                                    } else {
                                        failedOrg = true;
                                        this.buildFailedOrgDimInfo(failedOrgs, dimsList, org, currentDims, currentDimStr);
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
                if (failedOrg) {
                    restultState = CheckRestultState.FAIL;
                } else if (failedAndErrorOrg) {
                    restultState = CheckRestultState.SUCCESS_ERROR;
                }
            } else {
                boolean failedOrg = false;
                for (AnalyzeModel model : models) {
                    if (asyncTaskMonitor.isCancel()) {
                        logger.info("\u5ba1\u6838\u9879\uff1a\uff1a\u6570\u636e\u5206\u6790:\u5df2\u4e2d\u65ad");
                        return null;
                    }
                    List orgDimSetDTOList = (List)checkFmlOrgRes.get(model.getKey());
                    Map<String, Set<String>> dimOrgListMap = this.buildDimOrgList(orgDimSetDTOList, dimsList);
                    for (DimensionCombination dimensionCombination : dimensionCombinations) {
                        String org;
                        MultcheckResOrg orgRes;
                        HashMap<String, String> currentDims;
                        String currentDimStr = this.buildCurrentDim(dimsList, dimensionCombination, currentDims = new HashMap<String, String>(), orgRes = this.initResOrg(param, resOrgs, model, org = (String)dimensionCombination.getDWDimensionValue().getValue()));
                        Set<String> errorOrgList = dimOrgListMap.get(StringUtils.hasLength(currentDimStr) ? currentDimStr : "ORG");
                        OrgStateVO orgStateVO = new OrgStateVO();
                        if (!CollectionUtils.isEmpty(errorOrgList) && errorOrgList.contains(org)) {
                            orgStateVO.setFmlRes(false);
                            orgStateVO.setOrgRes(false);
                            failedOrg = true;
                            if (CollectionUtils.isEmpty(dimsList)) {
                                this.buildFailedOrgInfo(failedOrgs, org);
                            } else {
                                this.buildFailedOrgDimInfo(failedOrgs, dimsList, org, currentDims, currentDimStr);
                            }
                        } else {
                            orgStateVO.setFmlRes(true);
                            orgStateVO.setOrgRes(true);
                        }
                        orgRes.setResult(orgStateVO.getRes());
                    }
                }
                if (failedOrg) {
                    restultState = CheckRestultState.FAIL;
                }
            }
            if (asyncTaskMonitor.isCancel()) {
                logger.info("\u5ba1\u6838\u9879\uff1a\uff1a\u6570\u636e\u5206\u6790:\u5df2\u4e2d\u65ad");
                return null;
            }
            long t = System.currentTimeMillis();
            this.saveOrgRes(resOrgs, dimsList, task);
            logger.info("orgService.batchSave::=" + (System.currentTimeMillis() - t) + "::resOrgs=" + resOrgs.size());
            ArrayList successOrgs = new ArrayList();
            successWidthExplainOrgs.removeAll(failedOrgs.keySet());
            orgList.removeAll(failedOrgs.keySet());
            orgList.removeAll(successWidthExplainOrgs);
            successOrgs.addAll(orgList);
            HashMap<String, FailedOrgInfo> failedOrgs0 = new HashMap<String, FailedOrgInfo>();
            if (!CollectionUtils.isEmpty(failedOrgs)) {
                if (CollectionUtils.isEmpty(dimsList)) {
                    failedOrgs0 = failedOrgs;
                } else {
                    TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(task);
                    List dimForPage = this.contextService.getDynamicDimNamesForPage(taskDefine.getDataScheme());
                    boolean hasPageDim = false;
                    HashMap entityValues = new HashMap();
                    if (!CollectionUtils.isEmpty(dimForPage)) {
                        hasPageDim = true;
                        for (String dimKey : dimForPage) {
                            HashMap<String, String> dimValues = new HashMap<String, String>();
                            entityValues.put(dimKey, dimValues);
                            EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(dimKey);
                            DimensionValueSet dimensionValueSet = new DimensionValueSet();
                            dimensionValueSet.setValue("DATATIME", (Object)period);
                            IEntityQuery query = this.entityDataService.newEntityQuery();
                            query.sorted(true);
                            query.setAuthorityOperations(AuthorityType.Read);
                            query.setEntityView(entityViewDefine);
                            query.setMasterKeys(dimensionValueSet);
                            ExecutorContext context1 = new ExecutorContext(this.dataDefinitionRuntimeController);
                            context1.setVarDimensionValueSet(dimensionValueSet);
                            context1.setPeriodView(taskDefine.getDateTime());
                            IEntityTable entityTable = query.executeReader((IContext)context1);
                            List allrows = entityTable.getAllRows();
                            for (IEntityRow row : allrows) {
                                dimValues.put(row.getCode(), row.getTitle());
                            }
                        }
                    }
                    for (String org : failedOrgs.keySet()) {
                        FailedOrgInfo failedOrgInfo = (FailedOrgInfo)failedOrgs.get(org);
                        List dimInfo = failedOrgInfo.getDimInfo();
                        if (hasPageDim) {
                            String desc = "";
                            for (FailedOrgDimInfo info : dimInfo) {
                                Map dims = info.getDims();
                                String dimDesc = info.getDesc();
                                String dimTitle = "";
                                for (String dimKey : dimForPage) {
                                    Map dimValues = (Map)entityValues.get(dimKey);
                                    dimTitle = dimTitle + String.format("\u3010%s\u3011", dimValues.get(dims.get(dimKey)));
                                }
                                desc = desc + dimTitle + dimDesc + ";";
                            }
                            FailedOrgInfo failedOrgInfo0 = new FailedOrgInfo();
                            failedOrgs0.put(org, failedOrgInfo0);
                            failedOrgInfo0.setDesc(desc);
                            continue;
                        }
                        FailedOrgInfo failedOrgInfo0 = new FailedOrgInfo();
                        failedOrgs0.put(org, failedOrgInfo0);
                        failedOrgInfo0.setDesc(((FailedOrgDimInfo)dimInfo.get(0)).getDesc());
                    }
                }
            }
            if (asyncTaskMonitor.isCancel()) {
                logger.info("\u5ba1\u6838\u9879\uff1a\uff1a\u6570\u636e\u5206\u6790:\u5df2\u4e2d\u65ad");
                return null;
            }
            CheckItemResult result = new CheckItemResult();
            result.setRunId(param.getRunId());
            result.setResult(restultState);
            result.setSuccessOrgs(successOrgs);
            result.setSuccessWithExplainOrgs(new ArrayList(successWidthExplainOrgs));
            result.setFailedOrgs(failedOrgs0);
            result.setRunConfig(param.getCheckItem().getConfig());
            return result;
        }
        catch (Exception e) {
            logger.error("\u7efc\u5408\u5ba1\u6838\uff1a\uff1a\u6570\u636e\u5206\u6790\u6a21\u578b\u5f02\u5e38" + param.getCheckItem().getKey(), e);
            return null;
        }
    }

    private void saveOrgRes(List<MultcheckResOrg> resOrgs, List<String> dimsList, String task) {
        this.orgService.batchSave(resOrgs, dimsList, task);
    }

    @NotNull
    private Map<String, Set<String>> buildDimOrgList(List<OrgDimSetDTO> orgDimSetDTOList, List<String> dimsList) {
        HashMap<String, Set<String>> dimOrgListMap = new HashMap<String, Set<String>>();
        if (!CollectionUtils.isEmpty(orgDimSetDTOList)) {
            for (OrgDimSetDTO orgDimSetDTO : orgDimSetDTOList) {
                DimensionValueSet dimSet = orgDimSetDTO.getDimSet();
                if (!CollectionUtils.isEmpty(dimsList)) {
                    StringBuilder combinationDimStr = new StringBuilder();
                    for (String dim : dimsList) {
                        String dimValue = (String)dimSet.getValue(dim);
                        combinationDimStr.append(dim).append("=").append(dimValue);
                    }
                    dimOrgListMap.put(combinationDimStr.toString(), orgDimSetDTO.getErrorOrgList());
                    continue;
                }
                dimOrgListMap.put("ORG", orgDimSetDTO.getErrorOrgList());
            }
        }
        return dimOrgListMap;
    }

    private boolean checkContent(String content, String ruleGroup, ContentCheckByGroupService doRuleCheckService) {
        ContentCheckResult result = this.checkService.checkContent(content, ruleGroup, doRuleCheckService);
        return result.getStatus();
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

    private Map<String, List<OrgDimSetDTO>> doCheckFml(List<Formula> formulas, List<String> orgList, CheckItemParam param) throws Exception {
        DimensionValueSet dimSet;
        List variableDimensionList;
        List arrayDimensionList;
        IMainDimFilter mainDimFilter = this.dataAccessProvider.newMainDimFilter();
        String dimensionName = this.entityMetaService.getDimensionName(param.getContext().getOrg());
        DynamicDimensionCollection collection = null;
        if (param.getContext().getDims() instanceof DynamicDimensionCollection) {
            collection = (DynamicDimensionCollection)param.getContext().getDims();
        }
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        List fixDimensionList = collection.getFixDimensionList();
        if (!CollectionUtils.isEmpty(fixDimensionList)) {
            for (FixedDimensionValue fdim : fixDimensionList) {
                if (dimensionName.equals(fdim.getName())) {
                    builder.setEntityValue(fdim.getName(), fdim.getEntityID(), new Object[]{orgList});
                    continue;
                }
                builder.setEntityValue(fdim.getName(), fdim.getEntityID(), new Object[]{fdim.getValue()});
            }
        }
        if (!CollectionUtils.isEmpty(arrayDimensionList = collection.getArrayDimensionList())) {
            for (ArrayDimensionValue adim : arrayDimensionList) {
                if (dimensionName.equals(adim.getName())) {
                    builder.setEntityValue(adim.getName(), adim.getEntityID(), new Object[]{orgList});
                    continue;
                }
                builder.setEntityValue(adim.getName(), adim.getEntityID(), adim.getValue());
            }
        }
        if (!CollectionUtils.isEmpty(variableDimensionList = collection.getVariableDimensionList())) {
            for (VariableDimensionValue vdim : variableDimensionList) {
                VariableDimensionValueProvider provider = vdim.getProvider();
                if (dimensionName.equals(vdim.getName())) {
                    builder.addVariableDimension(vdim.getName(), vdim.getEntityID(), provider);
                    continue;
                }
                builder.addVariableDimension(vdim.getName(), vdim.getEntityID(), provider);
            }
        }
        if (param.getAsyncTaskMonitor().isCancel()) {
            logger.info("\u5ba1\u6838\u9879\uff1a\uff1a\u6570\u636e\u5206\u6790:\u5df2\u4e2d\u65ad");
            return null;
        }
        HashMap<String, DimensionValueSet> fmlDimensionValueSetMap = new HashMap<String, DimensionValueSet>();
        List newDims = builder.getCollection().getDimensionCombinations();
        for (DimensionCombination dim : newDims) {
            if (param.getAsyncTaskMonitor().isCancel()) {
                logger.info("\u5ba1\u6838\u9879\uff1a\uff1a\u6570\u636e\u5206\u6790:\u5df2\u4e2d\u65ad");
                return null;
            }
            Collection names = dim.getNames();
            names.stream().sorted();
            String key = "";
            for (String name : names) {
                if (dimensionName.equals(name)) continue;
                key = key + dim.getValue(name) + ";";
            }
            DimensionValueSet dimensionValueSet = (DimensionValueSet)fmlDimensionValueSetMap.get(key);
            if (dimensionValueSet == null) {
                dimSet = dim.toDimensionValueSet();
                ArrayList<String> dimSetOrgList = new ArrayList<String>();
                dimSetOrgList.add((String)dimSet.getValue(dimensionName));
                dimSet.setValue(dimensionName, dimSetOrgList);
                fmlDimensionValueSetMap.put(key, dimSet);
                continue;
            }
            List dimSetOrgList = (List)dimensionValueSet.getValue(dimensionName);
            dimSetOrgList.add((String)dim.getValue(dimensionName));
        }
        if (param.getAsyncTaskMonitor().isCancel()) {
            logger.info("\u5ba1\u6838\u9879\uff1a\uff1a\u6570\u636e\u5206\u6790:\u5df2\u4e2d\u65ad");
            return null;
        }
        com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = new com.jiuqi.np.dataengine.executors.ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, param.getContext().getFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        HashMap<String, List<OrgDimSetDTO>> fmlOrgDimSetDTOMap = new HashMap<String, List<OrgDimSetDTO>>();
        for (String key : fmlDimensionValueSetMap.keySet()) {
            if (param.getAsyncTaskMonitor().isCancel()) {
                logger.info("\u5ba1\u6838\u9879\uff1a\uff1a\u6570\u636e\u5206\u6790:\u5df2\u4e2d\u65ad");
                return null;
            }
            dimSet = (DimensionValueSet)fmlDimensionValueSetMap.get(key);
            Map fmlOrgMap = mainDimFilter.filterByFormulas(executorContext, dimSet, formulas);
            if (CollectionUtils.isEmpty(fmlOrgMap)) continue;
            for (String fml : fmlOrgMap.keySet()) {
                List errorOrgList = (List)fmlOrgMap.get(fml);
                if (CollectionUtils.isEmpty(errorOrgList)) continue;
                OrgDimSetDTO dto = new OrgDimSetDTO(dimSet);
                ArrayList<OrgDimSetDTO> orgDimSetDTOList = (ArrayList<OrgDimSetDTO>)fmlOrgDimSetDTOMap.get(fml);
                if (orgDimSetDTOList == null) {
                    orgDimSetDTOList = new ArrayList<OrgDimSetDTO>();
                    fmlOrgDimSetDTOMap.put(fml, orgDimSetDTOList);
                }
                dto.setErrorOrgList(new HashSet<String>(errorOrgList));
                orgDimSetDTOList.add(dto);
            }
        }
        return fmlOrgDimSetDTOMap;
    }

    private void buildFailedOrgInfo(Map<String, FailedOrgInfo> failedOrgs, String org) {
        FailedOrgInfo failedOrgInfo = failedOrgs.get(org);
        if (failedOrgInfo == null) {
            failedOrgInfo = new FailedOrgInfo();
            failedOrgs.put(org, failedOrgInfo);
            failedOrgInfo.setDesc("1\u4e2a\u6a21\u677f\u5ba1\u6838\u4e0d\u901a\u8fc7");
        } else {
            String msg = failedOrgInfo.getDesc();
            int count = Integer.valueOf(msg.replace(DESCRIBE, ""));
            failedOrgInfo.setDesc(++count + DESCRIBE);
        }
    }

    @NotNull
    private MultcheckResOrg initResOrg(CheckItemParam param, List<MultcheckResOrg> resOrgs, AnalyzeModel model, String org) {
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

    private String getSameDimContent(List<String> dimsList, String currentDimStr, List<MCErrorDescription> mcErrorDescriptions) {
        for (MCErrorDescription error : mcErrorDescriptions) {
            Map errorDims = error.getDims();
            StringBuilder errorDimStr = new StringBuilder();
            for (String dim : dimsList) {
                errorDimStr.append(dim).append("=").append((String)errorDims.get(dim));
            }
            if (!errorDimStr.toString().equals(currentDimStr)) continue;
            return error.getDescription();
        }
        return null;
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
            dimInfo.setDesc("1\u4e2a\u6a21\u677f\u5ba1\u6838\u4e0d\u901a\u8fc7");
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
                dimInfo.setDesc("1\u4e2a\u6a21\u677f\u5ba1\u6838\u4e0d\u901a\u8fc7");
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

    public PluginInfo getResultPlugin() {
        return new PluginInfo("@nr", "nr-datacheck-dataanalyze-plugin", "Result");
    }
}

