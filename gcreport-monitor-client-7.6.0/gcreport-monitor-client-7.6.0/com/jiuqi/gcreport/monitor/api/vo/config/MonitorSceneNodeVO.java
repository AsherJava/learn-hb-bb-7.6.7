/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.vo.config;

import com.jiuqi.gcreport.monitor.api.inf.MonitorState;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorConfigDetailVO;
import java.util.List;

public class MonitorSceneNodeVO {
    private String code;
    private String name;
    private String label;
    private String title;
    private String groupCode;
    private String groupTitle;
    private Boolean checked = false;
    private Boolean expand = false;
    private Boolean disabled = false;
    private List<MonitorSceneNodeVO> children;
    private List<MonitorState> states;
    private MonitorConfigDetailVO configDataModel;

    public List<MonitorState> getStates() {
        return this.states;
    }

    public void setStates(List<MonitorState> states) {
        this.states = states;
    }

    public MonitorConfigDetailVO getConfigDataModel() {
        return this.configDataModel;
    }

    public void setConfigDataModel(MonitorConfigDetailVO configDataModel) {
        this.configDataModel = configDataModel;
    }

    public String getName() {
        if (this.name == null) {
            this.name = this.code;
        }
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        if (this.label == null && this.title != null) {
            this.label = this.title;
        }
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTitle() {
        if (this.title == null && this.label != null) {
            this.title = this.label;
        }
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroupCode() {
        return this.groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupTitle() {
        return this.groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getChecked() {
        return this.checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getExpand() {
        return this.expand;
    }

    public void setExpand(Boolean expand) {
        this.expand = expand;
    }

    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public List<MonitorSceneNodeVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<MonitorSceneNodeVO> children) {
        this.children = children;
    }
}

