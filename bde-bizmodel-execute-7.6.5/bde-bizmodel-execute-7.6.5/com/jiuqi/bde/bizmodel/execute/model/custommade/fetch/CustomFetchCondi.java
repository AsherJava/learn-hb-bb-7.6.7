/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch;

import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.CustomFetchExecuteSettingVO;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import java.util.List;

@Deprecated
class CustomFetchCondi {
    private FetchTaskContext fetchTaskContext;
    private List<CustomFetchExecuteSettingVO> fetchSettingList;
    private OrgMappingDTO orgMapping;

    public CustomFetchCondi(FetchTaskContext fetchTaskContext, List<CustomFetchExecuteSettingVO> fetchSettingList, OrgMappingDTO orgMapping) {
        this.fetchTaskContext = fetchTaskContext;
        this.fetchSettingList = fetchSettingList;
        this.orgMapping = orgMapping;
    }

    public FetchTaskContext getFetchTaskContext() {
        return this.fetchTaskContext;
    }

    public void setFetchTaskContext(FetchTaskContext fetchTaskContext) {
        this.fetchTaskContext = fetchTaskContext;
    }

    public List<CustomFetchExecuteSettingVO> getFetchSettingList() {
        return this.fetchSettingList;
    }

    public void setFetchSettingList(List<CustomFetchExecuteSettingVO> fetchSettingList) {
        this.fetchSettingList = fetchSettingList;
    }

    public OrgMappingDTO getOrgMapping() {
        return this.orgMapping;
    }

    public void setOrgMapping(OrgMappingDTO orgMapping) {
        this.orgMapping = orgMapping;
    }
}

