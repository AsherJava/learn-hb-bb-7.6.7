/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.CompleteDependentType
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyDependencies
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.OperateBusinessKeyCollection
 */
package com.jiuqi.nr.workflow2.service.dimension;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.CompleteDependentType;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyDependencies;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.OperateBusinessKeyCollection;
import com.jiuqi.nr.workflow2.service.common.TreeNode;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateColumn;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BusinessKeyDependencies
extends OperateBusinessKeyCollection
implements IBusinessKeyDependencies {
    private final List<TreeNode<IBusinessObject, IBusinessObject>> tree;
    private CompleteDependentType completeDependentType = CompleteDependentType.NONE;

    public BusinessKeyDependencies(String taskKey, IEventOperateResult operateResultSet) {
        super(taskKey, operateResultSet.allCheckPassBusinessObjects());
        this.tree = this.initTreeData(operateResultSet);
        List filterEventOperateColumns = operateResultSet.allEventOperateColumns().stream().filter(c -> c.getCompleteDependentType() == CompleteDependentType.CHILD_EXECUTION_FIRST || c.getCompleteDependentType() == CompleteDependentType.PARENT_EXECUTION_FIRST).collect(Collectors.toList());
        this.completeDependentType = filterEventOperateColumns.isEmpty() ? CompleteDependentType.NONE : ((IEventOperateColumn)filterEventOperateColumns.get(filterEventOperateColumns.size() - 1)).getCompleteDependentType();
    }

    public BusinessKeyDependencies(String taskKey, List<IBusinessObject> businessObjects) {
        super(taskKey, businessObjects);
        this.tree = new ArrayList<TreeNode<IBusinessObject, IBusinessObject>>();
    }

    public IBusinessObjectCollection getDependencies(IBusinessObject businessObject) {
        if (CompleteDependentType.PARENT_EXECUTION_FIRST == this.completeDependentType) {
            ArrayList<IBusinessObject> path = new ArrayList<IBusinessObject>();
            for (TreeNode<IBusinessObject, IBusinessObject> treeNode : this.tree) {
                TreeNode<IBusinessObject, IBusinessObject> node = treeNode.findNode(businessObject);
                if (node == null) continue;
                BusinessKeyDependencies.findSuperiorsRecursively(node, path);
                return new OperateBusinessKeyCollection(this.taskKey, path);
            }
        }
        for (TreeNode<IBusinessObject, IBusinessObject> treeNode : this.tree) {
            TreeNode<IBusinessObject, IBusinessObject> node = treeNode.findNode(businessObject);
            if (node == null) continue;
            return new OperateBusinessKeyCollection(this.taskKey, node.getChildren().stream().map(TreeNode::getData).collect(Collectors.toList()));
        }
        return new OperateBusinessKeyCollection(this.taskKey, new ArrayList());
    }

    public static void findSuperiorsRecursively(TreeNode<IBusinessObject, IBusinessObject> node, List<IBusinessObject> path) {
        if (node == null) {
            return;
        }
        TreeNode<IBusinessObject, IBusinessObject> parent = node.getParent();
        if (parent != null) {
            path.add(0, parent.getData());
            BusinessKeyDependencies.findSuperiorsRecursively(parent, path);
        }
    }

    private List<TreeNode<IBusinessObject, IBusinessObject>> initTreeData(IEventOperateResult operateResultSet) {
        ArrayList<TreeNode<IBusinessObject, IBusinessObject>> tree = new ArrayList<TreeNode<IBusinessObject, IBusinessObject>>();
        HashMap<IBusinessObject, TreeNode<IBusinessObject, IBusinessObject>> nodeMap = new HashMap<IBusinessObject, TreeNode<IBusinessObject, IBusinessObject>>();
        for (IBusinessObject iBusinessObject : this.businessObjects) {
            nodeMap.put(iBusinessObject, new TreeNode<IBusinessObject, IBusinessObject>(iBusinessObject, iBusinessObject));
        }
        for (Map.Entry entry : nodeMap.entrySet()) {
            Integer parentRowIndex = operateResultSet.getParentRowIndex((IBusinessObject)entry.getKey());
            IBusinessObject parentObject = operateResultSet.findBusinessObject(parentRowIndex);
            if (parentObject == null) {
                tree.add((TreeNode<IBusinessObject, IBusinessObject>)entry.getValue());
                continue;
            }
            TreeNode parent = (TreeNode)nodeMap.get(parentObject);
            if (parent == null) continue;
            parent.appendChild((TreeNode)entry.getValue());
        }
        return tree;
    }
}

