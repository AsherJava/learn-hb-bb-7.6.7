/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 */
package com.jiuqi.nr.form.selector.tree.impl;

import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.form.selector.tree.impl.ReportTreeNode;

public class ReportGroupNode
extends ReportTreeNode {
    public static ReportGroupNode assignGroupNode(FormGroupDefine group) {
        ReportGroupNode groupData = new ReportGroupNode();
        groupData.setKey(group.getKey());
        groupData.setCode(group.getCode());
        groupData.setTitle(group.getTitle());
        groupData.setType("group");
        return groupData;
    }
}

