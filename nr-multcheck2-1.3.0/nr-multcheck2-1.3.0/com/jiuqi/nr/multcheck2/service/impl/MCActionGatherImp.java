/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.tree.Tree
 *  com.jiuqi.nr.dataentry.gather.ActionItem
 *  com.jiuqi.nr.dataentry.gather.ActionItemImpl
 *  com.jiuqi.nr.dataentry.gather.ActionType
 *  com.jiuqi.nr.dataentry.gather.ITreeGathers
 *  com.jiuqi.nr.dataentry.util.Consts$GatherType
 */
package com.jiuqi.nr.multcheck2.service.impl;

import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.ActionItemImpl;
import com.jiuqi.nr.dataentry.gather.ActionType;
import com.jiuqi.nr.dataentry.gather.ITreeGathers;
import com.jiuqi.nr.dataentry.util.Consts;
import org.springframework.stereotype.Component;

@Component
public class MCActionGatherImp
implements ITreeGathers<ActionItem> {
    public Tree<ActionItem> gather() {
        Tree tree = new Tree();
        ActionItemImpl group = new ActionItemImpl("multCheck2", "\u7efc\u5408\u5ba1\u68382.0", "\u7efc\u5408\u5ba1\u6838", "#icon-_GJZzidingyihuizong");
        group.setActionType(ActionType.GROUP);
        Tree groupTree = tree.addChild((Object)group);
        ActionItemImpl singleMultCheck2 = new ActionItemImpl("singleMultCheck2", "\u7efc\u5408\u5ba1\u68382.0", "\u5355\u5355\u4f4d\u7efc\u5408\u5ba1\u6838", "#icon-_GJZquanshen");
        singleMultCheck2.setParentCode(group.getCode());
        groupTree.addChild((Object)singleMultCheck2);
        ActionItemImpl batchMultCheck2 = new ActionItemImpl("batchMultCheck2", "\u6279\u91cf\u7efc\u5408\u5ba1\u68382.0", "\u6279\u91cf\u7efc\u5408\u5ba1\u6838", "#icon-_GJZpiliangshenhe");
        batchMultCheck2.setParentCode(group.getCode());
        groupTree.addChild((Object)batchMultCheck2);
        return tree;
    }

    public Consts.GatherType getGatherType() {
        return Consts.GatherType.ACTION;
    }
}

