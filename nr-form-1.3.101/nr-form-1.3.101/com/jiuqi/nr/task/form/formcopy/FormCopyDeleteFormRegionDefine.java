/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 */
package com.jiuqi.nr.task.form.formcopy;

import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import java.util.ArrayList;
import java.util.List;

public class FormCopyDeleteFormRegionDefine {
    private String key;
    private String code;
    private String formKey;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public FormCopyDeleteFormRegionDefine() {
    }

    public FormCopyDeleteFormRegionDefine(DesignDataRegionDefine designDataRegionDefine) {
        this.key = designDataRegionDefine.getKey();
        this.code = designDataRegionDefine.getCode();
        this.formKey = designDataRegionDefine.getFormKey();
    }

    public static List<FormCopyDeleteFormRegionDefine> valueOfs(List<DesignDataRegionDefine> designDataRegionDefines) {
        ArrayList<FormCopyDeleteFormRegionDefine> result = new ArrayList<FormCopyDeleteFormRegionDefine>(designDataRegionDefines.size());
        for (DesignDataRegionDefine designDataRegionDefine : designDataRegionDefines) {
            FormCopyDeleteFormRegionDefine formCopyDeleteFormRegionDefine = new FormCopyDeleteFormRegionDefine(designDataRegionDefine);
            result.add(formCopyDeleteFormRegionDefine);
        }
        return result;
    }
}

