/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 */
package com.jiuqi.dc.mappingscheme.impl.event;

import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import org.springframework.context.ApplicationEvent;

public class BaseDataRefDefineDelEvent
extends ApplicationEvent {
    private static final long serialVersionUID = -3363833114148772472L;
    private DataMappingDefineDTO define;

    public BaseDataRefDefineDelEvent(Object source, DataMappingDefineDTO define) {
        super(source);
        this.define = define;
    }

    public DataMappingDefineDTO getDefine() {
        return this.define;
    }
}

