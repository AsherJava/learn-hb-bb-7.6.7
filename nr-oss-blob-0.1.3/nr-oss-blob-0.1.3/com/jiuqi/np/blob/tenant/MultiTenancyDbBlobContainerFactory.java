/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.np.blob.tenant;

import com.jiuqi.np.blob.BlobContainer;
import com.jiuqi.np.blob.impl.BlobContainerDbImpl;
import com.jiuqi.np.blob.impl.BlobContainerFactory;
import com.jiuqi.np.blob.tenant.TenantIdResolver;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.jdbc.core.JdbcTemplate;

public class MultiTenancyDbBlobContainerFactory
implements BlobContainerFactory {
    private final JdbcTemplate jdbcTemplate;
    private final TenantIdResolver tenantIdResolver;
    private final ConcurrentHashMap<String, BlobContainerDbImpl> containers = new ConcurrentHashMap();

    public MultiTenancyDbBlobContainerFactory(JdbcTemplate jdbcTemplate, TenantIdResolver tenantIdResolver) {
        this.jdbcTemplate = jdbcTemplate;
        this.tenantIdResolver = tenantIdResolver;
    }

    @Override
    public BlobContainer getBlobContainer(String name) {
        if (null == name) {
            throw new IllegalArgumentException("DbBlobContainer name must not be null.");
        }
        String containerKey = String.format("%s_%s", this.tenantIdResolver.getCurrentTenantId(), name);
        BlobContainerDbImpl container = this.containers.get(containerKey);
        if (container != null) {
            return container;
        }
        BlobContainerDbImpl newContainer = new BlobContainerDbImpl(name, this.jdbcTemplate);
        container = this.containers.putIfAbsent(containerKey, newContainer);
        if (container != null) {
            return container;
        }
        return newContainer;
    }
}

