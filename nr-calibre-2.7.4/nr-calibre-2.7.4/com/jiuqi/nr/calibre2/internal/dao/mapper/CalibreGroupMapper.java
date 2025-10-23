/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.calibre2.internal.dao.mapper;

import com.jiuqi.nr.calibre2.domain.CalibreGroupDO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CalibreGroupMapper
implements RowMapper<CalibreGroupDO> {
    public static final String TABLE_NAME = "NR_CALIBRE_GROUP";
    public static final String FIELD_KEY = "CG_KEY";
    public static final String FIELD_NAME = "CG_NAME";
    public static final String FIELD_PARENT = "CG_PARENT";
    public static final String FIELD_ORDER = "CG_ORDER";

    public CalibreGroupDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CalibreGroupDO calibreGroup = new CalibreGroupDO();
        calibreGroup.setKey(rs.getString(FIELD_KEY));
        calibreGroup.setName(rs.getString(FIELD_NAME));
        calibreGroup.setParent(rs.getString(FIELD_PARENT));
        calibreGroup.setOrder(rs.getString(FIELD_ORDER));
        return calibreGroup;
    }
}

