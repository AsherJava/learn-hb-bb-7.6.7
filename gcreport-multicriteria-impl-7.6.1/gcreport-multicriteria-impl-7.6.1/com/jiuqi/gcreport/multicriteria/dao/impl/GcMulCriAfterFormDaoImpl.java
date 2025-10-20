/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.multicriteria.dao.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.multicriteria.dao.GcMulCriAfterFormDao;
import com.jiuqi.gcreport.multicriteria.entity.GcMulCriAfterFormEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class GcMulCriAfterFormDaoImpl
extends GcDbSqlGenericDAO<GcMulCriAfterFormEO, String>
implements GcMulCriAfterFormDao {
    public GcMulCriAfterFormDaoImpl() {
        super(GcMulCriAfterFormEO.class);
    }

    @Override
    public void deleteMulCriAfterForms(String taskId, String schemeId) {
        String sql = "  delete  from GC_MULCRIAFTERFORM   \n where taskId=?\n and schemeId=?\n ";
        this.execute(sql, new Object[]{taskId, schemeId});
    }

    @Override
    public List<GcMulCriAfterFormEO> queryMulCriAfterForms(String taskId, String schemeId) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_MULCRIAFTERFORM", (String)"t");
        String sql = "  select " + allFieldsSQL + " from " + "GC_MULCRIAFTERFORM" + "  t \n where t.schemeId=?\n";
        if (!StringUtils.isEmpty((String)taskId)) {
            sql = sql + " and t.taskId ='" + taskId + "' \n";
        }
        return this.selectEntity(sql, new Object[]{schemeId});
    }
}

