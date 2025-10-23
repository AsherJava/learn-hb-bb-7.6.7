/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.configurations.bean.SingleConfigInfo
 *  nr.single.map.configurations.bean.SingleFileInfo
 *  nr.single.map.configurations.dao.ConfigDao
 *  nr.single.map.configurations.dao.FileInfoDao
 *  nr.single.map.configurations.service.MappingConfigService
 *  nr.single.map.configurations.service.MappingFileService
 */
package com.jiuqi.nr.param.transfer.definition.service;

import com.jiuqi.nr.param.transfer.definition.dto.singlemap.FileType;
import com.jiuqi.nr.param.transfer.definition.dto.singlemap.SingleMappingDTO;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.SingleConfigInfo;
import nr.single.map.configurations.bean.SingleFileInfo;
import nr.single.map.configurations.dao.ConfigDao;
import nr.single.map.configurations.dao.FileInfoDao;
import nr.single.map.configurations.service.MappingConfigService;
import nr.single.map.configurations.service.MappingFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SingleMappingService {
    @Autowired
    private ConfigDao configDao;
    @Autowired
    private MappingFileService mappingFileService;
    @Autowired
    private MappingConfigService mappingConfigService;
    @Autowired
    private FileInfoDao fileInfoDao;
    private final Logger logger = LoggerFactory.getLogger(SingleMappingService.class);

    public List<SingleConfigInfo> getMappingConfigInfoByFormScheme(String formSchemeKey) {
        if (formSchemeKey == null) {
            return Collections.emptyList();
        }
        return this.configDao.queryConfigByScheme(formSchemeKey);
    }

    public SingleConfigInfo getMappingConfigInfo(String key) {
        return this.configDao.queryConfigByKey(key);
    }

    public SingleMappingDTO getMapping(String defineKey) {
        SingleConfigInfo configInfo = this.configDao.queryInfo(defineKey);
        ISingleMappingConfig query = this.configDao.query(defineKey);
        SingleMappingDTO singleMappingDTO = new SingleMappingDTO();
        singleMappingDTO.setConfigInfo(configInfo);
        singleMappingDTO.setSingleMappingConfig(query);
        SingleFileInfo singleFileInfo = this.mappingFileService.queryFileInfo(query.getMappingConfigKey());
        if (singleFileInfo != null) {
            EnumMap<FileType, byte[]> files = new EnumMap<FileType, byte[]>(FileType.class);
            singleMappingDTO.setFiles(files);
            for (FileType type : FileType.values()) {
                byte[] file = this.getFile(type, singleFileInfo);
                files.put(type, file);
            }
            singleMappingDTO.setSingleFileInfo(singleFileInfo);
        }
        return singleMappingDTO;
    }

    private byte[] getFile(FileType type, SingleFileInfo singleFileInfo) {
        String key = this.getSingleFileInfoKey(type, singleFileInfo);
        byte[] file = null;
        if (StringUtils.hasLength(key)) {
            try {
                file = this.mappingFileService.queryFile(key);
            }
            catch (Exception e) {
                this.logger.error("\u6620\u5c04\u65b9\u6848\u83b7\u53d6\u6587\u4ef6\u5931\u8d25\uff0c\u8df3\u8fc7\u6b64\u65b9\u6848\u914d\u7f6e", e);
                return null;
            }
        }
        return file;
    }

    public void deleteMappingByFormSchemeKey(String formSchemeKey) {
        List list = this.configDao.getConfigByScheme(formSchemeKey);
        for (ISingleMappingConfig config : list) {
            this.mappingConfigService.deleteMappingConfig(config.getMappingConfigKey());
        }
    }

    public void setMapping(SingleMappingDTO singleMapping) {
        if (singleMapping == null) {
            return;
        }
        SingleConfigInfo configInfo = singleMapping.getConfigInfo();
        ISingleMappingConfig config = singleMapping.getSingleMappingConfig();
        if (this.configDao.query(config.getMappingConfigKey()) != null) {
            this.configDao.update(config);
        } else {
            this.configDao.insert(config);
        }
        this.configDao.updateInfo(configInfo);
        SingleFileInfo singleFileInfo = singleMapping.getSingleFileInfo();
        if (singleFileInfo != null) {
            EnumMap<FileType, byte[]> files = singleMapping.getFiles();
            for (FileType type : FileType.values()) {
                byte[] bytes = files.get((Object)type);
                if (bytes == null) {
                    this.logger.info("-----\u6620\u5c04\u65b9\u6848\u6587\u4ef6\u4e3a\u7a7a\uff0c\u8df3\u8fc7----");
                    continue;
                }
                this.dealWithFile(type, bytes, singleFileInfo);
            }
            if (this.fileInfoDao.query(singleFileInfo.getKey()) != null) {
                this.fileInfoDao.updata(singleFileInfo);
            } else {
                this.fileInfoDao.insert(singleFileInfo);
            }
        }
    }

    private void dealWithFile(FileType type, byte[] file, SingleFileInfo singleFileInfo) {
        this.logger.info("-----\u5f00\u59cb\u4fdd\u5b58\u6620\u5c04\u65b9\u6848\u6587\u4ef6----");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(file);
        String key = this.mappingFileService.insertFile((InputStream)byteArrayInputStream);
        this.setSingleFileInfoKey(type, singleFileInfo, key);
        this.logger.info("-----\u4fdd\u5b58\u6620\u5c04\u65b9\u6848\u6587\u4ef6\u6210\u529f----");
    }

    private String getSingleFileInfoKey(FileType type, SingleFileInfo singleFileInfo) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case ZB: {
                return singleFileInfo.getZbKey();
            }
            case JIO: {
                return singleFileInfo.getJioKey();
            }
            case ENTITY: {
                return singleFileInfo.getEntityKey();
            }
            case FORMULA: {
                return singleFileInfo.getFormulaKey();
            }
        }
        return null;
    }

    private void setSingleFileInfoKey(FileType type, SingleFileInfo singleFileInfo, String key) {
        switch (type) {
            case ZB: {
                singleFileInfo.setZbKey(key);
                break;
            }
            case JIO: {
                singleFileInfo.setJioKey(key);
                break;
            }
            case ENTITY: {
                singleFileInfo.setEntityKey(key);
                break;
            }
            case FORMULA: {
                singleFileInfo.setFormulaKey(key);
                break;
            }
        }
    }
}

