/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.dto;

import java.util.List;

public class ClbrSchemeBatchQueryDTO {
    private List<ClbrSchemeQueryDTO> clbrSchemeQueryDTOs;

    public List<ClbrSchemeQueryDTO> getClbrSchemeQueryDTOs() {
        return this.clbrSchemeQueryDTOs;
    }

    public void setClbrSchemeQueryDTOs(List<ClbrSchemeQueryDTO> clbrSchemeQueryDTOs) {
        this.clbrSchemeQueryDTOs = clbrSchemeQueryDTOs;
    }

    public static class ClbrSchemeQueryDTO {
        private String key;
        private String clbrType;
        private String relation;
        private String oppRelation;

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getClbrType() {
            return this.clbrType;
        }

        public void setClbrType(String clbrType) {
            this.clbrType = clbrType;
        }

        public String getRelation() {
            return this.relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }

        public String getOppRelation() {
            return this.oppRelation;
        }

        public void setOppRelation(String oppRelation) {
            this.oppRelation = oppRelation;
        }
    }
}

