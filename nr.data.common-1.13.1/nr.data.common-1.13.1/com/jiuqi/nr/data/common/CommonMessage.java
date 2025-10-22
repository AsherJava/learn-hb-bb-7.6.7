/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.data.common.AbstractMessage;
import com.jiuqi.nr.data.common.Message;
import com.jiuqi.nr.data.common.service.dto.ImportCancledResult;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.io.Serializable;
import java.util.List;

public class CommonMessage
extends AbstractMessage
implements Message<CommonMessage>,
Serializable {
    private static final long serialVersionUID = -691999387252439402L;
    @JsonIgnore
    private transient Object detail;
    private ImportCancledResult importCancledResult;

    public Object getDetail() {
        return this.detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public ImportCancledResult getImportCancledResult() {
        return this.importCancledResult;
    }

    public void setImportCancledResult(ImportCancledResult importCancledResult) {
        this.importCancledResult = importCancledResult;
    }

    public CommonMessage(List<String> forms, DimensionCollection dimensions, Object detail, List<String> successDW) {
        this.forms = forms;
        this.dimensions = dimensions;
        this.detail = detail;
        this.successDW = successDW;
    }

    public CommonMessage() {
    }

    @Override
    public CommonMessage getMessage() {
        return this;
    }
}

