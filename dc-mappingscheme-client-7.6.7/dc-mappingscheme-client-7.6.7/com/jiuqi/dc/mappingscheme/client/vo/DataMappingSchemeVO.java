/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.vo;

import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.vo.DataMappingVO;

public class DataMappingSchemeVO
extends DataSchemeDTO {
    private static final long serialVersionUID = 2384947656171782944L;
    private DataMappingVO dataMapping;

    @Override
    public DataMappingVO getDataMapping() {
        return this.dataMapping;
    }

    @Override
    public void setDataMapping(DataMappingVO dataMapping) {
        this.dataMapping = dataMapping;
    }
}

