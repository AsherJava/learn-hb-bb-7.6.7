/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.rewritesetting.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.rewritesetting.dao.RewriteSettingDao;
import com.jiuqi.gcreport.rewritesetting.entity.RewriteSettingEO;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RewriteSettingDaoImpl
extends GcDbSqlGenericDAO<RewriteSettingEO, String>
implements RewriteSettingDao {
    public RewriteSettingDaoImpl() {
        super(RewriteSettingEO.class);
    }

    @Override
    public void deleteRewriteSetting(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(ids, (String)"rewSetGroupId");
        String sql = "   delete from GC_REWRITE_SETTING   \n   where " + inSql + " \n";
        this.execute(sql);
    }

    @Override
    public void deleteRewriteSettingByGroupId(String groupId) {
        String sql = "   delete from GC_REWRITE_SETTING   \n   where rewSetGroupId='" + groupId + "' \n";
        this.execute(sql);
    }

    @Override
    public List<RewriteSettingEO> queryRewriteSettings(String schemeId) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_REWRITE_SETTING", (String)"t");
        StringBuilder sql = new StringBuilder();
        sql.append(" select " + allFieldsSQL + " from " + "GC_REWRITE_SETTING" + "  t \n");
        sql.append(" where t.schemeId='");
        sql.append(schemeId).append("'\n");
        sql.append(" order by t.rewSetGroupId desc, t.ordinal asc").append("\n");
        return this.selectEntity(sql.toString(), new Object[0]);
    }

    @Override
    public RewriteSettingEO queryRewriteSettingsById(String id) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_REWRITE_SETTING", (String)"t");
        StringBuilder sql = new StringBuilder();
        sql.append(" select " + allFieldsSQL + " from " + "GC_REWRITE_SETTING" + "  t \n");
        sql.append(" where t.id = ?");
        List rewriteSettingEOS = this.selectEntity(sql.toString(), new Object[]{id});
        if (CollectionUtils.isEmpty((Collection)rewriteSettingEOS)) {
            return null;
        }
        return (RewriteSettingEO)((Object)rewriteSettingEOS.get(0));
    }
}

