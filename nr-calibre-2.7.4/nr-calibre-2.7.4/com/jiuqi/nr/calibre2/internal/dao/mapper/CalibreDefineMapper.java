/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.calibre2.internal.dao.mapper;

import com.jiuqi.nr.calibre2.domain.CalibreDefineDO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CalibreDefineMapper
implements RowMapper<CalibreDefineDO> {
    public static final String TABLE_NAME = "NR_CALIBRE_DEFINE";
    public static final String FIELD_KEY = "CD_KEY";
    public static final String FIELD_CODE = "CD_CODE";
    public static final String FIELD_NAME = "CD_NAME";
    public static final String FIELD_GROUP = "CD_GROUP";
    public static final String FIELD_TYPE = "CD_TYPE";
    public static final String FIELD_STRUCT_TYPE = "CD_STRUCT_TYPE";
    public static final String FIELD_EDIT_MODEL = "CD_EXPRESSION_VALUES";
    public static final String FIELD_REFER = "CD_ENTITYID";
    public static final String FIELD_ORDER = "CD_ORDER";

    public CalibreDefineDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CalibreDefineDO calibreDefine = new CalibreDefineDO();
        calibreDefine.setKey(rs.getString(FIELD_KEY));
        calibreDefine.setCode(rs.getString(FIELD_CODE));
        calibreDefine.setName(rs.getString(FIELD_NAME));
        calibreDefine.setGroup(rs.getString(FIELD_GROUP));
        calibreDefine.setType(rs.getInt(FIELD_TYPE));
        calibreDefine.setStructType(rs.getInt(FIELD_STRUCT_TYPE));
        calibreDefine.setExpression_Values(rs.getInt(FIELD_EDIT_MODEL));
        calibreDefine.setEntityId(rs.getString(FIELD_REFER));
        calibreDefine.setOrder(rs.getString(FIELD_ORDER));
        return calibreDefine;
    }
}

