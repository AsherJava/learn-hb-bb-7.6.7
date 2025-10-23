/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.calibre2.internal.dao.mapper;

import com.jiuqi.nr.calibre2.internal.domain.CalibreSubListDO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CalibreSubListMapper
implements RowMapper<CalibreSubListDO> {
    public static final String TABLE_NAME = "NR_CALIBRE_SUBLIST";
    public static final String FIELD_CODE = "CS_CODE";
    public static final String FIELD_CALIBRE_CODE = "CS_CALIBRE_CODE";
    public static final String FIELD_VALUE = "CS_VALUE";

    public CalibreSubListDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CalibreSubListDO subListDO = new CalibreSubListDO();
        subListDO.setCode(rs.getString(FIELD_CODE));
        subListDO.setCalibreCode(rs.getString(FIELD_CALIBRE_CODE));
        subListDO.setValue(rs.getString(FIELD_VALUE));
        return subListDO;
    }
}

