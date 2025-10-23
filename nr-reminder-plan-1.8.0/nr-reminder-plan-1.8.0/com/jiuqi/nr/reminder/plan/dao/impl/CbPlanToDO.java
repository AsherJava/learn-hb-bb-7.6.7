/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.reminder.plan.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CbPlanToDO
implements RowMapper<CbPlanToDO> {
    private String planId;
    private String toId;
    public static final int TO = 1;
    public static final int CC_TO = 2;
    private int toType;
    public static final int ROLE = 1;
    public static final int USER = 2;
    private int toIdType;
    public static final String ID_COLUMN = "P_ID";
    public static final String TO_ID_COLUMN = "TO_ID";
    public static final String TO_TYPE_COLUMN = "TO_TYPE";
    public static final String TO_ID_TYPE_COLUMN = "TO_ID_TYPE";

    public String getPlanId() {
        return this.planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getToId() {
        return this.toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public int getToType() {
        return this.toType;
    }

    public void setToType(int toType) {
        this.toType = toType;
    }

    public int getToIdType() {
        return this.toIdType;
    }

    public void setToIdType(int toIdType) {
        this.toIdType = toIdType;
    }

    public CbPlanToDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CbPlanToDO cbPlanToDO = new CbPlanToDO();
        cbPlanToDO.setPlanId(rs.getString(ID_COLUMN));
        cbPlanToDO.setToId(rs.getString(TO_ID_COLUMN));
        cbPlanToDO.setToType(rs.getInt(TO_TYPE_COLUMN));
        cbPlanToDO.setToIdType(rs.getInt(TO_ID_TYPE_COLUMN));
        return cbPlanToDO;
    }
}

