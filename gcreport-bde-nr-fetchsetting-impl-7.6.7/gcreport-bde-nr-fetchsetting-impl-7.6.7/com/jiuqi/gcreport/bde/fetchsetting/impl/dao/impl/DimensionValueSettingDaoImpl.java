/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dao.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.DimensionValueSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.DimensionValueSettingEO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DimensionValueSettingDaoImpl
implements DimensionValueSettingDao {
    private static final String FILED_STRING = " id,fetchSchemeId,formSchemeId,formId,directionType,positionNum,rowGroupId,dimType,dimValue ";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void deleteBatchDimensionValueSetting(List<List<Object>> deleteWhereValues) {
        String sql = "DELETE FROM BDE_DIMVALUESETTING  \n WHERE FORMSCHEMEID = ? \n AND FETCHSCHEMEID = ? \n AND FORMID = ? \n AND DIRECTIONTYPE = ? \n AND POSITIONNUM = ? \n";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (List<Object> deleteWhereValue : deleteWhereValues) {
            argsList.add(deleteWhereValue.toArray());
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }

    @Override
    public List<DimensionValueSettingEO> listDimensionSetting(FetchSettingCond fetchSettingCond) {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,directionType,positionNum,rowGroupId,dimType,dimValue  \n  FROM BDE_DIMVALUESETTING  fs \n WHERE fs.FORMSCHEMEID = ? \n AND fs.FETCHSCHEMEID = ? \n AND fs.FORMID = ? \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getDimensionValueSettingEO(rs), new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId()});
    }

    private DimensionValueSettingEO getDimensionValueSettingEO(ResultSet rs) throws SQLException {
        DimensionValueSettingEO eo = new DimensionValueSettingEO();
        eo.setId(rs.getString(1));
        eo.setFetchSchemeId(rs.getString(2));
        eo.setFormSchemeId(rs.getString(3));
        eo.setFormId(rs.getString(4));
        eo.setDirectionType(rs.getString(5));
        eo.setPositionNum(rs.getString(6));
        eo.setRowGroupId(rs.getString(7));
        eo.setDimType(rs.getString(8));
        eo.setDimValue(rs.getString(9));
        return eo;
    }

    @Override
    public List<DimensionValueSettingEO> loadAll() {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,directionType,positionNum,rowGroupId,dimType,dimValue  \n  FROM BDE_DIMVALUESETTING  fs \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getDimensionValueSettingEO(rs));
    }

    @Override
    public void addBatch(List<DimensionValueSettingEO> dimensionValueSettingEOS) {
        String sql = "  insert into  BDE_DIMVALUESETTING \n (  id,fetchSchemeId,formSchemeId,formId,directionType,positionNum,rowGroupId,dimType,dimValue )\n values( ?,?,?,?,?,?,?,?,? )";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (DimensionValueSettingEO eo : dimensionValueSettingEOS) {
            if (StringUtils.isEmpty((String)eo.getId())) {
                eo.setId(UUIDUtils.newHalfGUIDStr());
            }
            Object[] args = new Object[]{eo.getId(), eo.getFetchSchemeId(), eo.getFormSchemeId(), eo.getFormId(), eo.getDirectionType(), eo.getPositionNum(), eo.getRowGroupId(), eo.getDimType(), eo.getDimValue()};
            argsList.add(args);
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }

    @Override
    public List<DimensionValueSettingEO> listFetchSettingByFetchSchemeId(String sourceId) {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,directionType,positionNum,rowGroupId,dimType,dimValue  \n  FROM BDE_DIMVALUESETTING  fs where fetchSchemeId = ?   \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getDimensionValueSettingEO(rs), new Object[]{sourceId});
    }

    @Override
    public void deleteByFetchSchemeId(String fetchSchemeId) {
        String sql = "DELETE FROM  BDE_DIMVALUESETTING WHERE FETCHSCHEMEID = ? \n";
        this.jdbcTemplate.update(sql, new Object[]{fetchSchemeId});
    }
}

