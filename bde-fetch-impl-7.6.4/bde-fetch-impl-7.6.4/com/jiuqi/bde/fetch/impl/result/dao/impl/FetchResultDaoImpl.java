/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.base.intf.FetchResultDim
 *  com.jiuqi.bde.base.intf.FloatColDataPojo
 *  com.jiuqi.bde.base.intf.FloatColResultVO
 *  com.jiuqi.bde.base.intf.FloatDefineEO
 *  com.jiuqi.bde.bizmodel.define.FetchResult
 *  com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil
 *  com.jiuqi.bde.bizmodel.execute.config.FetchResultConfig
 *  com.jiuqi.bde.bizmodel.execute.dto.FloatRowResultEO
 *  com.jiuqi.bde.common.constant.ColumnTypeEnum
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 *  com.jiuqi.bde.common.template.IntegerResultSetExtractor
 *  com.jiuqi.bde.common.template.StringRowMapper
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.fetch.impl.result.dao.impl;

import com.jiuqi.bde.base.intf.FetchResultDim;
import com.jiuqi.bde.base.intf.FloatColDataPojo;
import com.jiuqi.bde.base.intf.FloatColResultVO;
import com.jiuqi.bde.base.intf.FloatDefineEO;
import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil;
import com.jiuqi.bde.bizmodel.execute.config.FetchResultConfig;
import com.jiuqi.bde.bizmodel.execute.dto.FloatRowResultEO;
import com.jiuqi.bde.common.constant.ColumnTypeEnum;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import com.jiuqi.bde.common.template.IntegerResultSetExtractor;
import com.jiuqi.bde.common.template.StringRowMapper;
import com.jiuqi.bde.fetch.impl.result.dao.FetchResultDao;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FetchResultDaoImpl
extends BaseDataCenterDaoImpl
implements FetchResultDao {
    @Autowired
    private DataSourceService dataSourceService;
    @Value(value="${jiuqi.bde.fetch.result.table.scale:2}")
    private int hanaDatabaseStrToDecimalScale;
    @Value(value="${jiuqi.bde.fetch.result.table.precision:30}")
    private int hanaDatabaseStrToDecimalPrecision;

    @Override
    public void insertFixedResult(FetchResultDim fetchResultDim, List<FetchResult> fixedResultEOList) {
        if (fixedResultEOList == null || fixedResultEOList.size() == 0) {
            return;
        }
        String sql = "insert into BDE_RESULT_FIXED_%1$d (REQUESTTASKID, FORMID, REGIONID, FIELDDEFINEID, FETCHSETTINGID, SIGN, ZBVALUE, ZBVALUETYPE) values (?, ?, ?, ?, ?, ?, ? ,?) ";
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (FetchResult fixedResultEO : fixedResultEOList) {
            Object[] args = new Object[]{fetchResultDim.getRequestTaskId(), fetchResultDim.getFormId(), fetchResultDim.getRegionId(), fixedResultEO.getFieldDefineId(), fixedResultEO.getFetchSettingId(), fixedResultEO.getSign(), fixedResultEO.getZbValue(), fixedResultEO.getZbValueType().getCode()};
            batchArgs.add(args);
        }
        this.getJdbcTemplate().batchUpdate(String.format(sql, fetchResultDim.getRouteNum()), batchArgs);
    }

    @Override
    public void insertFloatRowDefine(FetchResultDim fetchResultDim, List<FloatDefineEO> floatDefineEOList) {
        if (CollectionUtils.isEmpty(floatDefineEOList)) {
            return;
        }
        String sql = "insert into BDE_RESULT_FLOATDEFINE_%1$d (REQUESTTASKID, FORMID, REGIONID, REQUESTREGIONID, FIELDDEFINEORDER, FIELDDEFINEID, FIELDDEFINETYPE, FIELDDEFINENAME) VALUES (? ,? ,? ,? ,? ,? ,? ,?)";
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (FloatDefineEO floatDefineEO : floatDefineEOList) {
            Object[] args = new Object[]{fetchResultDim.getRequestTaskId(), fetchResultDim.getFormId(), fetchResultDim.getRegionId(), fetchResultDim.getRequestRegionId(), floatDefineEO.getFieldDefineOrder(), floatDefineEO.getFieldDefineId(), floatDefineEO.getFieldDefineType(), floatDefineEO.getFieldDefineName()};
            batchArgs.add(args);
        }
        this.getJdbcTemplate().batchUpdate(String.format(sql, fetchResultDim.getRouteNum()), batchArgs);
    }

    @Override
    public void insertFloatRowResult(FetchResultDim fetchResultDim, List<FloatRowResultEO> floatRowResultEOList) {
        if (CollectionUtils.isEmpty(floatRowResultEOList)) {
            return;
        }
        if (floatRowResultEOList.get(0).getZbValues().size() > FetchResultConfig.fetchResultTableFieldNum) {
            throw new BusinessRuntimeException("\u6d6e\u52a8\u8868\u53d6\u6570\u5b57\u6bb5\u8d85\u51fa\u7ed3\u679c\u5217\u6570\u91cf\uff0c\u8bf7\u4fee\u6539\u7cfb\u7edf\u652f\u6301\u7684\u6700\u5927\u7ed3\u679c\u5217\u6570\u91cf");
        }
        String sql = "insert into BDE_RESULT_FLOATROW_%1$d (REQUESTREGIONID, FLOATORDER %2$s) values (?, ?  %3$s) ";
        StringBuilder zbValues = new StringBuilder();
        StringBuilder valuesSql = new StringBuilder();
        for (int i = 0; i < floatRowResultEOList.get(0).getZbValues().size(); ++i) {
            zbValues.append(String.format(",ZBVALUE_%1$d", i));
            valuesSql.append(",?");
        }
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (FloatRowResultEO floatRowResultEO : floatRowResultEOList) {
            Object[] args = new Object[2 + floatRowResultEO.getZbValues().size()];
            args[0] = fetchResultDim.getRequestRegionId();
            args[1] = floatRowResultEO.getFloatOrder();
            System.arraycopy(floatRowResultEO.getZbValues().toArray(new String[0]), 0, args, 2, floatRowResultEO.getZbValues().size());
            batchArgs.add(args);
        }
        this.getJdbcTemplate().batchUpdate(String.format(sql, fetchResultDim.getRouteNum(), zbValues.toString(), valuesSql.toString()), batchArgs);
    }

    @Override
    public void insertFloatColResult(FetchResultDim fetchResultDim, List<FetchResult> floatColResultEOList) {
        if (floatColResultEOList == null || floatColResultEOList.size() == 0) {
            return;
        }
        String sql = "insert into BDE_RESULT_FLOATCOL_%1$d (REQUESTREGIONID, FLOATORDER, FIELDDEFINEID, FETCHSETTINGID,  SIGN, ZBVALUE, ZBVALUETYPE) values (?, ?, ?, ?, ? , ?, ?) ";
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (FetchResult floatColResultEO : floatColResultEOList) {
            Object[] args = new Object[]{fetchResultDim.getRequestRegionId(), floatColResultEO.getFloatOrder(), floatColResultEO.getFieldDefineId(), floatColResultEO.getFetchSettingId(), floatColResultEO.getSign(), floatColResultEO.getZbValue(), floatColResultEO.getZbValueType().getCode()};
            batchArgs.add(args);
        }
        this.getJdbcTemplate().batchUpdate(String.format(sql, fetchResultDim.getRouteNum()), batchArgs);
    }

    @Override
    public Set<String> getFloatRowRegionSet(String requestTaskId, String formId, Integer routeNum) {
        StringBuffer sql = new StringBuffer();
        sql.append("select  t.REGIONID \n");
        sql.append("  from BDE_RESULT_FLOATDEFINE_%1$d t \n");
        sql.append(" where t.REQUESTTASKID = ? and t.FORMID = ? \n");
        sql.append(" group by T.REGIONID \n");
        return (Set)this.getJdbcTemplate().query(String.format(sql.toString(), routeNum), rs -> {
            HashSet<String> regionIdSet = new HashSet<String>();
            while (rs.next()) {
                regionIdSet.add(rs.getString(1));
            }
            return regionIdSet;
        }, new Object[]{requestTaskId, formId});
    }

    @Override
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
                floatDefineEO.setFieldDefineOrder(Integer.valueOf(rs.getInt(1)));
                floatDefineEO.setFieldDefineType(rs.getString(2));
                floatDefineEO.setFieldDefineName(rs.getString(3));
                floatDefineEOList.add(floatDefineEO);
            }
            return floatDefineEOList;
        }, new Object[]{fetchResultDim.getRequestRegionId()});
    }

    @Override
    public List<Map<String, Object>> getFloatRowResultsWithType(FetchResultDim fetchResultDim) {
        List<FloatDefineEO> list = this.getFloatDefineList(fetchResultDim);
        StringBuffer sql = new StringBuffer();
        sql.append("select t.FLOATORDER  %1$s \n");
        sql.append("  from BDE_RESULT_FLOATROW_%2$d t \n");
        sql.append(" where REQUESTREGIONID = ? \n");
        sql.append(" order by T.FLOATORDER \n");
        StringBuilder zbValueSql = new StringBuilder();
        for (int i = 0; i < list.size(); ++i) {
            zbValueSql.append(String.format(", ZBVALUE_%1$d", i));
        }
        List<FloatDefineEO> floatDefineEOList = this.getFloatDefineList(fetchResultDim);
        Map floatDefineMap = floatDefineEOList.stream().collect(Collectors.toMap(FloatDefineEO::getFieldDefineName, Function.identity(), (K1, K2) -> K1));
        return (List)this.getJdbcTemplate().query(String.format(sql.toString(), zbValueSql.toString(), fetchResultDim.getRouteNum()), rs -> {
            ArrayList results = new ArrayList(512);
            while (rs.next()) {
                HashMap<String, Object> resultMap = new HashMap<String, Object>();
                for (Map.Entry entry : floatDefineMap.entrySet()) {
                    Object value = null;
                    switch (Objects.requireNonNull(ColumnTypeEnum.getEnumByName((String)((FloatDefineEO)entry.getValue()).getFieldDefineType()))) {
                        case INT: {
                            value = rs.getInt(((FloatDefineEO)entry.getValue()).getFieldDefineOrder() + 2);
                            break;
                        }
                        case NUMBER: {
                            value = rs.getBigDecimal(((FloatDefineEO)entry.getValue()).getFieldDefineOrder() + 2) == null ? BigDecimal.ZERO : rs.getBigDecimal(((FloatDefineEO)entry.getValue()).getFieldDefineOrder() + 2);
                            break;
                        }
                        default: {
                            value = rs.getString(((FloatDefineEO)entry.getValue()).getFieldDefineOrder() + 2) == null ? "" : rs.getString(((FloatDefineEO)entry.getValue()).getFieldDefineOrder() + 2);
                        }
                    }
                    resultMap.put("${" + (String)entry.getKey() + "}", value);
                }
                results.add(resultMap);
            }
            return results;
        }, new Object[]{fetchResultDim.getRequestRegionId()});
    }

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
    public Map<String, Map<String, Object>> getFixedSumResults(String requestTaskId, String formId, Integer routeNum) {
        StringBuffer sql = new StringBuffer();
        IDbSqlHandler dbSqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(DataSourceService.CURRENT));
        sql.append("select T.REGIONID, T.FIELDDEFINEID, SUM(T.SIGN * ").append(dbSqlHandler.toDecimal("T.ZBVALUE", this.hanaDatabaseStrToDecimalPrecision, this.hanaDatabaseStrToDecimalScale)).append(") ZBVALUE \n");
        sql.append("  from BDE_RESULT_FIXED_%1$d t \n");
        sql.append(" where 1 = 1 \n");
        sql.append("   and t.REQUESTTASKID = ? and t.FORMID = ? \n");
        sql.append(" group by T.REGIONID, T.FIELDDEFINEID  \n");
        return (Map)this.getJdbcTemplate().query(String.format(sql.toString(), routeNum), rs -> {
            HashMap result = new HashMap();
            while (rs.next()) {
                if (!result.containsKey(rs.getString(1))) {
                    result.put(rs.getString(1), new HashMap());
                }
                ((Map)result.get(rs.getString(1))).put(rs.getString(2), rs.getDouble(3));
            }
            return result;
        }, new Object[]{requestTaskId, formId});
    }

    @Override
    public Map<String, Map<String, Map<String, Object>>> getFixedDetailResults(String requestTaskId, String formId, List<String> formulaFieldDefineIdList, Integer routeNum) {
        StringBuffer sql = new StringBuffer();
        IDbSqlHandler dbSqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(DataSourceService.CURRENT));
        sql.append("select T.REGIONID, T.FIELDDEFINEID, T.FETCHSETTINGID, SUM(T.SIGN * ").append(dbSqlHandler.toDecimal("T.ZBVALUE", this.hanaDatabaseStrToDecimalPrecision, this.hanaDatabaseStrToDecimalScale)).append(") ZBVALUE \n");
        sql.append("  from BDE_RESULT_FIXED_%1$d t \n");
        sql.append(" where 1 = 1 \n");
        sql.append("   and t.REQUESTTASKID = ? and t.FORMID = ? \n");
        sql.append("   and ").append(SqlBuildUtil.getStrInCondi((String)" T.FIELDDEFINEID ", formulaFieldDefineIdList));
        sql.append(" group by T.REGIONID, T.FIELDDEFINEID, T.FETCHSETTINGID, T.SIGN  \n");
        return (Map)this.getJdbcTemplate().query(String.format(sql.toString(), routeNum), rs -> {
            HashMap result = new HashMap();
            while (rs.next()) {
                if (!result.containsKey(rs.getString(1))) {
                    result.put(rs.getString(1), new HashMap());
                }
                if (!((Map)result.get(rs.getString(1))).containsKey(rs.getString(2))) {
                    ((Map)result.get(rs.getString(1))).put(rs.getString(2), new HashMap());
                }
                ((Map)((Map)result.get(rs.getString(1))).get(rs.getString(2))).put(rs.getString(3), rs.getDouble(4));
            }
            return result;
        }, new Object[]{requestTaskId, formId});
    }

    @Override
    public Map<String, Object> getFixedSumResults(String requestTaskId, String formId, String regionId, Integer routeNum) {
        StringBuffer sql = new StringBuffer();
        IDbSqlHandler dbSqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(DataSourceService.CURRENT));
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
                colDataPojo.setFieldDefineLength(Integer.valueOf(fieldDefineIdx + 1));
                colDataPojo.setZbSettingLength(Integer.valueOf(maxFetchSettingIdx + 1));
                return colDataPojo;
            }
        }, new Object[]{fetchResultDim.getRequestRegionId()});
    }

    @Override
    public Integer countByTableName(String tableName) {
        String sql = "SELECT COUNT(1) FROM %1$s";
        return (Integer)this.getJdbcTemplate().query(String.format(sql, tableName), (ResultSetExtractor)new IntegerResultSetExtractor());
    }

    @Override
    public void truncateByTableName(String tableName) {
        String sql = "TRUNCATE TABLE %1$s";
        this.getJdbcTemplate().update(String.format(sql, tableName));
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

