/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;

public class TaskTreeObj
implements INode {
    private String key;
    private String code;
    private String title;
    private String order;
    private String parentId;
    private NodeType type;
    private boolean canDesign;

    public TaskTreeObj(DesignTaskGroupDefine taskGroupDefine) {
        this.key = taskGroupDefine.getKey().toString();
        this.code = taskGroupDefine.getCode();
        this.title = taskGroupDefine.getTitle();
        this.order = taskGroupDefine.getOrder();
        this.parentId = taskGroupDefine.getParentKey();
        this.type = NodeType.TASKGROUP;
    }

    public TaskTreeObj(DesignTaskGroupDefine taskGroupDefine, boolean canDesign) {
        this.key = taskGroupDefine.getKey().toString();
        this.code = taskGroupDefine.getCode();
        this.title = taskGroupDefine.getTitle();
        this.order = taskGroupDefine.getOrder();
        this.parentId = taskGroupDefine.getParentKey();
        this.type = NodeType.TASKGROUP;
        this.canDesign = canDesign;
    }

    public boolean isCanDesign() {
        return this.canDesign;
    }

    public void setCanDesign(boolean canDesign) {
        this.canDesign = canDesign;
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
        TASKGROUP;

    }
}

