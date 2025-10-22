/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.util.StringHelper
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.dafafill.dao.DataFillDataDao;
import com.jiuqi.nr.dafafill.dao.DataFillDefinitionDao;
import com.jiuqi.nr.dafafill.entity.DataFillDefinition;
import com.jiuqi.nr.dafafill.service.IDataFillDefinitionService;
import com.jiuqi.nr.dafafill.web.vo.TaskTreeNodeVO;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.util.StringHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DataFillDefinitionServiceImpl
implements IDataFillDefinitionService {
    @Autowired
    private DataFillDefinitionDao dataFillDefinitionDao;
    @Autowired
    private DataFillDataDao dataFillDataDao;
    @Autowired
    private RunTimeAuthViewController runTimeAuthView;

    @Override
    public void add(DataFillDefinition definition) throws JQException {
        this.dataFillDefinitionDao.add(definition);
    }

    @Override
    public void modify(DataFillDefinition definition) throws JQException {
        this.dataFillDefinitionDao.modify(definition);
    }

    @Override
    public void delete(String id) throws JQException {
        this.dataFillDefinitionDao.deleteById(id);
        this.dataFillDataDao.deleteByDefinitionId(id);
    }

    @Override
    public void batchDelete(List<String> ids) throws JQException {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        this.dataFillDefinitionDao.batchDelete(ids);
        this.dataFillDataDao.batchDeleteByDefinitionIds(ids);
    }

    @Override
    public void deleteByParentId(String pid) throws JQException {
    }

    @Override
    public DataFillDefinition findById(String id) {
        return this.dataFillDefinitionDao.findById(id);
    }

    @Override
    public DataFillDefinition findByCode(String code) {
        return this.dataFillDefinitionDao.findByCode(code);
    }

    @Override
    public List<DataFillDefinition> findByParentId(String pid) {
        return this.dataFillDefinitionDao.findByParentId(pid);
    }

    @Override
    public List<DataFillDefinition> fuzzySearch(String fuzzyKey) {
        ArrayList<DataFillDefinition> res = new ArrayList<DataFillDefinition>();
        res.addAll(this.dataFillDefinitionDao.fuzzyTitle(fuzzyKey));
        res.addAll(this.dataFillDefinitionDao.fuzzyCode(fuzzyKey.toUpperCase()));
        return res;
    }

    @Override
    public List<DataFillDefinition> findAllDefinitions() {
        return this.dataFillDefinitionDao.findAll();
    }

    @Override
    public Map<String, List<DataFillDefinition>> findAllByParent() {
        HashMap<String, List<DataFillDefinition>> res = new HashMap<String, List<DataFillDefinition>>();
        List<DataFillDefinition> allDef = this.dataFillDefinitionDao.findAll();
        for (DataFillDefinition def : allDef) {
            ArrayList<DataFillDefinition> childs = (ArrayList<DataFillDefinition>)res.get(def.getParentId());
            if (childs == null) {
                childs = new ArrayList<DataFillDefinition>();
                res.put(def.getParentId(), childs);
            }
            childs.add(def);
        }
        return res;
    }

    @Override
    public void batchModifyParentId(List<String> ids, String parentId) {
        this.dataFillDefinitionDao.batchModifyParentId(ids, parentId);
    }

    @Override
    public ITree<TaskTreeNodeVO> buildTaskTree(String taskKey) {
        TaskTreeNodeVO root = new TaskTreeNodeVO();
        root.setKey("00000000000000000000000000000000");
        root.setCode("00000000000000000000000000000000");
        root.setTitle("\u5168\u90e8\u4efb\u52a1");
        root.setType("GROUP");
        ITree node = new ITree((INode)root);
        node.setExpanded(true);
        node.setLeaf(false);
        ArrayList<ITree<TaskTreeNodeVO>> children = new ArrayList<ITree<TaskTreeNodeVO>>();
        this.buildChildren(children, taskKey);
        node.setChildren(children);
        return node;
    }

    private void buildChildren(List<ITree<TaskTreeNodeVO>> children, String taskKey) {
        ArrayList<TaskGroupDefine> allTaskGroup = new ArrayList<TaskGroupDefine>();
        List allTaskDefines = this.runTimeAuthView.getAllTaskDefines();
        HashSet<String> groupSet = new HashSet<String>();
        for (TaskDefine taskDefine : allTaskDefines) {
            List groups = this.runTimeAuthView.getGroupByTask(taskDefine.getKey());
            for (TaskGroupDefine group : groups) {
                if (!groupSet.add(group.getKey())) continue;
                allTaskGroup.add(group);
            }
        }
        Map<String, TaskDefine> allTaskMap = allTaskDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, task -> task));
        HashSet<String> tasksHasGroup = new HashSet<String>();
        this.buildChildTree(null, allTaskGroup, allTaskMap, children, tasksHasGroup, taskKey);
        for (TaskDefine task2 : allTaskDefines) {
            if (tasksHasGroup.contains(task2.getKey())) continue;
            children.add(this.convertTaskTreeNode(task2, taskKey));
        }
    }

    private void buildChildTree(String parentId, List<TaskGroupDefine> allTaskGroup, Map<String, TaskDefine> allTaskMap, List<ITree<TaskTreeNodeVO>> children, Set<String> tasksHasGroup, String taskKey) {
        List taskDefines;
        for (TaskGroupDefine group : allTaskGroup) {
            if (!StringHelper.safeEquals((String)parentId, (String)group.getParentKey())) continue;
            ITree<TaskTreeNodeVO> node = this.convertGroupTreeNode(group);
            children.add(node);
            ArrayList<ITree<TaskTreeNodeVO>> nodeChildren = new ArrayList<ITree<TaskTreeNodeVO>>();
            this.buildChildTree(group.getKey(), allTaskGroup, allTaskMap, nodeChildren, tasksHasGroup, taskKey);
            node.setChildren(nodeChildren);
        }
        boolean isRoot = !StringUtils.hasText(parentId);
        List taskList = null;
        if (!isRoot && !CollectionUtils.isEmpty(taskDefines = this.runTimeAuthView.getTaskDefinesFromGroup(parentId))) {
            taskList = taskDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(taskList)) {
            tasksHasGroup.addAll(taskList);
            for (String key : taskList) {
                TaskDefine task = allTaskMap.get(key);
                if (task == null) continue;
                children.add(this.convertTaskTreeNode(task, taskKey));
            }
        }
    }

    private ITree<TaskTreeNodeVO> convertTaskTreeNode(TaskDefine task, String taskKey) {
        TaskTreeNodeVO r = new TaskTreeNodeVO();
        r.setKey(task.getKey());
        r.setCode(task.getTaskCode());
        r.setTitle(task.getTitle());
        r.setType("TASK");
        r.setDataScheme(task.getDataScheme());
        ITree node = new ITree((INode)r);
        node.setIcons(new String[]{"#icon-16_SHU_A_NR_shujufangan"});
        node.setLeaf(true);
        node.setSelected(task.getKey().equals(taskKey));
        return node;
    }

    private ITree<TaskTreeNodeVO> convertGroupTreeNode(TaskGroupDefine group) {
        TaskTreeNodeVO r = new TaskTreeNodeVO();
        r.setKey(group.getKey());
        r.setCode(group.getCode());
        r.setTitle(group.getTitle());
        r.setType("GROUP");
        ITree node = new ITree((INode)r);
        node.setIcons(new String[]{"#icon-16_SHU_A_NR_fenzu"});
        node.setLeaf(true);
        return node;
    }
}

