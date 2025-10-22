/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.dataentry.bean.FormLockParam;

@Deprecated
public interface IFormLockSyncService {
    public void syncLockForm(FormLockParam var1);

    public void syncBatchLockForm(FormLockParam var1);
}

