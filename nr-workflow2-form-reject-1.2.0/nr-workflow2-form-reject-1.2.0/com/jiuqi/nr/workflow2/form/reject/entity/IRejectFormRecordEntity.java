/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 */
package com.jiuqi.nr.workflow2.form.reject.entity;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.form.reject.enumeration.FormRejectStatus;

public interface IRejectFormRecordEntity {
    public IFormObject getFormObject();

    public FormRejectStatus getStatus();
}

