/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.common;

import com.jiuqi.nr.multcheck2.provider.FailedOrgInfo;
import com.jiuqi.nr.multcheck2.service.dto.DimSetDTO;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import com.jiuqi.nr.multcheck2.web.vo.ReportDimVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class SchemeExecuteResult {
    List<String> successList = new ArrayList<String>();
    Map<String, List<String>> successWithExplainMap = new HashMap<String, List<String>>();
    Map<String, Map<String, FailedOrgInfo>> failedMap = new HashMap<String, Map<String, FailedOrgInfo>>();
    Map<String, List<String>> ignoreMap = new HashMap<String, List<String>>();
    List<MCLabel> unboundList = new ArrayList<MCLabel>();
    private Map<String, DimSetDTO> OrgDims;
    private ReportDimVO reportDim;
    private Map<String, Map<String, DimSetDTO>> itemsOrgDims;

    public List<String> getSuccessList() {
        return this.successList;
    }

    public void setSuccessList(List<String> successList) {
        this.successList = successList;
    }

    public void successListAdd(String org) {
        if (CollectionUtils.isEmpty(this.successList)) {
            this.successList = new ArrayList<String>();
        }
        if (!this.successList.contains(org)) {
            this.successList.add(org);
        }
    }

    public Map<String, Map<String, FailedOrgInfo>> getFailedMap() {
        return this.failedMap;
    }

    public void setFailedMap(Map<String, Map<String, FailedOrgInfo>> failedMap) {
        this.failedMap = failedMap;
    }

    public void failedMapAdd(String org, String itemKey, FailedOrgInfo failedOrgInfo) {
        if (CollectionUtils.isEmpty(this.failedMap)) {
            this.failedMap = new HashMap<String, Map<String, FailedOrgInfo>>();
        }
        if (!this.failedMap.containsKey(org)) {
            this.failedMap.put(org, new HashMap());
        }
        this.failedMap.get(org).put(itemKey, failedOrgInfo);
    }

    public Map<String, List<String>> getIgnoreMap() {
        return this.ignoreMap;
    }

    public void setIgnoreMap(Map<String, List<String>> ignoreMap) {
        this.ignoreMap = ignoreMap;
    }

    public void ignoreMapAdd(String org, String item) {
        if (CollectionUtils.isEmpty(this.ignoreMap)) {
            this.ignoreMap = new HashMap<String, List<String>>();
        }
        if (!this.ignoreMap.containsKey(org)) {
            this.ignoreMap.put(org, new ArrayList());
        }
        this.ignoreMap.get(org).add(item);
    }

    public Map<String, List<String>> getSuccessWithExplainMap() {
        return this.successWithExplainMap;
    }

    public void setSuccessWithExplainMap(Map<String, List<String>> successWithExplainMap) {
        this.successWithExplainMap = successWithExplainMap;
    }

    public void successWithExplainMapAdd(String org, String item) {
        if (CollectionUtils.isEmpty(this.successWithExplainMap)) {
            this.successWithExplainMap = new HashMap<String, List<String>>();
        }
        if (!this.successWithExplainMap.containsKey(org)) {
            this.successWithExplainMap.put(org, new ArrayList());
        }
        this.successWithExplainMap.get(org).add(item);
    }

    public List<MCLabel> getUnboundList() {
        return this.unboundList;
    }

    public void setUnboundList(List<MCLabel> unboundList) {
        this.unboundList = unboundList;
    }

    public ReportDimVO getReportDim() {
        return this.reportDim;
    }

    public void setReportDim(ReportDimVO reportDim) {
        this.reportDim = reportDim;
    }

    public Map<String, DimSetDTO> getOrgDims() {
        return this.OrgDims;
    }

    public void setOrgDims(Map<String, DimSetDTO> orgDims) {
        this.OrgDims = orgDims;
    }

    public Map<String, Map<String, DimSetDTO>> getItemsOrgDims() {
        return this.itemsOrgDims;
    }

    public void setItemsOrgDims(Map<String, Map<String, DimSetDTO>> itemsOrgDims) {
        this.itemsOrgDims = itemsOrgDims;
    }
}

