/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.vo;

import java.util.List;

public class CalibreBatchBuildVO {
    private String calibreCode;
    private String entityId;
    private List<String> selectedEntity;
    private String selectedCalibre;

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public List<String> getSelectedEntity() {
        return this.selectedEntity;
    }

    public void setSelectedEntity(List<String> selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    public String getSelectedCalibre() {
        return this.selectedCalibre;
    }

    public void setSelectedCalibre(String selectedCalibre) {
        this.selectedCalibre = selectedCalibre;
    }

    public String getCalibreCode() {
        return this.calibreCode;
    }

    public void setCalibreCode(String calibreCode) {
        this.calibreCode = calibreCode;
    }
}

