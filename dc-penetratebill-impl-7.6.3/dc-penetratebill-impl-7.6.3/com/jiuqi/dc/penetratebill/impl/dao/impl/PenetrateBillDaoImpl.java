/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.penetratebill.impl.dao.impl;

import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.penetratebill.impl.dao.PenetrateBillDao;
import com.jiuqi.dc.penetratebill.impl.entity.PenetrateSchemeEO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PenetrateBillDaoImpl
extends BaseDataCenterDaoImpl
implements PenetrateBillDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String SELECT_SQL = "SELECT ID, VER, SCHEMENAME, SCOPETYPE, SCOPEVALUE, HANDLERCODE, CUSTOMPARAM, CREATEDATE, BILLNOFIELD, OPENWAY   FROM DC_SCHEME_PENETRATE_BILL";
    private static final String INSERT_SQL = "INSERT INTO DC_SCHEME_PENETRATE_BILL   (ID, VER, SCHEMENAME, SCOPETYPE, SCOPEVALUE, HANDLERCODE, CUSTOMPARAM, CREATEDATE, BILLNOFIELD, OPENWAY) VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE DC_SCHEME_PENETRATE_BILL SET VER = VER + 1, SCHEMENAME = ?, SCOPETYPE = ? , SCOPEVALUE = ? , HANDLERCODE = ? , CUSTOMPARAM = ?, BILLNOFIELD = ?, OPENWAY = ? WHERE ID = ?   AND VER = ? ";
    private static final String DELETE_SQL = "DELETE FROM DC_SCHEME_PENETRATE_BILL WHERE 1 = 1   AND ID = ?   AND VER = ?";

    @Override
    public List<PenetrateSchemeEO> selectAll() {
        return this.jdbcTemplate.query(SELECT_SQL, (RowMapper)new BeanPropertyRowMapper(PenetrateSchemeEO.class));
    }

    @Override
    public int insert(PenetrateSchemeEO schemeEO) {
        return this.jdbcTemplate.update(INSERT_SQL, this.convertInsertParam(schemeEO));
    }

    private Object[] convertInsertParam(PenetrateSchemeEO schemeEO) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date createDate = null;
        try {
            createDate = dateFormat.parse(schemeEO.getCreateDate());
        }
        catch (ParseException e) {
            throw new IllegalArgumentException("\u5355\u636e\u63d2\u5165\u65f6,\u5355\u636e\u521b\u5efa\u65e5\u671f\u8f6c\u6362\u5931\u8d25");
        }
        return new Object[]{schemeEO.getId(), schemeEO.getVer(), schemeEO.getSchemeName(), schemeEO.getScopeType(), schemeEO.getScopeValue(), schemeEO.getHandlerCode(), schemeEO.getCustomParam(), createDate, schemeEO.getBillNoField(), schemeEO.getOpenWay()};
    }

    @Override
    public int update(PenetrateSchemeEO schemeEO) {
        return this.jdbcTemplate.update(UPDATE_SQL, this.convertUpdateParam(schemeEO));
    }

    private Object[] convertUpdateParam(PenetrateSchemeEO schemeEO) {
        return new Object[]{schemeEO.getSchemeName(), schemeEO.getScopeType(), schemeEO.getScopeValue(), schemeEO.getHandlerCode(), schemeEO.getCustomParam(), schemeEO.getBillNoField(), schemeEO.getOpenWay(), schemeEO.getId(), schemeEO.getVer()};
    }

    @Override
    public int delete(PenetrateSchemeEO schemeEO) {
        return this.jdbcTemplate.update(DELETE_SQL, new Object[]{schemeEO.getId(), schemeEO.getVer()});
    }
}

