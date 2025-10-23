/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.dataengine.var.VariableManagerBase
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO
 *  com.jiuqi.nr.datascheme.internal.service.IDesignDataSchemeTreeService
 *  com.jiuqi.nr.datascheme.internal.service.IRuntimeDataSchemeTreeService
 *  com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO
 *  com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IRunTimeFormulaController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IDesignTimeFMDMAttributeService
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.function.func.doc.IFuncDoc
 *  com.jiuqi.nr.function.func.doc.service.IFuncDocService
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter
 *  com.jiuqi.nvwa.cellbook.model.Cell
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.cellbook.model.CellSheet
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.formulaeditor.internal.param.core;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.dataengine.var.VariableManagerBase;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.service.IDesignDataSchemeTreeService;
import com.jiuqi.nr.datascheme.internal.service.IRuntimeDataSchemeTreeService;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO;
import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IRunTimeFormulaController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IDesignTimeFMDMAttributeService;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.formulaeditor.common.EditorSchemeNodeFilter;
import com.jiuqi.nr.formulaeditor.common.EditorUtils;
import com.jiuqi.nr.formulaeditor.common.FormulaMonitor;
import com.jiuqi.nr.formulaeditor.dto.EditObject;
import com.jiuqi.nr.formulaeditor.dto.FormulaObj;
import com.jiuqi.nr.formulaeditor.dto.ParamObj;
import com.jiuqi.nr.formulaeditor.internal.IFormulaEditor;
import com.jiuqi.nr.formulaeditor.vo.EditorNodeData;
import com.jiuqi.nr.formulaeditor.vo.EntityData;
import com.jiuqi.nr.formulaeditor.vo.FieldData;
import com.jiuqi.nr.formulaeditor.vo.FormData;
import com.jiuqi.nr.formulaeditor.vo.FormulaCheckData;
import com.jiuqi.nr.formulaeditor.vo.FormulaVariableData;
import com.jiuqi.nr.formulaeditor.vo.FunctionData;
import com.jiuqi.nr.formulaeditor.vo.FunctionList;
import com.jiuqi.nr.formulaeditor.vo.LinkData;
import com.jiuqi.nr.formulaeditor.vo.ParameterData;
import com.jiuqi.nr.formulaeditor.vo.RegionData;
import com.jiuqi.nr.formulaeditor.vo.SchemeData;
import com.jiuqi.nr.formulaeditor.vo.TaskParam;
import com.jiuqi.nr.formulaeditor.vo.TreeObj;
import com.jiuqi.nr.function.func.doc.IFuncDoc;
import com.jiuqi.nr.function.func.doc.service.IFuncDocService;
import com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public abstract class DefaultFormulaEditor
implements IFormulaEditor {
    private static final Logger logger = LoggerFactory.getLogger(DefaultFormulaEditor.class);
    @Autowired
    protected IDesignTimeViewController iDesignTimeViewController;
    @Autowired
    protected IRunTimeViewController iRunTimeViewController;
    @Autowired
    protected IDesignDataSchemeService iDesignDataSchemeService;
    @Autowired
    protected IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    protected IDesignTimeFormulaController iDesignTimeFormulaController;
    @Autowired
    protected IRunTimeFormulaController iRunTimeFormulaController;
    @Autowired
    protected IDesignTimeFMDMAttributeService fmdmAttributeService;
    @Autowired
    protected IEntityDataService iEntityDataService;
    @Autowired
    protected DataModelService dataModelService;
    @Autowired
    protected IEntityMetaService iEntityMetaService;
    @Autowired
    protected IEntityViewRunTimeController viewRunTimeController;
    @Autowired
    protected IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    protected IDataDefinitionDesignTimeController iDataDefinitionDesignTimeController;
    @Autowired
    protected com.jiuqi.nr.definition.controller.IDesignTimeViewController oldIDesignTimeViewController;
    @Autowired
    protected IDataSchemeAuthService dataSchemeAuthService;
    @Autowired
    protected IDesignDataSchemeTreeService iDesignDataSchemeTreeService;
    @Autowired
    protected IRuntimeDataSchemeTreeService iRuntimeDataSchemeTreeService;
    @Autowired
    protected IFuncDocService funcDocService;
    private static final String SIGN_CATEGORY = "\u7b26\u53f7";

    @Override
    public TaskParam queryTaskData(EditObject editObject) {
        TaskParam taskParam = new TaskParam();
        ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> tree = new ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>();
        com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> rootTaskGroupITree = EditorUtils.createRootTaskGroupITree();
        if ("runtime".equals(editObject.getRunType())) {
            List rootGroup = this.iRunTimeViewController.listTaskGroupByParentGroupStream(null).auth().getList();
            List<com.jiuqi.nr.formulaeditor.vo.ITree<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>> recursion = this.createRunTreeNode(rootGroup);
            List allTaskDefines = this.iRunTimeViewController.listTaskByTaskGroupStream(null).auth().getList();
            for (TaskDefine taskDefine : allTaskDefines) {
                if (!this.checkTaskHasScheme(taskDefine.getKey(), "runtime")) continue;
                com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> taskNode = EditorUtils.createTaskITree(taskDefine, null);
                recursion.add(taskNode);
            }
            rootTaskGroupITree.setChildren(recursion);
            tree.add(rootTaskGroupITree);
            taskParam.setTree(tree);
        } else {
            List rootGroup = this.iDesignTimeViewController.listTaskGroupByParentGroup(null);
            List<com.jiuqi.nr.formulaeditor.vo.ITree<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>> recursion = this.createDesTreeNode(rootGroup);
            List notGroupTasks = this.iDesignTimeViewController.listTaskByTaskGroup(null);
            for (DesignTaskDefine taskDefine : notGroupTasks) {
                if (!this.checkTaskHasScheme(taskDefine.getKey(), "designer")) continue;
                com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> taskNode = EditorUtils.createTaskITree((TaskDefine)taskDefine, null);
                recursion.add(taskNode);
            }
            rootTaskGroupITree.setChildren(recursion);
            tree.add(rootTaskGroupITree);
            taskParam.setTree(tree);
        }
        return taskParam;
    }

    @Override
    public List<SchemeData> querySchemeData(EditObject editObject, ParamObj paramObj) {
        ArrayList<SchemeData> schemeDatas = new ArrayList<SchemeData>();
        if ("runtime".equals(editObject.getRunType())) {
            List formSchemeDefines = this.iRunTimeViewController.listFormSchemeByTaskStream(paramObj.getTaskKey()).auth().getList();
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                if (!this.checkSchemeHasForm(formSchemeDefine.getKey(), "runtime")) continue;
                schemeDatas.add(EditorUtils.createSchemeData(formSchemeDefine));
            }
        } else {
            List designFormSchemeDefines = this.iDesignTimeViewController.listFormSchemeByTask(paramObj.getTaskKey());
            for (DesignFormSchemeDefine designFormSchemeDefine : designFormSchemeDefines) {
                if (!this.checkSchemeHasForm(designFormSchemeDefine.getKey(), "designer")) continue;
                schemeDatas.add(EditorUtils.createSchemeData((FormSchemeDefine)designFormSchemeDefine));
            }
        }
        return schemeDatas;
    }

    @Override
    public List<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> queryFormData(EditObject editObject, ParamObj paramObj) {
        ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> treeObjs = new ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>();
        if ("runtime".equals(editObject.getRunType())) {
            List formGroupDefines = this.iRunTimeViewController.listFormGroupByFormSchemeStream(paramObj.getSchemeKey()).auth().getList();
            for (FormGroupDefine group : formGroupDefines) {
                com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> formGroupNode = EditorUtils.createFormGroupITree(group);
                List forms = this.iRunTimeViewController.listFormByGroupStream(group.getKey(), group.getFormSchemeKey()).auth().getList();
                if (null == forms || forms.isEmpty()) continue;
                ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>> children = new ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>>();
                for (FormDefine form : forms) {
                    com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> iNode;
                    if (form.getFormType().getValue() == FormType.FORM_TYPE_ANALYSISREPORT.getValue()) continue;
                    switch (form.getFormType()) {
                        case FORM_TYPE_SURVEY: {
                            iNode = EditorUtils.createFormITree(form, group.getKey(), "icon-16_SHU_A_NR_wenjuan");
                            break;
                        }
                        case FORM_TYPE_FLOAT: {
                            iNode = EditorUtils.createFormITree(form, group.getKey(), "icon-16_SHU_A_NR_fudongbiao1");
                            break;
                        }
                        case FORM_TYPE_ACCOUNT: {
                            iNode = EditorUtils.createFormITree(form, group.getKey(), "icon-16_SHU_A_NR_taizhangbiao");
                            break;
                        }
                        case FORM_TYPE_NEWFMDM: {
                            iNode = EditorUtils.createFormITree(form, group.getKey(), "icon-16_SHU_A_NR_fengmiandaimabiao");
                            break;
                        }
                        default: {
                            iNode = EditorUtils.createFormITree(form, group.getKey(), "icon-_GJZshuxingzhongji");
                        }
                    }
                    if (!StringUtils.isEmpty((String)paramObj.getFormKey()) && paramObj.getFormKey().equals(form.getKey())) {
                        iNode.setSelected(true);
                    }
                    children.add(iNode);
                }
                formGroupNode.setChildren(children);
                treeObjs.add(formGroupNode);
            }
        } else {
            List formGroupDefines = this.iDesignTimeViewController.listFormGroupByFormScheme(paramObj.getSchemeKey());
            for (DesignFormGroupDefine group : formGroupDefines) {
                com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> formGroupNode = EditorUtils.createFormGroupITree((FormGroupDefine)group);
                List forms = this.iDesignTimeViewController.listFormByGroup(group.getKey());
                if (null == forms || forms.isEmpty()) continue;
                ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>> children = new ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>>();
                for (DesignFormDefine form : forms) {
                    com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> iNode;
                    if (form.getFormType().getValue() == FormType.FORM_TYPE_ANALYSISREPORT.getValue()) continue;
                    switch (form.getFormType()) {
                        case FORM_TYPE_SURVEY: {
                            iNode = EditorUtils.createFormITree((FormDefine)form, group.getKey(), "icon-16_SHU_A_NR_wenjuan");
                            break;
                        }
                        case FORM_TYPE_FLOAT: {
                            iNode = EditorUtils.createFormITree((FormDefine)form, group.getKey(), "icon-16_SHU_A_NR_fudongbiao1");
                            break;
                        }
                        case FORM_TYPE_ACCOUNT: {
                            iNode = EditorUtils.createFormITree((FormDefine)form, group.getKey(), "icon-16_SHU_A_NR_taizhangbiao");
                            break;
                        }
                        case FORM_TYPE_NEWFMDM: {
                            iNode = EditorUtils.createFormITree((FormDefine)form, group.getKey(), "icon-16_SHU_A_NR_fengmiandaimabiao");
                            break;
                        }
                        default: {
                            iNode = EditorUtils.createFormITree((FormDefine)form, group.getKey(), "icon-_GJZshuxingzhongji");
                        }
                    }
                    if (!StringUtils.isEmpty((String)paramObj.getFormKey()) && paramObj.getFormKey().equals(form.getKey())) {
                        iNode.setSelected(true);
                    }
                    children.add(iNode);
                }
                formGroupNode.setChildren(children);
                treeObjs.add(formGroupNode);
            }
        }
        if (StringUtils.isEmpty((String)paramObj.getFormKey()) && !CollectionUtils.isEmpty(treeObjs) && !CollectionUtils.isEmpty(((com.jiuqi.nr.formulaeditor.vo.ITree)treeObjs.get(0)).getChildren())) {
            ((com.jiuqi.nr.formulaeditor.vo.ITree)treeObjs.get(0)).getChildren().get(0).setSelected(true);
        }
        return treeObjs;
    }

    @Override
    public List<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> searchFormData(EditObject editObject, ParamObj paramObj) {
        ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> treeObjs = new ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>();
        String keyWords = paramObj.getKeyWords().toUpperCase(Locale.ROOT);
        if ("runtime".equals(editObject.getRunType())) {
            List formGroupDefines = this.iRunTimeViewController.listFormGroupByFormSchemeStream(paramObj.getSchemeKey()).auth().getList();
            for (FormGroupDefine group : formGroupDefines) {
                boolean existForm = false;
                com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> formGroupNode = EditorUtils.createFormGroupITree(group);
                List forms = this.iRunTimeViewController.listFormByGroupStream(group.getKey(), group.getFormSchemeKey()).auth().getList();
                if (null == forms || forms.isEmpty()) continue;
                ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>> children = new ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>>();
                for (FormDefine form : forms) {
                    com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> iNode;
                    if (form.getFormType().getValue() == FormType.FORM_TYPE_ANALYSISREPORT.getValue()) continue;
                    switch (form.getFormType()) {
                        case FORM_TYPE_SURVEY: {
                            iNode = EditorUtils.createFormITree(form, group.getKey(), "icon-16_SHU_A_NR_wenjuan");
                            break;
                        }
                        case FORM_TYPE_FLOAT: {
                            iNode = EditorUtils.createFormITree(form, group.getKey(), "icon-16_SHU_A_NR_fudongbiao1");
                            break;
                        }
                        case FORM_TYPE_ACCOUNT: {
                            iNode = EditorUtils.createFormITree(form, group.getKey(), "icon-16_SHU_A_NR_taizhangbiao");
                            break;
                        }
                        case FORM_TYPE_NEWFMDM: {
                            iNode = EditorUtils.createFormITree(form, group.getKey(), "icon-16_SHU_A_NR_fengmiandaimabiao");
                            break;
                        }
                        default: {
                            iNode = EditorUtils.createFormITree(form, group.getKey(), "icon-_GJZshuxingzhongji");
                        }
                    }
                    if (!this.matchKeyWords(keyWords, form)) continue;
                    children.add(iNode);
                    existForm = true;
                }
                formGroupNode.setChildren(children);
                if (!existForm) continue;
                treeObjs.add(formGroupNode);
            }
        } else {
            List formGroupDefines = this.iDesignTimeViewController.listFormGroupByFormScheme(paramObj.getSchemeKey());
            for (DesignFormGroupDefine group : formGroupDefines) {
                boolean existForm = false;
                com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> formGroupNode = EditorUtils.createFormGroupITree((FormGroupDefine)group);
                List forms = this.iDesignTimeViewController.listFormByGroup(group.getKey());
                if (null == forms || forms.isEmpty()) continue;
                ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>> children = new ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>>();
                for (DesignFormDefine form : forms) {
                    com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> iNode;
                    if (form.getFormType().getValue() == FormType.FORM_TYPE_ANALYSISREPORT.getValue()) continue;
                    switch (form.getFormType()) {
                        case FORM_TYPE_SURVEY: {
                            iNode = EditorUtils.createFormITree((FormDefine)form, group.getKey(), "icon-16_SHU_A_NR_wenjuan");
                            break;
                        }
                        case FORM_TYPE_FLOAT: {
                            iNode = EditorUtils.createFormITree((FormDefine)form, group.getKey(), "icon-16_SHU_A_NR_fudongbiao1");
                            break;
                        }
                        case FORM_TYPE_ACCOUNT: {
                            iNode = EditorUtils.createFormITree((FormDefine)form, group.getKey(), "icon-16_SHU_A_NR_taizhangbiao");
                            break;
                        }
                        case FORM_TYPE_NEWFMDM: {
                            iNode = EditorUtils.createFormITree((FormDefine)form, group.getKey(), "icon-16_SHU_A_NR_fengmiandaimabiao");
                            break;
                        }
                        default: {
                            iNode = EditorUtils.createFormITree((FormDefine)form, group.getKey(), "icon-_GJZshuxingzhongji");
                        }
                    }
                    if (!this.matchKeyWords(keyWords, (FormDefine)form)) continue;
                    children.add(iNode);
                    existForm = true;
                }
                formGroupNode.setChildren(children);
                if (!existForm) continue;
                treeObjs.add(formGroupNode);
            }
        }
        return treeObjs;
    }

    private boolean matchKeyWords(String keyWords, FormDefine formDefine) {
        return formDefine.getTitle().toUpperCase(Locale.ROOT).contains(keyWords) || formDefine.getFormCode().toUpperCase(Locale.ROOT).contains(keyWords) || !StringUtils.isEmpty((String)formDefine.getSerialNumber()) && formDefine.getSerialNumber().toUpperCase(Locale.ROOT).contains(keyWords);
    }

    @Override
    public FormData queryStyleData(EditObject editObject, ParamObj paramObj) {
        FormData formData = new FormData();
        formData.setKey(paramObj.getFormKey());
        if ("runtime".equals(editObject.getRunType())) {
            BigDataDefine formStyleBigData = this.iRunTimeViewController.getFormStyle(paramObj.getFormKey(), paramObj.getSchemeKey());
            Grid2Data formStyle = Grid2Data.bytesToGrid((byte[])formStyleBigData.getData());
            EditorUtils.hiddenZeroRowCol(formStyle);
            String viewType = StringUtils.isNotEmpty((String)paramObj.getViewType()) ? paramObj.getViewType() : "1";
            CellBook cellBook = new CellBook();
            CellBookGrid2dataConverter.grid2DataToCellBook((Grid2Data)formStyle, (CellBook)cellBook, (String)paramObj.getFormKey());
            formData.setCellBook(cellBook);
            List dataRegionDefines = this.iRunTimeViewController.listDataRegionByForm(paramObj.getFormKey(), paramObj.getSchemeKey());
            List<RegionData> regionDatas = dataRegionDefines.stream().map(e -> EditorUtils.toRegionData(e)).collect(Collectors.toList());
            formData.setRegions(regionDatas);
            List dataLinkDefines = this.iRunTimeViewController.listDataLinkByForm(paramObj.getFormKey(), paramObj.getSchemeKey());
            List fieldKeys = this.iRunTimeViewController.listFieldKeyByForm(paramObj.getFormKey(), paramObj.getSchemeKey());
            List dataFields = this.iRuntimeDataSchemeService.getDataFields(fieldKeys);
            Map<String, String> fieldMap = dataFields.stream().collect(Collectors.toMap(Basic::getKey, Basic::getCode));
            List dataTables = this.iRuntimeDataSchemeService.getDataTables(dataFields.stream().map(e -> e.getDataTableKey()).distinct().collect(Collectors.toList()));
            Map<String, String> tableMaps = dataTables.stream().collect(Collectors.toMap(Basic::getKey, Basic::getCode));
            HashMap<String, String> fieldTableMap = new HashMap<String, String>();
            for (Object dataField : dataFields) {
                fieldTableMap.put(dataField.getKey(), tableMaps.get(dataField.getDataTableKey()));
            }
            ArrayList<LinkData> linkDatas = new ArrayList<LinkData>();
            for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
                switch (dataLinkDefine.getType()) {
                    case DATA_LINK_TYPE_FIELD: {
                        String field_tableCode = (String)fieldTableMap.get(dataLinkDefine.getLinkExpression());
                        String field_fieldCode = fieldMap.get(dataLinkDefine.getLinkExpression());
                        Cell fieldCell = (Cell)((List)((CellSheet)cellBook.getSheets().get(0)).getData().get(dataLinkDefine.getPosY())).get(dataLinkDefine.getPosX());
                        if ("1".equals(viewType)) {
                            fieldCell.setShowText(field_fieldCode);
                        } else {
                            fieldCell.setShowText(EditorUtils.getPosText(dataLinkDefine));
                        }
                        EditorUtils.setCellStyle(fieldCell);
                        LinkData field_linkData = new LinkData(dataLinkDefine);
                        field_linkData.setText(field_tableCode + "[" + field_fieldCode + "]");
                        linkDatas.add(field_linkData);
                        break;
                    }
                    case DATA_LINK_TYPE_INFO: {
                        String info_fieldCode = fieldMap.get(dataLinkDefine.getLinkExpression());
                        Cell info_cell = (Cell)((List)((CellSheet)cellBook.getSheets().get(0)).getData().get(dataLinkDefine.getPosY())).get(dataLinkDefine.getPosX());
                        if ("1".equals(viewType)) {
                            info_cell.setShowText(info_fieldCode);
                        } else {
                            info_cell.setShowText(EditorUtils.getPosText(dataLinkDefine));
                        }
                        EditorUtils.setCellStyle(info_cell);
                        LinkData info_linkData = new LinkData(dataLinkDefine);
                        info_linkData.setText(info_fieldCode);
                        linkDatas.add(info_linkData);
                        break;
                    }
                    case DATA_LINK_TYPE_FMDM: 
                    case DATA_LINK_TYPE_FORMULA: {
                        Cell formulaCell = (Cell)((List)((CellSheet)cellBook.getSheets().get(0)).getData().get(dataLinkDefine.getPosY())).get(dataLinkDefine.getPosX());
                        if ("1".equals(viewType)) {
                            formulaCell.setShowText(dataLinkDefine.getLinkExpression());
                        } else {
                            formulaCell.setShowText(EditorUtils.getPosText(dataLinkDefine));
                        }
                        EditorUtils.setCellStyle(formulaCell);
                        LinkData formula_linkData = new LinkData(dataLinkDefine);
                        formula_linkData.setText(dataLinkDefine.getLinkExpression());
                        linkDatas.add(formula_linkData);
                        break;
                    }
                }
            }
            formData.setLinks(linkDatas);
        } else {
            Grid2Data formStyle = this.iDesignTimeViewController.getFormStyle(paramObj.getFormKey());
            EditorUtils.hiddenZeroRowCol(formStyle);
            CellBook cellBook = new CellBook();
            String viewType = StringUtils.isNotEmpty((String)paramObj.getViewType()) ? paramObj.getViewType() : "1";
            CellBookGrid2dataConverter.grid2DataToCellBook((Grid2Data)formStyle, (CellBook)cellBook, (String)(paramObj.getFormKey() + "-" + viewType));
            formData.setCellBook(cellBook);
            List dataRegionDefines = this.iDesignTimeViewController.listDataRegionByForm(paramObj.getFormKey());
            List<RegionData> regionDatas = dataRegionDefines.stream().map(e -> EditorUtils.toRegionData((DataRegionDefine)e)).collect(Collectors.toList());
            formData.setRegions(regionDatas);
            List dataLinkDefines = this.iDesignTimeViewController.listDataLinkByForm(paramObj.getFormKey());
            List fieldKeys = this.iDesignTimeViewController.listFieldKeyByForm(paramObj.getFormKey());
            List dataFields = this.iDesignDataSchemeService.getDataFields(fieldKeys);
            Map<String, String> fieldMap = dataFields.stream().collect(Collectors.toMap(Basic::getKey, Basic::getCode));
            List dataTables = this.iDesignDataSchemeService.getDataTables(dataFields.stream().map(e -> e.getDataTableKey()).distinct().collect(Collectors.toList()));
            Map<String, String> tableMaps = dataTables.stream().collect(Collectors.toMap(Basic::getKey, Basic::getCode));
            HashMap<String, String> fieldTableMap = new HashMap<String, String>();
            for (DesignDataField dataField : dataFields) {
                fieldTableMap.put(dataField.getKey(), tableMaps.get(dataField.getDataTableKey()));
            }
            ArrayList<LinkData> linkDatas = new ArrayList<LinkData>();
            for (DesignDataLinkDefine dataLinkDefine : dataLinkDefines) {
                CellSheet cellSheet = (CellSheet)cellBook.getSheets().get(0);
                if (dataLinkDefine.getPosY() <= 0 || dataLinkDefine.getPosY() > cellSheet.getRowCount() - 1 || dataLinkDefine.getPosX() <= 0 || dataLinkDefine.getPosX() > cellSheet.getColumnCount() - 1) continue;
                switch (dataLinkDefine.getType()) {
                    case DATA_LINK_TYPE_FIELD: {
                        String field_tableCode = (String)fieldTableMap.get(dataLinkDefine.getLinkExpression());
                        String field_fieldCode = fieldMap.get(dataLinkDefine.getLinkExpression());
                        Cell fieldCell = (Cell)((List)((CellSheet)cellBook.getSheets().get(0)).getData().get(dataLinkDefine.getPosY())).get(dataLinkDefine.getPosX());
                        if ("1".equals(viewType)) {
                            fieldCell.setShowText(field_fieldCode);
                        } else {
                            fieldCell.setShowText(EditorUtils.getPosText((DataLinkDefine)dataLinkDefine));
                        }
                        EditorUtils.setCellStyle(fieldCell);
                        LinkData field_linkData = new LinkData((DataLinkDefine)dataLinkDefine);
                        field_linkData.setText(field_tableCode + "[" + field_fieldCode + "]");
                        linkDatas.add(field_linkData);
                        break;
                    }
                    case DATA_LINK_TYPE_INFO: {
                        String info_fieldCode = fieldMap.get(dataLinkDefine.getLinkExpression());
                        Cell info_cell = (Cell)((List)((CellSheet)cellBook.getSheets().get(0)).getData().get(dataLinkDefine.getPosY())).get(dataLinkDefine.getPosX());
                        if ("1".equals(viewType)) {
                            info_cell.setShowText(info_fieldCode);
                        } else {
                            info_cell.setShowText(EditorUtils.getPosText((DataLinkDefine)dataLinkDefine));
                        }
                        EditorUtils.setCellStyle(info_cell);
                        LinkData info_linkData = new LinkData((DataLinkDefine)dataLinkDefine);
                        info_linkData.setText(info_fieldCode);
                        linkDatas.add(info_linkData);
                        break;
                    }
                    case DATA_LINK_TYPE_FMDM: 
                    case DATA_LINK_TYPE_FORMULA: {
                        Cell formulaCell = (Cell)((List)((CellSheet)cellBook.getSheets().get(0)).getData().get(dataLinkDefine.getPosY())).get(dataLinkDefine.getPosX());
                        if ("1".equals(viewType)) {
                            formulaCell.setShowText(dataLinkDefine.getLinkExpression());
                        } else {
                            formulaCell.setShowText(EditorUtils.getPosText((DataLinkDefine)dataLinkDefine));
                        }
                        EditorUtils.setCellStyle(formulaCell);
                        LinkData formula_linkData = new LinkData((DataLinkDefine)dataLinkDefine);
                        formula_linkData.setText(dataLinkDefine.getLinkExpression());
                        linkDatas.add(formula_linkData);
                        break;
                    }
                }
            }
            formData.setLinks(linkDatas);
        }
        return formData;
    }

    @Override
    public List<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> initTreeData(EditObject editObject, ParamObj paramObj) {
        ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> otherTree = new ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>();
        ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> firstTree = new ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>();
        String currentDataScheme = "";
        TaskDefine task = null;
        if ("runtime".equals(editObject.getRunType())) {
            List allDataScheme = this.iRuntimeDataSchemeService.getAllDataScheme();
            task = this.iRunTimeViewController.getTask(paramObj.getTaskKey());
            if (null != task) {
                currentDataScheme = task.getDataScheme();
            } else if (null != allDataScheme && !allDataScheme.isEmpty()) {
                currentDataScheme = ((DataScheme)allDataScheme.get(0)).getKey();
            }
            for (DataScheme dataScheme : allDataScheme) {
                int interestType;
                if (!this.dataSchemeAuthService.canReadScheme(dataScheme.getKey())) continue;
                EditorSchemeNodeFilter filter = new EditorSchemeNodeFilter(currentDataScheme);
                boolean isCurr = false;
                if (currentDataScheme.equals(dataScheme.getKey())) {
                    interestType = EditorUtils.noField();
                    isCurr = true;
                } else {
                    interestType = EditorUtils.noDimAndField();
                }
                List rootTree = this.iRuntimeDataSchemeTreeService.getRootTree(dataScheme.getKey(), interestType, (NodeFilter)filter);
                List<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> data = EditorUtils.extractedRuntime(rootTree, isCurr);
                if (isCurr) {
                    firstTree.addAll(data);
                    continue;
                }
                otherTree.addAll(data);
            }
        } else {
            List allDataScheme = this.iDesignDataSchemeService.getAllDataScheme();
            task = this.iDesignTimeViewController.getTask(paramObj.getTaskKey());
            if (null != task) {
                currentDataScheme = task.getDataScheme();
            } else if (null != allDataScheme && !allDataScheme.isEmpty()) {
                currentDataScheme = ((DesignDataScheme)allDataScheme.get(0)).getKey();
            }
            for (DesignDataScheme designDataScheme : allDataScheme) {
                List<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> data;
                int interestType;
                if (!this.dataSchemeAuthService.canReadScheme(designDataScheme.getKey())) continue;
                EditorSchemeNodeFilter filter = new EditorSchemeNodeFilter(currentDataScheme);
                boolean isCurr = false;
                if (currentDataScheme.equals(designDataScheme.getKey())) {
                    interestType = EditorUtils.noField();
                    isCurr = true;
                } else {
                    interestType = EditorUtils.noDimAndField();
                }
                List rootTree = this.iDesignDataSchemeTreeService.getRootTree(designDataScheme.getKey(), interestType, (NodeFilter)filter);
                if (isCurr) {
                    data = EditorUtils.extractedDesign(rootTree, isCurr);
                    firstTree.addAll(data);
                    continue;
                }
                data = EditorUtils.extractedDesignNoChildren(rootTree, isCurr);
                otherTree.addAll(data);
            }
        }
        if (otherTree.size() != 0) {
            firstTree.addAll(otherTree);
        }
        return firstTree;
    }

    @Override
    public List<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> queryTreeGroupData(EditObject editObject, TreeObj treeObj) {
        ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> childTree = new ArrayList();
        if ("runtime".equals(editObject.getRunType())) {
            RuntimeDataSchemeNodeDTO parent = new RuntimeDataSchemeNodeDTO(treeObj.getData().getKey(), treeObj.getData().getCode(), treeObj.getData().getTitle(), Integer.parseInt(treeObj.getData().getType()), treeObj.getData().getParentKey());
            int interestType = EditorUtils.noField();
            List datas = this.iRuntimeDataSchemeTreeService.getChildTree((RuntimeDataSchemeNode)parent, interestType, null);
            childTree = EditorUtils.toEditorNodeTreeRun(datas);
        } else {
            DataSchemeNodeDTO parent = new DataSchemeNodeDTO(treeObj.getData().getKey(), treeObj.getData().getCode(), treeObj.getData().getTitle(), Integer.parseInt(treeObj.getData().getType()), treeObj.getData().getParentKey());
            int interestType = EditorUtils.noField();
            List data = this.iDesignDataSchemeTreeService.getChildTree((DataSchemeNode)parent, interestType, null);
            childTree = EditorUtils.toEditorNodeTree(data);
        }
        return childTree;
    }

    @Override
    public List<FieldData> queryFieldData(EditObject editObject, TreeObj treeObj) {
        List childTree;
        int interestType;
        RuntimeDataSchemeNodeDTO dataSchemeNode;
        ArrayList<FieldData> fieldDefineList = new ArrayList<FieldData>();
        if ("runtime".equals(editObject.getRunType())) {
            dataSchemeNode = new RuntimeDataSchemeNodeDTO(treeObj.getData().getKey(), treeObj.getData().getCode(), treeObj.getData().getTitle(), Integer.parseInt(treeObj.getData().getType()), treeObj.getData().getParentKey());
            interestType = EditorUtils.allField();
            childTree = this.iRuntimeDataSchemeTreeService.getChildTree((RuntimeDataSchemeNode)dataSchemeNode, interestType, null);
            HashMap<String, String> tableCodeMap = new HashMap<String, String>();
            for (ITree next : childTree) {
                if (!next.isLeaf()) continue;
                FieldData fieldData = new FieldData();
                fieldData.setKey(((RuntimeDataSchemeNode)next.getData()).getKey());
                fieldData.setCode(((RuntimeDataSchemeNode)next.getData()).getCode());
                fieldData.setTitle(((RuntimeDataSchemeNode)next.getData()).getTitle());
                if (null != tableCodeMap.get(((RuntimeDataSchemeNode)next.getData()).getParentKey())) {
                    fieldData.setTableCode((String)tableCodeMap.get(((RuntimeDataSchemeNode)next.getData()).getParentKey()));
                } else {
                    DataTable dataTable = this.iRuntimeDataSchemeService.getDataTable(((RuntimeDataSchemeNode)next.getData()).getParentKey());
                    if (null != dataTable) {
                        tableCodeMap.put(((RuntimeDataSchemeNode)next.getData()).getParentKey(), dataTable.getCode());
                        fieldData.setTableCode(dataTable.getCode());
                    }
                }
                if (((RuntimeDataSchemeNode)next.getData()).getType() == NodeType.ENTITY_ATTRIBUTE.getValue()) {
                    fieldData.setType("entity");
                } else {
                    fieldData.setType("field");
                }
                fieldDefineList.add(fieldData);
            }
        } else {
            dataSchemeNode = new DataSchemeNodeDTO(treeObj.getData().getKey(), treeObj.getData().getCode(), treeObj.getData().getTitle(), Integer.parseInt(treeObj.getData().getType()), treeObj.getData().getParentKey());
            interestType = EditorUtils.allField();
            childTree = this.iDesignDataSchemeTreeService.getChildTree((DataSchemeNode)dataSchemeNode, interestType, null);
            HashSet<String> infoKeys = new HashSet<String>();
            HashMap<String, String> tableCodeMap = new HashMap<String, String>();
            List allDataTable = this.iDesignDataSchemeService.getAllDataTable();
            for (DesignDataTable designDataTable : allDataTable) {
                if (DataTableType.MD_INFO.equals((Object)designDataTable.getDataTableType())) {
                    infoKeys.add(designDataTable.getKey());
                }
                tableCodeMap.put(designDataTable.getKey(), designDataTable.getCode());
            }
            for (ITree next : childTree) {
                if (!next.isLeaf()) continue;
                FieldData fieldData = new FieldData();
                fieldData.setKey(((DataSchemeNode)next.getData()).getKey());
                fieldData.setCode(((DataSchemeNode)next.getData()).getCode());
                fieldData.setTitle(((DataSchemeNode)next.getData()).getTitle());
                if (null != tableCodeMap.get(((DataSchemeNode)next.getData()).getParentKey())) {
                    fieldData.setTableCode((String)tableCodeMap.get(((DataSchemeNode)next.getData()).getParentKey()));
                }
                DesignDataFieldDO data = null;
                try {
                    if (((DataSchemeNode)next.getData()).getData() instanceof DesignDataFieldDO) {
                        data = (DesignDataFieldDO)((DataSchemeNode)next.getData()).getData();
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
                if (((DataSchemeNode)next.getData()).getType() == NodeType.ENTITY_ATTRIBUTE.getValue() || null != data && infoKeys.contains(data.getDataTableKey())) {
                    fieldData.setType("entity");
                } else {
                    fieldData.setType("field");
                }
                fieldDefineList.add(fieldData);
            }
        }
        for (int i = 0; i < fieldDefineList.size(); ++i) {
            ((FieldData)fieldDefineList.get(i)).setOrder(i + 1 + "");
        }
        return fieldDefineList;
    }

    @Override
    public List<FormulaCheckData> formulaCheck(EditObject editObject, FormulaObj formulaObj) {
        FormulaCheckData formulaCheckObj = new FormulaCheckData();
        formulaCheckObj.setFormula(formulaObj.getFormula());
        formulaCheckObj.setUseCalculate(false);
        formulaCheckObj.setUseCheck(false);
        formulaCheckObj.setReportName(formulaObj.getReportName());
        ArrayList<Formula> formuObjList = new ArrayList<Formula>();
        formuObjList.add(formulaCheckObj);
        DataEngineConsts.FormulaType type = DataEngineConsts.FormulaType.EXPRESSION;
        FormulaMonitor formulaMonitor = new FormulaMonitor();
        this.parseFormulas(formulaObj.getFormScheme(), formuObjList, type, formulaMonitor, editObject);
        Map<String, FormulaCheckData> checkResultMap = formulaMonitor.getCheckResultMap();
        Collection<FormulaCheckData> values = checkResultMap.values();
        ArrayList<FormulaCheckData> checkResultList = new ArrayList<FormulaCheckData>(values);
        return checkResultList;
    }

    @Override
    public List<FunctionList> queryFunctionData(EditObject editObject) {
        List list = this.funcDocService.list();
        Map<String, IFuncDoc> listMap = list.stream().collect(Collectors.toMap(IFuncDoc::name, e -> e));
        HashMap<String, FunctionData> functionMap = this.getFunctionObjs();
        Iterator<String> iterator = functionMap.keySet().iterator();
        List<String> category = list.stream().map(IFuncDoc::category).distinct().collect(Collectors.toList());
        List signFunc = list.stream().filter(e -> !e.deprecated() && SIGN_CATEGORY.equals(e.category())).collect(Collectors.toList());
        while (iterator.hasNext()) {
            String name = iterator.next();
            FunctionData functionData = functionMap.get(name);
            IFuncDoc doc = listMap.get(name);
            if (doc != null) {
                if (doc.deprecated()) {
                    iterator.remove();
                    continue;
                }
                functionData = FunctionData.getInstance(doc);
            }
            if (!category.contains(functionData.getGroup())) {
                category.add(functionData.getGroup());
            }
            functionMap.put(name, functionData);
        }
        ArrayList<FunctionData> allFunc = new ArrayList<FunctionData>(functionMap.values());
        List<FunctionData> signFuncList = signFunc.stream().map(FunctionData::getInstance).collect(Collectors.toList());
        allFunc.addAll(signFuncList);
        ArrayList<FunctionList> result = new ArrayList<FunctionList>();
        result.add(new FunctionList("\u5168\u90e8", allFunc));
        Map<String, List<FunctionData>> categoryMap = functionMap.values().stream().collect(Collectors.groupingBy(FunctionData::getGroup));
        category.forEach(key -> {
            List dataList = (List)categoryMap.get(key);
            if (!CollectionUtils.isEmpty(dataList)) {
                result.add(new FunctionList((String)key, dataList));
            }
        });
        result.add(new FunctionList(SIGN_CATEGORY, signFuncList));
        return result;
    }

    @Override
    public List<EntityData> queryEnumDefine(EditObject editObject, ParamObj paramObj) {
        List fieldKeys;
        DesignFormDefine formDefine = null;
        ArrayList<IEntityDefine> entityDefineList = new ArrayList<IEntityDefine>();
        if ("runtime".equals(editObject.getRunType())) {
            formDefine = this.iRunTimeViewController.getForm(paramObj.getFormKey(), paramObj.getSchemeKey());
            if (null == formDefine) {
                formDefine = this.iRunTimeViewController.getForm(editObject.getFormId(), editObject.getFormId());
            }
            if (null == formDefine) {
                return new ArrayList<EntityData>();
            }
            fieldKeys = this.iRunTimeViewController.listFieldKeyByForm(formDefine.getKey(), formDefine.getFormScheme());
            List feilds = this.iRuntimeDataSchemeService.getDataFields(fieldKeys);
            for (DataField field : feilds) {
                if (!StringUtils.isNotEmpty((String)field.getRefDataEntityKey())) continue;
                try {
                    IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(field.getEntityKey());
                    if (null == queryEntity) continue;
                    entityDefineList.add(queryEntity);
                }
                catch (Exception queryEntity) {}
            }
            if (FormType.FORM_TYPE_NEWFMDM.equals((Object)formDefine.getFormType())) {
                FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
                FormSchemeDefine formscheme = this.iRunTimeViewController.getFormScheme(formDefine.getFormScheme());
                fmdmAttributeDTO.setFormSchemeKey(formscheme.getKey());
                List attributes1 = this.fmdmAttributeService.list(fmdmAttributeDTO);
                List dataLinkDefines = this.iRunTimeViewController.listDataLinkByForm(formDefine.getKey(), formscheme.getKey());
                Map<String, DataLinkDefine> collect = dataLinkDefines.stream().filter(e -> e.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FMDM)).collect(Collectors.toMap(e -> e.getLinkExpression(), e -> e));
                for (IFMDMAttribute fmdm : attributes1) {
                    TableModelDefine tableModel;
                    IEntityDefine queryEntity;
                    if (null == collect.get(fmdm.getCode()) || !StringUtils.isNotEmpty((String)fmdm.getReferTableID()) || null == (queryEntity = this.iEntityMetaService.queryEntityByCode((tableModel = this.dataModelService.getTableModelDefineById(fmdm.getReferTableID())).getCode()))) continue;
                    entityDefineList.add(queryEntity);
                }
            }
        } else {
            formDefine = this.iDesignTimeViewController.getForm(paramObj.getFormKey());
            if (null == formDefine) {
                formDefine = this.iDesignTimeViewController.getForm(editObject.getFormId());
            }
            if (null == formDefine) {
                return new ArrayList<EntityData>();
            }
            fieldKeys = this.iDesignTimeViewController.listFieldKeyByForm(formDefine.getKey());
            List feilds = this.iDesignDataSchemeService.getDataFields(fieldKeys);
            for (DesignDataField field : feilds) {
                if (!StringUtils.isNotEmpty((String)field.getRefDataEntityKey())) continue;
                try {
                    IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(field.getEntityKey());
                    if (null == queryEntity) continue;
                    entityDefineList.add(queryEntity);
                }
                catch (Exception queryEntity) {}
            }
            if (FormType.FORM_TYPE_NEWFMDM.equals((Object)formDefine.getFormType())) {
                FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
                DesignFormSchemeDefine formscheme = this.iDesignTimeViewController.getFormScheme(formDefine.getFormScheme());
                fmdmAttributeDTO.setFormSchemeKey(formscheme.getKey());
                List attributes1 = this.fmdmAttributeService.list(fmdmAttributeDTO);
                List dataLinkDefines = this.iDesignTimeViewController.listDataLinkByForm(formDefine.getKey());
                Map<String, DesignDataLinkDefine> collect = dataLinkDefines.stream().filter(e -> e.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FMDM)).collect(Collectors.toMap(e -> e.getLinkExpression(), e -> e));
                for (IFMDMAttribute fmdm : attributes1) {
                    TableModelDefine tableModel;
                    IEntityDefine queryEntity;
                    if (null == collect.get(fmdm.getCode()) || !StringUtils.isNotEmpty((String)fmdm.getReferTableID()) || null == (queryEntity = this.iEntityMetaService.queryEntityByCode((tableModel = this.dataModelService.getTableModelDefineById(fmdm.getReferTableID())).getCode()))) continue;
                    entityDefineList.add(queryEntity);
                }
            }
        }
        List enumDatas = entityDefineList.stream().map(EntityData::new).collect(Collectors.toList());
        return enumDatas.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<EntityData>(Comparator.comparing(EntityData::getKey))), ArrayList::new));
    }

    @Override
    public List<FormulaVariableData> queryFormulaValiData(EditObject editObject, ParamObj paramObj) {
        ArrayList<FormulaVariableData> list = new ArrayList<FormulaVariableData>();
        VariableManagerBase variableManager = (VariableManagerBase)ExecutorContext.contextVariableManagerProvider.getNormalContextVariableManager();
        List allVars = variableManager.getAllVars();
        if (!CollectionUtils.isEmpty(allVars)) {
            allVars.stream().forEach(var -> {
                FormulaVariableData variable = new FormulaVariableData();
                variable.setCode(var.getVarName());
                variable.setTitle(var.getVarTitle());
                variable.setValueType(var.getDataType());
                list.add(variable);
            });
        }
        if ("runtime".equals(editObject.getRunType())) {
            List queryAllFormulaVariable = this.iRunTimeFormulaController.listFormulaVariByFormScheme(paramObj.getSchemeKey());
            queryAllFormulaVariable.stream().forEach(define -> list.add(EditorUtils.toFormulaVariableData(define)));
        } else {
            List queryAllFormulaVariable = this.iDesignTimeFormulaController.listFormulaVariByFormScheme(paramObj.getSchemeKey());
            queryAllFormulaVariable.stream().forEach(define -> list.add(EditorUtils.toFormulaVariableData(define)));
        }
        return list;
    }

    private List<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> createDesTreeNode(List<DesignTaskGroupDefine> designTaskGroupDefines) {
        ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> treeObj = new ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>();
        for (DesignTaskGroupDefine designTaskGroupDefine : designTaskGroupDefines) {
            com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> taskGroupNode = EditorUtils.createTaskGroupITree((TaskGroupDefine)designTaskGroupDefine);
            treeObj.add(taskGroupNode);
            List childTaskGroups = this.iDesignTimeViewController.listTaskGroupByParentGroup(designTaskGroupDefine.getKey());
            if (childTaskGroups.size() > 0) {
                List<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> recursion = this.createDesTreeNode(childTaskGroups);
                taskGroupNode.setChildren(new ArrayList());
                taskGroupNode.getChildren().addAll(recursion);
            }
            List allTasksInGroup = this.iDesignTimeViewController.listTaskByTaskGroup(designTaskGroupDefine.getKey());
            allTasksInGroup = allTasksInGroup.stream().filter(t -> t.getTaskType() != TaskType.TASK_TYPE_ANALYSIS).sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).collect(Collectors.toList());
            for (DesignTaskDefine designTaskDefine : allTasksInGroup) {
                if (!this.checkTaskHasScheme(designTaskDefine.getKey(), "designer")) continue;
                com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> taskNode = EditorUtils.createTaskITree((TaskDefine)designTaskDefine, designTaskGroupDefine.getKey());
                if (taskGroupNode.getChildren() == null) {
                    taskGroupNode.setChildren(new ArrayList(allTasksInGroup.size()));
                }
                taskGroupNode.getChildren().add(taskNode);
            }
        }
        return treeObj;
    }

    private List<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> createRunTreeNode(List<TaskGroupDefine> taskGroupDefines) {
        ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> treeObj = new ArrayList<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>>();
        for (TaskGroupDefine taskGroupDefine : taskGroupDefines) {
            com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> taskGroupNode = EditorUtils.createTaskGroupITree(taskGroupDefine);
            treeObj.add(taskGroupNode);
            List childTaskGroups = this.iRunTimeViewController.listTaskGroupByParentGroupStream(taskGroupDefine.getKey()).auth().getList();
            if (childTaskGroups.size() > 0) {
                List<com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData>> recursion = this.createRunTreeNode(childTaskGroups);
                if (null == taskGroupNode.getChildren()) {
                    taskGroupNode.setChildren(new ArrayList(taskGroupDefines.size()));
                }
                taskGroupNode.getChildren().addAll(recursion);
            }
            List allTasksInGroup = this.iRunTimeViewController.listTaskByTaskGroupStream(taskGroupDefine.getKey()).auth().getList();
            for (TaskDefine taskData : allTasksInGroup) {
                if (!this.checkTaskHasScheme(taskData.getKey(), "runtime")) continue;
                com.jiuqi.nr.formulaeditor.vo.ITree<EditorNodeData> taskNode = EditorUtils.createTaskITree(taskData, taskGroupDefine.getKey());
                if (null == taskGroupNode.getChildren()) {
                    taskGroupNode.setChildren(new ArrayList(allTasksInGroup.size()));
                }
                taskGroupNode.getChildren().add(taskNode);
            }
        }
        return treeObj;
    }

    private HashMap<String, FunctionData> getFunctionObjs() {
        ExecutorContext executorContext = new ExecutorContext(this.iDataDefinitionRuntimeController);
        Set functions = null;
        try {
            DefinitionsCache cache = new DefinitionsCache(executorContext);
            ReportFormulaParser formulaParser = cache.getFormulaParser(executorContext);
            functions = formulaParser.allFunctions();
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u7cfb\u7edf\u516c\u5f0f\u51fd\u6570\u5217\u8868\u65f6\u5f02\u5e38", e);
        }
        HashMap<String, FunctionData> functionObjs = new HashMap<String, FunctionData>();
        if (functions == null) {
            return functionObjs;
        }
        for (IFunction iFunction : functions) {
            if (iFunction.isDeprecated()) continue;
            FunctionData functionObj = new FunctionData(iFunction.name().toUpperCase(Locale.ROOT), iFunction.title(), iFunction.category(), iFunction.toDescription(), iFunction.parameters().size());
            try {
                functionObj.setResultType(this.getType(iFunction.getResultType(null, null)));
            }
            catch (Exception e) {
                logger.error("\u8bbe\u7f6e\u51fd\u6570" + iFunction.name() + "\u7684\u8fd4\u56de\u503c\u7c7b\u578b\u65f6\u5f02\u5e38", e);
            }
            ArrayList<ParameterData> parameterObjs = new ArrayList<ParameterData>();
            String params = "";
            for (IParameter parameter : iFunction.parameters()) {
                params = params + parameter.name() + "\uff1a" + DataType.toString((int)parameter.dataType()) + " " + parameter.title() + "\uff1b";
                ParameterData parameterObj = new ParameterData(parameter.name(), parameter.title(), DataType.toString((int)parameter.dataType()), parameter.isOmitable());
                parameterObjs.add(parameterObj);
            }
            functionObj.setParameter(params);
            functionObj.setParameterList(parameterObjs);
            functionObj.setParameterCount(parameterObjs.size());
            String desc = "\u51fd\u6570\uff1a" + functionObj.getFunction() + ";\u8bf4\u660e\uff1a" + functionObj.getTitle() + ";\u53c2\u6570\uff1a" + functionObj.getParameter();
            functionObj.setDescription(desc);
            functionObjs.put(iFunction.name().toUpperCase(Locale.ROOT), functionObj);
        }
        return functionObjs;
    }

    private String getType(int type) {
        switch (type) {
            case 0: {
                return "\u4efb\u610f\u7c7b\u578b";
            }
            case 1: {
                return "\u5e03\u5c14\u578b";
            }
            case 2: {
                return "\u65f6\u95f4\u578b";
            }
            case 3: {
                return "\u6570\u503c\u578b";
            }
            case 6: {
                return "\u5b57\u7b26\u578b";
            }
            case 10: {
                return "\u6570\u503c\u578b";
            }
            case 11: {
                return "\u6570\u7ec4\u578b";
            }
        }
        return "\u4efb\u610f\u7c7b\u578b";
    }

    private List<IParsedExpression> parseFormulas(String formSchemeKey, List<Formula> formulaList, DataEngineConsts.FormulaType formulaType, IMonitor formulaMonitor, EditObject editObject) {
        ArrayList<IParsedExpression> parsedExpressions = new ArrayList();
        try {
            ExecutorContext context = new ExecutorContext(this.iDataDefinitionRuntimeController);
            context.setJQReportModel(true);
            List formulaVariables = new ArrayList();
            context.setDesignTimeData(true, this.iDataDefinitionDesignTimeController);
            formulaVariables = this.iDesignTimeFormulaController.listFormulaVariByFormScheme(formSchemeKey);
            DesignReportFmlExecEnvironment environment = new DesignReportFmlExecEnvironment(this.oldIDesignTimeViewController, this.iDataDefinitionDesignTimeController, formSchemeKey, formulaVariables);
            context.setEnv((IFmlExecEnvironment)environment);
            parsedExpressions = DataEngineFormulaParser.parseFormula((ExecutorContext)context, formulaList, (DataEngineConsts.FormulaType)formulaType, (IMonitor)formulaMonitor);
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_017.getMessage(), e);
        }
        return parsedExpressions;
    }

    public boolean checkTaskHasScheme(String taskKey, String runType) {
        boolean check = true;
        if ("runtime".equals(runType)) {
            List formSchemeDefines = this.iRunTimeViewController.listFormSchemeByTask(taskKey);
            if (null == formSchemeDefines || formSchemeDefines.isEmpty()) {
                check = false;
            }
        } else {
            List designFormSchemeDefines = this.iDesignTimeViewController.listFormSchemeByTask(taskKey);
            if (null == designFormSchemeDefines || designFormSchemeDefines.isEmpty()) {
                check = false;
            }
        }
        return check;
    }

    public boolean checkSchemeHasForm(String formScheme, String runType) {
        boolean check = true;
        if ("runtime".equals(runType)) {
            List formDefines = this.iRunTimeViewController.listFormByFormScheme(formScheme);
            if (null == formDefines || formDefines.isEmpty()) {
                check = false;
            }
        } else {
            List designFormDefines = this.iDesignTimeViewController.listFormByFormScheme(formScheme);
            if (null == designFormDefines || designFormDefines.isEmpty()) {
                check = false;
            }
        }
        return check;
    }
}

