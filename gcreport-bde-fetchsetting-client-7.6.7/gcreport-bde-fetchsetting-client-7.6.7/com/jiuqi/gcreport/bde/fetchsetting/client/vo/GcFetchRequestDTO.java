/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestContextDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestFloatSettingDTO
 */
package com.jiuqi.gcreport.bde.fetchsetting.client.vo;

import com.jiuqi.bde.common.dto.fetch.request.FetchRequestContextDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestFloatSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.GcFetchRequestFixedSettingDTO;
import java.util.List;
import java.util.Map;

public class GcFetchRequestDTO {
    private String requestInstcId;
    private String requestTaskId;
    private String requestSourceType;
    private FetchRequestContextDTO fetchContext;
    private FetchRequestFloatSettingDTO floatSetting;
    private List<GcFetchRequestFixedSettingDTO> fixedSetting;
    private List<Map<String, Object>> filters;
    private Map<String, Object> extInfo;
    private boolean standaloneServer;
    private String appName;

    public GcFetchRequestDTO() {
    }

    public GcFetchRequestDTO(String requestInstcId, String requestSourceType) {
        this.requestInstcId = requestInstcId;
        this.requestSourceType = requestSourceType;
    }

    public GcFetchRequestDTO(String requestInstcId, String requestTaskId, String requestSourceType) {
        this.requestInstcId = requestInstcId;
        this.requestTaskId = requestTaskId;
        this.requestSourceType = requestSourceType;
    }

    public String getRequestSourceType() {
        return this.requestSourceType;
    }

    public void setRequestSourceType(String requestSourceType) {
        this.requestSourceType = requestSourceType;
    }

    public String getRequestInstcId() {
        return this.requestInstcId;
    }

    public void setRequestInstcId(String requestInstcId) {
        this.requestInstcId = requestInstcId;
    }

    public String getRequestTaskId() {
        return this.requestTaskId;
    }

    public void setRequestTaskId(String requestTaskId) {
        this.requestTaskId = requestTaskId;
    }

    public FetchRequestContextDTO getFetchContext() {
        return this.fetchContext;
    }

    public void setFetchContext(FetchRequestContextDTO fetchContext) {
        this.fetchContext = fetchContext;
    }

    public FetchRequestFloatSettingDTO getFloatSetting() {
        return this.floatSetting;
    }

    public void setFloatSetting(FetchRequestFloatSettingDTO floatSetting) {
        this.floatSetting = floatSetting;
    }

    public List<GcFetchRequestFixedSettingDTO> getFixedSetting() {
        return this.fixedSetting;
    }

    public void setFixedSetting(List<GcFetchRequestFixedSettingDTO> fixedSetting) {
        this.fixedSetting = fixedSetting;
    }

    public List<Map<String, Object>> getFilters() {
        return this.filters;
    }

    public void setFilters(List<Map<String, Object>> filters) {
        this.filters = filters;
    }

    public Map<String, Object> getExtInfo() {
        return this.extInfo;
    }

    public void setExtInfo(Map<String, Object> extInfo) {
        this.extInfo = extInfo;
    }

    public boolean isStandaloneServer() {
        return this.standaloneServer;
    }

    public void setStandaloneServer(boolean standaloneServer) {
        this.standaloneServer = standaloneServer;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}

