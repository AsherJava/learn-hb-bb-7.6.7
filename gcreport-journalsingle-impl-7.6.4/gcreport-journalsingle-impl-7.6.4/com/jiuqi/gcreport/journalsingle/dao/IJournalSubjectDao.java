/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.journalsingle.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.journalsingle.entity.JournalSubjectEO;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface IJournalSubjectDao
extends IDbSqlGenericDAO<JournalSubjectEO, String> {
    public JournalSubjectEO getPreNodeByParentIdAndOrder(String var1, String var2);

    public JournalSubjectEO getNextNodeByParentIdAndOrder(String var1, String var2);

    public Integer deleteAllSubjects(String var1);

    public Serializable insertSubject(JournalSubjectEO var1);

    public Integer deleteSubject(String var1);

    public List<JournalSubjectEO> listDirectChildSubjects(String var1, int var2, int var3);

    public List<JournalSubjectEO> listAllChildSubjects(String var1, int var2, int var3);

    public int countDirectChildSubjects(String var1);

    public int countAllChildSubjects(String var1);

    public List<JournalSubjectEO> listAllSubjects(String var1);

    public String getSubjectTitleByCode(String var1, String var2);

    public JournalSubjectEO getSubjectEOByCode(String var1, String var2);

    public JournalSubjectEO getSubjectEOByZbCode(String var1);

    public Integer deleteSubjectBySchemeIdAndSubjectCode(String var1, Set<String> var2);

    public JournalSubjectEO getSubjectEOByZbId(String var1);

    public String generateNewParents(JournalSubjectEO var1);
}

