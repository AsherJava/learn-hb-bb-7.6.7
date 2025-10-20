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
package com.jiuqi.gcreport.intermediatelibrary.job;

import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.ActionItemImpl;
import com.jiuqi.nr.dataentry.gather.ActionType;
import com.jiuqi.nr.dataentry.gather.ITreeGathers;
import com.jiuqi.nr.dataentry.util.Consts;
import org.springframework.stereotype.Component;

@Component
public class IntermediateLibraryActionItem
implements ITreeGathers<ActionItem> {
    public Tree<ActionItem> gather() {
        Tree tree = new Tree();
        ActionItemImpl intermediateLibrary = new ActionItemImpl("intermediateLibrary", "\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3", "\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u6570\u636e\u63d0\u53d6\u548c\u63a8\u9001", "#icon-_GJTdaoru");
        intermediateLibrary.setActionType(ActionType.GROUP);
        Tree checkTree = tree.addChild((Object)intermediateLibrary);
        ActionItemImpl intermediateLibraryExtract = new ActionItemImpl("intermediateLibraryExtract", "\u4ea4\u6362\u6570\u636e\u63d0\u53d6", "\u4ea4\u6362\u6570\u636e\u63d0\u53d6", "#icon-_GJTdaoru");
        intermediateLibraryExtract.setParentCode(intermediateLibrary.getCode());
        checkTree.addChild((Object)intermediateLibraryExtract);
        ActionItemImpl intermediateLibraryPush = new ActionItemImpl("intermediateLibraryPush", "\u4ea4\u6362\u6570\u636e\u63a8\u9001", "\u4ea4\u6362\u6570\u636e\u63a8\u9001", "#icon-_GJTdaochu");
        intermediateLibraryPush.setParentCode(intermediateLibrary.getCode());
        checkTree.addChild((Object)intermediateLibraryPush);
        return tree;
    }

    public Consts.GatherType getGatherType() {
        return Consts.GatherType.ACTION;
    }
}

