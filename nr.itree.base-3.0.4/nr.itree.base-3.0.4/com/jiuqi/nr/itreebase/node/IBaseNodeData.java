/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.itreebase.node;

import com.jiuqi.nr.common.itree.INode;

public interface IBaseNodeData
extends INode {
    public static final String NAME_OF_KEY = "key";
    public static final String NAME_OF_CODE = "code";
    public static final String NAME_OF_TITLE = "title";
    public static final String NAME_OF_PATH = "path";

    public void setKey(String var1);

    public void setCode(String var1);

    public void setTitle(String var1);

    public String[] getPath();

    public boolean containsKey(String var1);

    public Object get(String var1);

    public Object put(String var1, Object var2);

    public Object remove(String var1);
}

