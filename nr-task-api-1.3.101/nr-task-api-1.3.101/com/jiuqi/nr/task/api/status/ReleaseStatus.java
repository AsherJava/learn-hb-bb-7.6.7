/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.status;

import java.util.HashMap;
import java.util.Map;

public enum ReleaseStatus {
    NEVER_PUBLISH("never_publish"),
    PUBLISH_OVERDUE("publish_overdue"),
    SCHEDULED_PUBLISH("scheduled_publish"),
    SCHEDULED_OVERDUE("scheduled_overdue"),
    PUBLISHING("publishing"),
    PROTECTING("protecting"),
    PROTECT_END("protect_end"),
    PUBLISH_SUCCESS("publish_success"),
    PUBLISH_FAIL("publish_fail"),
    PUBLISH_WARRING("publish_warring"),
    SCHEDULED_CANCEL("scheduled_cancel"),
    ROLL_BACK_SUCCESS("roll_back_success"),
    PUBLISH_INTERRUPT("publish_interrupt"),
    PARAM_LOCKING("param_locking");

    private static final Map<String, ReleaseStatus> statusMap;
    private String status;

    private ReleaseStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return this.status;
    }

    public static ReleaseStatus getInstance(String status) {
        return statusMap.get(status);
    }

    static {
        statusMap = new HashMap<String, ReleaseStatus>(13);
        for (ReleaseStatus releaseStatus : ReleaseStatus.values()) {
            statusMap.put(releaseStatus.toString(), releaseStatus);
        }
    }
}

