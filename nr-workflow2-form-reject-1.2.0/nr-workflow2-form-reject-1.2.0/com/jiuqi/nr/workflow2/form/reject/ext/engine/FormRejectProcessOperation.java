/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation
 */
package com.jiuqi.nr.workflow2.form.reject.ext.engine;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation;
import java.util.Calendar;

public class FormRejectProcessOperation
implements IProcessOperation {
    private String id;
    private String instanceId;
    private String fromNode;
    private String action;
    private String toNode;
    private String operator;
    private Calendar operateTime;
    private String comment;
    private String operateType;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstanceId() {
        return this.instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getFromNode() {
        return this.fromNode;
    }

    public void setFromNode(String fromNode) {
        this.fromNode = fromNode;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getToNode() {
        return this.toNode;
    }

    public void setToNode(String toNode) {
        this.toNode = toNode;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Calendar getOperateTime() {
        return this.operateTime;
    }

    public void setOperateTime(Calendar operateTime) {
        this.operateTime = operateTime;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOperateType() {
        return this.operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
}

