/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.ArgumentPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.nr.system.check.datachange.util;

import java.sql.PreparedStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class FetchReader {
    protected Logger logger = LoggerFactory.getLogger(FetchReader.class);
    protected JdbcTemplate jdbcTemplate;

    public FetchReader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public <T> T query(String sql, Object[] args, ResultSetExtractor<T> rse) {
        ArgumentPreparedStatementSetter setter = null;
        if (args != null) {
            StringBuilder argsStr = new StringBuilder();
            for (Object arg : args) {
                argsStr.append(arg).append(",");
            }
            this.logger.info("{};\r\n{}", (Object)sql, (Object)argsStr);
            setter = new ArgumentPreparedStatementSetter(args);
        }
        return (T)this.jdbcTemplate.query(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, 1003, 1007);
            preparedStatement.setFetchSize(1000);
            preparedStatement.setFetchDirection(1000);
            return preparedStatement;
        }, setter, rse);
    }
}

