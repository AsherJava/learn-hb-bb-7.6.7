/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 */
package com.jiuqi.gcreport.financialcheckcore.scheme.dao.impl;

import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckProjectDao;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckProjectEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class FinancialCheckProjectDaoImpl
extends AbstractEntDbSqlGenericDAO<FinancialCheckProjectEO>
implements FinancialCheckProjectDao {
    public FinancialCheckProjectDaoImpl() {
        super(FinancialCheckProjectEO.class);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deleteByIds(List<String> ids) {
        TempTableCondition newConditionOfIds = SqlUtils.getNewConditionOfIds(ids, (String)"id");
        String sql = "delete from GC_FCPROJECT where " + newConditionOfIds.getCondition();
        try {
            this.execute(sql);
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)newConditionOfIds.getTempGroupId());
        }
    }

    @Override
    public void deleteBySchemeId(String schemeId) {
        String sql = " delete from GC_FCPROJECT where schemeId = ? ";
        this.execute(sql, new Object[]{schemeId});
    }

    @Override
    public List<FinancialCheckProjectEO> selectBySchemeId(String schemeId) {
        String columnsSqlByTableDefine = SqlUtils.getColumnsSqlByTableDefine((String)"GC_FCPROJECT", (String)"");
        String sql = " select " + columnsSqlByTableDefine + " from " + "GC_FCPROJECT" + " where SCHEMEID = ? order by SORTORDER ";
        return this.selectEntity(sql, new Object[]{schemeId});
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<FinancialCheckProjectEO> selectBySchemeIds(List<String> schemeIds) {
        String columnsSqlByTableDefine = SqlUtils.getColumnsSqlByTableDefine((String)"GC_FCPROJECT", (String)"");
        TempTableCondition idInSql = SqlUtils.getNewConditionOfIds(schemeIds, (String)"SCHEMEID");
        String sql = " select " + columnsSqlByTableDefine + " from " + "GC_FCPROJECT" + " where  " + idInSql.getCondition();
        try {
            List list = this.selectEntity(sql, new Object[0]);
            return list;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)idInSql.getTempGroupId());
        }
    }
}

