/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package com.jiuqi.nr.bpm.custom.common;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import com.jiuqi.nr.bpm.custom.serializer.WorkFlowInfoObjDeserializer;
import java.util.List;

@JsonDeserialize(using=WorkFlowInfoObjDeserializer.class)
public class WorkFlowInfoObj {
    private WorkFlowDefine define;
    private List<WorkFlowNodeSet> nodesets;
    private List<WorkFlowLine> lines;
    private List<WorkFlowAction> actions;
    private List<WorkFlowParticipant> particis;

    public WorkFlowInfoObj() {
    }

    public WorkFlowInfoObj(WorkFlowDefine define, List<WorkFlowNodeSet> nodesets, List<WorkFlowLine> lines, List<WorkFlowAction> actions, List<WorkFlowParticipant> particis) {
        this.define = define;
        this.nodesets = nodesets;
        this.actions = actions;
        this.lines = lines;
        this.particis = particis;
    }

    public WorkFlowDefine getDefine() {
        return this.define;
    }

    public void setDefine(WorkFlowDefine define) {
        this.define = define;
    }

    public List<WorkFlowNodeSet> getNodesets() {
        return this.nodesets;
    }

    public void setNodesets(List<WorkFlowNodeSet> nodesets) {
        this.nodesets = nodesets;
    }

    public List<WorkFlowLine> getLines() {
        return this.lines;
    }

    public void setLines(List<WorkFlowLine> lines) {
        this.lines = lines;
    }

    public List<WorkFlowAction> getActions() {
        return this.actions;
    }

    public void setActions(List<WorkFlowAction> actions) {
        this.actions = actions;
    }

    public List<WorkFlowParticipant> getParticis() {
        return this.particis;
    }

    public void setParticis(List<WorkFlowParticipant> particis) {
        this.particis = particis;
    }
}

