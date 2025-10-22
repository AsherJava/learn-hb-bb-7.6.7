/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.service.impl;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="jiuqi.system")
public class QueryFileSystemServices {
    protected static final String PROPERTIES_PREFIX = "jiuqi.system";
    private String fileService;

    public String getFileService() {
        return this.fileService;
    }

    public void setFileService(String fileService) {
        this.fileService = fileService;
    }
}

