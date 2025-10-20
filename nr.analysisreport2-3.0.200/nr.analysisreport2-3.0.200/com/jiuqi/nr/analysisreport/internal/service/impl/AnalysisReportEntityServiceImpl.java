/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  io.netty.util.internal.StringUtil
 *  javax.annotation.Resource
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.analysisreport.internal.service.impl;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.analysisreport.facade.DimensionObj;
import com.jiuqi.nr.analysisreport.helper.GenerateContext;
import com.jiuqi.nr.analysisreport.internal.service.IAnalysisReportEntityService;
import com.jiuqi.nr.analysisreport.utils.AnaUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class AnalysisReportEntityServiceImpl
implements IAnalysisReportEntityService {
    @Autowired
    IRunTimeViewController iRunTimeViewController;
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    IDataAccessProvider dataAccessProvider;
    @Resource
    RuntimeViewController runtimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityController;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;

    @Override
    public IEntityDefine queryEntityDefine(String entityId) throws Exception {
        return this.entityMetaService.queryEntity(entityId);
    }

    @Override
    public IEntityDefine queryEntityDefineByCode(String code) throws Exception {
        return this.entityMetaService.queryEntityByCode(code);
    }

    @Override
    public IEntityDefine queryEntityDefineByEntityId(String entityId) throws Exception {
        return this.entityMetaService.queryEntity(entityId);
    }

    @Override
    public IPeriodEntity queryPeriodEntityByEntityId(String entityId) {
        return this.periodEntityAdapter.getPeriodEntity(entityId);
    }

    @Override
    public IEntityModel queryEntityModel(String entityId) {
        try {
            IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
            return entityModel;
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public IEntityAttribute queryBizKeyField(String entityId) {
        IEntityModel entityModel = this.queryEntityModel(entityId);
        if (entityModel != null) {
            IEntityAttribute bizKeyField = entityModel.getBizKeyField();
            return bizKeyField;
        }
        return null;
    }

    @Override
    public List<TaskDefine> getAllTaskDefines() {
        return this.iRunTimeViewController.getAllTaskDefines();
    }

    @Override
    public TaskDefine queryTaskDefine(String ts_key) {
        return this.iRunTimeViewController.queryTaskDefine(ts_key);
    }

    @Override
    public List<TaskDefine> getAllRuntimeTaskDefines() {
        return this.runtimeViewController.getAllTaskDefines();
    }

    @Override
    public List<FormSchemeDefine> queryFormSchemeByTask(String fs_key) throws Exception {
        return this.iRunTimeViewController.queryFormSchemeByTask(fs_key);
    }

    @Override
    public List<FormGroupDefine> queryRootGroupsByFormScheme(String fs_key) {
        return this.iRunTimeViewController.queryRootGroupsByFormScheme(fs_key);
    }

    @Override
    public List<FormDefine> getAllFormsInGroup(String gd_key) throws Exception {
        return this.iRunTimeViewController.getAllFormsInGroup(gd_key);
    }

    @Override
    public FormDefine queryFormById(String fd_key) {
        return this.iRunTimeViewController.queryFormById(fd_key);
    }

    @Override
    public List<FormDefine> queryFormsById(List<String> fd_key) {
        return this.iRunTimeViewController.queryFormsById(fd_key);
    }

    @Override
    public List<FormSchemeDefine> queryFormSchemeByForm(String fd_key) throws Exception {
        return this.iRunTimeViewController.queryFormSchemeByForm(fd_key);
    }

    @Override
    public boolean isPeriodEntity(String entityId) {
        return this.periodEntityAdapter.isPeriodEntity(entityId);
    }

    @Override
    public Set<Map<String, Object>> getEntityDefineByFormula(String formula) throws Exception {
        HashSet<Map<String, Object>> res = new HashSet<Map<String, Object>>();
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(executorContext);
        QueryContext qContext = new QueryContext(executorContext, null);
        ReportFormulaParser parser = dataAssist.createFormulaParser(false);
        IExpression expression = parser.parseAssign(formula, (IContext)qContext);
        Iterator exp = expression.iterator();
        ArrayList<String> tableKey = new ArrayList<String>();
        while (exp.hasNext()) {
            IASTNode n = (IASTNode)exp.next();
            QueryFields fields = ExpressionUtils.getQueryFields((IASTNode)n);
            int fieldsCount = fields.getCount();
            for (int i = 0; i < fieldsCount; ++i) {
                QueryField fieldItem = fields.getItem(i);
                FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldItem.getUID());
                List formSchemeDefines = this.iRunTimeViewController.queryFormSchemeByField(fieldDefine.getKey());
                if (formSchemeDefines == null || formSchemeDefines.size() <= 0) continue;
                for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                    TaskDefine taskDefine = this.queryTaskDefine(formSchemeDefine.getTaskKey());
                    String masterEntitiesKey = taskDefine.getMasterEntitiesKey();
                    for (String entityKey : masterEntitiesKey.split(";")) {
                        ArrayList<Map<String, Object>> masterEntrys;
                        if (this.isPeriodEntity(entityKey)) {
                            IPeriodEntity periodEntity = this.queryPeriodEntityByEntityId(entityKey);
                            if (tableKey.contains(periodEntity.getKey())) continue;
                            masterEntrys = new ArrayList<Map<String, Object>>();
                            this.setMasterEntry(periodEntity, masterEntrys);
                            if (masterEntrys.size() > 0) {
                                masterEntrys.forEach(x -> res.add((Map<String, Object>)x));
                            }
                            tableKey.add(periodEntity.getKey());
                            continue;
                        }
                        IEntityDefine entityDefine = this.queryEntityDefineByEntityId(entityKey);
                        if (tableKey.contains(entityDefine.getId())) continue;
                        masterEntrys = new ArrayList();
                        this.setMasterEntry(entityDefine, true, masterEntrys);
                        if (masterEntrys.size() > 0) {
                            masterEntrys.forEach(x -> res.add((Map<String, Object>)x));
                        }
                        tableKey.add(entityDefine.getId());
                    }
                }
            }
        }
        return res;
    }

    @Override
    public Set<DimensionObj> getDimensionByFormula(String formula) throws Exception {
        HashSet<DimensionObj> masterEntityKeySet = new HashSet<DimensionObj>();
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(executorContext);
        QueryContext qContext = new QueryContext(executorContext, null);
        ReportFormulaParser parser = dataAssist.createFormulaParser(false);
        IExpression expression = parser.parseAssign(formula, (IContext)qContext);
        for (IASTNode n : expression) {
            QueryFields fields = ExpressionUtils.getQueryFields((IASTNode)n);
            int fieldsCount = fields.getCount();
            for (int i = 0; i < fieldsCount; ++i) {
                QueryField fieldItem = fields.getItem(i);
                TableDefine tableDefine = this.dataDefinitionRuntimeController.queryTableDefineByCode(fieldItem.getTable().getTableName());
                FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable(fieldItem.getFieldCode(), tableDefine.getKey());
                List formSchemeDefines = this.iRunTimeViewController.queryFormSchemeByField(fieldDefine.getKey());
                if (formSchemeDefines == null || formSchemeDefines.size() <= 0) continue;
                for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                    masterEntityKeySet.addAll(this.makeDimensionObject(formSchemeDefine.getTaskKey()));
                }
            }
        }
        return masterEntityKeySet;
    }

    public Set<String> getEntityViewKeyByFormula(String formula) throws Exception {
        return null;
    }

    @Override
    public Set<String> getEntityViewKeyByFormula(String formula, GenerateContext generateContext) throws Exception {
        HashSet<String> entityViewKeys = new HashSet<String>();
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(executorContext);
        QueryContext qContext = new QueryContext(executorContext, null);
        if (StringUtils.isNotEmpty((CharSequence)generateContext.getQcyProjectId())) {
            VariableManager variableManager = executorContext.getVariableManager();
            variableManager.add(new Variable("QCY_PROJECTID", "QCY_PROJECTID", 6, (Object)generateContext.getQcyProjectId()));
        }
        ReportFormulaParser parser = dataAssist.createFormulaParser(false);
        IExpression expression = parser.parseAssign(formula, (IContext)qContext);
        for (IASTNode n : expression) {
            QueryFields fields = ExpressionUtils.getQueryFields((IASTNode)n);
            int fieldsCount = fields.getCount();
            for (int i = 0; i < fieldsCount; ++i) {
                QueryField fieldItem = fields.getItem(i);
                DataField dataField = this.iRuntimeDataSchemeService.getDataFieldByColumnKey(fieldItem.getUID());
                if (dataField == null) continue;
                List dimensions = this.iRuntimeDataSchemeService.getDataSchemeDimension(dataField.getDataSchemeKey());
                block6: for (int j = 0; j < dimensions.size(); ++j) {
                    DataDimension dataDimension = (DataDimension)dimensions.get(j);
                    DimensionType dimensionType = dataDimension.getDimensionType();
                    switch (dimensionType) {
                        case PERIOD: {
                            IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(dataDimension.getDimKey());
                            entityViewKeys.add(periodEntity.getKey());
                            continue block6;
                        }
                        case UNIT: 
                        case DIMENSION: {
                            IEntityDefine dimEntity = this.entityMetaService.queryEntity(dataDimension.getDimKey());
                            if (dimEntity == null) continue block6;
                            entityViewKeys.add(dimEntity.getId());
                            continue block6;
                        }
                    }
                }
            }
        }
        return entityViewKeys;
    }

    @Override
    public Map<String, Map<String, Object>> getDimensionDefine(String taskKey) {
        HashMap<String, Map<String, Object>> defines = new HashMap<String, Map<String, Object>>();
        TaskDefine taskDefine = this.queryTaskDefine(taskKey);
        String dw = taskDefine.getDw();
        String datetime = taskDefine.getDateTime();
        String dims = taskDefine.getDims();
        try {
            IEntityDefine entity = this.queryEntityDefineByEntityId(dw);
            defines.put(dw, this.getDimensionDefine(entity, false));
        }
        catch (Exception entity) {
            // empty catch block
        }
        IPeriodEntity period = this.queryPeriodEntityByEntityId(datetime);
        defines.put(datetime, this.getDimensionDefine(period));
        if (!StringUtil.isNullOrEmpty((String)dims)) {
            for (String dim : dims.split(";")) {
                if (defines.containsKey(dim)) continue;
                try {
                    if (this.isPeriodEntity(dim)) {
                        IPeriodEntity periodEntity = this.queryPeriodEntityByEntityId(dim);
                        defines.put(dim, this.getDimensionDefine(periodEntity));
                        continue;
                    }
                    IEntityDefine entityDefine = this.queryEntityDefineByEntityId(dim);
                    defines.put(dim, this.getDimensionDefine(entityDefine, true));
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        return defines;
    }

    private void setMasterEntry(IEntityDefine entityDefine, boolean isNomal, List<Map<String, Object>> masterEntrys) throws Exception {
        masterEntrys.add(this.getDimensionDefine(entityDefine, isNomal));
    }

    private void setMasterEntry(IPeriodEntity periodEntity, List<Map<String, Object>> masterEntrys) throws Exception {
        masterEntrys.add(this.getDimensionDefine(periodEntity));
    }

    private Map<String, Object> getDimensionDefine(IEntityDefine entityDefine, boolean isNomal) {
        HashMap<String, Object> define = new HashMap<String, Object>();
        define.put("key", entityDefine.getId());
        define.put("code", entityDefine.getCode());
        define.put("tableName", entityDefine.getDimensionName());
        define.put("title", entityDefine.getTitle());
        define.put("viewKey", entityDefine.getId());
        define.put("dimensionView", null);
        define.put("dimensionView", entityDefine.getId());
        define.put("dimensionValue", null);
        define.put("dimensionTitle", null);
        define.put("type", isNomal ? "DIMENSION_NOMAL" : "DIMENSION_UNIT");
        return define;
    }

    private Map<String, Object> getDimensionDefine(IPeriodEntity periodEntity) {
        HashMap<String, Object> define = new HashMap<String, Object>();
        define.put("key", periodEntity.getKey());
        define.put("code", periodEntity.getCode());
        define.put("tableName", periodEntity.getDimensionName());
        define.put("title", periodEntity.getTitle());
        define.put("viewKey", periodEntity.getKey());
        define.put("dimensionView", null);
        define.put("dimensionView", periodEntity.getKey());
        define.put("dimensionValue", null);
        define.put("dimensionTitle", null);
        define.put("type", "DIMENSION_PERIOD");
        return define;
    }

    @Override
    public Map<String, String> queryEntityData(String entityId, String key, String code, ExecutorContext executorContext) {
        HashMap<String, String> res = new HashMap<String, String>();
        res.put("key", key);
        res.put("code", code);
        res.put("viewKey", entityId);
        try {
            IEntityQuery query = this.entityDataService.newEntityQuery();
            query.setEntityView(this.entityController.buildEntityView(entityId));
            IEntityTable entityTable = query.executeReader((IContext)executorContext);
            IEntityRow entityRow = entityTable.findByEntityKey(key);
            String title = entityRow.getAsString(code);
            res.put("title", title);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return res;
    }

    @Override
    public List<DimensionObj> makeDimensionObject(String taskID) throws Exception {
        ArrayList<DimensionObj> objects = new ArrayList<DimensionObj>();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskID);
        String dw = taskDefine.getDw();
        String dateTime = taskDefine.getDateTime();
        String dims = taskDefine.getDims();
        if (!StringUtil.isNullOrEmpty((String)dw)) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(dw);
            objects.add(DimensionObj.toTreeNodeObj(entityDefine));
        }
        if (!StringUtil.isNullOrEmpty((String)dateTime)) {
            IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(dateTime);
            objects.add(DimensionObj.toTreeNodeObj(periodEntity));
        }
        if (!StringUtil.isNullOrEmpty((String)dims)) {
            String[] entitiesKeyArr;
            for (String entityKey : entitiesKeyArr = dims.split(";")) {
                IEntityDefine dimEntity = this.entityMetaService.queryEntity(entityKey);
                objects.add(DimensionObj.toTreeNodeObj(dimEntity));
            }
        }
        return objects;
    }

    @Override
    public EntityViewDefine buildEntityViewDefine(String entityId) {
        return this.entityController.buildEntityView(entityId);
    }

    @Override
    public Set<String> getDataSchemeByFormula(String formula, Map<String, Object> varMap) {
        HashSet<String> dataSchemeKey = new HashSet<String>();
        try {
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(executorContext);
            QueryContext qContext = new QueryContext(executorContext, null);
            if (!CollectionUtils.isEmpty(varMap)) {
                AnaUtils.setFormulaVariable(executorContext.getVariableManager(), varMap);
            }
            ReportFormulaParser parser = dataAssist.createFormulaParser(false);
            IExpression expression = parser.parseAssign(formula, (IContext)qContext);
            for (IASTNode n : expression) {
                QueryFields fields = ExpressionUtils.getQueryFields((IASTNode)n);
                int fieldsCount = fields.getCount();
                for (int i = 0; i < fieldsCount; ++i) {
                    QueryField fieldItem = fields.getItem(i);
                    DataField dataField = this.iRuntimeDataSchemeService.getDataFieldByColumnKey(fieldItem.getUID());
                    if (dataField == null) continue;
                    dataSchemeKey.add(dataField.getDataSchemeKey());
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return dataSchemeKey;
    }
}

