/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.MD5Util
 */
package com.jiuqi.bde.base.intf;

import com.jiuqi.va.domain.common.MD5Util;

public class FetchResultDim {
    private String requestTaskId;
    private String formId;
    private String regionId;
    private Integer routeNum;
    private String requestRegionId;

    public FetchResultDim(String requestTaskId, String formId, String regionId, Integer routeNum) {
        this.requestTaskId = requestTaskId;
        this.formId = formId;
        this.regionId = regionId;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(requestTaskId).append(formId).append(regionId);
        this.requestRegionId = MD5Util.encrypt((String)stringBuilder.toString());
        this.routeNum = routeNum;
    }

    public String getRequestTaskId() {
        return this.requestTaskId;
    }

    public void setRequestTaskId(String requestTaskId) {
        this.requestTaskId = requestTaskId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public Integer getRouteNum() {
        return this.routeNum;
    }

    public void setRouteNum(Integer routeNum) {
        this.routeNum = routeNum;
    }

    public String getRequestRegionId() {
        return this.requestRegionId;
    }

    public void setRequestRegionId(String requestRegionId) {
        this.requestRegionId = requestRegionId;
    }

    public String toString() {
        return "ResultDimData{requestTaskId='" + this.requestTaskId + '\'' + ", formId='" + this.formId + '\'' + ", regionId='" + this.regionId + '\'' + ", routeNum=" + this.routeNum + ", RequestRegionId='" + this.requestRegionId + '\'' + '}';
    }
}

