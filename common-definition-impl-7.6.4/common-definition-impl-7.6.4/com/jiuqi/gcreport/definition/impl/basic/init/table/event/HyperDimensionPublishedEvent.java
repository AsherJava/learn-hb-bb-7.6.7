/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.event;

import com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO;
import org.springframework.context.ApplicationEvent;

public class HyperDimensionPublishedEvent
extends ApplicationEvent {
    private ShowModelDTO model;
    private static final long serialVersionUID = 5744698096137807039L;

    public HyperDimensionPublishedEvent(Object source, ShowModelDTO model) {
        super(source);
        this.model = model;
    }

    public ShowModelDTO getModel() {
        return this.model;
    }
}

