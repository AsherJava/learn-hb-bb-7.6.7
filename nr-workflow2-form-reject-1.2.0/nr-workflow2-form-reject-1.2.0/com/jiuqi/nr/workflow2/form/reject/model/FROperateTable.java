/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.workflow2.form.reject.model;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.form.reject.model.FRStatusTable;

public class FROperateTable
extends FRStatusTable {
    private static final String TABLE_NAME_PREFIX = "NR_WF_FRT_OPT_";
    private static final String COLUMN_OPT_ID = "OPT_ID";
    private static final String COLUMN_OPT_USER = "OPT_USER";
    private static final String COLUMN_OPT_TIME = "OPT_TIME";
    private static final String COLUMN_OPT_COMMENT = "OPT_COMMENT";

    public FROperateTable(FormSchemeDefine formSchemeDefine) {
        super(formSchemeDefine);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME_PREFIX + this.formSchemeDefine.getFormSchemeCode();
    }

    public String getOptIdColumnName() {
        return COLUMN_OPT_ID;
    }

    public String getOptUserColumnName() {
        return COLUMN_OPT_USER;
    }

    public String getOptTimeColumnName() {
        return COLUMN_OPT_TIME;
    }

    public String getOptCommentColumnName() {
        return COLUMN_OPT_COMMENT;
    }
}

