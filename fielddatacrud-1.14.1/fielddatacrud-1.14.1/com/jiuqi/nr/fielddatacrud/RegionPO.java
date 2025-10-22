/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fielddatacrud;

import java.util.Objects;

public class RegionPO {
    private String regionKey;
    private String formKey;
    private String taskKey;
    private String formSchemeKey;

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        RegionPO regionKey1 = (RegionPO)o;
        return Objects.equals(this.regionKey, regionKey1.regionKey) && Objects.equals(this.formKey, regionKey1.formKey);
    }

    public int hashCode() {
        int result = Objects.hashCode(this.regionKey);
        result = 31 * result + Objects.hashCode(this.formKey);
        return result;
    }

    public String toString() {
        return "RegionPO{regionKey='" + this.regionKey + '\'' + ", formKey='" + this.formKey + '\'' + ", taskKey='" + this.taskKey + '\'' + ", formSchemeKey='" + this.formSchemeKey + '\'' + '}';
    }
}

