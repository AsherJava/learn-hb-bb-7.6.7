/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.calibre2.internal.dao.mapper;

import com.jiuqi.nr.calibre2.common.CalibreTableColumn;
import com.jiuqi.nr.calibre2.domain.CalibreDataDO;
import com.jiuqi.nr.calibre2.domain.CalibreExpressionDO;
import com.jiuqi.nr.period.util.JacksonUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

public class CalibreDataMapper
implements RowMapper<CalibreDataDO> {
    private static final Logger logs = LoggerFactory.getLogger(CalibreDataMapper.class);
    public static final String TABLE_NAME = "NR_CALIBRE_DATA";
    public static final String FIELD_KEY = CalibreTableColumn.KEY.getCode();
    public static final String FIELD_CODE = CalibreTableColumn.CODE.getCode();
    public static final String FIELD_NAME = CalibreTableColumn.NAME.getCode();
    public static final String FIELD_PARENT = CalibreTableColumn.PARENT.getCode();
    public static final String FIELD_ORDER = CalibreTableColumn.ORDER.getCode();
    public static final String FIELD_VALUE = CalibreTableColumn.VALUE.getCode();
    public static final String FIELD_CALIBRE_CODE = CalibreTableColumn.CALIBRE_CODE.getCode();

    public CalibreDataDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CalibreDataDO calibreDataDO = new CalibreDataDO();
        calibreDataDO.setKey(rs.getString(FIELD_KEY));
        calibreDataDO.setCode(rs.getString(FIELD_CODE));
        calibreDataDO.setName(rs.getString(FIELD_NAME));
        calibreDataDO.setParent(rs.getString(FIELD_PARENT));
        calibreDataDO.setOrder(rs.getLong(FIELD_ORDER));
        String param = rs.getString(FIELD_VALUE);
        if (StringUtils.hasText(param)) {
            CalibreExpressionDO paramDO = null;
            try {
                paramDO = (CalibreExpressionDO)JacksonUtils.jsonToObject((String)param, CalibreExpressionDO.class);
            }
            catch (Exception e) {
                logs.error("\u8bfb\u53d6\u540c\u6b65\u5386\u53f2\u7684\u53c2\u6570\u65f6\u89e3\u6790\u9519\u8bef", e);
            }
            calibreDataDO.setValue(paramDO);
        }
        calibreDataDO.setCalibreCode(rs.getString(FIELD_CALIBRE_CODE));
        return calibreDataDO;
    }
}

