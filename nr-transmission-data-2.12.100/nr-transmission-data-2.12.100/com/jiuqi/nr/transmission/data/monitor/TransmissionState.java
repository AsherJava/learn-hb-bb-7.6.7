/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.monitor;

import java.util.Arrays;
import java.util.Optional;

public enum TransmissionState {
    NONE(1, "\u672a\u540c\u6b65"),
    PROCESSING(2, "\u540c\u6b65\u4e2d"),
    FINISHED(3, "\u540c\u6b65\u6210\u529f"),
    ERROR(4, "\u540c\u6b65\u5931\u8d25"),
    WAITING(5, "\u7b49\u5f85\u88c5\u5165"),
    SOMESUCCESS(6, "\u90e8\u5206\u6210\u529f"),
    CANCELED(7, "\u53d6\u6d88"),
    CANCELING(8, "\u53d6\u6d88\u4e2d");

    private int value;
    private String title;

    private TransmissionState(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static TransmissionState getTaskStateByValue(int value) {
        Optional<TransmissionState> authority = Arrays.stream(TransmissionState.values()).filter(o -> o.getValue() == value).findFirst();
        return authority.orElse(NONE);
    }
}

