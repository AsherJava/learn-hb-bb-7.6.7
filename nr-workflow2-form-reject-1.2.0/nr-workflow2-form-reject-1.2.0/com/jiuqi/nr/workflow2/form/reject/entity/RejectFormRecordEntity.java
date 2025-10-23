/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 */
package com.jiuqi.nr.workflow2.form.reject.entity;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.form.reject.entity.IRejectFormRecordEntity;
import com.jiuqi.nr.workflow2.form.reject.enumeration.FormRejectStatus;

public class RejectFormRecordEntity
implements IRejectFormRecordEntity {
    private IFormObject formObject;
    private FormRejectStatus status;

    public void setFormObject(IFormObject formObject) {
        this.formObject = formObject;
    }

    @Override
    public IFormObject getFormObject() {
        return this.formObject;
    }

    @Override
    public FormRejectStatus getStatus() {
        return this.status;
    }

    public void setStatus(FormRejectStatus status) {
        this.status = status;
    }
}

