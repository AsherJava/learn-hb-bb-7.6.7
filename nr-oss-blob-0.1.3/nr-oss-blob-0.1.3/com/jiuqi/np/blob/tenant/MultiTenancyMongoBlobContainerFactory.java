/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.MongoClient
 */
package com.jiuqi.np.blob.tenant;

import com.jiuqi.np.blob.BlobContainer;
import com.jiuqi.np.blob.impl.MongoBlobContainerFactory;
import com.jiuqi.np.blob.tenant.TenantIdResolver;
import com.mongodb.MongoClient;

public class MultiTenancyMongoBlobContainerFactory
extends MongoBlobContainerFactory {
    private static final String PREFIX = "tenants";
    private TenantIdResolver tenantIdResolver;

    public MultiTenancyMongoBlobContainerFactory(String dataBase, MongoClient mongoClient, TenantIdResolver tenantIdResolver) {
        super(dataBase, mongoClient);
        this.tenantIdResolver = tenantIdResolver;
    }

    @Override
    public BlobContainer getBlobContainer(String name) {
        return super.getBlobContainer(String.format("%s_%s_%s", PREFIX, this.tenantIdResolver.getCurrentTenantId(), name));
    }
}

