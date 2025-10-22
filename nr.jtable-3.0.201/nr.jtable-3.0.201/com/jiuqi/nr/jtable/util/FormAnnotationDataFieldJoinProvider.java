/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.setting.ISqlJoinProvider
 *  com.jiuqi.np.dataengine.setting.JoinType
 *  com.jiuqi.np.dataengine.setting.SqlJoinItem
 *  com.jiuqi.np.dataengine.setting.SqlJoinOneItem
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.np.dataengine.setting.ISqlJoinProvider;
import com.jiuqi.np.dataengine.setting.JoinType;
import com.jiuqi.np.dataengine.setting.SqlJoinItem;
import com.jiuqi.np.dataengine.setting.SqlJoinOneItem;
import java.io.Serializable;

public class FormAnnotationDataFieldJoinProvider
implements Serializable,
ISqlJoinProvider {
    private static final long serialVersionUID = 1L;
    private String anFiledCode;
    private String dataFiledCode;
    private String formSchemeCode;

    public FormAnnotationDataFieldJoinProvider(String anFiledCode, String dataFiledCode, String formSchemeCode) {
        this.anFiledCode = anFiledCode;
        this.dataFiledCode = dataFiledCode;
        this.formSchemeCode = formSchemeCode;
    }

    public SqlJoinItem getSqlJoinItem(String srcTable, String desTable) {
        String anTableCode = "SYS_FMCEAN_" + this.formSchemeCode;
        String dataTableCode = "SYS_FMCEANDF_" + this.formSchemeCode;
        if (anTableCode.equals(srcTable) && dataTableCode.equals(desTable)) {
            SqlJoinItem joinItem = new SqlJoinItem(srcTable, desTable);
            joinItem.setJoinType(JoinType.LEFT);
            SqlJoinOneItem recidItem = new SqlJoinOneItem(this.anFiledCode, this.dataFiledCode);
            joinItem.addJoinItem(recidItem);
            return joinItem;
        }
        return null;
    }
}

