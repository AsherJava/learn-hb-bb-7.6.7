/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

public class XmlDataExportLog {
    private String taskInfo;
    private String dwEntityId;
    private String periodValue;
    private Map<String, List<String>> successUnitReportMap;
    private Map<String, List<String>> errorUnitReportMap;
    private String curUnitInfo;
    private List<String> otherErrorLog;
    private String summaryLog;

    public String getCurUnitInfo() {
        return this.curUnitInfo;
    }

    public void setCurUnitInfo(String curUnitInfo) {
        this.curUnitInfo = curUnitInfo;
    }

    public String getSummaryLog() {
        StringBuilder sb = new StringBuilder();
        sb.append("\u5f53\u524d\u4efb\u52a1\uff1a").append(this.taskInfo).append("\r\n").append("\u4e3b\u7ef4\u5ea6:").append(this.dwEntityId).append("\r\n").append("\u65f6\u671f\uff1a").append(this.periodValue).append("\r\n");
        boolean isFail = false;
        if (this.errorUnitReportMap != null && this.errorUnitReportMap.size() > 0) {
            isFail = true;
            sb.append("\u5931\u8d25\u5bfc\u51fa\u7684\u65e5\u5fd7\u4fe1\u606f\uff1a");
            for (String key : this.errorUnitReportMap.keySet()) {
                sb.append(key).append("\uff1a");
                this.errorUnitReportMap.get(key).forEach(sb::append);
            }
        }
        if (this.otherErrorLog != null) {
            isFail = true;
            sb.append("\u5176\u4ed6\u5f02\u5e38\u65e5\u5fd7\u4fe1\u606f\uff1a");
            this.otherErrorLog.forEach(log -> sb.append((String)log).append(";"));
        }
        if (!isFail) {
            return "\u5bfc\u51fa\u6210\u529f.";
        }
        return sb.toString();
    }

    public void addSuccessReportLog(String logInfo) {
        if (StringUtils.hasLength(this.curUnitInfo)) {
            if (this.successUnitReportMap == null) {
                this.successUnitReportMap = new HashMap<String, List<String>>();
                this.successUnitReportMap.put(this.curUnitInfo, new ArrayList());
                this.successUnitReportMap.get(this.curUnitInfo).add(logInfo);
            } else {
                if (!this.successUnitReportMap.containsKey(this.curUnitInfo)) {
                    this.successUnitReportMap.put(this.curUnitInfo, new ArrayList());
                }
                this.successUnitReportMap.get(this.curUnitInfo).add(logInfo);
            }
        }
    }

    public void addFailureReportLog(String logInfo) {
        if (StringUtils.hasLength(this.curUnitInfo)) {
            if (this.errorUnitReportMap == null) {
                this.errorUnitReportMap = new HashMap<String, List<String>>();
                this.errorUnitReportMap.put(this.curUnitInfo, new ArrayList());
                this.errorUnitReportMap.get(this.curUnitInfo).add(logInfo);
            } else {
                if (!this.errorUnitReportMap.containsKey(this.curUnitInfo)) {
                    this.errorUnitReportMap.put(this.curUnitInfo, new ArrayList());
                }
                this.errorUnitReportMap.get(this.curUnitInfo).add(logInfo);
            }
        }
    }

    public void addOtherErrorLog(String log) {
        if (this.otherErrorLog == null) {
            this.otherErrorLog = new ArrayList<String>();
        }
        if (StringUtils.hasLength(log)) {
            this.otherErrorLog.add(log);
        }
    }

    public String getErrorLog() {
        StringBuilder sb = new StringBuilder();
        if (this.otherErrorLog != null && this.otherErrorLog.size() > 0) {
            sb.append("\u5176\u4ed6\u5f02\u5e38\u65e5\u5fd7\u4fe1\u606f\uff1a");
            this.otherErrorLog.forEach(log -> sb.append((String)log).append(";"));
        }
        return sb.toString();
    }

    public XmlDataExportLog setTaskInfo(String taskInfo) {
        this.taskInfo = taskInfo;
        return this;
    }

    public XmlDataExportLog setDwEntityId(String dwEntityId) {
        this.dwEntityId = dwEntityId;
        return this;
    }

    public XmlDataExportLog setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
        return this;
    }
}

