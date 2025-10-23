/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.bpm.setting.utils.SettingUtil
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datacheck.dataanalyze.AnalyzeModel
 *  com.jiuqi.nr.datacheck.dataanalyze.AnalyzeModelAuth
 *  com.jiuqi.nr.datacheck.dataanalyze.CheckCondition
 *  com.jiuqi.nr.datacheck.dataanalyze.OrgSelectType
 *  com.jiuqi.nr.datacheck.dataanalyze.vo.AnalyzeParam
 *  com.jiuqi.nr.datacheck.dataanalyze.vo.BatchDelErrorParam
 *  com.jiuqi.nr.datacheck.dataanalyze.vo.CheckModelState
 *  com.jiuqi.nr.datacheck.dataanalyze.vo.CheckUnitState
 *  com.jiuqi.nr.datacheck.dataanalyze.vo.ErrorNode
 *  com.jiuqi.nr.datacheck.dataanalyze.vo.ItemOrgParam
 *  com.jiuqi.nr.datacheck.dataanalyze.vo.OrgStateVO
 *  com.jiuqi.nr.datacheck.dataanalyze.vo.ResourceNodeVO
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
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
 *  com.jiuqi.nr.multcheck2.bean.MultcheckResOrg
 *  com.jiuqi.nr.multcheck2.dynamic.IMCContextService
 *  com.jiuqi.nr.multcheck2.dynamic.IMCErrorInfoService
 *  com.jiuqi.nr.multcheck2.dynamic.IMCItemOrgService
 *  com.jiuqi.nr.multcheck2.service.IMCEnvService
 *  com.jiuqi.nr.multcheck2.service.IMCResultService
 *  com.jiuqi.nr.multcheck2.service.dto.MCOrgTreeDTO
 *  com.jiuqi.nr.multcheck2.web.vo.MCLabel
 *  com.jiuqi.nr.multcheck2.web.vo.OrgTreeNode
 *  com.jiuqi.nr.singlequeryimport.auth.share.bean.ModalNodeInfo
 *  com.jiuqi.nr.singlequeryimport.bean.QueryModel
 *  com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 *  com.jiuqi.nvwa.dataanalyze.service.ResourceTreeContext
 *  com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService
 *  com.jiuqi.util.StringUtils
 *  javax.validation.Valid
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.json.JSONObject
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.singlequery.multcheck.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.bpm.setting.utils.SettingUtil;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datacheck.dataanalyze.AnalyzeModel;
import com.jiuqi.nr.datacheck.dataanalyze.AnalyzeModelAuth;
import com.jiuqi.nr.datacheck.dataanalyze.CheckCondition;
import com.jiuqi.nr.datacheck.dataanalyze.OrgSelectType;
import com.jiuqi.nr.datacheck.dataanalyze.vo.AnalyzeParam;
import com.jiuqi.nr.datacheck.dataanalyze.vo.BatchDelErrorParam;
import com.jiuqi.nr.datacheck.dataanalyze.vo.CheckModelState;
import com.jiuqi.nr.datacheck.dataanalyze.vo.CheckUnitState;
import com.jiuqi.nr.datacheck.dataanalyze.vo.ErrorNode;
import com.jiuqi.nr.datacheck.dataanalyze.vo.ItemOrgParam;
import com.jiuqi.nr.datacheck.dataanalyze.vo.OrgStateVO;
import com.jiuqi.nr.datacheck.dataanalyze.vo.ResourceNodeVO;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
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
import com.jiuqi.nr.multcheck2.bean.MultcheckResOrg;
import com.jiuqi.nr.multcheck2.dynamic.IMCContextService;
import com.jiuqi.nr.multcheck2.dynamic.IMCErrorInfoService;
import com.jiuqi.nr.multcheck2.dynamic.IMCItemOrgService;
import com.jiuqi.nr.multcheck2.service.IMCEnvService;
import com.jiuqi.nr.multcheck2.service.IMCResultService;
import com.jiuqi.nr.multcheck2.service.dto.MCOrgTreeDTO;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import com.jiuqi.nr.multcheck2.web.vo.OrgTreeNode;
import com.jiuqi.nr.singlequery.multcheck.checkdes.ISingleQueryCheckDesValidator;
import com.jiuqi.nr.singlequery.multcheck.checkdes.ISingleQueryDesValidatorProvider;
import com.jiuqi.nr.singlequery.multcheck.checkdes.SingleQueryContext;
import com.jiuqi.nr.singlequery.multcheck.vo.ErrorCheckParam;
import com.jiuqi.nr.singlequery.multcheck.vo.ErrorParam;
import com.jiuqi.nr.singlequery.multcheck.vo.QueryCheckModel;
import com.jiuqi.nr.singlequery.multcheck.vo.QueryCheckParam;
import com.jiuqi.nr.singlequery.multcheck.vo.SingleQueryErrorInfo;
import com.jiuqi.nr.singlequery.multcheck.vo.SingleQueryResult;
import com.jiuqi.nr.singlequeryimport.auth.share.bean.ModalNodeInfo;
import com.jiuqi.nr.singlequeryimport.bean.QueryModel;
import com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import com.jiuqi.nvwa.dataanalyze.service.ResourceTreeContext;
import com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/bmjs/multcheck/querycheck"})
public class QueryCheckController {
    private static final Logger logger = LoggerFactory.getLogger(QueryCheckController.class);
    public static final String CONFIRM_DESC = "CONFIRM-TRUE";
    public static final String TYPE_UNIT = "unit";
    public static final String TYPE_SCHEME = "scheme";
    private static final String muduleTitle = "\u6a2a\u5411\u8fc7\u5f55\u8868";
    @Autowired
    QueryModeleDao queryModeleDao;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    private ResourceTreeNodeService resourceTreeNodeService;
    @Autowired
    private IMCErrorInfoService errorInfoService;
    @Autowired
    private IMCEnvService envService;
    @Autowired
    IMCItemOrgService orgService;
    @Autowired
    IMCResultService resultService;
    @Autowired
    IEntityMetaService entityMetaService;
    @Autowired
    protected IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired(required=false)
    private List<ISingleQueryDesValidatorProvider> checkDesValidatorProviders;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private IMCContextService mcContextService;

    @PostMapping(value={"/get-tree"})
    public Map<String, Object> getTree(@RequestBody @Valid QueryCheckParam queryCheckParam) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        QueryCheckModel rootModel = new QueryCheckModel("root", "root", "\u6a2a\u5411\u8fc7\u5f55\u8868\u67e5\u8be2", "GROUP", "");
        ITree root = new ITree((INode)rootModel);
        root.setExpanded(true);
        ArrayList<ITree<QueryCheckModel>> nodeList = new ArrayList<ITree<QueryCheckModel>>();
        String taskKey = queryCheckParam.getTaskKey();
        if (!StringUtils.isNotEmpty((String)taskKey)) {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(queryCheckParam.getFormSchemeKey());
            taskKey = formScheme.getTaskKey();
        }
        List checkModel = this.queryModeleDao.getCheckModel(taskKey, queryCheckParam.getFormSchemeKey());
        Map<String, List<QueryModel>> modelGroups = checkModel.stream().filter(model -> StringUtils.isNotEmpty((String)model.getItemTitle())).collect(Collectors.groupingBy(m -> m.getGroup()));
        for (String key : modelGroups.keySet()) {
            QueryModel group = new QueryModel();
            group.setKey(UUID.randomUUID().toString());
            group.setItemTitle(key);
            ArrayList<ITree<QueryCheckModel>> groupChildren = new ArrayList<ITree<QueryCheckModel>>();
            List<QueryModel> modelList = modelGroups.get(key);
            for (QueryModel model2 : modelList) {
                if (!StringUtils.isNotEmpty((String)model2.getItemTitle())) continue;
                ITree<QueryCheckModel> node = this.convertToNode(model2, true, null, "querycheck");
                groupChildren.add(node);
            }
            ITree<QueryCheckModel> groupNode = this.convertToNode(group, false, null, "GROUP");
            groupNode.setChildren(groupChildren);
            nodeList.add(groupNode);
        }
        root.setChildren(nodeList.stream().filter(e -> !CollectionUtils.isEmpty(e.getChildren())).collect(Collectors.toList()));
        map.put("tree", Collections.singletonList(root));
        ArrayList resultList = new ArrayList();
        map.put("resultList", resultList);
        return map;
    }

    @PostMapping(value={"/get-nodes"})
    public List<AnalyzeModelAuth> getNodes(@RequestBody List<AnalyzeModel> models) throws Exception {
        ArrayList<AnalyzeModelAuth> nodes = new ArrayList<AnalyzeModelAuth>();
        for (AnalyzeModel model : models) {
            nodes.add(new AnalyzeModelAuth(model, true));
        }
        return nodes;
    }

    @PostMapping(value={"/init"})
    public SingleQueryResult getModels(@RequestBody @Valid AnalyzeParam param) throws Exception {
        HashMap<String, List<MultcheckResOrg>> orgResMap;
        Object dimValues;
        long startTime = System.nanoTime();
        SingleQueryResult res = new SingleQueryResult();
        HashMap<String, ResourceNodeVO> modelMap = new HashMap<String, ResourceNodeVO>();
        ArrayList<ResourceNodeVO> nodes = new ArrayList<ResourceNodeVO>();
        List models = param.getModels();
        List queryModels = this.queryModeleDao.getModalNodeInfosByKeys(param.getModels());
        for (ModalNodeInfo model : queryModels) {
            ResourceTreeNode treeNode = new ResourceTreeNode();
            treeNode.setGuid(model.getModalId());
            treeNode.setName(model.getTitle());
            treeNode.setTitle(model.getTitle());
            treeNode.setType("querycheck");
            ResourceNodeVO resource = new ResourceNodeVO(treeNode);
            nodes.add(resource);
            modelMap.put(model.getModalId(), resource);
        }
        res.setTabs(nodes);
        String task = param.getTask();
        String period = param.getPeriod();
        HashMap<String, DimensionValue> dimSet = new HashMap<String, DimensionValue>();
        for (String key : param.getDimSet().keySet()) {
            DimensionValue dimensionValue = new DimensionValue();
            String value = (String)param.getDimSet().get(key);
            dimensionValue.setValue(value);
            dimensionValue.setName(key);
            dimSet.put(key, dimensionValue);
        }
        List orgCodes = param.getOrgCodes();
        HashSet<String> orgCodesSet = new HashSet<String>(orgCodes);
        List labelList = this.mcContextService.getOrgLabels(task, period, param.getOrgEntity(), orgCodes);
        Map<String, MCLabel> orgMap = labelList.stream().collect(Collectors.toMap(MCLabel::getCode, l -> l));
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(task);
        List dimsList = this.mcContextService.getDynamicFieldsByTask(task);
        HashMap<String, Map<String, String>> entityValues = new HashMap<String, Map<String, String>>();
        boolean hasDim = false;
        if (!CollectionUtils.isEmpty(dimsList)) {
            hasDim = true;
            HashMap<String, String> entitys = new HashMap<String, String>();
            res.setEntitys(entitys);
            for (String dimKey : dimsList) {
                if ("ADJUST".equals(dimKey)) continue;
                DimensionValue dimensionValue = (DimensionValue)dimSet.get(dimKey);
                EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(dimKey);
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(dimKey);
                entitys.put(dimKey, entityDefine.getTitle());
                dimValues = new HashMap();
                entityValues.put(dimKey, (Map<String, String>)dimValues);
                DimensionValueSet dimensionValueSet = new DimensionValueSet();
                dimensionValueSet.setValue("DATATIME", (Object)period);
                if (dimensionValue != null && org.springframework.util.StringUtils.hasText(dimensionValue.getValue()) && !"PROVIDER_BASECURRENCY".equals(dimensionValue.getValue()) && !"PROVIDER_PBASECURRENCY".equals(dimensionValue.getValue())) {
                    dimensionValueSet.setValue(dimKey, Arrays.asList(dimensionValue.getValue().split(";")));
                }
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
        if (TYPE_SCHEME.equals(param.getType())) {
            ArrayList<ITree<ErrorNode>> modelNodes = new ArrayList<ITree<ErrorNode>>();
            ITree modelRoot = new ITree();
            ArrayList<ITree> modelNodeChild = new ArrayList<ITree>();
            res.setModels(modelNodes);
            modelNodes.add((ITree<ErrorNode>)modelRoot);
            modelRoot.setChildren(modelNodeChild);
            modelRoot.setKey("ROOT");
            modelRoot.setCode("ROOT");
            modelRoot.setTitle("\u5168\u90e8\u6a21\u677f");
            modelRoot.setLeaf(false);
            modelRoot.setExpanded(true);
            modelRoot.setSelected(true);
            orgResMap = new HashMap<String, List<MultcheckResOrg>>();
            HashMap<String, Integer> modelState = new HashMap<String, Integer>();
            List orgResList = this.orgService.getByItem(param.getRecordKey(), param.getItemKey(), param.getTask());
            if (!CollectionUtils.isEmpty(orgResList)) {
                dimValues = orgResList.iterator();
                while (dimValues.hasNext()) {
                    MultcheckResOrg orgRes = (MultcheckResOrg)dimValues.next();
                    if (!models.contains(orgRes.getResource())) continue;
                    this.addOrgRes(orgResMap, orgRes);
                    this.addModelState(modelState, orgRes.getResource(), orgRes.getResult());
                }
            }
            HashMap<String, List<MCErrorDescription>> errorMap = new HashMap<String, List<MCErrorDescription>>();
            HashMap<String, ArrayList<SingleQueryErrorInfo>> errorVoMap = new HashMap<String, ArrayList<SingleQueryErrorInfo>>();
            for (String model : models) {
                ResourceNodeVO resource = (ResourceNodeVO)modelMap.get(model);
                ITree modelNode = new ITree();
                modelNode.setCode(resource.getGuid());
                modelNode.setKey(resource.getGuid());
                modelNode.setTitle(resource.getTitle());
                modelNode.setLeaf(true);
                modelNodeChild.add(modelNode);
                Integer state = (Integer)modelState.get(model);
                this.buildModelState(param, resource, String.valueOf(state));
                String icon_type = "com.jiuqi.nr.zbquery.manage".equals(resource.getType()) ? "#icon-16_DH_A_NR_guoluchaxun" : "#icon16_SHU_A_NW_kuaisubaobiao";
                String icon_state = "";
                switch (resource.getState()) {
                    case SUCCESS: {
                        icon_state = "#icon-J_HXGLB_C_ZFJS_GreenDot";
                        break;
                    }
                    case SUCCESS_ERROR: {
                        icon_state = "#icon-J_HXGLB_C_ZFJS_YellowDot";
                        break;
                    }
                    default: {
                        icon_state = "#icon-J_HXGLB_C_ZFJS_RedDot";
                    }
                }
                modelNode.setIcons(new String[]{icon_state});
                this.buildResoureErrorMap(task, period, orgCodesSet, errorMap, model);
                DimensionCollection dimensionCollection = this.envService.buildDimensionCollection(task, period, orgCodes, dimSet);
                List combinations = dimensionCollection.getDimensionCombinations();
                for (DimensionCombination combination : combinations) {
                    String org = (String)combination.getDWDimensionValue().getValue();
                    String key = org + "@" + model;
                    SingleQueryErrorInfo error2 = this.createError(param, dimsList, entityValues, hasDim, errorMap, orgResMap, combination, model, orgMap, modelMap);
                    ArrayList<SingleQueryErrorInfo> analyzeErrors = (ArrayList<SingleQueryErrorInfo>)errorVoMap.get(key);
                    if (analyzeErrors == null) {
                        analyzeErrors = new ArrayList<SingleQueryErrorInfo>();
                        errorVoMap.put(key, analyzeErrors);
                    }
                    analyzeErrors.add(error2);
                }
            }
            ArrayList<SingleQueryErrorInfo> SingleQueryErrorInfoList = new ArrayList<SingleQueryErrorInfo>();
            for (MCLabel o : labelList) {
                for (String model : models) {
                    SingleQueryErrorInfoList.addAll((Collection)errorVoMap.get(o.getCode() + "@" + model));
                }
            }
            res.setErrors(SingleQueryErrorInfoList);
        } else {
            HashMap<String, List<MCErrorDescription>> errorMap = new HashMap<String, List<MCErrorDescription>>();
            ArrayList<SingleQueryErrorInfo> analyzeErrorList = new ArrayList<SingleQueryErrorInfo>();
            res.setErrors(analyzeErrorList);
            List errors = this.errorInfoService.getByOrg(task, period, "querycheck", (String)orgCodes.get(0));
            if (!CollectionUtils.isEmpty(errors)) {
                for (MCErrorDescription error3 : errors) {
                    if (!models.contains(error3.getResource())) continue;
                    this.addError(errorMap, error3);
                }
            }
            orgResMap = new HashMap();
            List orgResList = this.orgService.getByOrg(param.getRecordKey(), param.getItemKey(), param.getTask(), (String)orgCodes.get(0));
            if (!CollectionUtils.isEmpty(orgResList)) {
                for (MultcheckResOrg orgRes : orgResList) {
                    if (!models.contains(orgRes.getResource())) continue;
                    this.addOrgRes(orgResMap, orgRes);
                    this.buildModelState(param, (ResourceNodeVO)modelMap.get(orgRes.getResource()), orgRes.getResult());
                }
            }
            DimensionCollection dimensionCollection = this.envService.buildDimensionCollection(task, period, orgCodes, dimSet);
            List combinations = dimensionCollection.getDimensionCombinations();
            for (DimensionCombination combination : combinations) {
                for (String model : models) {
                    analyzeErrorList.add(this.createError(param, dimsList, entityValues, hasDim, errorMap, orgResMap, combination, model, orgMap, modelMap));
                }
            }
        }
        Map<String, Set<String>> resMap = this.getOrgCurrent(param.getRecordKey());
        res.getErrors().removeIf(error -> {
            Set orgSet = (Set)resMap.get(error.getResource());
            return orgSet != null && orgSet.contains(error.getOrg());
        });
        this.errorDesCheck(res.getErrors(), param.getItemKey());
        long endTime = System.nanoTime();
        long durationNano = endTime - startTime;
        double durationMillis = (double)durationNano / 1000000.0;
        logger.info("\u8017\u65f6: {} \u7eb3\u79d2 ({} \u6beb\u79d2)", (Object)durationNano, (Object)durationMillis);
        return res;
    }

    public Map<String, Set<String>> getOrgCurrent(String key) {
        String sql = "SELECT SQM_RESULT FROM SYS_SINGLE_QUERY_MUlTCHECK WHERE SQM_KEY = ?";
        ObjectMapper objectMapper = new ObjectMapper();
        return (Map)this.jdbcTemplate.queryForObject(sql, new Object[]{key}, (rs, rowNum) -> {
            try {
                String json = rs.getString("SQM_RESULT");
                return (Map)objectMapper.readValue(json, (TypeReference)new TypeReference<Map<String, Set<String>>>(){});
            }
            catch (Exception e) {
                throw new RuntimeException("Failed to read data from SYS_SINGLE_QUERY_MUlTCHECK", e);
            }
        });
    }

    private void buildModelState(AnalyzeParam param, ResourceNodeVO resourceNodeVO, String result) {
        if (param.getCheckRequires() == null) {
            if ("TRUE".equals(result)) {
                resourceNodeVO.setState(CheckModelState.SUCCESS);
            } else if ("FALSE".equals(result)) {
                resourceNodeVO.setState(CheckModelState.FAILED);
            }
        } else {
            OrgStateVO state = new OrgStateVO(result);
            if (CheckCondition.NEEDERROR == param.getCheckRequires()) {
                if (state.isOrgRes()) {
                    resourceNodeVO.setState(state.isFmlRes() ? CheckModelState.SUCCESS : CheckModelState.SUCCESS_ERROR);
                } else {
                    resourceNodeVO.setState(CheckModelState.FAILED);
                }
            } else {
                resourceNodeVO.setState(state.isOrgRes() ? CheckModelState.SUCCESS : CheckModelState.FAILED);
            }
        }
    }

    private void addModelState(Map<String, Integer> modelState, String model, String result) {
        Integer state = modelState.get(model);
        if ("TRUE".equals(result)) {
            result = "7";
        } else if ("FALSE".equals(result)) {
            result = "0";
        }
        if (state == null) {
            modelState.put(model, Integer.valueOf(result));
        } else {
            int res = state & Integer.valueOf(result);
            modelState.put(model, res);
        }
    }

    @PostMapping(value={"/get-models"})
    public List<ResourceNodeVO> getModels(@RequestBody List<String> guids) throws Exception {
        ArrayList<ResourceNodeVO> nodes = new ArrayList<ResourceNodeVO>();
        List models = this.queryModeleDao.getModalNodeInfosByKeys(guids);
        for (ModalNodeInfo model : models) {
            ResourceTreeNode treeNode = new ResourceTreeNode();
            treeNode.setGuid(model.getModalId());
            treeNode.setName(model.getTitle());
            treeNode.setTitle(model.getTitle());
            treeNode.setType("querycheck");
            nodes.add(new ResourceNodeVO(treeNode));
        }
        return nodes;
    }

    @PostMapping(value={"/get-success-org-resource"})
    public Map<String, String> getItemOrgSuccess(@RequestBody @Valid ItemOrgParam param) {
        HashMap<String, String> res = new HashMap<String, String>();
        HashMap dims = new HashMap();
        if (!CollectionUtils.isEmpty(param.getDimSet())) {
            for (Map.Entry entry : param.getDimSet().entrySet()) {
                dims.put(entry.getKey(), ((DimensionValue)entry.getValue()).getValue());
            }
        }
        for (String string : param.getOrgs()) {
            List orgResList = this.orgService.getByOrg(param.getRecordKey(), param.getItemKey(), param.getTask(), "querycheck", string, dims);
            String successModels = "";
            if (!CollectionUtils.isEmpty(orgResList)) {
                for (MultcheckResOrg orgRes : orgResList) {
                    if (!orgRes.getResult().equals("TRUE")) continue;
                    successModels = successModels + orgRes.getResource() + ";";
                }
            }
            res.put(string, successModels);
        }
        return res;
    }

    @GetMapping(value={"/get-model-org/{task}/{period}/{org}/{orgSelectType}"})
    public List<String> getOrgModels(@PathVariable String task, @PathVariable String period, @PathVariable String org, @PathVariable OrgSelectType orgSelectType) throws Exception {
        ArrayList<String> res = new ArrayList<String>();
        IEntityTable entityTable = this.getEntityTable(task, period);
        res.add(org);
        List childRows = null;
        if (OrgSelectType.UCURRENTDIRECTSUB == orgSelectType) {
            childRows = entityTable.getChildRows(org);
        } else if (OrgSelectType.UCURRENTALLSUB == orgSelectType) {
            childRows = entityTable.getAllChildRows(org);
        }
        if (!CollectionUtils.isEmpty(childRows)) {
            res.addAll(childRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toSet()));
        }
        return res;
    }

    private IEntityTable getEntityTable(String taskKey, String period) throws Exception {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        EntityViewDefine entityView = this.runTimeViewController.getViewByTaskDefineKey(taskKey);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.sorted(true);
        query.setEntityView(entityView);
        query.setMasterKeys(dimensionValueSet);
        query.setAuthorityOperations(AuthorityType.Read);
        query.sortedByQuery(false);
        SchemePeriodLinkDefine scheme = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, taskKey);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, scheme.getSchemeKey());
        context.setEnv((IFmlExecEnvironment)environment);
        context.setVarDimensionValueSet(dimensionValueSet);
        context.setPeriodView(taskDefine.getDateTime());
        return query.executeFullBuild((IContext)context);
    }

    @PostMapping(value={"/get-org-tree"})
    public List<ITree<OrgTreeNode>> getOrgTree(@RequestBody AnalyzeParam param) throws Exception {
        MCOrgTreeDTO orgTreeDTO = this.mcContextService.getOrgTreeByTaskPeriodOrg(param.getTask(), param.getPeriod(), param.getOrgEntity(), param.getFormScheme(), param.getOrgCodes());
        return orgTreeDTO.getTreeList();
    }

    @PostMapping(value={"/error-des-check/{itemKey}"})
    public List<SingleQueryErrorInfo> errorDesCheck(@RequestBody List<SingleQueryErrorInfo> errorInfos, @PathVariable String itemKey) {
        if (this.checkDesValidatorProviders != null && this.checkDesValidatorProviders.size() > 0) {
            List<ISingleQueryCheckDesValidator> checkDesValidators = this.getCheckDesValidators(itemKey);
            for (SingleQueryErrorInfo error : errorInfos) {
                if (StringUtils.isEmpty((String)error.getDescription())) continue;
                for (ISingleQueryCheckDesValidator validator : checkDesValidators) {
                    validator.validate(error);
                }
            }
        }
        return errorInfos;
    }

    @PostMapping(value={"/error-des-check-add"})
    public String errorDesCheckAdd(@RequestBody @Valid ErrorCheckParam errorCheckParam) {
        SingleQueryErrorInfo errorInfo = new SingleQueryErrorInfo();
        errorInfo.setDescription(errorCheckParam.getErrorDescription());
        boolean isValid = true;
        if (this.checkDesValidatorProviders != null && this.checkDesValidatorProviders.size() > 0) {
            List<ISingleQueryCheckDesValidator> checkDesValidators = this.getCheckDesValidators(errorCheckParam.getItemKey());
            if (!StringUtils.isEmpty((String)errorInfo.getDescription())) {
                for (ISingleQueryCheckDesValidator validator : checkDesValidators) {
                    isValid = validator.validate(errorInfo);
                }
            }
        }
        if (!isValid) {
            return errorInfo.getDesErrorReason();
        }
        return "TRUE";
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

    private void buildResoureErrorMap(String task, String period, Set<String> orgCodes, Map<String, List<MCErrorDescription>> errorMap, String model) throws Exception {
        List errors = this.errorInfoService.getByResource(task, period, "querycheck", model);
        if (!CollectionUtils.isEmpty(errors)) {
            for (MCErrorDescription error : errors) {
                if (!orgCodes.contains(error.getOrg())) continue;
                this.addError(errorMap, error);
            }
        }
    }

    private SingleQueryErrorInfo createError(AnalyzeParam param, List<String> dimsList, Map<String, Map<String, String>> entityValues, boolean hasDim, Map<String, List<MCErrorDescription>> errorMap, Map<String, List<MultcheckResOrg>> orgResMap, DimensionCombination combination, String model, Map<String, MCLabel> orgMap, Map<String, ResourceNodeVO> modelMap) {
        String task = param.getTask();
        String period = param.getPeriod();
        CheckCondition checkRequires = param.getCheckRequires();
        String org = (String)combination.getDWDimensionValue().getValue();
        String key = org + "@" + model;
        List<MultcheckResOrg> orgResList = orgResMap.get(key);
        ResourceNodeVO resource = modelMap.get(model);
        List<MCErrorDescription> errorList = errorMap.get(key);
        if (CollectionUtils.isEmpty(errorList)) {
            SingleQueryErrorInfo error = this.initError(task, period, org, resource, orgMap);
            this.initErrorDim(dimsList, entityValues, hasDim, combination, error);
            MultcheckResOrg checkOrgRes = hasDim ? this.getSameOrgRes(dimsList, combination, orgResList) : orgResList.get(0);
            error.setState(this.buildOrgResState(checkOrgRes, checkRequires));
            return error;
        }
        if (hasDim) {
            SingleQueryErrorInfo error = null;
            MCErrorDescription sameError = this.getSameError(dimsList, combination, errorList);
            error = sameError == null ? this.initError(task, period, org, resource, orgMap) : this.initErrorByMCError(orgMap, org, resource, sameError);
            this.initErrorDim(dimsList, entityValues, true, combination, error);
            MultcheckResOrg checkOrgRes = this.getSameOrgRes(dimsList, combination, orgResList);
            error.setState(this.buildOrgResState(checkOrgRes, checkRequires));
            return error;
        }
        SingleQueryErrorInfo error = this.initErrorByMCError(orgMap, org, resource, errorList.get(0));
        MultcheckResOrg checkOrgRes = orgResList.get(0);
        error.setState(this.buildOrgResState(checkOrgRes, checkRequires));
        return error;
    }

    private CheckUnitState buildOrgResState(MultcheckResOrg checkOrgRes, CheckCondition checkRequires) {
        String result = checkOrgRes.getResult();
        if (checkRequires == null) {
            if ("TRUE".equals(result)) {
                return CheckUnitState.SUCCESS;
            }
            return CheckUnitState.FAILED;
        }
        if ("TRUE".equals(result)) {
            result = "7";
        } else if ("FALSE".equals(result)) {
            result = "0";
        }
        OrgStateVO state = new OrgStateVO(result);
        if (CheckCondition.NEEDERROR == checkRequires) {
            if (state.isOrgRes()) {
                return state.isFmlRes() ? CheckUnitState.SUCCESS : CheckUnitState.FAILED_ERROR_LEGAL;
            }
            return CheckUnitState.FAILED_ERROR_NODE;
        }
        return state.isOrgRes() ? CheckUnitState.SUCCESS : CheckUnitState.FAILED;
    }

    @NotNull
    private SingleQueryErrorInfo initErrorByMCError(Map<String, MCLabel> orgMap, String org, ResourceNodeVO resource, MCErrorDescription sameError) {
        SingleQueryErrorInfo error = new SingleQueryErrorInfo(sameError);
        error.setOrgStr(orgMap.get(org).getTitle());
        error.setType("\u6a2a\u5411\u8fc7\u5f55\u8868\u67e5\u8be2");
        error.setResourceStr(resource.getTitle());
        return error;
    }

    @Nullable
    private MCErrorDescription getSameError(List<String> dimsList, DimensionCombination combination, List<MCErrorDescription> errorList) {
        MCErrorDescription sameError = null;
        for (MCErrorDescription e : errorList) {
            boolean different = false;
            for (String dim : dimsList) {
                String errorDimValue;
                if (different) break;
                String dimValue = (String)combination.getValue(dim);
                if (dimValue.equals(errorDimValue = (String)e.getDims().get(dim))) continue;
                different = true;
            }
            if (different) continue;
            sameError = e;
            break;
        }
        return sameError;
    }

    private MultcheckResOrg getSameOrgRes(List<String> dimsList, DimensionCombination combination, List<MultcheckResOrg> orgResList) {
        MultcheckResOrg sameRes = null;
        for (MultcheckResOrg resOrg : orgResList) {
            boolean different = false;
            for (String dim : dimsList) {
                String errorDimValue;
                if (different) break;
                String dimValue = (String)combination.getValue(dim);
                if (dimValue.equals(errorDimValue = (String)resOrg.getDims().get(dim))) continue;
                different = true;
            }
            if (different) continue;
            sameRes = resOrg;
            break;
        }
        return sameRes;
    }

    private void initErrorDim(List<String> dimsList, Map<String, Map<String, String>> entityValues, boolean hasDim, DimensionCombination combination, SingleQueryErrorInfo error) {
        if (hasDim) {
            HashMap<String, String> dims = new HashMap<String, String>();
            HashMap<String, String> dimsStr = new HashMap<String, String>();
            error.setDims(dims);
            error.setDimStr(dimsStr);
            for (String dimKey : dimsList) {
                String dimValue = (String)combination.getValue(dimKey);
                dims.put(dimKey, dimValue);
                if ("ADJUST".equals(dimKey)) continue;
                dimsStr.put(dimKey, entityValues.get(dimKey).get(dimValue));
            }
        }
    }

    private SingleQueryErrorInfo initError(String task, String period, String org, ResourceNodeVO resource, Map<String, MCLabel> orgMap) {
        SingleQueryErrorInfo error = new SingleQueryErrorInfo();
        error.setTask(task);
        error.setPeriod(period);
        error.setItemType("querycheck");
        error.setOrg(org);
        error.setOrgStr(orgMap.get(org).getTitle());
        error.setResource(resource.getGuid());
        error.setType("\u6a2a\u5411\u8fc7\u5f55\u8868\u67e5\u8be2");
        error.setResourceStr(resource.getTitle());
        return error;
    }

    private void addOrgRes(Map<String, List<MultcheckResOrg>> orgResMap, MultcheckResOrg orgRes) {
        String key = orgRes.getOrg() + "@" + orgRes.getResource();
        List<MultcheckResOrg> errorList = orgResMap.get(key);
        if (errorList == null) {
            errorList = new ArrayList<MultcheckResOrg>();
            orgResMap.put(key, errorList);
        }
        errorList.add(orgRes);
    }

    private void addError(Map<String, List<MCErrorDescription>> errorMap, MCErrorDescription e) {
        String key = e.getOrg() + "@" + e.getResource();
        List<MCErrorDescription> errorList = errorMap.get(key);
        if (errorList == null) {
            errorList = new ArrayList<MCErrorDescription>();
            errorMap.put(key, errorList);
        }
        errorList.add(e);
    }

    @PostMapping(value={"/add-error"})
    public String addError(@RequestBody @Valid SingleQueryErrorInfo error) {
        MCErrorDescription errorDescription = new MCErrorDescription();
        BeanUtils.copyProperties((Object)error, errorDescription);
        try {
            if (org.springframework.util.StringUtils.hasText(error.getKey())) {
                this.errorInfoService.modify(errorDescription);
                return error.getKey();
            }
            LogHelper.info((String)muduleTitle, (String)"\u6dfb\u52a0\u51fa\u9519\u8bf4\u660e,", (String)(SettingUtil.getCurrentUser().getName() + "\u6267\u884c\u4e86\u6dfb\u52a0\u9519\u8bef\u8bf4\u660e\u64cd\u4f5c"));
            return this.errorInfoService.add(errorDescription);
        }
        catch (Exception e) {
            logger.error("\u6dfb\u52a0\u9519\u8bef\u8bf4\u660e\u5f02\u5e38\uff1a\uff1a", e);
            return "FALSE";
        }
    }

    @PostMapping(value={"/batch-add-errors"})
    @Transactional
    public String addErrors(@RequestBody @Valid ErrorParam param) {
        List<SingleQueryErrorInfo> errors = param.getErrors();
        if (CollectionUtils.isEmpty(errors)) {
            return "\u9009\u62e9\u5355\u4f4d\u6a21\u677f\u4e3a\u7a7a\uff01";
        }
        ArrayList<MCErrorDescription> adds = new ArrayList<MCErrorDescription>();
        ArrayList<String> keys = new ArrayList<String>();
        for (SingleQueryErrorInfo error : errors) {
            MCErrorDescription errorDescription = new MCErrorDescription();
            BeanUtils.copyProperties((Object)error, errorDescription);
            if (org.springframework.util.StringUtils.hasText(error.getDescription()) && org.springframework.util.StringUtils.hasText(error.getKey())) {
                if (!param.isCover()) continue;
                keys.add(errorDescription.getKey());
                errorDescription.setDescription(param.getDescription());
                adds.add(errorDescription);
                continue;
            }
            errorDescription.setDescription(param.getDescription());
            adds.add(errorDescription);
        }
        try {
            if (!CollectionUtils.isEmpty(keys)) {
                this.errorInfoService.batchDeleteByKeys(keys, param.getTask());
            }
            if (!CollectionUtils.isEmpty(adds)) {
                this.errorInfoService.batchAdd(adds, param.getTask());
                LogHelper.info((String)muduleTitle, (String)"\u6dfb\u52a0\u51fa\u9519\u8bf4\u660e,", (String)(SettingUtil.getCurrentUser().getName() + "\u6267\u884c\u4e86\u6279\u91cf\u6dfb\u52a0\u9519\u8bef\u8bf4\u660e\u64cd\u4f5c"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "TRUE";
    }

    @GetMapping(value={"/del-error/{task}/{key}"})
    public String delError(@PathVariable String task, @PathVariable String key) {
        try {
            this.errorInfoService.deleteByKey(key, task);
            LogHelper.info((String)muduleTitle, (String)"\u5220\u9664\u9519\u8bef\u8bf4\u660e,", (String)(SettingUtil.getCurrentUser().getName() + "\u6267\u884c\u4e86\u5220\u9664\u9519\u8bef\u8bf4\u660e\u64cd\u4f5c"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "TRUE";
    }

    @PostMapping(value={"/batch-del-error"})
    public String delError(@RequestBody @Valid BatchDelErrorParam param) {
        try {
            this.errorInfoService.batchDeleteByOrgAndModel(param.getModelKeys(), param.getOrgCodes(), param.getTask(), param.getPeriod(), "querycheck");
            LogHelper.info((String)muduleTitle, (String)"\u5220\u9664\u9519\u8bef\u8bf4\u660e,", (String)(SettingUtil.getCurrentUser().getName() + "\u6267\u884c\u4e86\u6279\u91cf\u5220\u9664\u9519\u8bef\u8bf4\u660e\u64cd\u4f5c"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "TRUE";
    }

    @PostMapping(value={"/batch-del-error/keys/{task}"})
    public String delError(@PathVariable String task, @RequestBody List<String> keys) {
        try {
            this.errorInfoService.batchDeleteByKeys(keys, task);
            LogHelper.info((String)muduleTitle, (String)"\u5220\u9664\u9519\u8bef\u8bf4\u660e,", (String)(SettingUtil.getCurrentUser().getName() + "\u6267\u884c\u4e86\u6279\u91cf\u5220\u9664\u9519\u8bef\u8bf4\u660e\u64cd\u4f5c"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "TRUE";
    }

    private ITree<QueryCheckModel> convertToNode(QueryModel model, boolean leaf, String[] icons, String type) {
        QueryCheckModel treeNode = new QueryCheckModel(model.getKey(), model.getItemTitle(), model.getItemTitle(), type, this.getFormula(model.getItem()));
        ITree node = new ITree((INode)treeNode);
        node.setExpanded(false);
        node.setLeaf(leaf);
        if (icons != null) {
            node.setIcons(icons);
        }
        return node;
    }

    private String getFormula(String content) {
        if (StringUtils.isEmpty((String)content)) {
            return null;
        }
        JSONObject items = new JSONObject(content);
        if (items.getJSONObject("filter").has("formulaContent")) {
            return items.getJSONObject("filter").getString("formulaContent");
        }
        return null;
    }

    private ResourceTreeContext buildContext() {
        ResourceTreeContext context = new ResourceTreeContext();
        context.setUserId(NpContextHolder.getContext().getIdentityId());
        context.setPrivilegeId("dataanalysis_read");
        return context;
    }
}

