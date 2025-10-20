/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.dto;

import java.util.List;

public class ClbrGenerateAttributeDTO {
    private List<ClbrGenerateAttribute> items;

    public List<ClbrGenerateAttribute> getItems() {
        return this.items;
    }

    public void setItems(List<ClbrGenerateAttribute> items) {
        this.items = items;
    }

    public static class ClbrGenerateAttribute {
        private String attributeName;
        private String attributeTitle;

        public ClbrGenerateAttribute() {
        }

        public ClbrGenerateAttribute(String attributeName, String attributeTitle) {
            this.attributeName = attributeName;
            this.attributeTitle = attributeTitle;
        }

        public String getAttributeName() {
            return this.attributeName;
        }

        public void setAttributeName(String attributeName) {
            this.attributeName = attributeName;
        }

        public String getAttributeTitle() {
            return this.attributeTitle;
        }

        public void setAttributeTitle(String attributeTitle) {
            this.attributeTitle = attributeTitle;
        }
    }
}

