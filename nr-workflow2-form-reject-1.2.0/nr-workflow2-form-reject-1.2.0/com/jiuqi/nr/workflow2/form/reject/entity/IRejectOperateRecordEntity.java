/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 */
package com.jiuqi.nr.workflow2.form.reject.entity;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import java.util.Date;

public interface IRejectOperateRecordEntity {
    public IFormObject getFormObject();

    public String getOptId();

    public String getOptUser();

    public Date getOptTime();

    public String getOptComment();
}

