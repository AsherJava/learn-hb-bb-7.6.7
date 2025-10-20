/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.budcustom.dao.impl;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.gcreport.definition.impl.basic.init.table.budcustom.dao.DimPublishManageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DimPublishManageDaoImpl
implements DimPublishManageDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertPublishInfo(String id, String modelCode) {
        this.jdbcTemplate.update("INSERT INTO GC_PUBLISHMANAGE(ID,VER,MODELCODE,OPERATINGTIME) VALUES(?,?,?,?)", new Object[]{id, 0, modelCode, DateUtils.now()});
    }

    @Override
    public int updatePublishInfo(String id) {
        return this.jdbcTemplate.update("UPDATE GC_PUBLISHMANAGE SET VER = VER + 1,OPERATINGTIME = ? WHERE ID = ? AND VER = 0", new Object[]{DateUtils.now(), id});
    }
}

