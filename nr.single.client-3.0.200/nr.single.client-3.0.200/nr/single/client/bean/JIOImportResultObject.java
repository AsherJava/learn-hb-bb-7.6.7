/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.dataentry.bean.ReportImportResultObject
 *  nr.single.map.data.bean.RepeatImportParam
 */
package nr.single.client.bean;

import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.dataentry.bean.ReportImportResultObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import nr.single.client.bean.JIOImportResultPeriodObject;
import nr.single.client.bean.JioMatchResult;
import nr.single.map.data.bean.RepeatImportParam;

public class JIOImportResultObject
extends ReportImportResultObject
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<JioMatchResult> jioMatchs;
    private AsyncTaskInfo jioImportTask;
    private List<JIOImportResultObject> jioImportResults;
    private RepeatImportParam jioRepeatParm = null;
    private RepeatImportParam jioSelectParm = null;
    private String jioTaskFlag;
    private String jioFileFlag;
    private String jioTaskPeriod;
    private String jioTaskTitle;
    private String jioTaskYear;
    private String jioFileName;
    private String jioFilePath;
    private String jioTaskDir;
    private String jioFileKey;
    private String jioConfigKey;
    private List<JIOImportResultPeriodObject> jioPeriodResults;
    private JIOImportResultPeriodObject curPeriodResult;
    private int errorLevel = 0;
    private int attachFileNum = 0;

    public List<JioMatchResult> getJioMatchs() {
        if (this.jioMatchs == null) {
            this.jioMatchs = new ArrayList<JioMatchResult>();
        }
        return this.jioMatchs;
    }

    public void setJioMatchs(List<JioMatchResult> jioMatchs) {
        this.jioMatchs = jioMatchs;
    }

    public RepeatImportParam getJioRepeatParm() {
        return this.jioRepeatParm;
    }

    public void setJioRepeatParm(RepeatImportParam jioRepeatParm) {
        this.jioRepeatParm = jioRepeatParm;
    }

    public String getJioTaskFlag() {
        return this.jioTaskFlag;
    }

    public void setJioTaskFlag(String jioTaskFlag) {
        this.jioTaskFlag = jioTaskFlag;
    }

    public String getJioFileFlag() {
        return this.jioFileFlag;
    }

    public void setJioFileFlag(String jioFileFlag) {
        this.jioFileFlag = jioFileFlag;
    }

    public RepeatImportParam getJioSelectParm() {
        return this.jioSelectParm;
    }

    public void setJioSelectParm(RepeatImportParam jioSelectParm) {
        this.jioSelectParm = jioSelectParm;
    }

    public List<JIOImportResultPeriodObject> getJioPeriodResults() {
        if (this.jioPeriodResults == null) {
            this.jioPeriodResults = new ArrayList<JIOImportResultPeriodObject>();
        }
        return this.jioPeriodResults;
    }

    public void setJioPeriodResults(List<JIOImportResultPeriodObject> jioPeriodResults) {
        this.jioPeriodResults = jioPeriodResults;
    }

    public JIOImportResultPeriodObject getCurPeriodResult() {
        return this.curPeriodResult;
    }

    public void setCurPeriodResult(JIOImportResultPeriodObject curPeriodResult) {
        this.curPeriodResult = curPeriodResult;
    }

    public String getJioTaskPeriod() {
        return this.jioTaskPeriod;
    }

    public void setJioTaskPeriod(String jioTaskPeriod) {
        this.jioTaskPeriod = jioTaskPeriod;
    }

    public String getJioTaskYear() {
        return this.jioTaskYear;
    }

    public void setJioTaskYear(String jioTaskYear) {
        this.jioTaskYear = jioTaskYear;
    }

    public String getJioTaskTitle() {
        return this.jioTaskTitle;
    }

    public void setJioTaskTitle(String jioTaskTitle) {
        this.jioTaskTitle = jioTaskTitle;
    }

    public AsyncTaskInfo getJioImportTask() {
        return this.jioImportTask;
    }

    public void setJioImportTask(AsyncTaskInfo jioImportTask) {
        this.jioImportTask = jioImportTask;
    }

    public List<JIOImportResultObject> getJioImportResults() {
        return this.jioImportResults;
    }

    public void setJioImportResults(List<JIOImportResultObject> jioImportResults) {
        this.jioImportResults = jioImportResults;
    }

    public String getJioFileName() {
        return this.jioFileName;
    }

    public void setJioFileName(String jioFileName) {
        this.jioFileName = jioFileName;
    }

    public String getJioFileKey() {
        return this.jioFileKey;
    }

    public void setJioFileKey(String jioFileKey) {
        this.jioFileKey = jioFileKey;
    }

    public String getJioConfigKey() {
        return this.jioConfigKey;
    }

    public void setJioConfigKey(String jioConfigKey) {
        this.jioConfigKey = jioConfigKey;
    }

    public String getJioFilePath() {
        return this.jioFilePath;
    }

    public void setJioFilePath(String jioFilePath) {
        this.jioFilePath = jioFilePath;
    }

    public String getJioTaskDir() {
        return this.jioTaskDir;
    }

    public void setJioTaskDir(String jioTaskDir) {
        this.jioTaskDir = jioTaskDir;
    }

    public int getErrorLevel() {
        return this.errorLevel;
    }

    public void setErrorLevel(int errorLevel) {
        this.errorLevel = errorLevel;
    }

    public int getAttachFileNum() {
        return this.attachFileNum;
    }

    public void setAttachFileNum(int attachFileNum) {
        this.attachFileNum = attachFileNum;
    }
}

