/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package nr.single.para.upload.domain;

import com.jiuqi.np.period.PeriodType;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.common.CompareUpdateType;

public class TaskLinkMappingDTO {
    private String key;
    private String singleCode;
    private String singleTitle;
    private String matchKey;
    private String netTask;
    private String netFormScheme;
    private String year;
    private PeriodType taskType;
    private String currentFormula;
    private String linkTaskFormula;
    private CompareUpdateType updateType;
    private CompareChangeType changeType;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSingleCode() {
        return this.singleCode;
    }

    public void setSingleCode(String singleCode) {
        this.singleCode = singleCode;
    }

    public String getSingleTitle() {
        return this.singleTitle;
    }

    public void setSingleTitle(String singleTitle) {
        this.singleTitle = singleTitle;
    }

    public String getNetTask() {
        return this.netTask;
    }

    public void setNetTask(String netTask) {
        this.netTask = netTask;
    }

    public String getNetFormScheme() {
        return this.netFormScheme;
    }

    public void setNetFormScheme(String netFormScheme) {
        this.netFormScheme = netFormScheme;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public PeriodType getTaskType() {
        return this.taskType;
    }

    public void setTaskType(PeriodType taskType) {
        this.taskType = taskType;
    }

    public String getCurrentFormula() {
        return this.currentFormula;
    }

    public void setCurrentFormula(String currentFormula) {
        this.currentFormula = currentFormula;
    }

    public String getLinkTaskFormula() {
        return this.linkTaskFormula;
    }

    public void setLinkTaskFormula(String linkTaskFormula) {
        this.linkTaskFormula = linkTaskFormula;
    }

    public String getMatchKey() {
        return this.matchKey;
    }

    public void setMatchKey(String matchKey) {
        this.matchKey = matchKey;
    }

    public CompareUpdateType getUpdateType() {
        return this.updateType;
    }

    public void setUpdateType(CompareUpdateType updateType) {
        this.updateType = updateType;
    }

    public CompareChangeType getChangeType() {
        return this.changeType;
    }

    public void setChangeType(CompareChangeType changeType) {
        this.changeType = changeType;
    }
}

