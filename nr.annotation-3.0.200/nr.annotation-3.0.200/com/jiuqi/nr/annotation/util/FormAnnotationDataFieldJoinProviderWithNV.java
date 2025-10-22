/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider
 *  com.jiuqi.nvwa.dataengine.intf.JoinType
 *  com.jiuqi.nvwa.dataengine.intf.SqlJoinItem
 *  com.jiuqi.nvwa.dataengine.intf.SqlJoinOneItem
 */
package com.jiuqi.nr.annotation.util;

import com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider;
import com.jiuqi.nvwa.dataengine.intf.JoinType;
import com.jiuqi.nvwa.dataengine.intf.SqlJoinItem;
import com.jiuqi.nvwa.dataengine.intf.SqlJoinOneItem;
import java.io.Serializable;

public class FormAnnotationDataFieldJoinProviderWithNV
implements Serializable,
ISqlJoinProvider {
    private static final long serialVersionUID = 1L;
    private String anFiledCode = "FMCEAN_ID";
    private String dataFiledCode = "FMCEANDF_FMCEAN_ID";
    private String formSchemeCode;

    public FormAnnotationDataFieldJoinProviderWithNV(String formSchemeCode) {
        this.formSchemeCode = formSchemeCode;
    }

    public SqlJoinItem getSqlJoinItem(String srcTable, String desTable) {
        String anTableCode = "SYS_FMCEAN_" + this.formSchemeCode;
        String dataTableCode = "SYS_FMCEANDF_" + this.formSchemeCode;
        if (anTableCode.equals(srcTable) && dataTableCode.equals(desTable)) {
            SqlJoinItem joinItem = new SqlJoinItem(srcTable, desTable);
            joinItem.setJoinType(JoinType.Left);
            SqlJoinOneItem recidItem = new SqlJoinOneItem(this.anFiledCode, this.dataFiledCode);
            joinItem.addJoinItem(recidItem);
            return joinItem;
        }
        return null;
    }

    public JoinType getDefaultJoinType() {
        return JoinType.Left;
    }
}

