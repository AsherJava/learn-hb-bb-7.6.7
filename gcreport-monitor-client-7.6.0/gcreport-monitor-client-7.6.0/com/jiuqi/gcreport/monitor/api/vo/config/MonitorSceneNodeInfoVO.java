/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.vo.config;

import com.jiuqi.gcreport.monitor.api.inf.MonitorState;
import com.jiuqi.gcreport.monitor.api.vo.config.MonitorConfigDetailVO;
import java.util.List;

public class MonitorSceneNodeInfoVO {
    private String name;
    private String value;
    private String label;
    private String groupTitle;
    private Boolean expand;
    private List<MonitorState> states;
    private MonitorConfigDetailVO configDataModel;
    private List<MonitorSceneNodeInfoVO> children;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getGroupTitle() {
        return this.groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public Boolean getExpand() {
        return this.expand;
    }

    public void setExpand(Boolean expand) {
        this.expand = expand;
    }

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

    public List<MonitorSceneNodeInfoVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<MonitorSceneNodeInfoVO> children) {
        this.children = children;
    }
}

