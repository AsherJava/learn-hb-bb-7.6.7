/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.metadeploy.MetaDataDeployDim
 */
package com.jiuqi.va.bizmeta.domain.metadeploy;

import com.jiuqi.va.domain.metadeploy.MetaDataDeployDim;
import java.util.List;
import java.util.Map;

public class MetaDataDeployDTO {
    private String info;
    List<MetaDataDeployDim> deployDatas;
    private List<String> successData;
    private List<Map<String, Object>> failedData;

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<MetaDataDeployDim> getDeployDatas() {
        return this.deployDatas;
    }

    public void setDeployDatas(List<MetaDataDeployDim> deployDatas) {
        this.deployDatas = deployDatas;
    }

    public List<String> getSuccessData() {
        return this.successData;
    }

    public void setSuccessData(List<String> successData) {
        this.successData = successData;
    }

    public List<Map<String, Object>> getFailedData() {
        return this.failedData;
    }

    public void setFailedData(List<Map<String, Object>> failedData) {
        this.failedData = failedData;
    }
}

