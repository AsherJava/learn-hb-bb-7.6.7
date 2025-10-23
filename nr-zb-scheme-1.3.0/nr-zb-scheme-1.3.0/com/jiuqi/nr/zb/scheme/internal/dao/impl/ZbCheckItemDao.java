/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.zb.scheme.internal.dao.impl;

import com.jiuqi.nr.zb.scheme.internal.dao.IZbCheckItemDao;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbCheckItemDO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Repository
public class ZbCheckItemDao
implements IZbCheckItemDao {
    private static final String[] COLUMN_NAMES = new String[]{"ZCR_KEY", "ZCR_ZC_KEY", "ZCR_DF_KEY", "ZCR_ZB_KEY", "ZCR_DF_PATH", "ZCR_DIFF_TYPE", "ZCR_OPER_TYPE", "ZCR_FC_KEY", "ZCR_FG_KEY", "ZCR_FM_KEY", "ZCR_UPDATE_TIME", "ZCR_ORDER"};
    private static final String SQL_INSERT = String.format("insert into %s (%s) values (?,?,?,?,?,?,?,?,?,?,?,?)", "NR_ZB_CHECK_RESULT", String.join((CharSequence)",", COLUMN_NAMES));
    private static final String SQL_DELETEBYCHECK = String.format("delete from %s where %s=?", "NR_ZB_CHECK_RESULT", "ZCR_ZC_KEY");
    private static final String SQL_DELETEBYEXPIRETIME = String.format("delete from %s where %s < ?", "NR_ZB_CHECK_RESULT", "ZCR_UPDATE_TIME");
    private static final String SQL_QUERYBYCHECEK = String.format("select * from %s where %s=? order by %s", "NR_ZB_CHECK_RESULT", "ZCR_ZC_KEY", "ZCR_ORDER");
    private static final String SQL_QUERYBYCHECK_FORMGROUP = String.format("select * from %s where %s=? and %s=? order by %s", "NR_ZB_CHECK_RESULT", "ZCR_ZC_KEY", "ZCR_FG_KEY", "ZCR_ORDER");
    private static final String SQL_QUERYBYCHECK_FORM = String.format("select * from %s where %s=? and %s=? order by %s", "NR_ZB_CHECK_RESULT", "ZCR_ZC_KEY", "ZCR_FM_KEY", "ZCR_ORDER");
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(List<ZbCheckItemDO> zbCheckItemDOList) {
        if (CollectionUtils.isEmpty(zbCheckItemDOList)) {
            return;
        }
        this.jdbcTemplate.batchUpdate(SQL_INSERT, zbCheckItemDOList, 1000, (ps, item) -> {
            ps.setString(1, item.getKey());
            ps.setString(2, item.getCheckKey());
            ps.setString(3, item.getDataFieldKey());
            ps.setString(4, item.getZbInfoKey());
            ps.setString(5, item.getDataFieldPath());
            ps.setInt(6, item.getDiffType());
            ps.setInt(7, item.getOperType());
            ps.setString(8, item.getFormSchemeKey());
            ps.setString(9, item.getFormGroupKey());
            ps.setString(10, item.getFormKey());
            ps.setTimestamp(11, Timestamp.from(item.getUpdateTime()));
            ps.setString(12, item.getOrder());
        });
    }

    @Override
    public int deleteByCheck(String checkKey) {
        if (!StringUtils.hasLength(checkKey)) {
            return 0;
        }
        return this.jdbcTemplate.update(SQL_DELETEBYCHECK, new Object[]{checkKey});
    }

    @Override
    public int deleteByExpireTime(long expireTime) {
        return this.jdbcTemplate.update(SQL_DELETEBYEXPIRETIME, new Object[]{Timestamp.from(Instant.ofEpochMilli(expireTime))});
    }

    @Override
    public List<ZbCheckItemDO> listByCheck(String checkKey) {
        return this.jdbcTemplate.query(SQL_QUERYBYCHECEK, (RowMapper)ZbCheckItemRowMapper.INSTANCE, new Object[]{checkKey});
    }

    @Override
    public List<ZbCheckItemDO> listByCheckAndFormGroup(String checkKey, String formGroupKey) {
        return this.jdbcTemplate.query(SQL_QUERYBYCHECK_FORMGROUP, (RowMapper)ZbCheckItemRowMapper.INSTANCE, new Object[]{checkKey, formGroupKey});
    }

    @Override
    public List<ZbCheckItemDO> listByCheckAndForm(String checkKey, String formKey) {
        return this.jdbcTemplate.query(SQL_QUERYBYCHECK_FORM, (RowMapper)ZbCheckItemRowMapper.INSTANCE, new Object[]{checkKey, formKey});
    }

    static class ZbCheckItemRowMapper
    implements RowMapper<ZbCheckItemDO> {
        public static final ZbCheckItemRowMapper INSTANCE = new ZbCheckItemRowMapper();

        ZbCheckItemRowMapper() {
        }

        public ZbCheckItemDO mapRow(ResultSet result, int index) throws SQLException {
            int i = 1;
            ZbCheckItemDO item = new ZbCheckItemDO();
            item.setKey(result.getString(i));
            item.setCheckKey(result.getString(++i));
            item.setDataFieldKey(result.getString(++i));
            item.setZbInfoKey(result.getString(++i));
            item.setDataFieldPath(result.getString(++i));
            item.setDiffType(result.getInt(++i));
            item.setOperType(result.getInt(++i));
            item.setFormSchemeKey(result.getString(++i));
            item.setFormGroupKey(result.getString(++i));
            item.setFormKey(result.getString(++i));
            item.setUpdateTime(Instant.ofEpochMilli(result.getTimestamp(++i).getTime()));
            item.setOrder(result.getString(++i));
            return item;
        }
    }
}

