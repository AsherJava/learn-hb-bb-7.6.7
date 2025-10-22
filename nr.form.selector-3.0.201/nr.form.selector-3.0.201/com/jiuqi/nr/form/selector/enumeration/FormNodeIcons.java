/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormType
 */
package com.jiuqi.nr.form.selector.enumeration;

import com.jiuqi.nr.definition.common.FormType;

public enum FormNodeIcons {
    GUDINGBIAO(FormType.FORM_TYPE_FIX, "#icon-_GJZshuxingzhongji"),
    FUDONGBIAO(FormType.FORM_TYPE_FLOAT, "#icon-16_SHU_A_NR_fudongbiao1"),
    TAIZHANGBIAO(FormType.FORM_TYPE_ACCOUNT, "#icon-16_SHU_A_NR_taizhangbiao"),
    CHAXUNBIAO(FormType.FORM_TYPE_QUERY, "#icon-16_SHU_A_NR_chaxunbiao"),
    WENJUAN(FormType.FORM_TYPE_SURVEY, "#icon-16_SHU_A_NR_wenjuan"),
    FENGMIANDAIMABIAO(FormType.FORM_TYPE_NEWFMDM, "#icon-16_SHU_A_NR_fengmiandaimabiao");

    public FormType formType;
    public String iconKey;

    private FormNodeIcons(FormType formType, String iconKey) {
        this.formType = formType;
        this.iconKey = iconKey;
    }

    public static String getIconKey(FormType formType) {
        if (formType != null) {
            for (FormNodeIcons e : FormNodeIcons.values()) {
                if (formType.getValue() != e.formType.getValue()) continue;
                return e.iconKey;
            }
        }
        return null;
    }
}

