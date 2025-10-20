/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.CompressUtil
 */
package com.jiuqi.bde.common.dto.fetch.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestContextDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestFixedSettingDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestFloatSettingDTO;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.CompressUtil;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class FetchRequestDTO {
    private String requestRunnerId;
    private String requestInstcId;
    private String requestTaskId;
    private String requestSourceType;
    private FetchRequestContextDTO fetchContext;
    private FetchRequestFloatSettingDTO floatSetting;
    private List<FetchRequestFixedSettingDTO> fixedSetting;
    private boolean fixedSettingCompress;
    private String fixedSettingStr;
    private List<Map<String, Object>> filters;
    private Map<String, Object> extInfo;
    private Integer routeNum;
    private OrgMappingDTO orgMapping;

    public FetchRequestDTO() {
    }

    public FetchRequestDTO(String requestRunnerId, String requestInstcId, String requestTaskId, String requestSourceType) {
        this.requestRunnerId = requestRunnerId;
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

    public String getRequestRunnerId() {
        return this.requestRunnerId;
    }

    public void setRequestRunnerId(String requestRunnerId) {
        this.requestRunnerId = requestRunnerId;
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

    public List<FetchRequestFixedSettingDTO> getFixedSetting() {
        if (StringUtils.isEmpty((String)this.fixedSettingStr)) {
            return CollectionUtils.newArrayList();
        }
        if (!CollectionUtils.isEmpty(this.fixedSetting)) {
            return this.fixedSetting;
        }
        String fixedSettingStr = this.fixedSettingCompress ? CompressUtil.deCompress((String)this.fixedSettingStr) : this.fixedSettingStr;
        this.fixedSetting = (List)JsonUtils.readValue((String)fixedSettingStr, (TypeReference)new TypeReference<List<FetchRequestFixedSettingDTO>>(){});
        return this.fixedSetting;
    }

    public void setFixedSetting(List<FetchRequestFixedSettingDTO> fixedSetting) {
        if (CollectionUtils.isEmpty(fixedSetting)) {
            return;
        }
        String fixedSettingStr = JsonUtils.writeValueAsString(fixedSetting);
        if (StringUtils.isEmpty((String)fixedSettingStr)) {
            return;
        }
        this.fixedSettingCompress = CompressUtil.enableCompress((String)fixedSettingStr);
        this.fixedSettingStr = this.fixedSettingCompress ? CompressUtil.compress((String)fixedSettingStr) : fixedSettingStr;
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

    public Integer getRouteNum() {
        return this.routeNum;
    }

    public void setRouteNum(Integer routeNum) {
        this.routeNum = routeNum;
    }

    public OrgMappingDTO getOrgMapping() {
        return this.orgMapping;
    }

    public void setOrgMapping(OrgMappingDTO orgMapping) {
        this.orgMapping = orgMapping;
    }

    public String toString() {
        return "FetchRequestDTO [requestRunnerId=" + this.requestRunnerId + ", requestInstcId=" + this.requestInstcId + ", requestTaskId=" + this.requestTaskId + ", requestSourceType=" + this.requestSourceType + ", fetchContext=" + this.fetchContext + ", floatSetting=" + this.floatSetting + ", fixedSetting=" + this.fixedSetting + ", fixedSettingCompress=" + this.fixedSettingCompress + ", fixedSettingStr=" + this.fixedSettingStr + ", filters=" + this.filters + ", extInfo=" + this.extInfo + ", routeNum=" + this.routeNum + "]";
    }
}

