/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.time.setting.bean;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.io.Serializable;
import java.util.List;

public class TimeSettingInfo
implements Serializable {
    private static final long serialVersionUID = 8233445407030085994L;
    private String formSchemeKey;
    private String period;
    private String unitId;
    private DimensionValueSet dimensionValueSet;
    private String submitStartTime;
    private String deadLineTime;
    private String repayDeadline;
    private byte unitRange;
    private List<String> unitIds;
    private String operator;
    private String operatorOfUnitId;
    private boolean defaultColor;
    private int unitLevel;
    private boolean defaultTime;
    private TimeSettingInfo parentTime;
    private String createTime;
    private String updateTime;
    private boolean rootNode;

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getDeadLineTime() {
        return this.deadLineTime;
    }

    public void setDeadLineTime(String deadLineTime) {
        this.deadLineTime = deadLineTime;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public byte getUnitRange() {
        return this.unitRange;
    }

    public void setUnitRange(byte unitRange) {
        this.unitRange = unitRange;
    }

    public List<String> getUnitIds() {
        return this.unitIds;
    }

    public void setUnitIds(List<String> unitIds) {
        this.unitIds = unitIds;
    }

    public String getSubmitStartTime() {
        return this.submitStartTime;
    }

    public void setSubmitStartTime(String submitStartTime) {
        this.submitStartTime = submitStartTime;
    }

    public String getRepayDeadline() {
        return this.repayDeadline;
    }

    public void setRepayDeadline(String repayDeadline) {
        this.repayDeadline = repayDeadline;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatorOfUnitId() {
        return this.operatorOfUnitId;
    }

    public void setOperatorOfUnitId(String operatorOfUnitId) {
        this.operatorOfUnitId = operatorOfUnitId;
    }

    public boolean isDefaultColor() {
        return this.defaultColor;
    }

    public void setDefaultColor(boolean defaultColor) {
        this.defaultColor = defaultColor;
    }

    public boolean isDefaultTime() {
        return this.defaultTime;
    }

    public void setDefaultTime(boolean defaultTime) {
        this.defaultTime = defaultTime;
    }

    public TimeSettingInfo getParentTime() {
        return this.parentTime;
    }

    public void setParentTime(TimeSettingInfo parentTime) {
        this.parentTime = parentTime;
    }

    public int getUnitLevel() {
        return this.unitLevel;
    }

    public void setUnitLevel(int unitLevel) {
        this.unitLevel = unitLevel;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public boolean getRootNode() {
        return this.rootNode;
    }

    public void setRootNode(boolean rootNode) {
        this.rootNode = rootNode;
    }
}

