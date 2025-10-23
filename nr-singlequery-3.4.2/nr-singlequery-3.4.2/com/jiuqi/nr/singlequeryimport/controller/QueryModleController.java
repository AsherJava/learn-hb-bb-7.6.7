/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData
 *  com.jiuiqi.nr.unit.treebase.web.service.IUnitTreeDataService
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.nr.dataentry.paramInfo.FormSchemeData
 *  com.jiuqi.nr.dataentry.paramInfo.TaskData
 *  com.jiuqi.nr.dataentry.service.IDataEntryParamService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException
 *  com.jiuqi.nr.singlequeryimport.intf.utils.ResultObject
 *  com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet
 *  com.jiuqi.nr.unit.uselector.source.IUSelectorDataSource
 *  com.jiuqi.nr.unit.uselector.source.IUSelectorDataSourceHelper
 *  com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider
 *  com.jiuqi.nr.unit.uselector.web.IUSelectorInitController
 *  com.jiuqi.nr.unit.uselector.web.IUSelectorTreeDataController
 *  com.jiuqi.nr.unit.uselector.web.response.ContextInfo
 *  com.jiuqi.nr.zbquery.model.ConditionValues
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.server.util.ParameterConvertor
 *  com.jiuqi.util.OrderGenerator
 *  com.jiuqi.va.datamodel.service.VaDataModelPublishedService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.singlequeryimport.controller;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuiqi.nr.unit.treebase.web.service.IUnitTreeDataService;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.nr.dataentry.paramInfo.FormSchemeData;
import com.jiuqi.nr.dataentry.paramInfo.TaskData;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.singlequeryimport.bean.ParamVo.ChangeGroupNameParams;
import com.jiuqi.nr.singlequeryimport.bean.ParamVo.DataQueryParams;
import com.jiuqi.nr.singlequeryimport.bean.ParamVo.QueryLevelsParams;
import com.jiuqi.nr.singlequeryimport.bean.ParamVo.QueryModelVO;
import com.jiuqi.nr.singlequeryimport.bean.ParamVo.RepeatedJudgmentParams;
import com.jiuqi.nr.singlequeryimport.bean.QueryConfigInfo;
import com.jiuqi.nr.singlequeryimport.bean.QueryModel;
import com.jiuqi.nr.singlequeryimport.bean.QueryModelNode;
import com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao;
import com.jiuqi.nr.singlequeryimport.intf.utils.ResultObject;
import com.jiuqi.nr.singlequeryimport.parameter.BuildParam;
import com.jiuqi.nr.singlequeryimport.parameter.ParameterBuilder;
import com.jiuqi.nr.singlequeryimport.service.QueryByCustomService;
import com.jiuqi.nr.singlequeryimport.service.QueryModleService;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSource;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSourceHelper;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import com.jiuqi.nr.unit.uselector.web.IUSelectorInitController;
import com.jiuqi.nr.unit.uselector.web.IUSelectorTreeDataController;
import com.jiuqi.nr.unit.uselector.web.response.ContextInfo;
import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.server.util.ParameterConvertor;
import com.jiuqi.util.OrderGenerator;
import com.jiuqi.va.datamodel.service.VaDataModelPublishedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"\u67e5\u8be2\u6a21\u677f"})
@RequestMapping(value={"/queryModel"})
public class QueryModleController {
    Logger logger = LoggerFactory.getLogger(QueryModleController.class);
    @Autowired
    IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    IRunTimeViewController iRunTimeViewController;
    @Autowired
    QueryModleService queryModleService;
    @Autowired
    QueryByCustomService queryByCustom;
    @Autowired
    IDataEntryParamService dataEntryParamService;
    @Autowired
    VaDataModelPublishedService dataModelService;
    @Autowired
    IUSelectorInitController iuSelectorInitController;
    @Autowired
    IUSelectorTreeDataController iuSelectorTreeDataController;
    @Autowired
    private ParameterBuilder parameterBuilder;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    private IUSelectorDataSourceHelper dataSourceHelper;
    @Resource
    private USelectorResultSet resultSet;
    @Resource
    private QueryModeleDao queryModeleDao;
    @Resource
    private IUnitTreeDataService treeService;

    @RequestMapping(value={"/getTask"}, method={RequestMethod.GET})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public List<QueryModelNode> getTask() throws Exception {
        List<QueryModelNode> taskModel = this.queryModleService.getTaskModel();
        return taskModel;
    }

    @RequestMapping(value={"/getTaskByKey"}, method={RequestMethod.GET})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public QueryModelNode getTaskByKey(@RequestParam(value="key") String key) throws Exception {
        List<QueryModelNode> taskModel = this.queryModleService.getTaskModel();
        for (QueryModelNode node : taskModel) {
            if (!key.equals(node.getId())) continue;
            return node;
        }
        return null;
    }

    @RequestMapping(value={"/getFormScheme"}, method={RequestMethod.GET})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public List<QueryModelNode> getFormScheme(@RequestParam(value="key") String key) throws Exception {
        ArrayList<QueryModelNode> formSchemeModel = new ArrayList();
        try {
            formSchemeModel = this.queryModleService.getFormSchemeModel(key);
            return formSchemeModel;
        }
        catch (NotFoundFormSchemeException e) {
            this.logger.error("\u4efb\u52a1{}\u4e0b\u6ca1\u6709\u627e\u5230\u62a5\u8868\u65b9\u6848", (Object)key);
            return formSchemeModel;
        }
    }

    @RequestMapping(value={"/getFormSchemeBySchemeKey"}, method={RequestMethod.GET})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public QueryModelNode getFormSchemeBySchemeKey(@RequestParam(value="key") String key, @RequestParam(value="schemeKey") String schemeKey) throws Exception {
        List<Object> formSchemeModel = new ArrayList();
        try {
            formSchemeModel = this.queryModleService.getFormSchemeModel(key);
            for (QueryModelNode queryModelNode : formSchemeModel) {
                if (!queryModelNode.getId().equals(schemeKey)) continue;
                return queryModelNode;
            }
        }
        catch (NotFoundFormSchemeException e) {
            this.logger.error("\u4efb\u52a1{}\u4e0b\u6ca1\u6709\u627e\u5230\u62a5\u8868\u65b9\u6848", (Object)key);
        }
        return null;
    }

    @RequestMapping(value={"/getGroup"}, method={RequestMethod.GET})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public List<QueryModelNode> getGroup(@RequestParam(value="key") String key) throws Exception {
        List<QueryModelNode> groupModel = this.queryModleService.getGroupModel(key);
        return groupModel;
    }

    @PostMapping(value={"/getModel"})
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public List<QueryModelNode> geModel(@RequestBody @Valid QueryModelVO queryModelVO) throws Exception {
        List<QueryModelNode> taskModel = this.queryModleService.getModel(queryModelVO.getFormSchemeKey(), queryModelVO.getGroupKey());
        return taskModel;
    }

    @RequestMapping(value={"/getDisUseModel"}, method={RequestMethod.GET})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public List<QueryModelNode> getDisUseModel(@RequestParam String key, @RequestParam String formSchemeKey) throws Exception {
        List<QueryModelNode> taskModel = this.queryModleService.getDisUseModel(formSchemeKey, key);
        return taskModel;
    }

    @RequestMapping(value={"/getModelData"}, method={RequestMethod.GET})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public List<QueryModelNode> geModelData(@RequestParam String key) throws Exception {
        List<QueryModelNode> taskModel = this.queryModleService.getModelData(key);
        return taskModel;
    }

    @PostMapping(value={"/parameter/generate"})
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public List<String> generateParameter(@RequestBody @Valid BuildParam buildParam) throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        List<ParameterModel> paramModels = this.parameterBuilder.buildParameterModel(buildParam);
        for (ParameterModel paramModel : paramModels) {
            JSONObject json = ParameterConvertor.toJson(null, (ParameterModel)paramModel, (boolean)false);
            list.add(json.toString());
        }
        return list;
    }

    @RequestMapping(value={"/getData"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject getData(@RequestBody @Valid QueryConfigInfo queryConfigInfo) throws Exception {
        try {
            this.judgeQueryType(queryConfigInfo);
            this.logger.info("\u5f00\u59cb\u67e5\u8be2\u6a21\u677f\u6570\u636e,ModelId---->{}", (Object)queryConfigInfo.getModelId());
            return new ResultObject((Object)this.queryByCustom.query(queryConfigInfo));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return new ResultObject(false, null == e.getMessage() ? "\u6a21\u677f\u67e5\u8be2\u9519\u8bef\uff01" : e.getMessage());
        }
    }

    @RequestMapping(value={"/getModelSamples"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject getModelSamples(@RequestBody @Valid QueryConfigInfo queryConfigInfo) throws Exception {
        try {
            this.logger.info("\u5f00\u59cb\u67e5\u8be2\u6a21\u677f\u8868\u6837,ModelId---->{}", (Object)queryConfigInfo.getModelId());
            return new ResultObject((Object)this.queryByCustom.querySamples(queryConfigInfo));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return new ResultObject(false, null == e.getMessage() ? "\u6a21\u677f\u8868\u6837\u9519\u8bef\uff01" : e.getMessage());
        }
    }

    public void judgeQueryType(QueryConfigInfo queryConfigInfo) throws Exception {
        List<QueryModelNode> modelData = this.queryModleService.getModelData(queryConfigInfo.getModelId());
        if (!modelData.isEmpty()) {
            String queryDatatime = this.getQueryDatatime(queryConfigInfo);
            Date[] periodDateRegion = new DefaultPeriodAdapter().getPeriodDateRegion(queryDatatime);
            Date endDate = periodDateRegion[1];
            EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(modelData.get(0).getOrg(), "", true);
            IEntityQuery query = this.entityDataService.newEntityQuery();
            query.setEntityView(entityView);
            query.setAuthorityOperations(AuthorityType.Read);
            query.setQueryVersionDate(endDate == null ? Consts.DATE_VERSION_FOR_ALL : endDate);
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            IEntityTable entityTable = query.executeReader((IContext)executorContext);
            if (CollectionUtils.isEmpty(entityTable.getRootRows())) {
                throw new Exception("\u5f53\u524d\u7528\u6237\u6ca1\u6709\u53ef\u4ee5\u67e5\u8be2\u7684\u5355\u4f4d");
            }
            if (-1 != queryConfigInfo.getSelectNode()) {
                String org = modelData.get(0).getOrg();
                UnitTreeContextData ctx = new UnitTreeContextData();
                ctx.setDataSourceId("unit-selector-tree-data-source");
                ctx.setEntityId(org);
                ContextInfo contextInfo = this.iuSelectorInitController.loadContext(ctx);
                IUnitTreeContext context = this.resultSet.getRunContext(contextInfo.getSelectorKey());
                IUSelectorDataSource dataSource = this.dataSourceHelper.getBaseTreeDataSource(context.getITreeContext().getDataSourceId());
                IUSelectorEntityRowProvider entityRowProvider = dataSource.getUSelectorEntityRowProvider(context);
                ConditionValues conditionValues = queryConfigInfo.getConditionValues();
                String[] values = this.getOrgCondValues(conditionValues, org);
                List<String> list = Arrays.asList(values);
                List childRows = new ArrayList();
                if (!list.isEmpty()) {
                    if (1 == queryConfigInfo.getSelectNode()) {
                        childRows = entityRowProvider.getChildRowsAndSelf(list);
                    }
                    if (2 == queryConfigInfo.getSelectNode()) {
                        childRows = entityRowProvider.getAllChildRowsAndSelf(list);
                    }
                }
                StringBuffer s = new StringBuffer();
                if (!childRows.isEmpty()) {
                    HashSet<String> keys = new HashSet<String>();
                    for (int i = 0; i < childRows.size(); ++i) {
                        if (keys.contains(((IEntityRow)childRows.get(i)).getEntityKeyData())) continue;
                        keys.add(((IEntityRow)childRows.get(i)).getEntityKeyData());
                    }
                    String[] array = new String[keys.size()];
                    Integer i = 0;
                    for (String entityKeyData : keys) {
                        Integer n = i;
                        Integer n2 = i = Integer.valueOf(i + 1);
                        array[n.intValue()] = entityKeyData;
                        s.append(entityKeyData).append(";");
                    }
                    this.logger.info(String.format("\u67e5\u8be2\u5355\u4f4d\u5217\u8868\uff1a%s", s.toString()));
                    queryConfigInfo.getConditionValues().putValue(org.split("@")[0], array);
                }
            }
        } else {
            throw new Exception("\u5f53\u524d\u6a21\u677f\u4e0d\u5b58\u5728");
        }
    }

    private String[] getOrgCondValues(ConditionValues conditionValues, String org) {
        String[] values = conditionValues.contains(org) ? conditionValues.getValues(org) : conditionValues.getValues(org.split("@")[0]);
        return values;
    }

    private String getQueryDatatime(QueryConfigInfo queryConfigInfo) {
        ConditionValues conditionValues = queryConfigInfo.getConditionValues();
        if (conditionValues != null) {
            String[] condNames;
            for (String condName : condNames = conditionValues.names()) {
                if (!condName.startsWith("NR_PERIOD_")) continue;
                return conditionValues.getValue(condName);
            }
        }
        return null;
    }

    @RequestMapping(value={"/deleteModel"}, method={RequestMethod.GET})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public Boolean deleteById(@RequestParam String key) throws Exception {
        return this.queryModleService.deleteMyModle(key) == 1;
    }

    @RequestMapping(value={"/batchDelete"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public int[] batchDelete(@RequestBody List<String> key) throws Exception {
        return this.queryModleService.batchDelete(key);
    }

    @RequestMapping(value={"/updateOrder"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public String updateOrder(@RequestBody List<QueryModelNode> modelList) throws Exception {
        return this.queryModleService.updateOrder(modelList);
    }

    @RequestMapping(value={"/changeGroupName"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject changeGroupName(@RequestBody @Valid ChangeGroupNameParams groupNameParams) throws Exception {
        try {
            this.queryModleService.changeGroupName(groupNameParams.getNewName(), groupNameParams.getTaskKey(), groupNameParams.getFromSchemeKey(), groupNameParams.getOldName());
            return new ResultObject(true, "OK");
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "ERROR");
        }
    }

    @RequestMapping(value={"/deleteGroup"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject deleteGroup(@RequestBody @Valid ChangeGroupNameParams groupNameParams) throws Exception {
        try {
            this.queryModleService.deletGroup(groupNameParams.getOldName(), groupNameParams.getFromSchemeKey(), groupNameParams.getTaskKey());
            return new ResultObject(true, "OK");
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "ERROR");
        }
    }

    @RequestMapping(value={"/changeModelName"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject changeModelName(@RequestBody @Valid QueryModelNode queryModelNode) throws Exception {
        try {
            this.queryModleService.changeModelName(queryModelNode.getTitle(), queryModelNode.getId());
            return new ResultObject(true, "OK");
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "ERROR");
        }
    }

    @RequestMapping(value={"/saveModel"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject saveModel(@RequestBody @Valid QueryModel queryModel) throws Exception {
        try {
            queryModel.setOrder(OrderGenerator.newOrder());
            queryModel.setKey(UUID.randomUUID().toString());
            queryModel.setLevel(0);
            this.queryModleService.saveData(queryModel);
            return new ResultObject(true, "OK");
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "ERROR");
        }
    }

    @RequestMapping(value={"/updateModel"}, method={RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject updateModel(@RequestBody @Valid QueryModel queryModel) throws Exception {
        try {
            QueryModel data = this.queryModeleDao.getData(queryModel.getKey());
            if (null != data) {
                queryModel.setLevel(data.getLevel());
            } else {
                this.logger.info("\u6a21\u677f\uff1a{}\u4e0d\u5b58\u5728\u3002\u65e0\u6cd5\u83b7\u53d6level!", (Object)queryModel.getKey());
            }
            this.queryModleService.upData(queryModel);
            return new ResultObject(true, "OK");
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "ERROR");
        }
    }

    @RequestMapping(value={"/getTaskList"}, method={RequestMethod.GET})
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public List<TaskData> getTaskList() {
        List runtimeTaskList = this.dataEntryParamService.getRuntimeTaskList();
        return runtimeTaskList;
    }

    @RequestMapping(value={"/getFormSchemeList"}, method={RequestMethod.GET})
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public List<FormSchemeData> getFormSchemeList(@RequestParam String key) throws Exception {
        List runtimeTaskList = this.dataEntryParamService.runtimeFormSchemeList(key);
        return runtimeTaskList;
    }

    @RequestMapping(value={"/getGroupList"}, method={RequestMethod.GET})
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public Set<String> getGroupList(@RequestParam String taskKey, @RequestParam String schemeKey) throws Exception {
        Set<String> groupList = this.queryModleService.getGroupList(taskKey, schemeKey);
        return groupList;
    }

    @RequestMapping(value={"/copyModel"}, method={RequestMethod.GET})
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject copyModel(@RequestParam String copyId, @RequestParam String titleName) {
        try {
            QueryModel model = this.queryModleService.copyModel(copyId, titleName);
            return null == model ? new ResultObject(false, "\u521b\u5efa\u5931\u8d25") : new ResultObject(true, "\u521b\u5efa\u6210\u529f");
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "\u521b\u5efa\u5931\u8d25");
        }
    }

    @RequestMapping(value={"/moveModel"}, method={RequestMethod.GET})
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject moveModel(@RequestParam String modelId, @RequestParam String groupId) {
        try {
            Integer stat = this.queryModleService.moveModel(modelId, groupId);
            return 1 == stat ? new ResultObject(true, "\u79fb\u52a8\u6210\u529f") : new ResultObject(false, "\u79fb\u52a8\u5931\u8d25");
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "\u521b\u5efa\u5931\u8d25");
        }
    }

    @RequestMapping(value={"/disUse"}, method={RequestMethod.POST})
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject disUse(@RequestBody List<QueryModelNode> modleNodes) {
        try {
            String stat = this.queryModleService.disUse(modleNodes);
            return "OK" == stat ? new ResultObject(true, "\u6210\u529f") : new ResultObject(false, "\u5931\u8d25");
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "\u5931\u8d25");
        }
    }

    @RequestMapping(value={"/repeatedJudgment"}, method={RequestMethod.POST})
    @ApiOperation(value="\u5224\u65ad\u540c\u4e00\u4e2a\u62a5\u8868\u4e0b\u5206\u7ec4\u6216\u6a21\u677f\u540d\u79f0\u662f\u5426\u91cd\u590d")
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject repeatedJudgment(@RequestBody @Valid RepeatedJudgmentParams params) {
        try {
            Boolean stat = this.queryModleService.repeatedJudgment(params);
            return new ResultObject((Object)stat);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "\u5931\u8d25");
        }
    }

    @RequestMapping(value={"/dataQuery"}, method={RequestMethod.POST})
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject dataQuery(@RequestBody @Valid DataQueryParams params) {
        try {
            List<ArrayList<Object>> result = this.queryModleService.dataQuery(params);
            return new ResultObject(result);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "\u5931\u8d25");
        }
    }

    @RequestMapping(value={"/getLevelList"}, method={RequestMethod.POST})
    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u6a21\u677f\u6307\u6807\u6709\u54ea\u4e9b\u6307\u6807\u5b57\u6bb5\u6709\u5c42\u7ea7")
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject getLevelList(@RequestParam String key) {
        try {
            List<Integer> result = this.queryModleService.getLevelList(key);
            return new ResultObject(result);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "\u8be5\u6307\u6807\u4e0d\u53ef\u4ee5\u5206\u7ec4\u67e5\u8be2");
        }
    }

    @RequestMapping(value={"/getLevelByCode"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6839\u636e\u6307\u6807\u83b7\u53d6 \u5f53\u524d\u6307\u6807\u5173\u8054\u7684\u57fa\u7840\u4efb\u52a1\u7684\u5c42\u7ea7")
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject getLevelByCode(@RequestBody @Valid QueryLevelsParams params) {
        try {
            List<Integer> result = this.queryModleService.getLevelByCode(params);
            return new ResultObject(result);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "\u8be5\u6307\u6807\u4e0d\u53ef\u4ee5\u5206\u7ec4\u67e5\u8be2");
        }
    }

    @RequestMapping(value={"/getModelsFilter"}, method={RequestMethod.POST})
    @ApiOperation(value="\u83b7\u53d6\u6a21\u7248\u7684\u8fc7\u6ee4\u6761\u4ef6")
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject getModelsFilter(@RequestBody List<String> modelIds) {
        try {
            Map<String, String> result = this.queryModleService.getModelsFilter(modelIds);
            return new ResultObject(result);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return new ResultObject(false, "\u8be5\u6307\u6807\u4e0d\u53ef\u4ee5\u5206\u7ec4\u67e5\u8be2");
        }
    }
}

