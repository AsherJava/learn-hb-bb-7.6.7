/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaConditionVO
 */
package com.jiuqi.gcreport.multicriteria.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaConditionVO;
import com.jiuqi.gcreport.multicriteria.dao.GcMulCriAfterAmtDao;
import com.jiuqi.gcreport.multicriteria.dao.GcMulCriAfterZbDao;
import com.jiuqi.gcreport.multicriteria.entity.GcMulCriAfterZbEO;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GcMulCriAfterZbDaoImpl
extends GcDbSqlGenericDAO<GcMulCriAfterZbEO, String>
implements GcMulCriAfterZbDao {
    @Autowired
    private GcMulCriAfterAmtDao gcMulCriAfterAmtDao;

    public GcMulCriAfterZbDaoImpl() {
        super(GcMulCriAfterZbEO.class);
    }

    @Override
    public void deleteMulCriAfterZb(List<String> mcids) {
        if (CollectionUtils.isEmpty(mcids)) {
            return;
        }
        Set<String> ids = this.queryIdsByMcids(mcids);
        this.gcMulCriAfterAmtDao.deleteByIds(ids);
        String inSql = SqlUtils.getConditionOfIdsUseOr(mcids, (String)"mcid");
        String sql = "   delete from GC_MULCRIAFTERZB   \n   where " + inSql + " \n";
        this.execute(sql);
    }

    @Override
    public List<GcMulCriAfterZbEO> queryMulCriAfterDataByFormKey(String schemeId, Set<String> afterFormKeys) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_MULCRIAFTERZB", (String)"t");
        String inSql = SqlUtils.getConditionOfIdsUseOr(afterFormKeys, (String)"t.afterFormKey");
        StringBuilder sql = new StringBuilder();
        sql.append("  select " + allFieldsSQL + " from " + "GC_MULCRIAFTERZB" + "  t \n");
        sql.append(" where t.schemeId=?\n");
        sql.append(" and ").append(inSql).append(" \n");
        sql.append(" order by t.ordinal\n");
        return this.selectEntity(sql.toString(), new Object[]{schemeId});
    }

    @Override
    public Set<String> queryMcidsOfAfterAmtIsNotNull(GcMultiCriteriaConditionVO condition) {
        StringBuilder sql = new StringBuilder();
        sql.append("  select t.mcid AS ID from GC_MULCRIAFTERZB  t \n");
        sql.append(" where t.taskId=?\n");
        sql.append(" and t.schemeId=?\n");
        sql.append(" and t.afterFormKey=?\n");
        sql.append(" and t.afterZbAmt is null\n");
        List datas = this.selectFirstList(String.class, sql.toString(), new Object[]{condition.getTaskId(), condition.getSchemeId(), condition.getCurrFormKey()});
        return datas.stream().collect(Collectors.toSet());
    }

    @Override
    public List<GcMulCriAfterZbEO> queryMulCriAfterDataByIds(List<String> ids) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_MULCRIAFTERZB", (String)"i");
        String inSql = SqlUtils.getConditionOfIdsUseOr(ids, (String)"i.ID");
        String sql = "select " + allFieldsSQL + " from " + "GC_MULCRIAFTERZB" + "  i \nwhere " + inSql;
        return this.selectEntity(sql, new Object[0]);
    }

    @Override
    public Set<String> queryMulCriAfterDataByFormKeys(String schemeId, List<String> formKeys) {
        StringBuilder sql = new StringBuilder();
        sql.append("  select t.mcid AS ID from GC_MULCRIAFTERZB  t \n");
        sql.append(" where t.schemeId=?\n");
        if (formKeys != null) {
            String inSql = SqlUtils.getConditionOfIdsUseOr(formKeys, (String)"t.afterFormKey");
            sql.append(" and ").append(inSql).append(" \n");
        }
        List datas = this.selectFirstList(String.class, sql.toString(), new Object[]{schemeId});
        return datas.stream().collect(Collectors.toSet());
    }

    @Override
    public Set<String> queryMcidsByAfterZbKeys(String schemeId, Set<String> afterZbKeys) {
        StringBuilder sql = new StringBuilder();
        String inSql = SqlUtils.getConditionOfIdsUseOr(afterZbKeys, (String)"t.afterZbKey");
        sql.append("  select t.mcid AS ID from GC_MULCRIAFTERZB  t \n");
        sql.append(" where t.schemeId=?\n");
        sql.append(" and ").append(inSql).append(" \n");
        List datas = this.selectFirstList(String.class, sql.toString(), new Object[]{schemeId});
        return datas.stream().collect(Collectors.toSet());
    }

    @Override
    public Set<String> queryIdsByMcids(List<String> mcids) {
        StringBuilder sql = new StringBuilder();
        String inSql = SqlUtils.getConditionOfIdsUseOr(mcids, (String)"t.mcid");
        sql.append("  select t.id AS ID from GC_MULCRIAFTERZB  t \n");
        sql.append(" where ").append(inSql).append(" \n");
        List datas = this.selectFirstList(String.class, sql.toString(), new Object[0]);
        return datas.stream().collect(Collectors.toSet());
    }
}

