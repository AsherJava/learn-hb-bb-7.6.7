/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.rewritesetting.consts;

public final class RewriteSettingConst {

    public static enum FieldMappingEnum {
        INSIDE("inside", "\u96c6\u56e2\u5185\u6d6e\u52a8\u884c"),
        OUTSIDE("outside", "\u96c6\u56e2\u5916\u6d6e\u52a8\u884c");

        private String code;
        private String title;

        private FieldMappingEnum(String code, String title) {
            this.code = code;
            this.title = title;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

