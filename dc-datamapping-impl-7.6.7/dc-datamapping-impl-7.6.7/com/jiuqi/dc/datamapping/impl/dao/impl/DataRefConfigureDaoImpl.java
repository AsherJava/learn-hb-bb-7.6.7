/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.Pair
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.base.common.jdbc.extractor.IntegerResultSetExtractor
 *  com.jiuqi.dc.base.common.jdbc.extractor.StringResultSetExtractor
 *  com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefListDTO
 *  com.jiuqi.dc.datamapping.client.dto.IsolationParamContext
 *  com.jiuqi.dc.mappingscheme.client.common.DataRefUtil
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.organization.service.OrgAuthService
 *  io.jsonwebtoken.lang.Arrays
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.datamapping.impl.dao.impl;

import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.Pair;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.common.jdbc.extractor.IntegerResultSetExtractor;
import com.jiuqi.dc.base.common.jdbc.extractor.StringResultSetExtractor;
import com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import com.jiuqi.dc.datamapping.client.dto.IsolationParamContext;
import com.jiuqi.dc.datamapping.impl.dao.DataRefConfTempTableDao;
import com.jiuqi.dc.datamapping.impl.dao.DataRefConfigureDao;
import com.jiuqi.dc.datamapping.impl.enums.RefHandleStatus;
import com.jiuqi.dc.datamapping.impl.utils.DataRefResultExtractor;
import com.jiuqi.dc.datamapping.impl.utils.IsolationUtil;
import com.jiuqi.dc.mappingscheme.client.common.DataRefUtil;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.organization.service.OrgAuthService;
import io.jsonwebtoken.lang.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

@Repository
@Primary
public class DataRefConfigureDaoImpl
extends BaseDataCenterDaoImpl
implements DataRefConfigureDao {
    @Autowired
    private DataRefConfTempTableDao tmpTableDao;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private OrgAuthService orgAuthService;
    private static final String SQL_CONTENT = "SELECT * FROM (%1$s) T WHERE 1 = 1  ";
    private static final String SQL_COUNT = "SELECT COUNT(1) FROM (%1$s) T WHERE 1 = 1  ";
    private static final String FN_PERENT_SYMBOL = "%";
    private static final String FN_UPDATE_FIELD_SQL = ", %s = ?";
    private static final int TMP_TABLE_LIMIT = 2000;
    private static final String SELECT_SQL = "SELECT ID, DATASCHEMECODE, ODS_CODE, ODS_NAME, CODE, AUTOMATCHFLAG FROM %1$s WHERE 1 = 1 %2$s ";
    private static final String INSERT_SQL = "INSERT INTO %1$s   (ID, DATASCHEMECODE, CODE, ODS_CODE, ODS_NAME, AUTOMATCHFLAG, HANDLESTATUS, OPERATOR, OPERATETIME %2$s) VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ? %3$s)";
    private static final String UPDATE_SQL = "UPDATE %1$s  SET CODE = ?, AUTOMATCHFLAG = ?, HANDLESTATUS = ?, OPERATOR = ?, OPERATETIME = ? %2$s WHERE ID = ? ";
    private static final String DELETE_SQL = "DELETE FROM %1$s WHERE 1 = 1 ";

    @Override
    public int countAll(BaseDataMappingDefineDTO define, DataRefListDTO dto) {
        StringBuilder sqlTemplate = new StringBuilder(String.format(SQL_COUNT, this.buildSelectAllSql(define)));
        List<Object> values = this.buildFilterParamSql(define, dto, sqlTemplate);
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().query(sqlTemplate.toString(), (ResultSetExtractor)new IntegerResultSetExtractor(), values.toArray());
    }

    @Override
    public List<DataRefDTO> selectAll(BaseDataMappingDefineDTO define, DataRefListDTO dto) {
        StringBuilder sqlTemplate = new StringBuilder(String.format(SQL_CONTENT, this.buildSelectAllSql(define)));
        List<Object> values = this.buildFilterParamSql(define, dto, sqlTemplate);
        return (List)OuterDataSourceUtils.getJdbcTemplate().query(this.getPaginationSql(sqlTemplate.toString(), dto.getPagination(), dto.getPageNum(), dto.getPageSize()), (ResultSetExtractor)new DataRefResultExtractor(define, true), values.toArray());
    }

    private StringBuilder buildSelectAllSql(BaseDataMappingDefineDTO define) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT REFTABLE.ID AS ID,  ");
        sql.append("       REFTABLE.CODE AS CODE,  ");
        Pair<Boolean, String> pair = IsolationUtil.buildAdvancedSqlWithUnit(define);
        String joinSql = "";
        for (Pair<String, String> field : IsolationUtil.listDynamicRefField(define)) {
            if ("DC_UNITCODE".equals(field.getFirst())) {
                sql.append(String.format("  CASE WHEN " + OuterDataSourceUtils.getJdbcTemplate().getIDbSqlHandler().judgeEmpty("REFTABLE.ID", true) + " THEN REFORGTABLE.CODE ELSE REFTABLE.%1$s END AS %1$s, ", field.getFirst()));
                joinSql = String.format("  LEFT JOIN REF_MD_ORG REFORGTABLE ON REFORGTABLE.CODE = ODSTABLE.%1$s", (Boolean)pair.getFirst() != false ? "REAL_UNITCODE" : field.getSecond());
                continue;
            }
            sql.append(String.format("  CASE WHEN " + OuterDataSourceUtils.getJdbcTemplate().getIDbSqlHandler().judgeEmpty("REFTABLE.ID", true) + " THEN ODSTABLE.%2$s ELSE REFTABLE.%1$s END AS %1$s, ", field.getFirst(), field.getSecond()));
        }
        sql.append("       REFTABLE.HANDLESTATUS AS HANDLESTATUS,  ");
        sql.append("       REFTABLE.OPERATOR AS OPERATOR,  ");
        sql.append("       REFTABLE.OPERATETIME AS OPERATETIME,  ");
        sql.append("       CASE WHEN REFTABLE.AUTOMATCHFLAG IS NULL THEN 0 ELSE 1 END  AS AUTOMATCHFLAG  ");
        sql.append("  FROM (  ");
        sql.append((String)pair.getSecond());
        sql.append(" ) ODSTABLE  ");
        sql.append("  LEFT JOIN ").append(DataRefUtil.getRefTableName((String)define.getCode())).append(" REFTABLE  ");
        sql.append("    ON REFTABLE.DATASCHEMECODE = ? AND ODSTABLE.CODE = REFTABLE.ODS_CODE AND REFTABLE.HANDLESTATUS <> 4 ");
        this.builderIsolationSql(sql, define);
        sql.append(joinSql);
        return sql;
    }

    @Override
    public int countUnref(BaseDataMappingDefineDTO define, DataRefListDTO dto) {
        StringBuilder sqlTemplate = new StringBuilder(String.format(SQL_COUNT, this.buildSelectUnRefSql(define)));
        if (dto.getFilterParam() != null && !dto.getFilterParam().isEmpty()) {
            Map<String, String> filterParam = dto.getFilterParam().entrySet().stream().filter(item -> !((String)item.getKey()).equals("CODE") && !((String)item.getKey()).equals("NAME")).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            dto.setFilterParam(filterParam);
        }
        List<Object> values = this.buildFilterParamSql(define, dto, sqlTemplate);
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().query(sqlTemplate.toString(), (ResultSetExtractor)new IntegerResultSetExtractor(), values.toArray());
    }

    @Override
    public List<DataRefDTO> selectUnref(BaseDataMappingDefineDTO define, DataRefListDTO dto) {
        StringBuilder sqlTemplate = new StringBuilder(String.format(SQL_CONTENT, this.buildSelectUnRefSql(define)));
        List<Object> values = this.buildFilterParamSql(define, dto, sqlTemplate);
        return (List)OuterDataSourceUtils.getJdbcTemplate().query(this.getPaginationSql(sqlTemplate.toString(), dto.getPagination(), dto.getPageNum(), dto.getPageSize()), (ResultSetExtractor)new DataRefResultExtractor(define, false), values.toArray());
    }

    private StringBuilder buildSelectUnRefSql(BaseDataMappingDefineDTO define) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        Pair<Boolean, String> pair = IsolationUtil.buildAdvancedSqlWithUnit(define);
        for (Pair<String, String> field : IsolationUtil.listDynamicRefField(define)) {
            String odsField = (Boolean)pair.getFirst() != false && "DC_UNITCODE".equals(field.getFirst()) ? "REAL_UNITCODE" : (String)field.getSecond();
            sql.append(String.format("  ODSTABLE.%2$s AS %1$s, ", field.getFirst(), odsField));
        }
        sql.append("  CODE, NAME ");
        sql.append("  FROM (  ");
        sql.append((String)pair.getSecond());
        sql.append(" ) ODSTABLE  ");
        sql.append(" WHERE NOT EXISTS  ");
        sql.append(" (SELECT 1 FROM ").append(DataRefUtil.getRefTableName((String)define.getCode())).append(" REFTABLE WHERE REFTABLE.DATASCHEMECODE = ?  AND REFTABLE.ODS_CODE = ODSTABLE.CODE ");
        this.builderIsolationSql(sql, define);
        sql.append(") ");
        return sql;
    }

    @Override
    public int countHasref(BaseDataMappingDefineDTO define, DataRefListDTO dto) {
        StringBuilder sqlTemplate = new StringBuilder(String.format(SQL_COUNT, this.buildSelectHasRefSql(define, dto.getDataSchemeCodeList(), null)));
        List<Object> values = this.buildFilterParamSql(define, dto, sqlTemplate);
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().query(sqlTemplate.toString(), (ResultSetExtractor)new IntegerResultSetExtractor(), values.toArray());
    }

    @Override
    public List<DataRefDTO> selectHasref(BaseDataMappingDefineDTO define, DataRefListDTO dto) {
        StringBuilder sqlTemplate = new StringBuilder(String.format(SQL_CONTENT, this.buildSelectHasRefSql(define, dto.getDataSchemeCodeList(), null)));
        List<Object> values = this.buildFilterParamSql(define, dto, sqlTemplate);
        return (List)OuterDataSourceUtils.getJdbcTemplate().query(this.getPaginationSql(sqlTemplate.toString(), dto.getPagination(), dto.getPageNum(), dto.getPageSize()), (ResultSetExtractor)new DataRefResultExtractor(define, true), values.toArray());
    }

    @Override
    public int countMultiSchemeHasref(BaseDataMappingDefineDTO define, DataRefListDTO dto, Set<String> isolationList) {
        StringBuilder sqlTemplate = new StringBuilder(String.format(SQL_COUNT, this.buildSelectHasRefSql(define, dto.getDataSchemeCodeList(), isolationList)));
        List<Object> values = this.buildFilterParamSql((DataMappingDefineDTO)define, dto, sqlTemplate, isolationList);
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().query(sqlTemplate.toString(), (ResultSetExtractor)new IntegerResultSetExtractor(), values.toArray());
    }

    @Override
    public List<DataRefDTO> selectMultiSchemeHasref(BaseDataMappingDefineDTO define, DataRefListDTO dto, Set<String> isolationList) {
        StringBuilder sqlTemplate = new StringBuilder(String.format(SQL_CONTENT, this.buildSelectHasRefSql(define, dto.getDataSchemeCodeList(), isolationList)));
        List<Object> values = this.buildFilterParamSql((DataMappingDefineDTO)define, dto, sqlTemplate, isolationList);
        return (List)OuterDataSourceUtils.getJdbcTemplate().query(this.getPaginationSql(sqlTemplate.toString(), dto.getPagination(), dto.getPageNum(), dto.getPageSize()), (ResultSetExtractor)new DataRefResultExtractor(define, true, dto.getDataSchemeCodeList(), isolationList), values.toArray());
    }

    private StringBuilder buildSelectHasRefSql(BaseDataMappingDefineDTO define, List<String> codes, Set<String> isolationList) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT REFTABLE.ID  AS ID,  ");
        sql.append("       REFTABLE.CODE AS CODE,  ");
        sql.append("       REFTABLE.DATASCHEMECODE,  ");
        Set<String> fieldList = IsolationUtil.listDynamicField(define, isolationList);
        for (String field : fieldList) {
            sql.append(String.format("       REFTABLE.%1$s, ", field));
        }
        sql.append("       REFTABLE.ODS_CODE AS ODS_CODE,  ");
        sql.append("       REFTABLE.ODS_NAME AS ODS_NAME,  ");
        sql.append("       REFTABLE.HANDLESTATUS AS HANDLESTATUS,  ");
        sql.append("       REFTABLE.OPERATOR AS OPERATOR,  ");
        sql.append("       REFTABLE.OPERATETIME AS OPERATETIME,  ");
        sql.append("       CASE WHEN REFTABLE.AUTOMATCHFLAG IS NULL THEN 0 ELSE 1 END  AS AUTOMATCHFLAG  ");
        sql.append("  FROM ").append(DataRefUtil.getRefTableName((String)define.getCode())).append(" REFTABLE  ");
        sql.append("WHERE 1 = 1");
        sql.append("  AND REFTABLE.HANDLESTATUS <> 4");
        if (Objects.nonNull(define.getDataSchemeCode())) {
            sql.append("    AND REFTABLE.DATASCHEMECODE = ?");
        } else if (!CollectionUtils.isEmpty(codes)) {
            sql.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"REFTABLE.DATASCHEMECODE", codes));
        }
        sql.append("  ORDER BY (CASE REFTABLE.HANDLESTATUS WHEN 0 THEN 0 ELSE 1 END), REFTABLE.DATASCHEMECODE, ");
        if (!CollectionUtils.isEmpty(fieldList)) {
            sql.append(StringUtils.join((Object[])fieldList.toArray(), (String)", REFTABLE."));
            sql.append(", ");
        }
        sql.append("REFTABLE.ODS_CODE, REFTABLE.CODE ");
        return sql;
    }

    @Override
    public int countAutoMatch(BaseDataMappingDefineDTO define, DataRefListDTO dto) {
        StringBuilder sqlTemplate = new StringBuilder(String.format(SQL_COUNT, this.buildSelectAutoMatchSql(define)));
        List<Object> values = this.buildFilterParamSql(define, dto, sqlTemplate);
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().query(sqlTemplate.toString(), (ResultSetExtractor)new IntegerResultSetExtractor(), values.toArray());
    }

    @Override
    public int countResultByDataSource(String dataSourceCode, BaseDataMappingDefineDTO define, DataRefListDTO dto) {
        StringBuilder sqlTemplate = new StringBuilder(String.format(SQL_COUNT, this.getResultByDataSourceSql(dataSourceCode, define, dto)));
        return (Integer)this.dataSourceService.query(dataSourceCode, sqlTemplate.toString(), null, rs -> rs.next() ? rs.getInt(1) : 0);
    }

    @Override
    public List<DataRefDTO> selectAutoMatch(BaseDataMappingDefineDTO define, DataRefListDTO dto) {
        StringBuilder sqlTemplate = new StringBuilder(String.format(SQL_CONTENT, this.buildSelectAutoMatchSql(define)));
        List<Object> values = this.buildFilterParamSql(define, dto, sqlTemplate);
        return OuterDataSourceUtils.getJdbcTemplate().query(this.getPaginationSql(sqlTemplate.toString(), dto.getPagination(), dto.getPageNum(), dto.getPageSize()), (RowMapper)new BeanPropertyRowMapper(DataRefDTO.class), values.toArray());
    }

    private StringBuilder buildSelectAutoMatchSql(BaseDataMappingDefineDTO define) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT REFTABLE.ID    AS ID,  ");
        Pair<Boolean, String> pair = IsolationUtil.buildAdvancedSqlWithUnit(define);
        String joinSql = "";
        for (Pair<String, String> field : IsolationUtil.listDynamicRefField(define)) {
            if ("DC_UNITCODE".equals(field.getFirst())) {
                sql.append(String.format("  REFORGTABLE.CODE AS %1$s, ", field.getFirst()));
                joinSql = String.format("  JOIN REF_MD_ORG REFORGTABLE ON REFORGTABLE.CODE = ODSTABLE.%1$s", (Boolean)pair.getFirst() != false ? "REAL_UNITCODE" : field.getSecond());
                continue;
            }
            sql.append(String.format("  ODSTABLE.%2$s AS %1$s, ", field.getFirst(), field.getSecond()));
        }
        sql.append("       REFTABLE.CODE AS CODE,  ");
        sql.append("       CASE WHEN REFTABLE.AUTOMATCHFLAG IS NULL THEN 0 ELSE 1 END  AS AUTOMATCHFLAG  ");
        sql.append("  FROM (  ");
        sql.append((String)pair.getSecond());
        sql.append(" ) ODSTABLE  ");
        sql.append("  JOIN ").append(DataRefUtil.getRefTableName((String)define.getCode())).append(" REFTABLE  ");
        sql.append("    ON REFTABLE.DATASCHEMECODE = ? AND ODSTABLE.CODE = REFTABLE.ODS_CODE AND REFTABLE.AUTOMATCHFLAG = 1 AND REFTABLE.HANDLESTATUS <> 4 ");
        this.builderIsolationSql(sql, define);
        sql.append(joinSql);
        return sql;
    }

    private List<Object> buildFilterParamSql(BaseDataMappingDefineDTO define, DataRefListDTO dto, StringBuilder sqlTemplate) {
        HashSet set = CollectionUtils.newHashSet();
        set.add(define.getIsolationStrategy());
        return this.buildFilterParamSql((DataMappingDefineDTO)define, dto, sqlTemplate, set);
    }

    private List<Object> buildFilterParamSql(DataMappingDefineDTO define, DataRefListDTO dto, StringBuilder sqlTemplate, Set<String> isolationList) {
        ArrayList<Object> values = new ArrayList<Object>();
        if (Objects.nonNull(define.getDataSchemeCode())) {
            values.add(define.getDataSchemeCode());
        }
        if (dto.getFilterParam() != null && !dto.getFilterParam().isEmpty()) {
            for (Map.Entry entry : dto.getFilterParam().entrySet()) {
                if (StringUtils.isEmpty((String)((String)entry.getValue()))) continue;
                if ("HANDLESTATUS".equals(entry.getKey())) {
                    sqlTemplate.append(" AND ");
                    String[] statusByte = ((String)entry.getValue()).split(",");
                    ArrayList statusList = CollectionUtils.newArrayList();
                    for (String s : statusByte) {
                        statusList.add(Integer.parseInt(s));
                    }
                    sqlTemplate.append(SqlBuildUtil.getIntegerInCondi((String)String.format("T.%1$s", entry.getKey()), (List)statusList));
                    continue;
                }
                if ("STARTTIME".equals(entry.getKey())) {
                    sqlTemplate.append(String.format(" AND T.%1$s >= ? ", "OPERATETIME"));
                    values.add(DateUtils.parse((String)((String)entry.getValue()), (String)"yyyy-MM-dd HH:mm:ss"));
                    continue;
                }
                if ("ENDTIME".equals(entry.getKey())) {
                    sqlTemplate.append(String.format(" AND T.%1$s <= ? ", "OPERATETIME"));
                    values.add(DateUtils.parse((String)((String)entry.getValue()), (String)"yyyy-MM-dd HH:mm:ss"));
                    continue;
                }
                if ("DC_UNITCODE".equals(entry.getKey())) {
                    sqlTemplate.append(" AND ");
                    Object[] unitByte = ((String)entry.getValue()).split(",");
                    sqlTemplate.append(SqlBuildUtil.getStrInCondi((String)String.format("T.%1$s", entry.getKey()), (List)Arrays.asList((Object[])unitByte)));
                    continue;
                }
                sqlTemplate.append(String.format(" AND T.%1$s LIKE ? ", entry.getKey()));
                values.add(this.formatFilterCondi((String)entry.getValue()));
            }
        }
        if (dto.getFilterParamList() != null && !dto.getFilterParamList().isEmpty()) {
            sqlTemplate.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"T.ODS_CODE", (List)dto.getFilterParamList()));
        }
        boolean flag1 = false;
        boolean flag2 = false;
        for (String isolationStrategy : isolationList) {
            if (IsolationStrategy.getIsolationFieldByCode((String)isolationStrategy).contains("DC_UNITCODE")) {
                flag1 = true;
            }
            if (!IsolationStrategy.SHARE_ISOLATION.getCode().equals(isolationStrategy)) continue;
            flag2 = true;
            break;
        }
        if (flag1 && StringUtils.isEmpty((String)((String)dto.getFilterParam().get("DC_UNITCODE")))) {
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setTenantName(ShiroUtil.getTenantName());
            orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
            orgDTO.setCategoryname("MD_ORG");
            Set authCodes = this.orgAuthService.listAuthOrg((UserDO)ShiroUtil.getUser(), orgDTO);
            if (CollectionUtils.isEmpty((Collection)authCodes)) {
                return values;
            }
            sqlTemplate.append(" AND (");
            sqlTemplate.append(SqlBuildUtil.getStrInCondi((String)String.format("T.%1$s", "DC_UNITCODE"), new ArrayList(authCodes)));
            sqlTemplate.append(" OR ");
            sqlTemplate.append(String.format("T.%1$s IS NULL", "DC_UNITCODE"));
            if (flag2) {
                sqlTemplate.append(String.format(" OR T.%1$s = '%2$s'", "DC_UNITCODE", "-"));
            }
            sqlTemplate.append(" ) ");
        }
        return values;
    }

    private String formatFilterCondi(String value) {
        return FN_PERENT_SYMBOL.concat(value).concat(FN_PERENT_SYMBOL);
    }

    @Override
    public List<DataRefDTO> selectByOdsIdList(String tableName, List<String> odsIdList) {
        return this.selectByOdsIdListAndScheme(null, tableName, odsIdList);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<DataRefDTO> selectByOdsIdListAndScheme(String dataSchemeCode, String tableName, List<String> odsIdList) {
        if (CollectionUtils.isEmpty(odsIdList)) {
            return CollectionUtils.newArrayList();
        }
        boolean enableTmpTable = odsIdList.size() > 2000;
        try {
            Object[] objectArray;
            StringBuilder sql = new StringBuilder(String.format(SELECT_SQL, DataRefUtil.getRefTableName((String)tableName), StringUtils.isEmpty((String)dataSchemeCode) ? "" : "AND DATASCHEMECODE = ?"));
            sql.append(" AND ");
            sql.append(SqlBuildUtil.getStrInCondi((String)"ODS_CODE", odsIdList));
            GcBizJdbcTemplate gcBizJdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
            String string = sql.toString();
            BeanPropertyRowMapper beanPropertyRowMapper = new BeanPropertyRowMapper(DataRefDTO.class);
            if (StringUtils.isEmpty((String)dataSchemeCode)) {
                objectArray = new Object[]{};
            } else {
                Object[] objectArray2 = new Object[1];
                objectArray = objectArray2;
                objectArray2[0] = dataSchemeCode;
            }
            List list = gcBizJdbcTemplate.query(string, (RowMapper)beanPropertyRowMapper, objectArray);
            return list;
        }
        finally {
            if (enableTmpTable) {
                this.tmpTableDao.cleanTmpTable();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<DataRefDTO> selectByCodeList(String tableName, List<String> codeList) {
        if (CollectionUtils.isEmpty(codeList)) {
            return CollectionUtils.newArrayList();
        }
        boolean enableTmpTable = codeList.size() > 2000;
        try {
            StringBuilder sql = new StringBuilder(String.format(SELECT_SQL, DataRefUtil.getRefTableName((String)tableName), ""));
            sql.append(" AND ");
            sql.append(SqlBuildUtil.getStrInCondi((String)"CODE", codeList));
            List list = OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new BeanPropertyRowMapper(DataRefDTO.class), new Object[0]);
            return list;
        }
        finally {
            if (enableTmpTable) {
                this.tmpTableDao.cleanTmpTable();
            }
        }
    }

    @Override
    public List<DataRefDTO> selectAllRefData(BaseDataMappingDefineDTO define) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT REFTABLE.ID, REFTABLE.CODE, REFTABLE.ODS_CODE, REFTABLE.ODS_NAME, REFTABLE.AUTOMATCHFLAG, REFTABLE.HANDLESTATUS, REFTABLE.OPERATOR, REFTABLE.OPERATETIME, ");
        for (String field : IsolationUtil.listDynamicField(define)) {
            sql.append(String.format("       REFTABLE.%1$s, ", field));
        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append(" FROM ").append(DataRefUtil.getRefTableName((String)define.getCode())).append(" REFTABLE \n");
        sql.append(" WHERE 1 = 1 AND REFTABLE.DATASCHEMECODE = ? AND REFTABLE.HANDLESTATUS <> 4");
        return (List)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (ResultSetExtractor)new DataRefResultExtractor(define, true), new Object[]{define.getDataSchemeCode()});
    }

    public String getResultByDataSourceSql(String dataSourceCode, BaseDataMappingDefineDTO define, DataRefListDTO dto) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ODSTABLE.CODE AS ODS_CODE, ODSTABLE.NAME AS ODS_NAME, ");
        Pair<Boolean, String> pair = IsolationUtil.buildAdvancedSqlWithUnit(define);
        for (Pair<String, String> field : IsolationUtil.listDynamicRefField(define)) {
            String odsField = (Boolean)pair.getFirst() != false && "DC_UNITCODE".equals(field.getFirst()) ? "REAL_UNITCODE" : (String)field.getSecond();
            sql.append(String.format("  ODSTABLE.%2$s AS %1$s, ", field.getFirst(), odsField));
        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append("  FROM ( \n");
        sql.append((String)pair.getSecond());
        sql.append(" ) ODSTABLE \n");
        sql.append(" WHERE 1 = 1 \n");
        if (!StringUtils.isEmpty((String)((String)dto.getFilterParam().get("ODS_CODE")))) {
            sql.append("   AND ODSTABLE.CODE LIKE '").append(this.formatFilterCondi((String)dto.getFilterParam().get("ODS_CODE"))).append("'");
        }
        if (dto.getFilterParamList() != null && !dto.getFilterParamList().isEmpty()) {
            sql.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"ODSTABLE.CODE", (List)dto.getFilterParamList()));
        }
        return sql.toString();
    }

    @Override
    public List<DataRefDTO> getResultByDataSource(String dataSourceCode, BaseDataMappingDefineDTO define, DataRefListDTO dto) {
        String sql = this.getResultByDataSourceSql(dataSourceCode, define, dto);
        return (List)this.dataSourceService.query(dataSourceCode, sql.toString(), null, (ResultSetExtractor)new DataRefResultExtractor(define, false));
    }

    @Override
    public List<DataRefDTO> getResultByDataSource(String dataSourceCode, BaseDataMappingDefineDTO define, DataRefListDTO dto, int initialCapacity) {
        String sql = this.getResultByDataSourceSql(dataSourceCode, define, dto);
        return (List)this.dataSourceService.query(dataSourceCode, sql.toString(), null, (ResultSetExtractor)new DataRefResultExtractor(define, false, initialCapacity));
    }

    @Override
    public void batchInsert(BaseDataMappingDefineDTO define, List<DataRefDTO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        StringBuilder externalSql = new StringBuilder();
        StringBuilder externalField = new StringBuilder();
        Set<String> fieldList = IsolationUtil.listDynamicField(define);
        if (!CollectionUtils.isEmpty(fieldList)) {
            externalSql.append(", ");
            externalField.append(", ");
            for (String field : fieldList) {
                externalSql.append(field).append(", ");
                externalField.append("?,");
            }
            externalSql.deleteCharAt(externalSql.lastIndexOf(","));
            externalField.deleteCharAt(externalField.lastIndexOf(","));
        }
        String insertSql = String.format(INSERT_SQL, DataRefUtil.getRefTableName((String)define.getCode()), externalSql.toString(), externalField.toString());
        ArrayList<Object[]> params = new ArrayList<Object[]>(list.size());
        for (DataRefDTO item : list) {
            params.add(this.convertInsertParam(define, item));
        }
        OuterDataSourceUtils.getJdbcTemplate().batchUpdate(insertSql, params);
    }

    private Object[] convertInsertParam(BaseDataMappingDefineDTO define, DataRefDTO item) {
        LinkedList list = CollectionUtils.newLinkedList();
        list.add(item.getId());
        list.add(item.getDataSchemeCode());
        list.add(item.getCode());
        list.add(item.getOdsCode());
        list.add(item.getOdsName());
        list.add(item.getAutoMatchFlag());
        list.add(item.getHandleStatus());
        list.add(item.getOperator());
        list.add(item.getOperateTime());
        for (String field : IsolationUtil.listDynamicField(define)) {
            list.add(item.get((Object)field));
        }
        return list.toArray();
    }

    @Override
    public void batchUpdate(BaseDataMappingDefineDTO define, List<DataRefDTO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        StringBuilder externalSql = new StringBuilder();
        for (String field : IsolationUtil.listDynamicField(define)) {
            externalSql.append(String.format(FN_UPDATE_FIELD_SQL, field));
        }
        String updateSql = String.format(UPDATE_SQL, DataRefUtil.getRefTableName((String)define.getCode()), externalSql.toString());
        ArrayList<Object[]> params = new ArrayList<Object[]>(list.size());
        for (DataRefDTO item : list) {
            params.add(this.convertUpdateParam(define, item));
        }
        OuterDataSourceUtils.getJdbcTemplate().batchUpdate(updateSql, params);
    }

    private Object[] convertUpdateParam(BaseDataMappingDefineDTO define, DataRefDTO item) {
        ArrayList<Object> list = new ArrayList<Object>();
        list.add(item.getCode());
        list.add(item.getAutoMatchFlag());
        list.add(item.getHandleStatus());
        list.add(item.getOperator());
        list.add(item.getOperateTime());
        for (String field : IsolationUtil.listDynamicField(define)) {
            list.add(item.get((Object)field));
        }
        list.add(item.getId());
        return list.toArray();
    }

    @Override
    public void batchDelete(String tableName, List<DataRefDTO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        List ids = list.stream().map(item -> item.getId()).collect(Collectors.toList());
        StringBuilder sql = new StringBuilder(String.format(DELETE_SQL, DataRefUtil.getRefTableName((String)tableName)));
        sql.append(" AND ");
        sql.append(SqlBuildUtil.getStrInCondi((String)"ID", ids));
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString());
    }

    @Override
    public int deleteByBaseDataRefDefine(DataMappingDefineDTO define) {
        StringBuilder sql = new StringBuilder(String.format(DELETE_SQL, DataRefUtil.getRefTableName((String)define.getCode())));
        sql.append(" AND DATASCHEMECODE = ? \n");
        return OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), new Object[]{define.getDataSchemeCode()});
    }

    @Override
    public String getDataSchemeCodeByUnitCode(String unitCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DATASCHEMECODE \n");
        sql.append("  FROM ").append(DataRefUtil.getRefTableName((String)"MD_ORG")).append(" T \n");
        sql.append(" WHERE T.CODE = ? \n");
        return (String)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (ResultSetExtractor)new StringResultSetExtractor(), new Object[]{unitCode});
    }

    @Override
    public void updateHandleStatusById(String tableName, String id, String handleStatus) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(DataRefUtil.getRefTableName((String)tableName));
        sql.append("   SET HANDLESTATUS = ? \n");
        sql.append(" WHERE ID = ? \n");
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), new Object[]{handleStatus, id});
    }

    @Override
    public List<String> selectConflictData(String tableName, String dataSchemeCode, IsolationParamContext IsolationParamContext2) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ODS_CODE FROM %1$s ");
        sql.append(" WHERE DATASCHEMECODE = ? ");
        sql.append("   AND HANDLESTATUS IN ( ?, ? )");
        ArrayList<Object> queryParam = new ArrayList<Object>();
        queryParam.add(dataSchemeCode);
        queryParam.add(RefHandleStatus.PENDING.getCode());
        queryParam.add(RefHandleStatus.DELETED.getCode());
        if (!StringUtils.isEmpty((String)IsolationParamContext2.getUnitCode())) {
            sql.append("   AND DC_UNITCODE = ? ");
            queryParam.add(IsolationParamContext2.getUnitCode());
        }
        if (!StringUtils.isEmpty((String)IsolationParamContext2.getBookCode())) {
            sql.append("   AND ODS_BOOKCODE = ? ");
            queryParam.add(IsolationParamContext2.getBookCode());
        }
        if (!ObjectUtils.isEmpty(IsolationParamContext2.getAcctYear())) {
            sql.append("   AND ODS_ACCTYEAR = ? ");
            queryParam.add(IsolationParamContext2.getAcctYear());
        }
        String executeSql = sql.toString();
        executeSql = String.format(executeSql, tableName);
        return OuterDataSourceUtils.getJdbcTemplate().query(executeSql, (RowMapper)new StringRowMapper(), queryParam.toArray());
    }

    @Override
    public List<DataRefDTO> selectPendingData(String tableName, String dataSchemeCode, IsolationParamContext IsolationParamContext2) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID, CODE, ODS_CODE, DATASCHEMECODE FROM %1$s ");
        sql.append(" WHERE DATASCHEMECODE = ? ");
        sql.append("   AND HANDLESTATUS = ? ");
        ArrayList<Object> queryParam = new ArrayList<Object>();
        queryParam.add(dataSchemeCode);
        queryParam.add(RefHandleStatus.PENDING.getCode());
        if (!StringUtils.isEmpty((String)IsolationParamContext2.getUnitCode())) {
            sql.append("   AND DC_UNITCODE = ? ");
            queryParam.add(IsolationParamContext2.getUnitCode());
        }
        if (!StringUtils.isEmpty((String)IsolationParamContext2.getBookCode())) {
            sql.append("   AND ODS_BOOKCODE = ? ");
            queryParam.add(IsolationParamContext2.getBookCode());
        }
        if (!ObjectUtils.isEmpty(IsolationParamContext2.getAcctYear())) {
            sql.append("   AND ODS_ACCTYEAR = ? ");
            queryParam.add(IsolationParamContext2.getAcctYear());
        }
        String executeSql = sql.toString();
        executeSql = String.format(executeSql, DataRefUtil.getRefTableName((String)tableName));
        return OuterDataSourceUtils.getJdbcTemplate().query(executeSql, (rs, rownum) -> {
            DataRefDTO dataRefDTO = new DataRefDTO();
            dataRefDTO.setId(rs.getString("ID"));
            dataRefDTO.setCode(rs.getString("CODE"));
            dataRefDTO.setOdsCode(rs.getString("ODS_CODE"));
            dataRefDTO.setDataSchemeCode(rs.getString("DATASCHEMECODE"));
            return dataRefDTO;
        }, queryParam.toArray());
    }

    @Override
    public void batchUpdateDcUnitCode(String tableName, String dataSchemeCode, List<String> oldUnitCode, List<String> newUnitCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(DataRefUtil.getRefTableName((String)tableName));
        sql.append("   SET DC_UNITCODE = ? \n");
        sql.append(" WHERE DATASCHEMECODE = ? \n");
        sql.append("   AND DC_UNITCODE = ? \n");
        ArrayList<String[]> params = new ArrayList<String[]>(oldUnitCode.size());
        for (int i = 0; i < oldUnitCode.size(); ++i) {
            params.add(new String[]{newUnitCode.get(i), dataSchemeCode, oldUnitCode.get(i)});
        }
        OuterDataSourceUtils.getJdbcTemplate().batchUpdate(sql.toString(), params);
    }

    @Override
    public void deleteByDcUnitCode(String tableName, String dataSchemeCode, List<String> dcUnitCodes) {
        if (CollectionUtils.isEmpty(dcUnitCodes)) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(DataRefUtil.getRefTableName((String)tableName));
        sql.append(" WHERE DATASCHEMECODE = ? \n");
        sql.append("   AND ");
        sql.append(SqlBuildUtil.getStrInCondi((String)"DC_UNITCODE", dcUnitCodes));
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), new Object[]{dataSchemeCode});
    }

    private String getPaginationSql(String sql, Boolean pagination, Integer pageNum, Integer pageSize) {
        if (!Boolean.TRUE.equals(pagination)) {
            return sql;
        }
        return OuterDataSourceUtils.getJdbcTemplate().getIDbSqlHandler().getPageSql(sql, pageNum.intValue(), pageSize.intValue());
    }

    private void builderIsolationSql(StringBuilder sql, BaseDataMappingDefineDTO define) {
        List fieldList = IsolationStrategy.getIsolationFieldByCode((String)define.getIsolationStrategy());
        if (!CollectionUtils.isEmpty((Collection)fieldList)) {
            for (String field : fieldList) {
                sql.append("   AND ODSTABLE.").append(field.replace("ODS_", "")).append(" = REFTABLE.").append(field).append(" ");
            }
        }
    }
}

