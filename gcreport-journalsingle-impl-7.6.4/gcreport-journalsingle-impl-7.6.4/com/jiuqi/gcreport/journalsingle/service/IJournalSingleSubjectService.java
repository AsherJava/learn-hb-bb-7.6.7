/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.dto.Pagination
 *  com.jiuqi.gcreport.journalsingle.condition.JournalSubjectTreeCondition
 *  com.jiuqi.gcreport.journalsingle.vo.JournalSubjectTreeVO
 *  com.jiuqi.gcreport.journalsingle.vo.JournalSubjectVO
 */
package com.jiuqi.gcreport.journalsingle.service;

import com.jiuqi.gcreport.common.dto.Pagination;
import com.jiuqi.gcreport.journalsingle.condition.JournalSubjectTreeCondition;
import com.jiuqi.gcreport.journalsingle.entity.JournalSingleSchemeExcelModel;
import com.jiuqi.gcreport.journalsingle.entity.JournalSubjectEO;
import com.jiuqi.gcreport.journalsingle.vo.JournalSubjectTreeVO;
import com.jiuqi.gcreport.journalsingle.vo.JournalSubjectVO;
import java.util.List;

public interface IJournalSingleSubjectService {
    public void insertSubject(JournalSubjectVO var1);

    public void batchUpdateSubject(JournalSubjectVO[] var1);

    public void batchDeleteSubject(String[] var1);

    public Integer deleteAllSubjects(String var1);

    public void exchangeSort(String var1, int var2);

    public Pagination<JournalSubjectVO> listChildSubjectsOrSelf(String var1, boolean var2, int var3, int var4);

    public JournalSubjectEO getJournalSubjectEO(String var1);

    public String getSubjectTitleByCode(String var1, String var2);

    public JournalSubjectEO getSubjectEOByCode(String var1, String var2);

    public List<JournalSubjectTreeVO> listSubjectTree(String var1, String var2, boolean var3);

    public List<JournalSubjectTreeVO> listSubjectFilterTree(JournalSubjectTreeCondition var1);

    public List<JournalSubjectEO> listAllSubjects(String var1);

    public StringBuffer importSubject(List<JournalSingleSchemeExcelModel> var1, String var2);

    public JournalSubjectEO getSubjectEOByZbCode(String var1);

    public List<JournalSingleSchemeExcelModel> exportSubjectData(String var1, Boolean var2);
}

