/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.basedata.api.itree.INode
 *  com.jiuqi.gcreport.transfer.vo.TransferColumnVo
 *  com.jiuqi.gcreport.unionrule.vo.SubjectITree
 */
package com.jiuqi.gcreport.inputdata.gcoppunit;

import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.basedata.api.itree.INode;
import com.jiuqi.gcreport.transfer.vo.TransferColumnVo;
import com.jiuqi.gcreport.unionrule.vo.SubjectITree;
import java.util.List;
import java.util.Map;

public class GcOppUnitSubjectVO {
    TransferColumnVo rule;
    private Map<String, List<TransferColumnVo>> subjectRegionRel;
    private List<SubjectITree<GcBaseDataVO>> subjects;
    private INode selectSubject;
    private List<GcBaseDataVO> subjectsByFilter;

    public TransferColumnVo getRule() {
        return this.rule;
    }

    public void setRule(TransferColumnVo rule) {
        this.rule = rule;
    }

    public Map<String, List<TransferColumnVo>> getSubjectRegionRel() {
        return this.subjectRegionRel;
    }

    public void setSubjectRegionRel(Map<String, List<TransferColumnVo>> subjectRegionRel) {
        this.subjectRegionRel = subjectRegionRel;
    }

    public List<SubjectITree<GcBaseDataVO>> getSubjects() {
        return this.subjects;
    }

    public void setSubjects(List<SubjectITree<GcBaseDataVO>> subjects) {
        this.subjects = subjects;
    }

    public INode getSelectSubject() {
        return this.selectSubject;
    }

    public void setSelectSubject(INode selectSubject) {
        this.selectSubject = selectSubject;
    }

    public List<GcBaseDataVO> getSubjectsByFilter() {
        return this.subjectsByFilter;
    }

    public void setSubjectsByFilter(List<GcBaseDataVO> subjectsByFilter) {
        this.subjectsByFilter = subjectsByFilter;
    }

    public String toString() {
        return "GcOppUnitSubjectVO{rule=" + this.rule + ", subjectRegionRel=" + this.subjectRegionRel + ", subjects=" + this.subjects + ", selectSubject=" + this.selectSubject + '}';
    }
}

