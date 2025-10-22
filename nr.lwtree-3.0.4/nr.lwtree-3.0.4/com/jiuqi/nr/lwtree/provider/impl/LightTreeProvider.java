/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.lwtree.provider.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.lwtree.enumeration.ITreeShowPloy;
import com.jiuqi.nr.lwtree.provider.ITreeDecorator;
import com.jiuqi.nr.lwtree.provider.ITreeLoacteProvider;
import com.jiuqi.nr.lwtree.provider.ITreeProvider;
import com.jiuqi.nr.lwtree.provider.ITreeSearchProvider;
import com.jiuqi.nr.lwtree.provider.impl.LightTreeQueryer;
import com.jiuqi.nr.lwtree.provider.impl.LightTreeSearchProvider;
import com.jiuqi.nr.lwtree.query.IEntityRowQueryer;
import com.jiuqi.nr.lwtree.query.WithoutLeafNodeQuery;
import com.jiuqi.nr.lwtree.request.LightTreeLoadParam;
import com.jiuqi.nr.lwtree.response.INodeInfos;
import com.jiuqi.nr.lwtree.response.LightNodeData;
import com.jiuqi.nr.lwtree.response.LightTreeNodeInfo;
import java.util.ArrayList;
import java.util.List;

public class LightTreeProvider
extends ITreeDecorator<LightNodeData>
implements ITreeProvider<LightNodeData>,
ITreeSearchProvider<LightNodeData>,
ITreeLoacteProvider<LightNodeData> {
    protected LightTreeLoadParam ltParam;
    protected IEntityRowQueryer queryer;
    private boolean isCheckState;

    public LightTreeProvider(LightTreeLoadParam ltParam) {
        super(ltParam);
        this.ltParam = ltParam;
        this.initCheckState();
        this.initEntityQueryer();
    }

    private void initCheckState() {
        int showType = this.ltParam.getShowType();
        List<String> checkNodeKeys = this.ltParam.getCheckNodeKeys();
        this.isCheckState = showType == 1 && checkNodeKeys != null && !checkNodeKeys.isEmpty();
    }

    @Override
    public INodeInfos<LightNodeData> loadingTree() {
        if (this.isCheckState) {
            List<ITree<LightNodeData>> tree = this.getLocateTree(this.queryer, this.ltParam.getCheckNodeKeys(), null);
            return this.buildNodeInfo(tree);
        }
        String locatNode = this.ltParam.getLocateNode();
        if (StringUtils.isNotEmpty((String)locatNode)) {
            ArrayList<String> checklist = new ArrayList<String>();
            checklist.add(locatNode);
            List<ITree<LightNodeData>> tree = this.getLocateTree(this.queryer, checklist, locatNode);
            return this.buildNodeInfo(tree);
        }
        return this.getChildren(null);
    }

    @Override
    public INodeInfos<LightNodeData> getChildren() {
        String parentKey = this.ltParam.getParentKey();
        return this.getChildren(parentKey);
    }

    @Override
    public INodeInfos<LightNodeData> searchNode() {
        LightTreeSearchProvider searchProvider = new LightTreeSearchProvider(this.queryer, this.ltParam.getSearchParam());
        return searchProvider.searchNode();
    }

    @Override
    public INodeInfos<LightNodeData> locateTree() {
        String locatNode = this.ltParam.getLocateNode();
        List<String> checklist = this.ltParam.getCheckNodeKeys();
        if (StringUtils.isNotEmpty((String)locatNode) && !checklist.contains(locatNode)) {
            checklist.add(locatNode);
        }
        List<ITree<LightNodeData>> tree = this.getLocateTree(this.queryer, checklist, locatNode);
        return this.buildNodeInfo(tree);
    }

    @Override
    protected ITree<LightNodeData> buildTreeNode(IEntityRow row) {
        LightNodeData entObj = LightNodeData.buildEntityData(row);
        int childCount = this.queryer.getRowCounter().getChildCount(row.getEntityKeyData());
        entObj.setChildCount(childCount);
        ITree treeNode = new ITree((INode)entObj);
        treeNode.setLeaf(childCount <= 0);
        if (this.isCheckState) {
            List<String> checkNodeKeys = this.ltParam.getCheckNodeKeys();
            treeNode.setChecked(checkNodeKeys.contains(entObj.getKey()));
        }
        return treeNode;
    }

    private LightTreeNodeInfo<LightNodeData> getChildren(String parentKey) {
        ArrayList<ITree<LightNodeData>> children = new ArrayList<ITree<LightNodeData>>(0);
        List<IEntityRow> childRows = parentKey != null ? this.queryer.getChildRows(parentKey) : this.queryer.getRootRows();
        for (IEntityRow row : childRows) {
            ITree<LightNodeData> treeNode = this.buildTreeNode(row);
            children.add(treeNode);
        }
        return this.buildNodeInfo(children);
    }

    protected LightTreeNodeInfo<LightNodeData> buildNodeInfo(List<ITree<LightNodeData>> tree) {
        LightTreeNodeInfo<LightNodeData> nodesInfo = new LightTreeNodeInfo<LightNodeData>();
        nodesInfo.setNodes(tree);
        return nodesInfo;
    }

    private void initEntityQueryer() {
        int operation = this.ltParam.getShowPloy();
        ITreeShowPloy ploy = ITreeShowPloy.valueOf(operation);
        switch (ploy) {
            case WITH_OUT_LEAF_NODE: {
                this.queryer = this.getWithoutLeafNodeQuery();
                break;
            }
            default: {
                this.queryer = new LightTreeQueryer(this.loadInfo, this.ltParam);
            }
        }
    }

    @Override
    protected IEntityRowQueryer getWithoutLeafNodeQuery() {
        return new WithoutLeafNodeQuery(this.loadInfo);
    }
}

