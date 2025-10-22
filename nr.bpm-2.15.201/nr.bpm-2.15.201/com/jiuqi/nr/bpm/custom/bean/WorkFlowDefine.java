/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.custom.bean;

import java.io.Serializable;
import java.util.Date;

public class WorkFlowDefine
implements Serializable {
    private static final long serialVersionUID = -5102085625956244917L;
    private String id;
    private int state = 0;
    private String code;
    private String title;
    private String order;
    private String parentID;
    private String desc;
    private Date updatetime;
    private String dataid;
    private String xml;
    private String flowobjid;
    private boolean autostart;
    private boolean isSendEmail;
    private boolean subflow;
    private String linkid;
    private boolean custom;
    private String taskId;

    public String getLinkid() {
        return this.linkid;
    }

    public void setLinkid(String linkid) {
        this.linkid = linkid;
    }

    public String getFlowobjid() {
        return this.flowobjid;
    }

    public void setFlowobjid(String flowobjid) {
        this.flowobjid = flowobjid;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isAutostart() {
        return this.autostart;
    }

    public void setAutostart(boolean autostart) {
        this.autostart = autostart;
    }

    public boolean isSubflow() {
        return this.subflow;
    }

    public void setSubflow(boolean subflow) {
        this.subflow = subflow;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getDataid() {
        return this.dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid;
    }

    public String getXml() {
        return this.xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getParentID() {
        return this.parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public boolean isCustom() {
        return this.custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    public boolean isSendEmail() {
        return this.isSendEmail;
    }

    public void setSendEmail(boolean isSendEmail) {
        this.isSendEmail = isSendEmail;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}

