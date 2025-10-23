/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.web.vo;

import com.jiuqi.nr.task.web.vo.ValidateTimeVO;
import java.util.List;
import org.springframework.util.StringUtils;

public class ValidateTimeMergeVO {
    private String taskKey;
    private String formSchemeKey;
    private List<ValidateTimeVO> validateTimes;

    public ValidateTimeMergeVO() {
    }

    public ValidateTimeMergeVO(String taskKey, String formSchemeKey, List<ValidateTimeVO> validateTimes) {
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        for (ValidateTimeVO validateTime : validateTimes) {
            if (StringUtils.hasText(validateTime.getFormSchemeKey())) continue;
            validateTime.setFormSchemeKey(formSchemeKey);
        }
        this.validateTimes = validateTimes;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<ValidateTimeVO> getValidateTimes() {
        return this.validateTimes;
    }

    public void setValidateTimes(List<ValidateTimeVO> validateTimes) {
        this.validateTimes = validateTimes;
    }
}

