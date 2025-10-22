/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.attachment.param;

import com.jiuqi.nr.data.attachment.param.FileFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileFilterImpl
implements FileFilter {
    private Map<String, List<String>> fieldsInForms = new HashMap<String, List<String>>();

    @Override
    public List<String> getFieldsInForm(String formKey) {
        return this.fieldsInForms.get(formKey);
    }

    public void addFormFilter(String formKey, List<String> fieldKeys) {
        this.fieldsInForms.put(formKey, fieldKeys);
    }
}

