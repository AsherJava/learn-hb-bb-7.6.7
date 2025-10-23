/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.summary.internal.dao.impl;

import com.jiuqi.nr.summary.internal.dao.ISummaryConfigDao;
import com.jiuqi.nr.summary.internal.entity.SummaryConfigDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SummaryConfigDaoImpl
implements ISummaryConfigDao {
    private static final String TABLENAME = "NR_SUMMARY_FUNCTION_CONFIG";
    private static final String SFC_KEY = "SFC_KEY";
    private static final String SFC_MENUID = "SFC_MENUID";
    private static final String SFC_CONFIG = "SFC_CONFIG";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(SummaryConfigDO configDO) {
        String sql = "insert into %s(%s,%s,%s) values(?,?,?)";
        sql = String.format(sql, TABLENAME, SFC_KEY, SFC_MENUID, SFC_CONFIG);
        this.jdbcTemplate.update(sql, new Object[]{configDO.getKey(), configDO.getMenuId(), configDO.getConfig()});
    }

    @Override
    public void update(SummaryConfigDO configDO) {
        String sql = "update %s set %s=? where %s=?";
        sql = String.format(sql, TABLENAME, SFC_CONFIG, SFC_MENUID);
        this.jdbcTemplate.update(sql, new Object[]{configDO.getConfig(), configDO.getMenuId()});
    }

    @Override
    public SummaryConfigDO queryByMenuId(String menuId) {
        String sql = "select * from NR_SUMMARY_FUNCTION_CONFIG where SFC_MENUID=?";
        try {
            return (SummaryConfigDO)this.jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                SummaryConfigDO configDO = new SummaryConfigDO();
                configDO.setKey(rs.getString(SFC_KEY));
                configDO.setMenuId(rs.getString(SFC_MENUID));
                configDO.setConfig(rs.getString(SFC_CONFIG));
                return configDO;
            }, new Object[]{menuId});
        }
        catch (Exception e) {
            return null;
        }
    }
}

