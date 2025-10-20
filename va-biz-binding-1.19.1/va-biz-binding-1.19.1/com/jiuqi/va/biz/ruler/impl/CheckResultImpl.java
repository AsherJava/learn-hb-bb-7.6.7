/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.impl;

import com.jiuqi.va.biz.intf.data.DataTarget;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import java.util.List;
import java.util.stream.Stream;

public class CheckResultImpl
implements CheckResult {
    private String formulaName;
    private String checkMessage;
    private List<DataTarget> targetList;

    @Override
    public String getFormulaName() {
        return this.formulaName;
    }

    @Override
    public String getCheckMessage() {
        return this.checkMessage;
    }

    @Override
    public Stream<DataTarget> getTargetList() {
        return this.targetList.stream();
    }

    public void setFormulaName(String formulaName) {
        this.formulaName = formulaName;
    }

    public void setCheckMessage(String checkMessage) {
        this.checkMessage = checkMessage;
    }

    public void setTargetList(List<DataTarget> targetList) {
        this.targetList = targetList;
    }
}

