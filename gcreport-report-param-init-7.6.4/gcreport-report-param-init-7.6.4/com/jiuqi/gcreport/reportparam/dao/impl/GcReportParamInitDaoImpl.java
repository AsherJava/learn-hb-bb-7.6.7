/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.reportparam.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.reportparam.dao.GcReportParamInitDao;
import com.jiuqi.gcreport.reportparam.eo.GcReportParamInitEO;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GcReportParamInitDaoImpl
extends GcDbSqlGenericDAO<GcReportParamInitEO, String>
implements GcReportParamInitDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public GcReportParamInitDaoImpl() {
        super(GcReportParamInitEO.class);
    }

    @Override
    public GcReportParamInitEO selectByName(String paramFileName) {
        String sql = " SELECT %1$s FROM %2$s T WHERE PARAM_NAME = ?  ";
        sql = String.format(sql, SqlUtils.getColumnsSqlByEntity(GcReportParamInitEO.class, (String)"T"), "GC_REPORTPARAMINIT");
        List gcReportParamInitEOS = this.selectEntity(sql, new Object[]{paramFileName});
        if (!CollectionUtils.isEmpty((Collection)gcReportParamInitEOS)) {
            return (GcReportParamInitEO)((Object)gcReportParamInitEOS.get(0));
        }
        return null;
    }

    @Override
    public int queryOrgCount(String orgType) {
        String sql = "select count(*) from " + orgType;
        Integer i = (Integer)this.jdbcTemplate.queryForObject(sql, Integer.class);
        return i == null ? 0 : i;
    }

    @Override
    public List<String> queryAllOrgTypes() {
        String sql = "select name from md_org_category";
        return this.jdbcTemplate.queryForList(sql, String.class);
    }
}

