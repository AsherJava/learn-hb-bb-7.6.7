/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.blob.impl;

import com.jiuqi.np.blob.BlobContainer;
import com.jiuqi.np.blob.impl.BlobContainerFactory;
import com.jiuqi.np.blob.impl.BlobContainerFileImpl;

public class FileBlobContainerFactory
implements BlobContainerFactory {
    @Override
    public BlobContainer getBlobContainer(String name) {
        return new BlobContainerFileImpl(name);
    }
}

