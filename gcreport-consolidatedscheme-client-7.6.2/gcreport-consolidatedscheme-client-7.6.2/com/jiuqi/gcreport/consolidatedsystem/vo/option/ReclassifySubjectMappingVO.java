/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.option;

import java.util.ArrayList;
import java.util.List;

public class ReclassifySubjectMappingVO {
    private List<String> originSubjectCodes;
    private String reclassifySubjectCode;

    public List<String> getOriginSubjectCodes() {
        if (null == this.originSubjectCodes) {
            this.originSubjectCodes = new ArrayList<String>();
        }
        return this.originSubjectCodes;
    }

    public void setOriginSubjectCodes(List<String> originSubjectCodes) {
        this.originSubjectCodes = originSubjectCodes;
    }

    public String getReclassifySubjectCode() {
        return this.reclassifySubjectCode;
    }

    public void setReclassifySubjectCode(String reclassifySubjectCode) {
        this.reclassifySubjectCode = reclassifySubjectCode;
    }
}

