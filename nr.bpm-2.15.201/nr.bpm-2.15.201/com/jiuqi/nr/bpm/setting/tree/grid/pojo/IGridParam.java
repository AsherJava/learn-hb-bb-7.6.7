/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.tree.grid.pojo;

import java.util.ArrayList;
import java.util.List;

public class IGridParam {
    private String period;
    private String formSchemeKey;
    private boolean defaultWorkflow;
    private String parentId;
    private boolean selectCurrent;
    private boolean allChildren;
    private boolean directChildren;
    private List<String> selectId = new ArrayList<String>();
    private boolean selected;
    private int startState;

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public boolean isDefaultWorkflow() {
        return this.defaultWorkflow;
    }

    public void setDefaultWorkflow(boolean defaultWorkflow) {
        this.defaultWorkflow = defaultWorkflow;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isSelectCurrent() {
        return this.selectCurrent;
    }

    public void setSelectCurrent(boolean selectCurrent) {
        this.selectCurrent = selectCurrent;
    }

    public boolean isAllChildren() {
        return this.allChildren;
    }

    public void setAllChildren(boolean allChildren) {
        this.allChildren = allChildren;
    }

    public boolean isDirectChildren() {
        return this.directChildren;
    }

    public void setDirectChildren(boolean directChildren) {
        this.directChildren = directChildren;
    }

    public List<String> getSelectId() {
        return this.selectId;
    }

    public void setSelectId(List<String> selectId) {
        this.selectId = selectId;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getStartState() {
        return this.startState;
    }

    public void setStartState(int startState) {
        this.startState = startState;
    }
}

