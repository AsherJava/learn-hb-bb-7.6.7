/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 */
package com.jiuqi.nr.dataentry.asynctask;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;

public class TaskTreeObj
implements INode {
    private String key;
    private String code;
    private String title;
    private String order;
    private String parentId;
    private NodeType type;

    public TaskTreeObj(DesignTaskGroupDefine taskGroupDefine) {
        this.key = taskGroupDefine.getKey().toString();
        this.code = taskGroupDefine.getCode();
        this.title = taskGroupDefine.getTitle();
        this.order = taskGroupDefine.getOrder();
        this.parentId = taskGroupDefine.getParentKey();
        this.type = NodeType.TASKGROUP;
    }

    public TaskTreeObj(TaskGroupDefine taskGroupDefine) {
        this.key = taskGroupDefine.getKey().toString();
        this.code = taskGroupDefine.getCode();
        this.title = taskGroupDefine.getTitle();
        this.order = taskGroupDefine.getOrder();
        this.parentId = taskGroupDefine.getParentKey();
        this.type = NodeType.TASKGROUP;
    }

    public TaskTreeObj(TaskDefine taskDefine) {
        this.key = taskDefine.getKey().toString();
        this.code = taskDefine.getTaskCode();
        this.title = taskDefine.getTitle();
        this.order = taskDefine.getOrder();
        this.type = NodeType.TASKGROUP;
    }

    public NodeType getType() {
        return this.type;
    }

    public void setType(NodeType type) {
        this.type = type;
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

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public static enum NodeType {
        TASKGROUP,
        TASK;

    }
}

