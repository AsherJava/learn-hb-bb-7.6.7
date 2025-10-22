/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO;
import java.util.List;

public class GcOffSetVchrPageVo {
    private Integer pageSize;
    private Integer pageNum;
    private long count;
    private List<GcOffSetVchrItemVO> itemVOs;

    public GcOffSetVchrPageVo() {
    }

    public GcOffSetVchrPageVo(Integer pageSize, Integer pageNum, long count, List<GcOffSetVchrItemVO> itemVOs) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.count = count;
        this.itemVOs = itemVOs;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public long getCount() {
        return this.count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<GcOffSetVchrItemVO> getItemVOs() {
        return this.itemVOs;
    }

    public void setItemVOs(List<GcOffSetVchrItemVO> itemVOs) {
        this.itemVOs = itemVOs;
    }
}

