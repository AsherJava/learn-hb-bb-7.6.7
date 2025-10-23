/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.file.config;

import com.jiuqi.nr.file.config.FileAreaProperties;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value="nr.file")
public class FileServiceProperties {
    protected static final String PROPERTIES_PREFIX = "nr.file";
    private List<FileAreaProperties> areas = new ArrayList<FileAreaProperties>();

    public List<FileAreaProperties> getAreas() {
        return this.areas;
    }

    public void setAreas(List<FileAreaProperties> areas) {
        this.areas = areas;
    }
}

