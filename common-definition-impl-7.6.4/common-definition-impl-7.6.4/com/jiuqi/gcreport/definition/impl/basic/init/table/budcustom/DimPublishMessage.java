/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.budcustom;

import com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO;

public class DimPublishMessage {
    private String id;
    private ShowModelDTO showModelDTO;

    public DimPublishMessage(String id, ShowModelDTO showModelDTO) {
        this.id = id;
        this.showModelDTO = showModelDTO;
    }

    public DimPublishMessage() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ShowModelDTO getShowModelDTO() {
        return this.showModelDTO;
    }

    public void setShowModelDTO(ShowModelDTO showModelDTO) {
        this.showModelDTO = showModelDTO;
    }
}

