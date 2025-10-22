/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.process;

import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivitModel {
    private List<TaskObject> task = new ArrayList<TaskObject>();
    private String instantId;
    private Date createDate;

    public List<TaskObject> getTask() {
        return this.task;
    }

    public void setTask(List<TaskObject> task) {
        this.task = task;
    }

    public String getInstantId() {
        return this.instantId;
    }

    public void setInstantId(String instantId) {
        this.instantId = instantId;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public static class TaskObject {
        private WorkFlowNodeSet taskNode;
        private List<WorkFlowLine> outgoingLines;
        private List<WorkFlowLine> incomingLines;
        private List<WorkFlowParticipant> participants;
        private List<WorkFlowAction> actions;

        public WorkFlowNodeSet getTaskNode() {
            return this.taskNode;
        }

        public void setTaskNode(WorkFlowNodeSet taskNode) {
            this.taskNode = taskNode;
        }

        public List<WorkFlowLine> getOutgoingLines() {
            return this.outgoingLines;
        }

        public void setOutgoingLines(List<WorkFlowLine> outgoingLines) {
            this.outgoingLines = outgoingLines;
        }

        public List<WorkFlowLine> getIncomingLines() {
            return this.incomingLines;
        }

        public void setIncomingLines(List<WorkFlowLine> incomingLines) {
            this.incomingLines = incomingLines;
        }

        public List<WorkFlowParticipant> getParticipants() {
            return this.participants;
        }

        public void setParticipants(List<WorkFlowParticipant> participants) {
            this.participants = participants;
        }

        public List<WorkFlowAction> getActions() {
            return this.actions;
        }

        public void setActions(List<WorkFlowAction> actions) {
            this.actions = actions;
        }
    }
}

