/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package nr.single.data.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.Map;

public class TaskCopyParam
implements Serializable {
    private static final long serialVersionUID = -6516607131919983914L;
    private String taskKey;
    private String formSchemeKey;
    private String periodCode;
    private String oldTaskKey;
    private String oldFormSchemeKey;
    private String oldPeriodCode;
    private String taskLinkTag;
    private String formulaSchemeName;
    private String copyDataForms;
    private String copyErrorInfoForms;
    private int copyDirection;
    private int copyMode;
    private Map<String, DimensionValue> dimensionSet;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getPeriodCode() {
        return this.periodCode;
    }

    public void setPeriodCode(String periodCode) {
        this.periodCode = periodCode;
    }

    public String getOldTaskKey() {
        return this.oldTaskKey;
    }

    public void setOldTaskKey(String oldTaskKey) {
        this.oldTaskKey = oldTaskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getOldFormSchemeKey() {
        return this.oldFormSchemeKey;
    }

    public void setOldFormSchemeKey(String oldFormSchemeKey) {
        this.oldFormSchemeKey = oldFormSchemeKey;
    }

    public String getOldPeriodCode() {
        return this.oldPeriodCode;
    }

    public void setOldPeriodCode(String oldPeriodCode) {
        this.oldPeriodCode = oldPeriodCode;
    }

    public String getTaskLinkTag() {
        return this.taskLinkTag;
    }

    public void setTaskLinkTag(String taskLinkTag) {
        this.taskLinkTag = taskLinkTag;
    }

    public String getFormulaSchemeName() {
        return this.formulaSchemeName;
    }

    public void setFormulaSchemeName(String formulaSchemeName) {
        this.formulaSchemeName = formulaSchemeName;
    }

    public String getCopyDataForms() {
        return this.copyDataForms;
    }

    public void setCopyDataForms(String copyDataForms) {
        this.copyDataForms = copyDataForms;
    }

    public String getCopyErrorInfoForms() {
        return this.copyErrorInfoForms;
    }

    public void setCopyErrorInfoForms(String copyErrorInfoForms) {
        this.copyErrorInfoForms = copyErrorInfoForms;
    }

    public int getCopyDirection() {
        return this.copyDirection;
    }

    public void setCopyDirection(int copyDirection) {
        this.copyDirection = copyDirection;
    }

    public int getCopyMode() {
        return this.copyMode;
    }

    public void setCopyMode(int copyMode) {
        this.copyMode = copyMode;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }
}

