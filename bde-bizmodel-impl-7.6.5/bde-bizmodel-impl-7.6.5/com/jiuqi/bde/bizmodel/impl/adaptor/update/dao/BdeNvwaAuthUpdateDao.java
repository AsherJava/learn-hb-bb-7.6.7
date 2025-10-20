/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.bizmodel.impl.adaptor.update.dao;

import com.jiuqi.bde.bizmodel.impl.adaptor.update.dto.BdeNvwaAppUpdateDTO;
import com.jiuqi.bde.bizmodel.impl.adaptor.update.dto.BdeNvwaCertifyUpdateDTO;
import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BdeNvwaAuthUpdateDao {
    public static final Logger LOGGER = LoggerFactory.getLogger(BdeNvwaAuthUpdateDao.class);
    private static final String CS_TD_FILE = "NVWA_CERTIFY_SERVICE";
    private static final String CS_ID = "CS_ID";
    private static final String CS_TITLE = "CS_TITLE";
    private static final String CS_CODE = "CS_CODE";
    private static final String CS_CLIENTID = "CS_CLIENTID";
    private static final String CS_CLIENTSECRET = "CS_CLIENTSECRET";
    private static final String CS_TYPE = "CS_TYPE";
    private static final String CS_URL = "CS_URL";
    private static final String CS_FRONTENDURL = "CS_FRONTENDURL";
    private static final String CS_CURENT_SERVICE = "CS_CURENT_SERVICE";
    private static final String CS_EXTRA_INFO = "CS_EXTRA_INFO";
    private static final String CS_ORDINAL = "CS_ORDINAL";
    private static final String CS_USE_TOKEN = "CS_USE_TOKEN";
    private static final String AS_TD_FILE = "NVWA_APP_SERVICE";
    private static final String AS_ID = "AS_ID";
    private static final String AS_TITLE = "AS_TITLE";
    private static final String AS_CODE = "AS_CODE";
    private static final String AS_CLIENTID = "AS_CLIENTID";
    private static final String AS_CLIENTSECRET = "AS_CLIENTSECRET";
    private static final String AS_TYPE = "AS_TYPE";
    private static final String AS_URL = "AS_URL";
    private static final String AS_FRONTENDURL = "AS_FRONTENDURL";
    private static final String AS_EXTRA_INFO = "AS_EXTRA_INFO";
    private static final String AS_ORDINAL = "AS_ORDINAL";
    private static final String AS_TICKET_VALIDTIME = "AS_TICKET_VALIDTIME";
    private static final String AS_TOKEN_VALIDTIME = "AS_TOKEN_VALIDTIME";
    private static final String AS_IP_LIST = "AS_IP_LIST";
    private static final String AS_USE_TOKEN = "AS_USE_TOKEN";

    public List<BdeNvwaCertifyUpdateDTO> listCertify(String dataSourceCode) {
        String selectCertifyServiceSql = String.format("SELECT %s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s FROM %s ORDER BY %s", CS_ID, CS_TITLE, CS_CODE, CS_CLIENTID, CS_CLIENTSECRET, CS_TYPE, CS_URL, CS_FRONTENDURL, CS_CURENT_SERVICE, CS_EXTRA_INFO, CS_ORDINAL, CS_USE_TOKEN, CS_TD_FILE, CS_ORDINAL);
        GcBizJdbcTemplate bizJdbcTemplate = OuterDataSourceUtils.getJdbcTemplate((String)dataSourceCode);
        return bizJdbcTemplate.query(selectCertifyServiceSql, (RowMapper)new NvwaCertifyRowMapper());
    }

    private BdeNvwaCertifyUpdateDTO buildNvwaCertify(ResultSet rs) throws SQLException {
        BdeNvwaCertifyUpdateDTO nvwaCertify = new BdeNvwaCertifyUpdateDTO();
        nvwaCertify.setId(rs.getString(CS_ID));
        nvwaCertify.setCode(rs.getString(CS_CODE));
        nvwaCertify.setTitle(rs.getString(CS_TITLE));
        nvwaCertify.setType(rs.getString(CS_TYPE));
        nvwaCertify.setCurentService(rs.getInt(CS_CURENT_SERVICE));
        nvwaCertify.setUrl(rs.getString(CS_URL));
        nvwaCertify.setFrontendURL(rs.getString(CS_FRONTENDURL));
        nvwaCertify.setClientid(rs.getString(CS_CLIENTID));
        nvwaCertify.setClientsecret(rs.getString(CS_CLIENTSECRET));
        nvwaCertify.setOrdinal(rs.getString(CS_ORDINAL));
        nvwaCertify.setUseToken(rs.getInt(CS_USE_TOKEN) == 1);
        byte[] bytes = rs.getBytes(CS_EXTRA_INFO);
        if (bytes != null) {
            try {
                String config = new String(bytes, "UTF-8");
                nvwaCertify.setExtraInfo(config);
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return nvwaCertify;
    }

    public List<BdeNvwaAppUpdateDTO> listApp(String dataSourceCode) {
        String selectAppServiceSql = String.format("SELECT %s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s FROM %s WHERE 1=1 ORDER BY %s", AS_ID, AS_TITLE, AS_CODE, CS_ID, AS_CLIENTID, AS_CLIENTSECRET, AS_TYPE, AS_URL, AS_FRONTENDURL, AS_EXTRA_INFO, AS_ORDINAL, AS_TICKET_VALIDTIME, AS_TOKEN_VALIDTIME, AS_IP_LIST, AS_USE_TOKEN, AS_TD_FILE, AS_ORDINAL);
        GcBizJdbcTemplate bizJdbcTemplate = OuterDataSourceUtils.getJdbcTemplate((String)dataSourceCode);
        return bizJdbcTemplate.query(selectAppServiceSql, (RowMapper)new NvwaAppRowMapper());
    }

    private BdeNvwaAppUpdateDTO buildNvwaApp(ResultSet rs) throws SQLException {
        BdeNvwaAppUpdateDTO app = new BdeNvwaAppUpdateDTO();
        app.setId(rs.getString(AS_ID));
        app.setTitle(rs.getString(AS_TITLE));
        app.setCode(rs.getString(AS_CODE));
        app.setCsId(rs.getString(CS_ID));
        app.setClientid(rs.getString(AS_CLIENTID));
        app.setClientsecret(rs.getString(AS_CLIENTSECRET));
        app.setType(rs.getString(AS_TYPE));
        app.setUrl(rs.getString(AS_URL));
        app.setFrontendURL(rs.getString(AS_FRONTENDURL));
        app.setOrdinal(rs.getString(AS_ORDINAL));
        app.setTicketValidTime(rs.getInt(AS_TICKET_VALIDTIME));
        app.setTokenValidTime(rs.getInt(AS_TOKEN_VALIDTIME));
        app.setIpList(rs.getString(AS_IP_LIST));
        byte[] bytes = rs.getBytes(AS_EXTRA_INFO);
        if (bytes != null) {
            try {
                String config = new String(bytes, "UTF-8");
                app.setExtraInfo(config);
            }
            catch (IOException e) {
                LOGGER.error("\u67e5\u8be2\u5e94\u7528\u51fa\u9519", e);
            }
        }
        app.setUseToken(rs.getInt(AS_USE_TOKEN) == 1);
        return app;
    }

    class NvwaAppRowMapper
    implements RowMapper<BdeNvwaAppUpdateDTO> {
        NvwaAppRowMapper() {
        }

        public BdeNvwaAppUpdateDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            BdeNvwaAppUpdateDTO fileImpl = BdeNvwaAuthUpdateDao.this.buildNvwaApp(rs);
            return fileImpl;
        }
    }

    class NvwaCertifyRowMapper
    implements RowMapper<BdeNvwaCertifyUpdateDTO> {
        NvwaCertifyRowMapper() {
        }

        public BdeNvwaCertifyUpdateDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            BdeNvwaCertifyUpdateDTO fileImpl = BdeNvwaAuthUpdateDao.this.buildNvwaCertify(rs);
            return fileImpl;
        }
    }
}

