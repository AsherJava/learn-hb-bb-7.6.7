/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.common;

public class CalibreDefineOption {

    public static class ShareWay {
        private Integer shareType;
        private String shareName;

        public Integer getShareType() {
            return this.shareType;
        }

        public void setShareType(Integer shareType) {
            this.shareType = shareType;
        }

        public String getShareName() {
            return this.shareName;
        }

        public void setShareName(String shareName) {
            this.shareName = shareName;
        }
    }

    public static class PageCondition {
        private Integer pageSize;
        private Integer pageIndex;

        public Integer getPageSize() {
            return this.pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getPageIndex() {
            return this.pageIndex;
        }

        public void setPageIndex(Integer pageIndex) {
            this.pageIndex = pageIndex;
        }
    }
}

