/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.bde.fetch.impl.dao.impl;

import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.gcreport.bde.fetch.impl.dao.GcFetchTaskLogClobDao;
import com.jiuqi.gcreport.bde.fetch.impl.entity.GcFetchTaskLogClobEO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GcFetchTaskLogClobDaoImpl
implements GcFetchTaskLogClobDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<GcFetchTaskLogClobEO> listById(List<String> idList) {
        StringBuffer sql = new StringBuffer();
        sql.append("  select id,CLOBINFO \n");
        sql.append("    from ").append("GC_FETCH_TASKLOGCLOB").append(" T \n");
        sql.append("   where ").append(SqlBuildUtil.getStrInCondi((String)"T.ID", idList));
        return this.jdbcTemplate.query(sql.toString(), (rs, row) -> {
            GcFetchTaskLogClobEO gcFetchTaskLogClobEO = new GcFetchTaskLogClobEO();
            gcFetchTaskLogClobEO.setId(rs.getString(1));
            gcFetchTaskLogClobEO.setClobContent(rs.getString(2));
            return gcFetchTaskLogClobEO;
        });
    }

    @Override
    public void save(GcFetchTaskLogClobEO fetchTaskLogClob) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append("GC_FETCH_TASKLOGCLOB").append(" (ID,CLOBINFO) \n");
        sql.append("VALUES (?, ?)");
        this.jdbcTemplate.update(sql.toString(), new Object[]{fetchTaskLogClob.getId(), fetchTaskLogClob.getClobContent()});
    }

    @Override
    public GcFetchTaskLogClobEO get(String requestTaskId) {
        StringBuffer sql = new StringBuffer();
        sql.append("  select CLOBINFO \n");
        sql.append("    from ").append("GC_FETCH_TASKLOGCLOB").append(" T \n");
        sql.append("   where id = ? ");
        return (GcFetchTaskLogClobEO)this.jdbcTemplate.query(sql.toString(), rs -> {
            if (rs.next()) {
                GcFetchTaskLogClobEO gcFetchTaskLogClobEO = new GcFetchTaskLogClobEO();
                gcFetchTaskLogClobEO.setId(requestTaskId);
                gcFetchTaskLogClobEO.setClobContent(rs.getString(1));
                return gcFetchTaskLogClobEO;
            }
            return null;
        }, new Object[]{requestTaskId});
    }
}

