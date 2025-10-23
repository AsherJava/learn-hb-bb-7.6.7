/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.reminder.plan.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.jdbc.core.RowMapper;

public class CbPlanTimeDO
implements RowMapper<CbPlanTimeDO> {
    private String planId;
    private String id;
    private int periodType;
    private int periodValue;
    private Timestamp time;
    public static final String P_ID_COLUMN = "P_ID";
    public static final String ID_COLUMN = "ID";
    public static final String P_PERIOD_TYPE_COLUMN = "P_PERIOD_TYPE";
    public static final String P_PERIOD_VALUE_COLUMN = "P_PERIOD_VALUE";
    public static final String P_TIME_COLUMN = "P_TIME";

    public String getPlanId() {
        return this.planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public int getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(int periodValue) {
        this.periodValue = periodValue;
    }

    public Timestamp getTime() {
        return this.time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public CbPlanTimeDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CbPlanTimeDO cbPlanFormDO = new CbPlanTimeDO();
        cbPlanFormDO.setPlanId(rs.getString(P_ID_COLUMN));
        cbPlanFormDO.setId(rs.getString(ID_COLUMN));
        cbPlanFormDO.setPeriodType(rs.getInt(P_PERIOD_TYPE_COLUMN));
        cbPlanFormDO.setPeriodValue(rs.getInt(P_PERIOD_VALUE_COLUMN));
        cbPlanFormDO.setTime(rs.getTimestamp(P_TIME_COLUMN));
        return cbPlanFormDO;
    }
}

