/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.plantask.extend.vo;

import com.jiuqi.common.plantask.extend.vo.RowParamVO;
import java.util.List;

public class SettingPageTemplateVO {
    private String windowType;
    private List<RowParamVO> rowParams;

    public String getWindowType() {
        return this.windowType;
    }

    public void setWindowType(String windowType) {
        this.windowType = windowType;
    }

    public List<RowParamVO> getRowParams() {
        return this.rowParams;
    }

    public void setRowParams(List<RowParamVO> rowParams) {
        this.rowParams = rowParams;
    }

    public String toString() {
        return "SettingPageTemplateVO{windowType='" + this.windowType + '\'' + ", rowParams=" + this.rowParams + '}';
    }
}

