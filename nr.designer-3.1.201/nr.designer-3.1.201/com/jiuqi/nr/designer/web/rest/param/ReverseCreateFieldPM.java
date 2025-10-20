/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.nr.designer.web.rest.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

public class ReverseCreateFieldPM {
    private String dataSchemeKey;
    private String schemePrefix;
    private String formCode;
    private Integer formType;
    private String formTitle;
    @JsonIgnore
    private int currentFieldNum;
    private List<Region> regions;

    public int getCurrentFieldNum() {
        return this.currentFieldNum;
    }

    public void setCurrentFieldNum(int currentFieldNum) {
        this.currentFieldNum = currentFieldNum;
    }

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

    public List<Region> getRegions() {
        return this.regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public Integer getFormType() {
        return this.formType;
    }

    public void setFormType(Integer formType) {
        this.formType = formType;
    }

    public static class Region {
        private String tableKey;
        private String tableCode;
        private String regionKey;
        private int regionTop;
        private int fieldNum;
        private int fieldKind;
        private int fieldType;
        private List<String> fieldCodes;

        public String getTableKey() {
            return this.tableKey;
        }

        public void setTableKey(String tableKey) {
            this.tableKey = tableKey;
        }

        public String getTableCode() {
            return this.tableCode;
        }

        public void setTableCode(String tableCode) {
            this.tableCode = tableCode;
        }

        public String getRegionKey() {
            return this.regionKey;
        }

        public void setRegionKey(String regionKey) {
            this.regionKey = regionKey;
        }

        public int getRegionTop() {
            return this.regionTop;
        }

        public void setRegionTop(int regionTop) {
            this.regionTop = regionTop;
        }

        public int getFieldNum() {
            return this.fieldNum;
        }

        public void setFieldNum(int fieldNum) {
            this.fieldNum = fieldNum;
        }

        public int getFieldKind() {
            return this.fieldKind;
        }

        public void setFieldKind(int fieldKind) {
            this.fieldKind = fieldKind;
        }

        public int getFieldType() {
            return this.fieldType;
        }

        public void setFieldType(int fieldType) {
            this.fieldType = fieldType;
        }

        public List<String> getFieldCodes() {
            return this.fieldCodes;
        }

        public void setFieldCodes(List<String> fieldCodes) {
            this.fieldCodes = fieldCodes;
        }
    }
}

