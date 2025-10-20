/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FetchSettingVO
 */
package com.jiuqi.bde.bizmodel.client.vo;

import com.jiuqi.bde.bizmodel.client.vo.QueryFieldVO;
import com.jiuqi.bde.common.dto.FetchSettingVO;
import java.util.List;

public class ListExtInfoParam {
    private List<FetchSettingVO> fetchSettingVOList;
    private List<QueryFieldVO> dimList;

    public List<FetchSettingVO> getFetchSettingVOList() {
        return this.fetchSettingVOList;
    }

    public void setFetchSettingVOList(List<FetchSettingVO> fetchSettingVOList) {
        this.fetchSettingVOList = fetchSettingVOList;
    }

    public List<QueryFieldVO> getDimList() {
        return this.dimList;
    }

    public void setDimList(List<QueryFieldVO> dimList) {
        this.dimList = dimList;
    }
}

