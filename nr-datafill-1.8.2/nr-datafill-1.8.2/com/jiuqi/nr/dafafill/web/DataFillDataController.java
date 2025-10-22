/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.common.DataSchemeUtils
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.server.util.ParameterConvertor
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dafafill.web;

import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.dafafill.model.ConditionField;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.DataFillModel;
import com.jiuqi.nr.dafafill.model.FieldFormat;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.EditorType;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.model.enums.ShowContent;
import com.jiuqi.nr.dafafill.service.IDataFillDataService;
import com.jiuqi.nr.dafafill.service.ParameterBuilderHelp;
import com.jiuqi.nr.dafafill.tree.DataFillSchemeTree;
import com.jiuqi.nr.dafafill.util.QueryFieldUtil;
import com.jiuqi.nr.dafafill.web.vo.DataFillParameterModelVO;
import com.jiuqi.nr.dafafill.web.vo.DataSchemeDim;
import com.jiuqi.nr.dafafill.web.vo.EntityDataVO;
import com.jiuqi.nr.dafafill.web.vo.TaskDim;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.DataSchemeUtils;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.server.util.ParameterConvertor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/datafill/model"})
@Api(value="\u81ea\u5b9a\u4e49\u5f55\u5165\u6a21\u578b\u8bbe\u8ba1")
public class DataFillDataController {
    @Autowired
    IDataFillDataService service;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IRuntimeDataSchemeService schemeService;
    @Autowired
    @Qualifier(value="RuntimeDataSchemeNoCacheServiceImpl-NO_CACHE")
    private IRuntimeDataSchemeService schemeServiceNoCache;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController runTimeViewController;
    @Autowired
    IDataDefinitionRuntimeController runtimeCtrl;
    @Autowired
    private IRunTimeViewController runTime;
    @Autowired
    private ParameterBuilderHelp parameterBuilderHelp;
    @Autowired
    private DataFillSchemeTree schemeTree;
    @Autowired
    PeriodEngineService periodEngineService;
    @Autowired
    IAdjustPeriodService adjustPeriodService;
    @Autowired
    IDesignTimeViewController designTimeViewController;

    @GetMapping(value={"idByDefLang"})
    @ApiOperation(value="\u6839\u636e\u6a21\u677f\u5b9a\u4e49\u548c\u8bed\u8a00\u73af\u5883\u83b7\u53d6\u6a21\u578bid")
    @RequiresPermissions(value={"nr:datafill:app"})
    public String getIdByDefLang(@RequestParam(required=true) String defId, String language) throws JQException {
        return null;
    }

    @GetMapping(value={"getByid/{id}"})
    @ApiOperation(value="\u6839\u636eid\u83b7\u53d6\u6a21\u578b\u5bf9\u8c61")
    @RequiresPermissions(value={"nr:datafill:app"})
    public DataFillModel getModelByid(@PathVariable String id) throws JQException {
        return null;
    }

    @GetMapping(value={"queryModelByDefLang"})
    @ApiOperation(value="\u6839\u636e\u6a21\u677f\u5b9a\u4e49\u548c\u8bed\u8a00\u73af\u5883\u83b7\u53d6\u6a21\u578b\u5bf9\u8c61")
    @RequiresPermissions(value={"nr:datafill:app"})
    public DataFillModel queryModelByDefLang(String defId, String language) throws JQException {
        return this.service.getModelByDefinition(defId, language);
    }

    @PostMapping(value={"save/{defId}/{language}", "save/{defId}"})
    @ApiOperation(value="\u4fdd\u5b58\u6a21\u578b\u5bf9\u8c61")
    @RequiresPermissions(value={"nr:datafill:app"})
    public void saveModel(@Valid @RequestBody DataFillModel model, @PathVariable(required=true) String defId, @PathVariable(required=false) String language) throws JQException {
        this.service.saveModel(defId, language, model);
    }

    @PostMapping(value={"deleteByDefLang/{}"})
    @ApiOperation(value="\u6839\u636eid\u5220\u9664\u6a21\u578b")
    @RequiresPermissions(value={"nr:datafill:app"})
    public void deleteByDefLang(String defId, String language) throws JQException {
    }

    @GetMapping(value={"/querySchemeDim"})
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u65b9\u6848\u7684\u7ef4\u5ea6\u4fe1\u606f")
    @RequiresPermissions(value={"nr:datafill:app"})
    public Map<String, DataSchemeDim> getSchemeDim(@RequestParam(required=true) String taskKey) {
        TaskDefine taskDefine;
        List<DataScheme> allScheme = new ArrayList();
        HashSet<String> entitySet = new HashSet<String>();
        if (StringUtils.hasText(taskKey) && (taskDefine = this.runTime.queryTaskDefine(taskKey)) != null) {
            List linkDefines = this.designTimeViewController.listTaskOrgLinkByTask(taskKey);
            for (TaskOrgLinkDefine define : linkDefines) {
                entitySet.add(define.getEntity());
            }
            DataScheme dataScheme = this.schemeServiceNoCache.getDataScheme(taskDefine.getDataScheme());
            if (dataScheme != null) {
                allScheme.add(dataScheme);
            }
        }
        if (CollectionUtils.isEmpty(allScheme)) {
            allScheme = this.schemeServiceNoCache.getAllDataScheme();
        }
        HashMap<String, DataSchemeDim> res = new HashMap<String, DataSchemeDim>();
        HashMap<String, QueryField> dimCache = new HashMap<String, QueryField>();
        for (DataScheme scheme : allScheme) {
            DataSchemeDim dimObj = new DataSchemeDim();
            dimObj.setSchemeCode(scheme.getCode());
            res.put(scheme.getCode(), dimObj);
            List dims = this.schemeServiceNoCache.getDataSchemeDimension(scheme.getKey());
            for (DataDimension dim : dims) {
                DimensionType dimensionType = dim.getDimensionType();
                switch (dimensionType) {
                    case UNIT: {
                        if (dimCache.containsKey(dim.getDimKey())) {
                            dimObj.setMaster(QueryFieldUtil.copyDimField((QueryField)dimCache.get(dim.getDimKey()), scheme));
                            break;
                        }
                        QueryField f = this.schemeTree.convertSchemeDimToQueryField(dim, scheme);
                        if (f == null) break;
                        dimObj.setMaster(f);
                        dimCache.put(dim.getDimKey(), dimObj.getMaster());
                        break;
                    }
                    case UNIT_SCOPE: {
                        if (!CollectionUtils.isEmpty(entitySet) && !entitySet.contains(dim.getDimKey())) break;
                        if (dimCache.containsKey(dim.getDimKey())) {
                            dimObj.addScope(QueryFieldUtil.copyDimField((QueryField)dimCache.get(dim.getDimKey()), scheme));
                            break;
                        }
                        QueryField f = this.schemeTree.convertSchemeDimToQueryField(dim, scheme);
                        if (f == null) break;
                        dimObj.addScope(f);
                        dimCache.put(dim.getDimKey(), f);
                        break;
                    }
                    case PERIOD: {
                        if (dimCache.containsKey(dim.getDimKey())) {
                            dimObj.setPeriod(QueryFieldUtil.copyDimField((QueryField)dimCache.get(dim.getDimKey()), scheme));
                            break;
                        }
                        QueryField f = this.schemeTree.convertSchemeDimToQueryField(dim, scheme);
                        if (f == null) break;
                        dimObj.setPeriod(f);
                        dimCache.put(dim.getDimKey(), dimObj.getPeriod());
                        break;
                    }
                    case DIMENSION: {
                        QueryField f = null;
                        if (dimCache.containsKey(dim.getDimKey())) {
                            f = QueryFieldUtil.copyDimField((QueryField)dimCache.get(dim.getDimKey()), scheme);
                        } else {
                            f = this.schemeTree.convertSchemeDimToQueryField(dim, scheme);
                            if (f == null) break;
                            dimCache.put(dim.getDimKey(), f);
                        }
                        dimObj.addScene(f);
                        if ((!"ADJUST".equals(dim.getDimKey()) || !this.schemeServiceNoCache.enableAdjustPeriod(scheme.getKey()).booleanValue()) && !DataSchemeUtils.isSingleSelect((DataDimension)dim)) break;
                        dimObj.addHideDimensions(f.getCode());
                    }
                }
            }
        }
        return res;
    }

    @GetMapping(value={"/queryEntityData/{entityId}"})
    @ApiOperation(value="\u83b7\u53d6\u5b9e\u4f53\u6570\u636e")
    @RequiresPermissions(value={"nr:datafill:app"})
    public List<EntityDataVO> queryEntityData(@PathVariable String entityId) {
        ArrayList<EntityDataVO> res = new ArrayList<EntityDataVO>();
        List allRows = null;
        try {
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setEntityView(this.runTimeViewController.buildEntityView(entityId));
            iEntityQuery.sorted(true);
            ExecutorContext context = new ExecutorContext(this.runtimeCtrl);
            IEntityTable iEntityTable = null;
            iEntityTable = iEntityQuery.executeFullBuild((IContext)context);
            allRows = iEntityTable.getAllRows();
            for (IEntityRow row : allRows) {
                res.add(new EntityDataVO(row.getCode(), row.getTitle()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @GetMapping(value={"/queryTaskDim"})
    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u4efb\u52a1\u7684\u7ef4\u5ea6\u4fe1\u606f")
    @RequiresPermissions(value={"nr:datafill:app"})
    public HashMap<String, TaskDim> getTaskDim() {
        List tasks = this.runTime.getAllTaskDefines();
        HashMap<String, QueryField> entityCase = new HashMap<String, QueryField>();
        HashMap<String, TaskDim> res = new HashMap<String, TaskDim>();
        for (TaskDefine t : tasks) {
            TaskDim dimObj = new TaskDim();
            dimObj.setTaskCode(t.getTaskCode());
            IEntityDefine unit = this.entityMetaService.queryEntity(t.getDw());
            dimObj.setUnit(this.convertTaskUnitToQueryField(unit, t, entityCase));
            dimObj.setVersion(unit.getVersion());
            IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(t.getDateTime());
            dimObj.setPeriod(this.convertTaskPeriodToQueryField(periodEntity, t, entityCase));
            res.put(t.getTaskCode(), dimObj);
        }
        return res;
    }

    @PostMapping(value={"/getParameter"})
    @ApiOperation(value="\u83b7\u53d6\u53c2\u6570\u6a21\u578b")
    @RequiresPermissions(value={"nr:datafill:app"})
    public List<DataFillParameterModelVO> getParameter(@RequestBody DataFillContext dataFillContext) throws Exception {
        ArrayList<DataFillParameterModelVO> list = new ArrayList<DataFillParameterModelVO>();
        List<ParameterModel> paramModels = this.parameterBuilderHelp.getParameterModels(dataFillContext);
        List<ConditionField> conditionFields = dataFillContext.getModel().getConditionFields();
        for (int i = 0; i < paramModels.size(); ++i) {
            ParameterModel paramModel = paramModels.get(i);
            JSONObject json = ParameterConvertor.toJson(null, (ParameterModel)paramModel, (boolean)false);
            DataFillParameterModelVO dataFillParameterModelVO = new DataFillParameterModelVO();
            dataFillParameterModelVO.setJson(json.toString());
            dataFillParameterModelVO.setQuickCondition(conditionFields.get(i).isQuickCondition());
            list.add(dataFillParameterModelVO);
        }
        return list;
    }

    private void buildPeriodMaxMin(DataFillContext dataFillContext) {
        Optional<QueryField> periodField = dataFillContext.getModel().getQueryFields().stream().filter(e -> e.getFieldType() == FieldType.PERIOD).findFirst();
        if (periodField.isPresent()) {
            QueryField field = periodField.get();
            String taskKey = dataFillContext.getModel().getExtendedData().get("TASKKEY");
            if (StringUtils.hasText(taskKey)) {
                this.buildPeriodMaxMinByTask(field, taskKey);
            }
        }
    }

    private void buildPeriodMaxMinByFormScheme(QueryField field, String formSchemeKey) throws Exception {
        String temp;
        String periodValue;
        FormSchemeDefine formScheme = this.runTime.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTime.queryTaskDefine(formScheme.getTaskKey());
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
        List defineList = this.runTime.querySchemePeriodLinkBySchemeSort(formSchemeKey);
        List list = defineList.stream().map(SchemePeriodLinkDefine::getPeriodKey).collect(Collectors.toList());
        if (!list.contains(periodValue = periodProvider.getCurPeriod().getCode())) {
            periodValue = (String)list.get(list.size() - 1);
        }
        String priorPeriod = periodValue;
        do {
            temp = priorPeriod;
        } while (list.contains(priorPeriod = periodProvider.priorPeriod(priorPeriod)));
        field.setMinValue(temp);
        String nextPeriod = periodValue;
        do {
            temp = nextPeriod;
        } while (list.contains(nextPeriod = periodProvider.nextPeriod(nextPeriod)));
        field.setMaxValue(temp);
    }

    private void buildPeriodMaxMinByTask(QueryField field, String taskKey) {
        TaskDefine taskDefine = this.runTime.queryTaskDefine(taskKey);
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
        String fromPeriod = taskDefine.getFromPeriod();
        String toPeriod = taskDefine.getToPeriod();
        if (!StringUtils.hasText(fromPeriod)) {
            field.setMinValue(periodProvider.getPeriodCodeRegion()[0]);
        } else {
            field.setMinValue(fromPeriod);
        }
        if (!StringUtils.hasText(toPeriod)) {
            String[] region = periodProvider.getPeriodCodeRegion();
            field.setMaxValue(region[region.length - 1]);
        } else {
            field.setMaxValue(toPeriod);
        }
    }

    @PostMapping(value={"/getOrg"})
    @ApiOperation(value="\u83b7\u53d6\u5355\u4f4d\u4fe1\u606f")
    @RequiresPermissions(value={"nr:datafill:app"})
    public List<EntityDataVO> getOrgItem(@RequestParam String entityId, @RequestParam String periodId, @RequestParam String period, @RequestBody String[] entityKeyDatas) {
        ArrayList<EntityDataVO> list = new ArrayList<EntityDataVO>();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        try {
            IEntityDataService entityDataService = (IEntityDataService)SpringBeanUtils.getBean(IEntityDataService.class);
            IEntityViewRunTimeController runTimeController = (IEntityViewRunTimeController)SpringBeanUtils.getBean(IEntityViewRunTimeController.class);
            IDataDefinitionRuntimeController definitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
            EntityViewDefine entityViewDefine = runTimeController.buildEntityView(entityId);
            IEntityQuery entityQuery = entityDataService.newEntityQuery();
            entityQuery.setEntityView(entityViewDefine);
            entityQuery.setMasterKeys(dimensionValueSet);
            ExecutorContext executorContext = new ExecutorContext(definitionRuntimeController);
            executorContext.setPeriodView(periodId);
            IEntityTable iEntityTable = entityQuery.executeReader((IContext)executorContext);
            HashSet set = new HashSet();
            Collections.addAll(set, entityKeyDatas);
            Map entityRowMap = iEntityTable.quickFindByEntityKeys(set);
            for (String entityKeyData : entityKeyDatas) {
                EntityDataVO entity = new EntityDataVO();
                entity.setCode(entityKeyData);
                entity.setTitle(((IEntityRow)entityRowMap.get(entityKeyData)).getTitle());
                list.add(entity);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @PostMapping(value={"/getPeriod"})
    @ApiOperation(value="\u83b7\u53d6\u65f6\u671f\u4fe1\u606f")
    @RequiresPermissions(value={"nr:datafill:app"})
    public List<EntityDataVO> getPeriodTitle(@RequestParam String entityId, @RequestBody String[] periodDatas) {
        ArrayList<EntityDataVO> list = new ArrayList<EntityDataVO>();
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        IPeriodProvider periodProvider = periodAdapter.getPeriodProvider(entityId);
        for (String data : periodDatas) {
            EntityDataVO entity = new EntityDataVO();
            entity.setCode(data);
            entity.setTitle(periodProvider.getPeriodTitle(data));
            list.add(entity);
        }
        return list;
    }

    @GetMapping(value={"/enableAdjustPeriod/{schemeCode}"})
    @ApiOperation(value="\u6570\u636e\u65b9\u6848\u662f\u5426\u5f00\u542f\u4e86\u8c03\u6574\u671f")
    @RequiresPermissions(value={"nr:datafill:app"})
    public Map enableAdjustPeriod(@PathVariable String schemeCode) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("enableAdjustPeriod", "false");
        DataScheme scheme = this.schemeService.getDataSchemeByCode(schemeCode);
        if (scheme == null) {
            return map;
        }
        map.put("enableAdjustPeriod", this.schemeService.enableAdjustPeriod(scheme.getKey()).toString());
        map.put("dataSchemeKey", scheme.getKey());
        return map;
    }

    @GetMapping(value={"/getAdjustTitle/{schemeCode}/{period}/{code}"})
    @ApiOperation(value="\u83b7\u53d6\u8c03\u6574\u671f\u540d\u79f0")
    @RequiresPermissions(value={"nr:datafill:app"})
    public String getAdjustTitle(@PathVariable String schemeCode, @PathVariable String period, @PathVariable String code) {
        schemeCode = HtmlUtils.cleanUrlXSS((String)schemeCode);
        period = HtmlUtils.cleanUrlXSS((String)period);
        code = HtmlUtils.cleanUrlXSS((String)code);
        DataScheme scheme = this.schemeService.getDataSchemeByCode(schemeCode);
        if (scheme == null) {
            return code;
        }
        AdjustPeriod adjustPeriod = this.adjustPeriodService.queryAdjustPeriods(scheme.getKey(), period, code);
        return HtmlUtils.cleanUrlXSS((String)adjustPeriod.getTitle());
    }

    private QueryField convertTaskPeriodToQueryField(IPeriodEntity periodEntity, TaskDefine t, HashMap<String, QueryField> entityCase) {
        QueryField field = entityCase.get(periodEntity.getKey());
        if (field == null) {
            field = new QueryField();
            field.setId(periodEntity.getKey());
            field.setCode(periodEntity.getCode());
            field.setDataSchemeCode(t.getTaskCode());
            field.setFullCode(t.getTaskCode() + "." + periodEntity.getCode());
            field.setTitle(periodEntity.getTitle());
            field.setFieldType(FieldType.PERIOD);
            field.setEditorType(EditorType.CALENDAR);
            entityCase.put(periodEntity.getKey(), field);
        } else {
            field = this.cloneQueryField(field);
            field.setDataSchemeCode(t.getTaskCode());
            field.setFullCode(t.getTaskCode() + "." + periodEntity.getCode());
        }
        return field;
    }

    private QueryField convertTaskUnitToQueryField(IEntityDefine define, TaskDefine t, HashMap<String, QueryField> entityCase) {
        QueryField field = entityCase.get(define.getId());
        if (field == null) {
            field = new QueryField();
            field.setId(define.getId());
            field.setCode(define.getCode());
            field.setDataSchemeCode(t.getTaskCode());
            field.setFullCode(t.getTaskCode() + "." + define.getCode());
            field.setTitle(define.getTitle());
            field.setFieldType(FieldType.MASTER);
            field.setEditorType(EditorType.UNITSELECTOR);
            FieldFormat fieldFormat = new FieldFormat();
            fieldFormat.setShowContent(ShowContent.TITLE);
            field.setShowFormat(fieldFormat);
            entityCase.put(define.getId(), field);
        } else {
            field = this.cloneQueryField(field);
            field.setDataSchemeCode(t.getTaskCode());
            field.setFullCode(t.getTaskCode() + "." + define.getCode());
        }
        return field;
    }

    private QueryField cloneQueryField(QueryField f) {
        QueryField field = new QueryField();
        field.setId(f.getId());
        field.setCode(f.getCode());
        field.setDataSchemeCode(f.getDataSchemeCode());
        field.setFullCode(f.getFullCode());
        field.setTitle(f.getTitle());
        field.setFieldType(f.getFieldType());
        field.setDataType(f.getDataType());
        field.setEditorType(f.getEditorType());
        field.setShowFormat(f.getShowFormat());
        return field;
    }
}

