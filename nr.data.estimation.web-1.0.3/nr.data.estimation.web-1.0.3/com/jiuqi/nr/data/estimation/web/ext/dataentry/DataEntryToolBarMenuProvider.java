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
package com.jiuqi.nr.data.estimation.web.ext.dataentry;

import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.data.estimation.web.enumeration.DataEntryToolBarMenus;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.ActionItemImpl;
import com.jiuqi.nr.dataentry.gather.ActionType;
import com.jiuqi.nr.dataentry.gather.ITreeGathers;
import com.jiuqi.nr.dataentry.util.Consts;
import org.springframework.stereotype.Component;

@Component
public class DataEntryToolBarMenuProvider
implements ITreeGathers<ActionItem> {
    public Tree<ActionItem> gather() {
        Tree tree = new Tree();
        ActionItemImpl groupAction = new ActionItemImpl(DataEntryToolBarMenus.estimationGroupMenu.code, DataEntryToolBarMenus.estimationGroupMenu.title, DataEntryToolBarMenus.estimationGroupMenu.desc, DataEntryToolBarMenus.estimationGroupMenu.icon);
        groupAction.setActionType(DataEntryToolBarMenus.estimationGroupMenu.type);
        Tree groupNode = tree.addChild((Object)groupAction);
        for (DataEntryToolBarMenus menu : DataEntryToolBarMenus.values()) {
            if (menu.type != ActionType.BUTTON) continue;
            ActionItemImpl childTest = new ActionItemImpl(menu.code, menu.title, menu.desc, menu.icon);
            childTest.setParentCode(groupAction.getCode());
            groupNode.addChild((Object)childTest);
        }
        return tree;
    }

    public Consts.GatherType getGatherType() {
        return Consts.GatherType.ACTION;
    }
}

