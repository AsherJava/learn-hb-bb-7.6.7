/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.paramsync.domain;

import com.jiuqi.va.paramsync.domain.VaParamSyncMetaDataDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncMetaGroupDO;
import java.io.Serializable;
import java.util.List;

public class VaParamSyncMainfestDO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<VaParamSyncMetaDataDO> metaDatas;
    private List<VaParamSyncMetaGroupDO> groups;

    public List<VaParamSyncMetaDataDO> getMetaDatas() {
        return this.metaDatas;
    }

    public void setMetaDatas(List<VaParamSyncMetaDataDO> metaDatas) {
        this.metaDatas = metaDatas;
    }

    public List<VaParamSyncMetaGroupDO> getGroups() {
        return this.groups;
    }

    public void setGroups(List<VaParamSyncMetaGroupDO> groups) {
        this.groups = groups;
    }
}

