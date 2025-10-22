/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.efdcdatacheck.client.vo;

import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckReportLogVO;
import java.util.List;

public class EfdcCheckReportPageVO {
    private Integer pageSize;
    private Integer pageNum;
    private long count;
    private List<EfdcCheckReportLogVO> itemVOs;

    public EfdcCheckReportPageVO(Integer pageSize, Integer pageNum, long count, List<EfdcCheckReportLogVO> itemVOs) {
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

    public List<EfdcCheckReportLogVO> getItemVOs() {
        return this.itemVOs;
    }

    public void setItemVOs(List<EfdcCheckReportLogVO> itemVOs) {
        this.itemVOs = itemVOs;
    }
}

