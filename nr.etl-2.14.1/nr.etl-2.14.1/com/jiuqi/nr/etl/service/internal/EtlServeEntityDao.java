/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.crypto.Crypto
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.etl.service.internal;

import com.jiuqi.nr.common.crypto.Crypto;
import com.jiuqi.nr.etl.common.EtlServeEntity;
import com.jiuqi.nr.etl.common.ServeType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EtlServeEntityDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(EtlServeEntityDao.class);
    private final String TABLE = "NR_ETL_SERVER_";
    private final String PROTOCOL = "PROTOCOL_";
    private final String URL = "URL_";
    private final String USER_NAME = "USER_NAME_";
    private final String PWD = "PWD_";
    private final String TYPE = "TYPE_";
    private final String CREATE_USER = "CREATE_USER_";
    private final String CREATE_TIME = "CREATE_TIME_";
    private static final Function<ResultSet, EtlServeEntity> ENTITY_READER = rs -> {
        EtlServeEntity entity = new EtlServeEntity();
        int col = 1;
        try {
            entity.setProtocol(rs.getString(col++));
            entity.setUrl(rs.getString(col++));
            entity.setUserName(rs.getString(col++));
            entity.setPwd(rs.getString(col++));
            entity.setType(ServeType.valueOf(rs.getInt(col++)));
            entity.setCreateUser(rs.getString(col++));
            Timestamp createTime = rs.getTimestamp(col);
            if (createTime != null) {
                entity.setCreateTime(createTime.toInstant());
            }
        }
        catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("read StateInfo error.", e);
        }
        return entity;
    };

    public Optional<EtlServeEntity> getServerInfo() {
        String sql = String.format("SELECT %S,%S,%S,%S,%S,%S,%S FROM %S", "PROTOCOL_", "URL_", "USER_NAME_", "PWD_", "TYPE_", "CREATE_USER_", "CREATE_TIME_", "NR_ETL_SERVER_");
        return this.jdbcTemplate.query(sql, (rs, row) -> ENTITY_READER.apply(rs)).stream().findFirst();
    }

    public void delete() {
        String sql = String.format("DELETE FROM %S", "NR_ETL_SERVER_");
        this.jdbcTemplate.execute(sql);
    }

    public void save(EtlServeEntity stateInfo) {
        String sql = String.format("INSERT INTO %S (%S,%S,%S,%S,%S,%S,%S) VALUES (?,?,?,?,?,?,?)", "NR_ETL_SERVER_", "URL_", "USER_NAME_", "PWD_", "TYPE_", "CREATE_USER_", "CREATE_TIME_", "PROTOCOL_");
        Object[] params = new Object[7];
        params[0] = stateInfo.getUrl();
        params[1] = stateInfo.getUserName();
        try {
            params[2] = Crypto.encrypt((String)stateInfo.getPwd());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        params[3] = stateInfo.getType() != null ? Integer.valueOf(stateInfo.getType().getValue()) : Integer.valueOf(0);
        params[4] = stateInfo.getCreateUser();
        Instant createTime = stateInfo.getCreateTime();
        if (createTime != null) {
            params[5] = Timestamp.from(createTime);
        }
        params[6] = stateInfo.getProtocol();
        this.jdbcTemplate.update(sql, params);
    }
}

