/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.tree.Tree
 *  com.jiuqi.nr.dataentry.gather.ActionItem
 *  com.jiuqi.nr.dataentry.gather.ActionItemImpl
 *  com.jiuqi.nr.dataentry.gather.ITreeGathers
 *  com.jiuqi.nr.dataentry.util.Consts$GatherType
 */
package com.jiuqi.nr.zbquery.service.impl;

import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.ActionItemImpl;
import com.jiuqi.nr.dataentry.gather.ITreeGathers;
import com.jiuqi.nr.dataentry.util.Consts;
import org.springframework.stereotype.Component;

@Component
public class DataEntryActionsImpl
implements ITreeGathers<ActionItem> {
    public Consts.GatherType getGatherType() {
        return Consts.GatherType.ACTION;
    }

    public Tree<ActionItem> gather() {
        Tree tree = new Tree();
        ActionItemImpl peneQuery = new ActionItemImpl("query", "\u7a7f\u900f\u67e5\u8be2", "\u7a7f\u900f\u67e5\u8be2", "#icon-_Wguoluchaxun1");
        tree.addChild((Object)peneQuery);
        ActionItemImpl histQuery = new ActionItemImpl("historyQuery", "\u5386\u53f2\u6570\u636e\u67e5\u8be2", "\u5386\u53f2\u6570\u636e\u67e5\u8be2", "#icon-_Wguoluchaxun1");
        tree.addChild((Object)histQuery);
        ActionItemImpl integratedQuery = new ActionItemImpl("integratedQuery", "\u6570\u636e\u67e5\u8be2", "\u6570\u636e\u67e5\u8be2", "#icon-_Wguoluchaxun1");
        tree.addChild((Object)integratedQuery);
        return tree;
    }
}

