/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.mappingscheme.impl.dao.impl;

import com.jiuqi.dc.mappingscheme.impl.dao.FieldMappingDefineDao;
import com.jiuqi.dc.mappingscheme.impl.domain.FieldMappingDefineDO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
@Primary
public class FieldMappingDefineDaoImpl
implements FieldMappingDefineDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String SELECT_SQL = "SELECT       ITEM.ID,       ITEM.DATAMAPPINGID,       ITEM.TABLENAME,       ITEM.DATASCHEMECODE,       ITEM.FIELDNAME,       ITEM.FIELDTITLE,       ITEM.FIELDMAPPINGTYPE,       ITEM.ODS_FIELDNAME AS ODSFIELDNAME,       ITEM.RULETYPE,       ITEM.FIXEDFLAG,       ITEM.ORDINAL  FROM DC_REF_FIELDMAPINGDFINE ITEM ORDER BY ITEM.DATAMAPPINGID, ITEM.ORDINAL";
    private static final String SELECT_BY_DATATYPE_SQL = "SELECT       ITEM.ID,       ITEM.DATAMAPPINGID,       ITEM.TABLENAME,       ITEM.DATASCHEMECODE,       ITEM.FIELDNAME,       ITEM.FIELDTITLE,       ITEM.ODS_FIELDNAME AS ODSFIELDNAME,       ITEM.RULETYPE,       ITEM.FIXEDFLAG,       ITEM.ORDINAL  FROM DC_REF_FIELDMAPINGDFINE ITEM   JOIN DC_REF_DATAMAPINGDFINE DEFINE ON DEFINE.ID = ITEM.DATAMAPPINGID WHERE DEFINE.DATATYPE = ? ORDER BY ITEM.DATAMAPPINGID, ITEM.ORDINAL";
    private static final String INSERT_SQL = "INSERT INTO DC_REF_FIELDMAPINGDFINE    (       ID,       DATAMAPPINGID,       TABLENAME,       DATASCHEMECODE,       FIELDNAME,       FIELDTITLE,       FIELDMAPPINGTYPE,       ODS_FIELDNAME,       RULETYPE,       FIXEDFLAG,       ORDINAL)VALUES(       ?,       ?,       ?,       ?,       ?,       ?,       ?,       ?,       ?,       ?,       ?   )";
    private static final String DELETE_SQL = "DELETE FROM DC_REF_FIELDMAPINGDFINE WHERE 1 = 1   AND ID = ?";
    private static final String DELETE_BY_DEFINE_SQL = "DELETE FROM DC_REF_FIELDMAPINGDFINE WHERE 1 = 1   AND DATAMAPPINGID = ?";

    @Override
    public int insert(FieldMappingDefineDO defineItemDO) {
        return this.jdbcTemplate.update(INSERT_SQL, this.convertInsertParam(defineItemDO));
    }

    @Override
    public void batchInsert(List<FieldMappingDefineDO> itemList) {
        if (CollectionUtils.isEmpty(itemList)) {
            return;
        }
        ArrayList<Object[]> params = new ArrayList<Object[]>(itemList.size());
        for (FieldMappingDefineDO item : itemList) {
            params.add(this.convertInsertParam(item));
        }
        this.jdbcTemplate.batchUpdate(INSERT_SQL, params);
    }

    private Object[] convertInsertParam(FieldMappingDefineDO defineItemDO) {
        return new Object[]{defineItemDO.getId(), defineItemDO.getDataMappingId(), defineItemDO.getTableName(), defineItemDO.getDataSchemeCode(), defineItemDO.getFieldName(), defineItemDO.getFieldTitle(), defineItemDO.getFieldMappingType(), defineItemDO.getOdsFieldName(), defineItemDO.getRuleType(), defineItemDO.getFixedFlag(), defineItemDO.getOrdinal()};
    }

    @Override
    public int delete(FieldMappingDefineDO defineItemDO) {
        return this.jdbcTemplate.update(DELETE_SQL, new Object[]{defineItemDO.getId()});
    }

    @Override
    public int deleteByDataMappingId(String dataMappingId) {
        return this.jdbcTemplate.update(DELETE_BY_DEFINE_SQL, new Object[]{dataMappingId});
    }

    @Override
    public List<FieldMappingDefineDO> selectAll() {
        return this.jdbcTemplate.query(SELECT_SQL, (RowMapper)new BeanPropertyRowMapper(FieldMappingDefineDO.class), new Object[0]);
    }

    @Override
    public List<FieldMappingDefineDO> selectByModelType(String modelType) {
        return this.jdbcTemplate.query(SELECT_BY_DATATYPE_SQL, (RowMapper)new BeanPropertyRowMapper(FieldMappingDefineDO.class), new Object[]{modelType});
    }
}

