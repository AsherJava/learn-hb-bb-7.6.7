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
package com.jiuqi.nr.batch.gather.gzw.web.app.action;

import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.ActionItemImpl;
import com.jiuqi.nr.dataentry.gather.ITreeGathers;
import com.jiuqi.nr.dataentry.util.Consts;
import org.springframework.stereotype.Component;

@Component
public class DetailsQueryGZWMenuItemAction
implements ITreeGathers<ActionItem> {
    public Tree<ActionItem> gather() {
        Tree tree = new Tree();
        ActionItemImpl batch_gather_details_query = new ActionItemImpl("batch_gather_details_query", "\u663e\u793a\u6c47\u603b\u660e\u7ec6", "\u663e\u793a\u6c47\u603b\u660e\u7ec6", "#icon-_Wguoluchaxun1");
        tree.addChild((Object)batch_gather_details_query);
        return tree;
    }

    public Consts.GatherType getGatherType() {
        return Consts.GatherType.ACTION;
    }
}

