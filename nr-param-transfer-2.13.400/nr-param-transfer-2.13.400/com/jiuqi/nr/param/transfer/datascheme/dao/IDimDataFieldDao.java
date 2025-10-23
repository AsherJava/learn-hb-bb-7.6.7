/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.param.transfer.datascheme.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IDimDataFieldDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String SQL = "select df_ref_entity_id from nr_datascheme_field_des where df_dt_key = ? and df_kind <= 8 and df_ref_entity_id is not null group by df_ref_entity_id";

    public List<String> getEntityIdsByDataTableKey(String tableKey) {
        return this.jdbcTemplate.query(SQL, (rs, rowNum) -> rs.getString(1), new Object[]{tableKey});
    }
}

