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
package com.jiuqi.gcreport.bde.fetch.impl.action;

import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.ActionItemImpl;
import com.jiuqi.nr.dataentry.gather.ActionType;
import com.jiuqi.nr.dataentry.gather.ITreeGathers;
import com.jiuqi.nr.dataentry.util.Consts;
import org.springframework.stereotype.Component;

@Component
public class BdeDataEntryActionsImpl
implements ITreeGathers<ActionItem> {
    public Consts.GatherType getGatherType() {
        return Consts.GatherType.ACTION;
    }

    public Tree<ActionItem> gather() {
        Tree tree = new Tree();
        ActionItemImpl bdeFetchData = new ActionItemImpl("gcBDEFetchData", "BDE\u53d6\u6570", "BDE\u53d6\u6570", "#icon-16_GJ_A_GC_zidongduizhang");
        bdeFetchData.setActionType(ActionType.GROUP);
        Tree bdeFetchTree = tree.addChild((Object)bdeFetchData);
        ActionItemImpl batchBDEFetch = new ActionItemImpl("gcBatchBDEFetchData", "BDE\u6279\u91cf\u53d6\u6570", "BDE\u6279\u91cf\u53d6\u6570", "#icon-16_GJ_A_GC_zidongduizhang");
        batchBDEFetch.setParentCode(bdeFetchData.getCode());
        bdeFetchTree.addChild((Object)batchBDEFetch);
        tree.addChild((Object)new ActionItemImpl("gcBDEPenetration", "BDE\u7a7f\u900f\u67e5\u8be2", "", "#icon-16_GJ_A_GC_zidongduizhang"));
        return tree;
    }
}

