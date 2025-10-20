/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.designer.bean;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.designer.bean.BaseDataImpl;
import java.util.List;
import java.util.Map;

public interface IBaseData
extends INode {
    public String getId();

    public String getCode();

    public String getTitle();

    public String getOrdinal();

    public String getParentid();

    public boolean isLeaf();

    public void setLeaf(boolean var1);

    public String[] getParents();

    public List<BaseDataImpl.InternalRefs> getRefs();

    public void setRefs(List<BaseDataImpl.InternalRefs> var1);

    public Map<String, Object> getValueMap();

    public Object getFieldValue(String var1);

    public void setFieldValue(String var1, Object var2);

    public void addIndex(String var1, String var2);

    public String getIndex(String var1);
}

