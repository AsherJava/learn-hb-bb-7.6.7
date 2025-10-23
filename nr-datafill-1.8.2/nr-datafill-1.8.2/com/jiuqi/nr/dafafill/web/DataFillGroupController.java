/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.bi.util.StringHelper
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.json.JSONObject
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dafafill.web;

import com.jiuqi.bi.util.Guid;
import com.jiuqi.bi.util.StringHelper;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.dafafill.common.DataFillErrorEnum;
import com.jiuqi.nr.dafafill.entity.DataFillDefinition;
import com.jiuqi.nr.dafafill.entity.DataFillGroup;
import com.jiuqi.nr.dafafill.model.enums.ModelType;
import com.jiuqi.nr.dafafill.service.IDataFillDefinitionService;
import com.jiuqi.nr.dafafill.service.IDataFillGroupService;
import com.jiuqi.nr.dafafill.tree.SearchTreeNode;
import com.jiuqi.nr.dafafill.util.NrDataFillI18nUtil;
import com.jiuqi.nr.dafafill.web.vo.GroupTreeNodeVO;
import com.jiuqi.nr.dafafill.web.vo.MoveTreeNodeVO;
import com.jiuqi.nr.dafafill.web.vo.ReturnInfoVO;
import com.jiuqi.nr.dafafill.web.vo.TableItemVO;
import com.jiuqi.nr.dafafill.web.vo.TreeSelectNode;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping(value={"/api/v1/datafill/group"})
@Api(value="\u81ea\u5b9a\u4e49\u5f55\u5165\u5206\u7ec4\u7ba1\u7406")
public class DataFillGroupController {
    @Autowired
    IDataFillGroupService dataFillGroupService;
    @Autowired
    IDataFillDefinitionService dataFillDefinitionService;
    @Autowired
    private RunTimeAuthViewController runTimeAuthView;
    private static final String ALLTEMPLATES = "nr.dataFill.allTemplates";

    @GetMapping(value={"/{id}"})
    @ApiOperation(value="\u83b7\u53d6\u5206\u7ec4")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public DataFillGroup getByid(@PathVariable String id) throws JQException {
        return null;
    }

    @PostMapping(value={"add"})
    @ApiOperation(value="\u65b0\u589e\u5206\u7ec4")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public ReturnInfoVO add(@Valid @RequestBody DataFillGroup group) throws JQException {
        ReturnInfoVO returnInfo = new ReturnInfoVO();
        group.setModifyTime(new Timestamp(System.currentTimeMillis()));
        List<DataFillGroup> groupList = this.dataFillGroupService.findByParentId(group.getParentId());
        for (DataFillGroup g : groupList) {
            if (!g.getTitle().equals(group.getTitle()) || g.getId().equals(group.getId())) continue;
            returnInfo.setMsg(DataFillErrorEnum.DF_ERROR_TITLE_REPEAT.getMessage());
            return returnInfo;
        }
        List<DataFillDefinition> definitionList = this.dataFillDefinitionService.findByParentId(group.getParentId());
        for (DataFillDefinition d : definitionList) {
            if (!d.getTitle().equals(group.getTitle()) || d.getId().equals(group.getId())) continue;
            returnInfo.setMsg(DataFillErrorEnum.DF_ERROR_TITLE_REPEAT.getMessage());
            return returnInfo;
        }
        if (StringHelper.isNull((String)group.getId())) {
            group.setId(Guid.newGuid());
            group.setCreateTime(group.getModifyTime());
            returnInfo.setData(this.dataFillGroupService.add(group));
        } else {
            returnInfo.setData(this.dataFillGroupService.modify(group));
        }
        returnInfo.setSuccess(true);
        return returnInfo;
    }

    @PostMapping(value={"delete/{id}"})
    @ApiOperation(value="\u5220\u9664\u5206\u7ec4")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public void delete(@PathVariable String id) throws JQException {
        this.dataFillGroupService.delete(id);
    }

    @PostMapping(value={"canDelete"})
    @ApiOperation(value="\u5224\u65ad\u9009\u4e2d\u5206\u7ec4\u662f\u5426\u53ef\u4ee5\u5220\u9664")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public ReturnInfoVO canDelete(@RequestBody List<String> ids) {
        ReturnInfoVO returnInfo = new ReturnInfoVO();
        if (this.hasChildren(ids)) {
            returnInfo.setMsg(DataFillErrorEnum.DG_ERROR_NOT_DELETE.getMessage());
        } else {
            returnInfo.setSuccess(true);
        }
        return returnInfo;
    }

    private boolean hasChildren(List<String> ids) {
        for (String id : ids) {
            if (CollectionUtils.isEmpty(this.dataFillGroupService.findByParentId(id)) && CollectionUtils.isEmpty(this.dataFillDefinitionService.findByParentId(id))) continue;
            return true;
        }
        return false;
    }

    @PostMapping(value={"modify"})
    @ApiOperation(value="\u4fee\u6539\u5206\u7ec4")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public void modify(@RequestBody DataFillGroup group) throws JQException {
    }

    @GetMapping(value={"getchildren"})
    @ApiOperation(value="\u83b7\u53d6\u4e0b\u7ea7\u5206\u7ec4")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public List<DataFillGroup> getChildGroup(String id) throws JQException {
        return null;
    }

    @GetMapping(value={"/getChildrenGroupDef"})
    @ApiOperation(value="\u83b7\u53d6\u4e0b\u7ea7\u5206\u7ec4\u548c\u6a21\u677f\u5b9a\u4e49")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public List<TableItemVO> getChildrenGroupDef(@RequestParam String id, @RequestParam(required=false) String taskKey) throws JQException {
        if (StringUtils.hasText(taskKey)) {
            List definitions;
            HashMap<String, List<DataFillGroup>> parentToGro = new HashMap<String, List<DataFillGroup>>();
            HashMap<String, List<DataFillDefinition>> parentToDefByF = new HashMap<String, List<DataFillDefinition>>();
            HashSet<String> legalParentSet = new HashSet<String>();
            this.buildLegalParentSet(taskKey, parentToGro, parentToDefByF, legalParentSet);
            List groups = (List)parentToGro.get(id);
            List<TableItemVO> res = new ArrayList<TableItemVO>();
            if (!CollectionUtils.isEmpty(groups)) {
                res = groups.stream().filter(e -> legalParentSet.contains(e.getId())).map(TableItemVO::new).collect(Collectors.toList());
            }
            if (!CollectionUtils.isEmpty(definitions = (List)parentToDefByF.get(id))) {
                Map<String, TaskDefine> taskDefineMap = this.runTimeAuthView.getAllTaskDefines().stream().collect(Collectors.toMap(IBaseMetaItem::getKey, e -> e));
                res.addAll(definitions.stream().map(def -> new TableItemVO((DataFillDefinition)def, taskDefineMap)).collect(Collectors.toList()));
            }
            return res;
        }
        List<DataFillGroup> groups = this.dataFillGroupService.findByParentId(id);
        List<TableItemVO> res = groups.stream().map(TableItemVO::new).collect(Collectors.toList());
        List<DataFillDefinition> definitions = this.dataFillDefinitionService.findByParentId(id);
        Map<String, TaskDefine> taskDefineMap = this.runTimeAuthView.getAllTaskDefines().stream().collect(Collectors.toMap(IBaseMetaItem::getKey, e -> e));
        res.addAll(definitions.stream().map(def -> new TableItemVO((DataFillDefinition)def, taskDefineMap)).collect(Collectors.toList()));
        return res;
    }

    private static List<DataFillDefinition> filterBySingle(String taskKey, List<DataFillDefinition> definitions) {
        return definitions.stream().filter(e -> e.getSourceType() == ModelType.TASK || e.getSourceType() == ModelType.SCHEME && taskKey.equals(e.getTaskKey())).collect(Collectors.toList());
    }

    @GetMapping(value={"/allTree"})
    @ApiOperation(value="\u521d\u59cb\u5316\u6574\u9897\u5206\u7ec4\u6811")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public List<ITree<GroupTreeNodeVO>> initGroupTree(@RequestParam String taskKey) {
        ArrayList<ITree<GroupTreeNodeVO>> nodes = new ArrayList<ITree<GroupTreeNodeVO>>();
        ITree root = new ITree((INode)new GroupTreeNodeVO("00000000000000000000000000000000", "00000000000000000000000000000000", ALLTEMPLATES));
        root.setSelected(true);
        root.setExpanded(true);
        root.setIcons(new String[]{"#icon14_SHU_A_NW_tubiaokezhankai"});
        HashMap<String, List<DataFillGroup>> parentToGro = new HashMap<String, List<DataFillGroup>>();
        HashSet<String> legalParentSet = new HashSet<String>();
        this.buildLegalParentSet(taskKey, parentToGro, new HashMap<String, List<DataFillDefinition>>(), legalParentSet);
        root.setChildren(this.buildGroupChildren("00000000000000000000000000000000", parentToGro, legalParentSet));
        root.setLeaf(CollectionUtils.isEmpty(root.getChildren()));
        nodes.add(root);
        return nodes;
    }

    private void buildLegalParentSet(String taskKey, Map<String, List<DataFillGroup>> parentToGro, Map<String, List<DataFillDefinition>> parentToDefByF, Set<String> legalParentSet) {
        List<DataFillDefinition> allDefs = this.dataFillDefinitionService.findAllDefinitions();
        Set defParents = allDefs.stream().map(DataFillDefinition::getParentId).collect(Collectors.toSet());
        allDefs = DataFillGroupController.filterBySingle(taskKey, allDefs);
        for (DataFillDefinition dataFillDefinition : allDefs) {
            List children = parentToDefByF.computeIfAbsent(dataFillDefinition.getParentId(), k -> new ArrayList());
            children.add(dataFillDefinition);
        }
        List<DataFillGroup> allGroups = this.dataFillGroupService.query();
        for (DataFillGroup group : allGroups) {
            List children = parentToGro.computeIfAbsent(group.getParentId(), k -> new ArrayList());
            children.add(group);
        }
        List list = allGroups.stream().filter(e -> !defParents.contains(e.getId()) && !parentToGro.containsKey(e.getId())).map(DataFillGroup::getId).collect(Collectors.toList());
        legalParentSet.addAll(list);
        legalParentSet.addAll(parentToDefByF.keySet());
        for (String groupKey : list) {
            Optional<DataFillGroup> fillGroupOptional = allGroups.stream().filter(e -> groupKey.equals(e.getId())).findFirst();
            while (fillGroupOptional.isPresent()) {
                String parentId = fillGroupOptional.get().getParentId();
                legalParentSet.add(parentId);
                fillGroupOptional = allGroups.stream().filter(e -> parentId.equals(e.getId())).findFirst();
            }
        }
    }

    private List<ITree<GroupTreeNodeVO>> buildGroupChildren(String parentId, Map<String, List<DataFillGroup>> parentToGro, Set<String> legalParentSet) {
        ArrayList<ITree<GroupTreeNodeVO>> list = new ArrayList<ITree<GroupTreeNodeVO>>();
        if (!parentToGro.containsKey(parentId) || CollectionUtils.isEmpty(legalParentSet)) {
            return list;
        }
        List<DataFillGroup> groupList = parentToGro.get(parentId);
        for (DataFillGroup group : groupList) {
            String groupId = group.getId();
            if (!legalParentSet.contains(groupId)) continue;
            ITree inode = new ITree((INode)new GroupTreeNodeVO(group));
            inode.setIcons(new String[]{"#icon14_SHU_A_NW_tubiaokezhankai"});
            inode.setChildren(this.buildGroupChildren(groupId, parentToGro, legalParentSet));
            inode.setLeaf(CollectionUtils.isEmpty(inode.getChildren()));
            list.add((ITree<GroupTreeNodeVO>)inode);
        }
        return list;
    }

    @GetMapping(value={"tree"})
    @ApiOperation(value="\u83b7\u53d6\u5206\u7ec4\u6811")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public List<ITree<GroupTreeNodeVO>> getGroupTree(String id) throws JQException {
        ArrayList<ITree<GroupTreeNodeVO>> nodes = new ArrayList<ITree<GroupTreeNodeVO>>();
        GroupTreeNodeVO group = new GroupTreeNodeVO();
        group.setKey("00000000000000000000000000000000");
        group.setTitle(ALLTEMPLATES);
        group.setCode("00000000000000000000000000000000");
        ITree root = new ITree((INode)group);
        root.setLeaf(false);
        root.setSelected(true);
        root.setExpanded(true);
        root.setIcons(new String[]{"#icon14_SHU_A_NW_tubiaokezhankai"});
        nodes.add(root);
        List<DataFillGroup> childGroups = this.dataFillGroupService.findByParentId("00000000000000000000000000000000");
        if (!CollectionUtils.isEmpty(childGroups)) {
            List<String> parentIds = this.dataFillGroupService.getAllParentId();
            for (DataFillGroup g : childGroups) {
                GroupTreeNodeVO node = new GroupTreeNodeVO(g);
                ITree inode = new ITree((INode)node);
                inode.setLeaf(!parentIds.contains(g.getId()));
                inode.setIcons(new String[]{"#icon14_SHU_A_NW_tubiaokezhankai"});
                root.appendChild(inode);
            }
        } else {
            root.setLeaf(true);
        }
        return nodes;
    }

    @GetMapping(value={"children/{id}"})
    @ApiOperation(value="\u83b7\u53d6\u5206\u7ec4\u6811")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public List<ITree<GroupTreeNodeVO>> getChildren(@PathVariable String id) throws JQException {
        ArrayList<ITree<GroupTreeNodeVO>> nodes = new ArrayList<ITree<GroupTreeNodeVO>>();
        List<String> parentIds = this.dataFillGroupService.getAllParentId();
        List<DataFillGroup> childGroups = this.dataFillGroupService.findByParentId(id);
        if (!CollectionUtils.isEmpty(childGroups)) {
            for (DataFillGroup g : childGroups) {
                GroupTreeNodeVO node = new GroupTreeNodeVO(g);
                ITree inode = new ITree((INode)node);
                inode.setLeaf(!parentIds.contains(g.getId()));
                inode.setIcons(new String[]{"#icon14_SHU_A_NW_tubiaokezhankai"});
                nodes.add((ITree<GroupTreeNodeVO>)inode);
            }
        }
        return nodes;
    }

    @GetMapping(value={"locate/{id}"})
    @ApiOperation(value="\u5b9a\u4f4d\u5206\u7ec4\u6811")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public List<String> locate(@PathVariable String id) {
        ArrayList<String> paths = new ArrayList<String>();
        paths.add("00000000000000000000000000000000");
        this.findAllParentGroup(id, paths);
        return paths;
    }

    @PostMapping(value={"batch-del"})
    @ApiOperation(value="\u6279\u91cf\u5220\u9664\u5206\u7ec4\u548c\u6a21\u677f")
    @Transactional
    @RequiresPermissions(value={"nr:datafill:manage"})
    public ReturnInfoVO batchDel(@RequestBody List<TableItemVO> items) throws JQException {
        ArrayList<String> templateIds = new ArrayList<String>();
        ArrayList<String> groupIds = new ArrayList<String>();
        this.separate(items, templateIds, groupIds);
        this.dataFillDefinitionService.batchDelete(templateIds);
        ArrayList<String> tempGroupIds = new ArrayList<String>();
        StringBuffer hasChildrenGroupBuffer = new StringBuffer();
        if (groupIds.size() > 0) {
            List<DataFillGroup> allGroups = this.dataFillGroupService.query();
            Map<String, String> map = allGroups.stream().collect(Collectors.toMap(DataFillGroup::getId, DataFillGroup::getTitle));
            for (int i = 0; i < groupIds.size(); ++i) {
                String groupId = (String)groupIds.get(i);
                if (!CollectionUtils.isEmpty(this.dataFillGroupService.findByParentId(groupId)) || !CollectionUtils.isEmpty(this.dataFillDefinitionService.findByParentId(groupId))) {
                    hasChildrenGroupBuffer.append(map.get(groupId)).append("\uff0c");
                    continue;
                }
                tempGroupIds.add(groupId);
            }
        }
        this.dataFillGroupService.batchDelete(tempGroupIds);
        ReturnInfoVO returnInfo = new ReturnInfoVO();
        if (hasChildrenGroupBuffer.length() > 0) {
            returnInfo.setMsg(hasChildrenGroupBuffer.toString().substring(0, hasChildrenGroupBuffer.length() - 1) + "\u3002");
            returnInfo.setData(tempGroupIds);
        } else {
            returnInfo.setSuccess(true);
        }
        return returnInfo;
    }

    private void separate(List<TableItemVO> items, List<String> templateIds, List<String> groupIds) {
        for (TableItemVO item : items) {
            if (item.getSourceType() == null) {
                groupIds.add(item.getId());
                continue;
            }
            templateIds.add(item.getId());
        }
    }

    private void findAllParentGroup(String id, List<String> paths) {
        DataFillGroup group = this.dataFillGroupService.findById(id);
        if (group != null && !"00000000000000000000000000000000".equals(group.getParentId())) {
            this.findAllParentGroup(group.getParentId(), paths);
        }
        paths.add(id);
    }

    @GetMapping(value={"search"})
    @ApiOperation(value="\u5b9a\u4f4d\u5206\u7ec4\u6811")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public List<SearchTreeNode> search(@RequestParam String fuzzyKey, @RequestParam(required=false) String taskKey) {
        ArrayList<SearchTreeNode> res = new ArrayList<SearchTreeNode>();
        HashMap<String, List<String>> groupPaths = new HashMap<String, List<String>>();
        HashMap<String, List<String>> groupTitlePaths = new HashMap<String, List<String>>();
        List<DataFillDefinition> templates = this.dataFillDefinitionService.fuzzySearch(fuzzyKey);
        List<DataFillGroup> groups = this.dataFillGroupService.fuzzySearch(fuzzyKey);
        if (StringUtils.hasText(taskKey)) {
            templates = DataFillGroupController.filterBySingle(taskKey, templates);
            HashSet<String> legalParentSet = new HashSet<String>();
            this.buildLegalParentSet(taskKey, new HashMap<String, List<DataFillGroup>>(), new HashMap<String, List<DataFillDefinition>>(), legalParentSet);
            groups = groups.stream().filter(e -> legalParentSet.contains(e.getId())).collect(Collectors.toList());
        }
        HashSet<String> templateGuids = new HashSet<String>();
        for (DataFillDefinition t : templates) {
            if (!templateGuids.add(t.getId())) continue;
            res.add(this.convertTemplateToSearch(t, groupPaths, groupTitlePaths));
        }
        res.addAll(groups.stream().map(g -> this.convertGroupToSearch((DataFillGroup)g, groupPaths, groupTitlePaths)).collect(Collectors.toList()));
        return res;
    }

    private SearchTreeNode convertTemplateToSearch(DataFillDefinition template, HashMap<String, List<String>> groupPaths, HashMap<String, List<String>> groupTitlePaths) {
        SearchTreeNode search = new SearchTreeNode();
        if (!groupPaths.containsKey(template.getParentId())) {
            this.findAllParentGroup(template.getParentId(), groupPaths, groupTitlePaths);
        }
        search.setKey("TYPE_T" + template.getId());
        search.setKeyPath(groupPaths.get(template.getParentId()));
        List titlePaths = groupTitlePaths.get(template.getParentId()).stream().collect(Collectors.toList());
        titlePaths.add(template.getTitle());
        search.setTitlePath(titlePaths.stream().collect(Collectors.joining("\\")));
        return search;
    }

    private SearchTreeNode convertGroupToSearch(DataFillGroup group, HashMap<String, List<String>> groupPaths, HashMap<String, List<String>> groupTitlePaths) {
        SearchTreeNode search = new SearchTreeNode();
        if (!groupPaths.containsKey(group.getId())) {
            this.findAllParentGroup(group.getId(), groupPaths, groupTitlePaths);
        }
        search.setKey("TYPE_G" + group.getId());
        search.setKeyPath(groupPaths.get(group.getId()));
        search.setTitlePath(groupTitlePaths.get(group.getId()).stream().collect(Collectors.joining("\\")));
        return search;
    }

    private void findAllParentGroup(String id, HashMap<String, List<String>> groupPaths, HashMap<String, List<String>> groupTitlePaths) {
        ArrayList<String> paths = null;
        ArrayList<String> titles = null;
        if (!groupPaths.containsKey(id)) {
            paths = new ArrayList<String>();
            titles = new ArrayList<String>();
            groupPaths.put(id, paths);
            groupTitlePaths.put(id, titles);
            DataFillGroup group = this.dataFillGroupService.findById(id);
            if (group == null) {
                paths.add("00000000000000000000000000000000");
                titles.add(NrDataFillI18nUtil.parseMsg(NrDataFillI18nUtil.buildCode(ALLTEMPLATES)));
            } else {
                this.findAllParentGroup(group.getParentId(), groupPaths, groupTitlePaths);
                paths.addAll((Collection)groupPaths.get(group.getParentId()));
                titles.addAll((Collection)groupTitlePaths.get(group.getParentId()));
                paths.add(id);
                titles.add(group.getTitle());
            }
        }
    }

    @GetMapping(value={"paramter-template-tree"})
    @ApiOperation(value="\u529f\u80fd\u53c2\u6570\uff1a\u9009\u9879\u6a21\u677f\u6811\u5f62")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public List<Map<String, Object>> paramterTemplateTree() {
        ArrayList<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
        HashSet<String> effectiveGroups = new HashSet<String>();
        Map<String, List<DataFillDefinition>> defMap = this.dataFillDefinitionService.findAllByParent();
        effectiveGroups.addAll(defMap.keySet());
        List<DataFillGroup> groupList = this.dataFillGroupService.query();
        HashMap<String, List<DataFillGroup>> groupChildMap = new HashMap<String, List<DataFillGroup>>();
        HashMap<String, DataFillGroup> groupMap = new HashMap<String, DataFillGroup>();
        for (DataFillGroup g : groupList) {
            groupMap.put(g.getId(), g);
            ArrayList<DataFillGroup> childList = (ArrayList<DataFillGroup>)groupChildMap.get(g.getParentId());
            if (childList == null) {
                childList = new ArrayList<DataFillGroup>();
                groupChildMap.put(g.getParentId(), childList);
            }
            childList.add(g);
        }
        for (String groupId : defMap.keySet()) {
            HashSet<String> prentGuids = new HashSet<String>();
            this.findAllPrentGuid(groupMap, prentGuids, groupId);
            effectiveGroups.addAll(prentGuids);
        }
        this.buildParameterTree("00000000000000000000000000000000", groupChildMap, defMap, effectiveGroups, nodes);
        return nodes;
    }

    private void findAllPrentGuid(Map<String, DataFillGroup> groupMap, Set<String> prentGuids, String id) {
        DataFillGroup group = groupMap.get(id);
        if (group != null) {
            prentGuids.add(group.getParentId());
            this.findAllPrentGuid(groupMap, prentGuids, group.getParentId());
        }
    }

    private void buildParameterTree(String parentId, Map<String, List<DataFillGroup>> groupChildMap, Map<String, List<DataFillDefinition>> defMap, Set<String> effectiveGroups, List<Map<String, Object>> children) {
        List<DataFillDefinition> childDef;
        List<DataFillGroup> childGroup = groupChildMap.get(parentId);
        if (!CollectionUtils.isEmpty(childGroup)) {
            for (DataFillGroup group : childGroup) {
                if (!effectiveGroups.contains(group.getId())) continue;
                HashMap<String, Object> pnode = new HashMap<String, Object>();
                pnode.put("id", group.getId());
                pnode.put("label", group.getTitle());
                children.add(pnode);
                ArrayList<Map<String, Object>> pchildren = new ArrayList<Map<String, Object>>();
                pnode.put("children", pchildren);
                this.buildParameterTree(group.getId(), groupChildMap, defMap, effectiveGroups, pchildren);
            }
        }
        if (!CollectionUtils.isEmpty(childDef = defMap.get(parentId))) {
            for (DataFillDefinition def : childDef) {
                HashMap<String, String> pnode = new HashMap<String, String>();
                pnode.put("id", def.getId());
                pnode.put("label", def.getTitle());
                children.add(pnode);
            }
        }
    }

    @GetMapping(value={"initTreeSelect"})
    @ApiOperation(value="\u521d\u59cb\u5316\u5206\u7ec4TreeSelect")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public String initTreeSelect() {
        ArrayList<TreeSelectNode> list = new ArrayList<TreeSelectNode>();
        TreeSelectNode treeSelectNode = new TreeSelectNode();
        treeSelectNode.setId("00000000000000000000000000000000");
        treeSelectNode.setLabel(ALLTEMPLATES);
        treeSelectNode.setChildren(this.buildChildren("00000000000000000000000000000000"));
        list.add(treeSelectNode);
        return JSONObject.valueToString(list);
    }

    private List<TreeSelectNode> buildChildren(String parentId) {
        ArrayList<TreeSelectNode> list = new ArrayList<TreeSelectNode>();
        List<DataFillGroup> childGroups = this.dataFillGroupService.findByParentId(parentId);
        if (CollectionUtils.isEmpty(childGroups)) {
            return null;
        }
        for (DataFillGroup dataFillGroup : childGroups) {
            TreeSelectNode treeSelectNode = new TreeSelectNode();
            treeSelectNode.setId(dataFillGroup.getId());
            treeSelectNode.setLabel(dataFillGroup.getTitle());
            treeSelectNode.setChildren(this.buildChildren(dataFillGroup.getId()));
            list.add(treeSelectNode);
        }
        return list;
    }

    @PostMapping(value={"initMoveTree"})
    @ApiOperation(value="\u521d\u59cb\u5316\u5206\u7ec4TreeSelect")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public String initTreeSelect(@RequestBody List<TableItemVO> items, @RequestParam(required=false) String taskKey) {
        ArrayList<MoveTreeNodeVO> list = new ArrayList<MoveTreeNodeVO>();
        MoveTreeNodeVO MoveTreeNodeVO2 = new MoveTreeNodeVO();
        MoveTreeNodeVO2.setId("00000000000000000000000000000000");
        MoveTreeNodeVO2.setTitle(ALLTEMPLATES);
        if (StringUtils.hasText(taskKey)) {
            HashMap<String, List<DataFillGroup>> parentToGro = new HashMap<String, List<DataFillGroup>>();
            HashSet<String> legalParentSet = new HashSet<String>();
            this.buildLegalParentSet(taskKey, parentToGro, new HashMap<String, List<DataFillDefinition>>(), legalParentSet);
            MoveTreeNodeVO2.setChildren(this.buildMoveChildren("00000000000000000000000000000000", items.stream().map(TableItemVO::getId).collect(Collectors.toList()), parentToGro, legalParentSet));
        } else {
            MoveTreeNodeVO2.setChildren(this.buildMoveChildren("00000000000000000000000000000000", items.stream().map(TableItemVO::getId).collect(Collectors.toList())));
        }
        list.add(MoveTreeNodeVO2);
        return JSONObject.valueToString(list);
    }

    private List<MoveTreeNodeVO> buildMoveChildren(String parentId, List<String> idList) {
        ArrayList<MoveTreeNodeVO> list = new ArrayList<MoveTreeNodeVO>();
        List<DataFillGroup> childGroups = this.dataFillGroupService.findByParentId(parentId);
        if (CollectionUtils.isEmpty(childGroups)) {
            return null;
        }
        for (DataFillGroup dataFillGroup : childGroups) {
            MoveTreeNodeVO MoveTreeNodeVO2 = new MoveTreeNodeVO();
            MoveTreeNodeVO2.setId(dataFillGroup.getId());
            MoveTreeNodeVO2.setTitle(dataFillGroup.getTitle());
            MoveTreeNodeVO2.setChildren(this.buildMoveChildren(dataFillGroup.getId(), idList));
            if (idList.contains(dataFillGroup.getId())) continue;
            list.add(MoveTreeNodeVO2);
        }
        return list;
    }

    private List<MoveTreeNodeVO> buildMoveChildren(String parentId, List<String> idList, Map<String, List<DataFillGroup>> parentToGro, Set<String> legalParentSet) {
        ArrayList<MoveTreeNodeVO> list = new ArrayList<MoveTreeNodeVO>();
        List<DataFillGroup> childGroups = parentToGro.get(parentId);
        if (CollectionUtils.isEmpty(childGroups)) {
            return null;
        }
        for (DataFillGroup dataFillGroup : childGroups) {
            if (!legalParentSet.contains(dataFillGroup.getId())) continue;
            MoveTreeNodeVO moveTreeNodeVO = new MoveTreeNodeVO();
            moveTreeNodeVO.setId(dataFillGroup.getId());
            moveTreeNodeVO.setTitle(dataFillGroup.getTitle());
            moveTreeNodeVO.setChildren(this.buildMoveChildren(dataFillGroup.getId(), idList, parentToGro, legalParentSet));
            if (idList.contains(dataFillGroup.getId())) continue;
            list.add(moveTreeNodeVO);
        }
        return list;
    }

    @PostMapping(value={"move/{parentId}"})
    @ApiOperation(value="\u4fee\u6539\u52fe\u9009\u9879\u7684\u7236\u7ea7")
    @Transactional
    @RequiresPermissions(value={"nr:datafill:manage"})
    public ReturnInfoVO move(@RequestBody List<TableItemVO> items, @PathVariable String parentId) {
        ReturnInfoVO returnInfoVO = new ReturnInfoVO();
        DataFillGroup group = this.dataFillGroupService.findById(parentId);
        if (group == null && !"00000000000000000000000000000000".equals(parentId)) {
            returnInfoVO.setMsg(DataFillErrorEnum.DG_ERROR_NOT_EXIST.getMessage());
            return returnInfoVO;
        }
        ArrayList<String> groupIds = new ArrayList<String>();
        ArrayList<String> definitionIds = new ArrayList<String>();
        this.separate(items, definitionIds, groupIds);
        this.dataFillGroupService.batchModifyParentId(groupIds, parentId);
        this.dataFillDefinitionService.batchModifyParentId(definitionIds, parentId);
        returnInfoVO.setSuccess(true);
        return returnInfoVO;
    }
}

