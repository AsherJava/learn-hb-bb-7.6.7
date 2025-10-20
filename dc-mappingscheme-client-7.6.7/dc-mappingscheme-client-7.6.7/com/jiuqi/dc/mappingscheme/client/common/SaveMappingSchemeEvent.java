/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.common;

import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import org.springframework.context.ApplicationEvent;

public class SaveMappingSchemeEvent
extends ApplicationEvent {
    private static final long serialVersionUID = -8464910075005683323L;
    private DataSchemeDTO dto;

    public SaveMappingSchemeEvent(Object source, DataSchemeDTO dto) {
        super(source);
        this.dto = dto;
    }

    public DataSchemeDTO getDto() {
        return this.dto;
    }

    public void setDto(DataSchemeDTO dto) {
        this.dto = dto;
    }
}

