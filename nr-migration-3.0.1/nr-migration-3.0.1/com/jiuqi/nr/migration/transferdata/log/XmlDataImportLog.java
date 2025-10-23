/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

public class XmlDataImportLog {
    private String solutionInfo;
    private String tableName;
    private String periodStr;
    private String unitCount;
    private String curUnitInfo;
    private Map<String, List<String>> detailLogMap;
    private Map<String, List<String>> errorLogMap;
    private String errorLog;
    private List<String> noMappingFormulaCode = new ArrayList<String>();

    public XmlDataImportLog() {
        this.errorLogMap = new HashMap<String, List<String>>();
        this.detailLogMap = new HashMap<String, List<String>>();
    }

    public String getSummaryLog() {
        if (StringUtils.hasLength(this.errorLog)) {
            return this.errorLog;
        }
        StringBuilder sumLog = new StringBuilder();
        int allCount = this.errorLogMap.size();
        int errorCount = this.getErrorUnitCount();
        sumLog.append("\u6210\u529f\u5bfc\u5165").append(allCount - errorCount).append("\u4e2a\u5355\u4f4d  ").append(errorCount > 0 ? "\u5931\u8d25\u5bfc\u5165" + errorCount + "\u4e2a\u5355\u4f4d" : "").append("\n");
        if (this.errorLogMap.size() > 0) {
            StringBuilder errorUnit = new StringBuilder();
            for (String unitName : this.errorLogMap.keySet()) {
                List<String> errorInfo = this.errorLogMap.get(unitName);
                if (errorInfo.size() <= 0) continue;
                errorUnit.append(unitName).append("\n");
            }
            if (errorUnit.length() > 0) {
                sumLog.append("\u5bfc\u5165\u5931\u8d25\u7684\u5355\u4f4d\u5217\u8868\u662f\uff1a").append((CharSequence)errorUnit).append("\n");
            }
        }
        return sumLog.toString();
    }

    public void addDetailLog(String logInfo) {
        if (!this.detailLogMap.containsKey(this.curUnitInfo)) {
            this.detailLogMap.put(this.curUnitInfo, new ArrayList());
        }
        if (StringUtils.hasLength(logInfo)) {
            this.detailLogMap.get(this.curUnitInfo).add(logInfo);
        }
    }

    public void addErrorLog(String errorInfo) {
        if (!this.errorLogMap.containsKey(this.curUnitInfo)) {
            this.errorLogMap.put(this.curUnitInfo, new ArrayList());
        }
        if (StringUtils.hasLength(errorInfo)) {
            this.errorLogMap.get(this.curUnitInfo).add(errorInfo);
        }
    }

    private StringBuilder getLogHeadInfo() {
        StringBuilder sumLog = new StringBuilder();
        if (StringUtils.hasLength(this.getSolutionInfo())) {
            sumLog.append(this.getSolutionInfo()).append("\n");
        }
        if (StringUtils.hasLength(this.getTableName())) {
            sumLog.append(this.getTableName()).append("\n");
        }
        if (StringUtils.hasLength(this.getPeriodStr())) {
            sumLog.append(this.getPeriodStr()).append("\n");
        }
        int allCount = this.errorLogMap.size();
        int errorCount = this.getErrorUnitCount();
        sumLog.append("\u5171\u5bfc\u5165").append(this.getUnitCount()).append("\u4e2a\u5355\u4f4d\uff0c").append("\u6210\u529f\u5bfc\u5165").append(allCount - errorCount).append("\u4e2a\u5355\u4f4d  ").append(errorCount > 0 ? "\u5931\u8d25\u5bfc\u5165" + errorCount + "\u4e2a\u5355\u4f4d" : "").append("\n");
        return sumLog;
    }

    public String getDetailLog() {
        StringBuilder detailLog = this.getLogHeadInfo();
        ArrayList<String> successUnit = new ArrayList<String>();
        ArrayList<String> errorUnit = new ArrayList<String>();
        if (this.errorLogMap.size() > 0) {
            for (String unitName : this.errorLogMap.keySet()) {
                List<String> errorInfo = this.errorLogMap.get(unitName);
                if (errorInfo.size() > 0) {
                    errorUnit.add(unitName);
                    continue;
                }
                successUnit.add(unitName);
            }
        }
        if (errorUnit.size() > 0) {
            detailLog.append("\u5931\u8d25\u8be6\u60c5\uff1a").append("\n");
            errorUnit.forEach(u -> {
                List<String> errorInfo = this.errorLogMap.get(u);
                detailLog.append((String)u).append("\n").append("\u5931\u8d25\u539f\u56e0\uff1a");
                errorInfo.forEach(info -> detailLog.append((String)info).append("\n"));
                detailLog.append("\u5176\u4ed6\u4fe1\u606f\uff1a");
                this.detailLogMap.get(u).forEach(info -> detailLog.append((String)info).append("\n"));
                detailLog.append("\n");
            });
        }
        if (successUnit.size() > 0) {
            detailLog.append("\u8be6\u60c5\uff1a").append("\n");
            successUnit.forEach(u -> {
                List<String> successInfo = this.detailLogMap.get(u);
                detailLog.append((String)u).append("\n");
                successInfo.forEach(info -> detailLog.append((String)info).append("\n"));
                detailLog.append("\n");
            });
        }
        if (this.noMappingFormulaCode.size() > 0) {
            detailLog.append("\u516c\u5f0f\u9519\u8bef\u8bf4\u660e\u5bfc\u5165\u5b58\u5728\u5f02\u5e38\u60c5\u51b5\uff0c\u6ca1\u6709\u516c\u5f0f\u7f16\u7801\u6620\u5c04\uff1a").append(String.join((CharSequence)";", this.noMappingFormulaCode));
        }
        return detailLog.toString();
    }

    public List<String> getNoMappingFormulaCode() {
        return this.noMappingFormulaCode;
    }

    public void setNoMappingFormulaCode(List<String> noMappingFormulaCode) {
        this.noMappingFormulaCode = noMappingFormulaCode;
    }

    public void addNoMappingFormulaCode(String formulaCode) {
        this.noMappingFormulaCode.add(formulaCode);
    }

    private int getErrorUnitCount() {
        ArrayList<String> result = new ArrayList<String>();
        for (String unitTitle : this.errorLogMap.keySet()) {
            List<String> errorList = this.errorLogMap.get(unitTitle);
            if (errorList.size() <= 0) continue;
            result.add(unitTitle);
        }
        return result.size();
    }

    public String getSolutionInfo() {
        return this.solutionInfo;
    }

    public void setSolutionInfo(String solutionInfo) {
        this.solutionInfo = solutionInfo;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getUnitCount() {
        return this.unitCount;
    }

    public void setUnitCount(String unitCount) {
        this.unitCount = unitCount;
    }

    public String getCurUnitInfo() {
        return this.curUnitInfo;
    }

    public void setCurUnitInfo(String curUnitInfo) {
        this.curUnitInfo = curUnitInfo;
        if (!this.errorLogMap.containsKey(this.curUnitInfo)) {
            this.errorLogMap.put(this.curUnitInfo, new ArrayList());
        }
        if (!this.detailLogMap.containsKey(this.curUnitInfo)) {
            this.detailLogMap.put(this.curUnitInfo, new ArrayList());
        }
    }

    public void setSummaryLog(String summaryLog) {
        this.errorLog = summaryLog;
    }
}

