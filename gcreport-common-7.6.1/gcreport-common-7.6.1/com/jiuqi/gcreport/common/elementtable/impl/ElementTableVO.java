/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common.elementtable.impl;

import com.jiuqi.gcreport.common.elementtable.impl.ElementTableDataVO;
import com.jiuqi.gcreport.common.elementtable.impl.ElementTableSpanVO;
import com.jiuqi.gcreport.common.elementtable.impl.ElementTableTitleVO;
import java.util.List;
import java.util.Map;

public class ElementTableVO {
    private List<ElementTableTitleVO> titles;
    private Integer total;
    private List<? extends ElementTableDataVO> datas;
    private Map<String, ElementTableSpanVO> spanData;
    private Map<String, Object> params;

    public ElementTableVO(List<ElementTableTitleVO> titles, List<? extends ElementTableDataVO> datas) {
        this.setTitles(titles);
        this.setDatas(datas);
    }

    public ElementTableVO(List<ElementTableTitleVO> titles, Integer total, List<? extends ElementTableDataVO> datas) {
        this.setTitles(titles);
        this.setTotal(total);
        this.setDatas(datas);
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public List<? extends ElementTableDataVO> getDatas() {
        return this.datas;
    }

    public List<ElementTableTitleVO> getTitles() {
        return this.titles;
    }

    public Map<String, ElementTableSpanVO> getSpanData() {
        return this.spanData;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setDatas(List<? extends ElementTableDataVO> datas) {
        this.datas = datas;
    }

    public void setTitles(List<ElementTableTitleVO> titles) {
        this.titles = titles;
    }

    public void setSpanData(Map<String, ElementTableSpanVO> spanData) {
        this.spanData = spanData;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}

