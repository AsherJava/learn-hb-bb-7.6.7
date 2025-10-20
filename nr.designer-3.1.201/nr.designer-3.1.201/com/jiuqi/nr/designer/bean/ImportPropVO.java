/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImportPropVO
implements Serializable {
    private static final long serialVersionUID = 3135086476753429881L;
    private String tableKey;
    private List<TitleMapping> titleMapping = new ArrayList<TitleMapping>();
    private Boolean increment;
    private String viewKey;

    public ImportPropVO() {
    }

    public ImportPropVO(String tableKey, List<TitleMapping> titleMapping, Boolean increment, String viewKey) {
        this.tableKey = tableKey;
        this.titleMapping = titleMapping;
        this.increment = increment;
        this.viewKey = viewKey;
    }

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public List<TitleMapping> getTitleMapping() {
        return this.titleMapping;
    }

    public void setTitleMapping(List<TitleMapping> titleMapping) {
        this.titleMapping = titleMapping;
    }

    public Boolean getIncrement() {
        return this.increment;
    }

    public void setIncrement(Boolean increment) {
        this.increment = increment;
    }

    public String getViewKey() {
        return this.viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }

    public String toString() {
        return "ImportPropVO [tableKey=" + this.tableKey + ", titleMapping=" + this.titleMapping + ", increment=" + this.increment + "]";
    }

    public static class TitleMapping
    implements Serializable {
        private static final long serialVersionUID = -7412120575935134002L;
        private int index;
        private String code;
        private String title;

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
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

        public TitleMapping() {
        }

        public TitleMapping(int index, String code, String title) {
            this.index = index;
            this.code = code;
            this.title = title;
        }

        public String toString() {
            return "titleMapping [index=" + this.index + ", code=" + this.code + ", title=" + this.title + "]";
        }
    }
}

