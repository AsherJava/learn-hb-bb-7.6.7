/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 */
package com.jiuqi.nr.io.record.dao;

import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.io.record.bean.UnitFailureSubRecord;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

@Repository
public class UnitFailureSubRecordDao
extends BaseDao {
    public Class<UnitFailureSubRecord> getClz() {
        return UnitFailureSubRecord.class;
    }

    public void batchInsert(List<UnitFailureSubRecord> records) {
        super.insert(records.toArray());
    }

    public List<String> querySubRecordsByRecKey(String recKey, String factoryId, String dwKey) {
        List list = super.list(new String[]{"REC_KEY", "FACTORY_ID", "DW_KEY"}, new Object[]{recKey, factoryId, dwKey}, this.getClz());
        ArrayList<String> result = new ArrayList<String>();
        for (UnitFailureSubRecord record : list) {
            result.add(record.getDesc());
        }
        return result;
    }

    public void deleteByRecKeys(final List<String> recKeys) {
        StringBuffer sql = new StringBuffer("delete from ").append("NR_DX_UNIT_FAIL_SUB").append(" where ").append("REC_KEY").append("=?");
        this.jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setObject(1, recKeys.get(i));
            }

            public int getBatchSize() {
                return recKeys.size();
            }
        });
    }
}

