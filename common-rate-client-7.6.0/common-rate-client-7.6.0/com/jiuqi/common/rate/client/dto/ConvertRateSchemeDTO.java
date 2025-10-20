/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.rate.client.dto;

import com.jiuqi.common.rate.client.vo.ConvertRateSchemeVO;
import java.util.List;

public class ConvertRateSchemeDTO {
    private List<ConvertRateSchemeVO> saveDatas;
    private List<ConvertRateSchemeVO> deleteDatas;
    private Integer page;
    private Integer pageSize;

    public List<ConvertRateSchemeVO> getSaveDatas() {
        return this.saveDatas;
    }

    public void setSaveDatas(List<ConvertRateSchemeVO> saveDatas) {
        this.saveDatas = saveDatas;
    }

    public List<ConvertRateSchemeVO> getDeleteDatas() {
        return this.deleteDatas;
    }

    public void setDeleteDatas(List<ConvertRateSchemeVO> deleteDatas) {
        this.deleteDatas = deleteDatas;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

