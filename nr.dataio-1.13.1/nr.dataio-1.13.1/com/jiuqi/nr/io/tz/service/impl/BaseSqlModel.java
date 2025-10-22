/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.nr.common.db.DatabaseInstance
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.nr.common.db.DatabaseInstance;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseSqlModel {
    protected final JdbcTemplate jdbcTemplate;
    protected final IDatabase database;

    public BaseSqlModel(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.database = DatabaseInstance.getDatabase();
    }

    public abstract String buildSql();
}

