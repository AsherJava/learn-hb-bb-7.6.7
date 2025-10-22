/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.init.carryover.vo;

import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectVO;

public class CarryOverOffsetSubjectMappingVO {
    private String srcSubjectCode;
    private String srcSubjectTitle;
    private String destSubjectCode;
    private CarryOverOffsetSubjectVO destSubjectTitle;

    public String getSrcSubjectCode() {
        return this.srcSubjectCode;
    }

    public void setSrcSubjectCode(String srcSubjectCode) {
        this.srcSubjectCode = srcSubjectCode;
    }

    public String getSrcSubjectTitle() {
        return this.srcSubjectTitle;
    }

    public void setSrcSubjectTitle(String srcSubjectTitle) {
        this.srcSubjectTitle = srcSubjectTitle;
    }

    public String getDestSubjectCode() {
        return this.destSubjectCode;
    }

    public void setDestSubjectCode(String destSubjectCode) {
        this.destSubjectCode = destSubjectCode;
    }

    public CarryOverOffsetSubjectVO getDestSubjectTitle() {
        return this.destSubjectTitle;
    }

    public void setDestSubjectTitle(CarryOverOffsetSubjectVO destSubjectTitle) {
        this.destSubjectTitle = destSubjectTitle;
    }
}

