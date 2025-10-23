/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.task.form.controller.vo.FormulaUpdateRecordVO;
import com.jiuqi.nr.task.form.dto.CheckResult;
import java.io.Serializable;
import java.util.List;

public class SaveResult
extends CheckResult
implements Serializable {
    private List<FormulaUpdateRecordVO> updateRecords;

    public SaveResult() {
    }

    public SaveResult(CheckResult checkResult) {
        super(checkResult);
    }

    public List<FormulaUpdateRecordVO> getUpdateRecords() {
        return this.updateRecords;
    }

    public void setUpdateRecords(List<FormulaUpdateRecordVO> updateRecords) {
        this.updateRecords = updateRecords;
    }

    public static SaveResult errorResult(CheckResult checkResult) {
        return new SaveResult(checkResult);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

