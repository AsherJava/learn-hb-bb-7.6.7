/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.google.common.collect.Lists
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.grid.ncell.GridDataConverter
 *  com.jiuqi.bi.quickreport.engine.build.hyperlink.HyperlinkEnv
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.impl.DataRowImpl
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.nr.dataentry.service.IDataEntryParamService
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.designer.service.StepSaveService
 *  com.jiuqi.nr.designer.web.treebean.TaskLinkObject
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.internal.service.ZBQueryService
 *  com.jiuqi.nr.zbquery.bean.impl.SelectedFieldDefineEx
 *  com.jiuqi.nr.zbquery.common.ZBQueryErrorEnum
 *  com.jiuqi.nr.zbquery.engine.ZBQueryEngine
 *  com.jiuqi.nr.zbquery.engine.ZBQueryResult
 *  com.jiuqi.nr.zbquery.rest.param.FieldSelectParam
 *  com.jiuqi.nr.zbquery.rest.vo.QueryConfigVO
 *  com.jiuqi.nr.zbquery.util.HyperLinkDataCover
 *  com.jiuqi.nr.zbquery.util.ZBQueryLogHelper
 *  com.jiuqi.util.OrderGenerator
 *  com.jiuqi.va.datamodel.service.VaDataModelPublishedService
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.nr.singlequeryimport.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.jiuqi.bi.grid.ncell.GridDataConverter;
import com.jiuqi.bi.quickreport.engine.build.hyperlink.HyperlinkEnv;
import com.jiuqi.bi.util.Guid;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.designer.service.StepSaveService;
import com.jiuqi.nr.designer.web.treebean.TaskLinkObject;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.internal.service.ZBQueryService;
import com.jiuqi.nr.singlequeryimport.auth.FinalaccountQueryAuthResourceType;
import com.jiuqi.nr.singlequeryimport.auth.service.impl.FinalaccountQueryAuthServiceImpl;
import com.jiuqi.nr.singlequeryimport.auth.share.service.AuthShareService;
import com.jiuqi.nr.singlequeryimport.bean.Block;
import com.jiuqi.nr.singlequeryimport.bean.DbSelectIndex;
import com.jiuqi.nr.singlequeryimport.bean.DragDefine;
import com.jiuqi.nr.singlequeryimport.bean.GridData;
import com.jiuqi.nr.singlequeryimport.bean.ModalDefine;
import com.jiuqi.nr.singlequeryimport.bean.ParamVo.DataQueryParams;
import com.jiuqi.nr.singlequeryimport.bean.ParamVo.QueryLevelsParams;
import com.jiuqi.nr.singlequeryimport.bean.ParamVo.RepeatedJudgmentParams;
import com.jiuqi.nr.singlequeryimport.bean.QueryGridCell;
import com.jiuqi.nr.singlequeryimport.bean.QueryMergeCell;
import com.jiuqi.nr.singlequeryimport.bean.QueryModel;
import com.jiuqi.nr.singlequeryimport.bean.QueryModelNode;
import com.jiuqi.nr.singlequeryimport.bean.QueryResult;
import com.jiuqi.nr.singlequeryimport.bean.QuerySelectItem;
import com.jiuqi.nr.singlequeryimport.bean.TableGrid;
import com.jiuqi.nr.singlequeryimport.common.ContrastContext;
import com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao;
import com.jiuqi.nr.singlequeryimport.service.QueryModleService;
import com.jiuqi.nr.singlequeryimport.service.impl.SinglerQuserServiceImpl;
import com.jiuqi.nr.singlequeryimport.utils.DataQueryUtil;
import com.jiuqi.nr.singlequeryimport.utils.StyleType;
import com.jiuqi.nr.singlequeryimport.utils.XmlToJsonUtil;
import com.jiuqi.nr.zbquery.bean.impl.SelectedFieldDefineEx;
import com.jiuqi.nr.zbquery.common.ZBQueryErrorEnum;
import com.jiuqi.nr.zbquery.engine.ZBQueryEngine;
import com.jiuqi.nr.zbquery.engine.ZBQueryResult;
import com.jiuqi.nr.zbquery.rest.param.FieldSelectParam;
import com.jiuqi.nr.zbquery.rest.vo.QueryConfigVO;
import com.jiuqi.nr.zbquery.util.HyperLinkDataCover;
import com.jiuqi.nr.zbquery.util.ZBQueryLogHelper;
import com.jiuqi.util.OrderGenerator;
import com.jiuqi.va.datamodel.service.VaDataModelPublishedService;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class QueryModleServiceImpl
implements QueryModleService {
    private static final Logger logger = LoggerFactory.getLogger(QueryModleServiceImpl.class);
    @Autowired
    private QueryModeleDao queryModeleDao;
    @Autowired
    IRunTimeViewController iRunTimeViewController;
    @Autowired
    IDesignTimeViewController designTimeViewController;
    @Autowired
    IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private StepSaveService stepSaveService;
    @Autowired
    SinglerQuserServiceImpl service;
    @Autowired
    IEntityMetaService iEntityMetaService;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    ZBQueryService zbQueryService;
    @Autowired
    IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    FinalaccountQueryAuthServiceImpl finalaccountQueryAuthService;
    @Autowired
    AuthShareService authShareService;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    VaDataModelPublishedService dataModelService;
    @Autowired
    IDataAccessProvider dataAccessProvider;
    @Autowired
    IRunTimeViewController runTimeView;
    @Autowired
    IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    DataQueryUtil dataQueryUtil;
    @Autowired
    IDataDefinitionRuntimeController runtimeController;

    @Override
    public List<QueryModelNode> getTaskModel() throws Exception {
        HashSet taskKeyAuthority = new HashSet();
        List runtimeTaskList = this.dataEntryParamService.getRuntimeTaskList();
        runtimeTaskList.stream().forEach(taskData -> taskKeyAuthority.add(taskData.getKey()));
        ArrayList<QueryModelNode> modleNodes = new ArrayList<QueryModelNode>();
        Set<String> taskKey = this.queryModeleDao.getTaskKey();
        if (taskKey.size() > 0) {
            for (String key : taskKey) {
                TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(key);
                if (null == taskDefine) continue;
                QueryModelNode myModleNode = new QueryModelNode();
                myModleNode.setId(key);
                myModleNode.setTitle(taskDefine.getTitle());
                modleNodes.add(myModleNode);
            }
        }
        return modleNodes;
    }

    @Override
    public List<QueryModelNode> getFormSchemeModel(String taskKey) throws Exception {
        HashSet formSchemeKeyAuthority = new HashSet();
        List formSchemeData = this.dataEntryParamService.runtimeFormSchemeList(taskKey);
        formSchemeData.stream().forEach(taskData -> formSchemeKeyAuthority.add(taskData.getKey()));
        ArrayList<QueryModelNode> modleNodes = new ArrayList<QueryModelNode>();
        Set<String> schemeKey = this.queryModeleDao.getFormSchemeKeyByTaskKey(taskKey);
        if (schemeKey.size() > 0) {
            for (String key : schemeKey) {
                FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(key);
                QueryModelNode myModleNode = new QueryModelNode();
                myModleNode.setId(key);
                myModleNode.setTitle(formScheme.getTitle());
                modleNodes.add(myModleNode);
            }
        }
        return modleNodes;
    }

    @Override
    public List<QueryModelNode> getGroupModel(String taskKey) throws Exception {
        FormSchemeDefine formScheme;
        ArrayList<QueryModelNode> modleNodes = new ArrayList<QueryModelNode>();
        Set<String> groupKey = this.queryModeleDao.getGroupByFormSchemeKey(taskKey);
        if (groupKey.size() > 0 && null != (formScheme = this.iRunTimeViewController.getFormScheme(taskKey))) {
            for (String key : groupKey) {
                if (!this.finalaccountQueryAuthService.canReadGroupWithChildModels(taskKey, key)) continue;
                QueryModelNode myModleNode = new QueryModelNode();
                myModleNode.setId(key);
                myModleNode.setTitle(key);
                myModleNode.setOrg(formScheme.getDw());
                modleNodes.add(myModleNode);
            }
        }
        return modleNodes;
    }

    @Override
    public List<QueryModelNode> getModel(String formSchemeKey, String groupKey) throws Exception {
        List<QueryModel> models = this.queryModeleDao.getModel(formSchemeKey, groupKey);
        ArrayList<QueryModel> resultList = new ArrayList<QueryModel>();
        for (QueryModel current : models) {
            if (!org.springframework.util.StringUtils.hasText(current.getItemTitle()) || !this.finalaccountQueryAuthService.canReadModel(current.getKey())) continue;
            resultList.add(current);
        }
        return this.convertQueryModelToQueryModleNode(resultList);
    }

    @Override
    public List<QueryModelNode> getModelData(String key) throws Exception {
        return this.convertQueryModelToQueryModleNode(this.queryModeleDao.getModelData(key));
    }

    @Override
    public QueryModel copyModel(String modelId, String name) throws Exception {
        QueryModel copyModel = this.queryModeleDao.getData(modelId);
        QueryModel queryModel = new QueryModel();
        Integer save = null;
        if (null != copyModel) {
            String uuid = UUID.randomUUID().toString();
            queryModel.setKey(uuid);
            queryModel.setOrder(OrderGenerator.newOrder());
            queryModel.setItem(copyModel.getItem());
            queryModel.setTaskKey(copyModel.getTaskKey());
            queryModel.setFormschemeKey(copyModel.getFormschemeKey());
            queryModel.setItemTitle(name);
            queryModel.setCustom(1);
            queryModel.setGroup(copyModel.getGroup());
            queryModel.setDisUse(0);
            queryModel.setLevel(0);
            save = this.saveData(queryModel);
        }
        if (org.springframework.util.StringUtils.hasText(queryModel.getItemTitle())) {
            this.authShareService.addCurUserPrivilege(queryModel.getKey(), FinalaccountQueryAuthResourceType.FQ_MODEL_NODE);
        }
        if (org.springframework.util.StringUtils.hasText(queryModel.getGroup())) {
            this.authShareService.addCurUserGroupPrivilege(queryModel.getFormschemeKey(), queryModel.getGroup(), FinalaccountQueryAuthResourceType.FQ_GROUP);
        }
        return 0 == save ? queryModel : null;
    }

    @Override
    public Integer moveModel(String modelId, String groupId) throws Exception {
        QueryModel model = this.queryModeleDao.getData(modelId);
        model.setGroup(groupId);
        return this.upData(model);
    }

    @Override
    public String disUse(List<QueryModelNode> modleNodes) throws Exception {
        this.queryModeleDao.upDateDisUse(modleNodes);
        return "OK";
    }

    @Override
    public Boolean repeatedJudgment(RepeatedJudgmentParams params) throws Exception {
        Set<Object> titleList = new HashSet();
        if ("model".equalsIgnoreCase(params.getType().name())) {
            titleList = this.queryModeleDao.getModalTitleByFormSchemeKey(params.getSchemeKey());
            return titleList.contains(params.getTitle());
        }
        if ("group".equalsIgnoreCase(params.getType().name())) {
            titleList = this.queryModeleDao.getGroupByFormSchemeKey(params.getSchemeKey());
            return titleList.contains(params.getTitle());
        }
        return false;
    }

    @Override
    public List<Integer> getLevelByCode(QueryLevelsParams params) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        HashMap emnu = new HashMap();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(params.getTaskKey());
        DataScheme dataScheme = this.iRuntimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        List allDataFieldByKind = this.runtimeDataSchemeService.getAllDataFieldByKind(dataScheme.getKey(), new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM});
        List entityRefer = this.iEntityMetaService.getEntityRefer(taskDefine.getDw());
        entityRefer.forEach(e -> emnu.put(e.getOwnField(), e.getReferEntityId()));
        allDataFieldByKind.forEach(e -> emnu.put(e.getCode(), e.getRefDataEntityKey()));
        List collect = emnu.keySet().stream().filter(e -> params.getZb().contains((CharSequence)e)).collect(Collectors.toList());
        String code = ((String)emnu.get(collect.get(0))).split("@")[0];
        DataModelDTO param = new DataModelDTO();
        param.setName(code);
        DataModelDO dataModelDO = this.dataModelService.get(param);
        Object baseDataDefine = dataModelDO.getExtInfo().get("baseDataDefine");
        String levelCode = (String)((LinkedHashMap)baseDataDefine).get("levelcode");
        String levelString = levelCode.split("#")[0];
        for (int index = 0; index < levelString.length(); ++index) {
            String level = levelString.substring(index, index + 1);
            if (index > 0) {
                result.add(index, (Integer)result.get(index - 1) + Integer.parseInt(level));
                continue;
            }
            result.add(index, Integer.parseInt(level));
        }
        return result;
    }

    @Override
    public List<Integer> getLevelList(String key) throws Exception {
        QueryModel dataModel = this.queryModeleDao.getData(key);
        JSONObject items = new JSONObject(dataModel.getItem());
        JSONObject model = items.getJSONObject("model");
        JSONArray data = model.getJSONArray("data");
        return null;
    }

    @Override
    public List<ArrayList<Object>> dataQuery(DataQueryParams params) throws Exception {
        String formSchemeKey = params.getFormSchemeKey();
        String period = params.getPeriod();
        String filter = params.getFilter();
        List<String> fields = params.getFields();
        List<String> dw = params.getDw();
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery();
        DimensionValueSet dimensionValueSet = this.dataQueryUtil.buildDimension(formSchemeKey, period, dw);
        dataQuery.setMasterKeys(dimensionValueSet);
        dataQuery.setRowFilter(filter);
        dataQuery.setPagingInfoByRowIndex(1, 1);
        for (String field : fields) {
            dataQuery.addExpressionColumn(field);
        }
        ExecutorContext faExecutorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment faEnvironment = new ReportFmlExecEnvironment(this.runTimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey);
        faExecutorContext.setEnv((IFmlExecEnvironment)faEnvironment);
        faExecutorContext.setJQReportModel(true);
        IDataTable table = dataQuery.executeQuery(faExecutorContext);
        int count = table.getCount();
        ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
        if (table.getCount() > 0) {
            for (int i = 0; i < count; ++i) {
                IDataRow item = table.getItem(i);
                ArrayList rowDatas = ((DataRowImpl)item).getRowDatas();
                result.add(rowDatas);
            }
        }
        return result;
    }

    @Override
    public List<QueryModelNode> getDisUseModel(String formSchemeKey, String groupKey) throws Exception {
        List<QueryModel> models = this.queryModeleDao.getDisUseModel(formSchemeKey, groupKey);
        ArrayList<QueryModel> resultList = new ArrayList<QueryModel>();
        for (QueryModel current : models) {
            if (!this.finalaccountQueryAuthService.canWriteModel(current.getKey())) continue;
            resultList.add(current);
        }
        return this.convertQueryModelToQueryModleNode(resultList);
    }

    @Override
    public Map<String, String> getModelsFilter(List<String> modelIds) {
        HashMap<String, String> result = new HashMap<String, String>();
        List<QueryModel> models = this.queryModeleDao.getMdoelByKyes(modelIds);
        HashMap<String, QueryModel> modelMap = new HashMap<String, QueryModel>();
        for (QueryModel model : models) {
            modelMap.put(model.getKey(), model);
        }
        for (String modelId : modelIds) {
            if (modelMap.containsKey(modelId)) {
                QueryModel model = (QueryModel)modelMap.get(modelId);
                JSONObject items = new JSONObject(model.getItem());
                if (items.getJSONObject("filter").has("formulaContent")) {
                    result.put(modelId, items.getJSONObject("filter").getString("formulaContent"));
                    continue;
                }
                result.put(modelId, "");
                continue;
            }
            result.put(modelId, "");
        }
        return result;
    }

    private List<QueryModelNode> convertQueryModelToQueryModleNode(List<QueryModel> models) {
        ArrayList<QueryModelNode> modelNodes = new ArrayList<QueryModelNode>();
        for (QueryModel model : models) {
            if (null == model.getItemTitle()) continue;
            DesignTaskDefine task = this.designTimeViewController.getTask(model.getTaskKey());
            QueryModelNode myModelNode = new QueryModelNode();
            if (null != task) {
                myModelNode.setOrg(task.getDw());
            }
            myModelNode.setId(model.getKey());
            myModelNode.setTitle(model.getItemTitle());
            myModelNode.setCustom(model.getCustom());
            myModelNode.setOrder(model.getOrder());
            myModelNode.setItem(model.getItem());
            myModelNode.setDisUse(model.getDisUse());
            myModelNode.setForewarnCondition(model.getForewarnCondition());
            modelNodes.add(myModelNode);
        }
        return modelNodes;
    }

    @Override
    public QueryModel getModelById(QueryModel queryModel) throws Exception {
        QueryModel model = this.queryModeleDao.getModelById(queryModel);
        return model;
    }

    @Override
    public Integer deleteMyModle(String key) throws Exception {
        Integer result = this.queryModeleDao.deleteById(key);
        return result;
    }

    @Override
    public int[] batchDelete(List key) throws Exception {
        int[] result = this.queryModeleDao.batchDelete(key);
        return result;
    }

    @Override
    public StringBuffer saveMyModle(ModalDefine modalDefine, String modelGroupTitle, String formSchemetitle, ContrastContext context) throws Exception {
        StringBuffer info = new StringBuffer();
        List<DragDefine> dragDefines = modalDefine.getBlocks().getBlock().getDragDefines().getDragDefine();
        String title = modalDefine.getTitle();
        List zbDragDefines = dragDefines.stream().filter(dragDefine -> dragDefine.getTitle().equals("\u6307\u6807")).collect(Collectors.toList());
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTaskKey());
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(context.getFormSchemeKey());
        JSONObject changeItem = this.changeItem(modalDefine, formSchemeDefine.getKey(), taskDefine.getKey(), info);
        String uuid = UUID.randomUUID().toString();
        QueryModel queryModel = new QueryModel();
        queryModel.setKey(uuid);
        queryModel.setOrder(OrderGenerator.newOrder());
        queryModel.setItem(changeItem.toString());
        queryModel.setTaskKey(formSchemeDefine.getTaskKey());
        queryModel.setFormschemeKey(formSchemeDefine.getKey());
        queryModel.setItemTitle(title);
        queryModel.setCustom(0);
        queryModel.setDisUse(0);
        queryModel.setLevel(0);
        queryModel.setGroup(modelGroupTitle == null ? "\u9ed8\u8ba4\u5206\u7ec4" : modelGroupTitle);
        QueryModel modelById = this.queryModeleDao.getModelById(queryModel);
        if (modelById != null) {
            modelById.setItem(changeItem.toString());
            this.upData(modelById);
            logger.info("{}--\u66f4\u65b0\u6210\u529f", (Object)title);
        } else {
            this.saveData(queryModel);
            logger.info("{}--\u5bfc\u5165\u6210\u529f", (Object)title);
        }
        if (info.length() > 0) {
            info.insert(0, title + "--------->");
        }
        return info;
    }

    JSONObject changeItem(ModalDefine modalDefine, String formSchemeKey, String taskKey, StringBuffer info) throws Exception {
        JSONObject cellDataJson;
        Block block = modalDefine.getBlocks().getBlock();
        TableGrid tableGrid = block.getBinaryData().getTableGrid();
        GridData gridData = block.getGridData();
        Integer headRowCount = Integer.parseInt(gridData.getQueryGridHeadInfo().getHeadRowCount());
        JSONObject filter = new JSONObject();
        String blockCondition = block.getBlockCondition();
        filter.put("formulaContent", (Object)blockCondition);
        filter.put("filterCondition", (Object)blockCondition);
        JSONObject item = new JSONObject();
        JSONArray queryObjects = new JSONArray();
        JSONObject options = new JSONObject();
        JSONObject header = new JSONObject();
        JSONObject model = new JSONObject();
        String showSum = block.getShowSum();
        JSONArray mergerInfos = new JSONArray();
        if (null != gridData.getQueryMergeCells()) {
            List<QueryMergeCell> queryMergeCell = gridData.getQueryMergeCells().getQueryMergeCell();
            for (QueryMergeCell cell : queryMergeCell) {
                JSONObject serialNumberHeaderJson = new JSONObject();
                serialNumberHeaderJson.put("rowIndex", Integer.parseInt(cell.getTop()) - 1);
                serialNumberHeaderJson.put("columnIndex", Integer.parseInt(cell.getLeft()));
                serialNumberHeaderJson.put("rowSpan", Integer.parseInt(cell.getBottom()) - Integer.parseInt(cell.getTop()) + 1);
                serialNumberHeaderJson.put("columnSpan", Integer.parseInt(cell.getRight()) - Integer.parseInt(cell.getLeft()) + 1);
                mergerInfos.put((Object)serialNumberHeaderJson);
            }
        }
        JSONArray dataJsons = new JSONArray();
        List<QueryGridCell> queryGridCellList = gridData.getQueryGridCells().getQueryGridCell();
        Integer size = queryGridCellList.size();
        Integer tableRowCount = showSum.equals("True") ? headRowCount - 1 : headRowCount;
        List partition = Lists.partition(queryGridCellList, (int)(size / tableRowCount));
        for (List queryGridCell : partition) {
            JSONArray rowJson = new JSONArray();
            for (QueryGridCell cell : queryGridCell) {
                JSONObject cellDataJson2 = new JSONObject();
                cellDataJson2.put("s", 0);
                cellDataJson2.put("t", 1);
                cellDataJson2.put("v", (Object)(cell.getCellData() == null ? "" : cell.getCellData()));
                JSONObject serialNumberHeaderJson = cellDataJson2;
                rowJson.put((Object)serialNumberHeaderJson);
            }
            dataJsons.put((Object)rowJson);
        }
        List dragDefines = modalDefine.getBlocks().getBlock().getDragDefines().getDragDefine().stream().filter(dragDefine -> dragDefine.getTitle().equals("\u6307\u6807")).collect(Collectors.toList());
        List<QuerySelectItem> querySelectItems = ((DragDefine)dragDefines.get(0)).getQuerySelectItems().getQuerySelectItem();
        JSONArray zbJson = new JSONArray();
        JSONArray xsJson = new JSONArray();
        FieldSelectParam param = new FieldSelectParam();
        ArrayList zbs = new ArrayList();
        ArrayList<DbSelectIndex> dbSelectIndexLists = new ArrayList<DbSelectIndex>();
        for (QuerySelectItem querySelectItemt : querySelectItems) {
            JSONObject cellDataJson3 = new JSONObject();
            JSONObject Json = new JSONObject();
            Json.put("s", 2);
            Json.put("t", 1);
            Json.put("v", (Object)"");
            if ("ft_Float".equals(querySelectItemt.getFieldType())) {
                cellDataJson3.put("s", 4);
                Json.put("v", (Object)"2");
            } else {
                cellDataJson3.put("s", 2);
            }
            cellDataJson3.put("t", 1);
            this.parseCode(querySelectItemt, dbSelectIndexLists, info);
            this.addSelectIndex(dbSelectIndexLists, info, querySelectItemt, (List)partition.get(partition.size() - 1));
            cellDataJson3.put("v", (Object)querySelectItemt.getCode());
            zbJson.put((Object)cellDataJson3);
            xsJson.put((Object)Json);
        }
        param.setZbs(zbs);
        dataJsons.put((Object)zbJson);
        dataJsons.put((Object)xsJson);
        JSONArray resultData = new JSONArray();
        if (tableGrid.getUseRowNum().equals("True")) {
            item.put("columnNumber", 1);
            for (int i = 0; i < dataJsons.length(); ++i) {
                JSONArray jsonArray = dataJsons.getJSONArray(i);
                JSONArray cellArray = new JSONArray();
                JSONObject cellDataJson4 = new JSONObject();
                cellDataJson4.put("s", 3);
                cellDataJson4.put("t", 1);
                if (i == 0) {
                    cellDataJson4.put("v", (Object)"\u884c\u6b21");
                } else {
                    cellDataJson4.put("v", (Object)"");
                }
                cellArray.put((Object)cellDataJson4);
                cellArray.putAll(jsonArray);
                resultData.put((Object)cellArray);
            }
        } else {
            resultData.putAll(dataJsons);
            item.put("columnNumber", 0);
        }
        if (tableGrid.getUseColNum().equals("True")) {
            JSONArray cloArray = new JSONArray();
            for (int i = 0; i < resultData.getJSONArray(0).length(); ++i) {
                cellDataJson = new JSONObject();
                cellDataJson.put("s", 3);
                cellDataJson.put("t", 1);
                cellDataJson.put("v", (Object)"");
                if (i == 0) {
                    JSONObject headerJson = new JSONObject();
                    headerJson.put("rowIndex", showSum.equals("True") ? headRowCount - 1 : headRowCount);
                    headerJson.put("columnIndex", 1);
                    headerJson.put("rowSpan", 1);
                    headerJson.put("columnSpan", 2);
                    mergerInfos.put((Object)headerJson);
                    cellDataJson.put("v", (Object)"\u680f\u6b21");
                }
                if (i > 1) {
                    cellDataJson.put("v", i - 1);
                }
                cloArray.put((Object)cellDataJson);
            }
            resultData = XmlToJsonUtil.addObjection(resultData, cloArray, (Integer)(showSum.equals("True") ? headRowCount - 1 : headRowCount));
        }
        resultData = this.addLeftColums(resultData);
        if (showSum.equals("True")) {
            item.put("totalLine", 1);
            JSONArray cloArray = new JSONArray();
            for (int i = 0; i < resultData.getJSONArray(0).length(); ++i) {
                cellDataJson = new JSONObject();
                cellDataJson.put("s", 4);
                cellDataJson.put("v", (Object)"");
                if (i == 0) {
                    cellDataJson.put("s", 1);
                    cellDataJson.put("t", 1);
                    cellDataJson.put("v", (Object)"\u5408\u8ba1\u884c");
                }
                cloArray.put((Object)cellDataJson);
            }
            resultData = XmlToJsonUtil.addObjection(resultData, cloArray, (Integer)(tableGrid.getUseColNum().equals("True") ? headRowCount : headRowCount - 1));
        } else {
            item.put("totalLine", 0);
        }
        Map<String, JSONArray> columnsAndRowsStyle = this.columnsAndRowsSet(resultData, tableGrid);
        JSONArray sytles = StyleType.setStyleType();
        JSONArray orders = new JSONArray();
        header.put("columnHeader", 1);
        header.put("rowHeader", (Object)headRowCount);
        options.put("hiddenSerialNumberHeader", true);
        options.put("header", (Object)header);
        model.put("options", (Object)options);
        model.put("mergeInfo", (Object)mergerInfos);
        model.put("data", (Object)resultData);
        model.put("columns", (Object)columnsAndRowsStyle.get("columns"));
        model.put("rows", (Object)columnsAndRowsStyle.get("rows"));
        model.put("styles", (Object)sytles);
        model.put("orders", (Object)orders);
        item.put("model", (Object)model);
        item.put("params", (Object)new JSONObject(this.mapper.writeValueAsString((Object)param)));
        item.put("dbSelectIndexList", (Object)new JSONArray(this.mapper.writeValueAsString(dbSelectIndexLists)));
        item.put("custom", 0);
        item.put("headerRowNum", 1);
        item.put("queryObjects", (Object)queryObjects);
        item.put("filter", (Object)filter);
        return item;
    }

    JSONArray addLeftColums(JSONArray data) {
        JSONArray result = new JSONArray();
        int length = data.length();
        for (int i = 0; i < length; ++i) {
            JSONArray jsonArray = data.getJSONArray(i);
            JSONObject cellDataJson = new JSONObject();
            cellDataJson.put("s", 1);
            cellDataJson.put("t", 1);
            cellDataJson.put("v", (Object)"");
            if (i == 0) {
                cellDataJson.put("v", (Object)"\u8868\u5934\u680f");
            }
            if (i == length - 1) {
                cellDataJson.put("v", (Object)"\u5c0f\u6570\u4f4d");
            }
            if (i == length - 2) {
                cellDataJson.put("v", (Object)"\u8868\u8fbe\u5f0f");
            }
            result.put((Object)XmlToJsonUtil.addObjection(jsonArray, cellDataJson, (Integer)0));
        }
        return result;
    }

    void parseCode(QuerySelectItem querySelectItem, List<DbSelectIndex> dbSelectIndexLists, StringBuffer info) throws Exception {
        try {
            String code = querySelectItem.getCode();
            if (!code.isEmpty()) {
                if (code.contains("DWMC")) {
                    code = code.replace("DWMC", "NAME");
                }
                if (code.contains("SYS_ZDM")) {
                    code = code.replace("SYS_ZDM", "ORGCODE");
                }
                if (code.contains("FMDM") && code.contains("[") && code.contains("]")) {
                    String substring = code.substring(code.indexOf("[") + 1, code.indexOf("]"));
                    querySelectItem.setCode(substring);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            info.append(querySelectItem.getCode() + "\u6307\u6807\u89e3\u6790\u5931\u8d25\uff1a" + e.getMessage());
        }
    }

    void addSelectIndex(List<DbSelectIndex> dbSelectIndexLists, StringBuffer info, QuerySelectItem querySelectItem, List<QueryGridCell> queryGridCell) {
        DbSelectIndex dbSelectIndex = new DbSelectIndex();
        dbSelectIndex.setTitle(querySelectItem.getTitle());
        dbSelectIndex.setData(querySelectItem.getCode());
        String patternZb = "[A-Z]*[0-9]*[_]*[0-9]*\\[[0-9]*,[0-9]*](@[0-9]*)?";
        if (Pattern.compile(patternZb).matcher(querySelectItem.getCode()).matches()) {
            dbSelectIndex.setType("ZB");
        } else {
            dbSelectIndex.setType("FMDM");
        }
        dbSelectIndexLists.add(dbSelectIndex);
    }

    String filter(String code, String schemeKey, StringBuffer info) {
        if (null != code) {
            String patternZb = "[A-Z]*[0-9]*[_]*[0-9]*\\[[0-9]*,[0-9]*](@[0-9]*)?";
            Matcher m = Pattern.compile(patternZb).matcher(code);
            while (m.find()) {
                DataFieldDTO dataFieldDTO = null;
                String zb = m.group(0);
                String formName = zb.substring(0, zb.indexOf("["));
                String x = zb.substring(zb.indexOf(",") + 1, zb.indexOf("]"));
                String y = zb.substring(zb.indexOf("[") + 1, zb.indexOf(","));
                try {
                    if (zb.contains("@")) {
                        String linkCode = zb.substring(zb.indexOf("@") + 1);
                        ArrayList taskLinkObjBySchemeKey = this.stepSaveService.getTaskLinkObjBySchemeKey(schemeKey);
                        List collect = taskLinkObjBySchemeKey.stream().filter(taskLinkObject -> taskLinkObject.getLinkAlias().equals(linkCode)).collect(Collectors.toList());
                        dataFieldDTO = this.getDataFieldDTO(((TaskLinkObject)collect.get(0)).getRelatedFormSchemeKey(), formName, Integer.parseInt(x), Integer.parseInt(y), null);
                    } else {
                        dataFieldDTO = this.getDataFieldDTO(schemeKey, formName, Integer.parseInt(x), Integer.parseInt(y), null);
                    }
                    DataTable dataTable = this.getDataTable(dataFieldDTO.getDataTableKey());
                    String zbfile = dataTable.getCode() + "[" + dataFieldDTO.getCode() + "]";
                    code = code.replaceFirst(patternZb, zbfile);
                }
                catch (Exception e) {
                    info.append(zb + "\u6307\u6807\u4fe1\u606f\u89e3\u6790\u5931\u8d25     ");
                }
            }
        }
        return code;
    }

    Boolean regularMatching(Matcher m, String schemeKey, QuerySelectItem querySelectItemt, SelectedFieldDefineEx selectedFieldDefineEx, StringBuffer info) throws Exception {
        Boolean isZB = false;
        while (m.find()) {
            isZB = true;
            String zb = m.group(0);
            String formName = zb.substring(0, zb.indexOf("["));
            String x = zb.substring(zb.indexOf(",") + 1, zb.indexOf("]"));
            String y = zb.substring(zb.indexOf("[") + 1, zb.indexOf(","));
            try {
                DataFieldDTO dataFieldDTO = null;
                if (zb.contains("@")) {
                    String linkCode = zb.substring(zb.indexOf("@") + 1);
                    ArrayList taskLinkObjBySchemeKey = this.stepSaveService.getTaskLinkObjBySchemeKey(schemeKey);
                    List collect = taskLinkObjBySchemeKey.stream().filter(taskLinkObject -> taskLinkObject.getLinkAlias().equals(linkCode)).collect(Collectors.toList());
                    dataFieldDTO = this.getDataFieldDTO(((TaskLinkObject)collect.get(0)).getRelatedFormSchemeKey(), formName, Integer.parseInt(x), Integer.parseInt(y), selectedFieldDefineEx);
                } else {
                    dataFieldDTO = this.getDataFieldDTO(schemeKey, formName, Integer.parseInt(x), Integer.parseInt(y), selectedFieldDefineEx);
                }
                DataTable dataTable = this.getDataTable(dataFieldDTO.getDataTableKey());
                String zbfile = dataTable.getCode() + "[" + dataFieldDTO.getCode() + "]";
                String oldChart = zb.replace("[", "\\[").replace("]", "\\]");
                querySelectItemt.setCode(querySelectItemt.getCode().replaceFirst(oldChart, zbfile));
            }
            catch (Exception e) {
                info.append(zb + "\u6307\u6807\u4fe1\u606f\u89e3\u6790\u5931\u8d25     ");
            }
        }
        return isZB;
    }

    public DataFieldDTO getDataFieldDTO(String schemeCode, String formCode, int x, int y, SelectedFieldDefineEx selectedFieldDefineEx) throws Exception {
        try {
            FormDefine formDefine = this.iRunTimeViewController.queryFormByCodeInScheme(schemeCode, formCode);
            String key = formDefine.getKey();
            DataLinkDefine dataLinkDefine = this.iRunTimeViewController.queryDataLinkDefineByColRow(key, x, y);
            String linkExpression = dataLinkDefine.getLinkExpression();
            if (null != selectedFieldDefineEx) {
                selectedFieldDefineEx.setFieldCode(linkExpression);
                selectedFieldDefineEx.setFieldTitle(dataLinkDefine.getTitle());
            }
            DataFieldDTO fieldDefine = (DataFieldDTO)this.iRunTimeViewController.queryFieldDefine(linkExpression);
            return fieldDefine;
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    DataTable getDataTable(String dataTableKey) {
        DataTable dataTable = this.iRuntimeDataSchemeService.getDataTable(dataTableKey);
        return dataTable;
    }

    Map<String, JSONArray> columnsAndRowsSet(JSONArray resultData, TableGrid tableGrid) {
        HashMap<String, JSONArray> reslut = new HashMap<String, JSONArray>();
        JSONArray columns = new JSONArray();
        JSONObject column = new JSONObject();
        column.put("size", 188);
        JSONArray rows = new JSONArray();
        JSONObject row = new JSONObject();
        row.put("size", 30);
        while (rows.length() < resultData.length()) {
            rows.put((Object)row);
        }
        while (columns.length() < resultData.getJSONArray(0).length()) {
            columns.put((Object)column);
        }
        JSONObject fistColumn = new JSONObject();
        fistColumn.put("size", 60);
        columns.put(0, (Object)fistColumn);
        if (tableGrid.getUseRowNum().equals("True")) {
            JSONObject hcColumn = new JSONObject();
            hcColumn.put("size", 60);
            columns.put(1, (Object)fistColumn);
        }
        reslut.put("columns", columns);
        reslut.put("rows", rows);
        return reslut;
    }

    @Override
    public Set<String> getGroupList(String taskKey, String formScheme) throws Exception {
        List<QueryModel> groupList = this.queryModeleDao.getByTaskKeyAndSchemeKey(taskKey, formScheme);
        HashSet<String> groupName = new HashSet<String>();
        groupList.stream().forEach(queryModel -> groupName.add(queryModel.getGroup()));
        return groupName;
    }

    @Override
    public String updateOrder(List<QueryModelNode> modelList) throws Exception {
        return this.queryModeleDao.updateOrder(modelList);
    }

    @Override
    public QueryModel getQueryModelByKey(String modelId) throws Exception {
        return this.queryModeleDao.getData(modelId);
    }

    @Override
    public Integer saveData(QueryModel queryModel) throws DBParaException {
        this.queryModeleDao.insert(queryModel);
        ArrayList modalNodeInfos = new ArrayList();
        if (org.springframework.util.StringUtils.hasText(queryModel.getItemTitle())) {
            this.authShareService.addCurUserPrivilege(queryModel.getKey(), FinalaccountQueryAuthResourceType.FQ_MODEL_NODE);
        }
        if (org.springframework.util.StringUtils.hasText(queryModel.getGroup())) {
            this.authShareService.addCurUserGroupReadPrivilege(queryModel.getFormschemeKey(), queryModel.getGroup(), FinalaccountQueryAuthResourceType.FQ_GROUP);
        }
        return 1;
    }

    @Override
    public Integer upData(QueryModel queryModel) throws DBParaException {
        return this.queryModeleDao.update(queryModel);
    }

    public QueryResult zbQuery(@RequestBody QueryConfigVO queryConfig, boolean fetch) throws JQException {
        try {
            if (StringUtils.isEmpty((String)queryConfig.getCacheId())) {
                queryConfig.setCacheId(Guid.newGuid());
            }
            ZBQueryEngine queryEngine = new ZBQueryEngine(queryConfig.getCacheId(), queryConfig.getQueryModel());
            ZBQueryResult queryResult = null;
            queryResult = queryEngine.query(queryConfig.getConditionValues(), queryConfig.getPageInfo());
            ZBQueryLogHelper.info((String)"\u67e5\u8be2\u6307\u6807\u6570\u636e", (String)("\u6a21\u677f\u6807\u9898\uff1a" + queryConfig.getTitle()));
            JSONObject nCellData = GridDataConverter.buildNCellData((com.jiuqi.bi.grid.GridData)queryResult.getData(), (boolean)false);
            QueryResult queryResultVO = new QueryResult();
            JSONObject hyperLinkData = HyperLinkDataCover.hyperlinkEnv2Json((HyperlinkEnv)queryResult.getHyperlinkEnv());
            JSONObject qlData = HyperLinkDataCover.hyperlinkCellMsg2Json((com.jiuqi.bi.grid.GridData)queryResult.getData());
            hyperLinkData.put("qlData", (Object)qlData);
            queryResultVO.setCacheId(queryConfig.getCacheId());
            queryResultVO.setGridData(nCellData.toString());
            queryResultVO.setPageInfo(queryResult.getPageInfo());
            queryResultVO.setColNames(queryResult.getColNames());
            queryResultVO.setHyperlinkEnv(hyperLinkData.toString());
            return queryResultVO;
        }
        catch (Exception e) {
            ZBQueryLogHelper.error((String)"\u67e5\u8be2\u6307\u6807\u6570\u636e", (String)("\u6a21\u677f\u6807\u9898\uff1a" + queryConfig.getTitle()));
            throw new JQException((ErrorEnum)ZBQueryErrorEnum.ZBQUERY_EXCEPTION_100, e.getMessage(), (Throwable)e);
        }
    }

    public Integer zbQuerySize(@RequestBody QueryConfigVO queryConfig) throws JQException {
        try {
            if (StringUtils.isEmpty((String)queryConfig.getCacheId())) {
                queryConfig.setCacheId(Guid.newGuid());
            }
            ZBQueryEngine queryEngine = new ZBQueryEngine(queryConfig.getCacheId(), queryConfig.getQueryModel());
            ZBQueryResult queryResult = null;
            queryResult = queryEngine.query(queryConfig.getConditionValues(), queryConfig.getPageInfo());
            ZBQueryLogHelper.info((String)"\u67e5\u8be2\u6307\u6807\u6570\u636e", (String)("\u6a21\u677f\u6807\u9898\uff1a" + queryConfig.getTitle()));
            int recordSize = queryResult.getPageInfo().getRecordSize();
            return recordSize;
        }
        catch (Exception e) {
            ZBQueryLogHelper.error((String)"\u67e5\u8be2\u6307\u6807\u6570\u636e", (String)("\u6a21\u677f\u6807\u9898\uff1a" + queryConfig.getTitle()));
            throw new JQException((ErrorEnum)ZBQueryErrorEnum.ZBQUERY_EXCEPTION_100, e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public void changeModelName(String name, String key) throws Exception {
        this.queryModeleDao.changeModelName(name, key);
    }

    @Override
    public void changeGroupName(String newName, String taskKey, String fromSchemeKey, String oldName) throws Exception {
        this.queryModeleDao.changeGroupName(newName, taskKey, fromSchemeKey, oldName);
    }

    @Override
    public void deletGroup(String name, String fromSchemeKey, String taskKey) throws Exception {
        this.queryModeleDao.deleteGroup(name, fromSchemeKey, taskKey);
    }

    private String getFullFieldCode(String fieldKey, String zbCode) {
        DataField dataField = this.runtimeDataSchemeService.getDataField(fieldKey);
        if (dataField == null) {
            return zbCode;
        }
        DataTable dataTable = this.runtimeDataSchemeService.getDataTable(dataField.getDataTableKey());
        if (dataTable == null) {
            return zbCode;
        }
        return String.format("%S.%S", dataTable.getCode(), zbCode);
    }
}

