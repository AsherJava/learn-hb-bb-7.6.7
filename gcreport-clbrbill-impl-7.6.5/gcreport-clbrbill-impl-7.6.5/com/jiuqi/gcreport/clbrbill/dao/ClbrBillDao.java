/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.clbrbill.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClbrBillDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> queryClbrBillByClbrCode(List<String> clbrCode) {
        String inClause = String.join((CharSequence)",", Collections.nCopies(clbrCode.size(), "?"));
        String sql = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s\nFROM %s\nWHERE %s IN (%s)", "BILLCODE", "INITIATEAMT", "RECEIVEAMT", "THIRDAMT", "INITIATEQUOTEAMT", "RECEIVEQUOTEAMT", "THIRDQUOTEAMT", "ISTRIPARTITE", "CLBRSTATE", "GC_CLBRBILL", "BILLCODE", inClause);
        return this.jdbcTemplate.queryForList(sql, clbrCode.toArray());
    }
}

