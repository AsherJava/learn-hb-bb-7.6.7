/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.option;

import java.util.ArrayList;
import java.util.List;

public class ReduceReclassifySubjectMappingVO {
    private List<String> originSubjectCodes;
    private String reclassifyDebitSubjectCode;
    private String reclassifyCreditSubjectCode;

    public List<String> getOriginSubjectCodes() {
        if (null == this.originSubjectCodes) {
            this.originSubjectCodes = new ArrayList<String>();
        }
        return this.originSubjectCodes;
    }

    public void setOriginSubjectCodes(List<String> originSubjectCodes) {
        this.originSubjectCodes = originSubjectCodes;
    }

    public String getReclassifyDebitSubjectCode() {
        return this.reclassifyDebitSubjectCode;
    }

    public void setReclassifyDebitSubjectCode(String reclassifyDebitSubjectCode) {
        this.reclassifyDebitSubjectCode = reclassifyDebitSubjectCode;
    }

    public String getReclassifyCreditSubjectCode() {
        return this.reclassifyCreditSubjectCode;
    }

    public void setReclassifyCreditSubjectCode(String reclassifyCreditSubjectCode) {
        this.reclassifyCreditSubjectCode = reclassifyCreditSubjectCode;
    }
}

