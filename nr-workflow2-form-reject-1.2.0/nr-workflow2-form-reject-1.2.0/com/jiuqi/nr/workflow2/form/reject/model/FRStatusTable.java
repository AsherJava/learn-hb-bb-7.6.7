/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.workflow2.form.reject.model;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;

public class FRStatusTable {
    protected static final String TABLE_NAME_PREFIX = "NR_WF_FRT_IST_";
    protected static final String COLUMN_OPT_FORM_ID = "OPT_FORM_ID";
    protected static final String COLUMN_OPT_STATUS = "OPT_STATUS";
    protected static final String COLUMN_UNIT_CODE = "MDCODE";
    protected static final String COLUMN_PERIOD_CODE = "DATATIME";
    protected static final String COLUMN_ADJUST_CODE = "MDCODE";
    protected FormSchemeDefine formSchemeDefine;

    public FRStatusTable(FormSchemeDefine formSchemeDefine) {
        this.formSchemeDefine = formSchemeDefine;
    }

    public FormSchemeDefine getFormSchemeDefine() {
        return this.formSchemeDefine;
    }

    public String getTableName() {
        return TABLE_NAME_PREFIX + this.formSchemeDefine.getFormSchemeCode();
    }

    public String getUnitColumnCode() {
        return "MDCODE";
    }

    public String getPeriodColumnCode() {
        return COLUMN_PERIOD_CODE;
    }

    public String getAdjustColumnCode() {
        return "MDCODE";
    }

    public String getFormIdColumnName() {
        return COLUMN_OPT_FORM_ID;
    }

    public String getStatusColumnName() {
        return COLUMN_OPT_STATUS;
    }
}

