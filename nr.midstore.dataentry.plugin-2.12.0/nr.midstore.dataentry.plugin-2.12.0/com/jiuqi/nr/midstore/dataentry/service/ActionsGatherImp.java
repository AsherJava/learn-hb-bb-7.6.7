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
package com.jiuqi.nr.midstore.dataentry.service;

import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.ActionItemImpl;
import com.jiuqi.nr.dataentry.gather.ITreeGathers;
import com.jiuqi.nr.dataentry.util.Consts;
import org.springframework.stereotype.Component;

@Component
public class ActionsGatherImp
implements ITreeGathers<ActionItem> {
    public Consts.GatherType getGatherType() {
        return Consts.GatherType.ACTION;
    }

    public Tree<ActionItem> gather() {
        Tree tree = new Tree();
        ActionItemImpl midStorePull = new ActionItemImpl("midstorePull", "\u4e2d\u95f4\u5e93\u53d6\u6570", "\u63d0\u53d6\u4e2d\u95f4\u5e93\u6570\u636e", "#icon-_GJZzidingyihuizong ");
        tree.addChild((Object)midStorePull);
        return tree;
    }
}

