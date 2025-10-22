/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.itree.ITree$traverPloy
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.lwtree.provider;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.lwtree.para.ITreeLoadPara;
import com.jiuqi.nr.lwtree.para.ITreeParamsInitializer;
import com.jiuqi.nr.lwtree.query.IEntityRowQueryer;
import com.jiuqi.nr.lwtree.query.IEntityRowQueryerImpl;
import com.jiuqi.nr.lwtree.query.WithoutLeafNodeQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public abstract class ITreeDecorator<E extends INode> {
    protected ITreeParamsInitializer loadInfo;

    public ITreeDecorator(ITreeLoadPara loadPara) {
        this.loadInfo = new ITreeParamsInitializer(loadPara);
    }

    public List<ITree<E>> getLocateTree(IEntityRowQueryer queryer, List<String> checklist, String locatNode) {
        ITree<E> target;
        ArrayList<ITree<Object>> tree = new ArrayList<ITree<Object>>(0);
        List<ITree<E>> roots = this.reverseBuildRoots(queryer, checklist);
        if (locatNode != null && (target = this.findNodeIntree(roots, locatNode)) != null) {
            target.setSelected(true);
        }
        HashMap rootMap = new HashMap(0);
        roots.forEach(rt -> rootMap.put(rt.getKey(), rt));
        List<IEntityRow> rootRows = queryer.getRootRows();
        for (IEntityRow row : rootRows) {
            String keyData = row.getEntityKeyData();
            ITree findRoot = (ITree)rootMap.get(keyData);
            if (findRoot != null) {
                tree.add(findRoot);
                continue;
            }
            ITree<E> rt2 = this.buildTreeNode(row);
            tree.add(rt2);
        }
        return tree;
    }

    private List<ITree<E>> reverseBuildRoots(IEntityRowQueryer queryer, List<String> checklist) {
        ArrayList<ITree<ITree<E>>> roots = new ArrayList<ITree<ITree<E>>>();
        ITree<E> target = null;
        ITree<E> rootNode = null;
        for (int i = 0; i < checklist.size(); ++i) {
            IEntityRow row = queryer.findByEntityKey(checklist.get(i));
            if (row == null || (target = this.findNodeIntree(roots, row.getEntityKeyData())) != null || (rootNode = this.reverseNode2Root(queryer, row, roots, target = this.buildTreeNode(row))) == null) continue;
            roots.add(rootNode);
        }
        return roots;
    }

    private ITree<E> reverseNode2Root(IEntityRowQueryer queryer, IEntityRow row, List<ITree<E>> tree, ITree<E> target) {
        IEntityRow parentRow = queryer.findParentEntityRow(row);
        if (parentRow != null) {
            String parentKey = parentRow.getEntityKeyData();
            ITree<E> parent = this.findNodeIntree(tree, parentKey);
            if (parent == null) {
                parent = this.buildTreeNode(parentRow);
                parent.setExpanded(true);
                List<IEntityRow> childRows = queryer.getChildRows(parentKey);
                for (IEntityRow rw : childRows) {
                    if (rw.getEntityKeyData().equals(row.getEntityKeyData())) {
                        parent.appendChild(target);
                        continue;
                    }
                    ITree<E> child = this.buildTreeNode(rw);
                    parent.appendChild(child);
                }
                return this.reverseNode2Root(queryer, parentRow, tree, parent);
            }
            parent.setExpanded(true);
            List<IEntityRow> childRows = queryer.getChildRows(parentKey);
            for (IEntityRow rw : childRows) {
                if (rw.getEntityKeyData().equals(row.getEntityKeyData())) {
                    parent.appendChild(target);
                    continue;
                }
                ITree<E> child = this.buildTreeNode(rw);
                parent.appendChild(child);
            }
            return null;
        }
        return target;
    }

    private ITree<E> findNodeIntree(List<ITree<E>> tree, String entKey) {
        ITree target = null;
        block0: for (ITree<E> root : tree) {
            Iterator iterator = root.iterator(ITree.traverPloy.DEPTH_FIRST);
            while (iterator.hasNext()) {
                ITree next = (ITree)iterator.next();
                if (!entKey.equals(next.getKey())) continue;
                target = next;
                break block0;
            }
        }
        return target;
    }

    protected abstract ITree<E> buildTreeNode(IEntityRow var1);

    protected IEntityRowQueryer getDefaultQuery() {
        return new IEntityRowQueryerImpl(this.loadInfo);
    }

    protected IEntityRowQueryer getWithoutLeafNodeQuery() {
        return new WithoutLeafNodeQuery(this.loadInfo);
    }

    protected List<String> traverseNodeKeys(List<ITree<E>> tree) {
        ArrayList<String> entKeys = new ArrayList<String>(0);
        for (ITree<E> node : tree) {
            Iterator iterator = node.iterator(ITree.traverPloy.BREADTH_FIRST);
            while (iterator.hasNext()) {
                ITree next = (ITree)iterator.next();
                entKeys.add(next.getKey());
            }
        }
        return entKeys;
    }
}

