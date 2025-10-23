/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.param.transfer.definition.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DataTableByFormDefineDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String SQL = "select df.df_dt_key from nr_datascheme_field_des df inner join nr_param_datalink_des dl on df.df_key = dl.dl_field_key inner join nr_param_dataregion dr on dl.dl_region_key = dr.dr_key where dl.dl_type = 1 and dr.dr_form_key = ? group by df.df_dt_key";

    public List<String> getDataTableKeysByFormKey(String formKey) {
        return this.jdbcTemplate.query(SQL, (rs, rowNum) -> rs.getString(1), new Object[]{formKey});
    }
}

