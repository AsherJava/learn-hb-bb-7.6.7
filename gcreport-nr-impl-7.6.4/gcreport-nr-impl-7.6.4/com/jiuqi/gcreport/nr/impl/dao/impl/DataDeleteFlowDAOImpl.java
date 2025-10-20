/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.CallableStatementCallback
 *  org.springframework.jdbc.core.CallableStatementCreator
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.nr.impl.dao.impl;

import com.jiuqi.gcreport.nr.impl.dao.DataDeleteFlowDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataDeleteFlowDAOImpl
implements DataDeleteFlowDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void deleteFlow(final String procInstId) {
        try {
            this.jdbcTemplate.execute(new CallableStatementCreator(){

                public CallableStatement createCallableStatement(Connection con) throws SQLException {
                    String storedProc = "{call delete_flow(?)}";
                    return con.prepareCall(storedProc);
                }
            }, new CallableStatementCallback(){

                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                    try (CallableStatement stmt = cs;){
                        stmt.setString(1, procInstId);
                        stmt.execute();
                    }
                    return null;
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

