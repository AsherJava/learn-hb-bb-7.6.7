/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.mappingscheme.impl.dao.impl;

import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper;
import com.jiuqi.dc.mappingscheme.impl.dao.BaseDataMappingDefineDao;
import com.jiuqi.dc.mappingscheme.impl.domain.BaseDataMappingDefineDO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class BaseDataMappingDefineDaoImpl
implements BaseDataMappingDefineDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String SELECT_SQL = "SELECT        ID,       VER,       DATASCHEMECODE,       MODELTYPE,       CODE,       NAME,       ADVANCEDSQL,       RULETYPE,       AUTOMATCHDIM,\t\tCREATETIME FROM DC_REF_DATAMAPINGDFINE ";
    private static final String SELECT_BY_MODELTYPE_SQL = "SELECT        ID,       VER,       DATASCHEMECODE,       MODELTYPE,       CODE,       NAME,       ADVANCEDSQL,       RULETYPE,       AUTOMATCHDIM,\t\tCREATETIME FROM DC_REF_DATAMAPINGDFINE  WHERE MODELTYPE = ? ";
    private static final String INSERT_SQL = "INSERT INTO DC_REF_DATAMAPINGDFINE    (       ID,       VER,       DATASCHEMECODE,       MODELTYPE,       CODE,       NAME,       ADVANCEDSQL,       RULETYPE,       AUTOMATCHDIM,\t    CREATETIME)VALUES(       ?,       ?,       ?,       ?,       ?,       ?,       ?,       ?,       ?,       ?   )";
    private static final String UPDATE_SQL = "UPDATE DC_REF_DATAMAPINGDFINE SET VER = VER + 1,       NAME = ?,       ADVANCEDSQL = ?,       RULETYPE = ?,       AUTOMATCHDIM = ? WHERE ID = ?   AND VER = ?";
    private static final String DELETE_SQL = "DELETE FROM DC_REF_DATAMAPINGDFINE WHERE 1 = 1   AND ID = ?   AND VER = ?";

    @Override
    public int insert(BaseDataMappingDefineDO defineDO) {
        return this.jdbcTemplate.update(INSERT_SQL, this.convertInsertParam(defineDO));
    }

    private Object[] convertInsertParam(BaseDataMappingDefineDO defineDO) {
        return new Object[]{defineDO.getId(), defineDO.getVer(), defineDO.getDataSchemeCode(), defineDO.getModelType(), defineDO.getCode(), defineDO.getName(), defineDO.getAdvancedSql(), defineDO.getRuleType(), defineDO.getAutoMatchDim(), defineDO.getCreateTime()};
    }

    @Override
    public int update(BaseDataMappingDefineDO defineDO) {
        return this.jdbcTemplate.update(UPDATE_SQL, this.convertUpdateParam(defineDO));
    }

    private Object[] convertUpdateParam(BaseDataMappingDefineDO defineDO) {
        return new Object[]{defineDO.getName(), defineDO.getAdvancedSql(), defineDO.getRuleType(), defineDO.getAutoMatchDim(), defineDO.getId(), defineDO.getVer()};
    }

    @Override
    public int delete(BaseDataMappingDefineDO defineDO) {
        return this.jdbcTemplate.update(DELETE_SQL, new Object[]{defineDO.getId(), defineDO.getVer()});
    }

    @Override
    public List<BaseDataMappingDefineDO> selectAll() {
        return this.jdbcTemplate.query(SELECT_SQL, (RowMapper)new BeanPropertyRowMapper(BaseDataMappingDefineDO.class), new Object[0]);
    }

    @Override
    public List<BaseDataMappingDefineDO> selectByModelType(String modelType) {
        return this.jdbcTemplate.query(SELECT_BY_MODELTYPE_SQL, (RowMapper)new BeanPropertyRowMapper(BaseDataMappingDefineDO.class), new Object[]{modelType});
    }

    @Override
    public List<String> getTableColumn(String tableName) {
        GcBizJdbcTemplate bizJdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
        String sql = bizJdbcTemplate.getIDbSqlHandler().getTableColumnSql(tableName);
        return bizJdbcTemplate.query(sql, (RowMapper)new StringRowMapper());
    }
}

