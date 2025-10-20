/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.gcreport.basedata.impl.dao.impl;

import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDO;
import com.jiuqi.gcreport.basedata.impl.dao.GcBaseDataDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class GcBaseDataDaoImpl
implements GcBaseDataDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<GcBaseData> queryBaseDataSimpleItems(String tableName) {
        String querySql = String.format("select b.ID,b.CODE,b.TITLE from %1$s b where b.recoveryflag = 0 ", tableName);
        return this.jdbcTemplate.query(querySql, (RowMapper)new RowMapper<GcBaseData>(){

            public GcBaseData mapRow(ResultSet rs, int rowNum) throws SQLException {
                GcBaseDataDO iBaseData = new GcBaseDataDO();
                iBaseData.setId(rs.getString(1));
                iBaseData.setCode(rs.getString(2));
                iBaseData.setTitle(rs.getString(3));
                return iBaseData;
            }
        });
    }

    @Override
    public GcBaseData queryBaseDataSimpleItem(String tableName, String dictCode) {
        String querySql = String.format("select b.ID,b.CODE,b.TITLE from %1$s b where b.code = ? AND b.recoveryflag = 0  ", tableName);
        return (GcBaseData)this.jdbcTemplate.query(querySql, new Object[]{dictCode}, (ResultSetExtractor)new ResultSetExtractor<GcBaseData>(){

            public GcBaseData extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    GcBaseDataDO iBaseData = new GcBaseDataDO();
                    iBaseData.setId(rs.getString(1));
                    iBaseData.setCode(rs.getString(2));
                    iBaseData.setTitle(rs.getString(3));
                    return iBaseData;
                }
                return null;
            }
        });
    }
}

