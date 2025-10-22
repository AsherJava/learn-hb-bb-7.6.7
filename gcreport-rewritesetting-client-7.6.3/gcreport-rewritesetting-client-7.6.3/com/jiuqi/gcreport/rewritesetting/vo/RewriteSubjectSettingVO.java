/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.rewritesetting.vo;

public class RewriteSubjectSettingVO {
    private String id;
    private String taskId;
    private String schemeId;
    private String originSubjectCodes;
    private String convertedSubjectCode;
    private String rewriteFieldCode;
    private String rewriteFilter;
    private String memo;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getOriginSubjectCodes() {
        return this.originSubjectCodes;
    }

    public void setOriginSubjectCodes(String originSubjectCodes) {
        this.originSubjectCodes = originSubjectCodes;
    }

    public String getConvertedSubjectCode() {
        return this.convertedSubjectCode;
    }

    public void setConvertedSubjectCode(String convertedSubjectCode) {
        this.convertedSubjectCode = convertedSubjectCode;
    }

    public String getRewriteFieldCode() {
        return this.rewriteFieldCode;
    }

    public void setRewriteFieldCode(String rewriteFieldCode) {
        this.rewriteFieldCode = rewriteFieldCode;
    }

    public String getRewriteFilter() {
        return this.rewriteFilter;
    }

    public void setRewriteFilter(String rewriteFilter) {
        this.rewriteFilter = rewriteFilter;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}

