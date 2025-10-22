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

public class FormAnnotationDataFieldCommentJoinProvider
implements Serializable,
ISqlJoinProvider {
    private static final long serialVersionUID = 1L;
    private String anFiledCode;
    private String comFiledCode;
    private String dataLinkCode;
    private String formSchemeCode;

    public FormAnnotationDataFieldCommentJoinProvider(String anFiledCode, String dataLinkCode, String comFiledCode, String formSchemeCode) {
        this.dataLinkCode = dataLinkCode;
        this.anFiledCode = anFiledCode;
        this.comFiledCode = comFiledCode;
        this.formSchemeCode = formSchemeCode;
    }

    public SqlJoinItem getSqlJoinItem(String srcTable, String desTable) {
        String anTableCode = "SYS_FMCEAN_" + this.formSchemeCode;
        String dataLinkTable = "SYS_FMCEANDF_" + this.formSchemeCode;
        String comTableCode = "SYS_FMCEANCO_" + this.formSchemeCode;
        if (anTableCode.equals(srcTable) && dataLinkTable.equals(desTable) || anTableCode.equals(srcTable) && comTableCode.equals(desTable)) {
            SqlJoinItem joinItem = new SqlJoinItem(srcTable, desTable);
            String temp = "";
            if (dataLinkTable.equals(desTable)) {
                temp = this.dataLinkCode;
                joinItem.setJoinType(JoinType.LEFT);
            } else {
                temp = this.comFiledCode;
                joinItem.setJoinType(JoinType.LEFT);
            }
            SqlJoinOneItem recidItem = new SqlJoinOneItem(this.anFiledCode, temp);
            joinItem.addJoinItem(recidItem);
            return joinItem;
        }
        return null;
    }
}

