/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.ext.face.impl.form;

import com.jiuqi.nr.task.form.ext.face.IExtendInfo;
import com.jiuqi.nr.task.form.ext.face.impl.form.DefaultFormExt;
import com.jiuqi.nr.task.form.ext.face.impl.form.FormExtendInfo;
import org.springframework.stereotype.Component;

@Component
public class FixFormExt
extends DefaultFormExt {
    @Override
    public String getCode() {
        return "FORM_TYPE_FIX";
    }

    @Override
    public IExtendInfo extendConfig(String formSchemeKey) {
        FormExtendInfo formExtendInfo = new FormExtendInfo();
        boolean zbScheme = this.isZbScheme(formSchemeKey);
        formExtendInfo.setEnableCopyForm(!zbScheme);
        formExtendInfo.setEnableCopy(!zbScheme);
        formExtendInfo.setEnableSync(!zbScheme);
        return formExtendInfo;
    }
}

