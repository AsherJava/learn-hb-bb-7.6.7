/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.internal.dao.DataLinkDO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

class DataLinkRowMapper
implements RowMapper<DataLinkDO> {
    DataLinkRowMapper() {
    }

    public DataLinkDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DataLinkDO dataLink = new DataLinkDO();
        dataLink.setKey(rs.getString("dl_key"));
        dataLink.setUniqueCode(rs.getString("dl_unique_code"));
        dataLink.setRegionKey(rs.getString("dl_region_key"));
        dataLink.setType(rs.getInt("DL_TYPE"));
        if (dataLink.getType() == DataLinkType.DATA_LINK_TYPE_FIELD.getValue() || dataLink.getType() == DataLinkType.DATA_LINK_TYPE_INFO.getValue()) {
            dataLink.setLinkExpression(rs.getString("DL_FIELD_KEY"));
        } else {
            dataLink.setLinkExpression(rs.getString("dl_expression"));
        }
        dataLink.setPosX(rs.getInt("dl_posx"));
        dataLink.setPosY(rs.getInt("dl_posy"));
        dataLink.setColNum(rs.getInt("dl_col_num"));
        dataLink.setRowNum(rs.getInt("dl_row_num"));
        dataLink.setFormKey(rs.getString("FM_KEY"));
        return dataLink;
    }
}

