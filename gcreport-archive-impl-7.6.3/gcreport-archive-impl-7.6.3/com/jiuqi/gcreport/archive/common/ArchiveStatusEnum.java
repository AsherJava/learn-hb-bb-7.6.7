/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.archive.common;

public enum ArchiveStatusEnum {
    UPLOAD_SUCCESS(1),
    UPLOAD_FAILED(-1),
    SEND_SUCCESS(2),
    SEND_FAILED(-2),
    CANCEL_ARCHIVE(-3);

    private int status;

    private ArchiveStatusEnum(int status) {
        this.status = status;
    }

    public static ArchiveStatusEnum getEnum(Integer status) {
        if (status == null) {
            return null;
        }
        for (ArchiveStatusEnum statusEnum : ArchiveStatusEnum.values()) {
            if (statusEnum.getStatus() != status.intValue()) continue;
            return statusEnum;
        }
        return null;
    }

    public int getStatus() {
        return this.status;
    }
}

