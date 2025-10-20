/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.mappingscheme.impl.dao.impl;

import com.jiuqi.dc.mappingscheme.impl.dao.DataMappingDefineDao;
import com.jiuqi.dc.mappingscheme.impl.domain.DataMappingDefineDO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class DataMappingDefineDaoImpl
implements DataMappingDefineDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String SELECT_SQL = "SELECT        ID,       VER,       DATASCHEMECODE,       MODELTYPE,       CODE,       NAME,\t\tCREATETIME FROM DC_REF_DATAMAPINGDFINE ";
    private static final String SELECT_BY_MODELTYPE_SQL = "SELECT        ID,       VER,       DATASCHEMECODE,       MODELTYPE,       CODE,       NAME,\t\tCREATETIME FROM DC_REF_DATAMAPINGDFINE  WHERE MODELTYPE = ? ";
    private static final String INSERT_SQL = "INSERT INTO DC_REF_DATAMAPINGDFINE    (       ID,       VER,       DATASCHEMECODE,       MODELTYPE,       CODE,       NAME,\t    CREATETIME)VALUES(       ?,       ?,       ?,       ?,       ?,       ?,       ?   )";
    private static final String UPDATE_SQL = "UPDATE DC_REF_DATAMAPINGDFINE SET VER = VER + 1,       NAME = ?, WHERE ID = ?   AND VER = ?";
    private static final String DELETE_SQL = "DELETE FROM DC_REF_DATAMAPINGDFINE WHERE 1 = 1   AND ID = ?   AND VER = ?";

    @Override
    public int insert(DataMappingDefineDO defineDO) {
        return this.jdbcTemplate.update(INSERT_SQL, this.convertInsertParam(defineDO));
    }

    private Object[] convertInsertParam(DataMappingDefineDO defineDO) {
        return new Object[]{defineDO.getId(), defineDO.getVer(), defineDO.getDataSchemeCode(), defineDO.getModelType(), defineDO.getCode(), defineDO.getName(), defineDO.getCreateTime()};
    }

    @Override
    public int update(DataMappingDefineDO defineDO) {
        return this.jdbcTemplate.update(UPDATE_SQL, this.convertUpdateParam(defineDO));
    }

    private Object[] convertUpdateParam(DataMappingDefineDO defineDO) {
        return new Object[]{defineDO.getName(), defineDO.getId(), defineDO.getVer()};
    }

    @Override
    public int delete(DataMappingDefineDO defineDO) {
        return this.jdbcTemplate.update(DELETE_SQL, new Object[]{defineDO.getId(), defineDO.getVer()});
    }

    @Override
    public List<DataMappingDefineDO> selectAll() {
        return this.jdbcTemplate.query(SELECT_SQL, (RowMapper)new BeanPropertyRowMapper(DataMappingDefineDO.class), new Object[0]);
    }

    @Override
    public List<DataMappingDefineDO> selectByModelType(String modelType) {
        return this.jdbcTemplate.query(SELECT_BY_MODELTYPE_SQL, (RowMapper)new BeanPropertyRowMapper(DataMappingDefineDO.class), new Object[]{modelType});
    }
}

