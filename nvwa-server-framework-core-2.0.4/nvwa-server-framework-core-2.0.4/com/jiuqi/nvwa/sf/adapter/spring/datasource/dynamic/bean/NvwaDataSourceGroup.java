/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean;

import java.util.Objects;

public class NvwaDataSourceGroup {
    public static final NvwaDataSourceGroup SYSTEM_DATASOURCE_GROUP = new NvwaDataSourceGroup("SYSTEM", "\u7cfb\u7edf\u6570\u636e\u6e90");
    private String dsGroupGuid;
    private String dsGroupTitle;

    public NvwaDataSourceGroup() {
    }

    public NvwaDataSourceGroup(String dsGroupGuid, String dsGroupTitle) {
        this.dsGroupGuid = dsGroupGuid;
        this.dsGroupTitle = dsGroupTitle;
    }

    public String getDsGroupGuid() {
        return this.dsGroupGuid;
    }

    public void setDsGroupGuid(String dsGroupGuid) {
        this.dsGroupGuid = dsGroupGuid;
    }

    public String getDsGroupTitle() {
        return this.dsGroupTitle;
    }

    public void setDsGroupTitle(String dsGroupTitle) {
        this.dsGroupTitle = dsGroupTitle;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        NvwaDataSourceGroup that = (NvwaDataSourceGroup)o;
        return Objects.equals(this.dsGroupGuid, that.dsGroupGuid) && Objects.equals(this.dsGroupTitle, that.dsGroupTitle);
    }

    public int hashCode() {
        return Objects.hash(this.dsGroupGuid, this.dsGroupTitle);
    }

    public String toString() {
        return "NvwaDataSourceGroup{dsGroupGuid='" + this.dsGroupGuid + '\'' + ", dsGroupTitle='" + this.dsGroupTitle + '\'' + '}';
    }
}

