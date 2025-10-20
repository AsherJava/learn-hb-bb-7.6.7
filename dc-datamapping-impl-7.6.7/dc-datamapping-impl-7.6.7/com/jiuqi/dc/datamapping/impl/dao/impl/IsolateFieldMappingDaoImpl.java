/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefMappingCacheDTO
 *  com.jiuqi.dc.mappingscheme.client.common.DataRefUtil
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.datamapping.impl.dao.impl;

import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefMappingCacheDTO;
import com.jiuqi.dc.datamapping.impl.dao.IsolateFieldMappingDao;
import com.jiuqi.dc.mappingscheme.client.common.DataRefUtil;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class IsolateFieldMappingDaoImpl
extends BaseDataCenterDaoImpl
implements IsolateFieldMappingDao {
    @Override
    public List<DataRefMappingCacheDTO> loadShareMapping(String schemeCode, String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID, CODE, ODS_CODE, ODS_NAME");
        sql.append("  FROM ##TABLE##");
        sql.append(" WHERE DATASCHEMECODE = ?");
        sql.append("   AND HANDLESTATUS NOT IN (0, 4)");
        String executeSql = sql.toString().replace("##TABLE##", DataRefUtil.getRefTableName((String)tableName));
        return OuterDataSourceUtils.getJdbcTemplate().query(executeSql, (RowMapper)new BeanPropertyRowMapper(DataRefMappingCacheDTO.class), new Object[]{schemeCode});
    }

    @Override
    public List<DataRefDTO> loadIsolateAllMapping(IsolationStrategy isolationStrategy, String schemeCode, String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID, CODE, ODS_CODE, ##SELECTSQL## ODS_NAME");
        sql.append("  FROM ##TABLE##");
        sql.append(" WHERE DATASCHEMECODE = ?");
        sql.append("   AND HANDLESTATUS NOT IN (0, 4)");
        String executeSql = sql.toString();
        List fieldList = isolationStrategy.getFieldList();
        StringBuilder selectSql = new StringBuilder();
        if (fieldList.size() > 0) {
            for (String field : fieldList) {
                selectSql.append(field + ",");
            }
        }
        executeSql = executeSql.replace("##SELECTSQL##", selectSql.toString());
        executeSql = executeSql.replace("##TABLE##", DataRefUtil.getRefTableName((String)tableName));
        return OuterDataSourceUtils.getJdbcTemplate().query(executeSql, (rs, rowNum) -> {
            DataRefDTO data = new DataRefDTO();
            data.setId(rs.getString("ID"));
            data.setOdsCode(rs.getString("ODS_CODE"));
            data.setOdsName(rs.getString("ODS_NAME"));
            data.setCode(rs.getString("CODE"));
            if (fieldList.size() > 0) {
                for (String field : fieldList) {
                    data.put((Object)field, (Object)rs.getString(field));
                }
            }
            return data;
        }, new Object[]{schemeCode});
    }

    @Override
    public Map<String, String> getOdsUnit(String schemeCode, Set<Object> unitCodes) {
        if (ObjectUtils.isEmpty(unitCodes)) {
            return new HashMap<String, String>();
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CODE, PARENTCODE");
        sql.append("  FROM ODS_MD_ORG");
        sql.append(" WHERE DATASCHEMECODE = ?");
        sql.append("   AND (##UNITCODES##)");
        sql.append(" ORDER BY PARENTS");
        LinkedHashMap<String, String> odsMaps = new LinkedHashMap<String, String>();
        List inCondis = unitCodes.stream().map(obj -> String.valueOf(obj)).collect(Collectors.toList());
        String executeSql = sql.toString();
        executeSql = executeSql.replace("##UNITCODES##", SqlBuildUtil.getStrInCondi((String)"CODE", inCondis));
        OuterDataSourceUtils.getJdbcTemplate().query(executeSql, (rs, rowNum) -> {
            odsMaps.put(rs.getString("CODE"), rs.getString("PARENTCODE"));
            return null;
        }, new Object[]{schemeCode});
        return odsMaps;
    }

    @Override
    public Map<String, String> getOdsUnitByScheme(String schemeCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CODE, PARENTCODE");
        sql.append("  FROM ODS_MD_ORG");
        sql.append(" WHERE DATASCHEMECODE = ?");
        sql.append(" ORDER BY PARENTS");
        LinkedHashMap<String, String> odsMaps = new LinkedHashMap<String, String>();
        String executeSql = sql.toString();
        OuterDataSourceUtils.getJdbcTemplate().query(executeSql, (rs, rowNum) -> {
            odsMaps.put(rs.getString("CODE"), rs.getString("PARENTCODE"));
            return null;
        }, new Object[]{schemeCode});
        return odsMaps;
    }

    @Override
    public List<DataRefMappingCacheDTO> loadOrgMapping(String schemeCode, String assistCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID, CODE, ODS_CODE, ODS_NAME");
        sql.append("  FROM REF_MD_ORG");
        sql.append(" WHERE DATASCHEMECODE = ?");
        sql.append("   AND ODS_ASSISTCODE = ?");
        sql.append("   AND HANDLESTATUS NOT IN (0, 4)");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new BeanPropertyRowMapper(DataRefMappingCacheDTO.class), new Object[]{schemeCode, assistCode});
    }

    @Override
    public List<DataRefMappingCacheDTO> loadAllOrgMapping(String schemeCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID, CODE, ODS_CODE, ODS_NAME, ODS_ASSISTCODE");
        sql.append("  FROM REF_MD_ORG");
        sql.append(" WHERE DATASCHEMECODE = ?");
        sql.append("   AND HANDLESTATUS NOT IN (0, 4)");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new BeanPropertyRowMapper(DataRefMappingCacheDTO.class), new Object[]{schemeCode});
    }
}

