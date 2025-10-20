/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.navigation.dao;

import com.jiuqi.navigation.dao.NavigationDao;
import com.jiuqi.navigation.entity.NavigationEO;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NavigationDaoImpl
implements NavigationDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<NavigationEO> loadAll() {
        String querySql = String.format("SELECT B.ID,B.RECVER,B.CODE,B.TITLE,B.CONFIGVALUE,B.BACKIMG from %1$s B ", "GC_NAVIGATIONCONFIG");
        return this.jdbcTemplate.query(querySql, (rs, rowNum) -> this.getNavigationEOFromResultSet(rs));
    }

    private NavigationEO getNavigationEOFromResultSet(ResultSet rs) throws SQLException {
        NavigationEO navigationEO = new NavigationEO();
        navigationEO.setId(rs.getString(1));
        navigationEO.setRecver(rs.getString(2));
        navigationEO.setCode(rs.getString(3));
        navigationEO.setTitle(rs.getString(4));
        navigationEO.setConfigValue(rs.getString(5));
        navigationEO.setBackImg(rs.getString(6));
        return navigationEO;
    }

    @Override
    public List<NavigationEO> findByExample(NavigationEO eo) {
        return null;
    }

    @Override
    public void save(NavigationEO eo) {
        String insertSql = String.format("insert into %1$s (id, code, title, recver, backimg, configvalue) values  (?,?,?,?,?,?)", "GC_NAVIGATIONCONFIG");
        int insert = this.jdbcTemplate.update(insertSql, ps -> {
            ps.setString(1, eo.getId());
            ps.setString(2, eo.getCode());
            ps.setString(3, eo.getTitle());
            ps.setLong(4, Long.parseLong(eo.getRecver()));
            StringReader reader = new StringReader(eo.getBackImg());
            StringReader reader2 = new StringReader(eo.getConfigValue());
            ps.setCharacterStream(5, reader);
            ps.setCharacterStream(6, reader2);
        });
        if (insert != 1) {
            throw new RuntimeException("\u65b0\u589e\u6570\u636e\u5931\u8d25");
        }
    }

    @Override
    public void update(NavigationEO eo) {
        String updateSql = String.format("update %1$s set CODE = ?,TITLE = ?,BACKIMG = ?,CONFIGVALUE = ? where ID = ?", "GC_NAVIGATIONCONFIG");
        int update = this.jdbcTemplate.update(updateSql, ps -> {
            ps.setString(1, eo.getCode());
            ps.setString(2, eo.getTitle());
            StringReader reader = new StringReader(eo.getBackImg());
            StringReader reader2 = new StringReader(eo.getConfigValue());
            ps.setCharacterStream(3, reader);
            ps.setCharacterStream(4, reader2);
            ps.setString(5, eo.getId());
        });
        if (update != 1) {
            throw new RuntimeException("\u66f4\u65b0\u5931\u8d25");
        }
    }

    @Override
    public List<NavigationEO> findByCode(String code) {
        String querySql = String.format("SELECT B.ID,B.RECVER,B.CODE,B.TITLE,B.CONFIGVALUE,B.BACKIMG from %1$s B  where B.CODE = ?", "GC_NAVIGATIONCONFIG");
        return this.jdbcTemplate.query(querySql, new Object[]{code}, (rs, rowNum) -> this.getNavigationEOFromResultSet(rs));
    }

    @Override
    public void deleteById(String id) {
        String deleteSql = String.format("delete from %1$s  where ID = ?", "GC_NAVIGATIONCONFIG");
        this.jdbcTemplate.update(deleteSql, new Object[]{id});
    }
}

