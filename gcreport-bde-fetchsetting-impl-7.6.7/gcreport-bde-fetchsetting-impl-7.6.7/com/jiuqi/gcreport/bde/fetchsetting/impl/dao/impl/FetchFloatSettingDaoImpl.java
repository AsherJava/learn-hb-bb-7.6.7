/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dao.impl;

import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingEO;
import com.jiuqi.va.domain.common.JSONUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FetchFloatSettingDaoImpl
implements FetchFloatSettingDao {
    private static final String FILED_STRING = " id,fetchSchemeId,formSchemeId,formId,regionId,queryType,queryConfigInfo ";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<FetchFloatSettingEO> listFetchFloatSettingByFormId(FetchSettingCond fetchSettingCond) {
        StringBuffer sql = new StringBuffer();
        sql.append("  select ").append(FILED_STRING).append("\n");
        sql.append("    from ").append("BDE_FETCHFLOATSETTING").append("  fs \n");
        sql.append("   where 1=1 ");
        ArrayList<String> args = new ArrayList<String>();
        if (!StringUtils.isEmpty((String)fetchSettingCond.getFetchSchemeId())) {
            sql.append(" and fs.fetchSchemeId = ? ");
            args.add(fetchSettingCond.getFetchSchemeId());
        }
        if (!StringUtils.isEmpty((String)fetchSettingCond.getFormSchemeId())) {
            sql.append(" and fs.formSchemeId = ? ");
            args.add(fetchSettingCond.getFormSchemeId());
        }
        if (!StringUtils.isEmpty((String)fetchSettingCond.getFormId())) {
            sql.append(" and fs.formId = ? ");
            args.add(fetchSettingCond.getFormId());
        }
        if (!StringUtils.isEmpty((String)fetchSettingCond.getRegionId())) {
            sql.append(" and fs.regionId = ? ");
            args.add(fetchSettingCond.getRegionId());
        }
        return this.jdbcTemplate.query(sql.toString(), (rs, row) -> this.getFetchFloatSettingEO(rs), args.toArray());
    }

    private FetchFloatSettingEO getFetchFloatSettingEO(ResultSet rs) throws SQLException {
        FetchFloatSettingEO eo = new FetchFloatSettingEO();
        eo.setId(rs.getString(1));
        eo.setFetchSchemeId(rs.getString(2));
        eo.setFormSchemeId(rs.getString(3));
        eo.setFormId(rs.getString(4));
        eo.setRegionId(rs.getString(5));
        eo.setQueryType(rs.getString(6));
        eo.setQueryConfigInfo(rs.getString(7));
        return eo;
    }

    @Override
    public void deleteFloatFetchSettingByFetchSettingCond(FetchSettingCond fetchSettingCond) {
        ArrayList<String> args = new ArrayList<String>();
        args.add(fetchSettingCond.getFormSchemeId());
        args.add(fetchSettingCond.getFetchSchemeId());
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM ").append("BDE_FETCHFLOATSETTING").append("  \n");
        sql.append(" WHERE FORMSCHEMEID = ? \n");
        sql.append(" AND FETCHSCHEMEID = ? \n");
        if (!StringUtils.isEmpty((String)fetchSettingCond.getFormId())) {
            sql.append(" AND FORMID = ? \n");
            args.add(fetchSettingCond.getFormId());
        }
        this.jdbcTemplate.update(sql.toString(), args.toArray());
    }

    @Override
    public FetchFloatSettingEO getFetchFloatSetting(FetchSettingCond fetchSettingCond) {
        String sql = "  select queryType,queryConfigInfo \n      from BDE_FETCHFLOATSETTING  fs \n where fs.formSchemeId = ? \n and fs.fetchSchemeId = ? \n and fs.formId = ? \n and fs.regionId = ? \n";
        return (FetchFloatSettingEO)this.jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                return null;
            }
            FetchFloatSettingEO eo = new FetchFloatSettingEO();
            eo.setQueryType(rs.getString(1));
            eo.setQueryConfigInfo(rs.getString(2));
            return eo;
        }, new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId(), fetchSettingCond.getRegionId()});
    }

    @Override
    public List<FetchFloatSettingEO> loadAll() {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,queryType,queryConfigInfo  \n  FROM BDE_FETCHFLOATSETTING  fs \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchFloatSettingEO(rs));
    }

    @Override
    public void addBatch(List<FetchFloatSettingEO> fetchFloatSettingEOS) {
        String sql = "  insert into  BDE_FETCHFLOATSETTING \n (  id,fetchSchemeId,formSchemeId,formId,regionId,queryType,queryConfigInfo )\n values( ?,?,?,?,?,?,? )";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (FetchFloatSettingEO eo : fetchFloatSettingEOS) {
            if (StringUtils.isEmpty((String)eo.getId())) {
                eo.setId(UUIDUtils.newHalfGUIDStr());
            }
            Object[] args = new Object[]{eo.getId(), eo.getFetchSchemeId(), eo.getFormSchemeId(), eo.getFormId(), eo.getRegionId(), eo.getQueryType(), eo.getQueryConfigInfo()};
            argsList.add(args);
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }

    @Override
    public List<FetchFloatSettingEO> listFetchSettingByFetchSchemeId(String sourceId) {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,queryType,queryConfigInfo  \n  FROM BDE_FETCHFLOATSETTING  fs where fetchSchemeId = ?   \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchFloatSettingEO(rs), new Object[]{sourceId});
    }

    @Override
    public void deleteByFetchSchemeId(String fetchSchemeId) {
        String sql = "DELETE FROM  BDE_FETCHFLOATSETTING WHERE FETCHSCHEMEID = ? \n";
        this.jdbcTemplate.update(sql, new Object[]{fetchSchemeId});
    }

    @Override
    public void updateFloatSettingData(List<FloatRegionConfigVO> floatSettingList) {
        if (CollectionUtils.isEmpty(floatSettingList)) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("BDE_FETCHFLOATSETTING").append(" \n");
        sql.append("SET QUERYCONFIGINFO = ? WHERE 1 = 1 AND ID = ?");
        ArrayList<Object[]> argsList = new ArrayList<Object[]>(floatSettingList.size());
        for (FloatRegionConfigVO fetchFloatSetting : floatSettingList) {
            Object[] args = new Object[]{JSONUtil.toJSONString((Object)fetchFloatSetting.getQueryConfigInfo()), fetchFloatSetting.getId()};
            argsList.add(args);
        }
        this.jdbcTemplate.batchUpdate(sql.toString(), argsList);
    }

    @Override
    public void deleteFloatSettingData(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append("BDE_FETCHFLOATSETTING").append(" \n");
        sql.append("      WHERE 1 = 1 AND \n");
        sql.append(SqlBuildUtil.getStrInCondi((String)"ID", idList));
        this.jdbcTemplate.update(sql.toString());
    }

    @Override
    public void batchUpdateQueryConfigInfoById(Map<String, String> desIdToQueryConfigMap) {
        String sql = " UPDATE BDE_FETCHFLOATSETTING SET QUERYCONFIGINFO = ? WHERE ID = ?";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (String id : desIdToQueryConfigMap.keySet()) {
            argsList.add(new Object[]{desIdToQueryConfigMap.get(id), id});
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }

    @Override
    public List<String> lisFormKeyBySchemeKey(String fetchSchemeKey, String formSchemeKey) {
        String sql = "SELECT FORMID FROM BDE_FETCHFLOATSETTING WHERE FETCHSCHEMEID = ? AND FORMSCHEMEID = ? ";
        return (List)this.jdbcTemplate.query(sql, rs -> {
            ArrayList<String> result = new ArrayList<String>();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
            return result;
        }, new Object[]{fetchSchemeKey, formSchemeKey});
    }
}

