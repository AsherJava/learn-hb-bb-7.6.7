/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.ITree
 */
package com.jiuqi.nr.zbquery.extend;

import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.zbquery.bean.impl.ResourceTableAdaptNode;
import com.jiuqi.nr.zbquery.bean.impl.ResourceTreeAdaptNode;
import com.jiuqi.nr.zbquery.rest.param.AdaptTreeParam;
import java.util.List;

public interface IZBQueryAdaptTreeProvider {
    public String getTreeType();

    public List<ITree<ResourceTreeAdaptNode>> getRoots(AdaptTreeParam var1);

    public List<ITree<ResourceTreeAdaptNode>> getChildren(AdaptTreeParam var1);

    public List<ResourceTableAdaptNode> getFields(AdaptTreeParam var1);
}

