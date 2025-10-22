/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.web.bean;

import com.jiuqi.nr.examine.web.bean.DataSchemeInfo;
import java.util.List;

public class DataCleanParamInfo {
    boolean admin;
    List<DataSchemeInfo> dataSchemeInfos;

    public DataCleanParamInfo() {
    }

    public DataCleanParamInfo(List<DataSchemeInfo> schemeInfos, boolean isAdmin) {
        this.admin = isAdmin;
        this.dataSchemeInfos = schemeInfos;
    }

    public boolean getAdmin() {
        return this.admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<DataSchemeInfo> getDataSchemeInfos() {
        return this.dataSchemeInfos;
    }

    public void setDataSchemeInfos(List<DataSchemeInfo> dataSchemeInfos) {
        this.dataSchemeInfos = dataSchemeInfos;
    }
}

