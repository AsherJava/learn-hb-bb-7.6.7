/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.custom.bean;

import java.io.Serializable;
import java.util.Date;

public class WorkFlowNodeSet
implements Serializable {
    private static final long serialVersionUID = -5137379332518081354L;
    private String id;
    private String title;
    private String code;
    private String desc;
    private String[] partis;
    private String[] actions;
    private String linkid;
    private Date updateTime;
    private int frontarrive = 1;
    private int backarrive = 1;
    private boolean writable = true;
    private boolean getback = false;
    private boolean appointable = false;
    private boolean startappoint = false;
    private String appointResource;
    private String appointReName;
    private String appointUserRange;
    private String actionReName_pass;
    private String actionReName_reject;
    private String deadline;
    private boolean signNode;
    private String transfer = "allNum";
    private int countersign_count = 1;
    private String[] sign_user;
    private String[] sign_role;
    private String userOrRole = "user";
    private boolean specialTag;
    private boolean nodeJump = true;
    private boolean signStartMode = false;
    private boolean statisticalNode = false;

    public int getBackarrive() {
        return this.backarrive;
    }

    public void setBackarrive(int backarrive) {
        this.backarrive = backarrive;
    }

    public String getAppointResource() {
        return this.appointResource;
    }

    public void setAppointResource(String appointResource) {
        this.appointResource = appointResource;
    }

    public String getAppointReName() {
        return this.appointReName;
    }

    public void setAppointReName(String appointReName) {
        this.appointReName = appointReName;
    }

    public String getAppointUserRange() {
        return this.appointUserRange;
    }

    public void setAppointUserRange(String appointUserRange) {
        this.appointUserRange = appointUserRange;
    }

    public String getActionReName_pass() {
        return this.actionReName_pass;
    }

    public void setActionReName_pass(String actionReName_pass) {
        this.actionReName_pass = actionReName_pass;
    }

    public String getActionReName_reject() {
        return this.actionReName_reject;
    }

    public void setActionReName_reject(String actionReName_reject) {
        this.actionReName_reject = actionReName_reject;
    }

    public boolean isWritable() {
        return this.writable;
    }

    public void setWritable(boolean writable) {
        this.writable = writable;
    }

    public boolean isGetback() {
        return this.getback;
    }

    public void setGetback(boolean getback) {
        this.getback = getback;
    }

    public boolean isAppointable() {
        return this.appointable;
    }

    public void setAppointable(boolean appointable) {
        this.appointable = appointable;
    }

    public boolean isStartappoint() {
        return this.startappoint;
    }

    public void setStartappoint(boolean startappoint) {
        this.startappoint = startappoint;
    }

    public int getFrontarrive() {
        return this.frontarrive;
    }

    public void setFrontarrive(int frontarrive) {
        this.frontarrive = frontarrive;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String[] getPartis() {
        return this.partis;
    }

    public void setPartis(String[] partis) {
        this.partis = partis;
    }

    public String[] getActions() {
        return this.actions;
    }

    public void setActions(String[] actions) {
        this.actions = actions;
    }

    public String getLinkid() {
        return this.linkid;
    }

    public void setLinkid(String linkid) {
        this.linkid = linkid;
    }

    public String getDeadline() {
        return this.deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public boolean isSignNode() {
        return this.signNode;
    }

    public void setSignNode(boolean signNode) {
        this.signNode = signNode;
    }

    public int getCountersign_count() {
        return this.countersign_count;
    }

    public void setCountersign_count(int countersign_count) {
        this.countersign_count = countersign_count;
    }

    public String[] getSign_user() {
        return this.sign_user;
    }

    public void setSign_user(String[] sign_user) {
        this.sign_user = sign_user;
    }

    public String getUserOrRole() {
        return this.userOrRole;
    }

    public void setUserOrRole(String userOrRole) {
        this.userOrRole = userOrRole;
    }

    public boolean isSpecialTag() {
        return this.specialTag;
    }

    public void setSpecialTag(boolean specialTag) {
        this.specialTag = specialTag;
    }

    public String getTransfer() {
        return this.transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String[] getSign_role() {
        return this.sign_role;
    }

    public void setSign_role(String[] sign_role) {
        this.sign_role = sign_role;
    }

    public boolean isNodeJump() {
        return this.nodeJump;
    }

    public void setNodeJump(boolean nodeJump) {
        this.nodeJump = nodeJump;
    }

    public boolean isSignStartMode() {
        return this.signStartMode;
    }

    public void setSignStartMode(boolean signStartMode) {
        this.signStartMode = signStartMode;
    }

    public boolean isStatisticalNode() {
        return this.statisticalNode;
    }

    public void setStatisticalNode(boolean statisticalNode) {
        this.statisticalNode = statisticalNode;
    }
}

