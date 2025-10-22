/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.io.params.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.io.params.output.Datablocks;
import com.jiuqi.nr.io.params.output.ExportEntity;
import java.util.List;

public class ExportJsonData {
    private String formCode;
    @JsonProperty
    private List<Datablocks> datablocks;
    @JsonProperty
    private List<ExportEntity> entitys;

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public List<Datablocks> getDatablocks() {
        return this.datablocks;
    }

    public void setDatablocks(List<Datablocks> datablocks) {
        this.datablocks = datablocks;
    }

    public List<ExportEntity> getEntitys() {
        return this.entitys;
    }

    public void setEntitys(List<ExportEntity> entitys) {
        this.entitys = entitys;
    }
}

