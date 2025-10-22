/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.calculate.dto;

import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GcCalcRuleExecuteStateDTO {
    private AbstractUnionRule rule;
    private AtomicInteger createOffSetItemCount;
    private StringBuffer resultMsg;
    private AtomicBoolean successFlag;
    private Boolean businessSuccessFalg;

    public GcCalcRuleExecuteStateDTO(AbstractUnionRule rule) {
        this.rule = rule;
        this.successFlag = new AtomicBoolean(false);
    }

    public AbstractUnionRule getRule() {
        return this.rule;
    }

    public void setRule(AbstractUnionRule rule) {
        this.rule = rule;
    }

    public Boolean getSuccessFlag() {
        if (this.businessSuccessFalg == null) {
            return null;
        }
        return this.successFlag.get();
    }

    public void setSuccessFlag(boolean successFlag) {
        this.successFlag.set(successFlag);
        this.businessSuccessFalg = successFlag;
    }

    public void addCreateOffsetItemCountValue(Integer addCount) {
        if (this.createOffSetItemCount == null) {
            this.createOffSetItemCount = new AtomicInteger(0);
        }
        this.createOffSetItemCount.addAndGet(addCount);
    }

    public Integer getCreateOffSetItemCountValue() {
        return null;
    }

    public String getResultMsg() {
        if (this.resultMsg == null) {
            return null;
        }
        return this.resultMsg.toString();
    }

    public void addResultMsg(String msg) {
        if (this.resultMsg == null) {
            this.resultMsg = new StringBuffer();
        }
        this.resultMsg.append(msg);
    }
}

