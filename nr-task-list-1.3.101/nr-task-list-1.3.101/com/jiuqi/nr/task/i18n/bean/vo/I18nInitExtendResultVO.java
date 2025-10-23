/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 */
package com.jiuqi.nr.task.i18n.bean.vo;

import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.i18n.bean.I18nBaseObj;
import java.util.List;

public class I18nInitExtendResultVO {
    private List<I18nBaseObj> resultList;
    private List<UITreeNode<TreeData>> treeDatas;

    public I18nInitExtendResultVO() {
    }

    public I18nInitExtendResultVO(List<I18nBaseObj> resultList) {
        this.resultList = resultList;
    }

    public I18nInitExtendResultVO(List<I18nBaseObj> resultList, List<UITreeNode<TreeData>> treeDatas) {
        this.resultList = resultList;
        this.treeDatas = treeDatas;
    }

    public List<I18nBaseObj> getResultList() {
        return this.resultList;
    }

    public void setResultList(List<I18nBaseObj> resultList) {
        this.resultList = resultList;
    }

    public List<UITreeNode<TreeData>> getTreeDatas() {
        return this.treeDatas;
    }

    public void setTreeDatas(List<UITreeNode<TreeData>> treeDatas) {
        this.treeDatas = treeDatas;
    }
}

