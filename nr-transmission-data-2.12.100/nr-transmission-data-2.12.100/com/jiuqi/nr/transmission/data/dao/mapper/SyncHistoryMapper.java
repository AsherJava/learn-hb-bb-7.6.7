/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.transmission.data.dao.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nr.transmission.data.domain.SyncHistoryDO;
import com.jiuqi.nr.transmission.data.domain.SyncSchemeParamDO;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

public class SyncHistoryMapper
implements RowMapper<SyncHistoryDO> {
    private static final Logger logs = LoggerFactory.getLogger(SyncHistoryMapper.class);
    public static final String TABLE_NAME = "NR_TRANS_HISTORY";
    public static final String FIELD_TH_KEY = "TH_KEY";
    public static final String FIELD_TH_SCHEME_KEY = "TH_SCHEME_KEY";
    public static final String FIELD_TH_STATUS = "TH_STATUS";
    public static final String FIELD_TH_DETAIL = "TH_DETAIL";
    public static final String FIELD_TH_PARAM = "TH_PARAM";
    public static final String FIELD_TH_START_TIME = "TH_START_TIME";
    public static final String FIELD_TH_END_TIME = "TH_END_TIME";
    public static final String FIELD_TH_FILE_KEY = "TH_FILE_KEY";
    public static final String FIELD_TH_USER_ID = "TH_USER_ID";
    public static final String FIELD_TH_FINISH_ENTITY = "TH_FINISH_ENTITY";
    public static final String FIELD_TH_TYPE = "TH_TYPE";
    public static final String FIELD_TH_INSTANCE_ID = "TH_INSTANCE_ID";
    public static final String FIELD_TH_RESULT = "TH_RESULT";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SyncHistoryDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        String result;
        SyncHistoryDO historyDO = new SyncHistoryDO();
        historyDO.setKey(rs.getString(FIELD_TH_KEY));
        historyDO.setSchemeKey(rs.getString(FIELD_TH_SCHEME_KEY));
        historyDO.setStatus(rs.getInt(FIELD_TH_STATUS));
        historyDO.setDetail(rs.getString(FIELD_TH_DETAIL));
        String param = rs.getString(FIELD_TH_PARAM);
        if (StringUtils.hasText(param)) {
            SyncSchemeParamDO paramDO = null;
            try {
                paramDO = (SyncSchemeParamDO)JacksonUtils.jsonToObject((String)param, SyncSchemeParamDO.class);
            }
            catch (Exception e) {
                logs.error("\u8bfb\u53d6\u540c\u6b65\u5386\u53f2\u7684\u53c2\u6570\u65f6\u89e3\u6790\u9519\u8bef", e);
            }
            historyDO.setSyncSchemeParamDO(paramDO);
        }
        if (StringUtils.hasText(result = rs.getString(FIELD_TH_RESULT))) {
            DataImportResult dataImportResult = null;
            try {
                dataImportResult = (DataImportResult)JacksonUtils.jsonToObject((String)result, DataImportResult.class);
            }
            catch (Exception e) {
                logs.error("\u8bfb\u53d6\u540c\u6b65\u5386\u53f2\u7684\u540c\u6b65\u7ed3\u679c\u89e3\u6790\u9519\u8bef", e);
            }
            historyDO.setResult(dataImportResult);
        }
        if (rs.getTimestamp(FIELD_TH_START_TIME) != null) {
            historyDO.setStartTime(new Date(rs.getTimestamp(FIELD_TH_START_TIME).getTime()));
        }
        if (rs.getTimestamp(FIELD_TH_END_TIME) != null) {
            historyDO.setEndTime(new Date(rs.getTimestamp(FIELD_TH_END_TIME).getTime()));
        }
        historyDO.setFileKey(rs.getString(FIELD_TH_FILE_KEY));
        historyDO.setUserId(rs.getString(FIELD_TH_USER_ID));
        historyDO.setFinishEntity(rs.getString(FIELD_TH_FINISH_ENTITY));
        historyDO.setType(rs.getInt(FIELD_TH_TYPE));
        historyDO.setInstanceId(rs.getString(FIELD_TH_INSTANCE_ID));
        return historyDO;
    }
}

