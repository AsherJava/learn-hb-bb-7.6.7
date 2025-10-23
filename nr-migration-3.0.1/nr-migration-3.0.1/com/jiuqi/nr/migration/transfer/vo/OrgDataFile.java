/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transfer.vo;

import com.jiuqi.nr.migration.transfer.vo.OrgVersion;
import java.util.List;

public class OrgDataFile {
    private String Key;
    private String fileName;
    private String orgType;
    private List<OrgVersion> versions;
    private int unitCount = -1;

    public String getKey() {
        return this.Key;
    }

    public void setKey(String key) {
        this.Key = key;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public List<OrgVersion> getVersions() {
        return this.versions;
    }

    public void setVersions(List<OrgVersion> versions) {
        this.versions = versions;
    }

    public int getUnitCount() {
        return this.unitCount;
    }

    public void setUnitCount(int unitCount) {
        this.unitCount = unitCount;
    }
}

