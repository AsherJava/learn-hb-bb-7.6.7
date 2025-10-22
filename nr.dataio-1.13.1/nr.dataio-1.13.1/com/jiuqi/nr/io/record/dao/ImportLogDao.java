/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 */
package com.jiuqi.nr.io.record.dao;

import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.io.record.bean.ImportLog;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

@Repository
public class ImportLogDao
extends BaseDao {
    public Class<ImportLog> getClz() {
        return ImportLog.class;
    }

    public void insert(ImportLog importLog) {
        super.insert((Object)importLog);
    }

    public List<ImportLog> queryByRecKey(String recKey) {
        return super.list(new String[]{"REC_KEY"}, new Object[]{recKey}, this.getClz());
    }

    public List<ImportLog> queryByRecKeyAndFactory(String recKey, String factoryId) {
        return super.list(new String[]{"REC_KEY", "FACTORY_ID"}, new Object[]{recKey, factoryId}, this.getClz());
    }

    public void update(ImportLog importLog) {
        super.update((Object)importLog);
    }

    public void deleteByRecKey(final List<String> recKeys) {
        StringBuffer sql = new StringBuffer("delete from ").append("NR_DX_LOG_TABLE").append(" where ").append("REC_KEY").append("=?");
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

