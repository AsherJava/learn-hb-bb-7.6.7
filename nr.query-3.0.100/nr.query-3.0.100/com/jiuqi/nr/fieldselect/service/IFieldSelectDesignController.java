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
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.np.definition.internal.service.DesignTableGroupDefineService
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.DesignTimeViewController
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  javax.annotation.Resource
 *  org.json.JSONObject
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
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.np.definition.internal.service.DesignTableGroupDefineService;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.DesignTimeViewController;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.fieldselect.define.FieldSelectFormData;
import com.jiuqi.nr.fieldselect.service.impl.FieldSelectHelper;
import com.jiuqi.nr.query.querymodal.QueryModalTreeNode;
import com.jiuqi.nr.query.service.impl.QueryDimensionHelper;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/query-design-Manager"})
public class IFieldSelectDesignController {
    @Autowired
    private ObjectMapper objectMapper;
    @Resource
    RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    IDesignDataSchemeService iDesignDataSchemeService;
    @Autowired
    DesignTableGroupDefineService designTableGroupDefineService;
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    IDataDefinitionDesignTimeController dataDefinitionDesignTimeController;
    @Resource
    DesignTimeViewController designTimeViewController;
    private static final Logger logger = LoggerFactory.getLogger(IFieldSelectDesignController.class);

    @RequestMapping(value={"/fieldselect-design-griddata"}, method={RequestMethod.GET})
    public String getFormData(String formid, String formschemekey) {
        try {
            FieldSelectHelper helper = new FieldSelectHelper();
            FieldSelectFormData formData = helper.getDesignFormData(formschemekey, formid);
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            this.objectMapper.registerModule((Module)module);
            String fromDataStr = this.objectMapper.writeValueAsString((Object)formData);
            return fromDataStr;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
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
                    return this.objectMapper.writeValueAsString(schemes);
                }
                case 1: {
                    FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(nodeKey);
                    ITree<QueryModalTreeNode> groups = this.getAllGroupsInScheme(formScheme, TreeLoadLevel.DIRECTSECOND);
                    return this.objectMapper.writeValueAsString(groups);
                }
                case 2: {
                    FormGroupDefine group = this.runTimeAuthViewController.queryFormGroup(nodeKey);
                    ITree<QueryModalTreeNode> forms = this.getAllFormsInGroup(group, TreeLoadLevel.DIRECTSECOND);
                    return this.objectMapper.writeValueAsString(forms);
                }
                case 3: {
                    FormDefine form = this.runTimeAuthViewController.queryFormById(nodeKey);
                    ITree<QueryModalTreeNode> regions = this.getAllRegionsInForm(form);
                    return this.objectMapper.writeValueAsString(regions);
                }
            }
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
                return null;
            }
            List formSchemes = this.runTimeAuthViewController.queryFormSchemeByTask(taskid);
            if (formSchemes == null || formSchemes.size() == 0) {
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
            return this.objectMapper.writeValueAsString(treeNodes);
        }
        catch (Exception e) {
            return null;
        }
    }

    private List<ITree<QueryModalTreeNode>> tableTreeNode(QueryModalTreeNode parent) {
        ArrayList<ITree<QueryModalTreeNode>> tree = new ArrayList<ITree<QueryModalTreeNode>>();
        String parentId = parent.getKey();
        try {
            List tables;
            int leval = parent.getNodeType();
            List tableGroups = this.iRuntimeDataSchemeService.getDataGroupByParent(parentId);
            if (tableGroups != null) {
                tableGroups.forEach(group -> {
                    QueryModalTreeNode node = QueryModalTreeNode.buildTreeNodeData(group.getKey(), group.getTitle(), null, leval + 1);
                    node.setParentId(parentId);
                    ITree childNode = new ITree((INode)node);
                    childNode.setLeaf(false);
                    tree.add(childNode);
                });
            }
            if ((tables = this.iRuntimeDataSchemeService.getDataTableByGroup(parentId)) != null) {
                tables.forEach(table -> {
                    QueryModalTreeNode node = QueryModalTreeNode.buildTreeNodeData(table.getKey(), this.getTitle((DataTable)table), null, leval + 1);
                    node.setParentId(parentId);
                    ITree childNode = new ITree((INode)node);
                    childNode.setLeaf(true);
                    tree.add(childNode);
                });
            }
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return tree;
    }

    private String getTitle(DataTable table) {
        String title = table.getCode() + (StringUtils.isEmpty(table.getTitle()) ? "" : " | " + table.getTitle());
        return title;
    }

    @RequestMapping(value={"/fieldselect-design-queryTreeNodesChild"}, method={RequestMethod.POST})
    public String queryChildTreeData(@RequestBody QueryModalTreeNode treeNode) {
        try {
            List<Object> treeNodes = new ArrayList();
            treeNodes = this.tableTreeNode(treeNode);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u8bbe\u8ba1\u671f-\u83b7\u53d6\u6307\u6807\u5206\u7ec4\u5b50\u8282\u70b9\u6570\u636e", (String)("\u6210\u529f\uff0c\u8282\u70b9Id:" + treeNode.getId()));
            return this.objectMapper.writeValueAsString(treeNodes);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u8bbe\u8ba1\u671f-\u83b7\u53d6\u6307\u6807\u5206\u7ec4\u5b50\u8282\u70b9\u6570\u636e\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0:" + e));
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
                        QueryModalTreeNode node = QueryModalTreeNode.buildTreeNodeData(formDefine.getKey(), formDefine.getTitle(), formDefine.getUpdateTime(), 3);
                        ITree tree = new ITree((INode)node);
                        tree.setLeaf(false);
                        groups.add(tree);
                    }
                }
            }
            return this.objectMapper.writeValueAsString(groups);
        }
        catch (Exception e) {
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
            return this.objectMapper.writeValueAsString(groups);
        }
        catch (Exception e) {
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
            return this.objectMapper.writeValueAsString(groups);
        }
        catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-design-gottasks"}, method={RequestMethod.GET})
    public List<DesignTaskDefine> getAllTaskDefines(String taskCode) {
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)taskCode)) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u8bbe\u8ba1\u671f-\u83b7\u53d6\u4efb\u52a1", (String)"\u6210\u529f\uff0ctaskCode\u4e3a\u7a7a\uff0c\u67e5\u8be2\u6240\u6709\u4efb\u52a1");
            return this.designTimeViewController.getAllTaskDefines();
        }
        ArrayList<DesignTaskDefine> taskList = new ArrayList<DesignTaskDefine>();
        DesignTaskDefine task = this.designTimeViewController.queryTaskDefineByCode(taskCode);
        if (task != null) {
            taskList.add(task);
        }
        LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u8bbe\u8ba1\u671f-\u83b7\u53d6\u4efb\u52a1", (String)("\u6210\u529f\uff0ctaskCode\uff1a" + taskCode));
        return taskList;
    }

    @RequestMapping(value={"/fieldselect-design-gotFormSchemeByTask"}, method={RequestMethod.GET})
    public List<DesignFormSchemeDefine> getFormSchemeByTask(String taskKey) {
        try {
            if (!com.jiuqi.bi.util.StringUtils.isEmpty((String)taskKey) && !taskKey.equals("undefined")) {
                LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u8bbe\u8ba1\u671f-\u6839\u636e\u4efb\u52a1ID\u83b7\u53d6\u4efb\u52a1\u4e0b\u6240\u6709\u65b9\u6848", (String)("\u6210\u529f\uff0ctaskKey\uff1a" + taskKey));
                return this.designTimeViewController.queryFormSchemeByTask(taskKey);
            }
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u8bbe\u8ba1\u671f-\u6839\u636e\u4efb\u52a1ID\u83b7\u53d6\u4efb\u52a1\u4e0b\u6240\u6709\u65b9\u6848", (String)"\u5931\u8d25\uff0ctaskKey\u4e3a\u7a7a");
            return null;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u8bbe\u8ba1\u671f-\u6839\u636e\u4efb\u52a1ID\u83b7\u53d6\u4efb\u52a1\u4e0b\u6240\u6709\u65b9\u6848\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + taskKey));
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-design-getgroupandforms"}, method={RequestMethod.GET})
    public String getGroupAndFroms(String schemeKey) {
        FieldSelectHelper fieldHelper = new FieldSelectHelper();
        LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u8bbe\u8ba1\u671f-\u83b7\u53d6\u65b9\u6848\u4e0b\u6240\u6709\u5206\u7ec4\u548c\u8868\u5355", (String)("\u6210\u529f\uff0cschemeKey\uff1a" + schemeKey));
        return fieldHelper.getDesignGroupAndFroms(schemeKey);
    }

    @Deprecated
    @RequestMapping(value={"/fieldselect-getEnumbyFields"}, method={RequestMethod.GET})
    public String getContentByMasterKey(String entityViewKey) {
        try {
            QueryDimensionHelper dimensionHelper = new QueryDimensionHelper();
            return dimensionHelper.getContentByMasterKey(entityViewKey);
        }
        catch (DataTypeException e) {
            return null;
        }
        catch (RuntimeException e) {
            return null;
        }
        catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-getFieldsByRegion"}, method={RequestMethod.GET})
    public String getFieldsByRegionKey(String taskId, String dataregionid) {
        try {
            FieldSelectHelper fieldHelper = new FieldSelectHelper(taskId, null);
            Grid2Data fields = fieldHelper.getFieldsGrid(dataregionid);
            return this.objectMapper.writeValueAsString((Object)fields);
        }
        catch (Exception exception) {
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-design-getChildFields"}, method={RequestMethod.POST})
    public String getFieldsByRegionKey(@RequestBody QueryModalTreeNode treeNode) {
        try {
            FieldSelectHelper fieldHelper = new FieldSelectHelper(null, null);
            Grid2Data fields = fieldHelper.getChildGridByTable(treeNode.getId(), treeNode.getTitle());
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u8bbe\u8ba1\u671f-\u83b7\u53d6\u6811\u8282\u70b9\u4e0b\u7684\u6307\u6807\u5217\u8868", (String)("\u6210\u529f\uff0cnodeKey\uff1a" + treeNode.getKey()));
            return this.objectMapper.writeValueAsString((Object)fields);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u8bbe\u8ba1\u671f-\u83b7\u53d6\u6811\u8282\u70b9\u4e0b\u7684\u6307\u6807\u5217\u8868\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + ex));
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-design-getMasters"}, method={RequestMethod.GET})
    public String getMasterByTask(String taskId, String schemeKey) {
        try {
            FieldSelectHelper fieldHelper = new FieldSelectHelper(taskId, null);
            List<JSONObject> masters = fieldHelper.getDesignMasterEntity(schemeKey);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u8bbe\u8ba1\u671f-\u901a\u8fc7\u4efb\u52a1\u548c\u65b9\u6848\u83b7\u53d6\u4e3b\u4f53\u6570\u636e", (String)("\u6210\u529f\uff0ctaskId\uff1a" + taskId + ",schemeKey" + schemeKey));
            return masters.toString();
        }
        catch (Exception ex) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u8bbe\u8ba1\u671f-\u901a\u8fc7\u4efb\u52a1\u548c\u65b9\u6848\u83b7\u53d6\u4e3b\u4f53\u6570\u636e\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + ex));
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-checkQueryRelation"}, method={RequestMethod.POST})
    public Map<String, String> checkQueryRelation(@RequestBody Map<String, String> params) {
        FieldSelectHelper fieldHelper = new FieldSelectHelper();
        return fieldHelper.checkQueryRelation(params);
    }

    @RequestMapping(value={"/fieldselect-design-masterRelationCheck"}, method={RequestMethod.POST})
    public Map<String, Object> masterRelationCheck(@RequestBody Map<String, Object> params) {
        FieldSelectHelper fieldHelper = new FieldSelectHelper();
        boolean isFirst = params.keySet().size() == 2;
        LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u8bbe\u8ba1\u671f-\u83b7\u53d6\u6307\u6807\u6240\u5c5e\u7684\u4e3b\u4f53\u53ca\u4e0e\u4e0a\u4e00\u6b21\u4e3b\u4f53\u662f\u5426\u6709\u5173\u7cfb", (String)"\u6210\u529f");
        return fieldHelper.getDesignMasterViewFields(isFirst ? null : params, params.get("schemeKey").toString(), params.get("regionKey").toString(), isFirst);
    }

    @RequestMapping(value={"/fieldselect-masterRelationCheckForFields"}, method={RequestMethod.POST})
    public List<Map<String, Object>> relationCheck(@RequestBody Map<String, Object> params, String isFirst) {
        Boolean isFirstBool = null;
        try {
            isFirstBool = Boolean.valueOf(params.get("isFirst").toString());
            FieldSelectHelper fieldHelper = new FieldSelectHelper();
            return fieldHelper.getMasterViewFieldsDesignTime(params, isFirstBool);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value={"/fieldselect-fieldSearch"}, method={RequestMethod.POST})
    public Map<String, Object> fieldSearch(@RequestBody Map<String, String> params) {
        FieldSelectHelper fieldHelper = new FieldSelectHelper();
        return fieldHelper.getSearchItems(params.get("regionKey").toString(), params.get("searchWords").toString());
    }

    static enum TreeLoadLevel {
        DIRECT,
        DIRECTSECOND,
        ALL;

    }
}

