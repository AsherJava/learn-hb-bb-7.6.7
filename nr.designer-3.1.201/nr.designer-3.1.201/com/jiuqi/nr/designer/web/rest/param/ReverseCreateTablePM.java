/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.param;

import java.util.List;

public class ReverseCreateTablePM {
    private String dataSchemeKey;
    private String schemePrefix;
    private String formCode;
    private String formTitle;
    private int formType;
    private List<Region> regions;
    private List<String> tableCodes;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getSchemePrefix() {
        return this.schemePrefix;
    }

    public void setSchemePrefix(String schemePrefix) {
        this.schemePrefix = schemePrefix;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public int getFormType() {
        return this.formType;
    }

    public void setFormType(int formType) {
        this.formType = formType;
    }

    public List<Region> getRegions() {
        return this.regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public List<String> getTableCodes() {
        return this.tableCodes;
    }

    public void setTableCodes(List<String> tableCodes) {
        this.tableCodes = tableCodes;
    }

    public static class Region {
        private int regionKind;
        private String regionKey;
        private String regionTop;
        private String regionLeft;

        public int getRegionKind() {
            return this.regionKind;
        }

        public void setRegionKind(int regionKind) {
            this.regionKind = regionKind;
        }

        public String getRegionKey() {
            return this.regionKey;
        }

        public void setRegionKey(String regionKey) {
            this.regionKey = regionKey;
        }

        public String getRegionTop() {
            return this.regionTop;
        }

        public void setRegionTop(String regionTop) {
            this.regionTop = regionTop;
        }

        public String getRegionLeft() {
            return this.regionLeft;
        }

        public void setRegionLeft(String regionLeft) {
            this.regionLeft = regionLeft;
        }
    }
}

