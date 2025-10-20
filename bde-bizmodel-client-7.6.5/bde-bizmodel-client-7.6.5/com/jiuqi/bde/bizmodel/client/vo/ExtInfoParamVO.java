/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 */
package com.jiuqi.bde.bizmodel.client.vo;

import com.jiuqi.bde.bizmodel.client.vo.QueryFieldVO;
import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import java.util.List;
import java.util.Map;

public class ExtInfoParamVO {
    private Map<String, List<FixedAdaptSettingVO>> dataLinkFetchSetting;
    private List<QueryFieldVO> dimList;

    public Map<String, List<FixedAdaptSettingVO>> getDataLinkFetchSetting() {
        return this.dataLinkFetchSetting;
    }

    public void setDataLinkFetchSetting(Map<String, List<FixedAdaptSettingVO>> dataLinkFetchSetting) {
        this.dataLinkFetchSetting = dataLinkFetchSetting;
    }

    public List<QueryFieldVO> getDimList() {
        return this.dimList;
    }

    public void setDimList(List<QueryFieldVO> dimList) {
        this.dimList = dimList;
    }
}

