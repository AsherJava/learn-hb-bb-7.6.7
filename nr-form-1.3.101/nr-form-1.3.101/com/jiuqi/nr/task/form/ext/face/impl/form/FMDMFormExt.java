/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormType
 */
package com.jiuqi.nr.task.form.ext.face.impl.form;

import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.task.form.ext.face.IExtendInfo;
import com.jiuqi.nr.task.form.ext.face.impl.form.DefaultFormExt;
import com.jiuqi.nr.task.form.ext.face.impl.form.FormExtendInfo;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FMDMFormExt
extends DefaultFormExt {
    @Override
    public String getCode() {
        return "FORM_TYPE_NEWFMDM";
    }

    @Override
    public IExtendInfo extendConfig(String formSchemeKey) {
        boolean zbScheme;
        FormExtendInfo formExtendInfo = new FormExtendInfo();
        List designFormDefines = this.designTimeViewController.listFormByFormScheme(formSchemeKey);
        if (designFormDefines != null) {
            boolean anyMatch = designFormDefines.stream().anyMatch(define -> define.getFormType() == FormType.FORM_TYPE_NEWFMDM);
            formExtendInfo.setEnableAdd(!anyMatch);
        }
        formExtendInfo.setEnableCopyForm(!(zbScheme = this.isZbScheme(formSchemeKey)));
        formExtendInfo.setEnableCopy(!zbScheme);
        formExtendInfo.setEnableSync(!zbScheme);
        return formExtendInfo;
    }
}

