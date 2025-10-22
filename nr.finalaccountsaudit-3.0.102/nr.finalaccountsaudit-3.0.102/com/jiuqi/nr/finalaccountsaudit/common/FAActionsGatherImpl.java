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
package com.jiuqi.nr.finalaccountsaudit.common;

import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.ActionItemImpl;
import com.jiuqi.nr.dataentry.gather.ITreeGathers;
import com.jiuqi.nr.dataentry.util.Consts;
import org.springframework.stereotype.Component;

@Component(value="finalaccountsaudit_acions")
public class FAActionsGatherImpl
implements ITreeGathers<ActionItem> {
    public Tree<ActionItem> gather() {
        Tree tree = new Tree();
        ActionItemImpl enumDataCheck = new ActionItemImpl("enumDataCheck", "\u679a\u4e3e\u5b57\u5178\u68c0\u67e5", "\u679a\u4e3e\u5b57\u5178\u68c0\u67e5", "#icon-_GJZquanshen ");
        tree.addChild((Object)enumDataCheck);
        ActionItemImpl multCheck = new ActionItemImpl("multCheck", "\u7efc\u5408\u5ba1\u6838", "\u7efc\u5408\u5ba1\u6838", "#icon-_GJZquanshen ");
        tree.addChild((Object)multCheck);
        ActionItemImpl integrityCheck = new ActionItemImpl("integrityCheck", "\u8868\u5b8c\u6574\u6027\u68c0\u67e5", "\u8868\u5b8c\u6574\u6027\u68c0\u67e5", "#icon-_GJZquanshen ");
        tree.addChild((Object)integrityCheck);
        ActionItemImpl oneKeyCheck = new ActionItemImpl("oneKeyCheck", "\u4e00\u952e\u5ba1\u6838", "\u4e00\u952e\u5ba1\u6838", "#icon-_GJZquanshen ");
        tree.addChild((Object)oneKeyCheck);
        ActionItemImpl errorDescCheck = new ActionItemImpl("errorDescCheck", "\u51fa\u9519\u8bf4\u660e\u68c0\u67e5", "\u51fa\u9519\u8bf4\u660e\u68c0\u67e5", "#icon-_GJZquanshen ");
        tree.addChild((Object)errorDescCheck);
        ActionItemImpl attachmentCheck = new ActionItemImpl("attachmentCheck", "\u9644\u4ef6\u6587\u6863\u68c0\u67e5", "\u9644\u4ef6\u6587\u6863\u68c0\u67e5", "#icon-_GJZquanshen ");
        tree.addChild((Object)attachmentCheck);
        return tree;
    }

    public Consts.GatherType getGatherType() {
        return Consts.GatherType.ACTION;
    }
}

