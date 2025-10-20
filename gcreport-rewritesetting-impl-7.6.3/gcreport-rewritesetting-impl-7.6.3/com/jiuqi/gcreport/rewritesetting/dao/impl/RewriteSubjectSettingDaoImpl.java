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
import com.jiuqi.gcreport.rewritesetting.dao.RewriteSubjectSettingDao;
import com.jiuqi.gcreport.rewritesetting.entity.RewriteSubjectSettingEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RewriteSubjectSettingDaoImpl
extends GcDbSqlGenericDAO<RewriteSubjectSettingEO, String>
implements RewriteSubjectSettingDao {
    public RewriteSubjectSettingDaoImpl() {
        super(RewriteSubjectSettingEO.class);
    }

    @Override
    public void deleteRewriteSubjectSetting(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(ids, (String)"id");
        String sql = "   delete from GC_REWRITE_SETTING_SUBJECT   \n   where " + inSql + " \n";
        this.execute(sql);
    }

    @Override
    public List<RewriteSubjectSettingEO> queryRewriteSubjectSettings(String schemeId) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_REWRITE_SETTING_SUBJECT", (String)"t");
        StringBuilder sql = new StringBuilder();
        sql.append("  select " + allFieldsSQL + " from " + "GC_REWRITE_SETTING_SUBJECT" + "  t \n");
        sql.append(" where t.schemeId='");
        sql.append(schemeId).append("'\n");
        return this.selectEntity(sql.toString(), new Object[0]);
    }

    @Override
    public void deleteRewriteSubjectSettingBySchemeId(String schemeId) {
        String sql = "   delete from GC_REWRITE_SETTING_SUBJECT   \n   where schemeId='" + schemeId + "' \n";
        this.execute(sql);
    }
}

