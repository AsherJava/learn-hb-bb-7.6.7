/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package nr.single.para.compare.bean;

import com.jiuqi.np.period.PeriodType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.para.compare.bean.ParaCompareDataSchemeInfo;
import nr.single.para.compare.bean.ParaCompareRegionResult;
import nr.single.para.compare.bean.ParaCompareTaskInfo;
import nr.single.para.compare.bean.ParaImportResult;
import nr.single.para.compare.definition.common.TaskFindModeType;

public class ParaCompareResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String singleTaskFlag;
    private String singleTaskTitle;
    private String singleFileFlag;
    private String singleTaskYear;
    private PeriodType singlePeriodType;
    private String singleFromPeriod;
    private String singleToPeriod;
    private String compareId;
    private String asyncTaskId;
    private List<ParaCompareTaskInfo> mathchTasks;
    private ParaCompareDataSchemeInfo dataSchemeInfo;
    private ParaCompareTaskInfo netTaskInfo;
    private boolean success;
    private String message;
    private String formSchemeKey;
    private String mapSchemeKey;
    private TaskFindModeType taskFindMode;
    private ParaImportResult importResult;
    private List<ParaCompareRegionResult> compareRegions;
    private Map<String, List<ParaCompareRegionResult>> compareFormRegions;

    public List<ParaCompareTaskInfo> getMathchTasks() {
        if (this.mathchTasks == null) {
            this.mathchTasks = new ArrayList<ParaCompareTaskInfo>();
        }
        return this.mathchTasks;
    }

    public void setMathchTasks(List<ParaCompareTaskInfo> mathchTasks) {
        this.mathchTasks = mathchTasks;
    }

    public String getCompareId() {
        return this.compareId;
    }

    public void setCompareId(String compareId) {
        this.compareId = compareId;
    }

    public String getSingleTaskFlag() {
        return this.singleTaskFlag;
    }

    public void setSingleTaskFlag(String singleTaskFlag) {
        this.singleTaskFlag = singleTaskFlag;
    }

    public String getSingleTaskTitle() {
        return this.singleTaskTitle;
    }

    public void setSingleTaskTitle(String singleTaskTitle) {
        this.singleTaskTitle = singleTaskTitle;
    }

    public String getSingleFileFlag() {
        return this.singleFileFlag;
    }

    public void setSingleFileFlag(String singleFileFlag) {
        this.singleFileFlag = singleFileFlag;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAsyncTaskId() {
        return this.asyncTaskId;
    }

    public void setAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
    }

    public PeriodType getSinglePeriodType() {
        return this.singlePeriodType;
    }

    public void setSinglePeriodType(PeriodType singlePeriodType) {
        this.singlePeriodType = singlePeriodType;
    }

    public ParaCompareDataSchemeInfo getDataSchemeInfo() {
        return this.dataSchemeInfo;
    }

    public void setDataSchemeInfo(ParaCompareDataSchemeInfo dataSchemeInfo) {
        this.dataSchemeInfo = dataSchemeInfo;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public ParaImportResult getImportResult() {
        if (this.importResult == null) {
            this.importResult = new ParaImportResult();
        }
        return this.importResult;
    }

    public void setImportResult(ParaImportResult importResult) {
        this.importResult = importResult;
    }

    public ParaCompareTaskInfo getNetTaskInfo() {
        return this.netTaskInfo;
    }

    public void setNetTaskInfo(ParaCompareTaskInfo netTaskInfo) {
        this.netTaskInfo = netTaskInfo;
    }

    public String getSingleTaskYear() {
        return this.singleTaskYear;
    }

    public void setSingleTaskYear(String singleTaskYear) {
        this.singleTaskYear = singleTaskYear;
    }

    public TaskFindModeType getTaskFindMode() {
        return this.taskFindMode;
    }

    public void setTaskFindMode(TaskFindModeType taskFindMode) {
        this.taskFindMode = taskFindMode;
    }

    public List<ParaCompareRegionResult> getCompareRegions() {
        if (this.compareRegions == null) {
            this.compareRegions = new ArrayList<ParaCompareRegionResult>();
        }
        return this.compareRegions;
    }

    public void setCompareRegions(List<ParaCompareRegionResult> compareRegions) {
        this.compareRegions = compareRegions;
    }

    public Map<String, List<ParaCompareRegionResult>> getCompareFormRegions() {
        if (this.compareFormRegions == null) {
            this.compareFormRegions = new HashMap<String, List<ParaCompareRegionResult>>();
        }
        return this.compareFormRegions;
    }

    public void setCompareFormRegions(Map<String, List<ParaCompareRegionResult>> compareFormRegions) {
        this.compareFormRegions = compareFormRegions;
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

    public String getMapSchemeKey() {
        return this.mapSchemeKey;
    }

    public void setMapSchemeKey(String mapSchemeKey) {
        this.mapSchemeKey = mapSchemeKey;
    }
}

