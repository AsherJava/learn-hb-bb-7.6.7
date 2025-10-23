/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 */
package com.jiuqi.nr.task.i18n.service;

import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.i18n.bean.I18nBaseObj;
import com.jiuqi.nr.task.i18n.bean.vo.I18nTreeLoactedVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nTreeSearchVO;
import java.util.List;

public interface I18nTreeService {
    public List<UITreeNode<TreeData>> formGroupTreeLoad(String var1);

    public List<UITreeNode<TreeData>> formTreeLoad(String var1);

    public List<I18nBaseObj> treeSearch(I18nTreeSearchVO var1);

    public List<UITreeNode<TreeData>> treeLocated(I18nTreeLoactedVO var1);
}

