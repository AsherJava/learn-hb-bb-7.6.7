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

public class CbPlanFormDO
implements RowMapper<CbPlanFormDO> {
    private String planId;
    private String formId;
    public static final int FORM = 1;
    public static final int FORM_GROUP = 2;
    private int formType;
    public static final String ID_COLUMN = "P_ID";
    public static final String FORM_ID_COLUMN = "F_ID";
    public static final String FORM_TYPE_COLUMN = "F_TYPE";

    public String getPlanId() {
        return this.planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public int getFormType() {
        return this.formType;
    }

    public void setFormType(int formType) {
        this.formType = formType;
    }

    public CbPlanFormDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CbPlanFormDO cbPlanFormDO = new CbPlanFormDO();
        cbPlanFormDO.setPlanId(rs.getString(ID_COLUMN));
        cbPlanFormDO.setFormId(rs.getString(FORM_ID_COLUMN));
        cbPlanFormDO.setFormType(rs.getInt(FORM_TYPE_COLUMN));
        return cbPlanFormDO;
    }
}

