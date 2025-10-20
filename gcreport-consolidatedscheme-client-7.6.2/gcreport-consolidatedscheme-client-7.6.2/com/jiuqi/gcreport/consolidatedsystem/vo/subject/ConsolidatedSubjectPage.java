/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.subject;

import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import java.util.List;

public class ConsolidatedSubjectPage {
    private Integer total;
    private List<ConsolidatedSubjectVO> consolidatedSubjectVOS;

    public ConsolidatedSubjectPage() {
    }

    public ConsolidatedSubjectPage(Integer total, List<ConsolidatedSubjectVO> consolidatedSubjectVOS) {
        this.total = total;
        this.consolidatedSubjectVOS = consolidatedSubjectVOS;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<ConsolidatedSubjectVO> getConsolidatedSubjectVOS() {
        return this.consolidatedSubjectVOS;
    }

    public void setConsolidatedSubjectVOS(List<ConsolidatedSubjectVO> consolidatedSubjectVOS) {
        this.consolidatedSubjectVOS = consolidatedSubjectVOS;
    }
}

