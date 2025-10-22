/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.calibre2.domain.CalibreGroupDTO
 *  com.jiuqi.nr.calibre2.vo.GroupNodeVO
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.bi.dataset.remote.controller;

import com.jiuqi.bi.dataset.remote.controller.vo.CaliberTreeNodeVO;
import com.jiuqi.bi.dataset.remote.controller.vo.TaskTreeNodeVO;
import com.jiuqi.bi.dataset.remote.service.CaliberTreeService;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.calibre2.domain.CalibreGroupDTO;
import com.jiuqi.nr.calibre2.vo.GroupNodeVO;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/calibersum/dataset"})
public class CaliberSumDataSetController {
    @Autowired
    IRunTimeViewController iRunTimeViewController;
    @Autowired
    CaliberTreeService caliberTreeService;
    public static final String TYPE = "CaliberSumDataSet";

    @PostMapping(value={"/initTaskTree"})
    public List<ITree<TaskTreeNodeVO>> initTaskTree(@RequestBody String taskId) {
        ArrayList<ITree<TaskTreeNodeVO>> nodeList = new ArrayList<ITree<TaskTreeNodeVO>>();
        List taskDefines = this.iRunTimeViewController.getAllTaskDefinesByType(TaskType.TASK_TYPE_DEFAULT);
        if (taskDefines != null) {
            for (TaskDefine taskDefine : taskDefines) {
                TaskTreeNodeVO taskTreeNodeVO = new TaskTreeNodeVO(taskDefine);
                ITree treeNode = new ITree((INode)taskTreeNodeVO);
                treeNode.setTitle(taskDefine.getTitle());
                treeNode.setKey(taskDefine.getKey());
                treeNode.setCode(taskDefine.getTaskCode());
                treeNode.setLeaf(true);
                treeNode.setSelected(StringUtils.isNotEmpty((String)taskId) && taskId.equals(taskDefine.getKey()));
                treeNode.setChildren(null);
                nodeList.add((ITree<TaskTreeNodeVO>)treeNode);
            }
        }
        return nodeList;
    }

    @GetMapping(value={"/initCaliberTree"})
    public List<ITree<GroupNodeVO>> initCaliberTree() {
        return this.caliberTreeService.initTree();
    }

    @PostMapping(value={"/caliberTreeLoad"})
    public List<ITree<GroupNodeVO>> caliberTreeLoad(@RequestBody CaliberTreeNodeVO caliberTreeNodeVO) {
        CalibreGroupDTO groupTreeParam = new CalibreGroupDTO();
        groupTreeParam.setKey(caliberTreeNodeVO.getKey());
        return this.caliberTreeService.getChildrenNodes(groupTreeParam);
    }

    @PostMapping(value={"/locationCaliberTree"})
    public List<ITree<GroupNodeVO>> locationCaliberTree(@RequestBody String caliberId) {
        return this.caliberTreeService.locationTreeNode(caliberId);
    }
}

