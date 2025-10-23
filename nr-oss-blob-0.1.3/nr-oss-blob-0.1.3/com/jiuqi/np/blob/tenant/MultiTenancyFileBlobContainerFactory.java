/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.blob.tenant;

import com.jiuqi.np.blob.BlobContainer;
import com.jiuqi.np.blob.impl.FileBlobContainerFactory;
import com.jiuqi.np.blob.tenant.TenantIdResolver;
import java.io.File;

public class MultiTenancyFileBlobContainerFactory
extends FileBlobContainerFactory {
    private static final String SEPARATOR = File.separator;
    private final TenantIdResolver tenantIdResolver;

    public MultiTenancyFileBlobContainerFactory(TenantIdResolver tenantIdResolver) {
        this.tenantIdResolver = tenantIdResolver;
    }

    @Override
    public BlobContainer getBlobContainer(String name) {
        return super.getBlobContainer(String.format("%s%s%s", this.tenantIdResolver.getCurrentTenantId(), SEPARATOR, name));
    }
}

