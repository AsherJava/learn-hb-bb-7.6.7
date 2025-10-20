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
import com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckMappingDao;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckMappingEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class FinancialCheckMappingDaoImpl
extends AbstractEntDbSqlGenericDAO<FinancialCheckMappingEO>
implements FinancialCheckMappingDao {
    public FinancialCheckMappingDaoImpl() {
        super(FinancialCheckMappingEO.class);
    }

    @Override
    public void deleteBySchemeId(String schemeId) {
        String sql = " delete from GC_FCPROJECTMAPPING where schemeId = ? ";
        this.execute(sql, new Object[]{schemeId});
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<FinancialCheckMappingEO> selectBySchemeIds(List<String> schemeIds) {
        String columnsSqlByTableDefine = SqlUtils.getColumnsSqlByTableDefine((String)"GC_FCPROJECTMAPPING", (String)"");
        TempTableCondition conditionOfIds = SqlUtils.getNewConditionOfIds(schemeIds, (String)"schemeId");
        try {
            String sql = " select " + columnsSqlByTableDefine + " from " + "GC_FCPROJECTMAPPING" + " where " + conditionOfIds.getCondition();
            List list = this.selectEntity(sql, new Object[0]);
            return list;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)conditionOfIds.getTempGroupId());
        }
    }
}

