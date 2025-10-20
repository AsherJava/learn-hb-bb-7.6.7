/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

public interface ParamDeployEnum {

    public static enum ParamStatus {
        DEFAULT(0, "\u9ed8\u8ba4\u72b6\u6001"),
        MAINTENANCE(1, "\u7ef4\u62a4\u72b6\u6001"),
        READONLY(2, "\u53ea\u8bfb\u72b6\u6001"),
        LOCKED(4, "\u9501\u5b9a\u72b6\u6001");

        private final int value;
        private final String title;

        private ParamStatus(int value, String title) {
            this.value = value;
            this.title = title;
        }

        public int getValue() {
            return this.value;
        }

        public String getTitle() {
            return this.title;
        }

        public static ParamStatus valueOf(int value) {
            for (ParamStatus type : ParamStatus.values()) {
                if (type.getValue() != value) continue;
                return type;
            }
            return null;
        }
    }

    public static enum DeployStatus {
        NOT_DEPLOYED(0, "\u672a\u53d1\u5e03"),
        SUCCESS(1, "\u53d1\u5e03\u6210\u529f"),
        WARNING(2, "\u53d1\u5e03\u8b66\u544a"),
        FAIL(4, "\u53d1\u5e03\u5931\u8d25"),
        DEPLOY(8, "\u53d1\u5e03\u4e2d");

        private final int value;
        private final String title;

        private DeployStatus(int value, String title) {
            this.value = value;
            this.title = title;
        }

        public int getValue() {
            return this.value;
        }

        public String getTitle() {
            return this.title;
        }

        public static DeployStatus valueOf(int value) {
            for (DeployStatus type : DeployStatus.values()) {
                if (type.getValue() != value) continue;
                return type;
            }
            return null;
        }
    }
}

