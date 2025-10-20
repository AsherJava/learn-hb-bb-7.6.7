/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.aidocaudit.dao;

import com.jiuqi.gcreport.aidocaudit.eo.AidocauditRuleItemEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import java.util.List;

public interface IAidocauditRuleItemDao
extends IDbSqlGenericDAO<AidocauditRuleItemEO, String> {
    public List<AidocauditRuleItemEO> getByRuleId(String var1);
}

