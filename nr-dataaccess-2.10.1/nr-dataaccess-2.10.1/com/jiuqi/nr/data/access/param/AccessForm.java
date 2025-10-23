/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.data.access.param.IAccessForm;
import java.util.Objects;

public class AccessForm
implements IAccessForm {
    private final String formKey;
    private final String taskKey;
    private final String formSchemeKey;
    private Integer hashCode;

    public AccessForm(String formKey, String taskKey, String formSchemeKey) {
        this.formKey = formKey;
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
    }

    @Override
    public String getFormKey() {
        return this.formKey;
    }

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        AccessForm that = (AccessForm)o;
        return Objects.equals(this.formKey, that.formKey) && Objects.equals(this.taskKey, that.taskKey) && Objects.equals(this.formSchemeKey, that.formSchemeKey);
    }

    public int hashCode() {
        if (this.hashCode == null) {
            this.hashCode = Objects.hash(this.formKey, this.taskKey, this.formSchemeKey);
        }
        return this.hashCode;
    }

    public String toString() {
        return this.formKey;
    }
}

