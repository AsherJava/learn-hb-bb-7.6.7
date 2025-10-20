/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.DataLinkMappingDefine;
import com.jiuqi.nr.definition.internal.impl.RuntimeDataLinkMappingDefineImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RuntimeDataLinkMappingDefineDao
extends BaseDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Class<RuntimeDataLinkMappingDefineImpl> implClz = RuntimeDataLinkMappingDefineImpl.class;

    public Class<RuntimeDataLinkMappingDefineImpl> getClz() {
        return this.implClz;
    }

    public List<DataLinkMappingDefine> getByFormKey(String formKey) {
        return super.list(new String[]{"formKey"}, new Object[]{formKey}, this.implClz);
    }

    public List<DataLinkMappingDefine> getByFormSchemeKey(String formSchemeKey) {
        RowMapper rowMapper = this.getRowMapper(this.getClz());
        String sql = "SELECT M.* FROM NR_PARAM_DATALINKMAPPING M LEFT JOIN NR_PARAM_FORM_DES F ON M.FM_KEY = F.FM_KEY WHERE F.FM_FORMSCHEME =?";
        return this.jdbcTemplate.query("SELECT M.* FROM NR_PARAM_DATALINKMAPPING M LEFT JOIN NR_PARAM_FORM_DES F ON M.FM_KEY = F.FM_KEY WHERE F.FM_FORMSCHEME =?", pss -> pss.setString(1, formSchemeKey), rowMapper);
    }
}

