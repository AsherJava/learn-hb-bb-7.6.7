/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.fetch.impl.callback.service;

import com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FetchBalanceTableCleanServiceImpl {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String TRUNCATE_TABLE_SQL = "TRUNCATE TABLE %1$s";

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void doClean(MemoryBalanceTypeEnum table) {
        this.jdbcTemplate.update(String.format(TRUNCATE_TABLE_SQL, table.getCode()));
    }
}

