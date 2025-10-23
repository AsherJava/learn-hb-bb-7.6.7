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
import java.util.Objects;
import org.springframework.jdbc.core.RowMapper;

public class CbPlanDwDO
implements RowMapper<CbPlanDwDO> {
    private String planId;
    private String unitId;
    private Timestamp endTime;
    public static final String NR_CB_PLAN_DW = "NR_CB_PLAN_DW";
    public static final String ID_COLUMN = "P_ID";
    public static final String DW_COLUMN = "P_UNIT_ID";
    public static final String END_TIME_COLUMN = "P_END_TIME";

    public String getPlanId() {
        return this.planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Timestamp getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public CbPlanDwDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CbPlanDwDO cbPlanDwDO = new CbPlanDwDO();
        cbPlanDwDO.setPlanId(rs.getString(ID_COLUMN));
        cbPlanDwDO.setUnitId(rs.getString(DW_COLUMN));
        cbPlanDwDO.setEndTime(rs.getTimestamp(END_TIME_COLUMN));
        return cbPlanDwDO;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        CbPlanDwDO that = (CbPlanDwDO)o;
        if (!Objects.equals(this.planId, that.planId)) {
            return false;
        }
        return Objects.equals(this.unitId, that.unitId);
    }

    public int hashCode() {
        int result = this.planId != null ? this.planId.hashCode() : 0;
        result = 31 * result + (this.unitId != null ? this.unitId.hashCode() : 0);
        return result;
    }
}

