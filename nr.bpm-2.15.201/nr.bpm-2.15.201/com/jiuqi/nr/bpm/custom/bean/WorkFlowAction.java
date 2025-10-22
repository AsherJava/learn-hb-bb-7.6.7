/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.custom.bean;

import com.jiuqi.nr.bpm.custom.bean.FormulaSchemeData;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class WorkFlowAction
implements Serializable {
    private static final long serialVersionUID = -3254742044149501189L;
    private String id;
    private String linkid;
    private String nodeid;
    private String actionid;
    private String stateName;
    private String stateCode;
    private String actionTitle;
    private String actionCode;
    private String actionDesc;
    private String imagePath;
    private String exset;
    private Date updateTime;
    private List<FormulaSchemeData> calFormulaSchemeDataList;
    private List<FormulaSchemeData> checkFormulaSchemeDataList;

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

    public String getNodeid() {
        return this.nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
    }

    public String getActionid() {
        return this.actionid;
    }

    public void setActionid(String actionid) {
        this.actionid = actionid;
    }

    public String getExset() {
        return this.exset;
    }

    public void setExset(String exset) {
        this.exset = exset;
    }

    public String getLinkid() {
        return this.linkid;
    }

    public void setLinkid(String linkid) {
        this.linkid = linkid;
    }

    public String getStateName() {
        return this.stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getActionTitle() {
        return this.actionTitle;
    }

    public void setActionTitle(String actionTitle) {
        this.actionTitle = actionTitle;
    }

    public String getActionDesc() {
        return this.actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public List<FormulaSchemeData> getCalFormulaSchemeDataList() {
        return this.calFormulaSchemeDataList;
    }

    public void setCalFormulaSchemeDataList(List<FormulaSchemeData> calFormulaSchemeDataList) {
        this.calFormulaSchemeDataList = calFormulaSchemeDataList;
    }

    public List<FormulaSchemeData> getCheckFormulaSchemeDataList() {
        return this.checkFormulaSchemeDataList;
    }

    public void setCheckFormulaSchemeDataList(List<FormulaSchemeData> checkFormulaSchemeDataList) {
        this.checkFormulaSchemeDataList = checkFormulaSchemeDataList;
    }
}

