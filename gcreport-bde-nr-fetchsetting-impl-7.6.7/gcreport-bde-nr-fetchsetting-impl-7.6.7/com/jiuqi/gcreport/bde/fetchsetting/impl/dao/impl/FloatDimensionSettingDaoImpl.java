/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatDimensionSettingEO
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dao.impl;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FloatDimensionSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FloatDimensionSettingEO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FloatDimensionSettingDaoImpl
implements FloatDimensionSettingDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String FILED_STRING = " id, formSchemeId, formId, regionId, dimensionConfigInfo";

    @Override
    public FloatDimensionSettingEO getSelectedFields(String regionId) {
        String sql = "  select \n id, formSchemeId, formId, regionId, dimensionConfigInfo      from BDE_DIMENSIONSETTING where 1 = 1 \n and REGIONID = ? \n";
        return (FloatDimensionSettingEO)this.jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                return null;
            }
            FloatDimensionSettingEO eo = new FloatDimensionSettingEO();
            eo.setId(rs.getString(1));
            eo.setFormSchemeId(rs.getString(2));
            eo.setFormId(rs.getString(3));
            eo.setRegionId(rs.getString(4));
            eo.setDimensionConfigInfo(rs.getString(5));
            return eo;
        }, new Object[]{regionId});
    }

    @Override
    public void save(String formSchemeId, String formId, String regionId, String fetchSourceRowSettingStr) {
        String sql = "  insert into  BDE_DIMENSIONSETTING \n (  id, formSchemeId, formId, regionId, dimensionConfigInfo)\n values( ?,?,?,?,?)";
        this.jdbcTemplate.update(sql, new Object[]{UUIDUtils.newHalfGUIDStr(), formSchemeId, formId, regionId, fetchSourceRowSettingStr});
    }

    @Override
    public void delete(String regionId) {
        String sql = "DELETE FROM  BDE_DIMENSIONSETTING WHERE REGIONID = ? \n";
        this.jdbcTemplate.update(sql, new Object[]{regionId});
    }
}

