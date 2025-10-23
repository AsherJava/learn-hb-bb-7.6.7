/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.ITree
 */
package com.jiuqi.nr.zbquery.service;

import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.zbquery.bean.facade.IResourceTreeNode;
import com.jiuqi.nr.zbquery.bean.impl.SearchTreeNodeDTO;
import com.jiuqi.nr.zbquery.bean.impl.SelectedFieldDefineEx;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.rest.param.ResourceTreeQueryParam;
import com.jiuqi.nr.zbquery.rest.param.SearchNodeQueryParam;
import java.util.List;
import java.util.Map;

public interface ZBQueryResourceTreeService<E extends IResourceTreeNode> {
    public List<ITree<E>> queryResourceTreePath(E var1);

    public List<ITree<E>> queryResourceTreeChildren(E var1);

    public List<ITree<E>> queryAllResourceTreeChildren(E var1);

    public List<SearchTreeNodeDTO> filterResourceTree(SearchNodeQueryParam var1);

    public ITree<E> queryDimensionAttributeField(QueryObject var1);

    public List<ITree<E>> queryDimsByFieldNode(ResourceTreeQueryParam var1);

    public List<String> queryTreeNodePath(QueryObject var1);

    public Map<String, List<QueryObject>> handleSceneGrouping(ZBQueryModel var1);

    public List<E> handleFieldSelect(List<SelectedFieldDefineEx> var1, Map<String, String> var2);
}

