/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf;

import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchExecuteSetting;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import java.util.List;

public class CustomFetchCondi {
    private FetchTaskContext fetchTaskContext;
    private CustomBizModelDTO bizModel;
    private List<CustomFetchExecuteSetting> fetchSettingList;
    private OrgMappingDTO orgMapping;

    public CustomFetchCondi(FetchTaskContext fetchTaskContext, CustomBizModelDTO bizModel, List<CustomFetchExecuteSetting> fetchSettingList, OrgMappingDTO orgMapping) {
        this.fetchTaskContext = fetchTaskContext;
        this.bizModel = bizModel;
        this.fetchSettingList = fetchSettingList;
        this.orgMapping = orgMapping;
    }

    public FetchTaskContext getFetchTaskContext() {
        return this.fetchTaskContext;
    }

    public void setFetchTaskContext(FetchTaskContext fetchTaskContext) {
        this.fetchTaskContext = fetchTaskContext;
    }

    public CustomBizModelDTO getBizModel() {
        return this.bizModel;
    }

    public void setBizModel(CustomBizModelDTO bizModel) {
        this.bizModel = bizModel;
    }

    public List<CustomFetchExecuteSetting> getFetchSettingList() {
        return this.fetchSettingList;
    }

    public void setFetchSettingList(List<CustomFetchExecuteSetting> fetchSettingList) {
        this.fetchSettingList = fetchSettingList;
    }

    public OrgMappingDTO getOrgMapping() {
        return this.orgMapping;
    }

    public void setOrgMapping(OrgMappingDTO orgMapping) {
        this.orgMapping = orgMapping;
    }
}

