/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.workbench.myanalysis.dataset.action.DataSetNewDataToolbarAction
 */
package com.jiuqi.nr.zbquery.workbench.myanalysis.dataset.action;

import com.jiuqi.nvwa.workbench.myanalysis.dataset.action.DataSetNewDataToolbarAction;

public class ZBQueryDSNewDataToolbarAction
extends DataSetNewDataToolbarAction {
    public static final String ID = "myanalysis_toolbar_dsnewdata_zbquery";

    public ZBQueryDSNewDataToolbarAction() {
        super("\u67e5\u8be2\u6570\u636e\u96c6", false);
    }

    public String getId() {
        return ID;
    }

    public String getIcon() {
        return "#icon-_GJZchakanshangbao";
    }

    public String postAction() {
        return "myanalysis_table_dsedit_zbquery";
    }
}

