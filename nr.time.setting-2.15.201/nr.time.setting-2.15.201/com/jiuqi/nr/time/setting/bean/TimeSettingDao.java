/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.time.setting.bean;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.io.Serializable;

public class TimeSettingDao
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String formSchemeKey;
    private DimensionValueSet dimensionValueSet;
    private String submitStartTime;
    private String deadLineTime;
    private String repayDeadline;
    private String operator;
    private String operatorOfUnitId;
    private int unitLevel;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public String getSubmitStartTime() {
        return this.submitStartTime;
    }

    public void setSubmitStartTime(String submitStartTime) {
        this.submitStartTime = submitStartTime;
    }

    public String getDeadLineTime() {
        return this.deadLineTime;
    }

    public void setDeadLineTime(String deadLineTime) {
        this.deadLineTime = deadLineTime;
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

    public int getUnitLevel() {
        return this.unitLevel;
    }

    public void setUnitLevel(int unitLevel) {
        this.unitLevel = unitLevel;
    }
}

