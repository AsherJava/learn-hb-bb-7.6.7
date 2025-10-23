/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.transmission.data.dao.mapper;

import com.jiuqi.nr.transmission.data.domain.SyncSchemeParamDO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class SyncSchemeParamMapper
implements RowMapper<SyncSchemeParamDO> {
    public static final String TABLE_NAME = "NR_TRANS_PARAM";
    public static final String FIELD_TP_KEY = "TP_KEY";
    public static final String FIELD_TP_SCHEME_KEY = "TP_SCHEME_KEY";
    public static final String FIELD_TP_TASK = "TP_TASK";
    public static final String FIELD_TP_PERIOD = "TP_PERIOD";
    public static final String FIELD_TP_PERIOD_VALUE = "TP_PERIOD_VALUE";
    public static final String FIELD_TP_ENTITY_TYPE = "TP_ENTITY_TYPE";
    public static final String FIELD_TP_ENTITY = "TP_ENTITY";
    public static final String FIELD_TP_FORM_TYPE = "TP_FORM_TYPE";
    public static final String FIELD_TP_FORM = "TP_FORM";
    public static final String FIELD_TP_UPLOAD = "TP_IS_UPLOAD";
    public static final String FIELD_TP_FORCE_UPLOAD = "TP_FORCE_UPLOAD";
    public static final String FIELD_TP_DESC = "TP_DESC";
    public static final String FIELD_TP_DIM_KEYS = "TP_DIM_KEYS";
    public static final String FIELD_TP_DIM_VALUES = "TP_DIM_VALUES";
    public static final String FIELD_TP_ADJUST_PERIOD = "TP_ADJUST_PERIOD";
    public static final String FIELD_TP_MAP_KEY = "TP_MAP_KEY";
    public static final String FIELD_TP_DATA_MESSAGE = "TP_DATA_MESSAGE";

    public SyncSchemeParamDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        SyncSchemeParamDO paramDO = new SyncSchemeParamDO();
        paramDO.setKey(rs.getString(FIELD_TP_KEY));
        paramDO.setSchemeKey(rs.getString(FIELD_TP_SCHEME_KEY));
        paramDO.setTask(rs.getString(FIELD_TP_TASK));
        paramDO.setPeriod(rs.getInt(FIELD_TP_PERIOD));
        paramDO.setEntityType(rs.getInt(FIELD_TP_ENTITY_TYPE));
        paramDO.setPeriodValue(rs.getString(FIELD_TP_PERIOD_VALUE));
        paramDO.setEntity(rs.getString(FIELD_TP_ENTITY));
        paramDO.setFormType(rs.getInt(FIELD_TP_FORM_TYPE));
        paramDO.setForm(rs.getString(FIELD_TP_FORM));
        paramDO.setDescription(rs.getString(FIELD_TP_DESC));
        paramDO.setIsUpload(rs.getInt(FIELD_TP_UPLOAD));
        paramDO.setAllowForceUpload(rs.getInt(FIELD_TP_FORCE_UPLOAD));
        paramDO.setDimKeys(rs.getString(FIELD_TP_DIM_KEYS));
        paramDO.setDimValues(rs.getString(FIELD_TP_DIM_VALUES));
        paramDO.setAdjustPeriod(rs.getString(FIELD_TP_ADJUST_PERIOD));
        paramDO.setMappingSchemeKey(rs.getString(FIELD_TP_MAP_KEY));
        paramDO.setDataMessage(rs.getString(FIELD_TP_DATA_MESSAGE));
        return paramDO;
    }
}

