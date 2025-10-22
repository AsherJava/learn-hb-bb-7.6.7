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
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.DimensionSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.DimensionSettingEO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DimensionSettingDaoImpl
implements DimensionSettingDao {
    private static final String FILED_STRING = " id,fetchSchemeId,formSchemeId,formId,globalDim,globalDimValue,rowDim,colDim,directionType,positionNum,dimSetting ";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void deleteBatchDimensionSetting(List<List<Object>> deleteWhereValues) {
        String sql = "DELETE FROM BDE_DIMSETTING  \n WHERE FORMSCHEMEID = ? \n AND FETCHSCHEMEID = ? \n AND FORMID = ? \n AND DIRECTIONTYPE = ? \n AND POSITIONNUM = ? \n";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (List<Object> deleteWhereValue : deleteWhereValues) {
            argsList.add(deleteWhereValue.toArray());
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }

    @Override
    public List<DimensionSettingEO> listDimensionSetting(FetchSettingCond fetchSettingCond) {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,globalDim,globalDimValue,rowDim,colDim,directionType,positionNum,dimSetting  \n  FROM BDE_DIMSETTING  fs \n WHERE fs.FORMSCHEMEID = ? \n AND fs.FETCHSCHEMEID = ? \n AND fs.FORMID = ? \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getDimensionSettingEO(rs), new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId()});
    }

    private DimensionSettingEO getDimensionSettingEO(ResultSet rs) throws SQLException {
        DimensionSettingEO eo = new DimensionSettingEO();
        eo.setId(rs.getString(1));
        eo.setFetchSchemeId(rs.getString(2));
        eo.setFormSchemeId(rs.getString(3));
        eo.setFormId(rs.getString(4));
        eo.setGlobalDim(rs.getString(5));
        eo.setGlobalDimValue(rs.getString(6));
        eo.setRowDim(rs.getString(7));
        eo.setColDim(rs.getString(8));
        eo.setDirectionType(rs.getString(9));
        eo.setPositionNum(rs.getString(10));
        eo.setDimSetting(rs.getString(11));
        return eo;
    }

    @Override
    public List<DimensionSettingEO> loadAll() {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,globalDim,globalDimValue,rowDim,colDim,directionType,positionNum,dimSetting  \n  FROM BDE_DIMSETTING  fs \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getDimensionSettingEO(rs));
    }

    @Override
    public void addBatch(List<DimensionSettingEO> dimensionSettingEOS) {
        String sql = "  insert into  BDE_DIMSETTING \n (  id,fetchSchemeId,formSchemeId,formId,globalDim,globalDimValue,rowDim,colDim,directionType,positionNum,dimSetting )\n values( ?,?,?,?,?,?,?,?,?,?,? )";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (DimensionSettingEO eo : dimensionSettingEOS) {
            if (StringUtils.isEmpty((String)eo.getId())) {
                eo.setId(UUIDUtils.newHalfGUIDStr());
            }
            Object[] args = new Object[]{eo.getId(), eo.getFetchSchemeId(), eo.getFormSchemeId(), eo.getFormId(), eo.getGlobalDim(), eo.getGlobalDimValue(), eo.getRowDim(), eo.getColDim(), eo.getDirectionType(), eo.getPositionNum(), eo.getDimSetting()};
            argsList.add(args);
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }

    @Override
    public List<DimensionSettingEO> listFetchSettingByFetchSchemeId(String sourceId) {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,globalDim,globalDimValue,rowDim,colDim,directionType,positionNum,dimSetting  \n  FROM BDE_DIMSETTING  fs where fetchSchemeId = ?  \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getDimensionSettingEO(rs), new Object[]{sourceId});
    }

    @Override
    public void deleteByFetchSchemeId(String fetchSchemeId) {
        String sql = "DELETE FROM  BDE_DIMSETTING WHERE FETCHSCHEMEID = ? \n";
        this.jdbcTemplate.update(sql, new Object[]{fetchSchemeId});
    }
}

