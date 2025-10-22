/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.movedata.ge.impl;

import com.jiuqi.nr.bpm.movedata.NrActvityGeneralByteArray;
import com.jiuqi.nr.bpm.movedata.ge.IActvityGeneralByteArrayDao;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ActvityGeneralByteArrayDao
implements IActvityGeneralByteArrayDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean batchInsert(final List<NrActvityGeneralByteArray> nrActvityGeneralByteArraies) {
        int[] results;
        String sql = this.generateInsertSql();
        for (int result : results = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NrActvityGeneralByteArray nrActvityGeneralByteArray = (NrActvityGeneralByteArray)nrActvityGeneralByteArraies.get(i);
                ps.setString(1, nrActvityGeneralByteArray.getId());
                ps.setBigDecimal(2, nrActvityGeneralByteArray.getRev());
                ps.setString(3, nrActvityGeneralByteArray.getName());
                ps.setString(4, nrActvityGeneralByteArray.getDeploymentId());
                ps.setBytes(5, nrActvityGeneralByteArray.getBytes());
                ps.setBigDecimal(6, nrActvityGeneralByteArray.getGenerated());
            }

            public int getBatchSize() {
                return nrActvityGeneralByteArraies.size();
            }
        })) {
            if (result == 1 || result == -2) continue;
            return false;
        }
        return true;
    }

    @Override
    public void deleteById(String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from ");
        sql.append("ACT_GE_BYTEARRAY where ");
        sql.append("ID_ = ? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{id});
    }

    @Override
    public List<NrActvityGeneralByteArray> queryById(String id) {
        ArrayList<NrActvityGeneralByteArray> result = new ArrayList<NrActvityGeneralByteArray>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        this.addSqlParam(sql);
        sql.append("from ACT_GE_BYTEARRAY where ID_ = ? ");
        List resultList = this.jdbcTemplate.queryForList(sql.toString(), new Object[]{id});
        for (Map map : resultList) {
            NrActvityGeneralByteArray nrActvityGeneralByteArray = new NrActvityGeneralByteArray();
            Object obj = map.get("ID_");
            if (obj != null) {
                nrActvityGeneralByteArray.setId(id);
            }
            if ((obj = map.get("REV_")) != null && obj instanceof BigDecimal) {
                BigDecimal rev = (BigDecimal)obj;
                nrActvityGeneralByteArray.setRev(rev);
            }
            if ((obj = map.get("NAME_")) != null) {
                nrActvityGeneralByteArray.setName(obj.toString());
            }
            if ((obj = map.get("DEPLOYMENT_ID_")) != null) {
                nrActvityGeneralByteArray.setDeploymentId(obj.toString());
            }
            if ((obj = map.get("BYTES_")) != null) {
                nrActvityGeneralByteArray.setBytes((byte[])obj);
            }
            if ((obj = map.get("GENERATED_")) != null && obj instanceof BigDecimal) {
                nrActvityGeneralByteArray.setGenerated((BigDecimal)obj);
            }
            result.add(nrActvityGeneralByteArray);
        }
        return result;
    }

    private String generateInsertSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ACT_GE_BYTEARRAY");
        sql.append("(");
        this.addSqlParam(sql);
        sql.append(") values (");
        sql.append("?,?,?,?,?,?");
        sql.append(")");
        return sql.toString();
    }

    private void addSqlParam(StringBuffer sql) {
        sql.append("ID_,");
        sql.append("REV_,");
        sql.append("NAME_,");
        sql.append("DEPLOYMENT_ID_,");
        sql.append("BYTES_,");
        sql.append("GENERATED_ ");
    }
}

