/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nr.definition.common.DataRegionKind
 */
package com.jiuqi.nr.designer.web.rest.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.designer.common.ReverseItemState;
import com.jiuqi.nr.designer.util.DataRegionKindDeserialize;
import com.jiuqi.nr.designer.util.DataRegionKindSerialize;
import com.jiuqi.nr.designer.util.IReverseState;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ReverseFieldsPM {
    private String formSchemeKey;
    private String formKey;
    private String rule;
    private List<Region> regions;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getRule() {
        return this.rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public List<Region> getRegions() {
        return this.regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public static class Field
    implements IReverseState {
        private int posX;
        private int posY;
        private int decimal;
        private int dataFieldType;
        private int precision;
        private String key;
        private String code;
        private ReverseItemState state;

        public int getPosX() {
            return this.posX;
        }

        public void setPosX(int posX) {
            this.posX = posX;
        }

        public int getPosY() {
            return this.posY;
        }

        public void setPosY(int posY) {
            this.posY = posY;
        }

        public int getDecimal() {
            return this.decimal;
        }

        public void setDecimal(int decimal) {
            this.decimal = decimal;
        }

        public int getDataFieldType() {
            return this.dataFieldType;
        }

        public void setDataFieldType(int dataFieldType) {
            this.dataFieldType = dataFieldType;
        }

        public int getPrecision() {
            return this.precision;
        }

        public void setPrecision(int precision) {
            this.precision = precision;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public ReverseItemState getState() {
            return this.state;
        }

        @Override
        public void setState(ReverseItemState state) {
            this.state = state;
        }
    }

    public static class Region {
        private String regionKey;
        @JsonDeserialize(using=DataRegionKindDeserialize.class)
        @JsonSerialize(using=DataRegionKindSerialize.class)
        private DataRegionKind regionKind;
        private List<Table> tables;

        public String getRegionKey() {
            return this.regionKey;
        }

        public void setRegionKey(String regionKey) {
            this.regionKey = regionKey;
        }

        public DataRegionKind getRegionKind() {
            return this.regionKind;
        }

        public void setRegionKind(DataRegionKind regionKind) {
            this.regionKind = regionKind;
        }

        public List<Table> getTables() {
            return this.tables;
        }

        public void setTables(List<Table> tables) {
            this.tables = tables;
        }
    }

    public static class Table
    implements IReverseState {
        private String tableKey;
        private ReverseItemState state;
        private String tableCode;
        private String tableTitle;
        private List<Field> fields;

        public String getTableKey() {
            return this.tableKey;
        }

        public void setTableKey(String tableKey) {
            this.tableKey = tableKey;
        }

        @Override
        public ReverseItemState getState() {
            return this.state;
        }

        @Override
        public void setState(ReverseItemState state) {
            this.state = state;
        }

        public String getTableCode() {
            return this.tableCode;
        }

        public void setTableCode(String tableCode) {
            this.tableCode = tableCode;
        }

        public String getTableTitle() {
            return this.tableTitle;
        }

        public void setTableTitle(String tableTitle) {
            this.tableTitle = tableTitle;
        }

        public List<Field> getFields() {
            return this.fields;
        }

        public void setFields(List<Field> fields) {
            this.fields = fields;
        }
    }
}

