/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.syntax.function.FunctionProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.consolidatedsystem.common.ReturnObject
 *  com.jiuqi.gcreport.consolidatedsystem.common.TreeNode
 *  com.jiuqi.gcreport.consolidatedsystem.dao.functionEditor.FunctionEditorDao
 *  com.jiuqi.gcreport.consolidatedsystem.entity.functionEditor.GcFunctionEditorEO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.FunctionEditorVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.FunctionTreeVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.TaskSchemeVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.designer.web.facade.formuladesigner.FormulaDesignFormData
 *  com.jiuqi.nr.designer.web.facade.formuladesigner.FormulaDesignLinksData
 *  com.jiuqi.nr.designer.web.service.FormulaDesignerService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  org.springframework.cloud.util.ProxyUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.calculate.formula.functionEditor.service;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.syntax.function.FunctionProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.calculate.formula.functionEditor.service.FunctionEditorService;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.consolidatedsystem.common.ReturnObject;
import com.jiuqi.gcreport.consolidatedsystem.common.TreeNode;
import com.jiuqi.gcreport.consolidatedsystem.dao.functionEditor.FunctionEditorDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.functionEditor.GcFunctionEditorEO;
import com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.FunctionEditorVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.FunctionTreeVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.TaskSchemeVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.designer.web.facade.formuladesigner.FormulaDesignFormData;
import com.jiuqi.nr.designer.web.facade.formuladesigner.FormulaDesignLinksData;
import com.jiuqi.nr.designer.web.service.FormulaDesignerService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.util.ProxyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FunctionEditorServiceImpl
implements FunctionEditorService {
    private Logger logger = LoggerFactory.getLogger(FunctionEditorServiceImpl.class);
    @Autowired
    private FunctionEditorDao functionEditorDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private FormulaDesignerService formulaDesignerService;
    @Autowired
    private IDataAccessProvider provider;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private DataModelService dataModelService;

    @Override
    public List<TreeNode> getAllTableDefinesGroups() {
        return null;
    }

    @Override
    public List<TreeNode> getTabDefineByTableKind(TableKind tableKind) {
        try {
            List defineList = this.dataModelService.getTableModelDefines();
            List<TreeNode> ret = defineList.stream().filter(designTableDefine -> !StringUtils.isEmpty((String)designTableDefine.getTitle())).filter(designTableDefine -> designTableDefine.getCode().toLowerCase().startsWith("gc")).map(designTableDefine -> {
                TreeNode item = new TreeNode();
                item.setCode(designTableDefine.getCode());
                item.setTitle(designTableDefine.getTitle());
                item.setLabel(designTableDefine.getTitle());
                item.setValue(designTableDefine.getID());
                item.setId(designTableDefine.getID());
                return item;
            }).collect(Collectors.toList());
            return ret;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage());
        }
    }

    @Override
    public List<TreeNode> getFieldsInTableForFunctionTree(String tableKey) {
        try {
            List columns = this.dataModelService.getColumnModelDefinesByTable(tableKey);
            List<TreeNode> ret = columns.stream().map(designfield -> {
                TreeNode item = new TreeNode();
                item.setCode(designfield.getCode());
                item.setTitle(designfield.getTitle());
                item.setLabel(designfield.getTitle());
                item.setValue(designfield.getID());
                item.setOrder(String.valueOf(designfield.getOrder()));
                return item;
            }).collect(Collectors.toList());
            return ret;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void addFunction(FunctionEditorVO functionEditorVO) {
        GcFunctionEditorEO functionEO = new GcFunctionEditorEO();
        BeanUtils.copyProperties(functionEditorVO, functionEO);
        String userId = NpContextHolder.getContext().getUser().getId();
        functionEO.setUserId(userId);
        functionEO.setId(UUIDUtils.newUUIDStr());
        this.functionEditorDao.save((DefaultTableEntity)functionEO);
    }

    @Override
    public void deleteFunction(String functionId) {
        GcFunctionEditorEO eo = new GcFunctionEditorEO();
        eo.setId(functionId);
        this.functionEditorDao.delete((BaseEntity)eo);
    }

    @Override
    public List<GcFunctionEditorEO> getFunctionsByUserId(Integer pageSize, Integer pageNum) {
        String userId = NpContextHolder.getContext().getUserId();
        return this.functionEditorDao.queryByUserId(userId, pageSize, pageNum);
    }

    @Override
    public int queryAllCountByUserId() {
        String userId = NpContextHolder.getContext().getUserId();
        return this.functionEditorDao.queryCountByUserId(userId);
    }

    @Override
    public List<FunctionTreeVO> getInheritFunctionsTree(Boolean notIncludeGc) {
        List<IFunction> functionList = this.getFunctions(notIncludeGc);
        Map<String, List<IFunction>> collect = functionList.stream().collect(Collectors.groupingBy(ifunction -> {
            String category = ifunction.category();
            if (StringUtils.isEmpty((String)category)) {
                category = "\u672a\u77e5\u5206\u7ec4";
            }
            return category;
        }));
        Set<String> strings = collect.keySet();
        ArrayList<FunctionTreeVO> treeVOS = new ArrayList<FunctionTreeVO>();
        strings.forEach(s -> {
            FunctionTreeVO vo = new FunctionTreeVO();
            vo.setTitle(s);
            vo.setCode("");
            vo.setDescription("");
            vo.setFunction(null);
            List children = ((List)collect.get(s)).stream().map(iFunction -> {
                FunctionTreeVO child = new FunctionTreeVO();
                List parameters = iFunction.parameters();
                StringBuilder split = new StringBuilder(iFunction.name() + "(");
                if (parameters.size() == 1) {
                    split.append(" ");
                }
                for (int i = 1; i < parameters.size(); ++i) {
                    split.append(" , ");
                }
                split.append(")");
                child.setTitle(iFunction.title());
                child.setCode(split.toString());
                child.setDefCode(iFunction.name());
                child.setDescription(iFunction.toDescription());
                child.setFunction(iFunction);
                child.setChildren(null);
                return child;
            }).collect(Collectors.toList());
            vo.setChildren(children);
            treeVOS.add(vo);
        });
        return treeVOS;
    }

    private List<IFunction> getFunctions(Boolean notIncludeGc) {
        ArrayList<IFunction> functionList = new ArrayList<IFunction>();
        for (IFunction function : FunctionProvider.GLOBAL_PROVIDER) {
            functionList.add(function);
        }
        if (!Objects.equals(notIncludeGc, Boolean.TRUE)) {
            for (IFunction function : this.getFunctionProvider()) {
                functionList.add((IFunction)ProxyUtils.getTargetObject((Object)function));
            }
        }
        return functionList;
    }

    @Override
    public List<TaskSchemeVO> getTaskSchemeByFormScheme(String schemeId) {
        List group = null;
        try {
            List formGroupDefines = this.runTimeViewController.queryRootGroupsByFormScheme(schemeId);
            group = formGroupDefines.stream().map(formGroupDefine -> {
                TaskSchemeVO vo = new TaskSchemeVO();
                vo.setKey(formGroupDefine.getKey());
                vo.setTitle(formGroupDefine.getTitle());
                try {
                    List children = this.runTimeViewController.getAllFormsInGroup(formGroupDefine.getKey()).stream().map(define -> {
                        TaskSchemeVO node = new TaskSchemeVO();
                        node.setKey(define.getKey());
                        node.setTitle(define.getTitle());
                        node.setChildren(new ArrayList());
                        return node;
                    }).collect(Collectors.toList());
                    vo.setChildren(children);
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                }
                return vo;
            }).collect(Collectors.toList());
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        return group;
    }

    @Override
    public List<TaskSchemeVO> getBondTaskAndSchemeByDataScheme(String dataSchemeId) {
        List<TaskDefine> allTaskDefines = this.runTimeViewController.getAllTaskDefines();
        allTaskDefines = allTaskDefines.stream().filter(task -> dataSchemeId.equals(task.getDataScheme())).collect(Collectors.toList());
        return this.getBondTaskAndSchemeVOS(allTaskDefines);
    }

    @Override
    public List<TaskSchemeVO> getBondTaskAndSchemeVOS() {
        List allTaskDefines = this.runTimeViewController.getAllTaskDefines();
        return this.getBondTaskAndSchemeVOS(allTaskDefines);
    }

    @Override
    public List<TaskSchemeVO> getBondTaskAndSchemeVOSByTaskKeyList(String[] taskKeyList) {
        ArrayList<TaskDefine> allTaskDefines = new ArrayList<TaskDefine>();
        for (int i = 0; i < taskKeyList.length; ++i) {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKeyList[i]);
            allTaskDefines.add(taskDefine);
        }
        return this.getBondTaskAndSchemeVOS(allTaskDefines);
    }

    private List<TaskSchemeVO> getBondTaskAndSchemeVOS(List<TaskDefine> taskDefines) {
        List<String> allBoundTasks = taskDefines.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).map(IBaseMetaItem::getKey).collect(Collectors.toList());
        Map<String, TaskDefine> taskDefineMap = taskDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, taskDefine -> taskDefine));
        ArrayList<TaskSchemeVO> taskSchemeVOS = new ArrayList<TaskSchemeVO>();
        allBoundTasks.forEach(uuid -> {
            try {
                List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(uuid);
                formSchemeDefines.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).forEach(formSchemeDefine -> {
                    List formGroupDefines = this.runTimeViewController.queryRootGroupsByFormScheme(formSchemeDefine.getKey());
                    TaskSchemeVO taskSchemeVO = new TaskSchemeVO();
                    TaskDefine taskDefine = (TaskDefine)taskDefineMap.get(uuid);
                    taskSchemeVO.setTitle(taskDefine.getTitle() + " | " + formSchemeDefine.getTitle());
                    List group = formGroupDefines.stream().map(formGroupDefine -> {
                        TaskSchemeVO vo = new TaskSchemeVO();
                        vo.setKey(formGroupDefine.getKey());
                        vo.setTitle(formGroupDefine.getTitle());
                        try {
                            List children = this.runTimeViewController.getAllFormsInGroup(formGroupDefine.getKey()).stream().map(define -> {
                                TaskSchemeVO node = new TaskSchemeVO();
                                node.setKey(define.getKey());
                                node.setTitle(define.getTitle());
                                node.setChildren(new ArrayList());
                                return node;
                            }).collect(Collectors.toList());
                            vo.setChildren(children);
                        }
                        catch (Exception e) {
                            this.logger.error(e.getMessage(), e);
                        }
                        return vo;
                    }).collect(Collectors.toList());
                    taskSchemeVO.setChildren(group);
                    taskSchemeVOS.add(taskSchemeVO);
                });
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        });
        return taskSchemeVOS;
    }

    @Override
    public FormulaDesignFormData getFormData(String formKey) {
        FormulaDesignFormData formData = new FormulaDesignFormData();
        try {
            formData.setFormKey(formKey);
            List regions = this.runTimeViewController.getAllRegionsInForm(formKey);
            HashSet<String> regionKeySet = new HashSet<String>();
            Grid2Data griddata = this.formulaDesignerService.getGridData(formKey);
            ArrayList<FormulaDesignLinksData> links = new ArrayList<FormulaDesignLinksData>();
            for (DataRegionDefine region : regions) {
                String regionKey = region.getKey();
                if (!regionKeySet.add(regionKey)) continue;
                List dataLinks = this.runTimeViewController.getAllLinksInRegion(regionKey);
                for (DataLinkDefine link : dataLinks) {
                    FormulaDesignLinksData linkData = new FormulaDesignLinksData();
                    linkData.setCol(link.getColNum());
                    linkData.setRow(link.getRowNum());
                    linkData.setX(link.getPosX());
                    linkData.setY(link.getPosY());
                    linkData.setLinkExpression(link.getLinkExpression());
                    DataField fieldDefine = this.dataSchemeService.getDataField(link.getLinkExpression());
                    String tableCode = "";
                    if (fieldDefine != null) {
                        tableCode = this.dataSchemeService.getDataTable(fieldDefine.getDataTableKey()).getCode();
                        linkData.setDataType(fieldDefine.getType());
                        linkData.setFieldCode(fieldDefine.getCode());
                        linkData.setFieldTitle(fieldDefine.getTitle());
                    } else {
                        linkData.setDataType(FieldType.FIELD_TYPE_STRING);
                        linkData.setFieldCode("\u672a\u77e5");
                        linkData.setFieldTitle("\u672a\u77e5");
                    }
                    links.add(linkData);
                    GridCellData cellData = griddata.getGridCellData(linkData.getX(), linkData.getY());
                    if (cellData == null) continue;
                    cellData.setShowText(tableCode + "[" + linkData.getFieldCode() + "]");
                    cellData.setEditText(linkData.getFieldTitle());
                    cellData.setHorzAlign(3);
                    cellData.setForeGroundColor(255);
                    cellData.setEditable(true);
                }
            }
            formData.setLinks(links);
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule((Module)module);
            if (null == griddata) {
                griddata = new Grid2Data();
                griddata.insertRows(0, 1, -1);
                griddata.insertColumns(0, 1);
                griddata.setRowHidden(0, true);
                griddata.setColumnHidden(0, true);
            }
            String result = mapper.writeValueAsString((Object)griddata);
            formData.setGridData(result.getBytes());
        }
        catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
        }
        return formData;
    }

    @Override
    public ReturnObject checkFunction(String formula, List<String> sourceTables, boolean notIncludeGc) {
        ReturnObject ret = new ReturnObject(true, null);
        GcReportDataSet dataSet = CollectionUtils.isEmpty(sourceTables) ? new GcReportDataSet(new String[0]) : new GcReportDataSet(sourceTables.toArray(new String[sourceTables.size()]));
        GcReportExceutorContext context = new GcReportExceutorContext(this.runtimeController);
        try {
            if (!notIncludeGc) {
                context.registerFunctionProvider(this.getFunctionProvider());
            }
            context.setData(null);
            context.setDefaultGroupName(CollectionUtils.isEmpty(sourceTables) ? null : sourceTables.get(0));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            ret.setSuccess(false);
            ret.setErrorMessage("\u6821\u9a8c\u516c\u5f0f\u51fa\u73b0\u9519\u8bef");
        }
        try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);){
            DimensionValueSet dst = new DimensionValueSet();
            evaluator.prepare((ExecutorContext)context, dst, formula);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            ret.setSuccess(false);
            ret.setErrorMessage(e.getMessage());
        }
        return ret;
    }

    protected IFunctionProvider getFunctionProvider() {
        return new GcReportFunctionProvider();
    }
}

