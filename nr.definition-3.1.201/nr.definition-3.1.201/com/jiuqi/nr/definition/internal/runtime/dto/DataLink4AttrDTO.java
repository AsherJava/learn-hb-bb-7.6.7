/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.dto;

import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeBigDataTable;
import com.jiuqi.nr.definition.internal.runtime.dto.DataLinkDTO;

public class DataLink4AttrDTO
extends DataLinkDTO {
    private final RunTimeBigDataTable dataLinkAttr;

    public DataLink4AttrDTO(DataLinkDefine dataLinkDefine, RunTimeBigDataTable dataLinkAttr) {
        super(dataLinkDefine);
        this.dataLinkAttr = dataLinkAttr;
    }

    public RunTimeBigDataTable getDataLinkAttr() {
        return this.dataLinkAttr;
    }
}

