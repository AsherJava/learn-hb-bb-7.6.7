/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.blob.impl;

import com.jiuqi.np.blob.BlobContainer;
import com.jiuqi.np.blob.BlobContainerProvider;
import com.jiuqi.np.blob.impl.BlobContainerFactory;

public class BlobContainerProviderImpl
implements BlobContainerProvider {
    private final BlobContainerFactory blobContainerFactory;

    public BlobContainerProviderImpl(BlobContainerFactory blobContainerFactory) {
        this.blobContainerFactory = blobContainerFactory;
    }

    @Override
    public BlobContainer getContainer(String name) {
        return this.blobContainerFactory.getBlobContainer(name);
    }
}

