/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package nr.single.map.configurations.dao.impl;

import java.sql.Timestamp;
import nr.single.map.configurations.bean.SingleFileInfo;
import nr.single.map.configurations.dao.FileInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class FileInfoDaoImpl
implements FileInfoDao {
    private static final String SQL_QUERY = "select * from %s where %s=?";
    private static final String TABLE_NAME = "sys_singlefile";
    private static final String S_ID = "s_id";
    private static final String S_JIO = "s_jio";
    private static final String S_ZB = "s_zb";
    private static final String S_ENTITY = "s_entity";
    private static final String S_FORMULA = "s_formula";
    private static final String S_UPLOADTIME = "s_uploadtime";
    @Autowired
    private JdbcTemplate template;

    @Override
    public void insert(SingleFileInfo info) {
        String sql = String.format("insert into %s (%s,%s,%s,%s,%s,%s) values(?,?,?,?,?,?)", TABLE_NAME, S_ID, S_JIO, S_ZB, S_ENTITY, S_FORMULA, S_UPLOADTIME);
        this.template.update(sql, new Object[]{info.getKey().toString(), info.getJioKey(), info.getZbKey(), info.getEntityKey(), info.getFormulaKey(), new Timestamp(System.currentTimeMillis())});
    }

    @Override
    public void updata(SingleFileInfo info) {
        String sql = String.format("update %s set %s=?,%s=?,%s=?,%s=?,%s=? where %s=?", TABLE_NAME, S_JIO, S_ZB, S_ENTITY, S_FORMULA, S_UPLOADTIME, S_ID);
        this.template.update(sql, new Object[]{info.getJioKey(), info.getZbKey(), info.getEntityKey(), info.getFormulaKey(), new Timestamp(System.currentTimeMillis()), info.getKey().toString()});
    }

    @Override
    public void deleteBykey(String key) {
        String sql = String.format("delete from %s where %s=?", TABLE_NAME, S_ID);
        this.template.update(sql, new Object[]{key});
    }

    @Override
    public SingleFileInfo query(String key) {
        String sql = String.format(SQL_QUERY, TABLE_NAME, S_ID);
        Object[] args = new Object[]{key};
        return (SingleFileInfo)this.template.query(sql, args, rs -> {
            if (rs.next()) {
                return SingleFileInfo.buildFileInfo(rs);
            }
            return null;
        });
    }
}

