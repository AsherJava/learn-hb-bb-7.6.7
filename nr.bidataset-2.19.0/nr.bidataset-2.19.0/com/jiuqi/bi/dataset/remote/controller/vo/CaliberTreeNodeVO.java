/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.bi.dataset.remote.controller.vo;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.definition.facade.TaskDefine;

public class CaliberTreeNodeVO
implements INode {
    private String title;
    private String key;
    private String code;
    private String parent;

    public CaliberTreeNodeVO() {
    }

    public CaliberTreeNodeVO(TaskDefine taskDefine) {
        this.title = taskDefine.getTitle();
        this.key = taskDefine.getKey();
        this.code = taskDefine.getTaskCode();
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}

