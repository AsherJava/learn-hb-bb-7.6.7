/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.web.vo;

import com.jiuqi.nr.task.web.vo.ValidateTimeVO;
import java.util.List;

public class ValidateTimeSettingVO {
    private String limitStart;
    private String limitEnd;
    private List<ValidateTimeVO> formSchemes;

    public String getLimitStart() {
        return this.limitStart;
    }

    public void setLimitStart(String limitStart) {
        this.limitStart = limitStart;
    }

    public String getLimitEnd() {
        return this.limitEnd;
    }

    public void setLimitEnd(String limitEnd) {
        this.limitEnd = limitEnd;
    }

    public List<ValidateTimeVO> getFormSchemes() {
        return this.formSchemes;
    }

    public void setFormSchemes(List<ValidateTimeVO> formSchemes) {
        this.formSchemes = formSchemes;
    }
}

