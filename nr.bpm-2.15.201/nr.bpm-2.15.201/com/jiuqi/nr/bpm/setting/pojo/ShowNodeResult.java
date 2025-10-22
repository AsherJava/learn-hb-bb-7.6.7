/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.nr.bpm.setting.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

public class ShowNodeResult {
    private String nodeId;
    private String nodeName;
    private String user;
    private String role;
    private String actionCode;
    private String actionName;
    private String actionState;
    private String returnType;
    private String desc;
    private String time;
    private String actors;
    private String roles;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private List<String> rejectForms;

    public String getNodeId() {
        return this.nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getActionName() {
        return this.actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionState() {
        return this.actionState;
    }

    public void setActionState(String actionState) {
        this.actionState = actionState;
    }

    public String getReturnType() {
        return this.returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNodeName() {
        return this.nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getActors() {
        return this.actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoles() {
        return this.roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public List<String> getRejectForms() {
        return this.rejectForms;
    }

    public void setRejectForms(List<String> rejectForms) {
        this.rejectForms = rejectForms;
    }
}

