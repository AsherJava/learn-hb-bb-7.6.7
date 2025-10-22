/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 */
package com.jiuqi.gcreport.financialcheckapi.offset;

import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.financialcheckapi.offset.MdAgingDTO;
import java.util.List;

public class SubjectInfoVO
extends ConsolidatedSubjectVO {
    private List<MdAgingDTO> mdAgingList;

    public List<MdAgingDTO> getMdAgingList() {
        return this.mdAgingList;
    }

    public void setMdAgingList(List<MdAgingDTO> mdAgingList) {
        this.mdAgingList = mdAgingList;
    }
}

