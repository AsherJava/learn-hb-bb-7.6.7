/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.file.impl;

import com.jiuqi.nr.file.FileAreaConfig;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileTempAreaService;
import com.jiuqi.nr.file.impl.FileAreaConfigProvider;
import com.jiuqi.nr.file.impl.FileAreaServiceImpl;
import com.jiuqi.nr.file.impl.FileInfoService;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FileServiceImpl
extends FileAreaServiceImpl
implements FileService {
    @Autowired
    private FileTempAreaService fileTempAreaService;
    private final FileInfoService fileInfoService;
    private final FileAreaConfigProvider fileAreaConfigProvider;
    private final Map<String, FileAreaService> fileBlockServices = new LinkedHashMap<String, FileAreaService>();

    public FileServiceImpl(FileInfoService fileInfoService, FileAreaConfigProvider fileAreaConfigProvider) {
        super(fileInfoService, fileAreaConfigProvider.getFileAreaConfig("default"));
        this.fileInfoService = fileInfoService;
        this.fileAreaConfigProvider = fileAreaConfigProvider;
        this.fileBlockServices.put("default", this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public FileAreaService area(String areaName) {
        FileServiceImpl.checkAreaName(areaName);
        FileAreaService blockService = this.fileBlockServices.get(areaName);
        if (blockService != null) {
            return blockService;
        }
        Map<String, FileAreaService> map = this.fileBlockServices;
        synchronized (map) {
            blockService = this.fileBlockServices.get(areaName);
            if (blockService != null) {
                return blockService;
            }
            blockService = this.buildFileAreaService(areaName, this.fileAreaConfigProvider.getFileAreaConfig(areaName));
            this.fileBlockServices.put(areaName, blockService);
            return blockService;
        }
    }

    @Override
    public FileAreaService area(FileAreaConfig cfg) {
        if (null == cfg) {
            throw new IllegalArgumentException("cfg must not be empty.");
        }
        String areaName = cfg.getName();
        FileServiceImpl.checkAreaName(areaName);
        return this.buildFileAreaService(areaName, cfg);
    }

    @Override
    public FileAreaService tempArea() {
        String areaName = this.fileTempAreaService.getTempAreaName();
        return this.area(areaName);
    }

    private static void checkAreaName(String areaName) {
        if (StringUtils.isEmpty(areaName)) {
            throw new IllegalArgumentException("file area name must not be empty.");
        }
        if (!Pattern.matches("^[0-9a-zA-Z_]{1,}$", areaName)) {
            throw new IllegalArgumentException("file area name '" + areaName + "' format error. file area name can only contains number, letter and '_'.");
        }
    }

    protected FileAreaService buildFileAreaService(String areaName, FileAreaConfig cfg) {
        return new FileAreaServiceImpl(this.fileInfoService, cfg);
    }
}

