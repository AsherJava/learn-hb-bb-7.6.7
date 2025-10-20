/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ColumnTypeEnum
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 *  com.jiuqi.bde.common.template.StringRowMapper
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.base.result.dao.impl;

import com.jiuqi.bde.base.intf.FetchResultDim;
import com.jiuqi.bde.base.intf.FloatColDataPojo;
import com.jiuqi.bde.base.intf.FloatColResultVO;
import com.jiuqi.bde.base.intf.FloatDefineEO;
import com.jiuqi.bde.base.result.dao.FetchResultDao;
import com.jiuqi.bde.common.constant.ColumnTypeEnum;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import com.jiuqi.bde.common.template.StringRowMapper;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FetchResultDaoImpl
extends BaseDataCenterDaoImpl
implements FetchResultDao {
    @Value(value="${spring.datasource.dbType}")
    private String dbType;
    @Value(value="${jiuqi.bde.fetch.result.table.scale:2}")
    private int hanaDatabaseStrToDecimalScale;
    @Value(value="${jiuqi.bde.fetch.result.table.precision:30}")
    private int hanaDatabaseStrToDecimalPrecision;

    @Override
    public Map<String, Object> getSyncFixedResultsByFetchTest(String requestTaskId, String formId, Integer routeNum) {
        StringBuffer sql = new StringBuffer();
        sql.append("select t.FETCHSETTINGID, t.ZBVALUETYPE, T.SIGN, t.ZBVALUE AS ZBVALUE \n");
        sql.append("  from BDE_RESULT_FIXED_%1$d t \n");
        sql.append(" where 1 = 1 \n");
        sql.append("   and t.REQUESTTASKID = ? and t.FORMID = ? \n");
        return (Map)this.getJdbcTemplate().query(String.format(sql.toString(), routeNum), rs -> {
            HashMap<String, Object> result = new HashMap<String, Object>();
            ColumnTypeEnum columnType = null;
            while (rs.next()) {
                columnType = ColumnTypeEnum.getEnumByName((String)rs.getString(2));
                if (ColumnTypeEnum.INT == columnType) {
                    result.put(rs.getString(1), rs.getInt(3) * rs.getInt(4));
                    continue;
                }
                if (ColumnTypeEnum.NUMBER == columnType) {
                    result.put(rs.getString(1), (double)rs.getInt(3) * rs.getDouble(4));
                    continue;
                }
                result.put(rs.getString(1), rs.getString(4));
            }
            return result;
        }, new Object[]{requestTaskId, formId});
    }

    @Override
    public Map<String, Object> getSyncFixedResults(String requestTaskId, String formId, Set<String> strFetchSettingSet, Integer routeNum) {
        StringBuffer sql = new StringBuffer();
        sql.append("select t.FETCHSETTINGID, t.ZBVALUETYPE, T.SIGN, t.ZBVALUE AS ZBVALUE \n");
        sql.append("  from BDE_RESULT_FIXED_%1$d t \n");
        sql.append(" where 1 = 1 \n");
        sql.append("   and t.REQUESTTASKID = ? and t.FORMID = ? \n");
        return (Map)this.getJdbcTemplate().query(String.format(sql.toString(), routeNum), rs -> {
            HashMap<String, Object> result = new HashMap<String, Object>();
            ColumnTypeEnum columnType = null;
            while (rs.next()) {
                columnType = ColumnTypeEnum.getEnumByName((String)rs.getString(2));
                if (ColumnTypeEnum.INT == columnType) {
                    result.put(rs.getString(1), rs.getInt(3) * rs.getInt(4));
                    continue;
                }
                if (ColumnTypeEnum.NUMBER == columnType) {
                    result.put(rs.getString(1), (double)rs.getInt(3) * rs.getDouble(4));
                    continue;
                }
                result.put(rs.getString(1), rs.getString(4));
            }
            return result;
        }, new Object[]{requestTaskId, formId});
    }

    @Override
    public Map<String, Object> getFixedSumResults(String requestTaskId, String formId, String regionId, Integer routeNum) {
        StringBuffer sql = new StringBuffer();
        IDbSqlHandler dbSqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dbType);
        sql.append("select T.FIELDDEFINEID, SUM(T.SIGN * ").append(dbSqlHandler.toDecimal("T.ZBVALUE", this.hanaDatabaseStrToDecimalPrecision, this.hanaDatabaseStrToDecimalScale)).append(") ZBVALUE \n");
        sql.append("  from BDE_RESULT_FIXED_%1$d t \n");
        sql.append(" where 1 = 1 \n");
        sql.append("   and t.REQUESTTASKID = ? and t.FORMID = ?  AND ZBVALUETYPE IN ('NUM','INTEGER')\n");
        sql.append(" group by T.FIELDDEFINEID  \n");
        return (Map)this.getJdbcTemplate().query(String.format(sql.toString(), routeNum), rs -> {
            HashMap<String, Double> result = new HashMap<String, Double>();
            while (rs.next()) {
                result.put(rs.getString(1), rs.getDouble(2));
            }
            return result;
        }, new Object[]{requestTaskId, formId});
    }

    @Override
    public Map<String, Map<String, Object>> getFixedDetailResults(String requestTaskId, String formId, String regionId, List<String> formulaFieldDefineIdList, Integer routeNum) {
        StringBuffer sql = new StringBuffer();
        sql.append("select T.FIELDDEFINEID, T.FETCHSETTINGID, T.ZBVALUETYPE, T.SIGN, T.ZBVALUE \n");
        sql.append("  from BDE_RESULT_FIXED_%1$d t \n");
        sql.append(" where 1 = 1 \n");
        sql.append("   and t.REQUESTTASKID = ? and t.FORMID = ? \n");
        sql.append("   and ").append(SqlBuildUtil.getStrInCondi((String)" T.FIELDDEFINEID ", formulaFieldDefineIdList));
        return (Map)this.getJdbcTemplate().query(String.format(sql.toString(), routeNum), rs -> {
            HashMap result = new HashMap();
            ColumnTypeEnum columnType = null;
            while (rs.next()) {
                if (!result.containsKey(rs.getString(1))) {
                    result.put(rs.getString(1), new HashMap());
                }
                if (ColumnTypeEnum.INT == (columnType = ColumnTypeEnum.getEnumByName((String)rs.getString(3)))) {
                    ((Map)result.get(rs.getString(1))).put(rs.getString(2), rs.getInt(4) * rs.getInt(5));
                    continue;
                }
                if (ColumnTypeEnum.NUMBER == columnType) {
                    ((Map)result.get(rs.getString(1))).put(rs.getString(2), (double)rs.getInt(4) * rs.getDouble(5));
                    continue;
                }
                ((Map)result.get(rs.getString(1))).put(rs.getString(2), rs.getString(5));
            }
            return result;
        }, new Object[]{requestTaskId, formId});
    }

    @Override
    public FetchFloatRowResult getFloatRowDatasMap(FetchResultDim fetchResultDim) {
        List<FloatDefineEO> floatDefineEOList = this.getFloatDefineList(fetchResultDim);
        final Map<String, Integer> floatDefineMap = floatDefineEOList.stream().collect(Collectors.toMap(FloatDefineEO::getFieldDefineName, FloatDefineEO::getFieldDefineOrder));
        Map<String, ColumnTypeEnum> floatDefineTypeMap = floatDefineEOList.stream().collect(Collectors.toMap(FloatDefineEO::getFieldDefineName, type -> ColumnTypeEnum.getEnumByName((String)type.getFieldDefineType()) == null ? ColumnTypeEnum.STRING : ColumnTypeEnum.getEnumByName((String)type.getFieldDefineType())));
        if (floatDefineMap == null || floatDefineMap.size() == 0) {
            return null;
        }
        StringBuffer sql = new StringBuffer();
        sql.append("select T.FLOATORDER  %1$s \n");
        sql.append("  from BDE_RESULT_FLOATROW_%2$d t \n");
        sql.append(" where t.REQUESTREGIONID = ?  \n");
        sql.append(" order by T.FLOATORDER  \n");
        StringBuilder zbValueSql = new StringBuilder();
        for (int i = 0; i < floatDefineMap.size(); ++i) {
            zbValueSql.append(String.format(", ZBVALUE_%1$d", i));
        }
        List rowDatas = (List)this.getJdbcTemplate().query(String.format(sql.toString(), zbValueSql.toString(), fetchResultDim.getRouteNum()), (ResultSetExtractor)new ResultSetExtractor<List<String[]>>(){

            public List<String[]> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<String[]> results = new ArrayList<String[]>(512);
                int columnSize = floatDefineMap.size();
                while (rs.next()) {
                    String[] result = new String[columnSize];
                    for (int i = 0; i < columnSize; ++i) {
                        result[i] = rs.getString(i + 2);
                    }
                    results.add(result);
                }
                return results;
            }
        }, new Object[]{fetchResultDim.getRequestRegionId()});
        FetchFloatRowResult result = new FetchFloatRowResult();
        result.setFloatColumns(floatDefineMap);
        result.setRowDatas(rowDatas);
        result.setFloatColumnsType(floatDefineTypeMap);
        return result;
    }

    public List<FloatDefineEO> getFloatDefineList(FetchResultDim fetchResultDim) {
        StringBuilder defineSql = new StringBuilder();
        defineSql.append("\tSELECT\n");
        defineSql.append("        FIELDDEFINEORDER ,\n");
        defineSql.append("        FIELDDEFINETYPE ,\n");
        defineSql.append("        FIELDDEFINENAME\n");
        defineSql.append("\tFROM\n");
        defineSql.append("        BDE_RESULT_FLOATDEFINE_%1$d brf\n");
        defineSql.append("\tWHERE\n");
        defineSql.append("        REQUESTREGIONID = ?\n");
        return (List)this.getJdbcTemplate().query(String.format(defineSql.toString(), fetchResultDim.getRouteNum()), rs -> {
            ArrayList<FloatDefineEO> floatDefineEOList = new ArrayList<FloatDefineEO>();
            while (rs.next()) {
                FloatDefineEO floatDefineEO = new FloatDefineEO();
                floatDefineEO.setFieldDefineOrder(rs.getInt(1));
                floatDefineEO.setFieldDefineType(rs.getString(2));
                floatDefineEO.setFieldDefineName(rs.getString(3));
                floatDefineEOList.add(floatDefineEO);
            }
            return floatDefineEOList;
        }, new Object[]{fetchResultDim.getRequestRegionId()});
    }

    @Override
    public FloatColResultVO getFloatColData(FetchResultDim fetchResultDim) {
        final FloatColDataPojo colInfoPojo = this.getFloatColColumns(fetchResultDim);
        if (colInfoPojo.isEmpty()) {
            return new FloatColResultVO();
        }
        StringBuffer sql = new StringBuffer();
        sql.append("select T.FLOATORDER, T.FIELDDEFINEID,T.FETCHSETTINGID, T.ZBVALUETYPE, T.SIGN, T.ZBVALUE \n");
        sql.append("  from BDE_RESULT_FLOATCOL_%1$d t \n");
        sql.append(" where T.REQUESTREGIONID = ? \n");
        sql.append(" order by T.FLOATORDER, T.FIELDDEFINEID,T.FETCHSETTINGID \n");
        List rowDatas = (List)this.getJdbcTemplate().query(String.format(sql.toString(), fetchResultDim.getRouteNum()), (ResultSetExtractor)new ResultSetExtractor<List<Object[][]>>(){

            public List<Object[][]> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<Object[][]> results = new ArrayList<Object[][]>(512);
                HashSet<Integer> rowSet = new HashSet<Integer>();
                ColumnTypeEnum columnType = null;
                while (rs.next()) {
                    if (!rowSet.contains(rs.getInt(1))) {
                        results.add(new Object[colInfoPojo.getFieldDefineLength().intValue()][colInfoPojo.getZbSettingLength().intValue()]);
                        rowSet.add(rs.getInt(1));
                    }
                    Integer fieldDefineIndex = colInfoPojo.getFieldDefineIdx(rs.getString(2));
                    Integer fetchSettingIdx = colInfoPojo.getFetchSettingIdx(rs.getString(2), rs.getString(3));
                    columnType = ColumnTypeEnum.getEnumByName((String)rs.getString(4));
                    if (ColumnTypeEnum.INT == columnType) {
                        ((Object[][])results.get((int)rs.getInt((int)1)))[fieldDefineIndex.intValue()][fetchSettingIdx.intValue()] = (double)rs.getInt(5) * rs.getDouble(6);
                        continue;
                    }
                    if (ColumnTypeEnum.NUMBER == columnType) {
                        ((Object[][])results.get((int)rs.getInt((int)1)))[fieldDefineIndex.intValue()][fetchSettingIdx.intValue()] = (double)rs.getInt(5) * rs.getDouble(6);
                        continue;
                    }
                    ((Object[][])results.get((int)rs.getInt((int)1)))[fieldDefineIndex.intValue()][fetchSettingIdx.intValue()] = rs.getString(6);
                }
                return results;
            }
        }, new Object[]{fetchResultDim.getRequestRegionId()});
        return new FloatColResultVO(colInfoPojo.getColInfoMap(), rowDatas);
    }

    private FloatColDataPojo getFloatColColumns(FetchResultDim fetchResultDim) {
        StringBuffer sql = new StringBuffer();
        sql.append("select T.FIELDDEFINEID,T.FETCHSETTINGID \n");
        sql.append("  from BDE_RESULT_FLOATCOL_%1$d t \n");
        sql.append(" where T.REQUESTREGIONID = ? \n");
        sql.append(" group by T.FIELDDEFINEID,T.FETCHSETTINGID \n");
        sql.append(" order by T.FIELDDEFINEID,T.FETCHSETTINGID \n");
        return (FloatColDataPojo)this.getJdbcTemplate().query(String.format(sql.toString(), fetchResultDim.getRouteNum()), (ResultSetExtractor)new ResultSetExtractor<FloatColDataPojo>(){

            public FloatColDataPojo extractData(ResultSet rs) throws SQLException, DataAccessException {
                FloatColDataPojo colDataPojo = new FloatColDataPojo();
                int fieldDefineIdx = 0;
                int fetchSettingIdx = 0;
                int maxFetchSettingIdx = 0;
                String fieldDefine = null;
                String fetchSetting = null;
                while (rs.next()) {
                    fieldDefine = rs.getString(1);
                    fetchSetting = rs.getString(2);
                    if (!colDataPojo.containsfieldDefine(fieldDefine)) {
                        fetchSettingIdx = 0;
                        colDataPojo.putColInfo(fieldDefine, fieldDefineIdx++, fetchSetting, 0);
                    } else {
                        colDataPojo.putColInfo(fieldDefine, fetchSettingIdx++, fetchSetting, fetchSettingIdx);
                    }
                    maxFetchSettingIdx = Math.max(fetchSettingIdx, maxFetchSettingIdx);
                }
                colDataPojo.setFieldDefineLength(fieldDefineIdx + 1);
                colDataPojo.setZbSettingLength(maxFetchSettingIdx + 1);
                return colDataPojo;
            }
        }, new Object[]{fetchResultDim.getRequestRegionId()});
    }

    @Override
    public void deleteFloatRowByRequestTaskId(List<String> requestRegionIds, Integer routeNum) {
        String sql = "DELETE FROM BDE_RESULT_FLOATROW_%1$d WHERE REQUESTREGIONID in (%2$s) ";
        StringBuilder argSql = new StringBuilder();
        Object[] args = new Object[requestRegionIds.size()];
        for (int i = 0; i < requestRegionIds.size(); ++i) {
            argSql.append("?,");
            args[i] = requestRegionIds.get(i);
        }
        this.getJdbcTemplate().update(String.format(sql, routeNum, argSql.subSequence(0, argSql.length() - 1)), args);
    }

    @Override
    public void deleteFloatColByRequestTaskId(List<String> requestRegionIds, Integer routeNum) {
        String sql = "DELETE FROM BDE_RESULT_FLOATCOL_%1$d WHERE REQUESTREGIONID in (%2$s) ";
        StringBuilder argSql = new StringBuilder();
        Object[] args = new Object[requestRegionIds.size()];
        for (int i = 0; i < requestRegionIds.size(); ++i) {
            argSql.append("?,");
            args[i] = requestRegionIds.get(i);
        }
        this.getJdbcTemplate().update(String.format(sql, routeNum, argSql.subSequence(0, argSql.length() - 1)), args);
    }

    @Override
    public void deleteFixedByRequestTaskId(String requestTaskId, String formId, String regionId, Integer routeNum) {
        String sql = "DELETE FROM BDE_RESULT_FIXED_%1$d WHERE REQUESTTASKID = ? AND FORMID = ?  AND REGIONID = ?";
        this.getJdbcTemplate().update(String.format(sql, routeNum), new Object[]{requestTaskId, formId, regionId});
    }

    @Override
    public void deleteFloatDefineByRequestTaskId(String requestTaskId, String formId, String regionId, Integer routeNum) {
        String sql = "DELETE FROM BDE_RESULT_FLOATDEFINE_%1$d WHERE REQUESTTASKID = ? AND FORMID = ? AND REGIONID = ? ";
        this.getJdbcTemplate().update(String.format(sql, routeNum), new Object[]{requestTaskId, formId, regionId});
    }

    @Override
    public List<String> getRequestRegionIdsByFormId(String requestTaskId, String formId, String regionId, Integer routeNum) {
        String sql = "SELECT REQUESTREGIONID FROM BDE_RESULT_FLOATDEFINE_%1$d  WHERE REQUESTTASKID  = ? AND FORMID = ? AND REGIONID = ? ";
        return this.getJdbcTemplate().query(String.format(sql, routeNum), (RowMapper)new StringRowMapper(), new Object[]{requestTaskId, formId, regionId});
    }
}

