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
package com.jiuqi.nr.midstore2.dataentry.service;

import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.ActionItemImpl;
import com.jiuqi.nr.dataentry.gather.ActionType;
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
        ActionItemImpl group = new ActionItemImpl("midstore2", "\u4e2d\u95f4\u5e93", "\u4e2d\u95f4\u5e93\u6570\u636e\u4ea4\u6362", "#icon-_GJZzidingyihuizong");
        group.setActionType(ActionType.GROUP);
        Tree groupTree = tree.addChild((Object)group);
        String desc = "//\u6a21\u5f0f1\u4ec5\u8bbe\u7f6e\u4e2d\u95f4\u5e93\u65b9\u6848\u6807\u8bc6\uff1b\u591a\u4e2a\u65b9\u6848\u82f1\u6587\u9017\u53f7,\u9694\u5f00\n//\u6a21\u5f0f2json\u683c\u5f0f(\u5982\u4e0b)\uff1a\u6309\u9700\u914d\u7f6e\uff0c\u4e0d\u914d\u7f6e\u8d70\u9ed8\u8ba4\u503c\uff0c\u6709\u5176\u4ed6\u60c5\u666f\u8bf7\u8054\u7cfb\n//***\u5173\u4e8e\u60c5\u666f\uff1a\u4e2d\u95f4\u5e93\u65b9\u6848\u8bbe\u7f6e\u542f\u7528\u300a\u591a\u60c5\u666f\u6570\u636e\u4ea4\u6362\u300b\u652f\u6301\u60c5\u666f\u503c\u591a\u9009***\n{\n \"midstoreScheme\":\"\",// \u4e2d\u95f4\u5e93\u65b9\u6848\uff08\u975e\u7a7a\uff09\uff1a\u591a\u4e2a\u65b9\u6848\u82f1\u6587\u9017\u53f7,\u9694\u5f00\n \"form\":\"all\",//\u62a5\u8868\uff1a\u9ed8\u8ba4all\uff08\u5168\u90e8\uff09\u3001curr\uff08\u5f53\u524d\u62a5\u8868\uff09\u3001custom\uff08\u81ea\u5b9a\u4e49\uff09\u3001\uff08\u81ea\u5b9a\u4e49\uff09\u591a\u4e2a\u62a5\u8868code\u82f1\u6587\u9017\u53f7,\u9694\u5f00\n \"MD_ORG\":\"all\",//\u5355\u4f4d\uff1a\u9ed8\u8ba4curr\uff08\u5f53\u524d\u5355\u4f4d\uff09\u3001all\uff08\u5168\u90e8\uff09\u3001custom\uff08\u81ea\u5b9a\u4e49\uff09\u3001\uff08\u81ea\u5b9a\u4e49\uff09\u591a\u4e2a\u5355\u4f4dcode\u82f1\u6587\u9017\u53f7,\u9694\u5f00\n \"MD_CURRENCY\":\"PROVIDER_BASECURRENCY\",//\u5e01\u79cd\uff1a\u9ed8\u8ba4PROVIDER_BASECURRENCY(\u672c\u4f4d\u5e01)\u3001all\uff08\u5168\u90e8\uff09\u3001curr\uff08\u5f53\u524d\u5e01\u79cd\uff09\uff0ccustom\uff08\u81ea\u5b9a\u4e49\uff09\u3001\uff08\u81ea\u5b9a\u4e49\uff09\u5e01\u79cdcode\n \"overImport\":\"false\"//\u8986\u76d6\u5bfc\u5165\uff1a\u9ed8\u8ba4false,\u300a\u63d0\u524d\u4e2d\u95f4\u5e93\u6570\u636e\u300b\u6309\u94ae\u751f\u6548\uff0c\u5982\u9700\u8986\u76d6\u5f53\u524d\u6570\u636e:\"true\"\n}";
        ActionItemImpl midStorePull = new ActionItemImpl("midstore2Pull", "\u63d0\u53d6\u4e2d\u95f4\u5e93\u6570\u636e", desc, "#icon-_GJZzidingyihuizong ");
        midStorePull.setParentCode(group.getCode());
        groupTree.addChild((Object)midStorePull);
        ActionItemImpl midStorePush = new ActionItemImpl("midstore2Push", "\u63a8\u6570\u5230\u4e2d\u95f4\u5e93", desc, "#icon-_GJZzidingyihuizong ");
        midStorePush.setParentCode(group.getCode());
        groupTree.addChild((Object)midStorePush);
        return tree;
    }
}

