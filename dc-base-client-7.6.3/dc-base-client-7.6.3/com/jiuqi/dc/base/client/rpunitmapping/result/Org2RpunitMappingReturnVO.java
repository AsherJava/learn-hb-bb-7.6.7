/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.client.rpunitmapping.result;

import com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingVO;
import java.util.List;

public class Org2RpunitMappingReturnVO {
    private List<Org2RpunitMappingVO> dataList;
    private Integer totalCount;

    public List<Org2RpunitMappingVO> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<Org2RpunitMappingVO> dataList) {
        this.dataList = dataList;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}

