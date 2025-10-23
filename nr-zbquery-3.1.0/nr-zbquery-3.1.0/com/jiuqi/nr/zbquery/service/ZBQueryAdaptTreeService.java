/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.ITree
 */
package com.jiuqi.nr.zbquery.service;

import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.zbquery.bean.impl.ResourceTableAdaptNode;
import com.jiuqi.nr.zbquery.rest.param.AdaptTreeRequestParam;
import com.jiuqi.nr.zbquery.rest.vo.ResourceTreeAdaptNodeVo;
import java.util.List;

public interface ZBQueryAdaptTreeService {
    public List<ITree<ResourceTreeAdaptNodeVo>> initTree(AdaptTreeRequestParam var1);

    public List<ITree<ResourceTreeAdaptNodeVo>> getChildren(AdaptTreeRequestParam var1);

    public List<ResourceTableAdaptNode> getResourceFields(AdaptTreeRequestParam var1);
}

