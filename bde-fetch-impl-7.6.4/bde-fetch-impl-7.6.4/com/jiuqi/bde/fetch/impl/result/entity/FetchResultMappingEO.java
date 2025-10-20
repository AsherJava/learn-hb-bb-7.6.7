/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.fetch.impl.result.entity;

import com.jiuqi.bde.fetch.impl.result.enums.FetchResultTableStatusEnum;

public class FetchResultMappingEO {
    Integer routeNum;
    FetchResultTableStatusEnum routeStatus;

    public FetchResultMappingEO() {
    }

    public FetchResultMappingEO(Integer routeNum, FetchResultTableStatusEnum routeStatus) {
        this.routeNum = routeNum;
        this.routeStatus = routeStatus;
    }

    public Integer getRouteNum() {
        return this.routeNum;
    }

    public void setRouteNum(Integer routeNum) {
        this.routeNum = routeNum;
    }

    public FetchResultTableStatusEnum getRouteStatus() {
        return this.routeStatus;
    }

    public void setRouteStatus(Integer routeStatus) {
        this.routeStatus = FetchResultTableStatusEnum.getEnumByStatus(routeStatus);
    }
}

