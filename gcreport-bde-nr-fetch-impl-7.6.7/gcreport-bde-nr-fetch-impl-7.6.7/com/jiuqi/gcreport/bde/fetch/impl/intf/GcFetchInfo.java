/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 */
package com.jiuqi.gcreport.bde.fetch.impl.intf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class GcFetchInfo {
    private EfdcInfo efdcInfo;
    private String userName;
    private String rpUnitType;

    public GcFetchInfo() {
    }

    public GcFetchInfo(EfdcInfo efdcInfo, String userName, String rpUnitType) {
        this.efdcInfo = efdcInfo;
        this.userName = userName;
        this.rpUnitType = rpUnitType;
    }

    public EfdcInfo getEfdcInfo() {
        return this.efdcInfo;
    }

    public void setEfdcInfo(EfdcInfo efdcInfo) {
        this.efdcInfo = efdcInfo;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRpUnitType() {
        return this.rpUnitType;
    }

    public void setRpUnitType(String rpUnitType) {
        this.rpUnitType = rpUnitType;
    }

    public String toString() {
        return "GcFetchInfo [efdcInfo=" + this.efdcInfo + ", userName=" + this.userName + ", rpUnitType=" + this.rpUnitType + "]";
    }
}

