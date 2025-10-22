/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.pojo;

import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import java.util.Set;

public class WorkflowSettingPojo {
    private String key;
    private String title;
    private String dataType;
    private String dataId;
    private Set<String> deleteObj;
    private String workflowId;
    private String desc;
    private boolean chooseAll;
    private String order;
    private int type;
    private Set<String> dataObj;
    private Set<String> addObj;

    public WorkflowSettingPojo() {
    }

    public WorkflowSettingPojo(WorkflowSettingDefine workflowSetting) {
        this.key = workflowSetting.getId();
        this.title = workflowSetting.getTitle();
        this.dataId = workflowSetting.getDataId();
        this.dataId = workflowSetting.getDataId();
        this.workflowId = workflowSetting.getWorkflowId();
        this.desc = workflowSetting.getDesc();
        this.order = workflowSetting.getOrder();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataId() {
        return this.dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getWorkflowId() {
        return this.workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isChooseAll() {
        return this.chooseAll;
    }

    public void setChooseAll(boolean chooseAll) {
        this.chooseAll = chooseAll;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Set<String> getDataObj() {
        return this.dataObj;
    }

    public void setDataObj(Set<String> dataObj) {
        this.dataObj = dataObj;
    }

    public Set<String> getAddObj() {
        return this.addObj;
    }

    public void setAddObj(Set<String> addObj) {
        this.addObj = addObj;
    }

    public Set<String> getDeleteObj() {
        return this.deleteObj;
    }

    public void setDeleteObj(Set<String> deleteObj) {
        this.deleteObj = deleteObj;
    }
}

