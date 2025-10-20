/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.conversionsystem.vo;

import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionLogInfoVo;
import java.util.List;

public class ConversionLogInfoPageVo {
    private Integer pageSize;
    private Integer pageNum;
    private long count;
    private List<ConversionLogInfoVo> conversionLogInfoVos;

    public ConversionLogInfoPageVo(Integer pageSize, Integer pageNum, long count, List<ConversionLogInfoVo> conversionLogInfoVos) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.count = count;
        this.conversionLogInfoVos = conversionLogInfoVos;
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

    public List<ConversionLogInfoVo> getConversionLogInfoVos() {
        return this.conversionLogInfoVos;
    }

    public void setConversionLogInfoVos(List<ConversionLogInfoVo> conversionLogInfoVos) {
        this.conversionLogInfoVos = conversionLogInfoVos;
    }
}

