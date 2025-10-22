/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.journalsingle.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.journalsingle.entity.JournalSingleEO;
import java.util.List;

public interface IJournalSingleDao
extends IDbSqlGenericDAO<JournalSingleEO, String> {
    public void batchDeleteBySrcid(List<String> var1, int var2, int var3, int var4);

    public List<JournalSingleEO> findJournalSingleBySrcid(List<String> var1, int var2, int var3, int var4);

    public List<JournalSingleEO> listJournalSingleByDims(String var1, String var2, String var3, String var4, String var5, String var6);

    public Integer batchUpdatePostFlag(String var1, String var2, String var3, String var4, String var5);

    public Integer batchUpdatePostFlag(String var1, String var2, String var3, String var4, String var5, String var6);
}

