/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.dao.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.bill.setting.impl.dao.BillFetchCondiDao;
import com.jiuqi.gcreport.bde.bill.setting.impl.entity.BillFetchCondiEO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BillFetchCondiDaoImpl
implements BillFetchCondiDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String FILED_STRING = "ID,FETCHSCHEMEID,FETCHCONDITYPE,FETCHCONDICODE,FETCHCONDIVALUE";

    @Override
    public List<BillFetchCondiEO> queryBillFetchCondiEOByFetchSchemeId(String fetchSchemeId) {
        Assert.isNotEmpty((String)fetchSchemeId);
        String FETCH_CONDI_EO_BY_FETCH_SCHEME_ID_SQL = "SELECT ID,FETCHSCHEMEID,FETCHCONDITYPE,FETCHCONDICODE,FETCHCONDIVALUE FROM BDE_BILL_FETCH_CONDI WHERE fetchSchemeId = ?";
        return this.jdbcTemplate.query("SELECT ID,FETCHSCHEMEID,FETCHCONDITYPE,FETCHCONDICODE,FETCHCONDIVALUE FROM BDE_BILL_FETCH_CONDI WHERE fetchSchemeId = ?", (RowMapper)new BeanPropertyRowMapper(BillFetchCondiEO.class), new Object[]{fetchSchemeId});
    }

    @Override
    public void saveBillFetchCondi(BillFetchCondiEO findCondiEO) {
        if (StringUtils.isEmpty((String)findCondiEO.getFetchSchemeId())) {
            throw new RuntimeException("\u53d6\u6570\u6761\u4ef6\u4fdd\u5b58\u5931\u8d25\uff0c\u53d6\u6570\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)findCondiEO.getId())) {
            findCondiEO.setId(UUIDUtils.newHalfGUIDStr());
        }
        String sql = "  INSERT INTO  BDE_BILL_FETCH_CONDI \n ( ID,FETCHSCHEMEID,FETCHCONDITYPE,FETCHCONDICODE,FETCHCONDIVALUE)\n VALUES( ?,?,?,?,?)";
        this.jdbcTemplate.update(sql, new Object[]{findCondiEO.getId(), findCondiEO.getFetchSchemeId(), findCondiEO.getFetchCondiType(), findCondiEO.getFetchCondiCode(), findCondiEO.getFetchCondiValue()});
    }

    @Override
    public void saveBillFetchCondiEOs(List<BillFetchCondiEO> findCondiEOs) {
        for (BillFetchCondiEO findCondiEO : findCondiEOs) {
            this.saveBillFetchCondi(findCondiEO);
        }
    }

    @Override
    public int deleteBillFetchCondiEOByFetchSchemeId(String fetchSchemeId) {
        String sql = "delete from BDE_BILL_FETCH_CONDI where FETCHSCHEMEID = ? ";
        return this.jdbcTemplate.update(sql, new Object[]{fetchSchemeId});
    }

    private BillFetchCondiEO getBillFetchCondiEO(ResultSet rs) throws SQLException {
        BillFetchCondiEO fetchCondiEO = new BillFetchCondiEO();
        fetchCondiEO.setId(rs.getString(1));
        fetchCondiEO.setFetchSchemeId(rs.getString(2));
        fetchCondiEO.setFetchCondiType(rs.getString(3));
        fetchCondiEO.setFetchCondiCode(rs.getString(4));
        fetchCondiEO.setFetchCondiValue(rs.getString(5));
        return fetchCondiEO;
    }
}

