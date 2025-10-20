/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.base.common.jdbc.extractor.StringResultSetExtractor
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.mappingscheme.impl.dao.impl;

import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.common.jdbc.extractor.StringResultSetExtractor;
import com.jiuqi.dc.mappingscheme.impl.dao.DataSchemeOptionDao;
import com.jiuqi.dc.mappingscheme.impl.domain.DataSchemeOptionDO;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class DataSchemeOptionDaoImpl
extends BaseDataCenterDaoImpl
implements DataSchemeOptionDao {
    @Override
    public List<DataSchemeOptionDO> queryByDataSchemeCode(String dataSchemeCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.ID, T.DATASCHEMECODE, T.CODE, T.OPTIONVALUE \n");
        sql.append("  FROM ").append("DC_SCHEME_DATAMAPPINGOPTION").append(" T \n");
        sql.append(" WHERE T.DATASCHEMECODE = ? \n");
        return this.getJdbcTemplate().query(sql.toString(), (RowMapper)new BeanPropertyRowMapper(DataSchemeOptionDO.class), new Object[]{dataSchemeCode});
    }

    @Override
    public int insert(DataSchemeOptionDO option) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append("DC_SCHEME_DATAMAPPINGOPTION").append(" \n");
        sql.append(" (ID, DATASCHEMECODE, CODE, OPTIONVALUE) \n");
        sql.append("VALUES (? ,?, ?, ?) \n");
        return this.getJdbcTemplate().update(sql.toString(), new Object[]{option.getId(), option.getDataSchemeCode(), option.getCode(), option.getOptionValue()});
    }

    @Override
    public int update(DataSchemeOptionDO option) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("DC_SCHEME_DATAMAPPINGOPTION").append(" T \n");
        sql.append("   SET OPTIONVALUE = ? \n");
        sql.append(" WHERE T.ID = ? \n");
        return this.getJdbcTemplate().update(sql.toString(), new Object[]{option.getOptionValue(), option.getId()});
    }

    @Override
    public int deleteByDataSchemeCode(String dataSchemeCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append("DC_SCHEME_DATAMAPPINGOPTION").append(" \n");
        sql.append(" WHERE DATASCHEMECODE = ? \n");
        return this.getJdbcTemplate().update(sql.toString(), new Object[]{dataSchemeCode});
    }

    @Override
    public String queryOptionValue(String dataSchemeCode, String optionCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.OPTIONVALUE \n");
        sql.append("  FROM ").append("DC_SCHEME_DATAMAPPINGOPTION").append(" T \n");
        sql.append(" WHERE T.DATASCHEMECODE = ? \n");
        sql.append("   AND T.CODE = ? \n");
        return (String)this.getJdbcTemplate().query(sql.toString(), (ResultSetExtractor)new StringResultSetExtractor(), new Object[]{dataSchemeCode, optionCode});
    }
}

