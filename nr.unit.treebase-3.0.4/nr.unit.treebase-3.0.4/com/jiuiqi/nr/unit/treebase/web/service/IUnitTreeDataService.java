/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.web.service;

import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.List;
import java.util.Map;

public interface IUnitTreeDataService {
    public Map<String, Object> getStaticResource(UnitTreeContextData var1);

    public List<ITree<IBaseNodeData>> getTree(UnitTreeContextData var1);

    public List<ITree<IBaseNodeData>> getChildren(UnitTreeContextData var1);

    public Map<String, Integer> getNodeCountMap(UnitTreeContextData var1);

    public Map<String, Integer> getExpandNodeCountMap(UnitTreeContextData var1);
}

