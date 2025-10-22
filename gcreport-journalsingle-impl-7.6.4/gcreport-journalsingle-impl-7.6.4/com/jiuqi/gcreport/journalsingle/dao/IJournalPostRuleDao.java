/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.journalsingle.condition.JournalPostRuleCondition
 *  com.jiuqi.gcreport.journalsingle.vo.JournalPostReportVO
 */
package com.jiuqi.gcreport.journalsingle.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.journalsingle.condition.JournalPostRuleCondition;
import com.jiuqi.gcreport.journalsingle.entity.JournalPostRuleEO;
import com.jiuqi.gcreport.journalsingle.vo.JournalPostReportVO;
import java.util.List;

public interface IJournalPostRuleDao
extends IDbSqlGenericDAO<JournalPostRuleEO, String> {
    public List<JournalPostRuleEO> queryListByCondition(JournalPostRuleCondition var1);

    public void deleteByZbid(JournalPostReportVO var1, String var2);

    public void deleteRuleById(String var1);
}

