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
import com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckSchemeDao;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Repository
public class FinancialCheckSchemeDaoImpl
extends AbstractEntDbSqlGenericDAO<FinancialCheckSchemeEO>
implements FinancialCheckSchemeDao {
    public FinancialCheckSchemeDaoImpl() {
        super(FinancialCheckSchemeEO.class);
    }

    private String getAllFieldsSQL() {
        return SqlUtils.getNewColumnsSqlByEntity(FinancialCheckSchemeEO.class, (String)"scheme");
    }

    @Override
    public List<FinancialCheckSchemeEO> findByParentIdAndAcctYearOrderBySortOrder(String parentId, int year) {
        String columnSql = SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_FCSCHEME", (String)"scheme");
        String sql = "  select " + columnSql + "    from " + "GC_FCSCHEME" + "  scheme \n   where scheme.parentId = ? \n   order by scheme.sortOrder \n";
        return this.selectEntity(sql, new Object[]{parentId});
    }

    @Override
    public List<FinancialCheckSchemeEO> findByParentIdAndStartFlagOrderBySortOrder(String parentId, boolean startFlag) {
        String columnSql = SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_FCSCHEME", (String)"scheme");
        String sql = "  select " + columnSql + "    from " + "GC_FCSCHEME" + "  scheme \n   where scheme.parentId = ? \n     and scheme.SCHEMEENABLE = ? \n   order by scheme.sortOrder \n";
        return this.selectEntity(sql, new Object[]{parentId, startFlag});
    }

    @Override
    public void deleteByParentId(String parentId) {
        String sql = "  delete from GC_FCSCHEME   \n   where parentId = ?  or id = ?\n";
        this.execute(sql, new Object[]{parentId, parentId});
    }

    @Override
    public void startByParentId(String parentId, int startFlag) {
        String sql = "  update GC_FCSCHEME  scheme \n     set SCHEMEENABLE = ? \n   where scheme.id = ? or scheme.parentId = ?\n";
        this.execute(sql, new Object[]{startFlag, parentId, parentId});
    }

    @Override
    public Integer getMaxOrder() {
        String sql = "  select max(scheme.sortOrder)  NUM from GC_FCSCHEME  scheme";
        List re = this.selectFirstList(Integer.class, sql, new Object[0]);
        if (!re.isEmpty() && re.get(0) != null) {
            return (Integer)re.get(0);
        }
        return 0;
    }

    @Override
    public void start(String id, int startFlag) {
        String sql = "  update GC_FCSCHEME  scheme \n     set SCHEMEENABLE = ? \n   where scheme.id = ? \n";
        this.execute(sql, new Object[]{startFlag, id});
    }

    @Override
    public FinancialCheckSchemeEO getFrontScheme(String parentId, int acctYear, double sortOrder) {
        String columnSql = SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_FCSCHEME", (String)"scheme");
        String sql = "  select " + columnSql + "    from " + "GC_FCSCHEME" + "  scheme \n   where scheme.parentId = ? \n  and scheme.acctYear = ?      and scheme.sortOrder = (select max(s.sortOrder) from " + "GC_FCSCHEME" + "  s \n  where s.parentId = ? and s.acctYear = ? \n  and s.sortOrder < ?) \n";
        List list = this.selectEntity(sql, new Object[]{parentId, acctYear, parentId, acctYear, sortOrder});
        FinancialCheckSchemeEO scheme = null;
        if (!CollectionUtils.isEmpty(list)) {
            scheme = (FinancialCheckSchemeEO)((Object)list.get(0));
        }
        return scheme;
    }

    @Override
    public FinancialCheckSchemeEO getAfterScheme(String parentId, int acctYear, double sortOrder) {
        String columnSql = SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_FCSCHEME", (String)"scheme");
        String sql = "  select " + columnSql + "    from " + "GC_FCSCHEME" + "  scheme \n   where scheme.parentId = ? \n and scheme.acctYear = ?      and scheme.sortOrder = (select min(s.sortOrder) from " + "GC_FCSCHEME" + " s \n where s.parentId = ? and s.acctYear = ? \n and s.sortOrder > ?) \n";
        List list = this.selectEntity(sql, new Object[]{parentId, acctYear, parentId, acctYear, sortOrder});
        FinancialCheckSchemeEO scheme = null;
        if (!CollectionUtils.isEmpty(list)) {
            scheme = (FinancialCheckSchemeEO)((Object)list.get(0));
        }
        return scheme;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<FinancialCheckSchemeEO> getSchemeByIds(List<String> ids) {
        String columnSql = SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_FCSCHEME", (String)"scheme");
        TempTableCondition idInSql = SqlUtils.getNewConditionOfIds(ids, (String)"scheme.id");
        try {
            String sql = "  select " + columnSql + "    from " + "GC_FCSCHEME" + "  scheme \n where " + idInSql.getCondition() + " and scheme.SCHEMETYPE = 2 ";
            List list = this.selectEntity(sql, new Object[0]);
            return list;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)idInSql.getTempGroupId());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<FinancialCheckSchemeEO> getSchemeByIdsOrParentIds(List<String> ids) {
        String columnSql = SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_FCSCHEME", (String)"scheme");
        TempTableCondition newConditionOfIds = SqlUtils.getNewConditionOfIds(ids, (String)"scheme.id");
        TempTableCondition parentIdInSql = SqlUtils.getNewConditionOfIds(ids, (String)"scheme.parentId");
        try {
            String sql = "  select " + columnSql + "    from " + "GC_FCSCHEME" + "  scheme \n where (" + newConditionOfIds.getCondition() + " or " + parentIdInSql.getCondition() + ") and scheme.SCHEMETYPE = 2 ";
            List list = this.selectEntity(sql, new Object[0]);
            return list;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)newConditionOfIds.getTempGroupId());
            IdTemporaryTableUtils.deteteByGroupId((String)parentIdInSql.getTempGroupId());
        }
    }

    @Override
    public List<FinancialCheckSchemeEO> queryEnable(int acctYear, String parentId) {
        String columnSql = SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_FCSCHEME", (String)"scheme");
        ArrayList<Object> params = new ArrayList<Object>();
        String sql = "  select " + columnSql + "    from " + "GC_FCSCHEME" + "  scheme \n   where scheme.acctYear = ? \n     and (scheme.SCHEMEENABLE = 1 or scheme.SCHEMETYPE = 1)";
        params.add(acctYear);
        if (StringUtils.hasText(parentId)) {
            sql = sql + " and scheme.parentId = ?";
            params.add(parentId);
        }
        return this.selectEntity(sql, params);
    }
}

