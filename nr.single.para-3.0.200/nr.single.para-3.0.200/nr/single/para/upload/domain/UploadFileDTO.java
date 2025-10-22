/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.domain;

import java.util.ArrayList;
import java.util.List;
import nr.single.para.compare.bean.ParaCompareDataSchemeInfo;
import nr.single.para.compare.bean.ParaCompareTaskInfo;
import nr.single.para.compare.definition.common.CompareContextType;
import nr.single.para.compare.definition.common.TaskFindModeType;

public class UploadFileDTO {
    private String Key;
    private Boolean matchTask;
    private List<ParaCompareTaskInfo> taskInfos;
    private String taskKey;
    private String formSchemeKey;
    private Boolean sameTitle;
    private Boolean recognitionLevel;
    private Boolean importBaseParam;
    private Boolean importFormula;
    private Boolean importPrint;
    private Boolean importQuery;
    private Boolean coverParam;
    private String period;
    private Boolean custom;
    private String singleTaskName;
    private ParaCompareDataSchemeInfo paraCompareDataSchemeInfo;
    private TaskFindModeType taskFindMode;
    private CompareContextType floatRegionCompareType;
    private String singleTaskYear;
    private String singleFileFlag;
    private String singleFromPeriod;
    private String singleToPeriod;
    private ParaCompareTaskInfo netTaskInfo;

    public String getKey() {
        return this.Key;
    }

    public void setKey(String key) {
        this.Key = key;
    }

    public Boolean getMatchTask() {
        return this.matchTask;
    }

    public void setMatchTask(Boolean matchTask) {
        this.matchTask = matchTask;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public Boolean getSameTitle() {
        return this.sameTitle != null && this.sameTitle != false;
    }

    public void setSameTitle(Boolean sameTitle) {
        this.sameTitle = sameTitle;
    }

    public Boolean getRecognitionLevel() {
        return this.recognitionLevel != null && this.recognitionLevel != false;
    }

    public void setRecognitionLevel(Boolean recognitionLevel) {
        this.recognitionLevel = recognitionLevel;
    }

    public Boolean getImportFormula() {
        return this.importFormula != null && this.importFormula != false;
    }

    public void setImportFormula(Boolean importFormula) {
        this.importFormula = importFormula;
    }

    public Boolean getImportPrint() {
        return this.importPrint != null && this.importPrint != false;
    }

    public void setImportPrint(Boolean importPrint) {
        this.importPrint = importPrint;
    }

    public Boolean getImportQuery() {
        return this.importQuery != null && this.importQuery != false;
    }

    public void setImportQuery(Boolean importQuery) {
        this.importQuery = importQuery;
    }

    public Boolean getCoverParam() {
        return this.coverParam != null && this.coverParam != false;
    }

    public void setCoverParam(Boolean coverParam) {
        this.coverParam = coverParam;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String jioPeriodKey) {
        this.period = jioPeriodKey;
    }

    public Boolean getCustom() {
        return this.custom;
    }

    public void setCustom(Boolean custom) {
        this.custom = custom;
    }

    public List<ParaCompareTaskInfo> getTaskInfos() {
        if (this.taskInfos == null) {
            this.taskInfos = new ArrayList<ParaCompareTaskInfo>();
        }
        return this.taskInfos;
    }

    public void setTaskInfos(List<ParaCompareTaskInfo> taskInfos) {
        this.taskInfos = taskInfos;
    }

    public String getSingleTaskName() {
        return this.singleTaskName;
    }

    public void setSingleTaskName(String singleTaskName) {
        this.singleTaskName = singleTaskName;
    }

    public ParaCompareDataSchemeInfo getParaCompareDataSchemeInfo() {
        return this.paraCompareDataSchemeInfo;
    }

    public void setParaCompareDataSchemeInfo(ParaCompareDataSchemeInfo paraCompareDataSchemeInfo) {
        this.paraCompareDataSchemeInfo = paraCompareDataSchemeInfo;
    }

    public TaskFindModeType getTaskFindMode() {
        return this.taskFindMode;
    }

    public void setTaskFindMode(TaskFindModeType taskFindMode) {
        this.taskFindMode = taskFindMode;
    }

    public String getSingleTaskYear() {
        return this.singleTaskYear;
    }

    public void setSingleTaskYear(String singleTaskYear) {
        this.singleTaskYear = singleTaskYear;
    }

    public String getSingleFileFlag() {
        return this.singleFileFlag;
    }

    public void setSingleFileFlag(String singleFileFlag) {
        this.singleFileFlag = singleFileFlag;
    }

    public ParaCompareTaskInfo getNetTaskInfo() {
        return this.netTaskInfo;
    }

    public void setNetTaskInfo(ParaCompareTaskInfo netTaskInfo) {
        this.netTaskInfo = netTaskInfo;
    }

    public Boolean getImportBaseParam() {
        return this.importBaseParam;
    }

    public void setImportBaseParam(Boolean importBaseParam) {
        this.importBaseParam = importBaseParam;
    }

    public String toString() {
        return "UploadFileDTO{Key='" + this.Key + '\'' + ", matchTask=" + this.matchTask + ", taskInfos=" + this.taskInfos.toString() + ", taskKey='" + this.taskKey + '\'' + ", formSchemeKey='" + this.formSchemeKey + '\'' + ", sameTitle=" + this.sameTitle + ", recognitionLevel=" + this.recognitionLevel + ", importBaseParam=" + this.importBaseParam + ", importFormula=" + this.importFormula + ", importPrint=" + this.importPrint + ", coverParam=" + this.coverParam + ", period='" + this.period + '\'' + ", custom=" + this.custom + ", singleTaskName='" + this.singleTaskName + '\'' + ", paraCompareDataSchemeInfo=" + this.paraCompareDataSchemeInfo.toString() + ", taskFindMode=" + this.taskFindMode.toString() + ", singleTaskYear='" + this.singleTaskYear + '\'' + ", netTaskInfo=" + this.netTaskInfo.toString() + ", singleFileFlag='" + this.singleFileFlag + '\'' + '}';
    }

    public String getSingleFromPeriod() {
        return this.singleFromPeriod;
    }

    public void setSingleFromPeriod(String singleFromPeriod) {
        this.singleFromPeriod = singleFromPeriod;
    }

    public String getSingleToPeriod() {
        return this.singleToPeriod;
    }

    public void setSingleToPeriod(String singleToPeriod) {
        this.singleToPeriod = singleToPeriod;
    }

    public CompareContextType getFloatRegionCompareType() {
        return this.floatRegionCompareType;
    }

    public void setFloatRegionCompareType(CompareContextType floatRegionCompareType) {
        this.floatRegionCompareType = floatRegionCompareType;
    }
}

