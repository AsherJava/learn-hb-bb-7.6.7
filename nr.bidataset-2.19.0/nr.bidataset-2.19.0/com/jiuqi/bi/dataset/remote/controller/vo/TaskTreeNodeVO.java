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

public class TaskTreeNodeVO
implements INode {
    private String title;
    private String key;
    private String code;

    public TaskTreeNodeVO() {
    }

    public TaskTreeNodeVO(TaskDefine taskDefine) {
        this.title = taskDefine.getTitle();
        this.key = taskDefine.getKey();
        this.code = taskDefine.getTaskCode();
    }

    public String getKey() {
        return null;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return null;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return null;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

