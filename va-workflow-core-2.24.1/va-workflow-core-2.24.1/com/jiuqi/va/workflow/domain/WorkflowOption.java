/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.domain;

import java.math.BigDecimal;

public class WorkflowOption {

    public static enum SchemeType {
        ACTION,
        EDITABLEFIELD,
        EDITABLETABLE;

    }

    public static enum RelationType {
        OR,
        AND;

    }

    public static enum TopFlag {
        TOP(BigDecimal.ONE),
        NOTTOP(BigDecimal.ZERO);

        private BigDecimal topFlag;

        private TopFlag(BigDecimal topFlag) {
            this.topFlag = topFlag;
        }

        public BigDecimal getTopFlag() {
            return this.topFlag;
        }
    }

    public static enum DetectStatus {
        FAIL(1),
        SUCCESS(0);

        private int type;

        private DetectStatus(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }
    }

    public static enum WorkflowParamType {
        PUBLIC(1),
        CUSTOM(0);

        private Integer type;

        private WorkflowParamType(Integer type) {
            this.type = type;
        }

        public Integer getType() {
            return this.type;
        }
    }

    public static enum OrderType {
        ASC,
        DESC;

    }

    public static enum MoveType {
        UP,
        DOWN;

    }
}

