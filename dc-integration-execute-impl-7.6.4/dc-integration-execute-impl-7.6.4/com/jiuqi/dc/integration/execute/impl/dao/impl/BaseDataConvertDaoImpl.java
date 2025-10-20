/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.base.common.jdbc.extractor.IntegerResultSetExtractor
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.vo.OrgMappingVO
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 *  com.jiuqi.dc.mappingscheme.impl.enums.StorageType
 *  com.jiuqi.va.query.datasource.service.DynamicDataSourceService
 *  org.springframework.jdbc.core.ColumnMapRowMapper
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.integration.execute.impl.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.common.jdbc.extractor.IntegerResultSetExtractor;
import com.jiuqi.dc.integration.execute.impl.dao.BaseDataConvertDao;
import com.jiuqi.dc.integration.execute.impl.dao.impl.LowerColumnMapRowMapper;
import com.jiuqi.dc.integration.execute.impl.utils.BaseDataConvertUtil;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.vo.OrgMappingVO;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import com.jiuqi.dc.mappingscheme.impl.enums.StorageType;
import com.jiuqi.va.query.datasource.service.DynamicDataSourceService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

@Repository
public class BaseDataConvertDaoImpl
extends BaseDataCenterDaoImpl
implements BaseDataConvertDao {
    @Autowired
    private DynamicDataSourceService dynamicDataSourceService;

    @Override
    public int countUnConvertData(BaseDataMappingDefineDTO define, String dataSourceCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(1)  ");
        sql.append("  FROM (  ");
        sql.append(define.getAdvancedSql());
        sql.append(" ) ODSTABLE ");
        JdbcTemplate jdbcTemplate = !StringUtils.isEmpty((String)dataSourceCode) ? this.dynamicDataSourceService.getJdbcTemplate(dataSourceCode) : this.getJdbcTemplate();
        return (Integer)jdbcTemplate.query(sql.toString(), (ResultSetExtractor)new IntegerResultSetExtractor());
    }

    @Override
    public List<Map<String, Object>> selectUnConvertData(BaseDataMappingDefineDTO define, String dataSourceCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        for (FieldMappingDefineDTO item : define.getItems()) {
            if (item.getFieldName().equals("ID")) {
                sql.append(" ODSTABLE.ID AS SRCID,");
                continue;
            }
            if (item.getFieldName().equals("CODE")) {
                sql.append(" ODSTABLE.CODE AS CODE,");
                continue;
            }
            sql.append(String.format(" ODSTABLE.%1$s AS %2$s,", item.getOdsFieldName(), item.getFieldName()));
        }
        sql.delete(sql.length() - 1, sql.length());
        sql.append("  FROM (  ");
        sql.append(define.getAdvancedSql());
        sql.append(" ) ODSTABLE  ");
        JdbcTemplate jdbcTemplate = !StringUtils.isEmpty((String)dataSourceCode) ? this.dynamicDataSourceService.getJdbcTemplate(dataSourceCode) : this.getJdbcTemplate();
        return jdbcTemplate.query(sql.toString(), (RowMapper)new LowerColumnMapRowMapper(), new Object[0]);
    }

    @Override
    public Map<String, Map<String, Object>> selectConvertDataByIsolate(DataSchemeDTO dataScheme, BaseDataMappingDefineDTO define, Map<String, Object> isolateCode, String defaultStorageType) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        HashSet<String> codes = new HashSet<String>();
        for (Object item : define.getItems()) {
            if (item.getFieldName().equals("ID")) {
                sql.append(" ODSTABLE.ID AS SRCID,");
                continue;
            }
            if (item.getFieldName().equals("CODE")) {
                sql.append(" ODSTABLE.CODE AS CODE,");
                continue;
            }
            codes.add(item.getOdsFieldName());
            if (StorageType.ID.getCode().equals(defaultStorageType) && "UNITCODE".equals(item.getFieldName())) {
                sql.append(String.format(" %1$s.CODE AS %2$s,", item.getOdsFieldName(), item.getFieldName()));
                continue;
            }
            sql.append(String.format(" ODSTABLE.%1$s AS %2$s,", item.getOdsFieldName(), item.getFieldName()));
        }
        List isolationFieldByCode = IsolationStrategy.getIsolationFieldByCode((String)define.getIsolationStrategy());
        if (!CollectionUtils.isEmpty((Collection)isolationFieldByCode)) {
            for (String isolationFile : isolationFieldByCode) {
                String isolationCode = BaseDataConvertUtil.getCodeWithOutODS(isolationFile);
                if (codes.contains(isolationCode)) continue;
                if (StorageType.ID.getCode().equals(defaultStorageType) && "UNITCODE".equals(isolationCode)) {
                    sql.append(String.format(" %1$s.CODE AS %2$s,", isolationCode, isolationCode));
                    continue;
                }
                sql.append(String.format(" ODSTABLE.%1$s AS %2$s,", isolationCode, isolationCode));
            }
        }
        sql.delete(sql.length() - 1, sql.length());
        sql.append("  FROM (  ");
        sql.append(define.getAdvancedSql());
        sql.append(" ) ODSTABLE  ");
        sql.append(" ##JOINSQL##");
        String joinSql = "";
        ArrayList<String> params = new ArrayList<String>();
        if (!ObjectUtils.isEmpty(isolateCode)) {
            sql.append(String.format(" WHERE 1 = 1 ", new Object[0]));
            for (Map.Entry entry : isolateCode.entrySet()) {
                String key = (String)entry.getKey();
                Object value = entry.getValue();
                if (StorageType.ID.getCode().equals(defaultStorageType) && "UNITCODE".equals(key)) {
                    sql.append(String.format(" AND %1$s.CODE = ?", key));
                    params.add(value.toString());
                    StringBuilder joinSqlBuilder = new StringBuilder();
                    joinSqlBuilder.append(" JOIN (");
                    joinSqlBuilder.append(dataScheme.getDataMapping().getOrgMapping().getAdvancedSql());
                    joinSqlBuilder.append("    ) %1$s ON ODSTABLE.%2$s = %3$s.ID");
                    joinSql = String.format(joinSqlBuilder.toString(), key, key, key);
                    continue;
                }
                sql.append(String.format(" AND %1$s = ?", key));
                params.add(value.toString());
            }
        }
        String querySql = sql.toString();
        querySql = querySql.replace("##JOINSQL##", joinSql);
        JdbcTemplate jdbcTemplate = this.dynamicDataSourceService.getJdbcTemplate(dataScheme.getDataSourceCode());
        HashMap<String, Map<String, Object>> results = new HashMap<String, Map<String, Object>>();
        jdbcTemplate.query(querySql, (rs, rowNum) -> {
            int resultSize = rs.getMetaData().getColumnCount();
            HashMap<String, Object> row = new HashMap<String, Object>();
            for (int i = 1; i <= resultSize; ++i) {
                row.put(rs.getMetaData().getColumnLabel(i).toLowerCase(), rs.getObject(i));
            }
            String code = (String)row.get("code");
            results.put(code, row);
            return row;
        }, params.toArray());
        return results;
    }

    @Override
    public List<Map<String, Object>> selectIsolationCodes(DataSchemeDTO dataScheme, BaseDataMappingDefineDTO define, String defaultStorageType) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ##SELECTSQL##");
        sql.append("  FROM (  ");
        sql.append(define.getAdvancedSql());
        sql.append(" ) ODSTABLE  ");
        sql.append(" ##JOINSQL##");
        sql.append(" GROUP BY ##GROUPSQL##");
        List isolationFieldByCodes = IsolationStrategy.getIsolationFieldByCode((String)define.getIsolationStrategy());
        StringJoiner selectSql = new StringJoiner(",");
        StringJoiner groupSql = new StringJoiner(",");
        String joinSql = "";
        if (!CollectionUtils.isEmpty((Collection)isolationFieldByCodes)) {
            for (String isolationFieldByCode : isolationFieldByCodes) {
                String codeWithOutODS = BaseDataConvertUtil.getCodeWithOutODS(isolationFieldByCode);
                if (StorageType.ID.getCode().equals(defaultStorageType) && "UNITCODE".equals(codeWithOutODS)) {
                    selectSql.add(String.format(" %1$s.CODE AS %2$s", codeWithOutODS, codeWithOutODS));
                    groupSql.add(String.format("  %1$s.CODE ", codeWithOutODS));
                    StringBuilder joinSqlBuilder = new StringBuilder();
                    joinSqlBuilder.append(" JOIN (");
                    joinSqlBuilder.append(dataScheme.getDataMapping().getOrgMapping().getAdvancedSql());
                    joinSqlBuilder.append("    ) %1$s ON ODSTABLE.%2$s = %3$s.ID");
                    joinSql = String.format(joinSqlBuilder.toString(), codeWithOutODS, codeWithOutODS, codeWithOutODS);
                    continue;
                }
                selectSql.add(String.format(" ODSTABLE.%1$s AS %2$s", codeWithOutODS, codeWithOutODS));
                groupSql.add(String.format("  ODSTABLE.%1$s ", codeWithOutODS));
            }
        }
        String querySql = sql.toString();
        querySql = querySql.replace("##SELECTSQL##", selectSql.toString());
        querySql = querySql.replace("##GROUPSQL##", groupSql.toString());
        querySql = querySql.replace("##JOINSQL##", joinSql);
        JdbcTemplate jdbcTemplate = this.dynamicDataSourceService.getJdbcTemplate(dataScheme.getDataSourceCode());
        return jdbcTemplate.query(querySql, (RowMapper)new ColumnMapRowMapper(), new Object[0]);
    }

    @Override
    public List<Map<String, Object>> selectOrgData(DataSchemeDTO dataScheme, BaseDataMappingDefineDTO define) {
        OrgMappingVO orgMapping = dataScheme.getDataMapping().getOrgMapping();
        JdbcTemplate jdbcTemplate = this.dynamicDataSourceService.getJdbcTemplate(dataScheme.getDataSourceCode());
        return jdbcTemplate.query(orgMapping.getAdvancedSql(), (RowMapper)new ColumnMapRowMapper(), new Object[0]);
    }
}

