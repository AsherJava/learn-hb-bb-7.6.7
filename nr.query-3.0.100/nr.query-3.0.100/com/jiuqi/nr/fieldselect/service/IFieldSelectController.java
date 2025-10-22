/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  javax.annotation.Resource
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.fieldselect.service;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fieldselect.define.FieldSelectFormData;
import com.jiuqi.nr.fieldselect.define.FieldSelectFormFilter;
import com.jiuqi.nr.fieldselect.service.IFieldSelectorExtend;
import com.jiuqi.nr.fieldselect.service.impl.FieldSelectHelper;
import com.jiuqi.nr.query.querymodal.QueryModalTreeNode;
import com.jiuqi.nr.query.service.impl.QueryDimensionHelper;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/query-Manager"})
public class IFieldSelectController {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    QueryDimensionHelper dimensionHelper;
    @Autowired
    IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Resource
    RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    IDesignDataSchemeService iDesignDataSchemeService;
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    IEntityMetaService ientitymetaservice;
    @Autowired
    private IDataSchemeTreeService<DataSchemeNode> treeService;
    private static final Logger logger = LoggerFactory.getLogger(IFieldSelectController.class);
    @Autowired(required=false)
    List<IFieldSelectorExtend> fieldSelectExtend;

    @RequestMapping(value={"/fieldselect-griddata"}, method={RequestMethod.GET})
    public String getFormData(String formid, String formschemekey, Boolean useRelateType) {
        try {
            useRelateType = useRelateType == null ? false : useRelateType;
            FieldSelectHelper helper = new FieldSelectHelper();
            FieldSelectFormData formData = helper.getFormData(formschemekey, formid, useRelateType);
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            this.objectMapper.registerModule((Module)module);
            String fromDataStr = this.objectMapper.writeValueAsString((Object)formData);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u67e5\u8be2\u6307\u6807\u6570\u636e", (String)("\u67e5\u8be2\u6210\u529f\uff0c\u8868\u5355id:" + formid + "\uff0c\u65b9\u6848id\uff1a" + formschemekey));
            return fromDataStr;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u67e5\u8be2\u6307\u6807\u6570\u636e\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + ex));
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u67e5\u8be2\u6307\u6807\u6570\u636e", (String)("\u67e5\u8be2\u5931\u8d25\uff0c\u8868\u5355id:" + formid + "\uff0c\u65b9\u6848id\uff1a" + formschemekey));
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-getChildNodes"}, method={RequestMethod.GET})
    public String queryChildNodes(String nodeKey, String nodeType) {
        try {
            int type = Integer.parseInt(nodeType);
            switch (type) {
                case 0: {
                    TaskDefine task = this.runTimeAuthViewController.queryTaskDefine(nodeKey);
                    ITree<QueryModalTreeNode> schemes = this.getAllSchemeInTask(task);
                    LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u6839\u636e\u65b9\u6848\u52a0\u8f7d\u6570\u636e\u6e90", (String)("\u52a0\u8f7d\u6210\u529f\uff0c\u5f53\u524d\u8282\u70b9id:" + nodeKey + ",\u5f53\u524d\u8282\u70b9\u7c7b\u578b\uff1a" + nodeType));
                    return this.objectMapper.writeValueAsString(schemes);
                }
                case 1: {
                    FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(nodeKey);
                    ITree<QueryModalTreeNode> groups = this.getAllGroupsInScheme(formScheme, TreeLoadLevel.DIRECTSECOND);
                    LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u6839\u636e\u8868\u5355\u5206\u7ec4\u52a0\u8f7d\u6570\u636e\u6e90", (String)("\u52a0\u8f7d\u6210\u529f\uff0c\u5f53\u524d\u8282\u70b9id:" + nodeKey + ",\u5f53\u524d\u8282\u70b9\u7c7b\u578b\uff1a" + nodeType));
                    return this.objectMapper.writeValueAsString(groups);
                }
                case 2: {
                    FormGroupDefine group = this.runTimeAuthViewController.queryFormGroup(nodeKey);
                    ITree<QueryModalTreeNode> forms = this.getAllFormsInGroup(group, TreeLoadLevel.DIRECTSECOND);
                    LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u6839\u636e\u8868\u5355\u52a0\u8f7d\u6570\u636e\u6e90", (String)("\u52a0\u8f7d\u6210\u529f\uff0c\u5f53\u524d\u8282\u70b9id:" + nodeKey + ",\u5f53\u524d\u8282\u70b9\u7c7b\u578b\uff1a" + nodeType));
                    return this.objectMapper.writeValueAsString(forms);
                }
                case 3: {
                    FormDefine form = this.runTimeAuthViewController.queryFormById(nodeKey);
                    ITree<QueryModalTreeNode> regions = this.getAllRegionsInForm(form);
                    LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u6839\u636e\u533a\u57df\u52a0\u8f7d\u6570\u636e\u6e90", (String)("\u52a0\u8f7d\u6210\u529f\uff0c\u5f53\u524d\u8282\u70b9id:" + nodeKey + ",\u5f53\u524d\u8282\u70b9\u7c7b\u578b\uff1a" + nodeType));
                    return this.objectMapper.writeValueAsString(regions);
                }
            }
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u52a0\u8f7d\u6570\u636e\u6e90", (String)("\u52a0\u8f7d\u5931\u8d25\uff0c\u5f53\u524d\u8282\u70b9\u7c7b\u578b\uff1a" + nodeType + "\u4e0d\u5b58\u5728\u3002"));
            return null;
        }
        catch (Exception exception) {
            return null;
        }
    }

    private ITree<QueryModalTreeNode> getAllRegionsInForm(FormDefine form) {
        try {
            QueryModalTreeNode node = QueryModalTreeNode.buildTreeNodeData(form.getKey(), form.getTitle(), form.getUpdateTime(), 3);
            ITree treeLeve3 = new ITree((INode)node);
            List listRegionDefine = null;
            listRegionDefine = this.runTimeAuthViewController.getAllRegionsInForm(form.getKey());
            ITree treeLevel4 = new ITree();
            for (DataRegionDefine regionDefine : listRegionDefine) {
                node = QueryModalTreeNode.buildTreeNodeData(regionDefine.getKey(), regionDefine.getTitle(), regionDefine.getUpdateTime(), 4);
                node.setParentId(form.getKey());
                treeLevel4.appendChild((INode)node);
            }
            treeLeve3.appendChild(treeLevel4);
            return treeLeve3;
        }
        catch (Exception exception) {
            return null;
        }
    }

    private ITree<QueryModalTreeNode> getAllFormsInGroup(FormGroupDefine group, TreeLoadLevel loadLevel) {
        try {
            ITree treeLevel3 = new ITree();
            QueryModalTreeNode node = QueryModalTreeNode.buildTreeNodeData(group.getKey(), group.getTitle(), group.getUpdateTime(), 2);
            treeLevel3.appendChild((INode)node);
            if (loadLevel.equals((Object)TreeLoadLevel.DIRECTSECOND)) {
                List listFormDefine = null;
                listFormDefine = this.runTimeAuthViewController.getAllFormsInGroup(group.getKey());
                if (listFormDefine != null) {
                    ITree treeLevel4 = new ITree();
                    for (FormDefine formDefine : listFormDefine) {
                        if (formDefine.getFormType() == FormType.FORM_TYPE_ANALYSISREPORT) continue;
                        node = QueryModalTreeNode.buildTreeNodeData(formDefine.getKey(), formDefine.getTitle(), formDefine.getUpdateTime(), 3);
                        node.setParentId(group.getKey());
                        treeLevel4.appendChild((INode)node);
                    }
                    treeLevel3.appendChild(treeLevel4);
                }
            }
            return treeLevel3;
        }
        catch (Exception exception) {
            return null;
        }
    }

    private ITree<QueryModalTreeNode> getAllGroupsInScheme(FormSchemeDefine scheme, TreeLoadLevel loadLevel) {
        try {
            ITree treeLevel2 = new ITree();
            QueryModalTreeNode node = QueryModalTreeNode.buildTreeNodeData(scheme.getKey(), scheme.getTitle(), scheme.getUpdateTime(), 1);
            treeLevel2.appendChild((INode)node);
            if (loadLevel.equals((Object)TreeLoadLevel.DIRECTSECOND)) {
                List groups = this.runTimeAuthViewController.getAllFormGroupsInFormScheme(scheme.getKey());
                for (FormGroupDefine group : groups) {
                    ITree<QueryModalTreeNode> treeLevel3 = this.getAllFormsInGroup(group, TreeLoadLevel.DIRECT);
                    if (!treeLevel3.hasChildren()) continue;
                    treeLevel2.appendChild(treeLevel3);
                }
            }
            return treeLevel2;
        }
        catch (Exception exception) {
            return null;
        }
    }

    private ITree<QueryModalTreeNode> getAllSchemeInTask(TaskDefine task) {
        try {
            QueryModalTreeNode node = QueryModalTreeNode.buildTreeNodeData(task.getKey(), task.getTitle(), task.getUpdateTime(), 0);
            ITree treeLevel1 = new ITree((INode)node);
            List formSchemes = this.runTimeAuthViewController.queryFormSchemeByTask(task.getKey());
            if (formSchemes == null || formSchemes.size() == 0) {
                return null;
            }
            ITree treeLevel2 = new ITree();
            for (FormSchemeDefine scheme : formSchemes) {
                node = QueryModalTreeNode.buildTreeNodeData(scheme.getKey(), scheme.getTitle(), scheme.getUpdateTime(), 1);
                node.setParentId(task.getKey());
                treeLevel2.appendChild((INode)node);
            }
            treeLevel1.appendChild(treeLevel2);
            return treeLevel1;
        }
        catch (Exception exception) {
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-getTreeNodesByTask"}, method={RequestMethod.GET})
    public String queryTreeData(String taskid, String curSchemeKey) {
        try {
            ArrayList<ITree> treeNodes = new ArrayList<ITree>();
            TaskDefine task = this.runTimeAuthViewController.queryTaskDefine(taskid);
            if (task == null) {
                LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u83b7\u53d6\u4efb\u52a1\u4e0b\u6240\u6709\u62a5\u8868\u65b9\u6848", (String)("\u4efb\u52a1\u4e0d\u5b58\u5728\uff0ctaskid:" + taskid));
                return null;
            }
            List formSchemes = this.runTimeAuthViewController.queryFormSchemeByTask(taskid);
            if (formSchemes == null || formSchemes.size() == 0) {
                LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u83b7\u53d6\u4efb\u52a1\u4e0b\u6240\u6709\u62a5\u8868\u65b9\u6848", (String)("\u62a5\u8868\u65b9\u4e0d\u5b58\u5728\uff0ctaskid:" + taskid + ",formSchemeKey:" + curSchemeKey));
                return null;
            }
            for (FormSchemeDefine scheme : formSchemes) {
                if (!scheme.getKey().equals(curSchemeKey)) continue;
                List groups = this.runTimeAuthViewController.getAllFormGroupsInFormScheme(scheme.getKey());
                for (FormGroupDefine group : groups) {
                    QueryModalTreeNode node = QueryModalTreeNode.buildTreeNodeData(group.getKey(), group.getTitle(), group.getUpdateTime(), 0);
                    ITree treeLevel3 = new ITree((INode)node);
                    List listFormDefine = null;
                    listFormDefine = this.runTimeAuthViewController.getAllFormsInGroup(group.getKey());
                    if (listFormDefine != null) {
                        for (FormDefine formDefine : listFormDefine) {
                            node = QueryModalTreeNode.buildTreeNodeData(formDefine.getKey(), formDefine.getTitle(), formDefine.getUpdateTime(), 1);
                            node.setParentId(group.getKey());
                            ITree formNode = new ITree((INode)node);
                            formNode.setLeaf(true);
                            treeLevel3.appendChild(formNode);
                        }
                    }
                    treeNodes.add(treeLevel3);
                }
            }
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u83b7\u53d6\u4efb\u52a1\u4e0b\u6240\u6709\u62a5\u8868\u65b9\u6848", (String)("\u6210\u529f\uff0ctaskid:" + taskid + ",formSchemeKey:" + curSchemeKey));
            return this.objectMapper.writeValueAsString(treeNodes);
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u83b7\u53d6\u4efb\u52a1\u4e0b\u6240\u6709\u62a5\u8868\u65b9\u6848\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + e));
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-allform"}, method={RequestMethod.GET})
    public String queryAllForm(String formschemekey) {
        try {
            ArrayList<ITree> groups = new ArrayList<ITree>();
            if (formschemekey != null) {
                List listFormDefine = null;
                listFormDefine = this.runTimeAuthViewController.queryAllFormDefinesByFormScheme(formschemekey);
                if (listFormDefine != null) {
                    for (FormDefine formDefine : listFormDefine) {
                        if (formDefine.getFormType() == FormType.FORM_TYPE_ANALYSISREPORT) continue;
                        QueryModalTreeNode node = QueryModalTreeNode.buildTreeNodeData(formDefine.getKey(), formDefine.getTitle(), formDefine.getUpdateTime(), 3);
                        ITree tree = new ITree((INode)node);
                        tree.setLeaf(false);
                        groups.add(tree);
                    }
                }
            }
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u83b7\u53d6\u65b9\u6848\u4e0b\u7684\u6240\u6709\u8868\u5355", (String)("\u6210\u529f\uff0cformschemekey:" + formschemekey));
            return this.objectMapper.writeValueAsString(groups);
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u83b7\u53d6\u65b9\u6848\u4e0b\u7684\u6240\u6709\u8868\u5355\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0:" + e));
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-alldataregion"}, method={RequestMethod.GET})
    public String queryAllDataRegion(String formid) {
        try {
            ArrayList<ITree> groups = new ArrayList<ITree>();
            if (formid != null) {
                List listDataRegionDefine = null;
                listDataRegionDefine = this.runTimeAuthViewController.getAllRegionsInForm(formid);
                if (listDataRegionDefine != null) {
                    for (DataRegionDefine dataRegionDefine : listDataRegionDefine) {
                        QueryModalTreeNode node = QueryModalTreeNode.buildTreeNodeData(dataRegionDefine.getKey(), dataRegionDefine.getTitle(), dataRegionDefine.getUpdateTime(), 4);
                        ITree tree = new ITree((INode)node);
                        tree.setLeaf(true);
                        groups.add(tree);
                    }
                }
            }
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u83b7\u53d6\u6307\u5b9a\u8868\u5355\u4e0b\u6240\u6709\u7684\u6570\u636e\u57df", (String)("\u6210\u529f\uff0cformid:" + formid));
            return this.objectMapper.writeValueAsString(groups);
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u83b7\u53d6\u6307\u5b9a\u8868\u5355\u4e0b\u6240\u6709\u7684\u6570\u636e\u57df\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0:" + e));
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-allfield"}, method={RequestMethod.GET})
    public String queryAllField(String dataregionid) {
        try {
            ArrayList<ITree> groups = new ArrayList<ITree>();
            if (dataregionid != null) {
                List listFieldDefine = null;
                List fieldKeys = this.runTimeAuthViewController.getAllFieldsByLinksInRegion(dataregionid);
                listFieldDefine = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)fieldKeys);
                if (listFieldDefine != null) {
                    for (FieldDefine fieldDefine : listFieldDefine) {
                        QueryModalTreeNode node = QueryModalTreeNode.buildTreeNodeData(fieldDefine.getKey(), fieldDefine.getTitle(), null, 5);
                        ITree tree = new ITree((INode)node);
                        tree.setLeaf(true);
                        groups.add(tree);
                    }
                }
            }
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u83b7\u53d6\u6307\u5b9a\u6570\u636e\u57df\u4e0b\u6240\u6709\u7684\u6570\u636e\u94fe\u63a5\u5bf9\u5e94\u7684\u6307\u6807key", (String)("\u6210\u529f\uff0cdataRegionid:" + dataregionid));
            return this.objectMapper.writeValueAsString(groups);
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u83b7\u53d6\u6307\u5b9a\u6570\u636e\u57df\u4e0b\u6240\u6709\u7684\u6570\u636e\u94fe\u63a5\u5bf9\u5e94\u7684\u6307\u6807key", (String)("\u5f02\u5e38\u539f\u56e0:" + e));
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-gottasks"}, method={RequestMethod.GET})
    public List<TaskDefine> getAllTaskDefines(String[] taskTypes) {
        if (taskTypes == null || taskTypes.length == 0) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u83b7\u53d6\u4efb\u52a1", (String)"\u6210\u529f\uff0ctaskCode\u4e3a\u7a7a\uff0c\u67e5\u8be2\u6240\u6709\u4efb\u52a1");
            return this.runTimeAuthViewController.getAllReportTaskDefines();
        }
        ArrayList<TaskDefine> tasks = new ArrayList<TaskDefine>();
        for (String taskType : taskTypes) {
            List tasksTemp = this.runTimeAuthViewController.getAllTaskDefinesByType(TaskType.valueOf((String)taskType));
            if (tasksTemp == null || tasksTemp.size() <= 0) continue;
            tasks.addAll(tasksTemp);
        }
        return tasks;
    }

    @RequestMapping(value={"/fieldselect-gotFormSchemeByTask"}, method={RequestMethod.GET})
    public List<FormSchemeDefine> getFormSchemeByTask(String taskKey) {
        try {
            if (!StringUtils.isEmpty((String)taskKey) && !taskKey.equals("undefined")) {
                LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u6839\u636e\u4efb\u52a1ID\u83b7\u53d6\u4efb\u52a1\u4e0b\u6240\u6709\u65b9\u6848", (String)("\u6210\u529f\uff0ctaskKey\uff1a" + taskKey));
                return this.runTimeAuthViewController.queryFormSchemeByTask(taskKey);
            }
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u6839\u636e\u4efb\u52a1ID\u83b7\u53d6\u4efb\u52a1\u4e0b\u6240\u6709\u65b9\u6848", (String)"\u5931\u8d25\uff0ctaskKey\u4e3a\u7a7a");
            return null;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u6839\u636e\u4efb\u52a1ID\u83b7\u53d6\u4efb\u52a1\u4e0b\u6240\u6709\u65b9\u6848\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + taskKey));
            return null;
        }
    }

    @PostMapping(value={"/fieldselect-getgroupandforms"})
    public String getGroupAndFroms(@RequestBody FieldSelectFormFilter fieldSelectFormFilter) {
        if (fieldSelectFormFilter.getDisplayFMDM() == null) {
            fieldSelectFormFilter.setDisplayFMDM("true");
        }
        FieldSelectHelper fieldHelper = new FieldSelectHelper();
        LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u83b7\u53d6\u65b9\u6848\u4e0b\u6240\u6709\u5206\u7ec4\u548c\u8868\u5355", (String)("\u6210\u529f\uff0cschemeKey\uff1a" + fieldSelectFormFilter.getSchemeKey()));
        return fieldHelper.getGroupAndFroms(this.fieldSelectExtend, fieldSelectFormFilter);
    }

    @RequestMapping(value={"/fieldselect-getEnumbyFields"}, method={RequestMethod.GET})
    public String getContentByMasterKey(String entityViewKey) {
        try {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u901a\u8fc7\u5b9e\u4f53\u89c6\u56feid\u83b7\u53d6\u5b9e\u4f53\u6570\u636e", (String)("\u6210\u529f\uff0centityViewKey\uff1a" + entityViewKey));
            return this.dimensionHelper.getContentByMasterKey(entityViewKey);
        }
        catch (DataTypeException e) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u901a\u8fc7\u5b9e\u4f53\u89c6\u56feid\u83b7\u53d6\u5b9e\u4f53\u6570\u636e\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + (Object)((Object)e)));
            return null;
        }
        catch (RuntimeException e) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u901a\u8fc7\u5b9e\u4f53\u89c6\u56feid\u83b7\u53d6\u5b9e\u4f53\u6570\u636e\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + e));
            return null;
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u901a\u8fc7\u5b9e\u4f53\u89c6\u56feid\u83b7\u53d6\u5b9e\u4f53\u6570\u636e\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + e));
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-getFieldsByRegion"}, method={RequestMethod.GET})
    public String getFieldsByRegionKey(String taskId, String dataregionid) {
        try {
            FieldSelectHelper fieldHelper = new FieldSelectHelper(taskId, null);
            Grid2Data fields = fieldHelper.getFieldsGrid(dataregionid);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u83b7\u53d6\u5f53\u524d\u6570\u636e\u533a\u57df\u4e0b\u7684\u6240\u6709\u6307\u6807\u5217\u8868", (String)("\u6210\u529f\uff0cdataRegionid\uff1a" + dataregionid));
            return this.objectMapper.writeValueAsString((Object)fields);
        }
        catch (Exception ex) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u83b7\u53d6\u5f53\u524d\u6570\u636e\u533a\u57df\u4e0b\u7684\u6240\u6709\u6307\u6807\u5217\u8868\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + ex));
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u83b7\u53d6\u5f53\u524d\u6570\u636e\u533a\u57df\u4e0b\u7684\u6240\u6709\u6307\u6807\u5217\u8868", (String)("\u5931\u8d25\uff0cdataRegionid\uff1a" + dataregionid));
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-getMasters"}, method={RequestMethod.GET})
    public String getMasterByTask(String taskId, String schemeKey) {
        try {
            FieldSelectHelper fieldHelper = new FieldSelectHelper(taskId, null);
            List<JSONObject> masters = fieldHelper.getMasterEntity(schemeKey);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u901a\u8fc7\u4efb\u52a1\u548c\u65b9\u6848\u83b7\u53d6\u4e3b\u4f53\u6570\u636e", (String)("\u6210\u529f\uff0ctaskId\uff1a" + taskId + ",schemeKey" + schemeKey));
            return masters.toString();
        }
        catch (Exception ex) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u901a\u8fc7\u4efb\u52a1\u548c\u65b9\u6848\u83b7\u53d6\u4e3b\u4f53\u6570\u636e\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + ex));
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-checkQueryRelation"}, method={RequestMethod.POST})
    public Map<String, String> checkQueryRelation(@RequestBody Map<String, String> params) {
        FieldSelectHelper fieldHelper = new FieldSelectHelper();
        LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u5224\u65ad\u662f\u5426\u5177\u6709\u67e5\u8be2\u5173\u7cfb", (String)"\u6210\u529f");
        return fieldHelper.checkQueryRelation(params);
    }

    @RequestMapping(value={"/fieldselect-masterRelationCheck"}, method={RequestMethod.POST})
    public Map<String, Object> masterRelationCheck(@RequestBody Map<String, Object> params) {
        FieldSelectHelper fieldHelper = new FieldSelectHelper();
        boolean isFirst = params.keySet().size() == 4;
        try {
            Map<String, Object> res = fieldHelper.getMasterViewFields(isFirst, params, isFirst);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u83b7\u53d6\u6307\u6807\u6240\u5c5e\u7684\u4e3b\u4f53\u53ca\u4e0e\u4e0a\u4e00\u6b21\u4e3b\u4f53\u662f\u5426\u6709\u5173\u7cfb", (String)"\u6210\u529f");
            return res;
        }
        catch (Exception e) {
            logger.error("\u4e3b\u4f53\u68c0\u67e5\u5f02\u5e38!" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-masterRelationCheckForFields"}, method={RequestMethod.POST})
    public List<Map<String, Object>> relationCheck(@RequestBody Map<String, Object> params, String isFirst) {
        Boolean isFirstBool = null;
        try {
            isFirstBool = Boolean.valueOf(params.get("isFirst").toString());
            FieldSelectHelper fieldHelper = new FieldSelectHelper();
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u68c0\u67e5\u4e3b\u4f53\u4f9d\u8d56", (String)"\u6210\u529f");
            return fieldHelper.getMasterViewFieldsDesignTime(params, isFirstBool);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u68c0\u67e5\u4e3b\u4f53\u4f9d\u8d56\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + e));
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-fieldSearch"}, method={RequestMethod.POST})
    public Map<String, Object> fieldSearch(@RequestBody Map<String, String> params) {
        FieldSelectHelper fieldHelper = new FieldSelectHelper();
        LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u6307\u6807\u641c\u7d22", (String)("\u6210\u529f\uff0cregionKey\uff1a" + params.get("regionKey") + ",searchWords:" + params.get("searchWords")));
        return fieldHelper.getSearchItems(params.get("regionKey").toString(), params.get("searchWords").toString());
    }

    @RequestMapping(value={"/fieldselect-fieldAuthCheck"}, method={RequestMethod.POST})
    public Map<String, Object> fieldAuthCheck(@RequestBody Map<String, Object> params) {
        String keys = params.get("keys").toString();
        FieldSelectHelper fieldHelper = new FieldSelectHelper();
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        try {
            String[] fieldKeys;
            for (String fieldKey : fieldKeys = keys.split(",")) {
                Boolean auth = fieldHelper.checkFieldAuth(fieldKey.trim());
                result.put(fieldKey, auth);
            }
            return result;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-fieldEnumCheck"}, method={RequestMethod.POST})
    public Map<String, Object> fieldEnumCheck(@RequestBody List<String> fields) {
        try {
            FieldSelectHelper fieldHelper = new FieldSelectHelper();
            Map<String, Object> enumField = fieldHelper.checkEnumField(fields);
            return enumField;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u68c0\u67e5\u679a\u4e3e\u6307\u6807\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + e));
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-getDefaultFields"}, method={RequestMethod.GET})
    public String getExtendFields(String taskKey, String schemeKey) {
        try {
            if (this.fieldSelectExtend == null || this.fieldSelectExtend.size() == 0) {
                return null;
            }
            List<Object> fields = new ArrayList();
            for (IFieldSelectorExtend obj : this.fieldSelectExtend) {
                fields = obj.getDefaultFields(taskKey, schemeKey);
            }
            return this.objectMapper.writeValueAsString(fields);
        }
        catch (BeansException ex) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u52a0\u8f7d\u811a\u672c\u5904\u7406\u5668Bean\u5931\u8d25", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + ex));
            logger.error("\u52a0\u8f7d\u811a\u672c\u5904\u7406\u5668Bean\u5931\u8d25\uff01", ex);
        }
        catch (Exception ex) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u83b7\u53d6\u56fa\u5b9a\u6307\u6807\u5217\u8868\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + ex));
        }
        return null;
    }

    static enum TreeLoadLevel {
        DIRECT,
        DIRECTSECOND,
        ALL;

    }
}

