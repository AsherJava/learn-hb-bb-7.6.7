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
package com.jiuqi.gcreport.conversion.action;

import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.ActionItemImpl;
import com.jiuqi.nr.dataentry.gather.ITreeGathers;
import com.jiuqi.nr.dataentry.util.Consts;
import org.springframework.stereotype.Component;

@Component
public class GcConversionWorkPaperAction
implements ITreeGathers<ActionItem> {
    public Tree<ActionItem> gather() {
        Tree tree = new Tree();
        ActionItemImpl foreignCurrencyTranslationCheck = new ActionItemImpl("gcConversionWorkPaperAction", "\u5916\u5e01\u6298\u7b97\u5e95\u7a3f", "\u5916\u5e01\u6298\u7b97\u5e95\u7a3f", "#icon-_GJYlianggang ");
        tree.addChild((Object)foreignCurrencyTranslationCheck);
        ActionItemImpl conversionBatchCheck = new ActionItemImpl("gcConversionBatchAction", "\u5916\u5e01\u6279\u91cf\u7a3d\u6838", "\u5916\u5e01\u6279\u91cf\u7a3d\u6838", "#icon-_GJYlianggang ");
        tree.addChild((Object)conversionBatchCheck);
        return tree;
    }

    public Consts.GatherType getGatherType() {
        return Consts.GatherType.ACTION;
    }
}

