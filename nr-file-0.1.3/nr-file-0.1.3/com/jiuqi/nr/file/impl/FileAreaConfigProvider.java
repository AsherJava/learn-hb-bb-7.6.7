/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.file.impl;

import com.jiuqi.nr.file.FileAreaConfig;
import com.jiuqi.nr.file.FileAreaConfigSwitch;
import com.jiuqi.nr.file.config.FileAreaProperties;
import com.jiuqi.nr.file.config.FileServiceProperties;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FileAreaConfigProvider {
    @Autowired
    private FileServiceProperties fileServiceProperties;
    @Autowired(required=false)
    private List<FileAreaConfig> buildinFileAreaConfigs;

    public FileAreaConfig getFileAreaConfig(String areaName) {
        return new FileAreaConfigSwitch(this.internalGetFileAreaConfig(areaName));
    }

    private FileAreaConfig internalGetFileAreaConfig(String areaName) {
        if (StringUtils.isEmpty(areaName)) {
            throw new IllegalArgumentException("file area name must not be empty.");
        }
        FileAreaProperties fileAreaProperties = this.getFileAreaProperties(areaName);
        if (fileAreaProperties != null) {
            return fileAreaProperties;
        }
        FileAreaConfig fileAreaConfig = this.getBuildinConfig(areaName);
        if (fileAreaConfig != null) {
            return fileAreaConfig;
        }
        return this.createDefaultFileAreaConfig(areaName);
    }

    private FileAreaProperties getFileAreaProperties(String areaName) {
        for (FileAreaProperties fileAreaProperties : this.fileServiceProperties.getAreas()) {
            if (fileAreaProperties.getName() == null || !fileAreaProperties.getName().equals(areaName)) continue;
            return fileAreaProperties;
        }
        return null;
    }

    private FileAreaConfig getBuildinConfig(String areaName) {
        if (this.buildinFileAreaConfigs == null) {
            return null;
        }
        for (FileAreaConfig config : this.buildinFileAreaConfigs) {
            if (config.getName() == null || !config.getName().equals(areaName)) continue;
            return config;
        }
        return null;
    }

    private FileAreaConfig createDefaultFileAreaConfig(final String areaName) {
        return new FileAreaConfig(){

            @Override
            public String getName() {
                return areaName;
            }
        };
    }
}

