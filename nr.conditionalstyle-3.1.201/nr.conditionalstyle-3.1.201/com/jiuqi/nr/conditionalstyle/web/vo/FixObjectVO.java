/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.conditionalstyle.web.vo;

import com.jiuqi.nr.conditionalstyle.web.vo.ConditionalStyleVO;
import com.jiuqi.nr.conditionalstyle.web.vo.RegionVO;
import java.util.ArrayList;
import java.util.List;

public class FixObjectVO {
    private RegionVO regionVO;
    private List<ConditionalStyleVO> list;

    public RegionVO getRegionVO() {
        return this.regionVO;
    }

    public void setRegionVO(RegionVO regionVO) {
        this.regionVO = regionVO;
    }

    public List<ConditionalStyleVO> getList() {
        if (this.list == null) {
            this.list = new ArrayList<ConditionalStyleVO>();
        }
        return this.list;
    }

    public void setList(List<ConditionalStyleVO> list) {
        this.list = list;
    }
}

