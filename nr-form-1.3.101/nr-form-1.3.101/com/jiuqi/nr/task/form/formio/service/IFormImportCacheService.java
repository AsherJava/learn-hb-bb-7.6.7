/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.progress.ProgressItem
 */
package com.jiuqi.nr.task.form.formio.service;

import com.jiuqi.np.definition.progress.ProgressItem;

public interface IFormImportCacheService {
    public ProgressItem getProgress(String var1);

    public void setProgress(ProgressItem var1);

    public void removeProgress(String var1);
}

