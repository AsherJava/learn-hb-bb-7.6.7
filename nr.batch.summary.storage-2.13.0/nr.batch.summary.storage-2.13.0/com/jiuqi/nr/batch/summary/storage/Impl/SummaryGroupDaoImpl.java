/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.NpRollbackException
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.batch.summary.storage.Impl;

import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.batch.summary.storage.NamedParameterSqlBuilder;
import com.jiuqi.nr.batch.summary.storage.SummaryGroupDao;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryGroup;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummaryGroupDefine;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SummaryGroupDaoImpl
implements SummaryGroupDao {
    private static final String BSG_KEY = "BSG_KEY";
    private static final String TABLE_NAME = "NR_BS_GROUP";
    private static final String[] columns = new String[]{"BSG_KEY", "BSG_TITLE", "BSG_PARENT", "BSG_TASK", "BSG_CREATOR", "BSG_UPDATE_TIME", "BSG_ORDINAL"};
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int insertRow(SummaryGroupDefine bsGroup) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.insertSQL(columns);
        MapSqlParameterSource source = this.buildMapSource(bsGroup);
        return this.executeUpdate(sqlBuilder.toString(), source);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int modifyRow(SummaryGroupDefine bsGroup) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.updateSQL((String[])Arrays.stream(columns).filter(e -> !e.equals(BSG_KEY)).toArray(String[]::new)).andWhere(BSG_KEY);
        MapSqlParameterSource source = this.buildMapSource(bsGroup);
        return this.executeUpdate(sqlBuilder.toString(), source);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int removeRow(String bsGroupKey) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.deleteSQL().andWhere(BSG_KEY);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(BSG_KEY, (Object)bsGroupKey);
        return this.executeUpdate(sqlBuilder.toString(), source);
    }

    @Override
    public int removeRow(List<String> bsGroupKeys) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.deleteSQL().inWhere(BSG_KEY);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(BSG_KEY, bsGroupKeys);
        return this.executeUpdate(sqlBuilder.toString(), source);
    }

    @Override
    public int renameGroup(String bsGroupKey, String name) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.updateSQL(new String[]{columns[1]}).andWhere(BSG_KEY);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[1], (Object)name);
        source.addValue(BSG_KEY, (Object)bsGroupKey);
        return this.executeUpdate(sqlBuilder.toString(), source);
    }

    @Override
    public int moveGroup(String parentGroup, List<String> childGroups) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.updateSQL(new String[]{columns[2]}).inWhere(BSG_KEY);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[2], (Object)parentGroup);
        source.addValue(BSG_KEY, childGroups);
        return this.executeUpdate(sqlBuilder.toString(), source);
    }

    @Override
    public SummaryGroup findGroup(String groupKey) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).andWhere(BSG_KEY);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(BSG_KEY, (Object)groupKey);
        List<SummaryGroup> groups = this.executeQuery(sqlBuilder.toString(), source);
        return groups.size() == 1 ? groups.get(0) : null;
    }

    @Override
    public List<SummaryGroup> findChildGroups(String task, String groupKey) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).andWhere(columns[2], columns[3], columns[4]).orderBy(columns[1]);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[2], (Object)groupKey);
        source.addValue(columns[3], (Object)task);
        source.addValue(columns[4], (Object)BatchSummaryUtils.getCurrentUserID());
        return this.executeQuery(sqlBuilder.toString(), source);
    }

    @Override
    public List<SummaryGroup> findAllChildGroups(String task, String groupKey) {
        ArrayList<SummaryGroup> gs = new ArrayList<SummaryGroup>();
        List<SummaryGroup> rootGroups = this.findChildGroups(task, groupKey);
        if (rootGroups != null && !rootGroups.isEmpty()) {
            Collections.reverse(rootGroups);
            Stack stack = new Stack();
            rootGroups.forEach(stack::push);
            while (!stack.isEmpty()) {
                SummaryGroup group = (SummaryGroup)stack.pop();
                gs.add(group);
                List<SummaryGroup> childrenGroups = this.findChildGroups(task, group.getKey());
                if (childrenGroups == null || childrenGroups.isEmpty()) continue;
                childrenGroups.forEach(stack::push);
            }
        }
        return gs;
    }

    private List<SummaryGroup> executeQuery(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, this::readGroupDefine);
    }

    private int executeUpdate(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)source);
    }

    private MapSqlParameterSource buildMapSource(SummaryGroup bsGroup) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(columns[0], (Object)bsGroup.getKey());
        source.addValue(columns[1], (Object)bsGroup.getTitle());
        source.addValue(columns[2], (Object)bsGroup.getParent());
        source.addValue(columns[3], (Object)bsGroup.getTask());
        source.addValue(columns[4], (Object)bsGroup.getCreator());
        source.addValue(columns[5], (Object)bsGroup.getUpdateTime());
        source.addValue(columns[6], (Object)bsGroup.getOrdinal());
        return source;
    }

    private SummaryGroup readGroupDefine(ResultSet rs, int rowIdx) throws SQLException {
        SummaryGroupDefine impl = new SummaryGroupDefine();
        impl.setKey(rs.getString(columns[0]));
        impl.setTitle(rs.getString(columns[1]));
        impl.setParent(rs.getString(columns[2]));
        impl.setTask(rs.getString(columns[3]));
        impl.setCreator(rs.getString(columns[4]));
        impl.setUpdateTime(this.translate2Date(rs.getTimestamp(columns[5])));
        impl.setOrdinal(rs.getString(columns[6]));
        return impl;
    }

    private Date translate2Date(Timestamp timestamp) {
        if (timestamp != null) {
            return new Date(timestamp.getTime());
        }
        return null;
    }
}

