/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.tree.Tree
 */
package com.jiuqi.nr.query.gather.impl;

import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.query.common.QueryConst;
import com.jiuqi.nr.query.gather.ActionItem;
import com.jiuqi.nr.query.gather.ActionType;
import com.jiuqi.nr.query.gather.ITreeGathers;
import com.jiuqi.nr.query.gather.KeyStore;
import org.springframework.stereotype.Component;

@Component(value="query_actions")
public class ActionsGatherImpl
implements ITreeGathers<ActionItem> {
    @Override
    public Tree<ActionItem> gather() {
        Tree tree = new Tree();
        ActionItem separator = new ActionItem("separator", "\u5206\u5272\u7ebf", "\u5206\u5272\u7ebf", "#icon-_Ttianjia1");
        separator.setActionType(ActionType.SEPARATOR);
        tree.addChild((Object)separator);
        ActionItem newAction = new ActionItem("new", "\u65b0\u5efa", "\u65b0\u5efa", " iconfont icon-_Ttianjia1 ");
        tree.addChild((Object)newAction);
        ActionItem delete = new ActionItem("delete", "\u5220\u9664", "\u5220\u9664", "#icon-_Tshanjian1");
        tree.addChild((Object)delete);
        ActionItem update = new ActionItem("update", "\u4fee\u6539", "\u4fee\u6539", "#icon-_Tshanjian1");
        tree.addChild((Object)update);
        ActionItem save = new ActionItem("save", "\u4fdd\u5b58", "\u4fdd\u5b58", "#icon-_GJTbaocun ");
        KeyStore saveAccel = new KeyStore(83, 2);
        save.setAccelerator(saveAccel);
        tree.addChild((Object)save);
        return tree;
    }

    @Override
    public QueryConst.GatherType getGatherType() {
        return QueryConst.GatherType.ACTION;
    }
}

