/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dao.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.AdjustPeriodSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.AdjustPeriodSettingEO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdjustPeriodSettingDaoImpl
implements AdjustPeriodSettingDao {
    private static final String FILED_STRING = " ID,FETCHSCHEMEID,ADJUSTPERIOD,STARTADJUSTPERIOD,ENDADJUSTPERIOD,ORDINAL ";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<AdjustPeriodSettingEO> loadAll() {
        String sql = "  SELECT  ID,FETCHSCHEMEID,ADJUSTPERIOD,STARTADJUSTPERIOD,ENDADJUSTPERIOD,ORDINAL  \n  FROM BDE_ADJUSTPERIODSETTING ORDER BY ORDINAL";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getAdjustPeriodSettingEO(rs));
    }

    @Override
    public List<AdjustPeriodSettingEO> listAdjustPeriodSettingByFetchSchemeId(String fetchSchemeId) {
        String sql = "  SELECT  ID,FETCHSCHEMEID,ADJUSTPERIOD,STARTADJUSTPERIOD,ENDADJUSTPERIOD,ORDINAL  \n  FROM BDE_ADJUSTPERIODSETTING \n WHERE  FETCHSCHEMEID = ?  ORDER BY ORDINAL";
        Object[] args = new Object[]{1};
        args[0] = fetchSchemeId;
        return this.jdbcTemplate.query(sql, args, (rs, row) -> this.getAdjustPeriodSettingEO(rs));
    }

    @Override
    public void deleteByFetchSchemeId(String fetchSchemeId) {
        String sql = "DELETE FROM  BDE_ADJUSTPERIODSETTING WHERE FETCHSCHEMEID = ? \n";
        this.jdbcTemplate.update(sql, new Object[]{fetchSchemeId});
    }

    @Override
    public void update(AdjustPeriodSettingEO adjustPeriodSettingEO) {
        String sql = "UPDATE BDE_ADJUSTPERIODSETTING SET ADJUSTPERIOD = ? ,STARTADJUSTPERIOD = ? ,ENDADJUSTPERIOD = ? WHERE ID = ? \n";
        this.jdbcTemplate.update(sql, new Object[]{adjustPeriodSettingEO.getAdjustPeriod(), adjustPeriodSettingEO.getStartAdjustPeriod(), adjustPeriodSettingEO.getEndAdjustPeriod(), adjustPeriodSettingEO.getId()});
    }

    @Override
    public void addBatch(List<AdjustPeriodSettingEO> adjustPeriodSettingEOS) {
        String sql = "  INSERT INTO BDE_ADJUSTPERIODSETTING \n (  ID,FETCHSCHEMEID,ADJUSTPERIOD,STARTADJUSTPERIOD,ENDADJUSTPERIOD,ORDINAL )\n values( ?,?,?,?,?,? )";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        long time = System.currentTimeMillis();
        for (int i = 0; i < adjustPeriodSettingEOS.size(); ++i) {
            AdjustPeriodSettingEO eo = adjustPeriodSettingEOS.get(i);
            if (StringUtils.isEmpty((String)eo.getId())) {
                eo.setId(UUIDUtils.newHalfGUIDStr());
            }
            Object[] args = new Object[]{eo.getId(), eo.getFetchSchemeId(), eo.getAdjustPeriod(), eo.getStartAdjustPeriod(), eo.getEndAdjustPeriod(), time + (long)i};
            argsList.add(args);
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }

    private AdjustPeriodSettingEO getAdjustPeriodSettingEO(ResultSet rs) throws SQLException {
        AdjustPeriodSettingEO eo = new AdjustPeriodSettingEO();
        eo.setId(rs.getString(1));
        eo.setFetchSchemeId(rs.getString(2));
        eo.setAdjustPeriod(rs.getString(3));
        eo.setStartAdjustPeriod(rs.getString(4));
        eo.setEndAdjustPeriod(rs.getString(5));
        return eo;
    }
}

