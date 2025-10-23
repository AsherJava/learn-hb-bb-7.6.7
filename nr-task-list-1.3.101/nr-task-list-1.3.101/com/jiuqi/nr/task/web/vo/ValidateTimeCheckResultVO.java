/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.web.vo;

import com.jiuqi.nr.task.web.vo.ValidateTimeVO;
import java.util.List;

public class ValidateTimeCheckResultVO {
    private List<ValidateTimeVO> validateDataTimes;
    private String checkResult;
    private String message;

    public List<ValidateTimeVO> getValidateDataTimes() {
        return this.validateDataTimes;
    }

    public void setValidateDataTimes(List<ValidateTimeVO> validateDataTimes) {
        this.validateDataTimes = validateDataTimes;
    }

    public String getCheckResult() {
        return this.checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

