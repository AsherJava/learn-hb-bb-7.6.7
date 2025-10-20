/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.domain.debug;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BillFormulaDebugInfoVO {
    private UUID id;
    private String outLine;
    private String outType;
    private Object ruleOutPut;
    private Long time;
    private String message;
    private String sql;
    private List<BillFormulaDebugInfoVO> children;

    public String getOutLine() {
        return this.outLine;
    }

    public void setOutLine(String outLine) {
        this.outLine = outLine;
    }

    public String getOutType() {
        return this.outType;
    }

    public void setOutType(String outType) {
        this.outType = outType;
    }

    public Object getRuleOutPut() {
        return this.ruleOutPut;
    }

    public void setRuleOutPut(Object ruleOutPut) {
        this.ruleOutPut = ruleOutPut;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BillFormulaDebugInfoVO() {
    }

    public BillFormulaDebugInfoVO(String outLine, String message) {
        this.outLine = outLine;
        this.message = message;
    }

    public Long getTime() {
        return this.time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void addChildren(BillFormulaDebugInfoVO children) {
        if (this.children == null) {
            this.children = new ArrayList<BillFormulaDebugInfoVO>();
        }
        this.children.add(children);
    }

    public List<BillFormulaDebugInfoVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<BillFormulaDebugInfoVO> children) {
        this.children = children;
    }

    public UUID getId() {
        return UUID.randomUUID();
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}

