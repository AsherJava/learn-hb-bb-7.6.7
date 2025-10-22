/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.common.base.util.Assert
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
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchFloatSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchFloatSettingDesEO;
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
public class FetchFloatSettingDesDaoImpl
implements FetchFloatSettingDesDao {
    private static final String FILED_STRING = " id,fetchSchemeId,formSchemeId,formId,regionId,queryType,queryConfigInfo ";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<FetchFloatSettingDesEO> listFloatSettingDesByCondi(FetchSettingCond fetchSettingCond) {
        Assert.isNotEmpty((String)fetchSettingCond.getFormSchemeId());
        ArrayList<String> args = new ArrayList<String>();
        StringBuffer sql = new StringBuffer();
        sql.append("  select  id,fetchSchemeId,formSchemeId,formId,regionId,queryType,queryConfigInfo \n");
        sql.append("      from BDE_FETCHFLOATSETTING_DES  fs \n");
        sql.append(" where 1 = 1 \n");
        if (!StringUtils.isEmpty((String)fetchSettingCond.getFormSchemeId())) {
            sql.append(" and fs.formSchemeId = ? \n");
            args.add(fetchSettingCond.getFormSchemeId());
        }
        if (!StringUtils.isEmpty((String)fetchSettingCond.getFetchSchemeId())) {
            sql.append(" and fs.fetchSchemeId = ? \n");
            args.add(fetchSettingCond.getFetchSchemeId());
        }
        if (!StringUtils.isEmpty((String)fetchSettingCond.getFormId())) {
            sql.append(" and fs.formId = ? \n");
            args.add(fetchSettingCond.getFormId());
        }
        return this.jdbcTemplate.query(sql.toString(), (rs, row) -> this.getFetchFloatSettingDesEO(rs), args.toArray());
    }

    @Override
    public FetchFloatSettingDesEO listFloatSettingDesByRegionId(FetchSettingCond fetchSettingCond) {
        String sql = "  select  id,fetchSchemeId,formSchemeId,formId,regionId,queryType,queryConfigInfo \n      from BDE_FETCHFLOATSETTING_DES  fs \n where fs.formSchemeId = ? \n and fs.fetchSchemeId = ? \n and fs.formId = ? \n and fs.regionId = ? \n";
        return (FetchFloatSettingDesEO)this.jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                return null;
            }
            FetchFloatSettingDesEO eo = new FetchFloatSettingDesEO();
            eo.setId(rs.getString(1));
            eo.setFetchSchemeId(rs.getString(2));
            eo.setFormSchemeId(rs.getString(3));
            eo.setFormId(rs.getString(4));
            eo.setRegionId(rs.getString(5));
            eo.setQueryType(rs.getString(6));
            eo.setQueryConfigInfo(rs.getString(7));
            return eo;
        }, new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId(), fetchSettingCond.getRegionId()});
    }

    @Override
    public FetchFloatSettingDesEO getFetchFloatSettingDes(FetchSettingCond fetchSettingCond) {
        String sql = "  select queryType,queryConfigInfo\n      from BDE_FETCHFLOATSETTING_DES  fs \n where fs.formSchemeId = ? \n and fs.fetchSchemeId = ? \n and fs.formId = ? \n and fs.regionId = ? \n";
        return (FetchFloatSettingDesEO)this.jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                return null;
            }
            FetchFloatSettingDesEO eo = new FetchFloatSettingDesEO();
            eo.setQueryType(rs.getString(1));
            eo.setQueryConfigInfo(rs.getString(2));
            return eo;
        }, new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId(), fetchSettingCond.getRegionId()});
    }

    @Override
    public void deleteBatchFetchFloatSettingDesData(List<List<Object>> deleteWhereValues) {
        String sql = " delete  from BDE_FETCHFLOATSETTING_DES   \n where formSchemeId = ? \n and fetchSchemeId = ? \n and formId = ? \n and regionId = ? \n";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (List<Object> deleteWhereValue : deleteWhereValues) {
            argsList.add(deleteWhereValue.toArray());
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }

    @Override
    public List<FetchFloatSettingDesEO> listFetchFloatSettingDesByFetchSchemeId(FetchSettingCond fetchSettingCond) {
        String sql = "  select  id,fetchSchemeId,formSchemeId,formId,regionId,queryType,queryConfigInfo \n      from BDE_FETCHFLOATSETTING_DES  fs \n where fs.formSchemeId = ? \n and fs.fetchSchemeId = ? \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchFloatSettingDesEO(rs), new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId()});
    }

    private FetchFloatSettingDesEO getFetchFloatSettingDesEO(ResultSet rs) throws SQLException {
        FetchFloatSettingDesEO eo = new FetchFloatSettingDesEO();
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
    public List<FetchFloatSettingDesEO> listFetchFloatSettingDesByFormId(FetchSettingCond fetchSettingCond) {
        String sql = "  select  id,fetchSchemeId,formSchemeId,formId,regionId,queryType,queryConfigInfo \n      from BDE_FETCHFLOATSETTING_DES  fs \n where 1 = 1 \n and fs.formSchemeId = ? \n and fs.fetchSchemeId = ? \n and fs.formId = ? \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchFloatSettingDesEO(rs), new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId()});
    }

    @Override
    public List<FetchFloatSettingDesEO> listFetchFloatSettingDesByBillUniqueCodeAndFetchSchemeId(FetchSettingCond fetchSettingCond) {
        String sql = "  select  id,fetchSchemeId,formSchemeId,formId,regionId,queryType,queryConfigInfo \n      from BDE_FETCHFLOATSETTING_DES  fs \n where 1 = 1 \n and fs.formSchemeId = ? \n and fs.fetchSchemeId = ? \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchFloatSettingDesEO(rs), new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId()});
    }

    @Override
    public List<FetchFloatSettingDesEO> listFetchFloatSettingDesByFetchScheme(FetchSettingCond fetchSettingCond) {
        String sql = "  select  id,fetchSchemeId,formSchemeId,formId,regionId,queryType,queryConfigInfo \n      from BDE_FETCHFLOATSETTING_DES  fs \n where fs.fetchSchemeId = ? \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchFloatSettingDesEO(rs), new Object[]{fetchSettingCond.getFetchSchemeId()});
    }

    @Override
    public void addBatch(List<FetchFloatSettingDesEO> fetchFloatSettingDatas) {
        String sql = "  insert into  BDE_FETCHFLOATSETTING_DES \n (  id,fetchSchemeId,formSchemeId,formId,regionId,queryType,queryConfigInfo )\n values( ?,?,?,?,?,?,? )";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (FetchFloatSettingDesEO eo : fetchFloatSettingDatas) {
            if (StringUtils.isEmpty((String)eo.getId())) {
                eo.setId(UUIDUtils.newHalfGUIDStr());
            }
            Object[] args = new Object[]{eo.getId(), eo.getFetchSchemeId(), eo.getFormSchemeId(), eo.getFormId(), eo.getRegionId(), eo.getQueryType(), eo.getQueryConfigInfo()};
            argsList.add(args);
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }

    @Override
    public List<FetchFloatSettingDesEO> loadAll() {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,queryType,queryConfigInfo  \n  FROM BDE_FETCHFLOATSETTING_DES  fs \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchFloatSettingDesEO(rs));
    }

    @Override
    public List<FetchFloatSettingDesEO> listFetchSettingByFetchSchemeId(String sourceId) {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,queryType,queryConfigInfo  \n  FROM BDE_FETCHFLOATSETTING_DES  fs  where fetchSchemeId = ?   \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchFloatSettingDesEO(rs), new Object[]{sourceId});
    }

    @Override
    public void deleteByFetchSchemeId(String fetchSchemeId) {
        String sql = "DELETE FROM  BDE_FETCHFLOATSETTING_DES WHERE FETCHSCHEMEID = ? \n";
        this.jdbcTemplate.update(sql, new Object[]{fetchSchemeId});
    }

    @Override
    public void deleteFloatSettingDesData(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append("BDE_FETCHFLOATSETTING_DES").append(" \n");
        sql.append("      WHERE 1 = 1 AND \n");
        sql.append(SqlBuildUtil.getStrInCondi((String)"ID", idList));
        this.jdbcTemplate.update(sql.toString());
    }

    @Override
    public void updateFloatSettingDesData(List<FloatRegionConfigVO> floatSettingList) {
        if (CollectionUtils.isEmpty(floatSettingList)) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("BDE_FETCHFLOATSETTING_DES").append(" \n");
        sql.append("SET QUERYCONFIGINFO = ? WHERE 1 = 1 AND ID = ?");
        ArrayList<Object[]> argsList = new ArrayList<Object[]>(floatSettingList.size());
        for (FloatRegionConfigVO fetchFloatSetting : floatSettingList) {
            Object[] args = new Object[]{JSONUtil.toJSONString((Object)fetchFloatSetting.getQueryConfigInfo()), fetchFloatSetting.getId()};
            argsList.add(args);
        }
        this.jdbcTemplate.batchUpdate(sql.toString(), argsList);
    }

    @Override
    public void updateFloatRegionConfigVOStopFlag(String queryConfigInfo, String id) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("BDE_FETCHFLOATSETTING_DES").append(" \n");
        sql.append("SET QUERYCONFIGINFO = ? WHERE 1 = 1 AND ID = ?");
        this.jdbcTemplate.update(sql.toString(), new Object[]{queryConfigInfo, id});
    }

    @Override
    public void batchUpdateQueryConfigInfoById(Map<String, String> desIdToQueryConfigMap) {
        String sql = " UPDATE BDE_FETCHFLOATSETTING_DES SET QUERYCONFIGINFO = ? WHERE ID = ?";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (String id : desIdToQueryConfigMap.keySet()) {
            argsList.add(new Object[]{desIdToQueryConfigMap.get(id), id});
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }
}

