/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.dto;

import com.jiuqi.bde.common.dto.FetchSettingVO;
import com.jiuqi.bde.common.dto.FetchTaskDTO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import java.util.List;
import java.util.Map;

public class SyncFetchTaskDTO
extends FetchTaskDTO {
    private List<FetchSettingVO> fetchSettingList;
    private FloatRegionConfigVO floatRegionConfigVO;
    private List<Map<String, String>> filters;

    public List<FetchSettingVO> getFetchSettingList() {
        return this.fetchSettingList;
    }

    public void setFetchSettingList(List<FetchSettingVO> fetchSettingList) {
        this.fetchSettingList = fetchSettingList;
    }

    public FloatRegionConfigVO getFloatRegionConfigVO() {
        return this.floatRegionConfigVO;
    }

    public void setFloatRegionConfigVO(FloatRegionConfigVO floatRegionConfigVO) {
        this.floatRegionConfigVO = floatRegionConfigVO;
    }

    public List<Map<String, String>> getFilters() {
        return this.filters;
    }

    public void setFilters(List<Map<String, String>> filters) {
        this.filters = filters;
    }

    @Override
    public String toString() {
        return super.toString() + "SyncFetchTaskDTO{fetchSettingList=" + this.fetchSettingList + '}';
    }
}

