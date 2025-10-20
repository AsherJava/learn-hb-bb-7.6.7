/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.planpublish.common;

public enum PublishStatus {
    NEVER_PUBLISH("never_publish"),
    PUBLISH_OVERDUE("publish_overdue"),
    BRFORE_PUBLISH("before_publish"),
    PUBLISHING("publishing"),
    PROTECTING("protecting"),
    PROTECT_END("protect_end"),
    PUBLISH_SUCCESS("publish_success"),
    PUBLISH_FAIL("publish_fail"),
    PUBLISH_WARRING("publish_warring"),
    PUBLISH_CANCEL("publish_cancel"),
    ROLL_BACK_SUCCESS("roll_back_success"),
    PUBLISH_INTERRUPT("publish_interrupt"),
    PARAM_LOCKING("param_locking");

    private String status;

    private PublishStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return this.status;
    }
}

