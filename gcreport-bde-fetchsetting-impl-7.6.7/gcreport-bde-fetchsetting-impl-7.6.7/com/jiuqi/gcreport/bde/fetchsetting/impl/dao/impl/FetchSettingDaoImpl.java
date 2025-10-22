/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FetchSettingDaoImpl
implements FetchSettingDao {
    private static final String FILED_STRING_WITH_STOP_FLAG = " id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,stopFlag, sortOrder  ";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<FetchSettingEO> listFetchSettingByRegionId(FetchSettingCond fetchSettingCond) {
        String sql = "  SELECT \n id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,stopFlag, sortOrder    FROM BDE_FETCHSETTING  fs \n WHERE fs.FORMSCHEMEID = ? \n AND fs.FETCHSCHEMEID = ? \n AND fs.FORMID = ? \n AND fs.REGIONID = ? \n AND fs.stopFlag <> 1 \n order by sortOrder \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSettingEO(rs), new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId(), fetchSettingCond.getRegionId()});
    }

    private FetchSettingEO getFetchSettingEO(ResultSet rs) throws SQLException {
        FetchSettingEO eo = new FetchSettingEO();
        eo.setId(rs.getString(1));
        eo.setFetchSchemeId(rs.getString(2));
        eo.setFormSchemeId(rs.getString(3));
        eo.setFormId(rs.getString(4));
        eo.setRegionId(rs.getString(5));
        eo.setDataLinkId(rs.getString(6));
        eo.setFieldDefineId(rs.getString(7));
        eo.setRegionType(rs.getString(8));
        eo.setFixedSettingData(rs.getString(9));
        eo.setStopFlag(rs.getInt(10));
        eo.setSortOrder(rs.getInt(11));
        return eo;
    }

    @Override
    public List<FetchSettingEO> listEnableFetchSettingByFormId(String formSchemeId, String fetchSchemeId, String formId) {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,stopFlag, sortOrder   \n  FROM BDE_FETCHSETTING  fs \n WHERE fs.FORMSCHEMEID = ? \n AND fs.FETCHSCHEMEID = ? \n AND fs.FORMID = ? \n AND fs.stopFlag <> 1 \n order by sortOrder \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSettingEO(rs), new Object[]{formSchemeId, fetchSchemeId, formId});
    }

    @Override
    public List<FetchSettingEO> listFetchSettingByFormId(String formSchemeId, String fetchSchemeId, String formId) {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,stopFlag, sortOrder   \n  FROM BDE_FETCHSETTING  fs \n WHERE fs.FORMSCHEMEID = ? \n AND fs.FETCHSCHEMEID = ? \n AND fs.FORMID = ? \n order by sortOrder \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSettingEO(rs), new Object[]{formSchemeId, fetchSchemeId, formId});
    }

    @Override
    public Set<String> listFormulaFieldId(String formulaSchemeId, String formSchemeId, String formId, String regionId) {
        String sql = "  SELECT fieldDefineId \n  FROM BDE_FETCHSETTING  fs \n WHERE fs.FETCHSCHEMEID = ? \n AND fs.FORMSCHEMEID = ? \n AND fs.FORMID = ? \n AND fs.REGIONID = ? \n";
        return (Set)this.jdbcTemplate.query(sql, rs -> {
            HashSet<String> result = new HashSet<String>();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
            return result;
        }, new Object[]{formulaSchemeId, formSchemeId, formId, regionId});
    }

    @Override
    public void deleteFetchSettingByFetchSettingCond(FetchSettingCond fetchSettingCond) {
        ArrayList<String> args = new ArrayList<String>();
        args.add(fetchSettingCond.getFormSchemeId());
        args.add(fetchSettingCond.getFetchSchemeId());
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM ").append("BDE_FETCHSETTING").append("  \n");
        sql.append(" WHERE FORMSCHEMEID = ? \n");
        sql.append(" AND FETCHSCHEMEID = ? \n");
        if (!StringUtils.isEmpty((String)fetchSettingCond.getFormId())) {
            sql.append(" AND FORMID = ? \n");
            args.add(fetchSettingCond.getFormId());
        }
        this.jdbcTemplate.update(sql.toString(), args.toArray());
    }

    @Override
    public List<FetchSettingEO> listFetchSettingByDataLinkId(FetchSettingCond fetchSettingCond) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,stopFlag, sortOrder   \n");
        sql.append("   FROM BDE_FETCHSETTING  fs \n");
        sql.append("  WHERE fs.FORMSCHEMEID = ?  \n");
        sql.append("    AND fs.FETCHSCHEMEID = ?  \n");
        sql.append("    AND fs.FORMID = ?  \n");
        ArrayList<String> args = new ArrayList<String>();
        args.add(fetchSettingCond.getFormSchemeId());
        args.add(fetchSettingCond.getFetchSchemeId());
        args.add(fetchSettingCond.getFormId());
        if (!StringUtils.isEmpty((String)fetchSettingCond.getDataLinkId())) {
            sql.append("    AND fs.DATALINKID = ?  \n");
            args.add(fetchSettingCond.getDataLinkId());
        }
        if (!StringUtils.isEmpty((String)fetchSettingCond.getRegionId())) {
            sql.append("    AND fs.REGIONID = ?  \n");
            args.add(fetchSettingCond.getRegionId());
        }
        sql.append(" AND FS.STOPFLAG <> 1 \n");
        sql.append("  ORDER BY SORTORDER   \n");
        return this.jdbcTemplate.query(sql.toString(), (rs, row) -> this.getFetchSettingEO(rs), args.toArray());
    }

    @Override
    public List<FetchSettingEO> loadAll() {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,stopFlag, sortOrder   \n  FROM BDE_FETCHSETTING  fs \n AND fs.stopFlag <> 1 \n order by sortOrder \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSettingEO(rs));
    }

    @Override
    public void addBatch(List<FetchSettingEO> fetchSettingEOS) {
        String sql = "  insert into  BDE_FETCHSETTING \n (  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,stopFlag, sortOrder  )\n values( ?,?,?,?,?, ?,?,?,?,?,?)";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (FetchSettingEO eo : fetchSettingEOS) {
            if (StringUtils.isEmpty((String)eo.getId())) {
                eo.setId(UUIDUtils.newHalfGUIDStr());
            }
            Object[] args = new Object[]{eo.getId(), eo.getFetchSchemeId(), eo.getFormSchemeId(), eo.getFormId(), eo.getRegionId(), eo.getDataLinkId(), eo.getFieldDefineId(), eo.getRegionType(), eo.getFixedSettingData(), Objects.isNull(eo.getStopFlag()) ? 0 : eo.getStopFlag(), eo.getSortOrder()};
            argsList.add(args);
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }

    @Override
    public List<FetchSettingEO> listFetchSettingByFetchSchemeId(String sourceId) {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,stopFlag, sortOrder   \n  FROM BDE_FETCHSETTING  fs where fetchSchemeId = ?   \n AND FS.STOPFLAG <> 1 \n ORDER BY SORTORDER \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSettingEO(rs), new Object[]{sourceId});
    }

    @Override
    public void deleteByFetchSchemeId(String fetchSchemeId) {
        String sql = "DELETE FROM  BDE_FETCHSETTING WHERE FETCHSCHEMEID = ? \n";
        this.jdbcTemplate.update(sql, new Object[]{fetchSchemeId});
    }

    @Override
    public void deleteFetchSettingData(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append("BDE_FETCHSETTING").append(" \n");
        sql.append("      WHERE 1 = 1 AND \n");
        sql.append(SqlBuildUtil.getStrInCondi((String)"ID", idList));
        this.jdbcTemplate.update(sql.toString());
    }

    @Override
    public void updateFetchSettingDk(List<FetchSettingVO> updateFetchSettingList) {
        if (CollectionUtils.isEmpty(updateFetchSettingList)) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("BDE_FETCHSETTING").append(" SET DATALINKID = ? \n");
        sql.append("      WHERE 1 = 1 AND ").append("ID").append(" = ? ");
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>(updateFetchSettingList.size());
        for (FetchSettingVO fetchSettingVO : updateFetchSettingList) {
            Object[] args = new Object[]{fetchSettingVO.getDataLinkId(), fetchSettingVO.getId()};
            batchArgs.add(args);
        }
        this.jdbcTemplate.batchUpdate(sql.toString(), batchArgs);
    }

    @Override
    public List<FetchSettingEO> listAllFloatFetchSetting() {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,stopFlag, sortOrder   \n  FROM BDE_FETCHSETTING  fs \n AND FS.STOPFLAG <> 1 \n WHERE regiontype = 'FLOAT' \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSettingEO(rs));
    }

    @Override
    public void updateBatch(List<FetchSettingEO> fetchSettingEOS) {
        String sql = "update BDE_FETCHSETTING set fetchSchemeId=?,formSchemeId=?,formId=?,regionId=?,dataLinkId=?,fieldDefineId=?, \n          regionType=?,fixedSettingData = ?,sortOrder=?\n    where id = ? ";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (FetchSettingEO eo : fetchSettingEOS) {
            Object[] args = new Object[]{eo.getFetchSchemeId(), eo.getFormSchemeId(), eo.getFormId(), eo.getRegionId(), eo.getDataLinkId(), eo.getFieldDefineId(), eo.getRegionType(), eo.getFixedSettingData(), eo.getSortOrder(), eo.getId()};
            argsList.add(args);
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }

    @Override
    public List<String> listFormKeyBySchemeKey(String fetchSchemeKey, String formSchemeKey) {
        String sql = "SELECT FORMID FROM BDE_FETCHSETTING WHERE FETCHSCHEMEID = ? AND FORMSCHEMEID = ?  AND fs.stopFlag <> 1 ";
        return (List)this.jdbcTemplate.query(sql, rs -> {
            ArrayList<String> result = new ArrayList<String>();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
            return result;
        }, new Object[]{fetchSchemeKey, formSchemeKey});
    }

    @Override
    public List<FetchSettingEO> listFetchSettingWithStopFlagByRegionId(FetchSettingCond fetchSettingCond) {
        String sql = "  SELECT \n id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,stopFlag, sortOrder    FROM BDE_FETCHSETTING  fs \n WHERE fs.FORMSCHEMEID = ? \n AND fs.FETCHSCHEMEID = ? \n AND fs.FORMID = ? \n AND fs.REGIONID = ? \n order by sortOrder \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSettingEO(rs), new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId(), fetchSettingCond.getRegionId()});
    }
}

