/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.form.selector.tree;

import com.jiuqi.nr.common.itree.INode;

public interface IReportTreeNode
extends INode {
    public static final String TYPE_OF_FORM = "form";
    public static final String TYPE_OF_GROUP = "group";
    public static final String TYPE_OF_SCHEME = "scheme";
    public static final String TYPE_OF_TOTAL_FORM = "form_with_all";
    public static final String TYPE_OF_BETWEEN_FORM = "form_with_between";

    public String getType();
}

