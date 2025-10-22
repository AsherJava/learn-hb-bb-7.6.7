/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package com.jiuqi.nr.bpm.custom.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import com.jiuqi.nr.bpm.custom.serializer.WorkFlowDefineSaveDeserializer;
import java.util.List;

@JsonDeserialize(using=WorkFlowDefineSaveDeserializer.class)
public class WorkFlowDefineSaveEntity {
    public static final String CREAT_NODE_LIST = "creat_nodes";
    public static final String UPDATE_NODE_LIST = "update_nodes";
    public static final String DEL_NODE_LIST = "del_nodes";
    public static final String CREAT_LINE_LIST = "creat_lines";
    public static final String UPDATE_LINE_LIST = "update_lines";
    public static final String DEL_LINE_LIST = "del_lines";
    public static final String CREAT_ACTION_LIST = "creat_actions";
    public static final String UPDATE_ACTION_LIST = "update_actions";
    public static final String DEL_ACTION_LIST = "del_actions";
    public static final String CREAT_PARTICIPANT_LIST = "creat_participants";
    public static final String UPDATE_PARTICIPANT_LIST = "update_participants";
    public static final String DEL_PARTICIPANT_LIST = "del_participants";
    private WorkFlowDefine define;
    private List<WorkFlowNodeSet> creat_nodes;
    private List<WorkFlowNodeSet> update_nodes;
    private List<String> del_nodes;
    private List<WorkFlowLine> creat_lines;
    private List<WorkFlowLine> update_lines;
    private List<String> del_lines;
    private List<WorkFlowAction> creat_actions;
    private List<WorkFlowAction> update_actions;
    private List<String> del_actions;
    private List<WorkFlowParticipant> creat_participants;
    private List<WorkFlowParticipant> update_participants;
    private List<String> del_participants;

    public WorkFlowDefine getDefine() {
        return this.define;
    }

    public void setDefine(WorkFlowDefine define) {
        this.define = define;
    }

    public List<WorkFlowNodeSet> getCreat_nodes() {
        return this.creat_nodes;
    }

    public void setCreat_nodes(List<WorkFlowNodeSet> creat_nodes) {
        this.creat_nodes = creat_nodes;
    }

    public List<WorkFlowNodeSet> getUpdate_nodes() {
        return this.update_nodes;
    }

    public void setUpdate_nodes(List<WorkFlowNodeSet> update_nodes) {
        this.update_nodes = update_nodes;
    }

    public List<String> getDel_nodes() {
        return this.del_nodes;
    }

    public void setDel_nodes(List<String> del_nodes) {
        this.del_nodes = del_nodes;
    }

    public List<WorkFlowLine> getCreat_lines() {
        return this.creat_lines;
    }

    public void setCreat_lines(List<WorkFlowLine> creat_lines) {
        this.creat_lines = creat_lines;
    }

    public List<WorkFlowLine> getUpdate_lines() {
        return this.update_lines;
    }

    public void setUpdate_lines(List<WorkFlowLine> update_lines) {
        this.update_lines = update_lines;
    }

    public List<String> getDel_lines() {
        return this.del_lines;
    }

    public void setDel_lines(List<String> del_lines) {
        this.del_lines = del_lines;
    }

    public List<WorkFlowAction> getCreat_actions() {
        return this.creat_actions;
    }

    public void setCreat_actions(List<WorkFlowAction> creat_actions) {
        this.creat_actions = creat_actions;
    }

    public List<WorkFlowAction> getUpdate_actions() {
        return this.update_actions;
    }

    public void setUpdate_actions(List<WorkFlowAction> update_actions) {
        this.update_actions = update_actions;
    }

    public List<String> getDel_actions() {
        return this.del_actions;
    }

    public void setDel_actions(List<String> del_actions) {
        this.del_actions = del_actions;
    }

    public List<WorkFlowParticipant> getCreat_participants() {
        return this.creat_participants;
    }

    public void setCreat_participants(List<WorkFlowParticipant> creat_participants) {
        this.creat_participants = creat_participants;
    }

    public List<WorkFlowParticipant> getUpdate_participants() {
        return this.update_participants;
    }

    public void setUpdate_participants(List<WorkFlowParticipant> update_participants) {
        this.update_participants = update_participants;
    }

    public List<String> getDel_participants() {
        return this.del_participants;
    }

    public void setDel_participants(List<String> del_participants) {
        this.del_participants = del_participants;
    }
}

