/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.aidocaudit.dao;

import com.jiuqi.gcreport.aidocaudit.eo.AidocauditRuleEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import java.util.List;

public interface IAidocauditRuleDao
extends IDbSqlGenericDAO<AidocauditRuleEO, String> {
    public List<AidocauditRuleEO> queryListByIds(List<String> var1);

    public AidocauditRuleEO getByScoreTmplId(String var1);

    public List<AidocauditRuleEO> list(Integer var1);

    public List<AidocauditRuleEO> queryByRuleAttachIdAndStatus(String var1);
}

