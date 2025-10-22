/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.common.ReturnObject
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectTreeVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.unionrule.vo.SubjectITree
 */
package com.jiuqi.gcreport.consolidatedsystem.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.common.ReturnObject;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectTreeVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.unionrule.vo.SubjectITree;
import java.util.List;
import java.util.Map;

public interface ConsolidatedSubjectUIService {
    public void clearCache();

    public ConsolidatedSubjectVO saveSubject(ConsolidatedSubjectVO var1);

    public List<ConsolidatedSubjectVO> saveSubjects(List<ConsolidatedSubjectVO> var1);

    public void updateSubject(ConsolidatedSubjectVO var1);

    public void exchangeSort(String var1, String var2);

    public void exchangeSort(String var1, Integer var2);

    public void enableSubject(String var1, boolean var2);

    public void matchEndZb(String var1, String var2, String var3, boolean var4, List<String> var5);

    public void deleteSubjects(String[] var1);

    public void deleteSubjectsBySystemId(String var1);

    public void deleteSubjectsBySystemId(String var1, List<String> var2);

    public ConsolidatedSubjectVO getSubjectById(String var1);

    public ConsolidatedSubjectTreeVO getSubjectTree(String var1);

    public List<ConsolidatedSubjectVO> listSubjectsBySearchKey(String var1, String var2);

    public List<SubjectITree<GcBaseDataVO>> listSubjectTree(Map<String, Object> var1);

    public PageInfo listSubjects(String var1, String var2, Boolean var3, Integer var4, Integer var5);

    public ReturnObject checkSubjects(List<ConsolidatedSubjectEO> var1);

    public List<String> listExistCodes(String var1, List<String> var2);

    public List<SelectOptionVO> listAllSubjectsWithOption(String var1);
}

