/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.mapping2.web;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.mapping2.common.MappingErrorEnum;
import com.jiuqi.nr.mapping2.provider.INrMappingType;
import com.jiuqi.nr.mapping2.provider.NrMappingCollector;
import com.jiuqi.nr.mapping2.util.MappingSchemeUtil;
import com.jiuqi.nr.mapping2.web.vo.LabelVO;
import com.jiuqi.nr.mapping2.web.vo.NrMappingParamVO;
import com.jiuqi.nr.mapping2.web.vo.SelectOptionVO;
import com.jiuqi.nr.mapping2.web.vo.TaskFormSchemeVO;
import com.jiuqi.nr.mapping2.web.vo.TaskTreeNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/nr/mapping2/scheme"})
@Api(value="NR\u6620\u5c04\u6269\u5c55\uff1a\u5c5e\u6027\u63a5\u53e3")
public class MappingSchemeController {
    protected final Logger logger = LoggerFactory.getLogger(MappingSchemeController.class);
    @Autowired
    private IDesignTimeViewController designTime;
    @Autowired
    private com.jiuqi.nr.definition.controller.IRunTimeViewController runTime;
    @Autowired
    IRunTimeViewController dRunTime;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private NrMappingCollector collector;

    @GetMapping(value={"/init-task-tree"})
    @ApiOperation(value="\u52a0\u8f7d\u4efb\u52a1\u6811\u5f62")
    public NrMappingParamVO initTaskTree(String taskKey) {
        NrMappingParamVO param = new NrMappingParamVO();
        ArrayList<ITree<TaskTreeNode>> res = new ArrayList<ITree<TaskTreeNode>>();
        res.add(this.buildRootNode(taskKey));
        param.setTaskTree(res);
        ArrayList<LabelVO> types = new ArrayList<LabelVO>();
        for (INrMappingType type : this.collector.getTypeList()) {
            types.add(new LabelVO(type.getTypeCode(), type.getTypeTitle()));
        }
        param.setTypes(types);
        return param;
    }

    private ITree<TaskTreeNode> buildRootNode(String taskKey) {
        TaskTreeNode root = new TaskTreeNode();
        root.setKey("00000000000000000000000000000000");
        root.setCode("00000000000000000000000000000000");
        root.setTitle("\u5168\u90e8\u4efb\u52a1");
        root.setType("GROUP");
        ITree node = new ITree((INode)root);
        node.setExpanded(true);
        node.setLeaf(false);
        ArrayList<ITree<TaskTreeNode>> children = new ArrayList<ITree<TaskTreeNode>>();
        this.buildTaskTree(children, taskKey);
        node.setChildren(children);
        return node;
    }

    private void buildTaskTree(List<ITree<TaskTreeNode>> children, String taskKey) {
        List allTaskGroup = this.designTime.getAllTaskGroup();
        List allTaskDefines = this.runTime.getAllTaskDefines();
        Map<String, TaskDefine> allTaskMap = allTaskDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, task -> task));
        HashSet<String> tasksHasGroup = new HashSet<String>();
        this.buildChildTree(null, allTaskGroup, allTaskMap, children, tasksHasGroup, taskKey);
        for (TaskDefine task2 : allTaskDefines) {
            if (tasksHasGroup.contains(task2.getKey())) continue;
            List<FormSchemeDefine> formSchemes = this.getFormScheme(task2.getKey());
            List<SelectOptionVO> orgLinks = this.getOrgLinks(task2.getKey());
            children.add(MappingSchemeUtil.convertTaskTreeNode(task2, formSchemes, taskKey, orgLinks));
        }
    }

    private void buildChildTree(String parentId, List<DesignTaskGroupDefine> allTaskGroup, Map<String, TaskDefine> allTaskMap, List<ITree<TaskTreeNode>> children, Set<String> tasksHasGroup, String taskKey) {
        List links;
        for (DesignTaskGroupDefine group : allTaskGroup) {
            if (!MappingSchemeController.equals(group.getParentKey(), parentId)) continue;
            ITree<TaskTreeNode> node = MappingSchemeUtil.convertGroupTreeNode(group);
            children.add(node);
            ArrayList<ITree<TaskTreeNode>> nodeChildren = new ArrayList<ITree<TaskTreeNode>>();
            this.buildChildTree(group.getKey(), allTaskGroup, allTaskMap, nodeChildren, tasksHasGroup, taskKey);
            node.setChildren(nodeChildren);
        }
        boolean isRoot = !StringUtils.hasText(parentId);
        List taskList = null;
        if (!isRoot && !CollectionUtils.isEmpty(links = this.designTime.getGroupLinkByGroupKey(parentId))) {
            taskList = links.stream().map(link -> link.getTaskKey()).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(taskList)) {
            tasksHasGroup.addAll(taskList);
            for (String key : taskList) {
                TaskDefine task = allTaskMap.get(key);
                if (task == null) continue;
                List<FormSchemeDefine> formSchemes = this.getFormScheme(task.getKey());
                List<SelectOptionVO> orgLinks = this.getOrgLinks(task.getKey());
                children.add(MappingSchemeUtil.convertTaskTreeNode(task, formSchemes, taskKey, orgLinks));
            }
        }
    }

    @GetMapping(value={"/init-task-formscheme/{taskKey}/{formSchemeKey}/{type}"})
    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u6811\u548c\u62a5\u8868\u65b9\u6848\u5217\u8868")
    public TaskFormSchemeVO getTaskFormScheme(@PathVariable String taskKey, @PathVariable String formSchemeKey, @PathVariable String type) throws JQException {
        INrMappingType mappingType;
        TaskFormSchemeVO res = new TaskFormSchemeVO();
        TaskDefine task = this.runTime.queryTaskDefine(taskKey);
        if (Objects.isNull(task)) {
            DesignTaskDefine designTask = this.designTime.queryTaskDefine(taskKey);
            if (Objects.isNull(designTask)) {
                throw new JQException((ErrorEnum)MappingErrorEnum.MAPPING_003);
            }
            throw new JQException((ErrorEnum)MappingErrorEnum.MAPPING_002);
        }
        FormSchemeDefine formScheme = this.runTime.getFormScheme(formSchemeKey);
        if (Objects.isNull(formScheme)) {
            DesignFormSchemeDefine designForm = this.designTime.queryFormSchemeDefine(formSchemeKey);
            if (Objects.isNull(designForm)) {
                throw new JQException((ErrorEnum)MappingErrorEnum.MAPPING_0033);
            }
            throw new JQException((ErrorEnum)MappingErrorEnum.MAPPING_0022);
        }
        res.setOrgLinks(this.getOrgLinks(taskKey));
        List<FormSchemeDefine> formSchemes = this.getFormScheme(taskKey);
        res.setTaskKey(taskKey);
        res.setTaskTitle(task.getTaskCode() + " | " + task.getTitle());
        res.setOrgName(task.getDw());
        res.setFormShemeKey(formSchemeKey);
        res.setFormShemeTitle(formScheme.getTitle());
        if (!CollectionUtils.isEmpty(formSchemes)) {
            res.setFormSchemes(formSchemes.stream().map(MappingSchemeUtil::convertFormScheme).collect(Collectors.toList()));
        }
        if ((mappingType = this.collector.getTypeMap().get(type)) != null) {
            res.setTypeOption(mappingType.getTypeOption(null));
        }
        return res;
    }

    private List<SelectOptionVO> getOrgLinks(String taskKey) {
        ArrayList<SelectOptionVO> orgLinks = new ArrayList<SelectOptionVO>();
        List orgLinkDefs = this.dRunTime.listTaskOrgLinkByTask(taskKey);
        if (orgLinkDefs.size() > 1) {
            for (TaskOrgLinkDefine orgLinkDef : orgLinkDefs) {
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(orgLinkDef.getEntity());
                if (entityDefine == null) continue;
                SelectOptionVO vo = new SelectOptionVO();
                vo.setValue(entityDefine.getCode());
                vo.setLabel(entityDefine.getTitle() + "[" + entityDefine.getId() + "]");
                orgLinks.add(vo);
            }
        }
        return orgLinks;
    }

    private List<FormSchemeDefine> getFormScheme(String taskKey) {
        try {
            return this.runTime.queryFormSchemeByTask(taskKey);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    private static boolean equals(String a, String b) {
        if (!StringUtils.hasText(a)) {
            a = null;
        }
        if (!StringUtils.hasText(b)) {
            b = null;
        }
        return a == null ? b == null : a.equals(b);
    }
}

