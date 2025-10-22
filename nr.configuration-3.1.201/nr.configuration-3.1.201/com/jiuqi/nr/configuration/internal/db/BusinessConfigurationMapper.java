/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.configuration.internal.db;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.configuration.common.ConfigContentType;
import com.jiuqi.nr.configuration.facade.BusinessConfigurationDefine;
import com.jiuqi.nr.configuration.internal.impl.BusinessConfigurationDefineImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class BusinessConfigurationMapper
implements RowMapper<BusinessConfigurationDefine> {
    public BusinessConfigurationDefine mapRow(ResultSet rs, int rowNum) throws SQLException {
        BusinessConfigurationDefineImpl configurationDefine = new BusinessConfigurationDefineImpl();
        configurationDefine.setKey(rs.getString("BC_KEY"));
        configurationDefine.setTaskKey(StringUtils.isEmpty((String)rs.getString("BC_TASK_KEY")) ? null : rs.getString("BC_TASK_KEY"));
        configurationDefine.setFormSchemeKey(StringUtils.isEmpty((String)rs.getString("BC_FORM_SCHEME_KEY")) ? null : rs.getString("BC_FORM_SCHEME_KEY"));
        configurationDefine.setCode(rs.getString("BC_CODE"));
        configurationDefine.setTitle(rs.getString("BC_TITLE"));
        configurationDefine.setDescription(rs.getString("BC_DESC"));
        configurationDefine.setCategory(rs.getString("BC_CATEGORY"));
        configurationDefine.setConfigurationContent(rs.getString("BC_CONTENT"));
        configurationDefine.setContentType(ConfigContentType.forValue(rs.getInt("BC_CONTENT_TYPE")));
        configurationDefine.setFileName(rs.getString("BC_FILE_NAME"));
        return configurationDefine;
    }
}

