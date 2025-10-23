/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.np.blob.impl;

import com.jiuqi.np.blob.BlobContainer;
import com.jiuqi.np.blob.impl.BlobContainerDbImpl;
import com.jiuqi.np.blob.impl.BlobContainerFactory;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.jdbc.core.JdbcTemplate;

public class DbBlobContainerFactory
implements BlobContainerFactory {
    private final JdbcTemplate jdbcTemplate;
    private final ConcurrentHashMap<String, BlobContainerDbImpl> containers = new ConcurrentHashMap();

    public DbBlobContainerFactory(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BlobContainer getBlobContainer(String name) {
        if (null == name) {
            throw new IllegalArgumentException("DbBlobContainer name must not be null.");
        }
        BlobContainerDbImpl container = this.containers.get(name);
        if (container != null) {
            return container;
        }
        BlobContainerDbImpl newContainer = new BlobContainerDbImpl(name, this.jdbcTemplate);
        container = this.containers.putIfAbsent(name, newContainer);
        if (container != null) {
            return container;
        }
        return newContainer;
    }
}

