/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 */
package com.jiuqi.nr.zbselector.utils;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.zbselector.bean.TaskNodeType;
import com.jiuqi.nr.zbselector.bean.TaskTreeNode;

public class ZBSelectorConvert {
    public static TaskTreeNode TGD2TTN(TaskGroupDefine taskGroupDefine) {
        TaskTreeNode taskTreeNode = new TaskTreeNode();
        taskTreeNode.setKey(taskGroupDefine.getKey());
        taskTreeNode.setCode(taskGroupDefine.getCode());
        taskTreeNode.setTitle(taskGroupDefine.getTitle());
        taskTreeNode.setParentKey(taskGroupDefine.getParentKey());
        taskTreeNode.setOrder(taskGroupDefine.getOrder());
        taskTreeNode.setType(TaskNodeType.TASKGROUP);
        return taskTreeNode;
    }

    public static TaskTreeNode TD2TTN(TaskDefine taskDefine, String parentKey) {
        TaskTreeNode taskTreeNode = new TaskTreeNode();
        taskTreeNode.setKey(taskDefine.getKey());
        taskTreeNode.setCode(taskDefine.getTaskCode());
        taskTreeNode.setTitle(taskDefine.getTitle());
        taskTreeNode.setParentKey(parentKey);
        taskTreeNode.setOrder(taskDefine.getOrder());
        taskTreeNode.setType(TaskNodeType.TASK);
        return taskTreeNode;
    }

    public static ITree<TaskTreeNode> TTN2IT(TaskTreeNode taskTreeNode) {
        ITree iTree = new ITree();
        iTree.setKey(taskTreeNode.getKey());
        iTree.setCode(taskTreeNode.getCode());
        iTree.setTitle(taskTreeNode.getTitle());
        iTree.setData((INode)taskTreeNode);
        if (taskTreeNode.getType() == TaskNodeType.TASK) {
            iTree.setIcons(new String[]{"#icon-_ZBGtubiao1"});
            iTree.setLeaf(true);
        } else {
            iTree.setIcons(new String[]{"#icon14_SHU_A_NW_tubiaozhongji"});
            iTree.setLeaf(false);
        }
        return iTree;
    }
}

