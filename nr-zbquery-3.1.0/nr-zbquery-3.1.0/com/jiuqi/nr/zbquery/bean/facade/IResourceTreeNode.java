/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataresource.DataResourceNode
 *  com.jiuqi.nr.datascheme.api.core.INode
 */
package com.jiuqi.nr.zbquery.bean.facade;

import com.jiuqi.nr.dataresource.DataResourceNode;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.zbquery.model.QueryObject;

public interface IResourceTreeNode
extends INode,
Comparable<IResourceTreeNode> {
    public int getType();

    public String getParentKey();

    public String getOrder();

    public String getDimKey();

    public QueryObject getQueryObject();

    public String getDefineKey();

    public boolean isIndeterminate();

    public String getDataTableKey();

    public String getSource();

    public String getDw();

    public String getDataSchemeKey();

    public boolean isDisplayTiered();

    public String getLinkZb();

    public DataResourceNode change2DataResourceNode();
}

