/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition
 */
package com.jiuqi.nr.formula.web.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class ConditionImportResult {
    @JsonIgnore
    private Boolean success = Boolean.TRUE;
    private final List<ErrorInfo> errorInfos = new ArrayList<ErrorInfo>();
    @JsonIgnore
    private final List<DesignFormulaCondition> data = new ArrayList<DesignFormulaCondition>();
    private byte[] excel;

    public byte[] getExcel() {
        return this.excel;
    }

    public void setExcel(byte[] excel) {
        this.excel = excel;
    }

    public List<DesignFormulaCondition> getData() {
        return this.data;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public List<ErrorInfo> getErrorInfos() {
        return this.errorInfos;
    }

    public String toString() {
        return new StringJoiner(", ", ConditionImportResult.class.getSimpleName() + "[", "]").add("success=" + this.success).add("errorInfos=" + this.errorInfos).add("data=" + this.data).add("excel=" + Arrays.toString(this.excel)).toString();
    }

    public void addData(DesignFormulaCondition condition) {
        if (condition != null) {
            this.data.add(condition);
        }
    }

    public void addErrInfo(ErrorInfo error) {
        if (error != null) {
            this.errorInfos.add(error);
            this.success = Boolean.FALSE;
        }
    }

    public static class ErrorInfo {
        private Integer lineNum;
        private String message;
        private String code;
        private String title;
        private String condition;

        public ErrorInfo(Integer lineNum, String message) {
            this.lineNum = lineNum;
            this.message = message;
        }

        public String toString() {
            return new StringJoiner(", ", ErrorInfo.class.getSimpleName() + "[", "]").add("lineNum=" + this.lineNum).add("message='" + this.message + "'").add("code='" + this.code + "'").add("title='" + this.title + "'").add("condition='" + this.condition + "'").toString();
        }

        public Integer getLineNum() {
            return this.lineNum;
        }

        public String getMessage() {
            return this.message;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCondition() {
            return this.condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public void setLineNum(Integer lineNum) {
            this.lineNum = lineNum;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

