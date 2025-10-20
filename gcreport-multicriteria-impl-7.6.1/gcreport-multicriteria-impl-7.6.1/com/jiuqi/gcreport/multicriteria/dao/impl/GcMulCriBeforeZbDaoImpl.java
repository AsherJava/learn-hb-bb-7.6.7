/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.multicriteria.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.multicriteria.dao.GcMulCriBeforeZbDao;
import com.jiuqi.gcreport.multicriteria.entity.GcMulCriBeforeZbEO;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class GcMulCriBeforeZbDaoImpl
extends GcDbSqlGenericDAO<GcMulCriBeforeZbEO, String>
implements GcMulCriBeforeZbDao {
    public GcMulCriBeforeZbDaoImpl() {
        super(GcMulCriBeforeZbEO.class);
    }

    @Override
    public void deleteMulCriBeforeZb(List<String> mcids) {
        if (CollectionUtils.isEmpty(mcids)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(mcids, (String)"mcid");
        String sql = "   delete from GC_MULCRIBEFOREZB   \n   where " + inSql + " \n";
        this.execute(sql);
    }

    @Override
    public List<GcMulCriBeforeZbEO> queryMulCriBeforeDataByFormKey(String schemeId, String currFormKey) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_MULCRIBEFOREZB", (String)"t");
        StringBuilder sql = new StringBuilder();
        sql.append("  select " + allFieldsSQL + " from " + "GC_MULCRIBEFOREZB" + "  t \n");
        sql.append(" where t.schemeId=?\n");
        sql.append(" and t.beforeFormKey=?\n");
        return this.selectEntity(sql.toString(), new Object[]{schemeId, currFormKey});
    }

    @Override
    public List<GcMulCriBeforeZbEO> queryMulCriBeforeDataByMcids(List<String> mcids) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_MULCRIBEFOREZB", (String)"t");
        String inSql = SqlUtils.getConditionOfIdsUseOr(mcids, (String)"t.mcid");
        StringBuilder sql = new StringBuilder();
        sql.append("  select " + allFieldsSQL + " from " + "GC_MULCRIBEFOREZB" + "  t \n");
        sql.append(" where \n");
        sql.append(inSql + "\n");
        sql.append(" order by t.ordinal\n");
        return this.selectEntity(sql.toString(), new Object[0]);
    }

    @Override
    public Set<String> queryMulCriBeforeMcidsByFormKeys(String schemeId, List<String> formKeys) {
        StringBuilder sql = new StringBuilder();
        sql.append("  select t.mcid AS ID from GC_MULCRIBEFOREZB  t \n");
        sql.append(" where t.schemeId=?\n");
        if (formKeys != null) {
            String inSql = SqlUtils.getConditionOfIdsUseOr(formKeys, (String)"t.beforeFormKey");
            sql.append(" and ").append(inSql).append(" \n");
        }
        List datas = this.selectFirstList(String.class, sql.toString(), new Object[]{schemeId});
        return datas.stream().collect(Collectors.toSet());
    }

    @Override
    public Set<String> queryMcidsByBeforeZbKeys(String schemeId, Set<String> afterZbKeys) {
        StringBuilder sql = new StringBuilder();
        String inSql = SqlUtils.getConditionOfIdsUseOr(afterZbKeys, (String)"t.beforeZbKey");
        sql.append("select t.mcid AS ID from GC_MULCRIBEFOREZB  t \n");
        sql.append(" where t.schemeId=?\n");
        sql.append(" and ").append(inSql).append(" \n");
        List datas = this.selectFirstList(String.class, sql.toString(), new Object[]{schemeId});
        return datas.stream().collect(Collectors.toSet());
    }
}

