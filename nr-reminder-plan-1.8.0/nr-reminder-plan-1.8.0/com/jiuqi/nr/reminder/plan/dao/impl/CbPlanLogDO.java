/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.reminder.plan.dao.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.jdbc.core.RowMapper;

public class CbPlanLogDO
implements RowMapper<CbPlanLogDO> {
    private String logId;
    private String id;
    private String unit;
    private String unitCode;
    private String form;
    private String formKey;
    private String dataTime;
    private String recipientId;
    private String channel;
    private String channelTitle;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp sendTime;
    private int status;
    private String message;
    private String order;
    public static final String NR_CB_PLAN_LOG = "NR_CB_PLAN_LOG";
    public static final String ID = "ID";
    public static final String LOG_ID = "LOG_ID";
    public static final String P_UNIT = "P_UNIT";
    public static final String P_FORM = "P_FORM";
    public static final String P_DATA_TIME = "P_DATA_TIME";
    public static final String RECIPIENT_ID = "RECIPIENT_ID";
    public static final String P_CHANNEL = "P_CHANNEL";
    public static final String P_SEND_TIME = "P_SEND_TIME";
    public static final String SEND_STATUS = "SEND_STATUS";
    public static final String LOG_DETAIL_MESSAGE = "LOG_DETAIL_MESSAGE";
    public static final String P_UNIT_CODE = "P_UNIT_CODE";
    public static final String P_FORM_KEY = "P_FORM_KEY";
    public static final String P_ORDER = "P_ORDER";

    public String getLogId() {
        return this.logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getForm() {
        return this.form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getRecipientId() {
        return this.recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelTitle() {
        return this.channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public Timestamp getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public CbPlanLogDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CbPlanLogDO cbPlanLog = new CbPlanLogDO();
        cbPlanLog.setId(rs.getString(ID));
        cbPlanLog.setLogId(rs.getString(LOG_ID));
        cbPlanLog.setUnit(rs.getString(P_UNIT));
        cbPlanLog.setForm(rs.getString(P_FORM));
        cbPlanLog.setUnitCode(rs.getString(P_UNIT_CODE));
        cbPlanLog.setFormKey(rs.getString(P_FORM_KEY));
        cbPlanLog.setDataTime(rs.getString(P_DATA_TIME));
        cbPlanLog.setRecipientId(rs.getString(RECIPIENT_ID));
        cbPlanLog.setChannel(rs.getString(P_CHANNEL));
        cbPlanLog.setSendTime(rs.getTimestamp(P_SEND_TIME));
        cbPlanLog.setStatus(rs.getInt(SEND_STATUS));
        cbPlanLog.setMessage(rs.getString(LOG_DETAIL_MESSAGE));
        cbPlanLog.setOrder(rs.getString(P_ORDER));
        return cbPlanLog;
    }
}

