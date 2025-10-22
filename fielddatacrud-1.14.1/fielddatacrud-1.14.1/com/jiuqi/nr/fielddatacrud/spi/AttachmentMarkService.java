/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fielddatacrud.spi;

import java.util.Set;

public interface AttachmentMarkService {
    public void batchMarkDeletion(String var1, Set<String> var2);

    default public void batchMarkPictureDeletion(String formSchemeKey, Set<String> groupKeys) {
    }
}

