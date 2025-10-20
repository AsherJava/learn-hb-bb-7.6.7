/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.journalsingle.vo;

import com.jiuqi.gcreport.journalsingle.vo.JournalSubjectFormulaVO;
import java.util.List;

public class JournalPostReportVO {
    private String taskId;
    private String schemeId;
    private String formId;
    private String periodStr;
    private List<JournalSubjectFormulaVO> zbSubject;

    public JournalPostReportVO() {
    }

    public JournalPostReportVO(String taskId, String schemeId, String periodStr, String formId, List<JournalSubjectFormulaVO> zbSubject) {
        this.taskId = taskId;
        this.schemeId = schemeId;
        this.formId = formId;
        this.periodStr = periodStr;
        this.zbSubject = zbSubject;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public List<JournalSubjectFormulaVO> getZbSubject() {
        return this.zbSubject;
    }

    public void setZbSubject(List<JournalSubjectFormulaVO> zbSubject) {
        this.zbSubject = zbSubject;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }
}

