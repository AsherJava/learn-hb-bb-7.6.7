/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.consolidatedsystem.dao.subject;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.BoundSubjectEO;
import java.util.List;
import java.util.Map;

public interface BoundSubjectDao
extends IDbSqlGenericDAO<BoundSubjectEO, String> {
    public BoundSubjectEO getBoundSubjectByConsSubject(String var1, String var2, String var3);

    public List<BoundSubjectEO> getBoundSubjectListByConsSubject(String var1, String var2);

    public void saveBoundSubjects(List<BoundSubjectEO> var1);

    public String getBoundSubjectStr(String var1, String var2);

    public void deleteBoundByConsSubjectCode(List<String> var1, String var2);

    public void deleteBoundByConsSubjectId(List<String> var1, String var2);

    public List<BoundSubjectEO> getBoundSubjectListBySystemId(String var1);

    public Map<String, List<String>> getConsToBoundCodeMapBySystemId(String var1);
}

