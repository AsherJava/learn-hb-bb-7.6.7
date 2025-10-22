/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.dataentry.paramInfo.FormulaCheckResultGroupInfo;
import java.util.ArrayList;
import java.util.List;

public class FormulaCheckGroupReturnInfo {
    private String message;
    private int totalCount;
    private int hintCount;
    private int warnCount;
    private int errorCount;
    private int showCount;
    private List<FormulaCheckResultGroupInfo> results = new ArrayList<FormulaCheckResultGroupInfo>();

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<FormulaCheckResultGroupInfo> getResults() {
        return this.results;
    }

    public void setResults(List<FormulaCheckResultGroupInfo> results) {
        this.results = results;
    }

    public void incrWranCount() {
        ++this.warnCount;
    }

    public void incrHintCount() {
        ++this.hintCount;
    }

    public void incrErrorCount() {
        ++this.errorCount;
    }

    public int getHintCount() {
        return this.hintCount;
    }

    public void setHintCount(int hintCount) {
        this.hintCount = hintCount;
    }

    public int getWarnCount() {
        return this.warnCount;
    }

    public void setWarnCount(int warnCount) {
        this.warnCount = warnCount;
    }

    public int getErrorCount() {
        return this.errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public int getShowCount() {
        return this.showCount;
    }

    public void setShowCount(int showCount) {
        this.showCount = showCount;
    }
}

