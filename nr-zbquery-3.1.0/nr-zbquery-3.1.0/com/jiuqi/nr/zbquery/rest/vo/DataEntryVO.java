/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.rest.vo;

import com.jiuqi.nr.zbquery.model.NullRowDisplayMode;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.rest.vo.DimensionVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataEntryVO {
    List<DimensionVO> dimensionVOList;
    List<String> zbIdList;
    List<String> linkIdList;
    HashMap<String, String[]> innerDimMap = new HashMap();
    String taskKey;
    String queryType;
    private NullRowDisplayMode nullRowDisplayMode = NullRowDisplayMode.DISPLAY_ALLNULL;
    String params;
    Map<String, String> paramMap = new HashMap<String, String>();
    List<QueryDimension> queryDimensions = new ArrayList<QueryDimension>();
    private String FMDMSelected = "";

    public List<DimensionVO> getDimensionVOList() {
        return this.dimensionVOList;
    }

    public void setDimensionVOList(List<DimensionVO> dimensionVOList) {
        this.dimensionVOList = dimensionVOList;
    }

    public List<String> getZbIdList() {
        return this.zbIdList;
    }

    public void setZbIdList(List<String> zbIdList) {
        this.zbIdList = zbIdList;
    }

    public List<String> getLinkIdList() {
        return this.linkIdList;
    }

    public void setLinkIdList(List<String> linkIdList) {
        this.linkIdList = linkIdList;
    }

    public HashMap<String, String[]> getInnerDimMap() {
        return this.innerDimMap;
    }

    public void setInnerDimMap(HashMap<String, String[]> innerDimMap) {
        this.innerDimMap = innerDimMap;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getQueryType() {
        return this.queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public NullRowDisplayMode getNullRowDisplayMode() {
        return this.nullRowDisplayMode;
    }

    public void setNullRowDisplayMode(NullRowDisplayMode nullRowDisplayMode) {
        this.nullRowDisplayMode = nullRowDisplayMode;
    }

    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Map<String, String> getParamMap() {
        return this.paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public List<QueryDimension> getQueryDimensions() {
        return this.queryDimensions;
    }

    public void addQueryDimensions(QueryDimension queryDimension) {
        this.queryDimensions.add(queryDimension);
    }

    public String getFMDMSelected() {
        return this.FMDMSelected;
    }

    public void setFMDMSelected(String FMDMSelected) {
        this.FMDMSelected = FMDMSelected;
    }
}

