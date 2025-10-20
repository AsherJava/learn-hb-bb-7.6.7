/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.mappingscheme.impl.dao.impl;

import com.jiuqi.dc.mappingscheme.impl.dao.DataSchemeDao;
import com.jiuqi.dc.mappingscheme.impl.domain.DataSchemeDO;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class DataSchemeDaoImpl
implements DataSchemeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String SELECT_SQL = "SELECT ID, VER, CODE, NAME, PLUGINTYPE, DATASOURCECODE, CUSTOMCONFIG, STOPFLAG, SOURCEDATATYPE, ETLJOBID, DATAMAPPING  FROM DC_SCHEME_DATAMAPPING";
    private static final String INSERT_SQL = "INSERT INTO DC_SCHEME_DATAMAPPING   (ID, VER, CODE, NAME, PLUGINTYPE, DATASOURCECODE, CUSTOMCONFIG, STOPFLAG, CREATETIME, SOURCEDATATYPE, ETLJOBID, DATAMAPPING) VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    private static final String UPDATE_SQL = "UPDATE DC_SCHEME_DATAMAPPING SET VER = VER + 1, NAME = ?, PLUGINTYPE = ? , DATASOURCECODE = ? , CUSTOMCONFIG = ? , SOURCEDATATYPE = ?, ETLJOBID = ?, DATAMAPPING = ? WHERE ID = ?   AND VER = ? ";
    private static final String UPDATE_STOPFLAG_SQL = "UPDATE DC_SCHEME_DATAMAPPING    SET VER = VER + 1, STOPFLAG = ?  WHERE ID = ?   AND VER = ? ";
    private static final String DELETE_SQL = "DELETE FROM DC_SCHEME_DATAMAPPING WHERE 1 = 1   AND ID = ?   AND VER = ?";
    private static final String EXIST_QUOTE_SQL = "SELECT COUNT(1) FROM DC_REF_FIELDMAPINGDFINE  WHERE 1 = 1   AND DATASCHEMECODE = ? ";

    @Override
    public List<DataSchemeDO> selectAll() {
        return this.jdbcTemplate.query(SELECT_SQL, (RowMapper)new BeanPropertyRowMapper(DataSchemeDO.class), new Object[0]);
    }

    @Override
    public int insert(DataSchemeDO dataScheme) {
        return this.jdbcTemplate.update(INSERT_SQL, this.convertInsertParam(dataScheme));
    }

    private Object[] convertInsertParam(DataSchemeDO dataScheme) {
        return new Object[]{dataScheme.getId(), dataScheme.getVer(), dataScheme.getCode(), dataScheme.getName(), dataScheme.getPluginType(), dataScheme.getDataSourceCode(), dataScheme.getCustomConfig(), dataScheme.getStopFlag(), new Date(), dataScheme.getSourceDataType(), dataScheme.getEtlJobId(), dataScheme.getDataMapping()};
    }

    @Override
    public int update(DataSchemeDO dataScheme) {
        return this.jdbcTemplate.update(UPDATE_SQL, this.convertUpdateParam(dataScheme));
    }

    @Override
    public int delete(DataSchemeDO dataScheme) {
        return this.jdbcTemplate.update(DELETE_SQL, new Object[]{dataScheme.getId(), dataScheme.getVer()});
    }

    @Override
    public int stop(DataSchemeDO dataScheme) {
        return this.jdbcTemplate.update(UPDATE_STOPFLAG_SQL, new Object[]{dataScheme.getStopFlag(), dataScheme.getId(), dataScheme.getVer()});
    }

    private Object[] convertUpdateParam(DataSchemeDO dataScheme) {
        return new Object[]{dataScheme.getName(), dataScheme.getPluginType(), dataScheme.getDataSourceCode(), dataScheme.getCustomConfig(), dataScheme.getSourceDataType(), dataScheme.getEtlJobId(), dataScheme.getDataMapping(), dataScheme.getId(), dataScheme.getVer()};
    }

    @Override
    public int existQuote(String code) {
        return (Integer)this.jdbcTemplate.queryForObject(EXIST_QUOTE_SQL, Integer.class, new Object[]{code});
    }
}

