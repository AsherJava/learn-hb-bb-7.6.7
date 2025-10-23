/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.definition.util.SerializeUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.mapping.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.definition.util.SerializeUtils;
import com.jiuqi.nr.mapping.bean.JIOConfig;
import com.jiuqi.nr.mapping.dao.JIOConfigDao;
import com.jiuqi.nr.mapping.dto.JIOContent;
import com.jiuqi.nr.mapping.service.JIOConfigService;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class JIOConfigServiceImpl
implements JIOConfigService {
    @Autowired
    private JIOConfigDao jioDao;

    @Override
    public void saveJIOFile(byte[] file, String msKey) {
        if (file == null || file.length == 0) {
            Assert.notNull((Object)msKey, "JIO\u6587\u4ef6\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (this.jioDao.isExist(msKey)) {
            this.jioDao.addFile(file, msKey, UUID.randomUUID().toString());
        } else {
            this.jioDao.updateFile(file, msKey);
        }
    }

    @Override
    public byte[] getJIOFileByMs(String msKey) {
        return this.jioDao.findFileByMS(msKey);
    }

    @Override
    public void saveJIOConfig(byte[] config, String msKey) {
        if (config == null || config.length == 0) {
            Assert.notNull((Object)msKey, "JIO\u6587\u4ef6\u89e3\u6790\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (this.jioDao.isExist(msKey)) {
            this.jioDao.addConfig(config, msKey, UUID.randomUUID().toString());
        } else {
            this.jioDao.updateConfig(config, msKey);
        }
    }

    @Override
    public byte[] getJIOConfigByMs(String msKey) {
        return this.jioDao.findConfigByMS(msKey);
    }

    @Override
    public void saveJIOContent(JIOContent content, String msKey) {
        if (content == null) {
            Assert.notNull((Object)msKey, "JIO\u6587\u4ef6\u914d\u7f6e\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a");
        }
        try {
            byte[] contentByte = SerializeUtils.jsonSerializeToByte((Object)content);
            if (this.jioDao.isExist(msKey)) {
                this.jioDao.addContent(contentByte, msKey, UUID.randomUUID().toString());
            } else {
                this.jioDao.updateContent(contentByte, msKey);
            }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JIOContent getJIOContentByMs(String msKey) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            byte[] contentByte = this.jioDao.findContentByMS(msKey);
            if (contentByte != null) {
                return (JIOContent)objectMapper.readValue(contentByte, JIOContent.class);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<JIOContent> batchGetJIOContentByMs(List<String> msKeys) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<byte[]> contentByte = this.jioDao.batchFindContentByMS(msKeys);
        if (contentByte != null) {
            return contentByte.stream().map(content -> {
                try {
                    return (JIOContent)objectMapper.readValue(content, JIOContent.class);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveJIO(String msKey, byte[] file, byte[] config, JIOContent content) {
        JIOConfig jioConfig = new JIOConfig();
        if (this.jioDao.isExist(msKey)) {
            jioConfig.setFile(file);
            jioConfig.setConfig(config);
            try {
                byte[] contentByte = SerializeUtils.jsonSerializeToByte((Object)content);
                jioConfig.setContent(contentByte);
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            this.jioDao.updateByMS(msKey, jioConfig);
        } else {
            jioConfig.setKey(UUID.randomUUID().toString());
            jioConfig.setMsKey(msKey);
            jioConfig.setFile(file);
            jioConfig.setConfig(config);
            try {
                byte[] contentByte = SerializeUtils.jsonSerializeToByte((Object)content);
                jioConfig.setContent(contentByte);
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            this.jioDao.add(jioConfig);
        }
    }

    @Override
    public boolean isJIO(String msKey) {
        return this.jioDao.isExist(msKey);
    }
}

