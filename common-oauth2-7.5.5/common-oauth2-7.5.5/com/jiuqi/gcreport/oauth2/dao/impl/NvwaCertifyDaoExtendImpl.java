/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.oauth2.dao.impl;

import com.jiuqi.gcreport.oauth2.dao.NvwaCertifyDaoExtend;
import com.jiuqi.nvwa.certification.bean.NvwaCertify;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository(value="com.jiuqi.gcreport.oauth2.dao.impl.NvwaCertifyDaoExtend")
public class NvwaCertifyDaoExtendImpl
implements NvwaCertifyDaoExtend {
    private static final Logger logger = LoggerFactory.getLogger(NvwaCertifyDaoExtendImpl.class);
    private static final String TABLE_NAME = "NVWA_CERTIFY_SERVICE";
    private static final String FD_ID = "CS_ID";
    private static final String FD_TITLE = "CS_TITLE";
    private static final String FD_CODE = "CS_CODE";
    private static final String FD_CLIENTID = "CS_CLIENTID";
    private static final String FD_CLIENTSECRET = "CS_CLIENTSECRET";
    private static final String FD_TYPE = "CS_TYPE";
    private static final String FD_URL = "CS_URL";
    private static final String FD_FRONTENDURL = "CS_FRONTENDURL";
    private static final String FD_CURENT_SERVICE = "CS_CURENT_SERVICE";
    private static final String FD_EXTRA_INFO = "CS_EXTRA_INFO";
    private static final String FD_ORDINAL = "CS_ORDINAL";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<NvwaCertify> getNvwaCertifyByType(String type) {
        String sql = String.format("SELECT %s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s FROM %s WHERE %s=?", FD_ID, FD_TITLE, FD_CODE, FD_CLIENTID, FD_CLIENTSECRET, FD_TYPE, FD_URL, FD_FRONTENDURL, FD_CURENT_SERVICE, FD_EXTRA_INFO, FD_ORDINAL, TABLE_NAME, FD_TYPE);
        Object[] args = new Object[]{type};
        return (List)this.jdbcTemplate.query(sql, rs -> {
            LinkedList<NvwaCertify> ncList = new LinkedList<NvwaCertify>();
            while (rs.next()) {
                ncList.add(this.buildNvwaCertify(rs));
            }
            return ncList;
        }, args);
    }

    private NvwaCertify buildNvwaCertify(ResultSet rs) throws SQLException {
        NvwaCertify nc = new NvwaCertify();
        nc.setId(rs.getString(FD_ID));
        nc.setTitle(rs.getString(FD_TITLE));
        nc.setCode(rs.getString(FD_CODE));
        nc.setClientid(rs.getString(FD_CLIENTID));
        nc.setClientsecret(rs.getString(FD_CLIENTSECRET));
        nc.setType(rs.getString(FD_TYPE));
        nc.setUrl(rs.getString(FD_URL));
        nc.setFrontendURL(rs.getString(FD_FRONTENDURL));
        nc.setCurentService(rs.getInt(FD_CURENT_SERVICE));
        nc.setOrdinal(rs.getString(FD_ORDINAL));
        byte[] bytes = rs.getBytes(FD_EXTRA_INFO);
        if (bytes != null) {
            try {
                String config = new String(bytes, "UTF-8");
                nc.setExtraInfo(config);
            }
            catch (UnsupportedEncodingException e) {
                logger.error("\u8bfb\u6269\u5c55\u4fe1\u606f\u4e0d\u652f\u6301UTF-8");
            }
        }
        return nc;
    }
}

