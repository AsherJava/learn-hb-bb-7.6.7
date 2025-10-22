/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBField
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBLink
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBTable
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDO
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import com.jiuqi.nr.definition.facade.FormFieldInfoDefine;

@DBAnno.DBTable(dbTable="NR_PARAM_FORM_FIELD_INFO")
@DBAnno.DBLink(linkWith=DataFieldDO.class, linkField="key", field="fieldKey")
public class FormFieldInfoDefineImpl
implements FormFieldInfoDefine {
    public static final String DB_TABLE = "NR_PARAM_FORM_FIELD_INFO";
    public static final String DB_FIELD_FIELDKEY = "FIELD_KEY";
    public static final String DB_FIELD_FORMKEY = "FORM_KEY";
    public static final String DB_FIELD_TASKKEY = "TASK_KEY";
    @DBAnno.DBField(dbField="FIELD_KEY")
    private String fieldKey;
    @DBAnno.DBField(dbField="FORM_KEY")
    private String formKey;
    @DBAnno.DBField(dbField="TASK_KEY")
    private String taskKey;
    private String formSchemeKey;

    @Override
    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    @Override
    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.fieldKey == null ? 0 : this.fieldKey.hashCode());
        result = 31 * result + (this.formKey == null ? 0 : this.formKey.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        FormFieldInfoDefineImpl other = (FormFieldInfoDefineImpl)obj;
        if (this.fieldKey == null ? other.fieldKey != null : !this.fieldKey.equals(other.fieldKey)) {
            return false;
        }
        return !(this.formKey == null ? other.formKey != null : !this.formKey.equals(other.formKey));
    }
}

