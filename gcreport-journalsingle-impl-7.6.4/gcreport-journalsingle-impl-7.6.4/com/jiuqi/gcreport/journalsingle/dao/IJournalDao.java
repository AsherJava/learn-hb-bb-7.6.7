/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.journalsingle.condition.JournalDetailCondition
 */
package com.jiuqi.gcreport.journalsingle.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.journalsingle.condition.JournalDetailCondition;
import com.jiuqi.gcreport.journalsingle.entity.JournalEO;
import java.util.List;

public interface IJournalDao
extends IDbSqlGenericDAO<JournalEO, String> {
    public void batchDeleteBySrcid(List<String> var1, int var2, int var3, int var4);

    public List<JournalEO> queryJournalByDims(String var1, String var2, String var3, String var4, String var5);

    public int pushPreDetails(JournalDetailCondition var1);
}

