/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.zbquery.dao;

import com.jiuqi.nr.zbquery.bean.ZBQueryGroup;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ZBQueryGroupDao {
    private static final String TABLENNAME = "NR_ZBQUERY_GROUP";
    private static final String ID = "QG_ID";
    private static final String TITLE = "QG_TITLE";
    private static final String PARENTID = "QG_PARENTID";
    private static final String MODIFYTIME = "QG_MODIFYTIME";
    private static final String QUERY_F_ZBQUERY_GROUP;
    private static final Function<ResultSet, ZBQueryGroup> ENTITY_READER_ZBQUERY_GROUP;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String addQueryGroup(ZBQueryGroup zBQueryGroup) {
        String SQL_INSERT = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES ( ?, ?, ?, ?)", TABLENNAME, ID, TITLE, PARENTID, MODIFYTIME);
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{zBQueryGroup.getId(), zBQueryGroup.getTitle(), zBQueryGroup.getParentId(), new Timestamp(zBQueryGroup.getModifyTime())});
        return zBQueryGroup.getId();
    }

    public String modifyQueryGroup(ZBQueryGroup zBQueryGroup) {
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ? ", TABLENNAME, TITLE, PARENTID, MODIFYTIME, ID);
        this.jdbcTemplate.update(SQL_UPDATE, new Object[]{zBQueryGroup.getTitle(), zBQueryGroup.getParentId(), new Timestamp(zBQueryGroup.getModifyTime()), zBQueryGroup.getId()});
        return zBQueryGroup.getId();
    }

    public void deleteQueryGroupById(String groupId) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLENNAME, ID);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{groupId});
    }

    public ZBQueryGroup getQueryGroupById(String groupId) {
        String SQL_QUERY_BY_ID = String.format("SELECT %s FROM %s WHERE %s = ?", QUERY_F_ZBQUERY_GROUP, TABLENNAME, ID);
        return (ZBQueryGroup)this.jdbcTemplate.query(SQL_QUERY_BY_ID, rs -> {
            if (rs.next()) {
                return ENTITY_READER_ZBQUERY_GROUP.apply(rs);
            }
            return null;
        }, new Object[]{groupId});
    }

    public List<ZBQueryGroup> getQueryGroupChildren(String parentId) {
        String SQL_QUERY_BY_PARENT = String.format("SELECT %s FROM %s WHERE %s = ? ORDER BY %s", QUERY_F_ZBQUERY_GROUP, TABLENNAME, PARENTID, TITLE);
        return this.jdbcTemplate.query(SQL_QUERY_BY_PARENT, (rs, row) -> ENTITY_READER_ZBQUERY_GROUP.apply(rs), new Object[]{parentId});
    }

    public List<ZBQueryGroup> getQueryGroupByTitle(String title, boolean fuzzy) {
        if (fuzzy) {
            String SQL_FUZZY_QUERY_BY_TITLE = String.format("SELECT %s FROM %s WHERE UPPER(%s) LIKE ?", QUERY_F_ZBQUERY_GROUP, TABLENNAME, TITLE);
            return this.jdbcTemplate.query(SQL_FUZZY_QUERY_BY_TITLE, (rs, row) -> ENTITY_READER_ZBQUERY_GROUP.apply(rs), new Object[]{"%" + title.toUpperCase() + "%"});
        }
        String SQL_QUERY_BY_TITLE = String.format("SELECT %s FROM %s WHERE %s = ?", QUERY_F_ZBQUERY_GROUP, TABLENNAME, TITLE);
        return this.jdbcTemplate.query(SQL_QUERY_BY_TITLE, (rs, row) -> ENTITY_READER_ZBQUERY_GROUP.apply(rs), new Object[]{title});
    }

    public List<ZBQueryGroup> getAllQueryGroup() {
        String SQL_QUERY_ALL = String.format("SELECT %s FROM %s ORDER BY %s", QUERY_F_ZBQUERY_GROUP, TABLENNAME, TITLE);
        return this.jdbcTemplate.query(SQL_QUERY_ALL, (rs, row) -> ENTITY_READER_ZBQUERY_GROUP.apply(rs));
    }

    static {
        StringBuilder builder = new StringBuilder();
        QUERY_F_ZBQUERY_GROUP = builder.append(ID).append(",").append(TITLE).append(",").append(PARENTID).append(",").append(MODIFYTIME).toString();
        ENTITY_READER_ZBQUERY_GROUP = rs -> {
            ZBQueryGroup zBQueryGroup = new ZBQueryGroup();
            int col = 1;
            try {
                zBQueryGroup.setId(rs.getString(col++));
                zBQueryGroup.setTitle(rs.getString(col++));
                zBQueryGroup.setParentId(rs.getString(col++));
                Timestamp modifyTime = rs.getTimestamp(col);
                if (modifyTime != null) {
                    zBQueryGroup.setModifyTime(modifyTime.getTime());
                }
            }
            catch (SQLException e) {
                throw new RuntimeException("read ZBQueryGroup group error.", e);
            }
            return zBQueryGroup;
        };
    }
}

