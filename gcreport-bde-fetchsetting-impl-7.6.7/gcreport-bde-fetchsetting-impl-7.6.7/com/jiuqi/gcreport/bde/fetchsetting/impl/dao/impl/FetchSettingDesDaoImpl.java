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
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingListLinkCond
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingListLinkCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FetchSettingDesDaoImpl
implements FetchSettingDesDao {
    private static final String FILED_STRING = " id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,sortOrder  ";
    private static final String FILED_STRING_WITH_STOP_FLAG = " id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,stopFlag, sortOrder  ";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<FetchSettingDesEO> listFetchSettingDesByCondi(FetchSettingCond fetchSettingCond) {
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT \n").append(FILED_STRING);
        sql.append("    FROM ").append("BDE_FETCHSETTING_DES").append("  fs \n");
        sql.append("   WHERE 1 = 1");
        ArrayList<String> paramArray = new ArrayList<String>();
        if (!StringUtils.isEmpty((String)fetchSettingCond.getFormSchemeId())) {
            paramArray.add(fetchSettingCond.getFormSchemeId());
            sql.append(" AND fs.FORMSCHEMEID = ? \n");
        }
        if (!StringUtils.isEmpty((String)fetchSettingCond.getFetchSchemeId())) {
            paramArray.add(fetchSettingCond.getFetchSchemeId());
            sql.append(" AND fs.FETCHSCHEMEID = ? \n");
        }
        if (!StringUtils.isEmpty((String)fetchSettingCond.getFormId())) {
            paramArray.add(fetchSettingCond.getFormId());
            sql.append(" AND fs.FORMID = ? \n");
        }
        if (!StringUtils.isEmpty((String)fetchSettingCond.getRegionId())) {
            paramArray.add(fetchSettingCond.getRegionId());
            sql.append(" AND fs.REGIONID = ? \n");
        }
        if (!StringUtils.isEmpty((String)fetchSettingCond.getDataLinkId())) {
            paramArray.add(fetchSettingCond.getDataLinkId());
            sql.append(" AND fs.DATALINKID = ? \n");
        }
        sql.append(" order by sortOrder \n");
        return this.jdbcTemplate.query(sql.toString(), (rs, row) -> this.getFetchSettingDesEO(rs), paramArray.toArray());
    }

    @Override
    public List<FetchSettingDesEO> listFetchSettingDesByRegionId(FetchSettingCond fetchSettingCond) {
        String sql = "  SELECT \n id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,sortOrder    FROM BDE_FETCHSETTING_DES  fs \n WHERE fs.FORMSCHEMEID = ? \n AND fs.FETCHSCHEMEID = ? \n AND fs.FORMID = ? \n AND fs.REGIONID = ? \n order by sortOrder \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSettingDesEO(rs), new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId(), fetchSettingCond.getRegionId()});
    }

    @Override
    public List<FetchSettingDesEO> listFetchSettingDesWithStopFlagByRegionId(FetchSettingCond fetchSettingCond) {
        String sql = "  SELECT \n id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,stopFlag, sortOrder    FROM BDE_FETCHSETTING_DES  fs \n WHERE fs.FORMSCHEMEID = ? \n AND fs.FETCHSCHEMEID = ? \n AND fs.FORMID = ? \n AND fs.REGIONID = ? \n order by sortOrder \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSettingDesEOWithStopFlag(rs), new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId(), fetchSettingCond.getRegionId()});
    }

    private FetchSettingDesEO getFetchSettingDesEO(ResultSet rs) throws SQLException {
        FetchSettingDesEO eo = new FetchSettingDesEO();
        eo.setId(rs.getString(1));
        eo.setFetchSchemeId(rs.getString(2));
        eo.setFormSchemeId(rs.getString(3));
        eo.setFormId(rs.getString(4));
        eo.setRegionId(rs.getString(5));
        eo.setDataLinkId(rs.getString(6));
        eo.setFieldDefineId(rs.getString(7));
        eo.setRegionType(rs.getString(8));
        eo.setFixedSettingData(rs.getString(9));
        eo.setSortOrder(rs.getInt(10));
        return eo;
    }

    private FetchSettingDesEO getFetchSettingDesEOWithStopFlag(ResultSet rs) throws SQLException {
        FetchSettingDesEO eo = new FetchSettingDesEO();
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
    public List<FetchSettingDesEO> listFetchSettingDesByDataLinkId(FetchSettingCond fetchSettingCond) {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,sortOrder    FROM BDE_FETCHSETTING_DES  fs \n WHERE fs.FORMSCHEMEID = ? \n AND fs.FETCHSCHEMEID = ? \n AND fs.FORMID = ? \n AND fs.REGIONID = ? \n AND fs.DATALINKID = ? \n order by sortOrder \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSettingDesEO(rs), new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId(), fetchSettingCond.getRegionId(), fetchSettingCond.getDataLinkId()});
    }

    @Override
    public List<FetchSettingDesEO> listDataLinkFixedSettingDes(FetchSettingListLinkCond fetchSettingListLinkCond) {
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,sortOrder  ");
        sql.append("  FROM BDE_FETCHSETTING_DES  fs \n");
        sql.append(" WHERE fs.FORMSCHEMEID = ? \n");
        sql.append(" AND fs.FETCHSCHEMEID = ? \n");
        sql.append(" AND fs.FORMID = ? \n");
        sql.append(" AND fs.REGIONID = ? \n");
        sql.append("AND" + SqlBuildUtil.getStrInCondi((String)"fs.DATALINKID", (List)fetchSettingListLinkCond.getDataLinkIdList()));
        sql.append(" order by sortOrder \n");
        return this.jdbcTemplate.query(sql.toString(), (rs, row) -> this.getFetchSettingDesEO(rs), new Object[]{fetchSettingListLinkCond.getFormSchemeId(), fetchSettingListLinkCond.getFetchSchemeId(), fetchSettingListLinkCond.getFormId(), fetchSettingListLinkCond.getRegionId()});
    }

    @Override
    public void deleteBatchFetchSettingDesData(List<List<Object>> deleteWhereValues) {
        String sql = "DELETE FROM BDE_FETCHSETTING_DES \n WHERE FORMSCHEMEID = ? \n AND FETCHSCHEMEID = ? \n AND FORMID = ? \n AND REGIONID = ? \n AND DATALINKID = ? \n";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (List<Object> deleteWhereValue : deleteWhereValues) {
            argsList.add(deleteWhereValue.toArray());
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }

    @Override
    public void deleteFloatFetchSettingDesData(List<List<Object>> deleteWhereValues) {
        String sql = "DELETE FROM BDE_FETCHSETTING_DES \n WHERE FORMSCHEMEID = ? \n AND FETCHSCHEMEID = ? \n AND FORMID = ? \n AND REGIONID = ? \n";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (List<Object> deleteWhereValue : deleteWhereValues) {
            argsList.add(deleteWhereValue.toArray());
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }

    @Override
    public Set<String> listFormulaFieldId(String formulaSchemeId, String formSchemeId, String formId, String regionId) {
        String sql = "  SELECT fieldDefineId \n  FROM BDE_FETCHSETTING_DES  fs \n WHERE fs.FETCHSCHEMEID = ? \n AND fs.FORMSCHEMEID = ? \n AND fs.FORMID = ? \n AND fs.REGIONID = ? \n";
        return (Set)this.jdbcTemplate.query(sql, rs -> {
            HashSet<String> result = new HashSet<String>();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
            return result;
        }, new Object[]{formulaSchemeId, formSchemeId, formId, regionId});
    }

    @Override
    public void deleteBatchFetchSettingDesDataByRegionId(List<List<Object>> deleteWhereValues) {
        String sql = "DELETE FROM BDE_FETCHSETTING_DES  \n WHERE FORMSCHEMEID = ? \n AND FETCHSCHEMEID = ? \n AND FORMID = ? \n AND REGIONID = ? \n";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (List<Object> deleteWhereValue : deleteWhereValues) {
            argsList.add(deleteWhereValue.toArray());
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }

    @Override
    public List<FetchSettingDesEO> listFixedFetchSettingDesByFormId(FetchSettingCond fetchSettingCond) {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,sortOrder   \n  FROM BDE_FETCHSETTING_DES  fs \n WHERE fs.FORMSCHEMEID = ? \n AND fs.FETCHSCHEMEID = ? \n AND fs.FORMID = ? \n AND fs.REGIONTYPE = 'FIXED' \n order by sortOrder \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSettingDesEO(rs), new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId()});
    }

    @Override
    public List<FetchSettingDesEO> listFixedFetchFloatSettingDesByFormId(FetchSettingCond fetchSettingCond) {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,sortOrder   \n  FROM BDE_FETCHSETTING_DES  fs \n WHERE fs.FORMSCHEMEID = ? \n AND fs.FETCHSCHEMEID = ? \n AND fs.FORMID = ? \n AND fs.REGIONTYPE = 'FLOAT' \n order by sortOrder \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSettingDesEO(rs), new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId()});
    }

    @Override
    public int getFetchSettingDesByFetchSourceCode(String fetchSourceCode) {
        String sql = "  SELECT count(1) \n  FROM BDE_FETCHSETTING_DES  fs \n WHERE fs.FETCHSOURCECODE = ? \n";
        return (Integer)this.jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                return 0;
            }
            return rs.getInt(1);
        }, new Object[]{fetchSourceCode});
    }

    @Override
    public List<FetchSettingDesEO> listFetchSettingDesByFetchSchemeId(FetchSettingCond fetchSettingCond) {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,sortOrder   \n  FROM BDE_FETCHSETTING_DES  fs \n WHERE fs.FORMSCHEMEID = ? \n AND fs.FETCHSCHEMEID = ? \n AND fs.REGIONID <> '-' \n order by sortOrder \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSettingDesEO(rs), new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId()});
    }

    @Override
    public List<FetchSettingDesEO> listFetchSettingDesWithStopFlagByFetchSchemeId(FetchSettingCond fetchSettingCond) {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,stopFlag, sortOrder   \n  FROM BDE_FETCHSETTING_DES  fs \n WHERE fs.FORMSCHEMEID = ? \n AND fs.FETCHSCHEMEID = ? \n AND fs.REGIONID <> '-' \n order by sortOrder \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSettingDesEOWithStopFlag(rs), new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId()});
    }

    @Override
    public List<FetchSettingDesEO> listFetchSettingDesByFormId(FetchSettingCond fetchSettingCond) {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,sortOrder   \n  FROM BDE_FETCHSETTING_DES  fs \n WHERE fs.FORMSCHEMEID = ? \n AND fs.FETCHSCHEMEID = ? \n AND fs.FORMID = ? \n AND fs.REGIONID <> '-' \n order by sortOrder \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSettingDesEO(rs), new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId()});
    }

    @Override
    public List<FetchSettingDesEO> listFetchSettingDesWithStopFlagByFormId(FetchSettingCond fetchSettingCond) {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,stopFlag, sortOrder   \n  FROM BDE_FETCHSETTING_DES  fs \n WHERE fs.FORMSCHEMEID = ? \n AND fs.FETCHSCHEMEID = ? \n AND fs.FORMID = ? \n AND fs.REGIONID <> '-' \n order by sortOrder \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSettingDesEOWithStopFlag(rs), new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId()});
    }

    @Override
    public List<FetchSettingDesEO> loadAll() {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,sortOrder   \n  FROM BDE_FETCHSETTING_DES  fs \n order by sortOrder \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSettingDesEO(rs));
    }

    @Override
    public void addBatch(List<FetchSettingDesEO> fetchSettingDesEOS) {
        String sql = "  insert into  BDE_FETCHSETTING_DES \n (  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,sortOrder  )\n values( ?,?,?,?,?, ?,?,?,?,?)";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (FetchSettingDesEO eo : fetchSettingDesEOS) {
            if (StringUtils.isEmpty((String)eo.getId())) {
                eo.setId(UUIDUtils.newHalfGUIDStr());
            }
            Object[] args = new Object[]{eo.getId(), eo.getFetchSchemeId(), eo.getFormSchemeId(), eo.getFormId(), eo.getRegionId(), eo.getDataLinkId(), eo.getFieldDefineId(), eo.getRegionType(), eo.getFixedSettingData(), eo.getSortOrder()};
            argsList.add(args);
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }

    @Override
    public List<FetchSettingDesEO> listFetchSettingByFetchSchemeId(String sourceId) {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,sortOrder   \n  FROM BDE_FETCHSETTING_DES  fs where fetchSchemeId = ?   \n order by sortOrder \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSettingDesEO(rs), new Object[]{sourceId});
    }

    @Override
    public void deleteByFetchSchemeId(String fetchSchemeId) {
        String sql = "DELETE FROM  BDE_FETCHSETTING_DES WHERE FETCHSCHEMEID = ? \n";
        this.jdbcTemplate.update(sql, new Object[]{fetchSchemeId});
    }

    @Override
    public void deleteFetchSettingDesData(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append("BDE_FETCHSETTING_DES").append(" \n");
        sql.append("      WHERE 1 = 1 AND \n");
        sql.append(SqlBuildUtil.getStrInCondi((String)"ID", idList));
        this.jdbcTemplate.update(sql.toString());
    }

    @Override
    public void updateFetchSettingDesDk(List<FetchSettingVO> updateFetchSettingList) {
        if (CollectionUtils.isEmpty(updateFetchSettingList)) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("BDE_FETCHSETTING_DES").append(" SET DATALINKID = ? \n");
        sql.append("      WHERE 1 = 1 AND ").append("ID").append(" = ? ");
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>(updateFetchSettingList.size());
        for (FetchSettingVO fetchSettingVO : updateFetchSettingList) {
            Object[] args = new Object[]{fetchSettingVO.getDataLinkId(), fetchSettingVO.getId()};
            batchArgs.add(args);
        }
        this.jdbcTemplate.batchUpdate(sql.toString(), batchArgs);
    }

    @Override
    public List<FetchSettingDesEO> listAllFloatFetchSetting() {
        String sql = "  SELECT  id,fetchSchemeId,formSchemeId,formId,regionId,dataLinkId,fieldDefineId,regionType, fixedSettingData,sortOrder   \n  FROM BDE_FETCHSETTING_DES  fs \n WHERE regiontype = 'FLOAT' \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSettingDesEO(rs));
    }

    @Override
    public void updateBatch(List<FetchSettingDesEO> fetchSettingDesEOS) {
        String sql = "update BDE_FETCHSETTING_DES set fetchSchemeId=?,formSchemeId=?,formId=?,regionId=?,dataLinkId=?,fieldDefineId=?, \n          regionType=?,fixedSettingData = ?,sortOrder=?\n    where id = ? ";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (FetchSettingDesEO eo : fetchSettingDesEOS) {
            Object[] args = new Object[]{eo.getFetchSchemeId(), eo.getFormSchemeId(), eo.getFormId(), eo.getRegionId(), eo.getDataLinkId(), eo.getFieldDefineId(), eo.getRegionType(), eo.getFixedSettingData(), eo.getSortOrder(), eo.getId()};
            argsList.add(args);
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }

    @Override
    public void deleteFetchSettingByFetchSettingCond(FetchSettingCond fetchSettingCond, List<String> deleteLinkIds) {
        String sql = "DELETE FROM BDE_FETCHSETTING_DES  \n WHERE FORMSCHEMEID = ? \n AND FETCHSCHEMEID = ? \n AND FORMID = ? \n AND REGIONID = ? \n AND DATALINKID = ? \n AND REGIONTYPE = 'FLOAT' \n";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (String deleteLinkId : deleteLinkIds) {
            Object[] args = new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId(), fetchSettingCond.getRegionId(), deleteLinkId};
            argsList.add(args);
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }

    @Override
    public Map<String, Integer> getFixSettingDesStopFlag(FetchSettingCond fetchSettingCond, List<String> linkIds) {
        StringBuilder sql = new StringBuilder();
        sql.append("select DATALINKID, STOPFLAG FROM ").append("BDE_FETCHSETTING_DES").append(" fs  \n");
        sql.append(" WHERE fs.FORMSCHEMEID = ? \n");
        sql.append(" AND fs.FETCHSCHEMEID = ? \n");
        sql.append(" AND fs.FORMID = ? \n");
        sql.append(" AND fs.REGIONID = ? \n");
        sql.append(" AND ").append(SqlBuildUtil.getStrInCondi((String)"fs.DATALINKID", linkIds));
        return (Map)this.jdbcTemplate.query(sql.toString(), rs -> {
            HashMap<String, Integer> linkEnableFlag = new HashMap<String, Integer>();
            while (rs.next()) {
                linkEnableFlag.put(rs.getString(1), rs.getInt(2));
            }
            return linkEnableFlag;
        }, new Object[]{fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId(), fetchSettingCond.getRegionId()});
    }

    @Override
    public void disableFixSetting(FetchSettingCond fetchSettingCond, List<String> linkIds) {
        if (CollectionUtils.isEmpty(linkIds) || Objects.isNull(fetchSettingCond)) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("BDE_FETCHSETTING_DES").append(" SET stopFlag = 1 \n");
        sql.append("      WHERE 1 = 1 AND  DATALINKID = ? ");
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>(linkIds.size());
        for (String linkId : linkIds) {
            Object[] args = new Object[]{linkId};
            batchArgs.add(args);
        }
        this.jdbcTemplate.batchUpdate(sql.toString(), batchArgs);
    }

    @Override
    public void enableFixSetting(FetchSettingCond fetchSettingCond, List<String> linkIds) {
        if (CollectionUtils.isEmpty(linkIds) || Objects.isNull(fetchSettingCond)) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("BDE_FETCHSETTING_DES").append(" SET stopFlag = 0 \n");
        sql.append("      WHERE 1 = 1 AND  DATALINKID = ? ");
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>(linkIds.size());
        for (String linkId : linkIds) {
            Object[] args = new Object[]{linkId};
            batchArgs.add(args);
        }
        this.jdbcTemplate.batchUpdate(sql.toString(), batchArgs);
    }
}

