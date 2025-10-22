/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.vo;

import com.jiuqi.nr.analysisreport.vo.ReportBaseVO;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ReportVariableParseVO {
    private ReportBaseVO reportBaseVO;
    private ReentrantLock reentrantLock = new ReentrantLock();
    private ConcurrentHashMap<String, String> variableValueMap = new ConcurrentHashMap();
    private Map<String, Object> ext;
    private String content;

    public ReportBaseVO getReportBaseVO() {
        return this.reportBaseVO;
    }

    public void setReportBaseVO(ReportBaseVO reportBaseVO) {
        this.reportBaseVO = reportBaseVO;
    }

    public ReentrantLock getReentrantLock() {
        return this.reentrantLock;
    }

    public void setReentrantLock(ReentrantLock reentrantLock) {
        this.reentrantLock = reentrantLock;
    }

    public ConcurrentHashMap<String, String> getVariableValueMap() {
        return this.variableValueMap;
    }

    public void setVariableValueMap(ConcurrentHashMap<String, String> variableValueMap) {
        this.variableValueMap = variableValueMap;
    }

    public Map<String, Object> getExt() {
        return this.ext;
    }

    public void setExt(Map<String, Object> ext) {
        this.ext = ext;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

