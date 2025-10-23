/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.web.param;

import com.jiuqi.nr.datascheme.web.param.ShowFieldVO;
import java.util.List;
import java.util.Map;

public class ReportDimFieldVO {
    private Boolean TaskV2 = true;
    private Map<String, String> defaultCode;
    private List<ShowFieldVO> showFields;

    public Map<String, String> getDefaultCode() {
        return this.defaultCode;
    }

    public void setDefaultCode(Map<String, String> defaultCode) {
        this.defaultCode = defaultCode;
    }

    public List<ShowFieldVO> getShowFields() {
        return this.showFields;
    }

    public void setShowFields(List<ShowFieldVO> showFields) {
        this.showFields = showFields;
    }

    public Boolean isTaskV2() {
        return true;
    }

    public void setTaskV2(Boolean taskV2) {
        this.TaskV2 = true;
    }
}

