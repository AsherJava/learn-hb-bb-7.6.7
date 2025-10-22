/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 */
package com.jiuqi.gcreport.bde.fetchsetting.client.vo;

import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import java.util.List;

public class FetchFixedAndFloatSettingVO {
    private List<FetchSettingVO> fetchSettingList;
    private List<FloatRegionConfigVO> floatRegionConfigList;

    public List<FetchSettingVO> getFetchSettingList() {
        return this.fetchSettingList;
    }

    public void setFetchSettingList(List<FetchSettingVO> fetchSettingList) {
        this.fetchSettingList = fetchSettingList;
    }

    public List<FloatRegionConfigVO> getFloatRegionConfigList() {
        return this.floatRegionConfigList;
    }

    public void setFloatRegionConfigList(List<FloatRegionConfigVO> floatRegionConfigList) {
        this.floatRegionConfigList = floatRegionConfigList;
    }
}

