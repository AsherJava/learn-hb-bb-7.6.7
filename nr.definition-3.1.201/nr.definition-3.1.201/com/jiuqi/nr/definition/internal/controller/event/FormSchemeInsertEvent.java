/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.controller.event;

import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import org.springframework.context.ApplicationEvent;

public class FormSchemeInsertEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private DesignFormSchemeDefine formScheme;

    public FormSchemeInsertEvent(DesignFormSchemeDefine formScheme) {
        super(formScheme);
        this.formScheme = formScheme;
    }

    public DesignFormSchemeDefine getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(DesignFormSchemeDefine formScheme) {
        this.formScheme = formScheme;
    }
}

