/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.util.tree.Tree
 *  com.jiuqi.nr.dataentry.gather.ActionItem
 *  com.jiuqi.nr.dataentry.gather.ActionItemImpl
 *  com.jiuqi.nr.dataentry.gather.ActionType
 *  com.jiuqi.nr.dataentry.gather.ITreeGathers
 *  com.jiuqi.nr.dataentry.util.Consts$GatherType
 */
package com.jiuqi.gcreport.common.action;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.ActionItemImpl;
import com.jiuqi.nr.dataentry.gather.ActionType;
import com.jiuqi.nr.dataentry.gather.ITreeGathers;
import com.jiuqi.nr.dataentry.util.Consts;
import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public final class GcActionGather
implements ITreeGathers<ActionItem> {
    public Tree<ActionItem> gather() {
        Collection abstractGcActionItems = SpringContextUtils.getBeans(AbstractGcActionItem.class);
        Tree tree = new Tree();
        abstractGcActionItems.forEach(arg_0 -> ((Tree)tree).addChild(arg_0));
        tree.addChild((Object)new ActionItemImpl("gcEfdcDataCheckAction", "\u6570\u636e\u7a3d\u6838", "\u6570\u636e\u7a3d\u6838", "#icon-_GJZshenhe "));
        ActionItemImpl check = new ActionItemImpl("gcDifferenceProcess", "\u5dee\u989d\u5904\u7406", "\u96c6\u56e2\u5185\u5dee\u989d\u8f6c\u96c6\u56e2\u5916\u6d6e\u52a8\u884c", "#icon-_GJZshengchengtubiao ");
        check.setActionType(ActionType.GROUP);
        Tree checkTree = tree.addChild((Object)check);
        ActionItemImpl batchCheck = new ActionItemImpl("gcAllDifferenceProcess", "\u5168\u90e8\u5dee\u989d\u5904\u7406", "\u6240\u6709\u96c6\u56e2\u5185\u5dee\u989d\u8f6c\u96c6\u56e2\u5916\u6d6e\u52a8\u884c", "#icon-_GJZtubiaopeizhi ");
        checkTree.addChild((Object)batchCheck);
        ActionItemImpl wsClientAction = new ActionItemImpl("webserviceClientAction", "WS\u5ba2\u6237\u7aef", "WS\u5ba2\u6237\u7aef", "#icon-_GJYlianggang ");
        tree.addChild((Object)wsClientAction);
        ActionItemImpl dataIntegrationAction = new ActionItemImpl("dataIntegrationAction", "\u6570\u636e\u96c6\u6210\u5ba2\u6237\u7aef", "\u6570\u636e\u96c6\u6210\u5ba2\u6237\u7aef", "#icon-_GJYlianggang ");
        tree.addChild((Object)dataIntegrationAction);
        return tree;
    }

    public Consts.GatherType getGatherType() {
        return Consts.GatherType.ACTION;
    }

    public AbstractGcActionItem findActionByName(String actionCode) {
        Collection abstractGcActionItems = SpringContextUtils.getBeans(AbstractGcActionItem.class);
        Optional<AbstractGcActionItem> abstractGcActionItem = abstractGcActionItems.stream().filter(item -> item.getCode().equals(actionCode)).findFirst();
        return abstractGcActionItem.isPresent() ? abstractGcActionItem.get() : null;
    }
}

