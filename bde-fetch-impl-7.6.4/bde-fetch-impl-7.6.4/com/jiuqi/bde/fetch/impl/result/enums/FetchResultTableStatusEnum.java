/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.fetch.impl.result.enums;

public enum FetchResultTableStatusEnum {
    AVAIABLE(1),
    LOCK(0),
    STOP(-1);

    private final Integer status;

    private FetchResultTableStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }

    public static FetchResultTableStatusEnum getEnumByStatus(Integer status) {
        for (FetchResultTableStatusEnum fetchResultTableEnum : FetchResultTableStatusEnum.values()) {
            if (!fetchResultTableEnum.getStatus().equals(status)) continue;
            return fetchResultTableEnum;
        }
        return null;
    }
}

