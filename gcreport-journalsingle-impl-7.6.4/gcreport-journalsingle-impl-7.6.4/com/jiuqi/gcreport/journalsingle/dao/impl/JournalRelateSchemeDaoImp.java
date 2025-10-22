/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.journalsingle.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.journalsingle.dao.IJournalRelateSchemeDao;
import com.jiuqi.gcreport.journalsingle.entity.JournalRelateSchemeEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class JournalRelateSchemeDaoImp
extends GcDbSqlGenericDAO<JournalRelateSchemeEO, String>
implements IJournalRelateSchemeDao {
    public JournalRelateSchemeDaoImp() {
        super(JournalRelateSchemeEO.class);
    }

    @Override
    public List<JournalRelateSchemeEO> listRelateSchemes() {
        String sql = "select %1$s \nfrom GC_Journal_RelateScheme  t \norder by t.createTime \n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByEntity(JournalRelateSchemeEO.class, (String)"t"));
        return this.selectEntity(sql, new Object[0]);
    }

    @Override
    public Integer deleteRelateScheme(String taskId, String schemeId, String adjustCode) {
        String sql = "\tdelete from GC_Journal_RelateScheme   \n  where taskId=?\n  and schemeId=?\n  and adjustType=?\n";
        return this.execute(sql, new Object[]{taskId, schemeId, adjustCode});
    }

    @Override
    public String getRelateSchemeId(String taskId, String schemeId, String adjustCode) {
        String sql = " select t.id AS ID \n from GC_Journal_RelateScheme  t \n  where t.taskId=?\n  and t.schemeId=?\n  and t.adjustType=?\n";
        List datas = this.selectFirstList(String.class, sql = String.format(sql, SqlUtils.getColumnsSqlByEntity(JournalRelateSchemeEO.class, (String)"t")), new Object[]{taskId, schemeId, adjustCode});
        if (datas.isEmpty() || datas.get(0) == null) {
            return "";
        }
        return (String)datas.get(0);
    }
}

