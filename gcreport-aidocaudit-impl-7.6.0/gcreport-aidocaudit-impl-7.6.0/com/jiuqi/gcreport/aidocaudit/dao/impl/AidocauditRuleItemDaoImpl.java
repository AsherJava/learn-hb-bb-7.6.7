/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.aidocaudit.dao.impl;

import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditRuleItemDao;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditRuleItemEO;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AidocauditRuleItemDaoImpl
extends GcDbSqlGenericDAO<AidocauditRuleItemEO, String>
implements IAidocauditRuleItemDao {
    public AidocauditRuleItemDaoImpl() {
        super(AidocauditRuleItemEO.class);
    }

    @Override
    public List<AidocauditRuleItemEO> getByRuleId(String ruleId) {
        String sql = "SELECT %s FROM GC_AIDOCAUDIT_RULEITEM t WHERE t.RULEID = ? ORDER BY t.ORDINAL ASC";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_AIDOCAUDIT_RULEITEM", (String)"t");
        String formatSQL = String.format(sql, columns);
        return this.selectEntity(formatSQL, new Object[]{ruleId});
    }
}

